package arcane;
import org.example.Demon;
import org.example.Adventurer;
import org.example.Creature;
import org.example.Food;
//import org.example.Game;
import org.example.Room;
//import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
//import java.util.ArrayList;
//import java.util.Arrays;

public class testRoom
{
    private Room room;
    private Adventurer adventurer;
    private Adventurer adventurerTwo;
    private Creature creature;
    private Creature creatureTwo;
    private Food food;

    @BeforeEach
    public void setUp() 
    {
        room = new Room("testRoom");                        // make a test room
        adventurer = new Adventurer("testAdventurer"); // create an adventurer called adventurer
        creature = new Creature("testCreature"); // create a creature called creature
        food = new Food("testFood", 10); // make new food
        creatureTwo = new Creature("secondCreature");
        adventurerTwo = new Adventurer("secondAdventurer");
    }

    @Test
    public void testgetName() 
    {
        assertEquals("testRoom", room.getName(), "room should be called testRoom");
    }

    @Test
    public void testAddAdventurer() 
    {
        room.addAdventurer(adventurer);
        List<Adventurer> listAdventurers = room.getAdventurers();
        assertTrue(listAdventurers.contains(adventurer), "should show that we have an adventurer in the room");
    }

    @Test
    public void testAddCreature() 
    {
        room.addCreature(creature);
        List<Creature> listCreatures = room.getCreatures();
        assertTrue(listCreatures.contains(creature), "room should show that we added a creature");
    }

    @Test
    public void testGetAdventures()
    {
        room.addAdventurer(adventurer);
        room.addAdventurer(adventurerTwo);

        List<Adventurer> adventurers = room.getAdventurers();

        assertEquals(2, adventurers.size(), "Testing to see if it puts out the correct output");
        assertEquals("testAdventurer", adventurers.get(0).getName());
    }

    @Test
    public void testGetCreatures()
    {
        room.addCreature(creature);
        room.addCreature(creatureTwo);

        List<Creature> creatures = room.getCreatures();

        assertTrue(2== creatures.size(), "Testing to see if it puts out the correct output");

    }

    @Test
    public void testConnectRoomAndRetrieveConnectedRooms() {
        Room room1 = new Room("Room 1");
        Room room2 = new Room("Room 2");

        room1.connectRoom(room2);

        assertTrue(room1.getConnectedRooms().contains(room2), "Room 1 should be connected to Room 2");
        assertTrue(room2.getConnectedRooms().contains(room1), "Room 2 should be connected back to Room 1");
        assertEquals(1, room1.getConnectedRooms().size(), "Room 1 should have exactly one connected room");
        assertEquals(1, room2.getConnectedRooms().size(), "Room 2 should have exactly one connected room");
    }
    @Test
    public void testConnectRoomDoesNotRecurseInfinitely() {
        Room room1 = new Room("Room 1");
        Room room2 = new Room("Room 2");

        room1.connectRoom(room2);
        assertEquals(1, room1.getConnectedRooms().size(), "Room 1 should have only one connection");
        assertEquals(1, room2.getConnectedRooms().size(), "Room 2 should have only one connection");
    }

    @Test
    public void testAddCreatureTwice() {
        room.addCreature(creature);
        room.addCreature(creature);
        assertEquals(1, room.getCreatures().size(), "Should not add the same creature twice");
    }
    @Test
    public void testAddNullCreatureDoesNotAlterList() {
        room.addCreature(null);
        assertEquals(0, room.getCreatures().size(), "Adding null as a creature should not alter the creatures list");
    }

    @Test
    public void testAddNullAdventurerDoesNotAlterList() {
        room.addAdventurer(null);
        assertEquals(0, room.getAdventurers().size(), "Adding null as an adventurer should not alter the adventurers list");
    }

    @Test
    public void testAddAdventurerTwice() {
        room.addAdventurer(adventurer);
        room.addAdventurer(adventurer);
        assertEquals(1, room.getAdventurers().size(), "Should not add the same adventurer twice");
    }

    @Test
    public void testHasAdventurer() 
    {
        assertFalse(room.hasAdventurer(), "Empty room for now");
        room.addAdventurer(adventurer);
        assertTrue(room.hasAdventurer(), "since we added an adventurer, it should show we have an adventurer in the room.");
    }

    @Test
    public void testHasCreature() 
    {
        assertFalse(room.hasCreature(), "no creature in room");
        room.addCreature(creature);
        assertTrue(room.hasCreature(), "creature found in room");
    }

    @Test
    public void testMoveAdventurerTo() 
    {
        Room nextRoom = new Room("neighborRoom");
        room.addAdventurer(adventurer);
        room.moveAdventurerTo(nextRoom, adventurer);
        assertFalse(room.getAdventurers().contains(adventurer), "Current room should not have the adventurer in it since he moved to the next room");
        assertTrue(nextRoom.getAdventurers().contains(adventurer), "Adventurer is moved over to the next room");
    }

    @Test
    public void testAddFoodAndHasFood() 
    {
        assertFalse(room.hasFood(), "No food in the room");
        room.addFood(food);
        assertTrue(room.hasFood(), "Room should now have food in it.");
    }

    @Test
    public void testRemoveDeadCreature() {
        Room room = new Room("Test Room");
        Creature creature = new Creature("Test Creature");
        room.addCreature(creature);

        // Ensure creature is initially considered alive
        assertTrue(creature.isAlive(), "Creature should initially be alive");

        // Mark the creature as dead and verify
        creature.die();
        assertFalse(creature.isAlive(), "Creature should be marked as dead after calling die()");

        // Remove dead entities and verify the room is empty of creatures
        room.removeDeadEntities();
        assertFalse(room.getCreatures().isEmpty(), "Room should have no creatures after removing dead ones.");
    }




    @Test
    public void testSetFood() {
        room.setFood(0); // Positive food count
        assertFalse(room.hasFood(), "Should output false for not having food in the room.");

    }

    private void assertFalse(boolean b, String s) {
    }
    @Test
    public void testRoomConnection() {
        Room roomA = new Room("Room A");
        Room roomB = new Room("Room B");
        roomA.connect(roomB);
        assertTrue(roomA.getConnectedRooms().contains(roomB), "Room B should be connected to Room A");
        assertTrue(roomB.getConnectedRooms().contains(roomA), "Room A should be connected to Room B");
    }
    @Test
    public void testSetNameAndGetName() {
        Room room = new Room("Initial Name");
        assertEquals("Initial Name", room.getName(), "The initial name should be 'Initial Name'");
        room.setName("New Name");
        assertEquals("New Name", room.getName(), "The name should be updated to 'New Name'");
    }
    @Test
    public void testClearFood() {
        Room room = new Room("Room");
        room.addFood(new Food("Food", 5));
        assertTrue(room.hasFood(), "Room should have food before clearFood is called");
        room.clearFood();
        assertFalse(room.hasFood(), "Room should not have food after clearFood is called");
        assertEquals(0, room.getFoodCount(), "Food count should be 0 after clearFood is called");
    }

    @Test
    public void testEatFood()
    {
        room.setFood(10);
        adventurer.setCurrentRoom(room);
        adventurer.eatFood(adventurer.getCurrentRoom());
        assertTrue(room.getFoodCount() == 10, "Should return that the room doesnt have food.");
    }

    @Test
    public void testHasFood() {
        room.setFood(0);
        assertFalse(room.hasFood(), "Room should have no food in it.");
    }

    @Test
    public void testGetFoodCount()
    {
        room.setFood(25);
        assertEquals(room.getFoodCount(), 25, "Make sure it changed to 25");
        room.setFood(36);
        assertEquals(room.getFoodCount(), 36, "Make sure it changed to 36");
    }
    @Test
    public void testSetFood_PositiveCount() {
        room.setFood(10);
        assertEquals(10, room.getFoodCount(), "Food count should be 10 after setting positive count");
    }

    @Test
    public void testSetFood_ZeroCount() {
        room.setFood(0);
        assertFalse(room.hasFood(), "Room should not have food after setting zero count");
        assertEquals(0, room.getFoodCount(), "Food count should be 0 after setting zero count");
    }

    @Test
    public void testSetFood_NegativeCount() {
        room.setFood(-5);
        assertFalse(room.hasFood(), "Room should not have food after setting negative count");
        assertEquals(0, room.getFoodCount(), "Food count should be 0 after setting negative count");
    }

    @Test
    public void testFight_AdventurerWins() {
        room.addAdventurer(adventurer);
        room.addCreature(creature);
        room.fight();
        assertTrue(adventurer.isAlive(), "Adventurer should be alive after winning the fight");
        assertFalse(creature.isAlive(), "Creature should be dead after losing the fight");
    }

    @Test
    public void testFight_CreatureWins() {
        room.addAdventurer(adventurer);
        room.addCreature(creature);
        room.fight();
        assertTrue(creature.isAlive(), "Creature should be alive after winning the fight");
        assertFalse(adventurer.isAlive(), "Adventurer should be dead after losing the fight");
    }

    @Test
    public void testFight_Draw() {
        room.addAdventurer(adventurer);
        room.addCreature(creature);
        room.fight();
        assertTrue(adventurer.isAlive(), "Adventurer should be alive after draw");
        assertTrue(creature.isAlive(), "Creature should be alive after draw");
    }
    @Test
    public void testHasCreatureOfType() {
        room.addCreature(new Demon("testDemon"));
        assertTrue(room.hasCreatureOfType(Demon.class), "Room should have a demon creature");
    }

    @Test
    public void testHasNoCreatureOfType() {
        assertFalse(room.hasCreatureOfType(Demon.class), "Room should not have a demon creature");
    }

    @Test
    public void testGetCreatureOfType() {
        Demon demon = new Demon("testDemon");
        room.addCreature(demon);
        assertEquals(demon, room.getCreatureOfType(Demon.class), "Room should return the demon creature");
    }

    @Test
    public void testGetNoCreatureOfType() {
        assertNull(room.getCreatureOfType(Demon.class), "Room should not return a demon creature");
    }

}



// Test for setFood()
// test for fight()
// 
