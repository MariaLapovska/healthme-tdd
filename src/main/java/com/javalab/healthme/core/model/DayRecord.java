package com.javalab.healthme.core.model;

import java.time.LocalDate;

/**
 * @author Mariia Lapovska
 */
public class DayRecord {
    private LocalDate date;

    private int consumedCalories;
    private int consumedWater;
    private int walkedSteps;

    public DayRecord() {
        date = LocalDate.now();
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

    public int getConsumedCalories() {
        return consumedCalories;
    }

    public int getConsumedWater() {
        return consumedWater;
    }

    public int getWalkedSteps() {
        return walkedSteps;
    }

    public LocalDate getDate() {
        return date;
    }
}
