package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.application.*;
import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/persona")
public class ControllerPersona {

    @Autowired
    PersonaService personaService;
    @Autowired
    EstudianteService estudianteService;
    @Autowired
    ProfesorService profesorService;
    @Autowired
    MyFeign feign;

    private final static String GREATER_THAN = "GREATER_THAN";
    private final static String LESS_THAN = "LESS_THAN";
    private final static String EQUALS = "EQUALS";

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

    @GetMapping("/get")
    public List<PersonaOutputDto> getData(@RequestParam(required = false) String usuario,
                          @RequestParam(required = false) String nombre,
                          @RequestParam(required = false) String apellido,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy")Date createdDate,
                          @RequestParam(required = false) String dateCondition,
                          @RequestParam(required = false) String orderBy,
                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam Integer pageNumber){

        HashMap<String, Object> data = new HashMap<>();
        if(usuario!=null){
            data.put("usuario", usuario);
        }
        if(nombre!=null){
            data.put("name", nombre);
        }
        if(apellido!=null){
            data.put("surname", apellido);
        }
        if(dateCondition==null){
            dateCondition = GREATER_THAN;
        }
        if(!dateCondition.equals(GREATER_THAN) && !dateCondition.equals(LESS_THAN) && !dateCondition.equals(EQUALS)){
            dateCondition = GREATER_THAN;
        }
        if(createdDate!=null){
            data.put("createdDate", createdDate);
            data.put("dateCondition", dateCondition);
        }
        if(orderBy!=null){
            data.put("orderBy", orderBy);
        }

        data.put("pageSize", pageSize);
        data.put("pageNumber", pageNumber);

        return personaService.getPersonQuery(data);
    }

}
