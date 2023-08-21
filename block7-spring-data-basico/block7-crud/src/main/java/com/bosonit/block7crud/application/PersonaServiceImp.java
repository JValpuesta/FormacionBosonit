package com.bosonit.block7crud.application;

import com.bosonit.block7crud.controller.dto.PersonaInputDto;
import com.bosonit.block7crud.controller.dto.PersonaOutputDto;
import com.bosonit.block7crud.domain.Persona;
import com.bosonit.block7crud.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaServiceImp implements PersonaService{

    @Autowired
    PersonaRepository personaRepository;

    @Override
    public PersonaOutputDto addPersona(PersonaInputDto persona) {
        if (persona.getNombre().equals(null)) {
            persona.setNombre("vacío");
        }
        if (persona.getEdad().equals(null)) {
            persona.setNombre("0");
        }
        if (persona.getPoblacion().equals(null)) {
            persona.setNombre("vacío");
        }
        return personaRepository.save(new Persona(persona))
                .personaToPersonaOutputDto();
    }

    @Override
    public PersonaOutputDto getPersonaById(int id) {
        return personaRepository.findById(id).orElseThrow()
                .personaToPersonaOutputDto();
    }

    @Override
    public List<PersonaOutputDto> getPersonaByNombre(String name, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Persona> personas = personaRepository.findByNombre(name, pageRequest);
        List<PersonaOutputDto> personasOutput = new ArrayList<>();
        for(Persona p : personas){
            personasOutput.add(p.personaToPersonaOutputDto());
        }
        return personasOutput;
    }

    @Override
    public PersonaOutputDto updatePersona(PersonaInputDto persona) {
        personaRepository.findById(persona.getId()).orElseThrow();
        return personaRepository.save(new Persona(persona))
                .personaToPersonaOutputDto();
    }

    @Override
    public void deletePersonaById(int id) {
        personaRepository.findById(id).orElseThrow();
        personaRepository.deleteById(id);
    }

    @Override
    public List<PersonaOutputDto> getAllPersonas(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Persona::personaToPersonaOutputDto).toList();
    }


}
