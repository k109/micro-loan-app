package com.example.microloanapp.exceptions;

public class RiskyOperationException extends RuntimeException {

    public RiskyOperationException() {
        super();
    }

    public RiskyOperationException(String message) {
        super(message);
    }
}
