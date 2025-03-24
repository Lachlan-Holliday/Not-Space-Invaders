package game.core;

import game.ui.ObjectGraphic;

public class Bullet extends ObjectWithPosition {

    public Bullet(int x, int y) {
        super(x, y);
    }

    public void tick(int tick) {
        y -= 1;
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     * An Asteroid is represented by the following:
     *      - The text representation is "🔺".
     *      - The image path is "assets/bullet.png"
     *
     */
    public ObjectGraphic render() {
        return new ObjectGraphic("🔺", "assets/bullet.png");
    }


}
