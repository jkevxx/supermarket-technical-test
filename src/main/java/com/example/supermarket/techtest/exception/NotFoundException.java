package com.example.supermarket.techtest.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg){
        super(msg);
    }
}
