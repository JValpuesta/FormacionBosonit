package com.bosonit.block7crudvalidation.domain;

import java.util.Date;

public class UnprocessableEntityException extends RuntimeException {
    private CustomError customError;

    public UnprocessableEntityException(String mensaje) {
        super(mensaje);
        this.customError = new CustomError();
        this.customError.setTimestamp(new Date());
        this.customError.setHttpCode(422);
        this.customError.setMensaje(mensaje);
    }

    public CustomError getCustomError() {
        return customError;
    }
}

