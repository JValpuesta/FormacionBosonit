package com.bosonit.block7crudvalidation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorSimpleODTO implements ProfesorDTOInterface{

    int idProfesor;
    int idPersona;
    String coments;
    String branch;

}
