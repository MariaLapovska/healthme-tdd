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
}
