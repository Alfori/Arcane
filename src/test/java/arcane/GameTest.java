package arcane;
import static org.junit.jupiter.api.Assertions.*;
//import java.util.Arrays;
import java.util.ArrayList;
import org.example.Demon;
import java.lang.reflect.Field;
import java.util.List;
import org.example.Adventurer;
import org.example.Creature;
import org.example.Game;
import org.example.Room;
//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest 
{
    private Game game;
    private Room firstRoom;
    private Room secondRoom;
    Creature creature;
    Adventurer adventurer;

    @BeforeEach
    public void setUp() 
    {
        adventurer = new Adventurer ("testAdventurer");
        firstRoom = new Room("firstRoom");
        secondRoom = new Room("secondRoom");
        creature = new Creature ("testCreature");
        game = new Game(new ArrayList<Room>());

    }

    @Test
    void testGamePlayEndsGame() {
        game = new Game(new ArrayList<Room>());
        game.play();
        assertTrue(game.isGameOver(), "Game needs to finish once the play is over");
    }

    @Test
    void testToggleGameState() 
    {
        game = new Game(new ArrayList<Room>());
        assertFalse(game.isGameOver(), "Game is not over as there is nothing to stop it");

        game.toggleGameState();
        assertTrue(game.isGameOver(), "Game is toggled to be over, so it should be finished now");

        game.toggleGameState();
        assertFalse(game.isGameOver(), "We turned the gaem state back on so it should return that its on");
    }

    @Test
    void testGameReset()
    {
        game = new Game(new ArrayList<Room>());

        game.play();
        assertTrue(game.isGameOver(), "After we play the game is now over.");

        game.reset();
        assertFalse(game.isGameOver(), "Game should be back to being on and not over");
    }

    @Test
    public void testCheckGameOver() 
    {
        Adventurer deadAdventurer = new Adventurer("Dead Adventurer");
        deadAdventurer.setHealth(0);
        firstRoom.addAdventurer(deadAdventurer);

        Creature livingCreature = new Creature("Living Creature");
        secondRoom.addCreature(livingCreature);
        
        assertFalse(game.isGameOver(), "Game should be over because all adventurers are dead");

        game.checkGameOver();
        assertTrue(game.isGameOver(), "Game should be over because all adventurers are dead");
    }
    @Test
    void testRoomWithMultipleAdventurers() {
        Room room = new Room("Crowded Room");
        Adventurer adventurer1 = new Adventurer("Adventurer One");
        Adventurer adventurer2 = new Adventurer("Adventurer Two");
        room.addAdventurer(adventurer1);
        room.addAdventurer(adventurer2);

        assertEquals(2, room.getAdventurers().size(), "Room should contain two adventurers.");
    }

    @Test
    void testCreatureFightReducesHealth() {
        Creature creature = new Creature("Aggressive Creature");
        Adventurer adventurer = new Adventurer("Brave Adventurer");
        adventurer.setHealth(10); // Assume the adventurer starts with health of 10
        int initialHealth = adventurer.getHealth();

        // Simulating a fight where the creature attacks the adventurer.
        // Assuming the 'fight' method exists and is applicable in this context.
        creature.fight(adventurer);

        assertTrue(adventurer.getHealth() < initialHealth, "Adventurer's health should be reduced after fighting with a creature.");
    }


    @Test
    void testAdventurerHealthIncreasesWithFood() {
        int initialHealth = adventurer.getHealth();
        Room roomWithFood = new Room("Kitchen");
        roomWithFood.setFood(5); // Assume this method sets the amount of food in the room
        adventurer.eatFood(roomWithFood); // Assume this method allows the adventurer to eat food and gain health

        assertTrue(adventurer.getHealth() >= initialHealth, "Adventurer's health should increase after eating food.");
    }

    @Test
    void testDemonFleeingWhenAlone() {
        Demon demon = new Demon("Lonely Demon");
        Room spookyRoom = new Room("Spooky Room");
        spookyRoom.addCreature(demon);
        demon.act(spookyRoom); // Assume 'act' is a method where the demon decides to fight or flee based on conditions

        assertTrue(spookyRoom.getCreatures().isEmpty() || !demon.isInRoom(spookyRoom), "Demon should flee the room when alone.");
    }

    @Test
    void testRoomConnectivity() throws NoSuchFieldException, IllegalAccessException {
        Game game = Game.newBuilder().create3x3Grid().build();

        Field roomsField = Game.class.getDeclaredField("rooms");
        roomsField.setAccessible(true); // Bypass the access check
        List<Room> rooms = (List<Room>) roomsField.get(game);

        Room startRoom = rooms.get(0);
        Room connectedRoom = rooms.get(1);

        assertTrue(startRoom.isConnectedTo(connectedRoom), "Connected rooms should recognize each other as connected.");
    }
    @Test
    void testAdventurerMovement() {
        Game game = Game.newBuilder().createConnectedRooms(5).build();
        Adventurer adventurer = new Adventurer("Explorer");
        game.getRooms().get(0).addAdventurer(adventurer);
        game.play();

        assertNotEquals(1, game.getRoomOfAdventurer(adventurer), "Adventurer should have moved from the starting room.");

    }
    @Test
    void testFoodConsumptionIncreasesHealth() {
        Game game = Game.newBuilder()
                .createAndAddFoodItems(new String[][]{{"Cake", "5"}})
                .createConnectedRooms(1)
                .build();
        Adventurer adventurer = new Adventurer("Demon");
        Room room = game.getRooms().get(0);
        room.addAdventurer(adventurer);
        int initialHealth = 4;

        game.play();

        assertTrue(adventurer.getHealth() > initialHealth, "Adventurer's health should increase after consuming food.");
    }

    @Test
    void testGameEndsWhenAllAdventurersDead() {
        Game game = Game.newBuilder()
                .createAndAddGluttons(0) // Adjust the number of gluttons as needed
                .createConnectedRooms(1)
                .build();
        game.play();

        assertTrue(game.isGameOver(), "Game should end when all adventurers are dead.");
    }

    @Test
    void testRoomDisconnection() {
        Game game = Game.newBuilder().create2x2Grid().build();
        Room startRoom = game.getRooms().get(0);
        Room disconnectedRoom = game.getRooms().get(3);

        startRoom.disconnect(disconnectedRoom);

        assertFalse(startRoom.isConnectedTo(disconnectedRoom), "Rooms should recognize when they are disconnected.");
    }

    @Test
    public void testIsGameOver() 
    {
        game = new Game(new ArrayList<Room>());

        assertFalse(game.isGameOver(), "Should return false since the game is not over.");

        game.toggleGameState();

        assertTrue(game.isGameOver(), "Should return false since the game is not over.");
    }
    @Test
    public void testAnnounceWinner_NoWinnerYet() {
        game = new Game(new ArrayList<Room>());
        game.initializeMaze();
        assertFalse(game.isGameOver(), "Game should not be over before announcing winner");
        game.announceWinner();
        assertFalse(game.isGameOver(), "Game should not be over if no winner is announced");
    }




    @Test
    public void testMoveAdventurers() {
        game = new Game(new ArrayList<Room>());
        game.initializeMaze();
        game.play();
        assertTrue(game.isGameOver(), "Game should be over after playing");
    }


    @Test
    public void testPrintMazeState() {
        game = new Game(new ArrayList<Room>());
        game.initializeMaze();
        game.printMazeState();
    }

    @Test
    public void testCheckGameOver_AdventurersAlive() {
        game = new Game(new ArrayList<Room>());
        game.initializeMaze();
        assertFalse(game.isGameOver(), "Game should not be over if adventurers are alive");
    }

    @Test
    public void testCheckGameOver_CreaturesAlive() {
        game = new Game(new ArrayList<Room>());
        game.initializeMaze();
        assertFalse(game.isGameOver(), "Game should not be over if creatures are alive");
    }

    @Test
    public void testCheckGameOver_NoAdventurersOrCreatures() {
        game = new Game(new ArrayList<Room>());
        game.initializeMaze();
        assertFalse(game.isGameOver(), "Game should not be over if no adventurers or creatures are present");
    }

    @Test
    public void testPlay() {
        game = new Game(new ArrayList<Room>());
        game.initializeMaze();
        game.play(); // Just checking if it runs without errors
    }

    @Test
    public void testToggleGameState_PlayedGame() {
        game = new Game(new ArrayList<Room>());
        game.play();
        game.toggleGameState();
        assertFalse(game.isGameOver(), "Game should not be over if game state is toggled");
    }

    @Test
    public void testToggleGameState_NotPlayedGame() {
        game = new Game(new ArrayList<Room>());
        game.toggleGameState();
        assertTrue(game.isGameOver(), "Game should be over if game state is toggled without playing");
    }

    @Test
    public void testReset_GameOver() {
        game = new Game(new ArrayList<Room>());
        game.play();
        game.reset();
        assertFalse(game.isGameOver(), "Game should not be over after reset");
    }

    @Test
    public void testReset_NotGameOver() {
        game = new Game(new ArrayList<Room>());
        game.reset();
        assertFalse(game.isGameOver(), "Game should not be over if reset before playing");
    }
}

