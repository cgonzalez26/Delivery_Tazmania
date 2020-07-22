/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.TiposArticulos;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_TiposArticulos {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_TiposArticulos() {
        sql= new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
        String[]columnas={"idtipoarticulo","descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "tiposarticulos", "select idtipoarticulo,descripcion from tiposarticulos where activo=1");
        return datos;
    }
    
    public boolean InsertarTiposArticulos(TiposArticulos tiposarticulos){
        String[]datos={tiposarticulos.getDescripcion()};
        return sql.insertar(datos, "insert into tiposarticulos (descripcion,activo) values (?,1)");
    }
    
    public boolean EditarTiposArticulos(TiposArticulos tiposarticulos){
        String id=(Integer.toBinaryString(tiposarticulos.getIdtipoarticulo()));
        String datos[]={tiposarticulos.getDescripcion(),id};
        return sql.editar(datos, "update tiposarticulos set descripcion=? where idtipoarticulo=?");
    }
    
    public boolean EliminarTiposArticulos(TiposArticulos tiposarticulos){
        sql.baja_dedatos("tiposarticulos", "idtipoarticulo", tiposarticulos.getIdtipoarticulo());
        return true;
    }
            
    
}
