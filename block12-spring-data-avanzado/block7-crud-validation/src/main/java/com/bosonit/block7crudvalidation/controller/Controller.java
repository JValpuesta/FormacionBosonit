package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.application.EstudianteService;
import com.bosonit.block7crudvalidation.application.MyFeign;
import com.bosonit.block7crudvalidation.application.PersonaServiceImp2;
import com.bosonit.block7crudvalidation.application.ProfesorService;
import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/persona")
public class Controller {

    @Autowired
    PersonaServiceImp2 personaService; //Se debe usar la interfaz PersonaService y las notaciones @Component y @ComponentScan
    @Autowired
    EstudianteService estudianteService;
    @Autowired
    ProfesorService profesorService;
    @Autowired
    MyFeign feign;

    @PostMapping
    public ResponseEntity<PersonaOutputDto> addPersona(@RequestBody PersonaInputDto persona) {
        URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(personaService.addPersona(persona));
    }

    @GetMapping("/usuario/{usuario}")
    public Iterable<PersonaOutputDto> getPersonaByName(@PathVariable String usuario,
                                                       @RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                       @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return personaService.getPersonaByUsuario(usuario, pageNumber, pageSize);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PersonaDTOInterface> getPersonaById(@PathVariable int id,
                                                              @RequestParam(defaultValue = "simple", required = false)
                                                              String outputType) throws Exception {
        PersonaDTOInterface personaDTOInterface = personaService.getPersonaTypeById(id);
        if (outputType.equals("simple") || personaDTOInterface instanceof PersonaOutputDto) {
            return ResponseEntity.ok().body(personaService.getPersonaById(id));
        }
        if (outputType.equals("full")) {
            if (personaDTOInterface instanceof PersonaEstudianteODTO) {
                return ResponseEntity.ok().body(new PersonaEstudianteODTO(estudianteService.getEstudianteById(id)));
            } else {
                Profesor profesor = profesorService.getProfesorById(id);
                return ResponseEntity.ok().body(new PersonaProfesorODTO(profesor, estudianteService.getEstudianteByProfesor(profesor)));
            }
        } else {
            throw new Exception("Parámetro outputType incorrecto");
        }
    }

    @GetMapping("/profesor/{id}")
    public ProfesorOutputDto getProfesorFeign(@PathVariable int id) {
        return feign.getProfesorById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaOutputDto> updatePersona(@PathVariable int id, @RequestBody PersonaInputDto persona) {
            return ResponseEntity.ok().body(personaService.updatePersona(id, persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonaOutputDto> deletePersonaById(@PathVariable int id) {
            PersonaOutputDto personaAux = personaService.getPersonaById(id);
            personaService.deletePersonaById(id);
            return ResponseEntity.ok().body(personaAux);
    }

    @GetMapping
    public Iterable<PersonaOutputDto> getAllPersonas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {

        return personaService.getAllPersonas(pageNumber, pageSize);
    }

}
