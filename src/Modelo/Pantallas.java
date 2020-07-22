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
public class Pantallas {
    private int idpantalla;
    private String nombre;
    private int activo;

    public Pantallas() {
    }
    
    public Pantallas(int idpantalla, String nombre, int activo){
        this.idpantalla=idpantalla;
        this.nombre=nombre;
        this.activo=activo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getId_pantalla() {
        return idpantalla;
    }

    public void setId_pantalla(int idpantalla) {
        this.idpantalla = idpantalla;
    }

    public String getNombre_pantalla() {
        return nombre;
    }

    public void setNombre_pantalla(String nombre) {
        this.nombre = nombre;
    }
}

    
