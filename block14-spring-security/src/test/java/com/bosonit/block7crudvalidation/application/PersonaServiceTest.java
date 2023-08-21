package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.*;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PersonaServiceTest {

    private final int idEjemplo = 10;

    private final int idError = 0;
    private final PersonaInputDto personaInputDtoEjemplo = new PersonaInputDto("usuario", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, new Date(), "ejem.jpg", new Date());

    private final Persona personaEjemplo = new Persona(personaInputDtoEjemplo);

    private final Persona personaEjemploConId = new Persona(idEjemplo, "lolito", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, new Date(), "ejem.jpg", new Date());

    private final Persona persona2 = new Persona(11, "usuario", "123", "manolo",
            "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
            true, new Date(), "ejem.jpg", new Date());

    private final Profesor profesorEjemplo = new Profesor(idEjemplo, this.personaEjemploConId, "listo", "mates");

    private final Asignatura asignatura = new Asignatura();

    private final Asignatura[] estudios = new Asignatura[] {asignatura};

    private final Estudiante estudianteEjemplo = new Estudiante(idEjemplo, this.persona2, 40,
            "trabaja", profesorEjemplo, "mates", Arrays.stream(estudios).toList());

    @InjectMocks
    private PersonaServiceImp2 personaService;

    @Mock
    private PersonaRepository personaRepository;
    @Mock
    private EstudianteRepository estudianteRepository;
    @Mock
    private ProfesorRepository profesorRepository;

    @Test
    public void testAddPersona() {
        Mockito.when(personaRepository.save(personaEjemplo)).thenReturn(personaEjemplo);
        PersonaOutputDto personaOutputDto = personaService.addPersona(this.personaInputDtoEjemplo);
        Mockito.verify(personaRepository).save(personaEjemplo);

        Assert.assertEquals(personaEjemplo.getId(), personaOutputDto.getId());
    }

    @Test
    public void testGetPersonaById() {
        Mockito.when(personaRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.personaEjemploConId));
        PersonaOutputDto personaOutputDto = personaService.getPersonaById(this.idEjemplo);
        Mockito.verify(personaRepository).findById(this.idEjemplo);

        Assert.assertEquals(this.personaEjemploConId.getId(), personaOutputDto.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetPersonaByIdException() {
        Mockito.when(personaRepository.findById(this.idError)).thenReturn(Optional.empty());
        personaService.getPersonaById(this.idError);
    }

    @Test
    public void testGetPersonaTypePersonaById() {
        Mockito.when(personaRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.personaEjemploConId));
        Mockito.when(estudianteRepository.findByPersona_id(this.personaEjemploConId.getId())).thenReturn(Optional.empty());
        Mockito.when(profesorRepository.findByPersona(this.personaEjemploConId)).thenReturn(Optional.empty());

        PersonaOutputDto personaOutputDto = (PersonaOutputDto) personaService.getPersonaTypeById(this.idEjemplo);

        Mockito.verify(personaRepository).findById(this.idEjemplo);
        Mockito.verify(estudianteRepository).findByPersona_id(this.personaEjemploConId.getId());
        Mockito.verify(profesorRepository).findByPersona(this.personaEjemploConId);

        Assert.assertEquals(this.personaEjemploConId.getId(), personaOutputDto.getId());
        Assert.assertEquals(this.personaEjemploConId.getUsuario(), personaOutputDto.getUsuario());
    }

    @Test
    public void testGetPersonaTypeProfesorById() {
        Mockito.when(personaRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.personaEjemploConId));
        Mockito.when(estudianteRepository.findByPersona_id(this.personaEjemploConId.getId())).thenReturn(Optional.empty());
        Mockito.when(profesorRepository.findByPersona(this.personaEjemploConId))
                .thenReturn(Optional.of(this.profesorEjemplo));

        PersonaProfesorODTO personaProfesorODTO = (PersonaProfesorODTO) personaService.getPersonaTypeById(this.idEjemplo);

        Mockito.verify(personaRepository).findById(this.idEjemplo);
        Mockito.verify(estudianteRepository).findByPersona_id(this.personaEjemploConId.getId());
        Mockito.verify(profesorRepository).findByPersona(this.personaEjemploConId);

        Assert.assertEquals(this.personaEjemploConId.getId(), personaProfesorODTO.getPersonaOutputDto().getId());
        Assert.assertEquals(this.personaEjemploConId.getUsuario(), personaProfesorODTO.getPersonaOutputDto().getUsuario());
    }

    @Test
    public void testGetPersonaTypeEstudianteById() {
        Mockito.when(personaRepository.findById(this.persona2.getId())).thenReturn(Optional.of(this.persona2));
        Mockito.when(estudianteRepository.findByPersona_id(this.persona2.getId()))
                .thenReturn(Optional.of(this.estudianteEjemplo));

        PersonaEstudianteODTO personaEstudianteODTO = (PersonaEstudianteODTO) personaService.getPersonaTypeById(this.persona2.getId());

        Mockito.verify(personaRepository).findById(this.persona2.getId());
        Mockito.verify(estudianteRepository).findByPersona_id(this.persona2.getId());

        Assert.assertEquals(this.persona2.getId(), personaEstudianteODTO.getPersonaOutputDto().getId());
        Assert.assertEquals(this.persona2.getUsuario(), personaEstudianteODTO.getPersonaOutputDto().getUsuario());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetPersonaTypeByIdException() {
        Mockito.when(personaRepository.findById(this.idError)).thenReturn(Optional.empty());
        personaService.getPersonaTypeById(this.idError);
    }

    @Test
    public void testGetPersonaByUsuario() {
        String usuario = "usuario";
        int pageNumber = 0;
        int pageSize = 4;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Persona persona = new Persona(new PersonaInputDto(usuario, "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date()));
        List<Persona> lista = new ArrayList<>();
        lista.add(persona);

        Mockito.when(personaRepository.findByUsuario(usuario, pageRequest)).thenReturn(lista);
        List<PersonaOutputDto> listaPersonaOutputDto = personaService.getPersonaByUsuario(usuario, pageNumber, pageSize);
        Mockito.verify(personaRepository).findByUsuario(usuario, pageRequest);

        Assert.assertEquals(persona.getId(), listaPersonaOutputDto.get(0).getId());
    }

    @Test
    public void testGetAllPersonas() {
        int pageNumber = 0;
        int pageSize = 4;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Persona persona2 = new Persona(new PersonaInputDto("usuario", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date()));
        List<Persona> lista = new ArrayList<>();
        lista.add(this.personaEjemplo);
        lista.add(persona2);

        Page<Persona> page = new PageImpl<>(lista, PageRequest.of(pageNumber, pageSize), lista.size());

        Mockito.when(personaRepository.findAll(pageRequest)).thenReturn(page);
        List<PersonaOutputDto> listaPersonasOutputDto = personaService.getAllPersonas(pageNumber, pageSize);
        Mockito.verify(personaRepository).findAll(pageRequest);

        Assert.assertEquals(lista.stream().map(Persona::getId).toList(),
                listaPersonasOutputDto.stream().map(PersonaOutputDto::getId).toList());
    }

    @Test
    public void testDeletePersonaById() {
        Mockito.when(personaRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.personaEjemploConId));
        personaService.deletePersonaById(this.idEjemplo);
        Mockito.verify(personaRepository).findById(this.idEjemplo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeletePersonaByIdException() {
        Mockito.when(personaRepository.findById(this.idError)).thenReturn(Optional.empty());
        personaService.deletePersonaById(this.idError);
    }

    @Test
    public void testModificarPersona() {

        Mockito.when(personaRepository.findById(this.idEjemplo)).thenReturn(Optional.of(this.personaEjemploConId));
        Persona resultado = new Persona(this.personaInputDtoEjemplo);
        resultado.setId(this.idEjemplo);
        Mockito.when(personaRepository.save(resultado)).thenReturn(resultado);

        PersonaOutputDto personaOutputDto = personaService.updatePersona(this.idEjemplo, this.personaInputDtoEjemplo);

        Mockito.verify(personaRepository).findById(this.idEjemplo);
        Mockito.verify(personaRepository).save(resultado);

        Assert.assertEquals(this.personaInputDtoEjemplo.getUsuario(), personaOutputDto.getUsuario());
        Assert.assertEquals(personaOutputDto.getId(), this.idEjemplo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdatePersonaException() {
        Mockito.when(personaRepository.findById(this.idError)).thenReturn(Optional.empty());
        personaService.updatePersona(this.idError, this.personaInputDtoEjemplo);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsLengthMaxSize(){
        PersonaInputDto personaInputDto = new PersonaInputDto("usuariomasdediezcaracteres", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsLengthMinSize(){
        PersonaInputDto personaInputDto = new PersonaInputDto("solo5", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsBlankUsuario(){
        PersonaInputDto personaInputDto = new PersonaInputDto("", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsBlankPassword(){
        PersonaInputDto personaInputDto = new PersonaInputDto("usuario", "", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsBlankName(){
        PersonaInputDto personaInputDto = new PersonaInputDto("usuario", "123", "",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsBlankCompanyEmail(){
        PersonaInputDto personaInputDto = new PersonaInputDto("usuario", "123", "manolo",
                "fdz", "", "lolo@gmail.com", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsBlankPersonalEmail(){
        PersonaInputDto personaInputDto = new PersonaInputDto("usuario", "123", "manolo",
                "fdz", "lolo@lolo.es", "", "Logrono",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void testCheckPersonaNullsBlankCity(){
        PersonaInputDto personaInputDto = new PersonaInputDto("usuario", "123", "manolo",
                "fdz", "lolo@lolo.es", "lolo@gmail.com", "",
                true, new Date(), "ejem.jpg", new Date());
        personaService.checkPersonaNulls(personaInputDto);
    }

    @Test(expected = NullPointerException.class)
    public void testCheckPersonaNulls(){
        PersonaInputDto personaInputDto = new PersonaInputDto();
        personaService.checkPersonaNulls(personaInputDto);
    }

}