package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.PersonaDTOInterface;
import com.bosonit.block7crudvalidation.controller.dto.PersonaInputDto;
import com.bosonit.block7crudvalidation.controller.dto.PersonaOutputDto;
import com.bosonit.block7crudvalidation.domain.Persona;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaServiceImp implements PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    @Override
    public PersonaOutputDto addPersona(PersonaInputDto persona) throws Exception {
        if (persona.getUsuario().length() > 10) {
            throw new Exception("Longitud de usuario no puede ser superior a 10 caracteres");
        }
        if (persona.getUsuario().length() < 6) {
            throw new Exception("Longitud de usuario no puede ser inferior a 6 caracteres");
        }
        //Reutilizar código con una función
        if (persona.getUsuario().equals(null)) {throw new Exception("Usuario no puede ser nul"); }
        if (persona.getPassword().equals(null)) {throw new Exception("Password no puede ser nul"); }
        if (persona.getName().equals(null)) {throw new Exception("Name no puede ser nul"); }
        if (persona.getCompanyEmail().equals(null)) {throw new Exception("Company_email no puede ser nul"); }
        if (persona.getPersonalEmail().equals(null)) {throw new Exception("Personal_email no puede ser nul"); }
        if (persona.getCity().equals(null)) {throw new Exception("City no puede ser nul"); }
        if (persona.getActive().equals(null)) {throw new Exception("Active no puede ser nul"); }
        if (persona.getCreated_date().equals(null)) {throw new Exception("Created_date no puede ser nul"); }
        return personaRepository.save(new Persona(persona))
                .personaToPersonaOutputDto();
    }

    @Override
    public PersonaOutputDto getPersonaById(int id) {
        return personaRepository.findById(id).orElseThrow()
                .personaToPersonaOutputDto();
    }

    @Override
    public PersonaDTOInterface getPersonaTypeById(int id) {
        return null;
    }

    @Override
    public List<PersonaOutputDto> getPersonaByUsuario(String name, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Persona> personas = personaRepository.findByUsuario(name, pageRequest);
        List<PersonaOutputDto> personasOutput = new ArrayList<>();
        for (Persona p : personas) {
            personasOutput.add(p.personaToPersonaOutputDto());
        }
        return personasOutput;
    }

    @Override
    public PersonaOutputDto updatePersona(int id, PersonaInputDto persona) throws Exception {
        personaRepository.findById(id).orElseThrow();
        if (persona.getUsuario().length() > 10) {
            throw new Exception("Longitud de usuario no puede ser superior a 10 caracteres");
        }
        if (persona.getUsuario().length() < 6) {
            throw new Exception("Longitud de usuario no puede ser inferior a 6 caracteres");
        }
        //Reutilizar código con una función
        if (persona.getUsuario().equals(null)) {throw new Exception("Usuario no puede ser null"); }
        if (persona.getPassword().equals(null)) {throw new Exception("Password no puede ser null"); }
        if (persona.getName().equals(null)) {throw new Exception("Name no puede ser null"); }
        if (persona.getCompanyEmail().equals(null)) {throw new Exception("Company_email no puede ser null"); }
        if (persona.getPersonalEmail().equals(null)) {throw new Exception("Personal_email no puede ser null"); }
        if (persona.getCity().equals(null)) {throw new Exception("City no puede ser null"); }
        if (persona.getActive().equals(null)) {throw new Exception("Active no puede ser null"); }
        if (persona.getCreated_date().equals(null)) {throw new Exception("Created_date no puede ser null"); }
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
