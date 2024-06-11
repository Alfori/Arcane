package org.example;
import java.util.Random;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
//import java.util.logging.Level;


public class Room {
    private static final Logger LOGGER = Logger.getLogger(Room.class.getName());
    private List<Adventurer> adventurers = new ArrayList<>();
    private List<Creature> creatures = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private List<Food> foodItems = new ArrayList<>();
    private List<Room> connectedRooms = new ArrayList<>();

    private String name;
    private boolean hasFood;
    private int foodCount;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addCreature(Creature creature) {
        if (creature != null && !this.creatures.contains(creature)) {
            this.creatures.add(creature);
        }
    }
    public void removeDeadEntities() {
        creatures.removeIf(creature -> !creature.isAlive());
        adventurers.removeIf(adventurer -> !adventurer.isAlive());
    }
    public void addAdventurer(Adventurer adventurer) {
        if (adventurer != null && !this.adventurers.contains(adventurer)) {
            this.adventurers.add(adventurer);
        }
    }


    public List<Adventurer> getAdventurers() {
        return adventurers;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public boolean hasAdventurer() {
        return !adventurers.isEmpty();
    }

    public boolean hasCreature() {
        return !creatures.isEmpty();
    }

    public void moveAdventurerTo(Room newRoom, Adventurer adventurer) {
        this.adventurers.remove(adventurer);
        newRoom.addAdventurer(adventurer);
    }
    public boolean isAlone(Creature creature) {
        return creatures.size() == 1 && creatures.contains(creature);
    }

    // Method to add a creature to the room


    // Method to remove a creature from the room
    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }
    public void addFood(Food food) {
        this.foodItems.add(food);
        this.hasFood = true;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean hasFood() {
        return !this.foodItems.isEmpty();
    }
    public boolean hasCreatureOfType(Class<?> creatureType) {
        return creatures.stream().anyMatch(creatureType::isInstance);
    }
    public <T extends Creature> T getCreatureOfType(Class<T> creatureType) {
        for (Creature creature : creatures) {
            if (creatureType.isInstance(creature)) {
                return creatureType.cast(creature);
            }
        }
        return null;
    }
    public boolean isConnectedToAnyRoom() {
        return !this.connectedRooms.isEmpty();
    }
    public boolean isConnectedTo(Room otherRoom) {
        return connectedRooms.contains(otherRoom);
    }



    public void connectRoom(Room room) {
        if (!this.connectedRooms.contains(room)) {
            this.connectedRooms.add(room);
            room.connectRoom(this);
        }
    }
    public void connect(Room otherRoom) {
        if (!connectedRooms.contains(otherRoom)) {
            connectedRooms.add(otherRoom);
            if (!otherRoom.connectedRooms.contains(this)) {
                otherRoom.connect(this);
            }
        }
    }

    public List<Room> getConnectedRooms() {
        return Collections.unmodifiableList(this.connectedRooms);
    }

    public int getFoodCount() {
        return this.foodCount;
    }

    public void clearFood() {
        this.hasFood = false;
        this.foodCount = 0;
    }

    public void encounter() {
        if (!adventurers.isEmpty() && !creatures.isEmpty()) {
            fight();
        } else if (!adventurers.isEmpty() && hasFood) {
            adventurers.stream().filter(Adventurer::isAlive).forEach(this::eatFood);
        }
        removeDeadEntities();
    }

    public boolean containsAdventurer(Adventurer adventurer) {
        return adventurers.contains(adventurer);
    }



    public void fight() {
        Random random = new Random();
        adventurers.forEach(adventurer -> {
            if (adventurer.isAlive()) {
                creatures.forEach(creature -> {
                    int adventurerRoll = random.nextInt(6) + 1;
                    int creatureRoll = random.nextInt(6) + 1;
                    if (adventurerRoll > creatureRoll) {
                        int damage = adventurerRoll - creatureRoll;
                        creature.decreaseHealth(damage);
                        LOGGER.info(adventurer.getName() + " hits " + creature.getName() + " for " + damage + " damage.");
                    } else if (creatureRoll > adventurerRoll) {
                        int damage = creatureRoll - adventurerRoll;
                        adventurer.decreaseHealth(damage);
                        LOGGER.info(creature.getName() + " hits " + adventurer.getName() + " for " + damage + " damage.");
                    }
                });
            }
        });
        removeDeadEntities();
    }

    private void eatFood(Adventurer adventurer) {
        if (adventurer.isAlive()) {
            if (hasFood) {
                adventurer.eatFood(this);
                LOGGER.info(adventurer.getName() + " eats food and regains health.");
                clearFood();
            }
        }
    }

    public void setAdventurer(Adventurer adventurer) {
        addAdventurer(adventurer);
    }

    public void setCreature(Creature creature) {
        addCreature(creature);
    }
    public List<Food> getFoodItems() {
        return this.foodItems;
    }

    public void setFood(int food)
    {
        if (food > 0)
        {
            hasFood = true;
            this.foodCount = food;
            LOGGER.info("Food has been set to " + food + " in room");
        }

        else if (food == 0)
        {
            hasFood = false;
            this.foodCount = food;
            LOGGER.info("This room has been set to have no food.");
        }

        else
        {
            LOGGER.info("Food can not be negative or non number!");
        }
    }

    public void disconnect(Room room) {
        connectedRooms.remove(room);
        room.connectedRooms.remove(this);
    }
}



