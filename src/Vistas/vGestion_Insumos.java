package Vistas;

import Controlador.control_Insumos;

import Controlador.control_existencias;
import Modelo.Insumos;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Insumos extends javax.swing.JInternalFrame {

    Insumos I = new Insumos();
    control_existencias combo = new control_existencias();
    Object[] tipoinsumo;
    Object[] unidadmedida;
    DefaultTableModel modelprov;
    vLista_Insumos lista = null;
    String id;
    control_Insumos insumo = new control_Insumos();
    DefaultListModel list;
    ArrayList<String> listprov;

    public vGestion_Insumos() {
        initComponents();
        ComboTipoInsumo();
        ComboUnidadMedida();
        MostrarProveedores();
        EliminarItemsVacios();
        jBotonModif_Insumos.setEnabled(false);
        jListProveedores.setVisible(false);
    }

    public void MostrarProveedores() {
        String[] columnas = {"NOMBRE COMERCIAL PROVEEDOR"};
        Object[][] datos = insumo.MostrarProveedores();
        modelprov = new DefaultTableModel(datos, columnas);
        jTableProveedores.setModel(modelprov);
        EliminarFilasVaciasProveedores();
        //AjustarTamañoFilasProveedores();
    }

    public void ListaProveedores() {
        listprov = combo.list("proveedores", "Nombre_comercial", jTextFieldProveedor.getText());
        String substr = jTextFieldProveedor.getText().toLowerCase();
        list = new DefaultListModel();
        jListProveedores.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listprov.size(); i++) {
            if (listprov.get(i) == null) {
                listprov.remove(i);
            } else {
                String sublist = listprov.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listprov.get(i));
                    jListProveedores.setVisible(true);
                    if (jTextFieldProveedor.getText().isEmpty()) {
                        jListProveedores.setVisible(false);
                    }
                }
            }
        }
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

    public void ComboTipoInsumo() {
        tipoinsumo = combo.combox("tiposinsumos", "descripcion");
        for (Object tipoinsumo1 : tipoinsumo) {
            jCBTipo_Insumos.addItem((String) tipoinsumo1);
        }
    }
    
    public void ComboUnidadMedida() {
        unidadmedida = combo.combox("unidadesmedidas", "descripcion");
        for (Object um : unidadmedida) {
            jCBUnidad_Medida.addItem((String) um);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jCBTipo_Insumos.getItemCount(); i++) {
            if (jCBTipo_Insumos.getItemAt(i) == null) {
                jCBTipo_Insumos.removeItemAt(i);
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

    public void Limpiar() {
        jTextFieldProveedor.setText("");
        jTextDesc_Insumos.setText("");
        jTextPrecio_Insumos.setText("");
        jTextStock_Insumos.setText("");
        jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..");
    }

    public void Cerrar() {
        if (!jTextFieldProveedor.getText().equals("") || !jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..") || !jTextDesc_Insumos.getText().trim().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarNombreComercialProv = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProveedores = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregarProv = new javax.swing.JButton();
        jButtonCancelarProv = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelNomProveedor = new javax.swing.JLabel();
        jTextFieldNomProvedor = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldProveedor = new javax.swing.JTextField();
        jButtonSeleccionarProveedor = new javax.swing.JButton();
        jListProveedores = new javax.swing.JList<>();
        jEtiqDesc_Insumos = new javax.swing.JLabel();
        jEtiqPrecio_Insumos = new javax.swing.JLabel();
        jTextPrecio_Insumos = new javax.swing.JTextField();
        jEtiqStock_Insumos = new javax.swing.JLabel();
        jTextStock_Insumos = new javax.swing.JTextField();
        jCBTipo_Insumos = new javax.swing.JComboBox<>();
        jBotonAgregar_Insumos = new javax.swing.JButton();
        jBotonModif_Insumos = new javax.swing.JButton();
        jTextDesc_Insumos = new javax.swing.JTextField();
        jLabelProveedor = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCBUnidad_Medida = new javax.swing.JComboBox<>();
        jBotonCancelar = new javax.swing.JButton();

        vSeleccionarNombreComercialProv.setTitle("Seleccionar Nombre Comercial Proveedor");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vSeleccionarNombreComercialProv.setIconImage(icono);
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

        jTableProveedores.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jTableProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableProveedores);

        jButtonAgregarProv.setBackground(new java.awt.Color(252, 240, 0));
        jButtonAgregarProv.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarProv.setText("Agregar");
        jButtonAgregarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProvActionPerformed(evt);
            }
        });

        jButtonCancelarProv.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelarProv.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarProv.setText("Cancelar");
        jButtonCancelarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarProvActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelNomProveedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNomProveedor.setText("Nombre Proveedor");

        jTextFieldNomProvedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton4.setBackground(new java.awt.Color(252, 240, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabelNomProveedor)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldNomProvedor)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNomProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vSeleccionarNombreComercialProvLayout = new javax.swing.GroupLayout(vSeleccionarNombreComercialProv.getContentPane());
        vSeleccionarNombreComercialProv.getContentPane().setLayout(vSeleccionarNombreComercialProvLayout);
        vSeleccionarNombreComercialProvLayout.setHorizontalGroup(
            vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButtonAgregarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonCancelarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addContainerGap())
        );
        vSeleccionarNombreComercialProvLayout.setVerticalGroup(
            vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregarProv)
                    .addComponent(jButtonCancelarProv))
                .addContainerGap())
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Insumos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(708, 325));
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Unidad de Medida:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, -1, 30));

        jTextFieldProveedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProveedorKeyReleased(evt);
            }
        });
        getContentPane().add(jTextFieldProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 270, 30));

        jButtonSeleccionarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarProveedorActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSeleccionarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 43, 30));

        jListProveedores.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jListProveedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListProveedores.setValueIsAdjusting(true);
        jListProveedores.setVisibleRowCount(0);
        jListProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListProveedoresMouseClicked(evt);
            }
        });
        getContentPane().add(jListProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 270, 0));

        jEtiqDesc_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqDesc_Insumos.setText("(*) Descripción:");
        getContentPane().add(jEtiqDesc_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 94, 20));

        jEtiqPrecio_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqPrecio_Insumos.setText("Precio:");
        getContentPane().add(jEtiqPrecio_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jTextPrecio_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextPrecio_Insumos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPrecio_InsumosKeyTyped(evt);
            }
        });
        getContentPane().add(jTextPrecio_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 200, 30));

        jEtiqStock_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos.setText("Stock:");
        getContentPane().add(jEtiqStock_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, -1, 20));

        jTextStock_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextStock_Insumos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextStock_InsumosKeyTyped(evt);
            }
        });
        getContentPane().add(jTextStock_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 200, 30));

        jCBTipo_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCBTipo_Insumos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo..." }));
        getContentPane().add(jCBTipo_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 200, 30));

        jBotonAgregar_Insumos.setBackground(new java.awt.Color(252, 240, 0));
        jBotonAgregar_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgregar_Insumos.setText("Agregar");
        jBotonAgregar_Insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgregar_InsumosActionPerformed(evt);
            }
        });
        getContentPane().add(jBotonAgregar_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 91, -1));

        jBotonModif_Insumos.setBackground(new java.awt.Color(252, 240, 0));
        jBotonModif_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModif_Insumos.setText("Modificar");
        jBotonModif_Insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModif_InsumosActionPerformed(evt);
            }
        });
        getContentPane().add(jBotonModif_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 91, -1));

        jTextDesc_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextDesc_Insumos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDesc_InsumosKeyTyped(evt);
            }
        });
        getContentPane().add(jTextDesc_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 270, 30));

        jLabelProveedor.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelProveedor.setText("(*) Proveedor:");
        getContentPane().add(jLabelProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("(*) Tipo de Insumo:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 30));

        jCBUnidad_Medida.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCBUnidad_Medida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Unidad..." }));
        getContentPane().add(jCBUnidad_Medida, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 200, 30));

        jBotonCancelar.setBackground(new java.awt.Color(237, 124, 61));
        jBotonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonCancelar.setText("Cancelar");
        jBotonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jBotonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 91, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgregar_InsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgregar_InsumosActionPerformed
        if (jBotonAgregar_Insumos.getText().equals("Agregar")) {
            if (!jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..") && !jTextFieldProveedor.getText().equals("") && !jTextDesc_Insumos.getText().trim().equals("")) {               
                I.setIdtipoinsumo(insumo.ObtenerIDTipoInsumo((String) jCBTipo_Insumos.getSelectedItem()));
                I.setIdunidadmedida(insumo.ObtenerIDUnidadMedida((String) jCBUnidad_Medida.getSelectedItem()));
                I.setIdproveedor(insumo.ObtenerIDProveedor((jTextFieldProveedor.getText())));
                I.setDescripcion(jTextDesc_Insumos.getText());
                if (jTextPrecio_Insumos.getText().trim().length() == 0) {
                    I.setPrecio((float) 0.00);
                } else {
                    I.setPrecio(Float.parseFloat(jTextPrecio_Insumos.getText()));
                }
                if (jTextStock_Insumos.getText().trim().length() == 0) {
                    I.setStock((float) 0.00);
                } else {
                    I.setStock(Float.parseFloat(jTextStock_Insumos.getText()));
                }
                if (insumo.InsertarInsumos(I)) {
                    JOptionPane.showMessageDialog(null, "Nuevo Insumo agregado");
                    lista = new vLista_Insumos();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    this.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Insumos();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                this.dispose();
            }
        }

    }//GEN-LAST:event_jBotonAgregar_InsumosActionPerformed

    private void jBotonModif_InsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModif_InsumosActionPerformed
        if (!jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..") && !jTextFieldProveedor.getText().equals("") && !jTextDesc_Insumos.getText().trim().equals("")) {
           
            int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                I.setIdtipoinsumo(insumo.ObtenerIDTipoInsumo((String) jCBTipo_Insumos.getSelectedItem()));
                I.setIdproveedor(insumo.ObtenerIDProveedor(jTextFieldProveedor.getText()));
                I.setDescripcion(jTextDesc_Insumos.getText());
                I.setIdunidadmedida(insumo.ObtenerIDUnidadMedida((String) jCBUnidad_Medida.getSelectedItem()));
                if (jTextPrecio_Insumos.getText().trim().length() == 0) {
                    I.setPrecio((float) 0.00);
                } else {
                    I.setPrecio(Float.parseFloat(jTextPrecio_Insumos.getText()));
                }
                if (jTextStock_Insumos.getText().trim().length() == 0) {
                    I.setStock((float) 0.00);
                } else {
                    I.setStock(Float.parseFloat(jTextStock_Insumos.getText()));
                }
                I.setIdinsumo(Integer.parseInt(id));

                if (insumo.EditarInsumos(I)) {
                    JOptionPane.showMessageDialog(null, "Modificado");
                    jBotonModif_Insumos.setEnabled(false);
                    jBotonAgregar_Insumos.setEnabled(true);
                    lista = new vLista_Insumos();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    this.dispose();
                }
            }            
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jBotonModif_InsumosActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jBotonAgregar_Insumos.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Insumos();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                this.dispose();
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextStock_InsumosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextStock_InsumosKeyTyped
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
    }//GEN-LAST:event_jTextStock_InsumosKeyTyped

    private void jTextPrecio_InsumosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPrecio_InsumosKeyTyped
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
    }//GEN-LAST:event_jTextPrecio_InsumosKeyTyped

    private void jButtonCancelarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarProvActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextFieldProveedor.setText("");
        }
    }//GEN-LAST:event_jButtonCancelarProvActionPerformed

    private void jButtonAgregarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProvActionPerformed
        if (jTableProveedores.getRowCount() != 0) {
            int i = jTableProveedores.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarNombreComercialProv.dispose();
                jTextFieldProveedor.setText(jTableProveedores.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos proveedores todavia");
        }
    }//GEN-LAST:event_jButtonAgregarProvActionPerformed

    private void vSeleccionarNombreComercialProvWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextFieldProveedor.setText("");
        } else {
            vSeleccionarNombreComercialProv.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarNombreComercialProvWindowClosing

    private void jButtonSeleccionarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarProveedorActionPerformed
        vSeleccionarNombreComercialProv.setSize(330, 645);
        vSeleccionarNombreComercialProv.setLocationRelativeTo(this);
        vSeleccionarNombreComercialProv.setModal(true);
        vSeleccionarNombreComercialProv.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarProveedorActionPerformed

    private void vSeleccionarNombreComercialProvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvMouseClicked
        jTableProveedores.clearSelection();
        jTableProveedores.getSelectionModel().clearSelection();
        //MostrarProveedores();
        //jTextFieldNomProvedor.setText("");
    }//GEN-LAST:event_vSeleccionarNombreComercialProvMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!jTextFieldNomProvedor.getText().isEmpty()) {
            String[] columnas = {"NOMBRE COMERCIAL PROVEEDOR"};
            Object[][] datos = insumo.MostrarProveedorBuscado(jTextFieldNomProvedor.getText());
            if (datos.length != 0) {
                modelprov = new DefaultTableModel(datos, columnas);
                jTableProveedores.setModel(modelprov);
                EliminarFilasVaciasProveedores();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jListProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListProveedoresMouseClicked
        int i = jListProveedores.getSelectedIndex();
        if (i != -1) {
            jTextFieldProveedor.setText(jListProveedores.getSelectedValue());
            jListProveedores.setVisible(false);
        }
    }//GEN-LAST:event_jListProveedoresMouseClicked

    private void jTextFieldProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProveedorKeyReleased
        ListaProveedores();
    }//GEN-LAST:event_jTextFieldProveedorKeyReleased

    private void jTextDesc_InsumosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDesc_InsumosKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextDesc_InsumosKeyTyped

    private void jBotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonCancelarActionPerformed
        // TODO add your handling code here:
        //this.dispose();
        lista = new vLista_Insumos();
        vMenuPrincipal.jDesktopPane1.add(lista);
        lista.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jBotonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonAgregar_Insumos;
    public static javax.swing.JButton jBotonCancelar;
    public static javax.swing.JButton jBotonModif_Insumos;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonAgregarProv;
    private javax.swing.JButton jButtonCancelarProv;
    private javax.swing.JButton jButtonSeleccionarProveedor;
    public static javax.swing.JComboBox<String> jCBTipo_Insumos;
    public static javax.swing.JComboBox<String> jCBUnidad_Medida;
    private javax.swing.JLabel jEtiqDesc_Insumos;
    private javax.swing.JLabel jEtiqPrecio_Insumos;
    private javax.swing.JLabel jEtiqStock_Insumos;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelNomProveedor;
    private javax.swing.JLabel jLabelProveedor;
    private javax.swing.JList<String> jListProveedores;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProveedores;
    public static javax.swing.JTextField jTextDesc_Insumos;
    private javax.swing.JTextField jTextFieldNomProvedor;
    public static javax.swing.JTextField jTextFieldProveedor;
    public static javax.swing.JTextField jTextPrecio_Insumos;
    public static javax.swing.JTextField jTextStock_Insumos;
    private javax.swing.JDialog vSeleccionarNombreComercialProv;
    // End of variables declaration//GEN-END:variables
}
