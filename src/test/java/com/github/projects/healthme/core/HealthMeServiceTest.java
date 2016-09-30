package com.github.projects.healthme.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.github.projects.healthme.core.HealthMeService.Gender;

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
        assertEquals(1370, healthMeService.countCaloriesNorm(60, 165, 20, Gender.FEMALE));
    }

    @Test
    public void canCountWaterNorm() {
        assertEquals(1860, healthMeService.countWaterNorm(60, Gender.FEMALE));
    }

    @Test
    public void canCountStepsNorm() {
        assertEquals(11000, healthMeService.countStepsNorm(20, Gender.FEMALE));
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

    @Test
    public void canCountCaloriesPercentage
}