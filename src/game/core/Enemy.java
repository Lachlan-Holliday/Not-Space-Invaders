package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents an enemy UFO in the game.
 */
public class Enemy extends DescendingEnemy {

    /**
     * Creates an enemy at the given coordinate.
     *
     * @param x - the x Coordinate.
     * @param y - the y Coordinate.
     */
    public Enemy(int x, int y) {
        super(x, y);
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * A Bullet is represented by the following:
     *      - The text representation is "👾".
     *      - The image path is "assets/enemy.png"
     *
     * @return ObjectGraphic - the appropriate new ObjectGraphic
     */
    public ObjectGraphic render() {
        return new ObjectGraphic("👾", "assets/enemy.png");
    }
}
