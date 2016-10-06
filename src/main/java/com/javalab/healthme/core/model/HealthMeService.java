package com.javalab.healthme.core.model;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

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

    private List<DayRecord> dayRecords = new ArrayList<>();

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

    public int countResiduaryCalories(LocalDate from, LocalDate to) throws Exception {
        return countResiduary(from, to, stepsDayNorm, getMethodByName(DayRecord.class, "getConsumedCalories"));
    }

    public int countResiduaryWater(LocalDate from, LocalDate to) throws Exception  {
        return countResiduary(from, to, stepsDayNorm, getMethodByName(DayRecord.class, "getConsumedWater"));
    }

    public int countResiduarySteps(LocalDate from, LocalDate to) throws Exception  {
        return countResiduary(from, to, stepsDayNorm, getMethodByName(DayRecord.class, "getWalkedSteps"));
    }

    public Double countResiduaryCaloriesPercentage(LocalDate from, LocalDate to) throws Exception {
        return (double) countResiduaryCalories(from, to) / caloriesDayNorm;
    }

    public Double countResiduaryWaterPercentage(LocalDate from, LocalDate to) throws Exception {
        return (double) countResiduaryWater(from, to) / waterDayNorm;
    }

    public Double countResiduaryStepsPercentage(LocalDate from, LocalDate to) throws Exception {
        return (double) countResiduarySteps(from, to) / stepsDayNorm;
    }

    private double periodMedian(LocalDate from, LocalDate to) {
        //final int middleValue = (recordsInRange.size() % 2) == 0 ? 1 : ;


        return 0;
    }

    public double periodEatingMedian(LocalDate from, LocalDate to) {
        /*List<Double> weekPercentage = new ArrayList<>();
        while (dateWeekAgo.isBefore(date)) {
            weekPercentage.add(hundredPercents - leftForToday.apply(date));
            date = date.minusDays(1);
        }
        weekPercentage.sort(Comparator.naturalOrder());
        return weekPercentage.get(middleOfWeek);*/
        return 0;
    }

    public double periodDrinkingMedian(LocalDate from, LocalDate to) {
        return 0;
    }

    public double periodWalkingMedian(LocalDate from, LocalDate to) {
        return 0;
    }

    public void eat(LocalDate date, int calories) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.eat(calories);
        //dayRecords.put(date, dayRecord);
    }

    public void drink(LocalDate date, int milliliters) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.drink(milliliters);
        //dayRecords.put(date, dayRecord);
    }

    public void walk(LocalDate date, int steps) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.walk(steps);
        //dayRecords.put(date, dayRecord);
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
        DayRecord dayRecord = null;//= dayRecords.get(date);

        if (dayRecord == null) {
            return new DayRecord();
        }
        return dayRecord;
    }

    private int countResiduary(LocalDate from, LocalDate to, int norm, Method method) throws Exception {
        int residuary = 0;
        /*NavigableMap<LocalDate, DayRecord> recordsInRange = dayRecords.subMap(from, true, to, true);

        for (Map.Entry<LocalDate, DayRecord> recordEntry : recordsInRange.entrySet()) {
            residuary += norm - (int) method.invoke(recordEntry.getValue());
        }*/

        return residuary > 0 ? residuary : 0;
    }

    private Method getMethodByName(Object obj, String methodName) throws Exception {
        return obj.getClass().getMethod(methodName);
    }

    public enum Gender {
        MALE, FEMALE
    }
}
