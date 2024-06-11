package org.example;
import java.util.Collections;
import java.util.logging.Logger;
import org.example.IMaze;
import java.util.List;
import java.util.stream.Collectors;

public class Adaptor implements IMaze {
    private Room room;

    public Adaptor(Room room) {
        this.room = room;
    }

    @Override
    public List<String> getRooms() {
        return Collections.singletonList(room.getName());
    }

    @Override
    public List<String> getNeighborsOf(String roomName) {
        Room targetRoom = findRoomByName(roomName);
        if (targetRoom != null) {
            return Collections.unmodifiableList(targetRoom.getConnectedRooms().stream()
                    .map(Room::getName)
                    .collect(Collectors.toList()));
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getContents(String roomName) {
        Room targetRoom = findRoomByName(roomName);
        if (targetRoom != null) {
            List<String> contents = targetRoom.getAdventurers().stream()
                    .map(Adventurer::getName)
                    .collect(Collectors.toList());
            contents.addAll(targetRoom.getCreatures().stream()
                    .map(Creature::getName)
                    .collect(Collectors.toList()));
            contents.addAll(targetRoom.getFoodItems().stream()
                    .map(Food::getName)
                    .collect(Collectors.toList()));
            return Collections.unmodifiableList(contents);
        }
        return Collections.emptyList();
    }


    private Room findRoomByName(String roomName) {
        if (room.getName().equals(roomName)) {
            return room;
        }
        return null;
    }
}
