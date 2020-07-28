package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author Colo-PC
 */
public class Insumos {
    
    private int idinsumo;
    private int idtipoinsumo;
    private int idproveedor;
    private int idunidadmedida;
    private String descripcion;
    private float precio;
    private float stock;
    private Timestamp fecha_registro;
    private int activo;

    public Insumos() {
    }

    public Insumos(int idinsumo, int idtipoinsumo, int idproveedor, int idunidadmedida, String descripcion, float precio, float stock, Timestamp fecha_registro, int activo) {
        this.idinsumo = idinsumo;
        this.idtipoinsumo = idtipoinsumo;
        this.idproveedor = idproveedor;
        this.idunidadmedida = idunidadmedida;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.fecha_registro = fecha_registro;
        this.activo = activo;
    }

    public int getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(int idinsumo) {
        this.idinsumo = idinsumo;
    }

    public int getIdtipoinsumo() {
        return idtipoinsumo;
    }

    public void setIdtipoinsumo(int idtipoinsumo) {
        this.idtipoinsumo = idtipoinsumo;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public Timestamp getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    public float SubTotal(float texto){
        return precio*stock;
    }

    public int getIdunidadmedida() {
        return idunidadmedida;
    }

    public void setIdunidadmedida(int idunidadmedida) {
        this.idunidadmedida = idunidadmedida;
    }
    
}
