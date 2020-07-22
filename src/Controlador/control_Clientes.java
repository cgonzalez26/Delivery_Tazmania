package Controlador;

import Modelo.Clientes;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_Clientes {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_Clientes() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos() {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNIClienteTelefono(String dni, String nombre, String telefono) {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1 and dni like '%" + dni + "%' and nombre like '%" + nombre + "%' and telefono like '%" + telefono + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaCliente(String nombre) {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1 and nombre like '%" + nombre + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNI(String dni) {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1 and dni like '%" + dni + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaTelefono(String telefono) {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1 and telefono like '%" + telefono + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNICliente(String dni, String nombre) {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1 and dni like '%" + dni + "%' and nombre like '%" + nombre + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaDNITelefono(String dni, String telefono) {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1 and dni like '%" + dni + "%' and telefono like '%" + telefono + "%'");
        return datos;
    }

    public Object[][] MostrarDatosBusquedaClienteTelefono(String nombre, String telefono) {
        String[] columnas = {"idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "email"};
        Object[][] datos = sql.GetTabla(columnas, "clientes", "select idcliente,dni,nombre,apellido,domicilio,telefono,email from clientes where activo=1 and nombre like '%" + nombre + "%' and telefono like '%" + telefono + "%'");
        return datos;
    }

    public boolean InsertarClientes(Clientes cliente) {
        String datos[] = {cliente.getDni(), cliente.getNombre(), cliente.getApellido(), cliente.getDireccion(), cliente.getTelefono(), cliente.getEmail()};
        return sql.insertar(datos, "insert into clientes (dni,nombre,apellido,domicilio,telefono,email,activo) values (?,?,?,?,?,?,1)");
    }

    public boolean EditarClientes(Clientes cliente) {
        String idcliente = Integer.toString(cliente.getIdcliente());
        String datos[] = {cliente.getDni(), cliente.getNombre(), cliente.getApellido(), cliente.getDireccion(), cliente.getTelefono(), cliente.getEmail(), idcliente};
        return sql.editar(datos, "update clientes set dni=? nombre=?,apellido=?,domicilio=?,telefono=?,email=? where idcliente=?");
    }

    public boolean EliminarClientes(Clientes cliente) {
        sql.Eliminar("clientes", "idcliente", cliente.getIdcliente());
        return true;
    }

}
