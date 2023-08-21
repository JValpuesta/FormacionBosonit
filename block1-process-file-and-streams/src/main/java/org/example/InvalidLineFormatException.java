package org.example;

public class InvalidLineFormatException extends Exception {
    public InvalidLineFormatException(String message) {
        super(message);
    }

    public InvalidLineFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}