package game.core;

import game.GameModel;
import game.exceptions.BoundaryExceededException;
import game.utility.Direction;

/**
 * Represents a controllable object in the space game.
 */
public abstract class Controllable extends ObjectWithPosition {

    /**
     * Initializes a new Controllable object instance with coordinates x and y.
     *
     * @param x - the x Coordinate.
     * @param y - the y Coordinate.
     */
    public Controllable(int x, int y) {
        super(x, y);
    }

    /**
     * Moves the Controllable by one in the direction given.
     * Throws BoundaryExceededException if the Controllable is attempting to move outside the game boundaries.
     * Argument given to the exception is "Cannot move {up/down/left/right}.Out of bounds!" depending on the direction.
     *
     * @param direction - the given direction.
     *
     * @throws BoundaryExceededException - if attempting to move outside the game boundaries.
     */
    public void move(Direction direction) throws BoundaryExceededException {
        switch (direction) {
            case UP -> {
                if ((y + 1) > GameModel.GAME_HEIGHT) {
                    throw new BoundaryExceededException("Cannot move up. Out of bounds!");
                } else {
                    y -= 1;
                }
            }
            case LEFT -> {
                if ((x - 1) < 0) {
                    throw new BoundaryExceededException("Cannot move left. Out of bounds!");
                } else {
                    x -= 1;
                }
            }
            case RIGHT -> {
                if ((x + 1) > GameModel.GAME_WIDTH) {
                    throw new BoundaryExceededException("Cannot move right. Out of bounds!");
                } else {
                    x += 1;
                }
            }

            case DOWN -> {
                if ((y - 1) < 0) {
                    throw new BoundaryExceededException("Cannot move down. Out of bounds!");
                } else {
                    y += 1;
                }

            }
        }

    }
}