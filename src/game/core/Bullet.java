package game.core;

import game.ui.ObjectGraphic;

/**
 * Bullet class is a child of ObjectWithPosition.
 * The class allows you to render and construct a new Bullet.
 */
public class Bullet extends ObjectWithPosition {

    /**
     * Initializes a new Bullet instance with coordinates x and y.
     *
     * @param x - the x Coordinate.
     * @param y - the y Coordinate.
     */
    public Bullet(int x, int y) {
        super(x, y);
    }

    /**
     * Moves Bullet upwards by one, regardless of what the provided game tick is.
     *
     * @param tick - the given game tick.
     */
    public void tick(int tick) {
        y -= 1;
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * A Bullet is represented by the following:
     *      - The text representation is "🔺".
     *      - The image path is "assets/bullet.png"
     *
     * @return ObjectGraphic - the appropriate new ObjectGraphic
     */
    public ObjectGraphic render() {
        return new ObjectGraphic("🔺", "assets/bullet.png");
    }


}
