package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Asistencias;
import Controlador.control_Movimientos_Caja;
import Controlador.control_existencias;
import Modelo.Asistencias;
import Modelo.Movimientos_Caja;
import Modelo.Session;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Asistencias extends javax.swing.JInternalFrame {

    String id, fecha, date, desde, hasta;
    control_Asistencias asistencia = new control_Asistencias();
    Asistencias a = new Asistencias();
    control_existencias con = new control_existencias();
    control_Movimientos_Caja control_mc = new control_Movimientos_Caja();
    Movimientos_Caja mc = new Movimientos_Caja();
    Sentencias_sql sql = new Sentencias_sql();
    DefaultTableModel row, modelemp, buscarfechas;
    Timestamp fechaseleccionada;
    DefaultListModel list;
    ArrayList<String> listemp;

    public vGestion_Asistencias() {
        initComponents();
        IniciarFechas();
        Mostrar();
        MostrarEmpleados();
        jList2.setVisible(false);
        //((JTextField) jDateChooser1.getDateEditor().getUiComponent()).setEditable(false);

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    jButton1.setEnabled(false);
                    jButton3.setText("Modificar");
                    jButton2.setText("Cancelar");
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    fecha = (String) (jTable1.getValueAt(fila, 6));
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    id = (jTable1.getValueAt(fila, 0).toString());
                    jTextField1.setText(jTable1.getValueAt(fila, 3).toString());
                    jTextField2.setText(jTable1.getValueAt(fila, 4).toString());
                    jTextField3.setText(jTable1.getValueAt(fila, 5).toString());
                    jDateChooser1.setDate(fechaseleccionada);
                }
            }
        });
    }

    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void IniciarFechas() {
        Date hoy = new Date();
        jDateChooser2.setDate(hoy);
        jDateChooser3.setDate(hoy);
    }

    public void limpiar() {
        jTextField1.setText("");
        jTextField2.setText("");
        ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).setText("");
        jTextField3.setText("");
    }

    public void Mostrar() {
        String[] columnas = {"ID ASISTENCIA", "ID EMPLEADO", "NRO ASISTENCIA", "EMPLEADO", "DESCRIPCION", "SUELDO", "FECHA"};
        Object[][] datostabla = asistencia.MostrarDatos();
        row = new DefaultTableModel(datostabla, columnas);
        jTable1.setModel(row);
        EliminarFilasVacias();
        //AjustarTamañoFilasAsistencias();
        ocultar_columnas();
    }

    public void MostrarBusquedaFechas() {
        String[] columnas = {"ID ASISTENCIA", "ID EMPLEADO", "NRO ASISTENCIA", "EMPLEADO", "DESCRIPCION", "SUELDO", "FECHA"};
        Object[][] datostabla = asistencia.MostrarDatosFechas(desde, hasta);
        if (datostabla.length != 0) {
            buscarfechas = new DefaultTableModel(datostabla, columnas);
            jTable1.setModel(buscarfechas);
            EliminarFilasVacias();
            //AjustarTamañoFilasAsistencias();
            ocultar_columnas();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron datos");
        }
    }

    public void MostrarEmpleados() {
        String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
        Object[][] datostabla = asistencia.MostrarEmpleados();
        modelemp = new DefaultTableModel(datostabla, columnas);
        jTable2.setModel(modelemp);
        EliminarFilasVaciasEmpleados();
        //AjustarTamañoFilasEmpleados();
    }

    public void ListasEmpleado() {
        listemp = con.list("empleados", "Nombre", jTextField1.getText());
        String substr = jTextField1.getText().toLowerCase();
        list = new DefaultListModel();
        jList2.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listemp.size(); i++) {
            if (listemp.get(i) == null) {
                listemp.remove(i);
            } else {
                String sublist = listemp.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listemp.get(i));
                    jList2.setVisible(true);
                    if (jTextField1.getText().isEmpty()) {
                        jList2.setVisible(false);
                    }
                }
            }
        }
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

    public void AjustarTamañoFilasEmpleados() {
        if (jTable2.getRowCount() != 0) {
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int emp = (int) font.getStringBounds(jTable2.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (emp > jTable2.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTable2.getColumnModel().getColumn(0).setPreferredWidth(emp);
                }
            }
        }
    }

    public void ocultar_columnas() {
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
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

    public void EliminarFilasVaciasEmpleados() {
        if (jTable2.getRowCount() != 0) {
            int filas = jTable2.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable2.getValueAt(fila, 0) == null) {
                    modelemp.removeRow(fila);
                }
            }
        }
    }

    public void Cerrar() {
        if (!jTextField1.getText().trim().equals("") || !jTextField2.getText().trim().isEmpty() || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().trim().isEmpty() || !jTextField3.getText().trim().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarEmpleado = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jList2 = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jButton7 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        vSeleccionarEmpleado.setTitle("Seleccionar Nombre Empleado");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vSeleccionarEmpleado.setIconImage(icono);
        vSeleccionarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarEmpleadoMouseClicked(evt);
            }
        });
        vSeleccionarEmpleado.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarEmpleadoWindowClosing(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Aceptar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setText("Cancelar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setText("Nombre Empleado");

        jTextField4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton8.setText("Buscar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vSeleccionarEmpleadoLayout = new javax.swing.GroupLayout(vSeleccionarEmpleado.getContentPane());
        vSeleccionarEmpleado.getContentPane().setLayout(vSeleccionarEmpleadoLayout);
        vSeleccionarEmpleadoLayout.setHorizontalGroup(
            vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vSeleccionarEmpleadoLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        vSeleccionarEmpleadoLayout.setVerticalGroup(
            vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarEmpleadoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(vSeleccionarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Asistencias");
        setFocusable(false);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(860, 649));
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("(*) Fecha:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(662, 139, 56, 19));

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(662, 164, 172, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setText("(*) Empleado:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 170, 30));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 250, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 38, 30));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        getContentPane().add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 189, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("(*) Descripción:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 207, -1, 30));

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 243, 401, 35));

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 296, 824, 272));

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 586, 101, -1));

        jButton2.setBackground(new java.awt.Color(252, 249, 57));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 586, 101, -1));

        jButton3.setBackground(new java.awt.Color(240, 87, 49));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 586, 101, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Sueldo:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(662, 212, -1, 21));

        jTextField3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(662, 243, 172, 35));

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jDateChooser2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jDateChooser3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton7.setText("Buscar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 384, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(357, 357, 357)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jButton7)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jTextField1.getText().trim().equals("") && !jTextField2.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
            if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                date = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
                if (jTextField3.getText().equals("")) {
                    int i = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextField1.getText()));
                        a.setDescripcion(jTextField2.getText());
                        a.setSueldo((float) 0.0);
                        if (asistencia.InsertarAsistencias(a)) {
                            int idmovimiento = sql.obtenerUltimoId("asistencias", "idasistencia");
                            String codigo = sql.generaCodigo("pago_emp");
                            sql.ejecutarSql("UPDATE asistencias SET NroAsistencia ='" + codigo + "' WHERE idasistencia=" + Integer.toString(idmovimiento));
                            a.setNroAsistencia(codigo);
                            mc.setDescripcion("PAGO EMPLEADOS");
                            mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                            mc.setIdtipomovimiento(11);
                            mc.setIdusuario(Session.getIdusuario());
                            mc.setNromovimiento(codigo);
                            mc.setFecha_movimiento(date);
                            mc.setMonto(a.getSueldo());
                            mc.setIdmovimiento(idmovimiento);
                            mc.setDetalle(a.getDescripcion());
                            mc.setActivo(1);
                            control_mc.InsertarMovimientosCaja(mc);
                            JOptionPane.showMessageDialog(null, "Nueva Asistencia agregado");
                            Mostrar();
                            limpiar();
                        }
                    }
                } else {
                    String sueldo = jTextField3.getText(), svalor = "", svalordec = "", svalordecdob = "";
                    int cant = jTextField3.getText().length();
                    switch (cant) {
                        case 1:
                            svalor = sueldo.substring(0, 1);
                            break;
                        case 3:
                            svalordec = sueldo.substring(0, 3);
                            break;
                        case 4:
                            svalordecdob = sueldo.substring(0, 4);
                            break;
                        default:
                            break;
                    }
                    if (svalor.equals(",") || svalor.equals(".")) { //svalor.equals(",") || svalor.equals(".")
                        JOptionPane.showMessageDialog(null, "Ingrese correctamente el sueldo");
                    } else if (svalor.equals("0") || svalordec.equals("0.0") || svalordecdob.equals("0.00")) {
                        int j = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (j == 0) {
                            a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextField1.getText()));
                            a.setDescripcion(jTextField2.getText());
                            a.setSueldo((float) 0.0);
                            if (asistencia.InsertarAsistencias(a)) {
                                int idmovimiento = sql.obtenerUltimoId("asistencias", "idasistencia");
                                String codigo = sql.generaCodigo("pago_emp");
                                sql.ejecutarSql("UPDATE asistencias SET NroAsistencia ='" + codigo + "' WHERE idasistencia=" + Integer.toString(idmovimiento));
                                a.setNroAsistencia(codigo);
                                mc.setDescripcion("PAGO EMPLEADOS");
                                mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                                mc.setIdtipomovimiento(11);
                                mc.setIdusuario(Session.getIdusuario());
                                mc.setNromovimiento(codigo);
                                mc.setFecha_movimiento(date);
                                mc.setMonto(a.getSueldo());
                                mc.setIdmovimiento(idmovimiento);
                                mc.setDetalle(a.getDescripcion());
                                mc.setActivo(1);
                                control_mc.InsertarMovimientosCaja(mc);
                                JOptionPane.showMessageDialog(null, "Nueva Asistencia agregado");
                                Mostrar();
                                limpiar();
                            }
                        }
                    } else {
                        a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextField1.getText()));
                        a.setDescripcion(jTextField2.getText());
                        a.setSueldo(Float.parseFloat(sueldo));
                        if (asistencia.InsertarAsistencias(a)) {
                            int idmovimiento = sql.obtenerUltimoId("asistencias", "idasistencia");
                            String codigo = sql.generaCodigo("pago_emp");
                            sql.ejecutarSql("UPDATE asistencias SET NroAsistencia ='" + codigo + "' WHERE idasistencia=" + Integer.toString(idmovimiento));
                            a.setNroAsistencia(codigo);
                            mc.setDescripcion("PAGO EMPLEADOS");
                            mc.setIdcajaturno(Session.getIdcajaturno_abierta());
                            mc.setIdtipomovimiento(11);
                            mc.setIdusuario(Session.getIdusuario());
                            mc.setNromovimiento(codigo);
                            mc.setFecha_movimiento(date);
                            mc.setMonto(a.getSueldo());
                            mc.setIdmovimiento(idmovimiento);
                            mc.setDetalle(a.getDescripcion());
                            mc.setActivo(1);
                            control_mc.InsertarMovimientosCaja(mc);
                            JOptionPane.showMessageDialog(null, "Nueva Asistencia agregado");
                            Mostrar();
                            limpiar();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jButton2.getText().equals("Modificar")) {
            int s = jTable1.getSelectedRow();
            if (s == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                jButton1.setEnabled(false);
                jButton3.setText("Modificar");
                jButton2.setText("Cancelar");
                fecha = (String) (jTable1.getValueAt(s, 6));
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());

                } catch (ParseException ex) {
                    Logger.getLogger(vListas_Compras.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                id = (jTable1.getValueAt(s, 0).toString());
                jTextField1.setText(jTable1.getValueAt(s, 3).toString());
                jTextField2.setText(jTable1.getValueAt(s, 4).toString());
                jTextField3.setText(jTable1.getValueAt(s, 5).toString());
                jDateChooser1.setDate(fechaseleccionada);
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jButton1.setEnabled(true);
                limpiar();
                LimpiarSeleccion();
                jButton2.setText("Modificar");
                jButton3.setText("Eliminar");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jButton3.getText().equals("Modificar")) {
            if (!jTextField1.getText().trim().equals("") && !jTextField2.getText().trim().equals("") && !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals("")) {
                date = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    if (jTextField3.getText().equals("")) {
                        int i = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, guardar cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (i == 0) {
                            a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextField1.getText()));
                            a.setDescripcion(jTextField2.getText());
                            a.setSueldo((float) 0.0);
                            a.setIdasistencia(Integer.parseInt(id));
                            if (asistencia.EditarAsistencias(a)) {
                                mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(id), 11));
                                mc.setIdtipomovimiento(11);
                                mc.setIdusuario(Session.getIdusuario());
                                mc.setFecha_movimiento(date);
                                mc.setMonto(a.getSueldo());
                                mc.setIdmovimiento(Integer.parseInt(id));
                                mc.setDetalle(a.getDescripcion());
                                control_mc.EditarMovimientosCaja(mc);

                                JOptionPane.showMessageDialog(null, "Modificado");
                                jButton1.setEnabled(true);
                                jButton2.setText("Modificar");
                                jButton3.setText("Eliminar");
                                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                Mostrar();
                                LimpiarSeleccion();
                                limpiar();
                            }
                        }
                    } else {
                        String sueldo = jTextField3.getText(), svalor = "", svalordec = "", svalordecdob = "";
                        int cant = jTextField3.getText().length();
                        switch (cant) {
                            case 1:
                                svalor = sueldo.substring(0, 1);
                                break;
                            case 3:
                                svalordec = sueldo.substring(0, 3);
                                break;
                            case 4:
                                svalordecdob = sueldo.substring(0, 4);
                                break;
                            default:
                                break;
                        }
                        if (svalor.equals(",") || svalor.equals(".")) { //svalor.equals(",") || svalor.equals(".")
                            JOptionPane.showMessageDialog(null, "Ingrese correctamente el sueldo");
                        } else if (svalor.equals("0") || svalordec.equals("0.0") || svalordecdob.equals("0.00")) {
                            int j = JOptionPane.showConfirmDialog(null, "No cobrará sueldo este empleado, guardar cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                            if (j == 0) {
                                a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextField1.getText()));
                                a.setDescripcion(jTextField2.getText());
                                a.setSueldo((float) 0.0);
                                a.setIdasistencia(Integer.parseInt(id));
                                if (asistencia.EditarAsistencias(a)) {
                                    mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(id), 11));
                                    mc.setIdtipomovimiento(11);
                                    mc.setIdusuario(Session.getIdusuario());
                                    mc.setFecha_movimiento(date);
                                    mc.setMonto(a.getSueldo());
                                    mc.setIdmovimiento(Integer.parseInt(id));
                                    mc.setDetalle(a.getDescripcion());
                                    control_mc.EditarMovimientosCaja(mc);

                                    JOptionPane.showMessageDialog(null, "Modificado");
                                    jButton1.setEnabled(true);
                                    jButton2.setText("Modificar");
                                    jButton3.setText("Eliminar");
                                    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                    Mostrar();
                                    LimpiarSeleccion();
                                    limpiar();
                                }
                            }
                        } else {
                            a.setIdempleado(asistencia.ObtenerIDEmpleado(jTextField1.getText()));
                            a.setDescripcion(jTextField2.getText());
                            a.setSueldo(Float.parseFloat(sueldo));
                            a.setIdasistencia(Integer.parseInt(id));
                            if (asistencia.EditarAsistencias(a)) {
                                mc.setIdmovimientocaja(control_mc.ObtenerIDMovimientoCaja(Integer.parseInt(id), 11));
                                mc.setIdtipomovimiento(11);
                                mc.setIdusuario(Session.getIdusuario());
                                mc.setFecha_movimiento(date);
                                mc.setMonto(a.getSueldo());
                                mc.setIdmovimiento(Integer.parseInt(id));
                                mc.setDetalle(a.getDescripcion());
                                control_mc.EditarMovimientosCaja(mc);

                                JOptionPane.showMessageDialog(null, "Modificado");
                                jButton1.setEnabled(true);
                                jButton2.setText("Modificar");
                                jButton3.setText("Eliminar");
                                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                Mostrar();
                                LimpiarSeleccion();
                                limpiar();
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe completar los campos obligatorios");
            }
        } else {
            int fila = jTable1.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    String NroAsistencia = jTable1.getValueAt(fila, 2).toString();
                    int idmovimientocaja = control_mc.getIdMovimientocaja(NroAsistencia);
                    mc.setIdmovimientocaja(idmovimientocaja);
                    if (control_mc.EliminarMovimientosCajaAbierta(mc)) {
                        a.setIdasistencia(Integer.parseInt(jTable1.getValueAt(fila, 0).toString()));
                        if (asistencia.EliminarAsistencias(a)) {
                            JOptionPane.showMessageDialog(null, "Eliminado");
                            Mostrar();
                        }
                    }
                } else {
                    LimpiarSeleccion();
                }
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButton2.isEnabled() && jButton2.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            Cerrar();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (jTable2.getRowCount() != 0) {
            int i = jTable2.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarEmpleado.dispose();
                jTextField1.setText(jTable2.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos empleados todavia");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
            jTextField1.setText("");
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void vSeleccionarEmpleadoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarEmpleado.dispose();
            jTextField1.setText("");
        } else {
            vSeleccionarEmpleado.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarEmpleadoWindowClosing

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        vSeleccionarEmpleado.setSize(390, 633);
        vSeleccionarEmpleado.setLocationRelativeTo(this);
        vSeleccionarEmpleado.setModal(true);
        vSeleccionarEmpleado.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desde = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser3.getDateEditor().getUiComponent()).getText();
        LimpiarSeleccion();
        desde = "";
        hasta = "";
        IniciarFechas();
        Mostrar();
    }//GEN-LAST:event_formMouseClicked

    private void vSeleccionarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarEmpleadoMouseClicked
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
        MostrarEmpleados();
        jTextField4.setText("");
    }//GEN-LAST:event_vSeleccionarEmpleadoMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        desde = ((JTextField) jDateChooser2.getDateEditor().getUiComponent()).getText();
        hasta = ((JTextField) jDateChooser3.getDateEditor().getUiComponent()).getText();
        if (jDateChooser2.getDateEditor().getUiComponent().getForeground() != Color.RED) {
            if (jDateChooser3.getDateEditor().getUiComponent().getForeground() != Color.RED) {
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
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (!jTextField4.getText().isEmpty()) {
            String[] columnas = {"NOMBRE EMPLEADOS", "ROL DE TRABAJO"};
            Object[][] datostabla = asistencia.MostrarEmpleadoBuscado(jTextField4.getText());
            if (datostabla.length != 0) {
                modelemp = new DefaultTableModel(datostabla, columnas);
                jTable2.setModel(modelemp);
                EliminarFilasVaciasEmpleados();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        if (i != -1) {
            jTextField1.setText(jList2.getSelectedValue());
            jList2.setVisible(false);
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        ListasEmpleado();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    public static com.toedter.calendar.JDateChooser jDateChooser2;
    public static com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    public static javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JDialog vSeleccionarEmpleado;
    // End of variables declaration//GEN-END:variables
}
