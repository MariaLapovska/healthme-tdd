package com.javalab.healthme.core;

import java.time.LocalDate;

/**
 * @author Mariia Lapovska
 */
public class Day {
    private int consumedCalories;
    private int consumedWater;
    private int walkedSteps;

    private LocalDate date;

    public Day() {
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

    public LocalDate getDate() {
        return date;
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
}
