package Controlador;

import Modelo.Caja;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import Vistas.vAbrir_Caja;
import Vistas.vCerrar_Caja;
import java.sql.Connection;
import java.sql.Timestamp;
import javax.swing.JTextField;

/**
 *
 * @author CRISTIAN
 */
public class control_Cajas {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    //control_Movimientos_Caja control_mc = new control_Movimientos_Caja();

    public control_Cajas() {
        this.sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos() {
        String[] columnas = {"idcaja", "idtipomovimiento", "idusuario", "nota", "monto", "fecha_movimiento"};
        Object[][] datos = sql.GetTabla(columnas, "caja", "select idcaja,id,idproveedor,descripcion,precio,stock,fecharegistro from insumos where activo=1");
        return datos;
    }

    public boolean InsertarCaja(Caja caja) {
        String fecha =((JTextField)vAbrir_Caja.jDateChooser1.getDateEditor().getUiComponent()).getText();
        String datos[] = {Integer.toString(caja.getIdusuario()),
            Float.toString(caja.getMonto()),
            caja.getEstado()
        };
        return sql.insertar(datos, "INSERT INTO caja (idusuario,fecha_apertura,fecha_cierre,monto,estado,activo,fecha_registro) "
                + "values (?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),NOW(),?,?,1,NOW())");
    }

    public boolean EditarCaja(Caja caja) {
        String fecha = ((JTextField)vCerrar_Caja.jDateChooser1.getDateEditor().getUiComponent()).getText();
        String idcaja = (Integer.toString(caja.getIdCaja()));
        String datos[] = {Integer.toString(caja.getIdusuario()),
            Float.toString(caja.getMonto()),
            caja.getEstado()
        };
        return sql.insertar(datos, "UPDATE caja SET idusuario=?,fecha_cierre=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),monto=?,estado=? where idcaja=" + idcaja);
    }
    
    public boolean ModificarCaja(Caja caja){
        String fecha =((JTextField)vAbrir_Caja.jDateChooser1.getDateEditor().getUiComponent()).getText();
        String idcaja = (Integer.toString(caja.getIdCaja())), idusuario = (Integer.toString(caja.getIdusuario())),
        monto =Float.toString(caja.getMonto());        
        String datos[] = {idusuario,monto,idcaja};
        return sql.editar(datos, "UPDATE caja SET idusuario=?,fecha_apertura=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),monto=? where idcaja=?");
    }

    public boolean AnularCaja(Caja caja) {
        //String idcaja= (Integer.toString(caja.getIdCaja()));
        int idcaja = sql.obtenerUltimoId("caja", "idcaja");
        String datos[] = {caja.getEstado()};
        return sql.editar(datos, "UPDATE caja SET estado=?,activo=0 where idcaja=" + idcaja);
    }

    public boolean EliminarCaja(Caja caja) {
        sql.baja_dedatos("caja", "idcaja", caja.getIdCaja());
        return true;
    }

    public boolean AbrirCaja(Caja caja, int idturno) {
        //agregamos primero la Caja chica
        String fecha =((JTextField)vAbrir_Caja.jDateChooser1.getDateEditor().getUiComponent()).getText();
        if (InsertarCaja(caja)) {
            int idcaja = sql.obtenerUltimoId("caja", "idcaja");
            caja.setIdCaja(idcaja);
            //agregamos la caja del turno
            control_Caja_Turno cct = new control_Caja_Turno();
            control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
            if (!cct.AbrirCajaTurno(caja, idturno)) {
                return false;
            } else {
                //guardamos los valores de la Caja abierta en Sesion
                int idcajaturno = sql.obtenerUltimoId("caja_turno", "idcajaturno");
                Session.setIdcaja_abierta(idcaja);
                Session.setIdturno_abierto(idturno);
                Session.setIdcajaturno_abierta(idcajaturno);

                //guardamos el movimiento en Caja
                Movimientos_Caja mc = new Movimientos_Caja();
                //mc.setActivo(1);
                mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                mc.setIdtipomovimiento(1);
                mc.setIdusuario(Session.getIdusuario());
                mc.setFecha_movimiento(fecha);
                mc.setDescripcion("APERTURA DE CAJA");
                mc.setMonto(caja.getMonto());
                //control_mc.InsertarMovimientosCaja(mc);
                control_mc.AgregarMovimientoCaja(mc);
            }
        }
        return true;
    }

    public boolean CerrarCaja(Caja caja, int idturno) {
        String fecha = ((JTextField)vCerrar_Caja.jDateChooser1.getDateEditor().getUiComponent()).getText();
        //agregamos primero la Caja chica
        if (EditarCaja(caja)) {
            //agregamos la caja del turno
            control_Caja_Turno cct = new control_Caja_Turno();
            control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
            if (!cct.CerrarCajaTurno(caja, idturno)) {
                return false;
            } else {
                //insetar movimiento de caja
                Movimientos_Caja mc = new Movimientos_Caja();
                mc.setDescripcion("CIERRE DE CAJA");
                mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                mc.setIdtipomovimiento(3);
                mc.setIdusuario(Session.getIdusuario());
                mc.setFecha_movimiento(fecha);
                mc.setMonto(caja.getMonto());
                //control_mc.InsertarMovimientosCaja(mc);
                control_mc.AgregarMovimientoCaja(mc); //este genera Codigo de Movimiento
            }
        }
        return true;
    }

    public boolean existeCajaAbierta() {
        boolean estado = false;
        String estado_caja = sql.obtenerEstadoUltimaCaja();
        if (estado_caja.equals("ABIERTA")) {
            estado = true;
        } else if (estado_caja.equals("CERRADA")) {
            estado = false;
        } else if (estado_caja.equals("ANULADA")) {
            estado = false;
        }
        return estado;
    }

    public Caja obtenerUltimaCaja() {
        String[] columnas = {"idcaja", "idusuario", "fecha_apertura", "fecha_cierre", "monto", "estado"};
        Object[][] datos = sql.GetTabla(columnas, "caja", "SELECT c.idcaja,c.idusuario,c.fecha_apertura,c.fecha_cierre,c.monto,c.estado FROM caja AS c "
                + "WHERE c.activo=1 "
                + "ORDER BY c.idcaja DESC LIMIT 1");
        Caja caja = new Caja();
        caja.setIdCaja(Integer.valueOf((String) datos[0][0])); //Integer.parseInt(datos[0][0]
        caja.setIdusuario(Integer.valueOf((String) datos[0][1]));
        caja.setFecha_apertura(Timestamp.valueOf((String) datos[0][2]));
        caja.setFecha_cierre(Timestamp.valueOf((String) datos[0][3]));
        caja.setMonto(Float.valueOf((String) datos[0][4]));
        caja.setEstado((String) datos[0][5]);

        return caja;
    }

    public Caja obtenerCaja(int idcaja) {
        String[] columnas = {"idcaja", "idusuario", "fecha_apertura", "fecha_cierre", "monto", "estado", "activo"};
        Object[][] datos = sql.GetTabla(columnas, "caja", "SELECT idcaja,idusuario,fecha_apertura,fecha_cierre,monto,estado,activo "
                + "FROM caja WHERE activo=1 AND idcaja=" + Integer.toString(idcaja));
        Caja caja = new Caja();
        caja.setIdCaja(Integer.valueOf((String) datos[0][0])); //Integer.parseInt(datos[0][0]
        caja.setIdusuario(Integer.valueOf((String) datos[0][1]));
        caja.setFecha_apertura(Timestamp.valueOf((String) datos[0][2]));
        caja.setFecha_cierre(Timestamp.valueOf((String) datos[0][3]));
        caja.setMonto(Float.valueOf((String) datos[0][4]));
        caja.setEstado((String) datos[0][5]);

        return caja;
    }

    public Caja obtenerCajaAbierta() {
        Caja caja = obtenerUltimaCaja();
        if (caja.getEstado() == "ABIERTA") {
            return caja;
        }
        return null;
    }
    
}
