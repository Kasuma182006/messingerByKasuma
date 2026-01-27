package com.messinger.app.Models.usuario;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.messinger.app.Repositories.repositorieUsuario;

@SpringBootTest
public class UsuarioTests {
    @Autowired
    public repositorieUsuario repositorie;
    
    @Test
    public void usuario() {

        String usuario = "3117302211";
        String contrasena = "kasuma182006";


        Usuario ingreso = repositorie.findByIdAndContrasena(usuario, contrasena);
        assertTrue(ingreso != null, "hay un error, bro");
        System.out.println("Test ejecutado correctamente");


    }
}
