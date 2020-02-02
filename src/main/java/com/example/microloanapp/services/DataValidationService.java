package com.example.microloanapp.services;

import com.example.microloanapp.model.Loan;

public interface DataValidationService {

    void validateInput(Loan loan);
}
