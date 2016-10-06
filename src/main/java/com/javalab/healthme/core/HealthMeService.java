package com.javalab.healthme.core;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Mariia Lapovska
 */
public class HealthMeService {

    private SortedSet<DayRecord> dayRecords = new TreeSet<>(new
            DateComparator());

    public int calculateCaloriesNorm(int weight, int height, int age, Gender
            gender) {
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
