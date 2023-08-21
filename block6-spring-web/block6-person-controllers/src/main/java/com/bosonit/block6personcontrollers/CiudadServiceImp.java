package com.bosonit.block6personcontrollers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CiudadServiceImp implements CiudadService, CommandLineRunner {

    private List<Ciudad> ciudades = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        ciudades.add(new Ciudad("Logroño", 150000));
    }

    @Override
    public void anadirCiudad(String nombre, int poblacion) {
        ciudades.add(new Ciudad(nombre, poblacion));
    }

    @Override
    public List<Ciudad> obtenerLista() {
        return ciudades;
    }
}
