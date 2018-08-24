/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.modelo_DetalleCompras;
import Modelo.modelo_Insumos;
import Modelo.modelo_TipoInsumos;
import Vistas.vCompras_Insumos;
import Vistas.vInsumos;
import Vistas.vTipos_Insumos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public class control_Compras extends modelo_DetalleCompras {

    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();
    private String sql1 = "", sql2 = "", sql3 = "", sql4 = "";

    public control_Compras() {

    }

    @Override
    public ArrayList<String> LlenarComboProveedor() {
        ArrayList<String> Lista = new ArrayList<String>();
        sql1 = "SELECT NOMCOMERCIAL_PROVEEDOR FROM PROVEEDORES";
        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                Lista.add(rs.getString("NOMCOMERCIAL_PROVEEDOR"));
            }

        } catch (SQLException e) {
            return null;
        }
        return Lista;
    }

    @Override
    public DefaultTableModel MostrarDatosC() throws SQLException {

        DefaultTableModel modelo;

        String titulos[] = {"N°", "Usuario", "Proveedor de Comercio", "Nombre del Insumo", "Precio", "Cantidad", "Monto Total", "Fecha de Compra"};

        String datos[] = new String[8];

        modelo = new DefaultTableModel(null, titulos);

        sql1 = "SELECT D.ID_DETCOMPRA, U.NOMBRE_USUARIO, P.NOMCOMERCIAL_PROVEEDOR, D.NOMINSUMO_DETCOMPRA, D.PRECIOINSUMO_DETCOMPRA, D.CANTIDAD_DETCOMPRA, "
                + " C.MONTOTOTAL_COMPRA, C.FECHA_COMPRA FROM USUARIOS U INNER JOIN COMPRAS C ON C.ID_USUARIO=U.ID_USUARIO INNER JOIN PROVEEDORES P "
                + " ON C.ID_PROVEEDOR=P.ID_PROVEEDOR INNER JOIN DETALLE_COMPRAS D ON D.ID_COMPRA=C.ID_COMPRA";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_DETCOMPRA");
                datos[1] = rs.getString("NOMBRE_USUARIO");
                datos[2] = rs.getString("NOMCOMERCIAL_PROVEEDOR");
                datos[3] = rs.getString("NOMINSUMO_DETCOMPRA");
                datos[4] = rs.getString("PRECIOINSUMO_DETCOMPRA");
                datos[5] = rs.getString("CANTIDAD_DETCOMPRA");
                datos[6] = rs.getString("MONTOTOTAL_COMPRA");
                datos[7] = rs.getString("FECHA_COMPRA");
                modelo.addRow(datos);
            }
            pst1.close();
            rs.close();

        } catch (SQLException e) {
            Logger.getLogger(vCompras_Insumos.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return modelo;
    }

    @Override
    public int CantidadInsumo() {
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
    }

    @Override
    public int CantidadTipos() {
        String nomtipo = vCompras_Insumos.jTextField5.getText();
        sql1 = "SELECT COUNT(TIPO_INSUMO) AS CANTIDAD_INSUMOS FROM INSUMOS WHERE TIPO_INSUMO='" + nomtipo + "'";

        try {

            int tipo = 0;

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                tipo = rs.getInt("CANTIDAD_INSUMOS");
            }
            pst1.close();
            rs.close();

            return tipo;
        } catch (SQLException e) {
            Logger.getLogger(vCompras_Insumos.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    @Override
    public boolean EfectuarCompra(modelo_DetalleCompras modelo1) {
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
    }

    @Override
    public ArrayList<String> LlenarTipoInsumo() {
        ArrayList<String> Lista = new ArrayList<String>();
        sql1 = "SELECT NOMBRE_TIPOINSUMO FROM TIPOS_INSUMOS";
        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                Lista.add(rs.getString("NOMBRE_TIPOINSUMO"));
            }

        } catch (SQLException e) {
            return null;
        }
        return Lista;
    }

    @Override
    public DefaultTableModel MostrarDatosI() throws SQLException {
        DefaultTableModel modelo;

        String titulos[] = {"N°", "Nombre del Insumo", "Descripción", "Precio", "Stock", "Tipo Insumo", "Proveedor", "Fecha Registro"};

        modelo = new DefaultTableModel(null, titulos);

        String datos[] = new String[8];

        sql1 = "SELECT * FROM INSUMOS";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_INSUMO");
                datos[1] = rs.getString("NOMBRE_INSUMO");
                datos[2] = rs.getString("DESCRIPCION_INSUMO");
                datos[3] = rs.getString("PRECIO_INSUMO");
                datos[4] = rs.getString("STOCK_INSUMO");
                datos[5] = rs.getString("TIPO_INSUMO");
                datos[6] = rs.getString("PROVEEDOR_INSUMO");
                datos[7] = rs.getString("FECHAREGISTRO_INSUMO");
                modelo.addRow(datos);
            }
            pst1.close();
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(vInsumos.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return modelo;
    }

    @Override
    public boolean InsertarInsumos(modelo_Insumos modelo) {
        sql1 = "INSERT INTO INSUMOS (DESCRIPCION_INSUMO,PRECIO_INSUMO,STOCK_INSUMO,TIPO_INSUMO,PROVEEDOR_INSUMO,FECHAREGISTRO_INSUMO,ID_TIPOINSUMO)"
                + "VALUES (?,?,?,?,?,?,(SELECT ID_TIPOINSUMO FROM TIPOS_INSUMOS ORDER BY ID_TIPOINSUMO DESC LIMIT 1))";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);

            pst1.setString(1, modelo.getDescripcion_Insumos());
            pst1.setDouble(2, modelo.getPrecio_Insumos());
            pst1.setInt(3, modelo.getStock_Insumos());
            pst1.setString(4, modelo.getNombre_TipoInsumos());
            pst1.setString(5, modelo.getNomProv_Insumos());
            pst1.setString(6, modelo.getFechaReg_Insumos());

            int ej1 = pst1.executeUpdate();

            if (ej1 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Logger.getLogger(vInsumos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean EditarInsumos(modelo_Insumos modelo) {

        String nominsumo = (String) vInsumos.jCBTipo_Insumos.getSelectedItem();

        sql1 = "UPDATE INSUMOS SET NOMBRE_INSUMO=?, DESCRIPCION_INSUMO=?, PRECIO_INSUMO=?, STOCK_INSUMO=?, TIPO_INSUMO=?, PROVEEDOR_INSUMO=?, FECHAREGISTRO_INSUMO=?, "
                + "ID_TIPOINSUMO = (SELECT ID_TIPOINSUMO FROM TIPOS_INSUMOS WHERE NOMBRE_TIPOINSUMO='" + nominsumo + "') WHERE ID_INSUMO=?";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);

            pst1.setString(1, modelo.getNombre_Insumos());
            pst1.setString(2, modelo.getDescripcion_Insumos());
            pst1.setDouble(3, modelo.getPrecio_Insumos());
            pst1.setInt(4, modelo.getStock_Insumos());
            pst1.setString(5, modelo.getNombre_TipoInsumos());
            pst1.setString(6, modelo.getNomProv_Insumos());
            pst1.setString(7, modelo.getFechaReg_Insumos());
            pst1.setInt(8, modelo.getId_Insumos());

            int ej1 = pst1.executeUpdate();

            if (ej1 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Logger.getLogger(vInsumos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean EliminarInsumos(modelo_Insumos modelo) {
        sql1 = "DELETE FROM INSUMOS WHERE ID_INSUMO = ?";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);

            pst1.setInt(1, modelo.getId_Insumos());

            int ej1 = pst1.executeUpdate();

            if (ej1 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Logger.getLogger(vInsumos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public DefaultTableModel MostrarDatosTI() throws SQLException {
        DefaultTableModel modelo;

        String titulos[] = {"N°", "Nombre Tipo de Insumo"};

        modelo = new DefaultTableModel(null, titulos);

        String[] datos = new String[2];

        sql1 = "SELECT * FROM TIPOS_INSUMOS";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_TIPOINSUMO");
                datos[1] = rs.getString("NOMBRE_TIPOINSUMO");
                modelo.addRow(datos);
            }
            pst1.close();
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(vTipos_Insumos.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return modelo;
    }

    @Override
    public boolean InsertarTiposInsumos(modelo_TipoInsumos modelo) {
        sql1 = "INSERT INTO TIPOS_INSUMOS (NOMBRE_TIPOINSUMO) VALUES (?)";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);

            pst1.setString(1, modelo.getNombre_TipoInsumos());

            int ej1 = pst1.executeUpdate();

            if (ej1 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Logger.getLogger(vTipos_Insumos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public int CantidadIDTipoInsumos() {
        int idtipoinsumo = Integer.parseInt(vTipos_Insumos.jTextID_TipoInsumo.getText());
        sql1 = "SELECT COUNT(ID_TIPOINSUMO) AS CANTIDAD FROM INSUMOS WHERE ID_TIPOINSUMO="+idtipoinsumo;

        try {
            int idtipo = 0;

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                idtipo = rs.getInt("CANTIDAD");
            }
            pst1.close();
            rs.close();
            return idtipo;
        } catch (SQLException e) {
            Logger.getLogger(vTipos_Insumos.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    @Override
    public boolean EditarTiposInsumos(modelo_TipoInsumos modelo) {
        if (CantidadIDTipoInsumos() > 0) {
            sql1 = "UPDATE TIPOS_INSUMOS SET NOMBRE_TIPOINSUMO=? WHERE ID_TIPOINSUMO=?";
            sql2 = "UPDATE INSUMOS SET TIPO_INSUMO=? WHERE ID_TIPOINSUMO=?";
            try {

                PreparedStatement pst1 = cn.prepareStatement(sql1);
                PreparedStatement pst2 = cn.prepareStatement(sql2);

                pst1.setString(1, modelo.getNombre_TipoInsumos());
                pst1.setInt(2, modelo.getId_TipoInsumos());
                pst2.setString(1, modelo.getNombre_TipoInsumos());
                pst2.setInt(2, modelo.getId_TipoInsumos());

                int ej1 = pst1.executeUpdate();
                int ej2 = pst2.executeUpdate();

                if (ej1 != 0 && ej2 != 0) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {
                Logger.getLogger(vTipos_Insumos.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }
        }else{
           sql1 = "UPDATE TIPOS_INSUMOS SET NOMBRE_TIPOINSUMO=? WHERE ID_TIPOINSUMO=?";
           
           try{
               PreparedStatement pst1 = cn.prepareStatement(sql1); 
               
               pst1.setString(1, modelo.getNombre_TipoInsumos());
               pst1.setInt(2, modelo.getId_TipoInsumos());
               
                int ej1 = pst1.executeUpdate();
                
                if(ej1 !=0){
                    return true;
                }else{
                    return false;
                }
           }catch(SQLException e){
              Logger.getLogger(vTipos_Insumos.class.getName()).log(Level.SEVERE, null, e);
              return false; 
           }
        }
    }

    @Override
    public boolean EliminarTiposInsumos(modelo_TipoInsumos modelo) {
        int idtipoinsumo = Integer.parseInt(vTipos_Insumos.jTextID_TipoInsumo.getText());
        
        sql1 = "DELETE FROM INSUMOS WHERE ID_TIPOINSUMO="+idtipoinsumo;
        sql2= "DELETE FROM TIPOS_INSUMOS WHERE ID_TIPOINSUMO="+idtipoinsumo;

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            PreparedStatement pst2= cn.prepareStatement(sql2);

            int ej1 = pst1.executeUpdate();
            int ej2 = pst2.executeUpdate();

            if (ej1 != 0 && ej2 != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(vTipos_Insumos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

}
