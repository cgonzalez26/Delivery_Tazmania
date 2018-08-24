/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public abstract class modelo_TipoInsumos {
    private int Id_TipoInsumos;
    private String Nombre_TipoInsumos;
    
    public modelo_TipoInsumos(){
        
    }

    public modelo_TipoInsumos(int Id_TipoInsumos, String Nombre_TipoInsumos) {
        this.Id_TipoInsumos = Id_TipoInsumos;
        this.Nombre_TipoInsumos = Nombre_TipoInsumos;
    }

    public int getId_TipoInsumos() {
        return Id_TipoInsumos;
    }

    public void setId_TipoInsumos(int Id_TipoInsumos) {
        this.Id_TipoInsumos = Id_TipoInsumos;
    }

    public String getNombre_TipoInsumos() {
        return Nombre_TipoInsumos;
    }

    public void setNombre_TipoInsumos(String Nombre_TipoInsumos) {
        this.Nombre_TipoInsumos = Nombre_TipoInsumos;
    }
    
    public abstract DefaultTableModel MostrarDatosTI() throws SQLException;
    
    public abstract boolean InsertarTiposInsumos(modelo_TipoInsumos modelo);
    
    public abstract boolean EditarTiposInsumos(modelo_TipoInsumos modelo);
    
    public abstract boolean EliminarTiposInsumos(modelo_TipoInsumos modelo);
    
    public abstract int CantidadIDTipoInsumos();
}
