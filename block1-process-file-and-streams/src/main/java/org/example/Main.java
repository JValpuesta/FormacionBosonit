package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InvalidLineFormatException, IOException {
        List<Person> personas;
        //personas = leerFichero("C:\\Users\\jorge.valpuesta\\Desktop\\ejemplo.txt");
        //personas = leerFichero("..\\block1-process-file-and-streams\\src\\main\\resources\\ej.csv");
        personas = leerFichero("..\\block1-process-file-and-streams\\src\\main\\resources\\ejemplo.txt");
        Stream<Person> streamA = personas.stream().filter(p -> p.getAge() < 25 && p.getAge() > 0);
        List<Person> filtroA = streamA.toList();
        filtroA.forEach(p -> System.out.println("Name: " + p.getName() + ". Town: " + p.getTown() + ". Age: " + p.getAge()));

        Stream<Person> streamB = personas.stream().filter(p -> !p.getName().substring(0, 1).equals("A"));
        List<Person> filtroB = streamB.toList();
        filtroB.forEach(p -> System.out.println("Name: " + p.getName() + ". Town: " + p.getTown() + ". Age: " + p.getAge()));

        Optional<Person> filtroC = filtroA.stream().filter(persona -> persona.getTown().equals("Madrid")).findFirst();
        if (filtroC.isPresent()) {
            System.out.println("Name: " + filtroC.get().getName() + ". Town: " + filtroC.get().getTown() + ". Age: " + filtroC.get().getAge());
        } else {
            System.out.println("El optional está vacío");
        }

        Optional<Person> filtroD = filtroA.stream().filter(persona -> persona.getTown().equals("Barcelona")).findFirst();
        if (filtroD.isPresent()) // Comprobamos si contiene un valor
            System.out.println("Name: " + filtroD.get().getName() + ". Town: " + filtroD.get().getTown() + ". Age: " + filtroD.get().getAge()); // Obtenemos el valor
        else System.out.println("El optional está vacío");
    }

    public static List<Person> leerFichero(String ruta) throws InvalidLineFormatException, IOException {
        List<Person> personas = new ArrayList<>();
        Path path = Paths.get(ruta);
        String line;
        String separador = ":";
        int lineNumber = 0;
        try {
            BufferedReader br = Files.newBufferedReader(path);
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split(separador);
                int posicionS, contador = 0;
                posicionS = line.indexOf(separador);
                while (posicionS != -1) {
                    contador++;
                    posicionS = line.indexOf(separador, posicionS + 1);
                }
                if (contador != 2) {
                    throw new InvalidLineFormatException("Faltan delimitadores en la línea" + lineNumber + ": " + line);
                } else {
                    String name;
                    String town;
                    int age;
                    if (fields[0].equals("")) {
                        throw new InvalidLineFormatException("Nombre nulo en la línea" + lineNumber + ": " + line);
                    } else {
                        name = fields[0];
                    }
                    if (line.substring(line.length() - 2).equals(separador.concat(separador))) { //preguntar
                        town = "unknown";
                        age = 0;
                    } else {
                        if (fields[1].equals("")) {
                            town = "unknown";
                        } else {
                            town = fields[1];
                        }
                        if (fields.length == 2) {
                            age = 0;
                        } else {
                            try {
                                age = Integer.parseInt(fields[2]);
                            } catch (NumberFormatException e) {
                                throw new InvalidLineFormatException("Formato de edad erróneo en la línea " + lineNumber + ": " + line, e);
                            }
                        }
                    }
                    personas.add(new Person(name, town, age));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }


        return personas;
    }
}