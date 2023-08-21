package com.bosonit.block7crudvalidation;

import com.bosonit.block7crudvalidation.controller.dto.PersonaInputDto;
import com.bosonit.block7crudvalidation.domain.Persona;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

@EnableFeignClients
@SpringBootApplication
public class Block7CrudValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Block7CrudValidationApplication.class, args);
	}
	@Autowired
	PersonaRepository personaRepository;

	@PostConstruct
	public void populateDb() {

		personaRepository.save(new Persona(new PersonaInputDto("lolito", "123", "Manolo",
				"Fernandez","lolo@aa.es", "lolo@lolo.es", "Logroño",
				true, new Date(), "lolo.jpg", new Date(), "ADMIN")));

		personaRepository.save(new Persona(new PersonaInputDto("lolito1", "123", "Manolo",
				"Fernandez","lolo@aa.es", "lolo@lolo.es", "Logroño",
				true, new Date(), "lolo.jpg", new Date(), "USER")));

		personaRepository.save(new Persona(new PersonaInputDto("lolito2", "123", "Manolo",
				"Martinez","lolo@aa.es", "lolo@lolo.es", "Logroño",
				true, new Date(1680558896000L), "lolo.jpg", new Date(), "USER")));

		personaRepository.save(new Persona(new PersonaInputDto("lolita", "123", "Manoli",
				"Fernandez","lolo@aa.es", "lolo@lolo.es", "Logroño",
				true, new Date(1780558896000L), "lolo.jpg", new Date(), "ADMIN")));

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/addperson")
						.allowedOrigins("https://cdpn.io")
						.allowedMethods("POST")
						.allowedHeaders("*")
						.allowCredentials(true);
				registry.addMapping("/getall")
						.allowedOrigins("https://cdpn.io")
						.allowedMethods("GET")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

}
