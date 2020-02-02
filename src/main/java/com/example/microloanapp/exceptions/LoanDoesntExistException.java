package com.example.microloanapp.exceptions;

public class LoanDoesntExistException extends RuntimeException {
    public LoanDoesntExistException() {
        super();
    }

    public LoanDoesntExistException(String message) {
        super(message);
    }
}
