package com.bosonit.block7crudvalidation.repository;

import com.bosonit.block7crudvalidation.domain.Alumnos_Estudios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudiosRepository extends JpaRepository<Alumnos_Estudios, Integer> {
}
