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
public class EstudianteInputDto {
    Integer persona;
    Integer numHoursWeek;
    String coments;
    Integer profesor;
    String branch;
    List<Integer> estudios;
}
