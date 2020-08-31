package Vistas;

import Controlador.ColorearFilas;
import Controlador.control_ConsumosEmpleados;
import Controlador.control_existencias;
import Modelo.ConsumosEmpleados;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    DefaultTableModel tabla2, tabla3, modelstockprod;
    Object[][] dato1, dato2, dato3, stockprod;
    Timestamp fechaseleccionada;
    String id;
    public float cantidad;//cantidad para que se reste correctamente cuando se modifique un consumo
    public int idproducto;//producto con la misma idea para cantidad
    ArrayList<String> listemp, listprod;
    DefaultListModel list;
    vLista_ConsumosEmpleados consumos = null;

    public vConsumosEmpleados() {
        initComponents();
        //IniciarFechas();
        //MostrarDatos();
        MostrarEmpleados();
        MostrarProductos();
        listaEmpleado.setVisible(false);
        listaProducto.setVisible(false);
        /*jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    jButtonAgregar.setEnabled(false);
                    jButtonModificar.setText("Cancelar");
                    jButtonCancelar.setText("Modificar");
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
        });*/
    }

    /*public void IniciarFechas() {
        Date hoy = new Date();
        jDateChooser2.setDate(hoy);
        jDateChooser3.setDate(hoy);
    }*/

 /*public void LimpiarSeleccionTabla1() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }*/
    public void LimpiarSeleccionTablaEmp() {
        jTableEmpleados.clearSelection();
        jTableEmpleados.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionTablaProd() {
        jTableProductos.clearSelection();
        jTableProductos.getSelectionModel().clearSelection();
    }

    /*public void MostrarDatos() {
        String desde = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        String hasta = ((JTextField) jDateChooser3.getDateEditor().getUiComponent()).getText();
        String columnas[] = {"IDCONSUMO", "IDPRODUCTO", "NOMBRE EMPLEADO", "PRODUCTO", "CANTIDAD", "FECHA"};
        dato1 = contr_consumoempleado.MostrarDatos(desde, hasta);
        tabla1 = new DefaultTableModel(dato1, columnas);
        jTable1.setModel(tabla1);
        EliminarFilasVaciasTabla1();
        ocultarcolumnastabla1();
    }*/

 /*public void EliminarFilasVaciasTabla1() {
        if (jTable1.getRowCount() != 0) {
            for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                    if (jTable1.getValueAt(fila, columna) == null) {
                        tabla1.removeRow(fila);
                    }
                }
            }
        }
    }*/

 /*public void ocultarcolumnastabla1() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
    }*/
    public void VolverListaConsumosEmpleados() {
        consumos = new vLista_ConsumosEmpleados();
        vMenuPrincipal.jDesktopPane1.add(consumos);
        consumos.setVisible(true);
        dispose();
    }

    public void MostrarEmpleados() {
        String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
        dato2 = contr_consumoempleado.MostrarEmpleados();
        tabla2 = new DefaultTableModel(dato2, columnas);
        jTableEmpleados.setModel(tabla2);
        EliminarFilasVaciasTabla2();
    }

    public void ListaEmpleados() {
        listemp = combo.list("empleados", "Nombre", jTextFieldEmp.getText());
        String substr = jTextFieldEmp.getText().toLowerCase();
        list = new DefaultListModel();
        listaEmpleado.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listemp.size(); i++) {
            if (listemp.get(i) == null) {
                listemp.remove(i);
            } else {
                String sublist = listemp.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listemp.get(i));
                    listaEmpleado.setVisible(true);
                    if (jTextFieldEmp.getText().isEmpty()) {
                        listaEmpleado.setVisible(false);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasTabla2() {
        if (jTableEmpleados.getRowCount() != 0) {
            int filas = jTableEmpleados.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTableEmpleados.getValueAt(fila, 0) == null) {
                    tabla2.removeRow(fila);
                }
            }
        }
    }

    public void MostrarProductosStockN_MOD() {
        String[] columnas = new String[3];
        if (jButtonCancelar.getText().equals("Modificar")) {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK MODIFICADO";
            columnas[2] = "STOCK FINAL";
        } else {
            columnas[0] = "INSUMOS";
            columnas[1] = "STOCK ACTUAL";
            columnas[2] = "STOCK FINAL";
        }
        stockprod = contr_consumoempleado.MostrarProductoStockN_MOD(jTextFieldProd.getText(), Float.parseFloat(jTextFieldCantidad.getText()));
        modelstockprod = new DefaultTableModel(stockprod, columnas);
        jTableInforme.setModel(modelstockprod);
        EliminiarFilasVaciasStockProd();
    }

    public void MostrarProductos() {
        String columnas[] = {"IDPROD", "PRODUCTO"};
        dato3 = contr_consumoempleado.MostrarProductos();
        tabla3 = new DefaultTableModel(dato3, columnas);
        jTableProductos.setModel(tabla3);
        EliminarFilasVaciasTabla3();
        ocultarcolumnastabla3();
    }

    public void ListaProductos() {
        listprod = combo.list("productos", "descripcion", jTextFieldProd.getText());
        String substr = jTextFieldProd.getText().toLowerCase();
        list = new DefaultListModel();
        listaProducto.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listprod.size(); i++) {
            if (listprod.get(i) == null) {
                listprod.remove(i);
            } else {
                String sublist = listprod.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listprod.get(i));
                    listaProducto.setVisible(true);
                    if (jTextFieldProd.getText().isEmpty()) {
                        listaProducto.setVisible(false);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasTabla3() {
        if (jTableProductos.getRowCount() != 0) {
            int filas = jTableProductos.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTableProductos.getValueAt(fila, 0) == null) {
                    tabla3.removeRow(fila);
                }
            }
        }
    }

    public void EliminiarFilasVaciasStockProd() {
        if (jTableInforme.getRowCount() != 0) {
            int filas = jTableInforme.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTableInforme.getValueAt(fila, 0) == null) {
                    modelstockprod.removeRow(fila);
                }
            }
        }
    }

    public void ocultarcolumnastabla3() {
        jTableProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableProductos.getColumnModel().getColumn(0).setMinWidth(0);
        jTableProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void Limpiar() {
        //jTextFieldEmp.setText("");
        jTextFieldProd.setText("");
        jTextFieldCantidad.setText("");
        //((JTextField) jDateFecha.getDateEditor().getUiComponent()).setText("");
    }

    public void VerificarStockNegativoRegistrando() {
        String[] opciones = {"Ver Informe", "No ver y seguir"};
        //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + jTextFieldProd.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
        if (i == 0) {
            MostrarProductosStockN_MOD();//son aquellos productos que no fueron consumidos por dicho empleados (no fue registrado y no esta en la BD)
            jLabelProductoInforme.setText(jTextFieldProd.getText());
            color = new ColorearFilas(2);
            jTableInforme.getColumnModel().getColumn(2).setCellRenderer(color);
            vStocksProductos.setSize(727, 560);
            vStocksProductos.setLocationRelativeTo(this);
            vStocksProductos.setModal(true);
            vStocksProductos.setVisible(true);
        } else {
            Limpiar();
        }
    }

    public void VerificarStockCeroRegistrando() {
        String[] opc = {"Ver Informe", "No ver y seguir"};
        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica dicho informe para este producto " + jTextFieldProd.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
        if (i == 0) {
            MostrarProductosStockN_MOD();
            jLabelProductoInforme.setText(jTextFieldProd.getText());
            color = new ColorearFilas(2);
            jTableInforme.getColumnModel().getColumn(2).setCellRenderer(color);
            vStocksProductos.setSize(727, 560);
            vStocksProductos.setLocationRelativeTo(this);
            vStocksProductos.setModal(true);
            vStocksProductos.setVisible(true);
        } else {
            int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextFieldProd.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (g == 0) {
                ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                ce.setNomempleado(jTextFieldEmp.getText());
                ce.setProducto(jTextFieldProd.getText());
                ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                    ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                    ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                    if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                        JOptionPane.showMessageDialog(null, "Informe agregado");
                        //MostrarDatos();
                        //Limpiar();
                        VolverListaConsumosEmpleados();
                    }
                }
            } else {
                Limpiar();
            }
        }
    }

    public void VerificarStockNegativoModificando() {
        String[] opciones = {"Ver Informe", "No ver y seguir"};
        //Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que dichos stocks de los insumos del producto " + jTextFieldProd.getText() + ", puede tener problemas a futuro. Por lo tanto no se cargara el mismo, por favor revea los insumos de este producto.", "ATENCION!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
        if (i == 0) {
            MostrarProductosStockN_MOD();
            jLabelProductoInforme.setText(jTextFieldProd.getText());
            color = new ColorearFilas(2);
            jTableInforme.getColumnModel().getColumn(2).setCellRenderer(color);
            vStocksProductos.setSize(727, 560);
            vStocksProductos.setLocationRelativeTo(this);
            vStocksProductos.setModal(true);
            vStocksProductos.setVisible(true);
        } else {
            ce.setCantidad(cantidad);//ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
            ce.setIdproducto(idproducto);//ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
            if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                Limpiar();
                //LimpiarSeleccionTabla1();
            }
        }
    }

    public void VerificarStockCeroModificando() {
        String[] opc = {"Ver Informe", "No ver y seguir"};
        int i = JOptionPane.showOptionDialog(null, "Hemos verificado que si aplica dicho informe para este producto " + jTextFieldProd.getText() + ", dichos stocks de los insumos de la misma puede tener un stock 0", "ADVERTENCIA!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opc, opc[0]);
        if (i == 0) {
            MostrarProductosStockN_MOD();
            jLabelProductoInforme.setText(jTextFieldProd.getText());
            color = new ColorearFilas(2);
            jTableInforme.getColumnModel().getColumn(2).setCellRenderer(color);
            vStocksProductos.setSize(727, 560);
            vStocksProductos.setLocationRelativeTo(this);
            vStocksProductos.setModal(true);
            vStocksProductos.setVisible(true);
        } else {
            int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextFieldProd.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (g == 0) {
                ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                ce.setNomempleado(jTextFieldEmp.getText());
                ce.setProducto(jTextFieldProd.getText());
                ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                ce.setIdconsumo(Integer.parseInt(id));
                if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                    ce.getCantidad();
                    ce.getIdproducto();
                    if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        VolverListaConsumosEmpleados();
                        //MostrarDatos();
                        //Limpiar();
                        //LimpiarSeleccionTabla1();
                    }
                }
            } else {
                ce.setCantidad(cantidad);//ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));
                ce.setIdproducto(idproducto);//ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                    Limpiar();
                    //LimpiarSeleccionTabla1();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarEmpleado = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEmpleados = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jLabelNombreEmp = new javax.swing.JLabel();
        jTextFieldEmpleado = new javax.swing.JTextField();
        jButtonBuscarEmp = new javax.swing.JButton();
        jButtonAgregarEmp = new javax.swing.JButton();
        jButtonCancelarEmp = new javax.swing.JButton();
        vSeleccionarProducto = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jLabelNombreProd = new javax.swing.JLabel();
        jTextFieldProducto = new javax.swing.JTextField();
        jButtonBuscarProd = new javax.swing.JButton();
        jButtonAgregarProd = new javax.swing.JButton();
        jButtonCancelarProd = new javax.swing.JButton();
        vStocksProductos = new javax.swing.JDialog();
        jLabelProd = new javax.swing.JLabel();
        jLabelProductoInforme = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableInforme = new javax.swing.JTable();
        jButtonAceptarInforme = new javax.swing.JButton();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        jLabelFecha = new javax.swing.JLabel();
        jLabelCantidad = new javax.swing.JLabel();
        jTextFieldCantidad = new javax.swing.JTextField();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jTextField7 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jList4 = new javax.swing.JList<>();
        jList5 = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jButton19 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        listaEmpleado = new javax.swing.JList<>();
        jTextFieldEmp = new javax.swing.JTextField();
        jLabelEmpleado = new javax.swing.JLabel();
        jButtonSeleccionarEmpleado = new javax.swing.JButton();
        listaProducto = new javax.swing.JList<>();
        jButtonSeleccionarProductos = new javax.swing.JButton();
        jTextFieldProd = new javax.swing.JTextField();
        jLabelProducto = new javax.swing.JLabel();

        vSeleccionarEmpleado.setTitle("Seleccionar Empleado");
        java.awt.Image iconodeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
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

        jTableEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableEmpleados);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombreEmp.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreEmp.setText("Nombre:");

        jTextFieldEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarEmp.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarEmp.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarEmp.setText("Buscar");
        jButtonBuscarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarEmpActionPerformed(evt);
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
                        .addComponent(jLabelNombreEmp))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jButtonBuscarEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jTextFieldEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabelNombreEmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscarEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        jButtonAgregarEmp.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarEmp.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarEmp.setText("Agregar");
        jButtonAgregarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarEmpActionPerformed(evt);
            }
        });

        jButtonCancelarEmp.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelarEmp.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarEmp.setText("Cancelar");
        jButtonCancelarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarEmpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vSeleccionarEmpleadoLayout = new javax.swing.GroupLayout(vSeleccionarEmpleado.getContentPane());
        vSeleccionarEmpleado.getContentPane().setLayout(vSeleccionarEmpleadoLayout);
        vSeleccionarEmpleadoLayout.setHorizontalGroup(
            vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jButtonAgregarEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancelarEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jButtonAgregarEmp)
                    .addComponent(jButtonCancelarEmp))
                .addGap(11, 11, 11))
        );

        vSeleccionarProducto.setTitle("Seleccionar Producto");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
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

        jTableProductos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableProductos);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombreProd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreProd.setText("Producto");

        jTextFieldProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarProd.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarProd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarProd.setText("Buscar");
        jButtonBuscarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarProdActionPerformed(evt);
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
                                .addComponent(jLabelNombreProd))
                            .addComponent(jTextFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jButtonBuscarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNombreProd)
                .addGap(2, 2, 2)
                .addComponent(jTextFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscarProd)
                .addContainerGap())
        );

        jButtonAgregarProd.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarProd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarProd.setText("Agregar");
        jButtonAgregarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProdActionPerformed(evt);
            }
        });

        jButtonCancelarProd.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelarProd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarProd.setText("Cancelar");
        jButtonCancelarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vSeleccionarProductoLayout = new javax.swing.GroupLayout(vSeleccionarProducto.getContentPane());
        vSeleccionarProducto.getContentPane().setLayout(vSeleccionarProductoLayout);
        vSeleccionarProductoLayout.setHorizontalGroup(
            vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarProductoLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jButtonAgregarProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancelarProd)
                .addGap(56, 56, 56))
            .addGroup(vSeleccionarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
                    .addComponent(jButtonCancelarProd)
                    .addComponent(jButtonAgregarProd))
                .addContainerGap())
        );

        java.awt.Image iconodel = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vStocksProductos.setIconImage(iconodel);
        vStocksProductos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vStocksProductosWindowClosing(evt);
            }
        });

        jLabelProd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelProd.setText("Producto:");

        jLabelProductoInforme.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jTableInforme.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableInforme.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTableInforme);

        jButtonAceptarInforme.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAceptarInforme.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAceptarInforme.setText("Aceptar");
        jButtonAceptarInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarInformeActionPerformed(evt);
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
                                .addComponent(jLabelProd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelProductoInforme, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(174, 174, 174))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vStocksProductosLayout.createSequentialGroup()
                                .addComponent(jButtonAceptarInforme, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabelProd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelProductoInforme, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAceptarInforme)
                .addContainerGap())
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consumos de Empleados");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
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

        jDateFecha.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jDateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 145, 30));

        jLabelFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelFecha.setText("(*) Fecha:");
        getContentPane().add(jLabelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, -1, 30));

        jLabelCantidad.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelCantidad.setText("(*) Cantidad:");
        getContentPane().add(jLabelCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, 30));

        jTextFieldCantidad.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCantidadKeyTyped(evt);
            }
        });
        getContentPane().add(jTextFieldCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 118, 28));

        jButtonAgregar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 100, 30));

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 100, 30));

        jButtonCancelar.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 100, 30));

        jInternalFrame1.setBackground(new java.awt.Color(255, 248, 177));
        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.setTitle("Consumos de Empleados");
        jInternalFrame1.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(866, 561));
        jInternalFrame1.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jInternalFrame1formInternalFrameClosing(evt);
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
        jInternalFrame1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jInternalFrame1formMouseClicked(evt);
            }
        });
        jInternalFrame1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooser4.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jInternalFrame1.getContentPane().add(jDateChooser4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, 145, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("(*) Fecha:");
        jInternalFrame1.getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 128, -1, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel12.setText("(*) Cantidad:");
        jInternalFrame1.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 128, -1, 30));

        jTextField6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });
        jInternalFrame1.getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 118, 28));

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel13.setText("(*) Empleado:");
        jLayeredPane2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 170, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel14.setText("(*) Producto:");
        jLayeredPane2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, -1, 30));

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 42, 30));

        jTextField7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });
        jLayeredPane2.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 186, 30));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 42, 30));

        jTextField8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField8KeyReleased(evt);
            }
        });
        jLayeredPane2.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 196, 30));

        jList4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList4.setValueIsAdjusting(true);
        jList4.setVisibleRowCount(0);
        jList4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList4MouseClicked(evt);
            }
        });
        jLayeredPane2.add(jList4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 196, -1));

        jList5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList5.setValueIsAdjusting(true);
        jList5.setVisibleRowCount(0);
        jList5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList5MouseClicked(evt);
            }
        });
        jLayeredPane2.add(jList5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 186, -1));

        jInternalFrame1.getContentPane().add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jScrollPane5.setOpaque(false);

        jTable5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable5.setOpaque(false);
        jScrollPane5.setViewportView(jTable5);

        jInternalFrame1.getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 830, 275));

        jButton16.setBackground(new java.awt.Color(252, 249, 57));
        jButton16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton16.setText("Agregar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jInternalFrame1.getContentPane().add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, 85, -1));

        jButton17.setBackground(new java.awt.Color(252, 249, 57));
        jButton17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton17.setText("Modificar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jInternalFrame1.getContentPane().add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 500, 92, -1));

        jButton18.setBackground(new java.awt.Color(240, 87, 49));
        jButton18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton18.setText("Eliminar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jInternalFrame1.getContentPane().add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 500, 89, -1));

        jPanel4.setBackground(new java.awt.Color(255, 248, 177));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jDateChooser5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateChooser6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton19.setText("Buscar");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel15.setText("Desde");

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel16.setText("Hasta");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton19)
                .addGap(10, 10, 10))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );

        jInternalFrame1.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(187, 11, -1, 110));

        getContentPane().add(jInternalFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        listaEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        listaEmpleado.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaEmpleado.setValueIsAdjusting(true);
        listaEmpleado.setVisibleRowCount(0);
        listaEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaEmpleadoMouseClicked(evt);
            }
        });
        getContentPane().add(listaEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 196, -1));

        jTextFieldEmp.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldEmpKeyReleased(evt);
            }
        });
        getContentPane().add(jTextFieldEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 196, 30));

        jLabelEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelEmpleado.setText("(*) Empleado:");
        getContentPane().add(jLabelEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 170, 30));

        jButtonSeleccionarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarEmpleadoActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSeleccionarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 42, 30));

        listaProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        listaProducto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaProducto.setValueIsAdjusting(true);
        listaProducto.setVisibleRowCount(0);
        listaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaProductoMouseClicked(evt);
            }
        });
        getContentPane().add(listaProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 186, -1));

        jButtonSeleccionarProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarProductosActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSeleccionarProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 42, 30));

        jTextFieldProd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProdKeyReleased(evt);
            }
        });
        getContentPane().add(jTextFieldProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 186, 30));

        jLabelProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelProducto.setText("(*) Producto:");
        getContentPane().add(jLabelProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        if (!jTextFieldEmp.getText().equals("") && !jTextFieldProd.getText().equals("") && !jTextFieldCantidad.getText().isEmpty() && !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().isEmpty()) {
            if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                int stockneg = contr_consumoempleado.ConsultarStockNegativosN_MOD(jTextFieldProd.getText(), Float.parseFloat(jTextFieldCantidad.getText()));
                int stockcero = contr_consumoempleado.ConsultarStockCeroN_MOD(jTextFieldProd.getText(), Float.parseFloat(jTextFieldCantidad.getText()));
                if (stockneg > 0) {
                    VerificarStockNegativoRegistrando();
                } else if (stockcero > 0) {
                    VerificarStockCeroRegistrando();
                } else {
                    ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                    ce.setNomempleado(jTextFieldEmp.getText());
                    ce.setProducto(jTextFieldProd.getText());
                    ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                    if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                        ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                        ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Informe agregado");
                            //MostrarDatos();
                            //Limpiar();
                            VolverListaConsumosEmpleados();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonSeleccionarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarEmpleadoActionPerformed
        vSeleccionarEmpleado.setSize(470, 635);
        vSeleccionarEmpleado.setLocationRelativeTo(this);
        vSeleccionarEmpleado.setModal(true);
        vSeleccionarEmpleado.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarEmpleadoActionPerformed

    private void jButtonAgregarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarEmpActionPerformed
        if (jTableEmpleados.getRowCount() != 0) {
            int i = jTableEmpleados.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                vSeleccionarEmpleado.dispose();
                jTextFieldEmp.setText(jTableEmpleados.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos empleados todavia");
        }
    }//GEN-LAST:event_jButtonAgregarEmpActionPerformed

    private void jButtonCancelarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarEmpActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButtonCancelarEmpActionPerformed

    private void jButtonBuscarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarEmpActionPerformed
        if (!jTextFieldEmpleado.getText().isEmpty()) {
            dato2 = contr_consumoempleado.MostrarEmpleadosBuscado(jTextFieldEmpleado.getText());
            if (dato2.length != 0) {
                String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
                tabla2 = new DefaultTableModel(dato2, columnas);
                jTableEmpleados.setModel(tabla2);
                EliminarFilasVaciasTabla2();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarEmpActionPerformed

    private void vSeleccionarEmpleadoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarEmpleadoWindowClosing

    private void vSeleccionarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoMouseClicked
        //MostrarEmpleados();
        LimpiarSeleccionTablaEmp();
        //jTextFieldEmpleado.setText("");
    }//GEN-LAST:event_vSeleccionarEmpleadoMouseClicked

    private void jButtonSeleccionarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarProductosActionPerformed
        vSeleccionarProducto.setSize(348, 613);
        vSeleccionarProducto.setLocationRelativeTo(this);
        vSeleccionarProducto.setModal(true);
        vSeleccionarProducto.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarProductosActionPerformed

    private void jButtonAgregarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProdActionPerformed
        if (jTableProductos.getRowCount() != 0) {
            int i = jTableProductos.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                vSeleccionarProducto.dispose();
                ce.setIdproducto(Integer.parseInt(jTableProductos.getValueAt(i, 0).toString()));
                jTextFieldProd.setText(jTableProductos.getValueAt(i, 1).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos empleados todavia");
        }
    }//GEN-LAST:event_jButtonAgregarProdActionPerformed

    private void jButtonCancelarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarProdActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
        } else {
            vSeleccionarProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButtonCancelarProdActionPerformed

    private void jButtonBuscarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarProdActionPerformed
        if (!jTextFieldProducto.getText().isEmpty()) {
            dato3 = contr_consumoempleado.MostrarProductosBuscado(jTextFieldProducto.getText());
            if (dato3.length != 0) {
                String columnas[] = {"IDPROD", "PRODUCTO"};
                tabla3 = new DefaultTableModel(dato3, columnas);
                jTableProductos.setModel(tabla3);
                EliminarFilasVaciasTabla3();
                ocultarcolumnastabla3();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarProdActionPerformed

    private void vSeleccionarProductoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarProductoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarProducto.dispose();
        } else {
            vSeleccionarProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarProductoWindowClosing

    private void vSeleccionarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarProductoMouseClicked
        //MostrarProductos();
        LimpiarSeleccionTablaProd();
        //jTextFieldProducto.setText("");
    }//GEN-LAST:event_vSeleccionarProductoMouseClicked

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        /*if (jButtonModificar.getText().equals("Modificar")) {
            int fila = jTable1.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                jButtonAgregar.setEnabled(false);
                jButtonModificar.setText("Cancelar");
                jButtonCancelar.setText("Modificar");
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
                jTextFieldEmp.setText(jTable1.getValueAt(fila, 2).toString());
                jTextFieldProd.setText(jTable1.getValueAt(fila, 3).toString());
                jTextFieldCantidad.setText(jTable1.getValueAt(fila, 4).toString());
                jDateFecha.setDate(fechaseleccionada);
            }
        } else {
            
        }*/
        if (!jTextFieldEmp.getText().equals("") && !jTextFieldProd.getText().equals("") && !jTextFieldCantidad.getText().isEmpty() && !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().isEmpty()) {
            if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                int m = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (m == 0) {
                    ce.setCantidad(cantidad);//ce.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 4).toString()));                          
                    ce.setIdproducto(idproducto);//ce.setIdproducto(Integer.parseInt(jTable1.getValueAt(l, 1).toString()));
                    contr_consumoempleado.CancelarStockConsumidoLocal(ce);
                    int stockneg = contr_consumoempleado.ConsultarStockNegativosN_MOD(jTextFieldProd.getText(), Float.parseFloat(jTextFieldCantidad.getText()));
                    int stockcero = contr_consumoempleado.ConsultarStockCeroN_MOD(jTextFieldProd.getText(), Float.parseFloat(jTextFieldCantidad.getText()));
                    if (stockneg > 0) {
                        VerificarStockNegativoModificando();
                    } else if (stockcero > 0) {
                        VerificarStockCeroModificando();
                    } else {
                        ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                        ce.setNomempleado(jTextFieldEmp.getText());
                        ce.setProducto(jTextFieldProd.getText());
                        ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                        ce.setIdconsumo(Integer.parseInt(id));
                        if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                            ce.getCantidad();
                            ce.getIdproducto();
                            if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                                JOptionPane.showMessageDialog(null, "Modificado");
                                VolverListaConsumosEmpleados();
                                //MostrarDatos();
                                //Limpiar();
                                //LimpiarSeleccionTabla1();
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
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        /*int i = jTable1.getSelectedRow();
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
        }*/
        if (!jButtonAgregar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaConsumosEmpleados();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldEmp.getText().equals("") || !jTextFieldProd.getText().equals("") || !jTextFieldCantidad.getText().isEmpty() || !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaConsumosEmpleados();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (!jButtonAgregar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaConsumosEmpleados();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldEmp.getText().equals("") || !jTextFieldProd.getText().equals("") || !jTextFieldCantidad.getText().isEmpty() || !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaConsumosEmpleados();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        /*if (jButtonCancelar.getText().equals("Eliminar")) {
            LimpiarSeleccionTabla1();
            IniciarFechas();
            MostrarDatos();
        }*/
    }//GEN-LAST:event_formMouseClicked

    private void listaEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaEmpleadoMouseClicked
        int i = listaEmpleado.getSelectedIndex();
        if (i != -1) {
            jTextFieldEmp.setText(listaEmpleado.getSelectedValue());
            listaEmpleado.setVisible(false);
        }
    }//GEN-LAST:event_listaEmpleadoMouseClicked

    private void listaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaProductoMouseClicked
        int i = listaProducto.getSelectedIndex();
        if (i != -1) {
            jTextFieldProd.setText(listaProducto.getSelectedValue());
            int j = contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText());
            ce.setIdproducto(j);
            listaProducto.setVisible(false);
        }
    }//GEN-LAST:event_listaProductoMouseClicked

    private void jTextFieldEmpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmpKeyReleased
        ListaEmpleados();
    }//GEN-LAST:event_jTextFieldEmpKeyReleased

    private void jTextFieldProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProdKeyReleased
        ListaProductos();
    }//GEN-LAST:event_jTextFieldProdKeyReleased

    private void jTextFieldCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCantidadKeyTyped
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
    }//GEN-LAST:event_jTextFieldCantidadKeyTyped

    private void jButtonAceptarInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarInformeActionPerformed
        int contstockneg = 0;
        if (jTableInforme.getRowCount() != 0) {
            for (int i = 0; i < jTableInforme.getRowCount(); i++) {
                if (jTableInforme.getColumnName(2).equals("STOCK FINAL")) {
                    if (Float.parseFloat(jTableInforme.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                }
            }
        }
        if (contstockneg > 0) {
            if (!jButtonAgregar.isEnabled()) {
                ce.setCantidad(cantidad);
                ce.setIdproducto(idproducto);
                if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                    Limpiar();
                    vStocksProductos.dispose();
                }
            } else {
                vStocksProductos.dispose();
                Limpiar();
            }
        } else {
            if (!jButtonAgregar.isEnabled()) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextFieldProd.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                    ce.setNomempleado(jTextFieldEmp.getText());
                    ce.setProducto(jTextFieldProd.getText());
                    ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                    ce.setIdconsumo(Integer.parseInt(id));
                    if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                        ce.getCantidad();
                        ce.getIdproducto();
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Modificado");
                            //MostrarDatos();
                            //Limpiar();
                            //LimpiarSeleccionTabla1();
                            VolverListaConsumosEmpleados();
                        }
                    }
                } else {
                    ce.setCantidad(cantidad);
                    ce.setIdproducto(idproducto);
                    if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                        vStocksProductos.dispose();
                        Limpiar();
                        //LimpiarSeleccionTabla1();
                    }
                }
            } else {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextFieldProd.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setNomempleado(jTextFieldEmp.getText());
                    ce.setProducto(jTextFieldProd.getText());
                    ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                    if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                        ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                        ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Informe agregado");
                            //MostrarDatos();
                            //Limpiar();
                            VolverListaConsumosEmpleados();
                        }
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            }
        }
    }//GEN-LAST:event_jButtonAceptarInformeActionPerformed

    private void vStocksProductosWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vStocksProductosWindowClosing
        int contstockneg = 0;
        if (jTableInforme.getRowCount() != 0) {
            for (int i = 0; i < jTableInforme.getRowCount(); i++) {
                if (jTableInforme.getColumnName(2).equals("STOCK FINAL")) {
                    if (Float.parseFloat(jTableInforme.getValueAt(i, 2).toString()) < 0) {
                        contstockneg++;
                    }
                }
            }
        }
        if (contstockneg > 0) {
            if (!jButtonAgregar.isEnabled()) {
                ce.setCantidad(cantidad);
                ce.setIdproducto(idproducto);
                if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                    Limpiar();
                    vStocksProductos.dispose();
                }
            } else {
                vStocksProductos.dispose();
                Limpiar();
            }
        } else {
            if (!jButtonAgregar.isEnabled()) {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextFieldProd.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                    ce.setNomempleado(jTextFieldEmp.getText());
                    ce.setProducto(jTextFieldProd.getText());
                    ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                    ce.setIdconsumo(Integer.parseInt(id));
                    if (contr_consumoempleado.EditarConsumosEmpleados(ce)) {
                        ce.getCantidad();
                        ce.getIdproducto();
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Modificado");
                            //MostrarDatos();
                            //Limpiar();
                            //LimpiarSeleccionTabla1();
                            VolverListaConsumosEmpleados();
                        }
                    }
                } else {
                    ce.setCantidad(cantidad);
                    ce.setIdproducto(idproducto);
                    if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                        vStocksProductos.dispose();
                        Limpiar();
                        //LimpiarSeleccionTabla1();
                    }
                }
            } else {
                int g = JOptionPane.showConfirmDialog(null, "Agregar el producto " + jTextFieldProd.getText() + " o elegir otro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (g == 0) {
                    vStocksProductos.dispose();
                    ce.setNomempleado(jTextFieldEmp.getText());
                    ce.setProducto(jTextFieldProd.getText());
                    ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                    if (contr_consumoempleado.InsertarConsumosEmpleados(ce)) {
                        ce.setCantidad(Float.parseFloat(jTextFieldCantidad.getText()));
                        ce.setIdproducto(contr_consumoempleado.ObtenerIDProducto(jTextFieldProd.getText()));
                        if (contr_consumoempleado.RestarStockConsumidoLocal(ce)) {
                            JOptionPane.showMessageDialog(null, "Informe agregado");
                            //MostrarDatos();
                            //Limpiar();
                            VolverListaConsumosEmpleados();
                        }
                    }
                } else {
                    vStocksProductos.dispose();
                    Limpiar();
                }
            }
        }
    }//GEN-LAST:event_vStocksProductosWindowClosing

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyReleased

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8KeyReleased

    private void jList4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jList4MouseClicked

    private void jList5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jList5MouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jInternalFrame1formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFrame1formInternalFrameClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1formInternalFrameClosing

    private void jInternalFrame1formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame1formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButtonAceptarInforme;
    public static javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonAgregarEmp;
    private javax.swing.JButton jButtonAgregarProd;
    private javax.swing.JButton jButtonBuscarEmp;
    private javax.swing.JButton jButtonBuscarProd;
    public static javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonCancelarEmp;
    private javax.swing.JButton jButtonCancelarProd;
    public static javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonSeleccionarEmpleado;
    private javax.swing.JButton jButtonSeleccionarProductos;
    public static com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    public static com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCantidad;
    private javax.swing.JLabel jLabelEmpleado;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelNombreEmp;
    private javax.swing.JLabel jLabelNombreProd;
    private javax.swing.JLabel jLabelProd;
    private javax.swing.JLabel jLabelProducto;
    private javax.swing.JLabel jLabelProductoInforme;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JList<String> jList4;
    private javax.swing.JList<String> jList5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTableEmpleados;
    private javax.swing.JTable jTableInforme;
    private javax.swing.JTable jTableProductos;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    public static javax.swing.JTextField jTextFieldCantidad;
    public static javax.swing.JTextField jTextFieldEmp;
    private javax.swing.JTextField jTextFieldEmpleado;
    public static javax.swing.JTextField jTextFieldProd;
    private javax.swing.JTextField jTextFieldProducto;
    private javax.swing.JList<String> listaEmpleado;
    private javax.swing.JList<String> listaProducto;
    private javax.swing.JDialog vSeleccionarEmpleado;
    private javax.swing.JDialog vSeleccionarProducto;
    private javax.swing.JDialog vStocksProductos;
    // End of variables declaration//GEN-END:variables
}
