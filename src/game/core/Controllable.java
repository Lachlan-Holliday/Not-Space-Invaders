package game.core;

import game.GameModel;
import game.exceptions.BoundaryExceededException;
import game.utility.Direction;

public abstract class Controllable extends ObjectWithPosition {

    public Controllable(int x, int y) {
        super(x, y);
    }

    public void move(Direction direction) {
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
                    x += 1;
                }
            }
            case RIGHT -> {
                if ((x + 1) > GameModel.GAME_WIDTH) {
                    throw new BoundaryExceededException("Cannot move right. Out of bounds!");
                } else {
                    x -= 1;
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