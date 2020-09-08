package Vistas;

import Controlador.control_Empleados;
import Controlador.control_existencias;
import Modelo.Empleados;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Empleados extends javax.swing.JInternalFrame {

    control_existencias con = new control_existencias();
    control_Empleados empleado = new control_Empleados();
    Empleados e = new Empleados();
    vLista_Empleados lista = null;
    String id;

    public vGestion_Empleados() {
        initComponents();
        ComboTiposEmpleados();
        EliminarItemsVaciosTipoEmpleado();
        jButtonModificar.setEnabled(false);
    }

    public void ComboTiposEmpleados() {
        Object[] tipoempleado = con.combox("tiposempleados", "descripcion");
        for (Object dato : tipoempleado) {
            jComboBoxTipoEmpleado.addItem((String) dato);
        }
    }

    public void EliminarItemsVaciosTipoEmpleado() {
        for (int i = 0; i < jComboBoxTipoEmpleado.getItemCount(); i++) {
            if (jComboBoxTipoEmpleado.getItemAt(i) == null) {
                jComboBoxTipoEmpleado.removeItemAt(i);
            }
        }
    }

    public void Cerrar() {
        if (!jComboBoxTipoEmpleado.getSelectedItem().equals("Seleccionar Tipo...") || !jTextFieldNroDocumento.getText().trim().isEmpty() || !jTextFieldNombre.getText().trim().isEmpty() || !jTextFieldApellido.getText().trim().isEmpty() || !jTextFieldDomicilio.getText().trim().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNroDocumento = new javax.swing.JLabel();
        jTextFieldNroDocumento = new javax.swing.JTextField();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jLabelApellido = new javax.swing.JLabel();
        jTextFieldApellido = new javax.swing.JTextField();
        jLabelDomicilio = new javax.swing.JLabel();
        jTextFieldDomicilio = new javax.swing.JTextField();
        jLabelTelefono = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jComboBoxTipoEmpleado = new javax.swing.JComboBox<>();
        jBotonCancelar = new javax.swing.JButton();
        jLabelTipoEmpleado = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Empleados");
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

        jLabelNroDocumento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNroDocumento.setText("Nro Documento:");

        jTextFieldNroDocumento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldNroDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNroDocumentoKeyTyped(evt);
            }
        });

        jLabelNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombre.setText("(*) Nombres:");

        jTextFieldNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreKeyTyped(evt);
            }
        });

        jLabelApellido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelApellido.setText("Apellidos:");

        jTextFieldApellido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldApellidoKeyTyped(evt);
            }
        });

        jLabelDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDomicilio.setText("Domicilio:");

        jTextFieldDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTelefono.setText("Teléfono:");

        jTextFieldTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

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
        jButtonModificar.setMaximumSize(new java.awt.Dimension(75, 25));
        jButtonModificar.setMinimumSize(new java.awt.Dimension(75, 25));
        jButtonModificar.setPreferredSize(new java.awt.Dimension(75, 25));
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jComboBoxTipoEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBoxTipoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo..." }));

        jBotonCancelar.setBackground(new java.awt.Color(237, 124, 61));
        jBotonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonCancelar.setText("Cancelar");
        jBotonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonCancelarActionPerformed(evt);
            }
        });

        jLabelTipoEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTipoEmpleado.setText("(*)Tipo Empleado:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(jBotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTextFieldDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        if (!jTextFieldNombre.getText().trim().equals("") && !jComboBoxTipoEmpleado.getSelectedItem().equals("Seleccionar Tipo...")) {
            if (jTextFieldNroDocumento.getText().trim().length() <= 10) {
                e.setIdtipoempleado(empleado.ObtenerIDTipoEmpleado((String) jComboBoxTipoEmpleado.getSelectedItem()));
                e.setIdtipodocumento(1);
                if (jTextFieldNroDocumento.getText().trim().equals("")) {
                    e.setNroDocumento("-");
                } else {
                    e.setNroDocumento(jTextFieldNroDocumento.getText());
                }
                e.setNombre(jTextFieldNombre.getText());
                if (!jTextFieldApellido.getText().equals("")) {
                    e.setApellido(jTextFieldApellido.getText());
                } else {
                    e.setApellido("-");
                }
                if (jTextFieldDomicilio.getText().trim().isEmpty()) {
                    e.setDireccion("-");
                } else {
                    e.setDireccion(jTextFieldDomicilio.getText());
                }
                if (jTextFieldTelefono.getText().trim().isEmpty()) {
                    e.setTelefono("-");
                } else {
                    e.setTelefono(jTextFieldTelefono.getText());
                }
                if (empleado.InsertarEmpledos(e)) {
                    JOptionPane.showMessageDialog(null, "Nuevo empleado agregado");
                    lista = new vLista_Empleados();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "El formato del documento es incorrecto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        if (!jTextFieldNombre.getText().trim().equals("") && !jComboBoxTipoEmpleado.getSelectedItem().equals("Seleccionar Tipo...")) {
            if (jTextFieldNroDocumento.getText().trim().length() <= 10) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    e.setIdtipoempleado(empleado.ObtenerIDTipoEmpleado((String) jComboBoxTipoEmpleado.getSelectedItem()));
                    e.setIdtipodocumento(1);
                    if (jTextFieldNroDocumento.getText().trim().equals("")) {
                        e.setNroDocumento("-");
                    } else {
                        e.setNroDocumento(jTextFieldNroDocumento.getText());
                    }
                    e.setNombre(jTextFieldNombre.getText());
                    if (!jTextFieldApellido.getText().equals("")) {
                        e.setApellido(jTextFieldApellido.getText());
                    } else {
                        e.setApellido("-");
                    }
                    if (jTextFieldDomicilio.getText().trim().isEmpty()) {
                        e.setDireccion("-");
                    } else {
                        e.setDireccion(jTextFieldDomicilio.getText());
                    }
                    if (jTextFieldTelefono.getText().trim().isEmpty()) {
                        e.setTelefono("-");
                    } else {
                        e.setTelefono(jTextFieldTelefono.getText());
                    }
                    e.setIdempleados(Integer.parseInt(id));
                    if (empleado.EditarEmpleados(e)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        lista = new vLista_Empleados();
                        vMenuPrincipal.jDesktopPane1.add(lista);
                        lista.setVisible(true);
                        this.dispose();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "El formato del documento es incorrecto");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (!jButtonAgregar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Empleados();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextFieldNroDocumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNroDocumentoKeyTyped
        char[] p = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', KeyEvent.VK_BACK_SPACE};
        int b = 0;
        for (int i = 0; i <= 11; i++) {
            if (p[i] == evt.getKeyChar()) {
                b = 1;
            }
        }
        if (b == 0) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextFieldNroDocumentoKeyTyped

    private void jTextFieldNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextFieldNombreKeyTyped

    private void jTextFieldApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldApellidoKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextFieldApellidoKeyTyped

    private void jBotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonCancelarActionPerformed
        if (!jButtonAgregar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Empleados();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                dispose();
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_jBotonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonCancelar;
    public static javax.swing.JButton jButtonAgregar;
    public static javax.swing.JButton jButtonModificar;
    public static javax.swing.JComboBox<String> jComboBoxTipoEmpleado;
    private javax.swing.JLabel jLabelApellido;
    private javax.swing.JLabel jLabelDomicilio;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNroDocumento;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JLabel jLabelTipoEmpleado;
    public static javax.swing.JTextField jTextFieldApellido;
    public static javax.swing.JTextField jTextFieldDomicilio;
    public static javax.swing.JTextField jTextFieldNombre;
    public static javax.swing.JTextField jTextFieldNroDocumento;
    public static javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables
}
