package Controlador;

import Modelo.Recetas;
import Vistas.vGestion_Recetas;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_Recetas {
    private Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();
    
    public control_Recetas(){
        sql=new Sentencias_sql();
    }
    
    public Object[][] MostrarInsumos(){
        String[]columnas={"descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "insumos", "select descripcion from insumos where activo=1");
        return datos;
    }
    
    public Object[][] MostrarInsumoBuscado(String insumo){
        String[]columnas={"descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "insumos", "select descripcion from insumos where descripcion like '%" + insumo + "%' and activo=1");
        return datos;
    }
    
    public boolean InsertarRecetas(){
        return sql.InsertarRecetasLotes("insert into recetas (idproducto,idinsumo,activo) values (?,?,1)");
    }
    
    public boolean EditarReceta(Recetas receta){
        int fila = vGestion_Recetas.jTable2.getSelectedRow();
        String id=Integer.toString(receta.getIdreceta()), idinsumo=Integer.toString(receta.getIdinsumo());
        String datos[]={idinsumo,id};
        return sql.editar(datos, "update recetas set idinsumo=? where idreceta=?");
    }
    
    public boolean EliminarReceta(Recetas receta){
        sql.baja_dedatos("recetas", "idreceta",receta.getIdreceta());
        return true;
    }
    
    public int ObtenerIDProducto(String dato){
        return sql.ObtenerID("select idproducto from productos where descripcion='"+dato+"'");
    }
    
    public int ObtenerIDInsumo(String dato){
        return sql.ObtenerID("select idinsumo from insumos where descripcion='"+dato+"'");
    }  
}
