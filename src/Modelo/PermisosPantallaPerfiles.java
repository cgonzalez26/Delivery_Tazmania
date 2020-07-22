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
public class PermisosPantallaPerfiles {
    
    private int idpermisopantallaperfil;
    private int idtipousuario;
    private int idpantalla;
    private int activo;
    
    public PermisosPantallaPerfiles() {
    }

    public PermisosPantallaPerfiles(int idpermisopantallaperfil, int idtipousuario, int idpantalla, int activo) {
        this.idpermisopantallaperfil = idpermisopantallaperfil;
        this.idtipousuario = idtipousuario;
        this.idpantalla = idpantalla;
        this.activo = activo;
    }

    public int getIdpermisopantallaperfil() {
        return idpermisopantallaperfil;
    }

    public void setIdpermisopantallaperfil(int idpermisopantallaperfil) {
        this.idpermisopantallaperfil = idpermisopantallaperfil;
    }

    public int getIdtipousuario() {
        return idtipousuario;
    }

    public void setIdtipousuario(int idtipousuario) {
        this.idtipousuario = idtipousuario;
    }

    public int getIdpantalla() {
        return idpantalla;
    }

    public void setIdpantalla(int idpantalla) {
        this.idpantalla = idpantalla;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    
    
}
