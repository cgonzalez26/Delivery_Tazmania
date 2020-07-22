package Controlador;

import Modelo.TiposUsuarios;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_TiposUsuarios {
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    
    public control_TiposUsuarios(){
       sql=new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
       String[]columnas={"idtipousuario","Descripcion"};
       Object[][]datos=sql.GetTabla(columnas, "tiposusuarios", "select idtipousuario,Descripcion from tiposusuarios where activo=1");
       return datos;
    }
    
    public Object[][]MostrarTipoUserBuscado(String tipouser){
       String[]columnas={"idtipousuario","Descripcion"};
       Object[][]datos=sql.GetTabla(columnas, "tiposusuarios", "select idtipousuario,Descripcion from tiposusuarios where Descripcion like '%" + tipouser + "%' and activo=1");
       return datos;
    }
    
    public boolean InsertarTiposUsuarios(TiposUsuarios ti){
        String datos[]={ti.getDescripcion()};
        return sql.insertar(datos, "insert into tiposusuarios (Descripcion, activo) values (?,1)");
    }
    
    public boolean EditarTiposUsuaris(TiposUsuarios ti){
        String id= (Integer.toString(ti.getIdtipousuario()));
        String datos[]={ti.getDescripcion(),id};
        return sql.editar(datos, "update tiposusuarios set Descripcion=? where idtipousuario=?");
    }
    
    public boolean EliminarTiposInsumos(TiposUsuarios ti){
        sql.baja_dedatos("tiposusuarios", "idtipousuario", ti.getIdtipousuario());
        return true;
    }
    
    public String TipoUsuarioInicioSesion(String user){
       return sql.FiltrarTipoUsuario("select t.descripcion from tiposusuarios t INNER JOIN usuarios u on u.idtipousuario=t.idtipousuario where u.Login='"+user+"'");
    }   
}
