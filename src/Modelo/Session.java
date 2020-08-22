package Modelo;

/**
 *
 * @author CRISTIAN
 */
public class Session {
    public static int idusuario = 0;
    public static String login = "";
    public static int idcaja_abierta = 0;
    public static int idcajaturno_abierta = 0;
    public static int idturno_abierto = 0;
    public static boolean editadoPagoEmpleados = false;
    public static float fCajero = 0;
    public static float fCocinero = 0;
    public static float fPizzero = 0;
    public static float fCadete1 = 0;
    public static float fCadete2 = 0;
    public static float fCadete3 = 0;
    public static float fCadete4 = 0;
    
    public Session() {
    }

    public static int getIdusuario() {
        return idusuario;
    }

    public static void setIdusuario(int idusuario) {
        Session.idusuario = idusuario;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Session.login = login;
    }

    public static int getIdcaja_abierta() {
        return idcaja_abierta;
    }

    public static void setIdcaja_abierta(int idcaja_abierta) {
        Session.idcaja_abierta = idcaja_abierta;
    }

    public static int getIdturno_abierto() {
        return idturno_abierto;
    }

    public static void setIdturno_abierto(int idturno_abierto) {
        Session.idturno_abierto = idturno_abierto;
    }  

    public static int getIdcajaturno_abierta() {
        return idcajaturno_abierta;
    }

    public static void setIdcajaturno_abierta(int idcajaturno_abierta) {
        Session.idcajaturno_abierta = idcajaturno_abierta;
    }

    public static boolean isEditadoPagoEmpleados() {
        return editadoPagoEmpleados;
    }

    public static void setEditadoPagoEmpleados(boolean editadoPagoEmpleados) {
        Session.editadoPagoEmpleados = editadoPagoEmpleados;
    }
    
}
