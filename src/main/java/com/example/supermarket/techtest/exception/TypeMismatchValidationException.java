package com.example.supermarket.techtest.exception;


import java.util.Map;

public class TypeMismatchValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public TypeMismatchValidationException(Map<String, String> errors) {
        super("Type mismatch validation failed");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}