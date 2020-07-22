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
public class Caja {
    private int idcaja; 
    private int idusuario;
    private Timestamp fecha_apertura;
    private Timestamp fecha_cierre;
    private String nota;
    private float monto;
    private String estado;
    private int activo;

    public Caja() {
    }

    public Caja(int idcaja, int idusuario, Timestamp fecha_apertura, Timestamp fecha_cierre, String nota, String estado, float monto, int activo) {
        this.idcaja = idcaja;
        this.idusuario = idusuario;
        this.fecha_apertura = fecha_apertura;
        this.fecha_cierre = fecha_cierre;
        this.nota = nota;
        this.estado = estado;
        this.monto = monto;
        this.activo = activo;
    }

    public int getIdCaja() {
        return idcaja;
    }

    public void setIdCaja(int idcaja) {
        this.idcaja = idcaja;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public Timestamp getFecha_apertura() {
        return fecha_apertura;
    }

    public void setFecha_apertura(Timestamp fecha_apertura) {
        this.fecha_apertura = fecha_apertura;
    }

    public Timestamp getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Timestamp fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }
    
    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getEstado() {
         return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
  
}
