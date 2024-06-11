package arcane;

import org.example.Adventurer;
import org.example.Coward;
import org.example.Creature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testCoward {

    @Test
    void testFleeWhenEncounteringDemon() {
        Coward coward = new Coward("Test Coward");
        Creature demon = new Creature("Test Demon");
        demon.setHealth(10); // Set a health value greater than 0 for the creature
        coward.fight(demon);
        assertTrue(coward.getHealth() < 5, "Coward's health should decrease when encountering a demon");
    }

    @Test
    public void testNotFleeWhenEncounteringNonDemon() {
        Creature nonDemonCreature = new Creature("Non-Demon Creature");
        Adventurer coward = new Coward("Coward");
        coward.decreaseHealth(2);
        coward.fight(nonDemonCreature);
        assertFalse(coward.getHealth() >= 3, "Coward's health should remain unchanged when encountering a non-demon creature");
    }

}
