package org.example;

import java.util.logging.Logger;
import java.util.List;


public class Glutton extends Adventurer {

    private static final Logger LOGGER = Logger.getLogger(Glutton.class.getName());
    public Glutton(String name) {
        super(name);
        this.setHealth(3);
    }
    @Override
    public void eatFood(Room room) {
        if (room.hasFood() && !room.hasCreature()) {
            if (room.hasCreatureOfType(Demon.class)) {
                fight(room.getCreatureOfType(Demon.class));
            } else {
                super.eatFood(room);
            }
        }
    }
    @Override
    public void fight(Creature creature) {
        if (creature instanceof Demon) {
            super.fight(creature);
        } else {
            LOGGER.warning(getName() + " refuses to fight!");
        }
    }
}
