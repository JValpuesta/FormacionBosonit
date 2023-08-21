package com.bosonit.springdata.domain;

import com.bosonit.springdata.controller.dto.StudentInputDto;
import com.bosonit.springdata.controller.dto.StudentOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue
    int id;
    String name;
    String lastName;

    public Student(StudentInputDto studentInputDto) {
        this.id = studentInputDto.getId();
        this.name = studentInputDto.getName();
        this.lastName = studentInputDto.getLastName();
    }

    public StudentOutputDto studentToStudentOutputDto() {
        return new StudentOutputDto(
                this.id,
                this.name,
                this.lastName
        );
    }
}
