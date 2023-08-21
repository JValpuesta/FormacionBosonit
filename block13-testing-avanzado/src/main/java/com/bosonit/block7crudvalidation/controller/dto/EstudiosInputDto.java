package com.bosonit.block7crudvalidation.controller.dto;

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
public class EstudiosInputDto {
    Integer idProfesor;
    List<Integer> idStudent;
    String asignatura;
    String comment;
    Date initialDate;
    Date finishDate;
}
