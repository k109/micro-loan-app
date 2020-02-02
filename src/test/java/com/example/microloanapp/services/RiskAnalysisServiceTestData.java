package com.example.microloanapp.services;

import com.example.microloanapp.model.Loan;

import java.math.BigDecimal;

public class RiskAnalysisServiceTestData {

    private static final BigDecimal MAX_ALLOWED_AMOUNT = new BigDecimal(5000);
    private static final int NUMBER_OF_MONTHS = 5;
    private static final String TEST_IP = "192.168.0.0";

    public static Loan getLoanWithCorrectPeriodAndMaxAmountRequested() {
        Loan loan = new Loan();
        loan.setNumberOfMonths(NUMBER_OF_MONTHS);
        loan.setRequestedAmount(MAX_ALLOWED_AMOUNT);
        loan.setIp(TEST_IP);

        return loan;
    }
}
