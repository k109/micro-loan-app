package com.example.microloanapp.services;

import com.example.microloanapp.constants.LoanApplicationConstraints;
import com.example.microloanapp.exceptions.LoanDoesntExistException;
import com.example.microloanapp.model.Loan;
import com.example.microloanapp.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService{

    private static final int NUMBER_OF_GRACE_PERIOD_DAYS = LoanApplicationConstraints.NUMBER_OF_DAYS_WHEN_POSTPONING_PAYMENT;

    private final LoanRepository loanRepository;
    private final DataValidationService dataValidationService;
    private final RiskAnalysisService riskAnalysisService;
    private final DateTimeProviderService dateTimeProviderService;

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan findById(Long id) {
        return Optional.ofNullable(loanRepository.findById(id))
                .map(Optional::get)
                .orElseThrow(() -> new LoanDoesntExistException("Loan with id: " + id + " doesn't exist"));
    }

    @Override
    public Loan createNewLoan(Loan loan) {
        dataValidationService.validateInput(loan);
        riskAnalysisService.assesRisk(loan);

        int numberOfMonths = loan.getNumberOfMonths();
        loan.setStartDate(dateTimeProviderService.getCurrentDate());
        loan.setEndDate(dateTimeProviderService.getCurrentDate().plusMonths(numberOfMonths));
        return loanRepository.save(loan);
    }

    @Override
    public Loan extendPaymentPeriod(Long loanId) {
        Loan loan = findById(loanId);
        if(!loan.isPaymentPeriodExtended()) {
            loan.setPaymentPeriodExtended(true);
            LocalDate currentPaymentDate = loan.getEndDate();
            loan.setEndDate(currentPaymentDate.plusDays(NUMBER_OF_GRACE_PERIOD_DAYS));
            loan = loanRepository.save(loan);
        }
        return loan;
    }
}
