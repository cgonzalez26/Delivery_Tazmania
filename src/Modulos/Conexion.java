/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Colo-PC
 */
public class Conexion {
   private static Connection cnbd = null;
   
    public static Connection obtener() {
      if(cnbd == null){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnbd= DriverManager.getConnection("jdbc:mysql://localhost:3306/delivery_tazmania", "nombre de usuario","contraseña"); // agregar nombre y contraseña con el que entran a mysql workbench y el nombre de la BD que para un ejemplo lo deje (delivery_tazmania).
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Conexion a la Base de datos sin exito");
        } catch(ClassNotFoundException e){
            throw new ClassCastException(e.getMessage());
        }  
      }
         return cnbd;
    }
    
    public static void cerrar() throws SQLException{
        if(cnbd != null){
            cnbd.close();
        }  
    }
}
