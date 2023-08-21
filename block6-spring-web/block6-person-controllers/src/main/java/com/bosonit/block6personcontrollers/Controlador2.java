package com.bosonit.block6personcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controlador2")
public class Controlador2 {

    private final PersonaService personaService;

    private final CiudadService ciudadService;

    public Controlador2(PersonaService personaService, CiudadService ciudadService) {
        this.personaService = personaService;
        this.ciudadService = ciudadService;
    }

    @PostMapping("/getPersona")
    public ResponseEntity<Persona> getPersona() {
        Persona persona = personaService.obtenerPersona();
        persona.setEdad(persona.getEdad() * 2); //Con el setEdad() se multiplica la edad cada vez que se hace POST al controlador
                                                //Para que esto no pasara se debería de contruir y devolver una copia de Persona
        return ResponseEntity.ok(persona);
    }

    @GetMapping("/getCiudades")
    public ResponseEntity<List<Ciudad>> getCiudades() {
        return ResponseEntity.ok(ciudadService.obtenerLista());
    }
}

