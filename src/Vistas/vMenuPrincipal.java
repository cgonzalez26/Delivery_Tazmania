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
    private vLista_Asistencias asistencias = null;
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
    private vLista_Productos listaproductos = null;
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
    private vLista_ConsumosEmpleados consumos = null;

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
                if (jMenuCompras.getText().equals(nombres.get(i))) {
                    i++;
                    jMenuCompras.setVisible(true);
                    jMenuItem13.setVisible(false);
                    //jMenuItem32.setVisible(false);
                    if (i == nombres.size()) {
                        jMenuVentas.setVisible(false);
                        JMenuInsumos.setVisible(false);
                        jMenuEmpleados.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuCaja.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuCompras.setVisible(false);
                }
                if (prov.equals(nombres.get(i))) {
                    i++;
                    if (!jMenuCompras.isVisible()) {
                        jMenuCompras.setVisible(true);
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
                        jMenuVentas.setVisible(false);
                        JMenuInsumos.setVisible(false);
                        jMenuEmpleados.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuCaja.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuItem13.setVisible(false);
                    //jMenuItem32.setVisible(false);
                }
                if (jMenuVentas.getText().equals(nombres.get(i))) {
                    i++;
                    jMenuVentas.setVisible(true);
                    jMenuItem28.setVisible(false);
                    jMenuItem33.setVisible(false);
                    jMenuItem1.setVisible(false);
                    //jMenuItem7.setVisible(false);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        JMenuInsumos.setVisible(false);
                        jMenuCaja.setVisible(false);
                        jMenuEmpleados.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuVentas.setVisible(false);
                }

                if (prod.equals(nombres.get(i))) {
                    i++;
                    if (!jMenuVentas.isVisible()) {
                        jMenuVentas.setVisible(true);
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
                        JMenuInsumos.setVisible(false);
                        jMenuCaja.setVisible(false);
                        jMenuEmpleados.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuItem28.setVisible(false);
                    jMenuItem33.setVisible(false);
                }

                if (clie.equals(nombres.get(i))) {
                    i++;
                    if (!jMenuVentas.isVisible()) {
                        jMenuVentas.setVisible(true);
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
                        JMenuInsumos.setVisible(false);
                        jMenuCaja.setVisible(false);
                        jMenuEmpleados.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuItem1.setVisible(false);
                    //jMenuItem7.setVisible(false);
                }

                if (JMenuInsumos.getText().equals(nombres.get(i))) {
                    i++;
                    JMenuInsumos.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        jMenuEmpleados.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    JMenuInsumos.setVisible(false);
                }
                if (jMenuEmpleados.getText().equals(nombres.get(i))) {
                    i++;
                    jMenuEmpleados.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuEmpleados.setVisible(false);
                }
                if (jMenuReportes.getText().equals(nombres.get(i))) {
                    i++;
                    jMenuReportes.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        //jMenu6.setVisible(false);
                        //jMenu8.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuReportes.setVisible(false);
                }

                if (jMenuGastos.getText().equals(nombres.get(i))) {
                    i++;
                    jMenuGastos.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        //jMenu3.setVisible(false);
                        //jMenu4.setVisible(false);
                        jMenuReportes.setVisible(false);
                        //jMenu8.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuGastos.setVisible(false);
                }
                if (jMenuCaja.getText().equals(nombres.get(i))) {
                    i++;
                    jMenuCaja.setVisible(true);
                    if (i == nombres.size()) {
                        //jMenu2.setVisible(false);
                        //jMenu12.setVisible(false);
                        JMenuInsumos.setVisible(false);
                        jMenuEmpleados.setVisible(false);
                        jMenuReportes.setVisible(false);
                        jMenuGastos.setVisible(false);
                        jMenuConfiguracion.setVisible(false);
                        break;
                    }
                } else {
                    jMenuCaja.setVisible(false);
                }

                if (jMenuConfiguracion.getText().equals(nombres.get(i))) {
                    i++;
                    jMenuConfiguracion.setVisible(true);
                    //jMenuItem31.setVisible(false);
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
                    jMenuConfiguracion.setVisible(false);
                }

                if (user.equals(nombres.get(i))) {
                    i++;
                    if (!jMenuConfiguracion.isVisible()) {
                        jMenuConfiguracion.setVisible(true);
                        //jMenuItem31.setVisible(true);
                        jMenuItem34.setVisible(true);
                        jMenuItem35.setVisible(true);
                        jMenuItem27.setVisible(false);
                    } else {
                        //jMenuItem31.setVisible(true);
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
                    //jMenuItem31.setVisible(false);
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
            jMenuCompras.setEnabled(false);
            jMenuVentas.setEnabled(false);
            JMenuInsumos.setEnabled(false);
            jMenuEmpleados.setEnabled(false);
            jMenuReportes.setEnabled(false);
            jMenuGastos.setEnabled(false);
            jMenuConfiguracion.setEnabled(false);
            jMenuAdministrar.setEnabled(false);
//            jMenuItem12.setEnabled(false);
//            jMenuItem30.setEnabled(false);
//            jMenuItem29.setEnabled(false);
            jButtonVentas.setEnabled(false);
            jButtonCompras.setEnabled(false);
            jButtonGastos.setEnabled(false);
            jButtonProveedores.setEnabled(false);
            jButtonInsumos.setEnabled(false);
        }
    }

    public void VerificarCajaVisible() {
//        if (jMenuCaja.isVisible()) {
            VerificarCajaAbierta();
//        } else {
//            jMenuCompras.setEnabled(true);
//            jMenuVentas.setEnabled(true);
//            JMenuInsumos.setEnabled(true);
//            jMenuEmpleados.setEnabled(true);
//            jMenuReportes.setEnabled(true);
//            jMenuGastos.setEnabled(true);
//            jMenuConfiguracion.setEnabled(true);
//            jButtonVentas.setEnabled(true);
//            jButtonCompras.setEnabled(true);
//            jButtonGastos.setEnabled(true);
//            jButtonProveedores.setEnabled(true);
//            jButtonInsumos.setEnabled(true);
//        }
    }

    public void DeshabilitarBotonesCompraVenta() {
        if (jMenuCompras.isVisible() && jMenuItem2.isVisible() && jMenuItem8.isVisible() && !jMenuItem13.isVisible()) {
            jButtonCompras.setVisible(true);
            jButtonProveedores.setVisible(false);
        } else if (jMenuCompras.isVisible() && !jMenuItem2.isVisible() && !jMenuItem8.isVisible() && jMenuItem13.isVisible()) {
            jButtonProveedores.setVisible(true);
            jButtonCompras.setVisible(false);
        } else if (jMenuCompras.isVisible() && jMenuItem2.isVisible() && jMenuItem8.isVisible() && jMenuItem13.isVisible()) {
            jButtonProveedores.setVisible(true);
            jButtonCompras.setVisible(true);
        } else if (!jMenuCompras.isVisible()) {
            jButtonProveedores.setVisible(false);
            jButtonCompras.setVisible(false);
        }

        if (JMenuInsumos.isVisible()) {
            jButtonInsumos.setVisible(true);
        } else {
            jButtonInsumos.setVisible(false);
        }

        if (!jMenuVentas.isVisible()) {
            jButtonVentas.setVisible(false);
        } else if (jMenuVentas.isVisible() && !jMenuItem20.isVisible() && !jMenuItem19.isVisible() && jMenuItem28.isVisible() && jMenuItem33.isVisible()
                && jMenuItem1.isVisible()) {
            jButtonVentas.setVisible(false);
        } else if (jMenuVentas.isVisible() && !jMenuItem20.isVisible() && !jMenuItem19.isVisible() && !jMenuItem28.isVisible() && !jMenuItem33.isVisible()
                && jMenuItem1.isVisible()) {
            jButtonVentas.setVisible(false);
        } else if (jMenuVentas.isVisible() && !jMenuItem20.isVisible() && !jMenuItem19.isVisible() && jMenuItem28.isVisible() && jMenuItem33.isVisible()
                && !jMenuItem1.isVisible()) {
            jButtonVentas.setVisible(false);
        } else if (jMenuVentas.isVisible() && jMenuItem20.isVisible() && jMenuItem19.isVisible()) {
            jButtonVentas.setVisible(true);
        }

        if (jMenuCaja.isVisible()) {
            jButtonCaja.setVisible(true);
        } else {
            jButtonCaja.setVisible(false);
        }

        if (jMenuGastos.isVisible()) {
            jButtonGastos.setVisible(true);
        } else {
            jButtonGastos.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jButtonInsumos = new javax.swing.JButton();
        jButtonProveedores = new javax.swing.JButton();
        jButtonGastos = new javax.swing.JButton();
        jButtonCaja = new javax.swing.JButton();
        jButtonCompras = new javax.swing.JButton();
        jButtonVentas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuCompras = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuVentas = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        JMenuInsumos = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuEmpleados = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuReportes = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuGastos = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuCaja = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuConfiguracion = new javax.swing.JMenu();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuAdministrar = new javax.swing.JMenu();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuSalir = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jDesktopPane1.setBackground(new java.awt.Color(255, 248, 177));
        jDesktopPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDesktopPane1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jDesktopPane1.setPreferredSize(new java.awt.Dimension(1330, 730));

        jButtonInsumos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButtonInsumos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/insumos48.png"))); // NOI18N
        jButtonInsumos.setText("Insumos");
        jButtonInsumos.setPreferredSize(new java.awt.Dimension(143, 41));
        jButtonInsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsumosActionPerformed(evt);
            }
        });

        jButtonProveedores.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButtonProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuProveedor.png"))); // NOI18N
        jButtonProveedores.setText("Proveedores");
        jButtonProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProveedoresActionPerformed(evt);
            }
        });

        jButtonGastos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButtonGastos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gastos_48.png"))); // NOI18N
        jButtonGastos.setText("Gastos");
        jButtonGastos.setPreferredSize(new java.awt.Dimension(143, 41));
        jButtonGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGastosActionPerformed(evt);
            }
        });

        jButtonCaja.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButtonCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/caja48.png"))); // NOI18N
        jButtonCaja.setText("Caja");
        jButtonCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCajaActionPerformed(evt);
            }
        });

        jButtonCompras.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButtonCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuCompras48.png"))); // NOI18N
        jButtonCompras.setText("Compras");
        jButtonCompras.setPreferredSize(new java.awt.Dimension(143, 41));
        jButtonCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComprasActionPerformed(evt);
            }
        });

        jButtonVentas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jButtonVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonventa48.png"))); // NOI18N
        jButtonVentas.setText("Ventas");
        jButtonVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVentasActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel1.setText("Usuario:");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel2.setText("Usuario Conectado");
        jLabel2.setToolTipText("");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/DeliveryTazmania_main.png"))); // NOI18N

        jDesktopPane1.setLayer(jButtonInsumos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButtonProveedores, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButtonGastos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButtonCaja, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButtonCompras, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButtonVentas, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
                    .addComponent(jButtonCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsumos, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jButtonVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jButtonCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonInsumos, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jScrollPane1.setViewportView(jDesktopPane1);

        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(50, 60));

        jMenuCompras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuCompras.png"))); // NOI18N
        jMenuCompras.setText("Compras");
        jMenuCompras.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenuCompras.setPreferredSize(new java.awt.Dimension(125, 19));

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem2.setText("Comprar Insumos");
        jMenuItem2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem2.setPreferredSize(new java.awt.Dimension(260, 27));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuCompras.add(jMenuItem2);

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem8.setText("Listado de Compras");
        jMenuItem8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenuCompras.add(jMenuItem8);

        jMenuItem13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem13.setText("Administrar Proveedores");
        jMenuItem13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem13.setPreferredSize(new java.awt.Dimension(325, 25));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenuCompras.add(jMenuItem13);

        jMenuBar1.add(jMenuCompras);

        jMenuVentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonventa.png"))); // NOI18N
        jMenuVentas.setText("Ventas");
        jMenuVentas.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenuVentas.setHideActionText(true);
        jMenuVentas.setPreferredSize(new java.awt.Dimension(105, 19));

        jMenuItem20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem20.setText("Vender Productos");
        jMenuItem20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenuVentas.add(jMenuItem20);

        jMenuItem19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem19.setText("Listado de Ventas");
        jMenuItem19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenuVentas.add(jMenuItem19);

        jMenuItem28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem28.setText("Administrar Productos");
        jMenuItem28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenuVentas.add(jMenuItem28);

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem1.setText("Administrar Clientes");
        jMenuItem1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuVentas.add(jMenuItem1);

        jMenuBar1.add(jMenuVentas);

        JMenuInsumos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JMenuInsumos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/insumos32.png"))); // NOI18N
        JMenuInsumos.setText("Insumos");
        JMenuInsumos.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        JMenuInsumos.setPreferredSize(new java.awt.Dimension(123, 19));

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem4.setText("Administrar Insumos");
        jMenuItem4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem4.setPreferredSize(new java.awt.Dimension(295, 27));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        JMenuInsumos.add(jMenuItem4);

        jMenuBar1.add(JMenuInsumos);

        jMenuEmpleados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuEmpleados.png"))); // NOI18N
        jMenuEmpleados.setText("Empleados");
        jMenuEmpleados.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenuEmpleados.setPreferredSize(new java.awt.Dimension(142, 19));

        jMenuItem14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem14.setText("Administrar Empleados");
        jMenuItem14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenuEmpleados.add(jMenuItem14);

        jMenuItem16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem16.setText("Administrar Asistencias");
        jMenuItem16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenuEmpleados.add(jMenuItem16);

        jMenuItem11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem11.setText("Consumos de Empleados");
        jMenuItem11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenuEmpleados.add(jMenuItem11);

        jMenuBar1.add(jMenuEmpleados);

        jMenuReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuReportes.png"))); // NOI18N
        jMenuReportes.setText("Reportes");
        jMenuReportes.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenuReportes.setMaximumSize(new java.awt.Dimension(125, 32767));
        jMenuReportes.setPreferredSize(new java.awt.Dimension(125, 19));

        jMenuItem22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem22.setText("Ventas Diarias");
        jMenuItem22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenuReportes.add(jMenuItem22);

        jMenuItem23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem23.setText("Ventas Por Semana");
        jMenuItem23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenuReportes.add(jMenuItem23);

        jMenuItem24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem24.setText("Ventas Por Mes");
        jMenuItem24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenuReportes.add(jMenuItem24);

        jMenuItem25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem25.setText("Ventas Por Año");
        jMenuItem25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenuReportes.add(jMenuItem25);

        jMenuBar1.add(jMenuReportes);

        jMenuGastos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuGastos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gastos_32.png"))); // NOI18N
        jMenuGastos.setText("Gastos");
        jMenuGastos.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem9.setText("Administrar Gastos");
        jMenuItem9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenuGastos.add(jMenuItem9);

        jMenuBar1.add(jMenuGastos);

        jMenuCaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/caja.png"))); // NOI18N
        jMenuCaja.setText("Caja");
        jMenuCaja.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N

        jMenuItem18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem18.setText("Abrir Caja");
        jMenuItem18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenuCaja.add(jMenuItem18);

        jMenuItem12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem12.setText("Cerrar Caja");
        jMenuItem12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenuCaja.add(jMenuItem12);

        jMenuItem30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem30.setText("Movimientos Caja");
        jMenuItem30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenuCaja.add(jMenuItem30);

        jMenuBar1.add(jMenuCaja);

        jMenuConfiguracion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jmenuAdmin.png"))); // NOI18N
        jMenuConfiguracion.setText("Configuración");
        jMenuConfiguracion.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenuConfiguracion.setPreferredSize(new java.awt.Dimension(175, 19));

        jMenuItem27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem27.setText("Gestionar Permisos de Pantallas");
        jMenuItem27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem27.setPreferredSize(new java.awt.Dimension(390, 27));
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenuConfiguracion.add(jMenuItem27);

        jMenuItem34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem34.setText("Administrar Usuarios");
        jMenuItem34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem34.setPreferredSize(new java.awt.Dimension(290, 27));
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenuConfiguracion.add(jMenuItem34);

        jMenuItem35.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem35.setText("Tipos Usuarios");
        jMenuItem35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem35.setPreferredSize(new java.awt.Dimension(290, 27));
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenuConfiguracion.add(jMenuItem35);

        jMenuBar1.add(jMenuConfiguracion);

        jMenuAdministrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuAdministrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tools32.png"))); // NOI18N
        jMenuAdministrar.setText("Administrar");
        jMenuAdministrar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N

        jMenuItem33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem33.setText("Categorias Productos");
        jMenuItem33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem33.setPreferredSize(new java.awt.Dimension(320, 27));
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenuAdministrar.add(jMenuItem33);

        jMenuItem29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem29.setText("Tipos Movimientos Caja");
        jMenuItem29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem29.setPreferredSize(new java.awt.Dimension(320, 27));
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenuAdministrar.add(jMenuItem29);

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem5.setText("Tipos Insumos");
        jMenuItem5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem5.setPreferredSize(new java.awt.Dimension(320, 27));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenuAdministrar.add(jMenuItem5);

        jMenuItem15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem15.setText("Tipos Empleados");
        jMenuItem15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem15.setPreferredSize(new java.awt.Dimension(320, 27));
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenuAdministrar.add(jMenuItem15);

        jMenuBar1.add(jMenuAdministrar);

        jMenuSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout.png"))); // NOI18N
        jMenuSalir.setText("Salir");
        jMenuSalir.setContentAreaFilled(false);
        jMenuSalir.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jMenuSalir.setPreferredSize(new java.awt.Dimension(128, 19));
        jMenuSalir.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuSalirMenuSelected(evt);
            }
        });
        jMenuBar1.add(jMenuSalir);

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
            asistencias = new vLista_Asistencias();
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

    private void jButtonProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProveedoresActionPerformed
        if (proveedores == null || proveedores.isClosed()) {
            proveedores = new vGestion_Proveedores();
            jDesktopPane1.add(proveedores);
            proveedores.toFront();
            proveedores.setVisible(true);
        }
    }//GEN-LAST:event_jButtonProveedoresActionPerformed

    private void jButtonInsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsumosActionPerformed
        if (insumos == null || insumos.isClosed()) {
            insumos = new vGestion_Insumos();
            jDesktopPane1.add(insumos);
            insumos.setVisible(true);
        }
    }//GEN-LAST:event_jButtonInsumosActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        if (listaprov == null || listaprov.isClosed()) {
            listaprov = new vLista_Proveedores();
            jDesktopPane1.add(listaprov);
            listaprov.toFront();
            listaprov.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        if (listaproductos == null || listaproductos.isClosed()) {
            listaproductos = new vLista_Productos();
            jDesktopPane1.add(listaproductos);
            listaproductos.toFront();
            listaproductos.setVisible(true);
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

    private void jButtonGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGastosActionPerformed
        if (egreso == null || egreso.isClosed()) {
            egreso = new vGestion_Egresos();
            jDesktopPane1.add(egreso);
            egreso.setVisible(true);
        }
    }//GEN-LAST:event_jButtonGastosActionPerformed

    private void jButtonCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCajaActionPerformed
        if (abrircaja == null || abrircaja.isClosed()) {
            abrircaja = new vAbrir_Caja();
            jDesktopPane1.add(abrircaja);
            abrircaja.setVisible(true);
        }
    }//GEN-LAST:event_jButtonCajaActionPerformed

    private void jButtonVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVentasActionPerformed
        if (ventas == null || ventas.isClosed()) {
            ventas = new vVentas_Productos();
            jDesktopPane1.add(ventas);
            ventas.setVisible(true);
        }
    }//GEN-LAST:event_jButtonVentasActionPerformed

    private void jButtonComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComprasActionPerformed
        if (compras == null || compras.isClosed()) {
            compras = new vCompras_Insumos();
            jDesktopPane1.add(compras);
            compras.setVisible(true);
            compras.toFront();
        }
    }//GEN-LAST:event_jButtonComprasActionPerformed

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
            consumos = new vLista_ConsumosEmpleados();
            jDesktopPane1.add(consumos);
            consumos.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuSalirMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenuSalirMenuSelected
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
    }//GEN-LAST:event_jMenuSalirMenuSelected

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
    public static javax.swing.JMenu JMenuInsumos;
    public static javax.swing.JButton jButtonCaja;
    public static javax.swing.JButton jButtonCompras;
    public static javax.swing.JButton jButtonGastos;
    public static javax.swing.JButton jButtonInsumos;
    public static javax.swing.JButton jButtonProveedores;
    public static javax.swing.JButton jButtonVentas;
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenuAdministrar;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JMenu jMenuCaja;
    public static javax.swing.JMenu jMenuCompras;
    public static javax.swing.JMenu jMenuConfiguracion;
    public static javax.swing.JMenu jMenuEmpleados;
    public static javax.swing.JMenu jMenuGastos;
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
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    public static javax.swing.JMenu jMenuReportes;
    public static javax.swing.JMenu jMenuSalir;
    public static javax.swing.JMenu jMenuVentas;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
