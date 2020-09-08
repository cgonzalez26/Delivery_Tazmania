package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Recetas;
import Controlador.control_existencias;
import Modelo.Recetas;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Recetas extends javax.swing.JInternalFrame {

    control_existencias existencia = new control_existencias();
    control_Recetas receta = new control_Recetas();
    Recetas r = new Recetas();
    Sentencias_sql sql = new Sentencias_sql();
    DefaultTableModel modelo1, modelo3;
    String id;
    ArrayList<String> listreceta;
    DefaultListModel list;
    vLista_Recetas tablarecetasregistradas = null;
    vGestion_Productos ventanaproductos = null;

    public vGestion_Recetas() {
        initComponents();
        MostrarTablaInsumos();
        MostrarInsumos();
    }

    public void MostrarInsumos() {
        String[] columnas = {"INSUMOS"};
        Object[][] datostabla = receta.MostrarInsumos();
        modelo3 = new DefaultTableModel(datostabla, columnas);
        jTableInsumos.setModel(modelo3);
        EliminarFilasVaciasInsumos();
    }

    public void ListaInsumos() {
        listreceta = existencia.list("insumos", "descripcion", jTextFieldInsumo.getText());
        String substr = jTextFieldInsumo.getText().toLowerCase();
        list = new DefaultListModel();
        jListInsumos.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listreceta.size(); i++) {
            if (listreceta.get(i) == null) {
                listreceta.remove(i);
            } else {
                String sublist = listreceta.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listreceta.get(i));
                    jListInsumos.setVisible(true);
                    if (jTextFieldInsumo.getText().isEmpty()) {
                        jListInsumos.setVisible(false);
                    }
                }
            }
        }
    }

    public void MostrarTablaInsumos() {
        modelo1 = new DefaultTableModel();
        modelo1.addColumn("INSUMOS A AGREGAR");
        jTableInsumosAElegir.setModel(modelo1);
    }

    public void AgregarNuevoInsumo() {
        if (!jTextFieldInsumo.getText().equals("") && VerificarDuplicados() == false) {
            String datos[] = {jTextFieldInsumo.getText()};
            modelo1.addRow(datos);
            jTextFieldInsumo.setText("");
        } else if (jTextFieldInsumo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
        } else if (VerificarDuplicados() == true) {
            JOptionPane.showMessageDialog(null, "Insumo ya agregado!");
        }
    }

    public boolean VerificarDuplicados() {
        boolean repetido = false;
        for (int i = 0; i < jTableInsumosAElegir.getRowCount(); i++) {
            repetido = jTableInsumosAElegir.getValueAt(i, 0).equals(jTextFieldInsumo.getText());
        }
        return repetido;
    }

    public void ModificarInsumoAgregado() {
        int fila = jTableInsumosAElegir.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            jTextFieldInsumo.setText(jTableInsumosAElegir.getValueAt(fila, 0).toString());
            modelo1.removeRow(fila);
        }
    }

    public void EliminarInsumo() {
        int fila = jTableInsumosAElegir.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            modelo1.removeRow(fila);
        }
    }

    public void EliminarFilasVaciasInsumos() {
        if (jTableInsumos.getRowCount() != 0) {
            int filas = jTableInsumos.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTableInsumos.getValueAt(fila, 0) == null) {
                    modelo3.removeRow(fila);
                }
            }
        }
    }

    public void VolverListadoRecetas() {
        tablarecetasregistradas = new vLista_Recetas();
        vMenuPrincipal.jDesktopPane1.add(tablarecetasregistradas);
        tablarecetasregistradas.setVisible(true);
        tablarecetasregistradas.setTitle("Listado de Recetas del Producto "+jLabelNombreProductoElegido.getText());
        vLista_Recetas.jLabelNombreProductoElegido.setText(jLabelNombreProductoElegido.getText());
        dispose();
    }

    public void VolverListadoProductos() {
        ventanaproductos = new vGestion_Productos();
        vMenuPrincipal.jDesktopPane1.add(ventanaproductos);
        ventanaproductos.setVisible(true);
        dispose();
    }

    public void LimpiarSeleccionInsumosaAgregar() {
        jTableInsumosAElegir.clearSelection();
        jTableInsumosAElegir.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarInsumo = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableInsumos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregar = new javax.swing.JButton();
        jButtonCancelarBuscarInsumo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelNombreInsumoBuscar = new javax.swing.JLabel();
        jTextFieldInsumoBuscar = new javax.swing.JTextField();
        jButtonBuscarInsumo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelNombreProducto = new javax.swing.JLabel();
        jLabelNombreProductoElegido = new javax.swing.JLabel();
        jLabelInsumo = new javax.swing.JLabel();
        jTextFieldInsumo = new javax.swing.JTextField();
        jListInsumos = new javax.swing.JList<>();
        jButtonSeleccionarInsumo = new javax.swing.JButton();
        jButtonEliminarInsumoElegido = new javax.swing.JButton();
        jButtonAgregarInsumoElegido = new javax.swing.JButton();
        jButtonModificarInsumosElegido = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInsumosAElegir = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonCancelar = new javax.swing.JButton();
        jButtonRegistrarReceta = new javax.swing.JButton();
        jButtonModificarReceta = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

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

        jTableInsumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jTableInsumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTableInsumos);

        jButtonAgregar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jButtonCancelarBuscarInsumo.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelarBuscarInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarBuscarInsumo.setText("Cancelar");
        jButtonCancelarBuscarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarBuscarInsumoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelNombreInsumoBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreInsumoBuscar.setText("Nombre Insumo");

        jTextFieldInsumoBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldInsumoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldInsumoBuscarKeyTyped(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jButtonBuscarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextFieldInsumoBuscar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabelNombreInsumoBuscar)
                        .addGap(0, 92, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNombreInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBuscarInsumo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vSeleccionarInsumoLayout = new javax.swing.GroupLayout(vSeleccionarInsumo.getContentPane());
        vSeleccionarInsumo.getContentPane().setLayout(vSeleccionarInsumoLayout);
        vSeleccionarInsumoLayout.setHorizontalGroup(
            vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addGroup(vSeleccionarInsumoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonCancelarBuscarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vSeleccionarInsumoLayout.setVerticalGroup(
            vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregar)
                    .addComponent(jButtonCancelarBuscarInsumo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Recetas");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(1067, 645));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNombreProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreProducto.setText("Nombre Producto:");
        jPanel1.add(jLabelNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 140, 30));

        jLabelNombreProductoElegido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jPanel1.add(jLabelNombreProductoElegido, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 180, 30));

        jLabelInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelInsumo.setText("(*) Seleccionar Insumo");
        jPanel1.add(jLabelInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, -1, 30));

        jTextFieldInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldInsumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldInsumoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldInsumoKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 219, 31));

        jListInsumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jListInsumos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListInsumos.setValueIsAdjusting(true);
        jListInsumos.setVisibleRowCount(0);
        jListInsumos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListInsumosMouseClicked(evt);
            }
        });
        jPanel1.add(jListInsumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 219, -1));

        jButtonSeleccionarInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarInsumoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSeleccionarInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 36, 31));

        jButtonEliminarInsumoElegido.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminarInsumoElegido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminarInsumoElegido.setText("Eliminar");
        jButtonEliminarInsumoElegido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarInsumoElegidoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonEliminarInsumoElegido, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, 100, 30));

        jButtonAgregarInsumoElegido.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarInsumoElegido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarInsumoElegido.setText("Agregar");
        jButtonAgregarInsumoElegido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarInsumoElegidoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAgregarInsumoElegido, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 100, 30));

        jButtonModificarInsumosElegido.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificarInsumosElegido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificarInsumosElegido.setText("Modificar");
        jButtonModificarInsumosElegido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarInsumosElegidoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonModificarInsumosElegido, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 100, 30));

        jTableInsumosAElegir.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableInsumosAElegir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4"
            }
        ));
        jScrollPane1.setViewportView(jTableInsumosAElegir);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 430));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 700, 460));

        jButtonCancelar.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 510, 100, 30));

        jButtonRegistrarReceta.setBackground(new java.awt.Color(252, 249, 57));
        jButtonRegistrarReceta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonRegistrarReceta.setText("Registrar Receta");
        jButtonRegistrarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarRecetaActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRegistrarReceta, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 120, 30));

        jButtonModificarReceta.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificarReceta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificarReceta.setText("Modificar Receta");
        jButtonModificarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarRecetaActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonModificarReceta, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 510, 130, 30));

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Lista Recetas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 510, 100, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (!jButtonRegistrarReceta.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListadoRecetas();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            if (jTableInsumosAElegir.getRowCount() != 0) {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    dispose();
                    VolverListadoProductos();
                } else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextFieldInsumoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInsumoKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldInsumoKeyTyped

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        if (jTableInsumos.getRowCount() != 0) {
            int i = jTableInsumos.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarInsumo.dispose();
                jTextFieldInsumo.setText(jTableInsumos.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos insumos todavia");
        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonCancelarBuscarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarBuscarInsumoActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarInsumo.dispose();
            jTextFieldInsumo.setText("");
        }
    }//GEN-LAST:event_jButtonCancelarBuscarInsumoActionPerformed

    private void vSeleccionarInsumoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarInsumo.dispose();
            jTextFieldInsumo.setText("");
        } else {
            vSeleccionarInsumo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarInsumoWindowClosing

    private void jButtonSeleccionarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarInsumoActionPerformed
        vSeleccionarInsumo.setSize(330, 612);
        vSeleccionarInsumo.setLocationRelativeTo(this);
        vSeleccionarInsumo.setModal(true);
        vSeleccionarInsumo.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarInsumoActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccionInsumosaAgregar();
    }//GEN-LAST:event_formMouseClicked

    private void vSeleccionarInsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoMouseClicked
        jTableInsumos.clearSelection();
        jTableInsumos.getSelectionModel().clearSelection();
    }//GEN-LAST:event_vSeleccionarInsumoMouseClicked

    private void jButtonBuscarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarInsumoActionPerformed
        if (!jTextFieldInsumoBuscar.getText().isEmpty()) {
            String[] columnas = {"INSUMOS"};
            Object[][] datostabla = receta.MostrarInsumoBuscado(jTextFieldInsumoBuscar.getText());
            if (datostabla.length != 0) {
                modelo3 = new DefaultTableModel(datostabla, columnas);
                jTableInsumos.setModel(modelo3);
                EliminarFilasVaciasInsumos();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarInsumoActionPerformed

    private void jTextFieldInsumoKeyReleased(java.awt.event.KeyEvent evt) {
        ListaInsumos();
    }

    private void jListInsumosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jListInsumos.getSelectedIndex();
        if (i != -1) {
            jTextFieldInsumo.setText(jListInsumos.getSelectedValue());
            jListInsumos.setVisible(false);
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTextFieldInsumoBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInsumoBuscarKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldInsumoBuscarKeyTyped

    private void jButtonModificarInsumosElegidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarInsumosElegidoActionPerformed
        ModificarInsumoAgregado();
    }//GEN-LAST:event_jButtonModificarInsumosElegidoActionPerformed

    private void jButtonAgregarInsumoElegidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarInsumoElegidoActionPerformed
        AgregarNuevoInsumo();
    }//GEN-LAST:event_jButtonAgregarInsumoElegidoActionPerformed

    private void jButtonEliminarInsumoElegidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarInsumoElegidoActionPerformed
        EliminarInsumo();
    }//GEN-LAST:event_jButtonEliminarInsumoElegidoActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        if (!jButtonRegistrarReceta.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListadoRecetas();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            if (jTableInsumosAElegir.getRowCount() != 0) {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    dispose();
                    VolverListadoProductos();
                } else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonModificarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarRecetaActionPerformed
        if (jTableInsumosAElegir.getRowCount() != 0) {
            int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                for (int j = 0; j < jTableInsumosAElegir.getRowCount(); j++) {
                    r.setIdinsumo(receta.ObtenerIDInsumo(jTableInsumosAElegir.getValueAt(j, 0).toString()));
                    r.setIdreceta(Integer.parseInt(id));
                    receta.EditarReceta(r);
                }
                JOptionPane.showMessageDialog(null, "Receta Modificada");
                VolverListadoRecetas();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes agregar al menos un insumo en la tabla Insumos a Agregar");
        }
    }//GEN-LAST:event_jButtonModificarRecetaActionPerformed

    private void jButtonRegistrarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarRecetaActionPerformed
        if (jTableInsumosAElegir.getRowCount() != 0) {
            if (receta.InsertarRecetas()) {
                JOptionPane.showMessageDialog(null, "Receta Registrada");
                VolverListadoRecetas();
            } else {
                JOptionPane.showMessageDialog(null, "No agregado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes agregar al menos un insumo en la tabla Insumos a Agregar");
        }
    }//GEN-LAST:event_jButtonRegistrarRecetaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        VolverListadoRecetas();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonAgregarInsumoElegido;
    private javax.swing.JButton jButtonBuscarInsumo;
    public static javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonCancelarBuscarInsumo;
    public static javax.swing.JButton jButtonEliminarInsumoElegido;
    private javax.swing.JButton jButtonModificarInsumosElegido;
    public static javax.swing.JButton jButtonModificarReceta;
    public static javax.swing.JButton jButtonRegistrarReceta;
    private javax.swing.JButton jButtonSeleccionarInsumo;
    private javax.swing.JLabel jLabelInsumo;
    private javax.swing.JLabel jLabelNombreInsumoBuscar;
    private javax.swing.JLabel jLabelNombreProducto;
    public static javax.swing.JLabel jLabelNombreProductoElegido;
    private javax.swing.JList<String> jListInsumos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableInsumos;
    public static javax.swing.JTable jTableInsumosAElegir;
    public static javax.swing.JTextField jTextFieldInsumo;
    private javax.swing.JTextField jTextFieldInsumoBuscar;
    private javax.swing.JDialog vSeleccionarInsumo;
    // End of variables declaration//GEN-END:variables
}
