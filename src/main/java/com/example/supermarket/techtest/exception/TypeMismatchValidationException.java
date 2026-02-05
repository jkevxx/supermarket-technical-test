package com.example.supermarket.techtest.exception;


import lombok.Getter;

import java.util.Map;

@Getter
public class TypeMismatchValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public TypeMismatchValidationException(Map<String, String> errors) {
        super("Type mismatch validation failed");
        this.errors = errors;
    }

}