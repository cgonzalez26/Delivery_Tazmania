/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Colo-PC
 */
public class DetallesVentas {
    
    private int iddetalleventa;
    private int idventa;
    private int idproducto;
    private float precio;
    private float cantidad;
    private int activo;

    public DetallesVentas() {
    }

    public DetallesVentas(int iddetalleventa, int idventa, int idproducto, float precio, float cantidad, int activo) {
        this.iddetalleventa = iddetalleventa;
        this.idventa = idventa;
        this.idproducto = idproducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.activo = activo;
    }

    public int getIddetalleventa() {
        return iddetalleventa;
    }

    public void setIddetalleventa(int iddetalleventa) {
        this.iddetalleventa = iddetalleventa;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    
    
}
