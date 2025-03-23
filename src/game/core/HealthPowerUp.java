package game.core;

import game.ui.ObjectGraphic;

public class HealthPowerUp extends PowerUp {
    public HealthPowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(Ship ship) {
        //UPDATE SHIPS HEALTH
        System.out.println("Health restored by 20!");

    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("❤️", "assets/health.png");
    }
}
