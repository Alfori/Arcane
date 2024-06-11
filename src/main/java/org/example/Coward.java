package org.example;

import java.util.logging.Logger;

public class Coward extends Adventurer
{

    private static final Logger LOGGER = Logger.getLogger(Coward.class.getName());

    public Coward(String name)
    {
        super(name);
        this.setHealth(5);
    }

    @Override
    public void fight(Creature creature)
    {
        if (creature instanceof Demon)
        {
            super.fight(creature);
        }
        
        else
        {
            flee();
        }
    }

    // method for coward to flee
    private void flee()
    {
        // coward loses 0.5 health when it flees
        decreaseHealth(1);
        LOGGER.warning(getName() + " is running away!");
    }
}
