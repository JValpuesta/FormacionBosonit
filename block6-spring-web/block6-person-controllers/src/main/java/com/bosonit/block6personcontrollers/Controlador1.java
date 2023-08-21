package com.bosonit.block6personcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controlador1")
public class Controlador1 {

    private final PersonaService personaService;
    private final CiudadService ciudadService;

    public Controlador1(PersonaService personaService, CiudadService ciudadService) {
        this.personaService = personaService;
        this.ciudadService = ciudadService;
    }

    @GetMapping("/addPersona")
    public ResponseEntity<Persona> addPersona(
            @RequestHeader String nombre,
            @RequestHeader String poblacion,
            @RequestHeader int edad
    ) {
        Persona persona = personaService.createPersona(nombre, poblacion, edad);
        return ResponseEntity.ok(persona);
    }

    @PostMapping("/addCiudad")
    public ResponseEntity<Ciudad> addCiudad(@RequestBody Ciudad ciudad) {
        ciudadService.anadirCiudad(ciudad.getNombre(),ciudad.getNumeroHabitantes());
        return ResponseEntity.ok(ciudad);
    }

}
