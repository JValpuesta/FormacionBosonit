package com.bosonit.block7crudvalidation.controller.dto;

import com.bosonit.block7crudvalidation.domain.Estudiante;
import com.bosonit.block7crudvalidation.domain.Profesor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaProfesorODTO implements PersonaDTOInterface{

    PersonaOutputDto personaOutputDto;
    ProfesorSimpleODTO profesorSimpleODTO;
    List<EstudianteSimpleODTO> estudianteSimpleODTO;

    public PersonaProfesorODTO(Profesor profesor, List<Estudiante> estudiantes){
        this.personaOutputDto = profesor.getPersona().personaToPersonaOutputDto();
        this.estudianteSimpleODTO = estudiantes.stream().map(Estudiante::estudianteToEstudianteSimlpeODTO).toList();
        this.profesorSimpleODTO = profesor.profesorToProfesorSimlpeODTO();
    }

}
