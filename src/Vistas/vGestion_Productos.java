package Vistas;

import Controlador.control_Productos;
import Controlador.control_existencias;
import Modelo.Productos;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Colo-PC
 */
public final class vGestion_Productos extends javax.swing.JInternalFrame {

    control_existencias conexis = new control_existencias();
    control_Productos producto = new control_Productos();
    Productos p = new Productos();
    String id, categoria, nombreproducto, precioventa;
    vLista_Productos listaproductos = null;

    public vGestion_Productos() {
        initComponents();
        AutoCompletarCategoriaProducto();
        EliminarItemsVacios();
        jButtonModificar.setVisible(false);
    }
    
    public void AutoCompletarCategoriaProducto() {
        Object[] categoriaproducto = conexis.combox("categoriasproductos", "descripcion");
        for (Object row : categoriaproducto) {
            jComboBoxCategorias.addItem((String) row);
        }
    }

    public void EliminarItemsVacios() {
        for (int i = 0; i < jComboBoxCategorias.getItemCount(); i++) {
            if (jComboBoxCategorias.getItemAt(i) == null) {
                jComboBoxCategorias.removeItemAt(i);
            }
        }
    }
    
    public void VolverListaProductos() {
        listaproductos = new vLista_Productos();
        vMenuPrincipal.jDesktopPane1.add(listaproductos);
        listaproductos.setVisible(true);
    }

    public boolean VerificarProductosRepetidos() {
        boolean repetido;
        String nomproducto = producto.ObtenerProducto(jTextFieldNombreProducto.getText());
        repetido = !nomproducto.isEmpty();
        return repetido;
    }

    public boolean VerificarProductosRepetidosModificar() {
        boolean repetido = true;
        if (!jComboBoxCategorias.getSelectedItem().equals(categoria) || !jTextFieldNombreProducto.getText().equals(nombreproducto) || !jTextFieldPrecioVenta.getText().equals(precioventa)) {
            repetido = false;
        }
        return repetido;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jComboBoxCategorias = new javax.swing.JComboBox<>();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombreProducto = new javax.swing.JTextField();
        jButtonCancelar = new javax.swing.JButton();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jLabelPrecioVenta = new javax.swing.JLabel();
        jTextFieldPrecioVenta = new javax.swing.JTextField();
        jLabelCategoria = new javax.swing.JLabel();

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

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxCategorias.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jComboBoxCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Categoría..." }));
        jComboBoxCategorias.setOpaque(false);
        jLayeredPane1.add(jComboBoxCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 170, 30));

        jLabelNombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelNombre.setText("(*) Nombre");
        jLayeredPane1.add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 108, 22));

        jTextFieldNombreProducto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreProductoKeyTyped(evt);
            }
        });
        jLayeredPane1.add(jTextFieldNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 170, 30));

        jButtonCancelar.setBackground(new java.awt.Color(237, 124, 61));
        jButtonCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 100, 30));

        jButtonAgregar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButtonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 100, 30));

        jButtonModificar.setBackground(new java.awt.Color(252, 249, 57));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButtonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 100, 30));

        jLabelPrecioVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelPrecioVenta.setText("Precio Venta:");
        jLayeredPane1.add(jLabelPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 94, 19));

        jTextFieldPrecioVenta.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jTextFieldPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPrecioVentaKeyTyped(evt);
            }
        });
        jLayeredPane1.add(jTextFieldPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 170, 30));

        jLabelCategoria.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabelCategoria.setText("(*) Categoría:");
        jLayeredPane1.add(jLabelCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 140, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        if (!jComboBoxCategorias.getSelectedItem().equals("Seleccionar Categoría...") && !jTextFieldNombreProducto.getText().trim().equals("")) {
            if (VerificarProductosRepetidos() == false) {
                p.setIdcategoriaproducto(producto.ObtenerIDCategoriaProducto((String) jComboBoxCategorias.getSelectedItem()));
                p.setDescripcion(jTextFieldNombreProducto.getText());
                if (jTextFieldPrecioVenta.getText().trim().length() == 0) {
                    p.setPrecioventa((float) 0.0);
                } else {
                    p.setPrecioventa(Float.parseFloat(jTextFieldPrecioVenta.getText()));
                }
                if (producto.InsertarProductos(p)) {
                    JOptionPane.showMessageDialog(null, "Nuevo Producto agregado");
                    VolverListaProductos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Producto ya Agregado!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        if (!jComboBoxCategorias.getSelectedItem().equals("Seleccionar Categoría...") && !jTextFieldNombreProducto.getText().trim().equals("")) {
            if (VerificarProductosRepetidosModificar() == false) {
                int i = JOptionPane.showConfirmDialog(null, "Guardar Cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    p.setIdcategoriaproducto(producto.ObtenerIDCategoriaProducto((String) jComboBoxCategorias.getSelectedItem()));
                    p.setDescripcion(jTextFieldNombreProducto.getText());
                    if (jTextFieldPrecioVenta.getText().trim().length() == 0) {
                        p.setPrecioventa((float) 0.0);
                    } else {
                        p.setPrecioventa(Float.parseFloat(jTextFieldPrecioVenta.getText()));
                    }
                    p.setIdproducto(Integer.parseInt(id));
                    if (producto.EditarProductos(p)) {
                        JOptionPane.showMessageDialog(null, "Modificado");
                        VolverListaProductos();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Este producto ya fue agregado!");
                JOptionPane.showMessageDialog(null, "Elimine o modifique el producto con los mismos datos.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes completar los campos obligatorios");
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        if (!jButtonAgregar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaProductos();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldNombreProducto.getText().trim().isEmpty() || !jComboBoxCategorias.getSelectedItem().equals("Seleccionar Categoría...")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaProductos();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextFieldPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPrecioVentaKeyTyped
        char[] ñ = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', KeyEvent.VK_BACK_SPACE};
        int b = 0;
        for (int i = 0; i <= 11; i++) {
            if (ñ[i] == evt.getKeyChar()) {
                b = 1;
            }
        }
        if (b == 0) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_jTextFieldPrecioVentaKeyTyped

    private void jTextFieldNombreProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreProductoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cadena = ("" + c).toUpperCase();
            c = cadena.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jTextFieldNombreProductoKeyTyped

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if (!jButtonAgregar.isEnabled()) {
            int i = JOptionPane.showConfirmDialog(null, "Cancelar Modificacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaProductos();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else if (!jTextFieldNombreProducto.getText().trim().isEmpty() || !jComboBoxCategorias.getSelectedItem().equals("Seleccionar Categoría...")) {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                dispose();
                VolverListaProductos();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        } else {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jButtonAgregar;
    public static javax.swing.JButton jButtonCancelar;
    public static javax.swing.JButton jButtonModificar;
    public static javax.swing.JComboBox<String> jComboBoxCategorias;
    private javax.swing.JLabel jLabelCategoria;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelPrecioVenta;
    private javax.swing.JLayeredPane jLayeredPane1;
    public static javax.swing.JTextField jTextFieldNombreProducto;
    public static javax.swing.JTextField jTextFieldPrecioVenta;
    // End of variables declaration//GEN-END:variables
}
