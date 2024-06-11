package org.example;
import java.util.logging.Logger;
//import java.util.ArrayList;
import java.util.List;

public class Demon extends Creature {

    private static final Logger LOGGER = Logger.getLogger(Demon.class.getName());
    public Demon(String name) {
        super(name);
        this.setHealth(15);
    }

    @Override
    public void decreaseHealth(int damage) {
        int actualDamage = damage / 2;
        super.decreaseHealth(actualDamage);
    }
    public void act(Room room) {
        if (room.isAlone(this)) {
            flee(room);
        } else {

        }
    }


    private void flee(Room room) {
        LOGGER.info(getName() + " flees from " + room.getName());
        room.removeCreature(this);

    }
    public void fight(List<Adventurer> adventurers) {
        for (Adventurer adventurer : adventurers) {
            adventurer.decreaseHealth(2);
            if (!adventurer.isAlive())
            {
                LOGGER.warning(adventurer.getName() + " has been defeated by " + this.getName() + "!");
            }
        }
    }
}
