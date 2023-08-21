package com.bosonit.springdata.repository;

import com.bosonit.springdata.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}

