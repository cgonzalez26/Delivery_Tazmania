package Controlador;

import Modelo.Caja;
import Modelo.Compras;
import Modelo.DetallesCompras;
import Modelo.DetallesVentas;
import Vistas.vCompras_Insumos;
import Vistas.vGestion_PermisosPantallasPerfiles;
import Vistas.vGestion_Recetas;
import Vistas.vVentas_Productos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.lang.Boolean;
import java.util.List;

/**
 *
 * @author ANDRES
 */
public class Sentencias_sql {

    private Conexion con;
    PreparedStatement ps;
    ResultSet res;
    ResultSet res2;
    public String descripcion;

    public Sentencias_sql() {
        con = new Conexion();
    }

    public boolean insertar(String datos[], String insert) {
        boolean estado = false;
        try {
            ps = con.conectado().prepareStatement(insert);
            for (int i = 0; i <= datos.length - 1; i++) {
                /*if (datos[i].equals("0")) {
                    ps.setNull(i + 1, Types.INTEGER);
                } else {*/
                ps.setString(i + 1, datos[i]);

            }
            ps.execute();
            ps.close();
            estado = true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return estado;
    }

    public boolean InsertarRecetasLotes(String insert) {
        boolean estado = false;
        control_Recetas receta = new control_Recetas();
        int fila;
        try {
            for (fila = 0; fila < vGestion_Recetas.jTable1.getRowCount(); fila++) {
                ps = con.conectado().prepareStatement(insert);
                ps.setInt(1, receta.ObtenerIDProducto(vGestion_Recetas.jLabel2.getText()));
                ps.setInt(2, receta.ObtenerIDInsumo(vGestion_Recetas.jTable1.getValueAt(fila, 0).toString()));
                ps.addBatch();
                ps.executeBatch();
            }
            ps.close();
            return estado = true;
        } catch (SQLException e) {
            System.out.println(e);
            return estado = false;
        }
    }

    public boolean EditarDetalleCompraLotes(String valores) {
        boolean estado;
        control_DetallesCompras dc = new control_DetallesCompras();
        DetallesCompras d = new DetallesCompras();
        try {
            ps = con.conectado().prepareStatement(valores);
            ps.setInt(1, d.getIdcompra());
            ps.setInt(2, d.getIdinsumo());
            ps.setFloat(3, d.getPrecio());
            ps.setFloat(4, d.getCantidad());
            ps.setInt(5, d.getIddetallecompra());
            ps.addBatch();
            ps.executeBatch();

            ps.close();
            estado = true;
        } catch (SQLException e) {
            System.out.println(e);
            estado = false;
        }
        return estado;
    }

    public boolean ActualizarStockInsumos(String sql) {
        boolean estado = false;
        control_DetallesCompras dc = new control_DetallesCompras();
        try {
            for (int i = 0; i < vCompras_Insumos.jTable1.getRowCount(); i++) {
                ps = con.conectado().prepareStatement(sql);
                ps.setFloat(1, Float.parseFloat(vCompras_Insumos.jTable1.getValueAt(i, 2).toString()));
                ps.setInt(2, dc.ObtenerIDInsumo(vCompras_Insumos.jTable1.getValueAt(i, 0).toString()));
                ps.addBatch();
                ps.executeBatch();
            }
            ps.close();
            return estado = true;
        } catch (SQLException e) {
            System.out.println(e);
            return estado = false;
        }
    }

    public boolean DevolverStockRestadoCompra(String sql) {
        boolean estado = false;
        DetallesCompras dc = new DetallesCompras();
        try {
            ps = con.conectado().prepareStatement(sql);
            ps.setInt(1, dc.getIddetallecompra());
            ps.setInt(2, dc.getIdinsumo());
            ps.executeUpdate();
            ps.close();
            return estado = true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return estado = false;
        }
    }

    public boolean ActualizarStockVenta(Object datos[], String sql) {
        boolean estado = false;
        try {
            ps = con.conectado().prepareStatement(sql);
            for (int i = 0; i <= datos.length - 1; i++) {
                ps.setObject(i + 1, datos[i]);
            }
            ps.executeUpdate();
            ps.close();
            return estado = true;
        } catch (SQLException e) {
            System.out.println(e);
            return estado = false;
        }
    }

    public boolean DevolverStockRestadoVenta(String sql) {
        boolean estado = false;
        try {
            ps = con.conectado().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(vVentas_Productos.idprod));
            ps.setInt(2, Integer.parseInt(vVentas_Productos.idprod));
            ps.addBatch();
            ps.executeBatch();

            ps.close();
            return estado = true;
        } catch (SQLException e) {
            System.out.println(e);
            return estado = false;
        }
    }

    public Object[][] GetTabla(String colName[], String tabla, String sql) {
        int registros = 0;

        try {
            ps = con.conectado().prepareStatement("select count(*) as total from " + tabla);
            res = ps.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Object[][] data = new String[registros][colName.length];
        String col[] = new String[colName.length];

        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            int i = 0;
            while (res.next()) {
                for (int j = 0; j <= colName.length - 1; j++) {
                    col[j] = res.getString(colName[j]);
                    data[i][j] = col[j];
                }
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return data;
    }

    public List<Object[][]> DatosDetallesComprasVentas(String colName[], int reg, String sql) {
        List<Object[][]> lista = new ArrayList<>();
        Object[][] dato = new Object[reg][colName.length];
        Object datos[] = new Object[colName.length];
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            int s = 0;
            while (res.next()) {
                for (int i = 0; i < colName.length; i++) {
                    datos[i] = res.getString(colName[i]);
                    dato[s][i] = datos[i];
                    //dato[s][i] = datos[i];                   
                }
                s++;
                lista.add(dato);
            }
            ps.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<String> NrosFacturasCompras(String sql) {
        ArrayList<String> nroFacturas = new ArrayList<>();
        try {
            String nros = "";
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                nros = res.getString(5);
                nroFacturas.add(nros);
            }
            ps.close();
            res.close();
            return nroFacturas;
        } catch (SQLException e) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public Object[][] GetTabla2(String colName[], String tabla, String sql) {
        int registros = 0;
        try {
            ps = con.conectado().prepareStatement("select count(*) as total from " + tabla + " where activo =1");
            res = ps.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Object[][] data = new String[registros][colName.length];
        String col[] = new String[colName.length];
        try {
            ps = con.conectado().prepareStatement(sql);
            res2 = ps.executeQuery();
            int i = 0;
            while (res2.next()) {
                for (int j = 0; j <= colName.length - 1; j++) {
                    col[j] = res2.getString(colName[j]);
                    data[i][j] = col[j];
                }
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

    public Object[][] GetTabla3(String colName[], String tabla, String sql) {
        int registros = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            //res.next();
            while (res.next()) {
                registros++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Object[][] data = new String[registros][colName.length];
        String col[] = new String[colName.length];
        try {
            ps = con.conectado().prepareStatement(sql);
            res2 = ps.executeQuery();
            int i = 0;
            while (res2.next()) {
                for (int j = 0; j <= colName.length - 1; j++) {
                    col[j] = res2.getString(colName[j]);
                    data[i][j] = col[j];
                }
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

    public boolean existencias(String campo, String from_where) {
        int registros = 0;
        try {
            ps = con.conectado().prepareStatement("SELECT count(" + campo + ") as total  " + from_where);
            res = ps.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        if (registros > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String datos_string(String nombre_columna, String sentenciasql) {
        String datos = "";
        try {
            ps = con.conectado().prepareStatement(sentenciasql);
            res = ps.executeQuery();
            while (res.next()) {
                datos = res.getString(nombre_columna);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public String[] poblar_combox(String tabla, String nombrecol, String sql) {
        int registros = 0;
        try {
            ps = con.conectado().prepareStatement("SELECT count(*) as total FROM " + tabla);
            res = ps.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        String[] datos = new String[registros];
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            int i = 0;
            while (res.next()) {
                datos[i] = res.getString(nombrecol);
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public ArrayList<String> poblar_list_consulta(String nombrecol, String sql) {
        ArrayList<String> array = new ArrayList<>();
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                array.add(res.getString(nombrecol));
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return array;
    }

    public Object[] poblar_combox_con_consulta(String tabla, String idcol, String nombrecol, String sql) {
        int registros = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            //res.next();
            //registros = res.getRow();
            //registros = res.getInt("total"); 

            boolean ultimo = res.last();
            if (ultimo) {
                registros = res.getRow();
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        //System.out.println(registros);
        Object[] datos = new Object[registros];
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            int i = 0;
            while (res.next()) {
                //datos[i] = res.getObject(nombrecol);
                datos[i] = res.getObject(idcol) + "#" + res.getObject(nombrecol);
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int ObtenerCantidadRegistros(String sql) {
        int registros = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                registros = res.getInt(1);
            }
            ps.close();
            res.close();
            return registros;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Object[] poblar_combox_id(String tabla, String idcol, String nombrecol, String sql) {
        int registros = 0;
        try {
            ps = con.conectado().prepareStatement("SELECT count(*) as total FROM " + tabla);
            res = ps.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Object[] datos = new Object[registros];
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            int i = 0;
            while (res.next()) {
                //datos[i] = res.getObject(nombrecol);
                datos[i] = res.getObject(idcol) + "#" + res.getObject(nombrecol);
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public Double datos_totalfactura(String campo, String sql) {
        double data = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                data = res.getDouble(campo);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

    public boolean condicion_totalfac(String campo, String sql) {
        double data = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                data = res.getDouble(campo);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Controlador.Sentencias_sql.condicion_totalfac()");
            return false;
        }
    }

    public String VerificarDuplicadosNrosFacturas(String campo, String sql) {
        String nrofactura = "";
        try {
            ps = con.conectado().prepareCall(sql);
            res = ps.executeQuery();
            while (res.next()) {
                nrofactura = res.getString(campo);
            }
            ps.close();
            res.close();
            return nrofactura;
        } catch (SQLException e) {
            e.printStackTrace();
            return nrofactura = "";
        }
    }

    public boolean BajaMovCajaCompraVenta(String tabla, String idmov, Integer iddatomov, String idtipomov, Integer iddatotipomov) {
        String sql = "UPDATE " + tabla + " SET activo=0 WHERE " + idmov + "=" + iddatomov.toString() + " AND " + idtipomov + "=" + iddatotipomov.toString();
        try {
            ps = con.conectado().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean baja_dedatos(String tabla, String idcampo, Integer iddato) {
        String sql = "UPDATE " + tabla + " SET activo=0 WHERE " + idcampo + " = " + iddato.toString();
        try {
            ps = con.conectado().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean AnularEliminacion(String tabla, String idcampo, Integer iddato) {
        String sql = "update " + tabla + " set activo=1 where " + idcampo + "=" + iddato.toString();
        try {
            ps = con.conectado().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean Eliminar(String tabla, String idcampo, Integer iddato) {
        String sql = "delete from " + tabla + " where " + idcampo + " = " + iddato.toString();
        try {
            ps = con.conectado().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean editar(String datos[], String sql) {
        boolean estado = false;
        try {
            ps = con.conectado().prepareStatement(sql);
            for (int i = 0; i <= datos.length - 1; i++) {
                ps.setString(i + 1, datos[i]);
            }
            ps.executeUpdate();
            ps.close();
            estado = true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return estado;
    }

    public boolean ejecutarSql(String sql) {
        boolean estado = false;
        try {
            ps = con.conectado().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            estado = true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return estado;
    }

    public String Identificar(String sql, String columna) {
        String dato = "";
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                dato = res.getString(columna);
            }
            ps.close();
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return dato;
    }

    public boolean ConsultaReportes(String sql) {
        boolean estado = false;
        int cont = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                cont = cont + 1;
            }
            if (cont != 0) {
                estado = true;
            } else {
                estado = false;
            }
            ps.close();
            res.close();
            return estado;
        } catch (SQLException e) {
            System.out.println(e);
            return estado;
        }
    }

    public boolean InicioSesion(String campo1, String campo2) {
        String sql = "select idusuario,Login, Password from usuarios where Login='" + campo1 + "' and Password='" + campo2 + "'", login = "", pass = "";

        try {

            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            int idusuario = 0;
            while (res.next()) {
                idusuario = res.getInt("idusuario");
                login = res.getString("Login");
                pass = res.getString("Password");
            }
            ps.close();
            res.close();

            if (campo1.equals(login) && campo2.equals(pass)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public String FiltrarTipoUsuario(String sql) {
        String tipouser = "";
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                tipouser = res.getString("descripcion");
            }
            ps.close();
            res.close();
            return tipouser;
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, ex);
            return tipouser;
        }
    }

    public ArrayList<String> NombresPantallasHabilitadasTiposUsuarios(String sql) {
        ArrayList<String> nombres = new ArrayList<>();
        try {
            String nom = "";
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                nom = res.getString("nombre");
                nombres.add(nom);
            }
            ps.close();
            res.close();
            return nombres;
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int ConsultarStockNegativosN_MOD(String producto, float cantidad) {
        int cont = 0;
        try {
            float cantfinal = 0;
            String sql = "select i.stock - " + cantidad + " as cantFinal from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1";
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                cantfinal = res.getFloat("cantFinal");
                if (cantfinal < 0) {
                    cont++;
                }
            }
            ps.close();
            res.close();
            return cont;
        } catch (SQLException e) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int ConsultarStockCeroN_MOD(String producto, float cantidad) {
        int cont = 0;
        try {
            float cantfinal = 0;
            String sql = "select i.stock - " + cantidad + " as cantFinal from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1";
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                cantfinal = res.getFloat("cantFinal");
                if (cantfinal == 0) {
                    cont++;
                }
            }
            ps.close();
            res.close();
            return cont;
        } catch (SQLException e) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int ConsultarStockNegativosMOD(String producto, float numtabla, float numtexto) {
        int cont = 0;
        try {
            float stock_final_venta = 0;
            String sql = "select i.stock + " + numtabla + " - " + numtexto + " as stock_final_venta from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1";
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                stock_final_venta = res.getFloat("stock_final_venta");
                if (stock_final_venta < 0) {
                    cont++;
                }
            }
            ps.close();
            res.close();
            return cont;
        } catch (SQLException e) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int ConsultarStockCeroMOD(String producto, float numtabla, float numtexto) {
        int cont = 0;
        try {
            float stock_final_venta = 0;
            String sql = "select i.stock + " + numtabla + " - " + numtexto + " as stock_final_venta from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo INNER JOIN productos as p ON r.idproducto=p.idproducto where r.idproducto=(select idproducto from productos where descripcion='" + producto + "' and activo=1) and r.activo=1 and i.activo=1";
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                stock_final_venta = res.getFloat("stock_final_venta");
                if (stock_final_venta == 0) {
                    cont++;
                }
            }
            ps.close();
            res.close();
            return cont;
        } catch (SQLException e) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public boolean ConsultarStockInsumos(String sql) {
        boolean stock = false;
        try {
            float cantneg = 0;
            ps = con.conectado().prepareCall(sql);
            res = ps.executeQuery();
            while (res.next()) {
                cantneg = res.getFloat("stock");
                if (cantneg < 0) {
                    stock = true;
                }
            }
            ps.close();
            res.close();
            return stock;
        } catch (SQLException e) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
            return stock;
        }
    }

    public ArrayList<Double> MontosPorCajaTurno(String sql) {
        ArrayList<Double> montos = new ArrayList<>();
        try {
            double num = 0.0;
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                num = res.getDouble("monto");
                montos.add(num);
            }
            ps.close();
            res.close();
            return montos;
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public DefaultTableModel ConsultarInsumos() {
        String sql = "select idreceta,descripcion from recetas r INNER JOIN insumos i on r.idinsumo=i.idinsumo where idproducto=(select idproducto from productos where descripcion='" + descripcion + "' and activo=1) and r.activo=1 and i.activo=1", titulos[] = {"ID Receta", "INSUMOS AGREGADOS"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        String[] datos = new String[2];
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                datos[0] = res.getString("idreceta");
                datos[1] = res.getString("descripcion");
                modelo.addRow(datos);
            }
            ps.close();
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return modelo;
    }

    public void Permisos(int colnum, int colboolean, DefaultTableModel modelo, JTable tabla) {
        String sql = "select nombre from pantallas where activo=1";
        Object datos[] = new Object[colnum];
        try {
            ps = con.conectado().prepareCall(sql);
            res = ps.executeQuery();
            while (res.next()) {
                for (int i = 1; i <= colnum; i++) {
                    if (i == colboolean) {
                        //Object Boolean = true;                        
                        datos[colboolean - 1] = Boolean.FALSE;
                    } else {
                        datos[0] = res.getObject(i);
                    }
                }
                modelo.addRow(datos);
            }
            tabla.updateUI();
            res.close();
            ps.close();
        } catch (SQLException e) {
            //System.out.println(e);
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean SafeUpdates() {
        boolean estado = false;
        try {
            ps = con.conectado().prepareStatement("SET SQL_SAFE_UPDATES = 0");
            ps.executeUpdate();
            ps.close();
            return estado = true;
        } catch (SQLException e) {
            System.out.println(e);
            return estado = false;
        }
    }

    public int ObtenerID(String sql) {
        int id = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                id = res.getInt(1);
            }
            ps.close();
            res.close();
            return id;
        } catch (SQLException e) {
            Logger.getLogger(Sentencias_sql.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int obtenerUltimoId(String tabla, String columna) {
        String sql = "SELECT MAX(" + columna + ") AS id FROM " + tabla;
        int id = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                id = res.getInt("id");
            }
            ps.close();
            res.close();
            return id;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        }
    }

    public String obtenerEstadoUltimaCaja() {
        String estado = "CERRADA";
        try {
            ps = con.conectado().prepareStatement("SELECT estado FROM caja WHERE activo =1 ORDER BY idcaja DESC LIMIT 1");
            res = ps.executeQuery();
            res.next();
            estado = res.getString("estado");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return estado;
    }

    public int obtenerIdUltimaCaja() {
        int idcaja = 0;
        try {
            ps = con.conectado().prepareStatement("SELECT idcaja FROM caja WHERE activo =1 ORDER BY idcaja DESC LIMIT 1");
            res = ps.executeQuery();
            if (res.next()) {
                //while (res.next()) {
                idcaja = res.getInt("idcaja");
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        }
        //System.out.println(idcaja);
        return idcaja;
    }

    public int obtenerIdUltimaCajaTurno() {
        int idcajaturno = 0;
        try {
            ps = con.conectado().prepareStatement("SELECT idcajaturno FROM caja_turno WHERE activo =1 ORDER BY idcajaturno DESC LIMIT 1");
            res = ps.executeQuery();
            while (res.next()) {
                idcajaturno = res.getInt("idcajaturno");
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(idcajaturno);
        return idcajaturno;
    }

    public ArrayList<String> ObtenerDatosInsProd(String dato1, String dato2, String sql) {
        ArrayList<String> array = new ArrayList<>();
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            if (dato1.equals("precioventa")) {
                while (res.next()) {
                    array.add(res.getString(dato1));
                }
            } else {
                while (res.next()) {
                    array.add(res.getString(dato1));
                    array.add(res.getString(dato2));
                }
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return array;
    }

    public float getTotalSueldoTipoEmpleado(String campo, String sql) {
        float data = 0;
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                data = res.getFloat(campo);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

    public String generaCodigo(String objeto) {
        String tabla = "";
        String campo = "";
        String prefijo = "";
        String codigo = "";
        int id = 0;
        switch (objeto) {
            case "movimientocaja":
                tabla = "movimientos_caja";
                campo = "idmovimientocaja";
                prefijo = "MC_";
                break;
            case "egreso":
                tabla = "egresos";
                campo = "idegreso";
                prefijo = "EG_";
                break;
            case "venta":
                tabla = "ventas";
                campo = "idventa";
                prefijo = "VT_";
                break;
            case "compra":
                tabla = "compras";
                campo = "idcompra";
                prefijo = "CP_";
                break;
            case "pago_emp":
                tabla = "asistencias";
                campo = "idasistencia";
                prefijo = "PAGOEMP_";
                break;
            case "nropedido":
                tabla = "numerospedidosfactura";
                campo = "idnumeropedidofactura";
        }
        String sql = "SELECT " + campo + " FROM " + tabla + " ORDER BY " + campo + " DESC LIMIT 1";
        try {
            ps = con.conectado().prepareStatement(sql);
            res = ps.executeQuery();
            while (res.next()) {
                id = res.getInt(campo);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        if (objeto.equals("nropedido")) {
            codigo = String.format("%010d", id);
        } else {
            codigo = prefijo + String.format("%010d", id);
        }
        return codigo;
    }
}
