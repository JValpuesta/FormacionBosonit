package com.bosonit.block7crudvalidation.controller.dto;

import com.bosonit.block7crudvalidation.domain.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaEstudianteODTO implements PersonaDTOInterface{

    PersonaOutputDto personaOutputDto;
    EstudianteSimpleODTO estudianteSimpleODTO;
    ProfesorSimpleODTO profesorSimpleODTO;

    public PersonaEstudianteODTO(Estudiante estudiante){
        this.personaOutputDto = estudiante.getPersona().personaToPersonaOutputDto();
        this.estudianteSimpleODTO = estudiante.estudianteToEstudianteSimlpeODTO();
        this.profesorSimpleODTO = estudiante.getProfesor().profesorToProfesorSimlpeODTO();
    }

}
