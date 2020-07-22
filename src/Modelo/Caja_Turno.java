/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author CRISTIAN
 */
public class Caja_Turno extends Caja {
    private int idcajaturno; 
    private int idturno;   

    public Caja_Turno() {
    }

    public Caja_Turno(int idcajaturno, int idturno, int idcaja, int idusuario, Timestamp fecha_apertura, Timestamp fecha_cierre, String nota, float monto, String estado, int activo) {
        super(idcaja,idusuario,fecha_apertura,fecha_cierre,nota,estado,monto,activo);
        this.idcajaturno = idcajaturno;
        this.idturno = idturno;        
    }

    public int getIdcajaturno() {
        return idcajaturno;
    }

    public void setIdcajaturno(int idcajaturno) {
        this.idcajaturno = idcajaturno;
    }

    public int getIdTurno() {
        return idturno;
    }

    public void setIdTurno(int idturno) {
        this.idturno = idturno;
    }    
}
