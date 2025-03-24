package game.core;

import game.ui.ObjectGraphic;

public class Bullet extends ObjectWithPosition {

    public Bullet(int x, int y) {
        super(x, y);
    }

    public void tick(int tick) {
        y -= 1;
    }


    public ObjectGraphic render() {
        return new ObjectGraphic("🔺", "assets/bullet.png");
    }


}
