package Vistas;

import Controlador.ColorearFilas;
import Controlador.control_ConsumosEmpleados;
import Controlador.control_existencias;
import Modelo.ConsumosEmpleados;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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

/**
 *
 * @author Colo-PC
 */
public final class vConsumosEmpleados extends javax.swing.JInternalFrame {

    control_ConsumosEmpleados contr_consumoempleado = new control_ConsumosEmpleados();
    control_existencias combo = new control_existencias();
    ConsumosEmpleados ce = new ConsumosEmpleados();
    ColorearFilas color;
    DefaultTableModel tabla1, tabla2, tabla3, modelstockprod;
    Object[][] dato1, dato2, dato3, stockprod;
    Timestamp fechaseleccionada;
    String id, cant, prod;
    ArrayList<String> listemp, listprod;
    DefaultListModel list;

    public vConsumosEmpleados() {
        initComponents();
        IniciarFechas();
        MostrarDatos();
        MostrarEmpleados();
        MostrarProductos();
        jList2.setVisible(false);
        jList3.setVisible(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    jButton3.setEnabled(false);
                    jButton4.setText("Cancelar");
                    jButton5.setText("Modificar");
                    String fecha = jTable1.getValueAt(fila, 5).toString();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vConsumosEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    id = jTable1.getValueAt(fila, 0).toString();
                    cant = jTable1.getValueAt(fila, 4).toString();
                    prod = jTable1.getValueAt(fila, 1).toString();
                    jTextField1.setText(jTable1.getValueAt(fila, 2).toString());
                    jTextField2.setText(jTable1.getValueAt(fila, 3).toString());
                    jTextField3.setText(jTable1.getValueAt(fila, 4).toString());
                    jDateChooser1.setDate(fechaseleccionada);
                }
            }
        });
    }

    public void IniciarFechas() {
        Date hoy = new Date();
        jDateChooser2.setDate(hoy);
        jDateChooser3.setDate(hoy);
    }

    public void LimpiarSeleccionTabla1() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionTabla2() {
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionTabla3() {
        jTable3.clearSelection();
        jTable3.getSelectionModel().clearSelection();
    }

    public void MostrarDatos() {
        String desde = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        String hasta = ((JTextField) jDateChooser3.getDateEditor().getUiComponent()).getText();
        String columnas[] = {"IDCONSUMO", "IDPRODUCTO", "NOMBRE EMPLEADO", "PRODUCTO", "CANTIDAD", "FECHA"};
        dato1 = contr_consumoempleado.MostrarDatos(desde, hasta);
        tabla1 = new DefaultTableModel(dato1, columnas);
        jTable1.setModel(tabla1);
        EliminarFilasVaciasTabla1();
        ocultarcolumnastabla1();
    }

    public void EliminarFilasVaciasTabla1() {
        if (jTable1.getRowCount() != 0) {
            for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                    if (jTable1.getValueAt(fila, columna) == null) {
                        tabla1.removeRow(fila);
                    }
                }
            }
        }
    }

    public void ocultarcolumnastabla1() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    public void MostrarEmpleados() {
        String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
        dato2 = contr_consumoempleado.MostrarEmpleados();
        tabla2 = new DefaultTableModel(dato2, columnas);
        jTable2.setModel(tabla2);
        EliminarFilasVaciasTabla2();
    }

    public void ListaEmpleados() {
        listemp = combo.list("empleados", "Nombre", jTextField1.getText());
        String substr = jTextField1.getText().toLowerCase();
        list = new DefaultListModel();
        jList2.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listemp.size(); i++) {
            if (listemp.get(i) == null) {
                listemp.remove(i);
            } else {
                String sublist = listemp.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listemp.get(i));
                    jList2.setVisible(true);
                    if (jTextField1.getText().isEmpty()) {
                        jList2.setVisible(false);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasTabla2() {
        if (jTable2.getRowCount() != 0) {
            int filas = jTable2.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable2.getValueAt(fila, 0) == null) {
                    tabla2.removeRow(fila);
                }
            }
        }
    }

    public void MostrarProductosStockN_MOD() {
        String[] columnas = new String[3];
        if (jButton5.getText().equals("Modificar")) {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK MODIFICADO";
            columnas[2] = "STOCK FINAL";
        } else {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK ACTUAL";
            columnas[2] = "STOCK FINAL";
        }
        stockprod = contr_consumoempleado.MostrarProductoStockN_MOD(jTextField2.getText(), Float.parseFloat(jTextField3.getText()));
        modelstockprod = new DefaultTableModel(stockprod, columnas);
        jTable4.setModel(modelstockprod);
        EliminiarFilasVaciasStockProd();
    }

    public void MostrarProductosStockMOD() {
        if (jTable1.getRowCount() != 0) {
            int i = jTable1.getSelectedRow();
            String[] columnas = {"INSUMOS", "STOCK ACTUAL", "STOCK MODIFICADO", "STOCK FINAL"};
            stockprod = contr_consumoempleado.MostrarProductosStockMOD(jTextField2.getText(), Float.parseFloat(jTable1.getValueAt(i, 4).toString()), Float.parseFloat(jTextField3.getText()));
            modelstockprod = new DefaultTableModel(stockprod, columnas);
            jTable4.setModel(modelstockprod);
            EliminiarFilasVaciasStockProd();
        }
    }

    public void MostrarProductos() {
        String columnas[] = {"IDPROD", "PRODUCTO"};
        dato3 = contr_consumoempleado.MostrarProductos();
        tabla3 = new DefaultTableModel(dato3, columnas);
        jTable3.setModel(tabla3);
        EliminarFilasVaciasTabla3();
        ocultarcolumnastabla3();
    }

    public void ListaProductos() {
        listprod = combo.list("productos", "descripcion", jTextField2.getText());
        String substr = jTextField2.getText().toLowerCase();
        list = new DefaultListModel();
        jList3.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listprod.size(); i++) {
            if (listprod.get(i) == null) {
                listprod.remove(i);
            } else {
                String sublist = listprod.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listprod.get(i));
                    jList3.setVisible(true);
                    if (jTextField2.getText().isEmpty()) {
                        jList3.setVisible(false);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasTabla3() {
        if (jTable3.getRowCount() != 0) {
            int filas = jTable3.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable3.getValueAt(fila, 0) == null) {
                    tabla3.removeRow(fila);
                }
            }
        }
    }

    public void EliminiarFilasVaciasStockProd() {
        if (jTable4.getRowCount() != 0) {
            int filas = jTable4.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable4.getValueAt(fila, 0) == null) {
                    modelstockprod.removeRow(fila);
                }
            }
        }
    }

    public void ocultarcolumnastabla3() {
        jTable3.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable3.getColumnModel().getColumn(0).setMinWidth(0);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void Limpiar() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarEmpleado = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        vSeleccionarProducto = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        vStocksProductos = new javax.swing.JDialog();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton13 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jList2 = new javax.swing.JList<>();
        jList3 = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        vSeleccionarEmpleado.setTitle("Seleccionar Empleado");
        java.awt.Image iconodeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vSeleccionarEmpleado.setIconImage(iconodeliv);
        vSeleccionarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarEmpleadoMouseClicked(evt);
            }
        });
        vSeleccionarEmpleado.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarEmpleadoWindowClosing(evt);
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setText("Nombre:");

        jTextField4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton7.setText("Buscar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton8.setText("Agregar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton9.setText("Cancelar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vSeleccionarEmpleadoLayout = new javax.swing.GroupLayout(vSeleccionarEmpleado.getContentPane());
        vSeleccionarEmpleado.getContentPane().setLayout(vSeleccionarEmpleadoLayout);
        vSeleccionarEmpleadoLayout.setHorizontalGroup(
            vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
            .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        vSeleccionarEmpleadoLayout.setVerticalGroup(
            vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addGap(11, 11, 11))
        );

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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("Producto");

        jTextField5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton10.setText("Buscar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel6))
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(2, 2, 2)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap())
        );

        jButton11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton11.setText("Agregar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton12.setText("Cancelar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vSeleccionarProductoLayout = new javax.swing.GroupLayout(vSeleccionarProducto.getContentPane());
        vSeleccionarProducto.getContentPane().setLayout(vSeleccionarProductoLayout);
        vSeleccionarProductoLayout.setHorizontalGroup(
            vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarProductoLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addGap(86, 86, 86))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(vSeleccionarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        vSeleccionarProductoLayout.setVerticalGroup(
            vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton11))
                .addContainerGap())
        );

        java.awt.Image iconodel = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vStocksProductos.setIconImage(iconodel);
        vStocksProductos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vStocksProductosWindowClosing(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel10.setText("Producto:");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jTable4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable4);

        jButton13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton13.setText("Aceptar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
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
                        .addGap(0, 286, Short.MAX_VALUE)
                        .addGroup(vStocksProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vStocksProductosLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(174, 174, 174))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vStocksProductosLayout.createSequentialGroup()
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(303, 303, 303))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vStocksProductosLayout.createSequentialGroup()
                        .addComponent(jScrollPane4)
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addContainerGap())
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consumos de Empleados");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(866, 561));
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

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, 145, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("(*) Fecha");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 128, -1, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("(*) Cantidad");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 128, -1, 30));

        jTextField3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 118, 28));

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) Seleccionar Empleado");
        jLayeredPane1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 170, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel8.setText("(*) Seleccionar Producto");
        jLayeredPane1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, -1, 30));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 42, 30));

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jLayeredPane1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 186, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 42, 30));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jLayeredPane1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 196, 30));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jLayeredPane1.add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 196, -1));

        jList3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList3.setValueIsAdjusting(true);
        jList3.setVisibleRowCount(0);
        jList3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList3MouseClicked(evt);
            }
        });
        jLayeredPane1.add(jList3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 186, -1));

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jScrollPane1.setOpaque(false);

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
        jTable1.setOpaque(false);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 830, 275));

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Agregar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, 85, -1));

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 500, 92, -1));

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Eliminar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 500, 89, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jDateChooser2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateChooser3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Desde");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("Hasta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton6)
                .addGap(10, 10, 10))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(187, 11, -1, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!jTextField1.getText().equals("") && !jTextField2.getText().equals("") && !jTextField3.getText().isEmpty() && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().isEmpty()) {
            if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                int stockneg = contr_consumoempleado.ConsultarStockNegativosN_MOD(jTextField2.getText(), Float.parseFloat(jTextField3.getText()));
                int stockcero = contr_consumoempleado.ConsultarStockCeroN_MOD(jTextField2.getText(), Float.parseFloat(jTextField3.getText()));
                if (stockneg > 0) {
                    String[] opciones = {"Ver Informe", "No ver y seguir"};
                    //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                    int i = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + jTextField2.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                    if (i == 0) {
                        MostrarProductosStockN_MOD();
                        jLabel11.setText(jTextField2.getText());
                        color = new ColorearFilas(2);
                        jTable4.getColumnModel().getColumn(2).setCellRenderer(color);
                        vStocksProductos.setSize(727, 560);
                        vStocksProductos.setLocationRelativeTo(this);
                        vStocksProductos.setModal(true);
                        vStocksProductos.setVisible(true);
                    } else {
                        Limpiar();
                    }
                } else if (stockcero > 0) {
                    String[] opc = {"Ver Informe", "No ver y seguir"};
                    int i = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica dicho informe para este producto " + jTextField2.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                    if (i == 0) {
                        MostrarProductosStockN_MOD();
                        jLabel11.setText(jTextField2.getText());
                        color = new ColorearFilas(2);
                        jTable4.getColumnModel().getColumn(2).setCellRenderer(color);
                        vStocksProductos.setSize(727, 560);
                        vStocksProductos.setLocationRelativeTo(this);
                        vStocksProductos.setModal(true);
                        vStocksProductos.setVisible(true);
                    } else {
                        int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField2.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (g == 0) {
                            ce.setNomempleado(jTextField1.getText());
                            ce.setProducto(jTextField2.getText());
                            ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                            if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                                ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                                ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                                if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                                    JOptionPane.showMessageDialog(null, "Informe agregado.");
                                    MostrarDatos();
                                    Limpiar();
                                }
                            }
                        } else {
                            Limpiar();
                        }
                    }
                } else {
                    ce.setNomempleado(jTextField1.getText());
                    ce.setProducto(jTextField2.getText());
                    ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                    if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                        ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                        ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Informe agregado.");
                            MostrarDatos();
                            Limpiar();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        vSeleccionarEmpleado.setSize(470, 635);
        vSeleccionarEmpleado.setLocationRelativeTo(this);
        vSeleccionarEmpleado.setModal(true);
        vSeleccionarEmpleado.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (jTable2.getRowCount() != 0) {
            int i = jTable2.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                vSeleccionarEmpleado.dispose();
                jTextField1.setText(jTable2.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos empleados todavia");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (!jTextField4.getText().isEmpty()) {
            dato2 = contr_consumoempleado.MostrarEmpleadosBuscado(jTextField4.getText());
            if (dato2.length != 0) {
                String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
                tabla2 = new DefaultTableModel(dato2, columnas);
                jTable2.setModel(tabla2);
                EliminarFilasVaciasTabla2();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void vSeleccionarEmpleadoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarEmpleadoWindowClosing

    private void vSeleccionarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoMouseClicked
        MostrarEmpleados();
        LimpiarSeleccionTabla2();
    }//GEN-LAST:event_vSeleccionarEmpleadoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        vSeleccionarProducto.setSize(348, 613);
        vSeleccionarProducto.setLocationRelativeTo(this);
        vSeleccionarProducto.setModal(true);
        vSeleccionarProducto.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (jTable3.getRowCount() != 0) {
            int i = jTable3.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                vSeleccionarProducto.dispose();
                ce.setIdproducto(Integer.parseInt(jTable3.getValueAt(i, 0).toString()));
                jTextField2.setText(jTable3.getValueAt(i, 1).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos empleados todavia");
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
        } else {
            vSeleccionarProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (!jTextField5.getText().isEmpty()) {
            dato3 = contr_consumoempleado.MostrarProductosBuscado(jTextField5.getText());
            if (dato3.length != 0) {
                String columnas[] = {"IDPROD", "PRODUCTO"};
                tabla3 = new DefaultTableModel(dato3, columnas);
                jTable3.setModel(tabla3);
                EliminarFilasVaciasTabla3();
                ocultarcolumnastabla3();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void vSeleccionarProductoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarProductoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
        } else {
            vSeleccionarProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarProductoWindowClosing

    private void vSeleccionarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarProductoMouseClicked
        MostrarProductos();
        LimpiarSeleccionTabla3();
        jTextField5.setText("");
    }//GEN-LAST:event_vSeleccionarProductoMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jButton4.getText().equals("Modificar")) {
            int fila = jTable1.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                jButton3.setEnabled(false);
                jButton4.setText("Cancelar");
                jButton5.setText("Modificar");
                String fecha = jTable1.getValueAt(fila, 5).toString();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(vConsumosEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                }
                cant = jTable1.getValueAt(fila, 4).toString();
                prod = jTable1.getValueAt(fila, 1).toString();
                id = jTable1.getValueAt(fila, 0).toString();
                jTextField1.setText(jTable1.getValueAt(fila, 2).toString());
                jTextField2.setText(jTable1.getValueAt(fila, 3).toString());
                jTextField3.setText(jTable1.getValueAt(fila, 4).toString());
                jDateChooser1.setDate(fechaseleccionada);
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jButton3.setEnabled(true);
                jButton4.setText("Modificar");
                jButton5.setText("Eliminar");
                Limpiar();
                LimpiarSeleccionTabla1();
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (jButton5.getText().equals("Modificar")) {
            if (jTable1.getRowCount() != 0) {
                int l = jTable1.getSelectedRow();
                if (l == -1) {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                } else {
                    if (!jTextField1.getText().equals("") && !jTextField2.getText().equals("") && !jTextField3.getText().isEmpty() && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().isEmpty()) {
                        if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                            int m = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                            if (m == 0) {
                                ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                                ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                                contr_consumoempleado.CancelarStockConsumidoLocal(ce);
                                int stockneg = contr_consumoempleado.ConsultarStockNegativosN_MOD(jTextField2.getText(), Float.parseFloat(jTextField3.getText()));
                                int stockcero = contr_consumoempleado.ConsultarStockCeroN_MOD(jTextField2.getText(), Float.parseFloat(jTextField3.getText()));
                                if (stockneg > 0) {
                                    String[] opciones = {"Ver Informe", "No ver y seguir"};
                                    //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                                    int i = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + jTextField2.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
                                    if (i == 0) {
                                        MostrarProductosStockN_MOD();
                                        jLabel11.setText(jTextField2.getText());
                                        color = new ColorearFilas(2);
                                        jTable4.getColumnModel().getColumn(2).setCellRenderer(color);
                                        vStocksProductos.setSize(727, 560);
                                        vStocksProductos.setLocationRelativeTo(this);
                                        vStocksProductos.setModal(true);
                                        vStocksProductos.setVisible(true);
                                    } else {
                                        ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                                        ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                                            jButton3.setEnabled(true);
                                            jButton4.setText("Modificar");
                                            jButton5.setText("Eliminar");
                                            Limpiar();
                                            LimpiarSeleccionTabla1();
                                        }
                                    }
                                } else if (stockcero > 0) {
                                    String[] opc = {"Ver Informe", "No ver y seguir"};
                                    int i = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica dicho informe para este producto " + jTextField2.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
                                    if (i == 0) {
                                        MostrarProductosStockN_MOD();
                                        jLabel11.setText(jTextField2.getText());
                                        color = new ColorearFilas(2);
                                        jTable4.getColumnModel().getColumn(2).setCellRenderer(color);
                                        vStocksProductos.setSize(727, 560);
                                        vStocksProductos.setLocationRelativeTo(this);
                                        vStocksProductos.setModal(true);
                                        vStocksProductos.setVisible(true);
                                    } else {
                                        int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField2.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                        if (g == 0) {
                                            ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                                            ce.setNomempleado(jTextField1.getText());
                                            ce.setProducto(jTextField2.getText());
                                            ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                                            ce.setIdconsumo(Integer.parseInt(id));
                                            if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                                                ce.getCantidad();
                                                ce.getProducto();
                                                if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                                                    JOptionPane.showMessageDialog(null, "Modificado");
                                                    jButton3.setEnabled(true);
                                                    jButton4.setText("Modificar");
                                                    jButton5.setText("Eliminar");
                                                    MostrarDatos();
                                                    Limpiar();
                                                    LimpiarSeleccionTabla1();
                                                }
                                            }
                                        } else {
                                            ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                                            ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                                            if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                                                jButton3.setEnabled(true);
                                                jButton4.setText("Modificar");
                                                jButton5.setText("Eliminar");
                                                Limpiar();
                                                LimpiarSeleccionTabla1();
                                            }
                                        }
                                    }
                                } else {
                                    ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                                    ce.setNomempleado(jTextField1.getText());
                                    ce.setProducto(jTextField2.getText());
                                    ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                                    ce.setIdconsumo(Integer.parseInt(id));
                                    if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                                        ce.getCantidad();
                                        ce.getProducto();
                                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                                            JOptionPane.showMessageDialog(null, "Modificado");
                                            jButton3.setEnabled(true);
                                            jButton4.setText("Modificar");
                                            jButton5.setText("Eliminar");
                                            MostrarDatos();
                                            Limpiar();
                                            LimpiarSeleccionTabla1();
                                        }
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
                    }
                }
            }
        } else {
            int i = jTable1.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int j = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (j == 0) {
                    ce.setCantidad(Float.parseFloat(jTable1.getValueAt(i, 4).toString()));
                    ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(i, 1).toString()));
                    if (contr_consumoempleado.CancelarStockConsumidoLocal(ce)) {
                        ce.setIdconsumo(Integer.parseInt(jTable1.getValueAt(i, 0).toString()));
                        if (contr_consumoempleado.EliminarConsumosEmpleados(ce)) {
                            JOptionPane.showMessageDialog(null, "Eliminado");
                            MostrarDatos();
                        }
                    }
                } else {
                    LimpiarSeleccionTabla1();
                }
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButton4.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextField1.getText().equals("") || !jTextField2.getText().equals("") || !jTextField3.getText().isEmpty() || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (jButton5.getText().equals("Eliminar")) {
            LimpiarSeleccionTabla1();
            IniciarFechas();
            MostrarDatos();
        }
    }//GEN-LAST:event_formMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String desde = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        String hasta = ((JTextField) jDateChooser3.getDateEditor().getUiComponent()).getText();
        if (jDateChooser2.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateChooser3.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    String columnas[] = {"IDCONSUMO", "IDPRODUCTO", "NOMBRE EMPLEADO", "PRODUCTO", "CANTIDAD", "FECHA"};
                    dato1 = contr_consumoempleado.MostrarDatosBusqueda(desde, hasta);
                    if (dato1.length != 0) {
                        tabla1 = new DefaultTableModel(dato1, columnas);
                        jTable1.setModel(tabla1);
                        EliminarFilasVaciasTabla1();
                        ocultarcolumnastabla1();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron datos");
                    }
                } else if (desde.isEmpty() || hasta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar la fecha que falta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        if (i != -1) {
            jTextField1.setText(jList2.getSelectedValue());
            jList2.setVisible(false);
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jList3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList3MouseClicked
        int i = jList3.getSelectedIndex();
        if (i != -1) {
            jTextField2.setText(jList3.getSelectedValue());
            int j = contr_consumoempleado.ObtenerIDProducto(jTextField2.getText());
            ce.setIdproducto(j);
            jList3.setVisible(false);
        }
    }//GEN-LAST:event_jList3MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        ListaEmpleados();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        ListaProductos();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
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
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        int contstockneg = 0;
        if (jTable4.getRowCount() != 0) {
            for (int i = 0; i < jTable4.getRowCount(); i++) {
                if (jTable4.getColumnName(2).equals("STOCK FINAL")) {
                    if (Float.parseFloat(jTable4.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                }
            }
        }
        if (contstockneg > 0) {
            if (jButton5.getText().equals("Modificar")) {
                int l = jTable1.getSelectedRow();
                ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                    jButton3.setEnabled(true);
                    jButton4.setText("Modificar");
                    jButton5.setText("Eliminar");
                    Limpiar();
                    LimpiarSeleccionTabla1();
                }
            } else {
                vStocksProductos.dispose();
                Limpiar();
            }
        } else {
            if (jButton5.getText().equals("Modificar")) {
                int l = jTable1.getSelectedRow();
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField2.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                    ce.setNomempleado(jTextField1.getText());
                    ce.setProducto(jTextField2.getText());
                    ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                    ce.setIdconsumo(Integer.parseInt(id));
                    if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                        ce.getCantidad();
                        ce.getProducto();
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Modificado");
                            jButton3.setEnabled(true);
                            jButton4.setText("Modificar");
                            jButton5.setText("Eliminar");
                            MostrarDatos();
                            Limpiar();
                            LimpiarSeleccionTabla1();
                        }
                    }
                } else {
                    ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                    ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                    if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                        vStocksProductos.dispose();
                        jButton3.setEnabled(true);
                        jButton4.setText("Modificar");
                        jButton5.setText("Eliminar");
                        Limpiar();
                        LimpiarSeleccionTabla1();
                    }
                }
            } else {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField2.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setNomempleado(jTextField1.getText());
                    ce.setProducto(jTextField2.getText());
                    ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                    if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                        ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                        ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Informe agregado.");
                            MostrarDatos();
                            Limpiar();
                        }
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void vStocksProductosWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vStocksProductosWindowClosing
        int contstockneg = 0;
        if (jTable4.getRowCount() != 0) {
            for (int i = 0; i < jTable4.getRowCount(); i++) {
                if (jTable4.getColumnName(2).equals("STOCK FINAL")) {
                    if (Float.parseFloat(jTable4.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                }
            }
        }
        if (contstockneg > 0) {
            if (jButton5.getText().equals("Modificar")) {
                int l = jTable1.getSelectedRow();
                ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                    jButton3.setEnabled(true);
                    jButton4.setText("Modificar");
                    jButton5.setText("Eliminar");
                    Limpiar();
                    LimpiarSeleccionTabla1();
                }
            } else {
                vStocksProductos.dispose();
                Limpiar();
            }
        } else {
            if (jButton5.getText().equals("Modificar")) {
                int l = jTable1.getSelectedRow();
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField2.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                    ce.setNomempleado(jTextField1.getText());
                    ce.setProducto(jTextField2.getText());
                    ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                    ce.setIdconsumo(Integer.parseInt(id));
                    if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                        ce.getCantidad();
                        ce.getProducto();
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Modificado");
                            jButton3.setEnabled(true);
                            jButton4.setText("Modificar");
                            jButton5.setText("Eliminar");
                            MostrarDatos();
                            Limpiar();
                            LimpiarSeleccionTabla1();
                        }
                    }
                } else {
                    ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                    ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                    if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                        vStocksProductos.dispose();
                        jButton3.setEnabled(true);
                        jButton4.setText("Modificar");
                        jButton5.setText("Eliminar");
                        Limpiar();
                        LimpiarSeleccionTabla1();
                    }
                }
            } else {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextField2.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setNomempleado(jTextField1.getText());
                    ce.setProducto(jTextField2.getText());
                    ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                    if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                        ce.setCantidad(Float.parseFloat(jTextField3.getText()));
                        ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextField2.getText()));
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Informe agregado.");
                            MostrarDatos();
                            Limpiar();
                        }
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            }
        }
    }//GEN-LAST:event_vStocksProductosWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
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
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JDialog vSeleccionarEmpleado;
    private javax.swing.JDialog vSeleccionarProducto;
    private javax.swing.JDialog vStocksProductos;
    // End of variables declaration//GEN-END:variables
}
