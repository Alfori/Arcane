package org.example;

import java.util.Random;

public class AdventurerFactory {
    public static Adventurer createAdventurer(String type, String name) {
        switch (type.toLowerCase()) {
            case "adventurer":
                return new Adventurer(name);
            case "glutton":
                return new Glutton(name);
            case "knight":
                return new Knight(name);
            case "coward":
                return new Coward(name);
            default:
                return null;
        }
    }
}
