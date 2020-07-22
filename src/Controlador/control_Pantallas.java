/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Pantallas;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_Pantallas {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    
    public control_Pantallas(){
        sql= new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
        String[]columnas={"idpantalla","nombre"};
        Object[][]datos=sql.GetTabla(columnas, "pantallas", "select idpantalla, nombre from pantallas where activo=1");
        return datos;   
    }
    
    public boolean InsertarNombrePantalla(Pantallas pantalla){
        String datos[]={pantalla.getNombre_pantalla()};
        return sql.insertar(datos, "insert into pantallas (nombre,activo) values (?,1)");
    }
    
    public boolean EditarNombrePantalla(Pantallas pantalla){
        String id=(Integer.toString(pantalla.getId_pantalla()));
        String datos[]={pantalla.getNombre_pantalla(),id};
        return sql.editar(datos, "update pantallas set nombre=? where idpantalla=?");
    }
    
    public boolean EliminarNombrePantalla(Pantallas pantalla){
        sql.baja_dedatos("pantallas", "idpantalla", pantalla.getId_pantalla());
        return true;
    }
}
