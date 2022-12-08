package entities;

import entities.mob.enemy.Enemy;
import entities.item.Item;
import entities.mob.Bomber;
import graphics.Sprite;
import javafx.scene.image.Image;

public class Flame extends Entity {
    private char typeExplosion;
    private int timing = 0;

    public Flame(int x, int y, Image img, char typeExplosion) {
        super(x, y, img);
        this.typeExplosion = typeExplosion;
    }

    public int getTiming() {
        return timing;
    }

    @Override
    public void update() {
        ++timing;
        if (timing == 5) {
            switch (typeExplosion) {
                case 'h':
                    img = Sprite.explosion_horizontal1.getFxImage();
                    break;
                case 'v':
                    img = Sprite.explosion_vertical1.getFxImage();
                    break;
                case 't':
                    img = Sprite.explosion_vertical_top_last1.getFxImage();
                    break;
                case 'd':
                    img = Sprite.explosion_vertical_down_last1.getFxImage();
                    break;
                case 'r':
                    img = Sprite.explosion_horizontal_right_last1.getFxImage();
                    break;
                case 'l':
                    img = Sprite.explosion_horizontal_left_last1.getFxImage();
                    break;
            }
        } else if (timing == 10) {
            switch (typeExplosion) {
                case 'h':
                    img = Sprite.explosion_horizontal2.getFxImage();
                    break;
                case 'v':
                    img = Sprite.explosion_vertical2.getFxImage();
                    break;
                case 't':
                    img = Sprite.explosion_vertical_top_last2.getFxImage();
                    break;
                case 'd':
                    img = Sprite.explosion_vertical_down_last2.getFxImage();
                    break;
                case 'r':
                    img = Sprite.explosion_horizontal_right_last2.getFxImage();
                    break;
                case 'l':
                    img = Sprite.explosion_horizontal_left_last2.getFxImage();
                    break;
            }
        }
    }

    @Override
    public void collide(Entity e) {
        if (e instanceof Bomber player) {
            if (!player.isDead()) {
                int X1 = (player.getX() + 12) / Sprite.SCALED_SIZE;
                int Y1 = (player.getY() + 16) / Sprite.SCALED_SIZE;
                int X = this.getX() / Sprite.SCALED_SIZE;
                int Y = this.getY() / Sprite.SCALED_SIZE;
                if (X == X1 && Y == Y1) {
                    player.setDead(true);
                    player.setTiming(0);
                }
            }
        }
        if (e instanceof Enemy enemy) {
            if (!enemy.isDead()) {
                int X1 = (enemy.getX() + 2) / Sprite.SCALED_SIZE;
                int Y1 = (enemy.getY() + 2) / Sprite.SCALED_SIZE;
                int X2 = (enemy.getX() + 30) / Sprite.SCALED_SIZE;
                int Y2 = (enemy.getY() + 2) / Sprite.SCALED_SIZE;
                int X3 = (enemy.getX() + 2) / Sprite.SCALED_SIZE;
                int Y3 = (enemy.getY() + 30) / Sprite.SCALED_SIZE;
                int X4 = (enemy.getX() + 30) / Sprite.SCALED_SIZE;
                int Y4 = (enemy.getY() + 30) / Sprite.SCALED_SIZE;
                int X = this.getX() / Sprite.SCALED_SIZE;
                int Y = this.getY() / Sprite.SCALED_SIZE;
                if ((X == X1 && Y == Y1)
                        || (X == X2 && Y == Y2)
                        || (X == X3 && Y == Y3)
                        || (X == X4 && Y == Y4)) {
                    enemy.setDead(true);
                    enemy.setTiming(0);
                }
            }
        }
        if (e instanceof Item item) {
            int X = (item.getX()) / Sprite.SCALED_SIZE;
            int Y = (item.getY()) / Sprite.SCALED_SIZE;
            if (x/Sprite.SCALED_SIZE == X && y/Sprite.SCALED_SIZE == Y) {
                item.setDead(true);
            }
        }
    }

    @Override
    public void whenDead() {}
}


