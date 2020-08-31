/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Caja;
import Modelo.Caja_Turno;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import Vistas.vAbrir_Caja;
import Vistas.vMovimientos_Caja;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author CRISTIAN
 */
public class control_Movimientos_Caja {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    Movimientos_Caja mc = new Movimientos_Caja();
    String todate = "STR_TO_DATE(", parse = ",'%d/%m/%Y %H:%i:%s')";

    public control_Movimientos_Caja() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(Date desde, Date hasta) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String desdeComoCadena = sdf.format(desde);
        String hastaComoCadena = sdf.format(hasta);
        //String[] columnas={"idmovimientocaja","Movimiento","FechaMovimiento","Monto","TipoMovimiento","Turno","NroMovimiento","detalle","idmovimiento"};
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento", "tipoVenta"};
        int idcajaturno = Session.getIdcajaturno_abierta();
        //Object[][] datos=sql.GetTabla(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,IFNULL(tm.descripcion,'') as Movimiento,DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %h:%i') FechaMovimiento,mc.monto AS Monto,tm.tipo AS TipoMovimiento,t.descripcion AS Turno,mc.NroMovimiento,IFNULL(mc.detalle,'') as detalle,mc.idmovimiento FROM  movimientos_caja as mc "
        Object[][] datos = sql.GetTabla3(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno,DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento,"
                + "CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso,"
                + "CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso,"
                + "IFNULL(mc.detalle,'') as detalle,mc.idmovimiento, mc.tipoVenta FROM  movimientos_caja as mc "
                + "LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento "
                + "LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno "
                + "LEFT JOIN turnos as t ON t.idturno=ct.idturno "
                + "WHERE mc.activo=1 "
                //+ "AND mc.idcajaturno="+Integer.toString(idcajaturno) 
                //SI ponemos la CajaTurno no muestra cuando la caja actual esta cerrada
                + " AND (date(mc.fecha_movimiento) >=date('" + desdeComoCadena + "') AND date(mc.fecha_movimiento)<=date('" + hastaComoCadena + "'))"
                + " ORDER BY mc.fecha_movimiento DESC");
        return datos;
    }

    public boolean InsertarMovimientosCaja(Movimientos_Caja movimientos_caja) {
        //System.out.print(movimientos_caja.getIdusuario());
        String datos[] = {Integer.toString(movimientos_caja.getIdtipomovimiento()),
            Integer.toString(movimientos_caja.getIdcajaturno()),
            Integer.toString(movimientos_caja.getIdusuario()),
            movimientos_caja.getNromovimiento(),
            movimientos_caja.getFecha_movimiento(),
            Float.toString(movimientos_caja.getMonto()),
            Integer.toString(movimientos_caja.getIdmovimiento()),
            movimientos_caja.getDescripcion(),
            movimientos_caja.getDetalle(),
            movimientos_caja.getTipoVenta()
        };
        return sql.insertar(datos, "INSERT INTO movimientos_caja (idtipomovimiento,idcajaturno,idusuario,NroMovimiento,fecha_movimiento,monto,idmovimiento,descripcion,detalle,fecha_registro,activo,tipoVenta) values (?,?,?,?," + todate + "?" + parse + ",?,?,?,?,NOW(),1,?)");
    }

    public boolean EditarMovimientosCaja(Movimientos_Caja movimientos_caja) {
        movimientos_caja.setIdcajaturno(Session.getIdcajaturno_abierta());
        movimientos_caja.setIdusuario(Session.getIdusuario());

        String datos[] = {Integer.toString(movimientos_caja.getIdtipomovimiento()),
            Integer.toString(movimientos_caja.getIdcajaturno()),
            Integer.toString(movimientos_caja.getIdusuario()),
            movimientos_caja.getFecha_movimiento(),
            Float.toString(movimientos_caja.getMonto()),
            Integer.toString(movimientos_caja.getIdmovimiento()),
            movimientos_caja.getDescripcion(),
            movimientos_caja.getDetalle(),
            movimientos_caja.getTipoVenta()
        };

        //System.out.println("update movimientos_caja set idtipomovimiento=?,idcajaturno=?,idusuario=?,fecha_movimiento=?,monto=?,idmovimiento=?,descripcion=?,detalle=? where idmovimientocaja="+Integer.toString(movimientos_caja.getIdmovimientocaja()));
        return sql.editar(datos, "update movimientos_caja set idtipomovimiento=?,idcajaturno=?,idusuario=?,fecha_movimiento=" + todate + "?" + parse + ",monto=?,idmovimiento=?,descripcion=?,detalle=?,tipoVenta=? where idmovimientocaja=" + Integer.toString(movimientos_caja.getIdmovimientocaja()));

    }

    public boolean ModificarMovimientoCaja(Movimientos_Caja movimientos_caja) {
        movimientos_caja.setIdcajaturno(Session.getIdcajaturno_abierta());
        movimientos_caja.setIdusuario(Session.getIdusuario());
        String fecha = ((JTextField) vAbrir_Caja.jDateFecha.getDateEditor()).getText();
        String idmovcaja = Integer.toString(movimientos_caja.getIdmovimientocaja()), idusuario = Integer.toString(movimientos_caja.getIdusuario());
        String datos[] = {idusuario, Float.toString(movimientos_caja.getMonto()), idmovcaja};
        return sql.editar(datos, "UPDATE movimientos_caja SET idusuario=?,fecha_movimiento=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),monto=? where idmovimientocaja=?");
    }

    public boolean EliminarMovCajaCompraVenta(Movimientos_Caja movimientos_caja) {
        sql.BajaMovCajaCompraVenta("movimientos_caja", "idmovimiento", movimientos_caja.getIdmovimiento(), "idtipomovimiento", movimientos_caja.getIdtipomovimiento());
        return true;
    }

    public boolean EliminarMovimientosCaja(Movimientos_Caja movimientos_caja) {
        //System.out.println("id="+movimientos_caja.getIdmovimientocaja());
        sql.baja_dedatos("movimientos_caja", "idmovimiento", movimientos_caja.getIdmovimiento());
        return true;
    }

    public boolean EliminarMovimientosCajaAbierta(Movimientos_Caja movimientos_Caja) {
        sql.baja_dedatos("movimientos_caja", "idmovimientocaja", movimientos_Caja.getIdmovimientocaja());
        return true;
    }

    public Object[] getTiposMovimientos(String tabla, String id, String campo, String tipo) {
        //System.out.println("SELECT "+id+","+campo+" FROM "+tabla+" WHERE tipo='"+tipo+"';");
        //return sql.poblar_combox_id(tabla,id,campo, "SELECT "+id+","+campo+" FROM "+tabla+" WHERE tipo='"+tipo+"';");
        return sql.poblar_combox_con_consulta(tabla, id, campo, "SELECT " + id + "," + campo + " FROM " + tabla + " WHERE tipo='" + tipo + "';");
    }

    public int getIdMovimientocaja(String NroMovimiento) {
        return sql.ObtenerID("select idmovimientocaja from movimientos_caja where NroMovimiento='" + NroMovimiento + "'");
    }

    public int ObtenerIDMovimientoCaja(int idmovimiento, int idtipomovimiento) {
        return sql.ObtenerID("select idmovimientocaja from movimientos_caja where idmovimiento=" + idmovimiento + " and idtipomovimiento=" + idtipomovimiento);
    }

    public boolean AgregarMovimientoCaja(Movimientos_Caja movimientos_Caja) {
        movimientos_Caja.setIdcajaturno(Session.getIdcajaturno_abierta());
        movimientos_Caja.setIdusuario(Session.getIdusuario());

        if (!InsertarMovimientosCaja(movimientos_Caja)) {
            return false;
        }
        int idultimo = sql.obtenerUltimoId("movimientos_caja", "idmovimientocaja");
        String codigo = sql.generaCodigo("movimientocaja");
        //System.out.println(codigo);
        sql.ejecutarSql("UPDATE movimientos_caja SET NroMovimiento ='" + codigo + "' WHERE idmovimientocaja=" + Integer.toString(idultimo));
        return true;
    }

    public Double getTotalVentas(int idcajaturno) {
        return sql.datos_totalfactura("total", "SELECT round( sum(monto), 2 ) as total "
                + "  FROM movimientos_caja WHERE idtipomovimiento=10 AND activo=1 AND idcajaturno='" + idcajaturno + "';");
    }

    public Double getTotalGastos(int idcajaturno) {
        return sql.datos_totalfactura("total", "SELECT round( sum(mc.monto) , 2 ) as total "
                + "  FROM movimientos_caja AS mc "
                + "INNER JOIN tiposmovimientos AS tm ON tm.idtipomovimiento = mc.idtipomovimiento "
                + "WHERE mc.idtipomovimiento<>11 AND mc.activo=1 AND tm.tipo='EGRESO' "
                + "AND mc.idcajaturno='" + idcajaturno + "';");
    }

    public Double getTotalPagosEmpleados(int idcajaturno) {
        return sql.datos_totalfactura("total", "SELECT round( sum(mc.monto) , 2 ) as total "
                + "  FROM movimientos_caja AS mc "
                + "INNER JOIN tiposmovimientos AS tm ON tm.idtipomovimiento = mc.idtipomovimiento "
                + "WHERE mc.idtipomovimiento=11 AND mc.activo=1 AND tm.tipo='EGRESO' "
                + "AND mc.idcajaturno='" + idcajaturno + "';");
    }

    public Double getCajaChica(int idcajaturno) {
        return sql.datos_totalfactura("total", "SELECT round( sum(mc.monto) , 2 ) as total "
                + "  FROM movimientos_caja AS mc "
                + "INNER JOIN tiposmovimientos AS tm ON tm.idtipomovimiento = mc.idtipomovimiento "
                + "WHERE mc.idtipomovimiento=1 AND mc.activo=1 AND tm.tipo='INGRESO' "
                + "AND mc.idcajaturno='" + idcajaturno + "';");
    }
    
    public Double getVentaLocal(int idcajaturno){
        return sql.datos_totalfactura("total", "select round(sum(mc.monto),2) as total from movimientos_caja mc inner join tiposmovimientos tm on tm.idtipomovimiento=mc.idtipomovimiento where mc.idtipomovimiento=10 and mc.activo=1 and tm.tipo='INGRESO' and mc.tipoVenta='Local' and mc.idcajaturno='" + idcajaturno + "';");
    }
    
    public Double getVentaOnline(int idcajaturno){
        return sql.datos_totalfactura("total", "select round(sum(mc.monto),2) as total from movimientos_caja mc inner join tiposmovimientos tm on tm.idtipomovimiento=mc.idtipomovimiento where mc.idtipomovimiento=10 and mc.activo=1 and tm.tipo='INGRESO' and mc.tipoVenta='Online' and mc.idcajaturno='" + idcajaturno + "';");
    }
    
    public Object[][] buscarMovimientos(String texto, String turno, Date desde, Date hasta) {
        //System.out.println(texto+"--"+turno+"--"+desde+"--"+hasta); 
        if (!turno.contains("Turno")) {
            turno = "-1";
        }
        int idcajaturno = Session.getIdcajaturno_abierta();
        //String[] columnas={"idmovimientocaja","Movimiento","FechaMovimiento","Monto","TipoMovimiento","Turno","NroMovimiento","detalle","idmovimiento"};
        String[] columnas = {"idmovimientocaja", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento"};
//        System.out.println("SELECT mc.idmovimiento,tm.descripcion as Movimiento,DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %h:%i') as 'Fecha Movimiento',mc.monto AS Monto,tm.tipo AS TipoMovimiento,t.descripcion AS Turno "
//                + "FROM movimientos_caja as mc "
//                + "LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento "
//                + "LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno "
//                + "LEFT JOIN turnos as t ON t.idturno=ct.idturno "
//                + "WHERE mc.activo=1 "
//                + "AND tm.descripcion like '%"+texto+"%' "
//                + "AND (t.descripcion ='"+turno+"' OR '"+turno +"' ='-1') "    
//                + "AND mc.idcajaturno="+Integer.toString(idcajaturno)
//                + "ORDER BY mc.fecha_movimiento DESC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String desdeComoCadena = sdf.format(desde);
        String hastaComoCadena = sdf.format(hasta);
        //Object[][]datos=sql.GetTabla(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,IFNULL(tm.descripcion,'') as Movimiento,DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %h:%i') as FechaMovimiento,mc.monto AS Monto,tm.tipo AS TipoMovimiento,t.descripcion AS Turno,mc.NroMovimiento,IFNULL(mc.detalle,'') as detalle,mc.idmovimiento "
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno,DATE_FORMAT(mc.fecha_movimiento, '%d-%m-%Y %H:%i') FechaMovimiento,"
                + "CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso,"
                + "CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso,"
                + "IFNULL(mc.detalle,'') as detalle,mc.idmovimiento "
                + "FROM movimientos_caja as mc "
                + "LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento "
                + "LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno "
                + "LEFT JOIN turnos as t ON t.idturno=ct.idturno "
                + "WHERE mc.activo=1 "
                //+ "AND mc.idcajaturno="+Integer.toString(idcajaturno)
                + " AND tm.descripcion like '%" + texto + "%' "
                + " AND (t.descripcion ='" + turno + "' OR '" + turno + "' ='-1') "
                + " AND (date(mc.fecha_movimiento) >=date('" + desdeComoCadena + "') AND date(mc.fecha_movimiento)<=date('" + hastaComoCadena + "'))"
                + " ORDER BY mc.fecha_movimiento DESC");
        return datos;
    }

    public String getEstadoCajaByMovimiento(int idmovimientocaja) {
        String estado = "CERRADA";
        String query = "SELECT c.estado as estado FROM caja AS c "
                + "INNER JOIN caja_turno AS ct ON ct.idcaja = c.idcaja "
                + "INNER JOIN movimientos_caja AS mc ON mc.idcajaturno = ct.idcajaturno WHERE c.activo =1 "
                + "AND ct.estado = 'ABIERTA' AND mc.idmovimientocaja = " + idmovimientocaja + " ORDER BY c.idcaja DESC LIMIT 1";
        String campo = "estado";
        String estado_caja = sql.datos_string(campo, query);
        if (!estado_caja.equals("")) {
            estado = estado_caja;
        }
        return estado;
    }

    public String IdentificarIngEg(String desc) {
        String dato = sql.Identificar("select tipo from tiposmovimientos where descripcion ='" + desc + "'", "tipo");
        return dato;
    }

    public String IdentificarTipoMov(String desc) {
        String dato = sql.Identificar("select descripcion from tiposmovimientos where descripcion='" + desc + "'", "descripcion");
        return dato;
    }

    public String IdentificarCierreCaja(String desc) {
        String dato = sql.Identificar("select descripcion from tiposmovimientos where descripcion='" + desc + "'", "descripcion");
        return dato;
    }

    public ArrayList<Double> MontosPorIDCaja(int idcajaturno) {
        return sql.MontosPorCajaTurno("select monto from movimientos_caja where idcajaturno=" + idcajaturno + " and activo=1");
    }

}
