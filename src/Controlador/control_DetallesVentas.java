package Controlador;

import Modelo.DetallesVentas;
import Vistas.vVentas_Productos;
import java.sql.Connection;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_DetallesVentas {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_DetallesVentas() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String desde, String hasta, String nrofactura) {
        String[] columnas = {"iddetalleventa", "idventa", "idproducto", "NroFactura", "descripcion", "Precio", "Cantidad", "fechaVenta"};
        Object[][] datos = sql.GetTabla(columnas, "detallesventas", "select d.iddetalleventa,v.idventa,p.idproducto,v.NroFactura,p.descripcion,d.Precio,d.Cantidad,date_format(d.fechaVenta, '%d/%m/%Y %H:%i') as FechaVenta from detallesventas d INNER JOIN ventas v on d.idventa=v.idventa INNER JOIN productos p on d.idproducto=p.idproducto where v.NroFactura='" + nrofactura + "' and date(d.fechaVenta) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and d.activo=1 order by v.NroFactura desc");
        return datos;
    }

    public Object[][] MostrarProductos() {
        String[] columnas = {"descripcion", "precioventa"};
        Object[][] datos = sql.GetTabla(columnas, "productos", "select descripcion,precioventa from productos where activo=1");
        return datos;
    }

    public Object[][] MostrarProductoBuscado(String prod) {
        String[] columnas = {"descripcion", "precioventa"};
        Object[][] datos = sql.GetTabla(columnas, "productos", "select descripcion,precioventa from productos where descripcion like '%" + prod + "%' and activo=1");
        return datos;
    }

    public Object[][] MostrarProductoStockN_MOD(String producto, float cantidad) {
        String[] columnas = {"descripcion", "stock", "cantFinal"};
        Object[][] datos = sql.GetTabla3(columnas, "turnos", "select i.descripcion,i.stock,i.stock - " + cantidad + " as cantFinal from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1");
        return datos;
    }

    public Object[][] MostrarProductosStockMOD(String producto, float numtabla, float numtexto) {
        String[] columnas = {"descripcion", "stock", "stock_modificando_venta", "stock_final_venta"};
        Object[][] datos = sql.GetTabla3(columnas, "turnos", "select i.descripcion,i.stock,i.stock + " + numtabla + " as stock_modificando_venta,i.stock + " + numtabla + " - " + numtexto + " as stock_final_venta from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1");
        return datos;
    }

    public boolean RegistrarDetalleVenta(DetallesVentas dv) {
        String idventa = Integer.toString(dv.getIdventa()), idproducto = Integer.toString(dv.getIdproducto()), Precio = Float.toString(dv.getPrecio()), Cantidad = Float.toString(dv.getCantidad()), fecha = ((JTextField) vVentas_Productos.jDateChooser1.getDateEditor().getUiComponent()).getText();
        String datos[] = {idventa, idproducto, Precio, Cantidad};
        return sql.insertar(datos, "insert into detallesventas (idventa,idproducto,Precio,Cantidad,activo,fechaVenta) values (?,?,?,?,1,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'))");
    }

    public boolean EditarDetalleVenta(DetallesVentas detalleventa) {
        String precio = Float.toString(detalleventa.getPrecio()), cantidad = Float.toString(detalleventa.getCantidad()), id = Integer.toString(detalleventa.getIddetalleventa()), idventa = Integer.toString(detalleventa.getIdventa()), idprod = Integer.toString(detalleventa.getIdproducto()), fecha = ((JTextField) vVentas_Productos.jDateChooser1.getDateEditor().getUiComponent()).getText();
        String datos[] = {idventa, idprod, precio, cantidad, id};
        return sql.editar(datos, "update detallesventas set idventa=?,idproducto=?,Precio=?,Cantidad=?,fechaVenta=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i') where iddetalleventa=?");
    }

    public boolean EliminarDetalleVenta(DetallesVentas detalleventa) {
        sql.baja_dedatos("detallesventas", "iddetalleventa", detalleventa.getIddetalleventa());
        return true;
    }

    public boolean AnularEliminacionVenta(DetallesVentas detalleventa) {
        sql.AnularEliminacion("detallesventas", "iddetalleventa", detalleventa.getIddetalleventa());
        return true;
    }

    public boolean ActualizarStockInsumos(DetallesVentas detalleventa) {
        float cantidad = detalleventa.getCantidad();
        int idprod = detalleventa.getIdproducto();
        Object[] datos = {cantidad, idprod};
        return sql.ActualizarStockVenta(datos, "update insumos i inner join recetas r on r.idinsumo=i.idinsumo set stock=stock - ? where r.idproducto=? and r.activo=1 and i.activo=1");
    }

    public boolean ActualizarStockInsumos2(DetallesVentas dv) {
        String iddetalleventa = Integer.toString(dv.getIddetalleventa()), idproducto = Integer.toString(dv.getIdproducto());
        String datos[] = {iddetalleventa, idproducto};
        return sql.editar(datos, "update insumos i inner join recetas r on r.idinsumo=i.idinsumo set i.stock=i.stock - (select Cantidad from detallesventas where iddetalleventa=?) where r.idproducto=? and r.activo=1 and i.activo=1");
    }

    public boolean ActualizarStockInsumos3(DetallesVentas dv) {
        String iddetalleventa1 = Integer.toString(dv.getIddetalleventa()), iddetalleventa2 = Integer.toString(dv.getIddetalleventa());
        String datos[] = {iddetalleventa1, iddetalleventa2};
        return sql.editar(datos, "update insumos i inner join recetas r on r.idinsumo=i.idinsumo set i.stock=i.stock - (select Cantidad from detallesventas where iddetalleventa=?) where r.idproducto=(select idproducto from detallesventas where iddetalleventa=?) and r.activo=1 and i.activo=1");
    }

    public boolean SumarCantidadRestadaInsumos(DetallesVentas dv) {
        String cantidad = Float.toString(dv.getCantidad()), idprod = Integer.toString(dv.getIdproducto());
        String datos[] = {cantidad, idprod};
        return sql.editar(datos, "update insumos i inner join recetas r on r.idinsumo=i.idinsumo set i.stock=i.stock + ? where r.idproducto=? and r.activo=1 and i.activo=1");
    }

    public boolean SumarCantidadRestadaInsumos2(DetallesVentas dv) {
        String cantidad = Float.toString(dv.getCantidad()), idproducto = Integer.toString(dv.getIdproducto());
        String datos[] = {cantidad, idproducto};
        return sql.editar(datos, "update insumos i inner join recetas r on r.idinsumo=i.idinsumo set i.stock=i.stock+? where r.idproducto=? and r.activo=1 and i.activo=1");
    }

    public boolean ActualizarDevolverPrecio(DetallesVentas dv) {
        String iddetalleventa = Integer.toString(dv.getIddetalleventa()), idproducto = Integer.toString(dv.getIdproducto());
        String datos[] = {iddetalleventa, idproducto};
        return sql.editar(datos, "update productos p inner join detallesventas d on d.idproducto=p.idproducto set p.precioventa=(select Precio from detallesventas where iddetalleventa=?) where p.idproducto=?");
    }

    public int ConsultarStockNegativosN_MOD(String producto, float cantidad) {
        return sql.ConsultarStockNegativosN_MOD(producto, cantidad);
    }

    public int ConsultarStockCeroN_MOD(String producto, float cantidad) {
        return sql.ConsultarStockCeroN_MOD(producto, cantidad);
    }

    public int ConsultarStockNegativosMOD(String producto, float numtabla, float numtexto) {
        return sql.ConsultarStockNegativosMOD(producto, numtabla, numtexto);
    }

    public int ConsultarStockCeroMOD(String producto, float numtabla, float numtexto) {
        return sql.ConsultarStockCeroMOD(producto, numtabla, numtexto);
    }

    public int ObtenerIDVenta() {
        return sql.ObtenerID("select max(idventa) from ventas limit 1");
    }

    public int ObtenerIDProducto(String dato) {
        return sql.ObtenerID("select idproducto from productos where descripcion='" + dato + "'");
    }

    public int ObtenerIDProducto2(int id) {
        return sql.ObtenerID("select idproducto from detallesventas where iddetalleventa='" + id + "'");
    }

    public String ObtenerDescripcionProd(int iddetalleventa) {
        return sql.datos_string("producto", "select p.descripcion as producto from detallesventas as d LEFT JOIN productos as p on d.idproducto=p.idproducto where d.idproducto= (select idproducto from detallesventas where iddetalleventa='" + iddetalleventa + "' and activo=1) limit 1");
    }

}
