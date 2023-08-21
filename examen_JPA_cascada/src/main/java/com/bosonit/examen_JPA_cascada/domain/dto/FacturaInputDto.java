package com.bosonit.examen_JPA_cascada.domain.dto;

import com.bosonit.examen_JPA_cascada.domain.Cliente;
import com.bosonit.examen_JPA_cascada.domain.LineasFra;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaInputDto {

    Integer idCli;
    double importeFra;
    List<LineaInputDto> lineas;

}