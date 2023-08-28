package com.bosonit.tripbackend.application;

import com.bosonit.tripbackend.controller.dto.ClienteInputDto;
import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import com.bosonit.tripbackend.controller.dto.ViajeInputDto;
import com.bosonit.tripbackend.controller.dto.ViajeOutputDto;
import com.bosonit.tripbackend.domain.Cliente;
import com.bosonit.tripbackend.domain.Viaje;
import com.bosonit.tripbackend.exceptions.EntityNotFoundException;
import com.bosonit.tripbackend.exceptions.UnprocessableEntityException;
import com.bosonit.tripbackend.repository.ClienteRepository;
import com.bosonit.tripbackend.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViajeServiceImp implements ViajeService {
    @Autowired
    ViajeRepository viajeRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public ViajeOutputDto addViaje(ViajeInputDto viajeInputDto) throws UnprocessableEntityException {
        this.checkViaje(viajeInputDto);
        return viajeRepository.save(new Viaje(viajeInputDto)).viajeToViajeOutputDto();
    }

    @Override
    public ViajeOutputDto getViajeById(Integer idViaje) throws EntityNotFoundException {
        Optional<Viaje> optionalViaje = viajeRepository.findById(idViaje);
        if (optionalViaje.isEmpty()) {
            throw new EntityNotFoundException("Viaje con ID: " + idViaje + " no encontrado", 404);
        } else {
            return optionalViaje.get().viajeToViajeOutputDto();
        }
    }

    @Override
    public List<ViajeOutputDto> getAllViajes(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return viajeRepository.findAll(pageRequest).getContent().stream().map(Viaje::viajeToViajeOutputDto).toList();
    }

    @Override
    public void deleteViajeById(Integer idViaje) throws EntityNotFoundException {
        if (viajeRepository.findById(idViaje).isEmpty()) {
            throw new EntityNotFoundException("Viaje con ID: " + idViaje + " no encontrado", 404);
        } else {
            viajeRepository.deleteById(idViaje);
        }
    }

    @Override
    public ViajeOutputDto updateViajeById(ViajeInputDto viajeInputDto, Integer idViaje) throws EntityNotFoundException, UnprocessableEntityException {
        if (viajeRepository.findById(idViaje).isEmpty()) {
            throw new EntityNotFoundException("Viaje con ID: " + idViaje + " no encontrado", 404);
        } else {
            this.checkViaje(viajeInputDto);
            viajeRepository.deleteById(idViaje);
            Viaje viaje = viajeRepository.save(new Viaje(viajeInputDto));
            return viaje.viajeToViajeOutputDto();
        }
    }

    @Override
    public ClienteOutputDto addClienteToViaje(Integer idCliente, Integer idViaje)
            throws EntityNotFoundException, UnprocessableEntityException {
        Optional<Viaje> optionalViaje = viajeRepository.findById(idViaje);
        if (optionalViaje.isEmpty()) {
            throw new EntityNotFoundException("Viaje con ID: " + idViaje + " no encontrado", 404);
        } else {
            List<Cliente> clienteList = optionalViaje.get().getPassenger();
            if (clienteList.size()>=40) {
                throw new UnprocessableEntityException("No hay sitios disponibles", 422);
            } else {
                Optional<Cliente> optionalCliente = clienteRepository.findById(idCliente);
                if (optionalCliente.isEmpty()) {
                    throw new EntityNotFoundException("Cliente con ID: " + idCliente + " no encontrado", 404);
                } else {
                    Cliente cliente = optionalCliente.get();
                    clienteList.add(cliente);
                    viajeRepository.save(optionalViaje.get());
                    return cliente.clienteToClienteOutputDto();
                }
            }
        }
    }

    @Override
    public Integer getCountClientes(Integer idViaje) throws EntityNotFoundException {
        Optional<Viaje> optionalViaje = viajeRepository.findById(idViaje);
        if(optionalViaje.isEmpty()){
            throw new EntityNotFoundException("Viaje con ID: " + idViaje + " no encontrado", 404);
        } else {
            return optionalViaje.get().getPassenger().size();
        }
    }

    @Override
    public ViajeOutputDto updateViajeStatus(Integer idViaje, Boolean status) throws EntityNotFoundException {
        Optional<Viaje> optionalViaje = viajeRepository.findById(idViaje);
        if(optionalViaje.isEmpty()){
            throw new EntityNotFoundException("Viaje con ID: " + idViaje + " no encontrado", 404);
        } else {
            Viaje viaje = optionalViaje.get();
            viaje.setStatus(status);
            return viajeRepository.save(viaje).viajeToViajeOutputDto();
        }
    }

    @Override
    public Boolean getViajeStatus(Integer idViaje) throws EntityNotFoundException{
        Optional<Viaje> optionalViaje = viajeRepository.findById(idViaje);
        if(optionalViaje.isEmpty()){
            throw new EntityNotFoundException("Viaje con ID: " + idViaje + " no encontrado", 404);
        } else {
            return optionalViaje.get().getStatus();
        }
    }

    public void checkViaje(ViajeInputDto viajeInputDto) throws UnprocessableEntityException {

        if(viajeInputDto.getOrigin().isBlank()){
            throw new UnprocessableEntityException("El campo origen no puede estar vacío", 422);
        }
        if(viajeInputDto.getDestination().isBlank()){
            throw new UnprocessableEntityException("El campo destino no puede estar vacío", 422);
        }
        if(viajeInputDto.getArrivalDate().before(viajeInputDto.getDepartureDate())){
            throw new UnprocessableEntityException("La fecha de llegada no puede ser anterior a la de salida", 422);
        }

    }
}
