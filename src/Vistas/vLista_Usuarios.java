package Vistas;

import Controlador.control_Usuario;
import Modelo.Usuarios;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Usuarios extends javax.swing.JInternalFrame {

    vGestion_Usuarios user = null;
    control_Usuario usuario = new control_Usuario();
    Usuarios u = new Usuarios();
    Object[][] datostabla;
    String id;
    DefaultTableModel datos;

    public vLista_Usuarios() {
        initComponents();
        Mostrar();
        jTabla_Usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTabla_Usuario.rowAtPoint(e.getPoint());
                    user = new vGestion_Usuarios();
                    vMenuPrincipal.jDesktopPane1.add(user);
                    user.setVisible(true);
                    vGestion_Usuarios.jBotonAgre_Usuario.setEnabled(false);
                    vGestion_Usuarios.jBotonModif_Usuario.setEnabled(true);
                    id = (jTabla_Usuario.getValueAt(fila, 0).toString());
                    vGestion_Usuarios.jComboTipoUsuario.setSelectedItem(jTabla_Usuario.getValueAt(fila, 2).toString());
                    vGestion_Usuarios.jTextLogin_Usuario.setText(jTabla_Usuario.getValueAt(fila, 3).toString());
                    vGestion_Usuarios.jTextPass_Usuario.setText(jTabla_Usuario.getValueAt(fila, 4).toString());
                    vGestion_Usuarios.jTextNom_Usuario.setText(jTabla_Usuario.getValueAt(fila, 5).toString());
                    vGestion_Usuarios.jTextApe_Usuario.setText(jTabla_Usuario.getValueAt(fila, 6).toString());
                    vGestion_Usuarios.jTextDireccion_Usuario.setText(jTabla_Usuario.getValueAt(fila, 7).toString());
                    vGestion_Usuarios.jTextMail_Usuario.setText(jTabla_Usuario.getValueAt(fila, 8).toString());
                    vGestion_Usuarios.jTextTel_Usuario.setText(jTabla_Usuario.getValueAt(fila, 9).toString());
                    vGestion_Usuarios.jComboEstado_Usuario.setSelectedItem(jTabla_Usuario.getValueAt(fila, 10).toString());
                    user.iduser = id;
                    dispose();
                    LimpiarSeleccion();
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTabla_Usuario.clearSelection();
        jTabla_Usuario.getSelectionModel().clearSelection();
    }

    public void ocultar_columnas() {
        jTabla_Usuario.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(0).setMinWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(1).setMaxWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(1).setMinWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(4).setMaxWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(4).setMinWidth(0);
        jTabla_Usuario.getColumnModel().getColumn(4).setPreferredWidth(0);
    }

    public void Mostrar() {
        String[] columnas = {"IDUSER", "IDTIPOUSER", "TIPO USUARIO", "USUARIO", "CONTRASEÑA", "NOMBRES", "APELLIDOS", "DIRECCION", "MAIL", "TELEFONO", "ESTADO"};
        datostabla = usuario.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Usuario.setModel(datos);
        EliminarFilasVacias();
        ocultar_columnas();
    }

    public void EliminarFilasVacias() {
        if (jTabla_Usuario.getRowCount() != 0) {
            for (int columna = 0; columna < jTabla_Usuario.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTabla_Usuario.getRowCount(); fila++) {
                    if (jTabla_Usuario.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_Usuario = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jBotonNuevo_ListaUser = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelNombreUsuario = new javax.swing.JLabel();
        jTextFieldNombreUsuarioBuscar = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Usuarios");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTabla_Usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
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

        jBotonNuevo_ListaUser.setBackground(new java.awt.Color(252, 249, 57));
        jBotonNuevo_ListaUser.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonNuevo_ListaUser.setText("Nuevo");
        jBotonNuevo_ListaUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonNuevo_ListaUserActionPerformed(evt);
            }
        });

        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombreUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreUsuario.setText("Nombre Usuario");

        jTextFieldNombreUsuarioBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabelNombreUsuario)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabelNombreUsuario))
                    .addComponent(jTextFieldNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1187, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(283, 283, 283)
                                .addComponent(jBotonNuevo_ListaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(160, 160, 160)
                                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(154, 154, 154)
                                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(384, 384, 384)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonNuevo_ListaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonNuevo_ListaUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonNuevo_ListaUserActionPerformed
        user = new vGestion_Usuarios();
        vMenuPrincipal.jDesktopPane1.add(user);
        user.setVisible(true);
        dispose();
    }//GEN-LAST:event_jBotonNuevo_ListaUserActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int seleccionado = jTabla_Usuario.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                u.setIdusuario(Integer.parseInt(jTabla_Usuario.getValueAt(seleccionado, 0).toString()));
                if (usuario.EliminarUsuarios(u)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int seleccionado = jTabla_Usuario.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            user = new vGestion_Usuarios();
            vMenuPrincipal.jDesktopPane1.add(user);
            user.setVisible(true);
            vGestion_Usuarios.jBotonAgre_Usuario.setEnabled(false);
            vGestion_Usuarios.jBotonModif_Usuario.setEnabled(true);
            id = (jTabla_Usuario.getValueAt(seleccionado, 0).toString());
            vGestion_Usuarios.jComboTipoUsuario.setSelectedItem(jTabla_Usuario.getValueAt(seleccionado, 2).toString());
            vGestion_Usuarios.jTextLogin_Usuario.setText(jTabla_Usuario.getValueAt(seleccionado, 3).toString());
            vGestion_Usuarios.jTextPass_Usuario.setText(jTabla_Usuario.getValueAt(seleccionado, 4).toString());
            vGestion_Usuarios.jTextNom_Usuario.setText(jTabla_Usuario.getValueAt(seleccionado, 5).toString());
            vGestion_Usuarios.jTextApe_Usuario.setText(jTabla_Usuario.getValueAt(seleccionado, 6).toString());
            vGestion_Usuarios.jTextDireccion_Usuario.setText(jTabla_Usuario.getValueAt(seleccionado, 7).toString());
            vGestion_Usuarios.jTextMail_Usuario.setText(jTabla_Usuario.getValueAt(seleccionado, 8).toString());
            vGestion_Usuarios.jTextTel_Usuario.setText(jTabla_Usuario.getValueAt(seleccionado, 9).toString());
            vGestion_Usuarios.jComboEstado_Usuario.setSelectedItem(jTabla_Usuario.getValueAt(seleccionado, 10).toString());
            user.iduser = id;
            dispose();
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        //Mostrar();
        //jTextFieldNombreUsuarioBuscar.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if (!jTextFieldNombreUsuarioBuscar.getText().isEmpty()) {           
            datostabla = usuario.MostrarDatosBusqueda(jTextFieldNombreUsuarioBuscar.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDUSER", "IDTIPOUSER", "TIPO USUARIO", "USUARIO", "CONTRASEÑA", "NOMBRES", "APELLIDOS", "DIRECCION", "MAIL", "TELEFONO", "ESTADO"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_Usuario.setModel(datos);
                EliminarFilasVacias();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonNuevo_ListaUser;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JLabel jLabelNombreUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Usuario;
    private javax.swing.JTextField jTextFieldNombreUsuarioBuscar;
    // End of variables declaration//GEN-END:variables
}
