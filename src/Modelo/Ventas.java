/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author Colo-PC
 */
public class Ventas {

    private int idventa;
    private int idusuario;
    private String Nrofactura;
    private Timestamp fecha_venta;
    private String tipoVenta;
    private float montototal;
    private int activo;

    public Ventas() {
    }

    public Ventas(int idventa, int idusuario, String Nrofactura, Timestamp fecha_venta, String tipoVenta, float montototal, int activo) {
        this.idventa = idventa;
        this.idusuario = idusuario;
        this.Nrofactura = Nrofactura;
        this.fecha_venta = fecha_venta;
        this.tipoVenta = tipoVenta;
        this.montototal = montototal;
        this.activo = activo;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNrofactura() {
        return Nrofactura;
    }

    public void setNrofactura(String Nrofactura) {
        this.Nrofactura = Nrofactura;
    }

    public Timestamp getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(Timestamp fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public float getMontototal() {
        return montototal;
    }

    public void setMontototal(float montototal) {
        this.montototal = montototal;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

}
