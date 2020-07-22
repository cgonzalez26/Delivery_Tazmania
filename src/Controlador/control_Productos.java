
package Controlador;

import Modelo.Productos;
import Vistas.vGestion_Productos;
import java.sql.Connection;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC  
 */
public class control_Productos {
    private Sentencias_sql sql; 
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    
    public control_Productos(){
        sql= new Sentencias_sql();
    }
    
    public Object[][] MostrarDatos(){
        String[]columnas={"idproducto","idcategoriaproducto","c.descripcion","p.descripcion","precioventa","fecharegistro"};
        Object[][]datos=sql.GetTabla(columnas,"productos","select p.idproducto,c.idcategoriaproducto,c.descripcion,p.descripcion,p.precioventa,date_format(p.fecharegistro,'%d/%m/%Y %H:%i') as fecharegistro from productos as p INNER JOIN categoriasproductos as c on p.idcategoriaproducto=c.idcategoriaproducto where p.activo=1");
        return datos;
    }
    
    public Object[][] MostrarDatosBusqueda(String texto){
        String[]columnas={"idproducto","idcategoriaproducto","c.descripcion","p.descripcion","precioventa","fecharegistro"};
        Object[][]datos=sql.GetTabla(columnas,"productos","select p.idproducto,c.idcategoriaproducto,c.descripcion,p.descripcion,p.precioventa,date_format(p.fecharegistro,'%d/%m/%Y %H:%i') as fecharegistro from productos as p INNER JOIN categoriasproductos as c on p.idcategoriaproducto=c.idcategoriaproducto where p.activo=1 and p.descripcion like '%"+texto+"%'");
        return datos;
    }
        
    public boolean InsertarProductos(Productos producto){
        String precio= Float.toString(producto.getPrecioventa()), fecha=((JTextField)vGestion_Productos.jDateChooser1.getDateEditor().getUiComponent()).getText(),idcatprod=Integer.toString(producto.getIdcategoriaproducto());
        String datos[]={idcatprod,producto.getDescripcion(),precio};
        return sql.insertar(datos, "insert into productos (idcategoriaproducto,descripcion,precioventa,fecharegistro,activo) values (?,?,?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),1)");
    }
    
    public boolean EditarProductos(Productos producto){
        String precio= Float.toString(producto.getPrecioventa()), fecha=((JTextField)vGestion_Productos.jDateChooser1.getDateEditor().getUiComponent()).getText(), id=Integer.toString(producto.getIdproducto()),idcatprod=Integer.toString(producto.getIdcategoriaproducto());
        String datos[]={idcatprod,producto.getDescripcion(),precio,id};
        return sql.editar(datos, "update productos set idcategoriaproducto=?,descripcion=?,precioventa=?,fecharegistro=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s') where idproducto=?");
    }
    
    public boolean EliminarProductos(Productos producto){
        sql.baja_dedatos("productos", "idproducto", producto.getIdproducto());
        return true;
    }
    
    public int ObtenerIDCategoriaProducto(String dato){
        return sql.ObtenerID("select idcategoriaproducto from categoriasproductos where descripcion='"+dato+"'");
    }
    
    public boolean ActualizarPrecios(Productos producto){
        String idprod = Integer.toString(producto.getIdproducto()), precio = Float.toString(producto.getPrecioventa());
        String datos[] ={precio,idprod};
        return sql.editar(datos, "UPDATE productos SET precioventa=? where idproducto=?");
    }  
}
