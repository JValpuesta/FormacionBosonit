package com.bosonit.examen_JPA_cascada.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaOutputDto {

    Integer id;
    ClienteOutputDto clienteOutputDto;
    double importeFra = 0;
    List<LineaOutputDto> lineaOutputDtoList;

}
