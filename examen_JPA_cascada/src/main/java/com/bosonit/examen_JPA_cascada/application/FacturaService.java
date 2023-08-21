package com.bosonit.examen_JPA_cascada.application;

import com.bosonit.examen_JPA_cascada.domain.dto.FacturaInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.FacturaOutputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.LineaInputDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public interface FacturaService {

    Iterable<FacturaOutputDto> getAllFacturas(int pageNumber, int pageSize);

    HttpStatusCode deleteFacturaById(int id);

    ResponseEntity<FacturaOutputDto> addLinea(Integer id, LineaInputDto lineaInputDto) throws Exception;

    //void save(FacturaInputDto facturaInputDto) throws Exception;
}
