package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents a shield power-up in the game.
 */
public class ShieldPowerUp extends PowerUp {

    /**
     * Creates a new ShieldPowerUp with the given coordinates.
     *
     * @param x - the x Coordinate.
     * @param y - the y Coordinate.
     */
    public ShieldPowerUp(int x, int y) {
        super(x, y);
    }

    /**
     * Applies the shield effect to the ship, increasing the score by 50.
     * Sends "Shield activated! Score increased by 50."
     *
     * @param ship  -  the ship to apply the effect to.
     */
    @Override
    public void applyEffect(Ship ship) {
        ship.addScore(50);
        System.out.println("Shield activated! Score increased by 50.");
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * A Bullet is represented by the following:
     *      - The text representation is  "💠".
     *      - The image path is "assets/shield.png"
     *
     * @return ObjectGraphic - the appropriate new ObjectGraphic
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("💠", "assets/shield.png");
    }
}