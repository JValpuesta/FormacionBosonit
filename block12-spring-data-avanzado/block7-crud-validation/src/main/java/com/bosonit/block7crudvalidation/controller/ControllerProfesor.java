package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.application.ProfesorService;
import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/profesor")
public class ControllerProfesor {
    @Autowired
    ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<ProfesorOutputDto> addProfesor(@RequestBody ProfesorInputDto profesor) {
        URI location = URI.create("/profesor");
        return ResponseEntity.created(location).body(profesorService.addProfesor(profesor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTOInterface> getProfesorById(@PathVariable int id, @RequestParam(defaultValue = "simple") String outputType) throws Exception {
        Profesor profesor = profesorService.getProfesorById(id);
        if (outputType.equals("simple")) {
            return ResponseEntity.ok().body(profesor.profesorToProfesorSimlpeODTO());
        }
        if (outputType.equals("full")) {
            return ResponseEntity.ok().body(profesor.profesorToProfesorOutputDto());
        } else {
            throw new Exception("Parámetro outputType incorrecto");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorOutputDto> updateProfesor(@PathVariable int id, @RequestBody ProfesorInputDto profesor) {
            return ResponseEntity.ok().body(profesorService.updateProfesor(id, profesor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfesorOutputDto> deleteProfesorById(@PathVariable int id) {
            ProfesorOutputDto profesorAux = profesorService.getProfesorById(id).profesorToProfesorOutputDto();
            profesorService.deleteProfesorById(id);
            return ResponseEntity.ok().body(profesorAux);
    }

    @GetMapping
    public Iterable<ProfesorOutputDto> getAllProfesores(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return profesorService.getAllProfesores(pageNumber, pageSize);
    }

}
