package com.bosonit.block7crudvalidation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorOutputDto implements ProfesorDTOInterface{
    int idProfesor;
    PersonaOutputDto persona;
    String coments;
    String branch;

    public ProfesorOutputDto(ProfesorOutputDto profesorOutputDto) {
        setIdProfesor(profesorOutputDto.getIdProfesor());
        setPersona(profesorOutputDto.getPersona());
        setComents(profesorOutputDto.getComents());
        setBranch(profesorOutputDto.getBranch());
    }

}
