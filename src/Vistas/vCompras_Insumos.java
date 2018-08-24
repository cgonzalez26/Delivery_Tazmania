/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;


import Controlador.control_Compras;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vCompras_Insumos extends javax.swing.JPanel {

    /**
     * Creates new form jPanel2
     */
    public vCompras_Insumos() {
        initComponents();
        LlenarProveedores();
        Mostrar();
    }

    public ArrayList<String> LlenarProveedores() {
        control_Compras funcion = new control_Compras();

        ArrayList<String> lista1 = new ArrayList<String>();
        lista1 = funcion.LlenarComboProveedor();
        for (String lista : lista1) {
            jComboBox1.addItem(lista);
        }
        return lista1;
    }

    public void Mostrar() {
        try {
            DefaultTableModel model;
            control_Compras obtdatos = new control_Compras();
            model = obtdatos.MostrarDatosC();
            jTable1.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jScrollPane3.setViewportView(jScrollPane1);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 910, 150));

        jButton1.setText("Comprar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 450, -1, -1));

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 450, -1, -1));

        jButton3.setText("Salir");
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 450, -1, -1));

        jLabel1.setText("Fecha de Compra");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel2.setText("Nombre Comercial del Proveedor del Insumo");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        jLabel3.setText("Usuario responsable de la compra");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 170, -1));
        add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 160, -1));
        add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 370, 90, 30));

        jLabel4.setText("Monto Total");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 350, 60, -1));

        jLabel5.setText("Nombre Insumo");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 80, -1));

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 150, 20));

        jLabel6.setText("Tipo de Insumo");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, -1));
        add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 150, -1));

        jLabel7.setText("Precio");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));
        add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 140, 30));

        jLabel8.setText("Cantidad");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, -1, -1));
        add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 140, -1));

        jLabel9.setText("Descripcion");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 250, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Nombre Comercial Proveedor" }));
        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 230, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        control_Compras funcion = new control_Compras();

        if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una Fecha de la Compra");
            jDateChooser1.requestFocus();
        }

        String proveedor = (String) vCompras_Insumos.jComboBox1.getSelectedItem();

        if (proveedor.equals("Seleccionar Nombre Comercial Proveedor")) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Proveedor del Insumo a Comprar");
            jComboBox1.requestFocus();
        }

        if (jTextField2.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingresa el Usuario que hace la Compra");
            jTextField2.requestFocus();
        }

        if (jTextArea1.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar alguna descripción del Insumo a Comprar");
            jTextArea1.requestFocus();
        }

        if (jTextField4.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el Nombre de Insumo a Comprar");
            jTextField4.requestFocus();
        }

        if (jTextField5.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el Tipo de Insumo");
            jTextField5.requestFocus();
        }

        if (jTextField7.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar la Cantidad a Comprar de Insumos");
            jTextField7.requestFocus();
        }

        if (jTextField6.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el Precio del Insumo a Comprar");
            jTextField6.requestFocus();
        }

        int año = jDateChooser1.getCalendar().get(Calendar.YEAR);
        int mes = jDateChooser1.getCalendar().get(Calendar.MONTH) + 1;
        int dia = jDateChooser1.getCalendar().get(Calendar.DAY_OF_MONTH);
        String fecha = año + "-" + mes + "-" + dia;

        funcion.setFechaReg_Insumos(fecha);

        int nomprovcom = jComboBox1.getSelectedIndex();
        funcion.setNomProv_Insumos((String) jComboBox1.getItemAt(nomprovcom));

        funcion.setNomUser_Compras(jTextField2.getText());
        funcion.setDescripcion_Insumos(jTextArea1.getText());
        funcion.setNombre_Insumos(jTextField4.getText());
        funcion.setNombre_TipoInsumos(jTextField5.getText());
        funcion.setStock_Insumos(Integer.parseInt(jTextField7.getText()));
        funcion.setPrecio_Insumos(Double.parseDouble(jTextField6.getText()));
        funcion.setMontoTot_Compras(Integer.parseInt(jTextField3.getText()));

        int cantidad = funcion.CantidadInsumo();

        if(cantidad > 0){
            funcion.EfectuarCompra(funcion);
            JOptionPane.showMessageDialog(null, "Insumo ya comprado, se actualizara stock");
            Mostrar();
        }else{
            funcion.EfectuarCompra(funcion);
            JOptionPane.showMessageDialog(null, "Compra Registrada");
            Mostrar();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de Cancelar la Compra?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (i == 0) {
            jDateChooser1.setDate(null);
            jComboBox1.setSelectedIndex(-1);
            jTextField2.setText("");
            jTextArea1.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField7.setText("");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    public static javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    public static javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    public static javax.swing.JTextField jTextField4;
    public static javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    public static javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
