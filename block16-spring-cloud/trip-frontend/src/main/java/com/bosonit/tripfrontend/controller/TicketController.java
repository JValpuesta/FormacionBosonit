package com.bosonit.tripfrontend.controller;

import com.bosonit.tripfrontend.application.TicketService;
import com.bosonit.tripfrontend.controller.dto.TicketOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;
    @PostMapping("/generateTicket/{idClient}/{idTrip}")
    public TicketOutputDto generateTicket(@PathVariable Integer idClient, @PathVariable Integer idTrip) {
        return ticketService.addTicket(idClient, idTrip);
    }

}
