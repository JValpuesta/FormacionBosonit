package com.bosonit.block7crudvalidation.domain;

import com.bosonit.block7crudvalidation.controller.dto.ProfesorInputDto;
import com.bosonit.block7crudvalidation.controller.dto.ProfesorOutputDto;
import com.bosonit.block7crudvalidation.controller.dto.ProfesorSimpleODTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profesores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int idProfesor;
    @OneToOne
    @JoinColumn(name = "id_persona")
    Persona persona;
    @Column(name = "comentarios")
    String coments;
    @Column(name = "materia_principal", nullable = false)
    String branch;

    public Profesor(ProfesorInputDto profesorInputDto) {
        this.coments = profesorInputDto.getComents();
        this.branch = profesorInputDto.getBranch();
    }

    public Profesor(ProfesorSimpleODTO profesorSimpleODTO) {
        this.coments = profesorSimpleODTO.getComents();
        this.branch = profesorSimpleODTO.getBranch();
    }

    public ProfesorOutputDto profesorToProfesorOutputDto() {
        return new ProfesorOutputDto(
                this.idProfesor,
                this.persona.personaToPersonaOutputDto(),
                this.coments,
                this.branch
        );
    }

    public ProfesorSimpleODTO profesorToProfesorSimlpeODTO() {
        return new ProfesorSimpleODTO(
                this.idProfesor,
                this.persona.getId(),
                this.coments,
                this.branch
        );
    }
}
