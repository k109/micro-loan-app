package com.example.microloanapp.services;

import com.example.microloanapp.exceptions.DataInputException;
import com.example.microloanapp.model.Loan;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DataValidationServiceTest {

    private static final String EXCEPTION_MESSAGE_INCORRECT_NUMBER_OF_MONTHS = "Incorrect loan period";
    private static final String EXCEPTION_MESSAGE_INCORRECT_AMOUNT = "Incorrect amount requested";
    private DataValidationService dataValidationService;

    @BeforeEach
    public void setUp() {
        dataValidationService = new DataValidationServiceImpl();
    }

    @Test
    public void correctDataInput() {
        //given
        Loan loan = DataValidationServiceTestData.getCorrectLoan();

        //when
        dataValidationService.validateInput(loan);

        //then
        Assertions.assertDoesNotThrow(() -> new DataInputException());
    }

    @Test
    public void incorrectNumberOfMonthsInput() {
        //given
        Loan loan = DataValidationServiceTestData.getLoanWithIncorrectNumberOfMonths();

        //when
        //then
        Exception exception = Assertions.assertThrows(DataInputException.class,
                () -> dataValidationService.validateInput(loan));
        Assertions.assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE_INCORRECT_NUMBER_OF_MONTHS));
    }

    @Test
    public void incorrectAmountInput() {
        //given
        Loan loan = DataValidationServiceTestData.getLoanWithIncorrectAmount();

        //when
        //then
        Exception exception = Assertions.assertThrows(DataInputException.class,
                () -> dataValidationService.validateInput(loan));
        Assertions.assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE_INCORRECT_AMOUNT));
    }
}
