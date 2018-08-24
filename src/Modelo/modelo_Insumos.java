/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public abstract class modelo_Insumos extends modelo_TipoInsumos {
    
    private int Id_Insumos;
    private String Nombre_Insumos;
    private String Descripcion_Insumos;
    private double Precio_Insumos;
    private int Stock_Insumos;
    private String NomProv_Insumos;
    private String FechaReg_Insumos;
    
    public modelo_Insumos(){
        
    }
    
    public modelo_Insumos(int Id_Insumos,String Nombre_Insumos,String Descripcion_Insumos, double Precio_Insumos, int Stock_Insumos, String NomProv_Insumos,String FechaReg_Insumos){
        
        this.Id_Insumos=Id_Insumos;
        this.Nombre_Insumos=Nombre_Insumos;
        this.Descripcion_Insumos=Descripcion_Insumos;
        this.Precio_Insumos=Precio_Insumos;
        this.Stock_Insumos=Stock_Insumos;
        this.NomProv_Insumos=NomProv_Insumos;
        this.FechaReg_Insumos=FechaReg_Insumos;
    }

    public String getNombre_Insumos() {
        return Nombre_Insumos;
    }

    public void setNombre_Insumos(String Nombre_Insumos) {
        this.Nombre_Insumos = Nombre_Insumos;
    }

    public String getNomProv_Insumos() {
        return NomProv_Insumos;
    }

    public void setNomProv_Insumos(String NomProv_Insumos) {
        this.NomProv_Insumos = NomProv_Insumos;
    }

    public int getId_Insumos() {
        return Id_Insumos;
    }

    public void setId_Insumos(int Id_Insumos) {
        this.Id_Insumos = Id_Insumos;
    }

    public String getDescripcion_Insumos() {
        return Descripcion_Insumos;
    }

    public void setDescripcion_Insumos(String Descripcion_Insumos) {
        this.Descripcion_Insumos = Descripcion_Insumos;
    }

    public double getPrecio_Insumos() {
        return Precio_Insumos;
    }

    public void setPrecio_Insumos(double Precio_Insumos) {
        this.Precio_Insumos = Precio_Insumos;
    }

    public int getStock_Insumos() {
        return Stock_Insumos;
    }

    public void setStock_Insumos(int Stock_Insumos) {
        this.Stock_Insumos = Stock_Insumos;
    }

    public String getFechaReg_Insumos() {
        return FechaReg_Insumos;
    }

    public void setFechaReg_Insumos(String FechaReg_Insumos) {
        this.FechaReg_Insumos = FechaReg_Insumos;
    }
    
    public abstract ArrayList<String> LlenarTipoInsumo();
    
    public abstract DefaultTableModel MostrarDatosI() throws SQLException;
    
    public abstract boolean InsertarInsumos(modelo_Insumos modelo);
    
    public abstract boolean EditarInsumos(modelo_Insumos modelo);
    
    public abstract boolean EliminarInsumos(modelo_Insumos modelo);
    
}
