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
public class TiposUsuarios {
    
    private int idtipousuario;
    private String descripcion;

    public TiposUsuarios() {
    }

    public TiposUsuarios(int idtipousuario, String descripcion) {
        this.idtipousuario = idtipousuario;
        this.descripcion = descripcion;
    }

    public int getIdtipousuario() {
        return idtipousuario;
    }

    public void setIdtipousuario(int idtipousuario) {
        this.idtipousuario = idtipousuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
  
}
