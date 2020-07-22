/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author CRISTIAN
 */
public class Egresos {
    //atributos
    private Integer idegreso;
    private Integer idtipoegreso;
    private String nroegreso;
    private String descripcion;
    //private String Fecha;       
    private String Fecha;
    private Float Monto;
    private String detalle;
    private int idmovimientocaja;
    //contructor
    public Egresos() {      
    }

    public Egresos(Integer idegreso, Integer idtipoegreso, String descripcion, String Fecha, Float Monto, String detalle) {
        this.idegreso = idegreso;
        this.idtipoegreso = idtipoegreso;
        this.descripcion = descripcion;
        this.Fecha = Fecha;
        this.Monto = Monto;
        this.detalle = detalle;
        this.nroegreso = "";
        this.idmovimientocaja =0;
    }
    
    //metodos getter y setter
    public Integer getIdegreso() {
        return idegreso;
    }

    public void setIdegreso(Integer idegreso) {
        this.idegreso = idegreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public Integer getIdtipoegreso() {
        return idtipoegreso;
    }

    public void setIdtipoegreso(Integer idtipoegreso) {
        this.idtipoegreso = idtipoegreso;
    }

    public Float getMonto() {
        return Monto;
    }

    public void setMonto(Float Monto) {
        this.Monto = Monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getNroegreso() {
        return nroegreso;
    }

    public void setNroegreso(String nroegreso) {
        this.nroegreso = nroegreso;
    }

    public int getIdmovimientocaja() {
        return idmovimientocaja;
    }

    public void setIdmovimientocaja(int idmovimientocaja) {
        this.idmovimientocaja = idmovimientocaja;
    }
    
}
