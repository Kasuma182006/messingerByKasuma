package com.messinger.app.RestControllers;

import org.springframework.web.bind.annotation.RestController;

import com.messinger.app.Models.menssages.MensajesCargarDto;
import com.messinger.app.Repositories.MensajeRepositorie;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class RestControllers {

    MensajeRepositorie mensajerepositorie;

    public RestControllers(MensajeRepositorie mensajeRepositorie){
        this.mensajerepositorie = mensajeRepositorie;
    }

    @GetMapping("/cargarMensajes")
    public List<MensajesCargarDto> cargarMensajes(@RequestParam String contacto, HttpServletRequest request) {


        HttpSession sesion = request.getSession(false);

        List<MensajesCargarDto> mensajesCargardos = null;
        

        
        if (sesion != null && sesion.getAttribute("id") != null){

            mensajesCargardos = mensajerepositorie.cargarMensajes(sesion.getAttribute("id").toString(), contacto);
            
            return mensajesCargardos;
        }

        
        return mensajesCargardos;
        
        
        
    }
    
}
