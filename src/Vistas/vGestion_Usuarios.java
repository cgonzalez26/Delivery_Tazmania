/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.control_Usuario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Colo-PC
 */
public class vGestion_Usuarios extends javax.swing.JPanel {

    /**
     * Creates new form Panel4
     */
    public vGestion_Usuarios() {
        initComponents();
        Mostrar();
        ocultar_columnas();
        jTextNombre_Usuario.requestFocus();
        this.jEtiqID_Usuario.setVisible(false);
        this.jTextID_Usuario.setVisible(false);
        this.jBotonModif_Usuario.setEnabled(false);
        this.jBotonElim_Usuario.setEnabled(false);
        this.jBotonCancel_Usuario.setVisible(false);

        jTextID_Usuario.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE)) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });

        jTextNombre_Usuario.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if ((Character.isDigit(c))) {
                    getToolkit().beep();
                    e.consume();
                }
            }

        });
        
        
        
        jTabla_Usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {

                    int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Modificar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        jEtiqID_Usuario.setVisible(true);
                        jTextID_Usuario.setVisible(true);
                        jBotonModif_Usuario.setEnabled(true);
                        jBotonElim_Usuario.setEnabled(true);
                        jBotonAcep_Usuario.setEnabled(false);
                        jBotonCancel_Usuario.setVisible(true);

                        int fila = jTabla_Usuario.rowAtPoint(e.getPoint());
                        jTextID_Usuario.setText(jTabla_Usuario.getValueAt(fila, 0).toString());
                        jTextNombre_Usuario.setText(jTabla_Usuario.getValueAt(fila, 1).toString());
                        jTextApellido_Usuario.setText(jTabla_Usuario.getValueAt(fila, 2).toString());
                        jTextDomicilio_Usuario.setText(jTabla_Usuario.getValueAt(fila, 3).toString());
                        jTextTelefono_Usuario.setText(jTabla_Usuario.getValueAt(fila, 4).toString());
                        jTextCorreo_Usuario.setText(jTabla_Usuario.getValueAt(fila, 5).toString());
                        jTextNomUser_Usuario.setText(jTabla_Usuario.getValueAt(fila, 6).toString());
                        jTextPass_Usuario.setText(jTabla_Usuario.getValueAt(fila, 7).toString());
                        jComboAcceso_Usuario.setSelectedItem(jTabla_Usuario.getValueAt(fila, 8).toString());
                        jComboEstado_Usuario.setSelectedItem(jTabla_Usuario.getValueAt(fila, 9).toString());
                    } else {
                        jBotonAcep_Usuario.setEnabled(true);
                        jEtiqID_Usuario.setVisible(false);
                        jTextID_Usuario.setVisible(false);
                        jBotonModif_Usuario.setEnabled(false);
                        jBotonElim_Usuario.setEnabled(false);
                    }
                }
            }
        });
    }
    
        public void ocultar_columnas() {
        jTabla_Usuario.getColumnModel().getColumn(5).setMaxWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(5).setMinWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(5).setPreferredWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(7).setMaxWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(7).setMinWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(7).setPreferredWidth(0);
    }

    public void Mostrar() {
        try {
            DefaultTableModel model;
            control_Usuario obtdatos = new control_Usuario();
            model = obtdatos.MostrarDatos();

            jTabla_Usuario.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Limpiar() {
        jTextNombre_Usuario.setText("");
        jTextApellido_Usuario.setText("");
        jTextDomicilio_Usuario.setText("");
        jTextTelefono_Usuario.setText("");
        jTextCorreo_Usuario.setText("");
        jTextNomUser_Usuario.setText("");
        jTextPass_Usuario.setText("");
        jTextID_Usuario.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LTit_Usuario = new javax.swing.JLabel();
        jTextNombre_Usuario = new javax.swing.JTextField();
        jTextApellido_Usuario = new javax.swing.JTextField();
        jTextDomicilio_Usuario = new javax.swing.JTextField();
        jTextTelefono_Usuario = new javax.swing.JTextField();
        jTextCorreo_Usuario = new javax.swing.JTextField();
        jTextNomUser_Usuario = new javax.swing.JTextField();
        jEtiqNombre_Usuario = new javax.swing.JLabel();
        jEtiqApellido_Usuario = new javax.swing.JLabel();
        jEtiqDomicilio_Usuario = new javax.swing.JLabel();
        jEtiqTelefono_Usuario = new javax.swing.JLabel();
        jEtiqCorreo_Usuario = new javax.swing.JLabel();
        jEtiqNomUser_Usuario = new javax.swing.JLabel();
        jEtiqPass_Usuario = new javax.swing.JLabel();
        jBotonAcep_Usuario = new javax.swing.JButton();
        jBotonSalir_Usuario = new javax.swing.JButton();
        jComboAcceso_Usuario = new javax.swing.JComboBox<>();
        jEtiqAcceso_Usuario = new javax.swing.JLabel();
        jEtiqEstado_Usuario = new javax.swing.JLabel();
        jComboEstado_Usuario = new javax.swing.JComboBox<>();
        jBotonModif_Usuario = new javax.swing.JButton();
        jTextPass_Usuario = new javax.swing.JTextField();
        jTextID_Usuario = new javax.swing.JTextField();
        jEtiqID_Usuario = new javax.swing.JLabel();
        jBotonElim_Usuario = new javax.swing.JButton();
        jBotonCancel_Usuario = new javax.swing.JButton();
        jSP1_Usuario = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_Usuario = jTabla_Usuario = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LTit_Usuario.setFont(new java.awt.Font("Eras Demi ITC", 0, 18)); // NOI18N
        LTit_Usuario.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        LTit_Usuario.setText("Gestion de Usuarios");
        LTit_Usuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        add(LTit_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 180, 30));

        jTextNombre_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextNombre_UsuarioKeyPressed(evt);
            }
        });
        add(jTextNombre_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 110, -1));

        jTextApellido_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextApellido_UsuarioKeyPressed(evt);
            }
        });
        add(jTextApellido_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 110, -1));

        jTextDomicilio_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDomicilio_UsuarioKeyPressed(evt);
            }
        });
        add(jTextDomicilio_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 290, -1));

        jTextTelefono_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextTelefono_UsuarioKeyPressed(evt);
            }
        });
        add(jTextTelefono_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 220, -1));

        jTextCorreo_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextCorreo_UsuarioKeyPressed(evt);
            }
        });
        add(jTextCorreo_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 270, -1));

        jTextNomUser_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextNomUser_UsuarioKeyPressed(evt);
            }
        });
        add(jTextNomUser_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 140, -1));

        jEtiqNombre_Usuario.setText("Nombre");
        add(jEtiqNombre_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 80, 20));

        jEtiqApellido_Usuario.setText("Apellido");
        add(jEtiqApellido_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 70, 20));

        jEtiqDomicilio_Usuario.setText("Domicilio");
        add(jEtiqDomicilio_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 20));

        jEtiqTelefono_Usuario.setText("Telefono - Contacto");
        add(jEtiqTelefono_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 140, 20));

        jEtiqCorreo_Usuario.setText("Correo Electronico - Contacto");
        add(jEtiqCorreo_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 170, 20));

        jEtiqNomUser_Usuario.setText("Usuario");
        add(jEtiqNomUser_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 60, 20));

        jEtiqPass_Usuario.setText("Contraseña");
        add(jEtiqPass_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 60, 20));

        jBotonAcep_Usuario.setText("Agregar");
        jBotonAcep_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAcep_UsuarioActionPerformed(evt);
            }
        });
        jBotonAcep_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBotonAcep_UsuarioKeyPressed(evt);
            }
        });
        add(jBotonAcep_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 80, 30));

        jBotonSalir_Usuario.setText("Salir");
        jBotonSalir_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonSalir_UsuarioActionPerformed(evt);
            }
        });
        jBotonSalir_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBotonSalir_UsuarioKeyPressed(evt);
            }
        });
        add(jBotonSalir_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 430, 80, 30));

        jComboAcceso_Usuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuario Común" }));
        jComboAcceso_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboAcceso_UsuarioKeyPressed(evt);
            }
        });
        add(jComboAcceso_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 120, 20));

        jEtiqAcceso_Usuario.setText("Acceso");
        add(jEtiqAcceso_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 70, 20));

        jEtiqEstado_Usuario.setText("Estado");
        add(jEtiqEstado_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 50, 20));

        jComboEstado_Usuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        add(jComboEstado_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 120, -1));

        jBotonModif_Usuario.setText("Modificar");
        jBotonModif_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModif_UsuarioActionPerformed(evt);
            }
        });
        jBotonModif_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBotonModif_UsuarioKeyPressed(evt);
            }
        });
        add(jBotonModif_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, -1, 30));

        jTextPass_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPass_UsuarioKeyPressed(evt);
            }
        });
        add(jTextPass_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 270, -1));
        add(jTextID_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 30, 20));

        jEtiqID_Usuario.setText("Seleccione N°");
        add(jEtiqID_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, -1, 20));

        jBotonElim_Usuario.setText("Eliminar");
        jBotonElim_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonElim_UsuarioActionPerformed(evt);
            }
        });
        jBotonElim_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBotonElim_UsuarioKeyPressed(evt);
            }
        });
        add(jBotonElim_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, -1, 30));

        jBotonCancel_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonCancel_Usuario.setText("Cancelar");
        jBotonCancel_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonCancel_UsuarioActionPerformed(evt);
            }
        });
        add(jBotonCancel_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 80, 30));

        jSP1_Usuario.setFocusable(false);

        jTabla_Usuario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTabla_Usuario);

        jSP1_Usuario.setViewportView(jScrollPane1);

        add(jSP1_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 730, 410));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextNombre_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNombre_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jTextApellido_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTextNombre_UsuarioKeyPressed

    private void jTextApellido_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextApellido_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jTextDomicilio_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTextApellido_UsuarioKeyPressed

    private void jTextDomicilio_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDomicilio_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jTextTelefono_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTextDomicilio_UsuarioKeyPressed

    private void jTextTelefono_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTelefono_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jTextCorreo_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTextTelefono_UsuarioKeyPressed

    private void jTextCorreo_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCorreo_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jTextNomUser_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTextCorreo_UsuarioKeyPressed

    private void jTextNomUser_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNomUser_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jTextPass_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTextNomUser_UsuarioKeyPressed

    private void jBotonAcep_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAcep_UsuarioActionPerformed

        control_Usuario funcion = new control_Usuario();

        if (jTextNombre_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre");
            jTextNombre_Usuario.requestFocus();
            return;
        }

        if (jTextApellido_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Apellido");
            jTextApellido_Usuario.requestFocus();
            return;
        }

        if (jTextDomicilio_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Domicilio");
            jTextDomicilio_Usuario.requestFocus();
            return;
        }

        if (jTextNomUser_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un nombre de Usuario");
            jTextNomUser_Usuario.requestFocus();
            return;
        }

        if (jTextPass_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una Contraseña");
            jTextPass_Usuario.requestFocus();
            return;
        }

        int cantidad = funcion.ExistenciaUsuarios();

        if (cantidad == 0) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos correctos!, Su Usuario es " + jTextNomUser_Usuario.getText() + " y su contraseña es " + jTextPass_Usuario.getText() + " ");

            vLogin login = new vLogin();
            login.setResizable(false);
            login.toFront();
            login.setLocationRelativeTo(null);
            login.setVisible(true);

            setVisible(false);
        }

        int dato = funcion.VerificarLogin();

        if (dato > 0) {
            JOptionPane.showMessageDialog(this,"Nombre de usuario '" + jTextNomUser_Usuario.getText() + "' ocupado");
            jTextDomicilio_Usuario.setText("");
            jTextCorreo_Usuario.setText("");
            jTextTelefono_Usuario.setText("");
            jTextNomUser_Usuario.setText("");
            jTextPass_Usuario.setText("");
            jTextNombre_Usuario.requestFocus();
            return;
        }

        funcion.setNombre_persona(jTextNombre_Usuario.getText());
        funcion.setApellido_persona(jTextApellido_Usuario.getText());
        funcion.setDomicilio_persona(jTextDomicilio_Usuario.getText());
        funcion.setTelefonoContacto_persona(jTextTelefono_Usuario.getText());
        funcion.setEmailContacto_persona(jTextCorreo_Usuario.getText());
        funcion.setNombre_usuario(jTextNomUser_Usuario.getText());
        funcion.setContraseña_usuario(jTextPass_Usuario.getText());

        int acceso = jComboAcceso_Usuario.getSelectedIndex();
        funcion.setTipo_usario((String) jComboAcceso_Usuario.getItemAt(acceso));

        int estado = jComboEstado_Usuario.getSelectedIndex();

        if (estado == 0) {
            boolean boolean1 = Boolean.parseBoolean("true");
            funcion.setEstado_usuario(boolean1);
        } else {
            boolean boolean2 = Boolean.parseBoolean("false");
            funcion.setEstado_usuario(boolean2);
        }

        if (funcion.InsertarUsuarios(funcion)) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos Correctos.");
            Mostrar();
            ocultar_columnas();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido ingresar los datos.");
        }
    }//GEN-LAST:event_jBotonAcep_UsuarioActionPerformed

    private void jBotonAcep_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBotonAcep_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){

            control_Usuario funcion = new control_Usuario();

            if (jTextNombre_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre");
                jTextNombre_Usuario.requestFocus();
                return;
            }

            if (jTextApellido_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un Apellido");
                jTextApellido_Usuario.requestFocus();
                return;
            }

            if (jTextDomicilio_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un Domicilio");
                jTextDomicilio_Usuario.requestFocus();
                return;
            }

            if (jTextNomUser_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un nombre de Usuario");
                jTextNomUser_Usuario.requestFocus();
                return;
            }

            if (jTextPass_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar una Contraseña");
                jTextPass_Usuario.requestFocus();
                return;
            }

            int cantidad = funcion.ExistenciaUsuarios();

            if (cantidad == 0) {
                JOptionPane.showMessageDialog(null, "Ingreso de datos correctos!, Su Usuario es " + jTextNomUser_Usuario.getText() + " y su contraseña es " + jTextPass_Usuario.getText() + " ");

                vLogin login = new vLogin();
                login.setResizable(false);
                login.toFront();
                login.setLocationRelativeTo(null);
                login.setVisible(true);

                setVisible(false);
            }

            int dato = funcion.VerificarLogin();

            if (dato > 0) {
                JOptionPane.showMessageDialog(this, "Nombre de usuario " + jTextNomUser_Usuario.getText() + " ocupado");
                jTextNomUser_Usuario.requestFocus();
                return;
            }

            funcion.setNombre_persona(jTextNombre_Usuario.getText());
            funcion.setApellido_persona(jTextApellido_Usuario.getText());
            funcion.setDomicilio_persona(jTextDomicilio_Usuario.getText());
            funcion.setTelefonoContacto_persona(jTextTelefono_Usuario.getText());
            funcion.setEmailContacto_persona(jTextCorreo_Usuario.getText());
            funcion.setNombre_usuario(jTextNomUser_Usuario.getText());
            funcion.setContraseña_usuario(jTextPass_Usuario.getText());

            int acceso = jComboAcceso_Usuario.getSelectedIndex();
            funcion.setTipo_usario((String) jComboAcceso_Usuario.getItemAt(acceso));

            int estado = jComboEstado_Usuario.getSelectedIndex();

            if (estado == 0) {
                boolean boolean1 = Boolean.parseBoolean("true");
                funcion.setEstado_usuario(boolean1);
            } else {
                boolean boolean2 = Boolean.parseBoolean("false");
                funcion.setEstado_usuario(boolean2);
            }

            if (funcion.InsertarUsuarios(funcion)) {
                JOptionPane.showMessageDialog(null, "Ingreso de datos Correctos.");
                Mostrar();
                ocultar_columnas();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido ingresar los datos.");
            }
        }
    }//GEN-LAST:event_jBotonAcep_UsuarioKeyPressed

    private void jBotonSalir_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonSalir_UsuarioActionPerformed

        vMenuPrincipal menu = new vMenuPrincipal();

        menu.setVisible(true);
        menu.setSize(1292, 662);
        menu.setResizable(false);
        menu.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_jBotonSalir_UsuarioActionPerformed

    private void jBotonSalir_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBotonSalir_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            vMenuPrincipal menu = new vMenuPrincipal();

            menu.setVisible(true);
            menu.setSize(1292, 662);
            menu.setResizable(false);
            menu.setLocationRelativeTo(null);

            this.setVisible(false);
        }
    }//GEN-LAST:event_jBotonSalir_UsuarioKeyPressed

    private void jComboAcceso_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboAcceso_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jComboEstado_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jComboAcceso_UsuarioKeyPressed

    private void jBotonModif_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModif_UsuarioActionPerformed

        control_Usuario funcion = new control_Usuario();

        if (jTextID_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° a modificar");
            return;
        }

        if (jTextNombre_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre");
            jTextNombre_Usuario.requestFocus();
            return;
        }

        if (jTextApellido_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar Apellido");
            jTextApellido_Usuario.requestFocus();
            return;
        }

        if (jTextDomicilio_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Domicilio");
            jTextDomicilio_Usuario.requestFocus();
            return;
        }

        if (jTextNomUser_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un nombre de Usuario");
            jTextNomUser_Usuario.requestFocus();
            return;
        }

        if (jTextPass_Usuario.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una contraseña");
            jTextPass_Usuario.requestFocus();
            return;
        }

        /*int dato = funcion.VerificarLogin();

        if (dato >= 1) {
            JOptionPane.showMessageDialog(null, "Nombre de usuario '" + jTextNomUser_Usuario.getText() + "' ocupado");
            jTextNomUser_Usuario.requestFocus();
            return;
        }*/

        funcion.setId_persona(Integer.parseInt(jTextID_Usuario.getText()));
        funcion.setNombre_persona(jTextNombre_Usuario.getText());
        funcion.setApellido_persona(jTextApellido_Usuario.getText());
        funcion.setDomicilio_persona(jTextDomicilio_Usuario.getText());
        funcion.setTelefonoContacto_persona(jTextTelefono_Usuario.getText());
        funcion.setEmailContacto_persona(jTextCorreo_Usuario.getText());
        funcion.setNombre_usuario(jTextNomUser_Usuario.getText());
        funcion.setContraseña_usuario(jTextPass_Usuario.getText());
        funcion.setId_usuario(Integer.parseInt(jTextID_Usuario.getText()));

        int acceso = jComboAcceso_Usuario.getSelectedIndex();
        funcion.setTipo_usario((String) jComboAcceso_Usuario.getItemAt(acceso));

        int estado = jComboEstado_Usuario.getSelectedIndex();

        if (estado == 0) {
            boolean boolean1 = Boolean.parseBoolean("true");
            funcion.setEstado_usuario(boolean1);
        } else {
            boolean boolean2 = Boolean.parseBoolean("false");
            funcion.setEstado_usuario(boolean2);
        }

        if (funcion.EditarUsuarios(funcion)) {
            JOptionPane.showMessageDialog(null, "Modificación Exitosa");
            Mostrar();
            ocultar_columnas();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Modificación sin exito, verifique haber ingresado bien los datos u el N° que identifica al usuario");
        }
    }//GEN-LAST:event_jBotonModif_UsuarioActionPerformed

    private void jBotonModif_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBotonModif_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){

            control_Usuario funcion = new control_Usuario();

            if (jTextID_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Ingrese el N° a modificar");
                return;
            }

            if (jTextNombre_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre");
                jTextNombre_Usuario.requestFocus();
                return;
            }

            if (jTextApellido_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar Apellido");
                jTextApellido_Usuario.requestFocus();
                return;
            }

            if (jTextDomicilio_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un Domicilio");
                jTextDomicilio_Usuario.requestFocus();
                return;
            }

            if (jTextNomUser_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un nombre de Usuario");
                jTextNomUser_Usuario.requestFocus();
                return;
            }

            if (jTextPass_Usuario.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar una contraseña");
                jTextPass_Usuario.requestFocus();
                return;
            }

            int dato = funcion.VerificarLogin();

            if (dato > 1) {
                JOptionPane.showMessageDialog(this, "Nombre de usuario " + jTextNomUser_Usuario.getText() + " ocupado");
                jTextNomUser_Usuario.requestFocus();
                return;
            }

            funcion.setId_persona(Integer.parseInt(jTextID_Usuario.getText()));
            funcion.setNombre_persona(jTextNombre_Usuario.getText());
            funcion.setApellido_persona(jTextApellido_Usuario.getText());
            funcion.setDomicilio_persona(jTextDomicilio_Usuario.getText());
            funcion.setTelefonoContacto_persona(jTextTelefono_Usuario.getText());
            funcion.setEmailContacto_persona(jTextCorreo_Usuario.getText());
            funcion.setNombre_usuario(jTextNomUser_Usuario.getText());
            funcion.setContraseña_usuario(jTextPass_Usuario.getText());
            funcion.setId_usuario(Integer.parseInt(jTextID_Usuario.getText()));

            int acceso = jComboAcceso_Usuario.getSelectedIndex();
            funcion.setTipo_usario((String) jComboAcceso_Usuario.getItemAt(acceso));

            int estado = jComboEstado_Usuario.getSelectedIndex();

            if (estado == 0) {
                boolean boolean1 = Boolean.parseBoolean("true");
                funcion.setEstado_usuario(boolean1);
            } else {
                boolean boolean2 = Boolean.parseBoolean("false");
                funcion.setEstado_usuario(boolean2);
            }

            if (funcion.EditarUsuarios(funcion)) {
                JOptionPane.showMessageDialog(null, "Modificación Exitosa");
                Mostrar();
                ocultar_columnas();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Modificación sin exito, verifique haber ingresado bien los datos u el N° que identifica al usuario");
            }
        }
    }//GEN-LAST:event_jBotonModif_UsuarioKeyPressed

    private void jTextPass_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPass_UsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            jComboAcceso_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTextPass_UsuarioKeyPressed

    private void jBotonElim_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonElim_UsuarioActionPerformed

        int i = JOptionPane.showConfirmDialog(this, "Eliminar Usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (i == 0) {
            jTextID_Usuario.setVisible(true);
            jEtiqID_Usuario.setVisible(true);
            if (!jTextID_Usuario.getText().equals("")) {

                control_Usuario funcion = new control_Usuario();

                funcion.setId_persona(Integer.parseInt(jTextID_Usuario.getText()));

                if (funcion.EliminarUsuarios(funcion)) {
                    JOptionPane.showMessageDialog(null, "Se elimino Usuario");
                    Mostrar();
                    ocultar_columnas();
                } else {
                    JOptionPane.showMessageDialog(null, "No se elimino Usuario, ingrese el N° que identifica dicho usuario para realizar la accion necesaria");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el N° a eliminar");
            }
        }
    }//GEN-LAST:event_jBotonElim_UsuarioActionPerformed

    private void jBotonElim_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBotonElim_UsuarioKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            int i = JOptionPane.showConfirmDialog(this, "Eliminar Usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (i == 0) {
                jTextID_Usuario.setVisible(true);
                jEtiqID_Usuario.setVisible(true);
                if (!jTextID_Usuario.getText().equals("")) {

                    control_Usuario funcion = new control_Usuario();

                    funcion.setId_persona(Integer.parseInt(jTextID_Usuario.getText()));

                    if (funcion.EliminarUsuarios(funcion)) {
                        JOptionPane.showMessageDialog(null, "Se elimino Usuario");
                        Mostrar();
                        ocultar_columnas();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se elimino Usuario, ingrese el N° que identifica dicho usuario para realizar la accion necesaria");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese el N° a eliminar");
                }
            }
        }
    }//GEN-LAST:event_jBotonElim_UsuarioKeyPressed

    private void jBotonCancel_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonCancel_UsuarioActionPerformed

        Limpiar();
        jBotonElim_Usuario.setEnabled(false);
        jBotonModif_Usuario.setEnabled(false);
        jBotonCancel_Usuario.setVisible(false);
        jBotonAcep_Usuario.setEnabled(true);
    }//GEN-LAST:event_jBotonCancel_UsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LTit_Usuario;
    private javax.swing.JButton jBotonAcep_Usuario;
    private javax.swing.JButton jBotonCancel_Usuario;
    private javax.swing.JButton jBotonElim_Usuario;
    private javax.swing.JButton jBotonModif_Usuario;
    public static javax.swing.JButton jBotonSalir_Usuario;
    public static javax.swing.JComboBox<String> jComboAcceso_Usuario;
    public static javax.swing.JComboBox<String> jComboEstado_Usuario;
    private javax.swing.JLabel jEtiqAcceso_Usuario;
    private javax.swing.JLabel jEtiqApellido_Usuario;
    private javax.swing.JLabel jEtiqCorreo_Usuario;
    private javax.swing.JLabel jEtiqDomicilio_Usuario;
    private javax.swing.JLabel jEtiqEstado_Usuario;
    private javax.swing.JLabel jEtiqID_Usuario;
    private javax.swing.JLabel jEtiqNomUser_Usuario;
    private javax.swing.JLabel jEtiqNombre_Usuario;
    private javax.swing.JLabel jEtiqPass_Usuario;
    private javax.swing.JLabel jEtiqTelefono_Usuario;
    private javax.swing.JScrollPane jSP1_Usuario;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Usuario;
    public static javax.swing.JTextField jTextApellido_Usuario;
    public static javax.swing.JTextField jTextCorreo_Usuario;
    public static javax.swing.JTextField jTextDomicilio_Usuario;
    private javax.swing.JTextField jTextID_Usuario;
    public static javax.swing.JTextField jTextNomUser_Usuario;
    public static javax.swing.JTextField jTextNombre_Usuario;
    public static javax.swing.JTextField jTextPass_Usuario;
    public static javax.swing.JTextField jTextTelefono_Usuario;
    // End of variables declaration//GEN-END:variables
}
