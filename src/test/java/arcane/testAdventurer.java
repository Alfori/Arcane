package arcane;

import org.example.Adventurer;
import org.example.Room;

import org.example.Creature;
//import org.example.Creature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class testAdventurer
{
    private Adventurer adventurer;
    private Room theRoom;
    private Room neighborRoom;
    private Creature creature;

    @BeforeEach
    public void setUp() 
    {
        adventurer = new Adventurer ("testAdventurer");
        theRoom = new Room("currentRoom");
        neighborRoom = new Room("neighborRoom");
        creature = new Creature ("testCreature");
    }

    @Test
    public void testConst()
    {
        assertEquals("testAdventurer", adventurer.getName());
        assertTrue(adventurer.isAlive());
    }

    @Test
    public void testIsAlive()
    {
        adventurer.setHealth(0);
        assertTrue(adventurer.isAlive(), "check if player is not alive, should return true");
        adventurer.setHealth(26);
        assertTrue(adventurer.isAlive(), "check if player is alive, should return True");
    }

    @Test
    public void testingDecreaseHealth()
    {
        int currentHP = adventurer.getHealth();
        adventurer.decreaseHealth(2);
        assertTrue(adventurer.getHealth() == currentHP - 2, "Health should go down by 1");
    }

    @Test
    public void testEatFood()
    {
        theRoom.setFood(3);
        int currentHealth = adventurer.getHealth();
        adventurer.eatFood(theRoom);
        assertTrue (currentHealth <=(3+adventurer.getHealth()), "Eating food should increase health by 3");
    }

    @Test
    public void testingMove()
    {
        List<Room> neighboringRooms = new ArrayList<>(Arrays.asList(neighborRoom)); // list of neighboring rooms
        adventurer.move(neighboringRooms);
        assertFalse(neighboringRooms.contains(adventurer.getCurrentRoom()), "Adventurer should not move to one of the neighboring rooms.");
    }

    @Test
    public void testingFight()
    {
        int adventurerHealth = adventurer.getHealth();      // get the adventurers current health
        int creatureHealth = creature.getHealth();          // get the creatures current heatlh

        adventurer.fight(creature);                         // start the fight

        boolean adventurerWon = creature.getHealth() < creatureHealth;      //check if the adventurer won and have a bool to show whether its true or false
        boolean creatureWon = adventurer.getHealth() < adventurerHealth;    //check if the creature won
        boolean draw = adventurer.getHealth() == adventurerHealth && creature.getHealth() == creatureHealth;    // check if it resulted in a draw

        assertTrue(adventurerWon || creatureWon || draw, "Either one side wins or we have a draw.");    
    }

    @Test
    public void testSetCurrentRoom()
    {
        adventurer.setCurrentRoom(theRoom);
        Room test = adventurer.getCurrentRoom();

        assertTrue(adventurer.getCurrentRoom()  == test, "Testing if these two are equal/if it changed.");

        adventurer.setCurrentRoom(neighborRoom);
        assertFalse(adventurer.getCurrentRoom() == test, "See if these two are not the same anymore after changing the current room.");
    }

    @Test
    public void testingSetHealth()
    {
        adventurer.setHealth(23);
        assertTrue(adventurer.getHealth() == 23, "Should be true that the hp is 23.");
        adventurer.setHealth(31);
        assertTrue(adventurer.getHealth() == 31, "Should be true that the hp is 31.");
    }

    @Test
    public void testingGetHealth()
    {
        adventurer.setHealth(225);
        assertTrue(adventurer.getHealth() == 225, "should be 225.");
    }

    


}
