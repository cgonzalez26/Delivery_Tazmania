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
public class TiposEmpleados {
    
    int idtipoempleado;
    String descripcion;
    float sueldo; 
    int activo;

    public TiposEmpleados() {
    } 
    public TiposEmpleados(int idtipoempleado, String descripcion, float sueldo, int activo) {
        this.idtipoempleado = idtipoempleado;
        this.descripcion = descripcion;
        this.sueldo = sueldo;
        this.activo = activo;
    }

    public int getIdtipoempleado() {
        return idtipoempleado;
    }

    public void setIdtipoempleado(int idtipoempleado) {
        this.idtipoempleado = idtipoempleado;
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

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }
    
}
