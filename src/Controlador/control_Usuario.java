package Controlador;

import Modelo.Usuarios;
import java.sql.Connection;


/**
 *
 * @author Colo-PC
 */
public class control_Usuario extends Usuarios {
    private Sentencias_sql sql; 
    private Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_Usuario() {
        sql= new Sentencias_sql();
    }
    
    
    public Object[][] MostrarDatos(){
        String[] columnas={"idusuario","idtipousuario","descripcion","Login","Password","Nombre","Apellido","Direccion","Mail","Telefono","Estado"};
        Object[][] datos=sql.GetTabla(columnas, "usuarios", "select u.idusuario,t.idtipousuario,t.descripcion,u.Login,u.Password,u.Nombre,u.Apellido,u.Direccion, u.Mail,u.Telefono,u.Estado from usuarios u INNER JOIN tiposusuarios t on u.idtipousuario=t.idtipousuario where u.activo=1");
        return datos;       
    }
    
    public Object[][] MostrarDatosBusqueda(String texto){
        String[] columnas={"idusuario","idtipousuario","descripcion","Login","Password","Nombre","Apellido","Direccion","Mail","Telefono","Estado"};
        Object[][] datos=sql.GetTabla(columnas, "usuarios", "select u.idusuario,t.idtipousuario,t.descripcion,u.Login,u.Password,u.Nombre,u.Apellido,u.Direccion, u.Mail,u.Telefono,u.Estado from usuarios u INNER JOIN tiposusuarios t on u.idtipousuario=t.idtipousuario where u.activo=1 and u.Login like '%"+texto+"%'");
        return datos;       
    }
    
    public boolean InsertarUsuarios(Usuarios user){
        String idtipodoc = Integer.toString(user.getIdtipousuario());
        String datos[]= {idtipodoc,user.getLogin(),user.getPassword(),user.getNombre(),user.getApellido(),user.getDireccion(),user.getEmail(),user.getTelefono(),user.getEstado()};
        return sql.insertar(datos, "insert into usuarios (idtipousuario,Login,Password,Nombre,Apellido,Direccion,Mail,Telefono,Estado,activo) values (?,?,?,?,?,?,?,?,?,1)");
    }
    
    public boolean EditarUsuarios(Usuarios user){
        String idusuario= (Integer.toString(user.getIdusuario())),idtipodoc = Integer.toString(user.getIdtipousuario());
        String datos[]= {idtipodoc,user.getLogin(),user.getPassword(),user.getNombre(),user.getApellido(),user.getDireccion(),user.getEmail(),user.getTelefono(),user.getEstado(),idusuario};
        return sql.insertar(datos,"update usuarios set idtipousuario=?,Login=?,Password=?,Nombre=?,Apellido=?,Direccion=?,Mail=?,Telefono=?,Estado=? where idusuario=?" );
    }
    
    public boolean EliminarUsuarios(Usuarios user){
        sql.baja_dedatos("usuarios", "idusuario", user.getIdusuario());
        return true;
    }
    
    public boolean InicioSesion(Usuarios user){
      return sql.InicioSesion(user.getLogin(), user.getPassword());
    }
    
    public int ObtenerIDTipoUsuario(String dato){
        return sql.ObtenerID("select idtipousuario from tiposusuarios where descripcion='"+dato+"'");
    }
    
    public int ObtenerIDUsuario(String dato){
        return sql.ObtenerID("select idusuario from usuarios where Login='"+dato+"'");
    }

    /*@Override
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

    //@Override
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

    //@Override
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

    //@Override
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

    }*/
}