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
                    vGestion_Usuarios.jBotonModif_Usuario.setEnabled(true);
                    vGestion_Usuarios.jBotonAgre_Usuario.setText("Cancelar");
                    id = (jTabla_Usuario.getValueAt(fila, 0).toString());
                    vGestion_Usuarios.jComboBox1.setSelectedItem(jTabla_Usuario.getValueAt(fila, 2).toString());
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

    public void AjustarTamañoFilas() {
        if (jTabla_Usuario.getRowCount() != 0) {
            for (int i = 0; i < jTabla_Usuario.getRowCount(); i++) {
                String nom = jTabla_Usuario.getValueAt(i, 5).toString(), ape = jTabla_Usuario.getValueAt(i, 6).toString(), dir = jTabla_Usuario.getValueAt(i, 7).toString(), log = jTabla_Usuario.getValueAt(i, 3).toString(), correo = jTabla_Usuario.getValueAt(i, 8).toString(), telef = jTabla_Usuario.getValueAt(i, 9).toString(), tiuser = jTabla_Usuario.getValueAt(i, 2).toString(), est = jTabla_Usuario.getValueAt(i, 10).toString();
                if (nom != null && ape != null && dir != null && log != null && correo != null && telef != null && tiuser != null && est != null) {
                    Font font = new Font("Segoe UI Semibold", 0, 13);
                    int nombre = (int) font.getStringBounds(nom, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                    int apellido = (int) font.getStringBounds(ape, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                    int direc = (int) font.getStringBounds(dir, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                    int tel = (int) font.getStringBounds(telef, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                    int mail = (int) font.getStringBounds(correo, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                    int login = (int) font.getStringBounds(log, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                    int tipousuarios = (int) font.getStringBounds(tiuser, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                    int estado = (int) font.getStringBounds(est, new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();

                    if (tipousuarios > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(2).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(2).setPreferredWidth(tipousuarios);
                    }

                    if (login > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(3).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(3).setPreferredWidth(login);
                    }

                    if (nombre > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(5).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(5).setPreferredWidth(nombre);
                    }

                    if (apellido > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(6).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(6).setPreferredWidth(apellido);
                    }

                    if (direc > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(7).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(7).setPreferredWidth(direc);
                    }

                    if (mail > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(8).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(8).setPreferredWidth(mail);
                    }

                    if (tel > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(9).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(9).setPreferredWidth(tel);
                    }

                    if (estado > vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(10).getPreferredWidth()) {
                        vLista_Usuarios.jTabla_Usuario.getColumnModel().getColumn(10).setPreferredWidth(estado);
                    }
                }
            }
        }
    }

    public void Mostrar() {
        String[] columnas = {"IDUSER", "IDTIPOUSER", "TIPO USUARIO", "USUARIO", "CONTRASEÑA", "NOMBRE", "APELLIDO", "DIRECCION", "MAIL", "TELEFONO", "ESTADO"};
        datostabla = usuario.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Usuario.setModel(datos);
        EliminarFilasVacias();
        AjustarTamañoFilas();
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

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

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(252, 249, 57));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Nombre Usuario");

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jBotonNuevo_ListaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1187, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(265, 265, 265))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonNuevo_ListaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int seleccionado = jTabla_Usuario.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            user = new vGestion_Usuarios();
            vMenuPrincipal.jDesktopPane1.add(user);
            user.setVisible(true);
            vGestion_Usuarios.jBotonModif_Usuario.setEnabled(true);
            vGestion_Usuarios.jBotonAgre_Usuario.setText("Cancelar");
            id = (jTabla_Usuario.getValueAt(seleccionado, 0).toString());
            vGestion_Usuarios.jComboBox1.setSelectedItem(jTabla_Usuario.getValueAt(seleccionado, 2).toString());
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        Mostrar();
        jTextField1.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!jTextField1.getText().isEmpty()) {           
            datostabla = usuario.MostrarDatosBusqueda(jTextField1.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDUSER", "IDTIPOUSER", "TIPO USUARIO", "USUARIO", "CONTRASEÑA", "NOMBRE", "APELLIDO", "DIRECCION", "MAIL", "TELEFONO", "ESTADO"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_Usuario.setModel(datos);
                EliminarFilasVacias();
                AjustarTamañoFilas();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonNuevo_ListaUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Usuario;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
