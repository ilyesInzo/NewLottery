package org.example.exception;

public class ConfigInvalidException extends RuntimeException {
    public ConfigInvalidException(String message) {
        super(message);
    }
}
