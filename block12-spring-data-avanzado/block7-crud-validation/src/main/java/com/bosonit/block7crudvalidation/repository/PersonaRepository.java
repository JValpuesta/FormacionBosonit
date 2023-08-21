package com.bosonit.block7crudvalidation.repository;

import com.bosonit.block7crudvalidation.domain.Persona;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    List<Persona> findByUsuario(String usuario, PageRequest page);
}