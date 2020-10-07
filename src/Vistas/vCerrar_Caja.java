package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Caja_Turno;
import Controlador.control_Cajas;
import Controlador.control_CerrarCaja;
import Controlador.control_Movimientos_Caja;
import Controlador.control_Turnos;
import Controlador.control_TiposEmpleados;
import Modelo.Caja;
import Modelo.Caja_Turno;
import Modelo.Session;
import Modelo.Turnos;
import static Vistas.vMenuPrincipal.jMenuItem12;
import static Vistas.vMenuPrincipal.jMenuItem29;
import static Vistas.vMenuPrincipal.jMenuItem30;
import java.util.Date;
import javax.swing.JOptionPane;
import static Vistas.vMenuPrincipal.jMenuCompras;
import static Vistas.vMenuPrincipal.jMenuVentas;
import static Vistas.vMenuPrincipal.JMenuInsumos;
import static Vistas.vMenuPrincipal.jMenuEmpleados;
import static Vistas.vMenuPrincipal.jMenuReportes;
import static Vistas.vMenuPrincipal.jMenuGastos;
import static Vistas.vMenuPrincipal.jMenuConfiguracion;
import static Vistas.vMenuPrincipal.jButtonVentas;
import static Vistas.vMenuPrincipal.jButtonCompras;
import static Vistas.vMenuPrincipal.jButtonGastos;
import static Vistas.vMenuPrincipal.jButtonInsumos;
import static Vistas.vMenuPrincipal.jButtonProveedores;

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

    public vCerrar_Caja() {
        initComponents();
        Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate 
        jDateFecha.setDate(objDate);
        jLabelNomUsuario.setText(Session.getLogin());
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
            jLabeNomlTurno.setVisible(false);
            jLabelMensaje.setVisible(true);
            jLabelMensaje.setText("No hay Caja abierta");
            desabilitarCampos();
            DeshabilitarMenus();
        } else if (idcaja != 0) {
            caja = cc.obtenerCaja(idcaja);
            Caja_Turno caja_turno = cct.obtenerCajaTurno(caja.getIdCaja());
            //turno = ct.obtenerTurno(idturno);
            String dato = cct.ObtenerTurno();
            jLabeNomlTurno.setVisible(true);
            jLabeNomlTurno.setText(dato);
            //jTextCajaChica.setText(Float.toString(caja.getMonto()));
            jLabelMensaje.setVisible(false);
        }
    }

    public void DeshabilitarMenus() {
        jMenuCompras.setEnabled(false);
        jMenuVentas.setEnabled(false);
        JMenuInsumos.setEnabled(false);
        jMenuEmpleados.setEnabled(false);
        jMenuReportes.setEnabled(false);
        jMenuGastos.setEnabled(false);
        jMenuConfiguracion.setEnabled(false);
        jMenuItem12.setEnabled(false);
        jMenuItem30.setEnabled(false);
        jMenuItem29.setEnabled(false);
        jButtonVentas.setEnabled(false);
        jButtonCompras.setEnabled(false);
        jButtonGastos.setEnabled(false);
        jButtonProveedores.setEnabled(false);
        jButtonInsumos.setEnabled(false);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextCajaChica = new javax.swing.JTextField();
        jLabelCajaChica = new javax.swing.JLabel();
        jLabelTotalVentas = new javax.swing.JLabel();
        jTextVentas = new javax.swing.JTextField();
        jLabelGastos = new javax.swing.JLabel();
        jTextGastos = new javax.swing.JTextField();
        jLabelEmpleados = new javax.swing.JLabel();
        jTextTotal = new javax.swing.JTextField();
        btnCerrarCaja = new javax.swing.JButton();
        jTextEmpleados = new javax.swing.JTextField();
        jLabelTotal = new javax.swing.JLabel();
        jLabelFecha = new javax.swing.JLabel();
        jLabelUsuario = new javax.swing.JLabel();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        jLabelNomUsuario = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabelMensaje = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabeNomlTurno = new javax.swing.JLabel();
        jLabelVentasLocal = new javax.swing.JLabel();
        jLabelVentasOnline = new javax.swing.JLabel();
        jTextVentasLocal = new javax.swing.JTextField();
        jTextVentasOnline = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cierre de Caja");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N

        jTextCajaChica.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextCajaChica.setEnabled(false);

        jLabelCajaChica.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelCajaChica.setText("Caja chica:");

        jLabelTotalVentas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTotalVentas.setText("Total Ventas");

        jTextVentas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextVentas.setEnabled(false);

        jLabelGastos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelGastos.setText("Gastos:");

        jTextGastos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextGastos.setEnabled(false);

        jLabelEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelEmpleados.setText("Empleados:");

        jTextTotal.setEditable(false);
        jTextTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        btnCerrarCaja.setBackground(new java.awt.Color(252, 249, 57));
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

        jLabelTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTotal.setText("Total:");

        jLabelFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelFecha.setText("Fecha:");

        jLabelUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelUsuario.setText("Usuario:");

        jDateFecha.setDateFormatString("dd/MM/yyyy HH:mm:ss");
        jDateFecha.setEnabled(false);
        jDateFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelNomUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNomUsuario.setText("Usuario");

        btnCancelar.setBackground(new java.awt.Color(240, 87, 49));
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

        jLabeNomlTurno.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabeNomlTurno.setText("turno elegido");

        jLabelVentasLocal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelVentasLocal.setText("Ventas Locales (Efectivo - Pedidos YA)");

        jLabelVentasOnline.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelVentasOnline.setText("Ventas Online (Mercado Pago)");

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
                            .addComponent(jLabelEmpleados)
                            .addComponent(jLabelGastos)
                            .addComponent(jLabelTotalVentas)
                            .addComponent(jLabelCajaChica)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelTotal)
                                .addGap(13, 13, 13))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelFecha)
                                .addComponent(jLabel8)))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 595, Short.MAX_VALUE)
                                .addComponent(jLabelUsuario)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelNomUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextCajaChica, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabeNomlTurno)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelVentasLocal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextVentasLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelVentasOnline)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextVentasOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(btnCerrarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelUsuario)
                                .addComponent(jLabelNomUsuario)))
                        .addComponent(jLabelFecha, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jDateFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabeNomlTurno, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCajaChica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCajaChica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVentasLocal)
                    .addComponent(jTextVentasLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVentasOnline)
                    .addComponent(jTextVentasOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    public static com.toedter.calendar.JDateChooser jDateFecha;
    public static javax.swing.JLabel jLabeNomlTurno;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCajaChica;
    private javax.swing.JLabel jLabelEmpleados;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelGastos;
    public static javax.swing.JLabel jLabelMensaje;
    private javax.swing.JLabel jLabelNomUsuario;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalVentas;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLabel jLabelVentasLocal;
    private javax.swing.JLabel jLabelVentasOnline;
    public static javax.swing.JTextField jTextCajaChica;
    public static javax.swing.JTextField jTextEmpleados;
    public static javax.swing.JTextField jTextGastos;
    public static javax.swing.JTextField jTextTotal;
    public static javax.swing.JTextField jTextVentas;
    public static javax.swing.JTextField jTextVentasLocal;
    public static javax.swing.JTextField jTextVentasOnline;
    // End of variables declaration//GEN-END:variables
}
