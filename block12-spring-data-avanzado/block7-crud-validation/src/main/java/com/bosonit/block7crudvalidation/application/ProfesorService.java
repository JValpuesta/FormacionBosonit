package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.bosonit.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.bosonit.block7crudvalidation.domain.Profesor;

public interface ProfesorService {

    ProfesorOutputDto addProfesor(ProfesorInputDto profesor);
    Profesor getProfesorById(int id);
    ProfesorOutputDto updateProfesor(int id, ProfesorInputDto profesor);
    void deleteProfesorById(int id);
    Iterable<ProfesorOutputDto> getAllProfesores(int pageNumber, int pageSize);

}
