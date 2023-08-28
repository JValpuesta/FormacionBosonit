package com.bosonit.tripbackend.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityNotFoundException extends Exception{
    private Date timestamp;
    private int httpCode;
    public EntityNotFoundException(String message, int codigo) {
        super(message);
        setHttpCode(codigo);
        setTimestamp(new Date());
    }
}
