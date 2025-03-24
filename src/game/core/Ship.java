package game.core;

import game.ui.ObjectGraphic;

public class Ship extends Controllable {
    private int x;
    private int y;
    private int health;

    private int score;
    public Ship() {
        super(5, 10);
        this.health = 100;
        this.score = 0;
    }

    public Ship(int x, int y, int health) {
        super(x, y);
        this.health = health;
    }

    @Override
    public ObjectGraphic render() {

        return new ObjectGraphic("🚀", "assets/ship.png");
    }

    @Override
    public void tick(int tick) {

    }

    public void takeDamage(int damage) {
        health = Math.min(health - damage, 0);
    }

    public void heal(int amount) {
        health = Math.max(health + amount, 100);
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

}
