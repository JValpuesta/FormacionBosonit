package com.bosonit.block6simplecontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

public class MiControlador {
    /*@RequestMapping(value="/user")
    @GetMapping(value = { "/", "/{id}" })
    public String hola(@PathVariable Map<String, String> mapa) {
        return mapa.get("id") == null ? "Hola usuario" : "Hola " + mapa.get("id");
    }*/

    @GetMapping(value= "/user/{id}")
    public String hola(@PathVariable String id) {
        return "Hola usuario " + id;
    }


    @PostMapping("/useradd")
    public ResponseEntity<Persona> addUser(@RequestBody Persona persona) {
        Persona persona1 = new Persona(persona.getNombre(), persona.getPoblacion(), persona.getEdad()+1);
        return new ResponseEntity<>(persona1, HttpStatus.OK);
    }

}

