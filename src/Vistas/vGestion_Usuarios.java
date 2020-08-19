package Vistas;

import Controlador.control_Usuario;
import Controlador.control_existencias;
import Modelo.Usuarios;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Usuarios extends javax.swing.JInternalFrame {

    control_Usuario user = new control_Usuario();
    control_existencias combo = new control_existencias();
    Usuarios usuario = new Usuarios();
    Object[] tipouser;
    String iduser;
    vLista_Usuarios lista = null;

    public vGestion_Usuarios() {
        initComponents();
        ComboTipoUsuario();
        EliminarItemsVacios();
        jTextNom_Usuario.requestFocus();
        jBotonModif_Usuario.setEnabled(false);
    }

    public void Limpiar() {
        jTextNom_Usuario.setText("");
        jTextApe_Usuario.setText("");
        jTextDireccion_Usuario.setText("");
        jTextTel_Usuario.setText("");
        jTextMail_Usuario.setText("");
        jTextLogin_Usuario.setText("");
        jTextPass_Usuario.setText("");
        jComboBox1.setSelectedItem("(*) Seleccione Tipo Usuario");
        jComboEstado_Usuario.setSelectedItem("Seleccione Estado");
    }

    public void ComboTipoUsuario() {
        tipouser = combo.combox("tiposusuarios", "descripcion");
        for (Object tipousuario : tipouser) {
            jComboBox1.addItem((String) tipousuario);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i) == null) {
                jComboBox1.removeItemAt(i);
            }
        }
    }

    public void Cerrar() {
        if (!jComboBox1.getSelectedItem().equals("(*) Seleccione Tipo Usuario") || !jTextApe_Usuario.getText().isEmpty() || !jTextNom_Usuario.getText().trim().isEmpty() || !jTextApe_Usuario.getText().trim().isEmpty() || !jTextLogin_Usuario.getText().trim().isEmpty() || !jTextPass_Usuario.getText().trim().isEmpty() || !jTextDireccion_Usuario.getText().isEmpty() || !jTextTel_Usuario.getText().isEmpty() || !jTextMail_Usuario.getText().isEmpty()) {
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

        jEtiqLogin_Usuario = new javax.swing.JLabel();
        jTextLogin_Usuario = new javax.swing.JTextField();
        jEtiqPass_Usuario = new javax.swing.JLabel();
        jEtiqNom_Usuario = new javax.swing.JLabel();
        jTextNom_Usuario = new javax.swing.JTextField();
        jEtiqApe_Usuario = new javax.swing.JLabel();
        jTextApe_Usuario = new javax.swing.JTextField();
        jEtiqDireccion_Usuario = new javax.swing.JLabel();
        jTextDireccion_Usuario = new javax.swing.JTextField();
        jEtiqtMail_Usuario = new javax.swing.JLabel();
        jTextMail_Usuario = new javax.swing.JTextField();
        jEtiqTel_Usuario = new javax.swing.JLabel();
        jTextTel_Usuario = new javax.swing.JTextField();
        jComboEstado_Usuario = new javax.swing.JComboBox<>();
        jBotonAgre_Usuario = new javax.swing.JButton();
        jBotonModif_Usuario = new javax.swing.JButton();
        jTextPass_Usuario = new javax.swing.JPasswordField();
        jCheckBoxVerContraseña = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox<>();
        jBotonCancelar = new javax.swing.JButton();
        jEtiqtMail_Usuario1 = new javax.swing.JLabel();
        jEtiqtMail_Usuario2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Usuarios");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jEtiqLogin_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqLogin_Usuario.setText("(*) Usuario:");

        jTextLogin_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jEtiqPass_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqPass_Usuario.setText("(*) Contraseña:");

        jEtiqNom_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqNom_Usuario.setText("(*) Nombres:");

        jTextNom_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextNom_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNom_UsuarioKeyTyped(evt);
            }
        });

        jEtiqApe_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqApe_Usuario.setText("Apellidos:");

        jTextApe_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextApe_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextApe_UsuarioKeyTyped(evt);
            }
        });

        jEtiqDireccion_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqDireccion_Usuario.setText("Dirección:");

        jTextDireccion_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jEtiqtMail_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqtMail_Usuario.setText("E-mail:");

        jTextMail_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jEtiqTel_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqTel_Usuario.setText("Teléfono:");

        jTextTel_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jComboEstado_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboEstado_Usuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Estado", "PERMITIDO", "NO PERMITIDO" }));

        jBotonAgre_Usuario.setBackground(new java.awt.Color(252, 249, 57));
        jBotonAgre_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgre_Usuario.setText("Agregar");
        jBotonAgre_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgre_UsuarioActionPerformed(evt);
            }
        });

        jBotonModif_Usuario.setBackground(new java.awt.Color(252, 249, 57));
        jBotonModif_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModif_Usuario.setText("Modificar");
        jBotonModif_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModif_UsuarioActionPerformed(evt);
            }
        });

        jTextPass_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jCheckBoxVerContraseña.setBackground(new java.awt.Color(255, 248, 177));
        jCheckBoxVerContraseña.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCheckBoxVerContraseña.setText("Ver Contraseña");
        jCheckBoxVerContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxVerContraseñaActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccione Tipo..." }));

        jBotonCancelar.setBackground(new java.awt.Color(237, 124, 61));
        jBotonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonCancelar.setText("Cancelar");
        jBotonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonCancelarActionPerformed(evt);
            }
        });

        jEtiqtMail_Usuario1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqtMail_Usuario1.setText("Tipo de Usuario:");

        jEtiqtMail_Usuario2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqtMail_Usuario2.setText("Estado:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jEtiqDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jEtiqNom_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jEtiqLogin_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextLogin_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jTextNom_Usuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jEtiqTel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextTel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jEtiqtMail_Usuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDireccion_Usuario))
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(309, 309, 309)
                                .addComponent(jCheckBoxVerContraseña)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(213, 213, 213)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextPass_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jEtiqPass_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jEtiqApe_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextApe_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(1, 1, 1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jEtiqtMail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextMail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboEstado_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jEtiqtMail_Usuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jBotonAgre_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBotonModif_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)
                                .addComponent(jBotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(67, 67, 67))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jEtiqNom_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jEtiqApe_Usuario))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextNom_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextApe_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jEtiqLogin_Usuario)
                        .addGap(6, 6, 6)
                        .addComponent(jTextLogin_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jEtiqPass_Usuario)
                        .addGap(6, 6, 6)
                        .addComponent(jTextPass_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addComponent(jCheckBoxVerContraseña)
                .addGap(8, 8, 8)
                .addComponent(jEtiqDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jEtiqTel_Usuario)
                    .addComponent(jEtiqtMail_Usuario))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextTel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextMail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jEtiqtMail_Usuario1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jEtiqtMail_Usuario2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEstado_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBotonAgre_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonModif_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgre_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgre_UsuarioActionPerformed
        if (jBotonAgre_Usuario.getText().equals("Agregar")) {
            if (!jTextLogin_Usuario.getText().trim().equals("") && !jTextPass_Usuario.getText().trim().equals("") && !jTextNom_Usuario.getText().trim().equals("") && !jComboBox1.getSelectedItem().equals("(*) Seleccione Tipo Usuario")) {
                usuario.setIdtipousuario(user.ObtenerIDTipoUsuario(jComboBox1.getSelectedItem().toString()));
                usuario.setLogin(jTextLogin_Usuario.getText());
                usuario.setPassword(jTextPass_Usuario.getText());
                usuario.setNombre(jTextNom_Usuario.getText());
                if(!jTextApe_Usuario.getText().equals("")){
                    usuario.setApellido(jTextApe_Usuario.getText());
                } else {
                    usuario.setApellido("-");
                }
                if (jTextDireccion_Usuario.getText().equals("")) {
                    usuario.setDireccion("-");
                } else {
                    usuario.setDireccion(jTextDireccion_Usuario.getText());
                }
                if (jTextMail_Usuario.getText().equals("")) {
                    usuario.setEmail("-");
                } else {
                    usuario.setEmail(jTextMail_Usuario.getText());
                }
                if (jTextTel_Usuario.getText().equals("")) {
                    usuario.setTelefono("-");
                } else {
                    usuario.setTelefono(jTextTel_Usuario.getText());
                }
                if (jComboEstado_Usuario.getSelectedItem().equals("Seleccione Estado")) {
                    usuario.setEstado(jComboEstado_Usuario.getItemAt(1));
                } else {
                    usuario.setEstado(jComboEstado_Usuario.getSelectedItem().toString());
                }
                if (user.InsertarUsuarios(usuario)) {
                    JOptionPane.showMessageDialog(null, "Nuevo Usuario agregado");
                    lista = new vLista_Usuarios();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    lista.Mostrar();
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Usuarios();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                this.dispose();
            }
        }

    }//GEN-LAST:event_jBotonAgre_UsuarioActionPerformed

    private void jBotonModif_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModif_UsuarioActionPerformed
        if (!jTextLogin_Usuario.getText().trim().equals("") && !jTextPass_Usuario.getText().trim().equals("") && !jTextNom_Usuario.getText().trim().equals("") && !jComboBox1.getSelectedItem().equals("(*) Seleccione Tipo Usuario")) {
            int i = JOptionPane.showConfirmDialog(null, "Guardar Datos?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                usuario.setIdtipousuario(user.ObtenerIDTipoUsuario(jComboBox1.getSelectedItem().toString()));
                usuario.setLogin(jTextLogin_Usuario.getText());
                usuario.setPassword(jTextPass_Usuario.getText());
                usuario.setNombre(jTextNom_Usuario.getText());
                if(!jTextApe_Usuario.getText().equals("")){
                    usuario.setApellido(jTextApe_Usuario.getText());
                } else {
                    usuario.setApellido("-");
                }
                if (jTextDireccion_Usuario.getText().equals("")) {
                    usuario.setDireccion("-");
                } else {
                    usuario.setDireccion(jTextDireccion_Usuario.getText());
                }
                if (jTextMail_Usuario.getText().equals("")) {
                    usuario.setEmail("-");
                } else {
                    usuario.setEmail(jTextMail_Usuario.getText());
                }
                if (jTextTel_Usuario.getText().equals("")) {
                    usuario.setTelefono("-");
                } else {
                    usuario.setTelefono(jTextTel_Usuario.getText());
                }
                if (jComboEstado_Usuario.getSelectedItem().equals("Seleccione Estado")) {
                    usuario.setEstado(jComboEstado_Usuario.getItemAt(1));
                } else {
                    usuario.setEstado(jComboEstado_Usuario.getSelectedItem().toString());
                }
                usuario.setIdusuario(Integer.parseInt(iduser));
                if (user.EditarUsuarios(usuario)) {
                    JOptionPane.showMessageDialog(null, "Modificacion Completa");
                    jBotonModif_Usuario.setEnabled(false);
                    jBotonAgre_Usuario.setEnabled(true);
                    lista = new vLista_Usuarios();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jBotonModif_UsuarioActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jBotonAgre_Usuario.getText().trim().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                lista = new vLista_Usuarios();
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

    private void jTextNom_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNom_UsuarioKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextNom_UsuarioKeyTyped

    private void jTextApe_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextApe_UsuarioKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextApe_UsuarioKeyTyped

    private void jCheckBoxVerContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxVerContraseñaActionPerformed
        if (jCheckBoxVerContraseña.isSelected()) {
            jTextPass_Usuario.setEchoChar((char) 0);
        } else {
            jTextPass_Usuario.setEchoChar('\u25cf');
        }
    }//GEN-LAST:event_jCheckBoxVerContraseñaActionPerformed

    private void jBotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jBotonCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonAgre_Usuario;
    public static javax.swing.JButton jBotonCancelar;
    public static javax.swing.JButton jBotonModif_Usuario;
    private javax.swing.JCheckBox jCheckBoxVerContraseña;
    public static javax.swing.JComboBox<String> jComboBox1;
    public static javax.swing.JComboBox<String> jComboEstado_Usuario;
    private javax.swing.JLabel jEtiqApe_Usuario;
    private javax.swing.JLabel jEtiqDireccion_Usuario;
    private javax.swing.JLabel jEtiqLogin_Usuario;
    private javax.swing.JLabel jEtiqNom_Usuario;
    private javax.swing.JLabel jEtiqPass_Usuario;
    private javax.swing.JLabel jEtiqTel_Usuario;
    private javax.swing.JLabel jEtiqtMail_Usuario;
    private javax.swing.JLabel jEtiqtMail_Usuario1;
    private javax.swing.JLabel jEtiqtMail_Usuario2;
    public static javax.swing.JTextField jTextApe_Usuario;
    public static javax.swing.JTextField jTextDireccion_Usuario;
    public static javax.swing.JTextField jTextLogin_Usuario;
    public static javax.swing.JTextField jTextMail_Usuario;
    public static javax.swing.JTextField jTextNom_Usuario;
    public static javax.swing.JPasswordField jTextPass_Usuario;
    public static javax.swing.JTextField jTextTel_Usuario;
    // End of variables declaration//GEN-END:variables
}
