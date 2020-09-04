package Vistas;

import Controlador.control_BusquedasMovimientosCaja;
import Controlador.control_Caja_Turno;
import Controlador.control_Cajas;
import Controlador.control_Egresos;
import Controlador.control_Movimientos_Caja;
import Controlador.control_Turnos;
import Controlador.control_existencias;
import Modelo.Caja;
import Modelo.Caja_Turno;
import Modelo.Turnos;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import static Vistas.vMenuPrincipal.jMenuItem12;
import static Vistas.vMenuPrincipal.jMenuItem29;
import static Vistas.vMenuPrincipal.jMenuItem30;
import java.awt.Color;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import static Vistas.vMenuPrincipal.jMenuCompras;
import static Vistas.vMenuPrincipal.jMenuVentas;
import static Vistas.vMenuPrincipal.JMenuInsumos;
import static Vistas.vMenuPrincipal.jMenuEmpleados;
import static Vistas.vMenuPrincipal.jMenuReportes;
import static Vistas.vMenuPrincipal.jMenuGastos;
import static Vistas.vMenuPrincipal.jMenuConfiguracion;
import static Vistas.vMenuPrincipal.jButtonVentas;
import static Vistas.vMenuPrincipal.jButtonCompras;
import static Vistas.vMenuPrincipal.jButtonGastos;
import static Vistas.vMenuPrincipal.jButtonInsumos;
import static Vistas.vMenuPrincipal.jButtonProveedores;

/**
 *
 * @author CRISTIAN
 */
public final class vMovimientos_Caja extends javax.swing.JInternalFrame {

    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    Movimientos_Caja mc = new Movimientos_Caja();
    control_existencias ce = new control_existencias();
    vGestion_Movimientos_Caja gmc = null;
    vGestion_Egresos egresos = new vGestion_Egresos();
    control_Egresos egreso = new control_Egresos();
    vAbrir_Caja caja = new vAbrir_Caja();
    control_Cajas cc = new control_Cajas();
    control_Caja_Turno cct = new control_Caja_Turno();
    control_Turnos ct = new control_Turnos();
    control_BusquedasMovimientosCaja buscarmov = new control_BusquedasMovimientosCaja();
    Turnos turno = new Turnos();
    Object[][] datostabla;
    DefaultTableModel datos;
    Object[] turnos;
    ArrayList<String> listmov;
    DefaultListModel list;

    /**
     * Creates new form jMovimientos_Caja
     */
    public vMovimientos_Caja() {
        initComponents();
        inicializarComponentes();
        Mostrar();
        ComboTurnos();
        EliminarItemsVacios();
        jListaMovimientos.setVisible(false);
    }

    public void verificarCajaAbierta() {
        if (cc.existeCajaAbierta()) {
            int idcaja = Session.getIdcaja_abierta();
            //verificamos si la caja fue abierta en este turno
            Caja caja;
            if (idcaja == 0) {
                caja = cc.obtenerUltimaCaja();
                Caja_Turno caja_turno = cct.obtenerCajaTurno(caja.getIdCaja());
                Session.setIdcaja_abierta(caja.getIdCaja());
                Session.setIdturno_abierto(caja_turno.getIdTurno());
                //System.out.println(caja.getIdCaja()+"--"+caja_turno.getIdTurno()+"--"+caja.getMonto());
            } else {
                caja = cc.obtenerCaja(idcaja);
            }
            //cbxTurnos.setSelectedIndex();
            vAbrir_Caja.jTextCajaChica.setText(Float.toString(caja.getMonto()));
            vAbrir_Caja.jLabelMensaje.setText("Existe una Caja Abierta para el Turno");
        } else {
            //sino hay Caja abierta entonces podemos abrir una Caja
            //obtener el saldo de la Ultima Caja
            Caja caja = cc.obtenerUltimaCaja();
            Double monto_caja_anterior = cct.getCierreCaja(caja);
            vAbrir_Caja.jTextCajaChica.setText(Double.toString(monto_caja_anterior));
        }
    }

    public void inicializarComponentes() {
        Date fecha = new Date();
        jFechaDesde.setDate(fecha);
        jFechaHasta.setDate(fecha);
    }

    public void Mostrar() {
        String[] columnas = {"IDMOVIMIENTOCAJA", "IDCAJATURNO", "NRO.MOVIMIENTO", "CONCEPTO", "TIPO MOVIMIENTO", "TURNO", "FECHA MOVIMIENTO", "INGRESO", "EGRESO", "DETALLE", "IDMOVIMIENTO", "TIPO VENTA"};
        datostabla = control_mc.MostrarDatos(jFechaDesde.getDate(), jFechaHasta.getDate());
        datos = new DefaultTableModel(datostabla, columnas);
        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTabla_MovCaja.setModel(datos);
        //System.out.println("cant  "+datos.getRowCount());
        if (datos.getRowCount() > 0) {
            CalcularTotales(datos);
        }
        NullTipoVenta();
        EliminarFilasVacias();
        modificar_columnas();
    }
    
    public void ListaMovimientos(){
        listmov = ce.list("tiposmovimientos", "descripcion", txtbuscar.getText());
        String substr = txtbuscar.getText().toLowerCase();
        list = new DefaultListModel();
        jListaMovimientos.setModel(list);
        list.removeAllElements();
        for(int i=0; i < listmov.size(); i++){
            if(listmov.get(i) == null){
                listmov.remove(i);
            } else {
                String sublist = listmov.get(i).toLowerCase();
                if(sublist.contains(substr)){
                    list.addElement(listmov.get(i));
                    jListaMovimientos.setVisible(true);
                    if(txtbuscar.getText().isEmpty()){
                        jListaMovimientos.setVisible(false);
                    }
                }
            }
        }
    }

    public void NullTipoVenta() {
        if (jTabla_MovCaja.getRowCount() != 0) {
            for (int fila = 0; fila < jTabla_MovCaja.getRowCount(); fila++) {
                if (jTabla_MovCaja.getValueAt(fila, 11) == null) {
                    jTabla_MovCaja.setValueAt("-", fila, 11);
                }
            }
        }
    }

    public void EliminarFilasVacias() {
        if (jTabla_MovCaja.getRowCount() != 0) {
            for (int columna = 0; columna < jTabla_MovCaja.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTabla_MovCaja.getRowCount(); fila++) {
                    if (jTabla_MovCaja.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < cbxTurnos.getItemCount(); i++) {
            if (cbxTurnos.getItemAt(i) == null) {
                cbxTurnos.remove(i);
            }
        }
    }

    public void CalcularTotales(DefaultTableModel tabla) {
        float total_ingreso = calcularTotal(tabla, 7);
        float total_egreso = calcularTotal(tabla, 8);
        txtTotalIngreso.setText(Float.toString(total_ingreso));
        txtTotalEgreso.setText(Float.toString(total_egreso));
        txtTotalCaja.setText(Float.toString(total_ingreso - total_egreso));
    }

    public float calcularTotal(DefaultTableModel dtm, int NroColumna) {
        float total = 0;
        for (int fila = 0; fila < dtm.getRowCount(); fila++) {
            String valor = "";
            if (dtm.getValueAt(fila, NroColumna) != null) {
                valor = dtm.getValueAt(fila, NroColumna).toString();
            } else {
                valor = "0";
            }
            if (valor.equals("-")) {
                valor = "0";
            }
            total = total + Float.parseFloat(valor); // la columna 2 es la de costo.
        }
        return total;
    }

    private void ComboTurnos() {
        turnos = ce.getCatalogos("turnos", "idturno", "descripcion");
        cbxTurnos.removeAllItems();
        String dato, nomtipo;
        int idtipo;
        Turnos turno;
        nomtipo = "Seleccionar Turno...";
        cbxTurnos.addItem(nomtipo);
        for (Object te : turnos) {
            dato = (String) te;
            idtipo = Integer.parseInt(dato.substring(0, dato.indexOf("#")));
            nomtipo = dato.substring(dato.indexOf("#") + 1, dato.length());
            //completo el combo
            cbxTurnos.addItem(nomtipo);
        }
    }

    public void modificar_columnas() {
        jTabla_MovCaja.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(0).setMinWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(1).setMaxWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(1).setMinWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTabla_MovCaja.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTabla_MovCaja.getColumnModel().getColumn(4).setPreferredWidth(200);
        jTabla_MovCaja.getColumnModel().getColumn(5).setPreferredWidth(70);
        jTabla_MovCaja.getColumnModel().getColumn(6).setPreferredWidth(110);
        jTabla_MovCaja.getColumnModel().getColumn(7).setPreferredWidth(90);
        jTabla_MovCaja.getColumnModel().getColumn(8).setPreferredWidth(90);
        jTabla_MovCaja.getColumnModel().getColumn(9).setMaxWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(9).setMinWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(9).setPreferredWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(10).setMaxWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(10).setMinWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(10).setPreferredWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(11).setMaxWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(11).setMinWidth(0);
        jTabla_MovCaja.getColumnModel().getColumn(11).setPreferredWidth(0);
    }

    public void BuscarMovimientos() {
        String[] columnas = {"IDMOVIMIENTOCAJA", "IDCAJATURNO", "NRO.MOVIMIENTO", "CONCEPTO", "TIPO MOVIMIENTO", "TURNO", "FECHA MOVIMIENTO", "INGRESO", "EGRESO", "DETALLE", "IDMOVIMIENTO", "TIPO VENTA"};
        String fecha1 = ((JTextField) jFechaDesde.getDateEditor().getUiComponent()).getText(), fecha2 = ((JTextField) jFechaHasta.getDateEditor().getUiComponent()).getText();
        if (jFechaDesde.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jFechaHasta.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!txtbuscar.getText().isEmpty() && !cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && !fecha1.isEmpty() && !fecha2.isEmpty()) {
                    datostabla = buscarmov.buscarMovimientosTodos(txtbuscar.getText(), cbxTurnos.getSelectedItem().toString(), fecha1, fecha2);

                    if (datostabla.length != 0) {
                        datos = new DefaultTableModel(datostabla, columnas);
                        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        jTabla_MovCaja.setModel(datos);
                        modificar_columnas();
                        if (datos.getRowCount() > 0) {
                            CalcularTotales(datos);
                        }
                        NullTipoVenta();
                        EliminarFilasVacias();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    }
                } else if (txtbuscar.getText().isEmpty() && !cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && !fecha1.isEmpty() && !fecha2.isEmpty()) {
                    datostabla = buscarmov.buscarMovimientosFechasTurnos(cbxTurnos.getSelectedItem().toString(), fecha1, fecha2);

                    if (datostabla.length != 0) {
                        datos = new DefaultTableModel(datostabla, columnas);
                        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        jTabla_MovCaja.setModel(datos);
                        modificar_columnas();
                        if (datos.getRowCount() > 0) {
                            CalcularTotales(datos);
                        }
                        NullTipoVenta();
                        EliminarFilasVacias();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    }
                } else if (!txtbuscar.getText().isEmpty() && cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && !fecha1.isEmpty() && !fecha2.isEmpty()) {
                    datostabla = buscarmov.buscarMovimientosFechaMov(txtbuscar.getText(), fecha1, fecha2);

                    if (datostabla.length != 0) {
                        datos = new DefaultTableModel(datostabla, columnas);
                        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        jTabla_MovCaja.setModel(datos);
                        modificar_columnas();
                        if (datos.getRowCount() > 0) {
                            CalcularTotales(datos);
                        }
                        NullTipoVenta();
                        EliminarFilasVacias();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    }
                } else if (!txtbuscar.getText().isEmpty() && !cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && fecha1.isEmpty() && fecha2.isEmpty()) {
                    datostabla = buscarmov.buscarMovimientosMovTurnos(txtbuscar.getText(), cbxTurnos.getSelectedItem().toString());

                    if (datostabla.length != 0) {
                        datos = new DefaultTableModel(datostabla, columnas);
                        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        jTabla_MovCaja.setModel(datos);
                        modificar_columnas();
                        if (datos.getRowCount() > 0) {
                            CalcularTotales(datos);
                        }
                        NullTipoVenta();
                        EliminarFilasVacias();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    }
                } else if (txtbuscar.getText().isEmpty() && cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && !fecha1.isEmpty() && !fecha2.isEmpty()) {
                    datostabla = buscarmov.buscarMovimientosFechas(fecha1, fecha2);

                    if (datostabla.length != 0) {
                        datos = new DefaultTableModel(datostabla, columnas);
                        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        jTabla_MovCaja.setModel(datos);
                        modificar_columnas();
                        if (datos.getRowCount() > 0) {
                            CalcularTotales(datos);
                        }
                        NullTipoVenta();
                        EliminarFilasVacias();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    }
                } else if (!txtbuscar.getText().isEmpty() && cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && fecha1.isEmpty() && fecha2.isEmpty()) {
                    datostabla = buscarmov.buscarMovimientosMov(txtbuscar.getText());

                    if (datostabla.length != 0) {
                        datos = new DefaultTableModel(datostabla, columnas);
                        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        jTabla_MovCaja.setModel(datos);
                        modificar_columnas();
                        if (datos.getRowCount() > 0) {
                            CalcularTotales(datos);
                        }
                        NullTipoVenta();
                        EliminarFilasVacias();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    }
                } else if (txtbuscar.getText().isEmpty() && !cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && fecha1.isEmpty() && fecha2.isEmpty()) {
                    datostabla = buscarmov.buscarMovimientosTurnos(cbxTurnos.getSelectedItem().toString());

                    if (datostabla.length != 0) {
                        datos = new DefaultTableModel(datostabla, columnas);
                        //jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        jTabla_MovCaja.setModel(datos);
                        modificar_columnas();
                        if (datos.getRowCount() > 0) {
                            CalcularTotales(datos);
                        }
                        NullTipoVenta();
                        EliminarFilasVacias();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    }
                } else if (txtbuscar.getText().isEmpty() && cbxTurnos.getSelectedItem().equals("Seleccionar una opción") && fecha1.isEmpty() && fecha2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Deben completar los campos");
                } else if (!fecha1.isEmpty() || fecha2.isEmpty() || fecha1.isEmpty() || !fecha2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Para realizar la busqueda con fechas, ambas deben estar establecidas");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
        }
    }

    public void DeshabilitarAperturaAnulada() {
        jMenuCompras.setEnabled(false);
        jMenuVentas.setEnabled(false);
        JMenuInsumos.setEnabled(false);
        jMenuEmpleados.setEnabled(false);
        jMenuReportes.setEnabled(false);
        jMenuGastos.setEnabled(false);
        jMenuConfiguracion.setEnabled(false);
        jMenuItem12.setEnabled(false);
        jMenuItem30.setEnabled(false);
        jMenuItem29.setEnabled(false);
        jButtonVentas.setEnabled(false);
        jButtonCompras.setEnabled(false);
        jButtonGastos.setEnabled(false);
        jButtonProveedores.setEnabled(false);
        jButtonInsumos.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_MovCaja = jTabla_MovCaja= new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTotalIngreso = new javax.swing.JTextField();
        txtTotalEgreso = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTotalCaja = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jFechaDesde = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        cbxTurnos = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jFechaHasta = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();
        txtbuscar = new javax.swing.JTextField();
        jListaMovimientos = new javax.swing.JList<>();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Movimientos de Caja");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(1028, 490));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setOpaque(false);

        jTabla_MovCaja.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTabla_MovCaja.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTabla_MovCaja);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 992, 210));

        btnAgregar.setBackground(new java.awt.Color(252, 249, 57));
        btnAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnAgregar.setText("Nuevo");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, 100, 30));

        btnEditar.setBackground(new java.awt.Color(252, 249, 57));
        btnEditar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEditar.setText("Modificar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 420, 100, 30));

        btnEliminar.setBackground(new java.awt.Color(252, 249, 57));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 100, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("Ingreso:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 380, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel10.setText("Egreso:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 380, -1, -1));

        txtTotalIngreso.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtTotalIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalIngresoActionPerformed(evt);
            }
        });
        txtTotalIngreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalIngresoKeyReleased(evt);
            }
        });
        getContentPane().add(txtTotalIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 380, 108, -1));

        txtTotalEgreso.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtTotalEgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalEgresoActionPerformed(evt);
            }
        });
        txtTotalEgreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalEgresoKeyReleased(evt);
            }
        });
        getContentPane().add(txtTotalEgreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 380, 105, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel11.setText("TOTAL:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 420, -1, -1));

        txtTotalCaja.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtTotalCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCajaActionPerformed(evt);
            }
        });
        txtTotalCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalCajaKeyReleased(evt);
            }
        });
        getContentPane().add(txtTotalCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 420, 105, -1));

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Buscar Movimientos:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("Fecha Desde:");

        jFechaDesde.setDateFormatString("dd-MM-yyyy");
        jFechaDesde.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel8.setText("Turno:");

        cbxTurnos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        cbxTurnos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("Fecha Hasta:");

        jFechaHasta.setDateFormatString("dd-MM-yyyy");
        jFechaHasta.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N

        btnBuscar.setBackground(new java.awt.Color(252, 249, 57));
        btnBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtbuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addComponent(cbxTurnos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFechaDesde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(32, 32, 32))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 850, 130));

        jListaMovimientos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jListaMovimientos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListaMovimientosMouseClicked(evt);
            }
        });
        getContentPane().add(jListaMovimientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 238, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed

    }//GEN-LAST:event_txtbuscarActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        ListaMovimientos();
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
//        LimpiarSeleccion();
        //String[] columnas = {"idmovimientocaja","Movimiento","Fecha Movimiento","Monto","TipoMovimiento","Turno","Nro.Movimiento","detalle","idmovimiento"};   
        BuscarMovimientos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        egresos = new vGestion_Egresos();
        vMenuPrincipal.jDesktopPane1.add(egresos);
        egresos.setTitle("Administrar Movimientos Cajas");
        vGestion_Egresos.jEtiqTipo_Insumos.setText("(*) Tipo de Movimiento:");
        egresos.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        String fecha;
        Timestamp fechaseleccionada = null;
        int fila = jTabla_MovCaja.getSelectedRow();
        //System.out.println("fila "+fila);
        if (fila == -1 || jTabla_MovCaja.getValueAt(fila, 0) == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            String cierrecaja = control_mc.IdentificarCierreCaja(jTabla_MovCaja.getValueAt(fila, 4).toString());
            String concepto = control_mc.IdentificarIngEg(jTabla_MovCaja.getValueAt(fila, 4).toString());
            String tipomov = control_mc.IdentificarTipoMov(jTabla_MovCaja.getValueAt(fila, 4).toString());
            if (!tipomov.equals("CIERRE DE CAJA DIARIO")) {
                if (!tipomov.equals("COMPRA DE INSUMOS")) {
                    if (!tipomov.equals("PAGO A EMPLEADOS")) {
                        if (concepto.equals("EGRESO")) {
                            int idmovimiento = Integer.parseInt(jTabla_MovCaja.getValueAt(fila, 0).toString());
                            String est = control_mc.getEstadoCajaByMovimiento(idmovimiento);
                            if (est.equals("CERRADA")) {
                                JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                            } else {
                                if (idmovimiento > 0) {
                                    String idmovimientocaja = (jTabla_MovCaja.getValueAt(fila, 10).toString());
                                    //String idmovimientocaja = jTabla_MovCaja.getValueAt(fila, 0).toString();
                                    fecha = (String) (jTabla_MovCaja.getValueAt(fila, 6));
                                    //String subfecha=fecha.substring(0, 10);
                                    //DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    try {
                                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                                    } catch (ParseException ex) {
                                        Logger.getLogger(vMovimientos_Caja.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    egresos = new vGestion_Egresos();
                                    vMenuPrincipal.jDesktopPane1.add(egresos);
                                    vGestion_Egresos.jBotonAgregar_Egresos.setEnabled(false);
                                    vGestion_Egresos.jTextDescripcion_Egresos.setText(jTabla_MovCaja.getValueAt(fila, 3).toString());
                                    vGestion_Egresos.jCBTipoEgreso_Egresos.setSelectedItem(jTabla_MovCaja.getValueAt(fila, 4).toString());
                                    vGestion_Egresos.jDateFecha_Egresos.setDate(fechaseleccionada);
                                    vGestion_Egresos.jTextDetalle_Egresos.setText(jTabla_MovCaja.getValueAt(fila, 9).toString());
                                    if (!jTabla_MovCaja.getValueAt(fila, 7).toString().equals("-")) {
                                        vGestion_Egresos.jTextMonto_Egresos.setText(jTabla_MovCaja.getValueAt(fila, 7).toString());
                                    } else {
                                        vGestion_Egresos.jTextMonto_Egresos.setText(jTabla_MovCaja.getValueAt(fila, 8).toString());
                                    }
                                    egresos.id = egreso.ObtenerIDEgreso(jTabla_MovCaja.getValueAt(fila, 2).toString());
                                    egresos.NroMovimiento = jTabla_MovCaja.getValueAt(fila, 2).toString();
                                    egresos.setTitle("Administrar Movimientos Cajas");
                                    vGestion_Egresos.jEtiqTipo_Insumos.setText("(*) Tipo de Movimiento:");
                                    egresos.setVisible(true);
                                    dispose();
                                }
                            }
                        } else {
                            int idmovimiento = (Integer.parseInt(jTabla_MovCaja.getValueAt(fila, 10).toString()));
                            if (idmovimiento == 0) {
                                int idmovimientocaja = Integer.parseInt(jTabla_MovCaja.getValueAt(fila, 0).toString());
                                String est = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                                if (est.equals("ABIERTA")) {
                                    vMenuPrincipal.jDesktopPane1.add(caja);
                                    caja.setTitle("Modificar Apertura Caja");
                                    vAbrir_Caja.cbxTurnos.setEnabled(true);
                                    vAbrir_Caja.jTextCajaChica.setEnabled(true);
                                    vAbrir_Caja.jTextCajaChica.setText(jTabla_MovCaja.getValueAt(fila, 7).toString());
                                    vAbrir_Caja.jLabelMensaje.setText("Esta modificando Apertura de Caja");
                                    vAbrir_Caja.btnAbrirCaja.setText("Modificar Caja");
                                    vAbrir_Caja.btnAbrirCaja.setEnabled(true);
                                    vAbrir_Caja.btnCancelar.setText("Cancelar Modificación");
                                    vAbrir_Caja.btnCancelar.setEnabled(true);
                                    caja.id = Integer.toString(idmovimientocaja);
                                    caja.setVisible(true);
                                    dispose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Dicho concepto no es GASTO.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El movimmiento debe modificarse en el Origen");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El movimmiento debe modificarse en el Origen");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int seleccionado = jTabla_MovCaja.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int idmovimientocaja = Integer.parseInt(jTabla_MovCaja.getValueAt(seleccionado, 0).toString());
                mc.setIdmovimientocaja(idmovimientocaja);
                int idmovimiento = Integer.parseInt(jTabla_MovCaja.getValueAt(seleccionado, 10).toString());
                //System.out.println(jTabla_MovCaja.getValueAt(seleccionado, 0));
                //validar si tiene un Movimiento asociado entoncesno se puede borrar desde aqui
                if (idmovimiento > 0) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar Movimiento de Caja. Quitar el movimiento Asociado!");
                } else {
                    //validar si el Movimiento pertenece a una Caja Abierta
                    String est = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                    //System.out.println("estado = "+est);
                    if (est.equals("CERRADA")) {
                        JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                    } else if (est.equals("ABIERTA")) {
                        ArrayList<Double> montos = control_mc.MontosPorIDCaja(Integer.parseInt(jTabla_MovCaja.getValueAt(seleccionado, 1).toString()));
                        if (montos.size() == 1) {
                            mc.setIdmovimientocaja(idmovimientocaja);
                            if (control_mc.EliminarMovimientosCajaAbierta(mc)) {
                                Caja caja = new Caja();
                                caja.setEstado("ANULADA");
                                if (cc.AnularCaja(caja)) {
                                    Caja_Turno cajaturno = new Caja_Turno();
                                    cajaturno.setEstado("ANULADA");
                                    if (cct.AnularCajaTurno(cajaturno)) {
                                        DeshabilitarAperturaAnulada();
                                        Session.setIdcaja_abierta(0);
                                        Session.setIdcajaturno_abierta(0);
                                        Session.setIdturno_abierto(0);
                                        JOptionPane.showMessageDialog(null, "Eliminado");
                                        Mostrar();
                                    }
                                }
                                //datos.removeRow(jTabla_MovCaja.getRowCount()-1);
                            } else {
                                JOptionPane.showMessageDialog(null, "No se elimino el Movimiento de Caja");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No se puede eliminar esta apertura, solo se puede MODIFICAR.");
                        }
                    } else {
                        if (control_mc.EliminarMovimientosCaja(mc)) {
                            JOptionPane.showMessageDialog(null, "Eliminado");
                            Mostrar();
                            //datos.removeRow(jTabla_MovCaja.getRowCount()-1);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se elimino el Movimiento de Caja");
                        }
                    }
                }
            }
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtTotalIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalIngresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIngresoActionPerformed

    private void txtTotalIngresoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalIngresoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIngresoKeyReleased

    private void txtTotalEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalEgresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalEgresoActionPerformed

    private void txtTotalEgresoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalEgresoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalEgresoKeyReleased

    private void txtTotalCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCajaActionPerformed

    private void txtTotalCajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalCajaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCajaKeyReleased

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (!txtbuscar.getText().isEmpty() || !cbxTurnos.getSelectedItem().equals("Seleccionar una opción")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        //inicializarComponentes();
        //Mostrar();
        //cbxTurnos.setSelectedItem("Seleccionar una opción");
        //txtbuscar.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jListaMovimientosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListaMovimientosMouseClicked
       int i = jListaMovimientos.getSelectedIndex();
        if(i != -1){
            txtbuscar.setText(jListaMovimientos.getSelectedValue());
            jListaMovimientos.setVisible(false);
        }
    }//GEN-LAST:event_jListaMovimientosMouseClicked

    public void LimpiarSeleccion() {
        jTabla_MovCaja.clearSelection();
        jTabla_MovCaja.getSelectionModel().clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JComboBox<String> cbxTurnos;
    public static com.toedter.calendar.JDateChooser jFechaDesde;
    public static com.toedter.calendar.JDateChooser jFechaHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListaMovimientos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_MovCaja;
    private javax.swing.JTextField txtTotalCaja;
    private javax.swing.JTextField txtTotalEgreso;
    private javax.swing.JTextField txtTotalIngreso;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
