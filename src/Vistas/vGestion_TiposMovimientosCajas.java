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
        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnAgregar.setEnabled(false);
                    btnModificar.setText("Cancelar");
                    btnEliminar.setText("Modificar");
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    id = (jTable1.getValueAt(fila, 0).toString());
                    jTextField1.setText(jTable1.getValueAt(fila, 1).toString());
                    if (jTable1.getValueAt(fila, 2).toString().equals("INGRESO")) {
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
        jTable1.setModel(datos);
        EliminarFilasVacias();
        ocultar_columnas();
    }
    
    public void EliminarFilasVacias() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                for (int n = 0; n < jTable1.getRowCount(); n++) {
                    if (jTable1.getValueAt(n, i) == null) {
                        datos.removeRow(n);
                    }
                }
            }
        }
    }
    
    public void ocultar_columnas() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    public void limpiarseleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        rbIngreso = new javax.swing.JRadioButton();
        rbEgreso = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
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

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("(*) Tipo:");

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

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

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("(*) Nombre:");

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

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Nombre Tipo");

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jLabel3)))
                        .addGap(0, 92, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbIngreso)
                            .addComponent(rbEgreso))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbIngreso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbEgreso))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        tm.setDescripcion(jTextField1.getText());
        String tipo = "";
        tipo = (rbIngreso.isSelected()) ? "INGRESO" : "";
        tipo = (rbEgreso.isSelected()) ? "EGRESO" : "";
        tm.setTipo(tipo);
        
        if (!jTextField1.getText().trim().equals("") || !tipo.equals("")) {
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
            int i = jTable1.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                btnAgregar.setEnabled(false);
                btnModificar.setText("Cancelar");
                btnEliminar.setText("Modificar");
                id = (jTable1.getValueAt(i, 0).toString());
                jTextField1.setText(jTable1.getValueAt(i, 1).toString());
                if (jTable1.getValueAt(i, 2).toString().equals("INGRESO")) {
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
                jTextField1.setText("");
                rbEgreso.setSelected(false);
                rbIngreso.setSelected(false);
                limpiarseleccion();
            }
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (btnEliminar.getText().equals("Eliminar")) {
            int seleccionado = jTable1.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    tm.setIdtipomovimiento(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
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
            tm.setDescripcion(jTextField1.getText());
            String tipo = "";
            tipo = (rbIngreso.isSelected()) ? "INGRESO" : "";
            tipo = (rbEgreso.isSelected()) ? "EGRESO" : "";
            tm.setTipo(tipo);
            
            if (!jTextField1.getText().trim().equals("") || !tipo.equals("")) {
                if (control_tmc.EditarTiposMovimientos(tm)) {
                    JOptionPane.showMessageDialog(null, "Modicacion Completa");
                    btnAgregar.setEnabled(true);
                    btnModificar.setText("Modificar");
                    btnEliminar.setText("Eliminar");
                    jTextField1.setText("");
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
        } else if (!jTextField1.getText().isEmpty() || rbEgreso.isSelected() == true || rbIngreso.isSelected() == true) {
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
        jTextField2.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jTextField2.getText().isEmpty()) {
            String[] columnas = {"ID TIPOGASTO", "DESCRIPCION", "TIPO"};
            Object[][] datostabla = control_tmc.MostrarTipoMovBuscado(jTextField2.getText());
            if (datostabla.length != 0) {
                datos = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(datos);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JRadioButton rbEgreso;
    private javax.swing.JRadioButton rbIngreso;
    // End of variables declaration//GEN-END:variables
}
