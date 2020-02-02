package com.example.microloanapp.services;

import com.example.microloanapp.constants.LoanApplicationConstraints;
import com.example.microloanapp.exceptions.RiskyOperationException;
import com.example.microloanapp.model.Loan;
import com.example.microloanapp.repositories.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class RiskAnalysisServiceImpl implements RiskAnalysisService{

    private static final BigDecimal MAX_ALLOWED_AMOUNT = LoanApplicationConstraints.MAX_ALLOWED_AMOUNT;
    private static final LocalTime BEGIN_OF_HAZARDOUS_TIMEFRAME = LoanApplicationConstraints.BEGIN_OF_HAZARDOUS_TIMEFRAME;
    private static final LocalTime END_OF_HAZARDOUS_TIMEFRAME = LoanApplicationConstraints.END_OF_HAZARDOUS_TIMEFRAME;
    private static final long MAX_NUMBER_OF_LOANS_PER_IP = LoanApplicationConstraints.MAX_NUMBER_OF_LOANS_PER_IP;

    private final LoanRepository loanRepository;
    private final DateTimeProviderService dateTimeProviderService;

    @Override
    public void assesRisk(Loan loan) {
        checkAmountAndTimeFrame(loan.getRequestedAmount());
        checkClientIp(loan.getIp());
    }

    private boolean isMaxAmountRequested(BigDecimal requestedAmount) {
        return requestedAmount.compareTo(MAX_ALLOWED_AMOUNT) >= 0;
    }

    private boolean isTimeFrameHazardous() {
        LocalTime time = dateTimeProviderService.getCurrentTime();
        return time.compareTo(BEGIN_OF_HAZARDOUS_TIMEFRAME) >= 0
                && time.compareTo(END_OF_HAZARDOUS_TIMEFRAME) < 0;
    }

    private void checkAmountAndTimeFrame(BigDecimal requestedAmount) {
        if(isMaxAmountRequested(requestedAmount) && isTimeFrameHazardous()) {
            throw new RiskyOperationException("Cannot approve such amount at this time");
        }
    }

    private void checkClientIp(String ip) {
        Long numberOfLoans = loanRepository.findAll().stream()
                .filter(l -> l.getIp().equalsIgnoreCase(ip))
                .count();
        if(numberOfLoans == MAX_NUMBER_OF_LOANS_PER_IP) {
            throw new RiskyOperationException("Max number of loans reached for this ip: " + ip);
        }
    }
}
