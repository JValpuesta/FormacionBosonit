package com.bosonit.tripbackend.domain;

import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import com.bosonit.tripbackend.controller.dto.ViajeInputDto;
import com.bosonit.tripbackend.controller.dto.ViajeOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String origin;
    private String destination;
    private Date departureDate;
    private Date arrivalDate;
    @OneToMany()
    List<Cliente> passenger = new ArrayList<>();
    private Boolean status;

    public Viaje(ViajeInputDto tripInputDto) {
        this.origin = tripInputDto.getOrigin();
        this.destination = tripInputDto.getDestination();
        this.departureDate = tripInputDto.getDepartureDate();
        this.arrivalDate = tripInputDto.getArrivalDate();
        this.status = tripInputDto.getStatus();
    }

    public ViajeOutputDto viajeToViajeOutputDto() {
        return new ViajeOutputDto(
                this.id,
                this.origin,
                this.destination,
                this.departureDate,
                this.arrivalDate,
                this.passenger.stream().map(Cliente::clienteToClienteOutputDto).toList(),
                this.status
        );
    }

}
