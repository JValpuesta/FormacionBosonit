package com.bosonit.block6personcontrollers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonaConfiguration {

    @Bean
    public Persona bean1(){
        return new Persona("Juan Carlos", "Madrid",78);
    }
    @Bean
    public Persona bean2(){
        return  new Persona("Rafael", "Mallorca",47);
    }
    @Bean
    public Persona bean3(){
        return new Persona("Merche", "Cadiz",25);
    }

}

