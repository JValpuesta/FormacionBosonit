package com.bosonit.block5properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@SpringBootApplication
public class Block5PropertiesApplication {

    public static void main(String[] args) {
		SpringApplication.run(Block5PropertiesApplication.class, args);
    }
	@Component
	public class Componente implements CommandLineRunner {

		@Value("${greeting}")
		private String nombre;
		@Value("${my.number}")
		private int numero;

		@Value("${newproper}")
		private String newproper;

		@Value("${path}")
		private String path;
		@Value("${myurl}")
		private String myurl;
		@Value("${myurl2}")
		private String myurl2;

		@Override
		public void run(String... args) throws Exception {
			log.info("El valor de greeting es: "+nombre);
			log.info("El valor de my.number es: "+numero);
			log.info("El valor de new.property es: "+newproper);
			//MYURL se añade como variable de entorno en el cmd y desde el mismo terminal se ejecuta IntelliJ
			//Añadir como variable de entorno MYURL desde la interfaz de IntelliJ
			log.info("El valor de MYURL es: "+myurl);
			log.info("El valor de MYURL2 es: "+myurl2);
			log.info(path);
		}
	}

	@Component
	//@PropertySource("application.yml") no es necesario
	public class AppConfig implements CommandLineRunner{

		@Value("${yaml.greeting}")
		private String greeting1;
		@Value("${yaml.my.numberyml}")
		private int my1;

		@Override
		public void run(String... args) throws Exception {
			log.info("El valor de greeting es: "+greeting1);
			log.info("El valor de my.number es: "+my1);
		}
	}


}
