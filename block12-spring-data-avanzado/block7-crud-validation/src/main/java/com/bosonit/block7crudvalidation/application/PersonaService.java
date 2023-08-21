package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.PersonaDTOInterface;
import com.bosonit.block7crudvalidation.controller.dto.PersonaInputDto;
import com.bosonit.block7crudvalidation.controller.dto.PersonaOutputDto;

import java.util.HashMap;
import java.util.List;

public interface PersonaService {
    PersonaOutputDto addPersona(PersonaInputDto persona);
    PersonaOutputDto getPersonaById(int id);
    PersonaDTOInterface getPersonaTypeById(int id);
    List<PersonaOutputDto> getPersonaByUsuario(String usuario, int pageNumber, int pageSize);
    PersonaOutputDto updatePersona(int id, PersonaInputDto persona);
    void deletePersonaById( int id);
    Iterable<PersonaOutputDto> getAllPersonas(int pageNumber, int pageSize);
    List<PersonaOutputDto> getPersonQuery(HashMap<String, Object> conditions);
}