package com.example.microloanapp.services;

import com.example.microloanapp.exceptions.RiskyOperationException;
import com.example.microloanapp.model.Loan;
import com.example.microloanapp.repositories.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class RiskAnalysisServiceTest {

    private static final LocalTime TIME_OUTSIDE_HAZARDOUS_TIMEFRAME = LocalTime.of(10,15,0,0);
    private static final LocalTime TIME_INSIDE_HAZARDOUS_TIMEFRAME = LocalTime.of(3,15,0,0);
    private static final long CORRECT_NUMBER_OF_ALREADY_TAKEN_LOANS_USING_SPECIFIC_IP_TO_TAKE_ANOTHER = 1;
    private static final long INCORRECT_NUMBER_OF_ALREADY_TAKEN_LOANS_USING_SPECIFIC_IP_TO_TAKE_ANOTHER = 2;

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private DateTimeProviderService dateTimeProviderService;
    private RiskAnalysisService riskAnalysisService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        riskAnalysisService = new RiskAnalysisServiceImpl(loanRepository, dateTimeProviderService);
    }

    @Test
    public void loanIsNotRisky() {

        //given
        Loan loan = RiskAnalysisServiceTestData.getLoanWithCorrectPeriodAndMaxAmountRequested();
        lenient().when(dateTimeProviderService.getCurrentTime()).thenReturn(TIME_OUTSIDE_HAZARDOUS_TIMEFRAME);
        lenient().when(loanRepository.countAllByIp(anyString())).thenReturn(CORRECT_NUMBER_OF_ALREADY_TAKEN_LOANS_USING_SPECIFIC_IP_TO_TAKE_ANOTHER);

        //when
        riskAnalysisService.assesRisk(loan);
        //then
        Assertions.assertDoesNotThrow(() -> new RiskyOperationException());
    }

    @Test
    public void loanIsRiskyBecauseOfMaxRequestedAmountAndHazardousTimeframe() {

        //given
        Loan loan = RiskAnalysisServiceTestData.getLoanWithCorrectPeriodAndMaxAmountRequested();
        lenient().when(dateTimeProviderService.getCurrentTime()).thenReturn(TIME_INSIDE_HAZARDOUS_TIMEFRAME);
        lenient().when(loanRepository.countAllByIp(anyString())).thenReturn(CORRECT_NUMBER_OF_ALREADY_TAKEN_LOANS_USING_SPECIFIC_IP_TO_TAKE_ANOTHER);

        //when
        //then
        Assertions.assertThrows(RiskyOperationException.class, () -> riskAnalysisService.assesRisk(loan));
    }

    @Test
    public void loanIsRiskyBecauseTooManyLoansTakenFromTheSameIp() {

        //given
        Loan loan = RiskAnalysisServiceTestData.getLoanWithCorrectPeriodAndMaxAmountRequested();
        lenient().when(dateTimeProviderService.getCurrentTime()).thenReturn(TIME_OUTSIDE_HAZARDOUS_TIMEFRAME);
        lenient().when(loanRepository.countAllByIp(anyString())).thenReturn(INCORRECT_NUMBER_OF_ALREADY_TAKEN_LOANS_USING_SPECIFIC_IP_TO_TAKE_ANOTHER);

        //when
        //then
        Assertions.assertThrows(RiskyOperationException.class, () -> riskAnalysisService.assesRisk(loan));
    }
}
