package org.example.exception;

public class GetAllDataByNameException extends RuntimeException {
    public GetAllDataByNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
