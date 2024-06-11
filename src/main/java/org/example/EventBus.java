package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class EventBus 
{
    private static final Logger LOGGER = Logger.getLogger(EventBus.class.getName());
    private Map<EventType, List<IObserver>> observers = new HashMap<>();
    private static EventBus instance;

    private EventBus(){}

    public static synchronized EventBus getInstance()       // makes sure that only one instance of the EventBus is created, singleton pattern
    { 
        if (instance == null) 
        {
            instance = new EventBus();
        }

        return instance;
    }

    public void attach(IObserver observer, EventType eventType) // attaches an observer to a specific event type
    {
        List<IObserver> eventObservers = observers.get(eventType);      // gets list of observers for the event type

        if (eventObservers == null)     // if its null then create a new list
        {
            eventObservers = new ArrayList<>();             // create a new list
            observers.put(eventType, eventObservers);       // add list to map
        }

        eventObservers.add(observer);       // if not null then add observer to the list
    }
    

    public void postMessage(EventType eventType, String eventDescription)       // posts message to observers
        {
            List<IObserver> eventObservers = observers.get(eventType);       // gets list of observers for the event type
        if (eventObservers != null)     // makes sure its not empty
        {
            for (IObserver observer : eventObservers)       // if not empty then loop through the list and notify everyone thats in the list
            {
                observer.update(eventDescription);      // notify them
            }
        }

        List<IObserver> allObservers = observers.get(EventType.ALL);    // same butfor all observers
        if (allObservers != null)
        {
            for (IObserver observer : allObservers)
            {
                observer.update(eventDescription);
            }
        }
    }
}
