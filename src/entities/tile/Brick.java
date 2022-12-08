package entities.tile;

import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

public class Brick extends Tile {
    private int timing;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        timing = 0;
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }

    @Override
    public int getX() {
        return x / Sprite.SCALED_SIZE;
    }

    @Override
    public int getY() {
        return y / Sprite.SCALED_SIZE;
    }

    @Override
    public void whenDead() {
        if (dead) {
            if (timing == 0) {
                img = Sprite.brick_exploded.getFxImage();
            }
            timing++;
        }
        if (timing == 5) {
            img = Sprite.brick_exploded1.getFxImage();
        } else if (timing == 10) {
            img = Sprite.brick_exploded2.getFxImage();
        }
    }

    @Override
    public void update() {
        whenDead();
    }
}
