package com.javalab.healthme.core;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mariia Lapovska
 */
public class HealthMeService {

    private int caloriesDayNorm;
    private int waterDayNorm;
    private int stepsDayNorm;

    private SortedSet<DayRecord> dayRecords;

    public HealthMeService(int caloriesDayNorm, int waterDayNorm, int
            stepsDayNorm) {
        this.caloriesDayNorm = caloriesDayNorm;
        this.waterDayNorm = waterDayNorm;
        this.stepsDayNorm = stepsDayNorm;
        dayRecords = new TreeSet<>(new DateComparator());
    }

    public static int calculateCaloriesNorm(int weight, int height, int age, Gender
            gender) {
        int index = (gender == Gender.MALE) ? 5 : -161;

        return (int) (10 * weight + 6.25 * height - 5 * age + index);
    }

    public static int calculateWaterNorm(int weight, Gender gender) {
        int index = (gender == Gender.MALE) ? 35 : 31;

        return weight * index;
    }

    public static int calculateStepsNorm(int age, Gender gender) {
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

    public void eat(LocalDate date, int calories) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.eat(calories);
    }

    public void drink(LocalDate date, int milliliters) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.drink(milliliters);
    }

    public void walk(LocalDate date, int steps) {
        DayRecord dayRecord = getDayRecord(date);

        dayRecord.walk(steps);
    }

    public int countResiduaryCalories(LocalDate from, LocalDate to) throws
            Exception {
        return countResiduary(from, to, caloriesDayNorm, getMethodByName
                (DayRecord.class, "getConsumedCalories"));
    }

    public int countResiduaryWater(LocalDate from, LocalDate to) throws
            Exception {
        return countResiduary(from, to, waterDayNorm, getMethodByName
                (DayRecord.class, "getConsumedWater"));
    }

    public int countResiduarySteps(LocalDate from, LocalDate to) throws
            Exception {
        return countResiduary(from, to, stepsDayNorm, getMethodByName
                (DayRecord.class, "getWalkedSteps"));
    }

    public int countResiduaryCalories(LocalDate date) throws Exception {
        return countResiduary(date, date.plusDays(1), caloriesDayNorm,
                getMethodByName(DayRecord.class, "getConsumedCalories"));
    }

    public int countResiduaryWater(LocalDate date) throws Exception {
        return countResiduary(date, date.plusDays(1), waterDayNorm,
                getMethodByName(DayRecord.class, "getConsumedWater"));
    }

    public int countResiduarySteps(LocalDate date) throws Exception {
        return countResiduary(date, date.plusDays(1), stepsDayNorm,
                getMethodByName(DayRecord.class, "getWalkedSteps"));
    }

    public Double countResiduaryCaloriesPercentage(LocalDate date) throws
            Exception {
        return (double) countResiduaryCalories(date, date.plusDays(1)) /
                caloriesDayNorm;
    }

    public Double countResiduaryWaterPercentage(LocalDate date) throws
            Exception {
        return (double) countResiduaryWater(date, date.plusDays(1)) /
                waterDayNorm;
    }

    public Double countResiduaryStepsPercentage(LocalDate date) throws
            Exception {
        return (double) countResiduarySteps(date, date.plusDays(1)) /
                stepsDayNorm;
    }

    public DayRecord getDayRecord(LocalDate date) {
        for (DayRecord dayRecord : dayRecords) {
            if (dayRecord.getDate().equals(date)) {
                return dayRecord;
            }
        }

        DayRecord dayRecord = new DayRecord(date);
        dayRecords.add(dayRecord);

        return dayRecord;
    }

    private int countResiduary(LocalDate from, LocalDate to, int norm, Method
            getResiduary) throws Exception {
        int residuary = 0;

        SortedSet<DayRecord> recordsInRange = dayRecords.subSet(getDayRecord
                (from), getDayRecord((to)));

        for (DayRecord dayRecord : recordsInRange) {
            residuary += norm - (int) getResiduary.invoke(dayRecord);
        }

        return residuary > 0 ? residuary : 0;
    }

    private Method getMethodByName(Class<?> clazz, String methodName) throws
            Exception {
        return clazz.getMethod(methodName);
    }

    public enum Gender {
        MALE, FEMALE
    }

    private class DateComparator implements Comparator<DayRecord> {
        @Override
        public int compare(DayRecord a, DayRecord b) {
            return a.getDate().compareTo(b.getDate());
        }
    }
}
