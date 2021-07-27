/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productos;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import sistemafacturacionvisual.ConeccionBD;

/**
 *
 * @author jeniy
 */
public class GestorProductos extends javax.swing.JFrame {
    DefaultTableModel modelo;
    String id,nombre,precio,estado,stock;
    /**
     * Creates new form GestorProductos
     */
    String id_pro;
    public GestorProductos() {
        initComponents();
        bloquearbotonesInicio();
        bloqueartextosIniciar();
        cargarTablaProductos();
        
        
          jTblProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (jTblProductos.getSelectedRow() != -1) {
                    int fila = jTblProductos.getSelectedRow();
                    id = jTblProductos.getValueAt(fila, 0).toString().trim();
                    nombre = jTblProductos.getValueAt(fila, 1).toString().trim();
                    precio = jTblProductos.getValueAt(fila, 2).toString().trim();
                    estado = jTblProductos.getValueAt(fila, 3).toString().trim();
                    stock = jTblProductos.getValueAt(fila, 4).toString().trim();
                    obtenerProductoModificar();
                    desbloquearTextoModificar();
                }
            }
        });
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    
    public void bloquearbotonesInicio(){
    jBntModificar.setEnabled(true);
    jBntEliminar.setEnabled(true);
    jBntGuardar.setEnabled(false);
    
 
    }
     
    
     public void desbloquearbotonesNuevo(){
    jBntNuevo.setEnabled(false);
    jBntModificar.setEnabled(false);
    jBntEliminar.setEnabled(false);
    jBntGuardar.setEnabled(true);
    
    
    }
    
    public void desbloquearbotonesGuardar(){
    jBntNuevo.setEnabled(true);
    jBntModificar.setEnabled(true);
    jBntEliminar.setEnabled(true);
    jBntGuardar.setEnabled(true);
   
    
    }
    
    public void bloqueartextosIniciar(){
    jTxtId.setEnabled(false);
    jTxtNombre.setEnabled(false);
    jTxtPrecio.setEnabled(false);
    jTxtStock.setEnabled(false);
    jTxtEstado.setEnabled(false);
    }
    
    public void desbloqueartextosIniciar(){
    jTxtId.setEnabled(true);
    jTxtNombre.setEnabled(true);
    jTxtPrecio.setEnabled(true);
    jTxtStock.setEnabled(true);
    jTxtEstado.setText("DISPONIBLE");
    jTxtEstado.setEnabled(false);
    }
        
    public void desbloquearTextoModificar() {
        if (!jTxtId.getText().isEmpty()) {
            jTxtId.setEnabled(false);
            jTxtNombre.setEnabled(true);
            jTxtPrecio.setEnabled(true);
            jTxtStock.setEnabled(true);
            jTxtEstado.setEnabled(true);
        }
    }
    
    public void limpiarTextos(){
    jTxtId.setText("");
    jTxtNombre.setText("");
    jTxtPrecio.setText("");
    jTxtStock.setText("");
    jTxtEstado.setText("");
    }
    
    public void controlarIngresoNumeros(KeyEvent e) {
    char caracter = e.getKeyChar();
        if(((caracter<'0' || caracter>'9'))){
         e.consume();
        }
    }
    
    public void controlarIngresoLetras(KeyEvent e){
    char caracter = e.getKeyChar();
        if((caracter < '0') || (caracter > '9')){
        }else{
        e.consume();
        }
    }
    
    public void controlarIngresoCostos(KeyEvent e){
          char c = e.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            e.consume();
        }
     }
    
    public void controlarEliminarProducto(){
      estado="NO DISPONIBLE";
      precio="0.0";
      stock="0";
    }
    
    public void guardarNuevoProducto() {
        if (jTxtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se a seleccionado ningun producto");
        } else if (jTxtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el ID");
            jTxtId.requestFocus();
        } else if (jTxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre Producto");
            jTxtNombre.requestFocus();
        } else if (jTxtPrecio.getText().isEmpty()||jTxtPrecio.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Costo");
            jTxtPrecio.requestFocus();
        } else if (jTxtStock.getText().isEmpty()||jTxtStock.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Stock");
            jTxtStock.requestFocus();
        } else {
            try {
                String id, nombre, disponibilidad;
                float costo;
                int stock;
                ConeccionBD cc = new ConeccionBD();
                Connection cn = cc.conectar();
                id = jTxtId.getText();
                nombre = jTxtNombre.getText();
                costo = Float.parseFloat(jTxtPrecio.getText());
                disponibilidad = jTxtEstado.getText();
                stock = Integer.parseInt(jTxtStock.getText());

                String sql = "";
                sql = "insert into productos(ID_PRO,NOM_PRO,PRE_PRO,EST_PRO,STOCK_PRO) values (?,?,?,?,?)";
                PreparedStatement psd = cn.prepareStatement(sql);

                psd.setString(1, id);
                psd.setString(2, nombre);
                psd.setFloat(3, costo);
                psd.setString(4, disponibilidad);
                psd.setInt(5, stock);

                int n = psd.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "SE GUARDO CORRECTAMENTE");
                    limpiarTextos();
                    bloqueartextosIniciar();
                    desbloquearbotonesGuardar();
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }


    public void obtenerProductoModificar() {
        jTxtId.setText(id);
        jTxtNombre.setText(nombre);
        jTxtPrecio.setText(precio);
        jTxtEstado.setText(estado);
        jTxtStock.setText(stock);
    }
    
    public void controlarModificarProducto(){
        limpiarTextos();
        bloqueartextosIniciar();
    }
    
    public void actualizarBD() {
        if (jTxtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el ID");
            jTxtId.requestFocus();
        } else if (jTxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre Producto");
            jTxtNombre.requestFocus();
        } else if (jTxtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Costo");
            jTxtPrecio.requestFocus();
        } else if (jTxtStock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Stock");
            jTxtStock.requestFocus();
        } else if (estado.equals("NO DISPONIBLE")) {
            JOptionPane.showMessageDialog(null, "NO SE PUDO ACTUALIZAR EL PRODUCTO");
            controlarModificarProducto();
        } else {
            try {
                ConeccionBD cc = new ConeccionBD();
                Connection cn = cc.conectar();
                String sql = "";
                sql = "update productos set NOM_PRO='" + jTxtNombre.getText()
                        + "',PRE_PRO='" + jTxtPrecio.getText()
                        + "',EST_PRO='" + jTxtEstado.getText()
                        + "',STOCK_PRO='" + jTxtStock.getText()
                        + "' where ID_PRO='" + jTxtId.getText() + "'";
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    cargarTablaProductos();
                    limpiarTextos();
                    bloqueartextosIniciar();
                    JOptionPane.showMessageDialog(null, "MODIFICACION");
                }
            } catch (SQLException ex) {
                Logger.getLogger(GestorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void eliminarStockProducto(){
//    EliminarStockProductos eli = new EliminarStockProductos();
//    eli.setVisible(true);
//    this.dispose();
    if (jTxtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el ID");
            jTxtId.requestFocus();
        } else if (jTxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre Producto");
            jTxtNombre.requestFocus();
        } else if (jTxtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Costo");
            jTxtPrecio.requestFocus();
        } else if (jTxtStock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Stock");
            jTxtStock.requestFocus();
        } else if (estado.isEmpty() ||estado.equals("NO DISPONIBLE")) {
            JOptionPane.showMessageDialog(null, "El producto ya no se encuentra en stock");
            limpiarTextos();
            bloqueartextosIniciar();
        } else {
            try {
                controlarEliminarProducto();
                ConeccionBD cc = new ConeccionBD();
                Connection cn = cc.conectar();
                String sql = "";
                sql = "update productos set NOM_PRO='" + nombre
                        + "',PRE_PRO='" + precio
                        + "',EST_PRO='" + estado
                        + "',STOCK_PRO='" + stock
                        + "' where ID_PRO='" + id+"'";
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    cargarTablaProductos();
                    limpiarTextos();
                    bloqueartextosIniciar();
                    JOptionPane.showMessageDialog(null, "ELIMINADO");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    
     public void cargarTablaProductos() {
        
        try {
            String[] titulos = {"ID","NOMBRE","PRECIO","ESTADO","STOCK"};
            modelo = new DefaultTableModel(null, titulos);
            String[] registros = new String[5];
            
            ConeccionBD cc = new ConeccionBD();
            Connection cn = cc.conectar();
            
            String sql = "";
            sql = "select * from productos";
            Statement psd = null;
            psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            
            while (rs.next()) {
                registros[0] = rs.getString("ID_PRO");
                registros[1] = rs.getString("NOM_PRO");
                registros[2] = rs.getString("PRE_PRO");
                registros[3] = rs.getString("EST_PRO");
                registros[4] = rs.getString("STOCK_PRO");
                modelo.addRow(registros);
                
                jTblProductos .setModel(modelo);
            } 
          
        } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
//   FILTRAR PRODUCTOS DISPONIBLES
//            String sql = "";
//            sql = "select * from productos where EST_PRO = 'DISPONIBLE'";
//            Statement psd = null;
//            psd = cn.createStatement();
//            ResultSet rs = psd.executeQuery(sql);
//            while (rs.next()) {
//                registros[0] = rs.getString("ID_PRO");
//                registros[1] = rs.getString("NOM_PRO");
//                registros[2] = rs.getString("PRE_PRO");
//                registros[3] = rs.getString("EST_PRO");
//                registros[4] = rs.getString("STOCK_PRO");
//                modelo.addRow(registros);
            
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jBntNuevo = new javax.swing.JButton();
        jBntModificar = new javax.swing.JButton();
        jBntEliminar = new javax.swing.JButton();
        jBntGuardar = new javax.swing.JButton();
        jBntCancelar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jTxtStock = new javax.swing.JTextField();
        jTxtPrecio = new javax.swing.JTextField();
        jTxtNombre = new javax.swing.JTextField();
        jTxtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLblEstado = new javax.swing.JLabel();
        jTxtEstado = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblProductos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jBntNuevo.setBackground(new java.awt.Color(204, 204, 204));
        jBntNuevo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBntNuevo.setText("NUEVO");
        jBntNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntNuevoActionPerformed(evt);
            }
        });

        jBntModificar.setBackground(new java.awt.Color(204, 204, 204));
        jBntModificar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBntModificar.setText("MODIFICAR");
        jBntModificar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntModificarActionPerformed(evt);
            }
        });

        jBntEliminar.setBackground(new java.awt.Color(204, 204, 204));
        jBntEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBntEliminar.setText("ELIMINAR");
        jBntEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntEliminarActionPerformed(evt);
            }
        });

        jBntGuardar.setBackground(new java.awt.Color(204, 204, 204));
        jBntGuardar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBntGuardar.setText("GUARDAR");
        jBntGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntGuardarActionPerformed(evt);
            }
        });

        jBntCancelar.setBackground(new java.awt.Color(204, 204, 204));
        jBntCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBntCancelar.setText("CANCELAR");
        jBntCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBntNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBntModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBntEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBntGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBntCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBntNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBntModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBntEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jBntGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jBntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jSeparator1.setForeground(new java.awt.Color(0, 51, 51));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTxtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtStockKeyTyped(evt);
            }
        });

        jTxtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtPrecioKeyTyped(evt);
            }
        });

        jTxtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtNombreKeyTyped(evt);
            }
        });

        jTxtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtIdKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("STOCK:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("PRECIO:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("NOMBRE:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("ID PRODUCTO:");

        jLblEstado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblEstado.setText("ESTADO:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLblEstado)
                        .addGap(12, 12, 12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtId)
                    .addComponent(jTxtNombre)
                    .addComponent(jTxtPrecio)
                    .addComponent(jTxtStock)
                    .addComponent(jTxtEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addGap(108, 108, 108))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblEstado)
                    .addComponent(jTxtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(jTblProductos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBntEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntEliminarActionPerformed
        eliminarStockProducto();
    }//GEN-LAST:event_jBntEliminarActionPerformed

    private void jBntGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntGuardarActionPerformed
        guardarNuevoProducto();
    }//GEN-LAST:event_jBntGuardarActionPerformed

    private void jBntNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntNuevoActionPerformed
        limpiarTextos();
        desbloqueartextosIniciar();
        desbloquearbotonesNuevo();
    }//GEN-LAST:event_jBntNuevoActionPerformed

    private void jTxtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtIdKeyTyped
        controlarIngresoNumeros(evt);
    }//GEN-LAST:event_jTxtIdKeyTyped

    private void jTxtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombreKeyTyped
//        controlarIngresoLetras(evt);
    }//GEN-LAST:event_jTxtNombreKeyTyped

    private void jTxtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPrecioKeyTyped
//        controlarIngresoCostos(evt);
    }//GEN-LAST:event_jTxtPrecioKeyTyped

    private void jTxtStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtStockKeyTyped
         controlarIngresoNumeros(evt);
    }//GEN-LAST:event_jTxtStockKeyTyped

    private void jBntModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntModificarActionPerformed
        actualizarBD();
    }//GEN-LAST:event_jBntModificarActionPerformed

    private void jBntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntCancelarActionPerformed
        
    }//GEN-LAST:event_jBntCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestorProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestorProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestorProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestorProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestorProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBntCancelar;
    private javax.swing.JButton jBntEliminar;
    private javax.swing.JButton jBntGuardar;
    private javax.swing.JButton jBntModificar;
    private javax.swing.JButton jBntNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLblEstado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTblProductos;
    private javax.swing.JTextField jTxtEstado;
    private javax.swing.JTextField jTxtId;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtPrecio;
    private javax.swing.JTextField jTxtStock;
    // End of variables declaration//GEN-END:variables
}
