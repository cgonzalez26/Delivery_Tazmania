/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.control_TiposMovimientos;
import Modelo.TiposMovimientos;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_TiposMovimientosCajas extends javax.swing.JInternalFrame {
    
    String id;
    control_TiposMovimientos control_tmc = new control_TiposMovimientos();
    TiposMovimientos tm = new TiposMovimientos();
    DefaultTableModel datos;
    
    public vGestion_TiposMovimientosCajas() {
        initComponents();
        Mostrar();
        
        jTableTiposMovimientosCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnAgregar.setEnabled(false);
                    btnModificar.setText("Cancelar");
                    btnEliminar.setText("Modificar");
                    int fila = jTableTiposMovimientosCaja.rowAtPoint(e.getPoint());
                    id = (jTableTiposMovimientosCaja.getValueAt(fila, 0).toString());
                    jTextFieldNombreTipoMovimiento.setText(jTableTiposMovimientosCaja.getValueAt(fila, 1).toString());
                    if (jTableTiposMovimientosCaja.getValueAt(fila, 2).toString().equals("INGRESO")) {
                        rbIngreso.setSelected(true);
                        rbEgreso.setSelected(false);
                    } else {
                        rbEgreso.setSelected(true);
                        rbIngreso.setSelected(false);
                    }
                }
            }
        });
    }
    
    public void Mostrar() {
        String[] columnas = {"ID TIPOGASTO", "DESCRIPCION", "TIPO"};
        Object[][] datostabla = control_tmc.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTableTiposMovimientosCaja.setModel(datos);
        EliminarFilasVacias();
        ocultar_columnas();
    }
    
    public void EliminarFilasVacias() {
        if (jTableTiposMovimientosCaja.getRowCount() != 0) {
            for (int i = 0; i < jTableTiposMovimientosCaja.getColumnCount(); i++) {
                for (int n = 0; n < jTableTiposMovimientosCaja.getRowCount(); n++) {
                    if (jTableTiposMovimientosCaja.getValueAt(n, i) == null) {
                        datos.removeRow(n);
                    }
                }
            }
        }
    }
    
    public void ocultar_columnas() {
        jTableTiposMovimientosCaja.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableTiposMovimientosCaja.getColumnModel().getColumn(0).setMinWidth(0);
        jTableTiposMovimientosCaja.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    public void limpiarseleccion() {
        jTableTiposMovimientosCaja.clearSelection();
        jTableTiposMovimientosCaja.getSelectionModel().clearSelection();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelElegirTipoMovimiento = new javax.swing.JLabel();
        jTextFieldNombreTipoMovimiento = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTiposMovimientosCaja = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabelNombreTipoMovimiento = new javax.swing.JLabel();
        rbIngreso = new javax.swing.JRadioButton();
        rbEgreso = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelTipoMovimiento = new javax.swing.JLabel();
        jTextFieldNombreTipoBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Tipos de Movimientos de Caja");
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

        jLabelElegirTipoMovimiento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelElegirTipoMovimiento.setText("(*) Tipo:");

        jTextFieldNombreTipoMovimiento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jTableTiposMovimientosCaja.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableTiposMovimientosCaja.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableTiposMovimientosCaja);

        btnAgregar.setBackground(new java.awt.Color(252, 249, 57));
        btnAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(252, 249, 57));
        btnModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(252, 249, 57));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabelNombreTipoMovimiento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreTipoMovimiento.setText("(*) Nombre:");

        rbIngreso.setBackground(new java.awt.Color(255, 248, 177));
        rbIngreso.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        rbIngreso.setText("Ingreso");

        rbEgreso.setBackground(new java.awt.Color(255, 248, 177));
        rbEgreso.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        rbEgreso.setText("Egreso");
        rbEgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEgresoActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelTipoMovimiento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTipoMovimiento.setText("Nombre Tipo");

        jTextFieldNombreTipoBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextFieldNombreTipoBuscar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jLabelTipoMovimiento))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTipoMovimiento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNombreTipoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelNombreTipoMovimiento)
                                    .addComponent(jLabelElegirTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rbIngreso)
                                    .addComponent(rbEgreso))
                                .addGap(0, 181, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jTextFieldNombreTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNombreTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNombreTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelElegirTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbIngreso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbEgreso))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        tm.setDescripcion(jTextFieldNombreTipoMovimiento.getText());
        String tipo = "";
        tipo = (rbIngreso.isSelected()) ? "INGRESO" : "";
        tipo = (rbEgreso.isSelected()) ? "EGRESO" : "";
        tm.setTipo(tipo);
        
        if (!jTextFieldNombreTipoMovimiento.getText().trim().equals("") || !tipo.equals("")) {
            if (control_tmc.InsertarTiposMovimientos(tm)) {
                JOptionPane.showMessageDialog(null, "Nuevo Tipo Movimiento de Caja agregado");
                Mostrar();
            } else {
                JOptionPane.showMessageDialog(null, "No se ingreso nuevo Tipo Movimiento de Caja");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (btnModificar.getText().equals("Modificar")) {
            int i = jTableTiposMovimientosCaja.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                btnAgregar.setEnabled(false);
                btnModificar.setText("Cancelar");
                btnEliminar.setText("Modificar");
                id = (jTableTiposMovimientosCaja.getValueAt(i, 0).toString());
                jTextFieldNombreTipoMovimiento.setText(jTableTiposMovimientosCaja.getValueAt(i, 1).toString());
                if (jTableTiposMovimientosCaja.getValueAt(i, 2).toString().equals("INGRESO")) {
                    rbIngreso.setSelected(true);
                    rbEgreso.setSelected(false);
                } else {
                    rbEgreso.setSelected(true);
                    rbIngreso.setSelected(false);
                }
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                btnAgregar.setEnabled(true);
                btnModificar.setText("Modificar");
                btnEliminar.setText("Eliminar");
                jTextFieldNombreTipoMovimiento.setText("");
                rbEgreso.setSelected(false);
                rbIngreso.setSelected(false);
                limpiarseleccion();
            }
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (btnEliminar.getText().equals("Eliminar")) {
            int seleccionado = jTableTiposMovimientosCaja.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    tm.setIdtipomovimiento(Integer.parseInt(jTableTiposMovimientosCaja.getValueAt(seleccionado, 0).toString()));
                    if (control_tmc.EliminarTiposMovimientos(tm)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        Mostrar();
                        //datos.removeRow(seleccionado + 1);
                    }
                } else {
                    limpiarseleccion();
                }
            }
        } else {
            tm.setDescripcion(jTextFieldNombreTipoMovimiento.getText());
            String tipo = "";
            tipo = (rbIngreso.isSelected()) ? "INGRESO" : "";
            tipo = (rbEgreso.isSelected()) ? "EGRESO" : "";
            tm.setTipo(tipo);
            
            if (!jTextFieldNombreTipoMovimiento.getText().trim().equals("") || !tipo.equals("")) {
                if (control_tmc.EditarTiposMovimientos(tm)) {
                    JOptionPane.showMessageDialog(null, "Modicacion Completa");
                    btnAgregar.setEnabled(true);
                    btnModificar.setText("Modificar");
                    btnEliminar.setText("Eliminar");
                    jTextFieldNombreTipoMovimiento.setText("");
                    rbEgreso.setSelected(false);
                    rbIngreso.setSelected(false);
                    limpiarseleccion();
                    Mostrar();
                }
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void rbEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEgresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbEgresoActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (btnModificar.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldNombreTipoMovimiento.getText().isEmpty() || rbEgreso.isSelected() == true || rbIngreso.isSelected() == true) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        limpiarseleccion();
        Mostrar();
        jTextFieldNombreTipoBuscar.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jTextFieldNombreTipoBuscar.getText().isEmpty()) {
            String[] columnas = {"ID TIPOGASTO", "DESCRIPCION", "TIPO"};
            Object[][] datostabla = control_tmc.MostrarTipoMovBuscado(jTextFieldNombreTipoBuscar.getText());
            if (datostabla.length != 0) {
                datos = new DefaultTableModel(datostabla, columnas);
                jTableTiposMovimientosCaja.setModel(datos);
                EliminarFilasVacias();
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabelElegirTipoMovimiento;
    private javax.swing.JLabel jLabelNombreTipoMovimiento;
    private javax.swing.JLabel jLabelTipoMovimiento;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTiposMovimientosCaja;
    private javax.swing.JTextField jTextFieldNombreTipoBuscar;
    private javax.swing.JTextField jTextFieldNombreTipoMovimiento;
    private javax.swing.JRadioButton rbEgreso;
    private javax.swing.JRadioButton rbIngreso;
    // End of variables declaration//GEN-END:variables
}
