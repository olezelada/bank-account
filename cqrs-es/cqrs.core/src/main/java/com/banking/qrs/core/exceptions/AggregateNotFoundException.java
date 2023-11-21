package com.banking.qrs.core.exceptions;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(String message){
        super(message);
    }
}
