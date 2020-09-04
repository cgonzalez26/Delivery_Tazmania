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
        jListProveedores.setVisible(false);
        jListInsumos.setVisible(false);
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
        jDateFecha.setEnabled(false);
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
        jListProveedores.setModel(listmodel);
        listmodel.removeAllElements();
        if (listprov.size() > 0) {
            for (int i = 0; i < listprov.size(); i++) {
                if (listprov.get(i) == null) {
                    listprov.remove(i);
                } else {
                    String sublist = listprov.get(i).toLowerCase();
                    if (sublist.contains(substr)) {
                        listmodel.addElement(listprov.get(i));
                        jListProveedores.setVisible(true);
                        if (jTextProveedor.getText().isEmpty()) {
                            jListProveedores.setVisible(false);
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
        jListInsumos.setModel(listmodel);
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
                        jListInsumos.setVisible(true);
                        if (jTextInsumo.getText().isEmpty()) {
                            jListInsumos.setVisible(false);
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
        jTableProveedores.setModel(modelprov);
        EliminarFilasVaciasProveedores();
        //AjustarTamañoFilasProveedores();
    }

    public void MostrarInsumos() {
        String[] columnas = {"INSUMOS", "PRECIO", "CANTIDAD"};
        insumos = detallecompra.MostrarInsumos();
        modelinsumo = new DefaultTableModel(insumos, columnas);
        jTableInsumos.setModel(modelinsumo);
        ocultarColumnasIns();
        EliminarFilasVaciasInsumos();
        AjustarTamañoFilasInsumos();
    }

    public void ocultarColumnasIns() {
        jTableInsumos.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableInsumos.getColumnModel().getColumn(1).setMinWidth(0);
        jTableInsumos.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTableInsumos.getColumnModel().getColumn(2).setMaxWidth(0);
        jTableInsumos.getColumnModel().getColumn(2).setMinWidth(0);
        jTableInsumos.getColumnModel().getColumn(2).setPreferredWidth(0);
    }

    public void AjustarTamañoFilasProveedores() {
        if (jTableProveedores.getRowCount() != 0) {
            for (int i = 0; i < jTableProveedores.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nomprov = (int) font.getStringBounds(jTableProveedores.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nomprov > jTableProveedores.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTableProveedores.getColumnModel().getColumn(0).setPreferredWidth(nomprov);
                }
            }
        }
    }

    public void AjustarTamañoFilasInsumos() {
        if (jTableInsumos.getRowCount() != 0) {
            for (int i = 0; i < jTableInsumos.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nomins = (int) font.getStringBounds(jTableInsumos.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nomins > jTableInsumos.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTableInsumos.getColumnModel().getColumn(0).setPreferredWidth(nomins);
                }
            }
        }
    }

    public void EliminarFilasVaciasProveedores() {
        if (jTableProveedores.getRowCount() != 0) {
            int filas = jTableProveedores.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTableProveedores.getValueAt(fila, 0) == null) {
                    modelprov.removeRow(fila);
                }
            }
        }
    }

    public void EliminarFilasVaciasInsumos() {
        if (jTableInsumos.getRowCount() != 0) {
            int filas = jTableInsumos.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTableInsumos.getValueAt(fila, 0) == null) {
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
        jTableProveedores = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregarProv = new javax.swing.JButton();
        jButtonCancelarProv = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelNombreProveedor = new javax.swing.JLabel();
        jTextFieldProveedor = new javax.swing.JTextField();
        jButtonBuscarProv = new javax.swing.JButton();
        vSeleccionarInsumo = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableInsumos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregarIns = new javax.swing.JButton();
        jButtonCancelarIns = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabelNombreInsumo = new javax.swing.JLabel();
        jTextFieldInsumo = new javax.swing.JTextField();
        jButtonBuscarInsumo = new javax.swing.JButton();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jTextProveedor = new javax.swing.JTextField();
        jListProveedores = new javax.swing.JList<>();
        jButtonSeleccionarProv = new javax.swing.JButton();
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
        jLabelInsumo = new javax.swing.JLabel();
        jTextInsumo = new javax.swing.JTextField();
        jButtonSeleccionarInsumo = new javax.swing.JButton();
        jLabelPrecio = new javax.swing.JLabel();
        jTextPrecio = new javax.swing.JTextField();
        jLabelCantidad = new javax.swing.JLabel();
        JtextCantidad = new javax.swing.JTextField();
        jListInsumos = new javax.swing.JList<>();
        jLabelUsuario = new javax.swing.JLabel();
        jCBUsuario = new javax.swing.JComboBox<>();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        jLabelFecha = new javax.swing.JLabel();
        jButtonModificarCompra = new javax.swing.JButton();
        jButtonAgregarCompra = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jTextTotal = new javax.swing.JTextField();
        jlabelMensaje = new javax.swing.JLabel();
        jLabelProveedor = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        vSeleccionarNombreComercialProv.setTitle("Seleccionar Nombre Comercial Proveedor");
        java.awt.Image iconoprov = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
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

        jTableProveedores.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableProveedores);

        jButtonAgregarProv.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarProv.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarProv.setText("Agregar");
        jButtonAgregarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProvActionPerformed(evt);
            }
        });

        jButtonCancelarProv.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelarProv.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarProv.setText("Cancelar");
        jButtonCancelarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarProvActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelNombreProveedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreProveedor.setText("Nombre Proveedor");

        jTextFieldProveedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarProv.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarProv.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarProv.setText("Buscar");
        jButtonBuscarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarProvActionPerformed(evt);
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
                        .addComponent(jTextFieldProveedor))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabelNombreProveedor)
                        .addGap(0, 104, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jButtonBuscarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNombreProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBuscarProv)
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
                        .addGap(20, 20, 20)
                        .addComponent(jButtonAgregarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonCancelarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
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
                    .addComponent(jButtonCancelarProv)
                    .addComponent(jButtonAgregarProv))
                .addContainerGap())
        );

        vSeleccionarInsumo.setTitle("Seleccionar Insumo");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
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

        jTableInsumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableInsumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTableInsumos);

        jButtonAgregarIns.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarIns.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarIns.setText("Agregar");
        jButtonAgregarIns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarInsActionPerformed(evt);
            }
        });

        jButtonCancelarIns.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelarIns.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarIns.setText("Cancelar");
        jButtonCancelarIns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarInsActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelNombreInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreInsumo.setText("Nombre Insumo");

        jTextFieldInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldInsumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldInsumoKeyTyped(evt);
            }
        });

        jButtonBuscarInsumo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarInsumo.setText("Buscar");
        jButtonBuscarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarInsumoActionPerformed(evt);
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
                        .addComponent(jTextFieldInsumo))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabelNombreInsumo)
                        .addGap(0, 98, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jButtonBuscarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNombreInsumo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscarInsumo)
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
                        .addComponent(jButtonAgregarIns, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonCancelarIns, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jButtonAgregarIns)
                    .addComponent(jButtonCancelarIns))
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

        jListProveedores.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jListProveedores.setValueIsAdjusting(true);
        jListProveedores.setVisibleRowCount(0);
        jListProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListProveedoresMouseClicked(evt);
            }
        });
        jLayeredPane3.add(jListProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 290, -1));

        jButtonSeleccionarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarProv.setFocusable(false);
        jButtonSeleccionarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarProvActionPerformed(evt);
            }
        });
        jLayeredPane3.add(jButtonSeleccionarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 44, 30));

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

        jLabelInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelInsumo.setText("(*) Insumo:");

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

        jLabelPrecio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelPrecio.setText("(*) Precio:");

        jTextPrecio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPrecioKeyTyped(evt);
            }
        });

        jLabelCantidad.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelCantidad.setText("(*) Cantidad:");

        JtextCantidad.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        JtextCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JtextCantidadKeyTyped(evt);
            }
        });

        jListInsumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jListInsumos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jListInsumos.setValueIsAdjusting(true);
        jListInsumos.setVisibleRowCount(0);
        jListInsumos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListInsumosMouseClicked(evt);
            }
        });

        jLayeredPane2.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonAgregar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonModificar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonBorrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabelInsumo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jTextInsumo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButtonSeleccionarInsumo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabelPrecio, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jTextPrecio, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabelCantidad, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(JtextCantidad, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jListInsumos, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jLabelInsumo)
                        .addGap(243, 243, 243)
                        .addComponent(jLabelPrecio)
                        .addGap(152, 152, 152)
                        .addComponent(jLabelCantidad))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jListInsumos, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jLabelInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jTextInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jListInsumos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButtonSeleccionarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jButtonAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(jButtonModificar)
                        .addGap(31, 31, 31)
                        .addComponent(jButtonBorrar)
                        .addGap(63, 63, 63))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jLayeredPane3.add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1220, 320));

        jLabelUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelUsuario.setText("Usuario:");
        jLayeredPane3.add(jLabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 11, 232, -1));

        jCBUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCBUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccionar Usuario.." }));
        jLayeredPane3.add(jCBUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 35, 250, 32));
        jCBUsuario.getAccessibleContext().setAccessibleParent(this);

        jDateFecha.setDateFormatString("dd-MM-yyyy HH:mm");
        jDateFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLayeredPane3.add(jDateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 37, 175, 30));

        jLabelFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelFecha.setText("(*) Fecha:");
        jLayeredPane3.add(jLabelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 11, 60, 20));

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

        jLabelTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTotal.setText("TOTAL:");
        jLayeredPane3.add(jLabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, -1, 20));

        jTextTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLayeredPane3.add(jTextTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 410, 140, 32));

        jlabelMensaje.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jlabelMensaje.setForeground(new java.awt.Color(255, 0, 0));
        jlabelMensaje.setText("No hay CAJA ABIERTA.");
        jLayeredPane3.add(jlabelMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, 20));

        jLabelProveedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelProveedor.setText("(*) Proveedor:");
        jLayeredPane3.add(jLabelProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

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

    private void registrarMovimientoCaja(String fecha, int idmovimiento){
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
    }
    
    private void volverPantallaListadoCompras(){
        lista = new vListas_Compras();
        vMenuPrincipal.jDesktopPane1.add(lista);
        lista.setVisible(true);
        this.dispose();
    }
    
    private void jButtonAgregarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarCompraActionPerformed
        String fecha = ((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText();
        if (!jTextProveedor.getText().equals("") && !jCBUsuario.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextTotal.getText().trim().equals("") && !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().equals("")) {
            if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
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
                                    if (detallecompra.ActualizarStockInsumo(d)) {}
                                }else{
                                    System.out.print("Error al agregar Detalle de Compra" + d);
                                }
                            }
                            //actualizar Precio de Insumo en caso de que se haya cambiado
                            for (int d = 0; d < jTableDetalle.getRowCount(); d++) {
                                m.setPrecio(Float.parseFloat(jTableDetalle.getValueAt(d, 1).toString()));
                                m.setIdinsumo(detallecompra.ObtenerIDInsumo(jTableDetalle.getValueAt(d, 0).toString()));
                                if (insumo.ActualizarPrecio(m)) {}
                            }
                            if (sql.SafeUpdates()) {
                                //actuizar conviene hacer por detalles si se maneja las unidades de medidas
                                //if (detallecompra.ActualizarStockInsumos()) {
                                    JOptionPane.showMessageDialog(null, "Compra Registrada");                                    
                                    //Generamos el Movimmiento de Caja de la Compra
                                    int idmovimiento = sql.obtenerUltimoId("compras", "idcompra");
                                    registrarMovimientoCaja(fecha,idmovimiento);
                                    volverPantallaListadoCompras();
                                //}
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debes generar al menos, una compra de insumo");
                    }                  
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
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

    private void jButtonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarActionPerformed
        int fila = jTableDetalle.getSelectedRow();
        if (jButtonBorrar.getText().equals("Borrar")) {
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
                    total = total - (Float.parseFloat(jTableDetalle.getValueAt(fila, 1).toString()) * Float.parseFloat(jTableDetalle.getValueAt(fila, 2).toString()));
                    jTextTotal.setText(Float.toString(total));
                    //jTextField1.setText(Float.toString((float) 0.00));
                    jButtonBorrar.setText("Borrar");
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
                                        ((JTextField) vCompras_Insumos.jDateFecha.getDateEditor().getUiComponent()).setText("");
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
                                        jButtonBorrar.setText("Borrar");
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
        String fecha = ((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText();
        filascompra = jTableDetalle.getRowCount();
            if (!jTextProveedor.getText().equals("") && !jCBUsuario.getSelectedItem().equals("(*) Seleccionar Usuario..") && !jTextTotal.getText().trim().equals("") && !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().equals("")) {
                if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
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
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
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
        } else if (!jTextProveedor.getText().equals("") || !jCBUsuario.getSelectedItem().equals("(*) Seleccionar Usuario..") || !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().equals("") || jTableDetalle.getRowCount() > 0 || !jTextInsumo.getText().equals("") || !jTextPrecio.getText().equals("") || !JtextCantidad.getText().equals("")) {
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

    private void jButtonAgregarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProvActionPerformed
        if (jTableProveedores.getRowCount() != 0) {
            int m = jTableProveedores.getSelectedRow();
            if (m == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarNombreComercialProv.dispose();
                jTextProveedor.setText(jTableProveedores.getValueAt(m, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos proveedores todavia");
        }
    }//GEN-LAST:event_jButtonAgregarProvActionPerformed

    private void jButtonCancelarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarProvActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextProveedor.setText("");
        }
    }//GEN-LAST:event_jButtonCancelarProvActionPerformed

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

    private void jButtonAgregarInsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarInsActionPerformed
        if (jTableInsumos.getRowCount() != 0) {
            int i = jTableInsumos.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarInsumo.dispose();
                jTextInsumo.setText(jTableInsumos.getValueAt(i, 0).toString());
                jTextPrecio.setText(jTableInsumos.getValueAt(i, 1).toString());
                JtextCantidad.setText(jTableInsumos.getValueAt(i, 2).toString());
            }
        }
    }//GEN-LAST:event_jButtonAgregarInsActionPerformed

    private void jButtonCancelarInsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarInsActionPerformed
        int j = JOptionPane.showConfirmDialog(null, "Esta Seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (j == 0) {
            vSeleccionarInsumo.dispose();
            jTextInsumo.setText("");
            jTextPrecio.setText("");
            JtextCantidad.setText("");
        } else {
            vSeleccionarInsumo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButtonCancelarInsActionPerformed

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
        if (jButtonModificar.getText().equals("Modificar") && jTableDetalle.getRowCount() != 0) {
            jTableDetalle.clearSelection();
            jTableDetalle.getSelectionModel().clearSelection();
            jButtonBorrar.setText("Borrar");
        }
    }//GEN-LAST:event_formMouseClicked

    private void vSeleccionarNombreComercialProvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvMouseClicked
        jTableProveedores.clearSelection();
        jTableProveedores.getSelectionModel().clearSelection();
        //MostrarProveedores();
        //jTextFieldProveedor.setText("");
    }//GEN-LAST:event_vSeleccionarNombreComercialProvMouseClicked

    private void vSeleccionarInsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoMouseClicked
        jTableInsumos.clearSelection();
        jTableInsumos.getSelectionModel().clearSelection();
        //MostrarInsumos();
        //jTextFieldInsumo.setText("");
    }//GEN-LAST:event_vSeleccionarInsumoMouseClicked

    private void jButtonBuscarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarProvActionPerformed
        if (!jTextFieldProveedor.getText().isEmpty()) {
            String[] columnas = {"NOMBRE COMERCIAL PROVEEDORES"};
            proveedores = compra.MostrarProveedorBuscado(jTextFieldProveedor.getText());
            if (proveedores.length != 0) {
                modelprov = new DefaultTableModel(proveedores, columnas);
                jTableProveedores.setModel(modelprov);
                EliminarFilasVaciasProveedores();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarProvActionPerformed

    private void jButtonBuscarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarInsumoActionPerformed
        if (!jTextFieldInsumo.getText().isEmpty()) {
            String[] columnas = {"INSUMOS", "PRECIO", "CANTIDAD"};
            insumos = detallecompra.MostrarInsumosBuscado(jTextFieldInsumo.getText());
            if (insumos.length != 0) {
                modelinsumo = new DefaultTableModel(insumos, columnas);
                jTableInsumos.setModel(modelinsumo);
                ocultarColumnasIns();
                EliminarFilasVaciasInsumos();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarInsumoActionPerformed

    private void jListProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListProveedoresMouseClicked
        int i = jListProveedores.getSelectedIndex();
        if (i != -1) {
            jTextProveedor.setText(jListProveedores.getSelectedValue());
            jListProveedores.setVisible(false);
        }
    }//GEN-LAST:event_jListProveedoresMouseClicked

    private void jTextProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextProveedorKeyReleased
        ListaProveedores();
    }//GEN-LAST:event_jTextProveedorKeyReleased

    private void jTextInsumoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextInsumoKeyReleased
        ListaInsumos();
    }//GEN-LAST:event_jTextInsumoKeyReleased

    private void jListInsumosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListInsumosMouseClicked
        int i = jListInsumos.getSelectedIndex();
        ArrayList<String> array;
        if (i != -1) {
            jTextInsumo.setText(jListInsumos.getSelectedValue());
            array = compra.ObtenerDatosNumInsumos(jTextInsumo.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    jTextPrecio.setText(array.get(j));
                    j++;
                    JtextCantidad.setText(array.get(j));
                    j++;
                    jListInsumos.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jListInsumosMouseClicked

    private void jTableDetalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetalleMousePressed
        //jTextField1.setText(String.valueOf(Float.parseFloat(modelo.getValueAt(vCompras_Insumos.jTable1.getSelectedRow(), 1).toString()) * Float.parseFloat(modelo.getValueAt(vCompras_Insumos.jTable1.getSelectedRow(), 2).toString())));
        if (jButtonSeleccionarInsumo.isEnabled()) {
            int i = jTableDetalle.getSelectedRow(), j = filasdetalle;
            for (int l = 0; l < j; l++) {
                if (i == l) {
                    jButtonBorrar.setText("Borrar Detalle");
                    break;
                }
            }
            if (i >= j) {
                jButtonBorrar.setText("Borrar");
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

    private void jTextFieldInsumoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInsumoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldInsumoKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:       
        int i = JOptionPane.showConfirmDialog(null, "Desea cancelar la Operación?", "Confirmar", JOptionPane.YES_NO_OPTION);
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
    public static javax.swing.JButton jButtonAgregar;
    public static javax.swing.JButton jButtonAgregarCompra;
    private javax.swing.JButton jButtonAgregarIns;
    private javax.swing.JButton jButtonAgregarProv;
    public static javax.swing.JButton jButtonBorrar;
    private javax.swing.JButton jButtonBuscarInsumo;
    private javax.swing.JButton jButtonBuscarProv;
    private javax.swing.JButton jButtonCancelarIns;
    private javax.swing.JButton jButtonCancelarProv;
    public static javax.swing.JButton jButtonModificar;
    public static javax.swing.JButton jButtonModificarCompra;
    private javax.swing.JButton jButtonSeleccionarInsumo;
    private javax.swing.JButton jButtonSeleccionarProv;
    public static javax.swing.JComboBox<String> jCBUsuario;
    public static com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jLabelCantidad;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelInsumo;
    private javax.swing.JLabel jLabelNombreInsumo;
    private javax.swing.JLabel jLabelNombreProveedor;
    private javax.swing.JLabel jLabelPrecio;
    private javax.swing.JLabel jLabelProveedor;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JList<String> jListInsumos;
    private javax.swing.JList<String> jListProveedores;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    public static javax.swing.JTable jTableDetalle;
    private javax.swing.JTable jTableInsumos;
    private javax.swing.JTable jTableProveedores;
    private javax.swing.JTextField jTextFieldInsumo;
    private javax.swing.JTextField jTextFieldProveedor;
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
