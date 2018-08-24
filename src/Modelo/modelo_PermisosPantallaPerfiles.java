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
public class modelo_PermisosPantallaPerfiles {
    
    private modelo_TiposUsuarios id_tipousuario;
    private modelo_Pantallas id_pantalla;

    public modelo_PermisosPantallaPerfiles() {
    }

    public modelo_TiposUsuarios getId_tipousuario() {
        return id_tipousuario;
    }

    public void setId_tipousuario(modelo_TiposUsuarios id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }

    public modelo_Pantallas getId_pantalla() {
        return id_pantalla;
    }

    public void setId_pantalla(modelo_Pantallas id_pantalla) {
        this.id_pantalla = id_pantalla;
    }
    
    
     
    
}
