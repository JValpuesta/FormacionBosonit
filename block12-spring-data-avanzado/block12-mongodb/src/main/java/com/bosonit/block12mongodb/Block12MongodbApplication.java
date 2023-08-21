package com.bosonit.block12mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class Block12MongodbApplication implements CommandLineRunner {

	private final PersonDAL personDAL;

	@Autowired
	public Block12MongodbApplication(PersonDAL personDAL) {
		this.personDAL = personDAL;
	}

	public static void main(String[] args) {
		SpringApplication.run(Block12MongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		personDAL.savePerson(new Person(
				"Shubham", Arrays.asList("Harry potter", "Waking Up"), new Date(769372200000L)));
		personDAL.savePerson(new Person(
				"Sergey", Arrays.asList("Startup Guides", "Java"), new Date(664309800000L)));
	}
}
