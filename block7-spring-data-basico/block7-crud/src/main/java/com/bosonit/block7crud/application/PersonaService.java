package com.bosonit.block7crud.application;


import com.bosonit.block7crud.controller.dto.PersonaInputDto;
import com.bosonit.block7crud.controller.dto.PersonaOutputDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PersonaService {

    PersonaOutputDto addPersona(PersonaInputDto persona);
    PersonaOutputDto getPersonaById(int id);
    List<PersonaOutputDto> getPersonaByNombre(String name, int pageNumber, int pageSize);
    PersonaOutputDto updatePersona(PersonaInputDto persona);
    void deletePersonaById( int id);
    Iterable<PersonaOutputDto> getAllPersonas(int pageNumber, int pageSize);

}
