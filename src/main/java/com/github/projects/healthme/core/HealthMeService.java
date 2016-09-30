package com.github.projects.healthme.core;

/**
 * @author Mariia Lapovska
 */
public class HealthMeService {
    private int caloriesDayNorm;
    private int waterDayNorm;
    private int stepsDayNorm;

    private int consumedCalories;
    private int consumedWater;
    private int walkedSteps;

    public HealthMeService() {}

    public HealthMeService(int caloriesDayNorm, int waterDayNorm, int stepsDayNorm) {
        this.caloriesDayNorm = caloriesDayNorm;
        this.waterDayNorm = waterDayNorm;
        this.stepsDayNorm = stepsDayNorm;
    }

    public int countCaloriesNorm(int weight, int height, int age, Gender gender) {
        int index = (gender == Gender.MALE) ? 5 : -161;

        return (int) (10 * weight + 6.25 * height - 5 * age + index);
    }

    public int countWaterNorm(int weight, Gender gender) {
        int index = (gender == Gender.MALE) ? 35 : 31;

        return weight * index;
    }

    public int countStepsNorm(int age, Gender gender) {
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

    public void eat(int calories) {
        consumedCalories += calories;
    }

    public void drink(int milliliters) {
        consumedWater += milliliters;
    }

    public void walk(int steps) {
        walkedSteps += steps;
    }

    public int countResiduaryCalories() {
        return caloriesDayNorm - consumedCalories;
    }

    public int countResiduaryWater() {
        return waterDayNorm - consumedWater;
    }

    public int countResiduarySteps() {
        return stepsDayNorm - walkedSteps;
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