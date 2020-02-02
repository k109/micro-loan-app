package com.example.microloanapp.services;

import com.example.microloanapp.model.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class LoanServiceTestData {

    public static List<Loan> getListOfThreeStoredLoans() {
        return Arrays.asList(new Loan(), new Loan(), new Loan());
    }

    public static Loan getExampleLoanWithId(long id) {
        Loan loan = new Loan();
        loan.setId(id);
        return loan;
    }

    public static Loan getExampleLoanWith(long id, BigDecimal amount, int numberOfMonths, boolean periodExtended) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setRequestedAmount(amount);
        loan.setNumberOfMonths(numberOfMonths);
        loan.setPaymentPeriodExtended(periodExtended);
        return loan;
    }

    public static Loan getExampleLoanWithPeriodPaymentNotYetExtendedWithIdAndEndDate(Long id, LocalDate endDate) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setEndDate(endDate);
        loan.setPaymentPeriodExtended(false);
        return loan;
    }

    public static Loan getExampleLoanWithPeriodPaymentAlreadyExtendedWithIdAndEndDate(Long id, LocalDate endDate) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setEndDate(endDate);
        loan.setPaymentPeriodExtended(true);
        return loan;
    }
}
