package Controlador;

import java.sql.Connection;

/**
 *
 * @author Colo-PC
 */
public class control_BusquedasMovimientosCaja {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_BusquedasMovimientosCaja() {
        sql = new Sentencias_sql();
    }

    public Object[][] buscarMovimientosTodos(String texto, String turno, String desde, String hasta) {
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento", "tipoVenta"};
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno, DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento, CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso, CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso, IFNULL(mc.detalle,'') as detalle,mc.idmovimiento,mc.tipoVenta FROM movimientos_caja as mc LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno LEFT JOIN turnos as t ON t.idturno=ct.idturno WHERE mc.activo=1 AND tm.descripcion like '%" + texto + "%'  AND t.descripcion ='" + turno + "' and date(fecha_movimiento) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') AND str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') ORDER BY mc.fecha_movimiento DESC;");
        return datos;
    }

    public Object[][] buscarMovimientosFechasTurnos(String turno, String desde, String hasta) {
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento","tipoVenta"};
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno, DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento, CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso, CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso, IFNULL(mc.detalle,'') as detalle,mc.idmovimiento,mc.tipoVenta FROM movimientos_caja as mc LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno LEFT JOIN turnos as t ON t.idturno=ct.idturno WHERE mc.activo=1 AND t.descripcion ='" + turno + "' and date(fecha_movimiento) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') AND str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') ORDER BY mc.fecha_movimiento DESC;");
        return datos;
    }

    public Object[][] buscarMovimientosFechaMov(String texto, String desde, String hasta) {
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento","tipoVenta"};
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno, DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento, CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso, CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso, IFNULL(mc.detalle,'') as detalle,mc.idmovimiento,mc.tipoVenta FROM movimientos_caja as mc LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno LEFT JOIN turnos as t ON t.idturno=ct.idturno WHERE mc.activo=1 AND tm.descripcion like '%" + texto + "%' and date(fecha_movimiento) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') AND str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') ORDER BY mc.fecha_movimiento DESC;");
        return datos;
    }

    public Object[][] buscarMovimientosMovTurnos(String texto, String turno) {
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento","tipoVenta"};
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno, DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento, CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso, CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso, IFNULL(mc.detalle,'') as detalle,mc.idmovimiento,mc.tipoVenta FROM movimientos_caja as mc LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno LEFT JOIN turnos as t ON t.idturno=ct.idturno WHERE mc.activo=1 AND tm.descripcion like '%" + texto + "%'  AND t.descripcion ='" + turno + "' ORDER BY mc.fecha_movimiento DESC;");
        return datos;
    }

    public Object[][] buscarMovimientosFechas(String desde, String hasta) {
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento","tipoVenta"};
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno, DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento, CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso, CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso, IFNULL(mc.detalle,'') as detalle,mc.idmovimiento,mc.tipoVenta FROM movimientos_caja as mc LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno LEFT JOIN turnos as t ON t.idturno=ct.idturno WHERE mc.activo=1 and date(fecha_movimiento) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') AND str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') ORDER BY mc.fecha_movimiento DESC;");
        return datos;
    }

    public Object[][] buscarMovimientosMov(String texto) {
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento","tipoVenta"};
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno, DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento, CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso, CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso, IFNULL(mc.detalle,'') as detalle,mc.idmovimiento,mc.tipoVenta FROM movimientos_caja as mc LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno LEFT JOIN turnos as t ON t.idturno=ct.idturno WHERE mc.activo=1 AND tm.descripcion like '%" + texto + "%' ORDER BY mc.fecha_movimiento DESC;");
        return datos;
    }

    public Object[][] buscarMovimientosTurnos(String turno) {
        String[] columnas = {"idmovimientocaja", "idcajaturno", "NroMovimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento","tipoVenta"};
        Object[][] datos = sql.GetTabla2(columnas, "movimientos_caja", "SELECT mc.idmovimientocaja,ct.idcajaturno,mc.NroMovimiento,IFNULL(mc.descripcion,'') as Concepto,IFNULL(tm.descripcion,'') as TipoMovimiento,t.descripcion AS Turno, DATE_FORMAT(mc.fecha_movimiento, '%d/%m/%Y %H:%i') FechaMovimiento, CASE WHEN tm.tipo='INGRESO' THEN mc.monto ELSE '-' END AS Ingreso, CASE WHEN tm.tipo='EGRESO' THEN mc.monto ELSE '-' END AS Egreso, IFNULL(mc.detalle,'') as detalle,mc.idmovimiento,mc.tipoVenta FROM movimientos_caja as mc LEFT JOIN tiposmovimientos as tm ON tm.idtipomovimiento=mc.idtipomovimiento LEFT JOIN caja_turno as ct ON ct.idcajaturno=mc.idcajaturno LEFT JOIN turnos as t ON t.idturno=ct.idturno WHERE mc.activo=1 AND t.descripcion ='" + turno + "' ORDER BY mc.fecha_movimiento DESC;");
        return datos;
    }

}
