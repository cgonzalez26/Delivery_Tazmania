package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Asistencias;
import Controlador.control_Movimientos_Caja;
import Controlador.control_existencias;
import Modelo.Asistencias;
import Modelo.FechasBusquedas;
import Modelo.Movimientos_Caja;
import java.awt.Color;
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
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CRISTIAN
 */
public final class vLista_Asistencias extends javax.swing.JInternalFrame {

    String id, fecha, date, desde, hasta;
    control_Asistencias asistencia = new control_Asistencias();
    vGestion_Asistencias vAsistencias = new vGestion_Asistencias();
    Asistencias a = new Asistencias();
    control_existencias con = new control_existencias();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    Movimientos_Caja mc = new Movimientos_Caja();
    Sentencias_sql sql = new Sentencias_sql();
    DefaultTableModel row, modelemp, buscarfechas;
    Timestamp fechaseleccionada;
    DefaultListModel list;
    ArrayList<String> listemp;
    vGestion_Asistencias ventanaAsistencia = null;
    Date fechaactual = new Date();

    public vLista_Asistencias() {
        initComponents();
        IniciarFechas();
        Mostrar();

        jTableAsistencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTableAsistencias.rowAtPoint(e.getPoint());
                    fecha = (String) (jTableAsistencias.getValueAt(fila, 6));
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (ventanaAsistencia == null || ventanaAsistencia.isClosed()) {
                        VolverVentanaAsistencia("modificar");
                        SetearFechas();
                    }
                    id = (jTableAsistencias.getValueAt(fila, 0).toString());
                    vGestion_Asistencias.jTextFieldEmpleado.setText(jTableAsistencias.getValueAt(fila, 3).toString());
                    vGestion_Asistencias.jTextFieldDescripcion.setText(jTableAsistencias.getValueAt(fila, 4).toString());
                    vGestion_Asistencias.jTextFieldSueldo.setText(jTableAsistencias.getValueAt(fila, 5).toString());
                    vGestion_Asistencias.jDateFecha.setDate(fechaseleccionada);
                    ventanaAsistencia.id = id;
                    dispose();
                }
            }
        });
    }

    public void Mostrar() {
        String[] columnas = {"ID ASISTENCIA", "ID EMPLEADO", "NRO ASISTENCIA", "EMPLEADO", "DESCRIPCION", "SUELDO", "FECHA"};
        Object[][] datostabla = asistencia.MostrarDatos();
        row = new DefaultTableModel(datostabla, columnas);
        jTableAsistencias.setModel(row);
        EliminarFilasVacias();
        ocultar_columnas();
    }

    public void IniciarFechas() {
        if (FechasBusquedas.isIniciarFechaAsistencia() == false) {
            Date hoy = new Date();
            FechasBusquedas.setDateDesdeAsistencias(hoy);
            FechasBusquedas.setDateHastaAsistencias(hoy);
            jDateFechaDesde.setDate(FechasBusquedas.getDateDesdeAsistencias());
            jDateFechaHasta.setDate(FechasBusquedas.getDateHastaAsistencias());
            FechasBusquedas.setIniciarFechaAsistencia(true);
        } else {
            IniciarFechasSeteadas();
        }
    }

    public void SetearFechas() {
        FechasBusquedas.setDateDesdeAsistencias(jDateFechaDesde.getDate());
        FechasBusquedas.setDateHastaAsistencias(jDateFechaHasta.getDate());        
    }
    
    public void IniciarFechasSeteadas(){
        jDateFechaDesde.setDate(FechasBusquedas.getDateDesdeAsistencias());
        jDateFechaHasta.setDate(FechasBusquedas.getDateHastaAsistencias());
    }

    public void VolverVentanaAsistencia(String accion) {
        ventanaAsistencia = new vGestion_Asistencias();
        if (accion.equals("agregar")) {
            ventanaAsistencia.jButtonAgregarAsistencia.setEnabled(true);
            ventanaAsistencia.jButtonModificar.setEnabled(false);
            ventanaAsistencia.jDateFecha.setDate(fechaactual);
        } else {
            ventanaAsistencia.jButtonAgregarAsistencia.setEnabled(false);
            ventanaAsistencia.jButtonModificar.setEnabled(true);
        }

        vMenuPrincipal.jDesktopPane1.add(ventanaAsistencia);
        ventanaAsistencia.setVisible(true);
        dispose();
    }

    public void LimpiarSeleccion() {
        jTableAsistencias.clearSelection();
        jTableAsistencias.getSelectionModel().clearSelection();
    }

    public void EliminarFilasVacias() {
        if (jTableAsistencias.getRowCount() != 0) {
            for (int columna = 0; columna < jTableAsistencias.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableAsistencias.getRowCount(); fila++) {
                    if (jTableAsistencias.getValueAt(fila, columna) == null) {
                        row.removeRow(fila);
                    }
                }
            }
        }
    }

    public void MostrarBusquedaFechas() {
        String[] columnas = {"ID ASISTENCIA", "ID EMPLEADO", "NRO ASISTENCIA", "EMPLEADO", "DESCRIPCION", "SUELDO", "FECHA"};
        Object[][] datostabla = asistencia.MostrarDatosFechas(desde, hasta);
        if (datostabla.length != 0) {
            buscarfechas = new DefaultTableModel(datostabla, columnas);
            jTableAsistencias.setModel(buscarfechas);
            EliminarFilasVacias();
            //AjustarTamañoFilasAsistencias();
            ocultar_columnas();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron datos");
        }
    }

    public void ocultar_columnas() {
        jTableAsistencias.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableAsistencias.getColumnModel().getColumn(0).setMinWidth(0);
        jTableAsistencias.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableAsistencias.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableAsistencias.getColumnModel().getColumn(1).setMinWidth(0);
        jTableAsistencias.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAsistencias = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel1 = new javax.swing.JPanel();
        jDateFechaDesde = new com.toedter.calendar.JDateChooser();
        jDateFechaHasta = new com.toedter.calendar.JDateChooser();
        jButtonBuscarAsistencia = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonNuevaAsistencia = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado de Asistencias");
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

        jTableAsistencias.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableAsistencias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableAsistencias);

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jDateFechaDesde.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateFechaHasta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarAsistencia.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarAsistencia.setText("Buscar");
        jButtonBuscarAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarAsistenciaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("Desde:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setText("Hasta:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(112, 112, 112)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscarAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(222, 222, 222))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jButtonBuscarAsistencia)
                .addContainerGap())
        );

        jButtonNuevaAsistencia.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevaAsistencia.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevaAsistencia.setText("Nuevo");
        jButtonNuevaAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevaAsistenciaActionPerformed(evt);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(jButtonNuevaAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNuevaAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarAsistenciaActionPerformed
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        if (jDateFechaDesde.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateFechaHasta.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    MostrarBusquedaFechas();
                    SetearFechas();
                } else if (desde.isEmpty() || hasta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar la fecha que falta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
        }
    }//GEN-LAST:event_jButtonBuscarAsistenciaActionPerformed

    private void jButtonNuevaAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevaAsistenciaActionPerformed
        VolverVentanaAsistencia("agregar");
        SetearFechas();
    }//GEN-LAST:event_jButtonNuevaAsistenciaActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        /*if (jButtonModificar.getText().equals("Modificar")) {
            int s = jTableAsistencias.getSelectedRow();
            if (s == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                jButtonNuevaAsistencia.setEnabled(false);
                jButtonEliminar.setText("Modificar");
                jButtonModificar.setText("Cancelar");
                fecha = (String) (jTableAsistencias.getValueAt(s, 6));
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());

                } catch (ParseException ex) {
                    Logger.getLogger(vListas_Compras.class
                        .getName()).log(Level.SEVERE, null, ex);
                }
                id = (jTableAsistencias.getValueAt(s, 0).toString());
                jTextField1.setText(jTableAsistencias.getValueAt(s, 3).toString());
                jTextField2.setText(jTableAsistencias.getValueAt(s, 4).toString());
                jTextField3.setText(jTableAsistencias.getValueAt(s, 5).toString());
                jDateChooser1.setDate(fechaseleccionada);
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jButtonNuevaAsistencia.setEnabled(true);
                limpiar();
                LimpiarSeleccion();
                jButtonModificar.setText("Modificar");
                jButtonEliminar.setText("Eliminar");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        }*/
        int seleccionado = jTableAsistencias.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            fecha = (String) (jTableAsistencias.getValueAt(seleccionado, 6));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());

            } catch (ParseException ex) {
                Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (ventanaAsistencia == null || ventanaAsistencia.isClosed()) {
                VolverVentanaAsistencia("modificar");
                SetearFechas();
            }
            id = (jTableAsistencias.getValueAt(seleccionado, 0).toString());
            vGestion_Asistencias.jTextFieldEmpleado.setText(jTableAsistencias.getValueAt(seleccionado, 3).toString());
            vGestion_Asistencias.jTextFieldDescripcion.setText(jTableAsistencias.getValueAt(seleccionado, 4).toString());
            vGestion_Asistencias.jTextFieldSueldo.setText(jTableAsistencias.getValueAt(seleccionado, 5).toString());
            vGestion_Asistencias.jDateFecha.setDate(fechaseleccionada);
            ventanaAsistencia.id = id;
            dispose();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int fila = jTableAsistencias.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                String NroAsistencia = jTableAsistencias.getValueAt(fila, 2).toString();
                int idmovimientocaja = control_mc.getIdMovimientocaja(NroAsistencia);
                mc.setIdmovimientocaja(idmovimientocaja);
                if (control_mc.EliminarMovimientosCajaAbierta(mc)) {
                    a.setIdasistencia(Integer.parseInt(jTableAsistencias.getValueAt(fila, 0).toString()));
                    if (asistencia.EliminarAsistencias(a)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        Mostrar();
                    }
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        //desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        //hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccion();
        //desde = "";
        //hasta = "";
        //IniciarFechas();
        //Mostrar();
    }//GEN-LAST:event_formMouseClicked

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        dispose();
        SetearFechas();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarAsistencia;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevaAsistencia;
    public static com.toedter.calendar.JDateChooser jDateFechaDesde;
    public static com.toedter.calendar.JDateChooser jDateFechaHasta;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAsistencias;
    // End of variables declaration//GEN-END:variables
}
