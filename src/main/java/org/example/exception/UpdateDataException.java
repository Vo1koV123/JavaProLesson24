package org.example.exception;

public class UpdateDataException extends RuntimeException {
    public UpdateDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
