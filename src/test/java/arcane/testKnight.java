package arcane;

import org.example.Creature;
import org.example.Knight;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class testKnight {
    @Test
    public void knightInitialHealthIsCorrect() {
        Knight knight = new Knight("Test Knight");
        assertEquals(8, knight.getHealth(), "Knight should start with 8 health.");
    }

    @Test
    public void knightCanFightCreature() {
        Knight knight = new Knight("Fighting Knight");
        Creature creature = new Creature("Weak Creature");
        creature.setHealth(1);
        knight.fight(creature);
        assertEquals(0, creature.getHealth(), "Creature should have decreased health after fight.");
    }
}
