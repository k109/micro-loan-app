package com.example.microloanapp.services;

import com.example.microloanapp.model.Loan;

public interface RiskAnalysisService {

    void assesRisk(Loan loan);
}
