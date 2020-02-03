package com.example.microloanapp.v1.controllers;

import com.example.microloanapp.exceptions.DataInputException;
import com.example.microloanapp.exceptions.LoanDoesntExistException;
import com.example.microloanapp.exceptions.RiskyOperationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RiskyOperationException.class})
    public ResponseEntity<Object> handleRiskyOperation(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("Operation is to risky, loan NOT GRANTED, " + exception.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = {DataInputException.class})
    public ResponseEntity<Object> handleIncorectDataInput(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("Input data incorect, " + exception.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {LoanDoesntExistException.class})
    public ResponseEntity<Object> handleMissingLoan(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("Could not extend payment there is no loan with given id, " + exception.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
