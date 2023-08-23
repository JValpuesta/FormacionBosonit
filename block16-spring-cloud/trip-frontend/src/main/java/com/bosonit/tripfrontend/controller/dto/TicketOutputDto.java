package com.bosonit.tripfrontend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketOutputDto {
    private Integer idTicket;
    private Integer idClient;
    private String clientName;
    private String clientSurname;
    private String clientEmail;
    private String tripOrigin;
    private String tripDestination;
    private Date departureDate;
    private Date arrivalDate;
}