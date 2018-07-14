/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modulo.Conexion;
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.omg.CORBA.ORB;

/**
 *
 * @author Colo-PC
 */
public class fLogin extends JFrame implements ActionListener, KeyListener {

    private static JButton boton1, boton2;
    private static JLabel label1, label2, label3, label4;
    private static JTextField texto1;
    private static JPasswordField pass1;

    Conexion conexion = new Conexion();
    Connection cn = conexion.getConnection();

    public fLogin() {
        setLayout(null);
        setUndecorated(false);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(0);

        ImageIcon imagen = new ImageIcon("C:/Users/Colo-PC/Pictures/Camera Roll/logueo.png");
        label4 = new JLabel(imagen);
        label4.setBounds(0, 0, 300, 230);
        add(label4);

        label3 = new JLabel("BIENVENIDO!");
        label3.setFont(new Font("Eras Demi ITC", 1, 26));
        label3.setBounds(57, 20, 200, 30);
        add(label3);

        label2 = new JLabel("Nombre de Usuario");
        label2.setFont(new Font("Segoe UI Semibold", 3, 16));
        label2.setBounds(20, 172, 150, 80);
        add(label2);

        label1 = new JLabel("Contraseña");
        label1.setFont(new Font("Segoe UI Semibold", 3, 16));
        label1.setBounds(20, 240, 150, 80);
        add(label1);

        texto1 = new JTextField();
        texto1.setFont(new Font("Segoe UI Semibold", 3, 16));
        texto1.requestFocus();
        texto1.setBounds(20, 228, 250, 30);
        texto1.addKeyListener(this);
        texto1.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        add(texto1);

        pass1 = new JPasswordField();
        pass1.setFont(new Font("Segoe UI Semibold", 3, 16));
        pass1.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        pass1.setBounds(20, 295, 250, 30);
        pass1.addKeyListener(this);
        add(pass1);

        boton1 = new JButton("Ingresar");
        boton1.setFont(new Font("Segoe UI Semibold", 3, 16));
        boton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton1.setBounds(22, 370, 100, 35);
        add(boton1);
        boton1.addActionListener(this);
        boton1.addKeyListener(this);

        boton2 = new JButton("Salir");
        boton2.setFont(new Font("Segoe UI Semibold", 3, 16));
        boton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton2.setBounds(170, 370, 100, 35);
        add(boton2);
        boton2.addActionListener(this);
        boton2.addKeyListener(this);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == boton1) {
            String cadena1 = texto1.getText().trim();
            String cadena2 = pass1.getText().trim();
            String sql = "SELECT nom_user, pass_user FROM USUARIOS WHERE nom_user='"
            + cadena1 + "' && pass_user='" + cadena2 + "'"; //Ejemplo de una consulta de tabla que tenga usuarios.
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    String user = rs.getNString(2);
                    String pass = rs.getNString(3);

                    if (cadena1.equalsIgnoreCase(user) && cadena2.equalsIgnoreCase(pass)) {
                        JOptionPane.showMessageDialog(null, "Bienvenido: " + user);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario y Contraseña no Validas");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(fLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == boton2) {
            this.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        /*if ((e.getKeyCode() == e.VK_ENTER) != (e.getSource() == boton2)) {
            boton1.doClick();
        }*/
        if ((e.getKeyCode() == e.VK_ENTER) && (e.getSource() == boton2)) {
            boton2.doClick();
        }

        if ((e.getKeyCode() == e.VK_ENTER) && (e.getSource() == texto1)) {
            pass1.requestFocus();

        }
        if ((e.getKeyCode() == e.VK_ENTER) && (e.getSource() == pass1)) {
            boton1.requestFocus();
            boton1.doClick();
        }

        if (e.getKeyCode() == e.VK_ENTER && e.getSource() == boton1) {
            boton2.requestFocus();
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here

        fLogin login1 = new fLogin();

        login1.setBounds(0, 0, 300, 480);
        login1.setVisible(true);
        login1.setResizable(false);
        login1.setLocationRelativeTo(null);
    }

}
