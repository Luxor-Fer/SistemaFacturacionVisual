/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazAdmin;

import cajero.FacturaVenta;
import cajero.GestorClientes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;//
import net.sf.jasperreports.engine.JasperFillManager;//
import net.sf.jasperreports.engine.JasperPrint;//
import net.sf.jasperreports.engine.JasperReport;//
import net.sf.jasperreports.view.JasperViewer;//
import productos.EliminarStockProductos;
import productos.GestorInterno;
import productos.GestorProductos;
import productos.IngresarProductos;
import sistemafacturacionvisual.ConeccionBD;
import sistemafacturacionvisual.SistemaFacturacionVisual;
import sistemafacturacionvisual.VentanaPrincipal;

/**
 *
 * @author windows
 */
public class ventanaAdmin extends javax.swing.JFrame {

    /**
     * Creates new form ventanaAdmin
     */
    public ventanaAdmin() {
        initComponents();
    }
    public ventanaAdmin( boolean op){
        initComponents();
        jMenu1.setVisible(false);
    }
    
    public void abrirEliminarProductos(){
        EliminarStockProductos ventana = new EliminarStockProductos();
        jDesk.add(ventana);
        ventana.setVisible(true);
    }
    public void abrirGestorProductos(){
        //GestorInterno ventana = new GestorInterno();
        try {
        GestorInterno ventana = new GestorInterno();
        jDesk.add(ventana);
        ventana.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    ////PROBARGESTORCLIENTES
    public void abrirGestorCliente(){
        //GestorInterno ventana = new GestorInterno();
        try {
        GestorClientes ventana = new GestorClientes();
        jDesk.add(ventana);
        ventana.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void abrirIngresarProductos (){
        IngresarProductos ventana = new IngresarProductos();
        jDesk.add(ventana);
        ventana.setVisible(true);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesk = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesk.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jDesk.setForeground(new java.awt.Color(0, 153, 153));
        jDesk.setName("Administrador"); // NOI18N

        javax.swing.GroupLayout jDeskLayout = new javax.swing.GroupLayout(jDesk);
        jDesk.setLayout(jDeskLayout);
        jDeskLayout.setHorizontalGroup(
            jDeskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );
        jDeskLayout.setVerticalGroup(
            jDeskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );

        jMenu1.setText("Productos");

        jMenuItem3.setText("Ingresar Productos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem5.setText("Baja de Productos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem2.setText("Gestionar Productos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Clientes");

        jMenuItem1.setText("Gestion Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ventas");

        jMenuItem4.setText("Nueva Venta");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem6.setText("Ultima Factura");
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        jMenu5.setText("Reportes");

        jMenuItem9.setText("Facturas");
        jMenu5.add(jMenuItem9);

        jMenuItem10.setText("Productos Faltantes");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem10);

        jMenuItem11.setText("Mas Vendido Por Semana");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenuItem12.setText("Historial Mas Vendido");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem12);

        jMenuItem13.setText("Historial Facturas");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem13);

        jMenuBar1.add(jMenu5);

        jMenu4.setText("Salir");

        jMenuItem7.setText("Cerrar Sesi??n");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setText("EXIT");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesk)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesk)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        abrirGestorProductos();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        abrirGestorCliente();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        abrirEliminarProductos();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        abrirIngresarProductos ();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        FacturaVenta ventana = new FacturaVenta();
        jDesk.add(ventana);
        ventana.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        VentanaPrincipal ven = new VentanaPrincipal();
        ven.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        try {
        ConeccionBD cc = new ConeccionBD();
        //Map ced = new HashMap();
        JasperReport reporte = JasperCompileManager.compileReport("C:/Users/windows/Documents/netBeans8.2/SistemaFacturacionVisual/SistemaFacturacionVisual/SistemaFacturacionVisual/src/reportesIReport/HistorialReSportes.jrxml");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, cc.conectar());
        JasperViewer.viewReport(print, false);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
        ConeccionBD cc = new ConeccionBD();
        //Map ced = new HashMap();
        JasperReport reporte = JasperCompileManager.compileReport("C:/Users/windows/Documents/netBeans8.2/SistemaFacturacionVisual/SistemaFacturacionVisual/SistemaFacturacionVisual/src/reportesIReport/ProductosNoDisponibles.jrxml");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, cc.conectar());
        JasperViewer.viewReport(print, false);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        try {
        ConeccionBD cc = new ConeccionBD();
        //Map ced = new HashMap();
        JasperReport reporte = JasperCompileManager.compileReport("C:/Users/windows/Documents/netBeans8.2/SistemaFacturacionVisual/SistemaFacturacionVisual/SistemaFacturacionVisual/src/reportesIReport/ReporteMasVendidos.jrxml");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, cc.conectar());
        JasperViewer.viewReport(print, false);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        try {
        ConeccionBD cc = new ConeccionBD();
        //Map ced = new HashMap();
        JasperReport reporte = JasperCompileManager.compileReport("C:/Users/windows/Documents/netBeans8.2/SistemaFacturacionVisual/SistemaFacturacionVisual/SistemaFacturacionVisual/src/reportesIReport/ReporteMasVendidosSemana.jrxml");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, cc.conectar());
        JasperViewer.viewReport(print, false);
            
        } catch (Exception e) {
            System.out.println(e);
        }        
    }//GEN-LAST:event_jMenuItem11ActionPerformed

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
            java.util.logging.Logger.getLogger(ventanaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesk;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
}
