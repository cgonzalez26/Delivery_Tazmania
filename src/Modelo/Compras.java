/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;


/**
 *
 * @author Colo-PC
 */
public class Compras{
    
    private int idcompra;
    private int idproveedor;
    private int idusuario;
    private String Nrocompra;
    private Timestamp fecha_compra;
    private float Montototal;
    private int activo;
    private List<DetallesCompras> detallescompra;

    public Compras() {
        detallescompra= new ArrayList();
    }

    public Compras(int idcompra, int idproveedor, int idusuario, String Nrocompra, Timestamp fecha_compra, float Montototal, int activo, List<DetallesCompras> detallescompra) {
        this.idcompra = idcompra;
        this.idproveedor = idproveedor;
        this.idusuario = idusuario;
        this.Nrocompra = Nrocompra;
        this.fecha_compra = fecha_compra;
        this.Montototal = Montototal;
        this.activo = activo;
        this.detallescompra=detallescompra;
    }

    public int getIdcompra() {
        return idcompra;
    }

    public List<DetallesCompras> getDetallescompra() {
        return detallescompra;
    }

    public void setDetallescompra(List<DetallesCompras> detallescompra) {
        this.detallescompra = detallescompra;
    }

    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNrocompra() {
        return Nrocompra;
    }

    public void setNrocompra(String Nrocompra) {
        this.Nrocompra = Nrocompra;
    }

    public Timestamp getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Timestamp fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public float getMontototal() {
        return Montototal;
    }

    public void setMontototal(float Montototal) {
        this.Montototal = Montototal;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Object SubTotal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
