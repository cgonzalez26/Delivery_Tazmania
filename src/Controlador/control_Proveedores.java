package Controlador;

import Modelo.Proveedores;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_Proveedores extends Proveedores {

    private final Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_Proveedores() {
        sql = new Sentencias_sql();
    }

    //@Override
    public Object[][] MostrarDatos() {
        String[] columnas = {"idproveedor", "idtipodocumento", "Descripcion", "NroDocumento", "Nombre", "Apellido", "Nombre_comercial", "direccion", "Telefono"};
        Object[][] datos = sql.GetTabla(columnas, "proveedores", "select p.idproveedor,i.idtipodocumento,i.Descripcion,p.NroDocumento,p.Nombre,p.Apellido,p.Nombre_comercial, p.direccion,p.Telefono from proveedores p INNER JOIN tiposdocumentos i on p.idtipodocumento=i.idtipodocumento where p.activo=1");
        return datos;
    }

    public Object[][] MostrarDatosBusqueda(String nomcomercial) {
        String[] columnas = {"idproveedor", "idtipodocumento", "Descripcion", "NroDocumento", "Nombre", "Apellido", "Nombre_comercial", "direccion", "Telefono"};
        Object[][] datos = sql.GetTabla(columnas, "proveedores", "select p.idproveedor,i.idtipodocumento,i.Descripcion,p.NroDocumento,p.Nombre,p.Apellido,p.Nombre_comercial, p.direccion,p.Telefono from proveedores p LEFT JOIN tiposdocumentos i on p.idtipodocumento=i.idtipodocumento where p.activo=1 and p.Nombre_comercial like '%" + nomcomercial + "%'");
        return datos;
    }

    //@Override
    public boolean InsertarProveedores(Proveedores prov) {
        String idtipodoc = Integer.toString(prov.getIdTipoDocumento());
        String datos[] = {idtipodoc, prov.getNroDocumento(), prov.getNombre(), prov.getApellido(), prov.getNombre_comercial(), prov.getDireccion(), prov.getTelefono()};
        return sql.insertar(datos, "insert into proveedores(idtipodocumento,NroDocumento,Nombre, Apellido,Nombre_comercial,direccion,Telefono,activo) values(?,?,?,?,?,?,?,1)");
    }

    //@Override
    public boolean EditarProveedores(Proveedores prov) {
        String iddato = Integer.toString(prov.getIdProveedor()), idtipodoc = Integer.toString(prov.getIdTipoDocumento());
        String datos[] = {idtipodoc, prov.getNroDocumento(), prov.getNombre(), prov.getApellido(), prov.getNombre_comercial(), prov.getDireccion(), prov.getTelefono(), iddato};
        return sql.editar(datos, "UPDATE proveedores SET idtipodocumento=?,NroDocumento=?,Nombre=?,Apellido=?,Nombre_comercial=?,direccion=?,Telefono=? WHERE idproveedor=?");
    }

    //@Override
    public boolean EliminarProveedores(Proveedores prov) {
        //Integer idproveedor  = Integer.parseInt(sIdproveedor);        
        sql.baja_dedatos("proveedores", "idproveedor", prov.getIdProveedor());
        return true;
    }

    public int ObtenerIDTipoDoc(String dato) {
        return sql.ObtenerID("select idtipodocumento from tiposdocumentos where Descripcion='" + dato + "'");
    }

}
