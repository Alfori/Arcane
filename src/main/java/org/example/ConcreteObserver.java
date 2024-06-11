package org.example;

import org.example.AudibleObserver;
import java.util.logging.Logger;

public class ConcreteObserver implements AudibleObserver.IObserver {
    @Override
    public void update(String eventDescription) {
        // Implementation code here. This could be logging, updating UI, etc.
        Logger logger = Logger.getLogger(ConcreteObserver.class.getName());
        logger.info("Event occurred: " + eventDescription);
    }
}