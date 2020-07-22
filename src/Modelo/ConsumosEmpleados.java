package Modelo;

public class ConsumosEmpleados {
    
    private int idconsumo;
    private String nomempleado;
    private int idproducto;
    private String producto;
    private float cantidad;
    private String fecha;

    public ConsumosEmpleados() {

    }

    public ConsumosEmpleados(int idconsumo, String nomempleado, int idproducto, String producto, float cantidad, String fecha) {
        this.idconsumo = idconsumo;
        this.nomempleado = nomempleado;
        this.idproducto = idproducto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getIdconsumo() {
        return idconsumo;
    }

    public void setIdconsumo(int idconsumo) {
        this.idconsumo = idconsumo;
    }
    
    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNomempleado() {
        return nomempleado;
    }

    public void setNomempleado(String nomempleado) {
        this.nomempleado = nomempleado;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
