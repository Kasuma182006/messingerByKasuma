package com.messinger.app.Controllers;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.messinger.app.Models.contactos.contactos;
import com.messinger.app.Models.contactos.contactosDTO;
import com.messinger.app.Models.menssages.Mensajes;
import com.messinger.app.Models.menssages.MessagesModel;
import com.messinger.app.Models.usuario.Usuario;
import com.messinger.app.Models.usuario.modelRegistro;
import com.messinger.app.Repositories.MensajeRepositorie;
import com.messinger.app.Repositories.contactoRepositorie;
import com.messinger.app.Repositories.repositorieUsuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class ControllerUsuario {

    public repositorieUsuario repositorieusuario;
    public contactoRepositorie contactoRepositorie;
    public MensajeRepositorie mensajesRepositorie;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ControllerUsuario(repositorieUsuario repositorieusuario,
                             contactoRepositorie contactoRepositorie, MensajeRepositorie mensajeRepositorie) {
        this.repositorieusuario = repositorieusuario;
        this.contactoRepositorie = contactoRepositorie;
        this.mensajesRepositorie = mensajeRepositorie;
    }


    @GetMapping("/")
    public String raiz (){
        return "index";
    }

    @GetMapping("/registroVista")
    public String registroVista(){
        return "registro";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, HttpServletRequest request, RedirectAttributes redirect){
        Usuario user = repositorieusuario.findByIdAndContrasena(usuario.getId(), usuario.getContrasena());
        HttpSession sesion = request.getSession(false);
        if (user != null){

            if (sesion != null) {
                sesion.invalidate();
            }


            HttpSession nuevaSesion = request.getSession(true);
            nuevaSesion.setAttribute("nombreUsuario", user.getNombre());
            nuevaSesion.setAttribute("id", user.getId());


            return "redirect:/chat";
        }
        else {
            redirect.addFlashAttribute("error", "El número de telefono o contraseña son incorrectos, por favor vuelve a intentarlo");
            return "redirect:/";
        }
    }


    @PostMapping("/registro")
    public String registro (@ModelAttribute modelRegistro registro, RedirectAttributes redirect){

       Usuario usuarioNuevo = new Usuario();

       usuarioNuevo.setId(registro.getId());
       usuarioNuevo.setNombre(registro.getNombre());
       usuarioNuevo.setContrasena(registro.getContrasena());

       repositorieusuario.save(usuarioNuevo);

        redirect.addFlashAttribute("registro", "El usuario se ha registrado con éxito, por favor ingrese su usuario y contraseña");
        return "redirect:/";
    }


    @GetMapping("/chat")
    public String chat(HttpServletRequest request, RedirectAttributes redirect, Model modelo){

        HttpSession sesion = request.getSession(false);

        if (sesion != null && sesion.getAttribute("nombreUsuario") != null){
            String nombre = (String) sesion.getAttribute("nombreUsuario");
            String idUsuario=(String) sesion.getAttribute("id");
            
            List<contactosDTO> contactos = contactoRepositorie.contactos(idUsuario);

            modelo.addAttribute("nombreUsuario", nombre);
            modelo.addAttribute ("contactos",contactos);

            System.out.println(contactos);


            return "chat";
        }

        else {
            
            redirect.addFlashAttribute("sesionInvalida", "Se ha cerrrado la ultima sesión, por favor vuelva a iniciar sesión");
            return "redirect:/";
        }
      
    }


    @MessageMapping("/mensajes")
    public void mensajes(MessagesModel mensaje, SimpMessageHeaderAccessor accessor) {


        // 1) intentar obtener Principal (establecido por el HandshakeHandler)
        Principal principal = accessor.getUser();
        String autenticacion = null;
        if (principal != null) {
            autenticacion = principal.getName();
        } else {
            // 2) fallback a tu atributo de sesión (por compatibilidad)
            Object idAttr = accessor.getSessionAttributes() != null ? accessor.getSessionAttributes().get("id") : null;
            if (idAttr != null) {
                autenticacion = idAttr.toString();
            }
        }

     
     
     
     
        System.out.println("usuario autenticado " + autenticacion + "," + mensaje.destinatario() + "," + mensaje.mensaje());

        if (autenticacion != null) {
            messagingTemplate.convertAndSendToUser(mensaje.destinatario(), "/queue/messages", mensaje.mensaje());
            
            Mensajes newMensaje = new Mensajes();

            newMensaje.setId_emisor(autenticacion);
            newMensaje.setId_receptor(mensaje.destinatario());
            newMensaje.setHora(mensaje.hora());
            newMensaje.setMensaje(mensaje.mensaje());

            mensajesRepositorie.save(newMensaje);

        } else {
            System.out.println("El usuario no está autenticado");
        }
    }

    @MessageMapping("/grupoMensajes")
    

    @PostMapping("/agregarContacto")
    public String agregarContacto(@ModelAttribute contactos contacto, HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null){
            return "redirec:/";
        }
        
        contactos newContacto = new contactos();
        newContacto.setId_contacto(contacto.getId_contacto());
        newContacto.setId_usuario((String)sesion.getAttribute("id"));
        contactoRepositorie.save(newContacto);
        return "redirect:/chat";
    }
    
    
}