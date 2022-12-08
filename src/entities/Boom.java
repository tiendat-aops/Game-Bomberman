package entities;

import entities.mob.Bomber;
import gameplay.Game;
import graphics.Sprite;
import javafx.scene.image.Image;
import sound.Sound;

public class Boom extends Entity {
    public static final int TIME_BOMB = 120;
    private final int intervalTime = 40;
    private int timing;

    public Boom(int x, int y, Image img) {
        super(x, y, img);
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }

    @Override
    public void update() {
        timing++;
        if (timing == TIME_BOMB) Sound.play("BOM_11_M");
        if (timing == 1) {
            int X = x / Sprite.SCALED_SIZE;
            int Y = y / Sprite.SCALED_SIZE;
            Game.map.setAt(X, Y, 'B');
            if (timing == 1) {
                Sound.play("BOM_SET");
            }
        }
        if (timing == TIME_BOMB + 9) {
            int X = x / Sprite.SCALED_SIZE;
            int Y = y / Sprite.SCALED_SIZE;
            Game.map.setAt(X, Y, ' ');
        }
        if (timing < TIME_BOMB) {
            switch (timing % intervalTime) {
                case 0:
                    img = Sprite.bomb.getFxImage();
                    break;
                case 10:
                case 30:
                    img = Sprite.bomb_1.getFxImage();
                    break;
                case 20:
                    img = Sprite.bomb_2.getFxImage();
                    break;
            }
        } else if (timing < TIME_BOMB + 5) {
            img = Sprite.bomb_exploded.getFxImage();
            if (timing == 180) Sound.play("BOM_11_M");
        } else if (timing < TIME_BOMB + 10) {
            img = Sprite.bomb_exploded1.getFxImage();
        } else if (timing < TIME_BOMB + 15) {
            img = Sprite.bomb_exploded2.getFxImage();
        }
    }

    @Override
    public void collide(Entity e) {
        if (e instanceof Bomber player) {
            int X1 = (player.getX() + 12) / Sprite.SCALED_SIZE;
            int Y1 = (player.getY() + 16) / Sprite.SCALED_SIZE;
            if (timing < Boom.TIME_BOMB) {
                return;
            }
            int X = x / Sprite.SCALED_SIZE;
            int Y = y / Sprite.SCALED_SIZE;
            if (X == X1 && Y == Y1) {
                player.setDead(true);
                player.setTiming(0);
            }
        }
    }

    @Override
    public void whenDead() {}
}
