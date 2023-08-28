package com.bosonit.tripbackend.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnprocessableEntityException extends  Exception{
    private Date timestamp;
    private int httpCode;
    public UnprocessableEntityException(String message, int codigo) {
        super(message);
        setHttpCode(codigo);
        setTimestamp(new Date());
    }
}
