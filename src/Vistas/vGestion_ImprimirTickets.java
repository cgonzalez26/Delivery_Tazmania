package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_MercadoPago;
import Controlador.control_Reportes;
import Controlador.control_Ventas;
import Controlador.control_existencias;
import Modelo.MercadoPago;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_ImprimirTickets extends javax.swing.JInternalFrame {

    ArrayList<String> listcliente;
    DefaultListModel list;
    MercadoPago mp = new MercadoPago();
    control_MercadoPago contr_mp = new control_MercadoPago();
    control_existencias combo = new control_existencias();
    control_Ventas ventas = new control_Ventas();
    Sentencias_sql sql = new Sentencias_sql();
    control_Reportes reporte = new control_Reportes();
    Object[][] datostabla;
    DefaultTableModel tabla;
    String id;
    vListas_ClientesMercadoPago lista;
    vListas_Ventas listaventa;

    public vGestion_ImprimirTickets() {
        initComponents();
        MostrarCliente();
        jButtonModificar.setEnabled(false);
    }
    
    public void ListaClientes() {
        listcliente = combo.listaClientes(jTextDNI.getText());
        String substr = jTextDNI.getText().toLowerCase();
        list = new DefaultListModel();
        jListClientes.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listcliente.size(); i++) {
            if (listcliente.get(i) == null) {
                listcliente.remove(i);
            } else {
                String sublist = listcliente.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listcliente.get(i));
                    jListClientes.setVisible(true);
                    if (jTextDNI.getText().isEmpty()) {
                        jListClientes.setVisible(false);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasClientes() {
        if (jTableClientes.getRowCount() != 0) {
            for (int columna = 0; columna < jTableClientes.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableClientes.getRowCount(); fila++) {
                    if (jTableClientes.getValueAt(fila, columna) == null) {
                        tabla.removeRow(fila);
                    }
                }
            }
        }
    }

    public void MostrarCliente() {
        String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
        datostabla = ventas.MostrarClientes();
        tabla = new DefaultTableModel(datostabla, columnas);
        jTableClientes.setModel(tabla);
        EliminarFilasVaciasClientes();
        //ocultar_columnasClientes();
    }

    public void LimpiarSeleccionClientes() {
        jTableClientes.clearSelection();
        jTableClientes.getSelectionModel().clearSelection();
    }

    public void ocultar_columnasClientes() {
        jTableClientes.getColumnModel().getColumn(3).setMaxWidth(0);
        jTableClientes.getColumnModel().getColumn(3).setMinWidth(0);
        jTableClientes.getColumnModel().getColumn(3).setPreferredWidth(0);
    }

    public void BuscarClientes() {
        if (!jTextFieldNombre.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaCliente(jTextFieldNombre.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                //ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldBuscarDNI.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNI(jTextFieldBuscarDNI.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                //ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaTelefono(jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                //ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldBuscarDNI.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNICliente(jTextFieldBuscarDNI.getText(), jTextFieldNombre.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                //ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldNombre.getText().isEmpty() && jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaClienteTelefono(jTextFieldNombre.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                //ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldBuscarDNI.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNITelefono(jTextFieldBuscarDNI.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                //ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else if (!jTextFieldBuscarDNI.getText().isEmpty() && !jTextFieldNombre.getText().isEmpty() && !jTextFieldTelefono.getText().isEmpty()) {
            datostabla = ventas.MostrarDatosBusquedaDNIClienteTelefono(jTextFieldBuscarDNI.getText(), jTextFieldNombre.getText(), jTextFieldTelefono.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"DNI", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
                tabla = new DefaultTableModel(datostabla, columnas);
                jTableClientes.setModel(tabla);
                EliminarFilasVaciasClientes();
                //ocultar_columnasClientes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarCliente = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jButtonAgregarCliente = new javax.swing.JButton();
        jButtonCancelarCliente = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonBuscarClientes = new javax.swing.JButton();
        jLabelBuscarDNI = new javax.swing.JLabel();
        jTextFieldBuscarDNI = new javax.swing.JTextField();
        jLabelTelefono = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jListClientes = new javax.swing.JList<>();
        jTextFieldCliente = new javax.swing.JTextField();
        jLabelSeleccionarCliente = new javax.swing.JLabel();
        jButtonSeleccionarCliente = new javax.swing.JButton();
        jLabelNumeroOperacion = new javax.swing.JLabel();
        jTextFieldNumeroOperacion = new javax.swing.JTextField();
        jLabelDomicilio = new javax.swing.JLabel();
        jTextFieldDomicilio = new javax.swing.JTextField();
        jLabelTelefonoFactura = new javax.swing.JLabel();
        jTextFieldTelefonoTicket = new javax.swing.JTextField();
        jLabelDetalle = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDetalleTicket = new javax.swing.JTextArea();
        jLabelTotal = new javax.swing.JLabel();
        jTextFieldTotalTicket = new javax.swing.JTextField();
        jLabelAbonaFactura = new javax.swing.JLabel();
        jTextFieldAbonaCliente = new javax.swing.JTextField();
        jButtonImprimirTicket = new javax.swing.JButton();
        jButtonCancelarTicket = new javax.swing.JButton();
        jLabelDNI = new javax.swing.JLabel();
        jTextDNI = new javax.swing.JTextField();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        jLabelFecha = new javax.swing.JLabel();
        jButtonModificar = new javax.swing.JButton();

        vSeleccionarCliente.setTitle("Seleccionar Cliente");
        java.awt.Image icondeliv = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png")).getImage();
        vSeleccionarCliente.setIconImage(icondeliv);
        vSeleccionarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarClienteMouseClicked(evt);
            }
        });
        vSeleccionarCliente.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarClienteWindowClosing(evt);
            }
        });

        jTableClientes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTableClientes);

        jButtonAgregarCliente.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregarCliente.setText("Agregar");
        jButtonAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarClienteActionPerformed(evt);
            }
        });

        jButtonCancelarCliente.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarCliente.setText("Cancelar");
        jButtonCancelarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarClienteActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombre.setText("Nombre");

        jTextFieldNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButtonBuscarClientes.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarClientes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarClientes.setText("Buscar");
        jButtonBuscarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarClientesActionPerformed(evt);
            }
        });

        jLabelBuscarDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelBuscarDNI.setText("DNI");

        jTextFieldBuscarDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jLabelTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTelefono.setText("Telefono");

        jTextFieldTelefono.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBuscarDNI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabelNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabelTelefono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(383, 383, 383))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombre)
                    .addComponent(jLabelBuscarDNI)
                    .addComponent(jTextFieldBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTelefono)
                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscarClientes)
                .addContainerGap())
        );

        javax.swing.GroupLayout vSeleccionarClienteLayout = new javax.swing.GroupLayout(vSeleccionarCliente.getContentPane());
        vSeleccionarCliente.getContentPane().setLayout(vSeleccionarClienteLayout);
        vSeleccionarClienteLayout.setHorizontalGroup(
            vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(jButtonAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(256, 256, 256)
                        .addComponent(jButtonCancelarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        vSeleccionarClienteLayout.setVerticalGroup(
            vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancelarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Imprimir Ticket");
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jListClientes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jListClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListClientes.setValueIsAdjusting(true);
        jListClientes.setVisibleRowCount(0);
        jListClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListClientesMouseClicked(evt);
            }
        });
        getContentPane().add(jListClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 76, 360, -1));

        jTextFieldCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextFieldCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 116, 291, 32));

        jLabelSeleccionarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelSeleccionarCliente.setText("(*) Cliente");
        getContentPane().add(jLabelSeleccionarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 87, -1, -1));

        jButtonSeleccionarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButtonSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionarClienteActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSeleccionarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 44, 42, 32));

        jLabelNumeroOperacion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNumeroOperacion.setText("(*) N° OPERACIÓN");
        getContentPane().add(jLabelNumeroOperacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 159, -1, -1));

        jTextFieldNumeroOperacion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldNumeroOperacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNumeroOperacionKeyTyped(evt);
            }
        });
        getContentPane().add(jTextFieldNumeroOperacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 188, 408, 30));

        jLabelDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDomicilio.setText("(*) Domicilio:");
        getContentPane().add(jLabelDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 11, -1, 22));

        jTextFieldDomicilio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextFieldDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 44, 531, 28));

        jLabelTelefonoFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTelefonoFactura.setText("(*) Telefono:");
        getContentPane().add(jLabelTelefonoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 83, -1, 19));

        jTextFieldTelefonoTicket.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextFieldTelefonoTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 113, 266, 29));

        jLabelDetalle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDetalle.setText("(*) DETALLE:");
        getContentPane().add(jLabelDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 159, -1, -1));

        jTextAreaDetalleTicket.setColumns(20);
        jTextAreaDetalleTicket.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jTextAreaDetalleTicket.setRows(5);
        jScrollPane3.setViewportView(jTextAreaDetalleTicket);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 188, 530, 223));

        jLabelTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTotal.setText("(*) IMPORTE TOTAL");
        getContentPane().add(jLabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 364, -1, -1));

        jTextFieldTotalTicket.setEditable(false);
        jTextFieldTotalTicket.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextFieldTotalTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 393, 160, 31));

        jLabelAbonaFactura.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelAbonaFactura.setText("(*) El Cliente Abona CON:");
        getContentPane().add(jLabelAbonaFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 299, -1, 13));

        jTextFieldAbonaCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jTextFieldAbonaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 323, 160, 30));

        jButtonImprimirTicket.setBackground(new java.awt.Color(252, 249, 57));
        jButtonImprimirTicket.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonImprimirTicket.setText("Imprimir");
        jButtonImprimirTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirTicketActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonImprimirTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 444, 100, 30));

        jButtonCancelarTicket.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelarTicket.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelarTicket.setText("Cancelar");
        jButtonCancelarTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarTicketActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancelarTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 444, 100, 30));

        jLabelDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelDNI.setText("(*) Seleccionar por Nro Documento");
        getContentPane().add(jLabelDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 15, -1, -1));

        jTextDNI.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextDNIKeyReleased(evt);
            }
        });
        getContentPane().add(jTextDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 46, 248, 30));

        jDateFecha.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jDateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 258, 207, 30));

        jLabelFecha.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelFecha.setText("(*) Fecha");
        getContentPane().add(jLabelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 229, -1, -1));

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 444, 100, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListClientesMouseClicked
        int i = jListClientes.getSelectedIndex();
        ArrayList<String> array;
        if (i != -1) {
            //jTextFieldCliente.setText(jListClientes.getSelectedValue());
            char[] caracteres = jListClientes.getSelectedValue().toCharArray();
            ArrayList<Character> listcaracteres = new ArrayList<>();
            ArrayList<Character> listdni = new ArrayList<>();
            String cadena = jListClientes.getSelectedValue();
            for (char caracter : caracteres) {
                listcaracteres.add(caracter);
            }
            char dospuntos = ':';
            for (int m = 0; m < listcaracteres.size(); m++) {
                if (cadena.charAt(m) != dospuntos) {
                    listdni.add(cadena.charAt(m));
                } else {
                    break;
                }
            }

            String dni = listdni.stream().map(Object::toString).collect(Collectors.joining());
            jTextDNI.setText(dni);
            array = ventas.ObtenerDatosCliente(jTextDNI.getText());
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    jTextFieldCliente.setText(array.get(j));
                    j++;
                    jTextFieldDomicilio.setText(array.get(j));
                    j++;
                    jTextFieldTelefonoTicket.setText(array.get(j));
                    j++;
                    jListClientes.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jListClientesMouseClicked

    private void jButtonSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionarClienteActionPerformed
        vSeleccionarCliente.setSize(945, 520);
        vSeleccionarCliente.setLocationRelativeTo(this);
        vSeleccionarCliente.setModal(true);
        vSeleccionarCliente.setVisible(true);
    }//GEN-LAST:event_jButtonSeleccionarClienteActionPerformed

    private void jTextFieldNumeroOperacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNumeroOperacionKeyTyped
        char[] p = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', KeyEvent.VK_BACK_SPACE};
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
    }//GEN-LAST:event_jTextFieldNumeroOperacionKeyTyped

    private void jButtonImprimirTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirTicketActionPerformed
        if (!jTextFieldNumeroOperacion.getText().isEmpty() && !jTextFieldCliente.getText().equals("") && !jTextFieldDomicilio.getText().isEmpty() && !jTextFieldTelefonoTicket.getText().isEmpty() && !jTextAreaDetalleTicket.getText().isEmpty() && !jTextDNI.getText().isEmpty() && !jTextFieldTotalTicket.getText().isEmpty() && !jTextFieldAbonaCliente.getText().isEmpty()) {
            if (jLabelNumeroOperacion.getText().equals("(*) N° OPERACIÓN")) {
                int i = JOptionPane.showConfirmDialog(null, "Confirmar Ticket Venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    mp.setIdcliente(contr_mp.ObtenerIDCliente(jTextDNI.getText()));
                    mp.setNroOperacion(jTextFieldNumeroOperacion.getText());
                    mp.setImporte(Float.parseFloat(jTextFieldTotalTicket.getText()));
                    mp.setDescripcion(jTextAreaDetalleTicket.getText());
                    mp.setAbonoCliente(Float.parseFloat(jTextFieldAbonaCliente.getText()));
                    if (contr_mp.InsertarDatosMercadoPago(mp)) {
                        JOptionPane.showMessageDialog(null, "Ticket Imprimido");
                        listaventa = new vListas_Ventas();
                        vMenuPrincipal.jDesktopPane1.add(listaventa);
                        listaventa.setVisible(true);
                        listaventa.toFront();
                        reporte.CargarFactura(jTextFieldNumeroOperacion.getText(), jTextDNI.getText(), jTextFieldCliente.getText(), jTextFieldDomicilio.getText(), jTextFieldTelefonoTicket.getText(), jTextAreaDetalleTicket.getText(), /*jTextAreaSubTotal.getText(),*/ jTextFieldTotalTicket.getText(), jTextFieldAbonaCliente.getText());
                        dispose();
                    }
                } else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            } else {
                reporte.CargarFactura(jTextFieldNumeroOperacion.getText(), jTextDNI.getText(), jTextFieldCliente.getText(), jTextFieldDomicilio.getText(), jTextFieldTelefonoTicket.getText(), jTextAreaDetalleTicket.getText(), /*jTextAreaSubTotal.getText(),*/ jTextFieldTotalTicket.getText(), jTextFieldAbonaCliente.getText());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios.");
        }
    }//GEN-LAST:event_jButtonImprimirTicketActionPerformed

    private void jButtonCancelarTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarTicketActionPerformed
        if (jButtonModificar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                lista = new vListas_ClientesMercadoPago();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldCliente.getText().equals("") || !jTextFieldDomicilio.getText().isEmpty() || !jTextFieldTelefonoTicket.getText().isEmpty() || !jTextAreaDetalleTicket.getText().isEmpty() || !jTextDNI.getText().isEmpty() || !jTextFieldTotalTicket.getText().isEmpty() || !jTextFieldAbonaCliente.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                //listaventa = new vListas_Ventas();
                //vMenuPrincipal.jDesktopPane1.add(listaventa);
                //listaventa.setVisible(true);
                //listaventa.toFront();
                //LimpiarSeleccionVenta();
                //LimpiarSeleccionDetalleVenta();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
            //listaventa = new vListas_Ventas();
            //vMenuPrincipal.jDesktopPane1.add(listaventa);
            //listaventa.setVisible(true);
            //listaventa.toFront();
        }
    }//GEN-LAST:event_jButtonCancelarTicketActionPerformed

    private void jButtonAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarClienteActionPerformed
        if (jTableClientes.getRowCount() != 0) {
            int i = jTableClientes.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                jTextDNI.setText(jTableClientes.getValueAt(i, 0).toString());
                jTextFieldCliente.setText(jTableClientes.getValueAt(i, 1).toString() + " " + jTableClientes.getValueAt(i, 2).toString());
                jTextFieldDomicilio.setText(jTableClientes.getValueAt(i, 3).toString());
                jTextFieldTelefonoTicket.setText(jTableClientes.getValueAt(i, 4).toString());
                vSeleccionarCliente.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos clientes todavia");
        }
    }//GEN-LAST:event_jButtonAgregarClienteActionPerformed

    private void jButtonCancelarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarClienteActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarCliente.dispose();
        } else {
            vSeleccionarCliente.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_jButtonCancelarClienteActionPerformed

    private void jButtonBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarClientesActionPerformed
        BuscarClientes();
    }//GEN-LAST:event_jButtonBuscarClientesActionPerformed

    private void vSeleccionarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarClienteMouseClicked
        LimpiarSeleccionClientes();
        //MostrarCliente();
        //jTextFieldNombre.setText("");
        //jTextFieldDNI.setText("");
        //jTextFieldTelefono.setText("");
    }//GEN-LAST:event_vSeleccionarClienteMouseClicked

    private void vSeleccionarClienteWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarClienteWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarCliente.dispose();
        } else {
            vSeleccionarCliente.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarClienteWindowClosing

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        if (!jTextFieldNumeroOperacion.getText().isEmpty() && !jTextFieldCliente.getText().equals("") && !jTextFieldDomicilio.getText().isEmpty() && !jTextFieldTelefonoTicket.getText().isEmpty() && !jTextAreaDetalleTicket.getText().isEmpty() && !jTextDNI.getText().isEmpty() && !jTextFieldTotalTicket.getText().isEmpty() && !jTextFieldAbonaCliente.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                if (jLabelNumeroOperacion.getText().equals("(*) N° OPERACIÓN")) {
                    mp.setIdcliente(contr_mp.ObtenerIDCliente(jTextDNI.getText()));
                    mp.setNroOperacion(jTextFieldNumeroOperacion.getText());
                    mp.setImporte(Float.parseFloat(jTextFieldTotalTicket.getText()));
                    mp.setDescripcion(jTextAreaDetalleTicket.getText());
                    mp.setAbonoCliente(Float.parseFloat(jTextFieldAbonaCliente.getText()));
                    mp.setIdmercadopago(Integer.parseInt(id));
                    if (contr_mp.EditarDatosMercadoPago(mp)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        //reporte.CargarFactura(jTextFieldNumeroOperacion.getText(), jTextDNI.getText(), jTextFieldCliente.getText(), jTextFieldDomicilio.getText(), jTextFieldTelefonoTicket.getText(), jTextAreaDetalleTicket.getText(), /*jTextAreaSubTotal.getText(),*/ jTextFieldTotalTicket.getText(), jTextFieldAbonaCliente.getText());

                        lista = new vListas_ClientesMercadoPago();
                        vMenuPrincipal.jDesktopPane1.add(lista);
                        lista.setVisible(true);
                        lista.toFront();
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Modificado");
                    //reporte.CargarFactura(jTextFieldNumeroOperacion.getText(), jTextDNI.getText(), jTextFieldCliente.getText(), jTextFieldDomicilio.getText(), jTextFieldTelefonoTicket.getText(), jTextAreaDetalleTicket.getText(), /*jTextAreaSubTotal.getText(),*/ jTextFieldTotalTicket.getText(), jTextFieldAbonaCliente.getText());

                    lista = new vListas_ClientesMercadoPago();
                    vMenuPrincipal.jDesktopPane1.add(lista);
                    lista.setVisible(true);
                    lista.toFront();
                    dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButtonModificar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                lista = new vListas_ClientesMercadoPago();
                vMenuPrincipal.jDesktopPane1.add(lista);
                lista.setVisible(true);
                lista.toFront();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldCliente.getText().equals("") || !jTextFieldDomicilio.getText().isEmpty() || !jTextFieldTelefonoTicket.getText().isEmpty() || !jTextAreaDetalleTicket.getText().isEmpty() || !jTextDNI.getText().isEmpty() || !jTextFieldTotalTicket.getText().isEmpty() || !jTextFieldAbonaCliente.getText().isEmpty()) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                //listaventa = new vListas_Ventas();
                //vMenuPrincipal.jDesktopPane1.add(listaventa);
                //listaventa.setVisible(true);
                //listaventa.toFront();
                //LimpiarSeleccionVenta();
                //LimpiarSeleccionDetalleVenta();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
            //listaventa = new vListas_Ventas();
            //vMenuPrincipal.jDesktopPane1.add(listaventa);
            //listaventa.setVisible(true);
            //listaventa.toFront();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextDNIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDNIKeyReleased
        ListaClientes();
    }//GEN-LAST:event_jTextDNIKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarCliente;
    private javax.swing.JButton jButtonBuscarClientes;
    private javax.swing.JButton jButtonCancelarCliente;
    public static javax.swing.JButton jButtonCancelarTicket;
    public static javax.swing.JButton jButtonImprimirTicket;
    public static javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonSeleccionarCliente;
    public static com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jLabelAbonaFactura;
    private javax.swing.JLabel jLabelBuscarDNI;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelDetalle;
    private javax.swing.JLabel jLabelDomicilio;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelNombre;
    public static javax.swing.JLabel jLabelNumeroOperacion;
    private javax.swing.JLabel jLabelSeleccionarCliente;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JLabel jLabelTelefonoFactura;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JList<String> jListClientes;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableClientes;
    public static javax.swing.JTextArea jTextAreaDetalleTicket;
    public static javax.swing.JTextField jTextDNI;
    public static javax.swing.JTextField jTextFieldAbonaCliente;
    private javax.swing.JTextField jTextFieldBuscarDNI;
    public static javax.swing.JTextField jTextFieldCliente;
    public static javax.swing.JTextField jTextFieldDomicilio;
    private javax.swing.JTextField jTextFieldNombre;
    public static javax.swing.JTextField jTextFieldNumeroOperacion;
    private javax.swing.JTextField jTextFieldTelefono;
    public static javax.swing.JTextField jTextFieldTelefonoTicket;
    public static javax.swing.JTextField jTextFieldTotalTicket;
    private javax.swing.JDialog vSeleccionarCliente;
    // End of variables declaration//GEN-END:variables
}
