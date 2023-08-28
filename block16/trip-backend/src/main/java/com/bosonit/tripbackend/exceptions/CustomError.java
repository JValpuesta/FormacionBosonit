package com.bosonit.tripbackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {
    private Date timestamp;
    private int httpCode;
    private String message;

    public CustomError(String message, int codigo) {
        setMessage(message);
        setHttpCode(codigo);
        setTimestamp(new Date());
    }
}
