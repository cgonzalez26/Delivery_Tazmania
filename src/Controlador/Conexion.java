package Controlador;
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
   
    public Connection obtener() {
      if(cnbd == null){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnbd= DriverManager.getConnection("jdbc:mysql://localhost:3306/delivery_tazmania", "root",""); // agregar nombre y contraseña con el que entran a mysql workbench y el nombre de la BD que para un ejemplo lo deje (delivery_tazmania).
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Conexion a la Base de datos sin exito");
            e.printStackTrace();
            System.exit(0);
        } catch(ClassNotFoundException e){
            //throw new ClassCastException(e.getMessage());
            e.printStackTrace();
        }  
      }
         return cnbd;
    }
    public Connection conectado(){
      return cnbd;
    }
    public static void cerrar() throws SQLException{
        if(cnbd != null){
            cnbd.close();
        }  
    }
}
