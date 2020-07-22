/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vistas.vCerrar_Caja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author CRISTIAN
 */
public class control_CerrarCaja implements ActionListener,FocusListener{
    vCerrar_Caja vista_cc = new vCerrar_Caja();

    public control_CerrarCaja(vCerrar_Caja vista_cc){
        this.vista_cc = vista_cc;
    }
    
    private float calcularTotales(){
        float cajaChica = Float.valueOf(vista_cc.jTextCajaChica.getText());
        float totalVenta = Float.valueOf(vista_cc.jTextVentas.getText());
        float totalGastos = Float.valueOf(vista_cc.jTextGastos.getText());
        float totalPagosEmpleados = Float.valueOf(vista_cc.jTextEmpleados.getText());
        float totalVentasLocal = Float.valueOf(vista_cc.jTextVentasLocal.getText());
        float totalVentasOnline = Float.valueOf(vista_cc.jTextVentasOnline.getText());
        
        float total = cajaChica/*+totalVenta*/-totalGastos-totalPagosEmpleados+totalVentasLocal+totalVentasOnline;
        return total;
    }
    
    private void mostrarTotal(){
        float total= calcularTotales();
        System.out.println("total" +total);
        vista_cc.jTextTotal.setText(Float.toString(total));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista_cc.jTextCajaChica || e.getSource()==vista_cc.jTextVentas || 
                e.getSource()==vista_cc.jTextGastos || e.getSource()==vista_cc.jTextEmpleados ||
                e.getSource()==vista_cc.jTextVentasLocal.getText() || e.getSource()==vista_cc.jTextVentasOnline.getText()){
            mostrarTotal();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
         if(e.getSource()==vista_cc.jTextCajaChica || e.getSource()==vista_cc.jTextVentas || 
                e.getSource()==vista_cc.jTextGastos || e.getSource()==vista_cc.jTextEmpleados ||
                 e.getSource()==vista_cc.jTextVentasLocal.getText() || e.getSource()==vista_cc.jTextVentasOnline.getText()){
            mostrarTotal();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource()==vista_cc.jTextCajaChica || e.getSource()==vista_cc.jTextVentas || 
                e.getSource()==vista_cc.jTextGastos || e.getSource()==vista_cc.jTextEmpleados ||
                 e.getSource()==vista_cc.jTextVentasLocal.getText() || e.getSource()==vista_cc.jTextVentasOnline.getText()){
            mostrarTotal();
        }
    }

    /*@Override
    public void insertUpdate(DocumentEvent e) {
        if(e.getSource()==vista_cc.jTextCajaChica || e.getSource()==vista_cc.jTextVentas || 
                e.getSource()==vista_cc.jTextGastos || e.getSource()==vista_cc.jTextEmpleados){
            mostrarTotal();
        }   
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
