package Controlador;

import Modelo.CategoriasProductos;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_CategoriasProductos {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_CategoriasProductos() {
        sql= new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
        String []columnas={"idcategoriaproducto","descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "categoriasproductos","select idcategoriaproducto,descripcion from categoriasproductos where activo=1");
        return datos;
    }
    
    public Object[][]MostrarCategoriaProdBuscado(String catprod){
        String []columnas={"idcategoriaproducto","descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "categoriasproductos","select idcategoriaproducto,descripcion from categoriasproductos where descripcion like '%" + catprod + "%' and activo=1");
        return datos;
    }
    
    public boolean InsertarCategoriasProductos(CategoriasProductos categoriasproductos){
        String datos[]={categoriasproductos.getDescripcion()};
        return sql.insertar(datos, "insert into categoriasproductos (descripcion, activo) values (?,1)");
    }
    
    public boolean EditarCategoriasProductos(CategoriasProductos categoriasproductos){
        String id= (Integer.toString(categoriasproductos.getIdcategoriaproducto()));
        String datos[]={categoriasproductos.getDescripcion(),id};
        return sql.editar(datos, "update categoriasproductos set descripcion=? where idcategoriaproducto=?");
    }
    
    public boolean EliminarCategoriasProductos(CategoriasProductos categoriasproductos){
        sql.baja_dedatos("categoriasproductos", "idcategoriaproducto", categoriasproductos.getIdcategoriaproducto());
        return true;
    }
}
