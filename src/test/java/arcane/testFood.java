package arcane;
import org.example.Food;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class testFood
{
    private Food food;

    @BeforeEach
    void setUp()
    {
        food = new Food("Croissant", 10);
    }

    @Test       // Testing to see if the method is returning Croissant (the name of the food object)
    void testGetName()
    {
        assertEquals("Croissant", food.getName(), "Name should return Croissant.");
    }

    @Test       // Testing to see the health is actually boosting
    void testHealthBoost()
    {
        assertEquals(10, food.getHealthBoost(), "should return 10 for the amount the health boosts");
    }

    @Test       // Testing to make sure the food is not instantly consumed
    void testConsumed()
    {
        assertTrue(!food.isConsumed(), "food is not consumed at start");
    }

    @Test       // Testing to see if the food was consumed and how much it is putting back out
    void testIfItWasConsumed()
    {
        int tempBoost = food.consume();
        assertTrue(food.isConsumed(), "should say true to the food being already consumed");
        assertEquals(10, tempBoost, "make sure the consume is returning the coorect about of HP that should be returned");
        assertEquals(0, food.consume(), "consume should return 0 if the food is already consumed.");
    }

    @Test       // Testing the food reset method
    void testFoodConsumed()
    {
        int initialFoodAmount = 10;
        food.consume();
        food.reset();
        assertTrue(!food.isConsumed(), "food shouldnt be consumed after resetting");
        assertEquals(initialFoodAmount, food.consume(), "consuming the food should equal the amount it was before");
    }


}
