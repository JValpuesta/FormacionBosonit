package com.bosonit.block7crudvalidation.repository;

import com.bosonit.block7crudvalidation.domain.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudiosRepository extends JpaRepository<Asignatura, Integer> {
}
