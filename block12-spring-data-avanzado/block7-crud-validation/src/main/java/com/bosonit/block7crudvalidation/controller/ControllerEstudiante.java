package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.application.EstudianteService;
import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class ControllerEstudiante {

    @Autowired
    EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<EstudianteOutputDto> addEstudiante(@RequestBody EstudianteInputDto estudiante) {
        URI location = URI.create("/estudiante");
        return ResponseEntity.created(location).body(estudianteService.addEstudiante(estudiante));
    }

    @PostMapping("/anadir_asignaturas/{id}")
    public ResponseEntity<EstudianteOutputDto> addAsignaturasEstudiante(@PathVariable int id, @RequestBody List<Integer> asignaturas) {
        URI location = URI.create("/anadir_asignaturas/{id}");
        return ResponseEntity.created(location).body(estudianteService.addAsignaturasEstudiante(id, asignaturas));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<EstudianteDTOInterface> getEstudianteById(@PathVariable int id, @RequestParam(defaultValue = "simple") String outputType) throws Exception {
        Estudiante estudiante = estudianteService.getEstudianteById(id);
        if (outputType.equals("simple")) {
            return ResponseEntity.ok().body(estudiante.estudianteToEstudianteSimlpeODTO());
        }
        if (outputType.equals("full")) {
            return ResponseEntity.ok().body(estudiante.estudianteToEstudianteOutputDto());
        } else {
            throw new Exception("Parámetro outputType incorrecto");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteOutputDto> updateEstudiante(@PathVariable int id, @RequestBody EstudianteInputDto estudianteInputDto) {
            return ResponseEntity.ok().body(estudianteService.updateEstudiante(id, estudianteInputDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EstudianteOutputDto> deleteProfesorById(@PathVariable int id) {
            EstudianteOutputDto estudianteAux = estudianteService.getEstudianteById(id).estudianteToEstudianteOutputDto();
            estudianteService.deleteEstudianteById(id);
            return ResponseEntity.ok().body(estudianteAux);
    }

    @GetMapping
    public Iterable<EstudianteOutputDto> getAllEstudiantes(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return estudianteService.getAllEstudiantes(pageNumber, pageSize);
    }
}
