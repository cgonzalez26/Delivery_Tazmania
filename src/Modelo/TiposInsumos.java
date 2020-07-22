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
public class TiposInsumos {
    
    private int idtipoinsumo;
    private String descripcion;

    public TiposInsumos() {
    }

    public TiposInsumos(int idtipoinsumo, String descripcion) {
        this.idtipoinsumo = idtipoinsumo;
        this.descripcion = descripcion;
    }

    public int getIdtipoinsumo() {
        return idtipoinsumo;
    }

    public void setIdtipoinsumo(int idtipoinsumo) {
        this.idtipoinsumo = idtipoinsumo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
