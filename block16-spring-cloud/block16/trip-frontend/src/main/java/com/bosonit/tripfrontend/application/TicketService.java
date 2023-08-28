package com.bosonit.tripfrontend.application;

import com.bosonit.tripfrontend.controller.dto.TicketOutputDto;

public interface TicketService {
    TicketOutputDto addTicket(Integer idClient, Integer idTrip);
}
