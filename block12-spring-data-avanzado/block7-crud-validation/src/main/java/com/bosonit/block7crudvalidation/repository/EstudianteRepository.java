package com.bosonit.block7crudvalidation.repository;

import com.bosonit.block7crudvalidation.domain.Estudiante;
import com.bosonit.block7crudvalidation.domain.Persona;
import com.bosonit.block7crudvalidation.domain.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    List<Estudiante> findByProfesor (Profesor profesor);

    //List<Estudiante> findByProfesor_idProfesor (int idProfesor);

    Optional<Estudiante> findByPersona (Persona persona);
}
