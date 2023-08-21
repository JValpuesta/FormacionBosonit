package com.bosonit.block7crudvalidation.security;

import com.bosonit.block7crudvalidation.domain.Persona;
import com.bosonit.block7crudvalidation.domain.EntityNotFoundException;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam Map<String, String> requestMap) {
        String username = requestMap.get("username");
        String password = requestMap.get("password");

        Optional<Persona> optionalPersona = personaRepository.findByUsuario(username);
        if(optionalPersona.isEmpty()){
            throw new EntityNotFoundException("El nombre de usuario no existe");
        }

        Persona persona = optionalPersona.get();

        if (!persona.getPassword().equals(password)) {
            throw new EntityNotFoundException("Password incorrecta");
        }

        String role = Boolean.TRUE.equals(persona.getAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";
        return new ResponseEntity<>(jwtToken.generateToken(username, role), HttpStatus.OK);
    }
}
