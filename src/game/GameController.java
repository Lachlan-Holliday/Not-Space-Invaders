package game;

import game.core.Bullet;
import game.core.Enemy;
import game.core.SpaceObject;
import game.GameModel;
import game.ui.UI;
import game.utility.Direction;
import game.utility.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The Controller handling the game flow and interactions.
 *
 * Holds references to the UI and the Model, so it can pass information and references back and forth as necessary.
 * Manages changes to the game, which are stored in the Model, and displayed by the UI.
 */
public class GameController {
    private long startTime;
    private UI ui;
    private GameModel model;

    /**
     * Initializes the game controller with the given UI and Model.
     * Stores the ui, model and start time.
     * The start time System.currentTimeMillis() should be stored as a long.
     *
     * @param ui the UI used to draw the Game
     * @param model the model used to maintain game information
     * @provided
     */
    public GameController(UI ui, GameModel model) {
        this.ui = ui;
        this.model = model;
        this.startTime = System.currentTimeMillis(); // Start the timer
    }

    /**
     * Initializes the game controller with the given UI and a new GameModel (taking ui::log as the logger).
     * This constructor should call the other constructor using the "this()" keyword.
     *
     * @param ui the UI used to draw the Game
     * @provided
     */
    public GameController(UI ui) {
        this(ui, new GameModel(ui::log));
    }

    /**
     * Renders the current game state, including score, health, and ship position.
     * - Uses ui.setStat() to update the "Score", "Health" and "Level" appropriately with information from the model.
     * - Uses ui.setStat() to update "Time Survived" with (System.currentTimeMillis() - startTime) / 1000 + " seconds"
     * - Renders all SpaceObjects (including the Ship) using a single call to ui.render().
     *
     */
    public void renderGame() {
        ui.setStat("Score", "0"); //may have to make this the ships score
        ui.setStat("Health", "100");
        ui.setStat("level", "1");
        ui.setStat("Time Remaining", (System.currentTimeMillis() - startTime) / 1000 + " seconds");
        ui.render(model.getSpaceObjects());
    }


    /**
     * Starts the main game loop.
     *
     * Passes onTick and handlePlayerInput to ui.onStep and ui.onKey respectively.
     * @provided
     */
    public void startGame() {
        ui.onStep(this::onTick);
        ui.onKey(this::handlePlayerInput); // Registers key input
    }

    /**
     * Uses the provided tick to call and advance the following:
     *      - A call to renderGame() to draw the current state of the game.
     *      - A call to model.updateGame(tick) to advance the game by the given tick.
     *      - A call to model.checkCollisions() to handle game interactions.
     *      - A call to model.spawnObjects() to handle object creation.
     *      - A call to model.levelUp() to check and handle leveling.
     *
     * @param tick the provided tick
     * @provided
     */
    public void onTick(int tick) {
        renderGame(); // Update Visual
        model.updateGame(tick); // Update GameObjects
        model.checkCollisions(); // Check for Collisions
        model.spawnObjects(); // Handles new spawns
        model.levelUp(); // Level up when score threshold is met
    }

    /**
     *  Calls ui.pause() to pause the game until the method is called again.
     *  Logs "Game paused." after calling ui.pause().
     */
    public void pauseGame() {
        ui.pause();
        ui.log("Game paused.");
    }

    /**
     * Handles player input and performs actions such as moving the ship or firing bullets.
     * Uppercase and lowercase inputs should be treated identically:
     * - For movement keys "W", "A", "S" and "D" the ship should be moved up, left, down, or right respectively The following should also be logged:
     * "Core.Ship moved to (" + model.getShip().getX() + ", " + model.getShip().getY() + ")"
     * - For input "F" the fireBullet() method of the model should be called.
     * - For input "P" the pauseGame() method should be called.
     * - For all other inputs, the following should be logged: "Invalid input. Use W, A, S, D, F, or P."
     *
     * @param input - the player's input command.
     */
    public void handlePlayerInput(String input) {
        String[] validInput = new String[]{"W", "A", "S", "D", "F", "P"};
        if (Arrays.asList(validInput).contains(input.toUpperCase())) {
            switch (input.toUpperCase()) {
                case "W" -> {
                    model.getShip().move(Direction.valueOf("UP"));
                    ui.log("Core.Ship moved to (\" + model.getShip().getX() + \", \" + model.getShip().getY() + \")");
                }
                case "A" -> {
                    model.getShip().move(Direction.valueOf("LEFT"));
                    ui.log("Core.Ship moved to (\" + model.getShip().getX() + \", \" + model.getShip().getY() + \")");
                }
                case "S" -> {
                    model.getShip().move(Direction.valueOf("DOWN"));
                    ui.log("Core.Ship moved to (\" + model.getShip().getX() + \", \" + model.getShip().getY() + \")");
                }
                case "D" -> {
                    model.getShip().move(Direction.valueOf("RIGHT"));
                    ui.log("Core.Ship moved to (\" + model.getShip().getX() + \", \" + model.getShip().getY() + \")");
                }
                case "F" -> {
                    model.fireBullet();
                }
                case "P" -> {
                    pauseGame();
                }
            }
        } else {
            ui.log("Invalid input. Use W, A, S, D, F, or P.");
        }
    }

}

