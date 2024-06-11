package org.example;

public class FoodFactory {
    public static Food createFood(String name, int healthBoost) {
        return new Food(name, healthBoost);
    }
}
