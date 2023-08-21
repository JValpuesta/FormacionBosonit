package com.bosonit.block7crudvalidation.domain;

import java.util.Date;

public class EntityNotFoundException extends RuntimeException {
    private CustomError customError;

    public EntityNotFoundException(String mensaje) {
        super(mensaje);
        customError = new CustomError(new Date(), 404, mensaje);
    }

    public CustomError getCustomError() {
        return customError;
    }

}
