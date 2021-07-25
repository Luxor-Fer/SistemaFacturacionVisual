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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import sistemafacturacionvisual.ConeccionBD;

/**
 *
 * @author jeniy
 */
public class GestorClientes extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    String cedula,nombre,apellido,telefono,direccion;

    /**
     * Creates new form GestorClientes
     */
    public GestorClientes() {
        initComponents();
        bloquearbotonesInicio();
        bloqueartextosIniciar();
        cargarTablaCliente();
        
          jTblClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (jTblClientes.getSelectedRow() != -1) {
                    int fila = jTblClientes.getSelectedRow();
                    cedula = jTblClientes.getValueAt(fila, 0).toString().trim();
                    nombre = jTblClientes.getValueAt(fila, 1).toString().trim();
                    apellido = jTblClientes.getValueAt(fila, 2).toString().trim();
                    telefono = jTblClientes.getValueAt(fila, 3).toString().trim();
                    direccion = jTblClientes.getValueAt(fila, 4).toString().trim();
                    desbloquearBotones();
                    obtenerClienteModificar();
                    desbloquearTextoModificar();
                }
            }
        });
    }

    public void  desbloquearBotones(){
    jBntNuevo.setEnabled(true);
    jBntModificar.setEnabled(true);
    jBntGuardar.setEnabled(false);
    jBntCancelar.setEnabled(true);
    }
    public void bloquearbotonesInicio(){
    jBntNuevo.setEnabled(true); 
    jBntModificar.setEnabled(false);
    jBntGuardar.setEnabled(false);
    jBntCancelar.setEnabled(true);
    }
     
     public void desbloquearbotonesNuevo(){
    jBntNuevo.setEnabled(false);
    jBntModificar.setEnabled(false);
    jBntGuardar.setEnabled(true);
    }
    
    public void desbloquearbotonesGuardar(){
    jBntGuardar.setEnabled(false);
    }
    
    public void bloqueartextosIniciar(){
    jTxtCedula.setEnabled(false);
    jTxtNombre.setEnabled(false);
    jTxtApellido.setEnabled(false);
    jTxtTelefono.setEnabled(false);
    jTxtDireccion.setEnabled(false);
    }
    
    public void desbloqueartextosIniciar(){
    jTxtCedula.setEnabled(true);
    jTxtNombre.setEnabled(true);
    jTxtApellido.setEnabled(true);
    jTxtTelefono.setEnabled(true);
    jTxtDireccion.setEnabled(true);
    }
        
    public void desbloquearTextoModificar() {
        if (!jTxtCedula.getText().isEmpty()) {
            jTxtCedula.setEnabled(false);
            jTxtNombre.setEnabled(true);
            jTxtApellido.setEnabled(true);
            jTxtTelefono.setEnabled(true);
            jTxtDireccion.setEnabled(true);
        }
    }
    
    public void limpiarTextos(){
    jTxtCedula.setText("");
    jTxtNombre.setText("");
    jTxtApellido.setText("");
    jTxtTelefono.setText("");
    jTxtDireccion.setText("");
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
    
 public void guardarNuevoCliente() {
        
        if (jTxtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la Cedula");
            jTxtCedula.requestFocus();
        } else if (jTxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre");
            jTxtNombre.requestFocus();
        } else if (jTxtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Apellido");
            jTxtApellido.requestFocus();
        } else {
            
            if (jTxtTelefono.getText().isEmpty()) {
            jTxtTelefono.setText("0000000000");
            }
            if(jTxtDireccion.getText().isEmpty()){
                jTxtDireccion.setText("N/A");
            }
            try {
                String cedula, nombre, apellido,telefono,direccion;

                ConeccionBD cc = new ConeccionBD();
                Connection cn = cc.conectar();
                cedula = jTxtCedula.getText();
                nombre = jTxtNombre.getText();
                apellido = jTxtApellido.getText();
                telefono = jTxtTelefono.getText();
                direccion = jTxtDireccion.getText();

                String sql = "";
                sql = "insert into clientes(CED_CLI,NOM_CLI,APE_CLI,TEL_CLI,DIR_CLI) values (?,?,?,?,?)";
                PreparedStatement psd = cn.prepareStatement(sql);

                psd.setString(1, cedula);
                psd.setString(2, nombre);
                psd.setString(3, apellido);
                psd.setString(4, telefono);
                psd.setString(5, direccion);

                int n = psd.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "SE GUARDO CORRECTAMENTE");
                    limpiarTextos();
                    bloqueartextosIniciar();
                    cargarTablaCliente();
                    desbloquearbotonesGuardar();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void obtenerClienteModificar() {
        jTxtCedula.setText(cedula);
        jTxtNombre.setText(nombre);
        jTxtApellido.setText(apellido);
        jTxtTelefono.setText(telefono);
        jTxtDireccion.setText(direccion);
    }
  
       public void controlarModificarCliente(){
        limpiarTextos();
        bloqueartextosIniciar();
    }
    
    public void actualizarBD() {
        
        if (jTxtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la Cedula");
            jTxtCedula.requestFocus();
        } else if (jTxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre");
            jTxtNombre.requestFocus();
        } else if (jTxtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Apellido");
            jTxtApellido.requestFocus();
        } else {
            try {
                if (jTxtTelefono.getText().equals("")||jTxtTelefono.getText().isEmpty()) {
                    jTxtTelefono.setText("0000000000");
                }
                if (jTxtDireccion.getText().isEmpty()||jTxtTelefono.getText().equals("")) {
                    jTxtDireccion.setText("N/A");
                }
                ConeccionBD cc = new ConeccionBD();
                Connection cn = cc.conectar();
                String sql = "";
                sql = "update clientes set NOM_CLI='" + jTxtNombre.getText()
                        + "',APE_CLI='" + jTxtApellido.getText()
                        + "',TEL_CLI='" + jTxtTelefono.getText()
                        + "',DIR_CLI='" + jTxtDireccion.getText()
                        + "'where CED_CLI='" + jTxtCedula.getText()+"'";
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    cargarTablaCliente();
                    limpiarTextos();
                    bloqueartextosIniciar();
                    JOptionPane.showMessageDialog(null, "DATOS ACTUALIZADOS");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);

            }
        }
    }

    public void cargarTablaCliente() {
        
        try {
            String[] titulos = {"CEDULA","NOMBRE","APELLIDO","TELEFONO","DIRECCION"};
            modelo = new DefaultTableModel(null, titulos);
            String[] registros = new String[5];
            
            ConeccionBD cc = new ConeccionBD();
            Connection cn = cc.conectar();
            
            String sql = "";
            sql = "select * from clientes";
            Statement psd = null;
            psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            
            while (rs.next()) {
                registros[0] = rs.getString("CED_CLI");
                registros[1] = rs.getString("NOM_CLI");
                registros[2] = rs.getString("APE_CLI");
                registros[3] = rs.getString("TEL_CLI");
                registros[4] = rs.getString("DIR_CLI");
                modelo.addRow(registros);
                
                jTblClientes .setModel(modelo);
            } 
          
        } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtCedula = new javax.swing.JTextField();
        jTxtNombre = new javax.swing.JTextField();
        jTxtApellido = new javax.swing.JTextField();
        jTxtTelefono = new javax.swing.JTextField();
        jTxtDireccion = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jBntNuevo = new javax.swing.JButton();
        jBntModificar = new javax.swing.JButton();
        jBntGuardar = new javax.swing.JButton();
        jBntCancelar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblClientes = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 204, 204));
        setName("Registro Clientes"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("CEDULA:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("NOMBRE:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("APELLIDO:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("TELEFONO:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("DIRECCION:");

        jTxtCedula.setToolTipText("");
        jTxtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtCedulaKeyTyped(evt);
            }
        });

        jTxtNombre.setToolTipText("");
        jTxtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtNombreKeyTyped(evt);
            }
        });

        jTxtApellido.setToolTipText("");
        jTxtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtApellidoKeyTyped(evt);
            }
        });

        jTxtTelefono.setToolTipText("");
        jTxtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtTelefonoKeyTyped(evt);
            }
        });

        jTxtDireccion.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(64, 64, 64)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtCedula)
                    .addComponent(jTxtNombre)
                    .addComponent(jTxtApellido)
                    .addComponent(jTxtTelefono)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jBntNuevo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jBntNuevo.setText("NUEVO");
        jBntNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntNuevoActionPerformed(evt);
            }
        });

        jBntModificar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jBntModificar.setText("MODIFICAR");
        jBntModificar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntModificarActionPerformed(evt);
            }
        });

        jBntGuardar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jBntGuardar.setText("GUARDAR");
        jBntGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntGuardarActionPerformed(evt);
            }
        });

        jBntCancelar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jBntCancelar.setText("CANCELAR");
        jBntCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBntCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBntNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBntModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(jBntGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBntCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jBntNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jBntModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBntGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTblClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTblClientes);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBntNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntNuevoActionPerformed
        limpiarTextos();
        desbloqueartextosIniciar();
        desbloquearbotonesNuevo();
    }//GEN-LAST:event_jBntNuevoActionPerformed

    private void jBntModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntModificarActionPerformed
        actualizarBD();
    }//GEN-LAST:event_jBntModificarActionPerformed

    private void jBntGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntGuardarActionPerformed
        guardarNuevoCliente();
    }//GEN-LAST:event_jBntGuardarActionPerformed

    private void jBntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBntCancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jBntCancelarActionPerformed

    private void jTxtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCedulaKeyTyped
        controlarIngresoNumeros(evt);
    }//GEN-LAST:event_jTxtCedulaKeyTyped

    private void jTxtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombreKeyTyped
        controlarIngresoLetras(evt);
    }//GEN-LAST:event_jTxtNombreKeyTyped

    private void jTxtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtApellidoKeyTyped
        controlarIngresoLetras(evt);
    }//GEN-LAST:event_jTxtApellidoKeyTyped

    private void jTxtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTelefonoKeyTyped
        controlarIngresoNumeros(evt);
    }//GEN-LAST:event_jTxtTelefonoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBntCancelar;
    private javax.swing.JButton jBntGuardar;
    private javax.swing.JButton jBntModificar;
    private javax.swing.JButton jBntNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblClientes;
    private javax.swing.JTextField jTxtApellido;
    private javax.swing.JTextField jTxtCedula;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtTelefono;
    // End of variables declaration//GEN-END:variables
}
