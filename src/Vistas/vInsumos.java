/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.control_Compras;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vInsumos extends javax.swing.JPanel {

    /**
     * Creates new form Panel5
     */
    public vInsumos() {
        initComponents();
        LlenarTiposInsumos();
        LlenarProveedores();
        Mostrar();

        jTabla_Insumos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {

                    int fila = JOptionPane.showConfirmDialog(null, "Esta seguro que desea modificar?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    jTextNumID_Insumos.setText(jTabla_Insumos.getValueAt(fila, 0).toString());
                    jTextNomInsumo_Insumos.setText(jTabla_Insumos.getValueAt(fila, 1).toString());
                    jTextDesc_Insumos.setText(jTabla_Insumos.getValueAt(fila, 2).toString());
                    jTextPrecio_Insumos.setText(jTabla_Insumos.getValueAt(fila, 3).toString());
                    jTextStock_Insumos.setText(jTabla_Insumos.getValueAt(fila, 4).toString());
                    jCBTipo_Insumos.setSelectedItem(jTabla_Insumos.getValueAt(fila, 5).toString());
                    jCBNomComProv_Insumos.setSelectedItem(jTabla_Insumos.getValueAt(fila, 6).toString());
                    jDCFechReg_Insumos.setDate(Date.valueOf(jTabla_Insumos.getValueAt(fila, 7).toString()));
                }
            }
        });
    }

        public ArrayList<String> LlenarTiposInsumos() {
        control_Compras funcion = new control_Compras();

        ArrayList<String> lista2 = new ArrayList<String>();
        lista2 = funcion.LlenarTipoInsumo();
        for (String lista : lista2) {
            jCBTipo_Insumos.addItem(lista);
        }
        return lista2;
    }

    public ArrayList<String> LlenarProveedores() {
        control_Compras funcion = new control_Compras();

        ArrayList<String> lista1 = new ArrayList<String>();
        lista1 = funcion.LlenarComboProveedor();
        for (String lista : lista1) {
            jCBNomComProv_Insumos.addItem(lista);
        }
        return lista1;
    }

    public void Mostrar() {
        try {
            DefaultTableModel model;
            control_Compras obtdatos = new control_Compras();
            model = obtdatos.MostrarDatosI();
            jTabla_Insumos.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Limpiar() {
        jTextDesc_Insumos.setText("");
        jTextNumID_Insumos.setText("");
        jTextPrecio_Insumos.setText("");
        jTextStock_Insumos.setText("");
        jDCFechReg_Insumos.setDate(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_Insumos = jTabla_Insumos= new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jEtiqNumID_Insumos = new javax.swing.JLabel();
        jTextNumID_Insumos = new javax.swing.JTextField();
        jEtiqDesc_Insumos = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextDesc_Insumos = new javax.swing.JTextArea();
        jEtiqPrecio_Insumos = new javax.swing.JLabel();
        jTextPrecio_Insumos = new javax.swing.JTextField();
        jEtiqStock_Insumos = new javax.swing.JLabel();
        jTextStock_Insumos = new javax.swing.JTextField();
        jEtiqTipo_Insumos = new javax.swing.JLabel();
        jCBTipo_Insumos = new javax.swing.JComboBox<>();
        jEtiqNomComProv_Insumos = new javax.swing.JLabel();
        jCBNomComProv_Insumos = new javax.swing.JComboBox<>();
        jEtiqFechReg_Insumos = new javax.swing.JLabel();
        jDCFechReg_Insumos = new com.toedter.calendar.JDateChooser();
        jBotonAgregar_Insumos = new javax.swing.JButton();
        jBotonModificar_Insumos = new javax.swing.JButton();
        jBotonElim_Insumos = new javax.swing.JButton();
        jEtiqNomInsumo_Insumos = new javax.swing.JLabel();
        jTextNomInsumo_Insumos = new javax.swing.JTextField();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabla_Insumos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTabla_Insumos);

        jScrollPane2.setViewportView(jScrollPane1);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 490, 170));

        jEtiqNumID_Insumos.setText("N°");
        add(jEtiqNumID_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 20, 20));
        add(jTextNumID_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 60, 20));

        jEtiqDesc_Insumos.setText("Descripción ");
        add(jEtiqDesc_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        jTextDesc_Insumos.setColumns(20);
        jTextDesc_Insumos.setRows(5);
        jScrollPane3.setViewportView(jTextDesc_Insumos);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 210, 80));

        jEtiqPrecio_Insumos.setText("Precio");
        add(jEtiqPrecio_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 40, 20));
        add(jTextPrecio_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 210, 30));

        jEtiqStock_Insumos.setText("Stock");
        add(jEtiqStock_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 40, 20));
        add(jTextStock_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 210, 30));

        jEtiqTipo_Insumos.setText("Tipo de Insumo");
        add(jEtiqTipo_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 110, 30));

        jCBTipo_Insumos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo Insumo" }));
        add(jCBTipo_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 140, 20));

        jEtiqNomComProv_Insumos.setText("Nombre Comercial Proveedor");
        add(jEtiqNomComProv_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 140, 30));

        jCBNomComProv_Insumos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Nombre Comercial Proveedor" }));
        add(jCBNomComProv_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 230, -1));

        jEtiqFechReg_Insumos.setText("Fecha de Registro ");
        add(jEtiqFechReg_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 100, 20));
        add(jDCFechReg_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, -1, -1));

        jBotonAgregar_Insumos.setText("Agregar");
        jBotonAgregar_Insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgregar_InsumosActionPerformed(evt);
            }
        });
        add(jBotonAgregar_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, -1, -1));

        jBotonModificar_Insumos.setText("Modificar");
        jBotonModificar_Insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModificar_InsumosActionPerformed(evt);
            }
        });
        add(jBotonModificar_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 410, -1, -1));

        jBotonElim_Insumos.setText("Eliminar");
        jBotonElim_Insumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonElim_InsumosActionPerformed(evt);
            }
        });
        add(jBotonElim_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 410, -1, -1));

        jEtiqNomInsumo_Insumos.setText("Nombre");
        add(jEtiqNomInsumo_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        add(jTextNomInsumo_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 160, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgregar_InsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgregar_InsumosActionPerformed

        control_Compras funcion = new control_Compras();

        if (jTextNomInsumo_Insumos.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre del Insumo");
            jTextNomInsumo_Insumos.requestFocus();
        }

        if (jTextDesc_Insumos.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una Descripcion del Insumo");
            jTextDesc_Insumos.requestFocus();
        }

        if (jTextPrecio_Insumos.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Precio del Insumo");
            jTextPrecio_Insumos.requestFocus();
        }
        funcion.setNombre_Insumos(jTextNomInsumo_Insumos.getText());
        funcion.setDescripcion_Insumos(jTextDesc_Insumos.getText());
        funcion.setPrecio_Insumos(Double.parseDouble(jTextPrecio_Insumos.getText()));
        funcion.setStock_Insumos(Integer.parseInt(jTextStock_Insumos.getText()));

        int tipoinsumo = jCBTipo_Insumos.getSelectedIndex();
        funcion.setNombre_TipoInsumos((String) jCBTipo_Insumos.getItemAt(tipoinsumo));

        int nomcomprov = jCBNomComProv_Insumos.getSelectedIndex();
        funcion.setNomProv_Insumos((String) jCBNomComProv_Insumos.getItemAt(nomcomprov));

        int año = jDCFechReg_Insumos.getCalendar().get(Calendar.YEAR);
        int mes = jDCFechReg_Insumos.getCalendar().get(Calendar.MONTH) + 1;
        int dia = jDCFechReg_Insumos.getCalendar().get(Calendar.DAY_OF_MONTH);
        String fecha = año + "-" + mes + "-" + dia;

        funcion.setFechaReg_Insumos(fecha);

        if (funcion.InsertarInsumos(funcion)) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos con Exito");
            Mostrar();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Ingreso de datos sin Exito");
        }
    }//GEN-LAST:event_jBotonAgregar_InsumosActionPerformed

    private void jBotonModificar_InsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModificar_InsumosActionPerformed
        control_Compras funcion = new control_Compras();
        
        if(jTextNumID_Insumos.getText().trim().length() == 0){
            JOptionPane.showMessageDialog(null, "Debes ingresar un el N° que identifica al Insumo");
            jTextNumID_Insumos.requestFocus();
        }
        
        if (jTextNomInsumo_Insumos.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre del Insumo");
            jTextNomInsumo_Insumos.requestFocus();
        }

        if (jTextDesc_Insumos.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una Descripcion del Insumo");
            jTextDesc_Insumos.requestFocus();
        }

        if (jTextPrecio_Insumos.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Precio del Insumo");
            jTextPrecio_Insumos.requestFocus();
        }
        
        funcion.setNombre_Insumos(jTextNomInsumo_Insumos.getText());
        funcion.setDescripcion_Insumos(jTextDesc_Insumos.getText());
        funcion.setPrecio_Insumos(Double.parseDouble(jTextPrecio_Insumos.getText()));
        funcion.setStock_Insumos(Integer.parseInt(jTextStock_Insumos.getText()));

        int tipoinsumo = jCBTipo_Insumos.getSelectedIndex();
        funcion.setNombre_TipoInsumos((String) jCBTipo_Insumos.getItemAt(tipoinsumo));

        int nomcomprov = jCBNomComProv_Insumos.getSelectedIndex();
        funcion.setNomProv_Insumos((String) jCBNomComProv_Insumos.getItemAt(nomcomprov));

        int año = jDCFechReg_Insumos.getCalendar().get(Calendar.YEAR);
        int mes = jDCFechReg_Insumos.getCalendar().get(Calendar.MONTH) + 1;
        int dia = jDCFechReg_Insumos.getCalendar().get(Calendar.DAY_OF_MONTH);
        String fecha = año + "-" + mes + "-" + dia;

        funcion.setFechaReg_Insumos(fecha);
        funcion.setId_Insumos(Integer.parseInt(jTextNumID_Insumos.getText()));

        if (funcion.EditarInsumos(funcion)) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos con exito");
            Mostrar();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Ingreso de datos sin exito");
        }
    }//GEN-LAST:event_jBotonModificar_InsumosActionPerformed

    private void jBotonElim_InsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonElim_InsumosActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (i == 0) {
            if (!jTextNumID_Insumos.getText().trim().equals("")) {
                control_Compras funcion = new control_Compras();

                funcion.setId_Insumos(Integer.parseInt(jTextNumID_Insumos.getText()));

                if (funcion.EliminarInsumos(funcion)) {
                    JOptionPane.showMessageDialog(null, "Se elimino insumo");
                    Mostrar();
                    Limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se elimino insumo, Ingrese el numero para hacer la accion");
            }
        }
    }//GEN-LAST:event_jBotonElim_InsumosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonAgregar_Insumos;
    private javax.swing.JButton jBotonElim_Insumos;
    private javax.swing.JButton jBotonModificar_Insumos;
    private javax.swing.JComboBox<String> jCBNomComProv_Insumos;
    public static javax.swing.JComboBox<String> jCBTipo_Insumos;
    private com.toedter.calendar.JDateChooser jDCFechReg_Insumos;
    private javax.swing.JLabel jEtiqDesc_Insumos;
    private javax.swing.JLabel jEtiqFechReg_Insumos;
    private javax.swing.JLabel jEtiqNomComProv_Insumos;
    private javax.swing.JLabel jEtiqNomInsumo_Insumos;
    private javax.swing.JLabel jEtiqNumID_Insumos;
    private javax.swing.JLabel jEtiqPrecio_Insumos;
    private javax.swing.JLabel jEtiqStock_Insumos;
    private javax.swing.JLabel jEtiqTipo_Insumos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTabla_Insumos;
    private javax.swing.JTextArea jTextDesc_Insumos;
    private javax.swing.JTextField jTextNomInsumo_Insumos;
    private javax.swing.JTextField jTextNumID_Insumos;
    private javax.swing.JTextField jTextPrecio_Insumos;
    private javax.swing.JTextField jTextStock_Insumos;
    // End of variables declaration//GEN-END:variables
}
