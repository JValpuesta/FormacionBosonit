package com.bosonit.tripbackend.controller;

import com.bosonit.tripbackend.application.ClienteService;
import com.bosonit.tripbackend.controller.dto.ClienteInputDto;
import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import com.bosonit.tripbackend.exceptions.EntityNotFoundException;
import com.bosonit.tripbackend.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ClienteOutputDto createClient(@RequestBody ClienteInputDto clienteInputDto) throws UnprocessableEntityException {
        URI location =  URI.create("/client");
        return clienteService.addCliente(clienteInputDto);
    }

    @GetMapping("/{idClient}")
    public ClienteOutputDto getClientById(@PathVariable Integer idCliente) throws EntityNotFoundException {
        return clienteService.getClienteById(idCliente);
    }

    @GetMapping
    public List<ClienteOutputDto> getAllClientes(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                 @RequestParam(required = false, defaultValue = "4") int pageSize) {
        return clienteService.getAllClientes(pageNumber, pageSize);
    }

    @DeleteMapping("/{idClient}")
    public void deleteClienteById(@PathVariable Integer idCliente) throws EntityNotFoundException {
        clienteService.deleteClienteById(idCliente);
    }

    @PutMapping("/{idClient}")
    public ClienteOutputDto updateClientById(@RequestBody ClienteInputDto clientInputDto,
                                             @PathVariable Integer idCliente)
            throws EntityNotFoundException, UnprocessableEntityException {
        return clienteService.updateClienteById(clientInputDto, idCliente);
    }

}
