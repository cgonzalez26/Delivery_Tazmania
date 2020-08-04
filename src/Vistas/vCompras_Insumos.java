package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Compras;
import Controlador.control_DetallesCompras;
import Controlador.control_Insumos;
import Controlador.control_Movimientos_Caja;
import Controlador.control_existencias;
import Modelo.Compras;
import Modelo.DetallesCompras;
import Modelo.Insumos;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Calendar;
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
public final class vCompras_Insumos extends javax.swing.JInternalFrame {

    control_existencias combo = new control_existencias();
    control_Compras compra = new control_Compras();
    control_Insumos insumo = new control_Insumos();
    Sentencias_sql sql = new Sentencias_sql();
    Compras c = new Compras();
    Insumos m = new Insumos();
    control_DetallesCompras detallecompra = new control_DetallesCompras();
    DetallesCompras d = new DetallesCompras();
    Movimientos_Caja mc = new Movimientos_Caja();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    int filascompra = 0;
    public int filasdetalle = 0, cant = 0;
    public float total = 0;
    String nombre = "", tipo = "", proveedor = "", idcompra = "", iddetalle = "";
    public String desc = "", nrofactura = "";
    public ArrayList<String> iddetalles = new ArrayList<>();
    public ArrayList<Integer> iddetallescompras = new ArrayList<>();
    public ArrayList<Integer> idinsumos = new ArrayList<>();
    Object[] prov, user, datos[] = {};
    ArrayList<String> listprov, listins;
    Object[][] proveedores, insumos;
    public static DefaultTableModel modelo, modelprov, modelinsumo;
    DefaultListModel listmodel;
    vListas_Compras lista = null;

    public vCompras_Insumos() {
        initComponents();
        VerificarCajaAbierta();
        ComboUsuario();
        CrearColumnas();
        MostrarInsumos();
        MostrarProveedores();
        EliminarItemsVacios();
        jList1.setVisible(false);
        jList2.setVisible(false);
        //jTextField1.setText(Float.toString((float) 0.00));
        jTextTotal.setText(Float.toString((float) 0.00));
        //jTextField1.setEditable(false);
        jTextTotal.setEditable(false);
        jButtonModificarCompra.setEnabled(false);
    }

    public void VerificarCajaAbierta() {
        int idcaja = Session.getIdcaja_abierta();
        if (idcaja == 0) {
            DeshabilitarCampos();
            jlabelMensaje.setVisible(true);
        } else {
            jlabelMensaje.setVisible(false);
        }
    }

    public void DeshabilitarCampos() {
        //jTextField1.setEnabled(false);
        jTextInsumo.setEnabled(false);
        jTextProveedor.setEnabled(false);
        jTextProveedor.setEnabled(false);
        jTextPrecio.setEnabled(false);
        JtextCantidad.setEnabled(false);
        jDateChooser1.setEnabled(false);
        jCBUsuario.setEnabled(false);
        jButtonSeleccionarProv.setEnabled(false);
        jButtonSeleccionarProv.setEnabled(false);
        jButtonAgregar.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonBorrar.setEnabled(false);
        jButtonAgregarCompra.setEnabled(false);
        jButtonSeleccionarInsumo.setEnabled(false);
    }

    public void Limpiar() {
        jTextInsumo.setText("");
        JtextCantidad.setText("");
        jTextPrecio.setText("");
    }

    public void ComboUsuario() {
        user = combo.combox("usuarios", "Login");
        for (Object usuario : user) {
            jCBUsuario.addItem((String) usuario);
        }
    }

    public void ListaProveedores() {
        listprov = combo.list("proveedores", "Nombre_comercial", jTextProveedor.getText());
        String substr = jTextProveedor.getText().toLowerCase();
        listmodel = new DefaultListModel<>();
        jList1.setModel(listmodel);
        listmodel.removeAllElements();
        if (listprov.size() > 0) {
            for (int i = 0; i < listprov.size(); i++) {
                if (listprov.get(i) == null) {
                    listprov.remove(i);
                } else {
                    String sublist = listprov.get(i).toLowerCase();
                    if (sublist.contains(substr)) {
                        listmodel.addElement(listprov.get(i));
                        jList1.setVisible(true);
                        if (jTextProveedor.getText().isEmpty()) {
                            jList1.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void ListaInsumos() {
        listprov = combo.list("insumos", "descripcion", jTextInsumo.getText());
        String substr = jTextInsumo.getText().toLowerCase();
        listmodel = new DefaultListModel<>();
        jList2.setModel(listmodel);
        listmodel.removeAllElements();
        if (listprov.size() > 0) {
            for (int i = 0; i < listprov.size(); i++) {
                if (listprov.get(i) == null) {
                    listprov.remove(i);
                } else {
                    String sublist = listprov.get(i).toLowerCase();
                    if (sublist.contains(substr)) {
                        //listmodel.addElement(listprov.get(i));
                        listmodel.add(i, listprov.get(i));
                        jList2.setVisible(true);
                        if (jTextInsumo.getText().isEmpty()) {
                            jList2.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jCBUsuario.getItemCount(); i++) {
            if (jCBUsuario.getItemAt(i) == null) {
                jCBUsuario.removeItemAt(i);
            }
        }
    }

    public void MostrarProveedores() {
        String[] columnas = {"NOMBRE COMERCIAL PROVEEDORES"};
        proveedores = compra.MostrarProveedores();
        modelprov = new DefaultTableModel(proveedores, columnas);
        jTable2.setModel(modelprov);
        EliminarFilasVaciasProveedores();
        //AjustarTamañoFilasProveedores();
    }

    public void MostrarInsumos() {
        String[] columnas = {"INSUMOS", "PRECIO", "CANTIDAD"};
        insumos = detallecompra.MostrarInsumos();
        modelinsumo = new DefaultTableModel(insumos, columnas);
        jTable3.setModel(modelinsumo);
        ocultarColumnasIns();
        EliminarFilasVaciasInsumos();
        AjustarTamañoFilasInsumos();
    }

    public void ocultarColumnasIns() {
        jTable3.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable3.getColumnModel().getColumn(1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable3.getColumnModel().getColumn(2).setMaxWidth(0);
        jTable3.getColumnModel().getColumn(2).setMinWidth(0);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(0);
    }

    public void AjustarTamañoFilasProveedores() {
        if (jTable2.getRowCount() != 0) {
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nomprov = (int) font.getStringBounds(jTable2.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nomprov > jTable2.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(0).setPreferredWidth(nomprov);
                }
            }
        }
    }

    public void AjustarTamañoFilasInsumos() {
        if (jTable3.getRowCount() != 0) {
            for (int i = 0; i < jTable3.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nomins = (int) font.getStringBounds(jTable3.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nomins > jTable3.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTable3.getColumnModel().getColumn(0).setPreferredWidth(nomins);
                }
            }
        }
    }

    public void EliminarFilasVaciasProveedores() {
        if (jTable2.getRowCount() != 0) {
            int filas = jTable2.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable2.getValueAt(fila, 0) == null) {
                    modelprov.removeRow(fila);
                }
            }
        }
    }

    public void EliminarFilasVaciasInsumos() {
        if (jTable3.getRowCount() != 0) {
            int filas = jTable3.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable3.getValueAt(fila, 0) == null) {
                    modelinsumo.removeRow(fila);
                }
            }
        }
    }

    public void CrearColumnas() {
        Object[] columnas = {"INSUMO", "PRECIO", "CANTIDAD", "SUBTOTAL"};
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnas);
        jTableDetalle.setModel(modelo);
    }

    public float CalcularMontoTotal() {
        try {
            total = total + (Float.parseFloat(jTextPrecio.getText().trim()) * Float.parseFloat(JtextCantidad.getText().trim()));
            vCompras_Insumos.jTextTotal.setText(Float.toString(total));

        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return total;
    }

    public void LimpiarSeleccion() {
        jTableDetalle.clearSelection();
        jTableDetalle.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarNombreComercialProv = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        vSeleccionarInsumo = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jTextProveedor = new javax.swing.JTextField();
        jList1 = new javax.swing.JList<>();
        jButtonSeleccionarProv = new javax.swing.JButton();
        jList2 = new javax.swing.JList<>();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableDetalle = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonBorrar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextInsumo = new javax.swing.JTextField();
        jButtonSeleccionarInsumo = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextPrecio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        JtextCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCBUsuario = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jButtonModificarCompra = new javax.swing.JButton();
        jButtonAgregarCompra = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextTotal = new javax.swing.JTextField();
        jlabelMensaje = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        vSeleccionarNombreComercialProv.setTitle("Seleccionar Nombre Comercial Proveedor");
        java.awt.Image iconoprov = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vSeleccionarNombreComercialProv.setIconImage(iconoprov);
        vSeleccionarNombreComercialProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarNombreComercialProvMouseClicked(evt);
            }
        });
        vSeleccionarNombreComercialProv.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarNombreComercialProvWindowClosing(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton8.setText("Aceptar");
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

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Nombre Proveedor");

        jTextField5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton12.setText("Buscar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vSeleccionarNombreComercialProvLayout = new javax.swing.GroupLayout(vSeleccionarNombreComercialProv.getContentPane());
        vSeleccionarNombreComercialProv.getContentPane().setLayout(vSeleccionarNombreComercialProvLayout);
        vSeleccionarNombreComercialProvLayout.setHorizontalGroup(
            vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        vSeleccionarNombreComercialProvLayout.setVerticalGroup(
            vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton8))
                .addContainerGap())
        );

        vSeleccionarInsumo.setTitle("Seleccionar Insumo");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vSeleccionarInsumo.setIconImage(icono);
        vSeleccionarInsumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarInsumoMouseClicked(evt);
            }
        });
        vSeleccionarInsumo.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarInsumoWindowClosing(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

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

        jButton11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton11.setText("Cancelar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("Nombre Insumo");

        jTextField8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField8KeyTyped(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton13.setText("Buscar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
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
                        .addComponent(jTextField8))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel2)
                        .addGap(0, 98, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton13)
                .addContainerGap())
        );

        javax.swing.GroupLayout vSeleccionarInsumoLayout = new javax.swing.GroupLayout(vSeleccionarInsumo.getContentPane());
        vSeleccionarInsumo.getContentPane().setLayout(vSeleccionarInsumoLayout);
        vSeleccionarInsumoLayout.setHorizontalGroup(
            vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(vSeleccionarInsumoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        vSeleccionarInsumoLayout.setVerticalGroup(
            vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registrar Compra");
        setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setVisible(true);
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

        jLayeredPane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextProveedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextProveedorKeyReleased(evt);
            }
        });
        jLayeredPane3.add(jTextProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 292, 30));

        jList1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList1.setValueIsAdjusting(true);
        jList1.setVisibleRowCount(0);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jLayeredPane3.add(jList1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 290, -1));

        jButtonSeleccionarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarProv.setFocusable(false);
        jButtonSeleccionarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarProvActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButtonSeleccionarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 44, 30));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jLayeredPane3.add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 222, -1));

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Insumos a Comprar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jTableDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableDetalleMousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTableDetalle);

        jButtonAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add16.png"))); // NOI18N
        jButtonAgregar.setText("Agregar ");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit16.png"))); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonBorrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/delete16.png"))); // NOI18N
        jButtonBorrar.setText("Borrar");
        jButtonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("(*) Insumo:");

        jTextInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextInsumo.setToolTipText("");
        jTextInsumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextInsumoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextInsumoKeyTyped(evt);
            }
        });

        jButtonSeleccionarInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarInsumoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("(*) Precio:");

        jTextPrecio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPrecioKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) Cantidad:");

        JtextCantidad.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        JtextCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JtextCantidadKeyTyped(evt);
            }
        });

        jLayeredPane2.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonAgregar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonModificar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonBorrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jTextInsumo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonSeleccionarInsumo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jTextPrecio, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(JtextCantidad, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(243, 243, 243)
                        .addComponent(jLabel9)
                        .addGap(152, 152, 152)
                        .addComponent(jLabel7))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jTextInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButtonSeleccionarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jTextPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(JtextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSeleccionarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jButtonAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jButtonModificar)
                        .addGap(31, 31, 31)
                        .addComponent(jButtonBorrar)
                        .addGap(63, 63, 63))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jLayeredPane3.add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1220, 320));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setText("Usuario:");
        jLayeredPane3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 11, 232, -1));

        jCBUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCBUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccionar Usuario.." }));
        jLayeredPane3.add(jCBUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 35, 250, 32));
        jCBUsuario.getAccessibleContext().setAccessibleParent(this);

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLayeredPane3.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 37, 175, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("(*) Fecha:");
        jLayeredPane3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 11, 80, 20));

        jButtonModificarCompra.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificarCompra.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificarCompra.setText("Modificar Compra");
        jButtonModificarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarCompraActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButtonModificarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 460, 150, -1));

        jButtonAgregarCompra.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarCompra.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarCompra.setText("Registrar Compra");
        jButtonAgregarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarCompraActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButtonAgregarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 460, 136, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel10.setText("TOTAL:");
        jLayeredPane3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, -1, 20));

        jTextTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLayeredPane3.add(jTextTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 410, 140, 32));

        jlabelMensaje.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jlabelMensaje.setForeground(new java.awt.Color(255, 0, 0));
        jlabelMensaje.setText("No hay CAJA ABIERTA.");
        jLayeredPane3.add(jlabelMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("(*) Proveedor:");
        jLayeredPane3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        btnCancelar.setBackground(new java.awt.Color(240, 87, 49));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jLayeredPane3.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 460, -1, -1));

        getContentPane().add(jLayeredPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1242, 519));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarCompraActionPerformed
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        if (!jButtonAgregarCompra.getText().equals("Cancelar") && this.getTitle().equals("Registrar Compra")) {
            if (!jTextProveedor.getText().equals("") && !jCBUsuario.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextTotal.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    if (!jButtonModificar.getText().equals("Cancelar")) {
                        if (jTableDetalle.getRowCount() != 0) {
                            c.setIdproveedor(compra.ObtenerIDProveedor(jTextProveedor.getText()));
                            c.setIdusuario(compra.ObtenerIDUsuario((String) jCBUsuario.getSelectedItem()));
                            c.setMontototal(Float.parseFloat(jTextTotal.getText()));
                            //Guardar Encabezado de Compra
                            if (compra.EfectuarCompra(c)) {
                                //Guardar Detalle de Compra
                                for (int g = 0; g < jTableDetalle.getRowCount(); g++) {
                                    d.setIdcompra(detallecompra.ObtenerIDCompra());
                                    d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(g, 0).toString()));
                                    d.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(g, 1).toString()));
                                    d.setCantidad(Float.parseFloat(jTableDetalle.getValueAt(g, 2).toString()));
                                    if (detallecompra.RegistrarDetalleCompra(d)) {

                                    }
                                }
                                //actuizar Precio de Insumo en caso de que se haya cambiado
                                for (int d = 0; d < jTableDetalle.getRowCount(); d++) {
                                    m.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(d, 1).toString()));
                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(d, 0).toString()));
                                    if (insumo.ActualizarPrecio(m)) {

                                    }
                                }
                                if (sql.SafeUpdates()) {
                                    if (detallecompra.ActualizarStockInsumos()) {
                                        JOptionPane.showMessageDialog(null, "Compra Registrada");
                                        int idmovimiento = sql.obtenerUltimoId("compras", "idcompra");
                                        //String codigo = sql.generaCodigo("compra");
                                        //sql.ejecutarSql("UPDATE compras SET NroCompra ='" + codigo + "' WHERE idcompra=" + idmovimiento);
                                        //c.setNrocompra(codigo);
                                        
                                        //Generamos el Movimmiento de Caja de la Compra
                                        mc.setDescripcion("COMPRA INSUMOS");
                                        mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                                        mc.setIdtipomovimiento(12);
                                        mc.setIdusuario(Session.getIdusuario());
                                        //mc.setNromovimiento(codigo);
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(c.getMontototal());
                                        mc.setIdmovimiento(idmovimiento);
                                        mc.setActivo(1);
                                        control_mc.InsertarMovimientosCaja(mc);
                                        lista = new vListas_Compras();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        this.dispose();
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debes generar al menos, una compra de insumo");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, termine de Modificar el/los dato/s");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        } else if (jButtonAgregarCompra.getText().equals("Cancelar") && cant > 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                for (int x = 0; x < iddetallescompras.size(); x++) {
                    for (int m = 0; m < idinsumos.size(); m++) {
                        if (sql.SafeUpdates()) {
                            d.setIddetallecompra(iddetallescompras.get(x));
                            d.setIdinsumo(idinsumos.get(m));
                            if (detallecompra.ActualizarStockInsumos2(d)) {
                                d.setIddetallecompra(iddetallescompras.get(x));
                                d.setIdinsumo(idinsumos.get(m));
                                if (detallecompra.ActualizarDevolverPrecio(d)) {
                                    d.setIddetallecompra(iddetallescompras.get(x));
                                    if (detallecompra.AnularEliminacionCompra(d)) {
                                        x++;
                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Compras();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jButtonAgregarCompra.getText().equals("Cancelar") && cant == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vListas_Compras();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jButtonAgregarCompra.getText().equals("Registrar Compra") && this.getTitle().equals("Registrando nueva compra Factura N° " + nrofactura)) {
            if (!jTextProveedor.getText().equals("") && !jCBUsuario.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextTotal.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    if (!jButtonModificar.getText().equals("Cancelar")) {
                        if (jTableDetalle.getRowCount() != 0) {
                            c.setIdproveedor(compra.ObtenerIDProveedor(jTextProveedor.getText()));
                            c.setIdusuario(compra.ObtenerIDUsuario((String) jCBUsuario.getSelectedItem()));
                            c.setMontototal(Float.parseFloat(jTextTotal.getText()));
                            c.setIdcompra(Integer.parseInt(idcompra));
                            if (compra.EditarCompra(c)) {
                                for (int g = 0; g < jTableDetalle.getRowCount(); g++) {
                                    d.setIdcompra(Integer.parseInt(idcompra));
                                    d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(g, 0).toString()));
                                    d.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(g, 1).toString()));
                                    d.setCantidad(Float.parseFloat(jTableDetalle.getValueAt(g, 2).toString()));
                                    if (detallecompra.RegistrarDetalleCompra(d)) {

                                    }
                                }
                                for (int d = 0; d < jTableDetalle.getRowCount(); d++) {
                                    m.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(d, 1).toString()));
                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(d, 0).toString()));
                                    if (insumo.ActualizarPrecio(m)) {

                                    }
                                }
                                if (sql.SafeUpdates()) {
                                    if (detallecompra.ActualizarStockInsumos()) {
                                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(idcompra), 12));
                                        mc.setDescripcion("COMPRA INSUMOS");
                                        mc.setIdtipomovimiento(12);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(c.getMontototal());
                                        mc.setIdmovimiento(Integer.parseInt(idcompra));
                                        control_mc.EditarMovimientosCaja(mc);
                                        JOptionPane.showMessageDialog(null, "Modificacion completa de Factura N° " + nrofactura);
                                        lista = new vListas_Compras();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        dispose();
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debes generar al menos, una compra de insumo");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, termine de Modificar el/los dato/s");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        }
    }//GEN-LAST:event_jButtonAgregarCompraActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        boolean repetido = false;
        if (!jTextInsumo.getText().equals("") && !jTextPrecio.getText().equals("") && !JtextCantidad.getText().equals("")) {
            if (jTableDetalle.getRowCount() != 0) {
                for (int i = 0; i < jTableDetalle.getRowCount(); i++) {
                    if (jTextInsumo.getText().equals(jTableDetalle.getValueAt(i, 0).toString()) && jButtonModificar.getText().equals("Modificar")) {
                        JOptionPane.showMessageDialog(null, "Insumo ya agregado!");
                        repetido = true;
                    }
                }
            }
            if (repetido == false) {
                Float precio = Float.parseFloat(jTextPrecio.getText());
                Float cantidad = Float.parseFloat(JtextCantidad.getText());
                if (jButtonModificar.getText().equals("Modificar")) {                   
                    Object Datos[] = {jTextInsumo.getText(), precio, cantidad, precio * cantidad};
                    modelo.addRow(Datos);
                    //jTextField1.setText(String.valueOf(Float.parseFloat(jTextField6.getText()) * Float.parseFloat(jTextField4.getText())));
                    CalcularMontoTotal();
                    Limpiar();
                } else {
                    int i = jTableDetalle.getSelectedRow();
                    if (i == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        modelo.setValueAt(jTextInsumo.getText(), i, 0);
                        modelo.setValueAt((precio), i, 1);
                        modelo.setValueAt((cantidad), i, 2);
                        modelo.setValueAt((precio * cantidad), i, 2);
                        //jTextField1.setText(String.valueOf(Float.parseFloat(jTextField6.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        jTableDetalle.clearSelection();
                        jTableDetalle.getSelectionModel().clearSelection();
                        jButtonModificar.setText("Modificar");
                        jButtonBorrar.setEnabled(true);
                        LimpiarSeleccion();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos Insumo, Precio y Cantidad");
        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int i = jTableDetalle.getSelectedRow();
        if (jButtonModificar.getText().equals("Modificar")) {
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila");
            } else {
                jButtonModificar.setText("Cancelar");
                jButtonBorrar.setEnabled(false);
                jTextInsumo.setText(jTableDetalle.getValueAt(i, 0).toString());
                jTextPrecio.setText(jTableDetalle.getValueAt(i, 1).toString());
                JtextCantidad.setText(jTableDetalle.getValueAt(i, 2).toString());
                total = total - (Float.parseFloat(jTableDetalle.getValueAt(i, 1).toString()) * Float.parseFloat(jTableDetalle.getValueAt(i, 2).toString()));
                jTextTotal.setText(Float.toString(total));
                //jTextField1.setText(Float.toString((float) 0.00));
            }
        } else {
            total = total + (Float.parseFloat(jTextPrecio.getText().trim()) * Float.parseFloat(JtextCantidad.getText().trim()));
            jTextTotal.setText(Float.toString(total));
            jButtonModificar.setText("Modificar");
            jButtonBorrar.setEnabled(true);
            jTableDetalle.clearSelection();
            jTableDetalle.getSelectionModel().clearSelection();
            Limpiar();
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

<<<<<<< HEAD
    private void jButtonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarActionPerformed
        int fila = jTableDetalle.getSelectedRow();
        if (jButtonBorrar.getText().equals("Quitar")) {
=======
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (jButton5.getText().equals("Borrar")) {
>>>>>>> 02f64cb79204d14226051fd3b72534ff44adcf48
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                total = total - (Float.parseFloat(jTableDetalle.getValueAt(fila, 1).toString()) * Float.parseFloat(jTableDetalle.getValueAt(fila, 2).toString()));
                jTextTotal.setText(Float.toString(total));
                //jTextField1.setText(Float.toString((float) 0.00));
                modelo.removeRow(fila);
            }
        } else {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int j = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar un detalle compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (j == 0) {
<<<<<<< HEAD
                    total = total - (Float.parseFloat(jTableDetalle.getValueAt(fila, 1).toString()) * Float.parseFloat(jTableDetalle.getValueAt(fila, 2).toString()));
                    jTextTotal.setText(Float.toString(total));
                    //jTextField1.setText(Float.toString((float) 0.00));
                    jButtonBorrar.setText("Quitar");
=======
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 1).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 2).toString()));
                    jTextField7.setText(Float.toString(total));
                    jTextField1.setText(Float.toString((float) 0.00));
                    jButton5.setText("Borrar");
>>>>>>> 02f64cb79204d14226051fd3b72534ff44adcf48
                    if (sql.SafeUpdates()) {
                        iddetallescompras.add(Integer.parseInt(iddetalles.get(fila)));
                        idinsumos.add(detallecompra.ObtenerIDInsumo2(Integer.parseInt(iddetalles.get(fila))));
                        d.setIdinsumo(detallecompra.ObtenerIDInsumo2(Integer.parseInt(iddetalles.get(fila))));
                        d.setIddetallecompra(Integer.parseInt(iddetalles.get(fila)));
                        if (detallecompra.SumarCantidadRestadoInsumos(d)) {
                            d.setIddetallecompra(Integer.parseInt(iddetalles.get(fila)));
                            if (detallecompra.EliminarDetalleCompra(d)) {
                                cant++;
                                if (filasdetalle - 1 == 0 && jTableDetalle.getRowCount() == 1) {
                                    iddetalles.remove(fila);
                                    modelo.removeRow(fila);
                                    filasdetalle = filasdetalle - 1;
                                    String[] opciones = {"Seguir", "No seguir"};
                                    Icon iconopreg = new ImageIcon(getClass().getResource("/Imagenes/pregunta.png"));
                                    int i = JOptionPane.showOptionDialog(null, "Ha eliminado todos las compras registradas de esta Factura. Quiere seguir agregando nuevas compras o prefiere eliminar totalmente dicha Factura?", "Confimar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, iconopreg, opciones, opciones[0]);
                                    if (i == 0) {
                                        /*mc.setIdmovimiento(Integer.parseInt(idcompra));
                                        mc.setIdtipomovimiento(12);
                                        if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                                            c.setIdcompra(Integer.parseInt(idcompra));
                                            if (compra.EliminarCompra(c)) {
                                                jTextField3.setText("");
                                                jComboBox2.setSelectedIndex(0);
                                                ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
                                                jTextField2.setText("");
                                                jTextField4.setText("");
                                                jTextField6.setText("");
                                                jTextField1.setText(Float.toString((float) 0.00));
                                                jTextField7.setText(Float.toString((float) 0.00));
                                                jButton4.setEnabled(false);
                                                jButton1.setText("Registrar Compra");
                                            }
                                        }*/
                                        this.setTitle("Registrando nueva compra Factura N° " + nrofactura);
                                        jTextProveedor.setText("");
                                        jCBUsuario.setSelectedIndex(0);
                                        ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
<<<<<<< HEAD
                                        jTextInsumo.setText("");
                                        JtextCantidad.setText("");
                                        jTextPrecio.setText("");
                                        //jTextField1.setText(Float.toString((float) 0.00));
                                        jTextTotal.setText(Float.toString((float) 0.00));
                                        jButtonModificarCompra.setEnabled(true);
                                        jButtonModificarCompra.setText("Cancelar");
                                        jButtonAgregarCompra.setText("Registrar Compra");
                                        jButtonAgregar.setText("Agregar");
                                        jButtonModificar.setText("Modificar");
                                        jButtonBorrar.setText("Quitar");
=======
                                        jTextField2.setText("");
                                        jTextField4.setText("");
                                        jTextField6.setText("");
                                        jTextField1.setText(Float.toString((float) 0.00));
                                        jTextField7.setText(Float.toString((float) 0.00));
                                        jButton7.setEnabled(true);
                                        jButton7.setText("Cancelar");
                                        jButton1.setText("Registrar Compra");
                                        jButton3.setText("Agregar");
                                        jButton6.setText("Modificar");
                                        jButton5.setText("Borrar");
>>>>>>> 02f64cb79204d14226051fd3b72534ff44adcf48
                                    } else {
                                        mc.setIdmovimiento(Integer.parseInt(idcompra));
                                        mc.setIdtipomovimiento(12);
                                        if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                                            c.setIdcompra(Integer.parseInt(idcompra));
                                            if (compra.EliminarCompra(c)) {
                                                lista = new vListas_Compras();
                                                vMenuPrincipal.jDesktopPane1.add(lista);
                                                lista.setVisible(true);
                                                dispose();
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
    }//GEN-LAST:event_jButtonBorrarActionPerformed

    private void jButtonModificarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarCompraActionPerformed
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        filascompra = jTableDetalle.getRowCount();
        if (!jButtonModificarCompra.getText().equals("Cancelar") && !this.getTitle().equals("Registrando nueva compra Factura N° " + nrofactura)) {
            if (!jTextProveedor.getText().equals("") && !jCBUsuario.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextTotal.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    if (!jButtonModificar.getText().equals("Cancelar")) {
                        if (jTableDetalle.getRowCount() != 0) {
                            int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                            if (i == 0) {
                                if (filascompra > filasdetalle) {
                                    for (int l = filasdetalle; l < filascompra; l++) {
                                        d.setIdcompra(Integer.parseInt(idcompra));
                                        d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(l, 0).toString()));
                                        d.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(l, 1).toString()));
                                        d.setCantidad(Float.parseFloat(jTableDetalle.getValueAt(l, 2).toString()));
                                        if (detallecompra.RegistrarDetalleCompra(d)) {
                                            m.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(l, 1).toString()));
                                            m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(l, 0).toString()));
                                            if (insumo.ActualizarPrecio(m)) {

                                            }
                                        }
                                    }
                                    for (int f = 0; f < iddetalles.size(); f++) {
                                        iddetalle = iddetalles.get(f);
                                        if (sql.SafeUpdates()) {
                                            d.setIdinsumo(detallecompra.ObtenerIDInsumo2(Integer.parseInt(iddetalle)));
                                            d.setIddetallecompra(Integer.parseInt(iddetalle));
                                            if (detallecompra.SumarCantidadRestadoInsumos(d)) {
                                                c.setIdproveedor(compra.ObtenerIDProveedor(jTextProveedor.getText()));
                                                c.setIdusuario(compra.ObtenerIDUsuario((String) jCBUsuario.getSelectedItem()));
                                                c.setMontototal(Float.parseFloat(jTextTotal.getText()));
                                                c.setIdcompra(Integer.parseInt(idcompra));
                                                if (compra.EditarCompra(c)) {

                                                }
                                            }
                                        }
                                    }
                                    for (int p = 0; p < filasdetalle; p++) {
                                        for (int k = 0; k < iddetalles.size(); k++) {
                                            iddetalle = iddetalles.get(k);
                                            d.setIdcompra(Integer.parseInt(idcompra));
                                            d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(p, 0).toString()));
                                            d.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(p, 1).toString()));
                                            d.setCantidad(Float.parseFloat(jTableDetalle.getValueAt(p, 2).toString()));
                                            d.setIddetallecompra(Integer.parseInt(iddetalle));
                                            if (detallecompra.EditarDetalleCompraLotes(d)) {
                                                if (k + 1 < iddetalles.size()) {
                                                    m.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(p, 1).toString()));
                                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(p, 0).toString()));
                                                    if (insumo.ActualizarPrecio(m)) {
                                                        p = p + 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (detallecompra.ActualizarStockInsumos()) {
                                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(idcompra), 12));
                                        mc.setDescripcion("COMPRA INSUMOS");
                                        mc.setIdtipomovimiento(12);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(c.getMontototal());
                                        mc.setIdmovimiento(Integer.parseInt(idcompra));
                                        control_mc.EditarMovimientosCaja(mc);
                                        JOptionPane.showMessageDialog(null, "Modificacion Completa");
                                        lista = new vListas_Compras();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        dispose();
                                    }
                                } else {
                                    for (int j = 0; j < iddetalles.size(); j++) {
                                        iddetalle = iddetalles.get(j);
                                        if (sql.SafeUpdates()) {
                                            d.setIdinsumo(detallecompra.ObtenerIDInsumo2(Integer.parseInt(iddetalle)));
                                            d.setIddetallecompra(Integer.parseInt(iddetalle));
                                            if (detallecompra.SumarCantidadRestadoInsumos(d)) {
                                                c.setIdproveedor(compra.ObtenerIDProveedor(jTextProveedor.getText()));
                                                c.setIdusuario(compra.ObtenerIDUsuario((String) jCBUsuario.getSelectedItem()));
                                                c.setMontototal(Float.parseFloat(jTextTotal.getText()));
                                                c.setIdcompra(Integer.parseInt(idcompra));
                                                if (compra.EditarCompra(c)) {

                                                }
                                            }
                                        }
                                    }
                                    for (int tabla = 0; tabla < jTableDetalle.getRowCount(); tabla++) {
                                        for (int k = 0; k < iddetalles.size(); k++) {
                                            iddetalle = iddetalles.get(k);
                                            d.setIdcompra(Integer.parseInt(idcompra));
                                            d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(tabla, 0).toString()));
                                            d.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(tabla, 1).toString()));
                                            d.setCantidad(Float.parseFloat(jTableDetalle.getValueAt(tabla, 2).toString()));
                                            d.setIddetallecompra(Integer.parseInt(iddetalle));
                                            if (detallecompra.EditarDetalleCompraLotes(d)) {
                                                if (k + 1 < iddetalles.size()) {
                                                    m.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(k, 1).toString()));
                                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(k, 0).toString()));
                                                    if (insumo.ActualizarPrecio(m)) {
                                                        tabla = tabla + 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (detallecompra.ActualizarStockInsumos()) {
                                        mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(idcompra), 12));
                                        mc.setDescripcion("COMPRA INSUMOS");
                                        mc.setIdtipomovimiento(12);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setFecha_movimiento(fecha);
                                        mc.setMonto(c.getMontototal());
                                        mc.setIdmovimiento(Integer.parseInt(idcompra));
                                        control_mc.EditarMovimientosCaja(mc);
                                        JOptionPane.showMessageDialog(null, "Modificacion Completa");
                                        lista = new vListas_Compras();
                                        vMenuPrincipal.jDesktopPane1.add(lista);
                                        lista.setVisible(true);
                                        dispose();
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debes generar al menos, una compra de insumo");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, termine de Modificar el/los dato/s");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar nueva compra para Factura N° " + nrofactura + "?. De optar por si, la misma se eliminara", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mc.setIdmovimiento(Integer.parseInt(idcompra));
                mc.setIdtipomovimiento(12);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    c.setIdcompra(Integer.parseInt(idcompra));
                    if (compra.EliminarCompra(c)) {
                        lista = new vListas_Compras();
                        vMenuPrincipal.jDesktopPane1.add(lista);
                        lista.setVisible(true);
                        dispose();
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonModificarCompraActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButtonAgregarCompra.getText().equals("Cancelar") && cant > 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                for (int x = 0; x < iddetallescompras.size(); x++) {
                    for (int m = 0; m < idinsumos.size(); m++) {
                        if (sql.SafeUpdates()) {
                            d.setIddetallecompra(iddetallescompras.get(x));
                            d.setIdinsumo(idinsumos.get(m));
                            if (detallecompra.ActualizarStockInsumos2(d)) {
                                d.setIddetallecompra(iddetallescompras.get(x));
                                d.setIdinsumo(idinsumos.get(m));
                                if (detallecompra.ActualizarDevolverPrecio(d)) {
                                    d.setIddetallecompra(iddetallescompras.get(x));
                                    if (detallecompra.AnularEliminacionCompra(d)) {
                                        x++;
                                    }
                                }
                            }
                        }
                    }
                }
                lista = new vListas_Compras();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jButtonAgregarCompra.getText().equals("Cancelar") && cant == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vListas_Compras();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jButtonAgregarCompra.getText().equals("Registrar Compra") && this.getTitle().equals("Registrando nueva compra Factura N° " + nrofactura)) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar nueva compra para Factura N° " + nrofactura + "?. De optar por si, la misma se eliminara", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mc.setIdmovimiento(Integer.parseInt(idcompra));
                mc.setIdtipomovimiento(12);
                if (control_mc.EliminarMovCajaCompraVenta(mc)) {
                    c.setIdcompra(Integer.parseInt(idcompra));
                    if (compra.EliminarCompra(c)) {
                        lista = new vListas_Compras();
                        vMenuPrincipal.jDesktopPane1.add(lista);
                        lista.setVisible(true);
                        dispose();
                    }
                }
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextProveedor.getText().equals("") || !jCBUsuario.getSelectedItem().equals("(*) Seleccionar Usuario..") || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("") || jTableDetalle.getRowCount() > 0 || !jTextInsumo.getText().equals("") || !jTextPrecio.getText().equals("") || !JtextCantidad.getText().equals("")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void JtextCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtextCantidadKeyTyped
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
    }//GEN-LAST:event_JtextCantidadKeyTyped

    private void jTextPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPrecioKeyTyped
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
    }//GEN-LAST:event_jTextPrecioKeyTyped

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (jTable2.getRowCount() != 0) {
            int m = jTable2.getSelectedRow();
            if (m == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarNombreComercialProv.dispose();
                jTextProveedor.setText(jTable2.getValueAt(m, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos proveedores todavia");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextProveedor.setText("");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButtonSeleccionarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarProvActionPerformed
        vSeleccionarNombreComercialProv.setSize(345, 625);
        vSeleccionarNombreComercialProv.setLocationRelativeTo(this);
        vSeleccionarNombreComercialProv.setModal(true);
        vSeleccionarNombreComercialProv.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarProvActionPerformed

    private void vSeleccionarNombreComercialProvWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvWindowClosing
        int p = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextProveedor.setText("");
        } else {
            vSeleccionarNombreComercialProv.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarNombreComercialProvWindowClosing

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (jTable3.getRowCount() != 0) {
            int i = jTable3.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarInsumo.dispose();
                jTextInsumo.setText(jTable3.getValueAt(i, 0).toString());
                jTextPrecio.setText(jTable3.getValueAt(i, 1).toString());
                JtextCantidad.setText(jTable3.getValueAt(i, 2).toString());
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int j = JOptionPane.showConfirmDialog(null, "Esta Seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (j == 0) {
            vSeleccionarInsumo.dispose();
            jTextInsumo.setText("");
            jTextPrecio.setText("");
            JtextCantidad.setText("");
        } else {
            vSeleccionarInsumo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void vSeleccionarInsumoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoWindowClosing
        int j = JOptionPane.showConfirmDialog(null, "Esta Seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (j == 0) {
            vSeleccionarInsumo.dispose();
            jTextInsumo.setText("");
            jTextPrecio.setText("");
            JtextCantidad.setText("");
        } else {
            vSeleccionarInsumo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarInsumoWindowClosing

    private void jButtonSeleccionarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarInsumoActionPerformed
        vSeleccionarInsumo.setSize(360, 608);
        vSeleccionarInsumo.setLocationRelativeTo(this);
        vSeleccionarInsumo.setModal(true);
        vSeleccionarInsumo.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarInsumoActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
<<<<<<< HEAD
        if (jButtonModificar.getText().equals("Modificar") && jTableDetalle.getRowCount() != 0) {
            jTableDetalle.clearSelection();
            jTableDetalle.getSelectionModel().clearSelection();
            jButtonBorrar.setText("Quitar");
=======
        if (jButton6.getText().equals("Modificar") && jTable1.getRowCount() != 0) {
            jTable1.clearSelection();
            jTable1.getSelectionModel().clearSelection();
            jButton5.setText("Borrar");
>>>>>>> 02f64cb79204d14226051fd3b72534ff44adcf48
        }
    }//GEN-LAST:event_formMouseClicked

    private void vSeleccionarNombreComercialProvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvMouseClicked
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
        MostrarProveedores();
        jTextField5.setText("");
    }//GEN-LAST:event_vSeleccionarNombreComercialProvMouseClicked

    private void vSeleccionarInsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoMouseClicked
        jTable3.clearSelection();
        jTable3.getSelectionModel().clearSelection();
        MostrarInsumos();
        jTextField8.setText("");
    }//GEN-LAST:event_vSeleccionarInsumoMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        if (!jTextField5.getText().isEmpty()) {
            String[] columnas = {"NOMBRE COMERCIAL PROVEEDORES"};
            proveedores = compra.MostrarProveedorBuscado(jTextField5.getText());
            if (proveedores.length != 0) {
                modelprov = new DefaultTableModel(proveedores, columnas);
                jTable2.setModel(modelprov);
                EliminarFilasVaciasProveedores();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if (!jTextField8.getText().isEmpty()) {
            String[] columnas = {"INSUMOS", "PRECIO", "CANTIDAD"};
            insumos = detallecompra.MostrarInsumosBuscado(jTextField8.getText());
            if (insumos.length != 0) {
                modelinsumo = new DefaultTableModel(insumos, columnas);
                jTable3.setModel(modelinsumo);
                ocultarColumnasIns();
                EliminarFilasVaciasInsumos();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        int i = jList1.getSelectedIndex();
        if (i != -1) {
            jTextProveedor.setText(jList1.getSelectedValue());
            jList1.setVisible(false);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jTextProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextProveedorKeyReleased
        ListaProveedores();
    }//GEN-LAST:event_jTextProveedorKeyReleased

    private void jTextInsumoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextInsumoKeyReleased
        ListaInsumos();
    }//GEN-LAST:event_jTextInsumoKeyReleased

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        ArrayList<String> array;
        if (i != -1) {
            jTextInsumo.setText(jList2.getSelectedValue());
            array = compra.ObtenerDatosNumInsumos(jTextInsumo.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    jTextPrecio.setText(array.get(j));
                    j++;
                    JtextCantidad.setText(array.get(j));
                    j++;
                    jList2.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTableDetalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetalleMousePressed
        //jTextField1.setText(String.valueOf(Float.parseFloat(modelo.getValueAt(vCompras_Insumos.jTable1.getSelectedRow(), 1).toString()) * Float.parseFloat(modelo.getValueAt(vCompras_Insumos.jTable1.getSelectedRow(), 2).toString())));
        if (jButtonSeleccionarInsumo.isEnabled()) {
            int i = jTableDetalle.getSelectedRow(), j = filasdetalle;
            for (int l = 0; l < j; l++) {
                if (i == l) {
<<<<<<< HEAD
                    jButtonBorrar.setText("Eliminar");
=======
                    jButton6.setText("Modificar Detalle");
                    jButton5.setText("Borrar Detalle");
>>>>>>> 02f64cb79204d14226051fd3b72534ff44adcf48
                    break;
                }
            }
            if (i >= j) {
<<<<<<< HEAD
                jButtonBorrar.setText("Quitar");
=======
                jButton5.setText("Borrar");
>>>>>>> 02f64cb79204d14226051fd3b72534ff44adcf48
            }
        }
    }//GEN-LAST:event_jTableDetalleMousePressed

    private void jTextInsumoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextInsumoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextInsumoKeyTyped

    private void jTextField8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField8KeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:       
        int i = JOptionPane.showConfirmDialog(null, "Cancelar Operación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vListas_Compras lista_compras = new vListas_Compras();
            vMenuPrincipal.jDesktopPane1.add(lista_compras);
            lista_compras.setVisible(true);
            dispose();
        } else {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }        
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField JtextCantidad;
    public static javax.swing.JButton btnCancelar;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    public static javax.swing.JButton jButtonAgregar;
    public static javax.swing.JButton jButtonAgregarCompra;
    public static javax.swing.JButton jButtonBorrar;
    public static javax.swing.JButton jButtonModificar;
    public static javax.swing.JButton jButtonModificarCompra;
    private javax.swing.JButton jButtonSeleccionarInsumo;
    private javax.swing.JButton jButtonSeleccionarProv;
    public static javax.swing.JComboBox<String> jCBUsuario;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTable jTableDetalle;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField8;
    public static javax.swing.JTextField jTextInsumo;
    public static javax.swing.JTextField jTextPrecio;
    public static javax.swing.JTextField jTextProveedor;
    public static javax.swing.JTextField jTextTotal;
    private javax.swing.JLabel jlabelMensaje;
    private javax.swing.JDialog vSeleccionarInsumo;
    private javax.swing.JDialog vSeleccionarNombreComercialProv;
    // End of variables declaration//GEN-END:variables

    /*public TablaDetalleCompras getTablaDetalleCompras() {
        return tabladetallecompras;
    }*/
}
