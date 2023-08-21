package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.EstudiosRepository;
import com.bosonit.block7crudvalidation.repository.ProfesorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EstudiosServiceTest {

    private final int idEjemplo = 10;

    private final int idError = 0;

    private final Date date = new Date();

    private final List<Estudiante> listaVacia = new ArrayList<>();

    private final Persona personaEjemploConId = new Persona(idEjemplo, "lolito", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, date, "ejem.jpg", date);

    private final Persona persona2 = new Persona(11, "usuario", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, date, "ejem.jpg", date);

    private final Profesor profesorEjemplo = new Profesor(idEjemplo, this.personaEjemploConId, "listo", "mates");

    private final Asignatura asignatura = new Asignatura(this.idEjemplo, this.profesorEjemplo, this.listaVacia,
            "mates", "dificil", date, date);

    private final Asignatura[] estudios = new Asignatura[]{asignatura};

    private final Estudiante estudianteEjemplo = new Estudiante(idEjemplo, this.persona2, 40,
            "trabaja", profesorEjemplo, "mates", Arrays.stream(estudios).toList());

    private final Estudiante[] estudiantes = new Estudiante[]{estudianteEjemplo};

    private final EstudiosInputDto estudiosInputDtoEjemlpo = new EstudiosInputDto(idEjemplo,
            Arrays.stream(this.estudiantes).map(Estudiante::getIdStudent).toList(), "mates",
            "dificil", date, date);

    @InjectMocks
    private EstudiosServiceImp estudiosService;

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private EstudiosRepository estudiosRepository;

    @Test
    public void testAddEstudio() {
        Mockito.when(profesorRepository.findById(this.asignatura.getProfesor().getIdProfesor()))
                .thenReturn(Optional.of(this.profesorEjemplo));
        Mockito.when(estudianteRepository.findById(Mockito.any(Integer.class)))
                .thenReturn(Optional.of(this.estudianteEjemplo));
        Mockito.when(estudiosRepository.save(Mockito.any(Asignatura.class))).thenReturn(this.asignatura);
        EstudiosOutputDto estudiosOutputDto = estudiosService.addEstudios(this.estudiosInputDtoEjemlpo);
        Assertions.assertEquals(this.asignatura.getIdStudy(), estudiosOutputDto.getIdStudy());
    }

    @Test
    public void testGetEstudioById() {
        Mockito.when(estudiosRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.asignatura));
        EstudiosOutputDto estudiosOutputDto = estudiosService.getEstudiosById(this.idEjemplo);
        Assert.assertEquals(this.asignatura.getIdStudy(), estudiosOutputDto.getIdStudy());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetEstudioByIdException() {
        //Mockito.when(estudiosRepository.findById(this.idError)).thenReturn(Optional.empty());
        estudiosService.getEstudiosById(this.idError);
    }

    @Test
    public void testGetEstudioByEstudiante() {
        Mockito.when(estudianteRepository.findById(this.estudianteEjemplo.getIdStudent()))
                .thenReturn(Optional.of(this.estudianteEjemplo));
        List<EstudiosOutputDto> listaEstudiosOutputDto = estudiosService
                .getEstudiosByEstudiante(this.estudianteEjemplo.getIdStudent());
        Assert.assertEquals(this.estudianteEjemplo.getEstudios().stream().map(Asignatura::getIdStudy).toList(),
                listaEstudiosOutputDto.stream().map(EstudiosOutputDto::getIdStudy).toList());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetEstudioByEstudianteException() {
        Mockito.when(estudianteRepository.findById(this.estudianteEjemplo.getIdStudent()))
                .thenReturn(Optional.empty());
        estudiosService.getEstudiosByEstudiante(this.estudianteEjemplo.getIdStudent());
    }

    @Test
    public void testUpdateEstudios() {
        Mockito.when(estudiosRepository.findById(this.idEjemplo))
                .thenReturn(Optional.of(this.asignatura));
        Mockito.when(profesorRepository.findById(this.estudiosInputDtoEjemlpo.getIdProfesor()))
                .thenReturn(Optional.of(this.profesorEjemplo));
        Mockito.when(estudianteRepository.findById(this.idEjemplo))
                .thenReturn(Optional.of(this.estudianteEjemplo));
        Mockito.when(estudiosRepository.save(Mockito.any(Asignatura.class))).thenReturn(this.asignatura);
        EstudiosOutputDto estudiosOutputDto = estudiosService
                .updateEstudios(this.idEjemplo, this.estudiosInputDtoEjemlpo);
        Mockito.verify(estudiosRepository).deleteById(this.idEjemplo);
        Assert.assertEquals(this.estudiosInputDtoEjemlpo.getAsignatura(), estudiosOutputDto.getAsignatura());
        Assertions.assertEquals(this.idEjemplo, estudiosOutputDto.getIdStudy());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateEstudiosException() {
        estudiosService.updateEstudios(this.idError, this.estudiosInputDtoEjemlpo);
    }

    @Test
    public void testDeleteEstudios() {
        Mockito.when(estudiosRepository.findById(this.idEjemplo))
                .thenReturn(Optional.of(this.asignatura));
        estudiosService.deleteEstudiosById(this.idEjemplo);
        Mockito.verify(estudiosRepository).deleteById(this.idEjemplo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteEstudiosException() {
        estudiosService.deleteEstudiosById(this.idError);
    }

    @Test
    public void testGetAllEstudios() {
        int pageNumber = 0;
        int pageSize = 4;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Asignatura> lista = new ArrayList<>();
        lista.add(this.asignatura);
        lista.add(new Asignatura(this.idEjemplo + 1, this.profesorEjemplo, listaVacia,
                "mates", "dificil", date, date));
        Page<Asignatura> page = new PageImpl<>(lista, PageRequest.of(pageNumber, pageSize), lista.size());

        Mockito.when(estudiosRepository.findAll(pageRequest)).thenReturn(page);
        List<EstudiosOutputDto> listaEstudios = estudiosService
                .getAllEstudios(pageNumber, pageSize);

        Assert.assertEquals(lista.stream().map(Asignatura::getIdStudy).toList(),
                listaEstudios.stream().map(EstudiosOutputDto::getIdStudy).toList());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testCheckEstudiosEstudianteException() {
        Mockito.when(profesorRepository.findById(this.estudiosInputDtoEjemlpo.getIdProfesor()))
                .thenReturn(Optional.of(this.profesorEjemplo));
        Mockito.when(estudianteRepository.findById(this.idEjemplo))
                .thenReturn(Optional.empty());
        estudiosService.checkEstudiosNulls(this.estudiosInputDtoEjemlpo);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckEstudiosProfesorException() {
        Mockito.when(profesorRepository.findById(this.estudiosInputDtoEjemlpo.getIdProfesor()))
                .thenReturn(Optional.empty());
        estudiosService.checkEstudiosNulls(this.estudiosInputDtoEjemlpo);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckEstudiosInitialDateException() {
        EstudiosInputDto estudiosInputDto = this.estudiosInputDtoEjemlpo;
        estudiosInputDto.setInitialDate(new Date(9999999999L));
        estudiosService.checkEstudiosNulls(estudiosInputDto);
    }
}
