/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulos;



/**
 *
 * @author Colo-PC
 */
public class mUsuario extends mPersona {
    private int id_usuario;
    private String nombre_usuario;
    private String contraseña_usuario;
    private String tipo_usario;
    private boolean estado_usuario;
    

    public mUsuario() {

    }

    public mUsuario(int id_usuario, String nombre_usuario, String contraseña_usuario, String tipo_usuario, boolean estado_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.contraseña_usuario = contraseña_usuario;
        this.tipo_usario = tipo_usuario;
        this.estado_usuario = estado_usuario;
        this.id_usuario=id_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContraseña_usuario() {
        return contraseña_usuario;
    }

    public void setContraseña_usuario(String contraseña_usuario) {
        this.contraseña_usuario = contraseña_usuario;
    }

    public String getTipo_usario() {
        return tipo_usario;
    }

    public void setTipo_usario(String tipo_usario) {
        this.tipo_usario = tipo_usario;
    }

    public boolean isEstado_usuario() {
        return estado_usuario;
    }

    public void setEstado_usuario(boolean estado_usuario) {
        this.estado_usuario = estado_usuario;
    }

}
