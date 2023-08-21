package com.bosonit.block7crudvalidation.domain;

import com.bosonit.block7crudvalidation.controller.dto.PersonaInputDto;
import com.bosonit.block7crudvalidation.controller.dto.PersonaOutputDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue
    int id;
    @Column(nullable = false, length = 10)
    String usuario;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String name;
    String surname;
    @Column(nullable = false)
    String companyEmail;
    @Column(nullable = false)
    String personalEmail;
    @Column(nullable = false)
    String city;
    @Column(nullable = false)
    Boolean active;
    @Column(nullable = false)
    Boolean admin;
    @Column(nullable = false)
    Date createdDate;
    String imagenUrl;
    Date terminationDate;
    String role;

    public Persona(PersonaInputDto personaInputDto) {
        this.usuario = personaInputDto.getUsuario();
        this.password = personaInputDto.getPassword();
        this.name = personaInputDto.getName();
        this.surname = personaInputDto.getCity();
        this.companyEmail = personaInputDto.getCompanyEmail();
        this.personalEmail = personaInputDto.getPersonalEmail();
        this.city = personaInputDto.getCity();
        this.active = personaInputDto.getActive();
        this.createdDate = personaInputDto.getCreatedDate();
        this.imagenUrl = personaInputDto.getImagenUrl();
        this.terminationDate = personaInputDto.getTerminationDate();
        this.role = personaInputDto.getRole();
        this.setAdmin(personaInputDto.getRole().equals("ADMIN"));
    }

    public PersonaOutputDto personaToPersonaOutputDto() {
        return new PersonaOutputDto(
                this.id,
                this.usuario,
                this.name,
                this.surname,
                this.companyEmail,
                this.personalEmail,
                this.city,
                this.active,
                this.createdDate,
                this.imagenUrl,
                this.terminationDate
        );
    }



}
