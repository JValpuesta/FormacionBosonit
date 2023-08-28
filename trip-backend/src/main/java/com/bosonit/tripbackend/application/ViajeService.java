package com.bosonit.tripbackend.application;


import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import com.bosonit.tripbackend.controller.dto.ViajeInputDto;
import com.bosonit.tripbackend.controller.dto.ViajeOutputDto;
import com.bosonit.tripbackend.exceptions.EntityNotFoundException;
import com.bosonit.tripbackend.exceptions.UnprocessableEntityException;

import java.util.List;

public interface ViajeService {

    ViajeOutputDto addViaje(ViajeInputDto viajeInputDto) throws UnprocessableEntityException;
    ViajeOutputDto getViajeById(Integer idViaje) throws EntityNotFoundException;
    List<ViajeOutputDto> getAllViajes(int pageNumber, int pageSize);
    void deleteViajeById(Integer idViaje) throws EntityNotFoundException;
    ViajeOutputDto updateViajeById(ViajeInputDto viajeInputDto, Integer idViaje)
            throws EntityNotFoundException, UnprocessableEntityException;
    ClienteOutputDto addClienteToViaje(Integer idClient, Integer idViaje)
            throws EntityNotFoundException, UnprocessableEntityException;
    Integer getCountClientes(Integer idViaje) throws EntityNotFoundException;
    ViajeOutputDto updateViajeStatus(Integer idViaje, Boolean status) throws EntityNotFoundException;
    Boolean getViajeStatus(Integer idViaje) throws EntityNotFoundException;

}
