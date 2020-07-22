/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Caja_Turno;
import Controlador.control_Cajas;
import Controlador.control_CerrarCaja;
import Controlador.control_Movimientos_Caja;
import Controlador.control_PagosEmpleados;
import Controlador.control_Turnos;
import Controlador.control_TiposEmpleados;
import Modelo.Caja;
import Modelo.Caja_Turno;
import Modelo.Session;
import Modelo.Turnos;
import Modelo.Movimientos_Caja;
import static Vistas.vAbrir_Caja.jTextCajaChica;
import static Vistas.vMenuPrincipal.jButton1;
import static Vistas.vMenuPrincipal.jButton2;
import static Vistas.vMenuPrincipal.jButton3;
import static Vistas.vMenuPrincipal.jButton5;
import static Vistas.vMenuPrincipal.jButton6;
import static Vistas.vMenuPrincipal.jMenu12;
import static Vistas.vMenuPrincipal.jMenu13;
import static Vistas.vMenuPrincipal.jMenu14;
import static Vistas.vMenuPrincipal.jMenu2;
import static Vistas.vMenuPrincipal.jMenu4;
import static Vistas.vMenuPrincipal.jMenu6;
import static Vistas.vMenuPrincipal.jMenu8;
import static Vistas.vMenuPrincipal.jMenuItem12;
import static Vistas.vMenuPrincipal.jMenuItem29;
import static Vistas.vMenuPrincipal.jMenuItem30;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author CRISTIAN
 */
public final class vCerrar_Caja extends javax.swing.JInternalFrame {

    control_Cajas cc = new control_Cajas();
    control_Caja_Turno cct = new control_Caja_Turno();
    control_Turnos ct = new control_Turnos();
    control_TiposEmpleados control_te = new control_TiposEmpleados();
    private Sentencias_sql sql;
    Turnos turno = new Turnos();
    Caja caja;
    int idturno = 0;
    int idcaja = 0;
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    private float pago_empleado = 0;
    vMovimientos_Caja mc = new vMovimientos_Caja();

    /**
     * Creates new form vGestion_Caja
     */
    public vCerrar_Caja() {
        initComponents();
        Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate 
        jDateChooser1.setDate(objDate);
        jLabelUsuario.setText(Session.getLogin());
        if (!jTextTotal.getText().isEmpty()) {
            calcularTotales();
        } else {
            iniciar();
            calcularTotalesIniciales();
        }
        verificarCajaAbierta();
        //hacer esto mismo con todos los text para recalcular total
        /*jTextEmpleados.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calcularTotales();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calcularTotales();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calcularTotales();
            }
        }
        );*/
    }

    private void iniciar() {
        jTextCajaChica.setText("0");
        jTextVentas.setText("0");
        jTextGastos.setText("0");
        jTextEmpleados.setText("0");
        jTextVentasLocal.setText("0");
        jTextVentasOnline.setText("0");
        jTextTotal.setText("0");
    }

    private void calcularTotales() {
        String sCajaChica = jTextCajaChica.getText();
        String stotalVenta = jTextVentas.getText();
        String stotalGastos = jTextGastos.getText();
        String stotalPagosEmpleados = jTextEmpleados.getText();
        String sTotalVentaLocal = jTextVentasLocal.getText();
        String sTotalVentaOnline = jTextVentasOnline.getText();

        Double cajaChica = sCajaChica == null || sCajaChica.isEmpty() ? 0.0 : Double.parseDouble(sCajaChica);
        Double totalVenta = stotalVenta == null || stotalVenta.isEmpty() ? 0.0 : Double.parseDouble(stotalVenta);
        Double totalGastos = stotalGastos == null || stotalGastos.isEmpty() ? 0.0 : Double.parseDouble(stotalGastos);
        Double totalPagosEmpleados = stotalPagosEmpleados == null || stotalPagosEmpleados.isEmpty() ? 0.0 : Double.parseDouble(stotalPagosEmpleados);
        Double totalVentaLocal = sTotalVentaLocal == null || sTotalVentaLocal.isEmpty() ? 0.0 : Double.parseDouble(sTotalVentaLocal);
        Double totalVentaOnline = sTotalVentaOnline == null | sTotalVentaOnline.isEmpty() ? 0.0 : Double.parseDouble(sTotalVentaOnline);
        jTextTotal.setText(Double.toString(cajaChica /*+ totalVenta*/ - totalGastos - totalPagosEmpleados + totalVentaLocal + totalVentaOnline));
    }

    private void calcularTotalesIniciales() {
        Double cajaChica = control_mc.getCajaChica(Session.getIdcajaturno_abierta());
        Double totalVenta = control_mc.getTotalVentas(Session.getIdcajaturno_abierta());
        Double totalGastos = control_mc.getTotalGastos(Session.getIdcajaturno_abierta());
        Double totalPagosEmpleados = control_mc.getTotalPagosEmpleados(Session.getIdcajaturno_abierta());
        Double totalVentaLocal = control_mc.getVentaLocal(Session.getIdcajaturno_abierta());
        Double totalVentaOnline = control_mc.getVentaOnline(Session.getIdcajaturno_abierta());
        
        //System.out.println(cajaChica+" " +totalVenta+" " +totalGastos+" " +totalPagosEmpleados);
        jTextCajaChica.setText(Double.toString(cajaChica));
        jTextVentas.setText(Double.toString(totalVenta));
        jTextGastos.setText(Double.toString(totalGastos));
        jTextEmpleados.setText(Double.toString(totalPagosEmpleados));
        jTextVentasLocal.setText(Double.toString(totalVentaLocal));
        jTextVentasOnline.setText(Double.toString(totalVentaOnline));
        jTextTotal.setText(Double.toString(cajaChica /*+ totalVenta*/ - totalGastos - totalPagosEmpleados + totalVentaLocal + totalVentaOnline));
    }

    public void verificarCajaAbierta() {
        //si no hay Caja abierta entonces NO podemos cerrar
        idcaja = Session.getIdcaja_abierta();
        idturno = Session.getIdturno_abierto();

        if (!cc.existeCajaAbierta() || idcaja == 0) {
            jLabelTurno.setVisible(false);
            jLabelMensaje.setVisible(true);
            jLabelMensaje.setText("No hay Caja abierta");
            desabilitarCampos();
            DeshabilitarMenus();
        } else if (idcaja != 0) {
            caja = cc.obtenerCaja(idcaja);
            Caja_Turno caja_turno = cct.obtenerCajaTurno(caja.getIdCaja());
            //turno = ct.obtenerTurno(idturno);
            String dato = cct.ObtenerTurno();
            jLabelTurno.setVisible(true);
            jLabelTurno.setText(dato);
            //jTextCajaChica.setText(Float.toString(caja.getMonto()));
            jLabelMensaje.setVisible(false);
        }
    }

    public void DeshabilitarMenus() {
        jMenu2.setEnabled(false);
        jMenu12.setEnabled(false);
        jMenu4.setEnabled(false);
        jMenu8.setEnabled(false);
        jMenu13.setEnabled(false);
        jMenu6.setEnabled(false);
        jMenu14.setEnabled(false);
        jMenuItem12.setEnabled(false);
        jMenuItem30.setEnabled(false);
        jMenuItem29.setEnabled(false);
        jButton5.setEnabled(false);
        jButton6.setEnabled(false);
        jButton3.setEnabled(false);
        jButton2.setEnabled(false);
        jButton1.setEnabled(false);
    }

    public void desabilitarCampos() {
        jTextCajaChica.setEnabled(false);
        jTextVentas.setEnabled(false);
        jTextGastos.setEnabled(false);
        jTextEmpleados.setEnabled(false);
        jTextTotal.setEnabled(false);
        btnCerrarCaja.setEnabled(false);
    }

    public void setControlador(control_CerrarCaja cpe) {
        jTextCajaChica.addActionListener(cpe);
        jTextVentas.addFocusListener(cpe);
        jTextGastos.addActionListener(cpe);
        jTextEmpleados.addFocusListener(cpe);
        jTextVentasLocal.addActionListener(cpe);
        jTextVentasOnline.addFocusListener(cpe);
    }

    public void MostrarMovimientosCajas() {
        this.dispose();
        vMenuPrincipal.jDesktopPane1.add(mc);
        mc.show();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextCajaChica = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextVentas = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextGastos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextTotal = new javax.swing.JTextField();
        btnCerrarCaja = new javax.swing.JButton();
        jTextEmpleados = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabelUsuario = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabelMensaje = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelTurno = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextVentasLocal = new javax.swing.JTextField();
        jTextVentasOnline = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cierre de Caja");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N

        jTextCajaChica.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextCajaChica.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Caja chica:");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("Total Ventas");

        jTextVentas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextVentas.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Gastos:");

        jTextGastos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextGastos.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("Empleados:");

        jTextTotal.setEditable(false);
        jTextTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        btnCerrarCaja.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnCerrarCaja.setText("Cerrar Caja");
        btnCerrarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarCajaActionPerformed(evt);
            }
        });

        jTextEmpleados.setEditable(false);
        jTextEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextEmpleadosActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setText("Total:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("Fecha:");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("Usuario:");

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH: mm: ss");
        jDateChooser1.setEnabled(false);
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelUsuario.setText("Usuario");

        btnCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabelMensaje.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelMensaje.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMensaje.setText("jLabel2");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel8.setText("Turno:");

        jLabelTurno.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTurno.setText("jLabel9");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel9.setText("Ventas Local:");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel10.setText("Ventas Online:");

        jTextVentasLocal.setEditable(false);
        jTextVentasLocal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jTextVentasOnline.setEditable(false);
        jTextVentasOnline.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelMensaje)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(13, 13, 13))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8)))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextCajaChica, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTurno)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextVentasLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextVentasOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(btnCerrarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jLabelUsuario)))
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabelTurno, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCajaChica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextVentasLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextVentasOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelMensaje)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrarCaja)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarCajaActionPerformed
        Float cajachica = Float.parseFloat(jTextCajaChica.getText());
        Float ventas = Float.parseFloat(jTextVentas.getText());
        Float gastos = Float.parseFloat(jTextGastos.getText());
        Float empleados = Float.parseFloat(jTextEmpleados.getText());
        Float ventaslocal = Float.parseFloat(jTextVentasLocal.getText());
        Float ventasonline = Float.parseFloat(jTextVentasOnline.getText());
        Float total = cajachica /*+ ventas*/ - gastos - empleados + ventaslocal + ventasonline;
        jTextTotal.setText(total.toString());
        caja.setMonto(total);
        caja.setIdusuario(Session.getIdusuario());
        caja.setEstado("CERRADA");

        if (cc.CerrarCaja(caja, idturno)) {
            //al Cerrar la CAJA no tenemos Cajas Activas hasta que haya una Apertura de Caja
            Session.setIdcaja_abierta(0);
            Session.setIdturno_abierto(0);

            JOptionPane.showMessageDialog(null, "Cierre de Caja Registrada");
            //jButton4.setEnabled(true);
            //MostrarMovimientosCajas();
            verificarCajaAbierta();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo Cerrar la Caja actual");
        }
    }//GEN-LAST:event_btnCerrarCajaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jTextEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEmpleadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnCerrarCaja;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JLabel jLabelMensaje;
    public static javax.swing.JLabel jLabelTurno;
    private javax.swing.JLabel jLabelUsuario;
    public static javax.swing.JTextField jTextCajaChica;
    public static javax.swing.JTextField jTextEmpleados;
    public static javax.swing.JTextField jTextGastos;
    public static javax.swing.JTextField jTextTotal;
    public static javax.swing.JTextField jTextVentas;
    public static javax.swing.JTextField jTextVentasLocal;
    public static javax.swing.JTextField jTextVentasOnline;
    // End of variables declaration//GEN-END:variables
}
