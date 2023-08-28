package com.bosonit.tripbackend.repository;

import com.bosonit.tripbackend.domain.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
}
