package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.EstudiosRepository;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
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

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EstudianteServiceTest {

    private final int idEjemplo = 10;

    private final int idError = 0;

    private final Date date = new Date();

    private final Persona personaEjemploConId = new Persona(idEjemplo, "lolito", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, date, "ejem.jpg", date);

    private final Persona persona2 = new Persona(11, "usuario", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, date, "ejem.jpg", date);

    private final Profesor profesorEjemplo = new Profesor(idEjemplo, this.personaEjemploConId, "listo", "mates");

    private final Asignatura asignatura = new Asignatura(1, this.profesorEjemplo, new ArrayList<>(),
            "mates", "dificil", date, date);

    private final Asignatura[] estudios = new Asignatura[] {asignatura};

    private final Estudiante estudianteEjemplo = new Estudiante(idEjemplo, this.persona2, 40,
            "trabaja", profesorEjemplo, "mates", Arrays.stream(estudios).toList());

    private final EstudianteInputDto estudianteInputDtoEjemplo = new EstudianteInputDto(this.persona2.getId(),
            40, "trabaja", profesorEjemplo.getIdProfesor(), "mates",
            Arrays.stream(estudios).map(Asignatura::getIdStudy).toList());

    @InjectMocks
    private EstudianteServiceImp estudianteService;

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private EstudiosRepository estudiosRepository;

    @Test
    public void testAddEstudiante() {
        Mockito.when(personaRepository.findById(this.estudianteEjemplo.getPersona().getId()))
                .thenReturn(Optional.of(this.estudianteEjemplo.getPersona()));
        Mockito.when(profesorRepository.findById(this.estudianteEjemplo.getProfesor().getIdProfesor()))
                .thenReturn(Optional.of(this.profesorEjemplo));
        Mockito.when(estudiosRepository.findById(any(Integer.class))).thenReturn(Optional.of(this.asignatura));
        Mockito.when(estudianteRepository.save(any(Estudiante.class))).thenReturn(this.estudianteEjemplo);
        EstudianteOutputDto estudianteOutputDto = estudianteService.addEstudiante(this.estudianteInputDtoEjemplo);
        Assert.assertEquals(this.estudianteEjemplo.getIdStudent(), estudianteOutputDto.getIdStudent());
    }

    @Test
    public void testAddAsignaturaEstudiante(){
        Mockito.when(estudianteRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.estudianteEjemplo));
        Mockito.when(estudiosRepository.findById(this.asignatura.getIdStudy())).thenReturn(Optional.of(this.asignatura));
        EstudianteOutputDto estudianteOutputDto = estudianteService
                .addAsignaturasEstudiante(this.estudianteEjemplo.getIdStudent(),
                        Arrays.stream(this.estudios).map(Asignatura::getIdStudy).toList());
        Assert.assertEquals(estudianteOutputDto.getEstudios().stream().map(Asignatura::getIdStudy).toList(),
                Arrays.stream(this.estudios).map(Asignatura::getIdStudy).toList());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testAddAsignaturaEstudianteEstudianteException(){
        estudianteService.addAsignaturasEstudiante(this.estudianteEjemplo.getIdStudent(),
                        Arrays.stream(this.estudios).map(Asignatura::getIdStudy).toList());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testAddAsignaturaEstudianteAsignaturaException(){
        Mockito.when(estudianteRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.estudianteEjemplo));
        Mockito.when(estudiosRepository.findById(this.asignatura.getIdStudy())).thenReturn(Optional.empty());
        estudianteService.addAsignaturasEstudiante(this.estudianteEjemplo.getIdStudent(),
                        Arrays.stream(this.estudios).map(Asignatura::getIdStudy).toList());
    }

    @Test
    public void testGetEstudianteById() {
        Mockito.when(estudianteRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.estudianteEjemplo));
        Estudiante estudiante = estudianteService.getEstudianteById(this.idEjemplo);
        Assert.assertEquals(this.estudianteEjemplo.getIdStudent(), estudiante.getIdStudent());
        Assert.assertEquals(this.estudianteEjemplo.getPersona().getId(), estudiante.getPersona().getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetEstudianteByIdException() {
        estudianteService.getEstudianteById(this.idEjemplo);
    }

    @Test
    public void testUpdateEstudiante(){
        Mockito.when(estudianteRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.estudianteEjemplo));
        Mockito.when(personaRepository.findById(this.estudianteEjemplo.getPersona().getId()))
                .thenReturn(Optional.of(this.estudianteEjemplo.getPersona()));
        Mockito.when(profesorRepository.findById(this.estudianteEjemplo.getProfesor().getIdProfesor()))
                .thenReturn(Optional.of(this.profesorEjemplo));
        Mockito.when(estudiosRepository.findById(any(Integer.class))).thenReturn(Optional.of(this.asignatura));
        Mockito.when(estudianteRepository.save(any(Estudiante.class))).thenReturn(this.estudianteEjemplo);

        EstudianteOutputDto estudianteOutputDto = estudianteService
                .updateEstudiante(this.idEjemplo, this.estudianteInputDtoEjemplo);
        Assertions.assertEquals(this.estudianteInputDtoEjemplo.getPersona(),
                estudianteOutputDto.getPersona().getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateEstudianteException(){
        estudianteService.updateEstudiante(this.idEjemplo, this.estudianteInputDtoEjemplo);
    }

    @Test
    public void testDeleteEstudianteById() {
        Mockito.when(estudianteRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.estudianteEjemplo));
        estudianteService.deleteEstudianteById(this.idEjemplo);
        Mockito.verify(estudianteRepository).findById(this.idEjemplo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteEstudianteByIdException() {
        estudianteService.deleteEstudianteById(this.idEjemplo);
    }

    @Test
    public void testGetAllEstudiantes() {
        int pageNumber = 0;
        int pageSize = 4;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Persona personaAux = new Persona(idEjemplo+2, "lolit0", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        Estudiante estudianteAux = new Estudiante(idEjemplo+1, personaAux,40,
                "trabaja", profesorEjemplo, "mates", Arrays.stream(estudios).toList());
        List<Estudiante> lista = new ArrayList<>();
        lista.add(this.estudianteEjemplo);
        lista.add(estudianteAux);

        Page<Estudiante> page = new PageImpl<>(lista, PageRequest.of(pageNumber, pageSize), lista.size());

        Mockito.when(estudianteRepository.findAll(pageRequest)).thenReturn(page);
        List<EstudianteOutputDto> listaEstudiantes = (List<EstudianteOutputDto>) estudianteService
                .getAllEstudiantes(pageNumber, pageSize);

        Assert.assertEquals(lista.stream().map(Estudiante::getIdStudent).toList(),
                listaEstudiantes.stream().map(EstudianteOutputDto::getIdStudent).toList());
    }

    @Test
    public void testCheckEstudianteInputDto() {
        Mockito.when(personaRepository.findById(this.estudianteEjemplo.getPersona().getId()))
                .thenReturn(Optional.of(this.estudianteEjemplo.getPersona()));
        Mockito.when(profesorRepository.findById(this.estudianteEjemplo.getProfesor().getIdProfesor()))
                .thenReturn(Optional.of(this.profesorEjemplo));
        Mockito.when(estudiosRepository.findById(any(Integer.class))).thenReturn(Optional.of(this.asignatura));
        Estudiante estudiante = estudianteService.checkEstudianteInputDto(this.estudianteInputDtoEjemplo);
        Assertions.assertEquals(estudianteInputDtoEjemplo.getPersona(), estudiante.getPersona().getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testCheckEstudianteInputDtoEstudiosException() {
        Mockito.when(personaRepository.findById(this.estudianteEjemplo.getPersona().getId()))
                .thenReturn(Optional.of(this.estudianteEjemplo.getPersona()));
        Mockito.when(profesorRepository.findById(this.estudianteEjemplo.getProfesor().getIdProfesor()))
                .thenReturn(Optional.of(this.profesorEjemplo));
        Mockito.when(estudiosRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        estudianteService.checkEstudianteInputDto(this.estudianteInputDtoEjemplo);
    }
    @Test(expected = UnprocessableEntityException.class)
    public void testCheckEstudianteInputDtoProfesorException() {
        Mockito.when(personaRepository.findById(this.estudianteEjemplo.getPersona().getId()))
                .thenReturn(Optional.of(this.estudianteEjemplo.getPersona()));
        Mockito.when(profesorRepository.findById(this.estudianteEjemplo.getProfesor().getIdProfesor()))
                .thenReturn(Optional.empty());
        estudianteService.checkEstudianteInputDto(this.estudianteInputDtoEjemplo);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckEstudianteInputDtoPersonaException() {
        Mockito.when(personaRepository.findById(this.estudianteEjemplo.getPersona().getId()))
                .thenReturn(Optional.empty());
        estudianteService.checkEstudianteInputDto(this.estudianteInputDtoEjemplo);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckEstudianteInputDtoPersonaProfesorException() {
        Mockito.when(personaRepository.findById(this.estudianteEjemplo.getPersona().getId()))
                .thenReturn(Optional.of(this.personaEjemploConId));
        Mockito.when(profesorRepository.findByPersona(this.personaEjemploConId))
                .thenReturn(Optional.of(this.profesorEjemplo));
        estudianteService.checkEstudianteInputDto(this.estudianteInputDtoEjemplo);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckEstudianteInputDtoBranchException() {
        EstudianteInputDto estudianteInputDto = this.estudianteInputDtoEjemplo;
        estudianteInputDto.setBranch("");
        estudianteService.checkEstudianteInputDto(estudianteInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckEstudianteInputDtoNumHoursWeekException() {
        EstudianteInputDto estudianteInputDto = this.estudianteInputDtoEjemplo;
        estudianteInputDto.setNumHoursWeek(-1);
        estudianteService.checkEstudianteInputDto(estudianteInputDto);
    }

    @Test
    public void testGetEstudianteByProfesor(){
        List<Estudiante> estudianteList = new ArrayList<>();
        estudianteList.add(this.estudianteEjemplo);
        Mockito.when(estudianteRepository.findByProfesor(this.profesorEjemplo)).thenReturn(estudianteList);
        List<Estudiante> resultado = estudianteService.getEstudianteByProfesor(this.profesorEjemplo);
        Assert.assertEquals(resultado, estudianteList);
    }

}
