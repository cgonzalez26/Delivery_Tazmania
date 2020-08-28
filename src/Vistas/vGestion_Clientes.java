package Vistas;

import Controlador.control_Clientes;
import Modelo.Clientes;
import javax.swing.JOptionPane;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Clientes extends javax.swing.JInternalFrame {
    
    vLista_Clientes lista = null;
    control_Clientes cont_cliente = new control_Clientes();
    Clientes clientes = new Clientes();
    String idcliente;
    
    public vGestion_Clientes() {
        initComponents();
        jButtonModificar.setEnabled(false);
        Limpiar();
    }
    
    public void Limpiar() {
        jTextFieldNombre.setText("");
        jTextFieldApellido.setText("");
        jTextFieldDomicilio.setText("");
        jTextFieldTelefono.setText("");
        jTextFieldEmail.setText("");
        jTextFieldNroDocumento.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombre = new javax.swing.JLabel();
        jLabelApellido = new javax.swing.JLabel();
        jLabelDomicilio = new javax.swing.JLabel();
        jLabelTelefono = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jLabelNroDocumento = new javax.swing.JLabel();
        jTextFieldNroDocumento = new javax.swing.JTextField();
        jBotonCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Clientes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(694, 400));
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

        jLabelNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombre.setText("(*) Nombres:");

        jTextFieldNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelApellido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelApellido.setText("(*) Apellidos:");

        jTextFieldApellido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDomicilio.setText("(*) Domicilio:");

        jTextFieldDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTelefono.setText("(*) Teléfono:");

        jTextFieldTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelEmail.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelEmail.setText("E-mail:");

        jTextFieldEmail.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

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

        jLabelNroDocumento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNroDocumento.setText("(*) Nro Documento:");

        jTextFieldNroDocumento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jBotonCancelar.setBackground(new java.awt.Color(237, 124, 61));
        jBotonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonCancelar.setText("Cancelar");
        jBotonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelDomicilio)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNroDocumento)
                            .addComponent(jTextFieldNroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelApellido)
                            .addComponent(jLabelTelefono)
                            .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextFieldDomicilio))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jBotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTelefono))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNombre)
                            .addComponent(jLabelApellido))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addComponent(jLabelNroDocumento)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldNroDocumento)
                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabelDomicilio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButtonAgregar.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Clientes();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            if (!jTextFieldNroDocumento.getText().isEmpty() || !jTextFieldNombre.getText().isEmpty() || !jTextFieldApellido.getText().isEmpty() || !jTextFieldDomicilio.getText().isEmpty() || !jTextFieldTelefono.getText().isEmpty() || !jTextFieldEmail.getText().isEmpty()) {
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
    }//GEN-LAST:event_formInternalFrameClosing

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
//        if (jButton3.getText().equals("Agregar")) {
            if (!jTextFieldNroDocumento.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty() && !jTextFieldApellido.getText().isEmpty() && !jTextFieldDomicilio.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
                clientes.setDni(jTextFieldNroDocumento.getText());
                clientes.setNombre(jTextFieldNombre.getText());
                clientes.setApellido(jTextFieldApellido.getText());
                clientes.setDireccion(jTextFieldDomicilio.getText());
                clientes.setTelefono(jTextFieldTelefono.getText());
                if (jTextFieldEmail.getText().isEmpty()) {
                    clientes.setEmail("-");
                } else {
                    clientes.setEmail(jTextFieldEmail.getText());
                }
                if (cont_cliente.InsertarClientes(clientes)) {
                    JOptionPane.showMessageDialog(null, "Nuevo Cliente agregado");
                    lista = new vLista_Clientes();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
//        } else {
//            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
//            if (i == 0) {
//                lista = new vLista_Clientes();
//                vMenuPrincipal.jDesktopPane1.add(lista);
//                lista.setVisible(true);
//                this.dispose();
//            }
//        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        if (!jTextFieldNroDocumento.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty() && !jTextFieldApellido.getText().isEmpty() && !jTextFieldDomicilio.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Guardar Datos?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                clientes.setDni(jTextFieldNroDocumento.getText());
                clientes.setNombre(jTextFieldNombre.getText());
                clientes.setApellido(jTextFieldApellido.getText());
                clientes.setDireccion(jTextFieldDomicilio.getText());
                clientes.setTelefono(jTextFieldTelefono.getText());
                if (jTextFieldEmail.getText().isEmpty()) {
                    clientes.setEmail("-");
                } else {
                    clientes.setEmail(jTextFieldEmail.getText());
                }
                clientes.setIdcliente(Integer.parseInt(idcliente));
                if (cont_cliente.EditarClientes(clientes)) {
                    JOptionPane.showMessageDialog(null, "Modificación Completa");
                    lista = new vLista_Clientes();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jBotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonCancelarActionPerformed
        // TODO add your handling code here:
        int i = JOptionPane.showConfirmDialog(null, "Desea cancelar la Operación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            lista = new vLista_Clientes();
            vMenuPrincipal.jDesktopPane1.add(lista);
            lista.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jBotonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonCancelar;
    public static javax.swing.JButton jButtonAgregar;
    public static javax.swing.JButton jButtonModificar;
    private javax.swing.JLabel jLabelApellido;
    private javax.swing.JLabel jLabelDomicilio;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNroDocumento;
    private javax.swing.JLabel jLabelTelefono;
    public static final javax.swing.JTextField jTextFieldApellido = new javax.swing.JTextField();
    public static final javax.swing.JTextField jTextFieldDomicilio = new javax.swing.JTextField();
    public static final javax.swing.JTextField jTextFieldEmail = new javax.swing.JTextField();
    public static final javax.swing.JTextField jTextFieldNombre = new javax.swing.JTextField();
    public static javax.swing.JTextField jTextFieldNroDocumento;
    public static final javax.swing.JTextField jTextFieldTelefono = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}
