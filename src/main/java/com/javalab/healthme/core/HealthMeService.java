package com.javalab.healthme.core;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mariia Lapovska
 */
public class HealthMeService {
    private int caloriesDayNorm;
    private int waterDayNorm;
    private int stepsDayNorm;

    private SortedSet<Day> repository = new TreeSet<>((day1, day2) -> day1.getDate().compareTo(day2.getDate()));

    public HealthMeService(int caloriesDayNorm, int waterDayNorm, int stepsDayNorm) {
        this.caloriesDayNorm = caloriesDayNorm;
        this.waterDayNorm = waterDayNorm;
        this.stepsDayNorm = stepsDayNorm;
    }

    public int calculateCaloriesNorm(int weight, int height, int age, Gender gender) {
        int index = (gender == Gender.MALE) ? 5 : -161;

        return (int) (10 * weight + 6.25 * height - 5 * age + index);
    }

    public int calculateWaterNorm(int weight, Gender gender) {
        int index = (gender == Gender.MALE) ? 35 : 31;

        return weight * index;
    }

    public int calculateStepsNorm(int age, Gender gender) {
        double index = (gender == Gender.MALE) ? 1.3 : 1.1;
        int steps;

        if (age < 20) {
            steps = 15000;
        } else if (age >= 20 && age <= 50) {
            steps = 10000;
        } else {
            steps = 7000;
        }

        return (int) (steps * index);
    }

    public int countResiduaryCalories(Day... days) {
        int residuaryCalories = 0;

        for (Day day : days) {
            residuaryCalories += caloriesDayNorm - day.getConsumedCalories();
        }

        return residuaryCalories;
    }

    public int countResiduaryWater(Day... days) {
        int residuaryWater = 0;

        for (Day day : days) {
            residuaryWater += waterDayNorm - day.getConsumedWater();
        }

        return residuaryWater;
    }

    public int countResiduarySteps(Day... days) {
        int residuarySteps = 0;

        for (Day day : days) {
            residuarySteps += stepsDayNorm - day.getWalkedSteps();
        }

        return residuarySteps;
    }

    public boolean addNewDay() {
        return repository.add(new Day());
    }

    public Day getDayByDate(LocalDate date) {
        return repository.stream().filter(day -> day.getDate().equals(date)).findAny().orElse(null);
    }

    public int getCaloriesDayNorm() {
        return caloriesDayNorm;
    }

    public void setCaloriesDayNorm(int caloriesDayNorm) {
        this.caloriesDayNorm = caloriesDayNorm;
    }

    public int getWaterDayNorm() {
        return waterDayNorm;
    }

    public void setWaterDayNorm(int waterDayNorm) {
        this.waterDayNorm = waterDayNorm;
    }

    public int getStepsDayNorm() {
        return stepsDayNorm;
    }

    public void setStepsDayNorm(int stepsDayNorm) {
        this.stepsDayNorm = stepsDayNorm;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
