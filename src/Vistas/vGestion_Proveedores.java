/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.control_Proveedores;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Colo-PC
 */
public class vGestion_Proveedores extends javax.swing.JPanel {

    /**
     * Creates new form Panel3
     */
    public vGestion_Proveedores() {
        initComponents();
        Mostrar();
        GrupoBotones();
        jBotonEdit_Prov.setEnabled(false);
        jBotonElim_Prov.setEnabled(false);
        jEtiqNumID_Prov.setVisible(false);
        jTextNumID_Prov.setVisible(false);
        jTextNom_Prov.requestFocus();

        jTabla_Prov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Modificar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        jEtiqNumID_Prov.setVisible(true);
                        jTextNumID_Prov.setVisible(true);
                        jBotonEdit_Prov.setEnabled(true);
                        jBotonElim_Prov.setEnabled(true);
                        jBotonAgre_Prov.setEnabled(false);

                        int fila = jTabla_Prov.rowAtPoint(e.getPoint());

                        jTextNumID_Prov.setText(jTabla_Prov.getValueAt(fila, 0).toString());
                        if("DNI".equals(jTabla_Prov.getValueAt(fila, 1).toString())){
                            jRbDNI_Prov.setSelected(true);
                        }else{
                            jRbDNI_Prov.setSelected(false);
                        }
                        if("Libreta Civica".equals(jTabla_Prov.getValueAt(fila, 1).toString())){
                            jRbLC_Prov.setSelected(true);
                        }else{
                            jRbLC_Prov.setSelected(false);
                        }
                        /*jRbDNI_Prov.setText(jTabla_Prov.getValueAt(fila, 1).toString());
                        jRbLC_Prov.setText(jTabla_Prov.getValueAt(fila, 1).toString());*/
                        jTextNroDoc_Prov.setText(jTabla_Prov.getValueAt(fila, 2).toString());
                        jTextNom_Prov.setText(jTabla_Prov.getValueAt(fila, 3).toString());
                        jTextApe_Prov.setText(jTabla_Prov.getValueAt(fila, 4).toString());
                        jTextNomCom_Prov.setText(jTabla_Prov.getValueAt(fila, 5).toString());
                        jTextDirec_Prov.setText(jTabla_Prov.getValueAt(fila, 6).toString());
                        jTextTel_Prov.setText(jTabla_Prov.getValueAt(fila, 7).toString());
                    }
                }
            }
        });
    }

            public void Mostrar() {
        try {
            DefaultTableModel model;
            control_Proveedores obtdatos = new control_Proveedores();
            model = obtdatos.MostrarDatos();
            jTabla_Prov.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Limpiar() {
        jTextNom_Prov.setText("");
        jTextApe_Prov.setText("");
        jTextNomCom_Prov.setText("");
        jTextDirec_Prov.setText("");
        jTextTel_Prov.setText("");
        jTextNumID_Prov.setText("");
        jTextNroDoc_Prov.setText("");
    }

    public void GrupoBotones(){
        GrupoBotones.add(jRbDNI_Prov);
        GrupoBotones.add(jRbLC_Prov);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrupoBotones = new javax.swing.ButtonGroup();
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
        jEtiqTD_Prov = new javax.swing.JLabel();
        jRbDNI_Prov = new javax.swing.JRadioButton();
        jRbLC_Prov = new javax.swing.JRadioButton();
        jTextNroDoc_Prov = new javax.swing.JTextField();
        jEtiqNumDoc_Prov = new javax.swing.JLabel();
        jBotonAgre_Prov = new javax.swing.JButton();
        jBotonEdit_Prov = new javax.swing.JButton();
        jBotonElim_Prov = new javax.swing.JButton();
        jEtiqNumID_Prov = new javax.swing.JLabel();
        jTextNumID_Prov = new javax.swing.JTextField();
        jSP1_Prov = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_Prov = jTabla_Prov = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jEtiqNom_Prov.setText("Nombre");
        add(jEtiqNom_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 90, 20));
        add(jTextNom_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 180, 30));

        jEtiqApe_Prov.setText("Apellido");
        add(jEtiqApe_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 100, 20));
        add(jTextApe_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 170, 30));

        jEtiqNomCom_Prov.setText("Nombre Comercial");
        add(jEtiqNomCom_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 100, 20));
        add(jTextNomCom_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 140, 30));

        jEtiqDirec_Prov.setText("Direccion");
        add(jEtiqDirec_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 40, 90, 20));
        add(jTextDirec_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 200, 30));

        jEtiqTel_Prov.setText("Telefono");
        add(jEtiqTel_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 40, 80, 20));
        add(jTextTel_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 70, 220, 30));

        jEtiqTD_Prov.setText("Tipo Documento");
        add(jEtiqTD_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 90, 20));

        jRbDNI_Prov.setText("DNI");
        add(jRbDNI_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, -1, -1));

        jRbLC_Prov.setText("Libreta Civica");
        add(jRbLC_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, -1, -1));
        add(jTextNroDoc_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 160, 160, 30));

        jEtiqNumDoc_Prov.setText("Numero Documento");
        add(jEtiqNumDoc_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 100, 20));

        jBotonAgre_Prov.setText("Agregar");
        jBotonAgre_Prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgre_ProvActionPerformed(evt);
            }
        });
        add(jBotonAgre_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, 90, 30));

        jBotonEdit_Prov.setText("Editar");
        jBotonEdit_Prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonEdit_ProvActionPerformed(evt);
            }
        });
        add(jBotonEdit_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 470, 80, 30));

        jBotonElim_Prov.setText("Eliminar");
        jBotonElim_Prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonElim_ProvActionPerformed(evt);
            }
        });
        add(jBotonElim_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 470, 80, 30));

        jEtiqNumID_Prov.setText("N° Identificador");
        add(jEtiqNumID_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 80, 20));
        add(jTextNumID_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 50, 30));

        jTabla_Prov.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTabla_Prov);

        jSP1_Prov.setViewportView(jScrollPane1);

        add(jSP1_Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 890, 200));
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgre_ProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgre_ProvActionPerformed

        control_Proveedores funcion = new control_Proveedores();

        if (jTextNom_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre del Proveedor");
            jTextNom_Prov.requestFocus();
        }

        if (jTextApe_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Apellido del Proveedor");
            jTextApe_Prov.requestFocus();
        }

        if (jTextDirec_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el Domicilio del Proveedor");
            jTextDirec_Prov.requestFocus();
        }

        if (jRbDNI_Prov.isSelected() == false && jRbLC_Prov.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Tipo de Documento del Proveedor");
            jRbDNI_Prov.requestFocus();
            jRbLC_Prov.requestFocus();
        }

        if (jTextNroDoc_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el Numero de Documento del Proveedor");
            jTextNroDoc_Prov.requestFocus();
        }

        funcion.setNombre_persona(jTextNom_Prov.getText());
        funcion.setApellido_persona(jTextApe_Prov.getText());
        funcion.setNomComercial_Proveedores(jTextNomCom_Prov.getText());
        funcion.setDomicilio_persona(jTextDirec_Prov.getText());
        funcion.setTelefonoContacto_persona(jTextTel_Prov.getText());

        if (jRbDNI_Prov.isSelected()) {
            funcion.setTipoDoc_Proveedores(jRbDNI_Prov.getText());
        } else {
            funcion.setTipoDoc_Proveedores(jRbLC_Prov.getText());
        }

        funcion.setNroDoc_Proveedores(Integer.parseInt(jTextNroDoc_Prov.getText()));

        if (funcion.InsertarProveedores(funcion)) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos correctos!");
            Mostrar();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Ingreso de datos sin exito!");
        }
    }//GEN-LAST:event_jBotonAgre_ProvActionPerformed

    private void jBotonEdit_ProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonEdit_ProvActionPerformed
        control_Proveedores funcion = new control_Proveedores();

        if(jTextNumID_Prov.getText().trim().length() == 0){
            JOptionPane.showMessageDialog(null, "Debes ingresar el Numero que Identifica dicho Proveedor");
            jTextNumID_Prov.requestFocus();
        }

        if (jTextNom_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre del Proveedor");
            jTextNom_Prov.requestFocus();
        }

        if (jTextApe_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Apellido del Proveedor");
            jTextApe_Prov.requestFocus();
        }

        if (jTextDirec_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el Domicilio del Proveedor");
            jTextDirec_Prov.requestFocus();
        }

        if (jRbDNI_Prov.isSelected() == false && jRbLC_Prov.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Tipo de Documento del Proveedor");
            jRbDNI_Prov.requestFocus();
            jRbLC_Prov.requestFocus();
        }

        if (jTextNroDoc_Prov.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el Numero de Documento del Proveedor");
            jTextNroDoc_Prov.requestFocus();
        }
        funcion.setId_persona(Integer.parseInt(jTextNumID_Prov.getText()));
        funcion.setNombre_persona(jTextNom_Prov.getText());
        funcion.setApellido_persona(jTextApe_Prov.getText());
        funcion.setNomComercial_Proveedores(jTextNomCom_Prov.getText());
        funcion.setDomicilio_persona(jTextDirec_Prov.getText());
        funcion.setTelefonoContacto_persona(jTextTel_Prov.getText());
        funcion.setId_Proveedores(Integer.parseInt(jTextNumID_Prov.getText()));

        if (jRbDNI_Prov.isSelected()) {
            funcion.setTipoDoc_Proveedores(jRbDNI_Prov.getText());
        } else {
            funcion.setTipoDoc_Proveedores(jRbLC_Prov.getText());
        }

        funcion.setNroDoc_Proveedores(Integer.parseInt(jTextNroDoc_Prov.getText()));

        if (funcion.EditarProveedores(funcion)) {
            JOptionPane.showMessageDialog(null, "Modificación Exitosa!");
            Mostrar();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Modificación sin Exito!");
        }
    }//GEN-LAST:event_jBotonEdit_ProvActionPerformed

    private void jBotonElim_ProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonElim_ProvActionPerformed

        int i = JOptionPane.showConfirmDialog(this, "Eliminar Proveedor?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (i == 0) {

            if (!jTextNumID_Prov.getText().trim().equals("")) {
                control_Proveedores funcion = new control_Proveedores();

                funcion.setId_persona(Integer.parseInt(jTextNumID_Prov.getText()));

                if (funcion.EliminarProveedores(funcion)) {
                    JOptionPane.showMessageDialog(null, "Proveedor Eliminado");
                    Mostrar();
                    Limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se elimino Proveedor, Ingrese el numero que identifica dicho Proveedor");
            }
        }
    }//GEN-LAST:event_jBotonElim_ProvActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrupoBotones;
    private javax.swing.JButton jBotonAgre_Prov;
    private javax.swing.JButton jBotonEdit_Prov;
    private javax.swing.JButton jBotonElim_Prov;
    private javax.swing.JLabel jEtiqApe_Prov;
    private javax.swing.JLabel jEtiqDirec_Prov;
    private javax.swing.JLabel jEtiqNomCom_Prov;
    private javax.swing.JLabel jEtiqNom_Prov;
    private javax.swing.JLabel jEtiqNumDoc_Prov;
    private javax.swing.JLabel jEtiqNumID_Prov;
    private javax.swing.JLabel jEtiqTD_Prov;
    private javax.swing.JLabel jEtiqTel_Prov;
    private javax.swing.JRadioButton jRbDNI_Prov;
    private javax.swing.JRadioButton jRbLC_Prov;
    private javax.swing.JScrollPane jSP1_Prov;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla_Prov;
    private javax.swing.JTextField jTextApe_Prov;
    private javax.swing.JTextField jTextDirec_Prov;
    private javax.swing.JTextField jTextNomCom_Prov;
    private javax.swing.JTextField jTextNom_Prov;
    private javax.swing.JTextField jTextNroDoc_Prov;
    private javax.swing.JTextField jTextNumID_Prov;
    private javax.swing.JTextField jTextTel_Prov;
    // End of variables declaration//GEN-END:variables
}
