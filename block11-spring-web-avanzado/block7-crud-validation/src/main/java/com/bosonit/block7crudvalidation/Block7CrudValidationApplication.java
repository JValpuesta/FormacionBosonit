package com.bosonit.block7crudvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableFeignClients
@SpringBootApplication
public class Block7CrudValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Block7CrudValidationApplication.class, args);
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
