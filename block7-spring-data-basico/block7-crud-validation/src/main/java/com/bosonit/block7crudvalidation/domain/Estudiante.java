package com.bosonit.block7crudvalidation.domain;

import com.bosonit.block7crudvalidation.controller.dto.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer idStudent;
    @OneToOne
    @JoinColumn(name = "id_persona")
    Persona persona;
    @Column(name = "horas_por_semana", nullable = false)
    Integer numHoursWeek;
    @Column(name = "comentarios")
    String coments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesor")
    Profesor profesor; //Conceptualmente mal (se debería quitar)
    @Column(name = "rama", nullable = false)
    String branch;
    @ManyToMany
    List<Alumnos_Estudios> estudios;

    public Estudiante(EstudianteInputDto estudianteInputDto) {
        this.numHoursWeek = estudianteInputDto.getNumHoursWeek();
        this.coments = estudianteInputDto.getComents();
        this.branch = estudianteInputDto.getBranch();
    }

    public EstudianteOutputDto estudianteToEstudianteOutputDto() {
        return new EstudianteOutputDto(
                this.idStudent,
                this.persona.personaToPersonaOutputDto(),
                this.numHoursWeek,
                this.coments,
                this.profesor.profesorToProfesorOutputDto(),
                this.branch,
                this.estudios
        );
    }

    public EstudianteSimpleODTO estudianteToEstudianteSimlpeODTO() {
        return new EstudianteSimpleODTO(
                this.idStudent,
                this.persona.getId(),
                this.numHoursWeek,
                this.coments,
                this.profesor.getIdProfesor(),
                this.branch,
                this.estudios.stream()
                        .map(Alumnos_Estudios::getIdStudy)
                        .toList()
        );
    }
}

