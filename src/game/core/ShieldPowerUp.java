package game.core;

import game.ui.ObjectGraphic;

public class ShieldPowerUp extends PowerUp {
    public ShieldPowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(Ship ship) {
        //UPDATE SHIPS SHIELD
        System.out.println("Shield activated! Score increased by 50.");
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("💠", "assets/shield.png");
    }
}
