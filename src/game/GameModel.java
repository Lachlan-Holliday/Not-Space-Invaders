package game;


import game.core.*;
import game.utility.Logger;
import game.core.SpaceObject;

import java.util.*;

/**
 * Represents the game information and state. Stores and manipulates the game state.
 */
public class GameModel {
    public static final int GAME_HEIGHT = 20;
    public static final int GAME_WIDTH = 10;
    public static final int START_SPAWN_RATE = 2; // spawn rate (percentage chance per tick)
    public static final int SPAWN_RATE_INCREASE = 5; // Increase spawn rate by 5% per level
    public static final int START_LEVEL = 1; // Starting level value
    public static final int SCORE_THRESHOLD = 100; // Score threshold for leveling
    public static final int ASTEROID_DAMAGE = 10; // The amount of damage an asteroid deals
    public static final int ENEMY_DAMAGE = 20; // The amount of damage an enemy deals
    public static final double ENEMY_SPAWN_RATE = 0.5; // Percentage of asteroid spawn chance
    public static final double POWER_UP_SPAWN_RATE = 0.25; // Percentage of asteroid spawn chance

    private final Random random = new Random(); // ONLY USED IN this.spawnObjects()

    private Logger logger;
    private ArrayList<SpaceObject> allSpaceObjects;
    private int level;

    private int spawnRate;

    private Ship ship;

    /**
     * Models a game, storing and modifying data relevant to the game.
     * Logger argument should be a method reference to a .log method such as the UI.log method.
     * Example: Model gameModel = new GameModel(ui::log)
     * - Instantiates an empty list for storing all SpaceObjects the model needs to track.
     * - Instantiates the game level with the starting level value.
     * - Instantiates the game spawn rate with the starting spawn rate.
     * - Instantiates a new ship.
     * - Stores reference to the given logger.
     *
     * @param logger a functional interface for passing information between classes.
     */
    public GameModel (Logger logger) {
        this.allSpaceObjects = new ArrayList<>();
        this.level = START_LEVEL;
        this.spawnRate = START_SPAWN_RATE;
        this.logger = logger;
        Ship ship = new Ship();
        logger.log("GameModel initialized: Level " + level + ", SpawnRate " + spawnRate);

    }

    public List<SpaceObject> getSpaceObjects() {
        return allSpaceObjects;
    }

    public void addObject(SpaceObject object) {
        logger.log("Added object");
        allSpaceObjects.add(object);
    }

    public void updateGame(int tick) {
        List<SpaceObject> toRemove = new ArrayList<>();

        for (SpaceObject x : allSpaceObjects) {
            if ((x.getY() + 1) > GAME_HEIGHT) {
                toRemove.add(x);
            } else {
                x.tick(tick);
            }
        }

        allSpaceObjects.removeAll(toRemove);
    }

    public void checkCollisions() {
        List<SpaceObject> removeLater = new ArrayList<>();
        for (SpaceObject obj : allSpaceObjects) {
            if (obj instanceof Ship) {
                continue;
            }
            if (obj.getX() == ship.getX() && obj.getY() == ship.getY()) {
                if (obj instanceof PowerUp) {
                    logger.log("Power-up collected: " + obj.render());
                    ((PowerUp) obj).applyEffect(ship);
                }
            }





        }
    }




    /**
     * Sets the seed of the Random instance created in the constructor using .setSeed().
     *
     * This method should NEVER be called.
     *
     * @param seed to be set for the Random instance
     * @provided
     */
    public void setRandomSeed(int seed) {
        this.random.setSeed(seed);
    }
}
