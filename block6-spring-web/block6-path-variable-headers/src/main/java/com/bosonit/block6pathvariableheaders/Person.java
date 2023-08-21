package com.bosonit.block6pathvariableheaders;

public class Person {
    private String nombre;
    private String poblacion;
    private int edad;

    public Person(String nombre, String poblacion, int edad) {
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
