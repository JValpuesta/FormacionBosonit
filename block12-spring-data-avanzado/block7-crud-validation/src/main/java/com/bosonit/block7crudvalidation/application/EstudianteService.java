package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.EstudianteInputDto;
import com.bosonit.block7crudvalidation.controller.dto.EstudianteOutputDto;
import com.bosonit.block7crudvalidation.domain.Estudiante;
import com.bosonit.block7crudvalidation.domain.Profesor;

import java.util.List;

public interface EstudianteService {

    EstudianteOutputDto addEstudiante(EstudianteInputDto persona);
    EstudianteOutputDto addAsignaturasEstudiante(int id, List<Integer> asignaturas);
    Estudiante getEstudianteById(int id);
    List<Estudiante> getEstudianteByProfesor(Profesor profesor);
    EstudianteOutputDto updateEstudiante(int id, EstudianteInputDto persona);
    void deleteEstudianteById(int id);
    Iterable<EstudianteOutputDto> getAllEstudiantes(int pageNumber, int pageSize);

}
