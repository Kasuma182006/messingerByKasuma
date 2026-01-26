package com.messinger.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.messinger.app.Models.usuario.Usuario;

@Repository
public interface repositorieUsuario  extends JpaRepository<Usuario,String>     {
    Usuario findByIdAndContrasena(String id, String contrasena);
    
}   
