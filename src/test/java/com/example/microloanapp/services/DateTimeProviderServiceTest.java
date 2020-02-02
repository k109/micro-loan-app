package com.example.microloanapp.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

public class DateTimeProviderServiceTest {

    private Clock clock;
    private static final String TEST_DATE_TIME = "2020-02-02T10:15:00Z";
    private static final LocalTime CORRECT_TIME = LocalTime.of(10,15,0,0);
    private static final LocalTime INCORRECT_TIME = LocalTime.of(15,15,0,0);
    private static final LocalDate CORRECT_DATE = LocalDate.of(2020,02,02);
    private static final LocalDate INCORRECT_DATE = LocalDate.of(2021,05,02);

    private DateTimeProviderService dateTimeProviderService;

    @BeforeEach
    public void setUp() {
        clock = Clock.fixed(Instant.parse(TEST_DATE_TIME), ZoneOffset.UTC);
        dateTimeProviderService = new DateTimeProviderServiceImpl(clock);
    }

    @Test
    public void shouldReturnCorrectTime() {
        //given
        LocalTime expectedTime = CORRECT_TIME;

        //when
        LocalTime actualTime = dateTimeProviderService.getCurrentTime();

        //then
        Assertions.assertEquals(expectedTime, actualTime);
    }

    @Test
    public void shouldReturnIncorrectTime() {
        //given
        LocalTime expectedTime = INCORRECT_TIME;

        //when
        LocalTime actualTime = dateTimeProviderService.getCurrentTime();

        //then
        Assertions.assertNotEquals(expectedTime, actualTime);
    }

    @Test
    public void shouldReturnCorrectDate() {
        //given
        LocalDate expectedDate = CORRECT_DATE;

        //when
        LocalDate actualDate = dateTimeProviderService.getCurrentDate();

        //then
        Assertions.assertEquals(expectedDate, actualDate);
    }

    @Test
    public void shouldReturnIncorrectDate() {
        //given
        LocalDate expectedDate = INCORRECT_DATE;

        //when
        LocalDate actualDate = dateTimeProviderService.getCurrentDate();

        //then
        Assertions.assertNotEquals(expectedDate, actualDate);
    }
}
