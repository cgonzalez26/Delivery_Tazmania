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
public class Recetas {
      
    private int idreceta,idproducto,idinsumo,activo;

    public Recetas() {
    }

    public Recetas(int idreceta, int idproducto, int idinsumo, int activo) {
        this.idreceta = idreceta;
        this.idproducto = idproducto;
        this.idinsumo = idinsumo;
        this.activo = activo;
    }

    public int getIdreceta() {
        return idreceta;
    }

    public void setIdreceta(int idreceta) {
        this.idreceta = idreceta;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(int idinsumo) {
        this.idinsumo = idinsumo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    
    
}
