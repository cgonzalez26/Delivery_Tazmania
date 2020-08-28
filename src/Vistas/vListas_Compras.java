package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Compras;
import Controlador.control_DetallesCompras;
import Controlador.control_Movimientos_Caja;
import Controlador.control_busquedaCompras;
import Modelo.Compras;
import Modelo.DetallesCompras;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vListas_Compras extends javax.swing.JInternalFrame {

    Object[][] datoscompras, datosdetallecompra, prov, user;
    String idcompra, fecha, total, desde, hasta, nrofactura;
    vCompras_Insumos compra = null;
    control_Compras com = new control_Compras();
    control_DetallesCompras detallecompra = new control_DetallesCompras();
    control_busquedaCompras busquedacompra = new control_busquedaCompras();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    DefaultTableModel datoscompra, datosdetalle, modelprov, modeluser;
    Timestamp fechaseleccionada;

    public vListas_Compras() {
        initComponents();
        IniciarFechas();
        MostrarCompras();

        jTableDetallesCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (VerificarCajaAbierta() == false) {
                        //int fila = jTable1.rowAtPoint(e.getPoint());
                        int seleccionado = jTableCompras.getSelectedRow();
                        if (seleccionado == -1) {
                            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                        } else {
                            int idmovimientocaja = com.ObtenerIDMovCajaCompra(Integer.parseInt(jTableCompras.getValueAt(seleccionado, 0).toString()), "CP");
                            String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                            if (estado.equals("CERRADA")) {
                                JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                            } else {
                                fecha = (String) (jTableCompras.getValueAt(seleccionado, 7));
                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                try {
                                    fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                                } catch (ParseException ex) {
                                    Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (compra == null || compra.isClosed()) {
                                    compra = new vCompras_Insumos();
                                    vMenuPrincipal.jDesktopPane1.add(compra);
                                    compra.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                                    compra.setVisible(true);
                                    compra.toFront();
                                }
                                vCompras_Insumos.jButtonModificarCompra.setEnabled(true);
                                vCompras_Insumos.jButtonAgregarCompra.setText("Cancelar");
                                vCompras_Insumos.jButtonAgregarCompra.setEnabled(true);
                                idcompra = jTableCompras.getValueAt(seleccionado, 0).toString();
                                vCompras_Insumos.jTextProveedor.setText(jTableCompras.getValueAt(seleccionado, 3).toString());
                                vCompras_Insumos.jCBUsuario.setSelectedItem(jTableCompras.getValueAt(seleccionado, 5).toString());
                                vCompras_Insumos.jDateFecha.setDate(fechaseleccionada);
                                vCompras_Insumos.jTextTotal.setText(jTableCompras.getValueAt(seleccionado, 6).toString());
                                compra.setTitle("Modificar Compra");
                                compra.total = (Float.parseFloat(jTableCompras.getValueAt(seleccionado, 6).toString()));
                                compra.nrofactura = nrofactura;
                                compra.idcompra = idcompra;
                                PasarFilasDatosDetalle();
                                IDdetallescompras();
                                CantidadFilasRegistrado();
                                dispose();
                                vDetallesCompras.dispose();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
                    }
                }
            }
        });

        jTableCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int seleccionado = jTableCompras.getSelectedRow();
                    if (seleccionado == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        nrofactura = jTableCompras.getValueAt(seleccionado, 4).toString();
                        MostrarDetalleCompra();
                        Vistas.vListas_Compras lista = null;
                        vDetallesCompras.setSize(817, 311);
                        vDetallesCompras.setLocationRelativeTo(lista);
                        vDetallesCompras.setModal(true);
                        vDetallesCompras.setVisible(true);
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

    /*public void IDdetalles() {
        int[] id = jTable2.getSelectedRows();
        for (int i = 0; i < id.length; i++) {
            compra.iddetalles.add(jTable2.getValueAt(id[i], 0).toString());
        }
        //JOptionPane.showMessageDialog(null, compra.iddetalles);
        /*for(int i=0; i < compra.iddetalles.size(); i++){
            JOptionPane.showMessageDialog(null, compra.iddetalles.get(i));
        }
    }*/
    public void IDdetallescompras() {
        for (int i = 0; i < jTableDetallesCompras.getRowCount(); i++) {
            compra.iddetalles.add(jTableDetallesCompras.getValueAt(i, 0).toString());
        }
    }

    public ArrayList<String> ObtenerIDDetallesDesdeListaCompras() {
        int i = jTableCompras.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            compra.iddetalles = detallecompra.ObtenerIDDetalleDesdeListaCompra(jTableCompras.getValueAt(i, 4).toString());
        }
        return compra.iddetalles;
    }

    public void CantidadFilasRegistrado() {
        compra.filasdetalle = compra.iddetalles.size();
    }

    public int CantidadFilasDetalles() {
        int i = jTableCompras.getSelectedRow();

        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            compra.filasdetalle = detallecompra.CantidadTotalIDDetalles(jTableCompras.getValueAt(i, 4).toString());
        }
        return compra.filasdetalle;
    }

    /*public void PasarFilas() {
        if (compra == null || compra.isClosed()) {
            compra = new vCompras_Insumos();
            vMenuPrincipal.jDesktopPane1.add(compra);
            compra.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            compra.toFront();
            compra.setVisible(true);
        }
        int[] seleccionados = jTable2.getSelectedRows();
        Object[] fila = new Object[3];
        for (int i = 0; i < seleccionados.length; i++) {
            fila[0] = datosdetalle.getValueAt(seleccionados[i], 4);
            fila[1] = datosdetalle.getValueAt(seleccionados[i], 5);
            fila[2] = datosdetalle.getValueAt(seleccionados[i], 6);
            vCompras_Insumos.modelo.addRow(fila);
        }
    }*/
    public void PasarFilasDatosDetalle() {
        Object[] fila = new Object[4];
        for (int i = 0; i < jTableDetallesCompras.getRowCount(); i++) {
            float precio = Float.parseFloat(datosdetalle.getValueAt(i, 5).toString()), cant = Float.parseFloat(datosdetalle.getValueAt(i, 6).toString());
            float subtotal = precio * cant;
            String subtotalstr = String.valueOf(subtotal);
            fila[0] = datosdetalle.getValueAt(i, 4);
            fila[1] = datosdetalle.getValueAt(i, 5);
            fila[2] = datosdetalle.getValueAt(i, 6);
            fila[3] = subtotalstr;
            vCompras_Insumos.modelo.addRow(fila);
        }
    }

    public void ObtenerDatosDetalleDesdeListaCompras() {
        int i = jTableCompras.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            Object[][] datos = detallecompra.DatosDetalleDesdeListaComprasModificar(jTableCompras.getValueAt(i, 4).toString());
            Object[] columnas = {"INSUMO", "PRECIO", "CANTIDAD", "SUBTOTAL"};
            vCompras_Insumos.modelo = new DefaultTableModel(datos, columnas);
            vCompras_Insumos.jTableDetalle.setModel(vCompras_Insumos.modelo);
        }
    }

    /*public void SeleccionarFilasNroFacturaCompra() {
        int seleccionado = jTable1.getSelectedRow();
        ArrayList<Integer> numeros = new ArrayList<>();
        for (int j = 0; j < jTable2.getRowCount(); j++) {
            if ((jTable1.getValueAt(seleccionado, 4).equals(jTable2.getValueAt(j, 3)))) {
                numeros.add(j);
                Rectangle r = jTable2.getCellRect(j, 3, true);
                jTable2.scrollRectToVisible(r);
            }
        }
        int num = 0;
        for (int i = 0; i < numeros.size(); i++) {
            num = numeros.get(i);
        }
        jTable2.getSelectionModel().setSelectionInterval(numeros.get(0), num);
    }*/
    public void MostrarCompras() {
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDCOMPRA", "IDPROV", "IDUSER", "PROVEEDOR", "NROCOMPRA", "USUARIO", "MONTO TOTAL", "FECHA"};
        datoscompras = com.MostrarDatos(desde, hasta);
        datoscompra = new DefaultTableModel(datoscompras, columnas);
        jTableCompras.setModel(datoscompra);
        EliminarFilasVaciasCompras();
        //AjustarTamañoFilasCompra();
        ocultar_columnasCompra();
    }

    public void MostrarDetalleCompra() {
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDDETCOMPRA", "IDCOMPRA", "IDINSUMO", "NRO FACTURA COMPRA", "INSUMO", "PRECIO", "CANTIDAD", "FECHA"};
        datosdetallecompra = detallecompra.MostrarDatos(desde, hasta, nrofactura);
        datosdetalle = new DefaultTableModel(datosdetallecompra, columnas);
        jTableDetallesCompras.setModel(datosdetalle);
        EliminarFilasVaciasDetallesCompras();
        //AjustarTamañoFilasDetalle();
        ocultar_columnasDetalle();
    }

    public void IniciarFechas() {
        Date date = new Date();
        jDateFechaDesde.setDate(date);
        jDateFechaHasta.setDate(date);
    }

    public void ocultar_columnasCompra() {
        jTableCompras.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableCompras.getColumnModel().getColumn(0).setMinWidth(0);
        jTableCompras.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableCompras.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableCompras.getColumnModel().getColumn(1).setMinWidth(0);
        jTableCompras.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTableCompras.getColumnModel().getColumn(2).setMaxWidth(0);
        jTableCompras.getColumnModel().getColumn(2).setMinWidth(0);
        jTableCompras.getColumnModel().getColumn(2).setPreferredWidth(0);
    }

    public void ocultar_columnasDetalle() {
        jTableDetallesCompras.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(0).setMinWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(1).setMinWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(2).setMaxWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(2).setMinWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(7).setMaxWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(7).setMinWidth(0);
        jTableDetallesCompras.getColumnModel().getColumn(7).setPreferredWidth(0);
    }

    public void LimpiarSeleccionCompra() {
        jTableCompras.clearSelection();
        jTableCompras.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionDetalle() {
        jTableDetallesCompras.clearSelection();
        jTableDetallesCompras.getSelectionModel().clearSelection();
    }

    public void EliminarFilasVaciasCompras() {
        if (jTableCompras.getRowCount() != 0) {
            for (int columna = 0; columna < jTableCompras.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableCompras.getRowCount(); fila++) {
                    if (jTableCompras.getValueAt(fila, columna) == null) {
                        datoscompra.removeRow(fila);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasDetallesCompras() {
        if (jTableDetallesCompras.getRowCount() != 0) {
            for (int columna = 0; columna < jTableDetallesCompras.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableDetallesCompras.getRowCount(); fila++) {
                    if (jTableDetallesCompras.getValueAt(fila, columna) == null) {
                        datosdetalle.removeRow(fila);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vDetallesCompras = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDetallesCompras = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonNuevoDesdeDetalle = new javax.swing.JButton();
        jButtonModificarDesdeDetalle = new javax.swing.JButton();
        jButtonEliminarDesdeDetalle = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCompras = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonNuevo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButtonBuscarPorFechas = new javax.swing.JButton();
        jDateFechaDesde = new com.toedter.calendar.JDateChooser();
        jDateFechaHasta = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButtonVerDetalle = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();

        vDetallesCompras.setTitle("Detalle Compra");
        java.awt.Image iconodeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vDetallesCompras.setIconImage(iconodeliv);
        vDetallesCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vDetallesComprasMouseClicked(evt);
            }
        });

        jTableDetallesCompras.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableDetallesCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableDetallesCompras);

        jButtonNuevoDesdeDetalle.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevoDesdeDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevoDesdeDetalle.setText("Nuevo");
        jButtonNuevoDesdeDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoDesdeDetalleActionPerformed(evt);
            }
        });

        jButtonModificarDesdeDetalle.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificarDesdeDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificarDesdeDetalle.setText("Modificar");
        jButtonModificarDesdeDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarDesdeDetalleActionPerformed(evt);
            }
        });

        jButtonEliminarDesdeDetalle.setBackground(new java.awt.Color(237, 124, 61));
        jButtonEliminarDesdeDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminarDesdeDetalle.setText("Eliminar");
        jButtonEliminarDesdeDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarDesdeDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vDetallesComprasLayout = new javax.swing.GroupLayout(vDetallesCompras.getContentPane());
        vDetallesCompras.getContentPane().setLayout(vDetallesComprasLayout);
        vDetallesComprasLayout.setHorizontalGroup(
            vDetallesComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vDetallesComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(vDetallesComprasLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jButtonNuevoDesdeDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176)
                .addComponent(jButtonModificarDesdeDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addComponent(jButtonEliminarDesdeDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        vDetallesComprasLayout.setVerticalGroup(
            vDetallesComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vDetallesComprasLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(vDetallesComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevoDesdeDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificarDesdeDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminarDesdeDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Compras");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTableCompras.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableCompras);

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

        jButtonBuscarPorFechas.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarPorFechas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarPorFechas.setText("Buscar");
        jButtonBuscarPorFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarPorFechasActionPerformed(evt);
            }
        });

        jDateFechaDesde.setDateFormatString("dd-MM-yyyy");
        jDateFechaDesde.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jDateFechaDesde.setMinimumSize(new java.awt.Dimension(32, 20));

        jDateFechaHasta.setDateFormatString("dd-MM-yyyy");
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
                .addGap(27, 27, 27))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(jButtonBuscarPorFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jButtonBuscarPorFechas)
                .addContainerGap())
        );

        jButtonVerDetalle.setBackground(new java.awt.Color(252, 249, 57));
        jButtonVerDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonVerDetalle.setText("Ver Detalle");
        jButtonVerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerDetalleActionPerformed(evt);
            }
        });

        jButtonEliminar.setBackground(new java.awt.Color(237, 124, 61));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(jButtonVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 49, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        compra = new vCompras_Insumos();
        vMenuPrincipal.jDesktopPane1.add(compra);
        compra.setVisible(true);
        compra.toFront();
        this.dispose();
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonEliminarDesdeDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarDesdeDetalleActionPerformed
        int fila = jTableCompras.getSelectedRow();
        int seleccionado = jTableDetallesCompras.getSelectedRow();
        Compras c = new Compras();
        DetallesCompras dc = new DetallesCompras();
        Movimientos_Caja mc = new Movimientos_Caja();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int cantidad = jTableDetallesCompras.getRowCount();
                if (cantidad == 1) {
                    mc.setIdmovimiento(Integer.parseInt(jTableCompras.getValueAt(fila, 0).toString()));
                    mc.setIdtipomovimiento(12);
                    if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                        boolean stock = detallecompra.VerificarStock(Float.parseFloat(jTableDetallesCompras.getValueAt(seleccionado, 6).toString()), Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 2).toString()));
                        if (stock == true) {
                            dc.setIdinsumo(Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 2).toString()));
                            detallecompra.SetearCeroStock(dc);
                        } else {
                            dc.setCantidad(Float.parseFloat(jTableDetallesCompras.getValueAt(seleccionado, 6).toString()));
                            dc.setIdinsumo(Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 2).toString()));
                            detallecompra.RestarCantidadSumadaInsumos(dc);
                        }
                        dc.setIddetallecompra(Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 0).toString()));
                        detallecompra.EliminarDetalleCompra(dc);
                    }
                    c.setIdcompra(Integer.parseInt(jTableCompras.getValueAt(fila, 0).toString()));
                    if (com.EliminarCompra(c)) {
                        JOptionPane.showMessageDialog(null, "Compra Eliminada");
                        MostrarCompras();
                        vDetallesCompras.dispose();
                    }
                } else {
                    float totalcompra = Float.parseFloat(jTableCompras.getValueAt(fila, 6).toString()),
                            valor = Float.parseFloat(jTableDetallesCompras.getValueAt(seleccionado, 5).toString()) * Float.parseFloat(jTableDetallesCompras.getValueAt(seleccionado, 6).toString()),
                            totalfinal = totalcompra - valor;
                    c.setMontototal(totalfinal);
                    c.setIdcompra(Integer.parseInt(jTableCompras.getValueAt(fila, 0).toString()));
                    if (com.ActualizarTotalCompra(c)) {
                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(jTableCompras.getValueAt(fila, 0).toString()), 12));
                        mc.setDescripcion("COMPRA INSUMOS");
                        mc.setIdtipomovimiento(12);
                        mc.setIdusuario(Session.getIdusuario());
                        mc.setFecha_movimiento(jTableCompras.getValueAt(fila, 7).toString());
                        mc.setMonto(c.getMontototal());
                        mc.setIdmovimiento((Integer.parseInt(jTableCompras.getValueAt(fila, 0).toString())));
                        if (control_mc.EditarMovimientosCaja(mc)) {
                            boolean stock = detallecompra.VerificarStock(Float.parseFloat(jTableDetallesCompras.getValueAt(seleccionado, 6).toString()), Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 2).toString()));
                            if (stock == true) {
                                dc.setIdinsumo(Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 2).toString()));
                                detallecompra.SetearCeroStock(dc);
                            } else {
                                dc.setCantidad(Float.parseFloat(jTableDetallesCompras.getValueAt(seleccionado, 6).toString()));
                                dc.setIdinsumo(Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 2).toString()));
                                detallecompra.RestarCantidadSumadaInsumos(dc);
                            }
                            dc.setIddetallecompra(Integer.parseInt(jTableDetallesCompras.getValueAt(seleccionado, 0).toString()));
                            detallecompra.EliminarDetalleCompra(dc);
                            JOptionPane.showMessageDialog(null, "Eliminado");
                            MostrarDetalleCompra();
                        }

                        /*int[] seleccionados = jTable2.getSelectedRows();
                    for (int j = 0; j < seleccionados.length; j++) {
                        boolean stock = detallecompra.VerificarStock(Float.parseFloat(jTable2.getValueAt(seleccionados[j], 6).toString()), Integer.parseInt(jTable2.getValueAt(seleccionados[j], 2).toString()));
                        if (stock == true) {
                            dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(seleccionados[j], 2).toString()));
                            detallecompra.SetearCeroStock(dc);
                        } else {
                            dc.setCantidad(Float.parseFloat(jTable2.getValueAt(seleccionados[j], 6).toString()));
                            dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(seleccionados[j], 2).toString()));
                            if (detallecompra.RestarCantidadSumadaInsumos(dc)) {
                                
                            }
                        }

                        dc.setIddetallecompra(Integer.parseInt(jTable2.getValueAt(seleccionados[j], 0).toString()));
                        if (detallecompra.EliminarDetalleCompra(dc)) {
                            
                        }
                    }
                    for (int j = 0; j < jTable2.getRowCount(); j++) {
                        boolean stock = detallecompra.VerificarStock(Float.parseFloat(jTable2.getValueAt(j, 6).toString()), Integer.parseInt(jTable2.getValueAt(j, 2).toString()));
                        if (stock == true) {
                            dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(j, 2).toString()));
                            detallecompra.SetearCeroStock(dc);
                        } else {
                            dc.setCantidad(Float.parseFloat(jTable2.getValueAt(j, 6).toString()));
                            dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(j, 2).toString()));
                            if (detallecompra.RestarCantidadSumadaInsumos(dc)) {
                                    
                            }
                        }
                        dc.setIddetallecompra(Integer.parseInt(jTable2.getValueAt(j, 0).toString()));
                        if (detallecompra.EliminarDetalleCompra(dc)) {

                        }
                    }*/
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonEliminarDesdeDetalleActionPerformed

    private void jButtonModificarDesdeDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarDesdeDetalleActionPerformed
        if (VerificarCajaAbierta() == false) {
            int seleccionado = jTableCompras.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int idmovimientocaja = com.ObtenerIDMovCajaCompra(Integer.parseInt(jTableCompras.getValueAt(seleccionado, 0).toString()), "CP");
                String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                if (estado.equals("CERRADA")) {
                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                } else {
                    fecha = (String) (jTableCompras.getValueAt(seleccionado, 7));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (compra == null || compra.isClosed()) {
                        compra = new vCompras_Insumos();
                        vMenuPrincipal.jDesktopPane1.add(compra);
                        compra.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        compra.setVisible(true);
                        compra.toFront();
                    }
                    vCompras_Insumos.jButtonAgregarCompra.setText("Cancelar");
                    vCompras_Insumos.jButtonAgregarCompra.setEnabled(true);
                    //vCompras_Insumos.jTextField1.setEditable(false);
                    vCompras_Insumos.jTextTotal.setEditable(false);
                    vCompras_Insumos.jButtonModificarCompra.setEnabled(true);
                    idcompra = jTableCompras.getValueAt(seleccionado, 0).toString();
                    vCompras_Insumos.jTextProveedor.setText(jTableCompras.getValueAt(seleccionado, 3).toString());
                    vCompras_Insumos.jCBUsuario.setSelectedItem(jTableCompras.getValueAt(seleccionado, 5).toString());
                    vCompras_Insumos.jDateFecha.setDate(fechaseleccionada);
                    vCompras_Insumos.jTextTotal.setText(jTableCompras.getValueAt(seleccionado, 6).toString());
                    compra.setTitle("Modificar Compra");
                    compra.total = (Float.parseFloat(jTableCompras.getValueAt(seleccionado, 6).toString()));
                    compra.nrofactura = nrofactura;
                    compra.idcompra = idcompra;
                    PasarFilasDatosDetalle();
                    IDdetallescompras();
                    CantidadFilasRegistrado();
                    dispose();
                    vDetallesCompras.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
        }
    }//GEN-LAST:event_jButtonModificarDesdeDetalleActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccionCompra();
        //LimpiarSeleccionDetalle();
        desde = "";
        hasta = "";
        IniciarFechas();
        MostrarCompras();
        //MostrarDetalleCompra();
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarPorFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarPorFechasActionPerformed
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        if (jDateFechaDesde.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateFechaHasta.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    datoscompras = busquedacompra.NroComprasFechas(desde, hasta);
                    if (datoscompras.length != 0) {
                        String[] columnas = {"IDCOMPRA", "IDPROV", "IDUSER", "PROVEEDOR", "NROCOMPRA", "USUARIO", "MONTO TOTAL", "FECHA"};
                        datoscompra = new DefaultTableModel(datoscompras, columnas);
                        jTableCompras.setModel(datoscompra);
                        EliminarFilasVaciasCompras();
                        //AjustarTamañoFilasCompra();
                        ocultar_columnasCompra();

                        /*String[] columnasdetalle = {"IDDETCOMPRA", "IDCOMPRA", "IDINSUMO", "NRO FACTURA COMPRA", "INSUMO", "PRECIO", "CANTIDAD", "FECHA"};
                        datosdetallecompra = busquedacompra.NroDetallesComprasFechas(desde, hasta);
                        datosdetalle = new DefaultTableModel(datosdetallecompra, columnasdetalle);
                        jTable2.setModel(datosdetalle);
                        EliminarFilasVaciasDetallesCompras();
                        //AjustarTamañoFilasDetalle();
                        ocultar_columnasDetalle();*/
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
    }//GEN-LAST:event_jButtonBuscarPorFechasActionPerformed

    private void jButtonNuevoDesdeDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoDesdeDetalleActionPerformed
        compra = new vCompras_Insumos();
        vMenuPrincipal.jDesktopPane1.add(compra);
        compra.setVisible(true);
        compra.toFront();
        this.dispose();
        vDetallesCompras.dispose();
    }//GEN-LAST:event_jButtonNuevoDesdeDetalleActionPerformed

    private void jButtonVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerDetalleActionPerformed
        int seleccionado = jTableCompras.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            nrofactura = jTableCompras.getValueAt(seleccionado, 4).toString();
            MostrarDetalleCompra();
            vDetallesCompras.setSize(817, 311);
            vDetallesCompras.setLocationRelativeTo(this);
            vDetallesCompras.setModal(true);
            vDetallesCompras.setVisible(true);
        }
    }//GEN-LAST:event_jButtonVerDetalleActionPerformed

    private void vDetallesComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vDetallesComprasMouseClicked
        LimpiarSeleccionDetalle();
    }//GEN-LAST:event_vDetallesComprasMouseClicked

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        Compras c = new Compras();
        Movimientos_Caja mc = new Movimientos_Caja();
        DetallesCompras dc = new DetallesCompras();
        Sentencias_sql sql = new Sentencias_sql();
        int seleccionado = jTableCompras.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta Compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mc.setIdmovimiento(Integer.parseInt(jTableCompras.getValueAt(seleccionado, 0).toString()));
                mc.setIdtipomovimiento(12);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    if (sql.SafeUpdates()) {
                        Object[][] datos = detallecompra.ObtenerDatosDetalleComprasDesdeListaCompras(jTableCompras.getValueAt(i, 4).toString());
                        for (int j = 0; j < datos.length; j++) {
                            for (int l = 0; l < datos.length; l++) {
                                int idinsumo = (int) datos[j][l];
                                float cantidad = (float) datos[j][l + 1];
                                dc.setCantidad(cantidad);
                                dc.setIdinsumo(idinsumo);
                                detallecompra.RestarCantidadSumadaInsumos(dc);
                                if (j < datos.length - 1) {
                                    j++;
                                    l--;
                                } else {
                                    break;
                                }
                            }
                        }
                        dc.setIdcompra(Integer.parseInt(jTableCompras.getValueAt(seleccionado, 0).toString()));
                        if (detallecompra.EliminarDetalleDesdeListaCompra(dc)) {
                            c.setIdcompra(Integer.parseInt(jTableCompras.getValueAt(seleccionado, 0).toString()));
                            if (com.EliminarCompra(c)) {
                                MostrarCompras();
                                JOptionPane.showMessageDialog(null, "Compra Eliminada");
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        if (VerificarCajaAbierta() == false) {
            int seleccionado = jTableCompras.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int idmovimientocaja = com.ObtenerIDMovCajaCompra(Integer.parseInt(jTableCompras.getValueAt(seleccionado, 0).toString()), "CP");
                String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                if (estado.equals("CERRADA")) {
                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                } else {
                    fecha = (String) (jTableCompras.getValueAt(seleccionado, 7));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (compra == null || compra.isClosed()) {
                        compra = new vCompras_Insumos();
                        vMenuPrincipal.jDesktopPane1.add(compra);
                        compra.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        compra.setVisible(true);
                        compra.toFront();
                    }
                    vCompras_Insumos.jButtonModificarCompra.setEnabled(true);
                    vCompras_Insumos.jButtonAgregarCompra.setText("Cancelar");
                    vCompras_Insumos.jButtonAgregarCompra.setEnabled(true);
                    idcompra = jTableCompras.getValueAt(seleccionado, 0).toString();
                    vCompras_Insumos.jTextProveedor.setText(jTableCompras.getValueAt(seleccionado, 3).toString());
                    vCompras_Insumos.jCBUsuario.setSelectedItem(jTableCompras.getValueAt(seleccionado, 5).toString());
                    vCompras_Insumos.jDateFecha.setDate(fechaseleccionada);
                    vCompras_Insumos.jTextTotal.setText(jTableCompras.getValueAt(seleccionado, 6).toString());
                    compra.setTitle("Modificar Compra");
                    compra.total = (Float.parseFloat(jTableCompras.getValueAt(seleccionado, 6).toString()));
                    compra.nrofactura = nrofactura;
                    compra.idcompra = idcompra;
                    ObtenerDatosDetalleDesdeListaCompras();
                    ObtenerIDDetallesDesdeListaCompras();
                    CantidadFilasDetalles();
                    dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarPorFechas;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonEliminarDesdeDetalle;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonModificarDesdeDetalle;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JButton jButtonNuevoDesdeDetalle;
    private javax.swing.JButton jButtonVerDetalle;
    public static com.toedter.calendar.JDateChooser jDateFechaDesde;
    public static com.toedter.calendar.JDateChooser jDateFechaHasta;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTableCompras;
    private javax.swing.JTable jTableDetallesCompras;
    private javax.swing.JDialog vDetallesCompras;
    // End of variables declaration//GEN-END:variables
}
