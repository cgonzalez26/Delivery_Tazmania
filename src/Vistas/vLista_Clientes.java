package Vistas;

import Controlador.control_Clientes;
import Modelo.Clientes;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Clientes extends javax.swing.JInternalFrame {

    vGestion_Clientes vcliente = null;
    control_Clientes contr_clientes = new control_Clientes();
    Clientes cliente = new Clientes();
    Object[][] datostabla;
    DefaultTableModel tabla;
    String id;

    public vLista_Clientes() {
        initComponents();
        Mostrar();
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    vcliente = new vGestion_Clientes();
                    vMenuPrincipal.jDesktopPane1.add(vcliente);
                    vcliente.setVisible(true);
                    vGestion_Clientes.jButton3.setText("Cancelar");
                    vGestion_Clientes.jButton1.setEnabled(true);
                    id = jTable1.getValueAt(fila, 0).toString();
                    vGestion_Clientes.jTextField6.setText(jTable1.getValueAt(fila, 1).toString());
                    vGestion_Clientes.jTextField1.setText(jTable1.getValueAt(fila, 2).toString());
                    vGestion_Clientes.jTextField2.setText(jTable1.getValueAt(fila, 3).toString());
                    vGestion_Clientes.jTextField3.setText(jTable1.getValueAt(fila, 4).toString());
                    vGestion_Clientes.jTextField4.setText(jTable1.getValueAt(fila, 5).toString());
                    vGestion_Clientes.jTextField5.setText(jTable1.getValueAt(fila, 6).toString());
                    vcliente.idcliente = id;
                    dispose();
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void ocultar_columnas() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void Mostrar() {
        String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
        datostabla = contr_clientes.MostrarDatos();
        tabla = new DefaultTableModel(datostabla, columnas);
        jTable1.setModel(tabla);
        EliminarFilasVacias();
        ocultar_columnas();
    }

    public void EliminarFilasVacias() {
        if (jTable1.getRowCount() != 0) {
            for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                    if (jTable1.getValueAt(fila, columna) == null) {
                        tabla.removeRow(fila);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado Clientes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Nombre");

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("DNI");

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Telefono");

        jTextField3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(425, 425, 425))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Nuevo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Eliminar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        vcliente = new vGestion_Clientes();
        vMenuPrincipal.jDesktopPane1.add(vcliente);
        vcliente.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            vcliente = new vGestion_Clientes();
            vMenuPrincipal.jDesktopPane1.add(vcliente);
            vcliente.setVisible(true);
            vGestion_Clientes.jButton3.setText("Cancelar");
            vGestion_Clientes.jButton1.setEnabled(true);
            id = jTable1.getValueAt(fila, 0).toString();
            vGestion_Clientes.jTextField6.setText(jTable1.getValueAt(fila, 1).toString());
            vGestion_Clientes.jTextField1.setText(jTable1.getValueAt(fila, 2).toString());
            vGestion_Clientes.jTextField2.setText(jTable1.getValueAt(fila, 3).toString());
            vGestion_Clientes.jTextField3.setText(jTable1.getValueAt(fila, 4).toString());
            vGestion_Clientes.jTextField4.setText(jTable1.getValueAt(fila, 5).toString());
            vGestion_Clientes.jTextField5.setText(jTable1.getValueAt(fila, 6).toString());
            vcliente.idcliente = id;
            dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                cliente.setIdcliente(Integer.parseInt(jTable1.getValueAt(i, 0).toString()));
                if (contr_clientes.EliminarClientes(cliente)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jTextField1.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaCliente(jTextField1.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField2.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNI(jTextField2.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField3.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaTelefono(jTextField3.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField2.getText().isEmpty() && !jTextField1.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNICliente(jTextField2.getText(), jTextField1.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField3.getText().isEmpty() && jTextField1.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaClienteTelefono(jTextField1.getText(), jTextField3.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField2.getText().isEmpty() && !jTextField3.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNITelefono(jTextField2.getText(), jTextField3.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && !jTextField3.getText().isEmpty()) {
            datostabla = contr_clientes.MostrarDatosBusquedaDNIClienteTelefono(jTextField2.getText(), jTextField1.getText(), jTextField3.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "MAIL"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTable1.setModel(tabla);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        Mostrar();
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
