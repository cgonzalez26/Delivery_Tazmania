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
        jTextField1.setText(Float.toString((float) 0.00));
        jTextField7.setText(Float.toString((float) 0.00));
        jTextField1.setEditable(false);
        jTextField7.setEditable(false);
        jButton7.setEnabled(false);
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
        jTextField1.setEnabled(false);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jTextField3.setEnabled(false);
        jTextField6.setEnabled(false);
        jTextField4.setEnabled(false);
        jDateChooser1.setEnabled(false);
        jComboBox2.setEnabled(false);
        jButton2.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton6.setEnabled(false);
        jButton5.setEnabled(false);
        jButton1.setEnabled(false);
        jButton4.setEnabled(false);
    }

    public void Limpiar() {
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField6.setText("");
    }

    public void ComboUsuario() {
        user = combo.combox("usuarios", "Login");
        for (Object usuario : user) {
            jComboBox2.addItem((String) usuario);
        }
    }

    public void ListaProveedores() {
        listprov = combo.list("proveedores", "Nombre_comercial", jTextField3.getText());
        String substr = jTextField3.getText().toLowerCase();
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
                        if (jTextField3.getText().isEmpty()) {
                            jList1.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void ListaInsumos() {
        listprov = combo.list("insumos", "descripcion", jTextField2.getText());
        String substr = jTextField2.getText().toLowerCase();
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
                        if (jTextField2.getText().isEmpty()) {
                            jList2.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBox2.getItemCount(); i++) {
            if (jComboBox2.getItemAt(i) == null) {
                jComboBox2.removeItemAt(i);
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
        Object[] columnas = {"INSUMO", "PRECIO", "CANTIDAD"};
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnas);
        jTable1.setModel(modelo);
    }

    public float CalcularMontoTotal() {
        try {
            total = total + (Float.parseFloat(jTextField6.getText().trim()) * Float.parseFloat(jTextField4.getText().trim()));
            vCompras_Insumos.jTextField7.setText(Float.toString(total));

        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return total;
    }

    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
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
        jTextField3 = new javax.swing.JTextField();
        jList1 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jList2 = new javax.swing.JList<>();
        jButton4 = new javax.swing.JButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jlabelMensaje = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

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

        jTextField3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });
        jLayeredPane3.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 292, 30));

        jList1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList1.setValueIsAdjusting(true);
        jList1.setVisibleRowCount(0);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jLayeredPane3.add(jList1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 290, 0));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 44, 30));

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField2.setToolTipText("");
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        jLayeredPane3.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 224, 30));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jLayeredPane3.add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 222, 0));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, 46, 32));

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Insumos a Comprar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTable1);

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Agregar ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setText("Modificar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Borrar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLayeredPane2.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButton6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButton5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(jButton5))
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jButton6)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane3.add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1220, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setText("Usuario:");
        jLayeredPane3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 11, 232, -1));

        jComboBox2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccionar Usuario.." }));
        jLayeredPane3.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 35, 250, 32));
        jComboBox2.getAccessibleContext().setAccessibleParent(this);

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLayeredPane3.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 37, 175, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("(*) Fecha:");
        jLayeredPane3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 11, 80, 20));

        jButton7.setBackground(new java.awt.Color(252, 249, 57));
        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton7.setText("Modificar Compra");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 480, 150, -1));

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Registrar Compra");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 480, 136, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("(*) Precio;");
        jLayeredPane3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 388, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) Cantidad:");
        jLayeredPane3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 388, -1, 30));

        jTextField4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });
        jLayeredPane3.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 130, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel8.setText("Sub Total:");
        jLayeredPane3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 391, -1, -1));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLayeredPane3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 415, 150, 32));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel10.setText("TOTAL:");
        jLayeredPane3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1015, 391, -1, 20));

        jTextField6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });
        jLayeredPane3.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 420, 130, 32));

        jTextField7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLayeredPane3.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1015, 417, 140, 32));

        jlabelMensaje.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jlabelMensaje.setForeground(new java.awt.Color(255, 0, 0));
        jlabelMensaje.setText("No hay CAJA ABIERTA.");
        jLayeredPane3.add(jlabelMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(971, 485, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("(*) Insumo:");
        jLayeredPane3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("(*) Proveedor:");
        jLayeredPane3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        getContentPane().add(jLayeredPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1242, 519));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        if (!jButton1.getText().equals("Cancelar") && this.getTitle().equals("Registrar Compra")) {
            if (!jTextField3.getText().equals("") && !jComboBox2.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextField7.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    if (!jButton6.getText().equals("Cancelar")) {
                        if (jTable1.getRowCount() != 0) {
                            c.setIdproveedor(compra.ObtenerIDProveedor(jTextField3.getText()));
                            c.setIdusuario(compra.ObtenerIDUsuario((String) jComboBox2.getSelectedItem()));
                            c.setMontototal(Float.parseFloat(jTextField7.getText()));
                            if (compra.EfectuarCompra(c)) {
                                for (int g = 0; g < jTable1.getRowCount(); g++) {
                                    d.setIdcompra(detallecompra.ObtenerIDCompra());
                                    d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(g, 0).toString()));
                                    d.setPrecio(Float.parseFloat(jTable1.getValueAt(g, 1).toString()));
                                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(g, 2).toString()));
                                    if (detallecompra.RegistrarDetalleCompra(d)) {

                                    }
                                }
                                for (int d = 0; d < jTable1.getRowCount(); d++) {
                                    m.setPrecio(Float.parseFloat(jTable1.getValueAt(d, 1).toString()));
                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(d, 0).toString()));
                                    if (insumo.ActualizarPrecio(m)) {

                                    }
                                }
                                if (sql.SafeUpdates()) {
                                    if (detallecompra.ActualizarStockInsumos()) {
                                        JOptionPane.showMessageDialog(null, "Compra Registrada");
                                        int idmovimiento = sql.obtenerUltimoId("compras", "idcompra");
                                        String codigo = sql.generaCodigo("compra");
                                        sql.ejecutarSql("UPDATE compras SET NroCompra ='" + codigo + "' WHERE idcompra=" + idmovimiento);
                                        c.setNrocompra(codigo);
                                        mc.setDescripcion("COMPRA INSUMOS");
                                        mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                                        mc.setIdtipomovimiento(12);
                                        mc.setIdusuario(Session.getIdusuario());
                                        mc.setNromovimiento(codigo);
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
        } else if (jButton1.getText().equals("Cancelar") && cant > 0) {
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
        } else if (jButton1.getText().equals("Cancelar") && cant == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vListas_Compras();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jButton1.getText().equals("Registrar Compra") && this.getTitle().equals("Registrando nueva compra Factura N° " + nrofactura)) {
            if (!jTextField3.getText().equals("") && !jComboBox2.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextField7.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    if (!jButton6.getText().equals("Cancelar")) {
                        if (jTable1.getRowCount() != 0) {
                            c.setIdproveedor(compra.ObtenerIDProveedor(jTextField3.getText()));
                            c.setIdusuario(compra.ObtenerIDUsuario((String) jComboBox2.getSelectedItem()));
                            c.setMontototal(Float.parseFloat(jTextField7.getText()));
                            c.setIdcompra(Integer.parseInt(idcompra));
                            if (compra.EditarCompra(c)) {
                                for (int g = 0; g < jTable1.getRowCount(); g++) {
                                    d.setIdcompra(Integer.parseInt(idcompra));
                                    d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(g, 0).toString()));
                                    d.setPrecio(Float.parseFloat(jTable1.getValueAt(g, 1).toString()));
                                    d.setCantidad(Float.parseFloat(jTable1.getValueAt(g, 2).toString()));
                                    if (detallecompra.RegistrarDetalleCompra(d)) {

                                    }
                                }
                                for (int d = 0; d < jTable1.getRowCount(); d++) {
                                    m.setPrecio(Float.parseFloat(jTable1.getValueAt(d, 1).toString()));
                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(d, 0).toString()));
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        boolean repetido = false;
        if (!jTextField2.getText().equals("") && !jTextField6.getText().equals("") && !jTextField4.getText().equals("")) {
            if (jTable1.getRowCount() != 0) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    if (jTextField2.getText().equals(jTable1.getValueAt(i, 0).toString()) && jButton6.getText().equals("Modificar")) {
                        JOptionPane.showMessageDialog(null, "Insumo ya agregado!");
                        repetido = true;
                    }
                }
            }
            if (repetido == false) {
                if (jButton6.getText().equals("Modificar")) {
                    Object Datos[] = {jTextField2.getText(), (Float.parseFloat(jTextField6.getText())), (Float.parseFloat(jTextField4.getText()))};
                    modelo.addRow(Datos);
                    jTextField1.setText(String.valueOf(Float.parseFloat(jTextField6.getText()) * Float.parseFloat(jTextField4.getText())));
                    CalcularMontoTotal();
                    Limpiar();
                } else {
                    int i = jTable1.getSelectedRow();
                    if (i == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        modelo.setValueAt(jTextField2.getText(), i, 0);
                        modelo.setValueAt((Float.parseFloat(jTextField6.getText())), i, 1);
                        modelo.setValueAt((Float.parseFloat(jTextField4.getText())), i, 2);
                        jTextField1.setText(String.valueOf(Float.parseFloat(jTextField6.getText()) * Float.parseFloat(jTextField4.getText())));
                        CalcularMontoTotal();
                        Limpiar();
                        jTable1.clearSelection();
                        jTable1.getSelectionModel().clearSelection();
                        jButton6.setText("Modificar");
                        jButton5.setEnabled(true);
                        LimpiarSeleccion();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos Insumo, Precio y Cantidad");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int i = jTable1.getSelectedRow();
        if (jButton6.getText().equals("Modificar")) {
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila");
            } else {
                jButton6.setText("Cancelar");
                jButton5.setEnabled(false);
                jTextField2.setText(jTable1.getValueAt(i, 0).toString());
                jTextField6.setText(jTable1.getValueAt(i, 1).toString());
                jTextField4.setText(jTable1.getValueAt(i, 2).toString());
                total = total - (Float.parseFloat(jTable1.getValueAt(i, 1).toString()) * Float.parseFloat(jTable1.getValueAt(i, 2).toString()));
                jTextField7.setText(Float.toString(total));
                jTextField1.setText(Float.toString((float) 0.00));
            }
        } else {
            total = total + (Float.parseFloat(jTextField6.getText().trim()) * Float.parseFloat(jTextField4.getText().trim()));
            jTextField7.setText(Float.toString(total));
            jButton6.setText("Modificar");
            jButton5.setEnabled(true);
            jTable1.clearSelection();
            jTable1.getSelectionModel().clearSelection();
            Limpiar();
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (jButton5.getText().equals("Quitar")) {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                total = total - (Float.parseFloat(jTable1.getValueAt(fila, 1).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 2).toString()));
                jTextField7.setText(Float.toString(total));
                jTextField1.setText(Float.toString((float) 0.00));
                modelo.removeRow(fila);
            }
        } else {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int j = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar un detalle compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (j == 0) {
                    total = total - (Float.parseFloat(jTable1.getValueAt(fila, 1).toString()) * Float.parseFloat(jTable1.getValueAt(fila, 2).toString()));
                    jTextField7.setText(Float.toString(total));
                    jTextField1.setText(Float.toString((float) 0.00));
                    jButton5.setText("Quitar");
                    if (sql.SafeUpdates()) {
                        iddetallescompras.add(Integer.parseInt(iddetalles.get(fila)));
                        idinsumos.add(detallecompra.ObtenerIDInsumo2(Integer.parseInt(iddetalles.get(fila))));
                        d.setIdinsumo(detallecompra.ObtenerIDInsumo2(Integer.parseInt(iddetalles.get(fila))));
                        d.setIddetallecompra(Integer.parseInt(iddetalles.get(fila)));
                        if (detallecompra.SumarCantidadRestadoInsumos(d)) {
                            d.setIddetallecompra(Integer.parseInt(iddetalles.get(fila)));
                            if (detallecompra.EliminarDetalleCompra(d)) {
                                cant++;
                                if (filasdetalle - 1 == 0 && jTable1.getRowCount() == 1) {
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
                                        jTextField3.setText("");
                                        jComboBox2.setSelectedIndex(0);
                                        ((JTextField) vCompras_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
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
                                        jButton5.setText("Quitar");
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
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String fecha = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
        filascompra = jTable1.getRowCount();
        if (!jButton7.getText().equals("Cancelar") && !this.getTitle().equals("Registrando nueva compra Factura N° " + nrofactura)) {
            if (!jTextField3.getText().equals("") && !jComboBox2.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextField7.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    if (!jButton6.getText().equals("Cancelar")) {
                        if (jTable1.getRowCount() != 0) {
                            int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                            if (i == 0) {
                                if (filascompra > filasdetalle) {
                                    for (int l = filasdetalle; l < filascompra; l++) {
                                        d.setIdcompra(Integer.parseInt(idcompra));
                                        d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(l, 0).toString()));
                                        d.setPrecio(Float.parseFloat(jTable1.getValueAt(l, 1).toString()));
                                        d.setCantidad(Float.parseFloat(jTable1.getValueAt(l, 2).toString()));
                                        if (detallecompra.RegistrarDetalleCompra(d)) {
                                            m.setPrecio(Float.parseFloat(jTable1.getValueAt(l, 1).toString()));
                                            m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(l, 0).toString()));
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
                                                c.setIdproveedor(compra.ObtenerIDProveedor(jTextField3.getText()));
                                                c.setIdusuario(compra.ObtenerIDUsuario((String) jComboBox2.getSelectedItem()));
                                                c.setMontototal(Float.parseFloat(jTextField7.getText()));
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
                                            d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(p, 0).toString()));
                                            d.setPrecio(Float.parseFloat(jTable1.getValueAt(p, 1).toString()));
                                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(p, 2).toString()));
                                            d.setIddetallecompra(Integer.parseInt(iddetalle));
                                            if (detallecompra.EditarDetalleCompraLotes(d)) {
                                                if (k + 1 < iddetalles.size()) {
                                                    m.setPrecio(Float.parseFloat(jTable1.getValueAt(p, 1).toString()));
                                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(p, 0).toString()));
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
                                                c.setIdproveedor(compra.ObtenerIDProveedor(jTextField3.getText()));
                                                c.setIdusuario(compra.ObtenerIDUsuario((String) jComboBox2.getSelectedItem()));
                                                c.setMontototal(Float.parseFloat(jTextField7.getText()));
                                                c.setIdcompra(Integer.parseInt(idcompra));
                                                if (compra.EditarCompra(c)) {

                                                }
                                            }
                                        }
                                    }
                                    for (int tabla = 0; tabla < jTable1.getRowCount(); tabla++) {
                                        for (int k = 0; k < iddetalles.size(); k++) {
                                            iddetalle = iddetalles.get(k);
                                            d.setIdcompra(Integer.parseInt(idcompra));
                                            d.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(tabla, 0).toString()));
                                            d.setPrecio(Float.parseFloat(jTable1.getValueAt(tabla, 1).toString()));
                                            d.setCantidad(Float.parseFloat(jTable1.getValueAt(tabla, 2).toString()));
                                            d.setIddetallecompra(Integer.parseInt(iddetalle));
                                            if (detallecompra.EditarDetalleCompraLotes(d)) {
                                                if (k + 1 < iddetalles.size()) {
                                                    m.setPrecio(Float.parseFloat(jTable1.getValueAt(k, 1).toString()));
                                                    m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTable1.getValueAt(k, 0).toString()));
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
    }//GEN-LAST:event_jButton7ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButton1.getText().equals("Cancelar") && cant > 0) {
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
        } else if (jButton1.getText().equals("Cancelar") && cant == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vListas_Compras();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jButton1.getText().equals("Registrar Compra") && this.getTitle().equals("Registrando nueva compra Factura N° " + nrofactura)) {
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
        } else if (!jTextField3.getText().equals("") || !jComboBox2.getSelectedItem().equals("(*) Seleccionar Usuario..") || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("") || jTable1.getRowCount() > 0 || !jTextField2.getText().equals("") || !jTextField6.getText().equals("") || !jTextField4.getText().equals("")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

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

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
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
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (jTable2.getRowCount() != 0) {
            int m = jTable2.getSelectedRow();
            if (m == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarNombreComercialProv.dispose();
                jTextField3.setText(jTable2.getValueAt(m, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos proveedores todavia");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextField3.setText("");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        vSeleccionarNombreComercialProv.setSize(345, 625);
        vSeleccionarNombreComercialProv.setLocationRelativeTo(this);
        vSeleccionarNombreComercialProv.setModal(true);
        vSeleccionarNombreComercialProv.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void vSeleccionarNombreComercialProvWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvWindowClosing
        int p = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextField3.setText("");
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
                jTextField2.setText(jTable3.getValueAt(i, 0).toString());
                jTextField6.setText(jTable3.getValueAt(i, 1).toString());
                jTextField4.setText(jTable3.getValueAt(i, 2).toString());
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int j = JOptionPane.showConfirmDialog(null, "Esta Seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (j == 0) {
            vSeleccionarInsumo.dispose();
            jTextField2.setText("");
            jTextField6.setText("");
            jTextField4.setText("");
        } else {
            vSeleccionarInsumo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void vSeleccionarInsumoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoWindowClosing
        int j = JOptionPane.showConfirmDialog(null, "Esta Seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (j == 0) {
            vSeleccionarInsumo.dispose();
            jTextField2.setText("");
            jTextField6.setText("");
            jTextField4.setText("");
        } else {
            vSeleccionarInsumo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarInsumoWindowClosing

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        vSeleccionarInsumo.setSize(360, 608);
        vSeleccionarInsumo.setLocationRelativeTo(this);
        vSeleccionarInsumo.setModal(true);
        vSeleccionarInsumo.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (jButton6.getText().equals("Modificar") && jTable1.getRowCount() != 0) {
            jTable1.clearSelection();
            jTable1.getSelectionModel().clearSelection();
            jButton5.setText("Quitar");
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
            jTextField3.setText(jList1.getSelectedValue());
            jList1.setVisible(false);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        ListaProveedores();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        ListaInsumos();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        ArrayList<String> array;
        if (i != -1) {
            jTextField2.setText(jList2.getSelectedValue());
            array = compra.ObtenerDatosNumInsumos(jTextField2.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    jTextField6.setText(array.get(j));
                    j++;
                    jTextField4.setText(array.get(j));
                    j++;
                    jList2.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        jTextField1.setText(String.valueOf(Float.parseFloat(modelo.getValueAt(vCompras_Insumos.jTable1.getSelectedRow(), 1).toString()) * Float.parseFloat(modelo.getValueAt(vCompras_Insumos.jTable1.getSelectedRow(), 2).toString())));
        if (jButton4.isEnabled()) {
            int i = jTable1.getSelectedRow(), j = filasdetalle;
            for (int l = 0; l < j; l++) {
                if (i == l) {
                    jButton5.setText("Eliminar");
                    break;
                }
            }
            if (i >= j) {
                jButton5.setText("Quitar");
            }
        }
    }//GEN-LAST:event_jTable1MousePressed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField8KeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    public static javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    public static javax.swing.JButton jButton5;
    public static javax.swing.JButton jButton6;
    public static javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    public static javax.swing.JComboBox<String> jComboBox2;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
    public static javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jTextField2;
    public static javax.swing.JTextField jTextField3;
    public static javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    public static javax.swing.JTextField jTextField6;
    public static javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel jlabelMensaje;
    private javax.swing.JDialog vSeleccionarInsumo;
    private javax.swing.JDialog vSeleccionarNombreComercialProv;
    // End of variables declaration//GEN-END:variables

    /*public TablaDetalleCompras getTablaDetalleCompras() {
        return tabladetallecompras;
    }*/
}
