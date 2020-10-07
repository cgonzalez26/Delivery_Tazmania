package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author Colo-PC
 */
public class MercadoPago {

    private int idmercadopago;
    private int idcliente;
    private String nroOperacion;
    private float importe;
    private float abonoCliente;
    private String descripcion;
    private Timestamp fecha;

    public MercadoPago() {

    }

    public MercadoPago(int idmercadopago, int idcliente, String nroOperacion, float importe, float abonoCliente ,String descripcion, Timestamp fecha) {
        this.idmercadopago = idmercadopago;
        this.idcliente = idcliente;
        this.nroOperacion = nroOperacion;
        this.importe = importe;
        this.abonoCliente = abonoCliente;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getIdmercadopago() {
        return idmercadopago;
    }

    public void setIdmercadopago(int idmercadopago) {
        this.idmercadopago = idmercadopago;
    }

    public float getAbonoCliente() {
        return abonoCliente;
    }

    public void setAbonoCliente(float abonoCliente) {
        this.abonoCliente = abonoCliente;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNroOperacion() {
        return nroOperacion;
    }

    public void setNroOperacion(String nroOperacion) {
        this.nroOperacion = nroOperacion;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

}
