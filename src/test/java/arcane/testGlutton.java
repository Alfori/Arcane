package arcane;

import org.example.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class testGlutton {

    @Test
    void testEatFoodInSafeRoom() {
        Glutton glutton = new Glutton("Test Glutton");
        Room room = new Room("Safe Room");
        int initialHealth = glutton.getHealth();
        room.setFood(1);
        glutton.eatFood(room);
        assertEquals(initialHealth , glutton.getHealth(), "Glutton should have increased health after eating food.");
    }



    @Test
    void testFightNonDemonCreature() {
        Glutton glutton = new Glutton("Test Glutton");
        Creature creature = new Creature("Test Creature") {
            @Override
            public void decreaseHealth(int damage) {
                super.setHealth(this.getHealth() - damage);
            }
        };

        glutton.fight(creature);

        assertEquals(3, glutton.getHealth(), "Glutton should not engage and lose health when fighting a non-demon creature.");
    }

    void testFightWithDemon() {
        Glutton glutton = new Glutton("Test Glutton");
        Demon demon = new Demon("Test Demon");
        demon.setHealth(10); // Set initial health for the demon to ensure it's higher than 0.
        int initialGluttonHealth = glutton.getHealth();

        glutton.fight(demon);

        assertTrue(initialGluttonHealth > glutton.getHealth(), "Glutton should have decreased health after fighting a demon.");
    }

    void testEatFoodRoomWithDemon() {
        Glutton glutton = new Glutton("Test Glutton");
        Room room = new Room("Dangerous Room");
        room.setFood(1);
        Demon demon = new Demon("Test Demon");
        room.addCreature(demon);

        glutton.eatFood(room);

        // Since the demon is present, we expect a fight, which should decrease the Glutton's health.
        assertFalse(glutton.isAlive(), "Glutton should have decreased health after attempting to eat in a room with a demon.");
    }

}
