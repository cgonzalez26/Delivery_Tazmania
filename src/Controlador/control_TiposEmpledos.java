
package Controlador;

import Modelo.TiposEmpleados;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_TiposEmpledos {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_TiposEmpledos() {
        sql= new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
       String []columnas={"idtipoempleado","descripcion"};
       Object[][]datos=sql.GetTabla(columnas,"tiposempleados" , "select idtipoempleado,descripcion from tiposempleados where activo=1");
       return datos;
    }
    
    public Object[][]MostrarDatoBuscadoTipoEmpleado(String tipoemp){
       String []columnas={"idtipoempleado","descripcion"};
       Object[][]datos=sql.GetTabla(columnas,"tiposempleados" , "select idtipoempleado,descripcion from tiposempleados where descripcion like '%" + tipoemp + "%' and activo=1");
       return datos;
    }
    
    public boolean InsertarTiposEmpleados(TiposEmpleados tiposempleados){
        String datos[]={tiposempleados.getDescripcion()};
        return sql.insertar(datos, "insert into tiposempleados (descripcion,activo) values (?,1)");
    }
    
    public boolean EditarTiposEmpleados(TiposEmpleados tiposempleados){
        String id= (Integer.toString(tiposempleados.getIdtipoempleado()));
        String datos[]={tiposempleados.getDescripcion(), id};
        return sql.editar(datos, "update tiposempleados set descripcion=? where idtipoempleado=?");
    }
    
    public boolean EliminarTiposEmpleados(TiposEmpleados tiposempleados){
        sql.baja_dedatos("tiposempleados", "idtipoempleado", tiposempleados.getIdtipoempleado());
        return true;
    }  
}
