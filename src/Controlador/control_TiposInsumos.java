/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.TiposInsumos;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_TiposInsumos {
    private Sentencias_sql sql; 
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    
    public control_TiposInsumos(){
        sql= new Sentencias_sql();
    }
    
    public Object[][] MostrarDatos(){
        String[]columnas={"idtipoinsumo","descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "tiposinsumos", "select idtipoinsumo,descripcion from tiposinsumos where activo=1");
        return datos;
    }
    
    public Object[][] MostrarDatosBuscado(String tipo){
        String[]columnas={"idtipoinsumo","descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "tiposinsumos", "select idtipoinsumo,descripcion from tiposinsumos where descripcion like '%" + tipo + "%' and activo=1");
        return datos;
    }
    
    public boolean InsertarTiposInsumos(TiposInsumos tipoinsumo){
        String datos[]={tipoinsumo.getDescripcion()};
        return sql.insertar(datos, "insert into tiposinsumos (descripcion,activo) values (?,1)");
    }
       
    public boolean EditarTiposInsumos(TiposInsumos tipoinsumo){
        String id= (Integer.toString(tipoinsumo.getIdtipoinsumo()));
        String datos[]={tipoinsumo.getDescripcion(),id};
        return sql.editar(datos, "update tiposinsumos set descripcion=? where idtipoinsumo=?");
    }
    
    public boolean EliminarTiposInsumos(TiposInsumos tipoinsumo){
        sql.baja_dedatos("tiposinsumos", "idtipoinsumo", tipoinsumo.getIdtipoinsumo());
        return true;
    }
    
}
