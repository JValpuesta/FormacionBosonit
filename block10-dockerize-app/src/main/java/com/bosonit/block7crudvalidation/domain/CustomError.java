package com.bosonit.block7crudvalidation.domain;

import java.util.Date;

public class CustomError {

    private Date timestamp;
    private int HttpCode;
    private String mensaje;

    public CustomError() {
    }

    public CustomError(Date timestamp, int httpCode, String mensaje) {
        this.timestamp = timestamp;
        HttpCode = httpCode;
        this.mensaje = mensaje;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpCode() {
        return HttpCode;
    }

    public void setHttpCode(int httpCode) {
        HttpCode = httpCode;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
