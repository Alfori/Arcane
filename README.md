## Homework 5: Adding the Observer, Adaptor, and Singleton Patterns and BDD 


### Overview
In this update, we're enhancing ARCANE by integrating design patterns such as Observer, Adaptor, and Singleton. It also incorporates Behavior-Driven Development (BDD) to ensure robust game functionality.
Team Members:

1. Suman Upreti
2. Sultan Alfoory
3. Sunyojita Rattu

## Directory Structure
The project follows a correct directory structure with the following key directories:
- `src/main/java/arcane/org.example` for main game classes.
- `src/test/java/arcane` for test classes ensuring 80% method coverage.(Code_coverage_homework_5.png)

### Testing
Unit tests have been written with 81% method coverage, ensuring that all functionality is verified. This ensures that our code goes under rigorous testing and which gives reliability and stability in running and deploying our game.

### Game Mechanics
Audible Observer: Integrated an audible observer that vocalizes significant game events, enhancing the interactive experience.
Game Display Observer: Added a graphical user interface to visually represent the maze and its dynamics, improving user engagement.

### Behavior-Driven Development (BDD)
Incorporated BDD to articulate game functionalities through human-readable descriptions, ensuring alignment between developed features and specified behaviors.

### Design Patterns
Observer and Singleton: Implemented an Event Bus as a singleton to manage game events, ensuring a single instance controls event flow throughout the game. Observers are notified of specific events like creature deaths, turn completions, and game conclusions.

Adaptor: Wrapped our maze structure with a MazeAdaptor to interface with an external maze drawing library, allowing a graphical representation of game state changes.





### Java Version

Runtime version: 17.0.9+7-b1087.11 x86_64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
