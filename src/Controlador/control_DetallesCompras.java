package Controlador;

import Modelo.DetallesCompras;
import Vistas.vCompras_Insumos;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_DetallesCompras {

    Sentencias_sql sql;
    public String descripcion;
    //public int cantidad;
    //public float precio;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_DetallesCompras() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String desde, String hasta, String nrocompra) {
        String[] columnas = {"iddetallecompra", "idcompra", "idinsumo", "NroCompra", "descripcion", "Precio", "Cantidad", "FechaCompra"};
        Object[][] datos = sql.GetTabla(columnas, "detallescompras", "select d.iddetallecompra,c.idcompra,i.idinsumo,c.NroCompra,i.descripcion,d.Precio,d.Cantidad,date_format(d.fechaCompra,'%d-%m-%Y %H:%i') as FechaCompra from compras c INNER JOIN detallescompras d on c.idcompra=d.idcompra INNER JOIN insumos i on i.idinsumo=d.idinsumo where c.NroCompra='" + nrocompra + "' and date(d.fechaCompra) between str_to_date((str_to_date('" + desde + "','%d-%m-%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d-%m-%Y')),'%Y-%m-%d') and d.activo=1 order by c.NroCompra desc");
        return datos;
    }

    public Object[][] ObtenerDatosDetalleComprasDesdeListaCompras(String nrocompra) {
        Object[][] datos = sql.DatosDetallesComprasVentasParaEliminar("select d.idinsumo as idinsumo, d.Cantidad as cantidad from detallescompras as d INNER JOIN compras as c on c.idcompra = d.idcompra where c.NroCompra='" + nrocompra + "' and d.activo=1");
        return datos;
    }
    
    public Object[][] DatosDetalleDesdeListaComprasModificar(String nrocompra){
        Object[][] datos = sql.DatosDetallesComprasVentasParaModificar("select i.descripcion,d.Precio,d.Cantidad, d.Precio*d.Cantidad as subtotal from compras c INNER JOIN detallescompras d on c.idcompra=d.idcompra INNER JOIN insumos i on i.idinsumo=d.idinsumo where d.activo=1 and c.NroCompra='" + nrocompra + "'");
        return datos;
    }
    /*public Object[][] DatosDetallesVentas(String desde, String hasta){
        Object[][] datos = sql.DatosDetallesComprasVentas("select d.iddetallecompra as iddetalle,i.descripcion as descripcion,d.Precio as precio,d.Cantidad as cantidad from compras c INNER JOIN detallescompras d on c.idcompra=d.idcompra INNER JOIN insumos i on i.idinsumo=d.idinsumo where date(d.fechaCompra) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and d.activo=1");
        return datos;
    }*/
    public Object[][] MostrarInsumos() {
        String[] columnas = {"descripcion", "precio", "stock"};
        Object[][] datos = sql.GetTabla(columnas, "insumos", "select descripcion,precio,stock from insumos where activo=1");
        return datos;
    }

    public Object[][] MostrarInsumosBuscado(String insumo) {
        String[] columnas = {"descripcion", "precio", "stock"};
        Object[][] datos = sql.GetTabla(columnas, "insumos", "select descripcion,precio,stock from insumos where descripcion like '%" + insumo + "%' and activo=1");
        return datos;
    }

    public Object[][] MostrarProveedores() {
        String[] columnas = {"Nombre_comercial"};
        Object[][] datos = sql.GetTabla(columnas, "proveedores", "select Nombre_comercial from proveedores where activo=1");
        return datos;
    }

    public Object[][] MostrarUsuarios() {
        String[] columnas = {"Login"};
        Object[][] datos = sql.GetTabla(columnas, "usuarios", "select Login from usuarios where activo=1");
        return datos;
    }

    public boolean RegistrarDetalleCompra(DetallesCompras dc) {
        String idcompra = Integer.toString(dc.getIdcompra()), idinsumo = Integer.toString(dc.getIdinsumo()), precio = Float.toString(dc.getPrecio()), cantidad = Float.toString(dc.getCantidad()), fecha = ((JTextField) vCompras_Insumos.jDateFecha.getDateEditor().getUiComponent()).getText();
        String datos[] = {idcompra, idinsumo, precio, cantidad};
        //ActualizarStockInsumo(dc);
        return sql.insertar(datos, "insert into detallescompras (idcompra,idinsumo,Precio,Cantidad,activo,fechaCompra) values (?,?,?,?,1,STR_TO_DATE('" + fecha + "','%d-%m-%Y %H:%i'))");
    }

    public boolean EditarDetalleCompraLotes(DetallesCompras dc) {
        String idcompra = Integer.toString(dc.getIdcompra()), idinsumo = Integer.toString(dc.getIdinsumo()), precio = Float.toString(dc.getPrecio()), cantidad = Float.toString(dc.getCantidad()), iddetalle = Integer.toString(dc.getIddetallecompra()), fecha = ((JTextField) vCompras_Insumos.jDateFecha.getDateEditor().getUiComponent()).getText();
        String datos[] = {idcompra, idinsumo, precio, cantidad, iddetalle};
        return sql.editar(datos, "update detallescompras set idcompra=?,idinsumo=?,Precio=?,Cantidad=?, fechaCompra=STR_TO_DATE('" + fecha + "','%d-%m-%Y %H:%i') where iddetallecompra=?");
    }

    public boolean EliminarDetalleCompra(DetallesCompras dc) {
        return sql.baja_dedatos("detallescompras", "iddetallecompra", dc.getIddetallecompra());
    }

    public boolean EliminarDetalleDesdeListaCompra(DetallesCompras dc) {
        return sql.baja_dedatos("detallescompras", "idcompra", dc.getIdcompra());
    }

    public boolean AnularEliminacionCompra(DetallesCompras dc) {
        sql.AnularEliminacion("detallescompras", "iddetallecompra", dc.getIddetallecompra());
        return true;
    }

    public boolean ActualizarStockInsumo(DetallesCompras dc) {
        String datos[] = {Float.toString(dc.getCantidad()), Integer.toString(dc.getIdinsumo())};
        return sql.editar(datos,"update insumos set stock=stock+? where i.idinsumo=?");
    }
    
    public boolean ActualizarStockInsumos() {
        return sql.ActualizarStockInsumos("update insumos i INNER JOIN detallescompras d on d.idinsumo=i.idinsumo set stock=stock+? where i.idinsumo=?");
    }

    public boolean ActualizarStockInsumos2(DetallesCompras dc) {
        String iddetalle = Integer.toString(dc.getIddetallecompra()), idinsumo = Integer.toString(dc.getIdinsumo());
        String datos[] = {iddetalle, idinsumo};
        return sql.editar(datos, "update insumos i INNER JOIN detallescompras d on d.idinsumo=i.idinsumo set i.stock=i.stock+(select Cantidad from detallescompras where iddetallecompra=?) where i.idinsumo=?");
    }

    public boolean SumarCantidadRestadoInsumos(DetallesCompras dc) {
        String iddetalle = Integer.toString(dc.getIddetallecompra()), idinsumo = Integer.toString(dc.getIdinsumo());
        String datos[] = {iddetalle, idinsumo};
        return sql.editar(datos, "update insumos i INNER JOIN detallescompras d on d.idinsumo=i.idinsumo set i.stock=i.stock-(select Cantidad from detallescompras where iddetallecompra=?) where i.idinsumo=?");
    }

    public boolean RestarCantidadSumadaInsumos(DetallesCompras dc) {
        String cantidad = Float.toString(dc.getCantidad()), idinsumo = Integer.toString(dc.getIdinsumo());
        String datos[] = {cantidad, idinsumo};
        return sql.editar(datos, "update insumos i INNER JOIN detallescompras d on d.idinsumo=i.idinsumo set i.stock=i.stock-? where i.idinsumo=?");
    }

    public boolean SetearCeroStock(DetallesCompras dc) {
        String idinsumo = Integer.toString(dc.getIdinsumo());
        String datos[] = {idinsumo};
        return sql.editar(datos, "update insumos i INNER JOIN detallescompras d on d.idinsumo=i.idinsumo set i.stock=0 where i.idinsumo=?");
    }

    public boolean ActualizarDevolverPrecio(DetallesCompras dc) {
        String idinsumo = Integer.toString(dc.getIdinsumo()), iddetalle = Integer.toString(dc.getIddetallecompra());
        String datos[] = {iddetalle, idinsumo};
        return sql.editar(datos, "update insumos i INNER JOIN detallescompras d on d.idinsumo=i.idinsumo set i.precio=(select Precio from detallescompras where iddetallecompra=?) where i.idinsumo=?");
    }

    public boolean VerificarStock(float cantidad, int idinsumo) {
        return sql.ConsultarStockInsumos("select i.stock - '" + cantidad + "' as stock from insumos as i INNER JOIN detallescompras as d on d.idinsumo=i.idinsumo where d.idinsumo='" + idinsumo + "' and i.activo=1 limit 1");
    }
    
    public ArrayList<String> ObtenerIDDetalleDesdeListaCompra(String nrocompra){
        return sql.DatosID("select d.iddetallecompra from detallescompras as d INNER JOIN compras as c on c.idcompra=d.idcompra where c.NroCompra='" + nrocompra + "' and d.activo=1");
    }
    
    public int CantidadTotalIDDetalles(String nrocompra){
        return sql.CantidadID("select d.iddetallecompra from detallescompras as d INNER JOIN compras as c on c.idcompra=d.idcompra where c.NroCompra='" + nrocompra + "' and d.activo=1");
    }

    public int ObtenerIDCompra() {
        return sql.ObtenerID("select max(idcompra) from compras limit 1");
    }

    public int ObtenerIDInsumo(String dato) {
        return sql.ObtenerID("select idinsumo from insumos where descripcion='" + dato + "'");
    }

    public int ObtenerIDInsumo2(int id) {
        return sql.ObtenerID("select idinsumo from detallescompras where iddetallecompra='" + id + "'");
    }

}
