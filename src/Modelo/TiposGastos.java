package Modelo;

/**
 *
 * @author Colo-PC
 */
public class TiposGastos {
    
    int idtipogasto;
    String descripcion;
    int activo;

    public TiposGastos() {
    }
    
    public TiposGastos(int idtipogasto, String descripcion, int activo) {
        this.idtipogasto = idtipogasto;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdtipogasto() {
        return idtipogasto;
    }

    public void setIdtipogasto(int idtipogasto) {
        this.idtipogasto = idtipogasto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
