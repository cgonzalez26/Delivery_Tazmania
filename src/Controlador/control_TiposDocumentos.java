
package Controlador;

import Modelo.TiposDocumentos;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Colo-PC
 */
public class control_TiposDocumentos {
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    
    public control_TiposDocumentos(){
        sql= new Sentencias_sql();
    }
    
    public Object[][]MostarDatos(){
        String[]columnas={"idtipodocumento","Descripcion"};
        Object[][]datos=sql.GetTabla(columnas, "tiposdocumentos", "select idtipodocumento,Descripcion from tiposdocumentos where activo=1");
        return datos;
    }
    
    public boolean InsertarTiposDocumentos(TiposDocumentos td){
        String datos[]={td.getDescripcion()};
        return sql.insertar(datos, "insert into tiposdocumentos (Descripcion,activo) values (?,1)");
    }
    
    public boolean EditarTiposDocumentos(TiposDocumentos td){
        String id = (Integer.toString(td.getIdtipodocumento()));
        String datos[]={td.getDescripcion(),id};
        return sql.editar(datos, "update tiposdocumentos set Descripcion=? where idtipodocumento=?");
    }
    
    public boolean EliminarTiposDocumentos(TiposDocumentos td){
        sql.baja_dedatos("tiposdocumentos", "idtipodocumento", td.getIdtipodocumento());
        return true;
    }
    
    public TiposDocumentos getTipoDocumentoByDescripcion(List<TiposDocumentos> tipos,String descripcion){        
        Iterator<TiposDocumentos> it = tipos.iterator();
        while(it.hasNext())
        {
            TiposDocumentos td = it.next();
            if (td.getDescripcion().equals(descripcion)){
                return td;
            }
        }
        return null;
    }
}
