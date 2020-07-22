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
            jComboBox1.addItem((String) tiposuser);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i) == null) {
                jComboBox1.removeItemAt(i);
            }
        }
    }

    public void MostrarTablaPantallas() {
        sql.Permisos(2, 2, modelo, jTable2);
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
        if (jTable2.getRowCount() != 0) {
            if (jTable2.getRowCount() != 0) {
                for (int columna = 0; columna < jTable2.getColumnCount(); columna++) {
                    for (int fila = 0; fila < jTable2.getRowCount(); fila++) {
                        if (jTable2.getValueAt(fila, columna) == null) {
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
        jTable2.setModel(modelo);
        CheckBox();
    }

    public void CheckBox() {
        jTable2.getColumnModel().getColumn(1).setCellRenderer(jTable2.getDefaultRenderer(Boolean.class));
        jTable2.getColumnModel().getColumn(1).setCellEditor(jTable2.getDefaultEditor(Boolean.class));
    }

    public void ValidarBotonAgregarPantallas() {
        if (jTable1.getRowCount() != 0) {
            jButton1.setEnabled(false);
        } else {
            jButton1.setEnabled(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vHabilitarPermisosPantallasPerfiles = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
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
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        vHabilitarPermisosPantallasPerfiles.setTitle("Habilitar/Modificar Pantallas");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vHabilitarPermisosPantallasPerfiles.setIconImage(icono);
        vHabilitarPermisosPantallasPerfiles.setPreferredSize(new java.awt.Dimension(345, 560));
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

        jTable2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Título 1", "Título 2"
            }
        ));
        jTable2.setFocusable(false);
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 310, 410));

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Habilitar Permisos");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Cancelar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, 100, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Tipo Usuario:");
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setToolTipText("");
        vHabilitarPermisosPantallasPerfiles.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 220, 30));

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Permisos Pantallas a Tipos Usuarios");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Agregar Pantallas..");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo Usuario" }));
        jComboBox1.setFocusable(false);
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Eliminar Permisos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(67, 67, 67)
                        .addComponent(jButton2)
                        .addGap(61, 61, 61)
                        .addComponent(jButton3)
                        .addGap(167, 167, 167))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jComboBox1.getSelectedItem().equals("Seleccionar Tipo Usuario")) {
            vHabilitarPermisosPantallasPerfiles.setSize(345, 560);
            jLabel2.setText(jComboBox1.getSelectedItem().toString());
            vHabilitarPermisosPantallasPerfiles.setLocationRelativeTo(this);
            vHabilitarPermisosPantallasPerfiles.setModal(true);
            this.dispose();
            vHabilitarPermisosPantallasPerfiles.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un Tipo de Usuario valido");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void vHabilitarPermisosPantallasPerfilesWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vHabilitarPermisosPantallasPerfilesWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vHabilitarPermisosPantallasPerfiles.dispose();
            if (per == null || per.isClosed()) {
                per = new vGestion_PermisosPantallasPerfiles();
                vMenuPrincipal.jDesktopPane1.add(per);
                jComboBox1.setSelectedItem(jLabel2.getText());
                per.setVisible(true);
            }
        }
    }//GEN-LAST:event_vHabilitarPermisosPantallasPerfilesWindowClosing

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int compras = 0, ventas = 0, prov = 0, prod = 0, cliet = 0, caja = 0, insumos = 0, empleados = 0, gastos = 0, reportes = 0, administrador = 0, user = 0, contest = 0, contestmod = 0;
        int contcomp = 0, contventa = 0, contprov = 0, contprod = 0, contclie = 0, contcaja = 0, continsu = 0, contemp = 0, contgasto = 0, contreport = 0, contadmin = 0, contuser = 0, total = 0;
        String prove = "Proveedores", produ = "Productos", cliente = "Clientes", usuario = "Usuarios";
        if (jTable2.getRowCount() != 0 && !jLabel2.getText().equals("Seleccionar Tipo Usuario")) {
            if (jButton4.getText().equals("Habilitar Permisos")) {
                for (int i = 0; i < jTable2.getRowCount(); i++) {
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu2.getText())) {
                        compras = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(prove)) {
                        prov = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu12.getText())) {
                        ventas = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(produ)) {
                        prod = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(cliente)) {
                        cliet = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu4.getText())) {
                        insumos = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu8.getText())) {
                        empleados = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu13.getText())) {
                        reportes = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu6.getText())) {
                        gastos = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu3.getText())) {
                        caja = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(vMenuPrincipal.jMenu14.getText())) {
                        administrador = i;
                    }
                    if (jTable2.getValueAt(i, 0).equals(usuario)) {
                        user = i;
                    }
                    if (jTable2.getValueAt(i, 1).equals(true)) {
                        contest = contest + 1;
                    }
                }

                if (jTable2.getValueAt(compras, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(compras, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contcomp = contcomp + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(prov, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(prov, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contprov += 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(ventas, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(ventas, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contventa = contventa + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(prod, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(prod, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contprod += 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(cliet, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(cliet, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contclie += 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(insumos, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(insumos, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        continsu = continsu + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(empleados, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(empleados, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contemp = contemp + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(reportes, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(reportes, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contreport = contreport + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(gastos, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(gastos, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contgasto = contgasto + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(caja, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(caja, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contcaja = contcaja + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(administrador, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(administrador, 0).toString()));
                    if (permisos.InsertarPermisos(p)) {
                        contadmin = contadmin + 1;
                    }
                } else {

                }
                if (jTable2.getValueAt(user, 1).equals(true)) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                    p.setIdpantalla(permisos.ObtenerIDPantalla(jTable2.getValueAt(user, 0).toString()));
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
                        jComboBox1.setSelectedItem(jLabel2.getText());
                        per.setVisible(true);
                    }
                }
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    for (int k = 0; k < jTable2.getRowCount(); k++) {
                        if (jTable2.getValueAt(k, 1).equals(true)) {
                            contestmod = contestmod + 1;
                        }
                    }
                    if (contestmod == 0) {
                        p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                        if (permisos.EliminarPermisos2(p)) {
                            JOptionPane.showMessageDialog(null, "No selecciono ninguna pantalla a habilitar, por lo tanto los permisos fueron eliminados");
                            vHabilitarPermisosPantallasPerfiles.dispose();
                            if (per == null || per.isClosed()) {
                                per = new vGestion_PermisosPantallasPerfiles();
                                vMenuPrincipal.jDesktopPane1.add(per);
                                jComboBox1.setSelectedItem(jLabel2.getText());
                                per.setVisible(true);
                            }
                        }
                    } else {
                        p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
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

                            for (int j = 0; j < jTable2.getRowCount(); j++) {
                                if (jTable2.getValueAt(j, 1).equals(true)) {
                                    contest = contest + 1;
                                }
                            }

                            if (jTable2.getValueAt(0, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Compras"));
                                if (permisos.InsertarPermisos(p)) {
                                    contcomp = contcomp + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(1, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Proveedores"));
                                if (permisos.InsertarPermisos(p)) {
                                    contprov += 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(2, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Ventas"));
                                if (permisos.InsertarPermisos(p)) {
                                    contventa = contventa + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(3, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Productos"));
                                if (permisos.InsertarPermisos(p)) {
                                    contprod += 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(4, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Clientes"));
                                if (permisos.InsertarPermisos(p)) {
                                    contclie += 1;
                                }
                            } else {

                            }

                            if (jTable2.getValueAt(5, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Insumos"));
                                if (permisos.InsertarPermisos(p)) {
                                    continsu = continsu + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(6, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Empleados"));
                                if (permisos.InsertarPermisos(p)) {
                                    contemp = contemp + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(7, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Reportes"));
                                if (permisos.InsertarPermisos(p)) {
                                    contreport = contreport + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(8, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Gastos"));
                                if (permisos.InsertarPermisos(p)) {
                                    contgasto = contgasto + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(9, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Caja"));
                                if (permisos.InsertarPermisos(p)) {
                                    contcaja = contcaja + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(10, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
                                p.setIdpantalla(permisos.ObtenerIDPantalla("Administrar"));
                                if (permisos.InsertarPermisos(p)) {
                                    contadmin = contadmin + 1;
                                }
                            } else {

                            }
                            if (jTable2.getValueAt(11, 1).equals(true)) {
                                p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jLabel2.getText()));
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
                                    jComboBox1.setSelectedItem(jLabel2.getText());
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
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        /*int i=jTable2.getSelectedRow(), j= jTable2.getSelectedColumn();
        JOptionPane.showMessageDialog(null, i);
        JOptionPane.showMessageDialog(null, j);*/
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!jComboBox1.getSelectedItem().equals("Seleccionar Tipo Usuario")) {
            if (jTable1.getRowCount() != 0) {
                jButton4.setText("Modificar Permisos");
                vHabilitarPermisosPantallasPerfiles.setSize(345, 550);
                jLabel2.setText(jComboBox1.getSelectedItem().toString());
                java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gastronomia2.png")).getImage();
                vHabilitarPermisosPantallasPerfiles.setIconImage(icono);
                vHabilitarPermisosPantallasPerfiles.setLocationRelativeTo(this);
                vHabilitarPermisosPantallasPerfiles.setModal(true);
                this.dispose();
                boolean vista = this == null;
                for (int i = 0; i < jTable2.getRowCount(); i++) {
                    for (int j = 0; j < jTable1.getRowCount(); j++) {
                        if (jTable2.getValueAt(i, 0).equals(jTable1.getValueAt(j, 4))) {
                            jTable2.setValueAt(true, i, 1);
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String[] columnas = {"IDPERMISOPANTALLAPERFIL", "IDTIPOUSUARIO", "IDPANTALLA", "TIPO USUARIO", "PANTALLAS HABILITADAS"};
            datostabla = permisos.MostrarDatos(evt.getItem().toString());
            modelo2 = new DefaultTableModel(datostabla, columnas);
            jTable1.setModel(modelo2);
            EliminarFilasVacias();
            ocultar_columnas();
            ValidarBotonAgregarPantallas();
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (jButton4.getText().equals("Modificar Permisos")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vHabilitarPermisosPantallasPerfiles.dispose();
                if (per == null || per.isClosed()) {
                    per = new vGestion_PermisosPantallasPerfiles();
                    vMenuPrincipal.jDesktopPane1.add(per);
                    jComboBox1.setSelectedItem(jLabel2.getText());
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
                    jComboBox1.setSelectedItem(jLabel2.getText());
                    per.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!jComboBox1.getSelectedItem().equals("Seleccionar Tipo Usuario")) {
            if (jTable1.getRowCount() != 0) {
                int i = JOptionPane.showConfirmDialog(null, "Eliminar Permisos?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    p.setIdtipousuario(permisos.ObtenerIDTipoUsuario(jComboBox1.getSelectedItem().toString()));
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
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }//GEN-LAST:event_formMouseClicked

    private void vHabilitarPermisosPantallasPerfilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vHabilitarPermisosPantallasPerfilesMouseClicked
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
    }//GEN-LAST:event_vHabilitarPermisosPantallasPerfilesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    public static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    private javax.swing.JDialog vHabilitarPermisosPantallasPerfiles;
    // End of variables declaration//GEN-END:variables
}
