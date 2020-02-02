package com.example.microloanapp.repositories;

import com.example.microloanapp.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countAllByIp(String ip);
}
