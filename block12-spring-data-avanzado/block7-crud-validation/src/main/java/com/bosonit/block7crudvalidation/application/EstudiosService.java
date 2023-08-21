package com.bosonit.block7crudvalidation.application;


import com.bosonit.block7crudvalidation.controller.dto.EstudiosInputDto;
import com.bosonit.block7crudvalidation.controller.dto.EstudiosOutputDto;

import java.util.List;

public interface EstudiosService {
    EstudiosOutputDto addEstudios(EstudiosInputDto estudios);
    EstudiosOutputDto getEstudiosById(int id);
    List<EstudiosOutputDto> getEstudiosByEstudiante(int id);
    EstudiosOutputDto updateEstudios(int id, EstudiosInputDto estudios);
    void deleteEstudiosById( int id);
    Iterable<EstudiosOutputDto> getAllEstudios(int pageNumber, int pageSize);
}
