package game.core;

import game.ui.ObjectGraphic;

public class Ship extends Controllable {
    private int x;
    private int y;
    private int health;
    public Ship() {
        super(5, 10);
        this.health = 100;
    }

    public Ship(int x, int y, int health) {
        super(x, y);
        this.health = health;
    }

    @Override
    public ObjectGraphic render() {
        return null;
    }

    @Override
    public void tick(int tick) {

    }
}
