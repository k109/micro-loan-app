package com.example.microloanapp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class LoanData {

    private BigDecimal requestedAmount;
    private int numberOfMonths;
}
