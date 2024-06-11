package org.example;

import ch.qos.logback.classic.Logger;

public class Food {
    private String name;
    private int healthBoost;
    private boolean isConsumed;

    public Food(String name, int healthBoost) {
        this.name = name;
        this.healthBoost = healthBoost;
        this.isConsumed = false;
    }

    public String getName() {
        return name;
    }

    public int getHealthBoost() {
        return healthBoost;
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    public String getFoodName()
    {
        return name;
    }

    // mark the food as consumed and return the health boost value
    public int consume() {
        if (!isConsumed) {
            isConsumed = true;
            return healthBoost;
        }
        return 0; // no health boost if already consumed
    }

    // reset the food item to make it available again if needed
    public void reset() {
        isConsumed = false;
    }
}