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
    DefaultTableModel modelprov;
    vLista_Insumos lista = null;
    String id;
    control_Insumos insumo = new control_Insumos();
    DefaultListModel list;
    ArrayList<String> listprov;

    public vGestion_Insumos() {
        initComponents();
        ComboTipoInsumo();
        MostrarProveedores();
        EliminarItemsVacios();
        jBotonModif_Insumos.setEnabled(false);
        jList2.setVisible(false);
    }

    public void MostrarProveedores() {
        String[] columnas = {"NOMBRE COMERCIAL PROVEEDOR"};
        Object[][] datos = insumo.MostrarProveedores();
        modelprov = new DefaultTableModel(datos, columnas);
        jTable1.setModel(modelprov);
        EliminarFilasVaciasProveedores();
        //AjustarTamañoFilasProveedores();
    }

    public void ListaProveedores() {
        listprov = combo.list("proveedores", "Nombre_comercial", jTextField1.getText());
        String substr = jTextField1.getText().toLowerCase();
        list = new DefaultListModel();
        jList2.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listprov.size(); i++) {
            if (listprov.get(i) == null) {
                listprov.remove(i);
            } else {
                String sublist = listprov.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listprov.get(i));
                    jList2.setVisible(true);
                    if (jTextField1.getText().isEmpty()) {
                        jList2.setVisible(false);
                    }
                }
            }
        }
    }

    public void AjustarTamañoFilasProveedores() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nomprov = (int) font.getStringBounds(jTable1.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nomprov > jTable1.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(0).setPreferredWidth(nomprov);
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

    public void EliminarItemsVacios() {
        for (int i = 0; i < jCBTipo_Insumos.getItemCount(); i++) {
            if (jCBTipo_Insumos.getItemAt(i) == null) {
                jCBTipo_Insumos.removeItemAt(i);
            }
        }
    }

    public void EliminarFilasVaciasProveedores() {
        if (jTable1.getRowCount() != 0) {
            int filas = jTable1.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable1.getValueAt(fila, 0) == null) {
                    modelprov.removeRow(fila);
                }
            }
        }
    }

    public void Limpiar() {
        jTextField1.setText("");
        jTextDesc_Insumos.setText("");
        jTextPrecio_Insumos.setText("");
        jTextStock_Insumos.setText("");
        jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..");
    }

    public void Cerrar() {
        if (!jTextField1.getText().equals("") || !jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..") || !jTextDesc_Insumos.getText().trim().isEmpty()) {
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
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jList2 = new javax.swing.JList<>();
        jEtiqDesc_Insumos = new javax.swing.JLabel();
        jEtiqPrecio_Insumos = new javax.swing.JLabel();
        jTextPrecio_Insumos = new javax.swing.JTextField();
        jEtiqStock_Insumos = new javax.swing.JLabel();
        jTextStock_Insumos = new javax.swing.JTextField();
        jCBTipo_Insumos = new javax.swing.JComboBox<>();
        jBotonAgregar_Insumos = new javax.swing.JButton();
        jBotonModif_Insumos = new javax.swing.JButton();
        jTextDesc_Insumos = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();

        vSeleccionarNombreComercialProv.setTitle("Seleccionar Nombre Comercial Proveedor");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
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

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
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
        jScrollPane1.setViewportView(jTable1);

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("Nombre Proveedor");

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
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
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2)
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
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(vSeleccionarNombreComercialProvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(vSeleccionarNombreComercialProvLayout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Insumos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
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
        jLabel3.setText("(*) Seleccionar Proveedor");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, -2, -1, 30));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 291, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 43, 30));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        getContentPane().add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 291, -1));

        jEtiqDesc_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqDesc_Insumos.setText("(*) Descripción ");
        getContentPane().add(jEtiqDesc_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 94, 20));

        jEtiqPrecio_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqPrecio_Insumos.setText("Precio");
        getContentPane().add(jEtiqPrecio_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, -1, -1));

        jTextPrecio_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextPrecio_Insumos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPrecio_InsumosKeyTyped(evt);
            }
        });
        getContentPane().add(jTextPrecio_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, 170, 32));

        jEtiqStock_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos.setText("Stock");
        getContentPane().add(jEtiqStock_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, -1));

        jTextStock_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextStock_Insumos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextStock_InsumosKeyTyped(evt);
            }
        });
        getContentPane().add(jTextStock_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 168, 32));

        jCBTipo_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCBTipo_Insumos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccionar Tipo Insumo.." }));
        getContentPane().add(jCBTipo_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 198, 30));

        jBotonAgregar_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgregar_Insumos.setText("Agregar");
        jBotonAgregar_Insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgregar_InsumosActionPerformed(evt);
            }
        });
        getContentPane().add(jBotonAgregar_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 91, -1));

        jBotonModif_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModif_Insumos.setText("Modificar");
        jBotonModif_Insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModif_InsumosActionPerformed(evt);
            }
        });
        getContentPane().add(jBotonModif_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 91, -1));

        jTextDesc_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextDesc_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 265, 32));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Fecha");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 60, -1));

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 156, 33));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgregar_InsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgregar_InsumosActionPerformed
        if (jBotonAgregar_Insumos.getText().equals("Agregar")) {
            if (!jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..") && !jTextField1.getText().equals("") && !jTextDesc_Insumos.getText().trim().equals("")) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    I.setIdtipoinsumo(insumo.ObtenerIDTipoInsumo((String) jCBTipo_Insumos.getSelectedItem()));
                    I.setIdproveedor(insumo.ObtenerIDProveedor((jTextField1.getText())));
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
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto");
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
        if (!jCBTipo_Insumos.getSelectedItem().equals("(*) Seleccionar Tipo Insumo..") && !jTextField1.getText().equals("") && !jTextDesc_Insumos.getText().trim().equals("")) {
            if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    I.setIdtipoinsumo(insumo.ObtenerIDTipoInsumo((String) jCBTipo_Insumos.getSelectedItem()));
                    I.setIdproveedor(insumo.ObtenerIDProveedor(jTextField1.getText()));
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
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto");
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTable1.getRowCount() != 0) {
            int i = jTable1.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarNombreComercialProv.dispose();
                jTextField1.setText(jTable1.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos proveedores todavia");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void vSeleccionarNombreComercialProvWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarNombreComercialProv.dispose();
            jTextField1.setText("");
        } else {
            vSeleccionarNombreComercialProv.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarNombreComercialProvWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        vSeleccionarNombreComercialProv.setSize(330, 645);
        vSeleccionarNombreComercialProv.setLocationRelativeTo(this);
        vSeleccionarNombreComercialProv.setModal(true);
        vSeleccionarNombreComercialProv.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void vSeleccionarNombreComercialProvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarNombreComercialProvMouseClicked
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
        MostrarProveedores();
        jTextField2.setText("");
    }//GEN-LAST:event_vSeleccionarNombreComercialProvMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!jTextField2.getText().isEmpty()) {
            String[] columnas = {"NOMBRE COMERCIAL PROVEEDOR"};
            Object[][] datos = insumo.MostrarProveedorBuscado(jTextField2.getText());
            if (datos.length != 0) {
                modelprov = new DefaultTableModel(datos, columnas);
                jTable1.setModel(modelprov);
                EliminarFilasVaciasProveedores();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        if (i != -1) {
            jTextField1.setText(jList2.getSelectedValue());
            jList2.setVisible(false);
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        ListaProveedores();
    }//GEN-LAST:event_jTextField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonAgregar_Insumos;
    public static javax.swing.JButton jBotonModif_Insumos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    public static javax.swing.JComboBox<String> jCBTipo_Insumos;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jEtiqDesc_Insumos;
    private javax.swing.JLabel jEtiqPrecio_Insumos;
    private javax.swing.JLabel jEtiqStock_Insumos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTextField jTextDesc_Insumos;
    public static javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    public static javax.swing.JTextField jTextPrecio_Insumos;
    public static javax.swing.JTextField jTextStock_Insumos;
    private javax.swing.JDialog vSeleccionarNombreComercialProv;
    // End of variables declaration//GEN-END:variables
}
