package Vistas;

import Controlador.ColorearFilas;
import Controlador.Sentencias_sql;
import Controlador.control_DetallesVentas;
import Controlador.control_Movimientos_Caja;
import Controlador.control_Productos;
import Controlador.control_Ventas;
import Controlador.control_existencias;
import Modelo.DetallesVentas;
import Modelo.Movimientos_Caja;
import Modelo.Productos;
import Modelo.Session;
import Modelo.Ventas;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vVentas_Productos extends javax.swing.JInternalFrame {

    control_existencias combo = new control_existencias();
    control_Ventas venta = new control_Ventas();
    control_DetallesVentas detalle = new control_DetallesVentas();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    control_Productos productos = new control_Productos();
    Sentencias_sql sql = new Sentencias_sql();
    Ventas v = new Ventas();
    DetallesVentas d = new DetallesVentas();
    Movimientos_Caja mc = new Movimientos_Caja();
    Productos p = new Productos();
    vListas_Ventas lista = null;
    ColorearFilas color;
    Object[] user, producto;
    Object[][] produc, stockprod;
    public DefaultTableModel modelo, modelprod, modelstockprod;
    DefaultListModel listmodel;
    public static String idventa = "", iddetalle = "", desc = "", nrofactura = "", idprod;
    public int filasdetalle = 0;
    public ArrayList<String> iddetalles = new ArrayList<>();
    public ArrayList<Integer> iddetallesventas = new ArrayList<>();
    public ArrayList<Integer> idproductos = new ArrayList<>();
    public ArrayList<Integer> iddetallesventasmodif = new ArrayList<>();
    public ArrayList<Integer> idprodmodif = new ArrayList<>();
    public ArrayList<String> nomprod = new ArrayList<>();
    ArrayList<String> listprod;
    int cantidad = 0, cant = 0, cantmod = 0;
    float precio = 0;
    public float total = 0;

    public vVentas_Productos() {
        initComponents();
        VerificarCajaAbierta();
        CrearColumnas();
        MostrarProductos();
        ComboUsuario();
        EliminarItemsVacios();
        //jTextField6.setText(Float.toString((float) 0.0));
        txtTotal.setText(Float.toString((float) 0.0));
        //jTextField6.setEditable(false);
        txtTotal.setEditable(false);
        btnModificarVenta.setEnabled(false);
    }

    public void VerificarCajaAbierta() {
        int idcaja = Session.getIdcaja_abierta();
        if (idcaja == 0) {
            DeshabilitarCampos();
        } else {
            jLabel5.setVisible(false);
        }
    }

    public void DeshabilitarCampos() {
        cbxUsuario.setEnabled(false);
        txtTotal.setEnabled(false);
        txtProducto.setEnabled(false);
        txtCantidad.setEnabled(false);
        txtPrecio.setEnabled(false);
        //jTextField6.setEnabled(false);
        jDateChooser1.setEnabled(false);
        jButton2.setEnabled(false);
        btnAgregar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnGuardarVenta.setEnabled(false);
        btnModificarVenta.setEnabled(false);
        jLabel5.setVisible(true);
    }

    public void CrearColumnas() {
        modelo = new DefaultTableModel();
        modelo.addColumn("PRODUCTO");
        modelo.addColumn("PRECIO");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("SUBTOTAL");
        jTable1.setModel(modelo);
    }

    public void MostrarProductos() {
        String[] columnas = {"PRODUCTOS", "PRECIO"};
        produc = detalle.MostrarProductos();
        modelprod = new DefaultTableModel(produc, columnas);
        jTable2.setModel(modelprod);
        EliminarFilasVaciasProductos();
        ocultar_columnasprod();
    }

    public void MostrarProductosStockN_MOD() {
        String[] columnas = new String[3];
        if (btnModificar.getText().equals("Cancelar Modificar Detalle")) {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK MODIFICANDO VENTA";
            columnas[2] = "STOCK CONFIRMANDO VENTA";
        } else {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK ACTUAL";
            columnas[2] = "STOCK FINAL";
        }
        stockprod = detalle.MostrarProductoStockN_MOD(txtProducto.getText(), Float.parseFloat(txtCantidad.getText()));
        modelstockprod = new DefaultTableModel(stockprod, columnas);
        jTable3.setModel(modelstockprod);
        EliminiarFilasVaciasStockProd();
    }

    public void MostrarProductosStockMOD() {
        if (jTable1.getRowCount() != 0) {
            String[] columnas = {"INSUMOS", "STOCK ACTUAL", "STOCK MODIFICANDO VENTA", "STOCK FINAL VENTA"};
            int i = jTable1.getSelectedRow();
            stockprod = detalle.MostrarProductosStockMOD(txtProducto.getText(), Float.parseFloat(jTable1.getValueAt(i, 2).toString()), Float.parseFloat(txtCantidad.getText()));
            modelstockprod = new DefaultTableModel(stockprod, columnas);
            jTable3.setModel(modelstockprod);
            EliminiarFilasVaciasStockProd();
        }
    }

    public void ListaProductos() {
        listprod = combo.list("productos", "descripcion", txtProducto.getText());
        String substr = txtProducto.getText().toLowerCase();
        listmodel = new DefaultListModel<>();
        jList2.setModel(listmodel);
        listmodel.removeAllElements();
        if (listprod.size() > 0) {
            for (int i = 0; i < listprod.size(); i++) {
                if (listprod.get(i) == null) {
                    listprod.remove(i);
                } else {
                    String sublist = listprod.get(i).toLowerCase();
                    if (sublist.contains(substr)) {
                        listmodel.addElement(listprod.get(i));
                        jList2.setVisible(true);
                        if (txtProducto.getText().isEmpty()) {
                            jList2.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void ocultar_columnasprod() {
        jTable2.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(1).setMinWidth(0);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    public void EliminarItemsVacios() {
        for (int j = 0; j < cbxUsuario.getItemCount(); j++) {
            if (cbxUsuario.getItemAt(j) == null) {
                cbxUsuario.removeItemAt(j);
            }
        }
    }

    public void EliminarFilasVaciasProductos() {
        if (jTable2.getRowCount() != 0) {
            int filas = jTable2.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable2.getValueAt(fila, 0) == null) {
                    modelprod.removeRow(fila);
                }
            }
        }
    }

    public void EliminiarFilasVaciasStockProd() {
        if (jTable3.getRowCount() != 0) {
            int filas = jTable3.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable3.getValueAt(fila, 0) == null) {
                    modelstockprod.removeRow(fila);
                }
            }
        }
    }

    public float CalcularMontoTotal() {
        try {
            total = total + (Float.parseFloat(txtPrecio.getText().trim()) * Float.parseFloat(txtCantidad.getText().trim()));
            txtTotal.setText(Float.toString(total));

        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return total;
    }

    public void ComboUsuario() {
        user = combo.combox("usuarios", "Login");
        for (Object user1 : user) {
            cbxUsuario.addItem((String) user1);
        }
    }

    public void Limpiar() {
        txtProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
    }

    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarProducto = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        vStocksProductos = new javax.swing.JDialog();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        cbxUsuario = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnGuardarVenta = new javax.swing.JButton();
        btnModificarVenta = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnBorrar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel9 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbTipoVenta = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jList2 = new javax.swing.JList<>();
        btnCancelar = new javax.swing.JButton();

        vSeleccionarProducto.setTitle("Seleccionar Producto");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vSeleccionarProducto.setIconImage(icono);
        vSeleccionarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarProductoMouseClicked(evt);
            }
        });
        vSeleccionarProducto.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarProductoWindowClosing(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Aceptar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Cancelar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("Nombre Producto");

        jTextField7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton9.setText("Buscar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel2)
                        .addGap(0, 86, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vSeleccionarProductoLayout = new javax.swing.GroupLayout(vSeleccionarProducto.getContentPane());
        vSeleccionarProducto.getContentPane().setLayout(vSeleccionarProductoLayout);
        vSeleccionarProductoLayout.setHorizontalGroup(
            vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addGroup(vSeleccionarProductoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vSeleccionarProductoLayout.setVerticalGroup(
            vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        vStocksProductos.setTitle("Informe Producto Stock");
        java.awt.Image iconodeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vStocksProductos.setIconImage(iconodeliv);
        vStocksProductos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vStocksProductosWindowClosing(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel10.setText("Producto:");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

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
        jScrollPane3.setViewportView(jTable3);

        jButton10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton10.setText("Aceptar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vStocksProductosLayout = new javax.swing.GroupLayout(vStocksProductos.getContentPane());
        vStocksProductos.getContentPane().setLayout(vStocksProductosLayout);
        vStocksProductosLayout.setHorizontalGroup(
            vStocksProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vStocksProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vStocksProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vStocksProductosLayout.createSequentialGroup()
                        .addGap(0, 260, Short.MAX_VALUE)
                        .addGroup(vStocksProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vStocksProductosLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(174, 174, 174))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vStocksProductosLayout.createSequentialGroup()
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(303, 303, 303))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vStocksProductosLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())))
        );
        vStocksProductosLayout.setVerticalGroup(
            vStocksProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vStocksProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vStocksProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addContainerGap())
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Registrar Venta");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
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

        cbxUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        cbxUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccione Usuario.." }));
        cbxUsuario.setFocusable(false);
        getContentPane().add(cbxUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 202, 33));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("(*) Fecha.");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 161, 29));

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 196, 33));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("TOTAL:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 440, 50, 23));

        txtTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 440, 140, 32));

        btnGuardarVenta.setBackground(new java.awt.Color(252, 249, 57));
        btnGuardarVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnGuardarVenta.setText("Registrar Venta");
        btnGuardarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 490, -1, -1));

        btnModificarVenta.setBackground(new java.awt.Color(252, 249, 57));
        btnModificarVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnModificarVenta.setText("Modificar Venta");
        btnModificarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos a Vender", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        btnBorrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/delete16.png"))); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add16.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit16.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("(*) Producto:");

        txtProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) Precio:");

        txtPrecio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("(*) Cantidad:");

        txtCantidad.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                                .addGap(19, 19, 19))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregar)
                                .addGap(37, 37, 37)
                                .addComponent(btnModificar)
                                .addGap(37, 37, 37)
                                .addComponent(btnBorrar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 1188, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Usuario responsable de la venta");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 202, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("No Hay CAJA ABIERTA.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 150, 30));

        jPanel2.setBackground(new java.awt.Color(255, 248, 177));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "(*) Tipo de Venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        rbTipoVenta.setBackground(new java.awt.Color(255, 248, 177));
        rbTipoVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        rbTipoVenta.setText("Local");

        jRadioButton2.setBackground(new java.awt.Color(255, 248, 177));
        jRadioButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jRadioButton2.setText("Online");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbTipoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rbTipoVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, -1, 80));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        getContentPane().add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 500, 221, -1));

        btnCancelar.setBackground(new java.awt.Color(240, 87, 49));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 490, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        boolean repetido = false;
        if (!txtProducto.getText().equals("") && !txtCantidad.getText().equals("") && !txtPrecio.getText().equals("")) {
            if (jTable1.getRowCount() != 0) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    if (txtProducto.getText().equals(jTable1.getValueAt(i, 0).toString()) && btnModificar.getText().equals("Modificar")) {
                        JOptionPane.showMessageDialog(null, "Producto ya ingresado!");
                        repetido = true;
                    }
                }
            }
            if (repetido == false) {
                if (btnModificar.getText().equals("Modificar")) {
                    int stockneg = detalle.ConsultarStockNegativosN_MOD(txtProducto.getText(), Float.parseFloat(txtCantidad.getText()));
                    int stockcero = detalle.ConsultarStockCeroN_MOD(txtProducto.getText(), Float.parseFloat(txtCantidad.getText()));
                    if (stockneg > 0) {
                        String[] opciones = {"Ver Informe", "No ver y seguir Venta"};
                        //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + txtProducto.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                        if (i == 0) {
                            MostrarProductosStockN_MOD();
                            jLabel11.setText(txtProducto.getText());
                            color = new ColorearFilas(2);
                            jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                            vStocksProductos.setSize(727, 560); //728, 524
                            vStocksProductos.setLocationRelativeTo(this);
                            vStocksProductos.setModal(true);
                            vStocksProductos.setVisible(true);
                        } else {
                            Limpiar();
                        }
                    } else if (stockcero > 0) {
                        String[] opc = {"Ver Informe", "No ver y seguir Venta"};
                        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica la venta para este producto " + txtProducto.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                        if (i == 0) {
                            MostrarProductosStockN_MOD();
                            jLabel11.setText(txtProducto.getText());
                            color = new ColorearFilas(2);
                            jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                            vStocksProductos.setSize(727, 560);
                            vStocksProductos.setLocationRelativeTo(this);
                            vStocksProductos.setModal(true);
                            vStocksProductos.setVisible(true);
                        } else {
                            int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (g == 0) {
                                d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                                d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                                if (detalle.ActualizarStockInsumos(d)) {
                                    Object datos[] = {txtProducto.getText(), (Float.parseFloat(txtPrecio.getText())), (Float.parseFloat(txtCantidad.getText()))};
                                    modelo.addRow(datos);
                                    //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                    CalcularMontoTotal();
                                    Limpiar();
                                }
                            } else {
                                Limpiar();
                            }
                        }
                    } else {
                        d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                        d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                        if (detalle.ActualizarStockInsumos(d)) {
                            Object datos[] = {txtProducto.getText(), (Float.parseFloat(txtPrecio.getText())), (Float.parseFloat(txtCantidad.getText()))};
                            modelo.addRow(datos);
                            //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                            CalcularMontoTotal();
                            Limpiar();
                        }
                    }
                } else if (btnModificar.getText().equals("Cancelar")) {
                    int i = jTable1.getSelectedRow();
                    if (i == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                        detalle.SumarCantidadRestadaInsumos(d);
                        int stockneg = 0, stockcero = 0;
                        stockneg = detalle.ConsultarStockNegativosN_MOD(txtProducto.getText(), Float.parseFloat(txtCantidad.getText()));
                        stockcero = detalle.ConsultarStockCeroN_MOD(txtProducto.getText(), Float.parseFloat(txtCantidad.getText()));

                        if (stockneg > 0) {
                            String[] opciones = {"Ver Informe", "No ver y seguir Venta"};
                            //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                            int l = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + txtProducto.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                            if (l == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(txtProducto.getText());
                                color = new ColorearFilas(2);
                                jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                                vStocksProductos.setSize(727, 560);
                                vStocksProductos.setLocationRelativeTo(this);
                                vStocksProductos.setModal(true);
                                vStocksProductos.setVisible(true);
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                                if (detalle.ActualizarStockInsumos(d)) {
                                    total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                                    txtTotal.setText(Float.toString(total));
                                    btnModificar.setText("Modificar");
                                    btnBorrar.setEnabled(true);
                                    LimpiarSeleccion();
                                    Limpiar();
                                }
                            }
                        } else if (stockcero > 0) {
                            String[] opc = {"Ver Informe", "No ver y seguir Venta"};
                            int j = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica la venta para este producto " + txtProducto.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                            if (j == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(txtProducto.getText());
                                color = new ColorearFilas(2);
                                jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                                vStocksProductos.setSize(727, 560);
                                vStocksProductos.setLocationRelativeTo(this);
                                vStocksProductos.setModal(true);
                                vStocksProductos.setVisible(true);
                            } else {
                                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (g == 0) {
                                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        modelo.setValueAt(txtProducto.getText(), i, 0);
                                        modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), i, 1);
                                        modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), i, 2);
                                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                        CalcularMontoTotal();
                                        Limpiar();
                                        btnModificar.setText("Modificar");
                                        btnBorrar.setEnabled(true);
                                        LimpiarSeleccion();
                                    }
                                } else {
                                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                                        txtTotal.setText(Float.toString(total));
                                        btnModificar.setText("Modificar");
                                        btnBorrar.setEnabled(true);
                                        LimpiarSeleccion();
                                        Limpiar();
                                    }
                                }
                            }
                        } else {
                            d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                            d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                            if (detalle.ActualizarStockInsumos(d)) {
                                modelo.setValueAt(txtProducto.getText(), i, 0);
                                modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), i, 1);
                                modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), i, 2);
                                //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                CalcularMontoTotal();
                                Limpiar();
                                btnModificar.setText("Modificar");
                                btnBorrar.setEnabled(true);
                                LimpiarSeleccion();
                            }
                        }
                    }
                } else if (btnModificar.getText().equals("Cancelar Modificar Detalle")) {
                    int k = jTable1.getSelectedRow();
                    if (k == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        d.setCantidad(Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(k, 0).toString()));
                        detalle.SumarCantidadRestadaInsumos(d);
                        int stockneg = 0, stockcero = 0;
                        stockneg = detalle.ConsultarStockNegativosN_MOD(txtProducto.getText(), Float.parseFloat(txtCantidad.getText()));
                        stockcero = detalle.ConsultarStockCeroN_MOD(txtProducto.getText(), Float.parseFloat(txtCantidad.getText()));
                        if (stockneg > 0) {
                            String[] opciones = {"Ver Informe", "No ver y seguir Venta"};
                            //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                            int l = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + txtProducto.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                            if (l == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(txtProducto.getText());
                                color = new ColorearFilas(2);
                                jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                                vStocksProductos.setSize(727, 560);
                                vStocksProductos.setLocationRelativeTo(this);
                                vStocksProductos.setModal(true);
                                vStocksProductos.setVisible(true);
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(k, 0).toString()));
                                if (detalle.ActualizarStockInsumos(d)) {
                                    total = total + (Float.parseFloat(jTable1.getValueAt(k, 1).toString()) * Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                                    txtTotal.setText(Float.toString(total));
                                    btnModificar.setText("Modificar");
                                    btnBorrar.setEnabled(true);
                                    LimpiarSeleccion();
                                    Limpiar();
                                }
                            }
                        } else if (stockcero > 0) {
                            String[] opc = {"Ver Informe", "No ver y seguir Venta"};
                            int j = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica la venta para este producto " + txtProducto.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                            if (j == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(txtProducto.getText());
                                color = new ColorearFilas(2);
                                jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                                vStocksProductos.setSize(727, 560);
                                vStocksProductos.setLocationRelativeTo(this);
                                vStocksProductos.setModal(true);
                                vStocksProductos.setVisible(true);
                            } else {
                                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (g == 0) {
                                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        cantmod++;
                                        modelo.setValueAt(txtProducto.getText(), k, 0);
                                        modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), k, 1);
                                        modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), k, 2);
                                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                        CalcularMontoTotal();
                                        Limpiar();
                                        btnModificar.setText("Modificar");
                                        btnBorrar.setEnabled(true);
                                        LimpiarSeleccion();
                                    }

                                } else {
                                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(k, 0).toString()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        total = total + (Float.parseFloat(jTable1.getValueAt(k, 1).toString()) * Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                                        txtTotal.setText(Float.toString(total));
                                        btnModificar.setText("Modificar");
                                        btnBorrar.setEnabled(true);
                                        LimpiarSeleccion();
                                        Limpiar();
                                    }
                                }
                            }
                        } else {
                            d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                            d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                            if (detalle.ActualizarStockInsumos(d)) {
                                cantmod++;
                                modelo.setValueAt(txtProducto.getText(), k, 0);
                                modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), k, 1);
                                modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), k, 2);
                                //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                CalcularMontoTotal();
                                Limpiar();
                                btnModificar.setText("Modificar");
                                btnBorrar.setEnabled(true);
                                LimpiarSeleccion();
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos Insumo, Cantidad y Precio");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = jTable1.getSelectedRow();
        switch (btnModificar.getText()) {
            case "Modificar":
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                } else {
                    btnModificar.setText("Cancelar");
                    btnBorrar.setEnabled(false);
                    txtProducto.setText(jTable1.getValueAt(fila, 0).toString());
                    txtPrecio.setText(jTable1.getValueAt(fila, 1).toString());
                    txtCantidad.setText(jTable1.getValueAt(fila, 2).toString());
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                    txtTotal.setText(Float.toString(total));
                    //jTextField6.setText(Float.toString((float) 0.0));
                }
                break;
            case "Modificar Detalle":
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                } else {
                    btnModificar.setText("Cancelar Modificar Detalle");
                    btnBorrar.setEnabled(false);
                    txtProducto.setText(jTable1.getValueAt(fila, 0).toString());
                    txtPrecio.setText(jTable1.getValueAt(fila, 1).toString());
                    txtCantidad.setText(jTable1.getValueAt(fila, 2).toString());
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                    txtTotal.setText(Float.toString(total));
                    //jTextField6.setText(Float.toString((float) 0.0));
                }
                break;
            default:
                total = total + (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                txtTotal.setText(Float.toString(total));
                btnModificar.setText("Modificar");
                btnBorrar.setEnabled(true);
                LimpiarSeleccion();
                Limpiar();
                break;
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        int fila = jTable1.getSelectedRow();
        if (btnBorrar.getText().equals("Borrar")) {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                d.setCantidad(Float.parseFloat(jTable1.getValueAt(fila, 2).toString()));
                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(fila, 0).toString()));
                if (detalle.SumarCantidadRestadaInsumos(d)) {
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                    txtTotal.setText(Float.toString(total));
                    //jTextField6.setText(Float.toString((float) 0.0));
                    modelo.removeRow(fila);
                }
            }
        } else {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int j = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar un detalle venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (j == 0) {
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                    txtTotal.setText(Float.toString(total));
                    //jTextField6.setText(Float.toString((float) 0.0));
                    btnBorrar.setText("Borrar");
                    if (sql.SafeUpdates()) {
                        iddetallesventas.add(Integer.parseInt(iddetalles.get(fila)));
                        idproductos.add(detalle.ObtenerIDProducto2(Integer.parseInt(iddetalles.get(fila))));
                        d.setCantidad(Float.parseFloat(jTable1.getValueAt(fila, 2).toString()));
                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(fila, 0).toString()));
                        if (detalle.SumarCantidadRestadaInsumos(d)) {
                            d.setIddetalleventa(Integer.parseInt(iddetalles.get(fila)));
                            if (detalle.EliminarDetalleVenta(d)) {
                                cant++;
                                if (filasdetalle - 1 == 0 && jTable1.getRowCount() == 1) {
                                    iddetalles.remove(fila);
                                    modelo.removeRow(fila);
                                    filasdetalle = filasdetalle - 1;
                                    String[] opciones = {"Seguir", "No seguir"};
                                    Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                                    int i = JOptionPane.showOptionDialog(null, "Ha eliminado todos las ventas registradas de esta Factura. Quiere seguir agregando nuevas ventas o prefiere eliminar totalmente dicha Factura?", "Confimar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, iconopreg, opciones, opciones[0]);
                                    if (i == 0) {
                                        /*mc.setIdmovimiento(Integer.parseInt(idventa));
                                        mc.setIdtipomovimiento(10);
                                        if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                                            v.setIdventa(Integer.parseInt(idventa));
                                            if (venta.EliminarVenta(v)) {
                                                jComboBox1.setSelectedIndex(0);
                                                ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
                                                jTextField3.setText("");
                                                jTextField5.setText("");
                                                jTextField4.setText("");
                                                jTextField6.setText(Float.toString((float) 0.0));
                                                jTextField2.setText(Float.toString((float) 0.0));
                                                jButton1.setText("Registrar Venta");
                                                jButton3.setEnabled(false);
                                            }
                                        }*/
                                        this.setTitle("Registrando nueva venta Factura N° " + nrofactura);
                                        cbxUsuario.setSelectedIndex(0);
                                        ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
                                        txtProducto.setText("");
                                        txtPrecio.setText("");
                                        txtCantidad.setText("");
                                        //jTextField6.setText(Float.toString((float) 0.0));
                                        txtTotal.setText(Float.toString((float) 0.0));
                                        btnGuardarVenta.setText("Registrar Venta");
                                        btnModificarVenta.setEnabled(true);
                                        btnModificarVenta.setText("Cancelar");
                                        btnAgregar.setText("Agregar");
                                        btnModificar.setText("Modificar");
                                        btnBorrar.setText("Quitar");
                                    } else {
                                        mc.setIdmovimiento(Integer.parseInt(idventa));
                                        mc.setIdtipomovimiento(10);
                                        if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                                            v.setIdventa(Integer.parseInt(idventa));
                                            if (venta.EliminarVenta(v)) {
                                                lista = new vListas_Ventas();
                                                vMenuPrincipal.jDesktopPane1.add(lista);
                                                lista.setVisible(true);
                                                lista.toFront();
                                                this.dispose();
                                            }
                                        }
                                    }
                                } else {
                                    iddetalles.remove(fila);
                                    modelo.removeRow(fila);
                                    filasdetalle = filasdetalle - 1;
                                }
                            }

                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnGuardarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarVentaActionPerformed
        if (filasdetalle == 0) {
            cantmod = 0;
        }
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        if (btnGuardarVenta.getText().equals("Registrar Venta") && this.getTitle().equals("Registrar Venta")) {
            if (!cbxUsuario.getSelectedItem().equals("(*) Seleccione Usuario..") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (rbTipoVenta.isSelected() || jRadioButton2.isSelected()) {
                    if (!btnModificar.getText().equals("Cancelar")) {
                        if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                            if (jTable1.getRowCount() != 0) {
                                v.setIdusuario(venta.ObtenerIDUsuario((String) cbxUsuario.getSelectedItem()));
                                v.setMontototal(Float.parseFloat(txtTotal.getText()));
                                if (rbTipoVenta.isSelected()) {
                                    v.setTipoVenta(rbTipoVenta.getText());
                                } else {
                                    v.setTipoVenta(jRadioButton2.getText());
                                }
                                if (venta.EfectuarVenta(v)) {
                                    for (int g = 0; g < jTable1.getRowCount(); g++) {
                                        d.setIdventa(detalle.ObtenerIDVenta());
                                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(g, 0).toString()));
                                        d.setCantidad((int) Float.parseFloat(jTable1.getValueAt(g, 2).toString()));
                                        d.setPrecio(Float.parseFloat(jTable1.getValueAt(g, 1).toString()));
                                        if (detalle.RegistrarDetalleVenta(d)) {

                                        }
                                    }
                                    for (int h = 0; h < jTable1.getRowCount(); h++) {
                                        p.setPrecioventa(Float.parseFloat(jTable1.getValueAt(h, 1).toString()));
                                        p.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(h, 0).toString()));
                                        if (productos.ActualizarPrecios(p)) {

                                        }
                                    }
                                    if (sql.SafeUpdates()) {
                                        JOptionPane.showMessageDialog(null, "Venta Registrada");
                                        int idmovimiento = sql.obtenerUltimoId("ventas", "idventa");
                                        String codigo = sql.generaCodigo("venta");
                                        sql.ejecutarSql("UPDATE ventas SET NroFactura ='" + codigo + "' WHERE idventa=" + idmovimiento);
                                        v.setNrofactura(codigo);
                                        mc.setDescripcion("VENTA PRODUCTOS");
                                        mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                                        mc.setIdtipomovimiento(10);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setNromovimiento(codigo);
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(v.getMontototal());
                                        mc.setIdmovimiento(idmovimiento);
                                        mc.setActivo(1);
                                        mc.setTipoVenta(v.getTipoVenta());
                                        control_mc.InsertarMovimientosCaja(mc);
                                        lista = new vListas_Ventas();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        lista.toFront();
                                        this.dispose();
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Debes generar al menos, una venta");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, termine de Modificar el/los dato/s");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar que tipo de venta se va efectuar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        } else if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant > 0 && cantmod > 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int filasventa = jTable1.getRowCount();
                if (sql.SafeUpdates()) {
                    if (filasventa > filasdetalle) {
                        for (int q = filasdetalle; q < filasventa; q++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(q, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(q, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }

                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }

                    } else {
                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }
                    }
                }

                for (int x = 0; x < iddetallesventas.size(); x++) {
                    for (int m = 0; m < idproductos.size(); m++) {
                        if (sql.SafeUpdates()) {
                            d.setIddetalleventa(iddetallesventas.get(x));
                            d.setIdproducto(idproductos.get(m));
                            if (detalle.ActualizarStockInsumos2(d)) {
                                d.setIddetalleventa(iddetallesventas.get(x));
                                d.setIdproducto(idproductos.get(m));
                                if (detalle.ActualizarDevolverPrecio(d)) {
                                    d.setIddetalleventa(iddetallesventas.get(x));
                                    if (detalle.AnularEliminacionVenta(d)) {
                                        x++;
                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant > 0 && cantmod == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (jTable1.getRowCount() != 0) {
                    if (sql.SafeUpdates()) {
                        for (int n = 0; n < jTable1.getRowCount(); n++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(n, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(n, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }
                    }
                }
                for (int x = 0; x < iddetallesventas.size(); x++) {
                    for (int m = 0; m < idproductos.size(); m++) {
                        if (sql.SafeUpdates()) {
                            d.setIddetalleventa(iddetallesventas.get(x));
                            d.setIdproducto(idproductos.get(m));
                            if (detalle.ActualizarStockInsumos2(d)) {
                                d.setIddetalleventa(iddetallesventas.get(x));
                                d.setIdproducto(idproductos.get(m));
                                if (detalle.ActualizarDevolverPrecio(d)) {
                                    d.setIddetalleventa(iddetallesventas.get(x));
                                    if (detalle.AnularEliminacionVenta(d)) {
                                        x++;
                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant == 0 && cantmod > 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int filasventa = jTable1.getRowCount();
                if (sql.SafeUpdates()) {
                    if (filasventa > filasdetalle) {
                        for (int q = filasdetalle; q < filasventa; q++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(q, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(q, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }

                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }

                    } else {
                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant == 0 && cantmod == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (sql.SafeUpdates()) {
                    int filasventa = jTable1.getRowCount();
                    if (filasventa > filasdetalle) {
                        for (int q = filasdetalle; q < filasventa; q++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(q, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(q, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnGuardarVenta.getText().equals("Registrar Venta") && this.getTitle().equals("Registrando nueva venta Factura N° " + nrofactura)) {
            if (!cbxUsuario.getSelectedItem().equals("(*) Seleccione Usuario..") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (rbTipoVenta.isSelected() || jRadioButton2.isSelected()) {
                    if (!btnModificar.getText().equals("Cancelar")) {
                        if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                            if (jTable1.getRowCount() != 0) {
                                v.setIdusuario(venta.ObtenerIDUsuario((String) cbxUsuario.getSelectedItem()));
                                v.setMontototal(Float.parseFloat(txtTotal.getText()));
                                if (rbTipoVenta.isSelected()) {
                                    v.setTipoVenta(rbTipoVenta.getText());
                                } else {
                                    v.setTipoVenta(jRadioButton2.getText());
                                }
                                v.setIdventa(Integer.parseInt(idventa));
                                if (venta.EditarVenta(v)) {
                                    for (int g = 0; g < jTable1.getRowCount(); g++) {
                                        d.setIdventa(Integer.parseInt(idventa));
                                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(g, 0).toString()));
                                        d.setCantidad((int) Float.parseFloat(jTable1.getValueAt(g, 2).toString()));
                                        d.setPrecio(Float.parseFloat(jTable1.getValueAt(g, 1).toString()));
                                        if (detalle.RegistrarDetalleVenta(d)) {

                                        }
                                    }
                                    for (int h = 0; h < jTable1.getRowCount(); h++) {
                                        p.setPrecioventa(Float.parseFloat(jTable1.getValueAt(h, 1).toString()));
                                        p.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(h, 0).toString()));
                                        if (productos.ActualizarPrecios(p)) {

                                        }
                                    }
                                    if (sql.SafeUpdates()) {
                                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(idventa), 10));
                                        mc.setDescripcion("VENTA PRODUCTOS");
                                        mc.setIdtipomovimiento(10);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(v.getMontototal());
                                        mc.setIdmovimiento(Integer.parseInt(idventa));
                                        mc.setTipoVenta(v.getTipoVenta());
                                        control_mc.EditarMovimientosCaja(mc);
                                        JOptionPane.showMessageDialog(null, "Modificacion completa de Factura N° " + nrofactura);
                                        lista = new vListas_Ventas();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        lista.toFront();
                                        this.dispose();
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Debes generar al menos, una venta");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, termine de Modificar el/los dato/s");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar que tipo de venta se va efectuar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        }
    }//GEN-LAST:event_btnGuardarVentaActionPerformed

    private void btnModificarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarVentaActionPerformed
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        int filasventa = jTable1.getRowCount();
        if (!btnModificarVenta.getText().equals("Cancelar") && !this.getTitle().equals("Registrando nueva venta Factura N° " + nrofactura)) {
            if (!cbxUsuario.getSelectedItem().equals("(*) Seleccione Usuario..") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (rbTipoVenta.isSelected() || jRadioButton2.isSelected()) {
                    if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                        if (!btnModificar.getText().equals("Cancelar")) {
                            if (jTable1.getRowCount() != 0) {
                                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                                if (i == 0) {
                                    if (filasventa > filasdetalle) {
                                        for (int l = filasdetalle; l < filasventa; l++) {
                                            d.setIdventa(Integer.parseInt(idventa));
                                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(l, 0).toString()));
                                            d.setCantidad((int) Float.parseFloat(jTable1.getValueAt(l, 2).toString()));
                                            d.setPrecio(Float.parseFloat(jTable1.getValueAt(l, 1).toString()));
                                            if (detalle.RegistrarDetalleVenta(d)) {
                                                p.setPrecioventa(Float.parseFloat(jTable1.getValueAt(l, 1).toString()));
                                                p.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(l, 0).toString()));
                                                if (productos.ActualizarPrecios(p)) {

                                                }
                                            }
                                        }
                                        for (int f = 0; f < iddetalles.size(); f++) {
                                            iddetalle = iddetalles.get(f);
                                            if (sql.SafeUpdates()) {
                                                v.setIdusuario(venta.ObtenerIDUsuario((String) cbxUsuario.getSelectedItem()));
                                                v.setMontototal(Float.parseFloat(txtTotal.getText()));
                                                if (rbTipoVenta.isSelected()) {
                                                    v.setTipoVenta(rbTipoVenta.getText());
                                                } else {
                                                    v.setTipoVenta(jRadioButton2.getText());
                                                }
                                                v.setIdventa(Integer.parseInt(idventa));
                                                if (venta.EditarVenta(v)) {

                                                }
                                            }
                                        }
                                        for (int ñ = 0; ñ < filasdetalle; ñ++) {
                                            for (int k = 0; k < iddetalles.size(); k++) {
                                                iddetalle = iddetalles.get(k);
                                                d.setIdventa(Integer.parseInt(idventa));
                                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(ñ, 0).toString()));
                                                d.setCantidad((int) Float.parseFloat(jTable1.getValueAt(ñ, 2).toString()));
                                                d.setPrecio(Float.parseFloat(jTable1.getValueAt(ñ, 1).toString()));
                                                d.setIddetalleventa(Integer.parseInt(iddetalle));
                                                if (detalle.EditarDetalleVenta(d)) {
                                                    if (k + 1 < iddetalles.size()) {
                                                        p.setPrecioventa(Float.parseFloat(jTable1.getValueAt(ñ, 1).toString()));
                                                        p.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(ñ, 0).toString()));
                                                        if (productos.ActualizarPrecios(p)) {
                                                            ñ = ñ + 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(idventa), 10));
                                        mc.setDescripcion("VENTA PRODUCTOS");
                                        mc.setIdtipomovimiento(10);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(v.getMontototal());
                                        mc.setIdmovimiento(Integer.parseInt(idventa));
                                        mc.setTipoVenta(v.getTipoVenta());
                                        control_mc.EditarMovimientosCaja(mc);
                                        JOptionPane.showMessageDialog(null, "Modificacion Completa");
                                        lista = new vListas_Ventas();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        lista.toFront();
                                        this.dispose();
                                    } else {
                                        for (int f = 0; f < iddetalles.size(); f++) {
                                            iddetalle = iddetalles.get(f);
                                            if (sql.SafeUpdates()) {
                                                v.setIdusuario(venta.ObtenerIDUsuario((String) cbxUsuario.getSelectedItem()));
                                                v.setMontototal(Float.parseFloat(txtTotal.getText()));
                                                if (rbTipoVenta.isSelected()) {
                                                    v.setTipoVenta(rbTipoVenta.getText());
                                                } else {
                                                    v.setTipoVenta(jRadioButton2.getText());
                                                }
                                                v.setIdventa(Integer.parseInt(idventa));
                                                if (venta.EditarVenta(v)) {

                                                }
                                            }
                                        }
                                        for (int z = 0; z < filasdetalle; z++) {
                                            for (int k = 0; k < iddetalles.size(); k++) {
                                                iddetalle = iddetalles.get(k);
                                                d.setIdventa(Integer.parseInt(idventa));
                                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(z, 0).toString()));
                                                d.setCantidad((int) Float.parseFloat(jTable1.getValueAt(z, 2).toString()));
                                                d.setPrecio(Float.parseFloat(jTable1.getValueAt(z, 1).toString()));
                                                d.setIddetalleventa(Integer.parseInt(iddetalle));
                                                if (detalle.EditarDetalleVenta(d)) {
                                                    if (k + 1 < iddetalles.size()) {
                                                        p.setPrecioventa(Float.parseFloat(jTable1.getValueAt(z, 1).toString()));
                                                        p.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(z, 0).toString()));
                                                        if (productos.ActualizarPrecios(p)) {
                                                            z = z + 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(idventa), 10));
                                        mc.setDescripcion("VENTA PRODUCTOS");
                                        mc.setIdtipomovimiento(10);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(v.getMontototal());
                                        mc.setIdmovimiento(Integer.parseInt(idventa));
                                        mc.setTipoVenta(v.getTipoVenta());
                                        control_mc.EditarMovimientosCaja(mc);
                                        JOptionPane.showMessageDialog(null, "Modificacion Completa");
                                        lista = new vListas_Ventas();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        lista.toFront();
                                        this.dispose();
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Debes generar al menos, una venta producto");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, termine de Modificar el/los dato/s");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar que tipo de venta se va efectuar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar nueva venta para Factura N° " + nrofactura + "?. De optar por si, la misma se eliminara", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (jTable1.getRowCount() != 0) {
                    if (sql.SafeUpdates()) {
                        for (int n = 0; n < jTable1.getRowCount(); n++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(n, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(n, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }
                    }
                }
                mc.setIdmovimiento(Integer.parseInt(idventa));
                mc.setIdtipomovimiento(10);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    v.setIdventa(Integer.parseInt(idventa));
                    if (venta.EliminarVenta(v)) {
                        lista = new vListas_Ventas();
                        vMenuPrincipal.jDesktopPane1.add(lista);
                        lista.setVisible(true);
                        lista.toFront();
                        this.dispose();
                    }
                }
            }
        }
    }//GEN-LAST:event_btnModificarVentaActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (filasdetalle == 0) {
            cantmod = 0;
        }
        if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant > 0 && cantmod > 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int filasventa = jTable1.getRowCount();
                if (sql.SafeUpdates()) {
                    if (filasventa > filasdetalle) {
                        for (int q = filasdetalle; q < filasventa; q++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(q, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(q, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }
                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }

                    } else {
                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }
                    }
                }

                for (int x = 0; x < iddetallesventas.size(); x++) {
                    for (int m = 0; m < idproductos.size(); m++) {
                        if (sql.SafeUpdates()) {
                            d.setIddetalleventa(iddetallesventas.get(x));
                            d.setIdproducto(idproductos.get(m));
                            if (detalle.ActualizarStockInsumos2(d)) {
                                d.setIddetalleventa(iddetallesventas.get(x));
                                d.setIdproducto(idproductos.get(m));
                                if (detalle.ActualizarDevolverPrecio(d)) {
                                    d.setIddetalleventa(iddetallesventas.get(x));
                                    if (detalle.AnularEliminacionVenta(d)) {
                                        x++;
                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant > 0 && cantmod == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (jTable1.getRowCount() != 0) {
                    if (sql.SafeUpdates()) {
                        for (int n = 0; n < jTable1.getRowCount(); n++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(n, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(n, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }
                    }
                }
                for (int x = 0; x < iddetallesventas.size(); x++) {
                    for (int m = 0; m < idproductos.size(); m++) {
                        if (sql.SafeUpdates()) {
                            d.setIddetalleventa(iddetallesventas.get(x));
                            d.setIdproducto(idproductos.get(m));
                            if (detalle.ActualizarStockInsumos2(d)) {
                                d.setIddetalleventa(iddetallesventas.get(x));
                                d.setIdproducto(idproductos.get(m));
                                if (detalle.ActualizarDevolverPrecio(d)) {
                                    d.setIddetalleventa(iddetallesventas.get(x));
                                    if (detalle.AnularEliminacionVenta(d)) {
                                        x++;
                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant == 0 && cantmod > 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                int filasventa = jTable1.getRowCount();
                if (sql.SafeUpdates()) {
                    if (filasventa > filasdetalle) {
                        for (int q = filasdetalle; q < filasventa; q++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(q, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(q, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }

                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }

                    } else {
                        for (int b = 0; b < filasdetalle; b++) {
                            String prod = detalle.ObtenerDescripcionProd(Integer.parseInt(iddetalles.get(b)));
                            if (jTable1.getValueAt(b, 0).toString().equals(prod)) {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                    if (detalle.ActualizarStockInsumos2(d)) {

                                    }
                                }
                            } else {
                                d.setCantidad(Float.parseFloat(jTable1.getValueAt(b, 2).toString()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(b, 0).toString()));
                                if (detalle.SumarCantidadRestadaInsumos(d)) {
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    d.setIddetalleventa(Integer.parseInt(iddetalles.get(b)));
                                    if (detalle.ActualizarStockInsumos3(d)) {

                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnGuardarVenta.getText().trim().equals("Cancelar") && cant == 0 && cantmod == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (sql.SafeUpdates()) {
                    int filasventa = jTable1.getRowCount();
                    if (filasventa > filasdetalle) {
                        for (int q = filasdetalle; q < filasventa; q++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(q, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(q, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }
                    }
                }
                lista = new vListas_Ventas();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (btnModificarVenta.getText().equals("Cancelar") && this.getTitle().equals("Registrando nueva venta Factura N° " + nrofactura)) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar nueva venta para Factura N° " + nrofactura + "?. De optar por si, la misma se eliminara", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (jTable1.getRowCount() != 0) {
                    if (sql.SafeUpdates()) {
                        for (int n = 0; n < jTable1.getRowCount(); n++) {
                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(n, 2).toString()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(n, 0).toString()));
                            detalle.SumarCantidadRestadaInsumos(d);
                        }
                    }
                }
                mc.setIdmovimiento(Integer.parseInt(idventa));
                mc.setIdtipomovimiento(10);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    v.setIdventa(Integer.parseInt(idventa));
                    if (venta.EliminarVenta(v)) {
                        lista = new vListas_Ventas();
                        vMenuPrincipal.jDesktopPane1.add(lista);
                        lista.setVisible(true);
                        lista.toFront();
                        this.dispose();
                    }
                }
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jTable1.getRowCount() != 0 || !cbxUsuario.getSelectedItem().equals("(*) Seleccione Usuario..") || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("") || !txtProducto.getText().equals("") || !txtPrecio.getText().equals("") || !txtCantidad.getText().equals("") || rbTipoVenta.isSelected() || jRadioButton2.isSelected()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (jTable1.getRowCount() != 0) {
                    for (int q = 0; q < jTable1.getRowCount(); q++) {
                        d.setCantidad(Float.parseFloat(jTable1.getValueAt(q, 2).toString()));
                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(q, 0).toString()));
                        detalle.SumarCantidadRestadaInsumos(d);
                    }
                }
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyTyped
        /*if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }*/
    }//GEN-LAST:event_txtProductoKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char[] p = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', KeyEvent.VK_BACK_SPACE};
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
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        char[] p = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', KeyEvent.VK_BACK_SPACE};
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
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jTable2.getRowCount() != 0) {
            int i = jTable2.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarProducto.dispose();
                txtProducto.setText(jTable2.getValueAt(i, 0).toString());
                txtPrecio.setText(jTable2.getValueAt(i, 1).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos productos todavia");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
            txtProducto.setText("");
            txtPrecio.setText("");
        } else {
            vSeleccionarProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void vSeleccionarProductoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarProductoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
            txtProducto.setText("");
            txtPrecio.setText("");
        } else {
            vSeleccionarProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarProductoWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        vSeleccionarProducto.setSize(330, 612);
        vSeleccionarProducto.setLocationRelativeTo(this);
        vSeleccionarProducto.setModal(true);
        vSeleccionarProducto.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (jTable1.getRowCount() != 0 && btnModificar.getText().equals("Modificar") || btnModificar.getText().equals("Modificar Detalle")) {
            jTable1.clearSelection();
            jTable1.getSelectionModel().clearSelection();
            btnBorrar.setText("Borrar");
            btnModificar.setText("Modificar");
        }
    }//GEN-LAST:event_formMouseClicked

    private void vSeleccionarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarProductoMouseClicked
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
        MostrarProductos();
        jTextField7.setText("");
    }//GEN-LAST:event_vSeleccionarProductoMouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (!jTextField7.getText().isEmpty()) {
            String[] columnas = {"PRODUCTOS", "PRECIO"};
            produc = detalle.MostrarProductoBuscado(jTextField7.getText());
            if (produc.length != 0) {
                modelprod = new DefaultTableModel(produc, columnas);
                jTable2.setModel(modelprod);
                EliminarFilasVaciasProductos();
                ocultar_columnasprod();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        ArrayList<String> array;
        if (i != -1) {
            txtProducto.setText(jList2.getSelectedValue());
            array = venta.ObtenerDatosProd(txtProducto.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    txtPrecio.setText(array.get(j));
                    jList2.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void txtProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyReleased
        ListaProductos();
    }//GEN-LAST:event_txtProductoKeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int contstockneg = 0;
        if (jTable3.getRowCount() != 0) {
            for (int i = 0; i < jTable3.getRowCount(); i++) {
                if (jTable3.getColumnName(2).equals("STOCK CONFIRMANDO VENTA")) {
                    if (Float.parseFloat(jTable3.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                } else if (jTable3.getColumnName(2).equals("STOCK FINAL")) {
                    if (Float.parseFloat(jTable3.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                }

            }
        }
        if (contstockneg > 0) {
            int i = jTable1.getSelectedRow();
            switch (btnModificar.getText()) {
                case "Modificar":
                    vStocksProductos.dispose();
                    Limpiar();
                    break;
                case "Cancelar":
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                    break;
                default:
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                    break;
            }
        } else {
            int i = jTable1.getSelectedRow();
            if (btnModificar.getText().equals("Modificar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        Object datos[] = {txtProducto.getText(), (Float.parseFloat(txtPrecio.getText())), (Float.parseFloat(txtCantidad.getText()))};
                        modelo.addRow(datos);
                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            } else if (btnModificar.getText().equals("Cancelar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        modelo.setValueAt(txtProducto.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), i, 2);
                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                }
            } else if (btnModificar.getText().equals("Cancelar Modificar Detalle")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        cantmod++;
                        modelo.setValueAt(txtProducto.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), i, 2);
                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void vStocksProductosWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vStocksProductosWindowClosing
        int contstockneg = 0;
        if (jTable3.getRowCount() != 0) {
            for (int i = 0; i < jTable3.getRowCount(); i++) {
                if (jTable3.getColumnName(2).equals("STOCK CONFIRMANDO VENTA")) {
                    if (Float.parseFloat(jTable3.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                } else if (jTable3.getColumnName(2).equals("STOCK FINAL")) {
                    if (Float.parseFloat(jTable3.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                }

            }
        }
        if (contstockneg > 0) {
            int i = jTable1.getSelectedRow();
            switch (btnModificar.getText()) {
                case "Modificar":
                    vStocksProductos.dispose();
                    Limpiar();
                    break;
                case "Cancelar":
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                    break;
                default:
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                    break;
            }
        } else {
            int i = jTable1.getSelectedRow();
            if (btnModificar.getText().equals("Modificar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        Object datos[] = {txtProducto.getText(), (Float.parseFloat(txtPrecio.getText())), (Float.parseFloat(txtCantidad.getText()))};
                        modelo.addRow(datos);
                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            } else if (btnModificar.getText().equals("Cancelar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        modelo.setValueAt(txtProducto.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), i, 2);
                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                }
            } else if (btnModificar.getText().equals("Cancelar Modificar Detalle")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + txtProducto.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(txtCantidad.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(txtProducto.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        cantmod++;
                        modelo.setValueAt(txtProducto.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(txtPrecio.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(txtCantidad.getText()), i, 2);
                        //jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        txtTotal.setText(Float.toString(total));
                        btnModificar.setText("Modificar");
                        btnBorrar.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                }
            }
        }
    }//GEN-LAST:event_vStocksProductosWindowClosing

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if (btnModificar.getText().equals("Cancelar")) {
            jTable1.setFocusable(false);
        } else {
            //jTextField6.setText(String.valueOf(Float.parseFloat(modelo.getValueAt(jTable1.getSelectedRow(), 2).toString()) * Float.parseFloat(modelo.getValueAt(jTable1.getSelectedRow(), 1).toString())));
            int i = jTable1.getSelectedRow(), j = filasdetalle;
            if (i < j) {
                btnBorrar.setText("Borrar Detalle");
                btnModificar.setText("Modificar Detalle");
            } else if (i >= j) {
                btnBorrar.setText("Borrar");
                btnModificar.setText("Modificar");
            }
        }
    }//GEN-LAST:event_jTable1MousePressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int i = JOptionPane.showConfirmDialog(null, "Cancelar Operación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vListas_Ventas lista_ventas = new vListas_Ventas();
            vMenuPrincipal.jDesktopPane1.add(lista_ventas);
            lista_ventas.setVisible(true);
            dispose();
        } else {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAgregar;
    public static javax.swing.JButton btnBorrar;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnGuardarVenta;
    public static javax.swing.JButton btnModificar;
    public static javax.swing.JButton btnModificarVenta;
    public static javax.swing.JComboBox<String> cbxUsuario;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField7;
    public static javax.swing.JRadioButton rbTipoVenta;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtPrecio;
    public static javax.swing.JTextField txtProducto;
    public static javax.swing.JTextField txtTotal;
    private javax.swing.JDialog vSeleccionarProducto;
    private javax.swing.JDialog vStocksProductos;
    // End of variables declaration//GEN-END:variables
}
