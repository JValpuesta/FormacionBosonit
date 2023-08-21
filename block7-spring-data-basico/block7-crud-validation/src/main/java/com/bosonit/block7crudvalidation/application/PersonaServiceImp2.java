package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import com.bosonit.block7crudvalidation.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImp2 implements PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    ProfesorRepository profesorRepository;

    @Override
    public PersonaOutputDto addPersona(PersonaInputDto persona) {
        checkPersonaNulls(persona);
        return personaRepository.save(new Persona(persona))
                .personaToPersonaOutputDto();
    }

    public PersonaOutputDto getPersonaById(int id) {
        Optional<Persona> oPersona = personaRepository.findById(id);
        if (oPersona.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            return oPersona.get().personaToPersonaOutputDto();
        }
    }

    @Override
    public PersonaDTOInterface getPersonaTypeById(int id) {
        Optional<Persona> oPersona = personaRepository.findById(id);
        if (oPersona.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            Optional<Estudiante> oEstudiante = estudianteRepository.findById(id);
            if (oEstudiante.isPresent()) {
                return new PersonaEstudianteODTO(oEstudiante.get());
            }
            Optional<Profesor> oProfesor = profesorRepository.findById(id);
            if (oProfesor.isPresent()) {
                Profesor profesor = oProfesor.get();
                return new PersonaProfesorODTO(profesor,
                        estudianteRepository.findByProfesor(profesor));
            }
            return oPersona.get().personaToPersonaOutputDto();
        }
    }

    @Override
    public List<PersonaOutputDto> getPersonaByUsuario(String name, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personaRepository.findByUsuario(name, pageRequest).stream().map(Persona::personaToPersonaOutputDto).toList();
    }

    @Override
    public PersonaOutputDto updatePersona(int id, PersonaInputDto persona) {
        checkPersonaNulls(persona);
        if (personaRepository.findById(id).isPresent()) {
            personaRepository.deleteById(id);
            return personaRepository.save(new Persona(persona))
                    .personaToPersonaOutputDto();
        } else {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        }
    }

    @Override
    public void deletePersonaById(int id) {
        if (personaRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            personaRepository.deleteById(id);
        }
    }

    @Override
    public List<PersonaOutputDto> getAllPersonas(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Persona::personaToPersonaOutputDto).toList();
    }

    public void checkPersonaNulls(PersonaInputDto persona) {
        if (persona.getUsuario().length() > 10) {
            throw new UnprocessableEntityException("Longitud de usuario no puede ser superior a 10 caracteres");
        }
        if (persona.getUsuario().length() < 6) {
            throw new UnprocessableEntityException("Longitud de usuario no puede ser inferior a 6 caracteres");
        }
        if (persona.getUsuario().equals(null)) {
            throw new UnprocessableEntityException("Usuario no puede ser null");
        }
        if (persona.getPassword().equals(null)) {
            throw new UnprocessableEntityException("Password no puede ser null");
        }
        if (persona.getName().equals(null)) {
            throw new UnprocessableEntityException("Name no puede ser null");
        }
        if (persona.getCompanyEmail().equals(null)) {
            throw new UnprocessableEntityException("Company_email no puede ser null");
        }
        if (persona.getPersonalEmail().equals(null)) {
            throw new UnprocessableEntityException("Personal_email no puede ser null");
        }
        if (persona.getCity().equals(null)) {
            throw new UnprocessableEntityException("City no puede ser null");
        }
        if (persona.getActive().equals(null)) {
            throw new UnprocessableEntityException("Active no puede ser null");
        }
        if (persona.getCreated_date().equals(null)) {
            throw new UnprocessableEntityException("Created_date no puede ser null");
        }
    }
}

