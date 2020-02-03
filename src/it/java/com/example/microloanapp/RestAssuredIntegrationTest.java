package com.example.microloanapp;

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
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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

    private static final String POST_URL = LoanController.BASE_URL;

    private static Map<String, String> payload = new HashMap<>();
    private static final String AMOUNT = "4000";
    private static final String NUMBER_OF_MOUNTHS = "3";

    @Test
    public void whenLoanAmountAndPeriodprovidedThenLoanCreated() {

        payload.put("numberOfMonths", NUMBER_OF_MOUNTHS);
        payload.put("requestedAmount", AMOUNT);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(uri + POST_URL)
                .then()
                .statusCode(201).log().all();
    }
}