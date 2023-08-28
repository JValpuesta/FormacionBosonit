package com.bosonit.tripfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TripFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripFrontendApplication.class, args);
	}

}
