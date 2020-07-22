/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author CRISTIAN
 */
public class TiposMovimientos {
    private int idtipomovimiento;
    private String descripcion;
    private String tipo;
    int activo;

    public TiposMovimientos() {
    }

    public TiposMovimientos(int idtipomovimiento, String descripcion, String tipo, int activo) {
        this.idtipomovimiento = idtipomovimiento;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.activo = activo;
    }

    public int getIdtipomovimiento() {
        return idtipomovimiento;
    }

    public void setIdtipomovimiento(int idtipodocumento) {
        this.idtipomovimiento = idtipodocumento;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
