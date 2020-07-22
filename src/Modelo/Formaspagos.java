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
public class Formaspagos {
    
    private int idformapago;
    private String descripcion;
    private int activo;

    public Formaspagos() {
    }

    public Formaspagos(int idformapago, String descripcion, int activo) {
        this.idformapago = idformapago;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdformapago() {
        return idformapago;
    }

    public void setIdformapago(int idformapago) {
        this.idformapago = idformapago;
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
