package com.example.trading.exception;

public class PanNumberAlreadyExistsException extends RuntimeException{
    public PanNumberAlreadyExistsException(String message){
        super(message);
    }
}
