package com.bosonit.block7crudvalidation.repository;

import com.bosonit.block7crudvalidation.domain.Persona;
import com.bosonit.block7crudvalidation.domain.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {

    Optional<Profesor> findByPersona(Persona persona);

    //Optional<Profesor> findByPersona_id(Integer idPersona);

}
