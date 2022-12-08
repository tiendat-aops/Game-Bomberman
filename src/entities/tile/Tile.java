package entities.tile;

import entities.Entity;
import javafx.scene.image.Image;

public abstract class Tile extends Entity {
    public Tile(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
    }

    @Override
    public void collide(Entity e) {
    }

    @Override
    public void whenDead() {};
}
