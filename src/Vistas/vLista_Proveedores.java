package Vistas;

import Controlador.control_Proveedores;
import Modelo.Proveedores;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Proveedores extends javax.swing.JInternalFrame {

    Object[][] datostabla;
    vGestion_Proveedores proveedor = null;
    control_Proveedores prov = new control_Proveedores();
    Proveedores p = new Proveedores();
    String id;
    DefaultTableModel datos;

    public vLista_Proveedores() {
        initComponents();
        Mostrar();
        jTabla_Prov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    LimpiarSeleccion();
                    int fila = jTabla_Prov.rowAtPoint(e.getPoint());
                    proveedor = new vGestion_Proveedores();
                    vMenuPrincipal.jDesktopPane1.add(proveedor);
                    proveedor.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    proveedor.toFront();
                    proveedor.setVisible(true);
                    vGestion_Proveedores.jBotonAgre_Prov2.setText("Cancelar");
                    vGestion_Proveedores.jBotonModificar_Prov.setEnabled(true);
                    id = (jTabla_Prov.getValueAt(fila, 0).toString());
                    vGestion_Proveedores.jTextNroDoc_Prov.setText(jTabla_Prov.getValueAt(fila, 3).toString());
                    vGestion_Proveedores.jTextNom_Prov.setText(jTabla_Prov.getValueAt(fila, 4).toString());
                    vGestion_Proveedores.jTextApe_Prov.setText(jTabla_Prov.getValueAt(fila, 5).toString());
                    vGestion_Proveedores.jTextNomCom_Prov.setText(jTabla_Prov.getValueAt(fila, 6).toString());
                    vGestion_Proveedores.jTextDirec_Prov.setText(jTabla_Prov.getValueAt(fila, 7).toString());
                    vGestion_Proveedores.jTextTel_Prov.setText(jTabla_Prov.getValueAt(fila, 8).toString());
                    proveedor.idprov = id;
                    dispose();
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTabla_Prov.clearSelection();
        jTabla_Prov.getSelectionModel().clearSelection();
    }

    public void Mostrar() {
        String[] columnas = {"IDPROV", "ID TIPODOC", "TIPO DOC", "NRODOC", "NOMBRE", "APELLIDO", "NOMBRE COMERCIAL", "DIRECCION", "TELEFONO"};
        datostabla = prov.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Prov.setModel(datos);
        EliminarFilasVacias();
        AjustarTamañoFilas();
        ocultar_columnas();
    }

    public void AjustarTamañoFilas() {
        if (jTabla_Prov.getRowCount() != 0) {
            for (int i = 0; i < jTabla_Prov.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nrodoc = (int) font.getStringBounds(jTabla_Prov.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int nombre = (int) font.getStringBounds(jTabla_Prov.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int apellido = (int) font.getStringBounds(jTabla_Prov.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int nomcom = (int) font.getStringBounds(jTabla_Prov.getValueAt(i, 6).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int direc = (int) font.getStringBounds(jTabla_Prov.getValueAt(i, 7).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int tel = (int) font.getStringBounds(jTabla_Prov.getValueAt(i, 8).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nrodoc > jTabla_Prov.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTabla_Prov.getColumnModel().getColumn(3).setPreferredWidth(nrodoc);
                }
                if (nombre > jTabla_Prov.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTabla_Prov.getColumnModel().getColumn(4).setPreferredWidth(nombre);
                }
                if (apellido > jTabla_Prov.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTabla_Prov.getColumnModel().getColumn(5).setPreferredWidth(apellido);
                }
                if (nomcom > jTabla_Prov.getColumnModel().getColumn(6).getPreferredWidth()) {
                    jTabla_Prov.getColumnModel().getColumn(6).setPreferredWidth(nomcom);
                }
                if (direc > jTabla_Prov.getColumnModel().getColumn(7).getPreferredWidth()) {
                    jTabla_Prov.getColumnModel().getColumn(7).setPreferredWidth(direc);
                }
                if (tel > jTabla_Prov.getColumnModel().getColumn(8).getPreferredWidth()) {
                    jTabla_Prov.getColumnModel().getColumn(8).setPreferredWidth(tel);
                }
            }
        }
    }

    public void ocultar_columnas() {
        jTabla_Prov.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabla_Prov.getColumnModel().getColumn(0).setMinWidth(0);
        jTabla_Prov.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTabla_Prov.getColumnModel().getColumn(1).setMaxWidth(0);
        jTabla_Prov.getColumnModel().getColumn(1).setMinWidth(0);
        jTabla_Prov.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTabla_Prov.getColumnModel().getColumn(2).setMaxWidth(0);
        jTabla_Prov.getColumnModel().getColumn(2).setMinWidth(0);
        jTabla_Prov.getColumnModel().getColumn(2).setPreferredWidth(0);

    }

    public void EliminarFilasVacias() {
        if (jTabla_Prov.getRowCount() != 0) {
            for (int columna = 0; columna < jTabla_Prov.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTabla_Prov.getRowCount(); fila++) {
                    if (jTabla_Prov.getValueAt(fila, 0) == null) {
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
        jTabla_Prov = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Proveedores");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(1162, 411));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabla_Prov.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 118, 1125, 218));

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Nuevo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 347, 101, -1));

        jButton2.setBackground(new java.awt.Color(237, 124, 61));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(781, 347, 101, -1));

        jButton3.setBackground(new java.awt.Color(252, 249, 57));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(559, 347, 101, -1));

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Nombre Comercial");

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(194, 194, 194))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(6, 6, 6))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        proveedor = new vGestion_Proveedores();
        vMenuPrincipal.jDesktopPane1.add(proveedor);
        proveedor.setVisible(true);
        proveedor.toFront();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int seleccionado = jTabla_Prov.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(this, "Eliminar Proveedor?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (i == 0) {
                p.setIdProveedor(Integer.parseInt(jTabla_Prov.getValueAt(seleccionado, 0).toString()));
                if (prov.EliminarProveedores(p)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int seleccionado = jTabla_Prov.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            LimpiarSeleccion();
            proveedor = new vGestion_Proveedores();
            vMenuPrincipal.jDesktopPane1.add(proveedor);
            proveedor.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            proveedor.toFront();
            proveedor.setVisible(true);
            vGestion_Proveedores.jBotonAgre_Prov2.setText("Cancelar");
            vGestion_Proveedores.jBotonModificar_Prov.setEnabled(true);
            id = (jTabla_Prov.getValueAt(seleccionado, 0).toString());
            vGestion_Proveedores.jTextNroDoc_Prov.setText(jTabla_Prov.getValueAt(seleccionado, 3).toString());
            vGestion_Proveedores.jTextNom_Prov.setText(jTabla_Prov.getValueAt(seleccionado, 4).toString());
            vGestion_Proveedores.jTextApe_Prov.setText(jTabla_Prov.getValueAt(seleccionado, 5).toString());
            vGestion_Proveedores.jTextNomCom_Prov.setText(jTabla_Prov.getValueAt(seleccionado, 6).toString());
            vGestion_Proveedores.jTextDirec_Prov.setText(jTabla_Prov.getValueAt(seleccionado, 7).toString());
            vGestion_Proveedores.jTextTel_Prov.setText(jTabla_Prov.getValueAt(seleccionado, 8).toString());
            proveedor.idprov = id;
            dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        jTextField1.setText("");
        Mostrar();
    }//GEN-LAST:event_formMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!jTextField1.getText().isEmpty()) {
            datostabla = prov.MostrarDatosBusqueda(jTextField1.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDPROV", "ID TIPODOC", "TIPO DOC", "NRODOC", "NOMBRE", "APELLIDO", "NOMBRE COMERCIAL", "DIRECCION", "TELEFONO"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_Prov.setModel(datos);
                EliminarFilasVacias();
                AjustarTamañoFilas();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Prov;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
