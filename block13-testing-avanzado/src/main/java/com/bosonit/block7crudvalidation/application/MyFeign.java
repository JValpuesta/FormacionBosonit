package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.ProfesorOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url = "http://localhost:8081", name = "myFeign")
public interface MyFeign {
    @GetMapping(value = "/profesor/{id}")
    ProfesorOutputDto getProfesorById(@PathVariable int id);
}
