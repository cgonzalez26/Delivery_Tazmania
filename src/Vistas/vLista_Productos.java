package Vistas;

import Controlador.Sentencias_sql;
import Controlador.control_Productos;
import Controlador.control_existencias;
import Modelo.Productos;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class vLista_Productos extends javax.swing.JInternalFrame {

    Sentencias_sql tabla = new Sentencias_sql();
    control_existencias conexis = new control_existencias();
    String categoria, nombreproducto, desc, precioventa, idproducto;
    control_Productos producto = new control_Productos();
    Productos p = new Productos();
    Timestamp fechaseleccionada;
    DefaultTableModel datos, recetas;
    DefaultListModel list;
    ArrayList<String> listprod;
    vGestion_Productos ventanaproducto = null;

    public vLista_Productos() {
        initComponents();
        Mostrar();
        jListProductos.setVisible(false);
        jTableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    vGestion_Productos.jButtonAgregar.setEnabled(false);
                    int fila = jTableProductos.rowAtPoint(e.getPoint());
                    if (ventanaproducto == null || ventanaproducto.isClosed()) {
                        ventanaproducto = new vGestion_Productos();
                        vMenuPrincipal.jDesktopPane1.add(ventanaproducto);
                        ventanaproducto.setVisible(true);
                        ventanaproducto.toFront();
                    }
                    idproducto = jTableProductos.getValueAt(fila, 0).toString();
                    vGestion_Productos.jComboBoxCategorias.setSelectedItem(jTableProductos.getValueAt(fila, 2).toString());
                    vGestion_Productos.jTextFieldNombreProducto.setText(jTableProductos.getValueAt(fila, 3).toString());
                    vGestion_Productos.jTextFieldPrecioVenta.setText(jTableProductos.getValueAt(fila, 4).toString());
                    categoria = jTableProductos.getValueAt(fila, 2).toString();
                    nombreproducto = jTableProductos.getValueAt(fila, 3).toString();
                    precioventa = jTableProductos.getValueAt(fila, 4).toString();
                    ventanaproducto.categoria = categoria;
                    ventanaproducto.nombreproducto = nombreproducto;
                    ventanaproducto.precioventa = precioventa;
                    dispose();
                }
            }
        });
    }

    /*public void ReemplazarNulos() {
        if (jTableProductos.getRowCount() != 0) {
            for (int i = 0; i < jTableProductos.getRowCount(); i++) {
                if (jTableProductos.getValueAt(i, 5).equals("00/00/0000 12:00")) {
                    jTableProductos.setValueAt("-", i, 5);
                }
            }
        }
    }*/
    public void Mostrar() {
        String[] columnas = {"IDPROD", "IDCATEGORIAPRODUCTO", "CATEGORIA", "DESCRIPCION", "PRECIO VENTA"};
        Object[][] dato = producto.MostrarDatos();
        datos = new DefaultTableModel(dato, columnas);
        jTableProductos.setModel(datos);
        EliminarFilasVacias();
        //ReemplazarNulos();
        //AjustarTamañoFilas();
        ocultar_columnas();
    }

    public void ListaProductos() {
        listprod = conexis.list("productos", "descripcion", jTextFieldProductoBuscado.getText());
        String substr = jTextFieldProductoBuscado.getText().toLowerCase();
        list = new DefaultListModel();
        jListProductos.setModel(list);
        list.removeAllElements();
        if (listprod.size() > 0) {
            for (int i = 0; i < listprod.size(); i++) {
                if (listprod.get(i) == null) {
                    listprod.remove(i);
                } else {
                    String sublist = listprod.get(i).toLowerCase();
                    if (sublist.contains(substr)) {
                        list.addElement(listprod.get(i));
                        jListProductos.setVisible(true);
                        if (jTextFieldProductoBuscado.getText().isEmpty()) {
                            jListProductos.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void ocultar_columnas() {
        jTableProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableProductos.getColumnModel().getColumn(0).setMinWidth(0);
        jTableProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableProductos.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableProductos.getColumnModel().getColumn(1).setMinWidth(0);
        jTableProductos.getColumnModel().getColumn(1).setPreferredWidth(0);
    }

    public void EliminarFilasVacias() {
        if (jTableProductos.getRowCount() != 0) {
            for (int columna = 0; columna < jTableProductos.getColumnCount(); columna++) {
                for (int fila = 0; fila < jTableProductos.getRowCount(); fila++) {
                    if (jTableProductos.getValueAt(fila, columna) == null) {
                        datos.removeRow(fila);
                    }
                }
            }
        }
    }

    public void LimpiarSeleccion() {
        jTableProductos.clearSelection();
        jTableProductos.getSelectionModel().clearSelection();
    }

    /*public void MostrarRecetasActivas() {
        tabla.descripcion = desc;
        recetas = tabla.ConsultarInsumos();
        vGestion_Recetas.jTableInsumosRegistrados.setModel(recetas);
        ocultar_columnasreceta();
    }

    public void ocultar_columnasreceta() {
        vGestion_Recetas.jTableInsumosRegistrados.getColumnModel().getColumn(0).setMaxWidth(0);
        vGestion_Recetas.jTableInsumosRegistrados.getColumnModel().getColumn(0).setMinWidth(0);
        vGestion_Recetas.jTableInsumosRegistrados.getColumnModel().getColumn(0).setPreferredWidth(0);
    }*/
    public void VolverVentanaProductos() {
        ventanaproducto = new vGestion_Productos();
        vMenuPrincipal.jDesktopPane1.add(ventanaproducto);
        ventanaproducto.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane3 = new javax.swing.JLayeredPane();
        jLabelNombreProducto = new javax.swing.JLabel();
        jTextFieldProductoBuscado = new javax.swing.JTextField();
        jListProductos = new javax.swing.JList<>();
        jButtonBuscarProducto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProductos =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButtonNuevoProducto = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonAbrirReceta = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado Productos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.png"))); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 13))); // NOI18N

        jLabelNombreProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombreProducto.setText("Nombre Producto:");

        jTextFieldProductoBuscado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldProductoBuscado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProductoBuscadoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldProductoBuscadoKeyTyped(evt);
            }
        });

        jButtonBuscarProducto.setBackground(new java.awt.Color(252, 249, 57));
        jButtonBuscarProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButtonBuscarProducto.setText("Buscar");
        jButtonBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarProductoActionPerformed(evt);
            }
        });

        jLayeredPane3.setLayer(jLabelNombreProducto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jTextFieldProductoBuscado, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jListProductos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jButtonBuscarProducto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelNombreProducto)
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jListProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldProductoBuscado, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addGroup(jLayeredPane3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jButtonBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldProductoBuscado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jListProductos)
                .addGap(18, 18, 18)
                .addComponent(jButtonBuscarProducto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jLayeredPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 390, 120));

        jScrollPane1.setOpaque(false);

        jTableProductos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTableProductos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 810, 241));

        jButtonNuevoProducto.setBackground(new java.awt.Color(252, 249, 57));
        jButtonNuevoProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonNuevoProducto.setText("Nuevo");
        jButtonNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoProductoActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 100, 30));

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 100, 30));

        jButtonEliminar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 420, 100, 30));

        jButtonAbrirReceta.setBackground(new java.awt.Color(252, 240, 0));
        jButtonAbrirReceta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAbrirReceta.setText("Abrir Receta");
        jButtonAbrirReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbrirRecetaActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonAbrirReceta, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 420, 100, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldProductoBuscadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductoBuscadoKeyReleased
        ListaProductos();
    }//GEN-LAST:event_jTextFieldProductoBuscadoKeyReleased

    private void jTextFieldProductoBuscadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductoBuscadoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldProductoBuscadoKeyTyped

    private void jButtonBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarProductoActionPerformed
        if (!jTextFieldProductoBuscado.getText().isEmpty()) {
            String[] columnas = {"IDPROD", "IDCATEGORIAPRODUCTO", "CATEGORIA", "DESCRIPCION", "PRECIO VENTA"};
            Object[][] dato = producto.MostrarDatosBusqueda(jTextFieldProductoBuscado.getText());
            if (dato.length != 0) {
                datos = new DefaultTableModel(dato, columnas);
                jTableProductos.setModel(datos);
                EliminarFilasVacias();
                //ReemplazarNulos();
                //AjustarTamañoFilas();
                ocultar_columnas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar el campo");
        }
    }//GEN-LAST:event_jButtonBuscarProductoActionPerformed

    private void jButtonNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoProductoActionPerformed
        VolverVentanaProductos();
    }//GEN-LAST:event_jButtonNuevoProductoActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        int fila = jTableProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            vGestion_Productos.jButtonAgregar.setEnabled(false);
            if (ventanaproducto == null || ventanaproducto.isClosed()) {
                ventanaproducto = new vGestion_Productos();
                vMenuPrincipal.jDesktopPane1.add(ventanaproducto);
                ventanaproducto.setVisible(true);
                ventanaproducto.toFront();
            }
            idproducto = jTableProductos.getValueAt(fila, 0).toString();
            vGestion_Productos.jComboBoxCategorias.setSelectedItem(jTableProductos.getValueAt(fila, 2).toString());
            vGestion_Productos.jTextFieldNombreProducto.setText(jTableProductos.getValueAt(fila, 3).toString());
            vGestion_Productos.jTextFieldPrecioVenta.setText(jTableProductos.getValueAt(fila, 4).toString());
            categoria = jTableProductos.getValueAt(fila, 2).toString();
            nombreproducto = jTableProductos.getValueAt(fila, 3).toString();
            precioventa = jTableProductos.getValueAt(fila, 4).toString();
            ventanaproducto.categoria = categoria;
            ventanaproducto.nombreproducto = nombreproducto;
            ventanaproducto.precioventa = precioventa;
            dispose();
        }
        /*int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jButton1.setEnabled(true);
                jButton4.setEnabled(true);
                jButton2.setText("Modificar");
                jButton3.setText("Eliminar");
                Limpiar();
                LimpiarSeleccion();
            }*/
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int seleccionado = jTableProductos.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                p.setIdproducto(Integer.parseInt((String) jTableProductos.getValueAt(seleccionado, 0)));
                if (producto.EliminarProductos(p)) {
                    JOptionPane.showMessageDialog(null, "Eliminado");
                    Mostrar();
                }
            } else {
                LimpiarSeleccion();
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonAbrirRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbrirRecetaActionPerformed
        int seleccionado = jTableProductos.getSelectedRow();
        if (seleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
        } else {
            vGestion_Recetas receta = new vGestion_Recetas();
            //desc = jTableProductos.getValueAt(seleccionado, 3).toString();
            //MostrarRecetasActivas();
            vGestion_Recetas.jLabelNombreProductoElegido.setText(jTableProductos.getValueAt(seleccionado, 3).toString());
            vMenuPrincipal.jDesktopPane1.add(receta);
            receta.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonAbrirRecetaActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        LimpiarSeleccion();
        //Mostrar();
        //jTextFieldProductoBuscado.setText("");
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAbrirReceta;
    private javax.swing.JButton jButtonBuscarProducto;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevoProducto;
    private javax.swing.JLabel jLabelNombreProducto;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JList<String> jListProductos;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableProductos;
    private javax.swing.JTextField jTextFieldProductoBuscado;
    // End of variables declaration//GEN-END:variables
}
