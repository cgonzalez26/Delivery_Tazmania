package Vistas;

import Controlador.control_ConsumosEmpleados;
import Modelo.ConsumosEmpleados;
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

public final class vLista_ConsumosEmpleados extends javax.swing.JInternalFrame {

    public int idproducto;
    public float cantidad;
    String fecha, idconsumo;
    ConsumosEmpleados ce = new ConsumosEmpleados();
    control_ConsumosEmpleados contr_consumoempleado = new control_ConsumosEmpleados();
    DefaultTableModel tablaconsumos;
    Object[][] datosconsumos;
    Timestamp fechaseleccionada;
    vConsumosEmpleados consumosempleados = null;

    public vLista_ConsumosEmpleados() {
        initComponents();
        IniciarFechas();
        MostrarDatos();        
        jTableConsumosEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTableConsumosEmpleados.rowAtPoint(e.getPoint());
                    vConsumosEmpleados.jButtonAgregar.setEnabled(false);
                    String fecha = jTableConsumosEmpleados.getValueAt(fila, 5).toString();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vConsumosEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (consumosempleados == null || consumosempleados.isClosed()) {
                        consumosempleados = new vConsumosEmpleados();
                        vMenuPrincipal.jDesktopPane1.add(consumosempleados);
                        consumosempleados.setVisible(true);
                        consumosempleados.toFront();
                    }
                    idconsumo = jTableConsumosEmpleados.getValueAt(fila, 0).toString();
                    cantidad = Float.parseFloat(jTableConsumosEmpleados.getValueAt(fila, 4).toString());
                    idproducto = Integer.parseInt(jTableConsumosEmpleados.getValueAt(fila, 1).toString());
                    vConsumosEmpleados.jTextFieldEmp.setText(jTableConsumosEmpleados.getValueAt(fila, 2).toString());
                    vConsumosEmpleados.jTextFieldProd.setText(jTableConsumosEmpleados.getValueAt(fila, 3).toString());
                    vConsumosEmpleados.jTextFieldCantidad.setText(jTableConsumosEmpleados.getValueAt(fila, 4).toString());
                    vConsumosEmpleados.jDateFecha.setDate(fechaseleccionada);
                    consumosempleados.id = idconsumo;
                    consumosempleados.cantidad = cantidad;
                    consumosempleados.idproducto = idproducto;
                    dispose();
                }
            }
        });
    }

    public void MostrarDatos() {
        String desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        String hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        String columnas[] = {"IDCONSUMO", "IDPRODUCTO", "NOMBRE EMPLEADO", "PRODUCTO", "CANTIDAD", "FECHA"};
        datosconsumos = contr_consumoempleado.MostrarDatos(desde, hasta);
        tablaconsumos = new DefaultTableModel(datosconsumos, columnas);
        jTableConsumosEmpleados.setModel(tablaconsumos);
        EliminarFilasVaciasTabla1();
        ocultarcolumnastabla1();
    }

    public void EliminarFilasVaciasTabla1() {
        if (jTableConsumosEmpleados.getRowCount() != 0) {
            for (int columna = 0; columna < jTableConsumosEmpleados.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableConsumosEmpleados.getRowCount(); fila++) {
                    if (jTableConsumosEmpleados.getValueAt(fila, columna) == null) {
                        tablaconsumos.removeRow(fila);
                    }
                }
            }
        }
    }

    public void ocultarcolumnastabla1() {
        jTableConsumosEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableConsumosEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        jTableConsumosEmpleados.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableConsumosEmpleados.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableConsumosEmpleados.getColumnModel().getColumn(1).setMinWidth(0);
        jTableConsumosEmpleados.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    public void VolverVentanaConsumosEmpleados() {
        consumosempleados = new vConsumosEmpleados();
        vMenuPrincipal.jDesktopPane1.add(consumosempleados);
        consumosempleados.setVisible(true);
        dispose();
    }

    public void IniciarFechas() {
        Date hoy = new Date();
        jDateFechaDesde.setDate(hoy);
        jDateFechaHasta.setDate(hoy);
    }

    public void LimpiarSeleccionTabla1() {
        jTableConsumosEmpleados.clearSelection();
        jTableConsumosEmpleados.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jDateFechaDesde = new com.toedter.calendar.JDateChooser();
        jDateFechaHasta = new com.toedter.calendar.JDateChooser();
        jButtonBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConsumosEmpleados = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonNuevo = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado de Consumos Empleados");
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
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jDateFechaDesde.setDateFormatString("dd/MM/yyyy");
        jDateFechaDesde.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateFechaHasta.setDateFormatString("dd/MM/yyyy");
        jDateFechaHasta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Desde");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("Hasta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscar)
                .addContainerGap())
        );

        jScrollPane1.setOpaque(false);

        jTableConsumosEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableConsumosEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableConsumosEmpleados.setOpaque(false);
        jScrollPane1.setViewportView(jTableConsumosEmpleados);

        jButtonNuevo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevo.setText("Nuevo");
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(185, 185, 185)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        String hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        if (jDateFechaDesde.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateFechaHasta.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    String columnas[] = {"IDCONSUMO", "IDPRODUCTO", "NOMBRE EMPLEADO", "PRODUCTO", "CANTIDAD", "FECHA"};
                    datosconsumos = contr_consumoempleado.MostrarDatosBusqueda(desde, hasta);
                    if (datosconsumos.length != 0) {
                        tablaconsumos = new DefaultTableModel(datosconsumos, columnas);
                        jTableConsumosEmpleados.setModel(tablaconsumos);
                        EliminarFilasVaciasTabla1();
                        ocultarcolumnastabla1();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron datos");
                    }
                } else if (desde.isEmpty() || hasta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar la fecha que falta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        VolverVentanaConsumosEmpleados();
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int fila = jTableConsumosEmpleados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            vConsumosEmpleados.jButtonAgregar.setEnabled(false);
            String fecha = jTableConsumosEmpleados.getValueAt(fila, 5).toString();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(vConsumosEmpleados.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (consumosempleados == null || consumosempleados.isClosed()) {
                consumosempleados = new vConsumosEmpleados();
                vMenuPrincipal.jDesktopPane1.add(consumosempleados);
                consumosempleados.setVisible(true);
                consumosempleados.toFront();
            }
            idconsumo = jTableConsumosEmpleados.getValueAt(fila, 0).toString();
            cantidad = Float.parseFloat(jTableConsumosEmpleados.getValueAt(fila, 4).toString());
            idproducto = Integer.parseInt(jTableConsumosEmpleados.getValueAt(fila, 1).toString());
            vConsumosEmpleados.jTextFieldEmp.setText(jTableConsumosEmpleados.getValueAt(fila, 2).toString());
            vConsumosEmpleados.jTextFieldProd.setText(jTableConsumosEmpleados.getValueAt(fila, 3).toString());
            vConsumosEmpleados.jTextFieldCantidad.setText(jTableConsumosEmpleados.getValueAt(fila, 4).toString());
            vConsumosEmpleados.jDateFecha.setDate(fechaseleccionada);
            consumosempleados.id = idconsumo;
            consumosempleados.cantidad = cantidad;
            consumosempleados.idproducto = idproducto;
            dispose();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int i = jTableConsumosEmpleados.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int j = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (j == 0) {
                ce.setCantidad(Float.parseFloat(jTableConsumosEmpleados.getValueAt(i, 4).toString()));
                ce.setIdproducto(Integer.parseInt(jTableConsumosEmpleados.getValueAt(i, 1).toString()));
                if (contr_consumoempleado.CancelarStockConsumidoLocal(ce)) {
                    ce.setIdconsumo(Integer.parseInt(jTableConsumosEmpleados.getValueAt(i, 0).toString()));
                    if (contr_consumoempleado.EliminarConsumosEmpleados(ce)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        MostrarDatos();
                    }
                }
            } else {
                LimpiarSeleccionTabla1();
            }
        }

    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccionTabla1();
        //IniciarFechas();
        //MostrarDatos();
    }//GEN-LAST:event_formMouseClicked

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevo;
    private com.toedter.calendar.JDateChooser jDateFechaDesde;
    private com.toedter.calendar.JDateChooser jDateFechaHasta;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableConsumosEmpleados;
    // End of variables declaration//GEN-END:variables
}
