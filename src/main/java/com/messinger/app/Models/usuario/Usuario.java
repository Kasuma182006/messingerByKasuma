package com.messinger.app.Models.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    @Id 
    private String id;/*En este caso el id es el numero del usuario por lo que no se usa el generateValue (auto incrementable)*/
    
    private String nombre;
    private String contrasena;


}
