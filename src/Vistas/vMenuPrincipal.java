package Vistas;

import Controlador.control_PermisosPantallasPerfiles;
import Controlador.control_Reportes;
import Modelo.Fondo;
import Modelo.Session;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Colo-PC
 */
public final class vMenuPrincipal extends javax.swing.JFrame {

    public InputStream foto = this.getClass().getResourceAsStream("/Imagenes/LogoDelivery.jpg");
    control_Reportes reportes = new control_Reportes();
    control_PermisosPantallasPerfiles permiso = new control_PermisosPantallasPerfiles();
    private vGestion_Proveedores proveedores = null;
    private vLista_Proveedores listaprov = null;
    private vCompras_Insumos compras = null;
    private vVentas_Productos ventas = null;
    private vGestion_Asistencias asistencias = null;
    private vGestion_CategoriasProductos categoriasprod = null;
    private vGestion_Empleados empleados = null;
    private vGestion_FormasPagos formapagos = null;
    private vGestion_Insumos insumos = null;
    private vGestion_Pantallas pantallas = null;
    private vGestion_PermisosPantallasPerfiles permisos = null;
    private vGestion_Productos productos = null;
    private vGestion_Recetas recetas = null;
    private vGestion_TiposArticulos tiposart = null;
    private vGestion_TiposDocumentos tiposdoc = null;
    private vGestion_TiposEmpleados tiposemp = null;
    private vGestion_TiposGastos tiposgastos = null;
    private vGestion_TiposInsumos tiposinsumos = null;
    private vGestion_TiposUsuarios tiposuser = null;
    private vGestion_Usuarios usuarios = null;
    private vLista_Empleados listaemp = null;
    private vLista_Insumos listainsumos = null;
    private vLista_Usuarios listauser = null;
    private vListas_Compras listacompras = null;
    private vListas_Ventas listaventas = null;
    private vReporte_VentasSemanal ventassemanal = null;
    private vReporte_VentasMes ventasmes = null;
    private vReportes_Anio ventasanio = null;
    private vLista_Egresos listaegreso = null;
    private vGestion_Egresos egreso = null;
    private vAbrir_Caja abrircaja = null;
    private vCerrar_Caja cerrarcaja = null;
    private vMovimientos_Caja movcaja = null;
    private vGestion_TiposMovimientosCajas tipomovcaja = null;
    private vGestion_Clientes cliente = null;
    private vLista_Clientes listaclie = null;
    private vConsumosEmpleados consumos = null;

    public vMenuPrincipal() {
        initComponents();
        setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        setTitle("Delivery_Tazmania.V1");
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/LogoDelivery.PNG")).getImage());
        NombrePantallasHabilitadasporTiposUsuarios();
        DeshabilitarBotonesCompraVenta();
        VerificarCajaVisible();
        //cargarImagen(jDesktopPane1, foto);
        jLabel2.setText(vLogin.jTextNomUser_Login.getText());

        //jMenu1.setPreferredSize(new Dimension(26593000, 200));
        /*jMenu2.setPreferredSize(new Dimension(26993600, 200));
        jMenu12.setPreferredSize(new Dimension(24090900, 200));
        jMenu6.setPreferredSize(new Dimension(35002600, 200));
        jMenu4.setPreferredSize(new Dimension(27064000, 200));
        jMenu8.setPreferredSize(new Dimension(31670400, 200));
        jMenu3.setPreferredSize(new Dimension(29840600, 200));
        jMenu13.setPreferredSize(new Dimension(27840900, 200));
        jMenu14.setPreferredSize(new Dimension(38080500, 200));
        jMenu5.setPreferredSize(new Dimension(28804600, 200));*/
        setExtendedState(vMenuPrincipal.MAXIMIZED_BOTH);
    }

    public void NombrePantallasHabilitadasporTiposUsuarios() {
        String prov = "Proveedores", prod = "Productos", clie = "Clientes", user = "Usuarios";
        ArrayList<String> nombres = permiso.NombresPantallasHabilitadasPorTipoUsuario(vLogin.nombretu);
        if (!nombres.isEmpty()) {
            for (int i = 0; i < nombres.size(); i++) {
                if (jMenu2.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu2.setVisible(true);
                    jMenuItem13.setVisible(false);
                    //jMenuItem32.setVisible(false);
                    if (i == nombres.size()) {
                        jMenu12.setVisible(false);
                        jMenu4.setVisible(false);
                        jMenu8.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu3.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenu2.setVisible(false);
                }
                if (prov.equals(nombres.get(i))) {
                    i++;
                    if (!jMenu2.isVisible()) {
                        jMenu2.setVisible(true);
                        jMenuItem13.setVisible(true);
                        //jMenuItem32.setVisible(true);
                        jMenuItem2.setVisible(false);
                        jMenuItem8.setVisible(false);
                    } else {
                        jMenuItem13.setVisible(true);
                        //jMenuItem32.setVisible(true);
                        jMenuItem2.setVisible(true);
                        jMenuItem18.setVisible(true);
                    }
                    if (i == nombres.size()) {
                        jMenu12.setVisible(false);
                        jMenu4.setVisible(false);
                        jMenu8.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu3.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenuItem13.setVisible(false);
                    //jMenuItem32.setVisible(false);
                }
                if (jMenu12.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu12.setVisible(true);
                    jMenuItem28.setVisible(false);
                    jMenuItem33.setVisible(false);
                    jMenuItem1.setVisible(false);
                    //jMenuItem7.setVisible(false);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        jMenu4.setVisible(false);
                        jMenu3.setVisible(false);
                        jMenu8.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenu12.setVisible(false);
                }

                if (prod.equals(nombres.get(i))) {
                    i++;
                    if (!jMenu12.isVisible()) {
                        jMenu12.setVisible(true);
                        jMenuItem28.setVisible(true);
                        jMenuItem33.setVisible(true);
                        jMenuItem20.setVisible(false);
                        jMenuItem19.setVisible(false);
                        jMenuItem1.setVisible(false);
                        //jMenuItem7.setVisible(false);
                    } else {
                        jMenuItem28.setVisible(true);
                        jMenuItem33.setVisible(true);
                        jMenuItem20.setVisible(true);
                        jMenuItem19.setVisible(true);
                        jMenuItem1.setVisible(true);
                        //jMenuItem7.setVisible(true);
                    }
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        jMenu4.setVisible(false);
                        jMenu3.setVisible(false);
                        jMenu8.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenuItem28.setVisible(false);
                    jMenuItem33.setVisible(false);
                }

                if (clie.equals(nombres.get(i))) {
                    i++;
                    if (!jMenu12.isVisible()) {
                        jMenu12.setVisible(true);
                        jMenuItem1.setVisible(true);
                        //jMenuItem7.setVisible(true);
                        jMenuItem28.setVisible(false);
                        jMenuItem33.setVisible(false);
                        jMenuItem20.setVisible(false);
                        jMenuItem19.setVisible(false);
                    } else {
                        jMenuItem1.setVisible(true);
                        //jMenuItem7.setVisible(true);
                        jMenuItem28.setVisible(true);
                        jMenuItem33.setVisible(true);
                        jMenuItem20.setVisible(true);
                        jMenuItem19.setVisible(true);
                    }
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        jMenu4.setVisible(false);
                        jMenu3.setVisible(false);
                        jMenu8.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenuItem1.setVisible(false);
                    //jMenuItem7.setVisible(false);
                }

                if (jMenu4.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu4.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        jMenu8.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenu4.setVisible(false);
                }
                if (jMenu8.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu8.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenu8.setVisible(false);
                }
                if (jMenu13.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu13.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        //jMenu6.setVisible(false);
                        //jMenu8.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenu13.setVisible(false);
                }

                if (jMenu6.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu6.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        jMenu13.setVisible(false);
                        //jMenu8.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenu6.setVisible(false);
                }
                if (jMenu3.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu3.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        jMenu4.setVisible(false);
                        jMenu8.setVisible(false);
                        jMenu13.setVisible(false);
                        jMenu6.setVisible(false);
                        jMenu14.setVisible(false);
                        break;
                    }
                } else {
                    jMenu3.setVisible(false);
                }

                if (jMenu14.getText().equals(nombres.get(i))) {
                    i++;
                    jMenu14.setVisible(true);
                    jMenuItem31.setVisible(false);
                    jMenuItem34.setVisible(false);
                    jMenuItem35.setVisible(false);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        //jMenu6.setVisible(false);
                        //jMenu8.setVisible(false);
                        //jMenu13.setVisible(false);
                        break;
                    }
                } else {
                    jMenu14.setVisible(false);
                }

                if (user.equals(nombres.get(i))) {
                    i++;
                    if (!jMenu14.isVisible()) {
                        jMenu14.setVisible(true);
                        jMenuItem31.setVisible(true);
                        jMenuItem34.setVisible(true);
                        jMenuItem35.setVisible(true);
                        jMenuItem27.setVisible(false);
                    } else {
                        jMenuItem31.setVisible(true);
                        jMenuItem34.setVisible(true);
                        jMenuItem35.setVisible(true);
                        jMenuItem27.setVisible(true);
                    }
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        //jMenu6.setVisible(false);
                        //jMenu8.setVisible(false);
                        //jMenu13.setVisible(false);
                        break;
                    }
                } else {
                    jMenuItem31.setVisible(false);
                    jMenuItem34.setVisible(false);
                    jMenuItem35.setVisible(false);
                }
            }
        }
    }

    public void cargarImagen(javax.swing.JDesktopPane jDeskp, InputStream fileImagen) {
        try {
            BufferedImage image = ImageIO.read(fileImagen);
            jDeskp.setBorder(new Fondo(image));
        } catch (Exception e) {
            System.out.println("Imagen no disponible");
        }
    }

    public void VerificarCajaAbierta() {
        int idcaja = Session.getIdcaja_abierta(), iduser = Session.getIdturno_abierto();
        if (idcaja == 0) {
            jMenu2.setEnabled(false);
            jMenu12.setEnabled(false);
            jMenu4.setEnabled(false);
            jMenu8.setEnabled(false);
            jMenu13.setEnabled(false);
            jMenu6.setEnabled(false);
            jMenu14.setEnabled(false);
            jMenuItem12.setEnabled(false);
            jMenuItem30.setEnabled(false);
            jMenuItem29.setEnabled(false);
            jButton5.setEnabled(false);
            jButton6.setEnabled(false);
            jButton3.setEnabled(false);
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
        }
    }

    public void VerificarCajaVisible() {
        if (jMenu3.isVisible()) {
            VerificarCajaAbierta();
        } else {
            jMenu2.setEnabled(true);
            jMenu12.setEnabled(true);
            jMenu4.setEnabled(true);
            jMenu8.setEnabled(true);
            jMenu13.setEnabled(true);
            jMenu6.setEnabled(true);
            jMenu14.setEnabled(true);
            jButton5.setEnabled(true);
            jButton6.setEnabled(true);
            jButton3.setEnabled(true);
            jButton2.setEnabled(true);
            jButton1.setEnabled(true);
        }
    }

    public void DeshabilitarBotonesCompraVenta() {
        if (jMenu2.isVisible() && jMenuItem2.isVisible() && jMenuItem8.isVisible() && !jMenuItem13.isVisible()) {
            jButton6.setVisible(true);
            jButton2.setVisible(false);
        } else if (jMenu2.isVisible() && !jMenuItem2.isVisible() && !jMenuItem8.isVisible() && jMenuItem13.isVisible()) {
            jButton2.setVisible(true);
            jButton6.setVisible(false);
        } else if (jMenu2.isVisible() && jMenuItem2.isVisible() && jMenuItem8.isVisible() && jMenuItem13.isVisible()) {
            jButton2.setVisible(true);
            jButton6.setVisible(true);
        } else if (!jMenu2.isVisible()) {
            jButton2.setVisible(false);
            jButton6.setVisible(false);
        }

        if (jMenu4.isVisible()) {
            jButton1.setVisible(true);
        } else {
            jButton1.setVisible(false);
        }

        if (!jMenu12.isVisible()) {
            jButton5.setVisible(false);
        } else if (jMenu12.isVisible() && !jMenuItem20.isVisible() && !jMenuItem19.isVisible() && jMenuItem28.isVisible() && jMenuItem33.isVisible()
                && jMenuItem1.isVisible()) {
            jButton5.setVisible(false);
        } else if (jMenu12.isVisible() && !jMenuItem20.isVisible() && !jMenuItem19.isVisible() && !jMenuItem28.isVisible() && !jMenuItem33.isVisible()
                && jMenuItem1.isVisible()) {
            jButton5.setVisible(false);
        } else if (jMenu12.isVisible() && !jMenuItem20.isVisible() && !jMenuItem19.isVisible() && jMenuItem28.isVisible() && jMenuItem33.isVisible()
                && !jMenuItem1.isVisible()) {
            jButton5.setVisible(false);
        } else if (jMenu12.isVisible() && jMenuItem20.isVisible() && jMenuItem19.isVisible()) {
            jButton5.setVisible(true);
        }

        if (jMenu3.isVisible()) {
            jButton4.setVisible(true);
        } else {
            jButton4.setVisible(false);
        }

        if (jMenu6.isVisible()) {
            jButton3.setVisible(true);
        } else {
            jButton3.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenu14 = new javax.swing.JMenu();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenu_TiposProductos = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jDesktopPane1.setBackground(new java.awt.Color(255, 248, 177));
        jDesktopPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDesktopPane1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jDesktopPane1.setPreferredSize(new java.awt.Dimension(1330, 730));

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/insumos48.png"))); // NOI18N
        jButton1.setText("Insumos");
        jButton1.setPreferredSize(new java.awt.Dimension(143, 41));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuProveedor.png"))); // NOI18N
        jButton2.setText("Proveedores");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gastos_48.png"))); // NOI18N
        jButton3.setText("Gastos");
        jButton3.setPreferredSize(new java.awt.Dimension(143, 41));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/caja48.png"))); // NOI18N
        jButton4.setText("Caja");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuCompras48.png"))); // NOI18N
        jButton6.setText("Compras");
        jButton6.setPreferredSize(new java.awt.Dimension(143, 41));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonventa48.png"))); // NOI18N
        jButton5.setText("Ventas");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel1.setText("Usuario:");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel2.setText("Usuario Conectado");
        jLabel2.setToolTipText("");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/DeliveryTazmania_main.png"))); // NOI18N

        jDesktopPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(414, 414, 414))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(46, 46, 46))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(57, 57, 57)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jScrollPane1.setViewportView(jDesktopPane1);

        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(50, 60));

        jMenu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuCompras.png"))); // NOI18N
        jMenu2.setText("Compras");
        jMenu2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(125, 19));

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem2.setText("Comprar Insumos");
        jMenuItem2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem2.setPreferredSize(new java.awt.Dimension(260, 27));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem8.setText("Listado de Compras");
        jMenuItem8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuItem13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem13.setText("Administrar Proveedores");
        jMenuItem13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem13.setPreferredSize(new java.awt.Dimension(325, 25));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        jMenuBar1.add(jMenu2);

        jMenu12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonventa.png"))); // NOI18N
        jMenu12.setText("Ventas");
        jMenu12.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenu12.setHideActionText(true);
        jMenu12.setPreferredSize(new java.awt.Dimension(105, 19));

        jMenuItem20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem20.setText("Vender Productos");
        jMenuItem20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem20);

        jMenuItem19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem19.setText("Listado de Ventas");
        jMenuItem19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem19);

        jMenuItem28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem28.setText("Administrar Productos");
        jMenuItem28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem28);

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem1.setText("Administrar Clientes");
        jMenuItem1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem1);

        jMenuBar1.add(jMenu12);

        jMenu4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/insumos32.png"))); // NOI18N
        jMenu4.setText("Insumos");
        jMenu4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenu4.setPreferredSize(new java.awt.Dimension(123, 19));

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem4.setText("Administrar Insumos");
        jMenuItem4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem4.setPreferredSize(new java.awt.Dimension(295, 27));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar1.add(jMenu4);

        jMenu8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuEmpleados.png"))); // NOI18N
        jMenu8.setText("Empleados");
        jMenu8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenu8.setPreferredSize(new java.awt.Dimension(142, 19));

        jMenuItem14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem14.setText("Administrar Empleados");
        jMenuItem14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem14);

        jMenuItem16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem16.setText("Asistencias");
        jMenuItem16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem16);

        jMenuItem11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem11.setText("Consumos de Empleados");
        jMenuItem11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem11);

        jMenuBar1.add(jMenu8);

        jMenu13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuReportes.png"))); // NOI18N
        jMenu13.setText("Reportes");
        jMenu13.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenu13.setMaximumSize(new java.awt.Dimension(125, 32767));
        jMenu13.setPreferredSize(new java.awt.Dimension(125, 19));

        jMenuItem22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem22.setText("Ventas Diarias");
        jMenuItem22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem22);

        jMenuItem23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem23.setText("Ventas Por Semana");
        jMenuItem23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem23);

        jMenuItem24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem24.setText("Ventas Por Mes");
        jMenuItem24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem24);

        jMenuItem25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem25.setText("Ventas Por Año");
        jMenuItem25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem25);

        jMenuBar1.add(jMenu13);

        jMenu6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gastos_32.png"))); // NOI18N
        jMenu6.setText("Gastos");
        jMenu6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem9.setText("Administrar Gastos");
        jMenuItem9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem9);

        jMenuBar1.add(jMenu6);

        jMenu3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/caja.png"))); // NOI18N
        jMenu3.setText("Caja");
        jMenu3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N

        jMenuItem18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem18.setText("Abrir Caja");
        jMenuItem18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem18);

        jMenuItem12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem12.setText("Cerrar Caja");
        jMenuItem12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuItem30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem30.setText("Movimientos Caja");
        jMenuItem30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem30);

        jMenuBar1.add(jMenu3);

        jMenu14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuAdmin.png"))); // NOI18N
        jMenu14.setText("Configuración");
        jMenu14.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenu14.setPreferredSize(new java.awt.Dimension(175, 19));

        jMenuItem27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem27.setText("Gestionar Permisos de Pantallas");
        jMenuItem27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem27.setPreferredSize(new java.awt.Dimension(390, 27));
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem27);

        jMenuItem31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem31.setText("Registrar Usuarios");
        jMenuItem31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem31.setPreferredSize(new java.awt.Dimension(290, 27));
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem31);

        jMenuItem34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem34.setText("Listado de Usuarios");
        jMenuItem34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem34.setPreferredSize(new java.awt.Dimension(290, 27));
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem34);

        jMenuItem35.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem35.setText("Tipos Usuarios");
        jMenuItem35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem35.setPreferredSize(new java.awt.Dimension(290, 27));
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem35);

        jMenuBar1.add(jMenu14);

        jMenu1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tools32.png"))); // NOI18N
        jMenu1.setText("Administrar");
        jMenu1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N

        jMenuItem33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem33.setText("Categorias Productos");
        jMenuItem33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem33.setPreferredSize(new java.awt.Dimension(340, 27));
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem33);

        jMenu_TiposProductos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenu_TiposProductos.setText("Tipos Productos");
        jMenu_TiposProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu_TiposProductosActionPerformed(evt);
            }
        });
        jMenu1.add(jMenu_TiposProductos);

        jMenuItem29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem29.setText("Tipos Movimientos Caja");
        jMenuItem29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem29.setPreferredSize(new java.awt.Dimension(340, 27));
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem29);

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem5.setText("Tipos Insumos");
        jMenuItem5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem5.setPreferredSize(new java.awt.Dimension(340, 27));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem15.setText("Tipos Empleados");
        jMenuItem15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem15.setPreferredSize(new java.awt.Dimension(340, 27));
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem15);

        jMenuBar1.add(jMenu1);

        jMenu5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout.png"))); // NOI18N
        jMenu5.setText("Salir");
        jMenu5.setContentAreaFilled(false);
        jMenu5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenu5.setPreferredSize(new java.awt.Dimension(128, 19));
        jMenu5.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu5MenuSelected(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1359, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1168, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (compras == null || compras.isClosed()) {
            compras = new vCompras_Insumos();
            jDesktopPane1.add(compras);
            compras.setVisible(true);
            compras.toFront();
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (listainsumos == null || listainsumos.isClosed()) {
            listainsumos = new vLista_Insumos();
            jDesktopPane1.add(listainsumos);
            listainsumos.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (tiposinsumos == null || tiposinsumos.isClosed()) {
            tiposinsumos = new vGestion_TiposInsumos();
            jDesktopPane1.add(tiposinsumos);
            tiposinsumos.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        if (listacompras == null || listacompras.isClosed()) {
            listacompras = new vListas_Compras();
            jDesktopPane1.add(listacompras);
            listacompras.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
       if (listaemp == null || listaemp.isClosed()) {
            listaemp = new vLista_Empleados();
            jDesktopPane1.add(listaemp);
            listaemp.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        if (tiposemp == null || tiposemp.isClosed()) {
            tiposemp = new vGestion_TiposEmpleados();
            jDesktopPane1.add(tiposemp);
            tiposemp.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        if (asistencias == null || asistencias.isClosed()) {
            asistencias = new vGestion_Asistencias();
            jDesktopPane1.add(asistencias);
            asistencias.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        if (ventas == null || ventas.isClosed()) {
            ventas = new vVentas_Productos();
            jDesktopPane1.add(ventas);
            ventas.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        if (listaventas == null || listaventas.isClosed()) {
            listaventas = new vListas_Ventas();
            jDesktopPane1.add(listaventas);
            listaventas.setVisible(true);
            listaventas.toFront();
        }

    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        reportes.ReporteVentasDiarias();
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        if (ventassemanal == null || ventassemanal.isClosed()) {
            ventassemanal = new vReporte_VentasSemanal();
            jDesktopPane1.add(ventassemanal);
            ventassemanal.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        if (ventasmes == null || ventasmes.isClosed()) {
            ventasmes = new vReporte_VentasMes();
            jDesktopPane1.add(ventasmes);
            ventasmes.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        if (ventasanio == null || ventasanio.isClosed()) {
            ventasanio = new vReportes_Anio();
            jDesktopPane1.add(ventasanio);
            ventasanio.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        if (permisos == null || permisos.isClosed()) {
            permisos = new vGestion_PermisosPantallasPerfiles();
            jDesktopPane1.add(permisos);
            permisos.setVisible(true);
            permisos.toFront();
        }
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (proveedores == null || proveedores.isClosed()) {
            proveedores = new vGestion_Proveedores();
            jDesktopPane1.add(proveedores);
            proveedores.toFront();
            proveedores.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (insumos == null || insumos.isClosed()) {
            insumos = new vGestion_Insumos();
            jDesktopPane1.add(insumos);
            insumos.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        if (listaprov == null || listaprov.isClosed()) {
            listaprov = new vLista_Proveedores();
            jDesktopPane1.add(listaprov);
            listaprov.toFront();
            listaprov.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        if (productos == null || productos.isClosed()) {
            productos = new vGestion_Productos();
            jDesktopPane1.add(productos);
            productos.toFront();
            productos.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        if (categoriasprod == null || categoriasprod.isClosed()) {
            categoriasprod = new vGestion_CategoriasProductos();
            jDesktopPane1.add(categoriasprod);
            categoriasprod.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        if (movcaja == null || movcaja.isClosed()) {
            movcaja = new vMovimientos_Caja();
            jDesktopPane1.add(movcaja);
            movcaja.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        if (listaegreso == null || listaegreso.isClosed()) {
            listaegreso = new vLista_Egresos();
            jDesktopPane1.add(listaegreso);
            listaegreso.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        if (abrircaja == null || abrircaja.isClosed()) {
            abrircaja = new vAbrir_Caja();
            jDesktopPane1.add(abrircaja);
            abrircaja.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        if (cerrarcaja == null || cerrarcaja.isClosed()) {
            cerrarcaja = new vCerrar_Caja();
            jDesktopPane1.add(cerrarcaja);
            cerrarcaja.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        if (tipomovcaja == null || tipomovcaja.isClosed()) {
            tipomovcaja = new vGestion_TiposMovimientosCajas();
            jDesktopPane1.add(tipomovcaja);
            tipomovcaja.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (egreso == null || egreso.isClosed()) {
            egreso = new vGestion_Egresos();
            jDesktopPane1.add(egreso);
            egreso.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (abrircaja == null || abrircaja.isClosed()) {
            abrircaja = new vAbrir_Caja();
            jDesktopPane1.add(abrircaja);
            abrircaja.setVisible(true);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (ventas == null || ventas.isClosed()) {
            ventas = new vVentas_Productos();
            jDesktopPane1.add(ventas);
            ventas.setVisible(true);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (compras == null || compras.isClosed()) {
            compras = new vCompras_Insumos();
            jDesktopPane1.add(compras);
            compras.setVisible(true);
            compras.toFront();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        if (usuarios == null || usuarios.isClosed()) {
            usuarios = new vGestion_Usuarios();
            jDesktopPane1.add(usuarios);
            usuarios.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        if (listauser == null || listauser.isClosed()) {
            listauser = new vLista_Usuarios();
            jDesktopPane1.add(listauser);
            listauser.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        if (tiposuser == null || tiposuser.isClosed()) {
            tiposuser = new vGestion_TiposUsuarios();
            jDesktopPane1.add(tiposuser);
            tiposuser.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (listaclie == null || listaclie.isClosed()) {
            listaclie = new vLista_Clientes();
            jDesktopPane1.add(listaclie);
            listaclie.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        if (consumos == null || consumos.isClosed()) {
            consumos = new vConsumosEmpleados();
            jDesktopPane1.add(consumos);
            consumos.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenu5MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu5MenuSelected
        // TODO add your handling code here:
//        int idcaja = Session.getIdcaja_abierta();
//        if (idcaja == 0) {
            vLogin login1 = new vLogin();
            login1.toFront();
            login1.setVisible(true);
            this.dispose();
//        } else {
//            JOptionPane.showMessageDialog(null, "Antes de salir de Sesión debes Cerrar Caja!");
//        }
    }//GEN-LAST:event_jMenu5MenuSelected

    private void jMenu_TiposProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_TiposProductosActionPerformed
        // TODO add your handling code here:
        if (tiposart == null || tiposart.isClosed()) {
            tiposart = new vGestion_TiposArticulos();
            jDesktopPane1.add(tiposart);
            tiposart.setVisible(true);
        }
    }//GEN-LAST:event_jMenu_TiposProductosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vMenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton jButton1;
    public static javax.swing.JButton jButton2;
    public static javax.swing.JButton jButton3;
    public static javax.swing.JButton jButton4;
    public static javax.swing.JButton jButton5;
    public static javax.swing.JButton jButton6;
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    public static javax.swing.JMenu jMenu12;
    public static javax.swing.JMenu jMenu13;
    public static javax.swing.JMenu jMenu14;
    public static javax.swing.JMenu jMenu2;
    public static javax.swing.JMenu jMenu3;
    public static javax.swing.JMenu jMenu4;
    public static javax.swing.JMenu jMenu5;
    public static javax.swing.JMenu jMenu6;
    public static javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    public static javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    public static javax.swing.JMenuItem jMenuItem29;
    public static javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenu_TiposProductos;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
