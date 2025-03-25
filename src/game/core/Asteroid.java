package game.core;

import game.ui.ObjectGraphic;


/**
 * Asteroid class is a child of DescendingEnemy.
 * The class allows you to render and construct a new Asteroid.
 */
public class Asteroid extends DescendingEnemy {

    /**
     * Initializes a new Asteroid instance with coordinates x and y.
     *
     * @param x - the x Coordinate.
     * @param y - the y Coordinate.
     */
    public Asteroid(int x, int y) {
        super(x, y);
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * An Asteroid is represented by the following:
     *      - The text representation is "🌑".
     *      - The image path is "assets/asteroid.png"
     *
     * @return ObjectGraphic - the appropriate new ObjectGraphic
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🌑", "assets/asteroid.png");
    }
}
