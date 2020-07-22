/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Colo-PC
 */
public class Empleados extends Personas {
    
    int idempleados;
    int idtipoempleado;
    int idtipodocumento;
    int activo;

    public Empleados() {
    }

    public Empleados(int idempleados, int idtipoempleado, int idtipodocumento, int activo) {
        this.idempleados = idempleados;
        this.idtipoempleado = idtipoempleado;
        this.idtipodocumento = idtipodocumento;
        this.activo= activo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getIdempleados() {
        return idempleados;
    }

    public void setIdempleados(int idempleados) {
        this.idempleados = idempleados;
    }

    public int getIdtipoempleado() {
        return idtipoempleado;
    }

    public void setIdtipoempleado(int idtipoempleado) {
        this.idtipoempleado = idtipoempleado;
    }

    public int getIdtipodocumento() {
        return idtipodocumento;
    }

    public void setIdtipodocumento(int idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }
}
