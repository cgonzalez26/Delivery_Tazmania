package Vistas;

import Controlador.control_TiposArticulos;
import Modelo.TiposArticulos;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_TiposArticulos extends javax.swing.JInternalFrame {

    String id;
    control_TiposArticulos tipoarticulo = new control_TiposArticulos();
    TiposArticulos ta = new TiposArticulos();
    DefaultTableModel datos;

    public vGestion_TiposArticulos() {
        initComponents();
        Mostrar();

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    id = (jTable1.getValueAt(fila, 0).toString());
                    jTextField1.setText(jTable1.getValueAt(fila, 1).toString());
                    jButton1.setEnabled(false);
                    jButton3.setText("Modificar");
                    jButton2.setText("Cancelar");
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void Mostrar() {
        String[] columnas = {"ID TIPOARTICULO", "DESCRIPCION"};
        Object[][] datostabla = tipoarticulo.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTable1.setModel(datos);
        EliminarFilasVacias();
        ocultar_columnas();
    }

    public void ocultar_columnas() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void EliminarFilasVacias() {
        if (jTable1.getRowCount() != 0) {
            for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                    if (jTable1.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Administrar Tipos Articulos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("(*) Nombre");

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(24, 24, 24)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jTextField1.getText().trim().equals("")) {
            ta.setDescripcion(jTextField1.getText());
            if (tipoarticulo.InsertarTiposArticulos(ta)) {
                JOptionPane.showMessageDialog(null, "Nuevo Tipo Articulo agregado");
                Mostrar();
                jTextField1.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jButton2.getText().equals("Modificar")) {
            int s = jTable1.getSelectedRow();
            if (s == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                id = (jTable1.getValueAt(s, 0).toString());
                jTextField1.setText(jTable1.getValueAt(s, 1).toString());
                jButton1.setEnabled(false);
                jButton3.setText("Modificar");
                jButton2.setText("Cancelar");
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jTextField1.setText("");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jButton1.setEnabled(true);
                jButton2.setText("Modificar");
                jButton3.setText("Eliminar");
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jButton3.getText().equals("Eliminar")) {
            int seleccionado = jTable1.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    ta.setIdtipoarticulo(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                    if (tipoarticulo.EliminarTiposArticulos(ta)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        Mostrar();
                        EliminarFilasVacias();
                    }
                } else {
                    LimpiarSeleccion();
                }
            }
        } else {
            if (!jTextField1.getText().trim().equals("")) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Datos?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    ta.setDescripcion(jTextField1.getText());
                    ta.setIdtipoarticulo(Integer.parseInt(id));
                    if (tipoarticulo.EditarTiposArticulos(ta)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        Mostrar();
                        jButton1.setEnabled(true);
                        jButton2.setText("Modificar");
                        jButton3.setText("Eliminar");
                        jTextField1.setText("");
                        LimpiarSeleccion();
                        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe completar el campo obligatorio");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButton2.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextField1.getText().trim().isEmpty()) {
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

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
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
    }//GEN-LAST:event_jTextField1KeyTyped

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
