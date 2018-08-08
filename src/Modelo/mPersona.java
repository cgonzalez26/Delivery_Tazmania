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
public class mPersona {
    private int id_persona;
    private String nombre_persona;
    private String apellido_persona;
    private String domicilio_persona;
    private String telefonoContacto_persona;
    private String emailContacto_persona;
    
    //agregue los atributos mas relevantes para la identificacion de una persona.
    
    public mPersona() {

    }

    public mPersona(int id_persona,String nombre_persona, String apellido_persona, String domicilio_persona, String telefonoContacto_persona, String emailContacto_persona) {
        this.nombre_persona = nombre_persona;
        this.apellido_persona = apellido_persona;
        this.domicilio_persona = domicilio_persona;
        this.telefonoContacto_persona = telefonoContacto_persona;
        this.emailContacto_persona = emailContacto_persona;   
        this.id_persona= id_persona;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre_persona() {
        return nombre_persona;
    }

    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
    }

    public String getApellido_persona() {
        return apellido_persona;
    }

    public void setApellido_persona(String apellido_persona) {
        this.apellido_persona = apellido_persona;
    }

    public String getDomicilio_persona() {
        return domicilio_persona;
    }

    public void setDomicilio_persona(String domicilio_persona) {
        this.domicilio_persona = domicilio_persona;
    }

    public String getTelefonoContacto_persona() {
        return telefonoContacto_persona;
    }

    public void setTelefonoContacto_persona(String telefonoContacto_persona) {
        this.telefonoContacto_persona = telefonoContacto_persona;
    }

    public String getEmailContacto_persona() {
        return emailContacto_persona;
    }

    public void setEmailContacto_persona(String emailContacto_persona) {
        this.emailContacto_persona = emailContacto_persona;
    }

}
