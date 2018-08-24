/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.modelo_Proveedores;
import Vistas.vGestion_Proveedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public class control_Proveedores extends modelo_Proveedores {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.obtener();
    private String sql1 = "", sql2 = "";

    public control_Proveedores() {

    }

    @Override
    public DefaultTableModel MostrarDatos() throws SQLException {
        DefaultTableModel modelo;

        String titulos[] = {
            "N°", "Tipo Documento", "Nro Documento", "Nombre", "Apellido", "Nombre Comercial", "Direccion", "Telefono"
        };

        modelo = new DefaultTableModel(null, titulos);

        String[] datos = new String[8];

        sql1 = "SELECT P.ID_PERSONA, V.TIPODOC_PROVEEDOR, V.NRODOC_PROVEEDOR, P.NOMBRE_PERSONA, P.APELLIDO_PERSONA, V.NOMCOMERCIAL_PROVEEDOR, "
                + " P.DOMICILIO_PERSONA, P.TELCONTACTO_PERSONA FROM PERSONAS P INNER JOIN PROVEEDORES V WHERE P.ID_PERSONA=V.ID_PERSONA";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_PERSONA");
                datos[1] = rs.getString("TIPODOC_PROVEEDOR");
                datos[2] = rs.getString("NRODOC_PROVEEDOR");
                datos[3] = rs.getString("NOMBRE_PERSONA");
                datos[4] = rs.getString("APELLIDO_PERSONA");
                datos[5] = rs.getString("NOMCOMERCIAL_PROVEEDOR");
                datos[6] = rs.getString("DOMICILIO_PERSONA");
                datos[7] = rs.getString("TELCONTACTO_PERSONA");
                modelo.addRow(datos);
            }

            rs.close();
            pst1.close();

        } catch (SQLException e) {
            Logger.getLogger(vGestion_Proveedores.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return modelo;
    }

    @Override
    public boolean InsertarProveedores(modelo_Proveedores modelo) {
        sql1 = "INSERT INTO PERSONAS (NOMBRE_PERSONA, APELLIDO_PERSONA, DOMICILIO_PERSONA, TELCONTACTO_PERSONA, EMAILCONTACTO_PERSONA)"
                + "VALUES (?,?,?,?," + "'-'" + ")";

        sql2 = "INSERT INTO PROVEEDORES (TIPODOC_PROVEEDOR, NRODOC_PROVEEDOR, NOMCOMERCIAL_PROVEEDOR, ID_PERSONA)"
                + "VALUES (?,?,?,(SELECT ID_PERSONA FROM PERSONAS ORDER BY ID_PERSONA DESC LIMIT 1))";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            PreparedStatement pst2 = cn.prepareStatement(sql2);

            pst1.setString(1, modelo.getNombre_persona());
            pst1.setString(2, modelo.getApellido_persona());
            pst1.setString(3, modelo.getDomicilio_persona());
            pst1.setString(4, modelo.getTelefonoContacto_persona());

            int ej1 = pst1.executeUpdate();

            pst2.setString(1, modelo.getTipoDoc_Proveedores());
            pst2.setInt(2, modelo.getNroDoc_Proveedores());
            pst2.setString(3, modelo.getNomComercial_Proveedores());

            int ej2 = pst2.executeUpdate();

            if (ej1 != 0 && ej2 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Logger.getLogger(vGestion_Proveedores.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean EditarProveedores(modelo_Proveedores modelo) {

        sql1 = " UPDATE PERSONAS SET NOMBRE_PERSONA = ?, APELLIDO_PERSONA= ?, DOMICILIO_PERSONA= ?, TELCONTACTO_PERSONA= ? "
                + " WHERE ID_PERSONA=? ";

        sql2 = " UPDATE PROVEEDORES SET TIPODOC_PROVEEDOR= ?, NRODOC_PROVEEDOR= ?, NOMCOMERCIAL_PROVEEDOR= ? "
                + " WHERE ID_PERSONA=?";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);
            PreparedStatement pst2 = cn.prepareStatement(sql2);

            pst1.setString(1, modelo.getNombre_persona());
            pst1.setString(2, modelo.getApellido_persona());
            pst1.setString(3, modelo.getDomicilio_persona());
            pst1.setString(4, modelo.getTelefonoContacto_persona());
            pst1.setInt(5, modelo.getId_persona());

            int ej1 = pst1.executeUpdate();

            pst2.setString(1, modelo.getTipoDoc_Proveedores());
            pst2.setInt(2, modelo.getNroDoc_Proveedores());
            pst2.setString(3, modelo.getNomComercial_Proveedores());
            pst2.setInt(4, modelo.getId_Proveedores());
            
            int ej2 = pst2.executeUpdate();

            if (ej1 != 0 && ej2 != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(vGestion_Proveedores.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean EliminarProveedores(modelo_Proveedores modelo) {

        sql1 = "DELETE FROM PERSONAS WHERE ID_PERSONA=?";

        try {

            PreparedStatement pst1 = cn.prepareStatement(sql1);

            pst1.setInt(1, modelo.getId_persona());

            int ej1 = pst1.executeUpdate();

            if (ej1 != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Logger.getLogger(vGestion_Proveedores.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }

}
