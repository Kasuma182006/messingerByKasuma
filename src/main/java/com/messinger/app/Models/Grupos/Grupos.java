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
    private String id_grupo;
    
    private String nombre_grupo;
}
