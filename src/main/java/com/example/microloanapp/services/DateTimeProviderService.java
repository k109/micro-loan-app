package com.example.microloanapp.services;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DateTimeProviderService {

    LocalDate getCurrentDate();

    LocalTime getCurrentTime();
}
