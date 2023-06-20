package org.example.exception;

public class SaveDataException extends RuntimeException {
    public SaveDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
