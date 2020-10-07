package Controlador;

import Modelo.Compras;
import Vistas.vCompras_Insumos;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_Compras {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_Compras() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String desde, String hasta) {
        String[] columnas = {"idcompra", "idproveedor", "idusuario", "Nombre_comercial", "NroCompra", "Login", "MontoTotal", "FechaCompra"};
        Object[][] datos = sql.GetTabla(columnas, "compras", "select c.idcompra,p.idproveedor,u.idusuario,p.Nombre_comercial,c.NroCompra,u.Login,c.MontoTotal,date_format(c.FechaCompra,'%d/%m/%Y %H:%i') as FechaCompra from proveedores p INNER JOIN compras c on p.idproveedor=c.idproveedor INNER JOIN usuarios u on u.idusuario=c.idusuario where date(c.FechaCompra) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and c.activo=1 order by FechaCompra asc;");
        return datos;
    }

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

    public boolean EfectuarCompra(Compras compra) {
        String monto = (Float.toString(compra.getMontototal())), fecha = ((JTextField) vCompras_Insumos.jDateFecha.getDateEditor().getUiComponent()).getText(), idprov = Integer.toString(compra.getIdproveedor()), iduser = Integer.toString(compra.getIdusuario());
        String codigo = sql.generaCodigo("compra");
        String datos[] = {idprov, iduser, monto, codigo};
        return sql.insertar(datos, "insert into compras (idproveedor,idusuario,FechaCompra,MontoTotal,NroCompra,activo) values (?,?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),?,?,1)");
    }

    public boolean EditarCompra(Compras compra) {
        String monto = (Float.toString(compra.getMontototal())), id = (Integer.toString(compra.getIdcompra())), fecha = ((JTextField) vCompras_Insumos.jDateFecha.getDateEditor().getUiComponent()).getText(), idprov = Integer.toString(compra.getIdproveedor()), iduser = Integer.toString(compra.getIdusuario());
        String datos[] = {idprov, iduser, monto, id};
        return sql.editar(datos, "update compras set idproveedor=?,idusuario=?,FechaCompra=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),MontoTotal=? where idcompra=?");
    }

    public boolean ActualizarTotalCompra(Compras compra) {
        String total = (Float.toString(compra.getMontototal())), id = (Integer.toString(compra.getIdcompra()));
        String datos[] = {total, id};
        return sql.editar(datos, "update compras set MontoTotal=? where idcompra=?");
    }

    public boolean EliminarCompra(Compras compra) {
        return sql.baja_dedatos("compras", "idcompra", compra.getIdcompra());
    }

    public ArrayList<String> ObtenerDatosNumInsumos(String texto) {
        return sql.ObtenerDatosInsProd("precio","stock","","","select precio,stock from insumos where descripcion='" + texto + "' and activo=1");
    }

    public int ObtenerUltimoIDCompra() {
        return sql.ObtenerID("select max(idcompra) from compras");
    }

    public int ObtenerIDProveedor(String dato) {
        return sql.ObtenerID("select idproveedor from proveedores where Nombre_comercial='" + dato + "'");
    }

    public int ObtenerIDUsuario(String dato) {
        return sql.ObtenerID("select idusuario from usuarios where Login='" + dato + "'");
    }

    public int ObtenerIDMovCajaCompra(int id, String tipomovnro) {
        return sql.ObtenerID("select idmovimientocaja from movimientos_caja where idmovimiento=" + id + " and NroMovimiento like '%" + tipomovnro + "%'");
    }

    public String VerificarNroFactra(String dato) {
        return sql.VerificarDuplicadosNrosFacturas("NroCompra", "select NroCompra from compras where NroCompra='" + dato + "' where activo=1");
    }
}
