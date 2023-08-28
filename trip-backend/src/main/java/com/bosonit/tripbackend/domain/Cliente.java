package com.bosonit.tripbackend.domain;

import com.bosonit.tripbackend.controller.dto.ClienteInputDto;
import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private Integer telefono;

    public Cliente(ClienteInputDto clienteInputDto) {
        this.nombre = clienteInputDto.getNombre();
        this.apellido = clienteInputDto.getApellido();
        this.edad = clienteInputDto.getEdad();
        this.email = clienteInputDto.getEmail();
        this.telefono = clienteInputDto.getTelefono();
    }

    public ClienteOutputDto clienteToClienteOutputDto() {
        return new ClienteOutputDto(
                this.id,
                this.nombre,
                this.apellido,
                this.edad,
                this.email,
                this.telefono
        );
    }

}