/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.control_TiposGastos;
import Modelo.TiposGastos;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_TiposGastos extends javax.swing.JInternalFrame {
    
    String id;
    control_TiposGastos tipogasto = new control_TiposGastos();
    TiposGastos tg = new TiposGastos();
    DefaultTableModel datos;
    
    public vGestion_TiposGastos() {
        initComponents();
        Mostrar();
        
        jTableTiposGastos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    jButtonAgregar.setEnabled(false);
                    jButtonModificar.setText("Cancelar");
                    jButtonEliminar.setText("Modificar");
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    int fila = jTableTiposGastos.rowAtPoint(e.getPoint());
                    id = (jTableTiposGastos.getValueAt(fila, 0).toString());
                    jTextFieldTiposGastos.setText(jTableTiposGastos.getValueAt(fila, 1).toString());
                }
            }
        });
    }
    
    public void LimpiarSeleccion() {
        jTableTiposGastos.clearSelection();
        jTableTiposGastos.getSelectionModel().clearSelection();
    }
    
    public void Mostrar() {
        String[] columnas = {"ID TIPOGASTO", "DESCRIPCION"};
        Object[][] datostabla = tipogasto.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTableTiposGastos.setModel(datos);
        EliminarFilasVacias();
        ocultar_columnas();
    }
    
    public void ocultar_columnas() {
        jTableTiposGastos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableTiposGastos.getColumnModel().getColumn(0).setMinWidth(0);
        jTableTiposGastos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    public void EliminarFilasVacias() {
        if (jTableTiposGastos.getRowCount() != 0) {
            for (int columna = 0; columna < jTableTiposGastos.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableTiposGastos.getRowCount(); fila++) {
                    if (jTableTiposGastos.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTiposGastos = new javax.swing.JLabel();
        jTextFieldTiposGastos = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTiposGastos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Administrar Tipos Gastos");
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

        jLabelTiposGastos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTiposGastos.setText("(*) Nombre:");

        jTextFieldTiposGastos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldTiposGastos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTiposGastosKeyTyped(evt);
            }
        });

        jTableTiposGastos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableTiposGastos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableTiposGastos);

        jButtonAgregar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
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
                        .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldTiposGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTiposGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(33, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabelTiposGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTiposGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        if (!jTextFieldTiposGastos.getText().trim().equals("")) {
            tg.setDescripcion(jTextFieldTiposGastos.getText());
            if (tipogasto.InsertarTiposGastos(tg)) {
                JOptionPane.showMessageDialog(null, "Nuevo Tipo Gasto agreado");
                Mostrar();
                jTextFieldTiposGastos.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        if (jButtonModificar.getText().equals("Modificar")) {
            int s = jTableTiposGastos.getSelectedRow();
            if (s == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                jButtonAgregar.setEnabled(false);
                jButtonModificar.setText("Cancelar");
                jButtonEliminar.setText("Modificar");
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                id = (jTableTiposGastos.getValueAt(s, 0).toString());
                jTextFieldTiposGastos.setText(jTableTiposGastos.getValueAt(s, 1).toString());
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jButtonAgregar.setEnabled(true);
                jButtonModificar.setText("Modificar");
                jButtonEliminar.setText("Eliminar");
                jTextFieldTiposGastos.setText("");
                LimpiarSeleccion();
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        if (jButtonEliminar.getText().equals("Eliminar")) {
            int seleccionado = jTableTiposGastos.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    tg.setIdtipogasto(Integer.parseInt(jTableTiposGastos.getValueAt(seleccionado, 0).toString()));
                    if (tipogasto.EliminarTiposGastos(tg)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        Mostrar();
                        EliminarFilasVacias();
                    }
                } else {
                    LimpiarSeleccion();
                }
            }
        } else {
            if (!jTextFieldTiposGastos.getText().trim().equals("")) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    tg.setDescripcion(jTextFieldTiposGastos.getText());
                    tg.setIdtipogasto(Integer.parseInt(id));
                    if (tipogasto.EditarTiposGastos(tg)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        Mostrar();
                        jButtonAgregar.setEnabled(true);
                        jButtonModificar.setText("Modificar");
                        jButtonEliminar.setText("Eliminar");
                        jTextFieldTiposGastos.setText("");
                        LimpiarSeleccion();
                        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButtonModificar.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldTiposGastos.getText().trim().isEmpty()) {
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

    private void jTextFieldTiposGastosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTiposGastosKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextFieldTiposGastosKeyTyped

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JLabel jLabelTiposGastos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTiposGastos;
    private javax.swing.JTextField jTextFieldTiposGastos;
    // End of variables declaration//GEN-END:variables
}
