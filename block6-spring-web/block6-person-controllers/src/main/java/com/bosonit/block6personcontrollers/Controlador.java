package com.bosonit.block6personcontrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controlador")
public class Controlador {

    @Autowired
    @Qualifier("bean1")
    private Persona bean1;
    @Autowired
    @Qualifier("bean2")
    private Persona bean2;
    @Autowired
    @Qualifier("bean3")
    private Persona bean3;

    @GetMapping(value= "/bean/{id}")
    public ResponseEntity<Persona> persona(@PathVariable String id) {
        switch (id){
            case "bean1": return ResponseEntity.ok(bean1);
            case "bean2": return ResponseEntity.ok(bean2);
            case "bean3": return ResponseEntity.ok(bean3);
            default: return null;
        }
    }

}
