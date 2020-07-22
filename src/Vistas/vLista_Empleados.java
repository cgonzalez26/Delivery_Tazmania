package Vistas;

import Controlador.control_Empleados;
import Modelo.Empleados;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Empleados extends javax.swing.JInternalFrame {

    String idemp;
    vGestion_Empleados empleado = null;
    control_Empleados emp = new control_Empleados();
    Empleados e = new Empleados();
    DefaultTableModel datos;

    public vLista_Empleados() {
        initComponents();
        Mostrar();

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    empleado = new vGestion_Empleados();
                    vMenuPrincipal.jDesktopPane1.add(empleado);
                    empleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    empleado.setVisible(true);
                    vGestion_Empleados.jButton2.setEnabled(true);
                    vGestion_Empleados.jButton1.setText("Cancelar");
                    idemp = (jTable1.getValueAt(fila, 0).toString());
                    vGestion_Empleados.jComboBox1.setSelectedItem(jTable1.getValueAt(fila, 3).toString());
                    vGestion_Empleados.jTextField1.setText(jTable1.getValueAt(fila, 5).toString());
                    vGestion_Empleados.jTextField2.setText(jTable1.getValueAt(fila, 6).toString());
                    vGestion_Empleados.jTextField3.setText(jTable1.getValueAt(fila, 7).toString());
                    vGestion_Empleados.jTextField4.setText(jTable1.getValueAt(fila, 8).toString());
                    vGestion_Empleados.jTextField5.setText(jTable1.getValueAt(fila, 9).toString());
                    empleado.id = idemp;
                    dispose();
                    LimpiarSeleccion();
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void Mostrar() {
        String[] columnas = {"IDEMPLEADO", "IDTIPOEMP", "IDTIPODOC", "TIPO EMP", "TIPO DOC", "NRO DOC", "NOMBRE", "APELLIDO", "DOMICILIO", "TELEFONO"};
        Object[][] datostabla = emp.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTable1.setModel(datos);
        EliminarFilasVacias();
        AjustarTamañoFilas();
        ocultar_columnas();
    }

    public void AjustarTamañoFilas() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int tipoemp = (int) font.getStringBounds(jTable1.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int nrodoc = (int) font.getStringBounds(jTable1.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int nombre = (int) font.getStringBounds(jTable1.getValueAt(i, 6).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int apellido = (int) font.getStringBounds(jTable1.getValueAt(i, 7).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int direc = (int) font.getStringBounds(jTable1.getValueAt(i, 8).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int tel = (int) font.getStringBounds(jTable1.getValueAt(i, 9).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (tipoemp > jTable1.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(3).setPreferredWidth(tipoemp);
                }
                if (nrodoc > jTable1.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(5).setPreferredWidth(nrodoc);
                }
                if (nombre > jTable1.getColumnModel().getColumn(6).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(6).setPreferredWidth(nombre);
                }
                if (apellido > jTable1.getColumnModel().getColumn(7).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(7).setPreferredWidth(apellido);
                }
                if (direc > jTable1.getColumnModel().getColumn(8).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(8).setPreferredWidth(direc);
                }
                if (tel > jTable1.getColumnModel().getColumn(9).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(9).setPreferredWidth(tel);
                }
            }
        }
    }

    public void ocultar_columnas() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(4).setMinWidth(0);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);
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

        jButton1 = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Empleados");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Nuevo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Eliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
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

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Rol de Trabajo");

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1056, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(Eliminar)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        empleado = new vGestion_Empleados();
        vMenuPrincipal.jDesktopPane1.add(empleado);
        empleado.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                e.setIdempleados(Integer.parseInt(jTable1.getValueAt(seleccionado, 0).toString()));
                if (emp.EliminarEmpleados(e)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_EliminarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            empleado = new vGestion_Empleados();
            vMenuPrincipal.jDesktopPane1.add(empleado);
            empleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            empleado.setVisible(true);
            vGestion_Empleados.jButton2.setEnabled(true);
            vGestion_Empleados.jButton1.setText("Cancelar");
            idemp = (jTable1.getValueAt(seleccionado, 0).toString());
            vGestion_Empleados.jComboBox1.setSelectedItem(jTable1.getValueAt(seleccionado, 3).toString());
            vGestion_Empleados.jTextField1.setText(jTable1.getValueAt(seleccionado, 5).toString());
            vGestion_Empleados.jTextField2.setText(jTable1.getValueAt(seleccionado, 6).toString());
            vGestion_Empleados.jTextField3.setText(jTable1.getValueAt(seleccionado, 7).toString());
            vGestion_Empleados.jTextField4.setText(jTable1.getValueAt(seleccionado, 8).toString());
            vGestion_Empleados.jTextField5.setText(jTable1.getValueAt(seleccionado, 9).toString());
            empleado.id = idemp;
            dispose();
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        Mostrar();
        jTextField1.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!jTextField1.getText().isEmpty()) {
            Object[][] datostabla = emp.MostrarDatosBusqueda(jTextField1.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDEMPLEADO", "IDTIPOEMP", "IDTIPODOC", "TIPO EMP", "TIPO DOC", "NRO DOC", "NOMBRE", "APELLIDO", "DOMICILIO", "TELEFONO"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(datos);
                EliminarFilasVacias();
                AjustarTamañoFilas();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
