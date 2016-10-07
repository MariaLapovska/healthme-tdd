package com.javalab.healthme.core;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

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

    public static int calculateCaloriesNorm(int weight, int height, int age,
                                            Gender
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

    public double countResiduaryCaloriesPercentage(LocalDate from, LocalDate
            to) throws Exception {
        return (double) countResiduaryCalories(from, to) / (caloriesDayNorm *
                getNumOfRecords(from, to));
    }

    public double countResiduaryWaterPercentage(LocalDate from, LocalDate to)
            throws Exception {
        return (double) countResiduaryWater(from, to) / (waterDayNorm *
                getNumOfRecords(from, to));
    }

    public double countResiduaryStepsPercentage(LocalDate from, LocalDate to)
            throws Exception {
        return (double) countResiduarySteps(from, to) / (stepsDayNorm *
                getNumOfRecords(from, to));
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

    public double countResiduaryCaloriesPercentage(LocalDate date) throws
            Exception {
        return (double) countResiduaryCalories(date, date.plusDays(1)) /
                caloriesDayNorm;
    }

    public double countResiduaryWaterPercentage(LocalDate date) throws
            Exception {
        return (double) countResiduaryWater(date, date.plusDays(1)) /
                waterDayNorm;
    }

    public double countResiduaryStepsPercentage(LocalDate date) throws
            Exception {
        return (double) countResiduarySteps(date, date.plusDays(1)) /
                stepsDayNorm;
    }

    public double periodEatingMedian(LocalDate from, LocalDate to) throws
            Exception {
        return periodMedian(from, to, getMethodByName(DayRecord.class,
                "getConsumedCalories"));
    }

    public double periodDrinkingMedian(LocalDate from, LocalDate to) throws
            Exception {
        return periodMedian(from, to, getMethodByName(DayRecord.class,
                "getConsumedWater"));
    }

    public double periodWalkingMedian(LocalDate from, LocalDate to) throws
            Exception {
        return periodMedian(from, to, getMethodByName(DayRecord.class,
                "getWalkedSteps"));
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

    private int getNumOfRecords(LocalDate from, LocalDate to) {
        return dayRecords.subSet(getDayRecord(from), getDayRecord(to)).size();
    }

    private double periodMedian(LocalDate from, LocalDate to, Method
            getConsumed) throws Exception {
        SortedSet<DayRecord> recordsInRange = dayRecords.subSet(getDayRecord
                (from), getDayRecord((to)));
        List<Integer> periodData = new ArrayList<>();

        for (DayRecord dayRecord : recordsInRange) {
            periodData.add((int) getConsumed.invoke(dayRecord));
        }

        periodData.sort(Comparator.naturalOrder());
        int size = periodData.size();

        if ((size % 2) != 0) {
            return periodData.get(size / 2);
        } else {
            return ((double) (periodData.get((size / 2) - 1) + periodData.get
                    (size / 2)) / 2);
        }
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
