package com.bosonit.examen_JPA_cascada.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LineaOutputDto {

    Integer id;
    String producto;
    double cantidad;
    double importe;

}
