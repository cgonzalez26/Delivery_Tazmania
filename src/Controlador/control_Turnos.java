/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Turnos;
import java.sql.Connection;

/**
 *
 * @author CRISTIAN
 */
public class control_Turnos {
    private Sentencias_sql sql; 
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
   
    public control_Turnos() {
        this.sql = new Sentencias_sql();
    }
    
    public Object[][] MostrarDatos(){
        String[]columnas={"idturno","descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "turnos", "select idturno,descripcion from turnos where activo=1");
        return datos;
    }
    
    public boolean InsertarTurnos(Turnos turnos){
        String datos[]={turnos.getDescripcion()};
        return sql.insertar(datos, "insert into turnos (descripcion,activo) values (?,1)");
    }
       
    public boolean EditarTiposInsumos(Turnos turnos){
        String id= (Integer.toString(turnos.getIdturno()));
        String datos[]={turnos.getDescripcion(),id};
        return sql.editar(datos, "update turnos set descripcion=? where idturno=?");
    }
    
    public boolean EliminarTiposInsumos(Turnos turnos){
        sql.baja_dedatos("turnos", "idturno", turnos.getIdturno());
        return true;
    }
    
    public Turnos obtenerTurno(int idturno){
        String[] columnas1={"idturno","descripcion"};
        Object[][] datos1=sql.GetTabla(columnas1, "turnos", "SELECT idturno,descripcion FROM turnos WHERE activo=1 AND idturno="+Integer.toString(idturno));
        Turnos turnos = new Turnos();
        turnos.setIdturno(idturno); 
        turnos.setDescripcion((String)datos1[0][1]);               
        return turnos;
    }
    
}
