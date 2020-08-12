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
        jList2.setVisible(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (VerificarCajaAbierta() == false) {
                        //int fila = jTable1.rowAtPoint(e.getPoint());
                        int seleccionado = jTable1.getSelectedRow();
                        if (seleccionado == -1) {
                            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                        } else {
                            int idmovimientocaja = ventas.ObtenerIDMovVenta(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()), "VT");
                            String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                            if (estado.equals("CERRADA")) {
                                JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                            } else {
                                fecha = jTable1.getValueAt(seleccionado, 5).toString();
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
                                idventa = jTable1.getValueAt(seleccionado, 0).toString();
                                vVentas_Productos.cbxUsuario.setSelectedItem(jTable1.getValueAt(seleccionado, 2).toString());
                                vVentas_Productos.txtTotal.setText(jTable1.getValueAt(seleccionado, 4).toString());
                                if (jTable1.getValueAt(seleccionado, 6).equals("Local")) {
                                    vVentas_Productos.rbTipoVenta.setSelected(true);
                                } else {
                                    vVentas_Productos.jRadioButton2.setSelected(true);
                                }
                                vVentas_Productos.jDateChooser1.setDate(fechaseleccionada);
                                vVentas_Productos.idventa = idventa;
                                vVentas_Productos.nrofactura = nrofactura;
                                venta.total = Float.parseFloat(jTable1.getValueAt(seleccionado, 4).toString());
                                venta.setTitle("Modificar Venta");
                                IDDetalles2();
                                CantidadFilas();
                                PasarFilas2();
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

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int seleccionado = jTable1.getSelectedRow();
                    if (seleccionado == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        nrofactura = jTable1.getValueAt(seleccionado, 3).toString();
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
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDVENTA", "IDUSER", "USUARIO", "NRO FACTURA", "MONTO TOTAL", "FECHA", "TIPO VENTA"};
        datosventas = ventas.MostrarDatos(desde, hasta);
        datosventa = new DefaultTableModel(datosventas, columnas);
        jTable1.setModel(datosventa);
        EliminarFilasVaciasVentas();
        //AjustarTamañoFilasVenta();
        ocultar_columnasVenta();
    }

    public void MostrarDetallesVentas() {
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDDETALLE", "IDVENTA", "IDPRODUCTO", "NRO FACTURA VENTA", "PRODUCTO", "PRECIO", "CANTIDAD", "FECHA"};
        datosdetalles = detalle.MostrarDatos(desde, hasta, nrofactura);
        datosdetalle = new DefaultTableModel(datosdetalles, columnas);
        jTable2.setModel(datosdetalle);
        EliminarFilasVaciasDetalleVentas();
        //AjustarTamañoFilasDetalle();
        ocultar_columnasDetalleVenta();
    }

    public void MostrarCliente() {
        String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
        datostabla = ventas.MostrarClientes();
        tabla = new DefaultTableModel(datostabla, columnas);
        jTable3.setModel(tabla);
        EliminarFilasVaciasClientes();
        ocultar_columnasClientes();
    }

    public void ListaClientes() {
        listcliente = combo.list("clientes", "nombre", jTextField2.getText());
        String substr = jTextField2.getText().toLowerCase();
        list = new DefaultListModel();
        jList2.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listcliente.size(); i++) {
            if (listcliente.get(i) == null) {
                listcliente.remove(i);
            } else {
                String sublist = listcliente.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listcliente.get(i));
                    jList2.setVisible(true);
                    if (jTextField2.getText().isEmpty()) {
                        jList2.setVisible(false);
                    }
                }
            }
        }
    }

    public void IniciarFechas() {
        Date date = new Date();
        jDateChooser1.setDate(date);
        jDateChooser2.setDate(date);
    }

    public void AjustarTamañoFilasVenta() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int user = (int) font.getStringBounds(jTable1.getValueAt(i, 2).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int nrofactura = (int) font.getStringBounds(jTable1.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int monto = (int) font.getStringBounds(jTable1.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int date = (int) font.getStringBounds(jTable1.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (user > jTable1.getColumnModel().getColumn(2).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(2).setPreferredWidth(user);
                }
                if (nrofactura > jTable1.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(3).setPreferredWidth(nrofactura);
                }
                if (monto > jTable1.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(4).setPreferredWidth(monto);
                }
                if (date > jTable1.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(5).setPreferredWidth(date);
                }
            }
        }
    }

    public void AjustarTamañoFilasDetalle() {
        if (jTable2.getRowCount() != 0) {
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nrofactura = (int) font.getStringBounds(jTable2.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int producto = (int) font.getStringBounds(jTable2.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int precio = (int) font.getStringBounds(jTable2.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int cantidad = (int) font.getStringBounds(jTable2.getValueAt(i, 6).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nrofactura > jTable2.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(3).setPreferredWidth(nrofactura);
                }
                if (producto > jTable2.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(4).setPreferredWidth(producto);
                }
                if (precio > jTable2.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(5).setPreferredWidth(precio);
                }
                if (cantidad > jTable2.getColumnModel().getColumn(6).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(6).setPreferredWidth(cantidad);
                }
            }

        }
    }

    public void ocultar_columnasVenta() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    public void ocultar_columnasDetalleVenta() {
        jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(0).setMinWidth(0);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable2.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(1).setMinWidth(0);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable2.getColumnModel().getColumn(2).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(2).setMinWidth(0);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTable2.getColumnModel().getColumn(7).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(7).setMinWidth(0);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(0);
    }

    public void ocultar_columnasClientes() {
        jTable3.getColumnModel().getColumn(3).setMaxWidth(0);
        jTable3.getColumnModel().getColumn(3).setMinWidth(0);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(0);
    }

    public void LimpiarSeleccionVenta() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionDetalleVenta() {
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionProd() {
        jTable3.clearSelection();
        jTable3.getSelectionModel().clearSelection();
    }

    public void EliminarFilasVaciasVentas() {
        if (jTable1.getRowCount() != 0) {
            for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                    if (jTable1.getValueAt(fila, columna) == null) {
                        datosventa.removeRow(fila);
                    }
                }
            }

        }
    }

    public void EliminarFilasVaciasDetalleVentas() {
        if (jTable2.getRowCount() != 0) {
            for (int columna = 0; columna < jTable2.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable2.getRowCount(); fila++) {
                    if (jTable2.getValueAt(fila, columna) == null) {
                        datosdetalle.removeRow(fila);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasClientes() {
        if (jTable3.getRowCount() != 0) {
            for (int columna = 0; columna < jTable3.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable3.getRowCount(); fila++) {
                    if (jTable3.getValueAt(fila, columna) == null) {
                        tabla.removeRow(fila);
                    }
                }
            }
        }
    }

    public void nombresProductos() {
        int[] nombresprod = jTable2.getSelectedRows();
        for (int i = 0; i < nombresprod.length; i++) {
            venta.nomprod.add(jTable2.getValueAt(nombresprod[i], 4).toString());
        }
    }

    public void IDDetalles() {
        int[] seleccionados = jTable2.getSelectedRows();
        for (int i = 0; i < seleccionados.length; i++) {
            venta.iddetalles.add(jTable2.getValueAt(seleccionados[i], 0).toString());
        }
        //JOptionPane.showMessageDialog(null, venta.iddetalles);
    }

    public void IDDetalles2() {
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            venta.iddetalles.add(jTable2.getValueAt(i, 0).toString());
        }
    }

    public void CantidadFilas() {
        venta.filasdetalle = venta.iddetalles.size();
    }

    public void PasarFilas() {
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
    }

    public void PasarFilas2() {
        Object[] fila = new Object[3];
        for (int j = 0; j < jTable2.getRowCount(); j++) {
            fila[0] = jTable2.getValueAt(j, 4);
            fila[1] = jTable2.getValueAt(j, 5);
            fila[2] = jTable2.getValueAt(j, 6);
            venta.modelo.addRow(fila);
        }
    }

    public void IngresarDetalle() {
        int[] detalles = jTable2.getSelectedRows();
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < detalles.length; i++) {
            objetos.add(jTable2.getValueAt(detalles[i], 4));
            objetos.add(jTable2.getValueAt(detalles[i], 6));
            objetos.add(jTable2.getValueAt(detalles[i], 5));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextArea1.append(iterador.next() + " ");
        }
    }

    public void IngresarDetalle2() {
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            objetos.add(jTable2.getValueAt(i, 4));
            objetos.add(jTable2.getValueAt(i, 6));
            objetos.add(jTable2.getValueAt(i, 5));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextArea1.append(iterador.next() + " ");
        }
    }

    public void IngresarSubTotal() {
        int[] subtotales = jTable2.getSelectedRows();
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < subtotales.length; i++) {
            objetos.add(Double.parseDouble(jTable2.getValueAt(subtotales[i], 5).toString().trim()) * Double.parseDouble(jTable2.getValueAt(subtotales[i], 6).toString().trim()));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextArea2.append(iterador.next() + "\n");
        }
    }

    public void IngresarSubTotal2() {
        ArrayList<Object> objetos = new ArrayList<>();
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            objetos.add(Double.parseDouble(jTable2.getValueAt(i, 5).toString().trim()) * Double.parseDouble(jTable2.getValueAt(i, 6).toString().trim()));
        }
        Iterator iterador = objetos.listIterator();
        while (iterador.hasNext()) {
            jTextArea2.append(iterador.next() + "\n");
        }
    }

    public void IngresarTotal() {
        int total = jTable1.getSelectedRow();
        if (total != -1) {
            jTextField5.setText(jTable1.getValueAt(total, 4).toString());
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
        int seleccionado = jTable1.getSelectedRow();
        ArrayList<Integer> numeros = new ArrayList<>();
        for (int l = 0; l < jTable2.getRowCount(); l++) {
            if (jTable1.getValueAt(seleccionado, 3).equals(jTable2.getValueAt(l, 3))) {
                numeros.add(l);
                Rectangle r = jTable2.getCellRect(l, 3, true);
                jTable2.scrollRectToVisible(r);
            }
        }
        int num = 0;
        for (int i = 0; i < numeros.size(); i++) {
            num = numeros.get(i);
        }
        jTable2.getSelectionModel().setSelectionInterval(numeros.get(0), num);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vImprimirFactura = new javax.swing.JDialog();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jButton8 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jList2 = new javax.swing.JList<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        vSeleccionarCliente = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        vDetallesVentas = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jEtiqTitulo_DetalleCompras = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton1 = new javax.swing.JButton();
        jEtiqTitulo_Compras = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        vImprimirFactura.setTitle("Cargar Factura Venta");
        java.awt.Image iconodeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vImprimirFactura.setIconImage(iconodeliv);
        vImprimirFactura.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        vImprimirFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        vImprimirFactura.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vImprimirFacturaWindowClosing(evt);
            }
        });
        vImprimirFactura.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel13.setText("(*) Seleccionar Cliente");

        jLayeredPane1.setLayer(jButton8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTextField2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jList2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13)
                    .addComponent(jList2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jList2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vImprimirFactura.getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setText("(*) Dirección:");
        vImprimirFactura.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 123, -1, 22));

        jTextField3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 151, 531, 28));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("(*) Telefono:");
        vImprimirFactura.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 185, -1, 19));

        jTextField4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 209, 266, 29));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) DETALLE:");
        vImprimirFactura.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 256, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        vImprimirFactura.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 285, 588, 223));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel8.setText("(*) Sub Total");
        vImprimirFactura.getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 519, -1, -1));

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane4.setViewportView(jTextArea2);

        vImprimirFactura.getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 543, 294, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("(*) TOTAL");
        vImprimirFactura.getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 519, -1, -1));

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 543, 160, 31));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel10.setText("(*) El Cliente Abona CON:");
        vImprimirFactura.getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 580, -1, 13));

        jTextField6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        vImprimirFactura.getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 599, 160, 30));

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Imprimir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        vImprimirFactura.getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 640, 85, -1));

        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton7.setText("Cancelar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        vImprimirFactura.getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 640, 85, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("(*) ENVIO DE DINERO N°:");
        vImprimirFactura.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 63, -1, -1));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        vImprimirFactura.getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 408, 30));

        vSeleccionarCliente.setTitle("Seleccionar Cliente");
        java.awt.Image icondeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
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

        jTable3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable3);

        jButton9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton9.setText("Agregar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton10.setText("Cancelar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("Nombre");

        jTextField7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton11.setText("Buscar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel11.setText("DNI");

        jTextField8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel12.setText("Telefono");

        jTextField9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(387, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(383, 383, 383))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel11)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addContainerGap())
        );

        javax.swing.GroupLayout vSeleccionarClienteLayout = new javax.swing.GroupLayout(vSeleccionarCliente.getContentPane());
        vSeleccionarCliente.getContentPane().setLayout(vSeleccionarClienteLayout);
        vSeleccionarClienteLayout.setHorizontalGroup(
            vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(256, 256, 256)
                        .addComponent(jButton10)
                        .addContainerGap(290, Short.MAX_VALUE))
                    .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                        .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
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
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vDetallesVentas.setTitle("Detalle Venta");
        java.awt.Image icon = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vDetallesVentas.setIconImage(icon);

        jTable2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jButton4.setBackground(new java.awt.Color(252, 249, 57));
        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(252, 249, 57));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(252, 249, 57));
        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Imprimir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jEtiqTitulo_DetalleCompras.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jEtiqTitulo_DetalleCompras.setText("Detalles Ventas");

        jButton12.setBackground(new java.awt.Color(252, 249, 57));
        jButton12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton12.setText("Nuevo");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vDetallesVentasLayout = new javax.swing.GroupLayout(vDetallesVentas.getContentPane());
        vDetallesVentas.getContentPane().setLayout(vDetallesVentasLayout);
        vDetallesVentasLayout.setHorizontalGroup(
            vDetallesVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vDetallesVentasLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vDetallesVentasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(vDetallesVentasLayout.createSequentialGroup()
                .addGap(318, 318, 318)
                .addComponent(jEtiqTitulo_DetalleCompras)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vDetallesVentasLayout.setVerticalGroup(
            vDetallesVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vDetallesVentasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jEtiqTitulo_DetalleCompras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(vDetallesVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado Ventas y Detalles Ventas");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setVisible(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Nuevo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jEtiqTitulo_Compras.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jEtiqTitulo_Compras.setText("Ventas");

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Desde");

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setText("BUSCAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jDateChooser1.setMinimumSize(new java.awt.Dimension(32, 20));

        jDateChooser2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

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
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(66, 66, 66)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jButton13.setBackground(new java.awt.Color(252, 249, 57));
        jButton13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton13.setText("Ver Detalle");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(252, 249, 57));
        jButton14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton14.setText("Eliminar");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(374, 374, 374)
                        .addComponent(jEtiqTitulo_Compras, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(192, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEtiqTitulo_Compras)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila = jTable1.getSelectedRow();
        int seleccionado = jTable2.getSelectedRow();
        Ventas v = new Ventas();
        DetallesVentas dt = new DetallesVentas();
        Movimientos_Caja mc = new Movimientos_Caja();
        control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Eliminar Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int cantidad = jTable2.getRowCount();
                if (cantidad == 1) {
                    mc.setIdmovimiento(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                    mc.setIdtipomovimiento(10);
                    if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                        dt.setCantidad(Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()));
                        dt.setIdproducto(Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                        detalle.SumarCantidadRestadaInsumos2(dt);
                        dt.setIddetalleventa(Integer.parseInt(jTable2.getValueAt(seleccionado, 0).toString()));
                        detalle.EliminarDetalleVenta(dt);
                        v.setIdventa(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
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
                    float totalventa = Float.parseFloat(jTable1.getValueAt(fila, 4).toString()),
                            valor = Float.parseFloat(jTable2.getValueAt(seleccionado, 5).toString())
                            * Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()),
                            totalfinal = totalventa - valor;
                    v.setMontototal(totalfinal);
                    v.setIdventa(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                    if (ventas.ActualizarTotalVenta(v)) {
                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()), 10));
                        mc.setDescripcion("VENTA PRODUCTOS");
                        mc.setIdtipomovimiento(10);
                        mc.setIdusuario(Session.getIdusuario());
                        mc.setFecha_movimiento(jTable1.getValueAt(fila, 5).toString());
                        mc.setMonto(v.getMontototal());
                        mc.setIdmovimiento(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                        mc.setTipoVenta(jTable1.getValueAt(fila, 6).toString());
                        if (control_mc.EditarMovimientosCaja(mc)) {
                            dt.setCantidad(Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()));
                            dt.setIdproducto(Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                            detalle.SumarCantidadRestadaInsumos2(dt);
                            dt.setIddetalleventa(Integer.parseInt(jTable2.getValueAt(seleccionado, 0).toString()));
                            detalle.EliminarDetalleVenta(dt);
                            JOptionPane.showMessageDialog(null, "Eliminado");
                            MostrarDetallesVentas();
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        venta = new vVentas_Productos();
        vMenuPrincipal.jDesktopPane1.add(venta);
        venta.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            if (VerificarCajaAbierta() == false) {
                int idmovimientocaja = ventas.ObtenerIDMovVenta(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()), "VT");
                String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                if (estado.equals("CERRADA")) {
                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                } else {
                    fecha = jTable1.getValueAt(seleccionado, 5).toString();
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
                    idventa = jTable1.getValueAt(seleccionado, 0).toString();
                    vVentas_Productos.cbxUsuario.setSelectedItem(jTable1.getValueAt(seleccionado, 2).toString());
                    vVentas_Productos.txtTotal.setText(jTable1.getValueAt(seleccionado, 4).toString());
                    if (jTable1.getValueAt(seleccionado, 6).equals("Local")) {
                        vVentas_Productos.rbTipoVenta.setSelected(true);
                    } else {
                        vVentas_Productos.jRadioButton2.setSelected(true);
                    }
                    vVentas_Productos.jDateChooser1.setDate(fechaseleccionada);
                    vVentas_Productos.idventa = idventa;
                    vVentas_Productos.nrofactura = nrofactura;
                    venta.total = Float.parseFloat(jTable1.getValueAt(seleccionado, 4).toString());
                    venta.setTitle("Modificar Venta");
                    IDDetalles2();
                    CantidadFilas();
                    PasarFilas2();
                    dispose();
                    vDetallesVentas.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccionVenta();
        //LimpiarSeleccionDetalleVenta();
        desde = "";
        hasta = "";
        IniciarFechas();
        MostrarVentas();
        //MostrarDetallesVentas();
    }//GEN-LAST:event_formMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateChooser2.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    datosventas = busquedaventa.MostrarDatosNroVenta(desde, hasta);
                    if (datosventas.length != 0) {
                        String[] columnas = {"IDVENTA", "IDUSER", "USUARIO", "NRO FACTURA", "MONTO TOTAL", "FECHA", "TIPO VENTA"};
                        datosventa = new DefaultTableModel(datosventas, columnas);
                        jTable1.setModel(datosventa);
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
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        IngresarDetalle2();
        IngresarSubTotal2();
        IngresarTotal();
        vImprimirFactura.setSize(630, 715);
        vImprimirFactura.setLocationRelativeTo(this);
        vImprimirFactura.setModal(true);
        vImprimirFactura.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (!jTextField2.getText().equals("") || !jTextField3.getText().isEmpty() || !jTextField4.getText().isEmpty() || !jTextArea1.getText().isEmpty() || !jTextArea2.getText().isEmpty() || !jTextField5.getText().isEmpty() || !jTextField6.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vImprimirFactura.dispose();
                LimpiarSeleccionVenta();
                LimpiarSeleccionDetalleVenta();
            } else {
                vImprimirFactura.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (jTable3.getRowCount() != 0) {
            int i = jTable3.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                vSeleccionarCliente.dispose();
                jTextField2.setText(jTable3.getValueAt(i, 1).toString() + " " + jTable3.getValueAt(i, 2).toString());
                jTextField3.setText(jTable3.getValueAt(i, 3).toString());
                jTextField4.setText(jTable3.getValueAt(i, 4).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos clientes todavia");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!jTextField1.getText().isEmpty() && !jTextField2.getText().equals("") && !jTextField3.getText().isEmpty() && !jTextField4.getText().isEmpty() && !jTextArea1.getText().isEmpty() && !jTextArea2.getText().isEmpty() && !jTextField5.getText().isEmpty() && !jTextField6.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Confirmar Factura Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                reporte.CargarFactura(IngresarNroPedido(), jTextField1.getText(), jTextField2.getText(), jTextField3.getText(), jTextField4.getText(), jTextArea1.getText(), jTextArea2.getText(), jTextField5.getText(), jTextField6.getText());
                vImprimirFactura.dispose();
            } else {
                vImprimirFactura.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarCliente.dispose();
        } else {
            vSeleccionarCliente.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void vSeleccionarClienteWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarClienteWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarCliente.dispose();
        } else {
            vSeleccionarCliente.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarClienteWindowClosing

    private void vImprimirFacturaWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vImprimirFacturaWindowClosing
        if (!jTextField2.getText().equals("") || !jTextField3.getText().isEmpty() || !jTextField4.getText().isEmpty() || !jTextArea1.getText().isEmpty() || !jTextArea2.getText().isEmpty() || !jTextField5.getText().isEmpty() || !jTextField6.getText().isEmpty()) {
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

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        vSeleccionarCliente.setSize(945, 520);
        vSeleccionarCliente.setLocationRelativeTo(this);
        vSeleccionarCliente.setModal(true);
        vSeleccionarCliente.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
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
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (!jTextField7.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaCliente(jTextField7.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField8.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNI(jTextField8.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField9.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaTelefono(jTextField9.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField8.getText().isEmpty() && !jTextField7.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNICliente(jTextField8.getText(), jTextField7.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField7.getText().isEmpty() && jTextField9.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaClienteTelefono(jTextField7.getText(), jTextField9.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField8.getText().isEmpty() && !jTextField9.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNITelefono(jTextField8.getText(), jTextField9.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField8.getText().isEmpty() && !jTextField7.getText().isEmpty() && !jTextField9.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNIClienteTelefono(jTextField8.getText(), jTextField7.getText(), jTextField9.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(tabla);
                EliminarFilasVaciasClientes();
                ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos");
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void vSeleccionarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarClienteMouseClicked
        LimpiarSeleccionProd();
        MostrarCliente();
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
    }//GEN-LAST:event_vSeleccionarClienteMouseClicked

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        ArrayList<String> array;
        if (i != -1) {
            jTextField2.setText(jList2.getSelectedValue());
            array = ventas.ObtenerDatosCliente(jTextField2.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    jTextField3.setText(array.get(j));
                    j++;
                    jTextField4.setText(array.get(j));
                    j++;
                    jList2.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        ListaClientes();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        //SeleccionarFilas();
    }//GEN-LAST:event_jTable1MousePressed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            nrofactura = jTable1.getValueAt(seleccionado, 3).toString();
            MostrarDetallesVentas();
            vDetallesVentas.setSize(817, 311);
            vDetallesVentas.setLocationRelativeTo(this);
            vDetallesVentas.setModal(true);
            vDetallesVentas.setVisible(true);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        venta = new vVentas_Productos();
        vMenuPrincipal.jDesktopPane1.add(venta);
        venta.setVisible(true);
        this.dispose();
        vDetallesVentas.dispose();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        Movimientos_Caja mc = new Movimientos_Caja();
        DetallesVentas dv = new DetallesVentas();
        Ventas v = new Ventas();
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mc.setIdmovimiento(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                mc.setIdtipomovimiento(10);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    if (sql.SafeUpdates()) {
                        Object[][] datos = detalle.ObtenerDatosDetalleVentaDesdeListaVenta(jTable1.getValueAt(seleccionado, 3).toString());
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
                        dv.setIdventa(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                        if (detalle.EliminarDetalleDesdeListaVenta(dv)) {
                            v.setIdventa(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                            if (ventas.EliminarVenta(v)) {
                                JOptionPane.showMessageDialog(null, "Venta Eliminada");
                                MostrarVentas();
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    public static com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jEtiqTitulo_Compras;
    private javax.swing.JLabel jEtiqTitulo_DetalleCompras;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JDialog vDetallesVentas;
    private javax.swing.JDialog vImprimirFactura;
    private javax.swing.JDialog vSeleccionarCliente;
    // End of variables declaration//GEN-END:variables
}
