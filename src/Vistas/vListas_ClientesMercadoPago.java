package Vistas;

import Controlador.control_MercadoPago;
import Controlador.control_Reportes;
import Modelo.MercadoPago;
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
public final class vListas_ClientesMercadoPago extends javax.swing.JInternalFrame {

    control_MercadoPago control_mp = new control_MercadoPago();
    control_Reportes reporte = new control_Reportes();
    MercadoPago mp = new MercadoPago();
    vGestion_ImprimirTickets tickets;
    Object[][] datos;
    DefaultTableModel tabla;
    String fecha, id;
    Timestamp fechaseleccionada;

    public vListas_ClientesMercadoPago() {
        initComponents();
        MostrarDatos();
        jTableClientesMercadoPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int seleccionado = jTableClientesMercadoPago.getSelectedRow();
                    if (seleccionado == -1) {
                        JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
                    } else {
                        fecha = jTableClientesMercadoPago.getValueAt(seleccionado, 11).toString();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(vListas_Ventas.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (tickets == null || tickets.isClosed()) {
                            tickets = new vGestion_ImprimirTickets();
                            vMenuPrincipal.jDesktopPane1.add(tickets);
                            tickets.setVisible(true);
                            tickets.toFront();
                        }

                        vGestion_ImprimirTickets.jButtonImprimirTicket.setEnabled(false);
                        vGestion_ImprimirTickets.jButtonModificar.setEnabled(true);
                        id = jTableClientesMercadoPago.getValueAt(seleccionado, 0).toString();
                        vGestion_ImprimirTickets.jTextDNI.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 2).toString());
                        vGestion_ImprimirTickets.jTextFieldCliente.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 3).toString() + " " + jTableClientesMercadoPago.getValueAt(seleccionado, 4).toString());
                        vGestion_ImprimirTickets.jTextFieldDomicilio.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 5).toString());
                        vGestion_ImprimirTickets.jTextFieldTelefonoTicket.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 6).toString());
                        vGestion_ImprimirTickets.jTextAreaDetalleTicket.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 7).toString());
                        vGestion_ImprimirTickets.jTextFieldTotalTicket.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 8).toString());
                        vGestion_ImprimirTickets.jTextFieldAbonaCliente.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 9).toString());
                        vGestion_ImprimirTickets.jTextFieldNumeroOperacion.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 10).toString());
                        vGestion_ImprimirTickets.jDateFecha.setDate(fechaseleccionada);
                        tickets.id = id;
                        dispose();
                    }
                }
            }
        });
    }

    public void MostrarDatos() {
        String columnas[] = {"IDMERCADOPAGO", "IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DOMICILIO", "TELEFONO", "DESCRIPCION", "IMPORTE", "IMPORTE ABONADO CLIENTE", "NRO OPERACION", "FECHA"};
        datos = control_mp.MostrarDatos();
        tabla = new DefaultTableModel(datos, columnas);
        jTableClientesMercadoPago.setModel(tabla);
        EliminarFilasVacias();
        OcultarColumnas();
    }

    public void MostrarDatosBuscado() {
        String columnas[] = {"IDMERCADOPAGO", "IDCLIENTE", "DNI", "NOMBRE", "APELLIDO", "DOMICILIO", "TELEFONO", "DESCRIPCION", "IMPORTE", "IMPORTE ABONADO CLIENTE", "NRO OPERACION", "FECHA"};
        datos = control_mp.MostrarDatosPorBusqueda(jTextFieldNroOperacionBuscar.getText());
        tabla = new DefaultTableModel(datos, columnas);
        jTableClientesMercadoPago.setModel(tabla);
        EliminarFilasVacias();
        OcultarColumnas();
    }

    public void EliminarFilasVacias() {
        if (jTableClientesMercadoPago.getRowCount() != 0) {
            for (int columna = 0; columna < jTableClientesMercadoPago.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableClientesMercadoPago.getRowCount(); fila++) {
                    if (jTableClientesMercadoPago.getValueAt(fila, columna) == null) {
                        tabla.removeRow(fila);
                    }
                }
            }

        }
    }

    public void OcultarColumnas() {
        jTableClientesMercadoPago.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableClientesMercadoPago.getColumnModel().getColumn(0).setMinWidth(0);
        jTableClientesMercadoPago.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableClientesMercadoPago.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableClientesMercadoPago.getColumnModel().getColumn(1).setMinWidth(0);
        jTableClientesMercadoPago.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    public void LimpiarSeleccion() {
        jTableClientesMercadoPago.clearSelection();
        jTableClientesMercadoPago.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClientesMercadoPago = new javax.swing.JTable();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonImprimirTicket = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNroOperacionBuscar = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado Clientes Mercado Pago");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTableClientesMercadoPago.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableClientesMercadoPago);

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonImprimirTicket.setBackground(new java.awt.Color(252, 249, 57));
        jButtonImprimirTicket.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonImprimirTicket.setText("Imprimir Ticket");
        jButtonImprimirTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirTicketActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("N° DE OPERACIÓN");

        jTextFieldNroOperacionBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setToolTipText("");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(252, 252, 252))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNroOperacionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNroOperacionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136)
                        .addComponent(jButtonImprimirTicket))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonImprimirTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        MostrarDatosBuscado();
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int seleccionado = jTableClientesMercadoPago.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            fecha = jTableClientesMercadoPago.getValueAt(seleccionado, 11).toString();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(vListas_Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (tickets == null || tickets.isClosed()) {
                tickets = new vGestion_ImprimirTickets();
                vMenuPrincipal.jDesktopPane1.add(tickets);
                tickets.setVisible(true);
                tickets.toFront();
            }

            vGestion_ImprimirTickets.jButtonImprimirTicket.setEnabled(false);
            vGestion_ImprimirTickets.jButtonModificar.setEnabled(true);
            id = jTableClientesMercadoPago.getValueAt(seleccionado, 0).toString();
            vGestion_ImprimirTickets.jTextDNI.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 2).toString());
            vGestion_ImprimirTickets.jTextFieldCliente.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 3).toString() + " " + jTableClientesMercadoPago.getValueAt(seleccionado, 4).toString());
            vGestion_ImprimirTickets.jTextFieldDomicilio.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 5).toString());
            vGestion_ImprimirTickets.jTextFieldTelefonoTicket.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 6).toString());
            vGestion_ImprimirTickets.jTextAreaDetalleTicket.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 7).toString());
            vGestion_ImprimirTickets.jTextFieldTotalTicket.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 8).toString());
            vGestion_ImprimirTickets.jTextFieldAbonaCliente.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 9).toString());
            vGestion_ImprimirTickets.jTextFieldNumeroOperacion.setText(jTableClientesMercadoPago.getValueAt(seleccionado, 10).toString());
            vGestion_ImprimirTickets.jDateFecha.setDate(fechaseleccionada);
            tickets.id = id;
            dispose();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int idmercadopago = jTableClientesMercadoPago.getSelectedRow();
        if (idmercadopago == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                mp.setIdcliente(Integer.parseInt(jTableClientesMercadoPago.getValueAt(idmercadopago, 0).toString()));
                if (control_mp.EliminarMercadoPago(mp)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    MostrarDatos();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonImprimirTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirTicketActionPerformed
        int seleccionado = jTableClientesMercadoPago.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Imprimir este Ticket?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                //reporte.CargarFactura(jTextFieldNumeroOperacion.getText(), jTextDNI.getText(), jTextFieldCliente.getText(), jTextFieldDomicilio.getText(), jTextFieldTelefonoTicket.getText(), jTextAreaDetalleTicket.getText(), /*jTextAreaSubTotal.getText(),*/ jTextFieldTotalTicket.getText(), jTextFieldAbonaCliente.getText());
                reporte.CargarFactura(jTableClientesMercadoPago.getValueAt(seleccionado, 10).toString(), jTableClientesMercadoPago.getValueAt(seleccionado, 2).toString(), jTableClientesMercadoPago.getValueAt(seleccionado, 3).toString() + " " + jTableClientesMercadoPago.getValueAt(seleccionado, 4).toString(), jTableClientesMercadoPago.getValueAt(seleccionado, 5).toString(), jTableClientesMercadoPago.getValueAt(seleccionado, 6).toString(), jTableClientesMercadoPago.getValueAt(seleccionado, 7).toString(), jTableClientesMercadoPago.getValueAt(seleccionado, 8).toString(), jTableClientesMercadoPago.getValueAt(seleccionado, 9).toString());
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButtonImprimirTicketActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonImprimirTicket;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClientesMercadoPago;
    private javax.swing.JTextField jTextFieldNroOperacionBuscar;
    // End of variables declaration//GEN-END:variables
}
