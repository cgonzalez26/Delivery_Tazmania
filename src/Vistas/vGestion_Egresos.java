package Vistas;

import Controlador.control_Caja_Turno;
import Controlador.control_Cajas;
import Controlador.control_Egresos;
import Controlador.control_Movimientos_Caja;
import Controlador.control_TiposGastos;

import Controlador.control_existencias;
import Modelo.Egresos;
import Modelo.Session;
import Modelo.TiposGastos;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Egresos extends javax.swing.JInternalFrame {

    Egresos e = new Egresos();
    control_existencias ce = new control_existencias();
    Object[] tiposegresos, proveedor;
    Integer id = 0;
    String NroMovimiento = "";
    control_Egresos egreso = new control_Egresos();
    control_TiposGastos control_tg = new control_TiposGastos();
    Date fechaseleccionada;
    List<TiposGastos> tipos = new ArrayList();
    TiposGastos tg;
    vLista_Egresos listaeg = null;
    vMovimientos_Caja movcaja = null;
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    int idturno = 0;
    int idcaja = 0;
    control_Cajas cc = new control_Cajas();
    control_Caja_Turno cct = new control_Caja_Turno();
    public int idmovimientocaja = 0;
    Object[][] datostabla;

    public vGestion_Egresos() {
        initComponents();
        ComboTipoEgreso();
        //EliminarEgresosIngresos();
        EliminarItemsVacios();
        verificarCajaAbierta();
    }

    public void limpiarFormulario() {
        Date fecha = new Date();
        jTextDescripcion_Egresos.setText("");
        jCBTipoEgreso_Egresos.setSelectedIndex(0);
        jDateFecha_Egresos.setDate(fecha);
        jTextMonto_Egresos.setText("");
        jTextDetalle_Egresos.setText("");
    }

    public void verificarCajaAbierta() {
        //si no hay Caja abierta entonces NO podemos cerrar
        idcaja = Session.getIdcaja_abierta();
        idturno = Session.getIdturno_abierto();

        if (idcaja != 0) {
            jLabelMensaje.setVisible(false);
            //comprobamos que operacion se esta por realizar: Agregar o MOdificar               
            if (id == 0) {
                jBotonAgregar_Egresos.setEnabled(true);
                jBotonModif_Egresos.setEnabled(false);
                limpiarFormulario();
            } else {
                jBotonAgregar_Egresos.setEnabled(false);
                jBotonModif_Egresos.setEnabled(true);
            }
        } else {
            jLabelMensaje.setVisible(true);
            jLabelMensaje.setText("No Hay CAJA ABIERTA.");
            desabilitarCampos();
        }
    }

    public void desabilitarCampos() {
        jTextDescripcion_Egresos.setEnabled(false);
        jCBTipoEgreso_Egresos.setEnabled(false);
        jDateFecha_Egresos.setEnabled(false);
        jTextMonto_Egresos.setEnabled(false);
        jTextDetalle_Egresos.setEnabled(false);
        jBotonAgregar_Egresos.setEnabled(false);
        jBotonModif_Egresos.setEnabled(false);
    }

    public String getFecha() {
        int anio = jDateFecha_Egresos.getCalendar().get(Calendar.YEAR);
        int mes = jDateFecha_Egresos.getCalendar().get(Calendar.MONTH) + 1;
        int dia = jDateFecha_Egresos.getCalendar().get(Calendar.DAY_OF_MONTH);
        return anio + "-" + mes + "-" + dia;
    }

    public void ocultar_columnas() {
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(0).setMaxWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(0).setMinWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(0).setPreferredWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(6).setMaxWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(6).setMinWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(6).setPreferredWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(7).setMaxWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(7).setMinWidth(0);
        vLista_Egresos.jTabla_Egresos.getColumnModel().getColumn(7).setPreferredWidth(0);
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jCBTipoEgreso_Egresos.getItemCount(); i++) {
            if (jCBTipoEgreso_Egresos.getItemAt(i) == null) {
                jCBTipoEgreso_Egresos.remove(i);
            }
        }
    }

    public void EliminarEgresosIngresos() {
        /*int filas = jCBTipoEgreso_Egresos.getItemCount();
        filas--;*/
        for (int fila = 0; fila < jCBTipoEgreso_Egresos.getItemCount(); fila++) {
            if (jCBTipoEgreso_Egresos.getItemAt(fila).equals("CIERRE DE CAJA DIARIO") || jCBTipoEgreso_Egresos.getItemAt(fila).equals("COMPRA DE INSUMOS") || jCBTipoEgreso_Egresos.getItemAt(fila).equals("PAGO A EMPLEADOS")) {
                jCBTipoEgreso_Egresos.remove(fila);
            }
        }
    }

    public void ComboTipoEgreso() {
        tiposegresos = control_mc.getTiposMovimientos("tiposmovimientos", "idtipomovimiento", "descripcion", "EGRESO");
        jCBTipoEgreso_Egresos.removeAllItems();
        String dato, nomtipo;
        int idtipo;
        nomtipo = "Seleccionar una opción";
        jCBTipoEgreso_Egresos.addItem(nomtipo);
        for (Object te : tiposegresos) {
            //dividir lo obtenido desde la bd
            dato = (String) te;
            idtipo = Integer.parseInt(dato.substring(0, dato.indexOf("#")));
            nomtipo = dato.substring(dato.indexOf("#") + 1, dato.length());
            //completo el combo
            jCBTipoEgreso_Egresos.addItem(nomtipo);
            //cargo el list de tipo de gastos
            tg = new TiposGastos(idtipo, nomtipo, 1);
            tipos.add(tg);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEtiqDesc_Insumos = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextDetalle_Egresos = new javax.swing.JTextArea();
        jEtiqTipo_Insumos = new javax.swing.JLabel();
        jCBTipoEgreso_Egresos = new javax.swing.JComboBox<>();
        jBotonAgregar_Egresos = new javax.swing.JButton();
        jBotonModif_Egresos = new javax.swing.JButton();
        jEtiqStock_Insumos1 = new javax.swing.JLabel();
        jTextMonto_Egresos = new javax.swing.JTextField();
        jEtiqStock_Insumos2 = new javax.swing.JLabel();
        jEtiqStock_Insumos3 = new javax.swing.JLabel();
        jTextDescripcion_Egresos = new javax.swing.JTextField();
        jDateFecha_Egresos = new com.toedter.calendar.JDateChooser();
        jLabelMensaje = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Gastos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(526, 391));
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

        jEtiqDesc_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqDesc_Insumos.setText("Detalle del Gasto:");

        jTextDetalle_Egresos.setColumns(20);
        jTextDetalle_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextDetalle_Egresos.setRows(5);
        jScrollPane3.setViewportView(jTextDetalle_Egresos);

        jEtiqTipo_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqTipo_Insumos.setText("(*) Tipo de Gasto:");

        jCBTipoEgreso_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jCBTipoEgreso_Egresos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo Egreso" }));
        jCBTipoEgreso_Egresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBTipoEgreso_EgresosActionPerformed(evt);
            }
        });

        jBotonAgregar_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonAgregar_Egresos.setText("Agregar");
        jBotonAgregar_Egresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonAgregar_EgresosActionPerformed(evt);
            }
        });

        jBotonModif_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jBotonModif_Egresos.setText("Modificar");
        jBotonModif_Egresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModif_EgresosActionPerformed(evt);
            }
        });

        jEtiqStock_Insumos1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos1.setText("(*) Monto:");

        jTextMonto_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jEtiqStock_Insumos2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos2.setText("(*) Fecha:");

        jEtiqStock_Insumos3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jEtiqStock_Insumos3.setText("(*) Concepto:");

        jTextDescripcion_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateFecha_Egresos.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateFecha_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelMensaje.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        jLabelMensaje.setForeground(new java.awt.Color(255, 0, 0));
        jLabelMensaje.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jEtiqStock_Insumos2)
                            .addComponent(jDateFecha_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jEtiqStock_Insumos1)
                            .addComponent(jTextMonto_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jEtiqStock_Insumos3)
                            .addComponent(jTextDescripcion_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jEtiqTipo_Insumos)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCBTipoEgreso_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109)
                                .addComponent(jEtiqDesc_Insumos))
                            .addComponent(jLabelMensaje))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jBotonAgregar_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBotonModif_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jEtiqStock_Insumos3)
                .addGap(12, 12, 12)
                .addComponent(jTextDescripcion_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jEtiqTipo_Insumos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBTipoEgreso_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jEtiqDesc_Insumos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jEtiqStock_Insumos2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jDateFecha_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jEtiqStock_Insumos1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jTextMonto_Egresos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelMensaje)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBotonAgregar_Egresos)
                    .addComponent(jBotonModif_Egresos))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonAgregar_EgresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonAgregar_EgresosActionPerformed
        if (jBotonAgregar_Egresos.getText().equals("Agregar")) {
            if (!jTextDescripcion_Egresos.getText().trim().equals("")
                    && !jDateFecha_Egresos.getDateFormatString().trim().equals("")
                    && !jTextMonto_Egresos.getText().trim().equals("")
                    && !jTextMonto_Egresos.getText().trim().equals("0")
                    && !jCBTipoEgreso_Egresos.getSelectedItem().toString().equals("Seleccionar una opción")) {
                if (jDateFecha_Egresos.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    //String fecha = getFecha();
                    //Timestamp fecha = new Timestamp(jDateFecha_Egresos.getDateEditor().getDate().getTime());
                    //e.setFecha(fecha);
                    e.setDescripcion(jTextDescripcion_Egresos.getText());
                    // obtenemos el tipo de egreso seleccionado
                    TiposGastos tg = control_tg.getTipoGastoByDescripcion(tipos, jCBTipoEgreso_Egresos.getSelectedItem().toString());
                    e.setIdtipoegreso(tg.getIdtipogasto());
                    e.setMonto(Float.parseFloat(jTextMonto_Egresos.getText()));
                    e.setDetalle(jTextDetalle_Egresos.getText());
                    if (egreso.AgregarEgresos(e)) {
                        //insetar movimiento de caja                               
                        JOptionPane.showMessageDialog(null, "Nuevo Egreso agregado");
                        if (!vGestion_Egresos.this.getTitle().equals("Administrar Movimientos Cajas")) {
                            listaeg = new vLista_Egresos();
                            vMenuPrincipal.jDesktopPane1.add(listaeg);
                            listaeg.setVisible(true);
                            this.dispose();
                        } else {
                            movcaja = new vMovimientos_Caja();
                            vMenuPrincipal.jDesktopPane1.add(movcaja);
                            movcaja.setVisible(true);
                            this.dispose();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No se ingreso nuevo Egreso");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (!vGestion_Egresos.this.getTitle().equals("Administrar Movimientos Cajas")) {
                    listaeg = new vLista_Egresos();
                    vMenuPrincipal.jDesktopPane1.add(listaeg);
                    listaeg.setVisible(true);
                    this.dispose();
                } else {
                    movcaja = new vMovimientos_Caja();
                    vMenuPrincipal.jDesktopPane1.add(movcaja);
                    movcaja.setVisible(true);
                    this.dispose();
                }
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }

    }//GEN-LAST:event_jBotonAgregar_EgresosActionPerformed

    private void jBotonModif_EgresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModif_EgresosActionPerformed
        //String fecha = getFecha();
        //Timestamp fecha = new Timestamp(jDateFecha_Egresos.getDateEditor().getDate().getTime());
        //e.setFecha(fecha);

        e.setDescripcion(jTextDescripcion_Egresos.getText());
        // obtenemos el tipo de egreso seleccionado
        TiposGastos tg = control_tg.getTipoGastoByDescripcion(tipos, jCBTipoEgreso_Egresos.getSelectedItem().toString());
        e.setIdtipoegreso(tg.getIdtipogasto());
        e.setMonto(Float.parseFloat(jTextMonto_Egresos.getText()));
        e.setDetalle(jTextDetalle_Egresos.getText());
        e.setIdegreso(id);
        e.setIdmovimientocaja(control_mc.getIdMovimientocaja(NroMovimiento));

        if (!jTextDescripcion_Egresos.getText().trim().equals("")
                && !jDateFecha_Egresos.getDateFormatString().trim().equals("")
                && !jTextMonto_Egresos.getText().trim().equals("")
                && !jTextMonto_Egresos.getText().trim().equals("0")) {
            if (jDateFecha_Egresos.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    if (egreso.ModificarEgresos(e)) {
                        JOptionPane.showMessageDialog(null, "Modificación Completa");
                        if (!vGestion_Egresos.this.getTitle().equals("Administrar Movimientos Cajas")) {
                            listaeg = new vLista_Egresos();
                            vMenuPrincipal.jDesktopPane1.add(listaeg);
                            listaeg.setVisible(true);
                            this.dispose();
                        } else {
                            movcaja = new vMovimientos_Caja();
                            vMenuPrincipal.jDesktopPane1.add(movcaja);
                            movcaja.setVisible(true);
                            this.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ingreso nuevo Egreso");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
            }
        }
    }//GEN-LAST:event_jBotonModif_EgresosActionPerformed

    private void jCBTipoEgreso_EgresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBTipoEgreso_EgresosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBTipoEgreso_EgresosActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jBotonAgregar_Egresos.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (!vGestion_Egresos.this.getTitle().equals("Administrar Movimientos Cajas")) {
                    listaeg = new vLista_Egresos();
                    vMenuPrincipal.jDesktopPane1.add(listaeg);
                    listaeg.setVisible(true);
                    this.dispose();
                } else {
                    movcaja = new vMovimientos_Caja();
                    vMenuPrincipal.jDesktopPane1.add(movcaja);
                    movcaja.setVisible(true);
                    this.dispose();
                }
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextDescripcion_Egresos.getText().isEmpty() || !jCBTipoEgreso_Egresos.getSelectedItem().equals("Seleccionar una opción") || !jTextMonto_Egresos.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (vGestion_Egresos.this.getTitle().equals("Administrar Movimientos Cajas")) {
                    movcaja = new vMovimientos_Caja();
                    vMenuPrincipal.jDesktopPane1.add(movcaja);
                    movcaja.setVisible(true);
                    this.dispose();
                } else {
                    dispose();
                }
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            if (vGestion_Egresos.this.getTitle().equals("Administrar Movimientos Cajas")) {
                movcaja = new vMovimientos_Caja();
                vMenuPrincipal.jDesktopPane1.add(movcaja);
                movcaja.setVisible(true);
                this.dispose();
            } else {
                dispose();
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jBotonAgregar_Egresos;
    public static javax.swing.JButton jBotonModif_Egresos;
    public static javax.swing.JComboBox<String> jCBTipoEgreso_Egresos;
    public static com.toedter.calendar.JDateChooser jDateFecha_Egresos;
    private javax.swing.JLabel jEtiqDesc_Insumos;
    private javax.swing.JLabel jEtiqStock_Insumos1;
    private javax.swing.JLabel jEtiqStock_Insumos2;
    private javax.swing.JLabel jEtiqStock_Insumos3;
    public static javax.swing.JLabel jEtiqTipo_Insumos;
    private javax.swing.JLabel jLabelMensaje;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextField jTextDescripcion_Egresos;
    public static javax.swing.JTextArea jTextDetalle_Egresos;
    public static javax.swing.JTextField jTextMonto_Egresos;
    // End of variables declaration//GEN-END:variables
}
