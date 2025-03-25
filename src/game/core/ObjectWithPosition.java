package game.core;

/**
 * Represents a movable and interactive object in the space game.
 */
public abstract class ObjectWithPosition implements SpaceObject {

    protected int x; //The x coordinate of the Object

    protected int y; //The y coordinate of the Object

    /**
     * Creates a movable and interactive object at the given coordinates.
     *
     * @param x - the x Coordinate.
     * @param y - the y Coordinate.
     */
    public ObjectWithPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the SpaceObject, where 0 represents the left-most space with positive numbers extending to the right.
     *
     * @return x coordinate of the SpaceObject.
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the SpaceObject, where 0 represents the top-most space with positive numbers extending downwards.
     *
     * @return y coordinate of the SpaceObject.
     */
    @Override
    public int getY() {
        return y;
    }

}
