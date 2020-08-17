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
import Vistas.vCerrar_Caja;
import java.sql.Connection;
import java.sql.Timestamp;
import javax.swing.JTextField;
/**
 *
 * @author CRISTIAN
 */
public class control_Caja_Turno {
    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    //Caja_Turno ct;
    public control_Caja_Turno() {
        this.sql = new Sentencias_sql();
    }
    
    public Object[][] MostrarDatos(){
        String[] columnas={"idcaja","idtipomovimiento","idusuario","nota","monto","fecha_movimiento"};
        Object[][]datos=sql.GetTabla(columnas, "caja_turno", "select idcaja,id,idproveedor,descripcion,precio,stock,fecharegistro from insumos where activo=1");
        return datos;
    }
     
    public boolean InsertarCajaTurno(Caja_Turno cajaturno){ 
        String fecha = ((JTextField)vAbrir_Caja.jDateFecha.getDateEditor()).getText();
        String datos[]= {Integer.toString(cajaturno.getIdCaja()),
                Integer.toString(cajaturno.getIdTurno()),
                Integer.toString(cajaturno.getIdusuario()),               
                Float.toString(cajaturno.getMonto()),
                cajaturno.getEstado()                
                };                
        return sql.insertar(datos,"INSERT INTO caja_turno(idcaja,idturno,idusuario,fecha_apertura,fecha_cierre,monto,estado,activo,fecha_registro) "
                + "values (?,?,?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),?,?,1,NOW())");
    }
    
    public boolean EditarCajaTurno(Caja_Turno cajaturno){
        String fecha = ((JTextField)vCerrar_Caja.jDateFecha.getDateEditor()).getText();
        String idcajaturno= (Integer.toString(cajaturno.getIdcajaturno()));
        String datos[]= {Integer.toString(cajaturno.getIdusuario()),                               
                Float.toString(cajaturno.getMonto()),
                cajaturno.getEstado()
                };  
        return sql.insertar(datos,"UPDATE caja_turno SET idusuario=?,fecha_cierre=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),monto=?,estado=? where idcajaturno=" +idcajaturno);
    }
    
    public boolean ModificarCajaTurno(Caja_Turno cajaturno){
        String fecha = ((JTextField)vAbrir_Caja.jDateFecha.getDateEditor()).getText();
        String idcajaturno = Integer.toString(cajaturno.getIdcajaturno()), idusuario=Integer.toString(cajaturno.getIdusuario()),
        idturno = Integer.toString(cajaturno.getIdTurno()),monto=Float.toString(cajaturno.getMonto());
        String datos[]={idusuario,idturno,monto,idcajaturno};
        return sql.editar(datos,"UPDATE caja_turno SET idusuario=?,idturno=?,fecha_apertura=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i:%s'),monto=? where idcajaturno=?");
    }
    
    public boolean EliminarCajaTurno(Caja_Turno cajaturno){
        sql.baja_dedatos("caja_turno", "idcajaturno", cajaturno.getIdcajaturno());
        return true;
    }
    
    public Caja_Turno obtenerCajaTurno(int idcaja){
        String[] columnas={"idcajaturno","idcaja","idturno","idusuario","fecha_apertura","fecha_cierre","monto","estado","activo"};
        Object[][] datos=sql.GetTabla(columnas, "caja_turno", "SELECT idcajaturno,idcaja,idturno,idusuario,fecha_apertura,fecha_cierre,monto,estado,activo "
                + "FROM caja_turno WHERE activo=1 AND idcaja="+Integer.toString(idcaja));
        Caja_Turno caja_turno = new Caja_Turno();
        caja_turno.setIdcajaturno(Integer.valueOf((String)datos[0][0]));
        caja_turno.setIdCaja(Integer.valueOf((String)datos[0][1])); //Integer.parseInt(datos[0][0]        
        caja_turno.setIdTurno(Integer.valueOf((String)datos[0][2]));
        caja_turno.setIdusuario(Integer.valueOf((String)datos[0][3]));
        caja_turno.setFecha_apertura(Timestamp.valueOf((String)datos[0][4]));
        caja_turno.setFecha_cierre(Timestamp.valueOf((String)datos[0][5]));
        caja_turno.setMonto(Float.valueOf((String)datos[0][6]));
        caja_turno.setEstado((String)datos[0][7]);
        
        return caja_turno;
    }
    
    public boolean AbrirCajaTurno(Caja caja, int idturno){
        //agregamos primero la Caja chica
        String fecha = ((JTextField)vAbrir_Caja.jDateFecha.getDateEditor()).getText();
        Caja_Turno ct = new Caja_Turno();
        ct.setActivo(caja.getActivo());
        ct.setEstado(caja.getEstado());
        ct.setIdCaja(caja.getIdCaja());
        ct.setIdTurno(idturno);
        ct.setIdusuario(caja.getIdusuario());
        ct.setMonto(caja.getMonto());
        ct.setNota(caja.getNota());
        
        if(!InsertarCajaTurno(ct)){
            //insertamos el movimiento en la caja del Movimiento de Caja chica
            Movimientos_Caja mc = new Movimientos_Caja();
            mc.setIdcajaturno(Session.getIdcajaturno_abierta());
            mc.setIdtipomovimiento(1);
            mc.setIdusuario(Session.getIdusuario());
            mc.setFecha_movimiento(fecha);
            mc.setMonto(caja.getMonto());
            mc.setIdmovimiento(0);
            System.out.println(Session.getIdusuario());
            //control_mc.InsertarMovimientosCaja(mc);
            if(!control_mc.AgregarMovimientoCaja(mc)){
                return false;
            }
        }  
        return true;
    }
        
    
    public boolean CerrarCajaTurno(Caja caja, int idturno){
        //agregamos primero la Caja chica
        Caja_Turno ct = new Caja_Turno();
        int idcajaturno = sql.obtenerUltimoId("caja_turno","idcajaturno");
        Session.setIdcajaturno_abierta(idcajaturno);
        //System.out.println(Session.getIdcajaturno_abierta());
        ct.setIdcajaturno(Session.getIdcajaturno_abierta());
        ct.setEstado(caja.getEstado());
        ct.setIdTurno(idturno);
        ct.setIdusuario(caja.getIdusuario());
        ct.setMonto(caja.getMonto());
        ct.setEstado("CERRADA");
        ct.setNota(caja.getNota());
        if(!EditarCajaTurno(ct)){
            return false;
        }  
        return true;
    }
    
    public boolean AnularCajaTurno(Caja_Turno cajaturno){
        int idcajaturno = sql.obtenerUltimoId("caja_turno", "idcajaturno");
        String datos[] ={cajaturno.getEstado()};
        return sql.editar(datos, "UPDATE caja_turno SET estado=?,activo=0 where idcajaturno="+idcajaturno);
    }
    
    public Double getCierreCaja(Caja caja){        
        Caja_Turno ct = obtenerCajaTurno(caja.getIdCaja());        
        int idcajaturno = ct.getIdcajaturno();
        return sql.datos_totalfactura("monto", "SELECT round(mc.monto, 2 ) as monto "
             + "  FROM movimientos_caja AS mc "
                + "INNER JOIN tiposmovimientos AS tm ON tm.idtipomovimiento = mc.idtipomovimiento "
                + "WHERE mc.idtipomovimiento=3 AND mc.activo=1 "
                + "AND mc.idcajaturno='"+idcajaturno+"';");
       
    } 
    
    public String ObtenerTurno(){
        return sql.datos_string("descripcion", "select descripcion from turnos where idturno=(select idturno from caja_turno where idcajaturno = (select max(idcajaturno) from caja_turno))");
    }
    
}
