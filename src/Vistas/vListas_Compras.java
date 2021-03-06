package Vistas;

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
        MostrarDetalleCompra();

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (VerificarCajaAbierta() == false) {
                        int fila = jTable1.rowAtPoint(e.getPoint());
                        int idmovimientocaja = com.ObtenerIDMovCajaCompra(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()), "CP");
                        String estado = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                        if (estado.equals("CERRADA")) {
                            JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                        } else {
                            fecha = (String) (jTable1.getValueAt(fila, 7));
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
                            vCompras_Insumos.jButton7.setEnabled(true);
                            vCompras_Insumos.jButton1.setText("Cancelar");
                            vCompras_Insumos.jButton1.setEnabled(true);
                            idcompra = jTable1.getValueAt(fila, 0).toString();
                            vCompras_Insumos.jTextField3.setText(jTable1.getValueAt(fila, 3).toString());
                            nrofactura = jTable1.getValueAt(fila, 4).toString();
                            vCompras_Insumos.jComboBox2.setSelectedItem(jTable1.getValueAt(fila, 5).toString());
                            vCompras_Insumos.jDateChooser1.setDate(fechaseleccionada);
                            vCompras_Insumos.jTextField7.setText(jTable1.getValueAt(fila, 6).toString());
                            compra.setTitle("Modificar Compra");
                            compra.total = (Float.parseFloat(jTable1.getValueAt(fila, 6).toString()));
                            compra.nrofactura = nrofactura;
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
        datosdetallecompra = detallecompra.MostrarDatos(desde, hasta);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jEtiqTitulo_DetalleCompras = new javax.swing.JLabel();
        jEtiqTitulo_Compras = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Compras y Detalle");
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

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Nuevo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jEtiqTitulo_DetalleCompras.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jEtiqTitulo_DetalleCompras.setText("Detalle Compras");

        jEtiqTitulo_Compras.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jEtiqTitulo_Compras.setText("Compras");

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Eliminar Compra");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(353, 353, 353)
                        .addComponent(jEtiqTitulo_DetalleCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(183, 183, 183)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jEtiqTitulo_Compras, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(393, 393, 393))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(225, 225, 225))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEtiqTitulo_Compras)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jEtiqTitulo_DetalleCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jButton2))
                .addContainerGap())
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
        Compras c = new Compras();
        DetallesCompras dc = new DetallesCompras();
        Movimientos_Caja mc = new Movimientos_Caja();
        control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mc.setIdmovimiento(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                mc.setIdtipomovimiento(12);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    int[] seleccionados = jTable2.getSelectedRows();
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
                    c.setIdcompra(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                    if (com.EliminarCompra(c)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        MostrarCompras();
                        MostrarDetalleCompra();
                    }
                }
            } else {
                LimpiarSeleccionCompra();
                LimpiarSeleccionDetalle();
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
                    vCompras_Insumos.jButton1.setText("Cancelar");
                    vCompras_Insumos.jButton1.setEnabled(true);
                    vCompras_Insumos.jTextField1.setEditable(false);
                    vCompras_Insumos.jTextField7.setEditable(false);
                    vCompras_Insumos.jButton7.setEnabled(true);
                    idcompra = jTable1.getValueAt(seleccionado, 0).toString();
                    vCompras_Insumos.jTextField3.setText(jTable1.getValueAt(seleccionado, 3).toString());
                    nrofactura = jTable1.getValueAt(seleccionado, 4).toString();
                    vCompras_Insumos.jComboBox2.setSelectedItem(jTable1.getValueAt(seleccionado, 5).toString());
                    vCompras_Insumos.jDateChooser1.setDate(fechaseleccionada);
                    vCompras_Insumos.jTextField7.setText(jTable1.getValueAt(seleccionado, 6).toString());
                    compra.setTitle("Modificar Compra");
                    compra.total = (Float.parseFloat(jTable1.getValueAt(seleccionado, 6).toString()));
                    compra.nrofactura = nrofactura;
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
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desde = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccionCompra();
        LimpiarSeleccionDetalle();
        desde = "";
        hasta = "";
        IniciarFechas();
        MostrarCompras();
        MostrarDetalleCompra();
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

                        String[] columnasdetalle = {"IDDETCOMPRA", "IDCOMPRA", "IDINSUMO", "NRO FACTURA COMPRA", "INSUMO", "PRECIO", "CANTIDAD", "FECHA"};
                        datosdetallecompra = busquedacompra.NroDetallesComprasFechas(desde, hasta);
                        datosdetalle = new DefaultTableModel(datosdetallecompra, columnasdetalle);
                        jTable2.setModel(datosdetalle);
                        EliminarFilasVaciasDetallesCompras();
                        //AjustarTamañoFilasDetalle();
                        ocultar_columnasDetalle();
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
        SeleccionarFilasNroFacturaCompra();
    }//GEN-LAST:event_jTable1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    public static com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jEtiqTitulo_Compras;
    private javax.swing.JLabel jEtiqTitulo_DetalleCompras;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
