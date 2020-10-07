package Controlador;

import Modelo.MercadoPago;
import Vistas.vGestion_ImprimirTickets;
import java.sql.Connection;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_MercadoPago {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_MercadoPago() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos() {
        String columnas[] = {"idmercadopago", "idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "descripcion", "importe", "abonoCliente", "nroOperacion", "fecha"};
        Object[][] datos = sql.GetTabla(columnas, "mercadopago", "select m.idmercadopago, c.idcliente, c.dni, c.nombre, c.apellido, c.domicilio, c.telefono, m.descripcion, m.importe, m.abonoCliente, m.nroOperacion, m.fecha from mercadopago as m LEFT JOIN clientes as c ON m.idcliente=c.idcliente where m.activo=1");
        return datos;
    }

    public Object[][] MostrarDatosPorBusqueda(String texto) {
        String columnas[] = {"idmercadopago", "idcliente", "dni", "nombre", "apellido", "domicilio", "telefono", "descripcion", "importe", "abonoCliente", "nroOperacion", "fecha"};
        Object[][] datos = sql.GetTabla(columnas, "mercadopago", "select m.idmercadopago, c.idcliente, c.dni, c.nombre, c.apellido, c.domicilio, c.telefono, m.descripcion, m.importe, m.abonoCliente, m.nroOperacion, m.fecha from mercadopago as m LEFT JOIN clientes as c ON m.idcliente=c.idcliente where m.nroOperacion like '%" + texto + "%' and m.activo=1");
        return datos;
    }

    public boolean InsertarDatosMercadoPago(MercadoPago mp) {
        String idcliente = Integer.toString(mp.getIdcliente()), importe = Float.toString(mp.getImporte()), abonoCliente = Float.toString(mp.getAbonoCliente()), fecha = ((JTextField) vGestion_ImprimirTickets.jDateFecha.getDateEditor().getUiComponent()).getText();
        String[] datos = {idcliente, mp.getNroOperacion(), importe, mp.getDescripcion(), abonoCliente};
        return sql.insertar(datos, "insert into mercadopago (idcliente,nroOperacion,importe,descripcion,fecha,activo,abonoCliente) values (?,?,?,?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),1,?");
    }

    public boolean EditarDatosMercadoPago(MercadoPago mp) {
        String idmercadopago = Integer.toString(mp.getIdmercadopago()), idcliente = Integer.toString(mp.getIdcliente()), importe = Float.toString(mp.getImporte()), abonoCliente = Float.toString(mp.getAbonoCliente()), fecha = ((JTextField) vGestion_ImprimirTickets.jDateFecha.getDateEditor().getUiComponent()).getText();
        String[] datos = {idcliente, mp.getNroOperacion(), importe, mp.getDescripcion(), abonoCliente, idmercadopago};
        return sql.editar(datos, "update mercadopago set idcliente=?, nroOperacion=?, importe=?, descripcion=?, fecha=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'), abonoCliente=? where idmercadopago=?");
    }

    public boolean EliminarMercadoPago(MercadoPago mp) {
        sql.baja_dedatos("mercadopago", "idmercadopago", mp.getIdmercadopago());
        return true;
    }

    public int ObtenerIDCliente(String texto) {
        return sql.ObtenerID("select idcliente from mercadopago where idcliente = (select idcliente from clientes where dni='" + texto + "' and activo=1");
    }

}
