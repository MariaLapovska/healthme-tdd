package com.javalab.healthme.core.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * @author Mariia Lapovska
 */
public class HealthMeService {
    private static final int DEFAULT_CALORIES = 2000;
    private static final int DEFAULT_WATER = 2000;
    private static final int DEFAULT_STEPS = 2000;

    private int caloriesDayNorm;
    private int waterDayNorm;
    private int stepsDayNorm;

    private NavigableMap<LocalDate, DayRecord> dayRecords = new TreeMap<>();

    public HealthMeService() {
        caloriesDayNorm = DEFAULT_CALORIES;
        waterDayNorm = DEFAULT_WATER;
        stepsDayNorm = DEFAULT_STEPS;
    }

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

    public int countResiduaryCalories(LocalDate from, LocalDate to) {
        int residuaryCalories = 0;
        NavigableMap<LocalDate, DayRecord> recordsInRange = dayRecords.subMap(from, true, to, true);

        for (Map.Entry<LocalDate, DayRecord> recordEntry : recordsInRange.entrySet()) {
            residuaryCalories += caloriesDayNorm - recordEntry.getValue().getConsumedCalories();
        }

        return residuaryCalories > 0 ? residuaryCalories : 0;
    }

    public int countResiduaryWater(LocalDate from, LocalDate to) {
        int residuaryWater = 0;
        NavigableMap<LocalDate, DayRecord> recordsInRange = dayRecords.subMap(from, true, to, true);

        for (Map.Entry<LocalDate, DayRecord> recordEntry : recordsInRange.entrySet()) {
            residuaryWater += waterDayNorm - recordEntry.getValue().getConsumedWater();
        }

        return residuaryWater > 0 ? residuaryWater : 0;
    }

    public int countResiduarySteps(LocalDate from, LocalDate to) {
        int residuarySteps = 0;
        NavigableMap<LocalDate, DayRecord> recordsInRange = dayRecords.subMap(from, true, to, true);

        for (Map.Entry<LocalDate, DayRecord> recordEntry : recordsInRange.entrySet()) {
            residuarySteps += stepsDayNorm - recordEntry.getValue().getWalkedSteps();
        }

        return residuarySteps > 0 ? residuarySteps : 0;
    }

    public Double countResiduaryCaloriesPercentage(LocalDate from, LocalDate to) {
        return (double) countResiduaryCalories(from, to) / caloriesDayNorm;
    }

    public Double countResiduaryWaterPercentage(LocalDate from, LocalDate to) {
        return (double) countResiduaryWater(from, to) / waterDayNorm;
    }

    public Double countResiduaryStepsPercentage(LocalDate from, LocalDate to) {
        return (double) countResiduarySteps(from, to) / stepsDayNorm;
    }

    public void eat(LocalDate date, int calories) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.eat(calories);
        dayRecords.put(date, dayRecord);
    }

    public void drink(LocalDate date, int milliliters) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.drink(milliliters);
        dayRecords.put(date, dayRecord);
    }

    public void walk(LocalDate date, int steps) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.walk(steps);
        dayRecords.put(date, dayRecord);
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

    private DayRecord getDayRecord(LocalDate date) {
        DayRecord dayRecord = dayRecords.get(date);

        if (dayRecord == null) {
            return new DayRecord();
        }
        return dayRecord;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
