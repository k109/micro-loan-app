package com.example.microloanapp.constants;

import java.math.BigDecimal;
import java.time.LocalTime;

public class LoanApplicationConstraints {

    public static final int MIN_NUMBER_OF_MONTHS = 1;
    public static final int MAX_NUMBER_OF_MONTHS = 6;
    public static final BigDecimal MIN_ALLOWED_AMOUNT = new BigDecimal(500);
    public static final BigDecimal MAX_ALLOWED_AMOUNT = new BigDecimal(5000);
    public static final LocalTime BEGIN_OF_HAZARDOUS_TIMEFRAME = LocalTime.of(0,0,0,0);
    public static final LocalTime END_OF_HAZARDOUS_TIMEFRAME = LocalTime.of(6,0,0,0);
    public static final long MAX_NUMBER_OF_LOANS_PER_IP = 2;
    public static final int NUMBER_OF_DAYS_WHEN_POSTPONING_PAYMENT = 14;
}
