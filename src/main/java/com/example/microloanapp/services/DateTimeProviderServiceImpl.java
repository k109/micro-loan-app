package com.example.microloanapp.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DateTimeProviderServiceImpl implements DateTimeProviderService {

    private final Clock clock;

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now(clock);
    }

    @Override
    public LocalTime getCurrentTime() {
        return LocalTime.now(clock);
    }
}
