package Controlador;

import Modelo.ConsumosEmpleados;
import Vistas.vConsumosEmpleados;
import java.sql.Connection;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_ConsumosEmpleados {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_ConsumosEmpleados() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String desde, String hasta) {
        String columnas[] = {"idconsumo", "idproducto", "nombreEmp", "producto", "cantidad", "fecha"};
        Object[][] datos = sql.GetTabla(columnas, "consumosempleados", "select idconsumo,idproducto,nombreEmp,producto,cantidad,date_format(fecha, '%d-%m-%Y %H:%i') as fecha from consumosempleados where date(fecha) between str_to_date((str_to_date('" + desde + "','%d-%m-%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d-%m-%Y')),'%Y-%m-%d') and activo=1");
        return datos;
    }

    public Object[][] MostrarDatosBusqueda(String desde, String hasta) {
        String columnas[] = {"idconsumo", "idproducto", "nombreEmp", "producto", "cantidad", "fecha"};
        Object[][] datos = sql.GetTabla(columnas, "consumosempleados", "select idconsumo,idproducto,nombreEmp,producto,cantidad,date_format(fecha, '%d-%m-%Y %H:%i') as fecha from consumosempleados where date(fecha) between str_to_date((str_to_date('" + desde + "','%d-%m-%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d-%m-%Y')),'%Y-%m-%d') and activo=1");
        return datos;
    }

    public Object[][] MostrarEmpleados() {
        String[] columnas = {"Nombre", "descripcion"};
        Object[][] datos = sql.GetTabla(columnas, "empleados", "select e.Nombre, t.descripcion from empleados e LEFT JOIN tiposempleados t on e.idtipoempleado=t.idtipoempleado where e.activo=1");
        return datos;
    }

    public Object[][] MostrarEmpleadosBuscado(String empleado) {
        String[] columnas = {"Nombre", "descripcion"};
        Object[][] datos = sql.GetTabla(columnas, "empleados", "select e.Nombre, t.descripcion from empleados e LEFT JOIN tiposempleados t on e.idtipoempleado=t.idtipoempleado where e.Nombre like '%" + empleado + "%' and  e.activo=1");
        return datos;
    }

    public Object[][] MostrarProductos() {
        String[] columnas = {"idproducto", "descripcion"};
        Object[][] datos = sql.GetTabla(columnas, "productos", "select idproducto,descripcion from productos where activo=1");
        return datos;
    }

    public Object[][] MostrarProductosBuscado(String producto) {
        String[] columnas = {"idproducto", "descripcion"};
        Object[][] datos = sql.GetTabla(columnas, "productos", "select idproducto,descripcion from productos where descripcion like '%" + producto + "%' and activo=1");
        return datos;
    }
    
    public Object[][] MostrarProductoStockN_MOD(String producto, float cantidad){
        String[] columnas = {"descripcion","stock","cantFinal"};
        Object[][] datos = sql.GetTabla3(columnas, "turnos", "select i.descripcion,i.stock,i.stock - " + cantidad + " as cantFinal from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1");
        return datos;
    }
    
    public Object[][] MostrarProductosStockMOD(String producto, float numtabla, float numtexto){
        String[] columnas = {"descripcion","stock","stock_modificando_venta","stock_final_venta"};
        Object[][] datos = sql.GetTabla3(columnas, "turnos", "select i.descripcion,i.stock,i.stock + " + numtabla + " as stock_modificando_venta,i.stock + " + numtabla + " - " + numtexto + " as stock_final_venta from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1");
        return datos;
    }

    public boolean InsertarConsumosEmpleados(ConsumosEmpleados consemp) {
        String fecha = ((JTextField) vConsumosEmpleados.jDateFecha.getDateEditor().getUiComponent()).getText();
        String idprod = Integer.toString(consemp.getIdproducto()), cant = Float.toString(consemp.getCantidad());
        String datos[] = {idprod, consemp.getNomempleado(), consemp.getProducto(), cant};
        return sql.insertar(datos, "insert into consumosempleados (idproducto,nombreEmp,producto,cantidad,fecha,activo) values (?,?,?,?,STR_TO_DATE('" + fecha + "','%d-%m-%Y %H:%i'),1)");
    }

    public boolean EditarConsumosEmpleados(ConsumosEmpleados consemp) {
        String fecha = ((JTextField) vConsumosEmpleados.jDateFecha.getDateEditor().getUiComponent()).getText();
        String idprod = Integer.toString(consemp.getIdproducto()), cant = Float.toString(consemp.getCantidad()), id = Integer.toString(consemp.getIdconsumo());
        String datos[] = {idprod, consemp.getNomempleado(), consemp.getProducto(), cant, id};
        return sql.editar(datos, "update consumosempleados set idproducto=?,nombreEmp=?,producto=?,cantidad=?,fecha=STR_TO_DATE('" + fecha + "','%d-%m-%Y %H:%i') where idconsumo=?");
    }

    public boolean EliminarConsumosEmpleados(ConsumosEmpleados consemp) {
        sql.Eliminar("consumosempleados", "idconsumo", consemp.getIdconsumo());
        return true;
    }

    public boolean RestarStockConsumidoLocal(ConsumosEmpleados consemp) {
        String idprod = Integer.toString(consemp.getIdproducto()), cant = Float.toString(consemp.getCantidad());
        String datos[] = {cant, idprod};
        sql.insertar(datos, "update insumos i inner join recetas r on r.idinsumo=i.idinsumo set i.stock=i.stock-? where r.idproducto=? and r.activo=1 and i.activo=1");
        return true;
    }

    public boolean CancelarStockConsumidoLocal(ConsumosEmpleados consemp) {
        String idprod = Integer.toString(consemp.getIdproducto()), cant = Float.toString(consemp.getCantidad());
        String datos[] = {cant, idprod};
        sql.insertar(datos, "update insumos i inner join recetas r on r.idinsumo=i.idinsumo set i.stock=i.stock+? where r.idproducto=? and r.activo=1 and i.activo=1");
        return true;
    }

    public int ConsultarStockNegativosN_MOD(String producto, float cantidad){
        return sql.ConsultarStockNegativosN_MOD(producto, cantidad);
    }
    
    public int ConsultarStockCeroN_MOD(String producto, float cantidad){
        return sql.ConsultarStockCeroN_MOD(producto, cantidad);
    }
    
    public int ConsultarStockNegativosMOD(String producto, float numtabla, float numtexto){
        return sql.ConsultarStockNegativosMOD(producto, numtabla, numtexto);
    }
    
    public int ConsultarStockCeroMOD(String producto, float numtabla, float numtexto){
        return sql.ConsultarStockCeroMOD(producto, numtabla, numtexto);
    }
    
    public int ObtenerIDProducto(String dato) {
        return sql.ObtenerID("select idproducto from productos where descripcion='" + dato + "' and activo=1");
    }

}
