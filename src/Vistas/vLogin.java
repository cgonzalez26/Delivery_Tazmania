package Vistas;

import Controlador.control_Caja_Turno;
import Controlador.control_Cajas;
import Controlador.control_TiposUsuarios;
import Controlador.control_Turnos;
import Controlador.control_Usuario;
import Modelo.Session;
import Modelo.TiposUsuarios;
import Modelo.Usuarios;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class vLogin extends javax.swing.JFrame {

    vGestion_PermisosPantallasPerfiles permisos = null;
    control_Usuario user = new control_Usuario();
    control_TiposUsuarios tipousuario = new control_TiposUsuarios();
    control_Cajas cc = new control_Cajas();
    control_Turnos ct = new control_Turnos();
    TiposUsuarios tu = new TiposUsuarios();
    Usuarios u = new Usuarios();
    Session iduser = new Session();
    public static String nombretu = "";

    public vLogin() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.PNG")).getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEtiqTitulo_Login = new javax.swing.JLabel();
        jEtiqNom_Login = new javax.swing.JLabel();
        jTextNomUser_Login = new javax.swing.JTextField();
        jEtiqPass_Login = new javax.swing.JLabel();
        jBotonIngreso_Login = new javax.swing.JButton();
        jBotonSalir_Login = new javax.swing.JButton();
        jTextoPass_Login = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Delivery Tazmania");
        setBackground(new java.awt.Color(255, 248, 177));
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(430, 370));
        setName("jFLogin"); // NOI18N
        setPreferredSize(new java.awt.Dimension(430, 370));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jEtiqTitulo_Login.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jEtiqTitulo_Login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/DeliveryTazmania_login.png"))); // NOI18N
        jEtiqTitulo_Login.setText("Bienvenido!");
        getContentPane().add(jEtiqTitulo_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 200, 160));

        jEtiqNom_Login.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jEtiqNom_Login.setText("Usuario:");
        getContentPane().add(jEtiqNom_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 90, 30));

        jTextNomUser_Login.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jTextNomUser_Login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextNomUser_LoginKeyPressed(evt);
            }
        });
        getContentPane().add(jTextNomUser_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 250, 40));

        jEtiqPass_Login.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jEtiqPass_Login.setText("Contraseña:");
        getContentPane().add(jEtiqPass_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 130, 40));

        jBotonIngreso_Login.setBackground(new java.awt.Color(252, 249, 57));
        jBotonIngreso_Login.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jBotonIngreso_Login.setText("Ingresar");
        jBotonIngreso_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonIngreso_LoginActionPerformed(evt);
            }
        });
        jBotonIngreso_Login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBotonIngreso_LoginKeyPressed(evt);
            }
        });
        getContentPane().add(jBotonIngreso_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 100, 40));

        jBotonSalir_Login.setBackground(new java.awt.Color(237, 124, 61));
        jBotonSalir_Login.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jBotonSalir_Login.setText("Salir");
        jBotonSalir_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonSalir_LoginActionPerformed(evt);
            }
        });
        jBotonSalir_Login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBotonSalir_LoginKeyPressed(evt);
            }
        });
        getContentPane().add(jBotonSalir_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, 100, 40));

        jTextoPass_Login.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jTextoPass_Login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextoPass_LoginKeyPressed(evt);
            }
        });
        getContentPane().add(jTextoPass_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 250, 40));

        jCheckBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jCheckBox1.setText("Ver Contraseña");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 190, -1));

        setSize(new java.awt.Dimension(519, 433));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jBotonIngreso_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonIngreso_LoginActionPerformed
        u.setLogin(jTextNomUser_Login.getText());
        u.setPassword(jTextoPass_Login.getText());

        if (jTextNomUser_Login.getText().trim().equals("") && jTextoPass_Login.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Debes completar los campos");
        } else if (!jTextNomUser_Login.getText().trim().equals("") && jTextoPass_Login.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Debes ingresar la contraseña");
        } else if (jTextNomUser_Login.getText().trim().equals("") && !jTextoPass_Login.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el nombre de usuario");
        } else if (user.InicioSesion(u)) {
            nombretu = tipousuario.TipoUsuarioInicioSesion(jTextNomUser_Login.getText());
            Session.setIdusuario(user.ObtenerIDUsuario(jTextNomUser_Login.getText()));
            Session.setLogin(jTextNomUser_Login.getText());
            vMenuPrincipal menu = new vMenuPrincipal();
            menu.getFont();
            menu.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario y/o Contraseña no validos");
        }
    }//GEN-LAST:event_jBotonIngreso_LoginActionPerformed

    private void jBotonSalir_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonSalir_LoginActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jBotonSalir_LoginActionPerformed

    private void jTextNomUser_LoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNomUser_LoginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jTextoPass_Login.requestFocus();
        }

    }//GEN-LAST:event_jTextNomUser_LoginKeyPressed

    private void jTextoPass_LoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextoPass_LoginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jBotonIngreso_Login.requestFocus();
        }

    }//GEN-LAST:event_jTextoPass_LoginKeyPressed

    private void jBotonIngreso_LoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBotonIngreso_LoginKeyPressed
        /*  if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            control_Usuario usuario = new control_Usuario();
            usuario.ExistenciaUsuarios();

            if (jTextNomUser_Login.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre de Usuario!");
                return;
            }

            if (jTextoPass_Login.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar la Contraseña!");
                return;
            }

            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if (usuario.VerificarInicioSesion() == true) {
                    this.setVisible(false);
                } else {
                    this.setVisible(true);
                }
            }
        }*/
    }//GEN-LAST:event_jBotonIngreso_LoginKeyPressed

    private void jBotonSalir_LoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBotonSalir_LoginKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
            System.exit(0);
        }
    }//GEN-LAST:event_jBotonSalir_LoginKeyPressed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jTextoPass_Login.setEchoChar((char) 0);
        } else {
            jTextoPass_Login.setEchoChar('\u25cf');
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
 /*try {
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }*/
        //</editor-fold>

        try {
            try {
                javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(vMenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("No se ha podido cargar la la interfaz. Se usará la interfaz por defecto de Java."
                    + "\nError" + e);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonIngreso_Login;
    private javax.swing.JButton jBotonSalir_Login;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jEtiqNom_Login;
    private javax.swing.JLabel jEtiqPass_Login;
    private javax.swing.JLabel jEtiqTitulo_Login;
    public static javax.swing.JTextField jTextNomUser_Login;
    public static javax.swing.JPasswordField jTextoPass_Login;
    // End of variables declaration//GEN-END:variables
}
