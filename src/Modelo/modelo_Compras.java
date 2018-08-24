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
public abstract class modelo_Compras extends modelo_Insumos {
    
    private int Nro_Compras;
    private String NomUser_Compras;
    private double MontoTot_Compras;
    
    public modelo_Compras(){
        
    }
    
    public modelo_Compras(int Nro_Compras,String NomUser_Compras, double MontoTot_Compras){
        this.Nro_Compras=Nro_Compras;
        this.NomUser_Compras=NomUser_Compras;
        this.MontoTot_Compras=MontoTot_Compras;
    }

    public int getNro_Compras() {
        return Nro_Compras;
    }

    public void setNro_Compras(int Nro_Compras) {
        this.Nro_Compras = Nro_Compras;
    }

    public String getNomUser_Compras() {
        return NomUser_Compras;
    }

    public void setNomUser_Compras(String NomUser_Compras) {
        this.NomUser_Compras = NomUser_Compras;
    }

    public double getMontoTot_Compras() {
        return MontoTot_Compras;
    }

    public void setMontoTot_Compras(double MontoTot_Compras) {
        this.MontoTot_Compras = MontoTot_Compras;
    }
}
