package org.example;

import java.util.logging.Logger;
import csci.ooad.layout.IMaze;
import csci.ooad.layout.IMazeObserver;
import csci.ooad.layout.IMazeSubject;
import csci.ooad.layout.MazeObserver;
// import java.util.List;
import java.util.logging.Logger;

public class AudibleObserver implements IObserver, Game.IObserver
{
    private static final Logger LOGGER = Logger.getLogger(AudibleObserver.class.getName());     // logger fro the class
    private final Integer secondDelay;      // amount of delay in seconds

    public AudibleObserver(Integer secondDelay)      // user can set the delay they want in seconds
    {
        this.secondDelay = secondDelay;
    }


    @Override
    public void update(String eventDisc) {
        try {
            // For macOS, use the "say" command to speak the event description
            String[] command = {"say", eventDisc};

            // Execute the command
            Runtime.getRuntime().exec(command);

            // Wait for the specified delay after speaking the message
            if (secondDelay != null) {
                Thread.sleep(secondDelay * 1000); // Convert seconds to milliseconds
            }
        } catch (Exception e) {
            LOGGER.severe("Error occurred while trying to speak the event description: " + e.getMessage()); // Log any errors as severe
        }
    }

    public interface IObserver 
    {
        void update(String eventDescription);
    }

    public Integer getsecondDelay()
    {
        return secondDelay;      // simple getter for the secondDelay
    }
}