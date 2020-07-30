package Vistas;

import Controlador.control_Proveedores;
import Controlador.control_existencias;
import Modelo.Proveedores;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Proveedores extends javax.swing.JInternalFrame {

    control_Proveedores control_prov = new control_Proveedores();
    control_existencias combo = new control_existencias();
    Proveedores p = new Proveedores();
    String idprov;
    Object[] tipodoc;
    Object[][] datostabla;
    vLista_Proveedores lista = null;

    public vGestion_Proveedores() {
        initComponents();
        jTextNom_Prov.requestFocus();
        jBotonModificar_Prov.setEnabled(false);
    }

    public void Limpiar() {
        jTextNom_Prov.setText("");
        jTextApe_Prov.setText("");
        jTextNomCom_Prov.setText("");
        jTextDirec_Prov.setText("");
        jTextTel_Prov.setText("");
        jTextNroDoc_Prov.setText("");
    }

    public void Cerrar() {
        if (!jTextNom_Prov.getText().trim().isEmpty() || !jTextApe_Prov.getText().trim().isEmpty() || !jTextNomCom_Prov.getText().trim().isEmpty() || !jTextNroDoc_Prov.getText().trim().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEtiqNom_Prov = new javax.swing.JLabel();
        jTextNom_Prov = new javax.swing.JTextField();
        jEtiqApe_Prov = new javax.swing.JLabel();
        jTextApe_Prov = new javax.swing.JTextField();
        jEtiqNomCom_Prov = new javax.swing.JLabel();
        jTextNomCom_Prov = new javax.swing.JTextField();
        jEtiqDirec_Prov = new javax.swing.JLabel();
        jTextDirec_Prov = new javax.swing.JTextField();
        jEtiqTel_Prov = new javax.swing.JLabel();
        jTextTel_Prov = new javax.swing.JTextField();
        jTextNroDoc_Prov = new javax.swing.JTextField();
        jEtiqNumDoc_Prov = new javax.swing.JLabel();
        jBotonAgre_Prov2 = new javax.swing.JButton();
        jBotonModificar_Prov = new javax.swing.JButton();
        jBotonCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Proveedores");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVerifyInputWhenFocusTarget(false);
        setVisible(true);
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

        jEtiqNom_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqNom_Prov.setText("(*) Nombres:");

        jTextNom_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextNom_Prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNom_ProvKeyTyped(evt);
            }
        });

        jEtiqApe_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqApe_Prov.setText("Apellidos:");

        jTextApe_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextApe_Prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextApe_ProvKeyTyped(evt);
            }
        });

        jEtiqNomCom_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqNomCom_Prov.setText("(*) Nombre Comercial:");

        jTextNomCom_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jEtiqDirec_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqDirec_Prov.setText("Dirección:");

        jTextDirec_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jEtiqTel_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqTel_Prov.setText("Teléfono:");

        jTextTel_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jTextNroDoc_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextNroDoc_Prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNroDoc_ProvKeyTyped(evt);
            }
        });

        jEtiqNumDoc_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqNumDoc_Prov.setText(" Número Documento:");

        jBotonAgre_Prov2.setBackground(new java.awt.Color(252, 249, 57));
        jBotonAgre_Prov2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgre_Prov2.setText("Agregar");
        jBotonAgre_Prov2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgre_Prov2ActionPerformed(evt);
            }
        });

        jBotonModificar_Prov.setBackground(new java.awt.Color(252, 249, 57));
        jBotonModificar_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModificar_Prov.setText("Modificar");
        jBotonModificar_Prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModificar_ProvActionPerformed(evt);
            }
        });

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBotonAgre_Prov2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jBotonModificar_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jBotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextDirec_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jEtiqDirec_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextNomCom_Prov)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jEtiqNumDoc_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextNroDoc_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jEtiqNom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextNom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jEtiqNomCom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jEtiqApe_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextApe_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jEtiqTel_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextTel_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jEtiqApe_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextApe_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jEtiqTel_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTextTel_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jEtiqNom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextNom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jEtiqNumDoc_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTextNroDoc_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addComponent(jEtiqNomCom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextNomCom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jEtiqDirec_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextDirec_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBotonAgre_Prov2)
                    .addComponent(jBotonModificar_Prov)
                    .addComponent(jBotonCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgre_Prov2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgre_Prov2ActionPerformed
        if (jBotonAgre_Prov2.getText().equals("Agregar")) {
            if (!jTextNom_Prov.getText().trim().equals("") && !jTextNomCom_Prov.getText().trim().equals("")) {
                if (jTextNroDoc_Prov.getText().length() <= 10) {
                    p.setIdTipoDocumento(1);
                    p.setNombre(jTextNom_Prov.getText());
                    if (jTextNroDoc_Prov.getText().equals("")) {
                        p.setNroDocumento("-");
                    } else {
                        p.setNroDocumento(jTextNroDoc_Prov.getText());
                    }
                    if (!jTextApe_Prov.getText().equals("")) {
                        p.setApellido(jTextApe_Prov.getText());
                    } else {
                        p.setApellido("-");
                    }
                    p.setNombre_comercial(jTextNomCom_Prov.getText());
                    if (jTextDirec_Prov.getText().equals("")) {
                        p.setDireccion("-");
                    } else {
                        p.setDireccion(jTextDirec_Prov.getText());
                    }
                    if (jTextTel_Prov.getText().equals("")) {
                        p.setTelefono("-");
                    } else {
                        p.setTelefono(jTextTel_Prov.getText());
                    }
                    if (control_prov.InsertarProveedores(p)) {
                        JOptionPane.showMessageDialog(null, "Nuevo Proveedor agreado");
                        lista = new vLista_Proveedores();
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
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Proveedores();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                this.dispose();
            }
        }

    }//GEN-LAST:event_jBotonAgre_Prov2ActionPerformed

    private void jBotonModificar_ProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModificar_ProvActionPerformed
        if (!jTextNom_Prov.getText().trim().equals("") && !jTextNomCom_Prov.getText().trim().equals("")) {
            if (jTextNroDoc_Prov.getText().length() <= 10) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    p.setIdTipoDocumento(1);
                    p.setNombre(jTextNom_Prov.getText());
                    if (jTextNroDoc_Prov.getText().equals("")) {
                        p.setNroDocumento("-");
                    } else {
                        p.setNroDocumento(jTextNroDoc_Prov.getText());
                    }
                    if (!jTextApe_Prov.getText().equals("")) {
                        p.setApellido(jTextApe_Prov.getText());
                    } else {
                        p.setApellido("-");
                    }
                    p.setNombre_comercial(jTextNomCom_Prov.getText());
                    if (jTextDirec_Prov.getText().equals("")) {
                        p.setDireccion("-");
                    } else {
                        p.setDireccion(jTextDirec_Prov.getText());
                    }
                    if (jTextTel_Prov.getText().equals("")) {
                        p.setTelefono("-");
                    } else {
                        p.setTelefono(jTextTel_Prov.getText());
                    }
                    p.setIdproveedor(Integer.parseInt(idprov));

                    if (control_prov.EditarProveedores(p)) {
                        JOptionPane.showMessageDialog(null, "Modificacion Completa");
                        jBotonModificar_Prov.setEnabled(false);
                        jBotonAgre_Prov2.setEnabled(true);
                        lista = new vLista_Proveedores();
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
    }//GEN-LAST:event_jBotonModificar_ProvActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jBotonAgre_Prov2.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Proveedores();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextNom_ProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNom_ProvKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextNom_ProvKeyTyped

    private void jTextApe_ProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextApe_ProvKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextApe_ProvKeyTyped

    private void jTextNroDoc_ProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNroDoc_ProvKeyTyped
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
    }//GEN-LAST:event_jTextNroDoc_ProvKeyTyped

    private void jBotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jBotonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonAgre_Prov2;
    public static javax.swing.JButton jBotonCancelar;
    public static javax.swing.JButton jBotonModificar_Prov;
    private javax.swing.JLabel jEtiqApe_Prov;
    private javax.swing.JLabel jEtiqDirec_Prov;
    private javax.swing.JLabel jEtiqNomCom_Prov;
    private javax.swing.JLabel jEtiqNom_Prov;
    private javax.swing.JLabel jEtiqNumDoc_Prov;
    private javax.swing.JLabel jEtiqTel_Prov;
    public static javax.swing.JTextField jTextApe_Prov;
    public static javax.swing.JTextField jTextDirec_Prov;
    public static javax.swing.JTextField jTextNomCom_Prov;
    public static javax.swing.JTextField jTextNom_Prov;
    public static javax.swing.JTextField jTextNroDoc_Prov;
    public static javax.swing.JTextField jTextTel_Prov;
    // End of variables declaration//GEN-END:variables
}
