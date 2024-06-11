package org.example;
import java.util.Collections;
import java.util.logging.Logger;
import org.example.IMaze;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class ObserverMock implements IObserver {
    public List<String> events = new ArrayList<>();
    Logger logger = Logger.getLogger(ObserverMock.class.getName());

    @Override
    public void update(String eventDescription) {
        
        events.add(eventDescription);
        logger.info(eventDescription);
    }
}