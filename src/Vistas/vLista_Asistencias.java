package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Asistencias;
import Controlador.control_Movimientos_Caja;
import Controlador.control_existencias;
import Modelo.Asistencias;
import Modelo.Movimientos_Caja;
<<<<<<< HEAD
import static Vistas.vGestion_Asistencias.jDateChooser1;
=======
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
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
import static Vistas.vGestion_Asistencias.txtEmpleado;
import static Vistas.vGestion_Asistencias.txtDescripcion;
import java.awt.Font;
import java.awt.font.FontRenderContext;

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
<<<<<<< HEAD
    Date fechatoday = new Date();
    /**
     * Creates new form vLista_Asistencias
     */
=======
    vGestion_Asistencias ventanaAsistencia = null;

>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
    public vLista_Asistencias() {
        initComponents();
        
        //java.util.Date fechaParseada= new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        jDateChooser2.setDate(fechatoday);
        jDateChooser3.setDate(fechatoday);
        Mostrar();
<<<<<<< HEAD
        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    vGestion_Asistencias.btnAgregar.setEnabled(true);
                    vGestion_Asistencias.btnModificar.setEnabled(false);

                    int fila = jTable1.rowAtPoint(e.getPoint());
                    fecha = (String) (jTable1.getValueAt(fila, 6));
=======
        IniciarFechas();

        jTableAsistencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTableAsistencias.rowAtPoint(e.getPoint());
                    fecha = (String) (jTableAsistencias.getValueAt(fila, 6));
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                    }
<<<<<<< HEAD
                    id = (jTable1.getValueAt(fila, 0).toString());
                    vGestion_Asistencias.txtEmpleado.setText(jTable1.getValueAt(fila, 3).toString());
                    vGestion_Asistencias.txtDescripcion.setText(jTable1.getValueAt(fila, 4).toString());
                    vGestion_Asistencias.txtSueldo.setText(jTable1.getValueAt(fila, 5).toString());
                    vGestion_Asistencias.jDateChooser1.setDate(fechaseleccionada);
=======
                    if(ventanaAsistencia == null || ventanaAsistencia.isClosed()){
                        ventanaAsistencia = new vGestion_Asistencias();
                        vMenuPrincipal.jDesktopPane1.add(ventanaAsistencia);
                        ventanaAsistencia.setVisible(true);
                        ventanaAsistencia.toFront();
                    }
                    id = (jTableAsistencias.getValueAt(fila, 0).toString());
                    vGestion_Asistencias.jTextFieldEmpleado.setText(jTableAsistencias.getValueAt(fila, 3).toString());
                    vGestion_Asistencias.jTextFieldDescripcion.setText(jTableAsistencias.getValueAt(fila, 4).toString());
                    vGestion_Asistencias.jTextFieldSueldo.setText(jTableAsistencias.getValueAt(fila, 5).toString());
                    vGestion_Asistencias.jDateFecha.setDate(fechaseleccionada);
                    ventanaAsistencia.id = id;
                    dispose();
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
                }
            }
        });
    }

    public void AjustarTamañoFilasAsistencias() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int nomemp = (int) font.getStringBounds(jTable1.getValueAt(i, 2).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int desc = (int) font.getStringBounds(jTable1.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int date = (int) font.getStringBounds(jTable1.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (nomemp > jTable1.getColumnModel().getColumn(2).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(2).setPreferredWidth(nomemp);
                }
                if (desc > jTable1.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(3).setPreferredWidth(desc);
                }
                if (date > jTable1.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(4).setPreferredWidth(date);
                }
            }
        }
    }
    
     public void EliminarFilasVacias() {
        if (jTable1.getRowCount() != 0) {
            for (int columna = 0; columna < jTable1.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable1.getRowCount(); fila++) {
                    if (jTable1.getValueAt(fila, columna) == null) {
                        row.removeRow(fila);
                    }
                }
            }
        }
    }
     
    public void Mostrar() {
        String[] columnas = {"ID ASISTENCIA", "ID EMPLEADO", "NRO ASISTENCIA", "EMPLEADO", "DESCRIPCION", "SUELDO", "FECHA"};
        Object[][] datostabla = asistencia.MostrarDatos();
        row = new DefaultTableModel(datostabla, columnas);
        jTableAsistencias.setModel(row);
        EliminarFilasVacias();
        //AjustarTamañoFilasAsistencias();
        ocultar_columnas();
    }
    
    public void IniciarFechas() {
        Date hoy = new Date();
        jDateFechaDesde.setDate(hoy);
        jDateFechaHasta.setDate(hoy);
    }

    public void VolverVentanaAsistencia() {
        ventanaAsistencia = new vGestion_Asistencias();
        vMenuPrincipal.jDesktopPane1.add(ventanaAsistencia);
        ventanaAsistencia.setVisible(true);
        dispose();
    }
    
    public void LimpiarSeleccion() {
        jTableAsistencias.clearSelection();
        jTableAsistencias.getSelectionModel().clearSelection();
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
<<<<<<< HEAD
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonAgregar = new javax.swing.JButton();
=======
        jDateFechaDesde = new com.toedter.calendar.JDateChooser();
        jDateFechaHasta = new com.toedter.calendar.JDateChooser();
        jButtonBuscarAsistencia = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonNuevaAsistencia = new javax.swing.JButton();
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
<<<<<<< HEAD
        setResizable(true);
=======
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
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

<<<<<<< HEAD
        jDateChooser2.setDateFormatString("dd/MM/yyyy");
        jDateChooser2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateChooser3.setDateFormatString("dd/MM/yyyy");
        jDateChooser3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        btnBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
=======
        jDateFechaDesde.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateFechaHasta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarAsistencia.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarAsistencia.setText("Buscar");
        jButtonBuscarAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarAsistenciaActionPerformed(evt);
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
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
<<<<<<< HEAD
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
=======
                .addComponent(jButtonBuscarAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
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
<<<<<<< HEAD
                .addComponent(btnBuscar)
                .addContainerGap())
        );

        jButtonAgregar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
=======
                .addComponent(jButtonBuscarAsistencia)
                .addContainerGap())
        );

        jButtonNuevaAsistencia.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevaAsistencia.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevaAsistencia.setText("Nuevo");
        jButtonNuevaAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevaAsistenciaActionPerformed(evt);
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
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

<<<<<<< HEAD
        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
=======
        jButtonEliminar.setBackground(new java.awt.Color(240, 87, 49));
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
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
<<<<<<< HEAD
                        .addGap(136, 136, 136))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(194, 194, 194))))
=======
                        .addGap(136, 136, 136))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(jButtonNuevaAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
<<<<<<< HEAD
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAgregar)
                    .addComponent(jButtonModificar)
                    .addComponent(jButtonEliminar))
                .addContainerGap(23, Short.MAX_VALUE))
=======
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNuevaAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
<<<<<<< HEAD
        
=======

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

>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
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
<<<<<<< HEAD
    
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        desde = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser3.getDateEditor().getUiComponent()).getText();
        if (jDateChooser2.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateChooser3.getDateEditor().getUiComponent().getForeground() != Color.RED) {
=======

    private void jButtonBuscarAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarAsistenciaActionPerformed
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        if (jDateFechaDesde.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateFechaHasta.getDateEditor().getUiComponent().getForeground() != Color.RED) {
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
                if (!desde.isEmpty() && !hasta.isEmpty()) {
                    MostrarBusquedaFechas();
                } else if (desde.isEmpty() || hasta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar la fecha que falta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
        }
<<<<<<< HEAD
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        vAsistencias = new vGestion_Asistencias();
        vGestion_Asistencias.btnAgregar.setEnabled(true);
        vGestion_Asistencias.btnModificar.setEnabled(false);
        vGestion_Asistencias.jDateChooser1.setDate(fechatoday);
        
        vMenuPrincipal.jDesktopPane1.add(vAsistencias);
        vAsistencias.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            vAsistencias = new vGestion_Asistencias();
            vMenuPrincipal.jDesktopPane1.add(vAsistencias);
            vAsistencias.setVisible(true);
            vGestion_Asistencias.btnAgregar.setEnabled(false);
            vGestion_Asistencias.btnModificar.setEnabled(true);
            id = jTable1.getValueAt(fila, 0).toString();
            vGestion_Asistencias.txtEmpleado.setText(jTable1.getValueAt(fila, 3).toString());
            vGestion_Asistencias.txtDescripcion.setText(jTable1.getValueAt(fila, 4).toString());
            vGestion_Asistencias.txtSueldo.setText(jTable1.getValueAt(fila, 5).toString());
            
            fecha = (String) (jTable1.getValueAt(fila, 6));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
            }
            vGestion_Asistencias.jDateChooser1.setDate(fechaseleccionada);
                    
            vAsistencias.idasistencia = id;
=======
    }//GEN-LAST:event_jButtonBuscarAsistenciaActionPerformed

    private void jButtonNuevaAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevaAsistenciaActionPerformed
        VolverVentanaAsistencia();
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
            vGestion_Asistencias.jButtonAgregarAsistencia.setEnabled(false);
            fecha = (String) (jTableAsistencias.getValueAt(seleccionado, 6));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());

            } catch (ParseException ex) {
                Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (ventanaAsistencia == null || ventanaAsistencia.isClosed()) {
                ventanaAsistencia = new vGestion_Asistencias();
                vMenuPrincipal.jDesktopPane1.add(ventanaAsistencia);
                ventanaAsistencia.setVisible(true);
                ventanaAsistencia.toFront();
            }
            id = (jTableAsistencias.getValueAt(seleccionado, 0).toString());
            vGestion_Asistencias.jTextFieldEmpleado.setText(jTableAsistencias.getValueAt(seleccionado, 3).toString());
            vGestion_Asistencias.jTextFieldDescripcion.setText(jTableAsistencias.getValueAt(seleccionado, 4).toString());
            vGestion_Asistencias.jTextFieldSueldo.setText(jTableAsistencias.getValueAt(seleccionado, 5).toString());
            ventanaAsistencia.id = id;
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
            dispose();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

<<<<<<< HEAD
    public void IniciarFechas() {
        Date hoy = new Date();
        jDateChooser2.setDate(hoy);
        jDateChooser3.setDate(hoy);
    }
    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }
    
    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        
        int fila = jTable1.getSelectedRow();
=======
    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int fila = jTableAsistencias.getSelectedRow();
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
<<<<<<< HEAD
                String NroAsistencia = jTable1.getValueAt(fila, 2).toString();
                int idmovimientocaja = control_mc.getIdMovimientocaja(NroAsistencia);
                mc.setIdmovimientocaja(idmovimientocaja);
                if (control_mc.EliminarMovimientosCajaAbierta(mc)) {
                    a.setIdasistencia(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
=======
                String NroAsistencia = jTableAsistencias.getValueAt(fila, 2).toString();
                int idmovimientocaja = control_mc.getIdMovimientocaja(NroAsistencia);
                mc.setIdmovimientocaja(idmovimientocaja);
                if (control_mc.EliminarMovimientosCajaAbierta(mc)) {
                    a.setIdasistencia(Integer.parseInt(jTableAsistencias.getValueAt(fila, 0).toString()));
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
                    if (asistencia.EliminarAsistencias(a)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        Mostrar();
                    }
                }
            } else {
                LimpiarSeleccion();
            }
        }
<<<<<<< HEAD
        
    }//GEN-LAST:event_jButtonEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    public static com.toedter.calendar.JDateChooser jDateChooser2;
    public static com.toedter.calendar.JDateChooser jDateChooser3;
=======
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desde = ((JTextField) jDateFechaDesde.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateFechaHasta.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccion();
        desde = "";
        hasta = "";
        IniciarFechas();
        Mostrar();
    }//GEN-LAST:event_formMouseClicked

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarAsistencia;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevaAsistencia;
    public static com.toedter.calendar.JDateChooser jDateFechaDesde;
    public static com.toedter.calendar.JDateChooser jDateFechaHasta;
>>>>>>> f25c1ec5655d6016f03093e02d829f2c0b67637a
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAsistencias;
    // End of variables declaration//GEN-END:variables
}
