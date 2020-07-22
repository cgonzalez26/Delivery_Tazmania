package Controlador;

import Modelo.PermisosPantallaPerfiles;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Colo-PC
 */
public class control_PermisosPantallasPerfiles {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_PermisosPantallasPerfiles() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String tipousuario) {
        String[] columnas = {"l.idpermisopantallaperfil", "t.idtipousuario", "p.idpantalla","t.descripcion","p.nombre"};
        Object[][] datos = sql.GetTabla(columnas, "permisospantallasperfiles", "select l.idpermisopantallaperfil,t.idtipousuario,p.idpantalla,t.descripcion,p.nombre from permisospantallasperfiles l INNER JOIN tiposusuarios t on l.idtipousuario=t.idtipousuario INNER JOIN pantallas p on l.idpantalla=p.idpantalla where l.activo=1 and t.descripcion='"+tipousuario+"'");
        return datos;
    }
    
    public Object[][] ActualizacionEliminar(){
        String[] columnas = {"l.idpermisopantallaperfil", "t.idtipousuario", "p.idpantalla","t.descripcion","p.nombre"};
        Object[][] datos = sql.GetTabla(columnas, "permisospantallasperfiles", "select l.idpermisopantallaperfil,t.idtipousuario,p.idpantalla,t.descripcion,p.nombre from permisospantallasperfiles l INNER JOIN tiposusuarios t on l.idtipousuario=t.idtipousuario INNER JOIN pantallas p on l.idpantalla=p.idpantalla where l.activo=1");
        return datos;
    }

    public boolean InsertarPermisos(PermisosPantallaPerfiles permisos) {
        String idpantalla = Integer.toString(permisos.getIdpantalla()), idtipouser = Integer.toString(permisos.getIdtipousuario());
        String datos[] = {idtipouser, idpantalla};
        return sql.insertar(datos, "insert into permisospantallasperfiles (idtipousuario,idpantalla,activo) values (?,?,1)");
    }

    public boolean ModificarPermisos(PermisosPantallaPerfiles permisos) {
        String idpermiso = Integer.toString(permisos.getIdpermisopantallaperfil()), idpantalla = Integer.toString(permisos.getIdpantalla()), idtipouser = Integer.toString(permisos.getIdtipousuario());
        String datos[] = {idtipouser, idpantalla, idpermiso};
        return sql.insertar(datos, "update permisospantallasperfiles set idtipousuario=?,idpantalla=? where idpermisopantallaperfil=?");
    }

    public boolean EliminarPermisos1(PermisosPantallaPerfiles permisos) {
        sql.baja_dedatos("permisospantallasperfiles", "idpermisopantallaperfil", permisos.getIdpermisopantallaperfil());
        return true;
    }

    public int ObtenerIDTipoUsuario(String dato) {
        return sql.ObtenerID("select idtipousuario from tiposusuarios where descripcion='" + dato + "'");
    }

    public int ObtenerIDPantalla(String dato) {
        return sql.ObtenerID("select idpantalla from pantallas where nombre='" + dato + "'");
    }
    
    public ArrayList<String> NombresPantallasHabilitadasPorTipoUsuario(String tipouser){
        return sql.NombresPantallasHabilitadasTiposUsuarios("select p.nombre from pantallas p INNER JOIN permisospantallasperfiles l on l.idpantalla=p.idpantalla INNER JOIN tiposusuarios t on l.idtipousuario=t.idtipousuario where t.descripcion='"+tipouser+"'");
    }
    
    public boolean EliminarPermisos2(PermisosPantallaPerfiles permisos){
        sql.Eliminar("permisospantallasperfiles", "idtipousuario", permisos.getIdtipousuario());
        return true;
    }
    
}
