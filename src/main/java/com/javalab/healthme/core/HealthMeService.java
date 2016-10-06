package com.javalab.healthme.core;

/**
 * @author Mariia Lapovska
 */
public class HealthMeService {

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

    public enum Gender {
        MALE, FEMALE
    }
}
