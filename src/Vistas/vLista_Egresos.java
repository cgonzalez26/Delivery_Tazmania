package Vistas;

import Controlador.control_Egresos;
import Controlador.control_Movimientos_Caja;
import Modelo.Egresos;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import java.awt.Color;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Egresos extends javax.swing.JInternalFrame {

    String idegreso, fecha;
    String desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText(),
            hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
    Timestamp fechaseleccionada;
    Object[][] datostabla;
    vGestion_Egresos ge = null;
    control_Egresos egreso = new control_Egresos();
    Movimientos_Caja mc = new Movimientos_Caja();
    Egresos ins = new Egresos();
    DefaultTableModel datos;
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();

    public vLista_Egresos() {
        initComponents();
        IniciarFechas();
        Mostrar();

        jTabla_Egresos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Modificar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    //if (i == 0) {
                    if (VerificarCajaAbierta() == false) {
                        int fila = jTabla_Egresos.rowAtPoint(e.getPoint());
                        String nroMovimiento = (jTabla_Egresos.getValueAt(fila, 1).toString());
                        int idmovimientocaja = control_mc.getIdMovimientocaja(nroMovimiento);
                        String est = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                        if (est.equals("CERRADA")) {
                            JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                        } else {
                            fecha = (jTabla_Egresos.getValueAt(fila, 4).toString());
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            try {
                                fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                            } catch (ParseException ex) {
                                Logger.getLogger(vListas_Ventas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ge = new vGestion_Egresos();
                            vMenuPrincipal.jDesktopPane1.add(ge);
                            ge.setVisible(true);
                            vGestion_Egresos.jBotonAgregar_Egresos.setEnabled(false);
                            idegreso = (jTabla_Egresos.getValueAt(fila, 0).toString());
                            ge.jTextDescripcion_Egresos.setText(jTabla_Egresos.getValueAt(fila, 2).toString());
                            ge.jCBTipoEgreso_Egresos.setSelectedItem(jTabla_Egresos.getValueAt(fila, 3).toString());
                            ge.jDateFecha_Egresos.setDate(fechaseleccionada);
                            ge.jTextMonto_Egresos.setText(jTabla_Egresos.getValueAt(fila, 5).toString());
                            ge.jTextDetalle_Egresos.setText(jTabla_Egresos.getValueAt(fila, 7).toString());
                            ge.id = Integer.parseInt(idegreso);
                            dispose();
                            LimpiarSeleccion();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede Modificar, No hay CAJA ABIERTA.");
                    }
                }
            }
        });
    }

    public void IniciarFechas() {
        Date date = new Date();
        jDateFechaDesde.setDate(date);
        jDateFechaHasta.setDate(date);
    }

    public boolean VerificarCajaAbierta() {
        int idcaja = Session.getIdcaja_abierta();
        boolean est = false;
        if (idcaja == 0) {
            est = true;
        }
        return est;
    }

    public void LimpiarSeleccion() {
        jTabla_Egresos.clearSelection();
        jTabla_Egresos.getSelectionModel().clearSelection();
    }

    public void Mostrar() {
        String[] columnas = {"IDEGRESO", "NRO EGRESO", "CONCEPTO", "TIPO EGRESO", "FECHA", "MONTO", "IDTIPOEGRESO", "DETALLE"};
        datostabla = egreso.MostrarDatos(desde, hasta);
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Egresos.setModel(datos);
        ocultar_columnas();
        EliminarFilasVacias();
    }

    public void MostrarGastoBuscado() {
        datostabla = egreso.buscarEgreso(desde, hasta);
        if (datostabla.length != 0) {
            String[] columnas = {"IDEGRESO", "NRO EGRESO", "CONCEPTO", "TIPO EGRESO", "FECHA EGRESO", "MONTO", "IDTIPOEGRESO", "DETALLE"};
            datos = new DefaultTableModel(datostabla, columnas);
            jTabla_Egresos.setModel(datos);
            ocultar_columnas();
            EliminarFilasVacias();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron datos");
        }
    }

    public void EliminarFilasVacias() {
        if (jTabla_Egresos.getRowCount() != 0) {
            for (int columna = 0; columna < jTabla_Egresos.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTabla_Egresos.getRowCount(); fila++) {
                    if (jTabla_Egresos.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    public void ocultar_columnas() {
        jTabla_Egresos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(0).setMinWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(6).setMaxWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(6).setMinWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(6).setPreferredWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(7).setMaxWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(7).setMinWidth(0);
        jTabla_Egresos.getColumnModel().getColumn(7).setPreferredWidth(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_Egresos = jTabla_Egresos= new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelGasto = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jDateFechaDesde = new com.toedter.calendar.JDateChooser();
        jDateFechaHasta = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Gastos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTabla_Egresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTabla_Egresos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTabla_Egresos);

        btnNuevo.setBackground(new java.awt.Color(252, 249, 57));
        btnNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(252, 249, 57));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(252, 249, 57));
        btnEditar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEditar.setText("Modificar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelGasto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelGasto.setText("Desde");

        btnBuscar.setBackground(new java.awt.Color(252, 249, 57));
        btnBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Hasta");

        jDateFechaDesde.setDateFormatString("dd-MM-yyyy");

        jDateFechaHasta.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGasto)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 218, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(207, 207, 207))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        ge = new vGestion_Egresos();
        vMenuPrincipal.jDesktopPane1.add(ge);
        ge.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int seleccionado = jTabla_Egresos.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                String nroMovimiento = (jTabla_Egresos.getValueAt(seleccionado, 1).toString());
                int idmovimientocaja = control_mc.getIdMovimientocaja(nroMovimiento);
                mc.setIdmovimientocaja(idmovimientocaja);
                if (control_mc.EliminarMovimientosCaja(mc)) {
                    ins.setIdegreso(Integer.parseInt(jTabla_Egresos.getValueAt(seleccionado, 0).toString()));
                    if (egreso.DesactivarEgresos(ins)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        //datos.removeRow(jTabla_Egresos.getRowCount() - 1);
                        Mostrar();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se elimino el Gasto");
                    }
                }
            }
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int fila = jTabla_Egresos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            if (VerificarCajaAbierta() == false) {
                int idmovimiento = Integer.parseInt(jTabla_Egresos.getValueAt(fila, 0).toString());
                String nroMovimiento = (jTabla_Egresos.getValueAt(fila, 1).toString());
                int idmovimientocaja = control_mc.getIdMovimientocaja(nroMovimiento);
                if (idmovimientocaja == 0) {
                    JOptionPane.showMessageDialog(null, "El Egreso no tiene un Movimiento de Caja asociado!");
                } else {
                    //validar si el Movimiento pertenece a una Caja Abierta
                    String est = control_mc.getEstadoCajaByMovimiento(idmovimientocaja);
                    //System.out.println("estado = "+est);
                    if (est.equals("CERRADA")) {
                        JOptionPane.showMessageDialog(null, "La Caja del Movimiento está CERRADA!");
                    } else {

                        //String subfecha=fecha.substring(0, 10);
                        //DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        fecha = (String) (jTabla_Egresos.getValueAt(fila, 4));
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(vLista_Egresos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ge = new vGestion_Egresos();
                        vMenuPrincipal.jDesktopPane1.add(ge);
                        ge.setVisible(true);
                        vGestion_Egresos.jBotonAgregar_Egresos.setEnabled(false);
                        ge.jTextDescripcion_Egresos.setText(jTabla_Egresos.getValueAt(fila, 2).toString());
                        ge.jCBTipoEgreso_Egresos.setSelectedItem(jTabla_Egresos.getValueAt(fila, 3).toString());
                        //System.out.println(jTabla_Egresos.getValueAt(fila, 4).toString());
                        ge.jDateFecha_Egresos.setDate(fechaseleccionada);
                        ge.jTextMonto_Egresos.setText(jTabla_Egresos.getValueAt(fila, 5).toString());
                        ge.jTextDetalle_Egresos.setText(jTabla_Egresos.getValueAt(fila, 7).toString());
                        ge.id = idmovimiento;
                        ge.NroMovimiento = nroMovimiento;
                        dispose();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede Modificar, No hay CAJA ABIERTA.");
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (jDateFechaDesde.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateFechaHasta.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    MostrarGastoBuscado();
                } else if (desde.isEmpty() || hasta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar la fecha que falta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        //Mostrar();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    public static com.toedter.calendar.JDateChooser jDateFechaDesde;
    public static com.toedter.calendar.JDateChooser jDateFechaHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelGasto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Egresos;
    // End of variables declaration//GEN-END:variables
}
