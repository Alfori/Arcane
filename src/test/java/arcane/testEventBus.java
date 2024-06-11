package arcane;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.EventType;
import org.example.EventBus;
import org.example.IObserver;
import java.util.ArrayList;
import java.util.List;

public class testEventBus
{
    private static class TestObserver implements IObserver 
    {
        private List<String> receivedMessages = new ArrayList<>();

        @Override
        public void update(String eventDescription) 
        {
            receivedMessages.add(eventDescription);
        }

        public List<String> getReceivedMessages() 
        {
            return receivedMessages;
        }
    }

    @Test
    public void testPostMessage() 
    {
        EventBus eventBus = EventBus.getInstance();
        TestObserver observer = new TestObserver();
        eventBus.attach(observer, EventType.DEATH);

        String testMessage = "Test Adventurer was killed";
        eventBus.postMessage(EventType.DEATH, testMessage);

        // Ensure the observer received the correct message
        assertTrue(observer.getReceivedMessages().contains(testMessage));
    }
}
