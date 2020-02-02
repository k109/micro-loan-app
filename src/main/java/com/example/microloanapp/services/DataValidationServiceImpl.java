package com.example.microloanapp.services;

import com.example.microloanapp.exceptions.DataInputException;
import com.example.microloanapp.model.Loan;

import java.math.BigDecimal;

public class DataValidationServiceImpl implements DataValidationService {

    private static final int MIN_NUMBER_OF_MONTHS = 1;
    private static final int MAX_NUMBER_OF_MONTHS = 6;
    private static final BigDecimal MIN_ALLOWED_AMOUNT = new BigDecimal(500);
    private static final BigDecimal MAX_ALLOWED_AMOUNT = new BigDecimal(5000);

    @Override
    public void validateInput(Loan loan) {
        checkLoanDurationValidity(loan.getNumberOfMonths());
        checkRequestedAmount(loan.getRequestedAmount());
    }

    private void checkLoanDurationValidity(int requestedNumberOfMonths) {
        if(requestedNumberOfMonths < MIN_NUMBER_OF_MONTHS || requestedNumberOfMonths > MAX_NUMBER_OF_MONTHS) {
            throw new DataInputException("Requested loan period must be in range: " + MIN_NUMBER_OF_MONTHS + " - " + MAX_NUMBER_OF_MONTHS);
        }
    }

    private void checkRequestedAmount(BigDecimal amount) {
        if(amount.compareTo(MIN_ALLOWED_AMOUNT) < 0 || amount.compareTo(MAX_ALLOWED_AMOUNT) > 0) {
            throw new DataInputException("Requested amount is out of acceptable range: " + MIN_ALLOWED_AMOUNT + " - " + MIN_ALLOWED_AMOUNT);
        }
    }
}
