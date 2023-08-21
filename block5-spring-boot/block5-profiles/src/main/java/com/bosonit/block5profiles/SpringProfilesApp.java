package com.bosonit.block5profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SpringProfilesApp implements CommandLineRunner {


    private final Environment environment;
    @Value("${bd.url}")
    private String url;

    public static void main(String[] args) {
        SpringApplication.run(SpringProfilesApp.class, args);
    }

    @Override
    public void run(String... args) {
        String activeProfile = environment.getProperty("spring.profiles.active");

        System.out.println("Active profile: " + activeProfile);
        System.out.println("URL de la BD local: "+url);
/*        if (activeProfile != null) {
            switch (activeProfile) {
                case "local":
                    System.out.println("URL de la BD local: "+url);

                    break;
                case "int":
                    System.out.println("URL de la BD int: "+url);
                    // Acciones específicas para el perfil "INT"
                    break;
                case "pro":
                    System.out.println("URL de la BD pro: "+url);
                    // Acciones específicas para el perfil "PRO"
                    break;
                default:
                    System.out.println("Invalid profile name.");
                    break;
            }
        } else {
            System.out.println("spring.profiles.active is not set.");
        }
        */

    }


}
