/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleados;
import Vistas.vGestion_Empleados;
import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_Empleados {
    
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_Empleados() {
        sql=new Sentencias_sql();
    }
    
    public Object[][]MostrarDatos(){
        String[]columnas={"idempleado","idtipoempleado","idtipodocumento","t.Descripcion","d.descripcion","NroDocumento","Nombre","Apellido","direccion","Telefono"};
        Object[][]datos=sql.GetTabla(columnas, "empleados", "select e.idempleado,t.idtipoempleado,d.idtipodocumento,t.Descripcion,d.descripcion,e.NroDocumento,e.Nombre, e.Apellido,e.direccion,e.Telefono from empleados e INNER JOIN tiposempleados t on t.idtipoempleado=e.idtipoempleado INNER JOIN tiposdocumentos d on d.idtipodocumento=e.idtipodocumento where e.activo=1");
        return datos;
    }
    
    public Object[][]MostrarDatosBusqueda(String texto){
        String[]columnas={"idempleado","idtipoempleado","idtipodocumento","t.Descripcion","d.descripcion","NroDocumento","Nombre","Apellido","direccion","Telefono"};
        Object[][]datos=sql.GetTabla(columnas, "empleados", "select e.idempleado,t.idtipoempleado,d.idtipodocumento,t.Descripcion,d.descripcion,e.NroDocumento,e.Nombre, e.Apellido,e.direccion,e.Telefono from empleados e INNER JOIN tiposempleados t on t.idtipoempleado=e.idtipoempleado INNER JOIN tiposdocumentos d on d.idtipodocumento=e.idtipodocumento where e.activo=1 and t.Descripcion like '%"+texto+"%'");
        return datos;
    }
    
    public boolean InsertarEmpledos(Empleados empleados){
        String idtipoemp=Integer.toString(empleados.getIdtipoempleado()), idtipodoc=Integer.toString(empleados.getIdtipodocumento());
        String datos[]={idtipoemp,idtipodoc,empleados.getNroDocumento(),empleados.getNombre(),empleados.getApellido(),empleados.getDireccion(),empleados.getTelefono()};
        return sql.insertar(datos, "insert into empleados (idtipoempleado,idtipodocumento,NroDocumento,Nombre,Apellido,direccion,Telefono,activo) values (?,?,?,?,?,?,?,1)");
    }
    
    public boolean EditarEmpleados(Empleados empleados){
        String id= (Integer.toString(empleados.getIdempleados())),idtipoemp=Integer.toString(empleados.getIdtipoempleado()), idtipodoc=Integer.toString(empleados.getIdtipodocumento());
        String datos[]={idtipoemp,idtipodoc,empleados.getNroDocumento(),empleados.getNombre(),empleados.getApellido(),empleados.getDireccion(),empleados.getTelefono(),id};
        return sql.editar(datos, "update empleados set idtipoempleado=?,idtipodocumento=?,NroDocumento=?,Nombre=?,Apellido=?,direccion=?,Telefono=? where idempleado=?");
    }
    
    public boolean EliminarEmpleados(Empleados empleados){
        sql.baja_dedatos("empleados", "idempleado", empleados.getIdempleados());
        return true;
    }
    
    public int ObtenerIDTipoEmpleado(String dato){
        return sql.ObtenerID("select idtipoempleado from tiposempleados where descripcion='"+dato+"'");
    }
    
    public int ObtenerIDTipoDocumento(String tipodoc){
        return sql.ObtenerID("select idtipodocumento from tiposdocumentos where descripcion='"+tipodoc+"'");
    }

}
