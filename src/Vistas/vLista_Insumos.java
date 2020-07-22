package Vistas;

import Controlador.ColorearFilas;
import Controlador.control_Insumos;
import Controlador.control_existencias;
import Modelo.Insumos;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Colo-PC
 */
public final class vLista_Insumos extends javax.swing.JInternalFrame {

    String idinsumo, fecha;
    Object[][] datostabla, datostablati;
    vGestion_Insumos tipo = null;
    control_Insumos insumo = new control_Insumos();
    control_existencias combo = new control_existencias();
    ColorearFilas color;
    Object[] tipoinsumo;
    Insumos ins = new Insumos();
    DefaultTableModel datos;
    Timestamp fecha_insumo;

    public vLista_Insumos() {
        initComponents();
        Mostrar();
        ComboTipoInsumo();
        EliminarItemsVacios();
        

        jTabla_Insumos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTabla_Insumos.rowAtPoint(e.getPoint());
                    tipo = new vGestion_Insumos();
                    vMenuPrincipal.jDesktopPane1.add(tipo);
                    tipo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    tipo.setVisible(true);
                    vGestion_Insumos.jBotonModif_Insumos.setEnabled(true);
                    vGestion_Insumos.jBotonAgregar_Insumos.setText("Cancelar");
                    idinsumo = (jTabla_Insumos.getValueAt(fila, 0).toString());
                    vGestion_Insumos.jCBTipo_Insumos.setSelectedItem(jTabla_Insumos.getValueAt(fila, 3).toString());
                    vGestion_Insumos.jTextField1.setText(jTabla_Insumos.getValueAt(fila, 4).toString());
                    vGestion_Insumos.jTextDesc_Insumos.setText(jTabla_Insumos.getValueAt(fila, 5).toString());
                    vGestion_Insumos.jTextPrecio_Insumos.setText(jTabla_Insumos.getValueAt(fila, 6).toString());
                    vGestion_Insumos.jTextStock_Insumos.setText(jTabla_Insumos.getValueAt(fila, 7).toString());
                    fecha = jTabla_Insumos.getValueAt(fila, 8).toString();
                    if (!fecha.equals("-")) {
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            fecha_insumo = new java.sql.Timestamp(df.parse(fecha).getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(vLista_Insumos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        vGestion_Insumos.jDateChooser1.setDate(fecha_insumo);
                    }
                    tipo.id = idinsumo;
                    dispose();
                    LimpiarSeleccion();
                }
            }
        });
    }

    public void ComboTipoInsumo() {
        tipoinsumo = combo.combox("tiposinsumos", "descripcion");
        for (Object tiposinsumos : tipoinsumo) {
            jComboBox1.addItem((String) tiposinsumos);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i) == null) {
                jComboBox1.removeItemAt(i);
            }
        }
    }

    public void LimpiarSeleccion() {
        jTabla_Insumos.clearSelection();
        jTabla_Insumos.getSelectionModel().clearSelection();
    }

    public void ReemplazarNulos() {
        if (jTabla_Insumos.getRowCount() != 0) {
            for (int i = 0; i < jTabla_Insumos.getRowCount(); i++) {
                if (jTabla_Insumos.getValueAt(i, 8).equals("00/00/0000 12:00") || jTabla_Insumos.getValueAt(i, 8).equals("00/00/0000 12:00:00") || jTabla_Insumos.getValueAt(i, 8).equals("00/00/0000 00:00:00")) {
                    jTabla_Insumos.setValueAt("-", i, 8);
                }
            }
        }
    }

    public void EliminarSegundos() {
        if (jTabla_Insumos.getRowCount() != 0) {
            for (int i = 0; i < jTabla_Insumos.getRowCount(); i++) {
                String cantidad = jTabla_Insumos.getValueAt(i, 8).toString(), substring = "";
                if (cantidad.length() == 19) {
                    substring = cantidad.substring(0, 16);
                    jTabla_Insumos.setValueAt(substring, i, 8);
                }
            }
        }
    }

    public void Mostrar() {
        String[] columnas = {"IDINSUMO", "IDTIPOINSUMO", "IDPROVEEDOR", "TIPO INSUMO", "PROVEEDOR", "DESCRIPCION", "PRECIO", "STOCK", "FECHA"};
        datostabla = insumo.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Insumos.setModel(datos);
        EliminarFilasVacias();
        ReemplazarNulos();
        PintarPocoStock();
        AjustarTamañoFilas();
        ocultar_columnas();
    }

    public void AjustarTamañoFilas() {
        if (jTabla_Insumos.getRowCount() != 0) {
            for (int i = 0; i < jTabla_Insumos.getRowCount(); i++) {
                Font font = new Font("Segoe UI Semibold", 0, 13);
                int tipoinsu = (int) font.getStringBounds(jTabla_Insumos.getValueAt(i, 3).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int prov = (int) font.getStringBounds(jTabla_Insumos.getValueAt(i, 4).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int desc = (int) font.getStringBounds(jTabla_Insumos.getValueAt(i, 5).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int precio = (int) font.getStringBounds(jTabla_Insumos.getValueAt(i, 6).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int stock = (int) font.getStringBounds(jTabla_Insumos.getValueAt(i, 7).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                int date = (int) font.getStringBounds(jTabla_Insumos.getValueAt(i, 8).toString(), new FontRenderContext(font.getTransform(), false, false)).getBounds().getWidth();
                if (tipoinsu > jTabla_Insumos.getColumnModel().getColumn(3).getPreferredWidth()) {
                    jTabla_Insumos.getColumnModel().getColumn(3).setPreferredWidth(tipoinsu);
                }
                if (prov > jTabla_Insumos.getColumnModel().getColumn(4).getPreferredWidth()) {
                    jTabla_Insumos.getColumnModel().getColumn(4).setPreferredWidth(prov);
                }
                if (desc > jTabla_Insumos.getColumnModel().getColumn(5).getPreferredWidth()) {
                    jTabla_Insumos.getColumnModel().getColumn(5).setPreferredWidth(desc);
                }
                if (precio > jTabla_Insumos.getColumnModel().getColumn(6).getPreferredWidth()) {
                    jTabla_Insumos.getColumnModel().getColumn(6).setPreferredWidth(precio);
                }
                if (stock > jTabla_Insumos.getColumnModel().getColumn(7).getPreferredWidth()) {
                    jTabla_Insumos.getColumnModel().getColumn(7).setPreferredWidth(stock);
                }
                if (date > jTabla_Insumos.getColumnModel().getColumn(8).getPreferredWidth()) {
                    jTabla_Insumos.getColumnModel().getColumn(8).setPreferredWidth(date);
                }
            }
        }
    }

    public void ocultar_columnas() {
        jTabla_Insumos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(0).setMinWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(1).setMaxWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(1).setMinWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(2).setMaxWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(2).setMinWidth(0);
        jTabla_Insumos.getColumnModel().getColumn(2).setPreferredWidth(0);
    }

    public void EliminarFilasVacias() {
        if (jTabla_Insumos.getRowCount() != 0) {
            for (int columna = 0; columna < jTabla_Insumos.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTabla_Insumos.getRowCount(); fila++) {
                    if (jTabla_Insumos.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }
    
    public void PintarPocoStock(){
        /*if(jTabla_Insumos.getRowCount() != 0){
            for(int fila =0; fila < jTabla_Insumos.getRowCount(); fila++){
                float row = Float.parseFloat(jTabla_Insumos.getValueAt(fila, 7).toString());
                if(row <= 10){
                    Graphics g = jTabla_Insumos.getGraphics();
                    
                }
            }
        }*/
        color = new ColorearFilas(7);
        jTabla_Insumos.getColumnModel().getColumn(7).setCellRenderer(color);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla_Insumos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Insumos");
        setAutoscrolls(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.jpg"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTabla_Insumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTabla_Insumos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTabla_Insumos);

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Nuevo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar y/o Ordenar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel1.setText("Ordenar Por");

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel2.setText("Insumo");

        jTextField1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(284, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(265, 265, 265))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1072, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tipo = new vGestion_Insumos();
        vMenuPrincipal.jDesktopPane1.add(tipo);
        tipo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int seleccionado = jTabla_Insumos.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                ins.setIdinsumo(Integer.parseInt(jTabla_Insumos.getValueAt(seleccionado, 0).toString()));
                if (insumo.EliminarInsumos(ins)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int seleccionado = jTabla_Insumos.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            tipo = new vGestion_Insumos();
            vMenuPrincipal.jDesktopPane1.add(tipo);
            tipo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            tipo.setVisible(true);
            vGestion_Insumos.jBotonModif_Insumos.setEnabled(true);
            vGestion_Insumos.jBotonAgregar_Insumos.setText("Cancelar");
            idinsumo = (jTabla_Insumos.getValueAt(seleccionado, 0).toString());
            vGestion_Insumos.jCBTipo_Insumos.setSelectedItem(jTabla_Insumos.getValueAt(seleccionado, 3).toString());
            vGestion_Insumos.jTextField1.setText(jTabla_Insumos.getValueAt(seleccionado, 4).toString());
            vGestion_Insumos.jTextDesc_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 5).toString());
            vGestion_Insumos.jTextPrecio_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 6).toString());
            vGestion_Insumos.jTextStock_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 7).toString());
            fecha = jTabla_Insumos.getValueAt(seleccionado, 8).toString();
            if (!fecha.equals("-")) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    fecha_insumo = new java.sql.Timestamp(df.parse(fecha).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(vLista_Insumos.class.getName()).log(Level.SEVERE, null, ex);
                }
                vGestion_Insumos.jDateChooser1.setDate(fecha_insumo);
            } else {
                ((JTextField) vGestion_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
            }
            tipo.id = idinsumo;
            dispose();
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (!jComboBox1.getSelectedItem().equals("Ninguno")) {
            String[] columnas = {"IDINSUMO", "IDTIPOINSUMO", "IDPROVEEDOR", "TIPO INSUMO", "PROVEEDOR", "DESCRIPCION", "PRECIO", "STOCK", "FECHA"};
            datostablati = insumo.OrdenarInsumos(jComboBox1.getSelectedItem().toString());
            datos = new DefaultTableModel(datostablati, columnas);
            jTabla_Insumos.setModel(datos);
            EliminarFilasVacias();
            EliminarSegundos();
            ReemplazarNulos();
            PintarPocoStock();
            AjustarTamañoFilas();
            ocultar_columnas();
        } else {
            Mostrar();
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        Mostrar();
        jComboBox1.setSelectedItem("Ninguno");
        jTextField1.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!jTextField1.getText().isEmpty()) {
            datostabla = insumo.MostrarDatosBusquedaInsumos(jTextField1.getText());
            if (datostabla.length != 0) {
                String[] columnas = {"IDINSUMO", "IDTIPOINSUMO", "IDPROVEEDOR", "TIPO INSUMO", "PROVEEDOR", "DESCRIPCION", "PRECIO", "STOCK", "FECHA"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_Insumos.setModel(datos);
                EliminarFilasVacias();
                ReemplazarNulos();
                PintarPocoStock();
                AjustarTamañoFilas();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Insumos;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
