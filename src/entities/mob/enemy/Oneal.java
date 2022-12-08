package entities.mob.enemy;

import AI.AILow;
import AI.AINormal;
import entities.mob.Bomber;
import gameplay.Game;
import graphics.Sprite;
import javafx.scene.image.Image;

import static graphics.Sprite.movingSprite;

public class Oneal extends Enemy {

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
    }

    @Override
    public void whenDead() {
        if (dead) {
            if (timing == 0) {
                img = Sprite.oneal_dead.getFxImage();
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
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,
                Sprite.oneal_left3, timing, 30).getFxImage();
    }

    public void moveRight() {
        x += speed;
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2,
                Sprite.oneal_right3, timing, 30).getFxImage();
    }

    public void moveUp() {
        y -= speed;
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2,
                Sprite.oneal_right3, timing, 30).getFxImage();
    }

    public void moveDown() {
        y += speed;
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,
                Sprite.oneal_left3, timing, 30).getFxImage();
    }


    @Override
    public void update() {
        if (dead) {
            whenDead();
            return;
        }
        if (x % 32 == 0 && y % 32 == 0) {
            //goDoor();
            timing = 0;
            Bomber player = Game.bombers.get(0);
            String road = AINormal.bfs(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE,
                    player.getX() / Sprite.SCALED_SIZE, player.getY() / Sprite.SCALED_SIZE);
            if (road.charAt(0) == ' ' || road.length() > 3 || player.isDead()) {
                direction = AILow.directionForNonWallPass(x, y);
            } else {
                direction = road.charAt(0);
            }
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
