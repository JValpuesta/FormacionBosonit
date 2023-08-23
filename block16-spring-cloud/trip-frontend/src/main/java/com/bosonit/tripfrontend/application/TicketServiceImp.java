package com.bosonit.tripfrontend.application;

import com.bosonit.tripfrontend.controller.dto.TicketOutputDto;
import com.bosonit.tripfrontend.domain.Cliente;
import com.bosonit.tripfrontend.domain.Ticket;
import com.bosonit.tripfrontend.domain.Viaje;
import com.bosonit.tripfrontend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TicketServiceImp implements TicketService{
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TicketRepository ticketRepository;
    @Override
    public TicketOutputDto addTicket(Integer idCliente, Integer idViaje) {
        // Endpoints del servicio backend
        String userEndpoint = "http://localhost:8080/cliente/" + idCliente;
        String tripEndpoint = "http://localhost:8080/viaje/" + idViaje;

        // Usando RestTemplate para obtener detalles del usuario (cliente) y del viaje
        Cliente cliente = restTemplate.getForObject(userEndpoint, Cliente.class);
        Viaje viaje = restTemplate.getForObject(tripEndpoint, Viaje.class);

        // Validar que tanto el cliente como el viaje existan
        if (cliente == null || viaje == null) {
            throw new RuntimeException("Error al obtener datos del cliente o del viaje desde el backend");
        }

        Ticket ticket = new Ticket();
        ticket.setIdClient(cliente.getId());
        ticket.setClientName(cliente.getNombre());
        ticket.setClientSurname(cliente.getApellido());
        ticket.setClientEmail(cliente.getEmail());
        ticket.setTripOrigin(viaje.getOrigin());
        ticket.setTripDestination(viaje.getDestination());
        ticket.setDepartureDate(viaje.getDepartureDate());
        ticket.setArrivalDate(viaje.getArrivalDate());

        return ticketRepository.save(ticket).ticketToTicketOutputDto();
    }
}
