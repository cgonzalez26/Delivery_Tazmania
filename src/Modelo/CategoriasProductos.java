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
public class CategoriasProductos {
    
    private int idcategoriaproducto;
    private String descripcion;

    public CategoriasProductos() {
    }

    public CategoriasProductos(int idcategoriaproducto, String descripcion) {
        this.idcategoriaproducto = idcategoriaproducto;
        this.descripcion = descripcion;
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
 
}
