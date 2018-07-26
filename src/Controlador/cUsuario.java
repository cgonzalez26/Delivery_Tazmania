/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modulos.Conexion;
import Modulos.mPersona;
import Modulos.mUsuario;
import Vistas.vLogin;
/*import static Vistas.vLogin.jTextoNomUser_Login;
import static Vistas.vLogin.jTextoPass_Login;*/
import Vistas.vGestion_Usuarios;
import static Vistas.vGestion_Usuarios.jComboAcceso_Usuario;
import static Vistas.vGestion_Usuarios.jComboEstado_Usuario;
import static Vistas.vGestion_Usuarios.jTextApellido_Usuario;
import static Vistas.vGestion_Usuarios.jTextCorreo_Usuario;
import static Vistas.vGestion_Usuarios.jTextDomicilio_Usuario;
import static Vistas.vGestion_Usuarios.jTextNomUser_Usuario;
import static Vistas.vGestion_Usuarios.jTextNombre_Usuario;
import static Vistas.vGestion_Usuarios.jTextPass_Usuario;
import static Vistas.vGestion_Usuarios.jTextTelefono_Usuario;
import Vistas.vMenu;
import com.sun.glass.ui.Cursor;
import java.awt.List;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public class cUsuario {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    private String sql1 = "";
    private String sql2 = "";

    public cUsuario() {

    }
    
    //Cada consulta o query lo hice creando una bd temporal para su correcto funcionamiento.
    
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

    public boolean InsertarUsuarios(mUsuario modulo) {
        
        int combo = ExistenciaUsuarios();

        sql1 = "Insert into Personas (NOMBRE_PERSONA, APELLIDO_PERSONA, DOMICILIO_PERSONA, TELCONTACTO_PERSONA, EMAILCONTACTO_PERSONA)"
                + "VALUES (?,?,?,?,?)";
        
            sql2 = "Insert into Usuarios (NOMBRE_USUARIO, CONTRASEÑA_USUARIO, TIPO_USUARIO, ESTADO_USUARIO, ID_PERSONA)"
                + "VALUES (?,?,?,?,(select ID_PERSONA from Personas ORDER BY ID_PERSONA DESC LIMIT 1))";
        
        

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);

            PreparedStatement pst2 = cn.prepareStatement(sql2);

            pst1.setString(1, modulo.getNombre_persona());
            pst1.setString(2, modulo.getApellido_persona());
            pst1.setString(3, modulo.getDomicilio_persona());
            pst1.setString(4, modulo.getTelefonoContacto_persona());
            pst1.setString(5, modulo.getEmailContacto_persona());

            int ej1 = pst1.executeUpdate();

            pst2.setString(1, modulo.getNombre_usuario());
            pst2.setString(2, modulo.getContraseña_usuario());
            pst2.setString(3, modulo.getTipo_usario());
            pst2.setBoolean(4, modulo.isEstado_usuario());

            int ej2 = pst2.executeUpdate();

            if (ej1 != 0 || ej2 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    mUsuario datos = new mUsuario();

    public boolean EditarUsuarios(mUsuario modulo) {
        sql1 = " UPDATE PERSONAS SET NOMBRE_PERSONA = ?, APELLIDO_PERSONA= ?, DOMICILIO_PERSONA= ?, TELCONTACTO_PERSONA= ?, EMAILCONTACTO_PERSONA= ? "
                + " WHERE ID_PERSONA=? ";

        sql2 = " UPDATE USUARIOS SET NOMBRE_USUARIO= ?, CONTRASEÑA_USUARIO= ?, TIPO_USUARIO= ?, ESTADO_USUARIO= ? "
                + " WHERE ID_USUARIO= ? ";

        try {
            PreparedStatement pst1 = cn.prepareStatement(sql1);
            PreparedStatement pst2 = cn.prepareStatement(sql2);

            pst1.setString(1, modulo.getNombre_persona());
            pst1.setString(2, modulo.getApellido_persona());
            pst1.setString(3, modulo.getDomicilio_persona());
            pst1.setString(4, modulo.getTelefonoContacto_persona());
            pst1.setString(5, modulo.getEmailContacto_persona());
            pst1.setInt(6, modulo.getId_persona());

            pst2.setString(1, modulo.getNombre_usuario());
            pst2.setString(2, modulo.getContraseña_usuario());
            pst2.setString(3, modulo.getTipo_usario());
            pst2.setBoolean(4, modulo.isEstado_usuario());
            pst2.setInt(5, modulo.getId_usuario());

            int N1 = pst1.executeUpdate();
            int N2 = pst2.executeUpdate();

            if (N1 != 0 || N1 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean EliminarUsuarios(mUsuario modulo) {

        try {
            /*sql1=" DELETE u.* FROM PERSONAS p INNER JOIN USUARIOS u ON u.id_persona=p.id_persona WHERE (u.id_persona=p.id_persona) ";*/

            PreparedStatement pst1 = cn.prepareStatement(" DELETE from Personas where id_persona= ? ");

            pst1.setInt(1, modulo.getId_persona());

            int N1 = pst1.executeUpdate();

            if (N1 != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

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

    public boolean VerificarInicioSesion() {

        String cadena1 = vLogin.jTextNomUser_Login.getText().trim();
        String cadena2 = vLogin.jTextoPass_Login.getText().trim();

        String sql1 = "SELECT nombre_usuario, contraseña_usuario, tipo_usuario FROM USUARIOS WHERE nombre_usuario='"
                + cadena1 + "' and contraseña_usuario='" + cadena2 + "'"; //Ejemplo de una consulta de tabla que tenga usuarios.
        String sql2 = "SELECT estado_usuario from Usuarios where nombre_usuario='" + cadena1 + "' and contraseña_usuario='" + cadena2 + "'";

        String sql3 = "SET SQL_SAFE_UPDATES=0";

        String sql4 = "update Usuarios set estado_usuario=true where nombre_usuario IN (select nombre_usuario from (select * from  Usuarios where nombre_usuario='" + cadena1 + "' and contraseña_usuario='" + cadena2 + "') as todo)";

        mUsuario modulo = new mUsuario();

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

                vMenu menu = new vMenu();
                menu.setVisible(true);
                menu.setSize(794, 566);
                menu.setResizable(false);
                menu.setLocationRelativeTo(null);

                vMenu.jEtiqUserCndo_Menu.setText(vLogin.jTextNomUser_Login.getText());
                vMenu.jIconUser_Menu.setVisible(true);
                vMenu.jTitUser_Menu.setVisible(true);
                return true;

            } else if (cadena1.equals(user) && cadena2.equals(pass) && estado1 == false && tipo.equals("Usuario Común")) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + vLogin.jTextNomUser_Login.getText());
                vMenu menu = new vMenu();
                menu.setVisible(true);
                menu.setSize(794, 566);
                menu.setResizable(false);
                menu.setLocationRelativeTo(null);

                vMenu.jEtiqUserCndo_Menu.setText(vLogin.jTextNomUser_Login.getText());
                vMenu.jIconUser_Menu.setVisible(false);
                vMenu.jTitUser_Menu.setVisible(false);
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

    public void cerrarEstado() {

        String cadena = vMenu.jEtiqUserCndo_Menu.getText();
        String sql1 = "update Usuarios set estado_usuario=false where nombre_usuario IN (select nombre_usuario from (select * from  Usuarios where nombre_usuario='" + cadena + "') as todo)";

        try {
            PreparedStatement pst = cn.prepareStatement(sql1);

            pst.executeUpdate();

            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(vLogin.class.getName()).log(Level.SEVERE, null, e);
        }
    }

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
