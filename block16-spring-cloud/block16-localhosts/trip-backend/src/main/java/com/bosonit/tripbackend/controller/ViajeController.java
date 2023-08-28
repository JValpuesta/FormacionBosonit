package com.bosonit.tripbackend.controller;

import com.bosonit.tripbackend.application.ViajeService;
import com.bosonit.tripbackend.controller.dto.ClienteOutputDto;
import com.bosonit.tripbackend.controller.dto.ViajeInputDto;
import com.bosonit.tripbackend.controller.dto.ViajeOutputDto;
import com.bosonit.tripbackend.exceptions.EntityNotFoundException;
import com.bosonit.tripbackend.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/viaje")
public class ViajeController {

    @Autowired
    ViajeService viajeService;

    @PostMapping
    public ResponseEntity<ViajeOutputDto> addViaje(@RequestBody ViajeInputDto viajeInputDto)
            throws UnprocessableEntityException {
        URI location = URI.create("/viaje");
        return ResponseEntity.created(location).body(viajeService.addViaje(viajeInputDto));
    }

    @GetMapping("/{idViaje}")
    public ViajeOutputDto getViajeById(@PathVariable Integer idViaje) throws EntityNotFoundException {
        return viajeService.getViajeById(idViaje);
    }

    @GetMapping
    public Iterable<ViajeOutputDto> getAllViajes(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                              @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return viajeService.getAllViajes(pageNumber, pageSize);
    }

    @DeleteMapping("/{idViaje}")
    public void deleteViajeById(@PathVariable Integer idViaje) throws EntityNotFoundException {
        viajeService.deleteViajeById(idViaje);
    }

    @PutMapping("/{idViaje}")
    public ViajeOutputDto updateViajeById(@RequestBody ViajeInputDto viajeInputDto, @PathVariable Integer idViaje)
            throws UnprocessableEntityException, EntityNotFoundException {
        return viajeService.updateViajeById(viajeInputDto, idViaje);
    }

    @PutMapping("/{idViaje}/{status}")
    public ViajeOutputDto updateViajeStatus(@PathVariable Integer idViaje, @PathVariable Boolean status)
            throws EntityNotFoundException {
        return viajeService.updateViajeStatus(idViaje, status);
    }

    @PutMapping("/addCliente/{idCliente}/{idViaje}")
    public ResponseEntity<ClienteOutputDto> addPassengerToTrip(@PathVariable Integer idCliente,
                                                               @PathVariable Integer idViaje)
            throws UnprocessableEntityException, EntityNotFoundException {
        return ResponseEntity.ok().body(viajeService.addClienteToViaje(idCliente, idViaje));
    }

    @GetMapping("/count/{idViaje}")
    public ResponseEntity<Integer> getCountClientes(@PathVariable Integer idViaje) throws EntityNotFoundException {
        return ResponseEntity.ok().body(viajeService.getCountClientes(idViaje));
    }

    @GetMapping("/status/{idViaje}")
    public Boolean getViajeStatus(@PathVariable Integer idViaje) throws EntityNotFoundException {
        return viajeService.getViajeStatus(idViaje);
    }

}
