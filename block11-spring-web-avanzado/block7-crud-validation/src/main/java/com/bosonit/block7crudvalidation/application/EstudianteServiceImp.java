package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.EstudianteOutputDto;
import com.bosonit.block7crudvalidation.controller.dto.EstudianteInputDto;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.EstudiosRepository;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import com.bosonit.block7crudvalidation.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImp implements EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    EstudiosRepository estudiosRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    ProfesorRepository profesorRepository;

    @Override
    public EstudianteOutputDto addEstudiante(EstudianteInputDto estudianteInputDto) {
        Estudiante estudiante = this.checkEstudianteInputDto(estudianteInputDto);
        return estudianteRepository.save(estudiante).estudianteToEstudianteOutputDto();
    }

    @Override
    public EstudianteOutputDto addAsignaturasEstudiante(int id, List<Integer> asignaturas) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        if (estudiante.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            List<Alumnos_Estudios> estudios = new ArrayList<>();
            for (Integer idAsignatura : asignaturas) {
                Optional<Alumnos_Estudios> optionalAlumnosEstudios = estudiosRepository.findById(idAsignatura);
                if (optionalAlumnosEstudios.isPresent()) {
                    estudios.add(optionalAlumnosEstudios.get());
                } else {
                    throw new EntityNotFoundException("Asignatura de id " + idAsignatura + " no encontrada");
                }
            }
            estudiante.get().getEstudios().addAll(estudios);
            return estudiante.get().estudianteToEstudianteOutputDto();
        }
    }

    @Override
    public Estudiante getEstudianteById(int id) {
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);
        if (estudianteOptional.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            return estudianteOptional.get();
        }
    }

    @Override
    public List<Estudiante> getEstudianteByProfesor(Profesor profesor) {
        return estudianteRepository.findByProfesor(profesor);
    }

    @Override
    public EstudianteOutputDto updateEstudiante(int id, EstudianteInputDto estudianteInputDto) {
        if (estudianteRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            Estudiante estudiante = this.checkEstudianteInputDto(estudianteInputDto);
            estudianteRepository.deleteById(id);
            return estudianteRepository.save(estudiante)
                    .estudianteToEstudianteOutputDto();
        }
    }

    @Override
    public void deleteEstudianteById(int id) {
        if (estudianteRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            estudianteRepository.deleteById(id);
        }
    }

    @Override
    public Iterable<EstudianteOutputDto> getAllEstudiantes(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return estudianteRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Estudiante::estudianteToEstudianteOutputDto).toList();
    }

    public Estudiante checkEstudianteInputDto(EstudianteInputDto estudianteInputDto) {
        if (estudianteInputDto.getNumHoursWeek().equals(null)) {
            throw new UnprocessableEntityException("El atributo Num_hours_week de Estudiante no puede ser nulo");
        }
        if (estudianteInputDto.getPersona().equals(null)) {
            throw new UnprocessableEntityException("El atributo Persona de Estudiante no puede ser nulo");
        }
        if (estudianteInputDto.getProfesor().equals(null)) {
            throw new UnprocessableEntityException("El atributo Profesor de Estudiante no puede ser nulo");
        }
        if (estudianteInputDto.getBranch().equals(null)) {
            throw new UnprocessableEntityException("El atributo Branch de Estudiante no puede ser nulo");
        }
        Optional<Persona> persona = personaRepository.findById(estudianteInputDto.getPersona());
        if (persona.isPresent()) {
            Optional<Profesor> optionalProfesor = profesorRepository.findByPersona(persona.get());
            if (optionalProfesor.isEmpty()) {
                Estudiante estudiante = new Estudiante(estudianteInputDto);
                estudiante.setPersona(persona.get());
                Optional<Profesor> profesor = profesorRepository.findById(estudianteInputDto.getProfesor());
                if (profesor.isPresent()) {
                    estudiante.setProfesor(profesor.get());
                    List<Integer> asignaturas = estudianteInputDto.getEstudios();
                    for (Integer idAsignatura : asignaturas) {
                        Optional<Alumnos_Estudios> optionalAlumnosEstudios = estudiosRepository.findById(idAsignatura);
                        if (optionalAlumnosEstudios.isPresent()) {
                            estudiante.getEstudios().add(optionalAlumnosEstudios.get());
                        } else {
                            throw new EntityNotFoundException("Asignatura de id " + idAsignatura + " no encontrada");
                        }
                    }
                    return estudiante;
                } else {
                    throw new UnprocessableEntityException("El atributo Profesor de Estudiantes no existe");
                }
            } else {
                throw new UnprocessableEntityException("El atributo Persona ya está asignado a un profesor");
            }
        } else {
            throw new UnprocessableEntityException("El atributo Persona de Estudiantes no existe");
        }
    }

}
