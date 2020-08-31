package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_PermisosPantallasPerfiles;
import Controlador.control_TiposUsuarios;
import Controlador.control_existencias;
import Modelo.PermisosPantallaPerfiles;
import Modelo.TiposUsuarios;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_PermisosPantallasPerfiles extends javax.swing.JInternalFrame {

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modelo2;
    Object[][] datostabla;
    Object[] tipouser;
    vGestion_PermisosPantallasPerfiles per = null;
    TiposUsuarios tu = new TiposUsuarios();
    control_TiposUsuarios tipousuario = new control_TiposUsuarios();
    PermisosPantallaPerfiles p = new PermisosPantallaPerfiles();
    control_existencias combo = new control_existencias();
    control_PermisosPantallasPerfiles permisos = new control_PermisosPantallasPerfiles();
    Sentencias_sql sql = new Sentencias_sql();

    public vGestion_PermisosPantallasPerfiles() {
        initComponents();
        AgregarColumnasPantalla();
        MostrarTablaPantallas();
        TiposUsuarios();
        EliminarItemsVacios();
        EliminarFilasVacias();
        EliminarFilasVaciasPantallas();
    }

    public void TiposUsuarios() {
        tipouser = combo.combox("tiposusuarios", "descripcion");
        for (Object tiposuser : tipouser) {
            jComboBoxTipoUsuario.addItem((String) tiposuser);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBoxTipoUsuario.getItemCount(); i++) {
            if (jComboBoxTipoUsuario.getItemAt(i) == null) {
                jComboBoxTipoUsuario.removeItemAt(i);
            }
        }
    }

    public void MostrarTablaPantallas() {
        sql.Permisos(2, 2, modelo, jTableSeleccionarPermisos);
    }

    public void EliminarFilasVacias() {
        if (jTable1.getRowCount() != 0) {
            if (jTable1.getRowCount() != 0) {
                for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                    for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                        if (jTable1.getValueAt(fila, columna) == null) {
                            modelo2.removeRow(fila);
                        }
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasPantallas() {
        if (jTableSeleccionarPermisos.getRowCount() != 0) {
            if (jTableSeleccionarPermisos.getRowCount() != 0) {
                for (int columna = 0; columna < jTableSeleccionarPermisos.getColumnCount(); columna++) {
                    for (int fila = 0; fila < jTableSeleccionarPermisos.getRowCount(); fila++) {
                        if (jTableSeleccionarPermisos.getValueAt(fila, columna) == null) {
                            modelo.removeRow(fila);
                        }
                    }
                }
            }
        }
    }

    public void ocultar_columnas() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(3).setMinWidth(0);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(0);
    }

    public void AgregarColumnasPantalla() {
        modelo.addColumn("PANTALLA");
        modelo.addColumn("SELECCIONAR");
        jTableSeleccionarPermisos.setModel(modelo);
        CheckBox();
    }

    public void CheckBox() {
        jTableSeleccionarPermisos.getColumnModel().getColumn(1).setCellRenderer(jTableSeleccionarPermisos.getDefaultRenderer(Boolean.class));
        jTableSeleccionarPermisos.getColumnModel().getColumn(1).setCellEditor(jTableSeleccionarPermisos.getDefaultEditor(Boolean.class));
    }

    public void ValidarBotonAgregarPantallas() {
        if (jTable1.getRowCount() != 0) {
            jButtonAgregarPantallas.setEnabled(false);
        } else {
            jButtonAgregarPantallas.setEnabled(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vHabilitarPermisosPantallasPerfiles = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSeleccionarPermisos = new javax.swing.JTable(){
            boolean estado= false;
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if(colIndex == 0){
                    estado = false;
                } else {
                    estado = true;
                }
                return estado;
            }
        };
        jButtonHabilitarPermisos = new javax.swing.JButton();
        jButtonCancelarPermisos = new javax.swing.JButton();
        jLabelTipoUsuarioElegido = new javax.swing.JLabel();
        jLabelNomTipoUsuarioElegido = new javax.swing.JLabel();
        jButtonAgregarPantallas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jComboBoxTipoUsuario = new javax.swing.JComboBox<>();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jLabelTipoUsuario = new javax.swing.JLabel();

        vHabilitarPermisosPantallasPerfiles.setTitle("Habilitar/Modificar Pantallas");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vHabilitarPermisosPantallasPerfiles.setIconImage(icono);
        vHabilitarPermisosPantallasPerfiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vHabilitarPermisosPantallasPerfilesMouseClicked(evt);
            }
        });
        vHabilitarPermisosPantallasPerfiles.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vHabilitarPermisosPantallasPerfilesWindowClosing(evt);
            }
        });
        vHabilitarPermisosPantallasPerfiles.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableSeleccionarPermisos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableSeleccionarPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Título 1", "Título 2"
            }
        ));
        jTableSeleccionarPermisos.setFocusable(false);
        jTableSeleccionarPermisos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableSeleccionarPermisos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSeleccionarPermisosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableSeleccionarPermisos);

        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 310, 410));

        jButtonHabilitarPermisos.setBackground(new java.awt.Color(252, 249, 57));
        jButtonHabilitarPermisos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonHabilitarPermisos.setText("Habilitar Permisos");
        jButtonHabilitarPermisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHabilitarPermisosActionPerformed(evt);
            }
        });
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jButtonHabilitarPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        jButtonCancelarPermisos.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelarPermisos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarPermisos.setText("Cancelar");
        jButtonCancelarPermisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarPermisosActionPerformed(evt);
            }
        });
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jButtonCancelarPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, 100, -1));

        jLabelTipoUsuarioElegido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTipoUsuarioElegido.setText("Tipo Usuario:");
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jLabelTipoUsuarioElegido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 30));

        jLabelNomTipoUsuarioElegido.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNomTipoUsuarioElegido.setToolTipText("");
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jLabelNomTipoUsuarioElegido, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 220, 30));

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Permisos Pantallas a Tipos Usuarios");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jButtonAgregarPantallas.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarPantallas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarPantallas.setText("Agregar Pantallas..");
        jButtonAgregarPantallas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarPantallasActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBoxTipoUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBoxTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo..." }));
        jComboBoxTipoUsuario.setFocusable(false);
        jComboBoxTipoUsuario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoUsuarioItemStateChanged(evt);
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

        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar Permisos");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jLabelTipoUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTipoUsuario.setText("Tipo de Usuario:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonAgregarPantallas)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTipoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAgregarPantallas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(167, 167, 167))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarPantallasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarPantallasActionPerformed
        if (!jComboBoxTipoUsuario.getSelectedItem().equals("Seleccionar Tipo Usuario")) {
            vHabilitarPermisosPantallasPerfiles.setSize(345, 560);
            jLabelNomTipoUsuarioElegido.setText(jComboBoxTipoUsuario.getSelectedItem().toString());
            vHabilitarPermisosPantallasPerfiles.setLocationRelativeTo(this);
            vHabilitarPermisosPantallasPerfiles.setModal(true);
            this.dispose();
            vHabilitarPermisosPantallasPerfiles.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un Tipo de Usuario valido");
        }
    }//GEN-LAST:event_jButtonAgregarPantallasActionPerformed

    private void vHabilitarPermisosPantallasPerfilesWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vHabilitarPermisosPantallasPerfilesWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vHabilitarPermisosPantallasPerfiles.dispose();
            if (per == null || per.isClosed()) {
                per = new vGestion_PermisosPantallasPerfiles();
                vMenuPrincipal.jDesktopPane1.add(per);
                jComboBoxTipoUsuario.setSelectedItem(jLabelNomTipoUsuarioElegido.getText());
                per.setVisible(true);
            }
        }
    }//GEN-LAST:event_vHabilitarPermisosPantallasPerfilesWindowClosing

    private void jButtonHabilitarPermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHabilitarPermisosActionPerformed
        int compras = 0, ventas = 0, prov = 0, prod = 0, cliet = 0, caja = 0, insumos = 0, empleados = 0, gastos = 0, reportes = 0, administrador = 0, user = 0, contest = 0, contestmod = 0;
        int contcomp = 0, contventa = 0, contprov = 0, contprod = 0, contclie = 0, contcaja = 0, continsu = 0, contemp = 0, contgasto = 0, contreport = 0, contadmin = 0, contuser = 0, total = 0;
        String prove = "Proveedores", produ = "Productos", cliente = "Clientes", usuario = "Usuarios";
        if (jTableSeleccionarPermisos.getRowCount() != 0 && !jLabelNomTipoUsuarioElegido.getText().equals("Seleccionar Tipo Usuario")) {
            if (jButtonHabilitarPermisos.getText().equals("Habilitar Permisos")) {
                for (int i = 0; i < jTableSeleccionarPermisos.getRowCount(); i++) {
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.jMenuCompras.getText())) {
                        compras = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(prove)) {
                        prov = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.jMenuVentas.getText())) {
                        ventas = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(produ)) {
                        prod = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(cliente)) {
                        cliet = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.JMenuInsumos.getText())) {
                        insumos = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.jMenuEmpleados.getText())) {
                        empleados = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.jMenuReportes.getText())) {
                        reportes = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.jMenuGastos.getText())) {
                        gastos = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.jMenuCaja.getText())) {
                        caja = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(vMenuPrincipal.jMenuConfiguracion.getText())) {
                        administrador = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(usuario)) {
                        user = i;
                    }
                    if (jTableSeleccionarPermisos.getValueAt(i, 1).equals(true)) {
                        contest = contest + 1;
                    }
                }

                if (jTableSeleccionarPermisos.getValueAt(compras, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(compras, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contcomp = contcomp + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(prov, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(prov, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contprov += 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(ventas, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(ventas, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contventa = contventa + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(prod, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(prod, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contprod += 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(cliet, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(cliet, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contclie += 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(insumos, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(insumos, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        continsu = continsu + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(empleados, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(empleados, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contemp = contemp + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(reportes, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(reportes, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contreport = contreport + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(gastos, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(gastos, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contgasto = contgasto + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(caja, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(caja, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contcaja = contcaja + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(administrador, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(administrador, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contadmin = contadmin + 1;
                    }
                } else {

                }
                if (jTableSeleccionarPermisos.getValueAt(user, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTableSeleccionarPermisos.getValueAt(user, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contuser = contuser + 1;
                    }
                }

                total = contcomp + contventa + contprov + contprod + contclie + contcaja + continsu + contemp + contgasto + contreport + contadmin + contuser;
                if (contest == total) {
                    JOptionPane.showMessageDialog(null, "Pantallas Seleccionadas Habilitadas");
                    vHabilitarPermisosPantallasPerfiles.dispose();
                    if (per == null || per.isClosed()) {
                        per = new vGestion_PermisosPantallasPerfiles();
                        vMenuPrincipal.jDesktopPane1.add(per);
                        jComboBoxTipoUsuario.setSelectedItem(jLabelNomTipoUsuarioElegido.getText());
                        per.setVisible(true);
                    }
                }
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    for (int k = 0; k < jTableSeleccionarPermisos.getRowCount(); k++) {
                        if (jTableSeleccionarPermisos.getValueAt(k, 1).equals(true)) {
                            contestmod = contestmod + 1;
                        }
                    }
                    if (contestmod == 0) {
                        p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                        if (permisos.EliminarPermisos2(p)) {
                            JOptionPane.showMessageDialog(null, "No selecciono ninguna pantalla a habilitar, por lo tanto los permisos fueron eliminados");
                            vHabilitarPermisosPantallasPerfiles.dispose();
                            if (per == null || per.isClosed()) {
                                per = new vGestion_PermisosPantallasPerfiles();
                                vMenuPrincipal.jDesktopPane1.add(per);
                                jComboBoxTipoUsuario.setSelectedItem(jLabelNomTipoUsuarioElegido.getText());
                                per.setVisible(true);
                            }
                        }
                    } else {
                        p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                        if (permisos.EliminarPermisos2(p)) {
                            /*for (int l = 0; l < jTable2.getRowCount(); l++) {
                                if (jTable2.getValueAt(l, 0).toString().equals("Compras")) {
                                    compras = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(l, 0).toString().equals("Proveedores")) {
                                    prov = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(l, 0).toString().equals("Ventas")) {
                                    ventas = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(l, 0).toString().equals("Productos")) {
                                    prod = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(1, 0).toString().equals("Clientes")) {
                                    cliet = 1;
                                    l++;
                                }                                
                                if (jTable2.getValueAt(l, 0).toString().equals("Insumos")) {
                                    insumos = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(l, 0).toString().equals("Empleados")) {
                                    empleados = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(l, 0).toString().equals("Reportes")) {
                                    reportes = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(l, 0).toString().equals("Gastos")) {
                                    gastos = l;
                                    l++;
                                }                                
                                if (jTable2.getValueAt(l, 0).toString().equals("Caja")) {
                                    caja = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(l, 0).toString().equals("Administrar")) {
                                    administrador = l;
                                    l++;
                                }
                                if (jTable2.getValueAt(1, 0).toString().equals("Usuarios")) {
                                    user = 1;
                                    l++;
                                }
                            }*/

                            for (int j = 0; j < jTableSeleccionarPermisos.getRowCount(); j++) {
                                if (jTableSeleccionarPermisos.getValueAt(j, 1).equals(true)) {
                                    contest = contest + 1;
                                }
                            }

                            if (jTableSeleccionarPermisos.getValueAt(0, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Compras"));
                                if (permisos.InsertarPermisos(p)) {
                                    contcomp = contcomp + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(1, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Proveedores"));
                                if (permisos.InsertarPermisos(p)) {
                                    contprov += 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(2, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Ventas"));
                                if (permisos.InsertarPermisos(p)) {
                                    contventa = contventa + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(3, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Productos"));
                                if (permisos.InsertarPermisos(p)) {
                                    contprod += 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(4, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Clientes"));
                                if (permisos.InsertarPermisos(p)) {
                                    contclie += 1;
                                }
                            } else {

                            }

                            if (jTableSeleccionarPermisos.getValueAt(5, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Insumos"));
                                if (permisos.InsertarPermisos(p)) {
                                    continsu = continsu + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(6, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Empleados"));
                                if (permisos.InsertarPermisos(p)) {
                                    contemp = contemp + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(7, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Reportes"));
                                if (permisos.InsertarPermisos(p)) {
                                    contreport = contreport + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(8, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Gastos"));
                                if (permisos.InsertarPermisos(p)) {
                                    contgasto = contgasto + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(9, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Caja"));
                                if (permisos.InsertarPermisos(p)) {
                                    contcaja = contcaja + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(10, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Administrar"));
                                if (permisos.InsertarPermisos(p)) {
                                    contadmin = contadmin + 1;
                                }
                            } else {

                            }
                            if (jTableSeleccionarPermisos.getValueAt(11, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabelNomTipoUsuarioElegido.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Usuarios"));
                                if (permisos.InsertarPermisos(p)) {
                                    contuser = contuser + 1;
                                }
                            }
                            total = contcomp + contventa + contprov + contprod + contclie + contcaja + continsu + contemp + contgasto + contreport + contadmin + contuser;
                            if (contest == total) {
                                JOptionPane.showMessageDialog(null, "Permisos Modificados");
                                vHabilitarPermisosPantallasPerfiles.dispose();
                                if (per == null || per.isClosed()) {
                                    per = new vGestion_PermisosPantallasPerfiles();
                                    vMenuPrincipal.jDesktopPane1.add(per);
                                    jComboBoxTipoUsuario.setSelectedItem(jLabelNomTipoUsuarioElegido.getText());
                                    per.setVisible(true);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tabla vacia y/o nombre Tipo Usuario no valido");
        }
    }//GEN-LAST:event_jButtonHabilitarPermisosActionPerformed

    private void jTableSeleccionarPermisosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSeleccionarPermisosMouseClicked
        /*int i=jTable2.getSelectedRow(), j= jTable2.getSelectedColumn();
        JOptionPane.showMessageDialog(null, i);
        JOptionPane.showMessageDialog(null, j);*/
    }//GEN-LAST:event_jTableSeleccionarPermisosMouseClicked

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        if (!jComboBoxTipoUsuario.getSelectedItem().equals("Seleccionar Tipo Usuario")) {
            if (jTable1.getRowCount() != 0) {
                jButtonHabilitarPermisos.setText("Modificar Permisos");
                vHabilitarPermisosPantallasPerfiles.setSize(345, 550);
                jLabelNomTipoUsuarioElegido.setText(jComboBoxTipoUsuario.getSelectedItem().toString());
                java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gastronomia2.png")).getImage();
                vHabilitarPermisosPantallasPerfiles.setIconImage(icono);
                vHabilitarPermisosPantallasPerfiles.setLocationRelativeTo(this);
                vHabilitarPermisosPantallasPerfiles.setModal(true);
                this.dispose();
                boolean vista = this == null;
                for (int i = 0; i < jTableSeleccionarPermisos.getRowCount(); i++) {
                    for (int j = 0; j < jTable1.getRowCount(); j++) {
                        if (jTableSeleccionarPermisos.getValueAt(i, 0).equals(jTable1.getValueAt(j, 4))) {
                            jTableSeleccionarPermisos.setValueAt(true, i, 1);
                        }
                    }
                }
                vHabilitarPermisosPantallasPerfiles.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Este Tipo de Usuario no tiene dichos permisos habilitados");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un Tipo de Usuario valido");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jComboBoxTipoUsuarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoUsuarioItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String[] columnas = {"IDPERMISOPANTALLAPERFIL", "IDTIPOUSUARIO", "IDPANTALLA", "TIPO USUARIO", "PANTALLAS HABILITADAS"};
            datostabla = permisos.MostrarDatos(evt.getItem().toString());
            modelo2 = new DefaultTableModel(datostabla, columnas);
            jTable1.setModel(modelo2);
            EliminarFilasVacias();
            ocultar_columnas();
            ValidarBotonAgregarPantallas();
        }
    }//GEN-LAST:event_jComboBoxTipoUsuarioItemStateChanged

    private void jButtonCancelarPermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarPermisosActionPerformed
        if (jButtonHabilitarPermisos.getText().equals("Modificar Permisos")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vHabilitarPermisosPantallasPerfiles.dispose();
                if (per == null || per.isClosed()) {
                    per = new vGestion_PermisosPantallasPerfiles();
                    vMenuPrincipal.jDesktopPane1.add(per);
                    jComboBoxTipoUsuario.setSelectedItem(jLabelNomTipoUsuarioElegido.getText());
                    per.setVisible(true);
                }
            }
        } else {
            int j = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (j == 0) {
                vHabilitarPermisosPantallasPerfiles.dispose();
                if (per == null || per.isClosed()) {
                    per = new vGestion_PermisosPantallasPerfiles();
                    vMenuPrincipal.jDesktopPane1.add(per);
                    jComboBoxTipoUsuario.setSelectedItem(jLabelNomTipoUsuarioElegido.getText());
                    per.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jButtonCancelarPermisosActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        if (!jComboBoxTipoUsuario.getSelectedItem().equals("Seleccionar Tipo Usuario")) {
            if (jTable1.getRowCount() != 0) {
                int i = JOptionPane.showConfirmDialog(null, "Eliminar Permisos?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jComboBoxTipoUsuario.getSelectedItem().toString()));
                    if (permisos.EliminarPermisos2(p)) {
                        String[] columnas = {"IDPERMISOPANTALLAPERFIL", "IDTIPOUSUARIO", "IDPANTALLA", "TIPO USUARIO", "PANTALLAS HABILITADAS"};
                        datostabla = permisos.ActualizacionEliminar();
                        modelo2 = new DefaultTableModel(datostabla, columnas);
                        jTable1.setModel(modelo2);
                        EliminarFilasVacias();
                        ocultar_columnas();
                        ValidarBotonAgregarPantallas();
                        JOptionPane.showMessageDialog(null, "Permisos Eliminados");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Este Tipo de Usuario no tiene dichos permisos habilitados");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un Tipo de Usuario valido");
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }//GEN-LAST:event_formMouseClicked

    private void vHabilitarPermisosPantallasPerfilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vHabilitarPermisosPantallasPerfilesMouseClicked
        jTableSeleccionarPermisos.clearSelection();
        jTableSeleccionarPermisos.getSelectionModel().clearSelection();
    }//GEN-LAST:event_vHabilitarPermisosPantallasPerfilesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarPantallas;
    private javax.swing.JButton jButtonCancelarPermisos;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonHabilitarPermisos;
    private javax.swing.JButton jButtonModificar;
    public static javax.swing.JComboBox<String> jComboBoxTipoUsuario;
    private javax.swing.JLabel jLabelNomTipoUsuarioElegido;
    private javax.swing.JLabel jLabelTipoUsuario;
    private javax.swing.JLabel jLabelTipoUsuarioElegido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jTableSeleccionarPermisos;
    private javax.swing.JDialog vHabilitarPermisosPantallasPerfiles;
    // End of variables declaration//GEN-END:variables
}
