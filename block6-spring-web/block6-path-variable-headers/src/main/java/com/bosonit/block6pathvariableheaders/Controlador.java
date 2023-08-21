package com.bosonit.block6pathvariableheaders;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controlador {

    @PostMapping("/1")
    public Person enviarRecibir(@RequestBody Person persona) {
        return persona;
    }

    @GetMapping("/user/{id}")
    public String obtenerId(@PathVariable String id) {
        return "El id mandado es: " + id;
    }

    @PutMapping("/post")
    public Map<String, String> obtenerDatos(@RequestParam String var1, @RequestParam String var2) {
        Map<String, String> datos = new HashMap<>();
        datos.put("var1", var1);
        datos.put("var2", var2);
        return datos;
    }

    @GetMapping("/header")
    public HttpHeaders obtenerHeaders(@RequestHeader("h1") String h1, @RequestHeader("h2") String h2) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("h1", h1);
        headers.add("H2", h2);
        return headers;
    }

    @PostMapping("/all")
    public Respuesta obtenerTodo(
            @RequestBody(required = false) String body,
            @RequestParam(required = false) Map<String, String> params,
            @RequestHeader HttpHeaders headers
    ) {
        // Si el body es nulo o vacío, establecer un valor predeterminado
        if (body == null || body.isEmpty()) {
            body = "No se proporcionó ningún body en la solicitud.";
        }
        List<String> requestParams = new ArrayList<>();
        // Si requestParams es nulo, inicializar una lista vacía
        if (params != null) {
            params.forEach((key, value) -> requestParams.add(key + ": " + value));
        }

        // Obtener los headers de la solicitud y agregarlos a la lista headers
        List<String> requestHeaders = new ArrayList<>();
        headers.forEach((key, value) -> requestHeaders.add(key + ": " + value));

        return new Respuesta(body, requestParams, requestHeaders);
    }

}
