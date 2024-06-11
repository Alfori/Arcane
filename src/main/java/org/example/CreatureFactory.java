package org.example;

public class CreatureFactory {
    public static Creature createCreature(String type, String name) {
        switch (type.toLowerCase()) {
            case "demon":
                return new Demon(name);
            case "creature":
                return new Creature(name);
            case "knight":
                return new Creature(name);
            default:
                return null;
        }
    }
}
