package com.example.microloanapp;

import com.example.microloanapp.exceptions.LoanDoesntExistException;
import com.example.microloanapp.exceptions.RiskyOperationException;
import com.example.microloanapp.model.Loan;
import com.example.microloanapp.services.LoanService;
import com.example.microloanapp.v1.controllers.LoanController;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAssuredIntegrationTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    LoanService loanService;

    private static final String BASE_URL = LoanController.BASE_URL;

    private static final String AMOUNT = "4000";
    private static final String NUMBER_OF_MOUNTHS = "3";

    @Test
    public void whenLoanAmountAndPeriodprovidedThenLoanCreatedAndStatus201() {

        Map<String, String> payload = new HashMap<>();
        payload.put("numberOfMonths", NUMBER_OF_MOUNTHS);
        payload.put("requestedAmount", AMOUNT);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(uri + BASE_URL)
                .then()
                .statusCode(201).log().all();
    }

    @Test
    public void whenAskingForAllLoansThenReturnsAllLoansAndStatus200() {
        List<Loan> loans = new ArrayList<>();

        Loan loanOne = new Loan();
        loanOne.setId(10L);
        Loan loanTwo = new Loan();
        loanTwo.setId(11L);

        loans.add(loanOne);
        loans.add(loanTwo);

        when(loanService.findAllLoans()).thenReturn(loans);

        given()
                .contentType(ContentType.JSON)
                .get(uri + BASE_URL)
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void whenAskingForLoanWithSpecificIdThenReturnsLoanAndStatus200() {

        Loan loan = new Loan();
        loan.setId(20L);

        when(loanService.findById(anyLong())).thenReturn(loan);

        given()
                .contentType(ContentType.JSON)
                .get(uri + BASE_URL + "/20")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void whenAskingForLoanWhichDoesntExistThenReturnStatus404() {

        when(loanService.findById(anyLong())).thenThrow(new LoanDoesntExistException());

        given()
                .contentType(ContentType.JSON)
                .get(uri + BASE_URL + "/30")
                .then()
                .statusCode(404).log().all();
    }

    @Test
    public void whenCreatingNewLoanWhichIsConsideredRiskyThenCreationAbortedThenReturnStatus406() {
        Map<String, String> payload = new HashMap<>();
        payload.put("numberOfMonths", NUMBER_OF_MOUNTHS);
        payload.put("requestedAmount", AMOUNT);

        when(loanService.createNewLoan(any(Loan.class))).thenThrow(new RiskyOperationException());

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(uri + BASE_URL)
                .then()
                .statusCode(406).log().all();
    }
}