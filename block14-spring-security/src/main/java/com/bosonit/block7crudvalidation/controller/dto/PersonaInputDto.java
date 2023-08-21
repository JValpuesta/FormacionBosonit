package com.bosonit.block7crudvalidation.controller.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaInputDto {
    String usuario;
    String password;
    String name;
    String surname;
    String companyEmail;
    String personalEmail;
    String city;
    Boolean active;
    Date createdDate;
    String imagenUrl;
    Date terminationDate;
    String role;
}

