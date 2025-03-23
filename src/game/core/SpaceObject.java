package game.core;

import game.ui.ObjectGraphic;
import game.ui.Tickable;

public interface SpaceObject extends Tickable {
    int getX();

    int getY();

    ObjectGraphic render();

}
