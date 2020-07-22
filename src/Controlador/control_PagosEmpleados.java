/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vistas.vPagos_Empleados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;

/**
 *
 * @author CRISTIAN
 */
public class control_PagosEmpleados implements ActionListener,FocusListener{
    vPagos_Empleados vista_e = new vPagos_Empleados();
    
    public control_PagosEmpleados(vPagos_Empleados vista_e){
        this.vista_e = vista_e;
    }
    
    private float calcularTotales(){
        float total_cajeros = Float.valueOf(vista_e.jTextCajero.getText());
        float total_cocineros = Float.valueOf(vista_e.jTextCocinero.getText());
        float total_pizzeros = Float.valueOf(vista_e.jTextPizzero.getText());
        float total_cadete1 = Float.valueOf(vista_e.jTextCadete1.getText());
        float total_cadete2 = Float.valueOf(vista_e.jTextCadete2.getText());
        float total_cadete3 = Float.valueOf(vista_e.jTextCadete3.getText());
        float total_cadete4 = Float.valueOf(vista_e.jTextCadete4.getText());
        float total = total_cajeros+total_cocineros+total_pizzeros+total_cadete1+total_cadete2+total_cadete3+total_cadete4;
       return total;
     }
    
    @Override
    public void actionPerformed(ActionEvent e) {       
        if(e.getSource()==vista_e.jTextCajero || e.getSource()==vista_e.jTextCocinero || 
                e.getSource()==vista_e.jTextPizzero || e.getSource()==vista_e.jTextCadete1 || 
                e.getSource()==vista_e.jTextCadete2 || e.getSource()==vista_e.jTextCadete3 ||
                e.getSource()==vista_e.jTextCadete4){           
            float total= calcularTotales();
            vista_e.jTexTotal.setText(Float.toString(total));
        }    
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource()==vista_e.jTextCajero || e.getSource()==vista_e.jTextCocinero || 
                e.getSource()==vista_e.jTextPizzero || e.getSource()==vista_e.jTextCadete1 || 
                e.getSource()==vista_e.jTextCadete2 || e.getSource()==vista_e.jTextCadete3 ||
                e.getSource()==vista_e.jTextCadete4){           
            float total= calcularTotales();
            vista_e.jTexTotal.setText(Float.toString(total));
        }  
    }

    @Override
    public void focusLost(FocusEvent e) {
         if(e.getSource()==vista_e.jTextCajero || e.getSource()==vista_e.jTextCocinero || 
                e.getSource()==vista_e.jTextPizzero || e.getSource()==vista_e.jTextCadete1 || 
                e.getSource()==vista_e.jTextCadete2 || e.getSource()==vista_e.jTextCadete3 ||
                e.getSource()==vista_e.jTextCadete4){           
            float total= calcularTotales();
            vista_e.jTexTotal.setText(Float.toString(total));
        }  
    }
    
}
