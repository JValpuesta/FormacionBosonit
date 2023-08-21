package com.bosonit.block7crud.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaInputDto {
    int id;
    String nombre;
    String edad;
    String poblacion;
}
