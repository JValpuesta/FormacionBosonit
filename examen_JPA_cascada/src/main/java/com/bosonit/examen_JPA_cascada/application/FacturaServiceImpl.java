package com.bosonit.examen_JPA_cascada.application;

import com.bosonit.examen_JPA_cascada.domain.CabeceraFra;
import com.bosonit.examen_JPA_cascada.domain.LineasFra;
import com.bosonit.examen_JPA_cascada.domain.dto.FacturaOutputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.LineaInputDto;
import com.bosonit.examen_JPA_cascada.repository.CabeceraFraRepository;

import com.bosonit.examen_JPA_cascada.repository.LineasFraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    CabeceraFraRepository facturaRepository;
    @Autowired
    LineasFraRepository lineasFraRepository;

    @Override
    public Iterable<FacturaOutputDto> getAllFacturas(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return facturaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(CabeceraFra::facturaToFacturaDto).toList();
    }

    @Override
    public HttpStatusCode deleteFacturaById(int id) {
        Optional<CabeceraFra> optionalCabeceraFra = facturaRepository.findById(id);
        if (optionalCabeceraFra.isPresent()) {
            facturaRepository.deleteById(id);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }

    }

    @Override
    public ResponseEntity<FacturaOutputDto> addLinea(Integer id, LineaInputDto lineaInputDto) throws Exception {
        Optional<CabeceraFra> optionalCabeceraFra = facturaRepository.findById(id);
        if (optionalCabeceraFra.isPresent()) {
            if ((lineaInputDto.getIdFra().equals(id)) && (!lineaInputDto.getProducto().isEmpty())) {
                LineasFra lineasFra = new LineasFra(lineaInputDto);
                CabeceraFra cabeceraFra = optionalCabeceraFra.get();
                lineasFra.setIdFra(cabeceraFra);
                cabeceraFra.getListaLineas().add(lineasFra);
                cabeceraFra.setImporteFra(cabeceraFra.getImporteFra() + lineaInputDto.getImporte());
                lineasFraRepository.save(lineasFra);
                return ResponseEntity.ok().body(optionalCabeceraFra.get().facturaToFacturaDto());
            } else {
                throw new Exception("La linea a insertar no es válida");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FacturaOutputDto());
        }
    }

    /*public void save(FacturaInputDto facturaInputDto) throws Exception {
        Optional<Cliente> optionalCabeceraFra = clienteRepository.findById(facturaInputDto.getIdCli());
        if (optionalCabeceraFra.isPresent()) {
            CabeceraFra cabeceraFra = new CabeceraFra(facturaInputDto);
            cabeceraFra.setIdCli(optionalCabeceraFra.get());
            facturaRepository.save(cabeceraFra);
            List<LineaInputDto> listaLineas = facturaInputDto.getLineas();
            for (LineaInputDto lineaInputDto : listaLineas) {
                LineasFra lineasFra = new LineasFra(lineaInputDto);
                lineasFra.setIdFra(cabeceraFra);
                lineasFraRepository.save(lineasFra);
                cabeceraFra.setImporteFra(cabeceraFra.getImporteFra() + lineaInputDto.getImporte());
                cabeceraFra.getListaLineas().add(lineasFra);
            }
        } else {
            throw new Exception("Cliente de id " + facturaInputDto.getIdCli() + " no encontrado");
        }
    }*/
}