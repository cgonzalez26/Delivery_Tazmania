/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.TiposEmpleados;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Colo-PC
 */
public class control_TiposEmpleados {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_TiposEmpleados() {
        sql= new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
       String []columnas={"idtipoempleado","descripcion", "sueldo"};
       Object[][]datos=sql.GetTabla(columnas,"tiposempleados" , "select idtipoempleado,descripcion,Sueldo from tiposempleados where activo=1");
       return datos;
    }
    
    public Object[][]MostrarDatoBuscadoTipoEmpleado(String tipoemp){
       String []columnas={"idtipoempleado","descripcion", "sueldo"};
       Object[][]datos=sql.GetTabla(columnas,"tiposempleados" , "select idtipoempleado,descripcion,Sueldo from tiposempleados where descripcion like '%" + tipoemp + "%' and activo=1");
       return datos;
    }
    
    public boolean InsertarTiposEmpleados(TiposEmpleados tiposempleados){
        String datos[]={tiposempleados.getDescripcion()};
        return sql.insertar(datos, "insert into tiposempleados (descripcion,activo) values (?,1)");
    }
    
    public boolean EditarTiposEmpleados(TiposEmpleados tiposempleados){
        String id= (Integer.toString(tiposempleados.getIdtipoempleado()));
        String datos[]={tiposempleados.getDescripcion(), 
            Float.toString(tiposempleados.getSueldo()),
            id};
        return sql.editar(datos, "update tiposempleados set descripcion=?,sueldo=? where idtipoempleado=?");
    }
    
    public boolean EliminarTiposEmpleados(TiposEmpleados tiposempleados){
        sql.baja_dedatos("tiposempleados", "idtipoempleado", tiposempleados.getIdtipoempleado());
        return true;
    } 
    
    public TiposEmpleados getTipoEmpleadoByDescripcion(List<TiposEmpleados> tipos,String descripcion){        
        Iterator<TiposEmpleados> it = tipos.iterator();
        while(it.hasNext())
        {
            TiposEmpleados te = it.next();
            if (te.getDescripcion().equals(descripcion)){
                return te;
            }
        }
        return null;
    }
    
    public float getTotalSueldo(int idtipoempleado){
        float total= 0;
        //obtener sueldo segun el tipo de empleado(tabla tiposempleados)
        String query = "SELECT Sueldo FROM tiposempleados WHERE activo=1 AND idtipoempleado ="+Integer.toString(idtipoempleado);
        String campo = "Sueldo";
        total = sql.getTotalSueldoTipoEmpleado(campo, query);
        return total;
    }
    
    public float getTotalPagos(){
        float total_cajeros = getTotalSueldo(2);
        float total_cocineros = getTotalSueldo(3);
        float total_pizzeros = getTotalSueldo(4);
        float total_cadete = getTotalSueldo(5);
        float total = total_cajeros+total_cocineros+total_pizzeros+total_cadete*4;
        return total;
    }
}
