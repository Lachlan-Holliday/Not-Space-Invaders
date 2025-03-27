package game.core;

import game.ui.ObjectGraphic;

/**
 *  Represents the player's ship.
 */
public class Ship extends Controllable {
    private int health;

    private int score;

    /**
     * Constructs a Ship with default position and health.
     * By default, a ship should be at position x = 5 and y = 10, with 100 points of health.
     */
    public Ship() {
        super(5, 10);
        this.health = 100;
        this.score = 0;
    }

    /**
     * Constructs a Ship with the specified position and health. Also initialises score to be 0.
     *
     * @param x - the initial x coordinate.
     * @param y - the initial y coordinate.
     * @param health - the initial health of the ship.
     */
    public Ship(int x, int y, int health) {
        super(x, y);
        this.health = health;
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * A Bullet is represented by the following:
     *      - The text representation is  "🚀".
     *      - The image path is "assets/ship.png".
     *
     * @return ObjectGraphic - the appropriate new ObjectGraphic
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🚀", "assets/ship.png");
    }

    /**
     * As Ships have no tick-dependent behaviour, this method should be left blank.
     *
     * @param tick - the given game tick.
     */
    @Override
    public void tick(int tick) {}

    /**
     * Reduces the ship's health by the specified damage amount.
     * A ship's health can never fall below 0.
     *
     * @param damage - the amount of damage taken.
     */

    public void takeDamage(int damage) {
        health = Math.min(health - damage, 0);
    }

    /**
     * Heals the ship by the specified amount.
     * A ship's health can never rise above 100.
     *
     * @param amount  - the amount of health restored.
     */
    public void heal(int amount) {
        health = Math.max(health + amount, 100);
    }

    /**
     * Returns the current health of the ship.
     *
     * @return the current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the current score of the ship.
     *
     * @return the current score.
     */

    public int getScore() {
        return score;
    }

    /**
     * Adds points to the ship's score.
     *
     * @param points - the points to add.
     */
    public void addScore(int points) {
        score += points;
    }

}