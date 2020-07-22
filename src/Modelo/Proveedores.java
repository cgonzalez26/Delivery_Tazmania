/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author CRISTIAN
 */
public class Proveedores extends Personas{
    private int idproveedor;
    private String Nombre_comercial;
    private int activo;        
    
    public Proveedores(){        
    }
    
    public Proveedores(int idproveedor,String Nombre_comercial, int activo){
        this.idproveedor=idproveedor;
        this.Nombre_comercial=Nombre_comercial;
        this.activo=activo;
    }
    public int getIdProveedor() {
        return idproveedor;
    }

    public void setIdProveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }
 
    public String getNombre_comercial() {
        return Nombre_comercial;
    }

    public void setNombre_comercial(String Nombre_comercial) {
        this.Nombre_comercial = Nombre_comercial;
    }
    
    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
