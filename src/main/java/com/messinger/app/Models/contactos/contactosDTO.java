package com.messinger.app.Models.contactos;



public class contactosDTO {
    
    private String idContacto;

    public contactosDTO(String idContacto) {
        this.idContacto = idContacto;
    }

    public String getIdContacto() {
        return idContacto;
    }

    @Override
    public String toString() {
        return "ContactoDTO{idContacto='" + idContacto + "'}";
    }



}
