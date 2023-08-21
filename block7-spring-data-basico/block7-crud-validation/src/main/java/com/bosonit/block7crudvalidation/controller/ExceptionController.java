package com.bosonit.block7crudvalidation.controller;

import com.bosonit.block7crudvalidation.domain.CustomError;
import com.bosonit.block7crudvalidation.domain.EntityNotFoundException;
import com.bosonit.block7crudvalidation.domain.UnprocessableEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomError error = ex.getCustomError();
        return ResponseEntity.status(error.getHttpCode()).body(error);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<CustomError> handleUnprocessableEntityException(UnprocessableEntityException ex) {
        CustomError error = ex.getCustomError();
        return ResponseEntity.status(error.getHttpCode()).body(error);
    }
}