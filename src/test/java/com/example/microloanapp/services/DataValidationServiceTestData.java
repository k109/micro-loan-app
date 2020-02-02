package com.example.microloanapp.services;

import com.example.microloanapp.model.Loan;

import java.math.BigDecimal;

public class DataValidationServiceTestData {

    private static final int CORRECT_NUMBER_OF_MONTHS_EXAMPLE = 1;
    private static final int INCORRECT_NUMBER_OF_MONTHS_EXAMPLE = 10;
    private static final BigDecimal CORRECT_AMOUNT_EXAMPLE = new BigDecimal(1000);
    private static final BigDecimal INCORRECT_AMOUNT_EXAMPLE = new BigDecimal(6000);

    public static Loan getCorrectLoan() {
        Loan loan = new Loan();
        loan.setNumberOfMonths(CORRECT_NUMBER_OF_MONTHS_EXAMPLE);
        loan.setRequestedAmount(CORRECT_AMOUNT_EXAMPLE);
        return loan;
    }

    public static Loan getLoanWithIncorrectNumberOfMonths() {
        Loan loan = new Loan();
        loan.setNumberOfMonths(INCORRECT_NUMBER_OF_MONTHS_EXAMPLE);
        loan.setRequestedAmount(CORRECT_AMOUNT_EXAMPLE);
        return loan;
    }

    public static Loan getLoanWithIncorrectAmount() {
        Loan loan = new Loan();
        loan.setNumberOfMonths(CORRECT_NUMBER_OF_MONTHS_EXAMPLE);
        loan.setRequestedAmount(INCORRECT_AMOUNT_EXAMPLE);
        return loan;
    }
}
