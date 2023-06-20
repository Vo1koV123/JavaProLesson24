package org.example.exception;

public class GetAllDataByIdException extends RuntimeException{
    public GetAllDataByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
