package com.bosonit.examen_JPA_cascada.controller;

import com.bosonit.examen_JPA_cascada.application.FacturaServiceImpl;
import com.bosonit.examen_JPA_cascada.domain.dto.FacturaOutputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.LineaInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/factura")
public class FacturaController {
    @Autowired
    FacturaServiceImpl facturaService;

    @GetMapping
    public ResponseEntity<Iterable<FacturaOutputDto>> getFacturas(@RequestParam(defaultValue = "0", required = false)
                                                                  int pageNumber,
                                                                  @RequestParam(defaultValue = "4", required = false)
                                                                  int pageSize) {
        return ResponseEntity.ok().body(facturaService.getAllFacturas(pageNumber, pageSize));
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatusCode deleteFactura(@PathVariable int id) {
        return facturaService.deleteFacturaById(id);
    }

    @PostMapping(value = "/linea/{id}")
    public ResponseEntity<FacturaOutputDto> addLinea(@PathVariable int id, @RequestBody LineaInputDto lineaInputDto) throws Exception{
        return facturaService.addLinea(id, lineaInputDto);
    }

}