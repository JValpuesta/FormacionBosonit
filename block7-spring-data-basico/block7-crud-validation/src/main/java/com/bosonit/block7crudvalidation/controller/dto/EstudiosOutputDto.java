package com.bosonit.block7crudvalidation.controller.dto;

import com.bosonit.block7crudvalidation.domain.Estudiante;
import com.bosonit.block7crudvalidation.domain.Profesor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudiosOutputDto {
    int idStudy;
    Profesor profesor;
    List<Estudiante> student;
    String asignatura;
    String comment;
    Date initialDate;
    Date finishDate;
}
