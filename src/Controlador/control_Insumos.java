package Controlador;

import Modelo.Insumos;
import Vistas.vGestion_Insumos;
import java.sql.Connection;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_Insumos {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_Insumos() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos() {
        String[] columnas = {"idinsumo", "idtipoinsumo", "idproveedor", "t.descripcion", "Nombre_comercial", "i.descripcion", "precio", "stock", "fecharegistro"};
        Object[][] datos = sql.GetTabla(columnas, "insumos", "select i.idinsumo,t.idtipoinsumo,p.idproveedor,t.descripcion,p.Nombre_comercial,i.descripcion,i.precio,i.stock,date_format(i.fecharegistro,'%d/%m/%Y %H:%i') as fecharegistro from insumos i INNER JOIN tiposinsumos t on t.idtipoinsumo=i.idtipoinsumo INNER JOIN proveedores p on i.idproveedor=p.idproveedor where i.activo=1");
        return datos;
    }
    
    public Object[][] OrdenarInsumos(String tipo) {
        String[] columnas = {"idinsumo", "idtipoinsumo", "idproveedor", "t.descripcion", "Nombre_comercial", "i.descripcion", "precio", "stock", "fecharegistro"};
        Object[][] datos = sql.GetTabla(columnas, "insumos", "select i.idinsumo,t.idtipoinsumo,p.idproveedor,t.descripcion,p.Nombre_comercial,i.descripcion,i.precio,i.stock,date_format(i.fecharegistro,'%d/%m/%Y %H:%i:%s') as fecharegistro from insumos i INNER JOIN tiposinsumos t on t.idtipoinsumo=i.idtipoinsumo INNER JOIN proveedores p on i.idproveedor=p.idproveedor where i.activo=1 order by t.descripcion='" + tipo + "' desc");
        return datos;
    }
    
    public Object[][] MostrarDatosBusquedaInsumos(String texto) {
        String[] columnas = {"idinsumo", "idtipoinsumo", "idproveedor", "t.descripcion", "Nombre_comercial", "i.descripcion", "precio", "stock", "fecharegistro"};
        Object[][] datos = sql.GetTabla(columnas, "insumos", "select i.idinsumo,t.idtipoinsumo,p.idproveedor,t.descripcion,p.Nombre_comercial,i.descripcion,i.precio,i.stock,date_format(i.fecharegistro,'%d/%m/%Y %H:%i') as fecharegistro from insumos i INNER JOIN tiposinsumos t on t.idtipoinsumo=i.idtipoinsumo INNER JOIN proveedores p on i.idproveedor=p.idproveedor where i.activo=1 and i.descripcion like '%"+texto+"%'");
        return datos;
    }
    
    /*public Object[][] MostrarDatosBusquedaInsumosTipoInsumos(String texto,String tipo) {
        String[] columnas = {"idinsumo", "idtipoinsumo", "idproveedor", "t.descripcion", "Nombre_comercial", "i.descripcion", "precio", "stock", "fecharegistro"};
        Object[][] datos = sql.GetTabla(columnas, "insumos", "select i.idinsumo,t.idtipoinsumo,p.idproveedor,t.descripcion,p.Nombre_comercial,i.descripcion,i.precio,i.stock,date_format(i.fecharegistro,'%d/%m/%Y %h:%i') as fecharegistro from insumos i INNER JOIN tiposinsumos t on t.idtipoinsumo=i.idtipoinsumo INNER JOIN proveedores p on i.idproveedor=p.idproveedor where i.activo=1 and i.descripcion like '%"+texto+"%' order by t.descripcion='" + tipo + "' desc");
        return datos;
    }*/

    public Object[][] MostrarProveedores() {
        String[] columnas = {"Nombre_comercial"};
        Object[][] datos = sql.GetTabla(columnas, "proveedores", "select Nombre_comercial from proveedores where activo=1");
        return datos;
    }
    
    public Object[][] MostrarProveedorBuscado(String prov) {
        String[] columnas = {"Nombre_comercial"};
        Object[][] datos = sql.GetTabla(columnas, "proveedores", "select Nombre_comercial from proveedores where Nombre_comercial like '%" + prov + "%' and activo=1");
        return datos;
    }

    public boolean InsertarInsumos(Insumos insumos) {
        String idtipoinsumo = Integer.toString(insumos.getIdtipoinsumo()), idprov = Integer.toString(insumos.getIdproveedor()), fecha = ((JTextField) vGestion_Insumos.jDateChooser1.getDateEditor().getUiComponent()).getText();
        String precio = (Float.toString(insumos.getPrecio())), stock = (Float.toString(insumos.getStock()));
        String datos[] = {idtipoinsumo, idprov, insumos.getDescripcion(), precio, stock};
        return sql.insertar(datos, "insert into insumos (idtipoinsumo,idproveedor,descripcion,precio,stock,fecharegistro,activo) values (?,?,?,?,?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),1)");
    }

    public boolean EditarInsumos(Insumos insumos) {
        String idtipoinsumo = Integer.toString(insumos.getIdtipoinsumo()), idprov = Integer.toString(insumos.getIdproveedor()), fecha = ((JTextField) vGestion_Insumos.jDateChooser1.getDateEditor().getUiComponent()).getText();
        String precio = (Float.toString(insumos.getPrecio())), stock = (Float.toString(insumos.getStock())), id = (Integer.toString(insumos.getIdinsumo()));
        String datos[] = {idtipoinsumo, idprov, insumos.getDescripcion(), precio, stock, id};
        return sql.editar(datos, "update insumos set idtipoinsumo=?,idproveedor=?,descripcion=?,precio=?,stock=?,fecharegistro=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s') where idinsumo=?");
    }

    public boolean EliminarInsumos(Insumos insumos) {
        sql.baja_dedatos("insumos", "idinsumo", insumos.getIdinsumo());
        return true;
    }

    public int ObtenerIDTipoInsumo(String dato) {
        return sql.ObtenerID("select idtipoinsumo from tiposinsumos where descripcion='" + dato + "'");
    }

    public int ObtenerIDProveedor(String dato) {
        return sql.ObtenerID("select idproveedor from proveedores where Nombre_comercial='" + dato + "'");
    }
    
    public boolean ActualizarPrecio(Insumos insumos){
       String idinsumo = Integer.toString(insumos.getIdinsumo()), precio = (Float.toString(insumos.getPrecio()));
       String datos[]={precio,idinsumo};
       return sql.editar(datos, "UPDATE insumos SET precio=? where idinsumo=?");
    }
}
