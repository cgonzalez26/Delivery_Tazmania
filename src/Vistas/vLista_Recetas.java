package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Recetas;
import Modelo.Recetas;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Recetas extends javax.swing.JInternalFrame {

    vGestion_Recetas ventanareceta = null;
    control_Recetas receta = new control_Recetas();
    Sentencias_sql sql = new Sentencias_sql();
    Recetas r = new Recetas();
    String id;
    DefaultTableModel modelo2;

    public vLista_Recetas() {
        initComponents();
        MostrarRecetas();

        jTableInsumosRegistrados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int j = jTableInsumosRegistrados.rowAtPoint(e.getPoint());
                    if (ventanareceta == null || ventanareceta.isClosed()) {
                        ventanareceta = new vGestion_Recetas();
                        vMenuPrincipal.jDesktopPane1.add(ventanareceta);
                        ventanareceta.setVisible(true);
                        ventanareceta.toFront();
                    }
                    vGestion_Recetas.jButtonRegistrarReceta.setEnabled(false);
                    vGestion_Recetas.jButtonEliminarInsumoElegido.setVisible(false);
                    id = jTableInsumosRegistrados.getValueAt(j, 0).toString();
                    ventanareceta.id = id;
                    Object datos[] = new Object[jTableInsumosRegistrados.getRowCount()];
                    for (int i = 0; i < jTableInsumosRegistrados.getRowCount(); i++) {
                        datos[i] = jTableInsumosRegistrados.getValueAt(i, 0).toString();
                        ventanareceta.modelo1.addRow(datos);
                    }
                    dispose();
                }
            }
        });
    }

    public void MostrarRecetas() {
        sql.descripcion = jLabelNombreProductoElegido.getText();
        modelo2 = sql.ConsultarInsumos();
        jTableInsumosRegistrados.setModel(modelo2);
        EliminarFilasVacias();
        ocultar_columnareceta();
    }

    public void EliminarFilasVacias() {
        if (jTableInsumosRegistrados.getRowCount() != 0) {
            for (int columna = 0; columna < jTableInsumosRegistrados.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableInsumosRegistrados.getRowCount(); fila++) {
                    if (jTableInsumosRegistrados.getValueAt(fila, columna) == null) {
                        modelo2.removeRow(fila);
                    }
                }
            }
        }
    }

    public void ocultar_columnareceta() {
        jTableInsumosRegistrados.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableInsumosRegistrados.getColumnModel().getColumn(0).setMinWidth(0);
        jTableInsumosRegistrados.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void LimpiarSeleccionInsumosAgregados() {
        jTableInsumosRegistrados.clearSelection();
        jTableInsumosRegistrados.getSelectionModel().clearSelection();
    }

    public void volverVentanaRecetas() {
        ventanareceta = new vGestion_Recetas();
        vMenuPrincipal.jDesktopPane1.add(ventanareceta);
        ventanareceta.setVisible(true);
        ventanareceta.toFront();
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTableInsumosRegistrados = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregarInsumoRegistrar = new javax.swing.JButton();
        jButtonModificarInsumosRegistrar = new javax.swing.JButton();
        jButtonEliminarInsumoRegistrar = new javax.swing.JButton();
        jLabelNombreProducto = new javax.swing.JLabel();
        jLabelNombreProductoElegido = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado de Recetas del Producto");
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

        jTableInsumosRegistrados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableInsumosRegistrados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableInsumosRegistrados);

        jButtonAgregarInsumoRegistrar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarInsumoRegistrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarInsumoRegistrar.setText("Nuevo");
        jButtonAgregarInsumoRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarInsumoRegistrarActionPerformed(evt);
            }
        });

        jButtonModificarInsumosRegistrar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificarInsumosRegistrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificarInsumosRegistrar.setText("Modificar");
        jButtonModificarInsumosRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarInsumosRegistrarActionPerformed(evt);
            }
        });

        jButtonEliminarInsumoRegistrar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminarInsumoRegistrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminarInsumoRegistrar.setText("Eliminar");
        jButtonEliminarInsumoRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarInsumoRegistrarActionPerformed(evt);
            }
        });

        jLabelNombreProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreProducto.setText("Nombre Producto:");

        jLabelNombreProductoElegido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(jLabelNombreProductoElegido, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonAgregarInsumoRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonModificarInsumosRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonEliminarInsumoRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNombreProductoElegido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jButtonAgregarInsumoRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jButtonModificarInsumosRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jButtonEliminarInsumoRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(113, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(23, 23, 23))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarInsumoRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarInsumoRegistrarActionPerformed
        volverVentanaRecetas();
    }//GEN-LAST:event_jButtonAgregarInsumoRegistrarActionPerformed

    private void jButtonModificarInsumosRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarInsumosRegistrarActionPerformed
        int j = jTableInsumosRegistrados.getSelectedRow();
        if (j == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            if (ventanareceta == null || ventanareceta.isClosed()) {
                ventanareceta = new vGestion_Recetas();
                vMenuPrincipal.jDesktopPane1.add(ventanareceta);
                ventanareceta.setVisible(true);
                ventanareceta.toFront();
            }
            vGestion_Recetas.jButtonRegistrarReceta.setEnabled(false);
            vGestion_Recetas.jButtonEliminarInsumoElegido.setVisible(false);
            id = jTableInsumosRegistrados.getValueAt(j, 0).toString();
            ventanareceta.id = id;
            Object datos[] = new Object[jTableInsumosRegistrados.getRowCount()];
            for (int i = 0; i < jTableInsumosRegistrados.getRowCount(); i++) {
                datos[i] = jTableInsumosRegistrados.getValueAt(i, 0).toString();
                ventanareceta.modelo1.addRow(datos);
            }
            dispose();
            //jTextFieldInsumo.setText(jTableInsumosRegistrados.getValueAt(j, 1).toString());
            //jButtonAgregarInsumoRegistrar.setText("Modificar");
            //jButtonModificarInsumosRegistrar.setText("Cancelar");

        }
        /*else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jButtonAgregarInsumoRegistrar.setText("Agregar");
                jButtonModificarInsumosRegistrar.setText("Modificar");
                jTextFieldInsumo.setText("");
                LimpiarSeleccionInsumosAgregados();
            }
        }*/
    }//GEN-LAST:event_jButtonModificarInsumosRegistrarActionPerformed

    private void jButtonEliminarInsumoRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarInsumoRegistrarActionPerformed
        int seleccionado = jTableInsumosRegistrados.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                r.setIdreceta(Integer.parseInt(id = jTableInsumosRegistrados.getValueAt(seleccionado, 0).toString()));
                if (receta.EliminarReceta(r)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    MostrarRecetas();
                    EliminarFilasVacias();
                }
            } else {
                LimpiarSeleccionInsumosAgregados();
            }
        }
    }//GEN-LAST:event_jButtonEliminarInsumoRegistrarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccionInsumosAgregados();
    }//GEN-LAST:event_formMouseClicked

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        volverVentanaRecetas();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarInsumoRegistrar;
    private javax.swing.JButton jButtonEliminarInsumoRegistrar;
    private javax.swing.JButton jButtonModificarInsumosRegistrar;
    private javax.swing.JLabel jLabelNombreProducto;
    public static javax.swing.JLabel jLabelNombreProductoElegido;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTableInsumosRegistrados;
    // End of variables declaration//GEN-END:variables
}
