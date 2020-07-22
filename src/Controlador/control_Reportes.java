package Controlador;

import Controlador.Conexion;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Colo-PC
 */
public class control_Reportes {

    Conexion con;
    Connection cn;
    Sentencias_sql sql = new Sentencias_sql();

    public control_Reportes() {
        con = new Conexion();
        cn = con.obtener();
    }

    public void ReporteVentasDiarias() {
        String convfecha = "%d/%m/%Y %h:%i";
        if (sql.ConsultaReportes("select v.NroFactura,date_format(v.FechaVenta, '" + convfecha + "') Fecha,p.descripcion,d.Precio,d.Cantidad,(d.Precio*d.Cantidad) SubTotal from ventas v INNER JOIN detallesventas d on v.idventa=d.idventa INNER JOIN productos p on p.idproducto=d.idproducto where d.activo=1 and p.activo=1 and v.activo=1 order by date_format(v.FechaVenta, '" + convfecha + "')desc,v.NroFactura") == true) {
            try {
                JasperReport ventasdiarias;

                HashMap<String, Object> parametros = new HashMap<>();

                String ruta = "/Reportes/Ventas_Diarias.jasper";

                ventasdiarias = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));

                String rutasub = ("D:\\Delivery_Tazmania\\Reportes\\");

                File subreport = new File(rutasub);

                //String rutabs = Paths.get(subreport.getAbsolutePath()).getRoot() + File.separator;
                //subreporte = (JasperReport) JRLoader.loadObject(getClass().getResource(rutasub));
                parametros.put("SUBREPORT_DIR", subreport);

                JasperPrint jprint = JasperFillManager.fillReport(ventasdiarias, parametros, cn);

                JasperViewer ver = new JasperViewer(jprint, false);

                ver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                ver.setVisible(true);

            } catch (JRException ex) {
                Logger.getLogger(control_Reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron datos");
        }
    }

    public void ReporteVentasSemanal(String fechainicio, String fechafinal) {
        try {
            if (sql.ConsultaReportes("select v.NroFactura,date_format(v.FechaVenta, '%d/%m/%Y %h:%i') as Fecha,p.descripcion,d.Precio,d.Cantidad,(d.Precio*d.Cantidad) SubTotal from ventas v INNER JOIN detallesventas d on v.idventa=d.idventa INNER JOIN productos p on p.idproducto=d.idproducto where date(v.FechaVenta) between str_to_date((str_to_date('" + fechainicio + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + fechafinal + "','%d/%m/%Y')),'%Y-%m-%d') and d.activo=1 and p.activo=1 and v.activo=1 order by date_format(v.FechaVenta, '%d/%m/%Y %h:%i')desc,v.NroFactura") == true) {
                JasperReport ventassemanal;

                String ruta = "/Reportes/Ventas_Semanal.jasper";

                ventassemanal = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));

                String rutasub = ("D:\\Delivery_Tazmania\\Reportes");

                File subreport = new File(rutasub);

                //String rutabs = Paths.get(subreport.getAbsolutePath()).getRoot() + File.separator;
                HashMap<String, Object> parametros = new HashMap<>();

                parametros.put("FechaInicio", fechainicio);
                parametros.put("FechaFinal", fechafinal);
                parametros.put("SUBREPORT_DIR", subreport);

                JasperPrint jprint = JasperFillManager.fillReport(ventassemanal, parametros, cn);

                JasperViewer ver = new JasperViewer(jprint, false);

                ver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                ver.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }

        } catch (JRException ex) {
            Logger.getLogger(control_Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ReporteVentasMes(String mes, String año) {

        if (sql.ConsultaReportes("select v.NroFactura,date_format(v.FechaVenta, '%d/%m/%Y %h:%i') as Fecha,p.descripcion,d.Precio,d.Cantidad,(d.Precio*d.Cantidad) as SubTotal from ventas as v INNER JOIN detallesventas as d on v.idventa=d.idventa INNER JOIN productos as p on p.idproducto=d.idproducto where Month(v.FechaVenta)='" + mes + "' and year(v.FechaVenta)='" + año + "' and d.activo=1 and p.activo=1 and v.activo=1 order by date_format(v.FechaVenta, '%d/%m/%Y %h:%i')desc,v.NroFactura")) {
            try {
                JasperReport ventasmes;

                String ruta = "/Reportes/Ventas_Mes.jasper";

                ventasmes = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));

                String rutasub = ("D:\\Delivery_Tazmania\\Reportes");

                File subreport = new File(rutasub);

                //String rutabs = Paths.get(subreport.getAbsolutePath()).getRoot() + File.separator;
                HashMap<String, Object> parametros = new HashMap<>();

                parametros.put("Mes", mes);
                parametros.put("Año", año);
                parametros.put("SUBREPORT_DIR", subreport);

                JasperPrint jprint = JasperFillManager.fillReport(ventasmes, parametros, cn);

                JasperViewer ver = new JasperViewer(jprint, false);

                ver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                ver.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(control_Reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron datos");
        }
    }

    public void ReporteVentasAño(String año) {
        if (sql.ConsultaReportes("select v.NroFactura,date_format(v.FechaVenta, '%d/%m/%Y %h:%i') as Fecha,p.descripcion,d.Precio,d.Cantidad,(d.Precio*d.Cantidad) as SubTotal from ventas as v INNER JOIN detallesventas as d on v.idventa=d.idventa INNER JOIN productos as p on p.idproducto=d.idproducto where YEAR(v.FechaVenta)='" + año + "' and d.activo=1 and p.activo=1 and v.activo=1 order by date_format(v.FechaVenta, '%d/%m/%Y %h:%i') desc,v.NroFactura")) {

            try {
                JasperReport ventasaño;

                String ruta = "/Reportes/Ventas_Anio.jasper";

                String rutasub = ("D:\\Delivery_Tazmania\\Reportes");

                ventasaño = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));

                File subreport = new File(rutasub);

                //String rutabs = Paths.get(subreport.getAbsolutePath()).getRoot() + File.separator;
                HashMap<String, Object> parametro = new HashMap<>();

                parametro.put("Año", año);
                parametro.put("SUBREPORT_DIR", subreport);

                JasperPrint jprint = JasperFillManager.fillReport(ventasaño, parametro, cn);

                JasperViewer ver = new JasperViewer(jprint, false);

                ver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                ver.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(control_Reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron datos");
        }
    }

    public void CargarFactura(String pedido, String nroenvio, String cliente, String direccion, String telefono, String detalles, String subtotal, String total, String abona) {
        try {
            JasperReport facturaventa;

            String ruta = "/Reportes/Factura_Ventas_Comandera.jasper";

            HashMap<String, Object> parametros = new HashMap<>();

            parametros.put("pedido", pedido);
            parametros.put("nroenviopago", nroenvio);
            parametros.put("cliente", cliente);
            parametros.put("direccion", direccion);
            parametros.put("telefono", telefono);
            parametros.put("detalles", detalles);
            parametros.put("subtotal", subtotal);
            parametros.put("total", total);
            parametros.put("abona", abona);

            facturaventa = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));

            JasperPrint jprint = JasperFillManager.fillReport(facturaventa, parametros, new JREmptyDataSource());

            JasperViewer ver = new JasperViewer(jprint, false);

            ver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            ver.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(control_Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
