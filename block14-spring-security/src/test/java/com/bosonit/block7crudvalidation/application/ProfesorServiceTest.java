package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.bosonit.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import com.bosonit.block7crudvalidation.repository.ProfesorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProfesorServiceTest{

    private final int idEjemplo = 10;

    private final int idError = 0;
    private final ProfesorInputDto profesorInputDtoEjemplo =
            new ProfesorInputDto(10, "no trabja", "historia");

    private final Persona personaEjemploConId = new Persona(idEjemplo, "lolito", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, new Date(), "ejem.jpg", new Date());

    private final Profesor profesorEjemploConId =
            new Profesor(idEjemplo, this.personaEjemploConId, "no trabaja", "historia");

    @InjectMocks
    private ProfesorServiceImp profesorService;

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Test
    public void testAddProfesor() {
        Mockito.when(personaRepository.findById(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.of(this.profesorEjemploConId.getPersona()));
        Mockito.when(estudianteRepository.findByPersona_id(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.empty());
        Mockito.when(profesorRepository.save(any(Profesor.class))).thenReturn(profesorEjemploConId);
        ProfesorOutputDto profesorOutputDto = profesorService.addProfesor(this.profesorInputDtoEjemplo);
        Assert.assertEquals(this.profesorEjemploConId.getIdProfesor(), profesorOutputDto.getIdProfesor());
    }

    @Test
    public void testGetProfesorById() {
        Mockito.when(profesorRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.profesorEjemploConId));
        Profesor profesor = profesorService.getProfesorById(this.idEjemplo);

        Assert.assertEquals(this.profesorEjemploConId.getIdProfesor(), profesor.getIdProfesor());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetProfesorByIdException() {
        Mockito.when(profesorRepository.findById(this.idError)).thenReturn(Optional.empty());
        profesorService.getProfesorById(this.idError);
    }

    @Test
    public void testGetAllProfesores() {
        int pageNumber = 0;
        int pageSize = 4;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Persona persona2 = new Persona(idEjemplo+1, "lolit0", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        Profesor profesor2 = new Profesor(idEjemplo+1, persona2, "trabaja", "mates");
        List<Profesor> lista = new ArrayList<>();
        lista.add(this.profesorEjemploConId);
        lista.add(profesor2);

        Page<Profesor> page = new PageImpl<>(lista, PageRequest.of(pageNumber, pageSize), lista.size());

        Mockito.when(profesorRepository.findAll(pageRequest)).thenReturn(page);
        List<ProfesorOutputDto> listaProfesores = profesorService.getAllProfesores(pageNumber, pageSize);

        Assert.assertEquals(lista.stream().map(Profesor::getIdProfesor).toList(),
                listaProfesores.stream().map(ProfesorOutputDto::getIdProfesor).toList());
    }

    @Test
    public void testDeleteProfesorById() {
        Mockito.when(profesorRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.profesorEjemploConId));
        profesorService.deleteProfesorById(this.idEjemplo);
        Mockito.verify(profesorRepository).findById(this.idEjemplo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteProfesorByIdException() {
        Mockito.when(profesorRepository.findById(this.idError)).thenReturn(Optional.empty());
        profesorService.deleteProfesorById(this.idError);
    }

    @Test
    public void testModificarProfesor() {
        Mockito.when(profesorRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.profesorEjemploConId));
        Profesor resultado = new Profesor(this.profesorEjemploConId.getPersona(), this.profesorInputDtoEjemplo);
        resultado.setIdProfesor(this.idEjemplo);
        Mockito.when(personaRepository.findById(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.of(this.profesorEjemploConId.getPersona()));
        Mockito.when(estudianteRepository.findByPersona_id(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.empty());
        Mockito.when(profesorRepository.save(any(Profesor.class))).thenReturn(resultado);

        ProfesorOutputDto profesorOutputDto = profesorService.updateProfesor(this.idEjemplo, this.profesorInputDtoEjemplo);

        Mockito.verify(profesorRepository).findById(this.idEjemplo);
        Mockito.verify(profesorRepository).save(any(Profesor.class));

        Assert.assertEquals(this.profesorInputDtoEjemplo.getPersona(), profesorOutputDto.getPersona().getId());
        Assert.assertEquals(profesorOutputDto.getIdProfesor(), this.idEjemplo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateProfesorException() {
        Mockito.when(profesorRepository.findById(this.idError)).thenReturn(Optional.empty());
        profesorService.updateProfesor(this.idError, this.profesorInputDtoEjemplo);
    }

    @Test
    public void testCheckProfesor(){
        when(personaRepository.findById(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.of(this.profesorEjemploConId.getPersona()));
        when(estudianteRepository.findByPersona_id(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.empty());
        Persona persona = profesorService.checkProfesor((this.profesorInputDtoEjemplo));
        Assert.assertEquals(persona, this.profesorEjemploConId.getPersona());
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckProfesorEmptyBranch(){
        Persona persona = profesorService.checkProfesor((new ProfesorInputDto(this.idEjemplo,"hola","")));
        Assert.assertEquals(persona, this.profesorEjemploConId.getPersona());
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckProfesorNotAPerson(){
        when(personaRepository.findById(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.empty());
        profesorService.checkProfesor((this.profesorInputDtoEjemplo));
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckProfesorPersonStudent(){
        when(personaRepository.findById(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.of(this.profesorEjemploConId.getPersona()));
        when(estudianteRepository.findByPersona_id(this.profesorEjemploConId.getPersona().getId()))
                .thenReturn(Optional.of(new Estudiante()));
        profesorService.checkProfesor((this.profesorInputDtoEjemplo));
    }

}
