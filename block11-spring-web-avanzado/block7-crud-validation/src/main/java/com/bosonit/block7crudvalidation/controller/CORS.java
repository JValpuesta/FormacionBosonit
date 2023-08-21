package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.application.PersonaService;
import com.bosonit.block7crudvalidation.controller.dto.PersonaInputDto;
import com.bosonit.block7crudvalidation.controller.dto.PersonaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class CORS {

    @Autowired
    PersonaService personaService;

    @CrossOrigin(origins = "http://cdpn.io")
    @PostMapping("/addperson")
    public ResponseEntity<PersonaOutputDto> addPersona(@RequestBody PersonaInputDto persona){
        URI location = URI.create("/addperson");
        return ResponseEntity.created(location).body(personaService.addPersona(persona));
    }

    @CrossOrigin(origins = "http://cdpn.io")
    @GetMapping("/getall")
    public Iterable<PersonaOutputDto> getAllPersonas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {

        return personaService.getAllPersonas(pageNumber, pageSize);
    }
}
