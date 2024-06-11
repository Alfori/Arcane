package org.example;

//import java.util.List;

public class Knight extends Adventurer {
    public Knight(String name) {
        super(name);
        this.setHealth(8);
    }

    @Override
    public void fight(Creature creature) {
        super.fight(creature);
    }
}
