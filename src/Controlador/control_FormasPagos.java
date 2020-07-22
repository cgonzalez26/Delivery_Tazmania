/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Formaspagos;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_FormasPagos {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_FormasPagos() {
        sql= new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
        String[]columnas={"idformapago","Descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "formaspagos", "select idformapago,Descripcion from formaspagos where activo=1");
        return datos;
    }
    
    public boolean InsertarFormasPagos(Formaspagos formaspagos){
        String datos[]={formaspagos.getDescripcion()};
        return sql.insertar(datos, "insert into formaspagos (Descripcion,activo) values (?,1)");
    }
    
    public boolean EditarFormasPagos(Formaspagos formaspagos){
        String id=(Integer.toString(formaspagos.getIdformapago()));
        String datos[]={formaspagos.getDescripcion(),id};
        return sql.editar(datos, "update formaspagos set Descripcion=? where idformapago=?");
    }
    
    public boolean EliminarFormasPagos(Formaspagos formaspagos){
        sql.baja_dedatos("formaspagos", "idformapago", formaspagos.getIdformapago());
        return true;
    }
    
}
