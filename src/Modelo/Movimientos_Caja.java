/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author CRISTIAN
 */
public class Movimientos_Caja {

    private int idmovimientocaja;
    private int idtipomovimiento;
    private int idcajaturno;
    private int idusuario;
    private String nromovimiento;
    private int idmovimiento;
    private String fecha_movimiento;
    private float monto;
    private Timestamp fecha_registro;
    private int activo;
    private String detalle;
    private String descripcion;
    private String tipoVenta;

    public Movimientos_Caja() {
    }

    public Movimientos_Caja(int idmovimientocaja, int idtipomovimiento, int idcajaturno, int idusuario, String fecha_movimiento,
            float monto, Timestamp fecha_registro, int activo, int idmovimiento, String detalle, String descripcion, String tipoVenta) {
        this.idmovimientocaja = idmovimientocaja;
        this.idtipomovimiento = idtipomovimiento;
        this.idcajaturno = idcajaturno;
        this.idusuario = idusuario;
        this.fecha_movimiento = fecha_movimiento;
        this.monto = monto;
        this.fecha_registro = fecha_registro;
        this.activo = activo;
        this.idmovimiento = idmovimiento;
        this.detalle = detalle;
        this.descripcion = descripcion;
        this.tipoVenta=tipoVenta;
        this.nromovimiento = "";
    }

    public int getIdmovimientocaja() {
        return idmovimientocaja;
    }

    public void setIdmovimientocaja(int idmovimientocaja) {
        this.idmovimientocaja = idmovimientocaja;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public int getIdtipomovimiento() {
        return idtipomovimiento;
    }

    public void setIdtipomovimiento(int idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }

    public int getIdcajaturno() {
        return idcajaturno;
    }

    public void setIdcajaturno(int idcajaturno) {
        this.idcajaturno = idcajaturno;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(String fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
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

    public int getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(int idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNromovimiento() {
        return nromovimiento;
    }

    public void setNromovimiento(String nromovimiento) {
        this.nromovimiento = nromovimiento;
    }

}
