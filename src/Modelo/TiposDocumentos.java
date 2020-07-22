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
public class TiposDocumentos {
    
    private int idtipodocumento;
    private String descripcion;

    public TiposDocumentos() {
    }

    public TiposDocumentos(int idtipodocumento, String descripcion) {
        this.idtipodocumento = idtipodocumento;
        this.descripcion = descripcion;
    }

    public int getIdtipodocumento() {
        return idtipodocumento;
    }

    public void setIdtipodocumento(int idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
