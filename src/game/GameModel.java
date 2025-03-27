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
    public GameModel(Logger logger) {
        this.allSpaceObjects = new ArrayList<>();
        this.level = START_LEVEL;
        this.spawnRate = START_SPAWN_RATE;
        this.logger = logger;
        this.ship = new Ship();
        addObject(ship);
    }

    /**
     * Returns a list of all SpaceObjects in the game.
     *
     * @return a list of all spaceObjects.
     */
    public List<SpaceObject> getSpaceObjects() {
        return new ArrayList<>(allSpaceObjects);
    }

    /**
     * Adds a SpaceObject to the game.
     * Objects are considered part of the game only when they are tracked by the model.
     *
     * @param object - the SpaceObject to be added to the game.
     * @requires object != null.
     */
    public void addObject(SpaceObject object) {
        List<SpaceObject> copy = new ArrayList<>(allSpaceObjects);
        copy.add(object); // ensures no rendering issues
        allSpaceObjects = new ArrayList<>(copy);
    }

    /**
     * Updates the game state by moving all objects and then removing off-screen objects.
     *
     * Objects should be moved by calling .tick(tick) on each object.
     * Objects are considered off-screen if they are at y-coordinate > GAME_HEIGHT.
     *
     * @param tick - the tick value passed through to the objects tick() method.
     */
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
        List<SpaceObject> newList = new ArrayList<>(); //ensures no rendering issues
        for (SpaceObject obj : allSpaceObjects) {
            if (!toRemove.contains(obj)) {
                newList.add(obj);
            }
        }

        allSpaceObjects = new ArrayList<>(newList);
    }

    /**
     * Detects and handles collisions between spaceObjects (Ship and Bullet collisions).
     * Objects are considered to be colliding if they share x and y coordinates.
     *
     * First checks ship collision: - If the ship is colliding with a powerup, apply the effect, and .log("Power-up collected: " + obj.render())
     * - If the ship is colliding with an asteroid, take the appropriate damage, and .log("Hit by asteroid! Health reduced by " + ASTEROID_DAMAGE + ".")
     * - If the ship is colliding with an enemy, take the appropriate damage, and .log("Hit by enemy! Health reduced by " + ENEMY_DAMAGE + ".")
     * For any collisions with the ship, the colliding object should be removed.
     *
     * Then check bullet collision:
     * If a bullet collides with an enemy, remove both the enemy and the bullet. No logging required.
     */
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
                if (obj2 instanceof Enemy) { // Now only checking enemies, ignoring asteroids
                    if (obj1.getX() == obj2.getX() && obj1.getY() == obj2.getY()) {
                        removeLater.add(obj1); // Remove bullet
                        removeLater.add(obj2); // Remove enemy
                        logger.log("Bullet hit enemy! Removed.");
                    }
                }
            }
        }
        allSpaceObjects.removeAll(removeLater);
    }

    /**
     * Returns the ship instance in the game.
     *
     * @return the current ship instance.
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Fires a bullet from the ship's current position.
     *
     * Creates a new bullet at the coordinates the ship occupies.
     * Logs "Core.Bullet fired!"
     */
    public void fireBullet() {
        addObject(new Bullet(ship.getX(), ship.getY()));
        logger.log("Core.Bullet fired!");
    }

    /**
     * Returns the current level.
     *
     * @return the current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * If level progression requirements are satisfied, levels up the game by increasing the spawn rate and level number.
     *
     * To level up, the score must not be less than the current level multiplied by the score threshold.
     * To increase the level the spawn rate should increase by SPAWN_RATE_INCREASE, and the level number should increase by 1.
     * If the level is increased, log the following: "Level Up! Welcome to Level {new level}. Spawn rate increased to {new spawn rate}%."
     */
    public void levelUp() {
        if (!(ship.getScore() < level * SCORE_THRESHOLD)) {
            spawnRate += SPAWN_RATE_INCREASE;
            level += 1;
            logger.log("Level Up! Welcome to Level "
                    + level
                    + " Spawn rate increased to "
                    + spawnRate
                    + "%");

        }
    }

    /**
     * Spawns new objects (asteroids, enemies, and power-ups) at random positions.
     * Uses this.random to make EXACTLY 6 calls to random.nextInt() and 1 random.nextBoolean.
     *
     * Random calls should be in the following order:
     * 1. Check if an asteroid should spawn (random.nextInt(100) < spawnRate)
     * 2. If spawning an asteroid, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
     * 3. Check if an enemy should spawn (random.nextInt(100) < spawnRate * ENEMY_SPAWN_RATE)
     * 4. If spawning an enemy, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
     * 5. Check if a power-up should spawn (random.nextInt(100) < spawnRate * POWER_UP_SPAWN_RATE)
     * 6. If spawning a power-up, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
     * 7. If spawning a power-up, spawn a ShieldPowerUp if random.nextBoolean(), else a HealthPowerUp.
     *
     * Failure to match random calls correctly will result in failed tests.
     *
     * Objects spawn at y = 0 (top of the screen).
     * Objects may not spawn if there is a ship at the intended spawn location.
     * This should NOT impact calls to random.
     */
    public void spawnObjects() {
        // Asteroid
        if (random.nextInt(100) < spawnRate) {
            int x = random.nextInt(GAME_WIDTH);
            if (!(ship.getX() == x && ship.getY() == 0)) {
                addObject(new Asteroid(x, 0));
            }
        } else {
            random.nextInt(GAME_WIDTH);
        }

        // Enemy
        if (random.nextInt(100) < spawnRate * ENEMY_SPAWN_RATE) {
            int x = random.nextInt(GAME_WIDTH);
            if (!(ship.getX() == x && ship.getY() == 0)) {
                addObject(new Enemy(x, 0));
            }
        } else {
            random.nextInt(GAME_WIDTH);
        }

        // Power‑Up
        if (random.nextInt(100) < spawnRate * POWER_UP_SPAWN_RATE) {
            int x = random.nextInt(GAME_WIDTH);
            PowerUp powerUp = random.nextBoolean()
                    ? new ShieldPowerUp(x, 0)
                    : new HealthPowerUp(x, 0);
            if (!(ship.getX() == x && ship.getY() == 0)) {
                addObject(powerUp);
            }
        } else {
            random.nextInt(GAME_WIDTH);
            random.nextBoolean();
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
