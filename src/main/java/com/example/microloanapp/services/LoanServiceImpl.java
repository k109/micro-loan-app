package com.example.microloanapp.services;

import com.example.microloanapp.model.Loan;
import com.example.microloanapp.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Loan> findAllLoans() {
        return null;
    }

    @Override
    public Loan findById(Long id) {
        return null;
    }

    @Override
    public Loan createNewLoan(Loan loan) {
        return null;
    }

    @Override
    public Loan extendPaymentPeriod(Long loanId) {
        return null;
    }
}
