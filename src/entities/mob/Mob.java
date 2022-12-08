package entities.mob;

import entities.Entity;
import javafx.scene.image.Image;

public abstract class Mob extends Entity {
    protected int speed;
    protected char direction;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public Mob(int x, int y, Image img) {
        super(x, y, img);
    }
}
