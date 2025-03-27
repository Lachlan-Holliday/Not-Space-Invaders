package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents a health power-up in the game.
 */
public class HealthPowerUp extends PowerUp {

    /**
     * Creates a health power-up at the given coordinates
     *
     * @param x - the x Coordinate.
     * @param y - the y Coordinate.
     */
    public HealthPowerUp(int x, int y) {
        super(x, y);
    }

    /**
     * Applies the health effect to the ship, healing it for 20 health.
     * Sends "Health restored by 20!" to standard output (using System.out.println()).
     *
     * @param ship  -  the ship to apply the effect to.
     */
    @Override
    public void applyEffect(Ship ship) {
        ship.heal(20);
        System.out.println("Health restored by 20!");
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * A Bullet is represented by the following:
     *      - The text representation is "❤️".
     *      - The image path is "assets/health.png"
     *
     * @return ObjectGraphic - the appropriate new ObjectGraphic
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("❤️", "assets/health.png");
    }
}