package game.core;

public abstract class ObjectWithPosition implements SpaceObject {
    int x;
    int y;

    public ObjectWithPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

}
