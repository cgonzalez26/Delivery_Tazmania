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
public class modelo_Pantallas {
    private int id_pantalla;
    private String nombre_pantalla;
    
    public modelo_Pantallas(int id_pantalla, String nombre_pantalla){
        this.id_pantalla=id_pantalla;
        this.nombre_pantalla=nombre_pantalla;
    }

    public int getId_pantalla() {
        return id_pantalla;
    }

    public void setId_pantalla(int id_pantalla) {
        this.id_pantalla = id_pantalla;
    }

    public String getNombre_pantalla() {
        return nombre_pantalla;
    }

    public void setNombre_pantalla(String nombre_pantalla) {
        this.nombre_pantalla = nombre_pantalla;
    }
}

    
