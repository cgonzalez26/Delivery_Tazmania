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
        jTextField6.setText(Float.toString((float) 0.0));
        jTextField2.setText(Float.toString((float) 0.0));
        jTextField6.setEditable(false);
        jTextField2.setEditable(false);
        jButton3.setEnabled(false);
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
        jComboBox1.setEnabled(false);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jTextField4.setEnabled(false);
        jTextField5.setEnabled(false);
        jTextField6.setEnabled(false);
        jDateChooser1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton6.setEnabled(false);
        jButton7.setEnabled(false);
        jButton8.setEnabled(false);
        jButton1.setEnabled(false);
        jButton3.setEnabled(false);
        jLabel5.setVisible(true);
    }

    public void CrearColumnas() {
        modelo = new DefaultTableModel();
        modelo.addColumn("PRODUCTO");
        modelo.addColumn("PRECIO");
        modelo.addColumn("CANTIDAD");
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
        if (jButton7.getText().equals("Cancelar Modificar Detalle")) {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK MODIFICANDO VENTA";
            columnas[2] = "STOCK CONFIRMANDO VENTA";
        } else {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK ACTUAL";
            columnas[2] = "STOCK FINAL";
        }
        stockprod = detalle.MostrarProductoStockN_MOD(jTextField3.getText(), Float.parseFloat(jTextField4.getText()));
        modelstockprod = new DefaultTableModel(stockprod, columnas);
        jTable3.setModel(modelstockprod);
        EliminiarFilasVaciasStockProd();
    }

    public void MostrarProductosStockMOD() {
        if (jTable1.getRowCount() != 0) {
            String[] columnas = {"INSUMOS", "STOCK ACTUAL", "STOCK MODIFICANDO VENTA", "STOCK FINAL VENTA"};
            int i = jTable1.getSelectedRow();
            stockprod = detalle.MostrarProductosStockMOD(jTextField3.getText(), Float.parseFloat(jTable1.getValueAt(i, 2).toString()), Float.parseFloat(jTextField4.getText()));
            modelstockprod = new DefaultTableModel(stockprod, columnas);
            jTable3.setModel(modelstockprod);
            EliminiarFilasVaciasStockProd();
        }
    }

    public void ListaProductos() {
        listprod = combo.list("productos", "descripcion", jTextField3.getText());
        String substr = jTextField3.getText().toLowerCase();
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
                        if (jTextField3.getText().isEmpty()) {
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
        for (int j = 0; j < jComboBox1.getItemCount(); j++) {
            if (jComboBox1.getItemAt(j) == null) {
                jComboBox1.removeItemAt(j);
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
            total = total + (Float.parseFloat(jTextField5.getText().trim()) * Float.parseFloat(jTextField4.getText().trim()));
            jTextField2.setText(Float.toString(total));

        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return total;
    }

    public void ComboUsuario() {
        user = combo.combox("usuarios", "Login");
        for (Object user1 : user) {
            jComboBox1.addItem((String) user1);
        }
    }

    public void Limpiar() {
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jList2 = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();

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

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccione Usuario.." }));
        jComboBox1.setFocusable(false);
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 202, 33));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("(*) Fecha.");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 161, 29));

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 196, 33));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("TOTAL");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 440, 105, 23));

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 470, 150, 32));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("(*) Cantidad:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 440, 99, 23));

        jTextField4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, 143, 32));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) Precio:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 440, 113, 23));

        jTextField5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 470, 148, 32));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel8.setText("Sub Total");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 440, 120, 23));

        jTextField6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 470, 140, 32));

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Registrar Venta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 530, -1, -1));

        jButton3.setBackground(new java.awt.Color(252, 249, 57));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Modificar Venta");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 530, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos a Vender", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton8.setText("Borrar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setText("Agregar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton7.setText("Modificar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jButton6)
                .addGap(37, 37, 37)
                .addComponent(jButton7)
                .addGap(37, 37, 37)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 1188, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Usuario responsable de la venta");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 202, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("No Hay CAJA ABIERTA.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 540, 150, 30));

        jPanel2.setBackground(new java.awt.Color(255, 248, 177));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "(*) Tipo de Venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jRadioButton1.setBackground(new java.awt.Color(255, 248, 177));
        jRadioButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jRadioButton1.setText("Local");

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
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, -1, 80));

        jTextField3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 221, 30));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 470, 39, 32));

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

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("(*) Producto:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, -1, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        boolean repetido = false;
        if (!jTextField3.getText().equals("") && !jTextField4.getText().equals("") && !jTextField5.getText().equals("")) {
            if (jTable1.getRowCount() != 0) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    if (jTextField3.getText().equals(jTable1.getValueAt(i, 0).toString()) && jButton7.getText().equals("Modificar")) {
                        JOptionPane.showMessageDialog(null, "Producto ya ingresado!");
                        repetido = true;
                    }
                }
            }
            if (repetido == false) {
                if (jButton7.getText().equals("Modificar")) {
                    int stockneg = detalle.ConsultarStockNegativosN_MOD(jTextField3.getText(), Float.parseFloat(jTextField4.getText()));
                    int stockcero = detalle.ConsultarStockCeroN_MOD(jTextField3.getText(), Float.parseFloat(jTextField4.getText()));
                    if (stockneg > 0) {
                        String[] opciones = {"Ver Informe", "No ver y seguir Venta"};
                        //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + jTextField3.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                        if (i == 0) {
                            MostrarProductosStockN_MOD();
                            jLabel11.setText(jTextField3.getText());
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
                        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica la venta para este producto " + jTextField3.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                        if (i == 0) {
                            MostrarProductosStockN_MOD();
                            jLabel11.setText(jTextField3.getText());
                            color = new ColorearFilas(2);
                            jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                            vStocksProductos.setSize(727, 560);
                            vStocksProductos.setLocationRelativeTo(this);
                            vStocksProductos.setModal(true);
                            vStocksProductos.setVisible(true);
                        } else {
                            int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (g == 0) {
                                d.setCantidad(Float.parseFloat(jTextField4.getText()));
                                d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                                if (detalle.ActualizarStockInsumos(d)) {
                                    Object datos[] = {jTextField3.getText(), (Float.parseFloat(jTextField5.getText())), (Float.parseFloat(jTextField4.getText()))};
                                    modelo.addRow(datos);
                                    jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                    CalcularMontoTotal();
                                    Limpiar();
                                }
                            } else {
                                Limpiar();
                            }
                        }
                    } else {
                        d.setCantidad(Float.parseFloat(jTextField4.getText()));
                        d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                        if (detalle.ActualizarStockInsumos(d)) {
                            Object datos[] = {jTextField3.getText(), (Float.parseFloat(jTextField5.getText())), (Float.parseFloat(jTextField4.getText()))};
                            modelo.addRow(datos);
                            jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                            CalcularMontoTotal();
                            Limpiar();
                        }
                    }
                } else if (jButton7.getText().equals("Cancelar")) {
                    int i = jTable1.getSelectedRow();
                    if (i == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                        detalle.SumarCantidadRestadaInsumos(d);
                        int stockneg = 0, stockcero = 0;
                        stockneg = detalle.ConsultarStockNegativosN_MOD(jTextField3.getText(), Float.parseFloat(jTextField4.getText()));
                        stockcero = detalle.ConsultarStockCeroN_MOD(jTextField3.getText(), Float.parseFloat(jTextField4.getText()));

                        if (stockneg > 0) {
                            String[] opciones = {"Ver Informe", "No ver y seguir Venta"};
                            //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                            int l = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + jTextField3.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                            if (l == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(jTextField3.getText());
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
                                    jTextField2.setText(Float.toString(total));
                                    jButton7.setText("Modificar");
                                    jButton8.setEnabled(true);
                                    LimpiarSeleccion();
                                    Limpiar();
                                }
                            }
                        } else if (stockcero > 0) {
                            String[] opc = {"Ver Informe", "No ver y seguir Venta"};
                            int j = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica la venta para este producto " + jTextField3.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                            if (j == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(jTextField3.getText());
                                color = new ColorearFilas(2);
                                jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                                vStocksProductos.setSize(727, 560);
                                vStocksProductos.setLocationRelativeTo(this);
                                vStocksProductos.setModal(true);
                                vStocksProductos.setVisible(true);
                            } else {
                                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (g == 0) {
                                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        modelo.setValueAt(jTextField3.getText(), i, 0);
                                        modelo.setValueAt(Float.parseFloat(jTextField5.getText()), i, 1);
                                        modelo.setValueAt(Float.parseFloat(jTextField4.getText()), i, 2);
                                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                        CalcularMontoTotal();
                                        Limpiar();
                                        jButton7.setText("Modificar");
                                        jButton8.setEnabled(true);
                                        LimpiarSeleccion();
                                    }
                                } else {
                                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                                        jTextField2.setText(Float.toString(total));
                                        jButton7.setText("Modificar");
                                        jButton8.setEnabled(true);
                                        LimpiarSeleccion();
                                        Limpiar();
                                    }
                                }
                            }
                        } else {
                            d.setCantidad(Float.parseFloat(jTextField4.getText()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                            if (detalle.ActualizarStockInsumos(d)) {
                                modelo.setValueAt(jTextField3.getText(), i, 0);
                                modelo.setValueAt(Float.parseFloat(jTextField5.getText()), i, 1);
                                modelo.setValueAt(Float.parseFloat(jTextField4.getText()), i, 2);
                                jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                CalcularMontoTotal();
                                Limpiar();
                                jButton7.setText("Modificar");
                                jButton8.setEnabled(true);
                                LimpiarSeleccion();
                            }
                        }
                    }
                } else if (jButton7.getText().equals("Cancelar Modificar Detalle")) {
                    int k = jTable1.getSelectedRow();
                    if (k == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        d.setCantidad(Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                        d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(k, 0).toString()));
                        detalle.SumarCantidadRestadaInsumos(d);
                        int stockneg = 0, stockcero = 0;
                        stockneg = detalle.ConsultarStockNegativosN_MOD(jTextField3.getText(), Float.parseFloat(jTextField4.getText()));
                        stockcero = detalle.ConsultarStockCeroN_MOD(jTextField3.getText(), Float.parseFloat(jTextField4.getText()));
                        if (stockneg > 0) {
                            String[] opciones = {"Ver Informe", "No ver y seguir Venta"};
                            //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                            int l = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + jTextField3.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                            if (l == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(jTextField3.getText());
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
                                    jTextField2.setText(Float.toString(total));
                                    jButton7.setText("Modificar");
                                    jButton8.setEnabled(true);
                                    LimpiarSeleccion();
                                    Limpiar();
                                }
                            }
                        } else if (stockcero > 0) {
                            String[] opc = {"Ver Informe", "No ver y seguir Venta"};
                            int j = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica la venta para este producto " + jTextField3.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                            if (j == 0) {
                                MostrarProductosStockN_MOD();
                                jLabel11.setText(jTextField3.getText());
                                color = new ColorearFilas(2);
                                jTable3.getColumnModel().getColumn(2).setCellRenderer(color);
                                vStocksProductos.setSize(727, 560);
                                vStocksProductos.setLocationRelativeTo(this);
                                vStocksProductos.setModal(true);
                                vStocksProductos.setVisible(true);
                            } else {
                                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (g == 0) {
                                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        cantmod++;
                                        modelo.setValueAt(jTextField3.getText(), k, 0);
                                        modelo.setValueAt(Float.parseFloat(jTextField5.getText()), k, 1);
                                        modelo.setValueAt(Float.parseFloat(jTextField4.getText()), k, 2);
                                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                        CalcularMontoTotal();
                                        Limpiar();
                                        jButton7.setText("Modificar");
                                        jButton8.setEnabled(true);
                                        LimpiarSeleccion();
                                    }

                                } else {
                                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(k, 0).toString()));
                                    if (detalle.ActualizarStockInsumos(d)) {
                                        total = total + (Float.parseFloat(jTable1.getValueAt(k, 1).toString()) * Float.parseFloat(jTable1.getValueAt(k, 2).toString()));
                                        jTextField2.setText(Float.toString(total));
                                        jButton7.setText("Modificar");
                                        jButton8.setEnabled(true);
                                        LimpiarSeleccion();
                                        Limpiar();
                                    }
                                }
                            }
                        } else {
                            d.setCantidad(Float.parseFloat(jTextField4.getText()));
                            d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                            if (detalle.ActualizarStockInsumos(d)) {
                                cantmod++;
                                modelo.setValueAt(jTextField3.getText(), k, 0);
                                modelo.setValueAt(Float.parseFloat(jTextField5.getText()), k, 1);
                                modelo.setValueAt(Float.parseFloat(jTextField4.getText()), k, 2);
                                jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                                CalcularMontoTotal();
                                Limpiar();
                                jButton7.setText("Modificar");
                                jButton8.setEnabled(true);
                                LimpiarSeleccion();
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos Insumo, Cantidad y Precio");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int fila = jTable1.getSelectedRow();
        switch (jButton7.getText()) {
            case "Modificar":
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                } else {
                    jButton7.setText("Cancelar");
                    jButton8.setEnabled(false);
                    jTextField3.setText(jTable1.getValueAt(fila, 0).toString());
                    jTextField5.setText(jTable1.getValueAt(fila, 1).toString());
                    jTextField4.setText(jTable1.getValueAt(fila, 2).toString());
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                    jTextField2.setText(Float.toString(total));
                    jTextField6.setText(Float.toString((float) 0.0));
                }
                break;
            case "Modificar Detalle":
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                } else {
                    jButton7.setText("Cancelar Modificar Detalle");
                    jButton8.setEnabled(false);
                    jTextField3.setText(jTable1.getValueAt(fila, 0).toString());
                    jTextField5.setText(jTable1.getValueAt(fila, 1).toString());
                    jTextField4.setText(jTable1.getValueAt(fila, 2).toString());
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                    jTextField2.setText(Float.toString(total));
                    jTextField6.setText(Float.toString((float) 0.0));
                }
                break;
            default:
                total = total + (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                jTextField2.setText(Float.toString(total));
                jButton7.setText("Modificar");
                jButton8.setEnabled(true);
                LimpiarSeleccion();
                Limpiar();
                break;
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (jButton8.getText().equals("Borrar")) {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                d.setCantidad(Float.parseFloat(jTable1.getValueAt(fila, 2).toString()));
                d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(fila, 0).toString()));
                if (detalle.SumarCantidadRestadaInsumos(d)) {
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 2).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 1).toString()));
                    jTextField2.setText(Float.toString(total));
                    jTextField6.setText(Float.toString((float) 0.0));
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
                    jTextField2.setText(Float.toString(total));
                    jTextField6.setText(Float.toString((float) 0.0));
                    jButton8.setText("Borrar");
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
                                        jComboBox1.setSelectedIndex(0);
                                        ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
                                        jTextField3.setText("");
                                        jTextField5.setText("");
                                        jTextField4.setText("");
                                        jTextField6.setText(Float.toString((float) 0.0));
                                        jTextField2.setText(Float.toString((float) 0.0));
                                        jButton1.setText("Registrar Venta");
                                        jButton3.setEnabled(true);
                                        jButton3.setText("Cancelar");
                                        jButton6.setText("Agregar");
                                        jButton7.setText("Modificar");
                                        jButton8.setText("Borrar");
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
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (filasdetalle == 0) {
            cantmod = 0;
        }
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        if (jButton1.getText().equals("Registrar Venta") && this.getTitle().equals("Registrar Venta")) {
            if (!jComboBox1.getSelectedItem().equals("(*) Seleccione Usuario..") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
                    if (!jButton7.getText().equals("Cancelar")) {
                        if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                            if (jTable1.getRowCount() != 0) {
                                v.setIdusuario(venta.ObtenerIDUsuario((String) jComboBox1.getSelectedItem()));
                                v.setMontototal(Float.parseFloat(jTextField2.getText()));
                                if (jRadioButton1.isSelected()) {
                                    v.setTipoVenta(jRadioButton1.getText());
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
        } else if (jButton1.getText().trim().equals("Cancelar") && cant > 0 && cantmod > 0) {
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
        } else if (jButton1.getText().trim().equals("Cancelar") && cant > 0 && cantmod == 0) {
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
        } else if (jButton1.getText().trim().equals("Cancelar") && cant == 0 && cantmod > 0) {
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
        } else if (jButton1.getText().trim().equals("Cancelar") && cant == 0 && cantmod == 0) {
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
        } else if (jButton1.getText().equals("Registrar Venta") && this.getTitle().equals("Registrando nueva venta Factura N° " + nrofactura)) {
            if (!jComboBox1.getSelectedItem().equals("(*) Seleccione Usuario..") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
                    if (!jButton7.getText().equals("Cancelar")) {
                        if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                            if (jTable1.getRowCount() != 0) {
                                v.setIdusuario(venta.ObtenerIDUsuario((String) jComboBox1.getSelectedItem()));
                                v.setMontototal(Float.parseFloat(jTextField2.getText()));
                                if (jRadioButton1.isSelected()) {
                                    v.setTipoVenta(jRadioButton1.getText());
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        int filasventa = jTable1.getRowCount();
        if (!jButton3.getText().equals("Cancelar") && !this.getTitle().equals("Registrando nueva venta Factura N° " + nrofactura)) {
            if (!jComboBox1.getSelectedItem().equals("(*) Seleccione Usuario..") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
                    if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                        if (!jButton7.getText().equals("Cancelar")) {
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
                                                v.setIdusuario(venta.ObtenerIDUsuario((String) jComboBox1.getSelectedItem()));
                                                v.setMontototal(Float.parseFloat(jTextField2.getText()));
                                                if (jRadioButton1.isSelected()) {
                                                    v.setTipoVenta(jRadioButton1.getText());
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
                                                v.setIdusuario(venta.ObtenerIDUsuario((String) jComboBox1.getSelectedItem()));
                                                v.setMontototal(Float.parseFloat(jTextField2.getText()));
                                                if (jRadioButton1.isSelected()) {
                                                    v.setTipoVenta(jRadioButton1.getText());
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
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (filasdetalle == 0) {
            cantmod = 0;
        }
        if (jButton1.getText().trim().equals("Cancelar") && cant > 0 && cantmod > 0) {
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
        } else if (jButton1.getText().trim().equals("Cancelar") && cant > 0 && cantmod == 0) {
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
        } else if (jButton1.getText().trim().equals("Cancelar") && cant == 0 && cantmod > 0) {
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
        } else if (jButton1.getText().trim().equals("Cancelar") && cant == 0 && cantmod == 0) {
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
        } else if (jButton3.getText().equals("Cancelar") && this.getTitle().equals("Registrando nueva venta Factura N° " + nrofactura)) {
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
        } else if (jTable1.getRowCount() != 0 || !jComboBox1.getSelectedItem().equals("(*) Seleccione Usuario..") || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("") || !jTextField3.getText().equals("") || !jTextField5.getText().equals("") || !jTextField4.getText().equals("") || jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
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

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        /*if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }*/
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
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
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
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
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jTable2.getRowCount() != 0) {
            int i = jTable2.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarProducto.dispose();
                jTextField3.setText(jTable2.getValueAt(i, 0).toString());
                jTextField5.setText(jTable2.getValueAt(i, 1).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos productos todavia");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
            jTextField3.setText("");
            jTextField5.setText("");
        } else {
            vSeleccionarProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void vSeleccionarProductoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarProductoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
            jTextField3.setText("");
            jTextField5.setText("");
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
        if (jTable1.getRowCount() != 0 && jButton7.getText().equals("Modificar") || jButton7.getText().equals("Modificar Detalle")) {
            jTable1.clearSelection();
            jTable1.getSelectionModel().clearSelection();
            jButton8.setText("Borrar");
            jButton7.setText("Modificar");
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
            jTextField3.setText(jList2.getSelectedValue());
            array = venta.ObtenerDatosProd(jTextField3.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    jTextField5.setText(array.get(j));
                    jList2.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        ListaProductos();
    }//GEN-LAST:event_jTextField3KeyReleased

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
            switch (jButton7.getText()) {
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
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
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
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                    break;
            }
        } else {
            int i = jTable1.getSelectedRow();
            if (jButton7.getText().equals("Modificar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        Object datos[] = {jTextField3.getText(), (Float.parseFloat(jTextField5.getText())), (Float.parseFloat(jTextField4.getText()))};
                        modelo.addRow(datos);
                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            } else if (jButton7.getText().equals("Cancelar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        modelo.setValueAt(jTextField3.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(jTextField5.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(jTextField4.getText()), i, 2);
                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                }
            } else if (jButton7.getText().equals("Cancelar Modificar Detalle")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        cantmod++;
                        modelo.setValueAt(jTextField3.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(jTextField5.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(jTextField4.getText()), i, 2);
                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
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
            switch (jButton7.getText()) {
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
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
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
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                    break;
            }
        } else {
            int i = jTable1.getSelectedRow();
            if (jButton7.getText().equals("Modificar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        Object datos[] = {jTextField3.getText(), (Float.parseFloat(jTextField5.getText())), (Float.parseFloat(jTextField4.getText()))};
                        modelo.addRow(datos);
                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            } else if (jButton7.getText().equals("Cancelar")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        modelo.setValueAt(jTextField3.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(jTextField5.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(jTextField4.getText()), i, 2);
                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                }
            } else if (jButton7.getText().equals("Cancelar Modificar Detalle")) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField3.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    d.setCantidad(Float.parseFloat(jTextField4.getText()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTextField3.getText()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        cantmod++;
                        modelo.setValueAt(jTextField3.getText(), i, 0);
                        modelo.setValueAt(Float.parseFloat(jTextField5.getText()), i, 1);
                        modelo.setValueAt(Float.parseFloat(jTextField4.getText()), i, 2);
                        jTextField6.setText(String.valueOf(Float.parseFloat(jTextField5.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                    }
                } else {
                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                    d.setIdproducto(detalle.ObtenerIDProducto(jTable1.getValueAt(i, 0).toString()));
                    if (detalle.ActualizarStockInsumos(d)) {
                        vStocksProductos.dispose();
                        total = total + (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                        jTextField2.setText(Float.toString(total));
                        jButton7.setText("Modificar");
                        jButton8.setEnabled(true);
                        LimpiarSeleccion();
                        Limpiar();
                    }
                }
            }
        }
    }//GEN-LAST:event_vStocksProductosWindowClosing

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if (jButton7.getText().equals("Cancelar")) {
            jTable1.setFocusable(false);
        } else {
            jTextField6.setText(String.valueOf(Float.parseFloat(modelo.getValueAt(jTable1.getSelectedRow(), 2).toString()) * Float.parseFloat(modelo.getValueAt(jTable1.getSelectedRow(), 1).toString())));
            int i = jTable1.getSelectedRow(), j = filasdetalle;
            if (i < j) {
                jButton8.setText("Borrar Detalle");
                jButton7.setText("Modificar Detalle");
            } else if (i >= j) {
                jButton8.setText("Borrar");
                jButton7.setText("Modificar");
            }
        }
    }//GEN-LAST:event_jTable1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    public static javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    public static javax.swing.JButton jButton6;
    public static javax.swing.JButton jButton7;
    public static javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    public static javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTextField jTextField2;
    public static javax.swing.JTextField jTextField3;
    public static javax.swing.JTextField jTextField4;
    public static javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JDialog vSeleccionarProducto;
    private javax.swing.JDialog vStocksProductos;
    // End of variables declaration//GEN-END:variables
}
