package Vistas;

import Controlador.control_Proveedores;
import Modelo.Proveedores;
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
                    vGestion_Proveedores.jButtonAgregar.setEnabled(false);
                    vGestion_Proveedores.jButtonModificar.setEnabled(true);
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
        String[] columnas = {"IDPROV", "ID TIPODOC", "TIPO DOC", "NRO DOCUMENTO", "NOMBRES", "APELLIDOS", "NOMBRE COMERCIAL", "DOMICILIO", "TELEFONO"};
        datostabla = prov.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Prov.setModel(datos);
        EliminarFilasVacias();
        //AjustarTamañoFilas();
        ocultar_columnas();
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
        jButtonNuevo = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelNombreComercial = new javax.swing.JLabel();
        jTextFieldProveedorBuscar = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 1090, 218));

        jButtonNuevo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevo.setText("Nuevo");
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 100, 30));

        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 360, 100, 30));

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 100, 30));

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombreComercial.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreComercial.setText("Nombre Comercial");

        jTextFieldProveedorBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

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
                .addGap(19, 19, 19)
                .addComponent(jLabelNombreComercial)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldProveedorBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(194, 194, 194))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombreComercial)
                    .addComponent(jTextFieldProveedorBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscar)
                .addGap(6, 6, 6))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        proveedor = new vGestion_Proveedores();
        proveedor.jButtonAgregar.setEnabled(true);
        proveedor.jButtonModificar.setEnabled(false);
        vMenuPrincipal.jDesktopPane1.add(proveedor);
        proveedor.setVisible(true);
        proveedor.toFront();
        this.dispose();
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
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
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
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
//            vGestion_Proveedores.jBotonAgre_Prov2.setText("Cancelar");
            vGestion_Proveedores.jButtonAgregar.setEnabled(false);
            vGestion_Proveedores.jButtonModificar.setEnabled(true);
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
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        //jTextFieldProveedorBuscar.setText("");
        //Mostrar();
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if (!jTextFieldProveedorBuscar.getText().isEmpty()) {
            datostabla = prov.MostrarDatosBusqueda(jTextFieldProveedorBuscar.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDPROV", "ID TIPODOC", "TIPO DOC", "NRODOC", "NOMBRES", "APELLIDOS", "NOMBRE COMERCIAL", "DIRECCION", "TELEFONO"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_Prov.setModel(datos);
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
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JLabel jLabelNombreComercial;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Prov;
    private javax.swing.JTextField jTextFieldProveedorBuscar;
    // End of variables declaration//GEN-END:variables
}
