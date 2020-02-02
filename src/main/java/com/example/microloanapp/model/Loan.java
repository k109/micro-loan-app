package com.example.microloanapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal requestedAmount;
    private int numberOfMonths;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean paymentPeriodExtended = false;
    private String ip;
}
