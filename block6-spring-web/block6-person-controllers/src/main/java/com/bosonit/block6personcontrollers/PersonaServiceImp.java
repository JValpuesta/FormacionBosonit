package com.bosonit.block6personcontrollers;


import org.springframework.stereotype.Service;

@Service
public class PersonaServiceImp implements PersonaService{

    private Persona p;

    public Persona createPersona(String nombre, String poblacion, int edad) {
        p = new Persona(nombre, poblacion, edad);
        return p;
    }

    @Override
    public Persona obtenerPersona() {
        return p;
    }

}
