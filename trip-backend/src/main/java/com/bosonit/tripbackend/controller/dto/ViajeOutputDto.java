package com.bosonit.tripbackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViajeOutputDto {
    private Integer idViaje;
    private String origin;
    private String destination;
    private Date departureDate;
    private Date arrivalDate;
    private List<ClienteOutputDto> passengers;
    private Boolean status;
}