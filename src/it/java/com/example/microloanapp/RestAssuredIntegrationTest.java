package com.example.microloanapp;

import com.example.microloanapp.v1.controllers.LoanController;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

public class RestAssuredIntegrationTest {

    private static final String POST_URL = LoanController.BASE_URL;

    private static String payload = "{\n" +
            "  \"numberOfMonths\": 1,\n" +
            "  \"requestedAmount\": 4000\n" +
            "}";

    @Test
    public void  whenLoanAmountAndPeriodprovidedThenLoanCreated() {

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(POST_URL)
                .then()
                .statusCode(201);
    }
}