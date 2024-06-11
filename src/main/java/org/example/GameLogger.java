package org.example;

import java.util.logging.Logger;

public class GameLogger implements IObserver 
{
    private static final Logger LOGGER = Logger.getLogger(GameLogger.class.getName());      // MADE TO LOG EVENTS THAT ARE HAPPENING IN THE GAME


    @Override
    public void update(String eventDesc)
    {
        LOGGER.info(eventDesc); // logs the event that is happening in the game
    }

}