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
public abstract class modelo_Proveedores extends modelo_Persona {
    private int Id_Proveedores;
    private String TipoDoc_Proveedores;
    private int NroDoc_Proveedores;
    private String NomComercial_Proveedores;
    
    public modelo_Proveedores(){
        
    }
    
    public modelo_Proveedores(int Id_Proveedores,String TipoDoc_Proveedores, int NroDoc_Proveedores, String NomComercial_Proveedores){
        this.Id_Proveedores=Id_Proveedores;
        this.TipoDoc_Proveedores=TipoDoc_Proveedores;
        this.NroDoc_Proveedores=NroDoc_Proveedores;
        this.NomComercial_Proveedores=NomComercial_Proveedores;
    }

    public int getId_Proveedores() {
        return Id_Proveedores;
    }

    public void setId_Proveedores(int Id_Proveedores) {
        this.Id_Proveedores = Id_Proveedores;
    }

    public String getTipoDoc_Proveedores() {
        return TipoDoc_Proveedores;
    }

    public void setTipoDoc_Proveedores(String TipoDoc_Proveedores) {
        this.TipoDoc_Proveedores = TipoDoc_Proveedores;
    }

    public int getNroDoc_Proveedores() {
        return NroDoc_Proveedores;
    }

    public void setNroDoc_Proveedores(int NroDoc_Proveedores) {
        this.NroDoc_Proveedores = NroDoc_Proveedores;
    }

    public String getNomComercial_Proveedores() {
        return NomComercial_Proveedores;
    }

    public void setNomComercial_Proveedores(String NomComercial_Proveedores) {
        this.NomComercial_Proveedores = NomComercial_Proveedores;
    }
    
    public abstract DefaultTableModel MostrarDatos() throws SQLException;
    
    public abstract boolean InsertarProveedores(modelo_Proveedores modelo);
    
    public abstract boolean EditarProveedores(modelo_Proveedores modelo);
    
    public abstract boolean EliminarProveedores(modelo_Proveedores modelo);
    
}
