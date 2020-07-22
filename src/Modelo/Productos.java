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
public class Productos {
    
    private int idproducto;
    private int idcategoriaproducto;
    private String descripcion;
    private float precioventa;
    private Timestamp fecha_registro;
    private int activo;

    public Productos() {
    }

    public Productos(int idproducto, int idcategoriaproducto, String descripcion, float precioventa, Timestamp fecha_registro, int activo) {
        this.idproducto = idproducto;
        this.idcategoriaproducto = idcategoriaproducto;
        this.descripcion = descripcion;
        this.precioventa = precioventa;
        this.fecha_registro = fecha_registro;
        this.activo = activo;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdcategoriaproducto() {
        return idcategoriaproducto;
    }

    public void setIdcategoriaproducto(int idcategoriaproducto) {
        this.idcategoriaproducto = idcategoriaproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(float precioventa) {
        this.precioventa = precioventa;
    }

    public Timestamp getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    
    
}
