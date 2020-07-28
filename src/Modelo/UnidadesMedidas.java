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
public class UnidadesMedidas {
    private int idunidadmedida;
    private String descripcion;
    private String tipo;
    int activo;

    public UnidadesMedidas() {
    }

    public UnidadesMedidas(int idunidadmedida, String descripcion, int activo) {
        this.idunidadmedida = idunidadmedida;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdunidadmedida() {
        return idunidadmedida;
    }

    public void setIdunidadmedida(int idunidadmedida) {
        this.idunidadmedida = idunidadmedida;
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
