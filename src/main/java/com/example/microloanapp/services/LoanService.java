package com.example.microloanapp.services;

import com.example.microloanapp.model.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> findAllLoans();

    Loan findById(Long id);

    Loan createNewLoan(Loan loan);

    Loan extendPaymentPeriod(Long loanId);
}
