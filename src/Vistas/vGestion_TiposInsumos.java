package Vistas;

import Controlador.control_TiposInsumos;
import Modelo.TiposInsumos;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_TiposInsumos extends javax.swing.JInternalFrame {

    TiposInsumos ti = new TiposInsumos();
    control_TiposInsumos tipoinsumo = new control_TiposInsumos();
    Object[][] datostabla;
    String id;
    DefaultTableModel datos;

    public vGestion_TiposInsumos() {
        initComponents();
        MostrarDatos();

        jTabla_TiposInsumos.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (e.getClickCount() == 2) {
                    jBotonAgregar_TipoInsumo.setEnabled(false);
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    int fila = jTabla_TiposInsumos.rowAtPoint(e.getPoint());
                    id = (jTabla_TiposInsumos.getValueAt(fila, 0).toString());
                    jTextDescripcion_TipoInsumo.setText(jTabla_TiposInsumos.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTabla_TiposInsumos.clearSelection();
        jTabla_TiposInsumos.getSelectionModel().clearSelection();
    }

    public void MostrarDatos() {
        String[] columnas = {"ID TIPOINSUMO", "DESCRIPCION"};
        datostabla = tipoinsumo.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_TiposInsumos.setModel(datos);
        EliminarFilasVacias();
        ocultar_columnas();
    }

    public void ocultar_columnas() {
        jTabla_TiposInsumos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabla_TiposInsumos.getColumnModel().getColumn(0).setMinWidth(0);
        jTabla_TiposInsumos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void EliminarFilasVacias() {
        if (jTabla_TiposInsumos.getRowCount() != 0) {
            for (int columna = 0; columna < jTabla_TiposInsumos.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTabla_TiposInsumos.getRowCount(); fila++) {
                    if (jTabla_TiposInsumos.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEtiqNombre_TipoInsumo = new javax.swing.JLabel();
        jTextDescripcion_TipoInsumo = new javax.swing.JTextField();
        jBotonAgregar_TipoInsumo = new javax.swing.JButton();
        jBotonEliminar_TipoInsumo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_TiposInsumos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jBotonModif_TipoInsumo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelTipoInsumo = new javax.swing.JLabel();
        jTextFieldTipoInsumoBuscar = new javax.swing.JTextField();
        jButtonBuscarTipoInsumo = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Tipos Insumos");
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

        jEtiqNombre_TipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqNombre_TipoInsumo.setText("(*) Tipo de Insumo:");

        jTextDescripcion_TipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextDescripcion_TipoInsumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDescripcion_TipoInsumoKeyTyped(evt);
            }
        });

        jBotonAgregar_TipoInsumo.setBackground(new java.awt.Color(252, 249, 57));
        jBotonAgregar_TipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgregar_TipoInsumo.setText("Agregar");
        jBotonAgregar_TipoInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgregar_TipoInsumoActionPerformed(evt);
            }
        });

        jBotonEliminar_TipoInsumo.setBackground(new java.awt.Color(252, 249, 57));
        jBotonEliminar_TipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonEliminar_TipoInsumo.setText("Eliminar");
        jBotonEliminar_TipoInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonEliminar_TipoInsumoActionPerformed(evt);
            }
        });

        jTabla_TiposInsumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTabla_TiposInsumos.setModel(new javax.swing.table.DefaultTableModel(
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
        jTabla_TiposInsumos.setMinimumSize(new java.awt.Dimension(300, 65));
        jScrollPane1.setViewportView(jTabla_TiposInsumos);

        jBotonModif_TipoInsumo.setBackground(new java.awt.Color(252, 249, 57));
        jBotonModif_TipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModif_TipoInsumo.setText("Modificar");
        jBotonModif_TipoInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModif_TipoInsumoActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelTipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTipoInsumo.setText("Nombre Tipo");

        jTextFieldTipoInsumoBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldTipoInsumoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTipoInsumoBuscarKeyTyped(evt);
            }
        });

        jButtonBuscarTipoInsumo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarTipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarTipoInsumo.setText("Buscar");
        jButtonBuscarTipoInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarTipoInsumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabelTipoInsumo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jButtonBuscarTipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jTextFieldTipoInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTipoInsumo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTipoInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscarTipoInsumo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonCancelar.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBotonAgregar_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBotonModif_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(jBotonEliminar_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jEtiqNombre_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDescripcion_TipoInsumo))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jEtiqNombre_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextDescripcion_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBotonAgregar_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jBotonModif_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jBotonEliminar_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgregar_TipoInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgregar_TipoInsumoActionPerformed
        if (!jTextDescripcion_TipoInsumo.getText().trim().equals("")) {
            ti.setDescripcion(jTextDescripcion_TipoInsumo.getText());
            if (tipoinsumo.InsertarTiposInsumos(ti)) {
                JOptionPane.showMessageDialog(null, "Nuevo tipo de insumo agregado");
                MostrarDatos();
                jTextDescripcion_TipoInsumo.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
        }
    }//GEN-LAST:event_jBotonAgregar_TipoInsumoActionPerformed

    private void jBotonEliminar_TipoInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonEliminar_TipoInsumoActionPerformed
        int seleccionado = jTabla_TiposInsumos.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Eliminar Tipo de Insumo?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                ti.setIdtipoinsumo(Integer.parseInt(jTabla_TiposInsumos.getValueAt(seleccionado, 0).toString()));
                if (tipoinsumo.EliminarTiposInsumos(ti)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    MostrarDatos();
                    EliminarFilasVacias();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jBotonEliminar_TipoInsumoActionPerformed

    private void jBotonModif_TipoInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModif_TipoInsumoActionPerformed
        int s = jTabla_TiposInsumos.getSelectedRow();
        if (s == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            jBotonAgregar_TipoInsumo.setEnabled(false);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            id = (jTabla_TiposInsumos.getValueAt(s, 0).toString());
            jTextDescripcion_TipoInsumo.setText(jTabla_TiposInsumos.getValueAt(s, 1).toString());
        }

        if (!jBotonAgregar_TipoInsumo.isEnabled()) {
            if (!jTextDescripcion_TipoInsumo.getText().trim().equals("")) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    ti.setDescripcion(jTextDescripcion_TipoInsumo.getText());
                    ti.setIdtipoinsumo(Integer.parseInt(id));
                    if (tipoinsumo.EditarTiposInsumos(ti)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        jBotonAgregar_TipoInsumo.setEnabled(true);
                        jTextDescripcion_TipoInsumo.setText("");
                        LimpiarSeleccion();
                        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        MostrarDatos();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
            }
        }
    }//GEN-LAST:event_jBotonModif_TipoInsumoActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (!jBotonAgregar_TipoInsumo.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextDescripcion_TipoInsumo.getText().trim().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        jTabla_TiposInsumos.clearSelection();
        jTabla_TiposInsumos.getSelectionModel().clearSelection();
        //MostrarDatos();
        //jTextFieldTipoInsumoBuscar.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarTipoInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarTipoInsumoActionPerformed
        if (!jTextFieldTipoInsumoBuscar.getText().isEmpty()) {
            String[] columnas = {"ID TIPOINSUMO", "DESCRIPCION"};
            datostabla = tipoinsumo.MostrarDatosBuscado(jTextFieldTipoInsumoBuscar.getText());
            if (datostabla.length != 0) {
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_TiposInsumos.setModel(datos);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se han encontrado datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarTipoInsumoActionPerformed

    private void jTextDescripcion_TipoInsumoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDescripcion_TipoInsumoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextDescripcion_TipoInsumoKeyTyped

    private void jTextFieldTipoInsumoBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTipoInsumoBuscarKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldTipoInsumoBuscarKeyTyped

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            jBotonAgregar_TipoInsumo.setEnabled(true);
            jTextDescripcion_TipoInsumo.setText("");
            LimpiarSeleccion();
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonAgregar_TipoInsumo;
    private javax.swing.JButton jBotonEliminar_TipoInsumo;
    private javax.swing.JButton jBotonModif_TipoInsumo;
    private javax.swing.JButton jButtonBuscarTipoInsumo;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JLabel jEtiqNombre_TipoInsumo;
    private javax.swing.JLabel jLabelTipoInsumo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_TiposInsumos;
    public static javax.swing.JTextField jTextDescripcion_TipoInsumo;
    private javax.swing.JTextField jTextFieldTipoInsumoBuscar;
    // End of variables declaration//GEN-END:variables
}
