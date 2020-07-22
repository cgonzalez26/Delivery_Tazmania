/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import Modelo.TiposGastos;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Colo-PC
 */
public class control_TiposGastos {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    private List<TiposGastos> tiposgastos;

    public control_TiposGastos() {
        sql= new Sentencias_sql();
    }

    public Object[][]MostrarDatos(){
        String[]columnas={"idtipogasto","descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "tiposgastos", "select idtipogasto,descripcion from tiposgastos where activo=1");
        return datos;
    }
    
    public boolean InsertarTiposGastos(TiposGastos tiposgastos){
        String datos[]={tiposgastos.getDescripcion()};
        return sql.insertar(datos, "insert into tiposgastos (descripcion,activo) values (?,1)");
    }
    
    public boolean EditarTiposGastos(TiposGastos tiposgastos){
        String id=(Integer.toString(tiposgastos.getIdtipogasto()));
        String datos[]={tiposgastos.getDescripcion(),id};
        return sql.editar(datos, "update tiposgastos set descripcion=? where idtipogasto=?");
    }
    
    public boolean EliminarTiposGastos(TiposGastos tiposgastos){
        sql.baja_dedatos("tiposgastos", "idtipogasto", tiposgastos.getIdtipogasto());
        return true;
    }        
    
    public TiposGastos getTipoGastoByDescripcion(List<TiposGastos> tipos,String descripcion){        
        Iterator<TiposGastos> it = tipos.iterator();
        while(it.hasNext())
        {
            TiposGastos tg = it.next();
            if (tg.getDescripcion().equals(descripcion)){
                return tg;
            }
        }
        return null;
    }
}
