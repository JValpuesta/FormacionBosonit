package com.bosonit.examen_JPA_cascada.repository;

import com.bosonit.examen_JPA_cascada.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
