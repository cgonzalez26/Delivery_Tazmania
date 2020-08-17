package Vistas;

import Controlador.control_Egresos;
import Controlador.control_Movimientos_Caja;
import Modelo.Egresos;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Egresos extends javax.swing.JInternalFrame {

    String idegreso, fecha;
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
                            vGestion_Egresos.jBotonModif_Egresos.setEnabled(true);
                            vGestion_Egresos.jBotonAgregar_Egresos.setText("Cancelar");
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
        datostabla = egreso.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Egresos.setModel(datos);
        ocultar_columnas();
        EliminarFilasVacias();
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
        txtbuscarGasto = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

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

        btnEliminar.setBackground(new java.awt.Color(237, 124, 61));
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
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelGasto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelGasto.setText("Gasto:");

        txtbuscarGasto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        txtbuscarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarGastoActionPerformed(evt);
            }
        });
        txtbuscarGasto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarGastoKeyReleased(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(252, 249, 57));
        btnBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(txtbuscarGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscarGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabelGasto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 337, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(241, 241, 241))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(205, 205, 205))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
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
                        ge.jBotonModif_Egresos.setEnabled(true);
                        ge.jBotonAgregar_Egresos.setText("Cancelar");
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
        if (!txtbuscarGasto.getText().isEmpty()) {
            //LimpiarSeleccion();           
            datostabla = egreso.buscarEgreso(txtbuscarGasto.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDEGRESO", "NRO EGRESO", "CONCEPTO", "TIPO EGRESO", "FECHA EGRESO", "MONTO", "IDTIPOEGRESO", "DETALLE"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_Egresos.setModel(datos);
                ocultar_columnas();
                EliminarFilasVacias();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtbuscarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarGastoActionPerformed

    }//GEN-LAST:event_txtbuscarGastoActionPerformed

    private void txtbuscarGastoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarGastoKeyReleased

    }//GEN-LAST:event_txtbuscarGastoKeyReleased

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        Mostrar();
        txtbuscarGasto.setText("");
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabelGasto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Egresos;
    private javax.swing.JTextField txtbuscarGasto;
    // End of variables declaration//GEN-END:variables
}
