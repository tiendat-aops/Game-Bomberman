package entities.mob.enemy;

import AI.AILow;
import graphics.Sprite;
import javafx.scene.image.Image;

import static graphics.Sprite.movingSprite;

public class Doria extends Enemy {

    public Doria(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
    }

    @Override
    public void whenDead() {
        if (dead) {
            if (timing == 0) {
                img = Sprite.kondoria_dead.getFxImage();
            }
            timing++;
            if (timing > 5) {
                img = movingSprite(Sprite.mob_dead1, Sprite.mob_dead2,
                        Sprite.mob_dead3, timing, 30).getFxImage();
            }
        }
    }

    public void moveLeft() {
        x -= speed;
        img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2,
                Sprite.kondoria_left3, timing, 30).getFxImage();
    }

    public void moveRight() {
        x += speed;
        img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2,
                Sprite.kondoria_right3, timing, 30).getFxImage();
    }

    public void moveUp() {
        y -= speed;
        img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2,
                Sprite.kondoria_right3, timing, 30).getFxImage();
    }

    public void moveDown() {
        y += speed;
        img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2,
                Sprite.kondoria_left3, timing, 30).getFxImage();
    }

    @Override
    public void update() {
        if (dead) {
            whenDead();
            return;
        }
        //System.out.println(stepCnt);
        if (x % 32 == 0 && y % 32 == 0) {
            timing = 0;
            direction = AILow.directionForWallPass(x, y);
        }
        switch (direction) {
            case 'L':
                moveLeft();
                break;
            case 'R':
                moveRight();
                break;
            case 'U':
                moveUp();
                break;
            case 'D':
                moveDown();
                break;
        }
        timing++;
    }
}
