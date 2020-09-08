package Vistas;

import Controlador.control_Empleados;
import Modelo.Empleados;
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

        jTableEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTableEmpleados.rowAtPoint(e.getPoint());
                    empleado = new vGestion_Empleados();
                    vMenuPrincipal.jDesktopPane1.add(empleado);
                    empleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    empleado.setVisible(true);
                    vGestion_Empleados.jButtonAgregar.setEnabled(false);
                    vGestion_Empleados.jButtonModificar.setEnabled(true);
                    idemp = (jTableEmpleados.getValueAt(fila, 0).toString());
                    vGestion_Empleados.jComboBoxTipoEmpleado.setSelectedItem(jTableEmpleados.getValueAt(fila, 3).toString());
                    vGestion_Empleados.jTextFieldNroDocumento.setText(jTableEmpleados.getValueAt(fila, 5).toString());
                    vGestion_Empleados.jTextFieldNombre.setText(jTableEmpleados.getValueAt(fila, 6).toString());
                    vGestion_Empleados.jTextFieldApellido.setText(jTableEmpleados.getValueAt(fila, 7).toString());
                    vGestion_Empleados.jTextFieldDomicilio.setText(jTableEmpleados.getValueAt(fila, 8).toString());
                    vGestion_Empleados.jTextFieldTelefono.setText(jTableEmpleados.getValueAt(fila, 9).toString());
                    empleado.id = idemp;
                    dispose();
                    LimpiarSeleccion();
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTableEmpleados.clearSelection();
        jTableEmpleados.getSelectionModel().clearSelection();
    }

    public void Mostrar() {
        String[] columnas = {"IDEMPLEADO", "IDTIPOEMP", "IDTIPODOC", "TIPO EMP", "TIPO DOC", "NRO DOCUMENTO", "NOMBRES", "APELLIDOS", "DOMICILIO", "TELEFONO"};
        Object[][] datostabla = emp.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTableEmpleados.setModel(datos);
        EliminarFilasVacias();
        ocultar_columnas();
    }

    public void ocultar_columnas() {
        jTableEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        jTableEmpleados.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableEmpleados.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableEmpleados.getColumnModel().getColumn(1).setMinWidth(0);
        jTableEmpleados.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTableEmpleados.getColumnModel().getColumn(2).setMaxWidth(0);
        jTableEmpleados.getColumnModel().getColumn(2).setMinWidth(0);
        jTableEmpleados.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTableEmpleados.getColumnModel().getColumn(4).setMaxWidth(0);
        jTableEmpleados.getColumnModel().getColumn(4).setMinWidth(0);
        jTableEmpleados.getColumnModel().getColumn(4).setPreferredWidth(0);
    }

    public void EliminarFilasVacias() {
        if (jTableEmpleados.getRowCount() != 0) {
            for (int columna = 0; columna < jTableEmpleados.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableEmpleados.getRowCount(); fila++) {
                    if (jTableEmpleados.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonNuevo = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEmpleados = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonModificar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelRolTrabajo = new javax.swing.JLabel();
        jTextFieldBuscarRolTrabajo = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Empleados");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jButtonNuevo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevo.setText("Nuevo");
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
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

        jTableEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableEmpleados);

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelRolTrabajo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelRolTrabajo.setText("Rol de Trabajo");

        jTextFieldBuscarRolTrabajo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
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
                        .addComponent(jLabelRolTrabajo)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldBuscarRolTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRolTrabajo)
                    .addComponent(jTextFieldBuscarRolTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jButtonBuscar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1056, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(226, 226, 226))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        empleado = new vGestion_Empleados();
        vMenuPrincipal.jDesktopPane1.add(empleado);
        empleado.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int seleccionado = jTableEmpleados.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                e.setIdempleados(Integer.parseInt(jTableEmpleados.getValueAt(seleccionado, 0).toString()));
                if (emp.EliminarEmpleados(e)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int seleccionado = jTableEmpleados.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            empleado = new vGestion_Empleados();
            vMenuPrincipal.jDesktopPane1.add(empleado);
            empleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            empleado.setVisible(true);
            vGestion_Empleados.jButtonAgregar.setEnabled(false);
            vGestion_Empleados.jButtonModificar.setEnabled(true);
            idemp = (jTableEmpleados.getValueAt(seleccionado, 0).toString());
            vGestion_Empleados.jComboBoxTipoEmpleado.setSelectedItem(jTableEmpleados.getValueAt(seleccionado, 3).toString());
            vGestion_Empleados.jTextFieldNroDocumento.setText(jTableEmpleados.getValueAt(seleccionado, 5).toString());
            vGestion_Empleados.jTextFieldNombre.setText(jTableEmpleados.getValueAt(seleccionado, 6).toString());
            vGestion_Empleados.jTextFieldApellido.setText(jTableEmpleados.getValueAt(seleccionado, 7).toString());
            vGestion_Empleados.jTextFieldDomicilio.setText(jTableEmpleados.getValueAt(seleccionado, 8).toString());
            vGestion_Empleados.jTextFieldTelefono.setText(jTableEmpleados.getValueAt(seleccionado, 9).toString());
            empleado.id = idemp;
            dispose();
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        //Mostrar();
        //jTextFieldBuscarRolTrabajo.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if (!jTextFieldBuscarRolTrabajo.getText().isEmpty()) {
            Object[][] datostabla = emp.MostrarDatosBusqueda(jTextFieldBuscarRolTrabajo.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDEMPLEADO", "IDTIPOEMP", "IDTIPODOC", "TIPO EMP", "TIPO DOC", "NRO DOC", "NOMBRES", "APELLIDOS", "DOMICILIO", "TELEFONO"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTableEmpleados.setModel(datos);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JLabel jLabelRolTrabajo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableEmpleados;
    private javax.swing.JTextField jTextFieldBuscarRolTrabajo;
    // End of variables declaration//GEN-END:variables
}
