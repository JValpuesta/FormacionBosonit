package com.bosonit.tripbackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteInputDto {
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private Integer telefono;
}
