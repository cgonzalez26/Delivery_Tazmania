package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Recetas;
import Controlador.control_existencias;
import Modelo.Recetas;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Recetas extends javax.swing.JInternalFrame {

    control_existencias existencia = new control_existencias();
    control_Recetas receta = new control_Recetas();
    Recetas r = new Recetas();
    Sentencias_sql sql = new Sentencias_sql();
    DefaultTableModel modelo1, modelo2, modelo3;
    String id;
    ArrayList<String> listreceta;
    DefaultListModel list;

    public vGestion_Recetas() {
        initComponents();
        MostrarTablaInsumos();
        MostrarRecetas();
        MostrarInsumos();

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    jTextField1.setText(jTable1.getValueAt(fila, 0).toString());
                }
            }
        });

        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable2.rowAtPoint(e.getPoint());
                    id = jTable2.getValueAt(fila, 0).toString();
                    jTextField1.setText(jTable2.getValueAt(fila, 1).toString());
                    jButton1.setText("Modificar");
                    jButton5.setText("Cancelar");
                }
            }
        });
    }

    public void MostrarInsumos() {
        String[] columnas = {"INSUMOS"};
        Object[][] datostabla = receta.MostrarInsumos();
        modelo3 = new DefaultTableModel(datostabla, columnas);
        jTable3.setModel(modelo3);
        EliminarFilasVaciasInsumos();
        //AjustarTamañoFilas();
    }

    public void ListaInsumos() {
        listreceta = existencia.list("insumos", "descripcion", jTextField1.getText());
        String substr = jTextField1.getText().toLowerCase();
        list = new DefaultListModel();
        jList2.setModel(list);
        list.removeAllElements();
        for (int i = 0; i < listreceta.size(); i++) {
            if (listreceta.get(i) == null) {
                listreceta.remove(i);
            } else {
                String sublist = listreceta.get(i).toLowerCase();
                if (sublist.contains(substr)) {
                    list.addElement(listreceta.get(i));
                    jList2.setVisible(true);
                    if (jTextField1.getText().isEmpty()) {
                        jList2.setVisible(false);
                    }
                }
            }
        }
    }

    public void AjustarTamañoFilas() {
        if (jTable3.getRowCount() != 0) {
            for (int i = 0; i < jTable3.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int insumo = (int) font.getStringBounds(jTable3.getValueAt(i, 0).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (insumo > jTable3.getColumnModel().getColumn(0).getPreferredWidth()) {
                    jTable3.getColumnModel().getColumn(0).setPreferredWidth(insumo);
                }
            }
        }
    }

    public void MostrarTablaInsumos() {
        modelo1 = new DefaultTableModel();
        modelo1.addColumn("INSUMOS A AGREGAR");
        jTable1.setModel(modelo1);
    }

    public void AgregarNuevoInsumo() {
        if (!jTextField1.getText().equals("") && VerificarDuplicados() == false) {
            String datos[] = {jTextField1.getText()};
            modelo1.addRow(datos);
            jTextField1.setText("");
        } else if (jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
        } else if (VerificarDuplicados() == true) {
            JOptionPane.showMessageDialog(null, "Insumo ya agregado!");
        }
    }

    public boolean VerificarDuplicados() {
        boolean repetido = false;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            repetido = jTable1.getValueAt(i, 0).equals(jTextField1.getText());
        }
        return repetido;
    }

    public void ModificarInsumoAgregado() {
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            jTextField1.setText(jTable1.getValueAt(fila, 0).toString());
            modelo1.removeRow(fila);
        }
    }

    public void EliminarInsumo() {
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            modelo1.removeRow(fila);
        }
    }

    public void MostrarRecetas() {
        sql.descripcion = jLabel2.getText();
        modelo2 = sql.ConsultarInsumos();
        jTable2.setModel(modelo2);
        EliminarFilasVacias();
        ocultar_columnareceta();
    }

    public void EliminarFilasVacias() {
        if (jTable2.getRowCount() != 0) {
            for (int columna = 0; columna < jTable2.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTable2.getRowCount(); fila++) {
                    if (jTable2.getValueAt(fila, columna) == null) {
                        modelo2.removeRow(fila);
                    }
                }
            }
        }
    }

    public void EliminarFilasVaciasInsumos() {
        if (jTable3.getRowCount() != 0) {
            int filas = jTable3.getRowCount();
            filas--;
            for (int fila = filas; fila >= 0; fila--) {
                if (jTable3.getValueAt(fila, 0) == null) {
                    modelo3.removeRow(fila);
                }
            }
        }
    }

    public void ocultar_columnareceta() {
        jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(0).setMinWidth(0);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void LimpiarSeleccionInsumosAgregados() {
        jTable2.clearSelection();
        jTable2.getSelectionModel().clearSelection();
    }

    public void LimpiarSeleccionInsumosaAgregar() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vSeleccionarInsumo = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jList2 = new javax.swing.JList<>();
        jButton7 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        vSeleccionarInsumo.setTitle("Seleccionar Insumo");
        java.awt.Image icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg")).getImage();
        vSeleccionarInsumo.setIconImage(icono);
        vSeleccionarInsumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSeleccionarInsumoMouseClicked(evt);
            }
        });
        vSeleccionarInsumo.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vSeleccionarInsumoWindowClosing(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton8.setText("Aceptar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton9.setText("Cancelar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Nombre Insumo");

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton10.setText("Buscar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel3)
                        .addGap(0, 92, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vSeleccionarInsumoLayout = new javax.swing.GroupLayout(vSeleccionarInsumo.getContentPane());
        vSeleccionarInsumo.getContentPane().setLayout(vSeleccionarInsumoLayout);
        vSeleccionarInsumoLayout.setHorizontalGroup(
            vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vSeleccionarInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addGroup(vSeleccionarInsumoLayout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vSeleccionarInsumoLayout.setVerticalGroup(
            vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vSeleccionarInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vSeleccionarInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Recetas");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(1067, 645));
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

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Nombre Producto:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 5, 140, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 5, 190, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("(*) Seleccionar Insumo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 28, -1, 30));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 219, 31));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setValueIsAdjusting(true);
        jList2.setVisibleRowCount(0);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        getContentPane().add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 219, -1));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 36, 31));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Agregar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jButton4)
                .addGap(68, 68, 68)
                .addComponent(jButton3)
                .addGap(74, 74, 74)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 490));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Modificar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setText("Eliminar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(74, 74, 74)
                .addComponent(jButton5)
                .addGap(76, 76, 76)
                .addComponent(jButton6)
                .addGap(157, 157, 157))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, -1, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AgregarNuevoInsumo();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ModificarInsumoAgregado();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EliminarInsumo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jButton1.getText().equals("Agregar")) {
            if (jTable1.getRowCount() != 0) {
                if (receta.InsertarRecetas()) {
                    JOptionPane.showMessageDialog(null, "Agregado");
                    modelo1.setRowCount(0);
                    MostrarRecetas();
                } else {
                    JOptionPane.showMessageDialog(null, "No agregado");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes agregar al menos un insumo en la tabla Insumos a Agregar");
            }
        } else {
            if (!jTextField1.getText().trim().equals("")) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    r.setIdinsumo(receta.ObtenerIDInsumo(jTextField1.getText()));
                    r.setIdreceta(Integer.parseInt(id));
                    if (receta.EditarReceta(r)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        jButton1.setText("Agregar");
                        jButton5.setText("Modificar");
                        jTextField1.setText("");
                        MostrarRecetas();
                        LimpiarSeleccionInsumosAgregados();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar el campo obligatorio");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (jButton5.getText().equals("Modificar")) {
            int j = jTable2.getSelectedRow();
            if (j == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                id = jTable2.getValueAt(j, 0).toString();
                jTextField1.setText(jTable2.getValueAt(j, 1).toString());
                jButton1.setText("Modificar");
                jButton5.setText("Cancelar");
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                jButton1.setText("Agregar");
                jButton5.setText("Modificar");
                jTextField1.setText("");
                LimpiarSeleccionInsumosAgregados();
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int seleccionado = jTable2.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?", "confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                r.setIdreceta(Integer.parseInt(id = jTable2.getValueAt(seleccionado, 0).toString()));
                if (receta.EliminarReceta(r)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    MostrarRecetas();
                    EliminarFilasVacias();
                }
            } else {
                LimpiarSeleccionInsumosAgregados();
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButton5.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vGestion_Productos productos = new vGestion_Productos();
                vMenuPrincipal.jDesktopPane1.add(productos);
                productos.setVisible(true);
                productos.toFront();
                this.dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (jTable1.getRowCount() != 0) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vGestion_Productos productos = new vGestion_Productos();
                vMenuPrincipal.jDesktopPane1.add(productos);
                productos.setVisible(true);
                productos.toFront();
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextField1.getText().equals("")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                vGestion_Productos productos = new vGestion_Productos();
                vMenuPrincipal.jDesktopPane1.add(productos);
                productos.setVisible(true);
                productos.toFront();
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            vGestion_Productos productos = new vGestion_Productos();
            vMenuPrincipal.jDesktopPane1.add(productos);
            productos.setVisible(true);
            productos.toFront();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        if (!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
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

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (jTable3.getRowCount() != 0) {
            int i = jTable3.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                vSeleccionarInsumo.dispose();
                jTextField1.setText(jTable3.getValueAt(i, 0).toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han agregado dichos insumos todavia");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarInsumo.dispose();
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void vSeleccionarInsumoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoWindowClosing
        int i = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            vSeleccionarInsumo.dispose();
            jTextField1.setText("");
        } else {
            vSeleccionarInsumo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_vSeleccionarInsumoWindowClosing

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        vSeleccionarInsumo.setSize(330, 612);
        vSeleccionarInsumo.setLocationRelativeTo(this);
        vSeleccionarInsumo.setModal(true);
        vSeleccionarInsumo.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccionInsumosaAgregar();
        LimpiarSeleccionInsumosAgregados();
    }//GEN-LAST:event_formMouseClicked

    private void vSeleccionarInsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSeleccionarInsumoMouseClicked
        jTable3.clearSelection();
        jTable3.getSelectionModel().clearSelection();
        MostrarInsumos();
        jTextField2.setText("");
    }//GEN-LAST:event_vSeleccionarInsumoMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (!jTextField2.getText().isEmpty()) {
            String[] columnas = {"INSUMOS"};
            Object[][] datostabla = receta.MostrarInsumoBuscado(jTextField2.getText());
            if (datostabla.length != 0) {
                modelo3 = new DefaultTableModel(datostabla, columnas);
                jTable3.setModel(modelo3);
                EliminarFilasVaciasInsumos();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        ListaInsumos();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        if (i != -1) {
            jTextField1.setText(jList2.getSelectedValue());
            jList2.setVisible(false);
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JDialog vSeleccionarInsumo;
    // End of variables declaration//GEN-END:variables
}
