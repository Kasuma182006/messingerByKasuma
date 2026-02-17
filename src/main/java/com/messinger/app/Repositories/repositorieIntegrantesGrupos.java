package com.messinger.app.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.messinger.app.Models.IntegratesGrupos.IntegrantesGrupos;
import com.messinger.app.Models.IntegratesGrupos.IntegrantesDTO;

public interface repositorieIntegrantesGrupos extends JpaRepository<IntegrantesGrupos,Long>{
    
    @Query("SELECT new com.messinger.app.Models.IntegratesGrupos.IntegrantesDTO (u.id_usuario) from IntegrantesGrupos u where id_grupo = :id_grupo")
    List<IntegrantesDTO> buscarIntegrantes(@Param("id_grupo") Integer id_grupo);
}
 



