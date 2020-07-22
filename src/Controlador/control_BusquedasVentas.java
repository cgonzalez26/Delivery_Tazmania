package Controlador;

import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_BusquedasVentas {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_BusquedasVentas() {
        String[] columnas = {"idventa", "idusuario", "Login", "NroFactura", "MontoTotal", "FechaVenta"};
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatosNroVenta(String desde, String hasta) {
        String[] columnas = {"idventa", "idusuario", "Login", "NroFactura", "MontoTotal", "FechaVenta","tipoVenta"};
        Object[][] datos = sql.GetTabla(columnas, "ventas", "select v.idventa,u.idusuario,u.Login,v.NroFactura,v.MontoTotal,date_format(v.FechaVenta,'%d/%m/%Y %H:%i') as FechaVenta,tipoVenta  from ventas v LEFT JOIN usuarios u on v.idusuario=u.idusuario where date(v.FechaVenta) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and v.activo=1");
        return datos;
    }

    public Object[][] MostrarDatosDetallesVentasNroVenta(String desde, String hasta) {
        String[] columnas = {"iddetalleventa", "idventa", "idproducto", "NroFactura", "descripcion", "Precio", "Cantidad","FechaVenta"};
        Object[][] datos = sql.GetTabla(columnas, "detallesventas", "select d.iddetalleventa,v.idventa,p.idproducto,v.NroFactura,p.descripcion,d.Precio,d.Cantidad,date_format(d.fechaVenta, '%d/%m/%Y %H:%i') as FechaVenta from detallesventas d LEFT JOIN ventas v on d.idventa=v.idventa LEFT JOIN productos p on d.idproducto=p.idproducto where date(d.fechaVenta) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and d.activo=1 order by v.NroFactura desc");
        return datos;
    }

}
