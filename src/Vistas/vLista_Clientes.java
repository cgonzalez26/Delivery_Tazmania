package Vistas;

import Controlador.control_Clientes;
import Modelo.Clientes;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Clientes extends javax.swing.JInternalFrame {

    vGestion_Clientes vcliente = null;
    control_Clientes contr_clientes = new control_Clientes();
    Clientes cliente = new Clientes();
    Object[][] datostabla;
    DefaultTableModel tabla;
    String id;

    public vLista_Clientes() {
        initComponents();
        Mostrar();
        jTableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTableClientes.rowAtPoint(e.getPoint());
                    vcliente = new vGestion_Clientes();
                    vMenuPrincipal.jDesktopPane1.add(vcliente);
                    vcliente.setVisible(true);
                    vGestion_Clientes.jButtonAgregar.setEnabled(false);
                    vGestion_Usuarios.jBotonModif_Usuario.setEnabled(true);
                    id = jTableClientes.getValueAt(fila, 0).toString();
                    vGestion_Clientes.jTextFieldNroDocumento.setText(jTableClientes.getValueAt(fila, 1).toString());
                    vGestion_Clientes.jTextFieldNombre.setText(jTableClientes.getValueAt(fila, 2).toString());
                    vGestion_Clientes.jTextFieldApellido.setText(jTableClientes.getValueAt(fila, 3).toString());
                    vGestion_Clientes.jTextFieldDomicilio.setText(jTableClientes.getValueAt(fila, 4).toString());
                    vGestion_Clientes.jTextFieldTelefono.setText(jTableClientes.getValueAt(fila, 5).toString());
                    vGestion_Clientes.jTextFieldEmail.setText(jTableClientes.getValueAt(fila, 6).toString());
                    vcliente.idcliente = id;
                    dispose();
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTableClientes.clearSelection();
        jTableClientes.getSelectionModel().clearSelection();
    }

    public void ocultar_columnas() {
        jTableClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableClientes.getColumnModel().getColumn(0).setMinWidth(0);
        jTableClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void Mostrar() {
        String[] columnas = {"IDCLIENTE", "NRO DOCUMENTO", "NOMBRES", "APELLIDOS", "DOMICILIO", "TELEFONO", "E-MAIL"};
        datostabla = contr_clientes.MostrarDatos();
        tabla = new DefaultTableModel(datostabla, columnas);
        jTableClientes.setModel(tabla);
        EliminarFilasVacias();
        ocultar_columnas();
    }

    public void EliminarFilasVacias() {
        if (jTableClientes.getRowCount() != 0) {
            for (int columna = 0; columna < jTableClientes.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableClientes.getRowCount(); fila++) {
                    if (jTableClientes.getValueAt(fila, columna) == null) {
                        tabla.removeRow(fila);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel1 = new javax.swing.JPanel();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();
        jLabelDNI = new javax.swing.JLabel();
        jTextFieldDNI = new javax.swing.JTextField();
        jLabelTelefono = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jButtonNuevo = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado Clientes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTableClientes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableClientes);

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombre.setText("Nombre");

        jTextFieldNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jLabelDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDNI.setText("DNI");

        jTextFieldDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTelefono.setText("Telefono");

        jTextFieldTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDNI)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabelNombre)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(jLabelTelefono)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(425, 425, 425))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombre)
                    .addComponent(jLabelDNI)
                    .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTelefono)
                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonNuevo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevo.setText("Nuevo");
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
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
                .addGap(223, 223, 223)
                .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(218, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        vcliente = new vGestion_Clientes();
        vMenuPrincipal.jDesktopPane1.add(vcliente);
        vcliente.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int fila = jTableClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            vcliente = new vGestion_Clientes();
            vMenuPrincipal.jDesktopPane1.add(vcliente);
            vcliente.setVisible(true);
            vGestion_Clientes.jButtonAgregar.setEnabled(false);
            vGestion_Usuarios.jBotonModif_Usuario.setEnabled(true);
            id = jTableClientes.getValueAt(fila, 0).toString();
            vGestion_Clientes.jTextFieldNroDocumento.setText(jTableClientes.getValueAt(fila, 1).toString());
            vGestion_Clientes.jTextFieldNombre.setText(jTableClientes.getValueAt(fila, 2).toString());
            vGestion_Clientes.jTextFieldApellido.setText(jTableClientes.getValueAt(fila, 3).toString());
            vGestion_Clientes.jTextFieldDomicilio.setText(jTableClientes.getValueAt(fila, 4).toString());
            vGestion_Clientes.jTextFieldTelefono.setText(jTableClientes.getValueAt(fila, 5).toString());
            vGestion_Clientes.jTextFieldEmail.setText(jTableClientes.getValueAt(fila, 6).toString());
            vcliente.idcliente = id;
            dispose();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int fila = jTableClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                cliente.setIdcliente(Integer.parseInt(jTableClientes.getValueAt(i, 0).toString()));
                if (contr_clientes.EliminarClientes(cliente)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if (!jTextFieldNombre.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaCliente(jTextFieldNombre.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "E-MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldDNI.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNI(jTextFieldDNI.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "E-MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldTelefono.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaTelefono(jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "E-MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldDNI.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNICliente(jTextFieldDNI.getText(), jTextFieldNombre.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "E-MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldTelefono.getText().isEmpty() && jTextFieldNombre.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaClienteTelefono(jTextFieldNombre.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "E-MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldDNI.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNITelefono(jTextFieldDNI.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "E-MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldNombre.getText().isEmpty() && !jTextFieldDNI.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNIClienteTelefono(jTextFieldDNI.getText(), jTextFieldNombre.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "TELEFONO", "E-MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos");
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextField jTextFieldDNI;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables
}
