package com.bosonit.tripbackend.application;

import com.bosonit.tripbackend.controller.dto.ClienteInputDto;
import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import com.bosonit.tripbackend.exceptions.EntityNotFoundException;
import com.bosonit.tripbackend.exceptions.UnprocessableEntityException;

import java.util.List;

public interface ClienteService {

    ClienteOutputDto addCliente(ClienteInputDto clienteInputDto) throws UnprocessableEntityException;
    ClienteOutputDto getClienteById(Integer idCliente) throws EntityNotFoundException;
    List<ClienteOutputDto> getAllClientes(int pageNumber, int pageSize);
    void deleteClienteById(Integer idCliente) throws EntityNotFoundException;
    ClienteOutputDto updateClienteById(ClienteInputDto clienteInputDto, Integer idCliente)
            throws EntityNotFoundException, UnprocessableEntityException;

}
