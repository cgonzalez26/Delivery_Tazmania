/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public abstract class modelo_DetalleCompras extends modelo_Compras {
    
    private int NroCompra_DetalleCompras;
    
    public modelo_DetalleCompras(){
        
    }
    
    public modelo_DetalleCompras(int NroCompra_DetalleCompras){
        this.NroCompra_DetalleCompras=NroCompra_DetalleCompras;
    }

    public int getNroCompra_DetalleCompras() {
        return NroCompra_DetalleCompras;
    }

    public void setNroCompra_DetalleCompras(int NroCompra_DetalleCompras) {
        this.NroCompra_DetalleCompras = NroCompra_DetalleCompras;
    }
    
    public abstract ArrayList<String> LlenarComboProveedor();
    
    public abstract DefaultTableModel MostrarDatosC() throws SQLException;
    
    public abstract boolean EfectuarCompra(modelo_DetalleCompras modulo1);
    
    public abstract int CantidadInsumo();
    
    public abstract int CantidadTipos();
    
}
