package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.EstudiosInputDto;
import com.bosonit.block7crudvalidation.controller.dto.EstudiosOutputDto;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.EstudiosRepository;
import com.bosonit.block7crudvalidation.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudiosServiceImp implements EstudiosService {

    @Autowired
    EstudiosRepository asignaturaRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    EstudianteRepository estudianteRepository;


    @Override
    public EstudiosOutputDto addEstudios(EstudiosInputDto estudiosInputDto) {
        Alumnos_Estudios alumnosEstudios = this.checkEstudiosNulls(estudiosInputDto);
        return asignaturaRepository.save(alumnosEstudios).estudiosToEstudiosOutputDto();
    }

    @Override
    public EstudiosOutputDto getEstudiosById(int id) {
        Optional<Alumnos_Estudios> asignatura = asignaturaRepository.findById(id);
        if (asignatura.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            return asignatura.get().estudiosToEstudiosOutputDto();
        }
    }

    @Override
    public List<EstudiosOutputDto> getEstudiosByEstudiante(int id) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        if (estudiante.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            return estudiante.get().getEstudios().stream().map(Alumnos_Estudios::estudiosToEstudiosOutputDto).toList();
        }
    }

    @Override
    public EstudiosOutputDto updateEstudios(int id, EstudiosInputDto estudiosInputDto) {
        if (asignaturaRepository.findById(id).isPresent()) {
            Alumnos_Estudios alumnosEstudios = this.checkEstudiosNulls(estudiosInputDto);
            asignaturaRepository.deleteById(id);
            return asignaturaRepository.save(alumnosEstudios).estudiosToEstudiosOutputDto();
        } else {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        }
    }

    @Override
    public void deleteEstudiosById(int id) {
        if (asignaturaRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            asignaturaRepository.deleteById(id);
        }
    }

    @Override
    public List<EstudiosOutputDto> getAllEstudios(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return asignaturaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Alumnos_Estudios::estudiosToEstudiosOutputDto).toList();

    }

    public Alumnos_Estudios checkEstudiosNulls(EstudiosInputDto estudiosInputDto) {
        if (estudiosInputDto.getIdProfesor().equals(null)) {
            throw new UnprocessableEntityException("IdProfesor no puede ser null");
        }
        if (estudiosInputDto.getInitialDate().equals(null)) {
            throw new UnprocessableEntityException("Initial_date no puede ser null");
        }
        Optional<Profesor> profesor = profesorRepository.findById(estudiosInputDto.getIdProfesor());
        if (profesor.isPresent()) {
            Alumnos_Estudios alumnosEstudios = new Alumnos_Estudios(estudiosInputDto);
            alumnosEstudios.setProfesor(profesor.get());
            List<Integer> idStudents = estudiosInputDto.getIdStudent();
            for (Integer id : idStudents) {
                Optional<Estudiante> optionalEstudiante = estudianteRepository.findById(id);
                if (optionalEstudiante.isPresent()) {
                    alumnosEstudios.getStudents().add(optionalEstudiante.get());
                } else {
                    throw new EntityNotFoundException("Estudiante de id " + id + " no encontrado");
                }
            }
            return alumnosEstudios;
        } else {
            throw new UnprocessableEntityException("El atributo Profesor de Asignatura no existe");
        }
    }
}
