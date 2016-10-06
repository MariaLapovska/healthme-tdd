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
    private HealthMeService healthMeService;
    private DateTimeFormatter formatter;
    private LocalDate date;

    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        date = LocalDate.parse("05.10.2016", formatter);
        healthMeService = new HealthMeService();
    }

    @Test
    public void canCountCaloriesNorm() {
        assertEquals(1370, healthMeService.calculateCaloriesNorm(60, 165, 20,
                HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canCountWaterNorm() {
        assertEquals(1860, healthMeService.calculateWaterNorm(60,
                HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canCountStepsNorm() {
        assertEquals(11000, healthMeService.calculateStepsNorm(20,
                HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canEat() {
        healthMeService.eat(date, 200);
        assertEquals(200, healthMeService.getDayRecord(date)
                .getConsumedCalories());
    }

    @Test
    public void canDrink() {
        healthMeService.drink(date, 300);
        assertEquals(300, healthMeService.getDayRecord(date)
                .getConsumedWater());
    }

    @Test
    public void canWalk() {
        healthMeService.walk(date, 800);
        assertEquals(800, healthMeService.getDayRecord(date)
                .getWalkedSteps());
    }
}
