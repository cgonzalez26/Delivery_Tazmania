package Controlador;

import Modelo.Ventas;
import Vistas.vVentas_Productos;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_Ventas {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_Ventas() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String desde, String hasta) {
        String[] columnas = {"idventa", "idusuario", "Login", "NroFactura", "MontoTotal", "FechaVenta", "tipoVenta"};
        Object[][] datos = sql.GetTabla(columnas, "ventas", "select v.idventa,u.idusuario,u.Login,v.NroFactura,v.MontoTotal,date_format(v.FechaVenta,'%d-%m-%Y %H:%i') as FechaVenta, tipoVenta from ventas v INNER JOIN usuarios u on v.idusuario=u.idusuario where date(v.FechaVenta) between str_to_date((str_to_date('" + desde + "','%d-%m-%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d-%m-%Y')),'%Y-%m-%d') and v.activo=1");
        return datos;
    }

    public Object[][] MostrarClientes() {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNIClienteTelefono(String dni, String nombre, String telefono) {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1 and dni like '%" + dni + "%' and nombre like '%" + nombre + "%' and telefono like '%" + telefono + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaCliente(String nombre) {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1 and nombre like '%" + nombre + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNI(String dni) {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1 and dni like '%" + dni + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaTelefono(String telefono) {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1 and telefono like '%" + telefono + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNICliente(String dni, String nombre) {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1 and dni like '%" + dni + "%' and nombre like '%" + nombre + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNITelefono(String dni, String telefono) {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1 and dni like '%" + dni + "%' and telefono like '%" + telefono + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaClienteTelefono(String nombre, String telefono) {
        String[] columnas = {"dni", "nombre", "apellido", "domicilio", "telefono"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select dni,nombre,apellido,domicilio,telefono from clientes where activo=1 and nombre like '%" + nombre + "%' and telefono like '%" + telefono + "%'");
        return datos;
    }

    public boolean CrearNroPedido(String vacio) {
        String datos[] = {vacio};
        return sql.insertar(datos, "insert into numerospedidosfactura (nropedido) values (?)");
    }

    public boolean EfectuarVenta(Ventas venta) {
        String montototal = (Float.toString(venta.getMontototal())), fecha = ((JTextField) vVentas_Productos.jDateFecha.getDateEditor().getUiComponent()).getText(), iduser = Integer.toString(venta.getIdusuario());
        String datos[] = {iduser, montototal, venta.getTipoVenta()};
        return sql.insertar(datos, "insert into ventas (idusuario,FechaVenta,MontoTotal,activo,tipoVenta) values (?,STR_TO_DATE('" + fecha + "','%d-%m-%Y %H:%i'),?,1,?)");
    }

    public boolean EditarVenta(Ventas venta) {
        String id = (Integer.toString(venta.getIdventa())), montototal = (Float.toString(venta.getMontototal())), fecha = ((JTextField) vVentas_Productos.jDateFecha.getDateEditor().getUiComponent()).getText(), iduser = Integer.toString(venta.getIdusuario());
        String datos[] = {iduser, montototal, venta.getTipoVenta(), id};
        return sql.editar(datos, "update ventas set idusuario=?,FechaVenta=STR_TO_DATE('" + fecha + "','%d-%m-%Y %H:%i'), MontoTotal=?, tipoVenta=? where idventa=?");
    }

    public boolean ActualizarTotalVenta(Ventas venta) {
        String total = Float.toString(venta.getMontototal()), id = Integer.toString(venta.getIdventa());
        String dato[] = {total, id};
        return sql.editar(dato, "update ventas set MontoTotal=? where idventa=?");
    }

    public boolean EliminarVenta(Ventas venta) {
        sql.baja_dedatos("ventas", "idventa", venta.getIdventa());
        return true;
    }

    public int ObtenerIDUsuario(String dato) {
        return sql.ObtenerID("select idusuario from usuarios where Login='" + dato + "'");
    }

    public int ObtenerIDMovVenta(int id, String tipomovnro) {
        return sql.ObtenerID("select idmovimientocaja from movimientos_caja where idmovimiento=" + id + " and NroMovimiento like '%" + tipomovnro + "%'");
    }

    public String VerificarNroFactura(String dato) {
        return sql.VerificarDuplicadosNrosFacturas("NroFactura", "select NroFactura from ventas where NroFactura='" + dato + "' where activo=1");
    }
    
    public ArrayList<String> ObtenerDatosProd(String texto){
        return sql.ObtenerDatosInsProd("precioventa","","select precioventa from productos where descripcion='" + texto + "' and activo=1");
    }
    
    public ArrayList<String> ObtenerDatosCliente(String texto){
        return sql.ObtenerDatosInsProd("domicilio", "telefono", "select domicilio,telefono from clientes where nombre= '" + texto + "' and activo=1");
    }
}
