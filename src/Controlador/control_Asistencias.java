package Controlador;

import Modelo.Asistencias;
import Vistas.vGestion_Asistencias;
import java.sql.Connection;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_Asistencias {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();

    public control_Asistencias() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos() {
        String[] columnas = {"idasistencia", "idempleado", "NroAsistencia", "Nombre", "descripcion", "Sueldo", "fecha_asistencia"};
        String desde = ((JTextField) vGestion_Asistencias.jDateChooser2.getDateEditor().getUiComponent()).getText(), hasta = ((JTextField) vGestion_Asistencias.jDateChooser3.getDateEditor().getUiComponent()).getText();
        Object[][] datos = sql.GetTabla(columnas, "asistencias", "select a.idasistencia,e.idempleado,a.NroAsistencia,e.Nombre,a.descripcion,a.sueldo,date_format(a.fecha_asistencia,'%d/%m/%Y %H:%i') as fecha_asistencia from asistencias a INNER JOIN empleados e on a.idempleado=e.idempleado where date(a.fecha_asistencia) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and a.activo=1");
        return datos;
    }

    public Object[][] MostrarDatosFechas(String desde, String hasta) {
        String[] columnas = {"idasistencia", "idempleado", "NroAsistencia", "Nombre", "descripcion", "Sueldo", "fecha_asistencia"};
        Object[][] datos = sql.GetTabla(columnas, "asistencias", "select a.idasistencia,e.idempleado,a.NroAsistencia,e.Nombre,a.descripcion,a.sueldo,date_format(a.fecha_asistencia,'%d/%m/%Y %H:%i') as fecha_asistencia from asistencias a INNER JOIN empleados e on a.idempleado=e.idempleado where date(a.fecha_asistencia) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and a.activo=1");
        return datos;
    }

    public Object[][] MostrarEmpleados() {
        String[] columnas = {"Nombre", "descripcion"};
        Object[][] datos = sql.GetTabla(columnas, "empleados", "select e.Nombre, t.descripcion from empleados e LEFT JOIN tiposempleados t on e.idtipoempleado=t.idtipoempleado where e.activo=1");
        return datos;
    }

    public Object[][] MostrarEmpleadoBuscado(String emp) {
        String[] columnas = {"Nombre", "descripcion"};
        Object[][] datos = sql.GetTabla(columnas, "empleados", "select e.Nombre, t.descripcion from empleados e LEFT JOIN tiposempleados t on e.idtipoempleado=t.idtipoempleado where e.Nombre like '%" + emp + "%' and e.activo=1");
        return datos;
    }

    public boolean InsertarAsistencias(Asistencias asistencias) {
        String idemp = (Integer.toString(asistencias.getIdempleado())), fecha = ((JTextField) vGestion_Asistencias.jDateChooser1.getDateEditor().getUiComponent()).getText(), sueldo = Float.toString(asistencias.getSueldo());
        String datos[] = {idemp, asistencias.getDescripcion(), sueldo};
        return sql.insertar(datos, "insert into asistencias (idempleado,fecha_asistencia,descripcion,activo,sueldo) values (?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),?,1,?)");
    }

    public boolean EditarAsistencias(Asistencias asistencias) {
        String id = (Integer.toString(asistencias.getIdasistencia())), fecha = ((JTextField) vGestion_Asistencias.jDateChooser1.getDateEditor().getUiComponent()).getText(), idemp = (Integer.toString(asistencias.getIdempleado())), sueldo = Float.toString(asistencias.getSueldo());
        String datos[] = {idemp, asistencias.getDescripcion(), sueldo, id};
        return sql.editar(datos, "update asistencias set idempleado=?,fecha_asistencia=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),descripcion=?,sueldo=? where idasistencia=?");
    }

    public boolean EliminarAsistencias(Asistencias asistencias) {
        sql.baja_dedatos("asistencias", "idasistencia", asistencias.getIdasistencia());
        return true;
    }

    public int ObtenerIDEmpleado(String dato) {
        return sql.ObtenerID("select idempleado from empleados where Nombre='" + dato + "'");
    }

}
