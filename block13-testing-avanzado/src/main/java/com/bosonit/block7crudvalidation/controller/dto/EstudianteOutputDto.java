package com.bosonit.block7crudvalidation.controller.dto;

import com.bosonit.block7crudvalidation.domain.Asignatura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteOutputDto implements EstudianteDTOInterface{
    Integer idStudent;
    PersonaOutputDto persona;
    Integer numHoursWeek;
    String coments;
    ProfesorOutputDto profesor;
    String branch;
    List<Asignatura> estudios;

    public EstudianteOutputDto(EstudianteOutputDto estudiante) {
        setIdStudent(estudiante.getIdStudent());
        setPersona(estudiante.getPersona());
        setNumHoursWeek(estudiante.getNumHoursWeek());
        setComents(estudiante.getComents());
        setBranch(estudiante.getBranch());

        List<Asignatura> asignaturas = new ArrayList<>();
        if (estudiante.getEstudios() != null) {
            for (int i = 0; i < estudiante.getEstudios().size(); i++) {
                asignaturas.add(estudiante.getEstudios().get(i));
            }
        }
        setEstudios(asignaturas);
    }

}
