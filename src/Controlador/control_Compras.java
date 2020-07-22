package Controlador;

import Modelo.Compras;
import Vistas.vCompras_Insumos;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Colo-PC
 */
public class control_Compras {

    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();

    public control_Compras() {
        sql = new Sentencias_sql();
    }

    public Object[][] MostrarDatos(String desde, String hasta) {
        String[] columnas = {"idcompra", "idproveedor", "idusuario", "Nombre_comercial", "NroCompra", "Login", "MontoTotal", "FechaCompra"};
        Object[][] datos = sql.GetTabla(columnas, "compras", "select c.idcompra,p.idproveedor,u.idusuario,p.Nombre_comercial,c.NroCompra,u.Login,c.MontoTotal,date_format(c.FechaCompra,'%d/%m/%Y %H:%i') as FechaCompra from proveedores p INNER JOIN compras c on p.idproveedor=c.idproveedor INNER JOIN usuarios u on u.idusuario=c.idusuario where date(c.FechaCompra) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and c.activo=1 order by FechaCompra asc;");
        return datos;
    }

    public Object[][] MostrarProveedores() {
        String[] columnas = {"Nombre_comercial"};
        Object[][] datos = sql.GetTabla(columnas, "proveedores", "select Nombre_comercial from proveedores where activo=1");
        return datos;
    }

    public Object[][] MostrarProveedorBuscado(String prov) {
        String[] columnas = {"Nombre_comercial"};
        Object[][] datos = sql.GetTabla(columnas, "proveedores", "select Nombre_comercial from proveedores where Nombre_comercial like '%" + prov + "%' and activo=1");
        return datos;
    }

    public boolean EfectuarCompra(Compras compra) {
        String monto = (Float.toString(compra.getMontototal())), fecha = ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).getText(), idprov = Integer.toString(compra.getIdproveedor()), iduser = Integer.toString(compra.getIdusuario());
        String datos[] = {idprov, iduser, monto};
        return sql.insertar(datos, "insert into compras (idproveedor,idusuario,FechaCompra,MontoTotal,activo) values (?,?,STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),?,1)");
    }

    public boolean EditarCompra(Compras compra) {
        String monto = (Float.toString(compra.getMontototal())), id = (Integer.toString(compra.getIdcompra())), fecha = ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).getText(), idprov = Integer.toString(compra.getIdproveedor()), iduser = Integer.toString(compra.getIdusuario());
        String datos[] = {idprov, iduser, monto, id};
        return sql.editar(datos, "update compras set idproveedor=?,idusuario=?,FechaCompra=STR_TO_DATE('" + fecha + "','%d/%m/%Y %H:%i'),MontoTotal=? where idcompra=?");
    }

    public boolean ActualizarTotalCompra(Compras compra) {
        String total = (Float.toString(compra.getMontototal())), id = (Integer.toString(compra.getIdcompra()));
        String datos[] = {total, id};
        return sql.editar(datos, "update compras set MontoTotal=? where idcompra=?");
    }

    public boolean EliminarCompra(Compras compra) {
        return sql.baja_dedatos("compras", "idcompra", compra.getIdcompra());
    }

    public ArrayList<String> ObtenerDatosNumInsumos(String texto) {
        return sql.ObtenerDatosInsProd("precio","stock","select precio,stock from insumos where descripcion='" + texto + "' and activo=1");
    }

    public int ObtenerUltimoIDCompra() {
        return sql.ObtenerID("select max(idcompra) from compras");
    }

    public int ObtenerIDProveedor(String dato) {
        return sql.ObtenerID("select idproveedor from proveedores where Nombre_comercial='" + dato + "'");
    }

    public int ObtenerIDUsuario(String dato) {
        return sql.ObtenerID("select idusuario from usuarios where Login='" + dato + "'");
    }

    public int ObtenerIDMovCajaCompra(int id, String tipomovnro) {
        return sql.ObtenerID("select idmovimientocaja from movimientos_caja where idmovimiento=" + id + " and NroMovimiento like '%" + tipomovnro + "%'");
    }

    public String VerificarNroFactra(String dato) {
        return sql.VerificarDuplicadosNrosFacturas("NroCompra", "select NroCompra from compras where NroCompra='" + dato + "' where activo=1");
    }

    /*  public int CantidadInsumo() {
        String nominsumo = vCompras_Insumos.jTextField4.getText();
        sql1 = "SELECT COUNT(NOMBRE_INSUMO) AS CANTIDAD_INSUMOS FROM INSUMOS WHERE NOMBRE_INSUMO='" + nominsumo + "'";

        try {
            int insumo = 0;

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                insumo = rs.getInt("CANTIDAD_INSUMOS");
            }

            pst1.close();
            rs.close();

            return insumo;
        } catch (SQLException e) {
            Logger.getLogger(vCompras_Insumos.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }*/
 /*public boolean EfectuarCompra(DetallesCompras modelo1) {
        String proveedor = (String) vCompras_Insumos.jComboBox1.getSelectedItem();
        String usuario = vCompras_Insumos.jTextField2.getText().trim();
        int cantidad = Integer.parseInt(vCompras_Insumos.jTextField7.getText());
        String insumo = vCompras_Insumos.jTextField4.getText();
        String tipo = vCompras_Insumos.jTextField5.getText();

        if (CantidadInsumo() == 0 && CantidadTipos() == 0) {

            sql1 = "INSERT INTO COMPRAS (FECHA_COMPRA, PROVEEDOR_COMPRA, USUARIO_COMPRA, MONTOTOTAL_COMPRA, ID_PROVEEDOR, ID_USUARIO)"
                    + "VALUES (?,?,?,?,(SELECT ID_PROVEEDOR FROM PROVEEDORES WHERE NOMCOMERCIAL_PROVEEDOR='" + proveedor + "'),(SELECT ID_USUARIO FROM USUARIOS WHERE NOMBRE_USUARIO='" + usuario + "'))";

            sql2 = "INSERT INTO TIPOS_INSUMOS (NOMBRE_TIPOINSUMO) VALUES (?)";

            sql3 = "INSERT INTO INSUMOS (DESCRIPCION_INSUMO,NOMBRE_INSUMO,PRECIO_INSUMO, STOCK_INSUMO, TIPO_INSUMO, PROVEEDOR_INSUMO, FECHAREGISTRO_INSUMO, ID_TIPOINSUMO)"
                    + " VALUES (?,?,?,?,?,?,?,(SELECT ID_TIPOINSUMO FROM TIPOS_INSUMOS ORDER BY ID_TIPOINSUMO DESC LIMIT 1))";

            sql4 = "INSERT INTO DETALLE_COMPRAS (ID_COMPRA, ID_INSUMO, NOMINSUMO_DETCOMPRA, PRECIOINSUMO_DETCOMPRA, CANTIDAD_DETCOMPRA) "
                    + "VALUES ((SELECT ID_COMPRA FROM COMPRAS ORDER BY ID_COMPRA DESC LIMIT 1),(SELECT ID_INSUMO FROM INSUMOS ORDER BY ID_INSUMO DESC LIMIT 1),?,?,?)";

            try {

                PreparedStatement pst1 = cn.prepareStatement(sql1);
                PreparedStatement pst2 = cn.prepareStatement(sql2);
                PreparedStatement pst3 = cn.prepareStatement(sql3);
                PreparedStatement pst4 = cn.prepareStatement(sql4);

                pst1.setString(1, modelo1.getFechaReg_Insumos());
                pst1.setString(2, modelo1.getNomProv_Insumos());
                pst1.setString(3, modelo1.getNomUser_Compras());
                pst1.setDouble(4, modelo1.getMontoTot_Compras());

                pst2.setString(1, modelo1.getNombre_TipoInsumos());

                pst3.setString(1, modelo1.getDescripcion_Insumos());
                pst3.setString(2, modelo1.getNombre_Insumos());
                pst3.setDouble(3, modelo1.getPrecio_Insumos());
                pst3.setInt(4, modelo1.getStock_Insumos());
                pst3.setString(5, modelo1.getNombre_TipoInsumos());
                pst3.setString(6, modelo1.getNomProv_Insumos());
                pst3.setString(7, modelo1.getFechaReg_Insumos());

                pst4.setString(1, modelo1.getNombre_Insumos());
                pst4.setDouble(2, modelo1.getPrecio_Insumos());
                pst4.setInt(3, modelo1.getStock_Insumos());

                int ej1 = pst1.executeUpdate();

                int ej2 = pst2.executeUpdate();

                int ej3 = pst3.executeUpdate();

                int ej4 = pst4.executeUpdate();

                if (ej1 != 0 && ej2 != 0 && ej3 != 0 && ej4 != 0) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {
                Logger.getLogger(vCompras_Insumos.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }

        } else if (CantidadInsumo() > 0) {
            sql1 = "INSERT INTO COMPRAS (FECHA_COMPRA, PROVEEDOR_COMPRA, USUARIO_COMPRA, MONTOTOTAL_COMPRA, ID_PROVEEDOR, ID_USUARIO)"
                    + "VALUES (?,?,?,?,(SELECT ID_PROVEEDOR FROM PROVEEDORES WHERE NOMCOMERCIAL_PROVEEDOR='" + proveedor + "'),(SELECT ID_USUARIO FROM USUARIOS WHERE NOMBRE_USUARIO='" + usuario + "'))";

            sql2 = "SET SQL_SAFE_UPDATES=0";

            sql3 = "UPDATE INSUMOS I INNER JOIN DETALLE_COMPRAS D ON D.ID_INSUMO=I.ID_INSUMO SET I.STOCK_INSUMO= (I.STOCK_INSUMO + " + cantidad + ") WHERE I.NOMBRE_INSUMO='" + insumo + "'";

            sql4 = "INSERT INTO DETALLE_COMPRAS (ID_COMPRA, ID_INSUMO, NOMINSUMO_DETCOMPRA, PRECIOINSUMO_DETCOMPRA, CANTIDAD_DETCOMPRA) "
                    + "VALUES ((SELECT ID_COMPRA FROM COMPRAS ORDER BY ID_COMPRA DESC LIMIT 1),(SELECT ID_INSUMO FROM INSUMOS ORDER BY ID_INSUMO DESC LIMIT 1),?,?,?)";

            try {

                PreparedStatement pst1 = cn.prepareStatement(sql1);
                PreparedStatement pst2 = cn.prepareStatement(sql2);
                PreparedStatement pst3 = cn.prepareStatement(sql3);
                PreparedStatement pst4 = cn.prepareStatement(sql4);

                pst1.setString(1, modelo1.getFechaReg_Insumos());
                pst1.setString(2, modelo1.getNomProv_Insumos());
                pst1.setString(3, modelo1.getNomUser_Compras());
                pst1.setDouble(4, modelo1.getMontoTot_Compras());

                int ej1 = pst1.executeUpdate();

                int ej2 = pst2.executeUpdate();

                int ej3 = pst3.executeUpdate();

                pst4.setString(1, modelo1.getNombre_Insumos());
                pst4.setDouble(2, modelo1.getPrecio_Insumos());
                pst4.setInt(3, modelo1.getStock_Insumos());

                int ej4 = pst4.executeUpdate();

                if (ej1 != 0 && ej2 != 0 && ej3 != 0 && ej4 != 0) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {
                Logger.getLogger(vCompras_Insumos.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }

        } else if (CantidadInsumo() == 0 && CantidadTipos() > 0) {
            sql1 = "INSERT INTO COMPRAS (FECHA_COMPRA, PROVEEDOR_COMPRA, USUARIO_COMPRA, MONTOTOTAL_COMPRA, ID_PROVEEDOR, ID_USUARIO)"
                    + "VALUES (?,?,?,?,(SELECT ID_PROVEEDOR FROM PROVEEDORES WHERE NOMCOMERCIAL_PROVEEDOR='" + proveedor + "'),(SELECT ID_USUARIO FROM USUARIOS WHERE NOMBRE_USUARIO='" + usuario + "'))";

            sql3 = "INSERT INTO INSUMOS (DESCRIPCION_INSUMO,NOMBRE_INSUMO,PRECIO_INSUMO, STOCK_INSUMO, TIPO_INSUMO, PROVEEDOR_INSUMO, FECHAREGISTRO_INSUMO, ID_TIPOINSUMO)"
                    + " VALUES (?,?,?,?,?,?,?,(SELECT ID_TIPOINSUMO FROM INSUMOS AS I INNER JOIN DETALLE_COMPRAS AS D ON D.ID_INSUMO=I.ID_INSUMO WHERE I.TIPO_INSUMO='" + tipo + "' LIMIT 1))";

            sql4 = "INSERT INTO DETALLE_COMPRAS (ID_COMPRA, ID_INSUMO, NOMINSUMO_DETCOMPRA, PRECIOINSUMO_DETCOMPRA, CANTIDAD_DETCOMPRA) "
                    + "VALUES ((SELECT ID_COMPRA FROM COMPRAS ORDER BY ID_COMPRA DESC LIMIT 1),(SELECT ID_INSUMO FROM INSUMOS ORDER BY ID_INSUMO DESC LIMIT 1),?,?,?)";

            try {

                PreparedStatement pst1 = cn.prepareStatement(sql1);
                PreparedStatement pst3 = cn.prepareStatement(sql3);
                PreparedStatement pst4 = cn.prepareStatement(sql4);

                pst1.setString(1, modelo1.getFechaReg_Insumos());
                pst1.setString(2, modelo1.getNomProv_Insumos());
                pst1.setString(3, modelo1.getNomUser_Compras());
                pst1.setDouble(4, modelo1.getMontoTot_Compras());

                int ej1 = pst1.executeUpdate();

                pst3.setString(1, modelo1.getDescripcion_Insumos());
                pst3.setString(2, modelo1.getNombre_Insumos());
                pst3.setDouble(3, modelo1.getPrecio_Insumos());
                pst3.setInt(4, modelo1.getStock_Insumos());
                pst3.setString(5, modelo1.getNombre_TipoInsumos());
                pst3.setString(6, modelo1.getNomProv_Insumos());
                pst3.setString(7, modelo1.getFechaReg_Insumos());

                int ej3 = pst3.executeUpdate();

                pst4.setString(1, modelo1.getNombre_Insumos());
                pst4.setDouble(2, modelo1.getPrecio_Insumos());
                pst4.setInt(3, modelo1.getStock_Insumos());

                int ej4 = pst4.executeUpdate();

                if (ej1 != 0 && ej3 != 0 && ej4 != 0) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {
                Logger.getLogger(vCompras_Insumos.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }

        }
        return false;
    }*/
}
