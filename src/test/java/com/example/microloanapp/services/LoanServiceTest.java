package com.example.microloanapp.services;

import com.example.microloanapp.exceptions.DataInputException;
import com.example.microloanapp.exceptions.LoanDoesntExistException;
import com.example.microloanapp.exceptions.RiskyOperationException;
import com.example.microloanapp.model.Loan;
import com.example.microloanapp.repositories.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    private static final long ID = 1L;
    private static final long NOT_EXISTING_ID = 123L;
    public static final int CORRECT_NUMBER_OF_MONTHS = 5;
    public static final BigDecimal CORRECT_AMOUNT = new BigDecimal(1500);
    public static final LocalDate LOAN_CREATION_DATE_EXAMPLE = LocalDate.of(2020,02,02);
    public static final LocalDate LOAN_END_DATE_EXAMPLE = LocalDate.of(2020,10,02);


    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private DataValidationService dataValidationService;

    @Mock
    private RiskAnalysisService riskAnalysisService;

    @Mock
    private DateTimeProviderService dateTimeProviderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        riskAnalysisService = new RiskAnalysisServiceImpl(loanRepository, dateTimeProviderService);
        loanService = new LoanServiceImpl(loanRepository, dataValidationService, riskAnalysisService, dateTimeProviderService);
    }

    @Test
    public void successfullFindingAllLoans() {
        //given
        List<Loan> expectedLoans = LoanServiceTestData.getListOfThreeStoredLoans();
        when(loanRepository.findAll()).thenReturn(expectedLoans);

        //when
        List<Loan> actualLoans = loanService.findAllLoans();

        //then
        Assertions.assertEquals(expectedLoans.size(), actualLoans.size());
    }

    @Test
    public void successfullFindingLoanById() {
        //given
        Loan expectedLoan = LoanServiceTestData.getExampleLoanWithId(ID);
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(expectedLoan));

        //when
        Loan actualLoan = loanService.findById(ID);

        //then
        Assertions.assertEquals(ID, actualLoan.getId());
    }

    @Test
    public void usuccessfullLoanFindingById() {
        //given
        Loan expectedLoan = LoanServiceTestData.getExampleLoanWithId(ID);
        when(loanRepository.findById(anyLong())).thenThrow(new LoanDoesntExistException());

        //when
        //then
        Assertions.assertThrows(LoanDoesntExistException.class, () -> loanService.findById(NOT_EXISTING_ID));
    }

    @Test
    public void successfullCreationOfNewLoanWhenCorrectInputDataAndNoRisk() {
        //given
        Loan expectedLoan = LoanServiceTestData.getExampleLoanWith(ID, CORRECT_AMOUNT, CORRECT_NUMBER_OF_MONTHS, false);
        when(loanRepository.save(any(Loan.class))).thenReturn(expectedLoan);
        when(dateTimeProviderService.getCurrentDate()).thenReturn(LOAN_CREATION_DATE_EXAMPLE);
        doNothing().when(dataValidationService).validateInput(any(Loan.class));
        doNothing().when(riskAnalysisService).assesRisk(any(Loan.class));

        //when
        Loan actualLoan = loanService.createNewLoan(expectedLoan);

        //then
        Assertions.assertEquals(ID, actualLoan.getId());
        Assertions.assertEquals(LOAN_CREATION_DATE_EXAMPLE, actualLoan.getStartDate());
        Assertions.assertEquals(LOAN_CREATION_DATE_EXAMPLE.plusMonths(CORRECT_NUMBER_OF_MONTHS), actualLoan.getEndDate());
    }

    @Test
    public void unsuccessfullCreationOfNewLoanWhenIncorrectDataInput() {
        //given
        Loan expectedLoan = LoanServiceTestData.getExampleLoanWithId(ID);
        when(loanRepository.save(any(Loan.class))).thenReturn(expectedLoan);
        when(dateTimeProviderService.getCurrentDate()).thenReturn(LOAN_CREATION_DATE_EXAMPLE);
        doThrow(new DataInputException()).when(dataValidationService).validateInput(any(Loan.class));

        //when
        //then
        Assertions.assertThrows(DataInputException.class, () -> loanService.createNewLoan(expectedLoan));
    }

    @Test
    public void unsuccessfullCreationOfNewLoanWhenOperationIsConsideredRisky() {
        //given
        Loan expectedLoan = LoanServiceTestData.getExampleLoanWithId(ID);
        when(loanRepository.save(any(Loan.class))).thenReturn(expectedLoan);
        when(dateTimeProviderService.getCurrentDate()).thenReturn(LOAN_CREATION_DATE_EXAMPLE);
        doThrow(new RiskyOperationException()).when(riskAnalysisService).assesRisk(any(Loan.class));

        //when
        //then
        Assertions.assertThrows(RiskyOperationException.class, () -> loanService.createNewLoan(expectedLoan));
    }

    @Test
    public void successfullPaymentPeriodExtension() {
        //given
        Loan storedLoan = LoanServiceTestData.getExampleLoanWithPeriodPaymentNotYetExtendedWithIdAndEndDate(ID, LOAN_END_DATE_EXAMPLE);;
        Loan expectedLoan = LoanServiceTestData.getExampleLoanWithPeriodPaymentAlreadyExtendedWithIdAndEndDate(ID, LOAN_END_DATE_EXAMPLE.plusDays(14));
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(storedLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(expectedLoan);
        when(dateTimeProviderService.getCurrentDate()).thenReturn(LOAN_CREATION_DATE_EXAMPLE);

        //when
        Loan actualLoan = loanService.extendPaymentPeriod(ID);

        //then
        Assertions.assertEquals(true, actualLoan.isPaymentPeriodExtended());
        Assertions.assertEquals(LOAN_END_DATE_EXAMPLE.plusDays(14), actualLoan.getEndDate());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void unsuccessfullPaymentPeriodExtensionBecauseOfAlreadyPerformedInThePast() {
        //given
        Loan storedLoan = LoanServiceTestData.getExampleLoanWithPeriodPaymentAlreadyExtendedWithIdAndEndDate(ID, LOAN_END_DATE_EXAMPLE);;
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(storedLoan));
        when(dateTimeProviderService.getCurrentDate()).thenReturn(LOAN_CREATION_DATE_EXAMPLE);

        //when
        Loan actualLoan = loanService.extendPaymentPeriod(ID);

        //then
        verify(loanRepository, times(0)).save(any(Loan.class));
    }
}
