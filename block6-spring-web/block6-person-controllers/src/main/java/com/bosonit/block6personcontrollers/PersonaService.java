package com.bosonit.block6personcontrollers;

public interface PersonaService {

    public Persona createPersona(String nombre, String poblacion, int edad);

    public Persona obtenerPersona();
}
