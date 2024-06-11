package org.example;
import java.util.List;
import csci.ooad.layout.IMazeObserver;
import csci.ooad.layout.IMazeSubject;
import csci.ooad.layout.MazeObserver;

public interface IMaze {
    List<String> getRooms();
    List<String> getNeighborsOf(String roomName);
    List<String> getContents(String roomName);
}
