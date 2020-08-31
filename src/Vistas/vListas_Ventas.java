package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_BusquedasVentas;
import Controlador.control_Clientes;
import Controlador.control_DetallesVentas;
import Controlador.control_Movimientos_Caja;
import Controlador.control_Reportes;
import Controlador.control_Ventas;
import Controlador.control_existencias;
import Modelo.DetallesVentas;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import Modelo.Ventas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vListas_Ventas extends javax.swing.JInternalFrame {

    String idventa, iddetalle, fecha, desde, hasta, nrofactura;
    Timestamp fechaseleccionada;
    Object[][] datosventas, datosdetalles, datostabla;
    control_DetallesVentas detalle = new control_DetallesVentas();
    control_Ventas ventas = new control_Ventas();
    control_BusquedasVentas busquedaventa = new control_BusquedasVentas();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    control_Reportes reporte = new control_Reportes();
    control_existencias combo = new control_existencias();
    Sentencias_sql sql = new Sentencias_sql();
    vVentas_Productos venta = null;
    vListas_Ventas lista = null;
    DefaultTableModel datosventa, datosdetalle, tabla;
    DefaultListModel list;
    ArrayList<String> listcliente;

    public vListas_Ventas() {
        initComponents();
        IniciarFechas();
        MostrarVentas();
        MostrarCliente();
        jListClientes.setVisible(false);
        jTableDetallesVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (VerificarCajaAbierta() == false) {
                        //int fila = jTable1.rowAtPoint(e.getPoint());
                        int seleccionado = jTableVentas.getSelectedRow();
                        if (seleccionado == -1) {
                            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                        } else {
                            int idmovimientocaja = ventas.ObtenerIDMovVenta(Integer.parseInt(jTableVentas.getValueAt(seleccionado, 0).toString()), "VT");
                            String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                            if (estado.equals("CERRADA")) {
                                JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                            } else {
                                fecha = jTableVentas.getValueAt(seleccionado, 5).toString();
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                try {
                                    fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                                } catch (ParseException ex) {
                                    Logger.getLogger(vListas_Ventas.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (venta == null || venta.isClosed()) {
                                    venta = new vVentas_Productos();
                                    vMenuPrincipal.jDesktopPane1.add(venta);
                                    venta.setVisible(true);
                                    venta.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                                    venta.toFront();
                                }
                                vVentas_Productos.btnGuardarVenta.setText("Cancelar");
                                vVentas_Productos.btnGuardarVenta.setEnabled(true);
                                vVentas_Productos.btnModificarVenta.setEnabled(true);
                                idventa = jTableVentas.getValueAt(seleccionado, 0).toString();
                                vVentas_Productos.cbxUsuario.setSelectedItem(jTableVentas.getValueAt(seleccionado, 2).toString());
                                vVentas_Productos.txtTotal.setText(jTableVentas.getValueAt(seleccionado, 4).toString());
                                if (jTableVentas.getValueAt(seleccionado, 6).equals("Local")) {
                                    vVentas_Productos.jRadioLocal.setSelected(true);
                                } else {
                                    vVentas_Productos.jRadioOnline.setSelected(true);
                                }
                                vVentas_Productos.jDateFecha.setDate(fechaseleccionada);
                                vVentas_Productos.idventa = idventa;
                                vVentas_Productos.nrofactura = nrofactura;
                                venta.total = Float.parseFloat(jTableVentas.getValueAt(seleccionado, 4).toString());
                                venta.setTitle("Modificar Venta");
                                IDDetallesDatosDetalles();
                                CantidadFilas();
                                PasarFilasDatosDetalles();
                                dispose();
                                vDetallesVentas.dispose();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
                    }
                }
            }
        });

        jTableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int seleccionado = jTableVentas.getSelectedRow();
                    if (seleccionado == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        nrofactura = jTableVentas.getValueAt(seleccionado, 3).toString();
                        MostrarDetallesVentas();
                        Vistas.vListas_Ventas lista = null;
                        vDetallesVentas.setSize(817, 311);
                        vDetallesVentas.setLocationRelativeTo(lista);
                        vDetallesVentas.setModal(true);
                        vDetallesVentas.setVisible(true);
                    }
                }
            }
        });
    }

    public boolean VerificarCajaAbierta() {
        boolean est = false;
        int idcaja = Session.getIdcaja_abierta();
        if (idcaja == 0) {
            est = true;
        }
        return est;
    }

    public void MostrarVentas() {
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDVENTA", "IDUSER", "USUARIO", "NRO FACTURA", "MONTO TOTAL", "FECHA", "TIPO VENTA"};
        datosventas = ventas.MostrarDatos(desde, hasta);
        datosventa = new DefaultTableModel(datosventas, columnas);
        jTableVentas.setModel(datosventa);
        EliminarFilasVaciasVentas();
        //AjustarTamañoFilasVenta();
        ocultar_columnasVenta();
    }

    public void MostrarDetallesVentas() {
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDDETALLE", "IDVENTA", "IDPRODUCTO", "NRO FACTURA VENTA", "PRODUCTO", "PRECIO", "CANTIDAD", "FECHA"};
        datosdetalles = detalle.MostrarDatos(desde, hasta, nrofactura);
        datosdetalle = new DefaultTableModel(datosdetalles, columnas);
        jTableDetallesVentas.setModel(datosdetalle);
        EliminarFilasVaciasDetalleVentas();
        //AjustarTamañoFilasDetalle();
        ocultar_columnasDetalleVenta();
    }

    public void MostrarCliente() {
        String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
        datostabla = ventas.MostrarClientes();
        tabla = new DefaultTableModel(datostabla, columnas);
        jTableClientes.setModel(tabla);
        EliminarFilasVaciasClientes();
        ocultar_columnasClientes();
    }

    public void ListaClientes() {
        listcliente = combo.list("clientes", "nombre", jTextFieldCliente.getText());
        String substr = jTextFieldCliente.getText().toLowerCase();
        list = new DefaultListModel();
        jListClientes.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listcliente.size(); i++) {
            if (listcliente.get(i) == null) {
                listcliente.remove(i);
            } else {
                String sublist = listcliente.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listcliente.get(i));
                    jListClientes.setVisible(true);
                    if (jTextFieldCliente.getText().isEmpty()) {
                        jListClientes.setVisible(false);
                    }
                }
            }
        }
    }

    public void IniciarFechas() {
        Date date = new Date();
        jDateFechaDesde.setDate(date);
        jDateFechaHasta.setDate(date);
    }

    public void ocultar_columnasVenta() {
        jTableVentas.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableVentas.getColumnModel().getColumn(0).setMinWidth(0);
        jTableVentas.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableVentas.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableVentas.getColumnModel().getColumn(1).setMinWidth(0);
        jTableVentas.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    public void ocultar_columnasDetalleVenta() {
        jTableDetallesVentas.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(0).setMinWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(1).setMinWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(2).setMaxWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(2).setMinWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(7).setMaxWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(7).setMinWidth(0);
        jTableDetallesVentas.getColumnModel().getColumn(7).setPreferredWidth(0);
    }

    public void ocultar_columnasClientes() {
        jTableClientes.getColumnModel().getColumn(3).setMaxWidth(0);
        jTableClientes.getColumnModel().getColumn(3).setMinWidth(0);
        jTableClientes.getColumnModel().getColumn(3).setPreferredWidth(0);
    }

    public void LimpiarSeleccionVenta() {
        jTableVentas.clearSelection();
        jTableVentas.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionDetalleVenta() {
        jTableDetallesVentas.clearSelection();
        jTableDetallesVentas.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionProd() {
        jTableClientes.clearSelection();
        jTableClientes.getSelectionModel().clearSelection();
    }

    public void EliminarFilasVaciasVentas() {
        if (jTableVentas.getRowCount() != 0) {
            for (int columna = 0; columna < jTableVentas.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableVentas.getRowCount(); fila++) {
                    if (jTableVentas.getValueAt(fila, columna) == null) {
                        datosventa.removeRow(fila);
                    }
                }
            }

        }
    }

    public void EliminarFilasVaciasDetalleVentas() {
        if (jTableDetallesVentas.getRowCount() != 0) {
            for (int columna = 0; columna < jTableDetallesVentas.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableDetallesVentas.getRowCount(); fila++) {
                    if (jTableDetallesVentas.getValueAt(fila, columna) == null) {
                        datosdetalle.removeRow(fila);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasClientes() {
        if (jTableClientes.getRowCount() != 0) {
            for (int columna = 0; columna < jTableClientes.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableClientes.getRowCount(); fila++) {
                    if (jTableClientes.getValueAt(fila, columna) == null) {
                        tabla.removeRow(fila);
                    }
                }
            }
        }
    }

    /*public void nombresProductos() {
        int[] nombresprod = jTable2.getSelectedRows();
        for (int i = 0; i < nombresprod.length; i++) {
            venta.nomprod.add(jTable2.getValueAt(nombresprod[i], 4).toString());
        }
    }*/

 /*public void IDDetalles() {
        int[] seleccionados = jTable2.getSelectedRows();
        for (int i = 0; i < seleccionados.length; i++) {
            venta.iddetalles.add(jTable2.getValueAt(seleccionados[i], 0).toString());
        }
        //JOptionPane.showMessageDialog(null, venta.iddetalles);
    }*/
    public void IDDetallesDatosDetalles() {
        for (int i = 0; i < jTableDetallesVentas.getRowCount(); i++) {
            venta.iddetalles.add(jTableDetallesVentas.getValueAt(i, 0).toString());
        }
    }

    public ArrayList<String> ObtenerIDDetallesDesdeListaVentas() {
        int i = jTableVentas.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            venta.iddetalles = detalle.ObtenerIDDetalleDesdeListaVenta(jTableVentas.getValueAt(i, 3).toString());
        }
        return venta.iddetalles;
    }

    public void CantidadFilas() {
        venta.filasdetalle = venta.iddetalles.size();
    }

    public int CantidadFilasDetalles() {
        int i = jTableVentas.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            venta.filasdetalle = detalle.CantidadTotalIDDetalles(jTableVentas.getValueAt(i, 3).toString());
        }
        return venta.filasdetalle;
    }

    /*public void PasarFilas() {
        if (venta == null || venta.isClosed()) {
            venta = new vVentas_Productos();
            vMenuPrincipal.jDesktopPane1.add(venta);
            venta.setVisible(true);
            venta.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            venta.toFront();
        }
        int[] seleccionados = jTable2.getSelectedRows();
        Object[] fila = new Object[3];
        for (int j = 0; j < seleccionados.length; j++) {
            fila[0] = jTable2.getValueAt(seleccionados[j], 4);
            fila[1] = jTable2.getValueAt(seleccionados[j], 5);
            fila[2] = jTable2.getValueAt(seleccionados[j], 6);
            venta.modelo.addRow(fila);
        }
    }*/
    public void ObtenerDatosDetalleDesdeListaVentas() {
        int i = jTableVentas.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            Object[][] datos = detalle.DatosDetalleDesdeListaVentasModificar(jTableVentas.getValueAt(i, 3).toString());
            Object[] columnas = {"PRODUCTO", "PRECIO", "CANTIDAD", "SUBTOTAL"};
            venta.modelo = new DefaultTableModel(datos, columnas);
            vVentas_Productos.jTableDetalle.setModel(venta.modelo);
        }
    }

    public void PasarFilasDatosDetalles() {
        Object[] fila = new Object[4];
        for (int j = 0; j < jTableDetallesVentas.getRowCount(); j++) {
            float precio = Float.parseFloat(jTableDetallesVentas.getValueAt(j, 5).toString()), cant = Float.parseFloat(jTableDetallesVentas.getValueAt(j, 6).toString());
            float total = precio * cant;
            fila[0] = jTableDetallesVentas.getValueAt(j, 4);
            fila[1] = jTableDetallesVentas.getValueAt(j, 5);
            fila[2] = jTableDetallesVentas.getValueAt(j, 6);
            fila[3] = total;
            venta.modelo.addRow(fila);
        }
    }

    public void IngresarDetalle() {
        int[] detalles = jTableDetallesVentas.getSelectedRows();
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < detalles.length; i++) {
            objetos.add(jTableDetallesVentas.getValueAt(detalles[i], 4));
            objetos.add(jTableDetallesVentas.getValueAt(detalles[i], 6));
            objetos.add(jTableDetallesVentas.getValueAt(detalles[i], 5));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextAreaDetalleFactura.append(iterador.next() + " ");
        }
    }

    public void IngresarDetalle2() {
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < jTableDetallesVentas.getRowCount(); i++) {
            objetos.add(jTableDetallesVentas.getValueAt(i, 4));
            objetos.add(jTableDetallesVentas.getValueAt(i, 6));
            objetos.add(jTableDetallesVentas.getValueAt(i, 5));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextAreaDetalleFactura.append(iterador.next() + " ");
        }
    }

    public void IngresarSubTotal() {
        int[] subtotales = jTableDetallesVentas.getSelectedRows();
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < subtotales.length; i++) {
            objetos.add(Double.parseDouble(jTableDetallesVentas.getValueAt(subtotales[i], 5).toString().trim()) * Double.parseDouble(jTableDetallesVentas.getValueAt(subtotales[i], 6).toString().trim()));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextAreaSubTotal.append(iterador.next() + "\n");
        }
    }

    public void IngresarSubTotal2() {
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < jTableDetallesVentas.getRowCount(); i++) {
            objetos.add(Double.parseDouble(jTableDetallesVentas.getValueAt(i, 5).toString().trim()) * Double.parseDouble(jTableDetallesVentas.getValueAt(i, 6).toString().trim()));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextAreaSubTotal.append(iterador.next() + "\n");
        }
    }

    public void IngresarTotal() {
        int total = jTableVentas.getSelectedRow();
        if (total != -1) {
            jTextFieldTotalFactura.setText(jTableVentas.getValueAt(total, 4).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        }
    }

    public String IngresarNroPedido() {
        String codigo = "";
        if (ventas.CrearNroPedido("-")) {
            int idmovimiento = sql.obtenerUltimoId("numerospedidosfactura", "idnumeropedidofactura");
            codigo = sql.generaCodigo("nropedido");
            sql.ejecutarSql("UPDATE numerospedidosfactura SET nropedido ='" + codigo + "' WHERE idnumeropedidofactura=" + idmovimiento);
        }
        return codigo;
    }

    public void SeleccionarFilas() {
        int seleccionado = jTableVentas.getSelectedRow();
        ArrayList<Integer> numeros = new ArrayList<>();
        for (int l = 0; l < jTableDetallesVentas.getRowCount(); l++) {
            if (jTableVentas.getValueAt(seleccionado, 3).equals(jTableDetallesVentas.getValueAt(l, 3))) {
                numeros.add(l);
                Rectangle r = jTableDetallesVentas.getCellRect(l, 3, true);
                jTableDetallesVentas.scrollRectToVisible(r);
            }
        }
        int num = 0;
        for (int i = 0; i < numeros.size(); i++) {
            num = numeros.get(i);
        }
        jTableDetallesVentas.getSelectionModel().setSelectionInterval(numeros.get(0), num);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vImprimirFactura = new javax.swing.JDialog();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jButtonSeleccionarCliente = new javax.swing.JButton();
        jTextFieldCliente = new javax.swing.JTextField();
        jListClientes = new javax.swing.JList<>();
        jLabelSeleccionarCliente = new javax.swing.JLabel();
        jLabelDomicilio = new javax.swing.JLabel();
        jTextFieldDomicilio = new javax.swing.JTextField();
        jLabelTelefonoFactura = new javax.swing.JLabel();
        jTextFieldTelefonoFactura = new javax.swing.JTextField();
        jLabelDetalle = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDetalleFactura = new javax.swing.JTextArea();
        jLabelSubTotal = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaSubTotal = new javax.swing.JTextArea();
        jLabelTotal = new javax.swing.JLabel();
        jTextFieldTotalFactura = new javax.swing.JTextField();
        jLabelAbonaFactura = new javax.swing.JLabel();
        jTextFieldAbonaCliente = new javax.swing.JTextField();
        jButtonImprimirFactura = new javax.swing.JButton();
        jButtonCancelarFactura = new javax.swing.JButton();
        jLabelEnvioDinero = new javax.swing.JLabel();
        jTextFieldEnvioDinero = new javax.swing.JTextField();
        vSeleccionarCliente = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jButtonAgregarCliente = new javax.swing.JButton();
        jButtonCancelarCliente = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonBuscarClientes = new javax.swing.JButton();
        jLabelDNI = new javax.swing.JLabel();
        jTextFieldDNI = new javax.swing.JTextField();
        jLabelTelefono = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        vDetallesVentas = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDetallesVentas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonModificarDetalleVenta = new javax.swing.JButton();
        jButtonEliminarDetalleVenta = new javax.swing.JButton();
        jButtonImprimir = new javax.swing.JButton();
        jButtonNuevoDetalleVenta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVentas =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonNuevo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jDateFechaDesde = new com.toedter.calendar.JDateChooser();
        jDateFechaHasta = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButtonVerDetalle = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();

        vImprimirFactura.setTitle("Cargar Factura Venta");
        java.awt.Image iconodeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vImprimirFactura.setIconImage(iconodeliv);
        vImprimirFactura.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        vImprimirFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        vImprimirFactura.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vImprimirFacturaWindowClosing(evt);
            }
        });
        vImprimirFactura.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonSeleccionarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarClienteActionPerformed(evt);
            }
        });

        jTextFieldCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldClienteKeyReleased(evt);
            }
        });

        jListClientes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jListClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListClientes.setValueIsAdjusting(true);
        jListClientes.setVisibleRowCount(0);
        jListClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListClientesMouseClicked(evt);
            }
        });

        jLabelSeleccionarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelSeleccionarCliente.setText("(*) Seleccionar Cliente");

        jLayeredPane1.setLayer(jButtonSeleccionarCliente, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTextFieldCliente, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jListClientes, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabelSeleccionarCliente, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jButtonSeleccionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelSeleccionarCliente)
                    .addComponent(jListClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabelSeleccionarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonSeleccionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jListClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vImprimirFactura.getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabelDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDomicilio.setText("(*) Domicilio:");
        vImprimirFactura.getContentPane().add(jLabelDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 123, -1, 22));

        jTextFieldDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextFieldDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 151, 531, 28));

        jLabelTelefonoFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTelefonoFactura.setText("(*) Telefono:");
        vImprimirFactura.getContentPane().add(jLabelTelefonoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 185, -1, 19));

        jTextFieldTelefonoFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextFieldTelefonoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 209, 266, 29));

        jLabelDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDetalle.setText("(*) DETALLE:");
        vImprimirFactura.getContentPane().add(jLabelDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 256, -1, -1));

        jTextAreaDetalleFactura.setColumns(20);
        jTextAreaDetalleFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jTextAreaDetalleFactura.setRows(5);
        jScrollPane3.setViewportView(jTextAreaDetalleFactura);

        vImprimirFactura.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 285, 588, 223));

        jLabelSubTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelSubTotal.setText("(*) Sub Total");
        vImprimirFactura.getContentPane().add(jLabelSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 519, -1, -1));

        jTextAreaSubTotal.setEditable(false);
        jTextAreaSubTotal.setColumns(20);
        jTextAreaSubTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jTextAreaSubTotal.setRows(5);
        jScrollPane4.setViewportView(jTextAreaSubTotal);

        vImprimirFactura.getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 543, 294, -1));

        jLabelTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTotal.setText("(*) TOTAL");
        vImprimirFactura.getContentPane().add(jLabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 519, -1, -1));

        jTextFieldTotalFactura.setEditable(false);
        jTextFieldTotalFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextFieldTotalFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 543, 160, 31));

        jLabelAbonaFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelAbonaFactura.setText("(*) El Cliente Abona CON:");
        vImprimirFactura.getContentPane().add(jLabelAbonaFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 580, -1, 13));

        jTextFieldAbonaCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextFieldAbonaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 599, 160, 30));

        jButtonImprimirFactura.setBackground(new java.awt.Color(252, 249, 57));
        jButtonImprimirFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonImprimirFactura.setText("Imprimir");
        jButtonImprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirFacturaActionPerformed(evt);
            }
        });
        vImprimirFactura.getContentPane().add(jButtonImprimirFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 640, 100, 30));

        jButtonCancelarFactura.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelarFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarFactura.setText("Cancelar");
        jButtonCancelarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarFacturaActionPerformed(evt);
            }
        });
        vImprimirFactura.getContentPane().add(jButtonCancelarFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 640, 100, 30));

        jLabelEnvioDinero.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelEnvioDinero.setText("(*) ENVIO DE DINERO N°:");
        vImprimirFactura.getContentPane().add(jLabelEnvioDinero, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 63, -1, -1));

        jTextFieldEnvioDinero.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldEnvioDinero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEnvioDineroKeyTyped(evt);
            }
        });
        vImprimirFactura.getContentPane().add(jTextFieldEnvioDinero, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 408, 30));

        vSeleccionarCliente.setTitle("Seleccionar Cliente");
        java.awt.Image icondeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vSeleccionarCliente.setIconImage(icondeliv);
        vSeleccionarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarClienteMouseClicked(evt);
            }
        });
        vSeleccionarCliente.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarClienteWindowClosing(evt);
            }
        });

        jTableClientes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTableClientes);

        jButtonAgregarCliente.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarCliente.setText("Agregar");
        jButtonAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarClienteActionPerformed(evt);
            }
        });

        jButtonCancelarCliente.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarCliente.setText("Cancelar");
        jButtonCancelarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarClienteActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombre.setText("Nombre");

        jTextFieldNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarClientes.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarClientes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarClientes.setText("Buscar");
        jButtonBuscarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarClientesActionPerformed(evt);
            }
        });

        jLabelDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDNI.setText("DNI");

        jTextFieldDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTelefono.setText("Telefono");

        jTextFieldTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDNI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabelNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabelTelefono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(383, 383, 383))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombre)
                    .addComponent(jLabelDNI)
                    .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTelefono)
                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscarClientes)
                .addContainerGap())
        );

        javax.swing.GroupLayout vSeleccionarClienteLayout = new javax.swing.GroupLayout(vSeleccionarCliente.getContentPane());
        vSeleccionarCliente.getContentPane().setLayout(vSeleccionarClienteLayout);
        vSeleccionarClienteLayout.setHorizontalGroup(
            vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(jButtonAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(256, 256, 256)
                        .addComponent(jButtonCancelarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        vSeleccionarClienteLayout.setVerticalGroup(
            vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancelarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vDetallesVentas.setTitle("Detalle Venta");
        java.awt.Image icon = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vDetallesVentas.setIconImage(icon);

        jTableDetallesVentas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableDetallesVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableDetallesVentas);

        jButtonModificarDetalleVenta.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificarDetalleVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificarDetalleVenta.setText("Modificar");
        jButtonModificarDetalleVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarDetalleVentaActionPerformed(evt);
            }
        });

        jButtonEliminarDetalleVenta.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminarDetalleVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminarDetalleVenta.setText("Eliminar");
        jButtonEliminarDetalleVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarDetalleVentaActionPerformed(evt);
            }
        });

        jButtonImprimir.setBackground(new java.awt.Color(252, 249, 57));
        jButtonImprimir.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonImprimir.setText("Imprimir");
        jButtonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirActionPerformed(evt);
            }
        });

        jButtonNuevoDetalleVenta.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevoDetalleVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevoDetalleVenta.setText("Nuevo");
        jButtonNuevoDetalleVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoDetalleVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vDetallesVentasLayout = new javax.swing.GroupLayout(vDetallesVentas.getContentPane());
        vDetallesVentas.getContentPane().setLayout(vDetallesVentasLayout);
        vDetallesVentasLayout.setHorizontalGroup(
            vDetallesVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vDetallesVentasLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButtonNuevoDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jButtonModificarDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonEliminarDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addComponent(jButtonImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vDetallesVentasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        vDetallesVentasLayout.setVerticalGroup(
            vDetallesVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vDetallesVentasLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(vDetallesVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModificarDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminarDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNuevoDetalleVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado de Ventas");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setVisible(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTableVentas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableVentasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableVentas);

        jButtonNuevo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevo.setText("Nuevo");
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Desde");

        jButtonBuscar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jDateFechaDesde.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jDateFechaDesde.setMinimumSize(new java.awt.Dimension(32, 20));

        jDateFechaHasta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("Hasta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(66, 66, 66)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBuscar)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jButtonVerDetalle.setBackground(new java.awt.Color(252, 249, 57));
        jButtonVerDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonVerDetalle.setText("Ver Detalle");
        jButtonVerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerDetalleActionPerformed(evt);
            }
        });

        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jButtonVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        getAccessibleContext().setAccessibleName("Listado Ventas");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEliminarDetalleVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarDetalleVentaActionPerformed
        int fila = jTableVentas.getSelectedRow();
        int seleccionado = jTableDetallesVentas.getSelectedRow();
        Ventas v = new Ventas();
        DetallesVentas dt = new DetallesVentas();
        Movimientos_Caja mc = new Movimientos_Caja();
        control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Eliminar Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int cantidad = jTableDetallesVentas.getRowCount();
                if (cantidad == 1) {
                    mc.setIdmovimiento(Integer.parseInt(jTableVentas.getValueAt(fila, 0).toString()));
                    mc.setIdtipomovimiento(10);
                    if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                        dt.setCantidad(Float.parseFloat(jTableDetallesVentas.getValueAt(seleccionado, 6).toString()));
                        dt.setIdproducto(Integer.parseInt(jTableDetallesVentas.getValueAt(seleccionado, 2).toString()));
                        detalle.SumarCantidadRestadaInsumos2(dt);
                        dt.setIddetalleventa(Integer.parseInt(jTableDetallesVentas.getValueAt(seleccionado, 0).toString()));
                        detalle.EliminarDetalleVenta(dt);
                        v.setIdventa(Integer.parseInt(jTableVentas.getValueAt(fila, 0).toString()));
                        /*int[] seleccionados = jTable2.getSelectedRows();
                    for (int j = 0; j < seleccionados.length; j++) {
                        dt.setCantidad(Float.parseFloat(jTable2.getValueAt(seleccionados[j], 6).toString()));
                        dt.setIdproducto(Integer.parseInt(jTable2.getValueAt(seleccionados[j], 2).toString()));
                        if (detalle.SumarCantidadRestadaInsumos2(dt)) {

                        }
                        dt.setIddetalleventa(Integer.parseInt(jTable2.getValueAt(seleccionados[j], 0).toString()));
                        if (detalle.EliminarDetalleVenta(dt)) {

                        }
                    }
                    for (int j = 0; j < jTable2.getRowCount(); j++) {
                        dt.setCantidad(Float.parseFloat(jTable2.getValueAt(j, 6).toString()));
                        dt.setIdproducto(Integer.parseInt(jTable2.getValueAt(j, 2).toString()));
                        if (detalle.SumarCantidadRestadaInsumos2(dt)) {

                        }
                        dt.setIddetalleventa(Integer.parseInt(jTable2.getValueAt(j, 0).toString()));
                        if (detalle.EliminarDetalleVenta(dt)) {

                        }
                    }*/
                    }
                    if (ventas.EliminarVenta(v)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        MostrarVentas();
                        //MostrarDetallesVentas();
                        vDetallesVentas.dispose();
                    }
                } else {
                    float totalventa = Float.parseFloat(jTableVentas.getValueAt(fila, 4).toString()),
                            valor = Float.parseFloat(jTableDetallesVentas.getValueAt(seleccionado, 5).toString())
                            * Float.parseFloat(jTableDetallesVentas.getValueAt(seleccionado, 6).toString()),
                            totalfinal = totalventa - valor;
                    v.setMontototal(totalfinal);
                    v.setIdventa(Integer.parseInt(jTableVentas.getValueAt(fila, 0).toString()));
                    if (ventas.ActualizarTotalVenta(v)) {
                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(jTableVentas.getValueAt(fila, 0).toString()), 10));
                        mc.setDescripcion("VENTA PRODUCTOS");
                        mc.setIdtipomovimiento(10);
                        mc.setIdusuario(Session.getIdusuario());
                        mc.setFecha_movimiento(jTableVentas.getValueAt(fila, 5).toString());
                        mc.setMonto(v.getMontototal());
                        mc.setIdmovimiento(Integer.parseInt(jTableVentas.getValueAt(fila, 0).toString()));
                        mc.setTipoVenta(jTableVentas.getValueAt(fila, 6).toString());
                        if (control_mc.EditarMovimientosCaja(mc)) {
                            dt.setCantidad(Float.parseFloat(jTableDetallesVentas.getValueAt(seleccionado, 6).toString()));
                            dt.setIdproducto(Integer.parseInt(jTableDetallesVentas.getValueAt(seleccionado, 2).toString()));
                            detalle.SumarCantidadRestadaInsumos2(dt);
                            dt.setIddetalleventa(Integer.parseInt(jTableDetallesVentas.getValueAt(seleccionado, 0).toString()));
                            detalle.EliminarDetalleVenta(dt);
                            JOptionPane.showMessageDialog(null, "Eliminado");
                            MostrarDetallesVentas();
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonEliminarDetalleVentaActionPerformed

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        venta = new vVentas_Productos();
        vMenuPrincipal.jDesktopPane1.add(venta);
        venta.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonModificarDetalleVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarDetalleVentaActionPerformed
        if (VerificarCajaAbierta() == false) {
            int seleccionado = jTableVentas.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int idmovimientocaja = ventas.ObtenerIDMovVenta(Integer.parseInt(jTableVentas.getValueAt(seleccionado, 0).toString()), "VT");
                String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                if (estado.equals("CERRADA")) {
                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                } else {
                    fecha = jTableVentas.getValueAt(seleccionado, 5).toString();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Ventas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (venta == null || venta.isClosed()) {
                        venta = new vVentas_Productos();
                        vMenuPrincipal.jDesktopPane1.add(venta);
                        venta.setVisible(true);
                        venta.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        venta.toFront();
                    }
                    vVentas_Productos.btnGuardarVenta.setText("Cancelar");
                    vVentas_Productos.btnGuardarVenta.setEnabled(true);
                    vVentas_Productos.btnModificarVenta.setEnabled(true);
                    idventa = jTableVentas.getValueAt(seleccionado, 0).toString();
                    vVentas_Productos.cbxUsuario.setSelectedItem(jTableVentas.getValueAt(seleccionado, 2).toString());
                    vVentas_Productos.txtTotal.setText(jTableVentas.getValueAt(seleccionado, 4).toString());
                    if (jTableVentas.getValueAt(seleccionado, 6).equals("Local")) {
                        vVentas_Productos.jRadioLocal.setSelected(true);
                    } else {
                        vVentas_Productos.jRadioOnline.setSelected(true);
                    }
                    vVentas_Productos.jDateFecha.setDate(fechaseleccionada);
                    vVentas_Productos.idventa = idventa;
                    vVentas_Productos.nrofactura = nrofactura;
                    venta.total = Float.parseFloat(jTableVentas.getValueAt(seleccionado, 4).toString());
                    venta.setTitle("Modificar Venta");
                    IDDetallesDatosDetalles();
                    CantidadFilas();
                    PasarFilasDatosDetalles();
                    dispose();
                    vDetallesVentas.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
        }
    }//GEN-LAST:event_jButtonModificarDetalleVentaActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        //desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        //hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccionVenta();
        //LimpiarSeleccionDetalleVenta();
        //desde = "";
        //hasta = "";
        //IniciarFechas();
        //MostrarVentas();
        //MostrarDetallesVentas();
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        if (jDateFechaDesde.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateFechaHasta.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    datosventas = busquedaventa.MostrarDatosNroVenta(desde, hasta);
                    if (datosventas.length != 0) {
                        String[] columnas = {"IDVENTA", "IDUSER", "USUARIO", "NRO FACTURA", "MONTO TOTAL", "FECHA", "TIPO VENTA"};
                        datosventa = new DefaultTableModel(datosventas, columnas);
                        jTableVentas.setModel(datosventa);
                        EliminarFilasVaciasVentas();
                        //AjustarTamañoFilasVenta();
                        ocultar_columnasVenta();

                        /*String[] columnasdetalle = {"IDDETALLE", "IDVENTA", "IDPRODUCTO", "NRO FACTURA VENTA", "PRODUCTO", "PRECIO", "CANTIDAD", "FECHA"};
                        datosdetalles = busquedaventa.MostrarDatosDetallesVentasNroVenta(desde, hasta);
                        datosdetalle = new DefaultTableModel(datosdetalles, columnasdetalle);
                        jTable2.setModel(datosdetalle);
                        EliminarFilasVaciasDetalleVentas();
                        //AjustarTamañoFilasDetalle();
                        ocultar_columnasDetalleVenta();*/
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron datos");
                    }
                } else if (desde.isEmpty() || hasta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes ingresar la fecha que falta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirActionPerformed
        IngresarDetalle2();
        IngresarSubTotal2();
        IngresarTotal();
        vImprimirFactura.setSize(630, 715);
        vImprimirFactura.setLocationRelativeTo(this);
        vImprimirFactura.setModal(true);
        vImprimirFactura.setVisible(true);
    }//GEN-LAST:event_jButtonImprimirActionPerformed

    private void jButtonCancelarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarFacturaActionPerformed
        if (!jTextFieldCliente.getText().equals("") || !jTextFieldDomicilio.getText().isEmpty() || !jTextFieldTelefonoFactura.getText().isEmpty() || !jTextAreaDetalleFactura.getText().isEmpty() || !jTextAreaSubTotal.getText().isEmpty() || !jTextFieldTotalFactura.getText().isEmpty() || !jTextFieldAbonaCliente.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vImprimirFactura.dispose();
                LimpiarSeleccionVenta();
                LimpiarSeleccionDetalleVenta();
            } else {
                vImprimirFactura.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_jButtonCancelarFacturaActionPerformed

    private void jButtonAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarClienteActionPerformed
        if (jTableClientes.getRowCount() != 0) {
            int i = jTableClientes.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                vSeleccionarCliente.dispose();
                jTextFieldCliente.setText(jTableClientes.getValueAt(i, 1).toString() + " " + jTableClientes.getValueAt(i, 2).toString());
                jTextFieldDomicilio.setText(jTableClientes.getValueAt(i, 3).toString());
                jTextFieldTelefonoFactura.setText(jTableClientes.getValueAt(i, 4).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos clientes todavia");
        }
    }//GEN-LAST:event_jButtonAgregarClienteActionPerformed

    private void jButtonImprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirFacturaActionPerformed
        if (!jTextFieldEnvioDinero.getText().isEmpty() && !jTextFieldCliente.getText().equals("") && !jTextFieldDomicilio.getText().isEmpty() && !jTextFieldTelefonoFactura.getText().isEmpty() && !jTextAreaDetalleFactura.getText().isEmpty() && !jTextAreaSubTotal.getText().isEmpty() && !jTextFieldTotalFactura.getText().isEmpty() && !jTextFieldAbonaCliente.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Confirmar Factura Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                reporte.CargarFactura(IngresarNroPedido(), jTextFieldEnvioDinero.getText(), jTextFieldCliente.getText(), jTextFieldDomicilio.getText(), jTextFieldTelefonoFactura.getText(), jTextAreaDetalleFactura.getText(), jTextAreaSubTotal.getText(), jTextFieldTotalFactura.getText(), jTextFieldAbonaCliente.getText());
                vImprimirFactura.dispose();
            } else {
                vImprimirFactura.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios.");
        }
    }//GEN-LAST:event_jButtonImprimirFacturaActionPerformed

    private void jButtonCancelarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarClienteActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarCliente.dispose();
        } else {
            vSeleccionarCliente.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButtonCancelarClienteActionPerformed

    private void vSeleccionarClienteWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarClienteWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarCliente.dispose();
        } else {
            vSeleccionarCliente.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarClienteWindowClosing

    private void vImprimirFacturaWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vImprimirFacturaWindowClosing
        if (!jTextFieldCliente.getText().equals("") || !jTextFieldDomicilio.getText().isEmpty() || !jTextFieldTelefonoFactura.getText().isEmpty() || !jTextAreaDetalleFactura.getText().isEmpty() || !jTextAreaSubTotal.getText().isEmpty() || !jTextFieldTotalFactura.getText().isEmpty() || !jTextFieldAbonaCliente.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vImprimirFactura.dispose();
                LimpiarSeleccionVenta();
                LimpiarSeleccionDetalleVenta();
            } else {
                vImprimirFactura.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_vImprimirFacturaWindowClosing

    private void jButtonSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarClienteActionPerformed
        vSeleccionarCliente.setSize(945, 520);
        vSeleccionarCliente.setLocationRelativeTo(this);
        vSeleccionarCliente.setModal(true);
        vSeleccionarCliente.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarClienteActionPerformed

    private void jTextFieldEnvioDineroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEnvioDineroKeyTyped
        char[] p = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', KeyEvent.VK_BACK_SPACE};
        int b = 0;
        for (int i = 0; i <= 11; i++) {
            if (p[i] == evt.getKeyChar()) {
                b = 1;
            }
        }
        if (b == 0) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextFieldEnvioDineroKeyTyped

    private void jButtonBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarClientesActionPerformed
        if (!jTextFieldNombre.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaCliente(jTextFieldNombre.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldDNI.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNI(jTextFieldDNI.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaTelefono(jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldDNI.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNICliente(jTextFieldDNI.getText(), jTextFieldNombre.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldNombre.getText().isEmpty() && jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaClienteTelefono(jTextFieldNombre.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldDNI.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNITelefono(jTextFieldDNI.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldDNI.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNIClienteTelefono(jTextFieldDNI.getText(), jTextFieldNombre.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos");
        }
    }//GEN-LAST:event_jButtonBuscarClientesActionPerformed

    private void vSeleccionarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarClienteMouseClicked
        LimpiarSeleccionProd();
        MostrarCliente();
        jTextFieldNombre.setText("");
        jTextFieldDNI.setText("");
        jTextFieldTelefono.setText("");
    }//GEN-LAST:event_vSeleccionarClienteMouseClicked

    private void jListClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListClientesMouseClicked
        int i = jListClientes.getSelectedIndex();
        ArrayList<String> array;
        if (i != -1) {
            jTextFieldCliente.setText(jListClientes.getSelectedValue());
            array = ventas.ObtenerDatosCliente(jTextFieldCliente.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    jTextFieldDomicilio.setText(array.get(j));
                    j++;
                    jTextFieldTelefonoFactura.setText(array.get(j));
                    j++;
                    jListClientes.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jListClientesMouseClicked

    private void jTextFieldClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldClienteKeyReleased
        ListaClientes();
    }//GEN-LAST:event_jTextFieldClienteKeyReleased

    private void jTableVentasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVentasMousePressed
        //SeleccionarFilas();
    }//GEN-LAST:event_jTableVentasMousePressed

    private void jButtonVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerDetalleActionPerformed
        int seleccionado = jTableVentas.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            nrofactura = jTableVentas.getValueAt(seleccionado, 3).toString();
            MostrarDetallesVentas();
            vDetallesVentas.setSize(817, 311);
            vDetallesVentas.setLocationRelativeTo(this);
            vDetallesVentas.setModal(true);
            vDetallesVentas.setVisible(true);
        }
    }//GEN-LAST:event_jButtonVerDetalleActionPerformed

    private void jButtonNuevoDetalleVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoDetalleVentaActionPerformed
        venta = new vVentas_Productos();
        vMenuPrincipal.jDesktopPane1.add(venta);
        venta.setVisible(true);
        this.dispose();
        vDetallesVentas.dispose();
    }//GEN-LAST:event_jButtonNuevoDetalleVentaActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        Movimientos_Caja mc = new Movimientos_Caja();
        DetallesVentas dv = new DetallesVentas();
        Ventas v = new Ventas();
        int seleccionado = jTableVentas.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mc.setIdmovimiento(Integer.parseInt(jTableVentas.getValueAt(seleccionado, 0).toString()));
                mc.setIdtipomovimiento(10);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    if (sql.SafeUpdates()) {
                        Object[][] datos = detalle.ObtenerDatosDetalleVentaDesdeListaVenta(jTableVentas.getValueAt(seleccionado, 3).toString());
                        for (int j = 0; j < datos.length; j++) {
                            for (int m = 0; m < datos.length; m++) {
                                int idproducto = (int) datos[j][m];
                                float cantidad = (float) datos[j][m + 1];
                                dv.setCantidad(cantidad);
                                dv.setIdproducto(idproducto);
                                detalle.SumarCantidadRestadaInsumos2(dv);
                                if (j < datos.length - 1) {
                                    j++;
                                    m--;
                                } else {
                                    break;
                                }
                            }
                        }
                        dv.setIdventa(Integer.parseInt(jTableVentas.getValueAt(seleccionado, 0).toString()));
                        if (detalle.EliminarDetalleDesdeListaVenta(dv)) {
                            v.setIdventa(Integer.parseInt(jTableVentas.getValueAt(seleccionado, 0).toString()));
                            if (ventas.EliminarVenta(v)) {
                                JOptionPane.showMessageDialog(null, "Venta Eliminada");
                                MostrarVentas();
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        //datoscompra = (DefaultTableModel) jTable1.getModel();
        if (VerificarCajaAbierta() == false) {
            int seleccionado = jTableVentas.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int idmovimientocaja = ventas.ObtenerIDMovVenta(Integer.parseInt(jTableVentas.getValueAt(seleccionado, 0).toString()), "VT");
                String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                if (estado.equals("CERRADA")) {
                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                } else {
                    fecha = jTableVentas.getValueAt(seleccionado, 5).toString();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Ventas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (venta == null || venta.isClosed()) {
                        venta = new vVentas_Productos();
                        vMenuPrincipal.jDesktopPane1.add(venta);
                        venta.setVisible(true);
                        venta.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        venta.toFront();
                    }
                    vVentas_Productos.btnGuardarVenta.setText("Cancelar");
                    vVentas_Productos.btnGuardarVenta.setEnabled(true);
                    vVentas_Productos.btnModificarVenta.setEnabled(true);
                    idventa = jTableVentas.getValueAt(seleccionado, 0).toString();
                    vVentas_Productos.cbxUsuario.setSelectedItem(jTableVentas.getValueAt(seleccionado, 2).toString());
                    vVentas_Productos.txtTotal.setText(jTableVentas.getValueAt(seleccionado, 4).toString());
                    if (jTableVentas.getValueAt(seleccionado, 6).equals("Local")) {
                        vVentas_Productos.jRadioLocal.setSelected(true);
                    } else {
                        vVentas_Productos.jRadioOnline.setSelected(true);
                    }
                    vVentas_Productos.jDateFecha.setDate(fechaseleccionada);
                    vVentas_Productos.idventa = idventa;
                    vVentas_Productos.nrofactura = nrofactura;
                    venta.total = Float.parseFloat(jTableVentas.getValueAt(seleccionado, 4).toString());
                    venta.setTitle("Modificar Venta");
                    ObtenerIDDetallesDesdeListaVentas();
                    CantidadFilasDetalles();
                    ObtenerDatosDetalleDesdeListaVentas();
                    dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarCliente;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonBuscarClientes;
    private javax.swing.JButton jButtonCancelarCliente;
    private javax.swing.JButton jButtonCancelarFactura;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonEliminarDetalleVenta;
    private javax.swing.JButton jButtonImprimir;
    private javax.swing.JButton jButtonImprimirFactura;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonModificarDetalleVenta;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JButton jButtonNuevoDetalleVenta;
    private javax.swing.JButton jButtonSeleccionarCliente;
    private javax.swing.JButton jButtonVerDetalle;
    public static com.toedter.calendar.JDateChooser jDateFechaDesde;
    public static com.toedter.calendar.JDateChooser jDateFechaHasta;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelAbonaFactura;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelDetalle;
    private javax.swing.JLabel jLabelDomicilio;
    private javax.swing.JLabel jLabelEnvioDinero;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelSeleccionarCliente;
    private javax.swing.JLabel jLabelSubTotal;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JLabel jLabelTelefonoFactura;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jListClientes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableClientes;
    public static javax.swing.JTable jTableDetallesVentas;
    public static javax.swing.JTable jTableVentas;
    private javax.swing.JTextArea jTextAreaDetalleFactura;
    private javax.swing.JTextArea jTextAreaSubTotal;
    private javax.swing.JTextField jTextFieldAbonaCliente;
    private javax.swing.JTextField jTextFieldCliente;
    private javax.swing.JTextField jTextFieldDNI;
    private javax.swing.JTextField jTextFieldDomicilio;
    private javax.swing.JTextField jTextFieldEnvioDinero;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldTelefono;
    private javax.swing.JTextField jTextFieldTelefonoFactura;
    private javax.swing.JTextField jTextFieldTotalFactura;
    private javax.swing.JDialog vDetallesVentas;
    private javax.swing.JDialog vImprimirFactura;
    private javax.swing.JDialog vSeleccionarCliente;
    // End of variables declaration//GEN-END:variables
}
