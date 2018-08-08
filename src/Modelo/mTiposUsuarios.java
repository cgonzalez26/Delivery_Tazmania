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
public class mTiposUsuarios {
    
    private int id_tipousuario;
    private String nombre_usuario;
    
    public mTiposUsuarios(int id_tipousuario, String nombre_usuario){
        this.id_tipousuario=id_tipousuario;
        this.nombre_usuario=nombre_usuario;
    }

    public int getId_tipousuario() {
        return id_tipousuario;
    }

    public void setId_tipousuario(int id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }

    public String getNombre() {
        return nombre_usuario;
    }

    public void setNombre(String nombre) {
        this.nombre_usuario = nombre;
    }
}
