package com.bosonit.block6personcontrollers;

import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

public interface CiudadService {

    public void anadirCiudad(String nombre, int poblacion);

    public List<Ciudad> obtenerLista();

}
