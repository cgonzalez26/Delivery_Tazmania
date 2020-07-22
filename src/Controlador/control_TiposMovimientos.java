/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.TiposGastos;
import Modelo.TiposMovimientos;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author CRISTIAN
 */
public class control_TiposMovimientos {

    private Sentencias_sql sql;
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    private List<TiposMovimientos> tipos_movimientos;

    public control_TiposMovimientos() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos() {
        String[] columnas = {"idtipomovimiento", "descripcion", "tipo"};
        Object[][] datos = sql.GetTabla(columnas, "tiposmovimientos", "select idtipomovimiento,descripcion,tipo from tiposmovimientos where activo=1");
        return datos;
    }

    public Object[][] MostrarTipoMovBuscado(String tipomov) {
        String[] columnas = {"idtipomovimiento", "descripcion", "tipo"};
        Object[][] datos = sql.GetTabla(columnas, "tiposmovimientos", "select idtipomovimiento,descripcion,tipo from tiposmovimientos where descripcion like '%" + tipomov + "%' and activo=1");
        return datos;
    }

    public boolean InsertarTiposMovimientos(TiposMovimientos tipo_movimiento) {
        String datos[] = {tipo_movimiento.getDescripcion(), tipo_movimiento.getTipo()};
        return sql.insertar(datos, "insert into tiposmovimientos (descripcion,tipo,activo) values (?,?,1)");
    }

    public boolean EditarTiposMovimientos(TiposMovimientos tipo_movimiento) {
        String id = (Integer.toString(tipo_movimiento.getIdtipomovimiento()));
        String datos[] = {tipo_movimiento.getDescripcion(),
            tipo_movimiento.getTipo(),
            id};
        return sql.editar(datos, "update tiposmovimientos set descripcion=?,tipo=? where idtipomovimiento=?");
    }

    public boolean EliminarTiposMovimientos(TiposMovimientos tipo_movimiento) {
        sql.baja_dedatos("tiposmovimientos", "idtipomovimiento", tipo_movimiento.getIdtipomovimiento());
        return true;
    }

    public TiposMovimientos getTipoMovimientoByDescripcion(List<TiposMovimientos> tipos, String descripcion) {

        Iterator<TiposMovimientos> it = tipos.iterator();
        while (it.hasNext()) {
            TiposMovimientos tg = it.next();
            if (tg.getDescripcion().equals(descripcion)) {
                return tg;
            }
        }
        return null;
    }
}
