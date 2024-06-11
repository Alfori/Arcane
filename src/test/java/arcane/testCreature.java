package arcane;

import org.example.Creature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class testCreature 
{
    private Creature creature;

    @BeforeEach
    public void setup()
    {
        creature = new Creature("testCreature");
    }

    @Test
    public void testDecreaseHealth()
    {
        int initialHealth = creature.getHealth();
        creature.decreaseHealth(2);
        assertTrue(creature.getHealth() == initialHealth - 2, "Health should decrease by 2");
    }
    
    @Test
    public void testName()
    {
        assertEquals("testCreature", creature.getName(), "Creature should be named testCreature");
    }

    @Test
    public void testIsAlive()
    {
        int initialHealth = creature.getHealth();
        creature.decreaseHealth(initialHealth + 1);     //damage it by its health + 1 to make sure its dead.
        assertFalse(creature.isAlive(), "Should return false since its decreasing all the health and - 1.");
    }

    @Test
    public void testGetHealth()
    {
        assertTrue(creature.getHealth() == 8, "Health should return to the default which is 8");
        creature.setHealth(52);
        assertTrue(creature.getHealth() == 52, "Making sure we get the correct one after changing it.");
    }

    @Test
    public void testSetHealth()
    {
        creature.setHealth(12);
        assertEquals(creature.getHealth(), 12, "Testing to see if it gets the correct health.");
        creature.setHealth(17);
        assertEquals(creature.getHealth(), 17, "Testing to see if it gets the correct health again.");
    }


}

// added getCurrentRoom for adventurer + 
