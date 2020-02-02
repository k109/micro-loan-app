package com.example.microloanapp.v1.controllers;

import com.example.microloanapp.model.Loan;
import com.example.microloanapp.model.LoanData;
import com.example.microloanapp.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;

@RestController
@RequestMapping(LoanController.BASE_URL)
@RequiredArgsConstructor
public class LoanController {

    public static final String BASE_URL = "/api/v1/loans";
    private final LoanService loanService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Loan> getAllLoans() {
        return loanService.findAllLoans();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan createNewLoan(@Context HttpServletRequest request,
                              @RequestBody LoanData loanData) {

        String ip = request.getRemoteAddr();
        Loan loan = new Loan();
        loan.setIp(ip);
        loan.setRequestedAmount(loanData.getRequestedAmount());
        loan.setNumberOfMonths(loanData.getNumberOfMonths());

        return loanService.createNewLoan(loan);
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Loan extendPaymentPeriod(@PathVariable Long id) {
        return loanService.extendPaymentPeriod(id);
    }
}

