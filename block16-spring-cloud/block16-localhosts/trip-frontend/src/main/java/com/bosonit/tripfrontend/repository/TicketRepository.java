package com.bosonit.tripfrontend.repository;

import com.bosonit.tripfrontend.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
