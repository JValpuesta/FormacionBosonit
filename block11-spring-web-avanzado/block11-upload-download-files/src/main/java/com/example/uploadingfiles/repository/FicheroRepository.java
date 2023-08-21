package com.example.uploadingfiles.repository;

import com.example.uploadingfiles.domain.Fichero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FicheroRepository extends JpaRepository<Fichero, Integer> {
}
