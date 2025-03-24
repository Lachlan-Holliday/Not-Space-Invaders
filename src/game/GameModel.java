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
        this.ship = new Ship();

    }

    public List<SpaceObject> getSpaceObjects() {
        return new ArrayList<>(allSpaceObjects);
    }

    public void addObject(SpaceObject object) {
        List<SpaceObject> copy = new ArrayList<>(allSpaceObjects);
        copy.add(object);
        allSpaceObjects = new ArrayList<>(copy);
    }

    public void updateGame(int tick) {
        List<SpaceObject> toRemove = new ArrayList<>();
        List<SpaceObject> current = new ArrayList<>(allSpaceObjects);


        for (SpaceObject x : current) {
            if ((x.getY() + 1) > GAME_HEIGHT) {
                toRemove.add(x);
            } else {
                x.tick(tick);
            }
        }
        List<SpaceObject> newList = new ArrayList<>();
        for (SpaceObject obj : allSpaceObjects) {
            if (!toRemove.contains(obj)) {
                newList.add(obj);
            }
        }

        allSpaceObjects = new ArrayList<>(newList);
    }


    public void checkCollisions() {
        List<SpaceObject> allSpaceObjectsCopy = new ArrayList<>(allSpaceObjects);
        List<SpaceObject> removeLater = new ArrayList<>();
        for (SpaceObject obj : allSpaceObjectsCopy) {
            if (obj instanceof Ship) { //CHECK IF WE CAN DELETE LATER (Ship may not be in array)
                continue;
            }
            if (obj.getX() == ship.getX() && obj.getY() == ship.getY()) {
                switch (obj) {
                    case PowerUp powerUp -> {
                        logger.log("Power-up collected: " + obj.render());
                        powerUp.applyEffect(ship);
                        removeLater.add(obj);
                    }
                    case Asteroid asteroid -> {
                        logger.log("Hit by asteroid! Health reduced by " + ASTEROID_DAMAGE + ".");
                        ship.takeDamage(ASTEROID_DAMAGE);
                        removeLater.add(obj);
                    }
                    case Enemy enemy -> {
                        logger.log("Hit by enemy! Health reduced by " + ENEMY_DAMAGE + ".");
                        ship.takeDamage(ENEMY_DAMAGE);
                        removeLater.add(obj);
                    }
                    default -> {
                    }
                }
            }
        }
        for (SpaceObject obj1 : allSpaceObjectsCopy) {
            if (!(obj1 instanceof Bullet)) {
                continue;
            }

            for (SpaceObject obj2 : allSpaceObjectsCopy) {
                if (!(obj2 instanceof Enemy)) {
                    continue;
                }

                if (obj1.getX() == obj2.getX() && obj1.getY() == obj2.getY()) {
                    removeLater.add(obj1);
                    removeLater.add(obj2);
                }
            }
        }
        allSpaceObjects.removeAll(removeLater);
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
