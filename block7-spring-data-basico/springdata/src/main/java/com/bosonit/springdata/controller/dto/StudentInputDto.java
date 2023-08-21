package com.bosonit.springdata.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInputDto {
    int id;
    String name;
    String lastName;
}

