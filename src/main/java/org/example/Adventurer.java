package org.example;

import java.util.logging.Logger;
import java.util.List;
import java.util.Random;

public class Adventurer implements IObserver {
    private static final Logger LOGGER = Logger.getLogger(Adventurer.class.getName());
    private String name;
    private int health;
    private boolean hasEatenThisTurn = false;
    private Room currentRoom;
    private boolean alive = true;
    // initializing the adventurer with a name and default health of 5
    public Adventurer(String name) {
        this.name = name;
        this.health = 5;
        this.currentRoom = null;
    }

    // method to move the adventurer to a random neighboring room
    public void move(List<Room> neighboringRooms) {
        if (neighboringRooms.isEmpty()) {
            LOGGER.warning("No neighboring rooms available.");
            return;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(neighboringRooms.size());
        LOGGER.info(name + " moves to " + neighboringRooms.get(randomIndex).getName());
    }
    public boolean hasEatenThisTurn() {
        return hasEatenThisTurn;
    }

    @Override
    public void update(String eventDesc) 
    {
        LOGGER.info("Adventurer received event: " + eventDesc);
    }


    public void eat() {

        this.hasEatenThisTurn = true;
        EventBus.getInstance().postMessage(EventType.ATE_SOMETHING, getName() + " has eaten.");
    }

    // method for the adventurer to eat food and regain health per homework 3 requests
    public void eatFood(Room room) {
        if (!hasEatenThisTurn && isAlive()) {
            if (room.hasFood() && !room.hasCreature()) {
                this.health += room.getFoodCount();
                EventBus.getInstance().postMessage(EventType.ATE_SOMETHING, getName() + " has eaten.");
                room.clearFood();
                LOGGER.info(name + " eats " + room.getFoodCount() + " food item and gains health.");
                hasEatenThisTurn = true;
            } else if (!room.hasFood()) {
                LOGGER.info(name + " finds no food in the room.");
            } else if (room.hasCreature()) {
                LOGGER.info(name + " cannot eat with creatures in the room.");
            }
        } else if (hasEatenThisTurn) {
            LOGGER.info(name + " has already eaten this turn.");
            EventBus.getInstance().postMessage(EventType.ATE_SOMETHING, getName() + " has eaten.");
        }
    }

    public void resetFoodConsumption() {
        hasEatenThisTurn = false;
    }

    // Method for the adventurer to engage in combat with a creature
    public void fight(Creature creature) {
        Random random = new Random();
        int adventurerRoll = random.nextInt(6) + 1;
        int creatureRoll = random.nextInt(6) + 1;

        if (adventurerRoll == creatureRoll) {
            LOGGER.info("The combat ends in a draw.");
        } else if (adventurerRoll > creatureRoll) {
            creature.decreaseHealth(adventurerRoll - creatureRoll); // Damage the creature
            LOGGER.info(name + " defeats " + creature.getName() + " for " + (adventurerRoll - creatureRoll) + " damage.");
        } else {
            decreaseHealth(creatureRoll - adventurerRoll); // Damage the adventurer
            LOGGER.info(creature.getName() + " defeats " + name + " for " + (creatureRoll - adventurerRoll) + " damage.");
        }
    }

    // method to decrease the adventurer's health
    public void decreaseHealth(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            die();
        }
    }
    public boolean isAlive() {
        return alive;
    }

    // Method to mark the adventurer as dead
    public void die() {
        this.alive = false;
        logDeath();
        EventBus.getInstance().postMessage(EventType.DEATH, getName() + " has died.");
    }
    // method to log the death of the adventurer
    private void logDeath() {
        LOGGER.severe("Adventurer " + name + " has died.");
    }

    // getter methods for name and health
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    // checking to see if the adventurer is alive


    public interface LivingEntity {
        boolean isAlive();
    }

    public void setHealth(int newHealth)
    {
        if (newHealth >= 0)
        {
            this.health = newHealth;
        }
        
        else
        {
            LOGGER.warning("The health can not be a negative number or 0.");
        }
    }

    // For Ratu //
    public Room getCurrentRoom()
    {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room movedRoom)
    {
        this.currentRoom = movedRoom;
    }
}