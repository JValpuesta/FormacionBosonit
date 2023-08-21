package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.application.EstudiosService;
import com.bosonit.block7crudvalidation.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/asignatura")
public class ControllerAsignatura {

    @Autowired
    EstudiosService estudiosService;

    @PostMapping
    public ResponseEntity<EstudiosOutputDto> addAsignatura(@RequestBody EstudiosInputDto estudiosInputDto) {
        URI location = URI.create("/asignatura");
        return ResponseEntity.created(location).body(estudiosService.addEstudios(estudiosInputDto));
    }

    @GetMapping("/estudiante_asignatura/{id}")
    public List<EstudiosOutputDto> getAsignaturasByEstudiante(@PathVariable int id) {
        return estudiosService.getEstudiosByEstudiante(id);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EstudiosOutputDto> getAsignaturaById(@PathVariable int id) {
            return ResponseEntity.ok().body(estudiosService.getEstudiosById(id));
    }

    @PutMapping
    public ResponseEntity<EstudiosOutputDto> updateAsignatura(@PathVariable int id, @RequestBody EstudiosInputDto estudiosInputDto) {
            return ResponseEntity.ok().body(estudiosService.updateEstudios(id, estudiosInputDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EstudiosOutputDto> deleteAsignaturaById(@PathVariable int id) {
            EstudiosOutputDto estudiosAux = estudiosService.getEstudiosById(id);
            estudiosService.deleteEstudiosById(id);
            return ResponseEntity.ok().body(estudiosAux);
    }

    @GetMapping
    public Iterable<EstudiosOutputDto> getAllAsignaturas(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {

        return estudiosService.getAllEstudios(pageNumber, pageSize);
    }

}
