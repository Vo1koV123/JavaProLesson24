package org.example.exception;

public class RemoveDataException extends RuntimeException {
    public RemoveDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
