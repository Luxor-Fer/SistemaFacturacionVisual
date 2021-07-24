/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemafacturacionvisual;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author windows
 */
public class ConeccionBD {
    Connection connect=null;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/sistema_facturacion_visual","root","");
            //JOptionPane.showMessageDialog(null, "Se conecto correctamente");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se conecto a la base de datos");
        }
        return connect;
    }
}
