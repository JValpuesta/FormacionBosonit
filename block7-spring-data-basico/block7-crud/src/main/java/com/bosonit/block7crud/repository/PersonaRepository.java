package com.bosonit.block7crud.repository;

import com.bosonit.block7crud.controller.dto.PersonaOutputDto;
import com.bosonit.block7crud.domain.Persona;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    List<Persona> findByNombre(String nombre, PageRequest page);
}
