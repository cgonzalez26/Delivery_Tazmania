/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author Colo-PC
 */
public class Conexion {
    Connection cnbd = null;
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnbd= DriverManager.getConnection("jdbc:mysql://localhost:3306/");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexion!");
            System.exit(0);
        }
         return cnbd;
    }
}
