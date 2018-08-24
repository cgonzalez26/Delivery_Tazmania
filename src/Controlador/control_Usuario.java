package Controlador;



import Modelo.modelo_Usuario;

import Modelo.Conexion;
import Modelo.mPersona;
import Modelo.mUsuario;
import Vistas.vLogin;
import Vistas.vGestion_Usuarios;
import Vistas.vMenuPrincipal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public class control_Usuario extends modelo_Usuario {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    private String sql1 = "", sql2="";

    public control_Usuario() {

    }
    
    //Cada consulta o query lo hice creando una bd temporal para su correcto funcionamiento.
    
    @Override
    public DefaultTableModel MostrarDatos() throws SQLException {
        DefaultTableModel model;
        String titulos[] = {
            "N°", "Nombre", "Apellido", "Domicilio", "Telefono Contacto", "Correo Electronico", "Usuario", "Contraseña", "Acceso", "Estado"};
        model = new DefaultTableModel(null, titulos);

        String[] datos = new String[10];
        sql1 = "SELECT P.ID_PERSONA, P.NOMBRE_PERSONA, P.APELLIDO_PERSONA, P.DOMICILIO_PERSONA, P.TELCONTACTO_PERSONA, P.EMAILCONTACTO_PERSONA, "
                + " U.NOMBRE_USUARIO, U.CONTRASEÑA_USUARIO ,U.TIPO_USUARIO, U.ESTADO_USUARIO "
                + " FROM Personas P INNER JOIN Usuarios U WHERE P.ID_PERSONA=U.ID_PERSONA";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_PERSONA");
                datos[1] = rs.getString("NOMBRE_PERSONA");
                datos[2] = rs.getString("APELLIDO_PERSONA");
                datos[3] = rs.getString("DOMICILIO_PERSONA");
                datos[4] = rs.getString("TELCONTACTO_PERSONA");
                datos[5] = rs.getString("EMAILCONTACTO_PERSONA");
                datos[6] = rs.getString("NOMBRE_USUARIO");
                datos[7] = rs.getString("CONTRASEÑA_USUARIO");
                datos[8] = rs.getString("TIPO_USUARIO");
                datos[9] = rs.getString("ESTADO_USUARIO");
                model.addRow(datos);

            }

            if (rs != null || pst1 != null) {
                rs.close();
                pst1.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } finally {
            /*Conexion.cerrar();*/
        }
        return model;
    }

    @Override
    public boolean InsertarUsuarios(modelo_Usuario modelo) {

        sql1 = "Insert into Personas (NOMBRE_PERSONA, APELLIDO_PERSONA, DOMICILIO_PERSONA, TELCONTACTO_PERSONA, EMAILCONTACTO_PERSONA)"
                + "VALUES (?,?,?,?,?)";
        
            sql2 = "Insert into Usuarios (NOMBRE_USUARIO, CONTRASEÑA_USUARIO, TIPO_USUARIO, ESTADO_USUARIO, ID_PERSONA)"
                + "VALUES (?,?,?,?,(select ID_PERSONA from Personas ORDER BY ID_PERSONA DESC LIMIT 1))";
        
        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);

            PreparedStatement pst2 = cn.prepareStatement(sql2);

            pst1.setString(1, modelo.getNombre_persona());
            pst1.setString(2, modelo.getApellido_persona());
            pst1.setString(3, modelo.getDomicilio_persona());
            pst1.setString(4, modelo.getTelefonoContacto_persona());
            pst1.setString(5, modelo.getEmailContacto_persona());

            int ej1 = pst1.executeUpdate();

            pst2.setString(1, modelo.getNombre_usuario());
            pst2.setString(2, modelo.getContraseña_usuario());
            pst2.setString(3, modelo.getTipo_usario());
            pst2.setBoolean(4, modelo.isEstado_usuario());

            int ej2 = pst2.executeUpdate();

            if (ej1 != 0 || ej2 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public boolean EditarUsuarios(modelo_Usuario modelo) {
        sql1 = " UPDATE PERSONAS SET NOMBRE_PERSONA = ?, APELLIDO_PERSONA= ?, DOMICILIO_PERSONA= ?, TELCONTACTO_PERSONA= ?, EMAILCONTACTO_PERSONA= ? "
                + " WHERE ID_PERSONA=? ";

        sql2 = " UPDATE USUARIOS SET NOMBRE_USUARIO= ?, CONTRASEÑA_USUARIO= ?, TIPO_USUARIO= ?, ESTADO_USUARIO= ? "
                + " WHERE ID_PERSONA= ? ";

        try {
            PreparedStatement pst1 = cn.prepareStatement(sql1);
            PreparedStatement pst2 = cn.prepareStatement(sql2);

            pst1.setString(1, modelo.getNombre_persona());
            pst1.setString(2, modelo.getApellido_persona());
            pst1.setString(3, modelo.getDomicilio_persona());
            pst1.setString(4, modelo.getTelefonoContacto_persona());
            pst1.setString(5, modelo.getEmailContacto_persona());
            pst1.setInt(6, modelo.getId_persona());

            pst2.setString(1, modelo.getNombre_usuario());
            pst2.setString(2, modelo.getContraseña_usuario());
            pst2.setString(3, modelo.getTipo_usario());
            pst2.setBoolean(4, modelo.isEstado_usuario());
            pst2.setInt(5, modelo.getId_usuario());

            int N1 = pst1.executeUpdate();
            int N2 = pst2.executeUpdate();

            if (N1 != 0 || N1 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public boolean EliminarUsuarios(modelo_Usuario modelo) {

        try {
            /*sql1=" DELETE u.* FROM PERSONAS p INNER JOIN USUARIOS u ON u.id_persona=p.id_persona WHERE (u.id_persona=p.id_persona) ";*/

            PreparedStatement pst1 = cn.prepareStatement(" DELETE from Personas where id_persona= ? ");

            pst1.setInt(1, modelo.getId_persona());

            int N1 = pst1.executeUpdate();

            if (N1 != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public int VerificarLogin() {
        String sql = "select count(nombre_usuario) from Usuarios where nombre_usuario =" + "'" + vGestion_Usuarios.jTextNomUser_Usuario.getText() + "'";
        try {
            int resultado = 0;
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                resultado = rs.getInt(1);
            }
            rs.close();
            pst.close();
            if (resultado > 0) {
                return resultado;
            }
            return resultado;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean VerificarInicioSesion() {

        String cadena1 = vLogin.jTextNomUser_Login.getText().trim();
        String cadena2 = vLogin.jTextoPass_Login.getText().trim();

        String sql1 = "SELECT nombre_usuario, contraseña_usuario, tipo_usuario FROM USUARIOS WHERE nombre_usuario='"
                + cadena1 + "' and contraseña_usuario='" + cadena2 + "'"; //Ejemplo de una consulta de tabla que tenga usuarios.
        String sql2 = "SELECT estado_usuario from Usuarios where nombre_usuario='" + cadena1 + "' and contraseña_usuario='" + cadena2 + "'";

        String sql3 = "SET SQL_SAFE_UPDATES=0";

        String sql4 = "update Usuarios set estado_usuario=true where nombre_usuario IN (select nombre_usuario from (select * from  Usuarios where nombre_usuario='" + cadena1 + "' and contraseña_usuario='" + cadena2 + "') as todo)";

        try {

            String user = "", pass = "", tipo = "";
            boolean estado1 = false;

            Statement st1 = cn.createStatement();
            Statement st2 = cn.createStatement();
            PreparedStatement pst3 = cn.prepareStatement(sql3);
            PreparedStatement pst4 = cn.prepareStatement(sql4);

            ResultSet rs1 = st1.executeQuery(sql1);
            ResultSet rs2 = st2.executeQuery(sql2);

            pst3.executeUpdate(sql3);
            pst4.executeUpdate(sql4);

            while (rs1.next()) {
                user = rs1.getString("nombre_usuario");
                pass = rs1.getString("contraseña_usuario");
                tipo = rs1.getString("tipo_usuario");
            }

            while (rs2.next()) {
                estado1 = rs2.getBoolean(1);
            }

            st1.close();
            st2.close();
            pst3.close();
            pst4.close();
            rs1.close();
            rs2.close();

            if (cadena1.equals(user) && cadena2.equals(pass) && estado1 == false && tipo.equals("Administrador")) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + vLogin.jTextNomUser_Login.getText());

                vMenuPrincipal menu = new vMenuPrincipal();
                menu.setVisible(true);
                menu.setSize(1292, 662);
                menu.setResizable(false);
                menu.setLocationRelativeTo(null);

                return true;

            } else if (cadena1.equals(user) && cadena2.equals(pass) && estado1 == false && tipo.equals("Usuario Común")) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + vLogin.jTextNomUser_Login.getText());
                vMenuPrincipal menu = new vMenuPrincipal();
                menu.setVisible(true);
                menu.setSize(1292, 662);
                menu.setResizable(false);
                menu.setLocationRelativeTo(null);

                return true;
            } else if (cadena1.equals(user) && cadena2.equals(pass) && estado1 == true) {
                JOptionPane.showMessageDialog(null, "Este usuario " + vLogin.jTextNomUser_Login.getText() + " ya inicio sesion!");

                vLogin.jTextNomUser_Login.requestFocus();
                return false;

            } else{
                JOptionPane.showMessageDialog(null, "Usuario y Contraseña no validos o inexistente");
                vLogin.jTextNomUser_Login.requestFocus();
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vLogin.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean cerrarEstado() {
        String sql1 = "update Usuarios set estado_usuario= false where estado_usuario=true";
        String sql2 = "SET SQL_SAFE_UPDATES=0";

        try {
            PreparedStatement pst = cn.prepareStatement(sql2);
            PreparedStatement pst2 = cn.prepareStatement(sql1);

            pst.executeUpdate();
            pst2.executeUpdate();

            pst.close();
            pst2.close();
            
            return true;
        } catch (SQLException e) {
            Logger.getLogger(vLogin.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public int ExistenciaUsuarios() {
        sql1 = "SELECT count(*) as cant_usuarios from Usuarios";

        try {
            int num = 0;
            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                num = rs.getInt("cant_usuarios");
            }
            return num;
        } catch (SQLException e) {
            Logger.getLogger(vLogin.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

    }
}