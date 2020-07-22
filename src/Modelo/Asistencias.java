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
public class Asistencias {

    private int idasistencia;
    private int idempleado;
    private String NroAsistencia;
    private Timestamp fecha_asistencia;
    private String descripcion;
    private float sueldo;
    private int activo;

    public Asistencias() {
    }

    public Asistencias(int idasistencia, int idempleado, String NroAsistencia, Timestamp fecha_asistencia, String descripcion, float Sueldo, int activo) {
        this.idasistencia = idasistencia;
        this.idempleado = idempleado;
        this.NroAsistencia = NroAsistencia;
        this.fecha_asistencia = fecha_asistencia;
        this.descripcion = descripcion;
        this.sueldo = Sueldo;
        this.activo = activo;
    }

    public String getNroAsistencia() {
        return NroAsistencia;
    }

    public void setNroAsistencia(String NroAsistencia) {
        this.NroAsistencia = NroAsistencia;
    }

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    public int getIdasistencia() {
        return idasistencia;
    }

    public void setIdasistencia(int idasistencia) {
        this.idasistencia = idasistencia;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public Timestamp getFecha_asistencia() {
        return fecha_asistencia;
    }

    public void setFecha_asistencia(Timestamp fecha_asistencia) {
        this.fecha_asistencia = fecha_asistencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

}
