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
        //EliminarItemsVacios();
        

        jTabla_Insumos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int seleccionado = jTabla_Insumos.rowAtPoint(e.getPoint());
                    tipo = new vGestion_Insumos();
                    vMenuPrincipal.jDesktopPane1.add(tipo);
                    tipo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    tipo.setVisible(true);
                    vGestion_Insumos.jBotonModif_Insumos.setEnabled(true);
                    vGestion_Insumos.jBotonAgregar_Insumos.setEnabled(false);
                    //vGestion_Insumos.jBotonAgregar_Insumos.setText("Cancelar");
                    idinsumo = (jTabla_Insumos.getValueAt(seleccionado, 0).toString());
                    
                    vGestion_Insumos.jTextDesc_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 3).toString());
                    vGestion_Insumos.jCBTipo_Insumos.setSelectedItem(jTabla_Insumos.getValueAt(seleccionado, 4).toString());
                    vGestion_Insumos.jTextFieldProveedor.setText(jTabla_Insumos.getValueAt(seleccionado, 5).toString());///prov
                    vGestion_Insumos.jCBUnidad_Medida.setSelectedItem(jTabla_Insumos.getValueAt(seleccionado, 6).toString());
                    vGestion_Insumos.jTextPrecio_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 7).toString());
                    vGestion_Insumos.jTextStock_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 8).toString());
                    //fecha = jTabla_Insumos.getValueAt(fila, 8).toString();
                    /*if (!fecha.equals("-")) {
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            fecha_insumo = new java.sql.Timestamp(df.parse(fecha).getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(vLista_Insumos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //vGestion_Insumos.jDateChooser1.setDate(fecha_insumo);
                    }*/
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
            jComboBox_TipoInsumo.addItem((String) tiposinsumos);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBox_TipoInsumo.getItemCount(); i++) {
            if (jComboBox_TipoInsumo.getItemAt(i) == null) {
                jComboBox_TipoInsumo.removeItemAt(i);
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
        String[] columnas = {"IDINSUMO", "IDTIPOINSUMO", "IDPROVEEDOR", "DESCRIPCION", "TIPO INSUMO", "PROVEEDOR", "UNIDAD MEDIDA", "PRECIO", "STOCK"};
        datostabla = insumo.MostrarDatos();
        datos = new DefaultTableModel(datostabla, columnas);
        jTabla_Insumos.setModel(datos);
        //EliminarFilasVacias();
        //ReemplazarNulos();
        PintarPocoStock();
        ocultar_columnas();
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
        color = new ColorearFilas(8);
        jTabla_Insumos.getColumnModel().getColumn(8).setCellRenderer(color);
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
        jButtonNuevo = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelTipoInsumoBuscar = new javax.swing.JLabel();
        jComboBox_TipoInsumo = new javax.swing.JComboBox<>();
        jLabelInsumoBuscar = new javax.swing.JLabel();
        jTextFieldInsumoBuscar = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 248, 177));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de Insumos");
        setAutoscrolls(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
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

        jButtonNuevo.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevo.setText("Nuevo");
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
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

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 177));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelTipoInsumoBuscar.setBackground(new java.awt.Color(255, 248, 177));
        jLabelTipoInsumoBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelTipoInsumoBuscar.setText("Tipo de Insumo:");

        jComboBox_TipoInsumo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBox_TipoInsumo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno" }));

        jLabelInsumoBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelInsumoBuscar.setText("Insumo:");

        jTextFieldInsumoBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldInsumoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldInsumoBuscarKeyTyped(evt);
            }
        });

        jButtonBuscar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInsumoBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox_TipoInsumo, 0, 270, Short.MAX_VALUE)
                    .addComponent(jLabelTipoInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelInsumoBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelTipoInsumoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_TipoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButtonBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(130, 130, 130)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(130, 130, 130)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(198, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1051, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        tipo = new vGestion_Insumos();
        vMenuPrincipal.jDesktopPane1.add(tipo);
        tipo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
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
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int seleccionado = jTabla_Insumos.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            tipo = new vGestion_Insumos();
            vMenuPrincipal.jDesktopPane1.add(tipo);
            tipo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            tipo.setVisible(true);
            vGestion_Insumos.jBotonModif_Insumos.setEnabled(true);
            vGestion_Insumos.jBotonAgregar_Insumos.setEnabled(false);
            //vGestion_Insumos.jBotonAgregar_Insumos.setText("Cancelar");
            idinsumo = (jTabla_Insumos.getValueAt(seleccionado, 0).toString());
            vGestion_Insumos.jTextDesc_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 3).toString());
            vGestion_Insumos.jCBTipo_Insumos.setSelectedItem(jTabla_Insumos.getValueAt(seleccionado, 4).toString());
            vGestion_Insumos.jTextFieldProveedor.setText(jTabla_Insumos.getValueAt(seleccionado, 5).toString());///prov
            vGestion_Insumos.jCBUnidad_Medida.setSelectedItem(jTabla_Insumos.getValueAt(seleccionado, 6).toString());
            vGestion_Insumos.jTextPrecio_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 7).toString());
            vGestion_Insumos.jTextStock_Insumos.setText(jTabla_Insumos.getValueAt(seleccionado, 8).toString());
            //fecha = jTabla_Insumos.getValueAt(seleccionado, 8).toString();
            /*if (!fecha.equals("-")) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    fecha_insumo = new java.sql.Timestamp(df.parse(fecha).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(vLista_Insumos.class.getName()).log(Level.SEVERE, null, ex);
                }
                vGestion_Insumos.jDateChooser1.setDate(fecha_insumo);
            } else {
                ((JTextField) vGestion_Insumos.jDateChooser1.getDateEditor().getUiComponent()).setText("");
            }*/
            tipo.id = idinsumo;
            dispose();
            LimpiarSeleccion();
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        //Mostrar();
        //jComboBox_TipoInsumo.setSelectedItem("Ninguno");
        //jTextFieldInsumoBuscar.setText("");
    }//GEN-LAST:event_formMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
//        if (!jTextField1.getText().isEmpty() || jComboBox1.getSelectedIndex() != 0) {
            datostabla = insumo.MostrarDatosBusquedaInsumos(jTextFieldInsumoBuscar.getText(),Integer.toString(jComboBox_TipoInsumo.getSelectedIndex()));
            if (datostabla.length != 0) {
                String[] columnas = {"IDINSUMO", "IDTIPOINSUMO", "IDPROVEEDOR", "DESCRIPCION", "TIPO INSUMO", "PROVEEDOR", "UNIDAD MEDIDA", "PRECIO", "STOCK"};
                datos = new DefaultTableModel(datostabla, columnas);
                jTabla_Insumos.setModel(datos);
                EliminarFilasVacias();
                //ReemplazarNulos();
                PintarPocoStock();
                //AjustarTamañoFilas();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Debes completar algún Filtro de Búsqueda");
//        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jTextFieldInsumoBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInsumoBuscarKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldInsumoBuscarKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JComboBox<String> jComboBox_TipoInsumo;
    private javax.swing.JLabel jLabelInsumoBuscar;
    private javax.swing.JLabel jLabelTipoInsumoBuscar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTabla_Insumos;
    private javax.swing.JTextField jTextFieldInsumoBuscar;
    // End of variables declaration//GEN-END:variables
}
