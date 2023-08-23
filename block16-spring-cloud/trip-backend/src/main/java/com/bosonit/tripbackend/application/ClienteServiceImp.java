package com.bosonit.tripbackend.application;

import com.bosonit.tripbackend.controller.dto.ClienteInputDto;
import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import com.bosonit.tripbackend.domain.Cliente;
import com.bosonit.tripbackend.exceptions.EntityNotFoundException;
import com.bosonit.tripbackend.exceptions.UnprocessableEntityException;
import com.bosonit.tripbackend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImp implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public ClienteOutputDto addCliente(ClienteInputDto clienteInputDto) throws UnprocessableEntityException {
        this.checkCliente(clienteInputDto);
        return clienteRepository.save(new Cliente(clienteInputDto)).clienteToClienteOutputDto();
    }

    @Override
    public ClienteOutputDto getClienteById(Integer idCliente) throws EntityNotFoundException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        if (clienteOptional.isEmpty()) {
            throw new EntityNotFoundException("Cliente con ID: " + idCliente + " no encontrado", 404);
        } else {
            return clienteOptional.get().clienteToClienteOutputDto();
        }
    }

    @Override
    public List<ClienteOutputDto> getAllClientes(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return clienteRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Cliente::clienteToClienteOutputDto)
                .toList();
    }

    @Override
    public void deleteClienteById(Integer idCliente) throws EntityNotFoundException {
        if (clienteRepository.findById(idCliente).isEmpty()) {
            throw new EntityNotFoundException("Cliente con ID: " + idCliente + " no encontrado", 404);
        } else {
            clienteRepository.deleteById(idCliente);
        }
    }

    @Override
    public ClienteOutputDto updateClienteById(ClienteInputDto clienteInputDto, Integer idCliente)
            throws EntityNotFoundException, UnprocessableEntityException {
        if (clienteRepository.findById(idCliente).isEmpty()) {
            throw new EntityNotFoundException("Cliente con ID: " + idCliente + " no encontrado", 404);
        } else {
            this.checkCliente(clienteInputDto);
            Cliente cliente = clienteRepository.save(new Cliente(clienteInputDto));
            clienteRepository.deleteById(idCliente);
            cliente.setId(idCliente);
            return cliente.clienteToClienteOutputDto();
        }
    }

    public void checkCliente(ClienteInputDto clienteInputDto) throws UnprocessableEntityException {
        if(clienteInputDto.getNombre().isBlank()){
            throw new UnprocessableEntityException("El nombre del cliente no puede estar vacío", 422);
        }
        if(clienteInputDto.getApellido().isBlank()){
            throw new UnprocessableEntityException("El apellido del cliente no puede estar vacío", 422);
        }
        if(clienteInputDto.getEdad() <= 0){
            throw new UnprocessableEntityException("La edad del cliente debe ser mayor que 0", 422);
        }
        if(clienteInputDto.getEmail().isBlank()){
            throw new UnprocessableEntityException("El email del cliente no puede estar vacío", 422);
        }
        if(clienteInputDto.getTelefono() > 100000000 && clienteInputDto.getTelefono() < 1000000000){
            throw new UnprocessableEntityException("Número de teléfono no válido", 422);
        }
    }
}
