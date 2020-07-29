package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Productos;
import Controlador.control_existencias;
import Modelo.Productos;
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
public final class vGestion_Productos extends javax.swing.JInternalFrame {

    Sentencias_sql tabla = new Sentencias_sql();
    control_existencias conexis = new control_existencias();
    control_Productos producto = new control_Productos();
    Productos p = new Productos();
    String id, fecha, nombre, desc;
    Timestamp fechaseleccionada;
    DefaultTableModel datos, recetas;
    DefaultListModel list;
    ArrayList<String> listprod;
    public vGestion_Productos() {
        initComponents();
        AutoCompletarCategoriaProducto();
        Mostrar();
        EliminarItemsVacios();
        jList2.setVisible(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    jButton1.setEnabled(false);
                    jButton2.setText("Cancelar");
                    jButton3.setText("Modificar");
                    jButton4.setEnabled(false);
                    int fila = jTable1.rowAtPoint(e.getPoint());

                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    id = jTable1.getValueAt(fila, 0).toString();
                    jComboBox1.setSelectedItem(jTable1.getValueAt(fila, 2).toString());
                    jTextField2.setText(jTable1.getValueAt(fila, 3).toString());
                    jTextField3.setText(jTable1.getValueAt(fila, 4).toString());
                    fecha = jTable1.getValueAt(fila, 5).toString();
                    if (!fecha.equals("-")) {
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jDateChooser1.setDate(fechaseleccionada);
                    }
                }
            }
        });
    }

    public void Limpiar() {
        jComboBox1.setSelectedItem("(*) Seleccionar Tipo Categoria..");
        jTextField2.setText("");
        jTextField3.setText("");
        ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).setText("");
    }

    public void AutoCompletarCategoriaProducto() {
        Object[] categoriaproducto = conexis.combox("categoriasproductos", "descripcion");
        for (Object row : categoriaproducto) {
            jComboBox1.addItem((String) row);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i) == null) {
                jComboBox1.removeItemAt(i);
            }
        }
    }

    public void ReemplazarNulos() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (jTable1.getValueAt(i, 5).equals("00/00/0000 12:00")) {
                    jTable1.setValueAt("-", i, 5);
                }
            }
        }
    }

    public void Mostrar() {
        String[] columnas = {"IDPROD", "IDCATEGORIAPRODUCTO", "CATEGORIA", "DESCRIPCION", "PRECIO VENTA", "FECHA REGISTRO"};
        Object[][] dato = producto.MostrarDatos();
        datos = new DefaultTableModel(dato, columnas);
        jTable1.setModel(datos);
        EliminarFilasVacias();
        ReemplazarNulos();
        //AjustarTamañoFilas();
        ocultar_columnas();
    }
    
    public void ListaProductos(){
        listprod = conexis.list("productos", "descripcion", jTextField1.getText());
        String substr = jTextField1.getText().toLowerCase();
        list = new DefaultListModel();
        jList2.setModel(list);
        list.removeAllElements();
        if(listprod.size() > 0){
            for(int i=0; i < listprod.size(); i++){
                if(listprod.get(i) == null){
                    listprod.remove(i);
                } else {
                    String sublist = listprod.get(i).toLowerCase();
                    if(sublist.contains(substr)){
                        list.addElement(listprod.get(i));
                        jList2.setVisible(true);
                        if(jTextField1.getText().isEmpty()){
                            jList2.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void AjustarTamañoFilas() {
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int categoria = (int) font.getStringBounds(jTable1.getValueAt(i, 2).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int descrip = (int) font.getStringBounds(jTable1.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int precio = (int) font.getStringBounds(jTable1.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int fechareg = (int) font.getStringBounds(jTable1.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (categoria > jTable1.getColumnModel().getColumn(2).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(2).setPreferredWidth(categoria);
                }
                if (descrip > jTable1.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(3).setPreferredWidth(descrip);
                }
                if (precio > jTable1.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(4).setPreferredWidth(precio);
                }
                if (fechareg > jTable1.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTable1.getColumnModel().getColumn(5).setPreferredWidth(fechareg);
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
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    public void LimpiarSeleccion() {
        jTable1.clearSelection();
        jTable1.getSelectionModel().clearSelection();
    }

    public void MostrarRecetasActivas() {
        tabla.descripcion = desc;
        recetas = tabla.ConsultarInsumos();
        vGestion_Recetas.jTable2.setModel(recetas);
        ocultar_columnasreceta();
    }

    public void ocultar_columnasreceta() {
        vGestion_Recetas.jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        vGestion_Recetas.jTable2.getColumnModel().getColumn(0).setMinWidth(0);
        vGestion_Recetas.jTable2.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public boolean VerificarProductosRepetidos() {
        boolean repetido = false;
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (jTextField2.getText().equals(jTable1.getValueAt(i, 3).toString())) {
                    repetido = true;
                }
            }
        }
        return repetido;
    }

    public boolean VerificarProductosRepetidosModificar() {
        boolean repetido = true;
        if (jTable1.getRowCount() != 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (!jComboBox1.getSelectedItem().equals(jTable1.getValueAt(i, 2).toString()) || !jTextField2.getText().equals(jTable1.getValueAt(i, 3).toString()) || !jTextField3.getText().equals(jTable1.getValueAt(i, 4).toString()) || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().equals(jTable1.getValueAt(i, 5).toString())) {
                    repetido = false;
                }
            }
        }
        return repetido;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jList2 = new javax.swing.JList<>();
        jButton5 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Productos");
        setAutoscrolls(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);
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

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Nombre Producto:");
        jLayeredPane1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, -1, 30));

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        jLayeredPane1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 229, 30));

        jList2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jLayeredPane1.add(jList2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 230, -1));

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 92, -1));

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Tipo..." }));
        jComboBox1.setOpaque(false);
        jLayeredPane1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 170, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("(*) Nombre");
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 108, 22));

        jTextField2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        jLayeredPane1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 170, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setText("Fecha:");
        jLayeredPane1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 210, 104, 22));

        jDateChooser1.setDateFormatString("dd/MM/yyyy HH:mm");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jDateChooser1.setOpaque(false);
        jLayeredPane1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 240, 170, 30));

        jScrollPane1.setOpaque(false);

        jTable1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTable1);

        jLayeredPane1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 289, 1129, 241));

        jButton3.setBackground(new java.awt.Color(237, 124, 61));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 548, 101, -1));

        jButton4.setBackground(new java.awt.Color(252, 240, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Abrir Receta");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1014, 548, -1, -1));

        jButton1.setBackground(new java.awt.Color(252, 249, 57));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 548, 101, -1));

        jButton2.setBackground(new java.awt.Color(252, 249, 57));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 548, 101, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setText("Precio Venta:");
        jLayeredPane1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, 94, 19));

        jTextField3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        jLayeredPane1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 160, 170, 30));

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 94, Short.MAX_VALUE)
        );

        jLayeredPane1.add(jLayeredPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 390, 120));
        jLayeredPane3.getAccessibleContext().setAccessibleName("Buscar Por:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setText("(*) Tipo de Categoría:");
        jLayeredPane1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 140, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1152, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jComboBox1.getSelectedItem().equals("(*) Seleccionar Tipo Categoria..") && !jTextField2.getText().trim().equals("")) {
            if (VerificarProductosRepetidos() == false) {
                if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                    p.setIdcategoriaproducto(producto.ObtenerIDCategoriaProducto((String) jComboBox1.getSelectedItem()));
                    p.setDescripcion(jTextField2.getText());
                    if (jTextField3.getText().trim().length() == 0) {
                        p.setPrecioventa((float) 0.0);
                    } else {
                        p.setPrecioventa(Float.parseFloat(jTextField3.getText()));
                    }
                    if (producto.InsertarProductos(p)) {
                        JOptionPane.showMessageDialog(null, "Nuevo Producto agregado");
                        Mostrar();
                        Limpiar();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Producto ya Agregado!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jButton2.getText().equals("Modificar")) {
            int s = jTable1.getSelectedRow();
            if (s == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                jButton1.setEnabled(false);
                jButton2.setText("Cancelar");
                jButton3.setText("Modificar");
                jButton4.setEnabled(false);
                fecha = jTable1.getValueAt(s, 5).toString();
                if (!fecha.equals("-")) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    try {
                        fechaseleccionada = new java.sql.Timestamp(df.parse(fecha).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(vListas_Compras.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jDateChooser1.setDate(fechaseleccionada);
                }
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                id = jTable1.getValueAt(s, 0).toString();
                jComboBox1.setSelectedItem(jTable1.getValueAt(s, 2).toString());
                jTextField2.setText(jTable1.getValueAt(s, 3).toString());
                jTextField3.setText(jTable1.getValueAt(s, 4).toString());
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jButton1.setEnabled(true);
                jButton4.setEnabled(true);
                jButton2.setText("Modificar");
                jButton3.setText("Eliminar");
                Limpiar();
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jButton3.getText().equals("Eliminar")) {
            int seleccionado = jTable1.getSelectedRow();
            if (seleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Esta seguro de Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    p.setIdproducto(Integer.parseInt((String) jTable1.getValueAt(seleccionado, 0)));
                    if (producto.EliminarProductos(p)) {
                        JOptionPane.showMessageDialog(null, "Eliminado");
                        Mostrar();
                    }
                } else {
                    LimpiarSeleccion();
                }
            }
        } else {
            if (!jComboBox1.getSelectedItem().equals("(*) Seleccionar Tipo Categoria..") && !jTextField2.getText().trim().equals("")) {
                if (VerificarProductosRepetidosModificar() == false) {
                    if (jDateChooser1.getDateEditor().getUiComponent().getForeground() != Color.RED) {
                        int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (i == 0) {
                            p.setIdcategoriaproducto(producto.ObtenerIDCategoriaProducto((String) jComboBox1.getSelectedItem()));
                            p.setDescripcion(jTextField2.getText());
                            if (jTextField3.getText().trim().length() == 0) {
                                p.setPrecioventa((float) 0.0);
                            } else {
                                p.setPrecioventa(Float.parseFloat(jTextField3.getText()));
                            }
                            p.setIdproducto(Integer.parseInt(id));
                            if (producto.EditarProductos(p)) {
                                JOptionPane.showMessageDialog(null, "Modificado");
                                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                Mostrar();
                                Limpiar();
                                LimpiarSeleccion();
                                jButton1.setEnabled(true);
                                jButton2.setText("Modificar");
                                jButton3.setText("Eliminar");
                                jButton4.setEnabled(true);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Formato de Fecha incorrecto!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Este producto ya fue agregado!");
                    JOptionPane.showMessageDialog(null, "Elimine o modifique el producto con los mismos datos.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int seleccionado = jTable1.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            vGestion_Recetas receta = new vGestion_Recetas();
            desc = jTable1.getValueAt(seleccionado, 3).toString();
            MostrarRecetasActivas();
            vGestion_Recetas.jLabel2.setText(jTable1.getValueAt(seleccionado, 3).toString());
            vMenuPrincipal.jDesktopPane1.add(receta);
            receta.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (jButton2.getText().equals("Cancelar")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextField2.getText().trim().isEmpty() || !((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText().isEmpty() || !jComboBox1.getSelectedItem().equals("(*) Seleccionar Tipo Categoria..")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        char[] p = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', KeyEvent.VK_BACK_SPACE};
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
    }//GEN-LAST:event_jTextField3KeyTyped

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        Mostrar();
        jTextField1.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (!jTextField1.getText().isEmpty()) {
            String[] columnas = {"IDPROD", "IDCATEGORIAPRODUCTO", "CATEGORIA", "DESCRIPCION", "PRECIO VENTA", "FECHA REGISTRO"};
            Object[][] dato = producto.MostrarDatosBusqueda(jTextField1.getText());
            if (dato.length != 0) {
                datos = new DefaultTableModel(dato, columnas);
                jTable1.setModel(datos);
                EliminarFilasVacias();
                ReemplazarNulos();
                //AjustarTamañoFilas();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        ListaProductos();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        int i = jList2.getSelectedIndex();
        if(i != -1){
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

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextField1KeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
