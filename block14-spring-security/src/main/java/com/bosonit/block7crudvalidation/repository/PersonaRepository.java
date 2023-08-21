package com.bosonit.block7crudvalidation.repository;

import com.bosonit.block7crudvalidation.domain.Persona;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByUsuario(String usuario);
    List<Persona> findByUsuario(String usuario, PageRequest page);
}