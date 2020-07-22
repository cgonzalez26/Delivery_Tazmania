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
public class TiposArticulos {
    
    private int idtipoarticulo;
    private String descripcion;
    private int activo;

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public TiposArticulos() {
    }

    public TiposArticulos(int idtipoarticulo, String descripcion) {
        this.idtipoarticulo = idtipoarticulo;
        this.descripcion = descripcion;
    }

    public int getIdtipoarticulo() {
        return idtipoarticulo;
    }

    public void setIdtipoarticulo(int idtipoarticulo) {
        this.idtipoarticulo = idtipoarticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
