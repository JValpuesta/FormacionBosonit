package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.bosonit.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.bosonit.block7crudvalidation.domain.Profesor;

import java.util.List;

public interface ProfesorService {

    ProfesorOutputDto addProfesor(ProfesorInputDto profesor);
    Profesor getProfesorById(int id);
    ProfesorOutputDto updateProfesor(int id, ProfesorInputDto profesor);
    void deleteProfesorById(int id);
    List<ProfesorOutputDto> getAllProfesores(int pageNumber, int pageSize);

}
