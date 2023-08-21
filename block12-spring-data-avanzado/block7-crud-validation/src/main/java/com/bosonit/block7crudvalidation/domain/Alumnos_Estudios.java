package com.bosonit.block7crudvalidation.domain;

import com.bosonit.block7crudvalidation.controller.dto.EstudiosInputDto;
import com.bosonit.block7crudvalidation.controller.dto.EstudiosOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "estudios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alumnos_Estudios {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer idStudy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    Profesor profesor; //Quitar atributo
    @ManyToMany(cascade = CascadeType.ALL)
    List<Estudiante> students;
    @Column(name = "asignatura")
    String asignatura;
    @Column(name = "comentarios")
    String comment;
    @Column(name = "initial_date", nullable = false)
    Date initialDate;
    @Column(name = "finish_date")
    Date finishDate;

    public EstudiosOutputDto estudiosToEstudiosOutputDto() {
        return new EstudiosOutputDto(this.idStudy, this.profesor, this.students, this.asignatura, this.comment, this.initialDate, this.finishDate);
    }

    public Alumnos_Estudios(EstudiosInputDto estudiosInputDto) {
        this.asignatura = estudiosInputDto.getAsignatura();
        this.comment = estudiosInputDto.getComment();
        this.initialDate = estudiosInputDto.getInitialDate();
        this.finishDate = estudiosInputDto.getFinishDate();
    }
}


