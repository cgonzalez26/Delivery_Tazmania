package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Asistencias;
import Controlador.control_Movimientos_Caja;
import Controlador.control_existencias;
import Modelo.Asistencias;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Asistencias extends javax.swing.JInternalFrame {

    String fecha, date, desde, hasta;
    public String id;
    control_Asistencias asistencia = new control_Asistencias();
    Asistencias a = new Asistencias();
    control_existencias con = new control_existencias();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    Movimientos_Caja mc = new Movimientos_Caja();
    Sentencias_sql sql = new Sentencias_sql();
    DefaultTableModel row, modelemp, buscarfechas;
    Timestamp fechaseleccionada;
    DefaultListModel list;
    ArrayList<String> listemp;
    vLista_Asistencias lista = null;

    public vGestion_Asistencias() {
        initComponents();
        MostrarEmpleados();
        listaEmpleados.setVisible(false);
    }

    public void limpiar() {
        jTextFieldEmpleado.setText("");
        jTextFieldDescripcion.setText("");
        ((JTextField) jDateFecha.getDateEditor().getUiComponent()).setText("");
        jTextFieldSueldo.setText("");
    }

    public void VolverListaAsistencias() {
        lista = new vLista_Asistencias();
        vMenuPrincipal.jDesktopPane1.add(lista);
        lista.setVisible(true);
        this.dispose();
    }

    public void MostrarEmpleados() {
        String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
        Object[][] datostabla = asistencia.MostrarEmpleados();
        modelemp = new DefaultTableModel(datostabla, columnas);
        jTableEmpleados.setModel(modelemp);
        EliminarFilasVaciasEmpleados();
    }

    public void ListasEmpleado() {
        listemp = con.list("empleados", "Nombre", jTextFieldEmpleado.getText());
        String substr = jTextFieldEmpleado.getText().toLowerCase();
        list = new DefaultListModel();
        listaEmpleados.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listemp.size(); i++) {
            if (listemp.get(i) == null) {
                listemp.remove(i);
            } else {
                String sublist = listemp.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listemp.get(i));
                    listaEmpleados.setVisible(true);
                    if (jTextFieldEmpleado.getText().isEmpty()) {
                        listaEmpleados.setVisible(false);
                    }
                }
            }
        }
    }

    public void AjustarTamañoFilasEmpleados() {
        if (jTableEmpleados.getRowCount() != 0) {
            for (int i = 0; i < jTableEmpleados.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int emp = (int) font.getStringBounds(jTableEmpleados.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (emp > jTableEmpleados.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTableEmpleados.getColumnModel().getColumn(0).setPreferredWidth(emp);
                }
            }
        }
    }
    
    public void EliminarFilasVaciasEmpleados() {
        if (jTableEmpleados.getRowCount() != 0) {
            int filas = jTableEmpleados.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTableEmpleados.getValueAt(fila, 0) == null) {
                    modelemp.removeRow(fila);
                }
            }
        }
    }

    public void Cerrar() {
        if (!jTextFieldEmpleado.getText().trim().equals("") || !jTextFieldDescripcion.getText().trim().isEmpty() || !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().trim().isEmpty() || !jTextFieldSueldo.getText().trim().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaAsistencias();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
            VolverListaAsistencias();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarEmpleado = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEmpleados = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonAgregarEmpleado = new javax.swing.JButton();
        jButtonCancelarEmpleado = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelNombreEmpleado = new javax.swing.JLabel();
        jTextFieldBuscarEmpleado = new javax.swing.JTextField();
        jButtonBuscarEmpleado = new javax.swing.JButton();
        jLabelFecha = new javax.swing.JLabel();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        jLabelEmpleado = new javax.swing.JLabel();
        jTextFieldEmpleado = new javax.swing.JTextField();
        jButtonSeleccionarEmpleados = new javax.swing.JButton();
        listaEmpleados = new javax.swing.JList<>();
        jLabelDescripcion = new javax.swing.JLabel();
        jTextFieldDescripcion = new javax.swing.JTextField();
        jButtonAgregarAsistencia = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jLabelSueldo = new javax.swing.JLabel();
        jTextFieldSueldo = new javax.swing.JTextField();
        jButtonCancelar = new javax.swing.JButton();

        vSeleccionarEmpleado.setTitle("Seleccionar Nombre Empleado");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vSeleccionarEmpleado.setIconImage(icono);
        vSeleccionarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarEmpleadoMouseClicked(evt);
            }
        });
        vSeleccionarEmpleado.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarEmpleadoWindowClosing(evt);
            }
        });

        jTableEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableEmpleados);

        jButtonAgregarEmpleado.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarEmpleado.setText("Agregar");
        jButtonAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarEmpleadoActionPerformed(evt);
            }
        });

        jButtonCancelarEmpleado.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelarEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarEmpleado.setText("Cancelar");
        jButtonCancelarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarEmpleadoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelNombreEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreEmpleado.setText("Nombre Empleado");

        jTextFieldBuscarEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarEmpleado.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarEmpleado.setText("Buscar");
        jButtonBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextFieldBuscarEmpleado))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabelNombreEmpleado)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jButtonBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabelNombreEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscarEmpleado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vSeleccionarEmpleadoLayout = new javax.swing.GroupLayout(vSeleccionarEmpleado.getContentPane());
        vSeleccionarEmpleado.getContentPane().setLayout(vSeleccionarEmpleadoLayout);
        vSeleccionarEmpleadoLayout.setHorizontalGroup(
            vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jButtonAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButtonCancelarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        vSeleccionarEmpleadoLayout.setVerticalGroup(
            vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregarEmpleado)
                    .addComponent(jButtonCancelarEmpleado))
                .addContainerGap())
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Asistencias");
        setFocusable(false);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(860, 649));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelFecha.setText("(*) Fecha:");
        getContentPane().add(jLabelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 56, 19));

        jDateFecha.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        getContentPane().add(jDateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 172, 30));

        jLabelEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelEmpleado.setText("(*) Empleado:");
        getContentPane().add(jLabelEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 170, 30));

        jTextFieldEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldEmpleadoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEmpleadoKeyTyped(evt);
            }
        });
        getContentPane().add(jTextFieldEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 250, 30));

        jButtonSeleccionarEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarEmpleadosActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSeleccionarEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 38, 30));

        listaEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        listaEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaEmpleados.setValueIsAdjusting(true);
        listaEmpleados.setVisibleRowCount(0);
        listaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaEmpleadosMouseClicked(evt);
            }
        });
        getContentPane().add(listaEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 250, 0));

        jLabelDescripcion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDescripcion.setText("(*) Descripción:");
        getContentPane().add(jLabelDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 30));

        jTextFieldDescripcion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextFieldDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 300, 35));

        jButtonAgregarAsistencia.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarAsistencia.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarAsistencia.setText("Agregar");
        jButtonAgregarAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarAsistenciaActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonAgregarAsistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 100, 30));

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, 100, 30));

        jLabelSueldo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelSueldo.setText("Sueldo:");
        getContentPane().add(jLabelSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, 21));

        jTextFieldSueldo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextFieldSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 172, 35));

        jButtonCancelar.setBackground(new java.awt.Color(240, 87, 49));
        jButtonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 100, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarAsistenciaActionPerformed
        if (!jTextFieldEmpleado.getText().trim().equals("") && !jTextFieldDescripcion.getText().trim().equals("") && !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().equals("")) {
            if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                date = ((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText();
                if (jTextFieldSueldo.getText().equals("")) {
                    int i = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextFieldEmpleado.getText()));
                        a.setDescripcion(jTextFieldDescripcion.getText());
                        a.setSueldo((float) 0.0);
                        if (asistencia.InsertarAsistencias(a)) {
                            int idmovimiento = sql.obtenerUltimoId("asistencias", "idasistencia");
                            String codigo = sql.generaCodigo("pago_emp");
                            sql.ejecutarSql("UPDATE asistencias SET NroAsistencia ='" + codigo + "' WHERE idasistencia=" + Integer.toString(idmovimiento));
                            a.setNroAsistencia(codigo);
                            mc.setDescripcion("PAGO EMPLEADOS");
                            mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                            mc.setIdtipomovimiento(11);
                            mc.setIdusuario(Session.getIdusuario());
                            mc.setNromovimiento(codigo);
                            mc.setFecha_movimiento(date);
                            mc.setMonto(a.getSueldo());
                            mc.setIdmovimiento(idmovimiento);
                            mc.setDetalle(a.getDescripcion());
                            mc.setActivo(1);
                            control_mc.InsertarMovimientosCaja(mc);
                            JOptionPane.showMessageDialog(null, "Nueva Asistencia agregado");
                            //Mostrar();
                            VolverListaAsistencias();
                            //limpiar();
                        }
                    }
                } else {
                    String sueldo = jTextFieldSueldo.getText(), svalor = "", svalordec = "", svalordecdob = "";
                    int cant = jTextFieldSueldo.getText().length();
                    switch (cant) {
                        case 1:
                            svalor = sueldo.substring(0, 1);
                            break;
                        case 3:
                            svalordec = sueldo.substring(0, 3);
                            break;
                        case 4:
                            svalordecdob = sueldo.substring(0, 4);
                            break;
                        default:
                            break;
                    }
                    if (svalor.equals(",") || svalor.equals(".")) { //svalor.equals(",") || svalor.equals(".")
                        JOptionPane.showMessageDialog(null, "Ingrese correctamente el sueldo");
                    } else if (svalor.equals("0") || svalordec.equals("0.0") || svalordecdob.equals("0.00")) {
                        int j = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (j == 0) {
                            a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextFieldEmpleado.getText()));
                            a.setDescripcion(jTextFieldDescripcion.getText());
                            a.setSueldo((float) 0.0);
                            if (asistencia.InsertarAsistencias(a)) {
                                int idmovimiento = sql.obtenerUltimoId("asistencias", "idasistencia");
                                String codigo = sql.generaCodigo("pago_emp");
                                sql.ejecutarSql("UPDATE asistencias SET NroAsistencia ='" + codigo + "' WHERE idasistencia=" + Integer.toString(idmovimiento));
                                a.setNroAsistencia(codigo);
                                mc.setDescripcion("PAGO EMPLEADOS");
                                mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                                mc.setIdtipomovimiento(11);
                                mc.setIdusuario(Session.getIdusuario());
                                mc.setNromovimiento(codigo);
                                mc.setFecha_movimiento(date);
                                mc.setMonto(a.getSueldo());
                                mc.setIdmovimiento(idmovimiento);
                                mc.setDetalle(a.getDescripcion());
                                mc.setActivo(1);
                                control_mc.InsertarMovimientosCaja(mc);
                                JOptionPane.showMessageDialog(null, "Nueva Asistencia agregado");
                                VolverListaAsistencias();
                                //Mostrar();
                                //limpiar();
                            }
                        }
                    } else {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextFieldEmpleado.getText()));
                        a.setDescripcion(jTextFieldDescripcion.getText());
                        a.setSueldo(Float.parseFloat(sueldo));
                        if (asistencia.InsertarAsistencias(a)) {
                            int idmovimiento = sql.obtenerUltimoId("asistencias", "idasistencia");
                            String codigo = sql.generaCodigo("pago_emp");
                            sql.ejecutarSql("UPDATE asistencias SET NroAsistencia ='" + codigo + "' WHERE idasistencia=" + Integer.toString(idmovimiento));
                            a.setNroAsistencia(codigo);
                            mc.setDescripcion("PAGO EMPLEADOS");
                            mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                            mc.setIdtipomovimiento(11);
                            mc.setIdusuario(Session.getIdusuario());
                            mc.setNromovimiento(codigo);
                            mc.setFecha_movimiento(date);
                            mc.setMonto(a.getSueldo());
                            mc.setIdmovimiento(idmovimiento);
                            mc.setDetalle(a.getDescripcion());
                            mc.setActivo(1);
                            control_mc.InsertarMovimientosCaja(mc);
                            JOptionPane.showMessageDialog(null, "Nueva Asistencia agregado");
                            VolverListaAsistencias();
                            //Mostrar();
                            //limpiar();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonAgregarAsistenciaActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        /*if (jButtonModificar.getText().equals("Modificar")) {
            int s = jTable1.getSelectedRow();
            if (s == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                jButtonAgregarAsistencia.setEnabled(false);
                jButton3.setText("Modificar");
                jButtonModificar.setText("Cancelar");
                fecha = (String) (jTable1.getValueAt(s, 6));
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());

                } catch (ParseException ex) {
                    Logger.getLogger(vListas_Compras.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                id = (jTable1.getValueAt(s, 0).toString());
                jTextFieldEmpleado.setText(jTable1.getValueAt(s, 3).toString());
                jTextField2.setText(jTable1.getValueAt(s, 4).toString());
                jTextFieldSueldo.setText(jTable1.getValueAt(s, 5).toString());
                jDateFecha.setDate(fechaseleccionada);
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jButtonAgregarAsistencia.setEnabled(true);
                limpiar();
                LimpiarSeleccion();
                jButtonModificar.setText("Modificar");
                jButton3.setText("Eliminar");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        }*/
        if (!jTextFieldEmpleado.getText().trim().equals("") && !jTextFieldDescripcion.getText().trim().equals("") && !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().equals("")) {
            date = ((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText();
            if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (jTextFieldSueldo.getText().equals("")) {
                    int i = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, guardar cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextFieldEmpleado.getText()));
                        a.setDescripcion(jTextFieldDescripcion.getText());
                        a.setSueldo((float) 0.0);
                        a.setIdasistencia(Integer.parseInt(id));
                        if (asistencia.EditarAsistencias(a)) {
                            mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(id), 11));
                            mc.setIdtipomovimiento(11);
                            mc.setIdusuario(Session.getIdusuario());
                            mc.setFecha_movimiento(date);
                            mc.setMonto(a.getSueldo());
                            mc.setIdmovimiento(Integer.parseInt(id));
                            mc.setDetalle(a.getDescripcion());
                            control_mc.EditarMovimientosCaja(mc);

                            JOptionPane.showMessageDialog(null, "Modificado");
                            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            //Mostrar();
                            //limpiar();
                            VolverListaAsistencias();
                        }
                    }
                } else {
                    String sueldo = jTextFieldSueldo.getText(), svalor = "", svalordec = "", svalordecdob = "";
                    int cant = jTextFieldSueldo.getText().length();
                    switch (cant) {
                        case 1:
                            svalor = sueldo.substring(0, 1);
                            break;
                        case 3:
                            svalordec = sueldo.substring(0, 3);
                            break;
                        case 4:
                            svalordecdob = sueldo.substring(0, 4);
                            break;
                        default:
                            break;
                    }
                    if (svalor.equals(",") || svalor.equals(".")) { //svalor.equals(",") || svalor.equals(".")
                        JOptionPane.showMessageDialog(null, "Ingrese correctamente el sueldo");
                    } else if (svalor.equals("0") || svalordec.equals("0.0") || svalordecdob.equals("0.00")) {
                        int j = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, guardar cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (j == 0) {
                            a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextFieldEmpleado.getText()));
                            a.setDescripcion(jTextFieldDescripcion.getText());
                            a.setSueldo((float) 0.0);
                            a.setIdasistencia(Integer.parseInt(id));
                            if (asistencia.EditarAsistencias(a)) {
                                mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(id), 11));
                                mc.setIdtipomovimiento(11);
                                mc.setIdusuario(Session.getIdusuario());
                                mc.setFecha_movimiento(date);
                                mc.setMonto(a.getSueldo());
                                mc.setIdmovimiento(Integer.parseInt(id));
                                mc.setDetalle(a.getDescripcion());
                                control_mc.EditarMovimientosCaja(mc);

                                JOptionPane.showMessageDialog(null, "Modificado");
                                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                //Mostrar();
                                //limpiar();
                                VolverListaAsistencias();
                            }
                        }
                    } else {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextFieldEmpleado.getText()));
                        a.setDescripcion(jTextFieldDescripcion.getText());
                        a.setSueldo(Float.parseFloat(sueldo));
                        a.setIdasistencia(Integer.parseInt(id));
                        if (asistencia.EditarAsistencias(a)) {
                            mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(id), 11));
                            mc.setIdtipomovimiento(11);
                            mc.setIdusuario(Session.getIdusuario());
                            mc.setFecha_movimiento(date);
                            mc.setMonto(a.getSueldo());
                            mc.setIdmovimiento(Integer.parseInt(id));
                            mc.setDetalle(a.getDescripcion());
                            control_mc.EditarMovimientosCaja(mc);

                            JOptionPane.showMessageDialog(null, "Modificado");
                            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            //Mostrar();
                            //limpiar();
                            VolverListaAsistencias();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (!jButtonAgregarAsistencia.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaAsistencias();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextFieldEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmpleadoKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextFieldEmpleadoKeyTyped

    private void jButtonAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarEmpleadoActionPerformed
        if (jTableEmpleados.getRowCount() != 0) {
            int i = jTableEmpleados.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarEmpleado.dispose();
                jTextFieldEmpleado.setText(jTableEmpleados.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos empleados todavia");
        }
    }//GEN-LAST:event_jButtonAgregarEmpleadoActionPerformed

    private void jButtonCancelarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarEmpleadoActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
            jTextFieldEmpleado.setText("");
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButtonCancelarEmpleadoActionPerformed

    private void vSeleccionarEmpleadoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
            jTextFieldEmpleado.setText("");
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarEmpleadoWindowClosing

    private void jButtonSeleccionarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarEmpleadosActionPerformed
        vSeleccionarEmpleado.setSize(390, 633);
        vSeleccionarEmpleado.setLocationRelativeTo(this);
        vSeleccionarEmpleado.setModal(true);
        vSeleccionarEmpleado.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarEmpleadosActionPerformed

    private void vSeleccionarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoMouseClicked
        jTableEmpleados.clearSelection();
        jTableEmpleados.getSelectionModel().clearSelection();
        MostrarEmpleados();
        jTextFieldBuscarEmpleado.setText("");
    }//GEN-LAST:event_vSeleccionarEmpleadoMouseClicked

    private void jButtonBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarEmpleadoActionPerformed
        if (!jTextFieldBuscarEmpleado.getText().isEmpty()) {
            String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
            Object[][] datostabla = asistencia.MostrarEmpleadoBuscado(jTextFieldBuscarEmpleado.getText());
            if (datostabla.length != 0) {
                modelemp = new DefaultTableModel(datostabla, columnas);
                jTableEmpleados.setModel(modelemp);
                EliminarFilasVaciasEmpleados();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarEmpleadoActionPerformed

    private void listaEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaEmpleadosMouseClicked
        int i = listaEmpleados.getSelectedIndex();
        if (i != -1) {
            jTextFieldEmpleado.setText(listaEmpleados.getSelectedValue());
            listaEmpleados.setVisible(false);
        }
    }//GEN-LAST:event_listaEmpleadosMouseClicked

    private void jTextFieldEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmpleadoKeyReleased
        ListasEmpleado();
    }//GEN-LAST:event_jTextFieldEmpleadoKeyReleased

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        if (!jButtonAgregarAsistencia.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaAsistencias();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jButtonAgregarAsistencia;
    private javax.swing.JButton jButtonAgregarEmpleado;
    private javax.swing.JButton jButtonBuscarEmpleado;
    public static javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonCancelarEmpleado;
    public static javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonSeleccionarEmpleados;
    public static com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jLabelDescripcion;
    private javax.swing.JLabel jLabelEmpleado;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelNombreEmpleado;
    private javax.swing.JLabel jLabelSueldo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableEmpleados;
    private javax.swing.JTextField jTextFieldBuscarEmpleado;
    public static javax.swing.JTextField jTextFieldDescripcion;
    public static javax.swing.JTextField jTextFieldEmpleado;
    public static javax.swing.JTextField jTextFieldSueldo;
    private javax.swing.JList<String> listaEmpleados;
    private javax.swing.JDialog vSeleccionarEmpleado;
    // End of variables declaration//GEN-END:variables
}
