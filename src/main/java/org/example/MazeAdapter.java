package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import csci.ooad.layout.IMazeObserver;
import csci.ooad.layout.IMazeSubject;
import csci.ooad.layout.MazeObserver;
import csci.ooad.layout.IMaze;

public class MazeAdapter implements IMaze {
    private final Game game;

    public MazeAdapter(Game game) {
        this.game = game;
    }


    @Override
    public List<String> getRooms() {
        return game.getRooms().stream()
                .map(Room::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getNeighborsOf(String roomName) 
    {
        Room room = game.getRooms().stream()
                .filter(r -> r.getName().equals(roomName))
                .findFirst()
                .orElse(null);
        if (room != null) 
        {
            return room.getConnectedRooms().stream()
                    .map(Room::getName)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<String> getContents(String roomName) {
        Room room = game.getRooms().stream()
                .filter(r -> r.getName().equals(roomName))
                .findFirst()
                .orElse(null);
                
        if (room != null)
        {
            List<String> contents = new ArrayList<>();

            room.getAdventurers().forEach(adventurer -> contents.add(adventurer.getName() + "(health: " + adventurer.getHealth() + ")"));

            room.getCreatures().forEach(creature -> contents.add(creature.getName() + "(health: " + creature.getHealth() + ")"));
            
            room.getFoodItems().forEach(food -> contents.add(food.getName()));

            return contents;
        }
        return List.of();
    }
}
