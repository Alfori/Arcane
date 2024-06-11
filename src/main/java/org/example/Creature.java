package org.example;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
import java.util.logging.Logger;
public class Creature implements IObserver {
    private static final Logger LOGGER = Logger.getLogger(Creature.class.getName());
    private String name;
    private int health;
    private boolean alive = true;
    private Room currentRoom;
    public Creature(String name) {
        this.name = name;
        this.health = 8;
    }

    @Override
    public void update(String eventDesc) {
        LOGGER.info("Creature received event: " + eventDesc);
    }

    public void decreaseHealth(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            die();
        }
    }
    public void fight(Adventurer adventurer) {
        // Simple combat logic: deal a fixed amount of damage
        int damage = 2;
        adventurer.decreaseHealth(damage);
        LOGGER.info(this.name + " attacks " + adventurer.getName() + " for " + damage + " damage.");
    }
    public void die() {
        this.alive = false;
        logDeath();
        EventBus.getInstance().postMessage(EventType.DEATH, getName() + " has died.");
    }
    private void logDeath() {
        LOGGER.severe("Creature " + name + " has died.");
    }
    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return health > 0;
    }
    public boolean isInRoom(Room room) {
        return this.currentRoom != null && this.currentRoom.equals(room);
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth)
    {
        this.health = newHealth;
    }
}