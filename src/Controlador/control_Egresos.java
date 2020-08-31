/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Egresos;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import Vistas.vGestion_Egresos;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_Egresos {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    Movimientos_Caja mc = new Movimientos_Caja();

    //String prov,ti;
    public control_Egresos() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String desde, String hasta) {
        String[] columnas = {"idegreso", "NroEgreso", "egreso", "tipoegreso", "fecha_egreso", "monto", "idtipoegreso", "detalle"};
        Object[][] datos = sql.GetTabla2(columnas, "egresos", "select e.idegreso,e.NroEgreso as NroEgreso,e.descripcion as egreso,tm.descripcion as tipoegreso,DATE_FORMAT(e.fecha_egreso, '%d/%m/%Y %H:%i') as fecha_egreso,e.monto,e.idtipoegreso,e.detalle "
                + " from egresos as e"
                + " left join tiposmovimientos as tm on tm.idtipomovimiento = e.idtipoegreso where date(e.fecha_egreso) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and e.activo=1");
        return datos;
    }

    public Object[][] buscarEgreso(String desde, String hasta) {
        String[] columnas = {"idegreso", "NroEgreso", "egreso", "tipoegreso", "fecha_egreso", "monto", "idtipoegreso", "detalle"};
        Object[][] datos = sql.GetTabla2(columnas, "egresos", "select e.idegreso,e.NroEgreso as NroEgreso,e.descripcion as egreso,tm.descripcion as tipoegreso,DATE_FORMAT(e.fecha_egreso, '%d/%m/%Y %H:%i') as fecha_egreso,e.monto,e.idtipoegreso,e.detalle "
                + " from egresos as e"
                + " left join tiposmovimientos as tm on tm.idtipomovimiento = e.idtipoegreso where between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and e.activo=1");
        return datos;
    }

    public boolean InsertarEgresos(Egresos egreso) {
        //(Integer.toString(insumos.getIdtipoinsumo())),idproveedor= (Integer.toString(insumos.getIdproveedor()))insumos.getFecha_registro().toString() 
        //Date fecha = egreso.getFecha();
        String fecha = ((JTextField) vGestion_Egresos.jDateFecha_Egresos.getDateEditor().getUiComponent()).getText();
        String datos[] = {Integer.toString(egreso.getIdtipoegreso()), egreso.getDescripcion(), egreso.getDetalle(), Float.toString(egreso.getMonto())};
        return sql.insertar(datos, "insert into egresos (idtipoegreso,fecha_egreso,descripcion,detalle,monto,fecha_registro,activo) values (?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),?,?,?,NOW(),1)");
    }

    public boolean EditarEgresos(Egresos egreso) {
        String fecha = ((JTextField) vGestion_Egresos.jDateFecha_Egresos.getDateEditor().getUiComponent()).getText();
        String datos[] = {Integer.toString(egreso.getIdtipoegreso()),
            egreso.getDescripcion(),
            egreso.getDetalle(),
            Float.toString(egreso.getMonto()),
            Integer.toString(egreso.getIdegreso())};
        return sql.editar(datos, "update egresos set idtipoegreso=?,fecha_egreso=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),descripcion=?,detalle=?,monto=? where idegreso=?");
    }

    public boolean EliminarEgresos(Egresos egreso) {
        sql.baja_dedatos("egresos", "idegreso", egreso.getIdegreso());
        return true;
    }

    public boolean DesactivarEgresos(Egresos egreso) {
        if (EliminarEgresos(egreso)) {
            /*int idmovimientocaja = control_mc.getIdMovimientocaja(egreso.getIdegreso());
            //quitar el Movimiento de caja asociado al Egreso
            if(idmovimientocaja!= 0){
                mc.setIdmovimientocaja(idmovimientocaja);
                //System.out.println("entro eliminar caja");
                control_mc.EliminarMovimientosCaja(mc);*/
            return true;
        }
        return false;
    }

    public boolean ModificarEgresos(Egresos egreso) {
        if (!EditarEgresos(egreso)) {
            return false;
        }
        String fecha = ((JTextField) vGestion_Egresos.jDateFecha_Egresos.getDateEditor().getUiComponent()).getText();
        //control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
        //Movimientos_Caja mc1 = new Movimientos_Caja();              
        //mc.setIdcajaturno(Session.getIdcajaturno_abierta());
        mc.setIdmovimientocaja(egreso.getIdmovimientocaja());
        mc.setDescripcion(egreso.getDescripcion());
        mc.setIdtipomovimiento(egreso.getIdtipoegreso());
        mc.setIdusuario(Session.getIdusuario());
        mc.setFecha_movimiento(fecha);
        mc.setMonto(egreso.getMonto());
        mc.setDetalle(egreso.getDetalle());
        mc.setIdmovimiento(egreso.getIdegreso());
        //System.out.println(Session.getIdusuario());
        control_mc.EditarMovimientosCaja(mc);
        return true;
    }

    public boolean AgregarEgresos(Egresos egreso) {
        if (!InsertarEgresos(egreso)) {
            return false;
        }
        String fecha = ((JTextField) vGestion_Egresos.jDateFecha_Egresos.getDateEditor().getUiComponent()).getText();
        int idmovimiento = sql.obtenerUltimoId("egresos", "idegreso");
        String codigo = sql.generaCodigo("egreso");
        sql.ejecutarSql("UPDATE egresos SET NroEgreso ='" + codigo + "' WHERE idegreso=" + Integer.toString(idmovimiento));
        egreso.setNroegreso(codigo);
        //control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
        //Movimientos_Caja mc = new Movimientos_Caja();  
        mc.setDescripcion(egreso.getDescripcion());
        mc.setIdcajaturno(Session.getIdcajaturno_abierta());
        mc.setIdtipomovimiento(egreso.getIdtipoegreso());
        mc.setIdusuario(Session.getIdusuario());
        mc.setNromovimiento(codigo);
        mc.setFecha_movimiento(fecha);
        mc.setMonto(egreso.getMonto());
        mc.setIdmovimiento(idmovimiento);
        mc.setDetalle(egreso.getDetalle());
        mc.setActivo(1);
        //System.out.println(Session.getIdusuario());
        //control_mc.AgregarMovimientoCaja(mc); //no me sirve porque actualiza el Codigo del mov de caja
        control_mc.InsertarMovimientosCaja(mc);
        return true;
    }

    public int ObtenerIDEgreso(String dato) {
        return sql.ObtenerID("select idegreso from egresos where NroEgreso='" + dato + "'");
    }

}
