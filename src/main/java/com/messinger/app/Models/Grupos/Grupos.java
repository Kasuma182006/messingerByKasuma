package com.messinger.app.Models.Grupos;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Grupos {
    
    @Id
    private int id_grupo;
    
    private String nombre_grupo;
    private String id_creador;

}
