package com.example.microloanapp.constants;

import java.math.BigDecimal;

public class LoanApplicationConstraints {

    public static final int MIN_NUMBER_OF_MONTHS = 1;
    public static final int MAX_NUMBER_OF_MONTHS = 6;
    public static final BigDecimal MIN_ALLOWED_AMOUNT = new BigDecimal(500);
    public static final BigDecimal MAX_ALLOWED_AMOUNT = new BigDecimal(5000);
}
