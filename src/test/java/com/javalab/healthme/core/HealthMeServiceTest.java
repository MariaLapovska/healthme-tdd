package com.javalab.healthme.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mariia Lapovska
 */
public class HealthMeServiceTest {
    private HealthMeService healthMeService;

    @Before
    public void setUp() {
        healthMeService = new HealthMeService(2000, 2000, 5000);
    }

    @Test
    public void canCountCaloriesNorm() {
        assertEquals(1370, healthMeService.calculateCaloriesNorm(60, 165, 20, HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canCountWaterNorm() {
        assertEquals(1860, healthMeService.calculateWaterNorm(60, HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canCountStepsNorm() {
        assertEquals(11000, healthMeService.calculateStepsNorm(20, HealthMeService.Gender.FEMALE));
    }

    @Test
    public void canCountResiduaryCalories() {
        assertEquals(2000, healthMeService.countResiduaryCalories());
    }

    @Test
    public void canCountResiduaryWater() {
        assertEquals(2000, healthMeService.countResiduaryWater());
    }

    @Test
    public void canCountResiduarySteps() {
        assertEquals(5000, healthMeService.countResiduarySteps());
    }
}
