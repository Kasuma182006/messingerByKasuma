package com.messinger.app.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.messinger.app.Models.contactos.contactos;
import com.messinger.app.Models.contactos.contactosDTO;

public interface contactoRepositorie extends JpaRepository<contactos,Long> {

    @Query("SELECT new com.messinger.app.Models.contactos.contactosDTO (u.id_contacto) FROM contactos u WHERE u.id_usuario = :idUsuario")
    List<contactosDTO> contactos(@Param("idUsuario") String idUsuario);
    
}
