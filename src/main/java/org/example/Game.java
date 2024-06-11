package org.example;


import csci.ooad.layout.IMaze;
import csci.ooad.layout.IMazeObserver;
import csci.ooad.layout.IMazeSubject;
import csci.ooad.layout.MazeObserver;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.example.AudibleObserver;


public class Game implements IMazeSubject {
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
    private int turn = 0;
    private List<Room> rooms = new ArrayList<>();

    private List<IObserver> observers = new ArrayList<>();
    private List<IMazeObserver> mazeObservers = new ArrayList<>();
    private List<IObserver> generalObservers = new ArrayList<>();

    public void attachGeneralObserver(AudibleObserver observer) {
        generalObservers.add(observer);
    }

    // Method to notify general observers
    public void notifyGeneralObservers(String message) {
        for (IObserver observer : generalObservers) {
            observer.update(message);
        }
    }

    public void attach(IObserver observer) {
        observers.add(observer);
    }
    @Override
    public void attach(IMazeObserver observer) {
        mazeObservers.add(observer);
    }

    public void detach(IObserver observer) {
        observers.remove(observer);
    }


    public void notifyAllObservers(String message) {
        IMaze currentMaze = getMaze();

        for (IMazeObserver observer : mazeObservers) {
            observer.update(currentMaze, message);
        }
        for (IObserver observer : generalObservers) {
            observer.update(message);
        }
    }



    // Single constructor to initialize the game with rooms
    public Game(List<Room> initialRooms) {
        this.rooms = new ArrayList<>(initialRooms);
        // Example setup for MazeObserver, adjust as necessary
        IMazeObserver mazeObserver = MazeObserver.getNewBuilder("Arcane Game")
                .useRadialLayoutStrategy()
                .setDelayInSecondsAfterUpdate(3)
                .build();
        this.attach(mazeObserver);
    }
    public List<Room> getRooms() {
        return this.rooms;
    }



    @Override
    public csci.ooad.layout.IMaze getMaze() {
        return new MazeAdapter(this);
    }



    private boolean isGameOver = false;

    // builder class as an inner class of Maze class
    public static Builder newBuilder() {
        return new Builder();
    }


    public Room getRoomOfAdventurer(Adventurer adventurer) {
        for (Room room : rooms) {
            if (room.containsAdventurer(adventurer)) {
                return room;
            }
        }
        return null; // or throw an exception if the adventurer must always be in a room
    }

    public static class Builder {
        private List<Room> rooms = new ArrayList<>();
        public Builder create2x2Grid() {
            rooms.add(new Room("Room 1"));
            rooms.add(new Room("Room 2"));
            rooms.add(new Room("Room 3"));
            rooms.add(new Room("Room 4"));
            rooms.get(0).connect(rooms.get(1));
            rooms.get(0).connect(rooms.get(2));
            rooms.get(1).connect(rooms.get(0));
            rooms.get(1).connect(rooms.get(3));
            rooms.get(2).connect(rooms.get(0));
            rooms.get(2).connect(rooms.get(3));
            rooms.get(3).connect(rooms.get(1));
            rooms.get(3).connect(rooms.get(2));
            return this;
        }

        public Builder create3x3Grid() {
            rooms.clear();
            for (int i = 1; i <= 9; i++) {
                rooms.add(new Room("Room " + i));
            }
            for (int i = 0; i < 9; i++) {
                if (i % 3 != 2) {
                    rooms.get(i).connect(rooms.get(i + 1));
                }
                if (i % 3 != 0) {
                    rooms.get(i).connect(rooms.get(i - 1));
                }
                if (i < 6) {
                    rooms.get(i).connect(rooms.get(i + 3));
                }
                if (i > 2) {
                    rooms.get(i).connect(rooms.get(i - 3));
                }
            }
            return this;
        }

        public Builder createConnectedRooms(int n) {
            rooms.clear();
            for (int i = 1; i <= n; i++) {
                rooms.add(new Room("Room " + i));
            }
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    rooms.get(i).connect(rooms.get(j));
                    rooms.get(j).connect(rooms.get(i));
                }
            }
            return this;
        }


        public Game build() {
            validateMaze();
            return new Game(this.rooms); // Now valid with the new constructor
        }


        private void validateMaze() {
            if (rooms.size() > 1) {
                for (Room room : rooms) {
                    if (!room.isConnectedToAnyRoom()) {
                        throw new IllegalStateException("Maze is invalid!");
                    }
                }
            }
        }
        
        public Builder addCreatures(Creature[] creatures) {
            for (Creature creature : creatures) {
                int randomRoomIndex = new Random().nextInt(rooms.size());
                rooms.get(randomRoomIndex).addCreature(creature);
            }
            return this;
        }
        
        
        public Builder addAdventurers(Adventurer[] adventurers) {
            for (Adventurer adventurer : adventurers) {
                int randomRoomIndex = new Random().nextInt(rooms.size());
                rooms.get(randomRoomIndex).addAdventurer(adventurer);
            }
            return this;
        }
        
        public Builder addToRoom(String roomName, Object object) {
            for (Room room : rooms) {
                if (room.getName().equals(roomName)) {
                    // add the object to the found room
                    if (object instanceof Adventurer) {
                        room.addAdventurer((Adventurer) object);
                    } else if (object instanceof Creature) {
                        room.addCreature((Creature) object);
                    } else if (object instanceof Food) {
                        room.addFood((Food) object);
                    } else {
                        throw new IllegalArgumentException("Unsupported object type.");
                    }
                    return this;
                }
            }
            throw new IllegalArgumentException("Room with name '" + roomName + "' not found.");
        }

        public Builder createAndAddCowards(int count) {
            for (int i = 0; i < count; i++) {
                Adventurer newAdventurer = AdventurerFactory.createAdventurer("coward", "Coward " + (i + 1));
                addAdventurers(new Adventurer[]{newAdventurer});
            }
            return this;
        }

        public Builder createAndAddGluttons(int count) {
            for (int i = 0; i < count; i++) {
                Adventurer newAdventurer = AdventurerFactory.createAdventurer("glutton", "Glutton " + (i + 1));
                addAdventurers(new Adventurer[]{newAdventurer});
            }
            return this;
        }


        public Builder createAndAddKnights(int count) {
            for (int i = 0; i < count; i++) {
                Creature knight = CreatureFactory.createCreature("knight", "Knight " + (i + 1));
                addCreatures(new Creature[]{knight});
            }
            return this;
        }


        public Builder createAndAddDemons(int count) {
            for (int i = 0; i < count; i++) {
                addCreatures(new Creature[]{CreatureFactory.createCreature("demon", "Demon " + (i + 1))});

            }
            return this;
        }

        public Builder createAndAddFoodItems(String[][] foodOptions) {
            for (String[] foodOption : foodOptions) {
                String foodName = foodOption[0];
                int healthBoost = Integer.parseInt(foodOption[1]);
                for (Room room : rooms) {
                    Food food = FoodFactory.createFood(foodName, healthBoost);
                    room.addFood(food);
                }
            }
            return this;
        }
        

        public Builder createRoomNames(){
            for (int i = 0; i < rooms.size(); i++) {
                rooms.get(i).setName("Room " + (i + 1));
            }
            return this;
        }
    }

    // TO HERE

    private static class Move {
        private Adventurer adventurer;
        private Room oldRoom;
        private Room newRoom;

        public Move(Adventurer adventurer, Room oldRoom, Room newRoom) {
            this.adventurer = adventurer;
            this.oldRoom = oldRoom;
            this.newRoom = newRoom;
        }

        public Adventurer getAdventurer() {
            return adventurer;
        }

        public Room getOldRoom() {
            return oldRoom;
        }

        public Room getNewRoom() {
            return newRoom;
        }
    }

    // method to simulate game play
    public void play() {
        List<Move> moves = new ArrayList<>();
        Random random = new Random();
        while (!isGameOver) {
            LOGGER.info("ARCANE MAZE: turn " + turn);

            rooms.forEach(room -> room.getAdventurers().forEach(Adventurer::resetFoodConsumption));
            // move adventurers
            rooms.forEach(room -> room.getAdventurers().forEach(adventurer -> {
                if (random.nextBoolean()) {
                    Room nextRoom = rooms.get(random.nextInt(rooms.size()));
                    moves.add(new Move(adventurer, room, nextRoom));
                }
            }));

            moves.forEach(move -> move.getOldRoom().moveAdventurerTo(move.getNewRoom(), move.getAdventurer()));
            // adventurers eat food and encounters
            rooms.forEach(room -> {
                room.encounter(); // handle encounters
            });

            printMazeState();
            notifyAllObservers("Turn " + this.turn + " ended.");
            checkGameOver();
            turn++;
        }

        EventBus.getInstance().postMessage(EventType.GAME_OVER, "Game is over.");
        announceWinner();
    }

    public void checkGameOver() {
        long adventurersAlive = rooms.stream()
                .flatMap(room -> room.getAdventurers().stream())
                .filter(Adventurer::isAlive)
                .count();

        long creaturesAlive = rooms.stream()
                .flatMap(room -> room.getCreatures().stream())
                .filter(Creature::isAlive)
                .count();

        isGameOver = adventurersAlive == 0 || creaturesAlive == 0;
    }

    // checking if the game is over
    public boolean isGameOver() {
        return isGameOver;
    }

    public void initializeMaze() {
        // 3x3 grid of rooms
        for (int i = 0; i < 9; i++) {
            rooms.add(new Room("Room " + i));
        }

        // adventurers assigned randomly to rooms
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int randomRoomIndex = random.nextInt(9);
            rooms.get(randomRoomIndex).setAdventurer(new Adventurer("Adventurer " + i));
        }

        // creatures assigned randomly to rooms
        for (int i = 0; i < 5; i++) {
            int randomRoomIndex = random.nextInt(9);
            rooms.get(randomRoomIndex).setCreature(new Creature("Creature " + i));
        }

        String[][] foodOptions = {
                {"Cake", "5"},
                {"Fries", "3"},
                {"Steak", "10"},
                {"Salad", "2"},
                {"Pasta", "8"},
                {"Cookie", "4"},
                {"Banana", "6"},
                {"Apple", "5"}
        };
        for (int i = 0; i < 10; i++) {
            int randomRoomIndex = random.nextInt(9);
            int randomFoodIndex = random.nextInt(foodOptions.length);
            String foodName = foodOptions[randomFoodIndex][0];
            int healthBoost = Integer.parseInt(foodOptions[randomFoodIndex][1]);
            rooms.get(randomRoomIndex).addFood(new Food(foodName, healthBoost));
        }

        EventBus eventBus = EventBus.getInstance();
        AudibleObserver audibleObserver = new AudibleObserver(5);
        eventBus.attach(audibleObserver, EventType.TURN_ENDED);
        eventBus.attach(audibleObserver, EventType.GAME_OVER);
        eventBus.attach(audibleObserver, EventType.DEATH);
        eventBus.attach(audibleObserver, EventType.TURN_ENDED);
        eventBus.attach(audibleObserver, EventType.ATE_SOMETHING);
        eventBus.attach(audibleObserver, EventType.FIGHT_OUTCOME);
    }


    public void printMazeState() {
        StringBuilder sb = new StringBuilder();
        sb.append("ARCANE MAZE: turn ").append(turn).append("\n");
        for (Room room : rooms) {
            sb.append("\t").append(room.getName()).append(":\n\t\tAdventurers: ");

            if (!room.getAdventurers().isEmpty()) {
                room.getAdventurers().forEach(adventurer ->
                        sb.append(adventurer.getName()).append("(health: ").append(adventurer.getHealth()).append(") "));
            } else {
                sb.append("None");
            }
            sb.append("\n\t\tCreatures: ");

            if (!room.getCreatures().isEmpty()) {
                room.getCreatures().forEach(creature ->
                        sb.append(creature.getName()).append("(health: ").append(creature.getHealth()).append(") "));
            } else {
                sb.append("None");
            }

            sb.append("\n\t\tFood: ");
            if (!room.getFoodItems().isEmpty()) {
                room.getFoodItems().forEach(food -> sb.append(food.getName()).append(" "));
            } else {
                sb.append("None");
            }
            sb.append("\n\n");
        }

        LOGGER.info(sb.toString());
    }

    public interface IObservable {
        void attach(IObserver observer);
        void detach(IObserver observer);
        void notifyObservers(String message);
    }

    // Observer interface
    public interface IObserver {
        void update(String message);
    }

    public static void main(String[] args) {
        Game game = new Game(new ArrayList<Room>());
        EventBus.getInstance().postMessage(EventType.GAME_START, "Game has began.");

        game.initializeMaze();
        AudibleObserver audibleObserver = new AudibleObserver(5);
        game.attachGeneralObserver(audibleObserver);

        game.notifyObservers("Game Started");
        game.play();

        LOGGER.info("Game Over: " + game.isGameOver());    }
    public void reset() {
        isGameOver = false;
    }
    public void announceWinner() {
        long adventurersAlive = rooms.stream()
                .flatMap(room -> room.getAdventurers().stream())
                .filter(Adventurer::isAlive)
                .count();

        long creaturesAlive = rooms.stream()
                .flatMap(room -> room.getCreatures().stream())
                .filter(Creature::isAlive)
                .count();

        if (adventurersAlive > 0 && creaturesAlive == 0) {
            LOGGER.info("The adventurers have won the game!");
        } else if (creaturesAlive > 0 && adventurersAlive == 0) {
            LOGGER.info("The creatures have won the game!");
        } else {
            LOGGER.info("The game ended in a draw.");
        }
    }

    public void toggleGameState() {
        isGameOver = !isGameOver;
    }

}