/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.control_Compras;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vTipos_Insumos extends javax.swing.JPanel {

    /**
     * Creates new form Panel1
     */
    public vTipos_Insumos() {
        initComponents();
        MostrarDatos();

        jTabla_TiposInsumos.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (e.getClickCount() == 2) {
                    int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea modificar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        int fila = jTabla_TiposInsumos.rowAtPoint(e.getPoint());
                        jTextID_TipoInsumo.setText(jTabla_TiposInsumos.getValueAt(fila, 0).toString());
                        jTextNombre_TipoInsumo.setText(jTabla_TiposInsumos.getValueAt(fila, 1).toString());
                    }

                }

            }
        });
    }

    public void MostrarDatos() {
        try {
            DefaultTableModel model;
            control_Compras obtdatos = new control_Compras();
            model = obtdatos.MostrarDatosTI();
            jTabla_TiposInsumos.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Limpiar() {
        jTextID_TipoInsumo.setText("");
        jTextNombre_TipoInsumo.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEtiqNombre_TipoInsumo = new javax.swing.JLabel();
        jTextNombre_TipoInsumo = new javax.swing.JTextField();
        jEtiqID_TipoInsumo = new javax.swing.JLabel();
        jTextID_TipoInsumo = new javax.swing.JTextField();
        jBotonAgregar_TipoInsumo = new javax.swing.JButton();
        jBotonModificar_TipoInsumo = new javax.swing.JButton();
        jBotonEliminar_TipoInsumo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jSP1_TipoInsumo = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_TiposInsumos = jTabla_TiposInsumos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jEtiqNombre_TipoInsumo.setText("Nuevo tipo de Insumo");
        add(jEtiqNombre_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 110, 20));
        add(jTextNombre_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 200, 30));

        jEtiqID_TipoInsumo.setText("N°");
        add(jEtiqID_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 20, 20));
        add(jTextID_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 70, 20));

        jBotonAgregar_TipoInsumo.setText("Agregar");
        jBotonAgregar_TipoInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgregar_TipoInsumoActionPerformed(evt);
            }
        });
        add(jBotonAgregar_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jBotonModificar_TipoInsumo.setText("Modificar");
        jBotonModificar_TipoInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModificar_TipoInsumoActionPerformed(evt);
            }
        });
        add(jBotonModificar_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, -1, -1));

        jBotonEliminar_TipoInsumo.setText("Eliminar");
        jBotonEliminar_TipoInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonEliminar_TipoInsumoActionPerformed(evt);
            }
        });
        add(jBotonEliminar_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, -1, -1));
        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, -1, -1));

        jTabla_TiposInsumos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTabla_TiposInsumos);

        jSP1_TipoInsumo.setViewportView(jScrollPane1);

        add(jSP1_TipoInsumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 560, 200));
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgregar_TipoInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgregar_TipoInsumoActionPerformed
        control_Compras funcion = new control_Compras();

        if (jTextNombre_TipoInsumo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nuevo Nombre Tipo de Insumo");
            jTextNombre_TipoInsumo.requestFocus();
        }

        funcion.setNombre_TipoInsumos(jTextNombre_TipoInsumo.getText());

        if (funcion.InsertarTiposInsumos(funcion)) {
            JOptionPane.showMessageDialog(null, "Ingresos de datos con exito");
            MostrarDatos();
        } else {
            JOptionPane.showMessageDialog(null, "Ingreso de datos sin exito");
        }
    }//GEN-LAST:event_jBotonAgregar_TipoInsumoActionPerformed

    private void jBotonModificar_TipoInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModificar_TipoInsumoActionPerformed

        control_Compras funcion = new control_Compras();

        if (jTextNombre_TipoInsumo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un nuevo Nombre Tipo de Insumo");
            jTextNombre_TipoInsumo.requestFocus();
        }

        if (jTextID_TipoInsumo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el numero que identifica el Tipo de Insumo");
        }

        funcion.setId_TipoInsumos(Integer.parseInt(jTextID_TipoInsumo.getText()));
        funcion.setNombre_TipoInsumos(jTextNombre_TipoInsumo.getText());

        int cantidad = funcion.CantidadIDTipoInsumos();

        if (cantidad > 0) {
            funcion.EditarTiposInsumos(funcion);
            JOptionPane.showMessageDialog(null, "Modificacion completa para ambas tablas");
            MostrarDatos();
        } else {
            funcion.EditarTiposInsumos(funcion);
            JOptionPane.showMessageDialog(null, "Modificacion completa");
            MostrarDatos();
        }
    }//GEN-LAST:event_jBotonModificar_TipoInsumoActionPerformed

    private void jBotonEliminar_TipoInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonEliminar_TipoInsumoActionPerformed

        int i = JOptionPane.showConfirmDialog(null, "Eliminar Tipo de Insumo?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (i == 0) {

            if (jTextID_TipoInsumo.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar el N° que identifica al Tipo Insumo");
            } else {
                control_Compras funcion = new control_Compras();

                funcion.setId_TipoInsumos(Integer.parseInt(jTextID_TipoInsumo.getText()));

                if (funcion.EliminarTiposInsumos(funcion)) {
                    JOptionPane.showMessageDialog(null, "Eliminado con Exito");
                    MostrarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Eliminacion con Exito");
                    MostrarDatos();
                }
            }
        }
    }//GEN-LAST:event_jBotonEliminar_TipoInsumoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonAgregar_TipoInsumo;
    private javax.swing.JButton jBotonEliminar_TipoInsumo;
    private javax.swing.JButton jBotonModificar_TipoInsumo;
    private javax.swing.JLabel jEtiqID_TipoInsumo;
    private javax.swing.JLabel jEtiqNombre_TipoInsumo;
    private javax.swing.JScrollPane jSP1_TipoInsumo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTabla_TiposInsumos;
    public static javax.swing.JTextField jTextID_TipoInsumo;
    public static javax.swing.JTextField jTextNombre_TipoInsumo;
    // End of variables declaration//GEN-END:variables
}
