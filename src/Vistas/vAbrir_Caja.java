package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Caja_Turno;
import Controlador.control_Cajas;
import Controlador.control_Movimientos_Caja;
import Controlador.control_existencias;
import Modelo.Caja;
import Modelo.Caja_Turno;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import Modelo.Turnos;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static Vistas.vMenuPrincipal.jMenuCompras;
import static Vistas.vMenuPrincipal.jMenuVentas;
import static Vistas.vMenuPrincipal.JMenuInsumos;
import static Vistas.vMenuPrincipal.jMenuEmpleados;
import static Vistas.vMenuPrincipal.jMenuReportes;
import static Vistas.vMenuPrincipal.jMenuGastos;
import static Vistas.vMenuPrincipal.jMenuConfiguracion;

/**
 *
 * @author CRISTIAN
 */
public final class vAbrir_Caja extends javax.swing.JInternalFrame {

    control_existencias ce = new control_existencias();
    Object[] turnos;
    control_Cajas cc = new control_Cajas();
    control_Caja_Turno control_ct = new control_Caja_Turno();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    vMovimientos_Caja movimientoscajas;
    private Sentencias_sql sql;
    HashMap<String, Integer> lista_turnos = new HashMap<String, Integer>();
    String id = "";

    //List<Turnos> turnos = new ArrayList();
    /**
     * Creates new form vAbrir_Caja
     */
    public vAbrir_Caja() {
        initComponents();
        jLabelMensaje.setText("");
        Date objDate = new Date();
        jDateFecha.setDate(objDate);
        jLabelNomUsuario.setText(Session.getLogin());
        ComboTurnos();
        EliminarItemsVacios();
        verificarCajaAbierta();
    }

    public void verificarCajaAbierta() {
        if (this.getTitle().equals("Apertura de Caja")) {
            if (cc.existeCajaAbierta()) {
                int idcaja = Session.getIdcaja_abierta();
                //verificamos si la caja fue abierta en este turno
                Caja caja;
                if (idcaja == 0) {
                    caja = cc.obtenerUltimaCaja();
                    Caja_Turno caja_turno = control_ct.obtenerCajaTurno(caja.getIdCaja());
                    Session.setIdcaja_abierta(caja.getIdCaja());
                    Session.setIdturno_abierto(caja_turno.getIdTurno());
                    //System.out.println(caja.getIdCaja()+"--"+caja_turno.getIdTurno()+"--"+caja.getMonto());
                } else {
                    caja = cc.obtenerCaja(idcaja);
                }
                String dato = control_ct.ObtenerTurno();
                //fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                //jDateChooser1.setDate(fechaseleccionada);
                Date objFechaCaja = new Date(caja.getFecha_apertura().getTime());
                jDateFecha.setDate(objFechaCaja);
                cbxTurnos.setSelectedItem(dato);
                jTextCajaChica.setText(Float.toString(caja.getMonto()));
                jLabelMensaje.setText("Existe una Caja Abierta para el Turno");
                HabilitarMenus();
                desabilitarCampos();
            } else {
                //sino hay Caja abierta entonces podemos abrir una Caja
                //obtener el saldo de la Ultima Caja
                Caja caja = cc.obtenerUltimaCaja();
                Double monto_caja_anterior = control_ct.getCierreCaja(caja);
                jTextCajaChica.setText(Double.toString(monto_caja_anterior));
            }
        }
    }

    public void EliminarItemsVacios() {
        for (int x = 0; x < cbxTurnos.getItemCount(); x++) {
            if (cbxTurnos.getItemAt(x) == null) {
                cbxTurnos.remove(x);
            }
        }
    }

    public void desabilitarCampos() {
        cbxTurnos.setEnabled(false);
        jTextCajaChica.setEnabled(false);
        btnAbrirCaja.setEnabled(false);
    }

    public void HabilitarMenus() {
        jMenuCompras.setEnabled(true);
        jMenuVentas.setEnabled(true);
        JMenuInsumos.setEnabled(true);
        jMenuEmpleados.setEnabled(true);
        jMenuReportes.setEnabled(true);
        jMenuGastos.setEnabled(true);
        jMenuConfiguracion.setEnabled(true);
        vMenuPrincipal.jMenuItem12.setEnabled(true);
        vMenuPrincipal.jMenuItem30.setEnabled(true);
        vMenuPrincipal.jMenuItem29.setEnabled(true);
        vMenuPrincipal.jButtonVentas.setEnabled(true);
        vMenuPrincipal.jButtonCompras.setEnabled(true);
        vMenuPrincipal.jButtonGastos.setEnabled(true);
        vMenuPrincipal.jButtonProveedores.setEnabled(true);
        vMenuPrincipal.jButtonInsumos.setEnabled(true);
    }

    private void ComboTurnos() {
        turnos = ce.getCatalogos("turnos", "idturno", "descripcion");
        cbxTurnos.removeAllItems();
        String dato, nomtipo;
        int idtipo;
        Turnos turno;
        for (Object te : turnos) {
            //dividir lo obtenido desde la bd
            dato = (String) te;
            idtipo = Integer.parseInt(dato.substring(0, dato.indexOf("#")));
            nomtipo = dato.substring(dato.indexOf("#") + 1, dato.length());
            //completo el combo
            cbxTurnos.addItem(nomtipo);
            lista_turnos.put(nomtipo, idtipo);
            //cargo el list de tipo de gastos
//            turno = new Turnos(idtipo, nomtipo, 1);
//            turnos.add(turno);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnAbrirCaja = new javax.swing.JButton();
        jLabelFecha = new javax.swing.JLabel();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        jLabelUsuario = new javax.swing.JLabel();
        jLabelNomUsuario = new javax.swing.JLabel();
        jLabelCajaChica = new javax.swing.JLabel();
        jTextCajaChica = new javax.swing.JTextField();
        cbxTurnos = new javax.swing.JComboBox<>();
        jLabelTurno = new javax.swing.JLabel();
        jLabelMensaje = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Apertura de Caja");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
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

        btnCancelar.setBackground(new java.awt.Color(240, 87, 49));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAbrirCaja.setBackground(new java.awt.Color(252, 249, 57));
        btnAbrirCaja.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnAbrirCaja.setText("Abrir Caja");
        btnAbrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirCajaActionPerformed(evt);
            }
        });

        jLabelFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelFecha.setText("Fecha:");

        jDateFecha.setDateFormatString("dd/MM/yyyy HH:mm:ss");
        jDateFecha.setEnabled(false);
        jDateFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelUsuario.setText("Usuario:");

        jLabelNomUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNomUsuario.setText("Usuario");

        jLabelCajaChica.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelCajaChica.setText("Caja chica:");

        jTextCajaChica.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextCajaChica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCajaChicaKeyTyped(evt);
            }
        });

        cbxTurnos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        cbxTurnos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelTurno.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTurno.setText("Turno:");

        jLabelMensaje.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        jLabelMensaje.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMensaje.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAbrirCaja)
                                .addGap(60, 60, 60)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelCajaChica)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextCajaChica))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelFecha)
                                        .addComponent(jLabelTurno))
                                    .addGap(33, 33, 33)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jDateFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                        .addComponent(cbxTurnos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(jLabelUsuario)
                                    .addGap(22, 22, 22)
                                    .addComponent(jLabelNomUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabelMensaje)))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsuario)
                    .addComponent(jLabelNomUsuario))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFecha)
                    .addComponent(jDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTurno)
                    .addComponent(cbxTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextCajaChica, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCajaChica, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelMensaje)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAbrirCaja))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirCajaActionPerformed
        //obtener los valores de la pantalla
        if (btnAbrirCaja.getText().equals("Abrir Caja")) {
            Float cajachica = Float.parseFloat(jTextCajaChica.getText());
            Caja caja = new Caja();
            caja.setActivo(1);
            caja.setMonto(cajachica);
            caja.setEstado("ABIERTA");
            caja.setIdusuario(Session.getIdusuario());
            //int idturno =cbxTurnos.getSelectedIndex(); //corregir para que tome id de un list paralelo
            int idturno = lista_turnos.get(cbxTurnos.getSelectedItem());
            //cbxTurnos.getSelectedItem();
            //setteamos la variable global de caja abierta

            //abrir caja    
            if (cc.AbrirCaja(caja, idturno)) {
                //System.out.println("caja abierta "+Session.getIdcaja_abierta());
                JOptionPane.showMessageDialog(null, "Apertura de Caja Registrada");
                verificarCajaAbierta();
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                Float cajachica = Float.parseFloat(jTextCajaChica.getText());
                Caja caja = new Caja();
                //caja.setActivo(1);           
                //caja.setEstado("ABIERTA");
                caja.setIdusuario(Session.getIdusuario());
                caja.setMonto(cajachica);
                sql = new Sentencias_sql();
                int idcaja = sql.obtenerUltimoId("caja", "idcaja");
                caja.setIdCaja(idcaja);
                control_Cajas control_caja = new control_Cajas();
                if (control_caja.ModificarCaja(caja)) {
                    //int idturno =cbxTurnos.getSelectedIndex(); //corregir para que tome id de un list paralelo
                    int idturno = lista_turnos.get(cbxTurnos.getSelectedItem());
                    Caja_Turno cajaturno = new Caja_Turno();
                    cajaturno.setIdusuario(caja.getIdusuario());
                    cajaturno.setIdTurno(idturno);
                    cajaturno.setMonto(caja.getMonto());
                    int idcajaturno = sql.obtenerUltimoId("caja_turno", "idcajaturno");
                    cajaturno.setIdcajaturno(idcajaturno);
                    control_Caja_Turno control_cajaturno = new control_Caja_Turno();
                    if (control_cajaturno.ModificarCajaTurno(cajaturno)) {
                        Movimientos_Caja movcaja = new Movimientos_Caja();
                        movcaja.setIdusuario(cajaturno.getIdusuario());
                        movcaja.setMonto(caja.getMonto());
                        movcaja.setIdmovimientocaja(Integer.parseInt(id));
                        control_Movimientos_Caja control_movcaja = new control_Movimientos_Caja();
                        if (control_movcaja.ModificarMovimientoCaja(movcaja)) {
                            JOptionPane.showMessageDialog(null, "Modificado");
                            movimientoscajas = new vMovimientos_Caja();
                            vMenuPrincipal.jDesktopPane1.add(movimientoscajas);
                            movimientoscajas.setVisible(true);                            
                            this.dispose();
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnAbrirCajaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        if (btnCancelar.getText().equals("Cancelar")) {
            this.dispose();
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificación?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                movimientoscajas = new vMovimientos_Caja();
                vMenuPrincipal.jDesktopPane1.add(movimientoscajas);
                movimientoscajas.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (this.getTitle().equals("Modificar Apertura Caja")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificación?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                movimientoscajas = new vMovimientos_Caja();
                vMenuPrincipal.jDesktopPane1.add(movimientoscajas);
                movimientoscajas.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextCajaChica.getText().equals("0.0") && jTextCajaChica.isEnabled() == true) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextCajaChicaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCajaChicaKeyTyped
        char[] p = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', KeyEvent.VK_BACK_SPACE};
        int b = 0;
        for (int i = 0; i <= 11; i++) {
            if (p[i] == evt.getKeyChar()) {
                b = 1;
            }
        }
        if (b == 0) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextCajaChicaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAbrirCaja;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JComboBox<String> cbxTurnos;
    public static com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jLabelCajaChica;
    private javax.swing.JLabel jLabelFecha;
    public static javax.swing.JLabel jLabelMensaje;
    public static javax.swing.JLabel jLabelNomUsuario;
    private javax.swing.JLabel jLabelTurno;
    private javax.swing.JLabel jLabelUsuario;
    public static javax.swing.JTextField jTextCajaChica;
    // End of variables declaration//GEN-END:variables

}
