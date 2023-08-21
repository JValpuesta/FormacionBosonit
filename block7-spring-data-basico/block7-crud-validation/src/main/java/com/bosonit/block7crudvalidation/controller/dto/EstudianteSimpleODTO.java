package com.bosonit.block7crudvalidation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteSimpleODTO implements EstudianteDTOInterface{
    Integer idStudent;
    int persona;
    Integer numHoursWeek;
    String coments;
    int profesor;
    String branch;
    List<Integer> estudios;

}


