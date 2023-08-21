package com.bosonit.examen_JPA_cascada;

import com.bosonit.examen_JPA_cascada.application.FacturaService;
import com.bosonit.examen_JPA_cascada.domain.CabeceraFra;
import com.bosonit.examen_JPA_cascada.domain.Cliente;
import com.bosonit.examen_JPA_cascada.domain.LineasFra;
import com.bosonit.examen_JPA_cascada.domain.dto.ClienteInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.FacturaInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.LineaInputDto;
import com.bosonit.examen_JPA_cascada.domain.dto.LineaOutputDto;
import com.bosonit.examen_JPA_cascada.repository.CabeceraFraRepository;
import com.bosonit.examen_JPA_cascada.repository.ClienteRepository;
import com.bosonit.examen_JPA_cascada.repository.LineasFraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ExamenJpaCascadaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExamenJpaCascadaApplication.class, args);
	}

	@Autowired
	FacturaService facturaService;
	@Autowired
	CabeceraFraRepository facturaRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	LineasFraRepository lineasFraRepository;

	@Override
	public void run(String... args) throws Exception {
		Cliente cliente1 = new Cliente(new ClienteInputDto("Pepe"));
		clienteRepository.save(cliente1);

		CabeceraFra factura1 = new CabeceraFra(1, cliente1, 0, new ArrayList<>());
		LineasFra linea1 = new LineasFra(1, factura1, "aguacate", 5, 3);
		LineasFra linea2 = new LineasFra(2, factura1, "papel", 25, 7);
		List<LineasFra> lista = new ArrayList<>();
		lista.add(linea1);
		lista.add(linea2);
		factura1.setImporteFra(factura1.getImporteFra()+linea1.getPrecio()+linea2.getPrecio());
		factura1.setListaLineas(lista);
		facturaRepository.save(factura1);


		/*LineaInputDto linea1 = new LineaInputDto("aguacate", 5, 3, 0);
		LineaInputDto linea2 = new LineaInputDto("papel", 25, 7, 0);
		FacturaInputDto factura1 = new FacturaInputDto(cliente1.getId(), 0, new ArrayList<>());
		factura1.getLineas().add(linea1);
		factura1.getLineas().add(linea2);
		facturaService.save(factura1);*/
	}
}
