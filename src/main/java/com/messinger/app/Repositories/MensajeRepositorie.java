package com.messinger.app.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.messinger.app.Models.menssages.Mensajes;
import com.messinger.app.Models.menssages.MensajesCargarDto;

public interface MensajeRepositorie extends JpaRepository <Mensajes,Long> {

        @Query("Select new com.messinger.app.Models.menssages.MensajesCargarDto (u.mensaje, u.hora, u.id_emisor) FROM Mensajes u WHERE u.id_emisor = :id_emisor AND u.id_receptor =   :id_receptor OR u.id_emisor = :id_receptor AND u.id_receptor = :id_emisor ORDER BY u.hora ASC" )
        List<MensajesCargarDto> cargarMensajes(@Param ("id_emisor") String id_emisor, @Param ("id_receptor") String id_receptor);
}
