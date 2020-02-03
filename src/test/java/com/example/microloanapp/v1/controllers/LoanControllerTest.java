package com.example.microloanapp.v1.controllers;

import com.example.microloanapp.exceptions.DataInputException;
import com.example.microloanapp.exceptions.LoanDoesntExistException;
import com.example.microloanapp.exceptions.RiskyOperationException;
import com.example.microloanapp.model.Loan;
import com.example.microloanapp.services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.example.microloanapp.utils.JsonConverter.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LoanControllerTest {

    private static final long ID = 1L;
    private static final long ID_SECOND = 2L;
    private static final BigDecimal AMOUNT = new BigDecimal(3000);
    private static final int NUMBER_OF_MONTHS = 2;


    @Mock
    LoanService loanService;

    @InjectMocks
    LoanController loanController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(loanController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListLoans() throws Exception {
        //given
        Loan loanFirst = new Loan();
        loanFirst.setId(ID);
        loanFirst.setNumberOfMonths(NUMBER_OF_MONTHS);
        loanFirst.setRequestedAmount(AMOUNT);

        Loan loanSecond = new Loan();
        loanSecond.setId(ID_SECOND);
        loanSecond.setNumberOfMonths(NUMBER_OF_MONTHS);
        loanSecond.setRequestedAmount(AMOUNT);

        List<Loan> loans = Arrays.asList(loanFirst, loanSecond);
        when(loanService.findAllLoans()).thenReturn(loans);

        //when
        //then
        mockMvc.perform(get(LoanController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[1].id").value(ID_SECOND));
    }

    @Test
    public void testGetLoanById() throws Exception {
        //given
        Loan expectedLoan = new Loan();
        expectedLoan.setId(ID);
        when(loanService.findById(ID)).thenReturn(expectedLoan);

        //when
        //then
        mockMvc.perform(get(LoanController.BASE_URL+"/"+ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testCreateNewLoan() throws Exception {
        //given
        Loan expectedLoan = new Loan();
        expectedLoan.setId(ID);
        expectedLoan.setNumberOfMonths(NUMBER_OF_MONTHS);
        expectedLoan.setRequestedAmount(AMOUNT);
        when(loanService.createNewLoan(any(Loan.class))).thenReturn(expectedLoan);

        //when
        //then
        mockMvc.perform(post(LoanController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedLoan)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    public void testExtendPaymentPeriod() throws Exception {
        //given
        Loan expectedLoan = new Loan();
        expectedLoan.setId(ID);
        expectedLoan.setPaymentPeriodExtended(true);
        when(loanService.extendPaymentPeriod(anyLong())).thenReturn(expectedLoan);

        //when
        //then
        mockMvc.perform(patch(LoanController.BASE_URL+"/"+ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.paymentPeriodExtended").value(true));
    }

    @Test
    public void testRiskyOperationException() throws Exception {
        //given
        Loan expectedLoan = new Loan();
        expectedLoan.setId(ID);
        expectedLoan.setNumberOfMonths(2);
        expectedLoan.setRequestedAmount(new BigDecimal(1000));
        when(loanService.createNewLoan(any(Loan.class))).thenThrow(RiskyOperationException.class);

        //when
        //then
        mockMvc.perform(post(LoanController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedLoan)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testDataInputException() throws Exception {
        //given
        Loan expectedLoan = new Loan();
        expectedLoan.setId(ID);
        expectedLoan.setNumberOfMonths(2);
        expectedLoan.setRequestedAmount(new BigDecimal(1000));
        when(loanService.createNewLoan(any(Loan.class))).thenThrow(DataInputException.class);

        //when
        //then
        mockMvc.perform(post(LoanController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedLoan)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoanDoesntExistException() throws Exception {
        //given
        when(loanService.findById(anyLong())).thenThrow(LoanDoesntExistException.class);

        //when
        //then
        mockMvc.perform(get(LoanController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
