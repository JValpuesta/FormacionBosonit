package com.bosonit.tripbackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViajeInputDto {
    private String origin;
    private String destination;
    private Date departureDate;
    private Date arrivalDate;
    private Boolean status;
}