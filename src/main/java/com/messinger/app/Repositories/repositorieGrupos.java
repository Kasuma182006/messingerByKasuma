package com.messinger.app.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.messinger.app.Models.Grupos.Grupos;

public interface repositorieGrupos extends JpaRepository <Grupos,Integer>{
    @Query("Select u FROM Grupos u where  u.id_creador = :id_creador")
    List<Grupos> findGrupos(@Param("id_creador") String id_creador);
}
