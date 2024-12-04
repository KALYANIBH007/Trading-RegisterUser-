package com.example.trading.exception;

public class KafkaServerDownException extends RuntimeException{
    public KafkaServerDownException(String message){
        super(message);
    }
}
