package Modelo;

/**
 *
 * @author CRISTIAN
 */
public class Usuarios extends Personas{

    private int idusuario;
    private int idtipousuario;
    private String Login;
    private String Password;
    private String Estado;
    private int activo;        
    
    public Usuarios(){
    }
    public Usuarios(int idusuario, int idtipousuario, String Login, String Password,String Estado, int activo) {
        this.idusuario = idusuario;
        this.idtipousuario = idtipousuario;
        this.Login = Login;
        this.Password = Password;
        this.Estado = Estado;
        this.activo = activo;
    }
     
      public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdtipousuario() {
        return idtipousuario;
    }

    public void setIdtipousuario(int idtipousuario) {
        this.idtipousuario = idtipousuario;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
