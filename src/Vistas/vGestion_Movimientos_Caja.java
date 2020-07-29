/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controlador.control_Caja_Turno;
import Controlador.control_Cajas;
import Controlador.control_Movimientos_Caja;
import Controlador.control_TiposGastos;
import Controlador.control_TiposMovimientos;

import Controlador.control_existencias;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import Modelo.TiposGastos;
import Modelo.TiposMovimientos;
import static Vistas.vMenuPrincipal.jDesktopPane1;
import java.awt.Color;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Movimientos_Caja extends javax.swing.JInternalFrame {

    Movimientos_Caja mc = new Movimientos_Caja();
    control_existencias ce = new control_existencias();
    Object[] tiposmovimientos, proveedor;
    Integer id = 0;
    control_TiposMovimientos control_tm = new control_TiposMovimientos();
    Date fechaseleccionada;
    List<TiposMovimientos> tipos = new ArrayList();
    TiposMovimientos tm;
    TiposGastos tg;
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    vMovimientos_Caja movcaja = new vMovimientos_Caja();
      int idturno = 0;
    int idcaja = 0;
     control_Cajas cc = new control_Cajas();
     control_Caja_Turno cct = new control_Caja_Turno();
    Object[][] datostabla;

    public vGestion_Movimientos_Caja() {
        initComponents();
        ComboTipoMovimiento();
        EliminarItemsVacios();
        jBotonModificar.setEnabled(false);
        verificarCajaAbierta();
    }

    public void verificarCajaAbierta() {
        //si no hay Caja abierta entonces NO podemos cerrar
        idcaja = Session.getIdcaja_abierta();
        idturno = Session.getIdturno_abierto();

        if (idcaja != 0) {
            jLabelMensaje.setVisible(false);
        } else {
            jLabelMensaje.setVisible(true);
            jLabelMensaje.setText("No hay Caja abierta");
            desabilitarCampos();
        }
        if (id == 0) {
            limpiarFormulario();
        }
    }

    public void limpiarFormulario() {
        Date fecha = new Date();
        jTextDescripcion.setText("");
        //jCBTipoMovimiento.setSelectedIndex(0);
        jDateFecha.setDate(fecha);
        jTextMonto.setText("");
        jTextDetalle.setText("");
        jBotonAgregar.setEnabled(true);
        jBotonModificar.setEnabled(false);
    }

    public void desabilitarCampos() {
        jTextDescripcion.setEnabled(false);
        jCBTipoMovimiento.setEnabled(false);
        jDateFecha.setEnabled(false);
        jTextMonto.setEnabled(false);
        jTextDetalle.setEnabled(false);
        jBotonAgregar.setEnabled(false);
        jBotonModificar.setEnabled(false);
    }

    public String getFecha() {
        int anio = jDateFecha.getCalendar().get(Calendar.YEAR);
        int mes = jDateFecha.getCalendar().get(Calendar.MONTH) + 1;
        int dia = jDateFecha.getCalendar().get(Calendar.DAY_OF_MONTH);
        return anio + "-" + mes + "-" + dia;
    }

    public void volverListado() {
//        vMovimientos_Caja vmc = new vMovimientos_Caja();
//        vMenuPrincipal.jDesktopPane1.add(vmc);
//        vmc.show();
        String[] columnas = {"idmovimientocaja", "Nro.Movimiento", "Concepto", "TipoMovimiento", "Turno", "FechaMovimiento", "Ingreso", "Egreso", "detalle", "idmovimiento"};
        datostabla = control_mc.MostrarDatos(vMovimientos_Caja.jFechaDesde.getDate(), vMovimientos_Caja.jFechaHasta.getDate());
        DefaultTableModel datos = new DefaultTableModel(datostabla, columnas);
        vMovimientos_Caja.jTabla_MovCaja.setModel(datos);
        vMovimientos_Caja.jTabla_MovCaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ocultar_columnas();
        //System.out.println("cant"+datos.getRowCount());
//        if(datos.getRowCount()>0){
//            CalcularTotales(datos);
//        }
        this.dispose();
    }

    public void ocultar_columnas() {
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(0).setMaxWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(0).setMinWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(0).setPreferredWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(1).setPreferredWidth(100);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(2).setPreferredWidth(150);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(3).setPreferredWidth(200);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(4).setPreferredWidth(70);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(6).setPreferredWidth(90);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(7).setPreferredWidth(90);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(8).setMaxWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(8).setMinWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(8).setPreferredWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(9).setMaxWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(9).setMinWidth(0);
        vMovimientos_Caja.jTabla_MovCaja.getColumnModel().getColumn(9).setPreferredWidth(0);
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jCBTipoMovimiento.getItemCount(); i++) {
            if (jCBTipoMovimiento.getItemAt(i) == null) {
                jCBTipoMovimiento.remove(i);
            }
        }
    }

    public void ComboTipoMovimiento() {
        tiposmovimientos = control_mc.getTiposMovimientos("tiposmovimientos", "idtipomovimiento", "descripcion", "EGRESO");
        jCBTipoMovimiento.removeAllItems();
        String dato, nomtipo, tipo;
        int idtipo;
        for (Object te : tiposmovimientos) {
            //dividir lo obtenido desde la bd
            dato = (String) te;
            idtipo = Integer.parseInt(dato.substring(0, dato.indexOf("#")));
            nomtipo = dato.substring(dato.indexOf("#") + 1, dato.length());

            //completo el combo
            jCBTipoMovimiento.addItem(nomtipo);
            //cargo el list de tipo de gastos
            tm = new TiposMovimientos(idtipo, nomtipo, "", 1);
            tipos.add(tm);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEtiqDesc_Insumos = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextDetalle = new javax.swing.JTextArea();
        jEtiqTipo_Insumos = new javax.swing.JLabel();
        jCBTipoMovimiento = new javax.swing.JComboBox<>();
        jBotonAgregar = new javax.swing.JButton();
        jBotonModificar = new javax.swing.JButton();
        jEtiqStock_Insumos1 = new javax.swing.JLabel();
        jTextMonto = new javax.swing.JTextField();
        jEtiqStock_Insumos2 = new javax.swing.JLabel();
        jEtiqStock_Insumos3 = new javax.swing.JLabel();
        jTextDescripcion = new javax.swing.JTextField();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        jLabelMensaje = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Movimiento de Caja");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(547, 357));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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

        jEtiqDesc_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqDesc_Insumos.setText("Detalle del Movimiento:");
        getContentPane().add(jEtiqDesc_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 148, -1, 30));

        jTextDetalle.setColumns(20);
        jTextDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextDetalle.setRows(5);
        jTextDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDetalleKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jTextDetalle);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 230, 120));

        jEtiqTipo_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqTipo_Insumos.setText("(*) Tipo de Movimiento:");
        getContentPane().add(jEtiqTipo_Insumos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, 20));

        jCBTipoMovimiento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCBTipoMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo Movimiento" }));
        jCBTipoMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBTipoMovimientoActionPerformed(evt);
            }
        });
        getContentPane().add(jCBTipoMovimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 30));

        jBotonAgregar.setBackground(new java.awt.Color(252, 240, 0));
        jBotonAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgregar.setText("Agregar");
        jBotonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jBotonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 90, -1));

        jBotonModificar.setBackground(new java.awt.Color(252, 240, 0));
        jBotonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModificar.setText("Modificar");
        jBotonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jBotonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, 100, -1));

        jEtiqStock_Insumos1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos1.setText("(*) Monto:");
        getContentPane().add(jEtiqStock_Insumos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jTextMonto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextMontoKeyTyped(evt);
            }
        });
        getContentPane().add(jTextMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 120, 30));

        jEtiqStock_Insumos2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos2.setText("(*) Fecha:");
        getContentPane().add(jEtiqStock_Insumos2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jEtiqStock_Insumos3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos3.setText("(*) Concepto:");
        getContentPane().add(jEtiqStock_Insumos3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jTextDescripcion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDescripcionKeyTyped(evt);
            }
        });
        getContentPane().add(jTextDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 450, 30));

        jDateFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jDateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 170, 30));

        jLabelMensaje.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelMensaje.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMensaje.setText("jLabel2");
        getContentPane().add(jLabelMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgregarActionPerformed
        if (jBotonAgregar.getText().equals("Agregar")) {
            if (!jTextDescripcion.getText().trim().equals("")
                    && !jDateFecha.getDateFormatString().trim().equals("")
                    && !jTextMonto.getText().trim().equals("")
                    && !jTextMonto.getText().trim().equals("0")) {
                if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    //String fecha = getFecha();
                    //System.out.println(jDateFecha.getDateEditor().getDate());
                    String fecha = ((JTextField)jDateFecha.getDateEditor().getUiComponent()).getText();
                    mc.setFecha_movimiento(fecha);
                    //mc.setFecha_movimiento(Timestamp.valueOf(jDateFecha.getDateFormatString()));
                    mc.setDescripcion(jTextDescripcion.getText());
                    // obtenemos el tipo de egreso seleccionado
                    TiposMovimientos tm = control_tm.getTipoMovimientoByDescripcion(tipos, jCBTipoMovimiento.getSelectedItem().toString());
                    mc.setIdtipomovimiento(tm.getIdtipomovimiento());
                    mc.setMonto(Float.parseFloat(jTextMonto.getText()));
                    mc.setDetalle(jTextDetalle.getText());
                    mc.setIdmovimiento(0);
                    //System.out.println(mc);
                    if (control_mc.AgregarMovimientoCaja(mc)) {
                        //insetar movimiento de caja                               
                        JOptionPane.showMessageDialog(null, "Nuevo Movimiento de Caja agregado");
                        volverListado();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ingreso nuevo Movimiento de Caja");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe completar los campos obligatorios");
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vMenuPrincipal.jDesktopPane1.add(movcaja);
                movcaja.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }

    }//GEN-LAST:event_jBotonAgregarActionPerformed

    private void jBotonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModificarActionPerformed
        //String fecha = getFecha();
//        mc.setFecha(fecha);
        String fecha = ((JTextField)jDateFecha.getDateEditor().getUiComponent()).getText();
        mc.setFecha_movimiento(fecha);
        mc.setDescripcion(jTextDescripcion.getText());
        // obtenemos el tipo de egreso seleccionado
        TiposMovimientos tm = control_tm.getTipoMovimientoByDescripcion(tipos, jCBTipoMovimiento.getSelectedItem().toString());
        mc.setIdtipomovimiento(tm.getIdtipomovimiento());
        mc.setMonto(Float.parseFloat(jTextMonto.getText()));
        mc.setDetalle(jTextDetalle.getText());
        mc.setIdmovimiento(0);
        mc.setIdmovimientocaja(id);
        if (!jTextDescripcion.getText().trim().equals("")
                && !jDateFecha.getDateFormatString().trim().equals("")
                && !jTextMonto.getText().trim().equals("")
                && !jTextMonto.getText().trim().equals("0")) {
            if (jDateFecha.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (control_mc.EditarMovimientosCaja(mc)) {
                    JOptionPane.showMessageDialog(null, "Modificación Completa");
                    vMenuPrincipal.jDesktopPane1.add(movcaja);
                    movcaja.setVisible(true);
                    dispose();
                    //volverListado();
                } else {
                    JOptionPane.showMessageDialog(null, "No se ingreso nuevo Movimiento de Caja");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar el campo obligatorio");
        }
    }//GEN-LAST:event_jBotonModificarActionPerformed

    private void jCBTipoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBTipoMovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBTipoMovimientoActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jBotonAgregar.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vMenuPrincipal.jDesktopPane1.add(movcaja);
                movcaja.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jBotonAgregar.getText().equals("Cancelar") && !jTextDescripcion.getText().isEmpty() || !jCBTipoMovimiento.getSelectedItem().equals("Seleccionar Tipo Moviemiento") || !((JTextField) jDateFecha.getDateEditor().getUiComponent()).getText().isEmpty() || !jTextMonto.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vMenuPrincipal.jDesktopPane1.add(movcaja);
                movcaja.setVisible(true);
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed

    private void jTextDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDescripcionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextDescripcionKeyTyped

    private void jTextDetalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDetalleKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextDetalleKeyTyped

    private void jTextMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextMontoKeyTyped
       char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextMontoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonAgregar;
    public static javax.swing.JButton jBotonModificar;
    public static javax.swing.JComboBox<String> jCBTipoMovimiento;
    public com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jEtiqDesc_Insumos;
    private javax.swing.JLabel jEtiqStock_Insumos1;
    private javax.swing.JLabel jEtiqStock_Insumos2;
    private javax.swing.JLabel jEtiqStock_Insumos3;
    private javax.swing.JLabel jEtiqTipo_Insumos;
    private javax.swing.JLabel jLabelMensaje;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextField jTextDescripcion;
    public static javax.swing.JTextArea jTextDetalle;
    public static javax.swing.JTextField jTextMonto;
    // End of variables declaration//GEN-END:variables
}
