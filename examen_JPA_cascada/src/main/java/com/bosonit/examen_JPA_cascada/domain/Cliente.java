package com.bosonit.examen_JPA_cascada.domain;

import com.bosonit.examen_JPA_cascada.domain.dto.ClienteInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.ClienteOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;
    @Column(nullable = false, length = 100)
    String nombre;

    public Cliente (ClienteInputDto clienteInputDto) {
        this.nombre = clienteInputDto.getNombre();
    }

    public ClienteOutputDto clienteToClienteOutputDto(){
        return new ClienteOutputDto(
            this.id, this.nombre
        );
    }

}
