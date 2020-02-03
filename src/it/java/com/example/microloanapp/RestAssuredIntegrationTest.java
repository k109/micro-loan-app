package com.example.microloanapp;

import com.example.microloanapp.v1.controllers.LoanController;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredIntegrationTest {

    private static final String POST_URL = LoanController.BASE_URL;

    private static Map<String, String> payload = new HashMap<>();
    private static final String ID = "1";
    private static final String AMOUNT = "4000";
    private static final String NUMBER_OF_MOUNTHS = "3";

    @Test
    public void whenLoanAmountAndPeriodprovidedThenLoanCreated() {

        payload.put("numberOfMonths", NUMBER_OF_MOUNTHS);
        payload.put("requestedAmount", AMOUNT);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(POST_URL)
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("numberOfMonths", equalTo(3))
                .body("requestedAmount", equalTo(4000));
    }
}