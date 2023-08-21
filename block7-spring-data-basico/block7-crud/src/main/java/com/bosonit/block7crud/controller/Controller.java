package com.bosonit.block7crud.controller;


import com.bosonit.block7crud.application.PersonaServiceImp;
import com.bosonit.block7crud.controller.dto.PersonaInputDto;
import com.bosonit.block7crud.controller.dto.PersonaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/persona")
public class Controller {

    @Autowired
    PersonaServiceImp personaService;

    @PostMapping
    public ResponseEntity<PersonaOutputDto> addPersona(@RequestBody PersonaInputDto persona) {
        URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(personaService.addPersona(persona));
    }

    @GetMapping("/nombre/{nombre}")
    public Iterable<PersonaOutputDto> getPersonaByName(@PathVariable String nombre,
                                                       @RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                       @RequestParam(defaultValue = "4", required = false) int pageSize) {

        return personaService.getPersonaByNombre(nombre, pageNumber, pageSize);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaOutputDto> getPersonaById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(personaService.getPersonaById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaOutputDto> updatePersona(@PathVariable int id, @RequestBody PersonaInputDto persona) {
        try {
            personaService.getPersonaById(id);
            return ResponseEntity.ok().body(personaService.addPersona(persona));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonaOutputDto> deletePersonaById(@PathVariable int id) {
        try {
            PersonaOutputDto personaAux = personaService.getPersonaById(id);
            personaService.deletePersonaById(id);
            return ResponseEntity.ok().body(personaAux);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Iterable<PersonaOutputDto> getAllPersonas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {

        return personaService.getAllPersonas(pageNumber, pageSize);
    }

}
