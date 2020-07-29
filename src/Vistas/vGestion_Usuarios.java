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
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox<>();

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
        jTextLogin_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextLogin_UsuarioKeyTyped(evt);
            }
        });

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
        jTextDireccion_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDireccion_UsuarioKeyTyped(evt);
            }
        });

        jEtiqtMail_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqtMail_Usuario.setText("E-mail:");

        jTextMail_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextMail_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextMail_UsuarioKeyTyped(evt);
            }
        });

        jEtiqTel_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqTel_Usuario.setText("Teléfono:");

        jTextTel_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextTel_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextTel_UsuarioKeyTyped(evt);
            }
        });

        jComboEstado_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboEstado_Usuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Estado", "PERMITIDO", "NO PERMITIDO" }));

        jBotonAgre_Usuario.setBackground(new java.awt.Color(252, 240, 0));
        jBotonAgre_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgre_Usuario.setText("Agregar");
        jBotonAgre_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgre_UsuarioActionPerformed(evt);
            }
        });

        jBotonModif_Usuario.setBackground(new java.awt.Color(252, 240, 0));
        jBotonModif_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModif_Usuario.setText("Modificar");
        jBotonModif_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModif_UsuarioActionPerformed(evt);
            }
        });

        jTextPass_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextPass_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPass_UsuarioKeyTyped(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCheckBox1.setText("Ver Contraseña");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(*) Seleccione Tipo Usuario" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBotonAgre_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(199, 199, 199)
                                .addComponent(jBotonModif_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jEtiqNom_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(262, 262, 262)
                                    .addComponent(jEtiqApe_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextNom_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(82, 82, 82)
                                    .addComponent(jTextApe_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextTel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(86, 86, 86)
                                    .addComponent(jTextMail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jEtiqLogin_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextLogin_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(82, 82, 82)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jEtiqPass_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextPass_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(342, 342, 342)
                                .addComponent(jCheckBox1))
                            .addComponent(jEtiqDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jEtiqTel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(276, 276, 276)
                                .addComponent(jEtiqtMail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboEstado_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jEtiqNom_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jEtiqApe_Usuario)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextNom_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextApe_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jEtiqLogin_Usuario)
                                .addGap(6, 6, 6)
                                .addComponent(jTextLogin_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jEtiqPass_Usuario)
                                .addGap(6, 6, 6)
                                .addComponent(jTextPass_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2)
                        .addComponent(jCheckBox1)
                        .addGap(0, 0, 0)
                        .addComponent(jEtiqDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextDireccion_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jEtiqTel_Usuario)
                            .addComponent(jEtiqtMail_Usuario)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(jComboEstado_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextTel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextMail_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBotonAgre_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBotonModif_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextNom_UsuarioKeyTyped

    private void jTextApe_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextApe_UsuarioKeyTyped
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
    }//GEN-LAST:event_jTextApe_UsuarioKeyTyped

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jTextPass_Usuario.setEchoChar((char) 0);
        } else {
            jTextPass_Usuario.setEchoChar('\u25cf');
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jTextLogin_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextLogin_UsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextLogin_UsuarioKeyTyped

    private void jTextPass_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPass_UsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextPass_UsuarioKeyTyped

    private void jTextDireccion_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDireccion_UsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextDireccion_UsuarioKeyTyped

    private void jTextTel_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTel_UsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextTel_UsuarioKeyTyped

    private void jTextMail_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextMail_UsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextMail_UsuarioKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonAgre_Usuario;
    public static javax.swing.JButton jBotonModif_Usuario;
    private javax.swing.JCheckBox jCheckBox1;
    public static javax.swing.JComboBox<String> jComboBox1;
    public static javax.swing.JComboBox<String> jComboEstado_Usuario;
    private javax.swing.JLabel jEtiqApe_Usuario;
    private javax.swing.JLabel jEtiqDireccion_Usuario;
    private javax.swing.JLabel jEtiqLogin_Usuario;
    private javax.swing.JLabel jEtiqNom_Usuario;
    private javax.swing.JLabel jEtiqPass_Usuario;
    private javax.swing.JLabel jEtiqTel_Usuario;
    private javax.swing.JLabel jEtiqtMail_Usuario;
    public static javax.swing.JTextField jTextApe_Usuario;
    public static javax.swing.JTextField jTextDireccion_Usuario;
    public static javax.swing.JTextField jTextLogin_Usuario;
    public static javax.swing.JTextField jTextMail_Usuario;
    public static javax.swing.JTextField jTextNom_Usuario;
    public static javax.swing.JPasswordField jTextPass_Usuario;
    public static javax.swing.JTextField jTextTel_Usuario;
    // End of variables declaration//GEN-END:variables
}
