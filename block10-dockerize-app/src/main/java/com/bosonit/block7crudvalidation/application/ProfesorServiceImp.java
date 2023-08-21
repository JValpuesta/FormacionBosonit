package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.PersonaInputDto;
import com.bosonit.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.bosonit.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import com.bosonit.block7crudvalidation.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProfesorServiceImp implements ProfesorService {

    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    EstudianteRepository estudianteRepository;

    @Override
    public ProfesorOutputDto addProfesor(ProfesorInputDto profesorInputDto) {
        Profesor profesor = this.checkProfesor(profesorInputDto);
        return profesorRepository.save(profesor).profesorToProfesorOutputDto();
    }

    @Override
    public Profesor getProfesorById(int id) { //En este caso no importa, pero debería de devolver ProfesorOutputDto
        Optional<Profesor> profesor = profesorRepository.findById(id);
        if (profesor.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            return profesor.get();
        }
    }

    @Override
    public ProfesorOutputDto updateProfesor(int id, ProfesorInputDto profesorInputDto) {
        if (profesorRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            Profesor profesor = this.checkProfesor(profesorInputDto);
            profesorRepository.deleteById(id);
            return profesorRepository.save(profesor)
                    .profesorToProfesorOutputDto();
        }
    }

    @Override
    public void deleteProfesorById(int id) {
        if (profesorRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            profesorRepository.deleteById(id);
        }
    }

    @Override
    public Iterable<ProfesorOutputDto> getAllProfesores(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return profesorRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Profesor::profesorToProfesorOutputDto).toList();
    }

    public Profesor checkProfesor(ProfesorInputDto profesorInputDto) {
        if (profesorInputDto.getBranch().isEmpty()) {
            throw new UnprocessableEntityException("El atributo Branch de Profesor no puede ser nulo");
        }
        Optional<Persona> persona = personaRepository.findById(profesorInputDto.getPersona());
        if (persona.isPresent()) {
            Optional<Estudiante> optionalEstudiante = estudianteRepository.findByPersona(persona.get());
            if (optionalEstudiante.isEmpty()) {
                Profesor profesor = new Profesor(profesorInputDto);
                profesor.setPersona(persona.get());
                return profesor;
            } else {
                throw new UnprocessableEntityException("El atributo Persona de Profesor está asignado a un Estudiante");
            }
        } else {
            throw new UnprocessableEntityException("El atributo Persona de Profesor no existe");
        }
    }
}
