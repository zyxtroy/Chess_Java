# Chess

This is a simple graphical chess game implemented in Java.

## Features

- Graphical user interface for playing chess
- User can choose between playing with a friend or AI
- The AI is implmented using Minimax algorithm, with 3 levels of difficulity. The **hard** level requires lots of computations. Depending on the computer you might encouter latency for the AI to make a move
- Once the game start, There are two buttons on the top of game windiw: **Game** and **File**
- Click on the **Game** button, user can create a new 1-player or 2-player game, undo a move, or close the game
- Click on the **File** button, user can **Save** or **Load** a game. Saved files can be found in the **SAVES** folder inside the project




## Compiling and Running the Game

1. Navigate to the directory containing the source files.

2. Compile the Java files:

   ```bash
   javac -d bin src/*.java

3. Run the `Main` class in IntelliJ or use the command:

    ```bash
    java -cp bin Main

This will start the chess game.
