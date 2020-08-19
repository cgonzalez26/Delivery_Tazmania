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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Asistencias extends javax.swing.JInternalFrame {

    String id, fecha, date, desde, hasta;
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
    String idasistencia;

    public vGestion_Asistencias() {
        initComponents();       
        MostrarEmpleados();
        jList2.setVisible(false);
        //((JTextField) jDateChooser1.getDateEditor().getUiComponent()).setEditable(false);

        
    }       

    public void limpiar() {
        txtEmpleado.setText("");
        txtDescripcion.setText("");
        ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).setText("");
        txtSueldo.setText("");
    }  
    

    public void MostrarEmpleados() {
        String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
        Object[][] datostabla = asistencia.MostrarEmpleados();
        modelemp = new DefaultTableModel(datostabla, columnas);
        jTable2.setModel(modelemp);
        EliminarFilasVaciasEmpleados();
        AjustarTamañoFilasEmpleados();
    }

    public void ListasEmpleado() {
        listemp = con.list("empleados", "Nombre", txtEmpleado.getText());
        String substr = txtEmpleado.getText().toLowerCase();
        list = new DefaultListModel();
        jList2.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listemp.size(); i++) {
            if (listemp.get(i) == null) {
                listemp.remove(i);
            } else {
                String sublist = listemp.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listemp.get(i));
                    jList2.setVisible(true);
                    if (txtEmpleado.getText().isEmpty()) {
                        jList2.setVisible(false);
                    }
                }
            }
        }
    }

    

    public void AjustarTamañoFilasEmpleados() {
        if (jTable2.getRowCount() != 0) {
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int emp = (int) font.getStringBounds(jTable2.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (emp > jTable2.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(0).setPreferredWidth(emp);
                }
            }
        }
    }
   

    public void EliminarFilasVaciasEmpleados() {
        if (jTable2.getRowCount() != 0) {
            int filas = jTable2.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable2.getValueAt(fila, 0) == null) {
                    modelemp.removeRow(fila);
                }
            }
        }
    }

    public void Cerrar() {
        if (!txtEmpleado.getText().trim().equals("") || !txtDescripcion.getText().trim().isEmpty() || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().trim().isEmpty() || !txtSueldo.getText().trim().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarEmpleado = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jList2 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSueldo = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();

        vSeleccionarEmpleado.setTitle("Seleccionar Nombre Empleado");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
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

        jTable2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Aceptar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setText("Cancelar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("Nombre Empleado");

        jTextField4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton8.setText("Buscar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
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
                        .addComponent(jTextField4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
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
                        .addGap(29, 29, 29)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
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
                    .addComponent(jButton5)
                    .addComponent(jButton6))
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
        setPreferredSize(new java.awt.Dimension(694, 300));
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
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        getContentPane().add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 250, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("(*) Fecha:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 56, 19));

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 172, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) Empleado:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 170, 30));

        txtEmpleado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmpleadoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpleadoKeyTyped(evt);
            }
        });
        getContentPane().add(txtEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 250, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 38, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("(*) Descripción:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 30));

        txtDescripcion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 300, 35));

        btnAgregar.setBackground(new java.awt.Color(252, 249, 57));
        btnAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 101, -1));

        btnModificar.setBackground(new java.awt.Color(252, 249, 57));
        btnModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, 101, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Sueldo:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, 21));

        txtSueldo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtSueldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSueldoActionPerformed(evt);
            }
        });
        getContentPane().add(txtSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 172, 35));

        btnCancelar.setBackground(new java.awt.Color(240, 87, 49));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void volverPrincipal(){
        lista = new vLista_Asistencias();
        vMenuPrincipal.jDesktopPane1.add(lista);
        lista.setVisible(true);
        this.dispose();
    }
    
    private void agregarMovimientoCaja(){
    }
    
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (!txtEmpleado.getText().trim().equals("") && !txtDescripcion.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
            if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                date = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
                if (txtSueldo.getText().equals("")) {
                    int i = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(txtEmpleado.getText()));
                        a.setDescripcion(txtDescripcion.getText());
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
                            //limpiar();
                            volverPrincipal();
                        }
                    }
                } else {
                    String sueldo = txtSueldo.getText(), svalor = "", svalordec = "", svalordecdob = "";
                    int cant = txtSueldo.getText().length();
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
                            a.setIdempleado(asistencia.ObtenerIDEmpleado(txtEmpleado.getText()));
                            a.setDescripcion(txtDescripcion.getText());
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
                                //limpiar();
                                volverPrincipal();
                            }
                        }
                    } else {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(txtEmpleado.getText()));
                        a.setDescripcion(txtDescripcion.getText());
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
                            //Mostrar();
                            //impiar();
                            volverPrincipal();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar los campos obligatorios");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
                 
        //fecha = (String) (jTable1.getValueAt(s, 6));
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        try {
//            fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
//
//        } catch (ParseException ex) {
//            Logger.getLogger(vListas_Compras.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
       if (!txtEmpleado.getText().trim().equals("(*) Seleccionar Empleado..") && !txtDescripcion.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
            if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    a.setIdempleado(asistencia.ObtenerIDEmpleado(txtEmpleado.getText()));
                    a.setDescripcion(txtDescripcion.getText());
                    a.setIdasistencia(Integer.parseInt(id));
                    //a.setFecha_asistencia(fechaseleccionada);
                    if (asistencia.EditarAsistencias(a)) {
                       volverPrincipal();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar los campos obligatorios");
        }      
    }//GEN-LAST:event_btnModificarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (btnModificar.isEnabled() && btnModificar.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpleadoKeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtEmpleadoKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (jTable2.getRowCount() != 0) {
            int i = jTable2.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarEmpleado.dispose();
                txtEmpleado.setText(jTable2.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos empleados todavia");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
            txtEmpleado.setText("");
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void vSeleccionarEmpleadoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
            txtEmpleado.setText("");
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarEmpleadoWindowClosing

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        vSeleccionarEmpleado.setSize(390, 633);
        vSeleccionarEmpleado.setLocationRelativeTo(this);
        vSeleccionarEmpleado.setModal(true);
        vSeleccionarEmpleado.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        /*desde = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser3.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccion();
        desde = "";
        hasta = "";
        IniciarFechas();
        Mostrar();*/
    }//GEN-LAST:event_formMouseClicked

    private void vSeleccionarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoMouseClicked
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
        MostrarEmpleados();
        jTextField4.setText("");
    }//GEN-LAST:event_vSeleccionarEmpleadoMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (!jTextField4.getText().isEmpty()) {
            String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
            Object[][] datostabla = asistencia.MostrarEmpleadoBuscado(jTextField4.getText());
            if (datostabla.length != 0) {
                modelemp = new DefaultTableModel(datostabla, columnas);
                jTable2.setModel(modelemp);
                EliminarFilasVaciasEmpleados();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        if (i != -1) {
            txtEmpleado.setText(jList2.getSelectedValue());
            jList2.setVisible(false);
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void txtEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpleadoKeyReleased
        ListasEmpleado();
    }//GEN-LAST:event_txtEmpleadoKeyReleased

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int i = JOptionPane.showConfirmDialog(null, "Desea cancelar la Operación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
//            lista = new vLista_Asistencias();
//            vMenuPrincipal.jDesktopPane1.add(lista);
//            lista.setVisible(true);
//            this.dispose();
            volverPrincipal();
        } else {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtSueldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSueldoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSueldoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAgregar;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnModificar;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField4;
    public static javax.swing.JTextField txtDescripcion;
    public static javax.swing.JTextField txtEmpleado;
    public static javax.swing.JTextField txtSueldo;
    private javax.swing.JDialog vSeleccionarEmpleado;
    // End of variables declaration//GEN-END:variables
}
