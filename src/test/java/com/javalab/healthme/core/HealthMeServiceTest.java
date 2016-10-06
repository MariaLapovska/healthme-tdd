package com.javalab.healthme.core;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 * @author Mariia Lapovska
 */
public class HealthMeServiceTest {
    private final double precision = 0.0001;

    private HealthMeService healthMeService;
    private DateTimeFormatter formatter;
    private LocalDate date;

    private void initHealthService() {
        healthMeService.eat(LocalDate.parse("05.10.2016", formatter), 100);
        healthMeService.drink(LocalDate.parse("05.10.2016", formatter), 400);
        healthMeService.walk(LocalDate.parse("05.10.2016", formatter), 3000);

        healthMeService.eat(LocalDate.parse("05.10.2016", formatter), 100);
        healthMeService.drink(LocalDate.parse("05.10.2016", formatter), 400);
        healthMeService.walk(LocalDate.parse("05.10.2016", formatter), 3000);

        healthMeService.eat(LocalDate.parse("06.10.2016", formatter), 500);
        healthMeService.drink(LocalDate.parse("06.10.2016", formatter), 500);
        healthMeService.walk(LocalDate.parse("06.10.2016", formatter), 2000);
    }

    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        date = LocalDate.parse("05.10.2016", formatter);
        healthMeService = new HealthMeService(2000, 2000, 5000);
        initHealthService();
    }

    @Test
    public void canCountCaloriesNorm() {
        assertEquals(1370, HealthMeService.calculateCaloriesNorm(60, 165, 20,
                HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canCountWaterNorm() {
        assertEquals(1860, HealthMeService.calculateWaterNorm(60,
                HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canCountStepsNorm() {
        assertEquals(11000, HealthMeService.calculateStepsNorm(20,
                HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canEat() {
        assertEquals(200, healthMeService.getDayRecord(date)
                .getConsumedCalories());
    }

    @Test
    public void canDrink() {
        assertEquals(800, healthMeService.getDayRecord(date)
                .getConsumedWater());
    }

    @Test
    public void canWalk() {
        assertEquals(6000, healthMeService.getDayRecord(date)
                .getWalkedSteps());
    }

    @Test
    public void canCountResiduaryCaloriesForPeriod() throws Exception {
        assertEquals(3300, healthMeService.countResiduaryCalories(date, date
                .plusDays(2)));
    }

    @Test
    public void canCountResiduaryWaterForPeriod() throws Exception {
        assertEquals(2700, healthMeService.countResiduaryWater(date, date
                .plusDays(2)));
    }

    @Test
    public void canCountResiduaryStepsForPeriod() throws Exception {
        assertEquals(2000, healthMeService.countResiduarySteps(date, date
                .plusDays(2)));
    }

    @Test
    public void canCountResiduaryCaloriesForDay() throws Exception {
        assertEquals(1500, healthMeService.countResiduaryCalories(date
                .plusDays(1)));
    }

    @Test
    public void canCountResiduaryWaterForDay() throws Exception {
        assertEquals(1500, healthMeService.countResiduaryWater(date
                .plusDays(1)));
    }

    @Test
    public void canCountResiduaryStepsForDay() throws Exception {
        assertEquals(3000, healthMeService.countResiduarySteps(date
                .plusDays(1)));
    }

    @Test
    public void canCountResiduaryCaloriesPercentageForDay() throws Exception {
        assertEquals(0.9, healthMeService.countResiduaryCaloriesPercentage
                (date), precision);
    }

    @Test
    public void canCountResiduaryWaterPercentageForDay() throws Exception {
        assertEquals(0.6, healthMeService.countResiduaryWaterPercentage
                (date), precision);
    }

    @Test
    public void canCountResiduaryStepsPercentageForDay() throws Exception {
        assertEquals(0.0, healthMeService.countResiduaryStepsPercentage
                (date), precision);
    }

}