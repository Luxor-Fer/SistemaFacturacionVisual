/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cajero;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import sistemafacturacionvisual.ConeccionBD;
import sistemafacturacionvisual.VentanaPrincipal;

/**
 *
 * @author jeniy
 */
public class FacturaVenta extends javax.swing.JInternalFrame {
//    DefaultTableModel modelo;
    /**
     * Creates new form FacturaVenta
     */
    String id,nombre,precio,cantidad,total;
    DefaultTableModel modelo;
    public FacturaVenta() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        controlarDatosFactura();
        botonesInicio();
        cargarTabla();
        
        jTblProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                desbloquearBotonesVender();
                if (jTblProductos.getSelectedRow() != -1) {
                    int fila = jTblProductos.getSelectedRow();
                    id = jTblProductos.getValueAt(fila, 0).toString().trim();
                    nombre = jTblProductos.getValueAt(fila, 1).toString().trim();
                    precio = jTblProductos.getValueAt(fila, 2).toString().trim();
                    cantidad = jTblProductos.getValueAt(fila, 3).toString().trim();
                    total = jTblProductos.getValueAt(fila, 4).toString().trim();
                }
            }
        });
    }
    
    public void controlarDatosFactura(){
     jTxtFecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        jTxtFecha.setEnabled(false);
        jLblCheck.setVisible(false);
        jLblError.setVisible(false);
        jTxtChina.setText(String.valueOf(obtenerNumFact()));
        jTxtChina.setEnabled(false);
        jTxtCedVend.setText(VentanaPrincipal.cedIngresa);
        jTxtCedVend.setEnabled(false);
    }
    
    public void limpiarFactura(){
    jLblTotal.setText("0");
    jTxtCedula.setText("");
    jLblCheck.setVisible(false);
    jLblError.setVisible(false);
    jTxtCedula.setEnabled(true);
    botonesInicio();
    int filas=jTblProductos.getRowCount();
    for (int i = 0;i<=filas-1; i++) {
    modelo.removeRow(0);
    }
    jTblProductos.setModel(modelo);
    }
    
    public void botonesInicio(){
    jBtnBuscarProducto.setEnabled(false);
    jBtnEliminar.setEnabled(false);
    jBtnVender.setEnabled(false);
    }
   
    public void desbloquearVerificacion(){
        jTxtCedula.setEnabled(false);
        jBtnBuscarProducto.setEnabled(true);
    
    }

    public void desbloquearBotonesVender() {
        if (jTblProductos.getRowCount() == 0) {
            jBtnEliminar.setEnabled(false);
            jBtnVender.setEnabled(false);
        }else{
         jBtnEliminar.setEnabled(true);
            jBtnVender.setEnabled(true);
        }
    }

    
    public void cargarTabla (){
        String[] titulos = {"ID","NOMBRE","PRECIO","CANTIDAD","TOTAL"};
        modelo = new DefaultTableModel(null, titulos);
        jTblProductos.setModel(modelo);
        String[] registros = new String[5];
        
    }
    
    public void eliminarProductoSeleccionado(){
        modelo.removeRow(jTblProductos.getSelectedRow());
        jTblProductos.setModel(modelo);
        Float valPro = Float.parseFloat(total);
        Float valtot = Float.parseFloat(jLblTotal.getText());
        Float res = valtot - valPro;
        jLblTotal.setText(String.valueOf(res));
    }
    
    public void controlarIngresoNumeros(KeyEvent e) {
    char caracter = e.getKeyChar();
        if(((caracter<'0' || caracter>'9'))){
         e.consume();
        }
    }
    
    public int obtenerNumFact() {
        try {
            ConeccionBD cn = new ConeccionBD();
            Connection cc = cn.conectar();
            String sql = "SELECT MAX(NUM_FAC) FROM FACTURA";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            int val = 0;
            if (rs.next()) {
                val = rs.getInt("MAX(NUM_FAC)");
                return val + 1;
            } else {
                return 1;
            }
        } catch (SQLException ex) {
            return 0;
        }
    }

    
    public void verProductos(JTable tabla){
    TablaProductos tp = new TablaProductos();
    tp.agregarTabla(tabla, this, jLblTotal);
    tp.setVisible(true);
    }
    public void validarCedula (){
        try {
            ConeccionBD cn = new ConeccionBD();
            Connection cc = cn.conectar();
            String sql = "SELECT CED_CLI FROM CLIENTES "
                    + "WHERE CED_CLI = "+ jTxtCedula.getText();
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            if (rs.first()){
                jLblError.setVisible(false);
                jLblCheck.setVisible(true);
                desbloquearVerificacion();
            }else{
                jLblCheck.setVisible(false);
                jLblError.setVisible(true);
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean metodoActuStock(Integer cant, String id) {
        try {
            ConeccionBD cc = new ConeccionBD();
            Connection cn = cc.conectar();
            
            String sqlupdate = "UPDATE PRODUCTOS SET STOCK_PRO =" + cant + " WHERE ID_PRO = '" + id + "'";
            PreparedStatement psd2 = cn.prepareStatement(sqlupdate);
            psd2.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
    public Integer metodoSelectStock (String id1){
        try {
            int consulta = 0;

            ConeccionBD cc = new ConeccionBD();
            Connection cn = cc.conectar();
            
            String sqlsoporte = "SELECT STOCK_PRO FROM PRODUCTOS WHERE ID_PRO = '"+id1+"'";
            Statement psd3 = cn.createStatement();
            ResultSet rs = psd3.executeQuery(sqlsoporte);
            while(rs.next())
                consulta = rs.getInt("STOCK_PRO"); 
            return consulta;
            
        } catch (SQLException ex) {
            System.out.println("error select stock: "+ ex);
            return 0;
        }
    }
    public void realizarVenta(){
        try {
            ConeccionBD cc = new ConeccionBD();
            Connection cn = cc.conectar();
            String sqlFac = "INSERT INTO FACTURA VALUES("+Integer.parseInt(jTxtChina.getText())+", '"
                    + jTxtFecha.getText()+"', '"+jTxtCedVend.getText()+"', '"+jTxtCedula.getText()+"', "+Float.parseFloat(jLblTotal.getText())+")";
            PreparedStatement psd = cn.prepareStatement(sqlFac);
            int n = psd.executeUpdate();
            
            for (int i = 0; i < jTblProductos.getRowCount(); i++) {
                String id2 = jTblProductos.getValueAt(i, 0).toString().trim();
                Integer cant = Integer.parseInt(jTblProductos.getValueAt(i, 3).toString());
                String sqlDet = "INSERT INTO DETALLE_PRODUCTOS VALUES ('"+id2+"', "+cant+", "+Integer.parseInt(jTxtChina.getText())+")";
                PreparedStatement psd1 = cn.prepareStatement(sqlDet);
                int b = psd1.executeUpdate();
                if(b>0)
                    System.out.println("holi");
                
                Integer nuevoStock = metodoSelectStock(id2);
                cant = nuevoStock - cant;
                
                metodoActuStock(cant, id2);

                }
            if(n>0){
                System.out.println("clase: Factura Venta (realizarVenta) implementar reporte factura enviar");
                limpiarFactura();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void verificarCantidadStock(){
        try {
            ConeccionBD cn = new ConeccionBD();
            Connection cc = cn.conectar();
            String sql = "SELECT ID_PRO FROM PRODUCTOS "
                    + "WHERE EST_PRO = 'DISPONIBLE' "
                    + "AND STOCK_PRO = 0";
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while ( rs.next() ){
                cambiarEstdoProducto(rs.getString("ID_PRO"), cc);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void cambiarEstdoProducto (String idPro, Connection cc){
        try {
            String sql = "UPDATE PRODUCTOS SET EST_PRO = 'NO DISPONIBLE' WHERE ID_PRO = '"+idPro+"'";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblProductos = new javax.swing.JTable();
        jLblTotalText = new javax.swing.JLabel();
        jLblTotal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtChina = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTxtCedVend = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTxtFecha = new javax.swing.JTextField();
        jLblCheck = new javax.swing.JLabel();
        jLblError = new javax.swing.JLabel();
        jTxtCedula = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jBtnBuscarProducto = new javax.swing.JButton();
        jBtnVender = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTblProductos);

        jLblTotalText.setBackground(new java.awt.Color(204, 204, 255));
        jLblTotalText.setText("TOTAL : ");
        jLblTotalText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblTotal.setText("0");
        jLblTotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLblTotalText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLblTotalText)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Nº  FACTURA:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("FECHA:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("C.I. CLIENTE");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("C.I. VENDEDOR");

        jButton4.setText("VALIDAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLblCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png"))); // NOI18N

        jLblError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alertTxt.png"))); // NOI18N

        jTxtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCedulaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtCedVend, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(jTxtChina)
                    .addComponent(jTxtFecha)
                    .addComponent(jTxtCedula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblError, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTxtChina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jButton4)
                                .addComponent(jTxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblCheck, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtCedVend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jBtnBuscarProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnBuscarProducto.setText("BUSCAR PRODUCTO");
        jBtnBuscarProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarProductoActionPerformed(evt);
            }
        });

        jBtnVender.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnVender.setText("VENDER");
        jBtnVender.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnVenderActionPerformed(evt);
            }
        });

        jBtnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnLimpiar.setText("LIMPIAR");
        jBtnLimpiar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnLimpiar.setPreferredSize(new java.awt.Dimension(85, 29));
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnEliminar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnEliminar.setText("ELIMINAR");
        jBtnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jBtnSalir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnSalir.setText("SALIR");
        jBtnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnSalir.setPreferredSize(new java.awt.Dimension(85, 29));
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnBuscarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jBtnVender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnVender, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarProductoActionPerformed
        verProductos(jTblProductos);
    }//GEN-LAST:event_jBtnBuscarProductoActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        validarCedula();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTxtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCedulaKeyTyped
        controlarIngresoNumeros(evt);
    }//GEN-LAST:event_jTxtCedulaKeyTyped

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
      limpiarFactura();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        eliminarProductoSeleccionado();
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnVenderActionPerformed
        realizarVenta();
        verificarCantidadStock();
    }//GEN-LAST:event_jBtnVenderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnBuscarProducto;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnVender;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLblCheck;
    private javax.swing.JLabel jLblError;
    private javax.swing.JLabel jLblTotal;
    private javax.swing.JLabel jLblTotalText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblProductos;
    private javax.swing.JTextField jTxtCedVend;
    private javax.swing.JTextField jTxtCedula;
    private javax.swing.JTextField jTxtChina;
    private javax.swing.JTextField jTxtFecha;
    // End of variables declaration//GEN-END:variables
}
