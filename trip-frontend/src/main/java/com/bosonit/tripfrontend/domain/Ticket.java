package com.bosonit.tripfrontend.domain;

import com.bosonit.tripfrontend.controller.dto.TicketOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTicket;
    private Integer idClient;
    private String clientName;
    private String clientSurname;
    private String clientEmail;
    private String tripOrigin;
    private String tripDestination;
    private Date departureDate;
    private Date arrivalDate;

    public TicketOutputDto ticketToTicketOutputDto () {
        return new TicketOutputDto(
                this.idTicket,
                this.idClient,
                this.clientName,
                this.clientSurname,
                this.clientEmail,
                this.tripOrigin,
                this.tripDestination,
                this.departureDate,
                this.arrivalDate
        );
    }

}
