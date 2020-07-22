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
public class DetallesCompras {
    
    private int iddetallecompra;
    private int idcompra;
    private int idinsumo;
    private float precio;
    private float cantidad;
    private int activo;

    public DetallesCompras() {
    }

    public DetallesCompras(int iddetallecompra, int idcompra, int idinsumo, float precio, float cantidad, int activo) {
        this.iddetallecompra = iddetallecompra;
        this.idcompra = idcompra;
        this.idinsumo = idinsumo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.activo = activo;
    }

    public int getIddetallecompra() {
        return iddetallecompra;
    }

    public void setIddetallecompra(int iddetallecompra) {
        this.iddetallecompra = iddetallecompra;
    }

    public int getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
    }

    public int getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(int idinsumo) {
        this.idinsumo = idinsumo;
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
