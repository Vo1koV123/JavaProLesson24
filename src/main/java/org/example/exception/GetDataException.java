package org.example.exception;

public class GetDataException extends RuntimeException{
    public GetDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
