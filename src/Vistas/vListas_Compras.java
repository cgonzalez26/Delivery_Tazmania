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

        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (VerificarCajaAbierta() == false) {
                        //int fila = jTable1.rowAtPoint(e.getPoint());
                        int seleccionado = jTable1.getSelectedRow();
                        if (seleccionado == -1) {
                            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                        } else {
                            int idmovimientocaja = com.ObtenerIDMovCajaCompra(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()), "CP");
                            String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                            if (estado.equals("CERRADA")) {
                                JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                            } else {
                                fecha = (String) (jTable1.getValueAt(seleccionado, 7));
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
                                idcompra = jTable1.getValueAt(seleccionado, 0).toString();
                                vCompras_Insumos.jTextProveedor.setText(jTable1.getValueAt(seleccionado, 3).toString());
                                vCompras_Insumos.jCBUsuario.setSelectedItem(jTable1.getValueAt(seleccionado, 5).toString());
                                vCompras_Insumos.jDateChooser1.setDate(fechaseleccionada);
                                vCompras_Insumos.jTextTotal.setText(jTable1.getValueAt(seleccionado, 6).toString());
                                compra.setTitle("Modificar Compra");
                                compra.total = (Float.parseFloat(jTable1.getValueAt(seleccionado, 6).toString()));
                                compra.nrofactura = nrofactura;
                                compra.idcompra = idcompra;
                                PasarFilas2();
                                IDdetalles2();
                                CantidadFilas();
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

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int seleccionado = jTable1.getSelectedRow();
                    if (seleccionado == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        nrofactura = jTable1.getValueAt(seleccionado, 4).toString();
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

    public void IDdetalles() {
        int[] id = jTable2.getSelectedRows();
        for (int i = 0; i < id.length; i++) {
            compra.iddetalles.add(jTable2.getValueAt(id[i], 0).toString());
        }
        //JOptionPane.showMessageDialog(null, compra.iddetalles);
        /*for(int i=0; i < compra.iddetalles.size(); i++){
            JOptionPane.showMessageDialog(null, compra.iddetalles.get(i));
        }*/
    }

    public void IDdetalles2() {
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            compra.iddetalles.add(jTable2.getValueAt(i, 0).toString());
        }
    }

    public void CantidadFilas() {
        compra.filasdetalle = compra.iddetalles.size();
    }

    public void PasarFilas() {
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
    }

    public void PasarFilas2() {
        Object[] fila = new Object[3];
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            fila[0] = datosdetalle.getValueAt(i, 4);
            fila[1] = datosdetalle.getValueAt(i, 5);
            fila[2] = datosdetalle.getValueAt(i, 6);
            vCompras_Insumos.modelo.addRow(fila);
        }
    }

    public void SeleccionarFilasNroFacturaCompra() {
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
    }

    public void MostrarCompras() {
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDCOMPRA", "IDPROV", "IDUSER", "PROVEEDOR", "NROCOMPRA", "USUARIO", "MONTO TOTAL", "FECHA"};
        datoscompras = com.MostrarDatos(desde, hasta);
        datoscompra = new DefaultTableModel(datoscompras, columnas);
        jTable1.setModel(datoscompra);
        EliminarFilasVaciasCompras();
        //AjustarTamañoFilasCompra();
        ocultar_columnasCompra();
    }

    public void MostrarDetalleCompra() {
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        String[] columnas = {"IDDETCOMPRA", "IDCOMPRA", "IDINSUMO", "NRO FACTURA COMPRA", "INSUMO", "PRECIO", "CANTIDAD", "FECHA"};
        datosdetallecompra = detallecompra.MostrarDatos(desde, hasta, nrofactura);
        datosdetalle = new DefaultTableModel(datosdetallecompra, columnas);
        jTable2.setModel(datosdetalle);
        EliminarFilasVaciasDetallesCompras();
        //AjustarTamañoFilasDetalle();
        ocultar_columnasDetalle();
    }

    public void IniciarFechas() {
        Date date = new Date();
        jDateChooser1.setDate(date);
        jDateChooser2.setDate(date);
    }

    public void AjustarTamañoFilasCompra() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int prov = (int) font.getStringBounds(jTable1.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int nrocompra = (int) font.getStringBounds(jTable1.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int user = (int) font.getStringBounds(jTable1.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int date = (int) font.getStringBounds(jTable1.getValueAt(i, 6).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int montotal = (int) font.getStringBounds(jTable1.getValueAt(i, 7).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (prov > jTable1.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(3).setPreferredWidth(prov);
                }
                if (nrocompra > jTable1.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(4).setPreferredWidth(nrocompra);
                }
                if (user > jTable1.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(5).setPreferredWidth(user);
                }
                if (date > jTable1.getColumnModel().getColumn(6).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(6).setPreferredWidth(date);
                }
                if (montotal > jTable1.getColumnModel().getColumn(7).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(7).setPreferredWidth(montotal);
                }
            }
        }
    }

    public void AjustarTamañoFilasDetalle() {
        if (jTable2.getRowCount() != 0) {
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nrocompra = (int) font.getStringBounds(jTable2.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int insumo = (int) font.getStringBounds(jTable2.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int precio = (int) font.getStringBounds(jTable2.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int cant = (int) font.getStringBounds(jTable2.getValueAt(i, 6).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nrocompra > jTable2.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(3).setPreferredWidth(nrocompra);
                }
                if (insumo > jTable2.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(4).setPreferredWidth(insumo);
                }
                if (precio > jTable2.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(5).setPreferredWidth(precio);
                }
                if (cant > jTable2.getColumnModel().getColumn(6).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(6).setPreferredWidth(cant);
                }
            }
        }
    }

    public void ocultar_columnasCompra() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(0);
    }

    public void ocultar_columnasDetalle() {
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

    public void LimpiarSeleccionCompra() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionDetalle() {
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
    }

    public void EliminarFilasVaciasCompras() {
        if (jTable1.getRowCount() != 0) {
            for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                    if (jTable1.getValueAt(fila, columna) == null) {
                        datoscompra.removeRow(fila);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasDetallesCompras() {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vDetallesCompras = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        vDetallesCompras.setTitle("Detalle Compra");
        java.awt.Image iconodeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vDetallesCompras.setIconImage(iconodeliv);
        vDetallesCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vDetallesComprasMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel1.setText("Detalles Compras");

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

        jButton3.setBackground(new java.awt.Color(252, 249, 57));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Nuevo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout vDetallesComprasLayout = new javax.swing.GroupLayout(vDetallesCompras.getContentPane());
        vDetallesCompras.getContentPane().setLayout(vDetallesComprasLayout);
        vDetallesComprasLayout.setHorizontalGroup(
            vDetallesComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vDetallesComprasLayout.createSequentialGroup()
                .addGap(308, 308, 308)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(vDetallesComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(vDetallesComprasLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        vDetallesComprasLayout.setVerticalGroup(
            vDetallesComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vDetallesComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(vDetallesComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
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
                .addGap(27, 27, 27))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addContainerGap())
        );

        jButton5.setBackground(new java.awt.Color(252, 249, 57));
        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Ver Detalle");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(252, 249, 57));
        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton7.setText("Eliminar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(252, 249, 57));
        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton8.setText("Modificar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        compra = new vCompras_Insumos();
        vMenuPrincipal.jDesktopPane1.add(compra);
        compra.setVisible(true);
        compra.toFront();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila = jTable1.getSelectedRow();
        int seleccionado = jTable2.getSelectedRow();
        Compras c = new Compras();
        DetallesCompras dc = new DetallesCompras();
        Movimientos_Caja mc = new Movimientos_Caja();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int cantidad = jTable2.getRowCount();
                if (cantidad == 1) {
                    mc.setIdmovimiento(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                    mc.setIdtipomovimiento(12);
                    if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                        boolean stock = detallecompra.VerificarStock(Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()), Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                        if (stock == true) {
                            dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                            detallecompra.SetearCeroStock(dc);
                        } else {
                            dc.setCantidad(Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()));
                            dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                            detallecompra.RestarCantidadSumadaInsumos(dc);
                        }
                        dc.setIddetallecompra(Integer.parseInt(jTable2.getValueAt(seleccionado, 0).toString()));
                        detallecompra.EliminarDetalleCompra(dc);
                    }
                    c.setIdcompra(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                    if (com.EliminarCompra(c)) {
                        JOptionPane.showMessageDialog(null, "Compra Eliminada");
                        MostrarCompras();
                        vDetallesCompras.dispose();
                    }
                } else {
                    float totalcompra = Float.parseFloat(jTable1.getValueAt(fila, 6).toString()),
                            valor = Float.parseFloat(jTable2.getValueAt(seleccionado, 5).toString()) * Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()),
                            totalfinal = totalcompra - valor;
                    c.setMontototal(totalfinal);
                    c.setIdcompra(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                    if (com.ActualizarTotalCompra(c)) {
                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()), 12));
                        mc.setDescripcion("COMPRA INSUMOS");
                        mc.setIdtipomovimiento(12);
                        mc.setIdusuario(Session.getIdusuario());
                        mc.setFecha_movimiento(jTable1.getValueAt(fila, 7).toString());
                        mc.setMonto(c.getMontototal());
                        mc.setIdmovimiento((Integer.parseInt(jTable1.getValueAt(fila, 0).toString())));
                        if (control_mc.EditarMovimientosCaja(mc)) {
                            boolean stock = detallecompra.VerificarStock(Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()), Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                            if (stock == true) {
                                dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                                detallecompra.SetearCeroStock(dc);
                            } else {
                                dc.setCantidad(Float.parseFloat(jTable2.getValueAt(seleccionado, 6).toString()));
                                dc.setIdinsumo(Integer.parseInt(jTable2.getValueAt(seleccionado, 2).toString()));
                                detallecompra.RestarCantidadSumadaInsumos(dc);
                            }
                            dc.setIddetallecompra(Integer.parseInt(jTable2.getValueAt(seleccionado, 0).toString()));
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            //datoscompra = (DefaultTableModel) jTable1.getModel();
            if (VerificarCajaAbierta() == false) {
                int idmovimientocaja = com.ObtenerIDMovCajaCompra(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()), "CP");
                String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                if (estado.equals("CERRADA")) {
                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                } else {
                    fecha = (String) (jTable1.getValueAt(seleccionado, 7));
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
                    idcompra = jTable1.getValueAt(seleccionado, 0).toString();
                    vCompras_Insumos.jTextProveedor.setText(jTable1.getValueAt(seleccionado, 3).toString());
                    vCompras_Insumos.jCBUsuario.setSelectedItem(jTable1.getValueAt(seleccionado, 5).toString());
                    vCompras_Insumos.jDateChooser1.setDate(fechaseleccionada);
                    vCompras_Insumos.jTextTotal.setText(jTable1.getValueAt(seleccionado, 6).toString());
                    compra.setTitle("Modificar Compra");
                    compra.total = (Float.parseFloat(jTable1.getValueAt(seleccionado, 6).toString()));
                    compra.nrofactura = nrofactura;
                    compra.idcompra = idcompra;
                    PasarFilas2();
                    IDdetalles2();
                    CantidadFilas();
                    dispose();
                    vDetallesCompras.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccionCompra();
        //LimpiarSeleccionDetalle();
        desde = "";
        hasta = "";
        IniciarFechas();
        MostrarCompras();
        //MostrarDetalleCompra();
    }//GEN-LAST:event_formMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateChooser2.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    datoscompras = busquedacompra.NroComprasFechas(desde, hasta);
                    if (datoscompras.length != 0) {
                        String[] columnas = {"IDCOMPRA", "IDPROV", "IDUSER", "PROVEEDOR", "NROCOMPRA", "USUARIO", "MONTO TOTAL", "FECHA"};
                        datoscompra = new DefaultTableModel(datoscompras, columnas);
                        jTable1.setModel(datoscompra);
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
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        //SeleccionarFilasNroFacturaCompra();
    }//GEN-LAST:event_jTable1MousePressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        compra = new vCompras_Insumos();
        vMenuPrincipal.jDesktopPane1.add(compra);
        compra.setVisible(true);
        compra.toFront();
        this.dispose();
        vDetallesCompras.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            nrofactura = jTable1.getValueAt(seleccionado, 4).toString();
            MostrarDetalleCompra();
            vDetallesCompras.setSize(817, 311);
            vDetallesCompras.setLocationRelativeTo(this);
            vDetallesCompras.setModal(true);
            vDetallesCompras.setVisible(true);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void vDetallesComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vDetallesComprasMouseClicked
        LimpiarSeleccionDetalle();
    }//GEN-LAST:event_vDetallesComprasMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Compras c = new Compras();
        Movimientos_Caja mc = new Movimientos_Caja();
        DetallesCompras dc = new DetallesCompras();
        Sentencias_sql sql = new Sentencias_sql();
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta Compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mc.setIdmovimiento(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                mc.setIdtipomovimiento(12);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    if (sql.SafeUpdates()) {
                        Object[][] datos = detallecompra.ObtenerDatosDetalleComprasDesdeListaCompras(jTable1.getValueAt(i, 4).toString());
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
                        dc.setIdcompra(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                        if (detallecompra.EliminarDetalleDesdeListaCompra(dc)) {
                            c.setIdcompra(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                            if (com.EliminarCompra(c)) {
                                MostrarCompras();
                                JOptionPane.showMessageDialog(null, "Compra Eliminada");
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            //datoscompra = (DefaultTableModel) jTable1.getModel();
            if (VerificarCajaAbierta() == false) {
                int idmovimientocaja = com.ObtenerIDMovCajaCompra(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()), "CP");
                String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                if (estado.equals("CERRADA")) {
                    JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                } else {
                    fecha = (String) (jTable1.getValueAt(seleccionado, 6));
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
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
                    
                    /*vCompras_Insumos.jButton1.setEnabled(true);
                    vCompras_Insumos.jTextField1.setEditable(false);
                    vCompras_Insumos.jTextField7.setEditable(false);
                    vCompras_Insumos.jButton7.setEnabled(true);
                    idcompra = jTable1.getValueAt(seleccionado, 0).toString();
                    vCompras_Insumos.jTextField3.setText(jTable1.getValueAt(seleccionado, 3).toString());
                    vCompras_Insumos.jComboBox2.setSelectedItem(jTable1.getValueAt(seleccionado, 5).toString());
                    vCompras_Insumos.jTextField5.setText(jTable1.getValueAt(seleccionado, 4).toString());
                    vCompras_Insumos.jDateChooser1.setDate(fechaseleccionada);
                    vCompras_Insumos.jTextField7.setText(jTable1.getValueAt(seleccionado, 7).toString());
                    compra.total = (Float.parseFloat(jTable1.getValueAt(seleccionado, 7).toString()));*/
                    compra.idcompra = idcompra;
                    PasarFilas();
                    IDdetalles();
                    CantidadFilas();
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede Modificar. No hay CAJA ABIERTA.");
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    public static com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JDialog vDetallesCompras;
    // End of variables declaration//GEN-END:variables
}
