package entities.mob;

import entities.Entity;
import entities.item.BombItem;
import entities.item.FlameItem;
import entities.item.Item;
import entities.item.SpeedItem;
import entities.tile.Portal;
import gameplay.Game;
import graphics.Sprite;
import javafx.scene.image.Image;
import sound.Sound;

import static graphics.Sprite.SCALED_SIZE;


public class Bomber extends Mob {
    public static final int BOMBER_WIDTH = 24;
    public static final int BOMBER_HEIGHT = 32;

    private boolean moving;
    private int frame;
    private int sizeOfBoom;
    private int lengthOfBoom;
    private int heart;
    private boolean inPortal;

    private int timing;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        frame = 0;
        speed = 1;
        sizeOfBoom = 2;
        lengthOfBoom = 1;
        heart = 3;
        timing = 0;
        inPortal = false;
        moving = false;
    }

    public boolean inPortal() {
        return inPortal;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setMoving(boolean moving, char direction) {
        this.moving = moving;
        this.direction = direction;
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }

    public int getSizeOfBoom() {
        return sizeOfBoom;
    }

    public void setSizeOfBoom(int sizeOfBoom) {
        this.sizeOfBoom = sizeOfBoom;
    }

    public int getLocationX() {
        return (x + 11) / SCALED_SIZE;
    }

    public int getLocationY() {
        return (y + 16) / SCALED_SIZE;
    }

    public int getLengthOfBoom() {
        return lengthOfBoom;
    }

    public void setLengthOfBoom(int lengthOfBoom) {
        this.lengthOfBoom = lengthOfBoom;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public void whenDead() {
        if (dead) {
            if (timing == 0) {
                img = Sprite.player_dead1.getFxImage();
                Sound.play("AA126_11");
            }
            timing++;
            if (timing == 5) {
                img = Sprite.player_dead2.getFxImage();
            } else if (timing == 10) {
                img = Sprite.player_dead3.getFxImage();
            }
        }

    }

    public boolean checkInBoom() {
        int X = x / SCALED_SIZE;
        int Y = y / SCALED_SIZE;
        if (Game.map.getAt(X, Y) == 'B') {
            return true;
        }
        X = (x + 22) / SCALED_SIZE;
        if (Game.map.getAt(X, Y) == 'B') {
            return true;
        }
        Y = (y + 30) / SCALED_SIZE;
        if (Game.map.getAt(X, Y) == 'B') {
            return true;
        }
        X = (x) / SCALED_SIZE;
        if (Game.map.getAt(X, Y) == 'B') {
            return true;
        }
        return false;
    }

    public void moveUp() {
        frame++;
        boolean inBoom = checkInBoom();
        boolean checkCollision = false;
        y -= speed;
        int X1 = x / SCALED_SIZE;
        int X2 = (x + BOMBER_WIDTH - 1) / SCALED_SIZE;
        int X3 = (x + 12) / SCALED_SIZE;
        int Y = y / SCALED_SIZE;
        if (Game.map.isBlocked(X1, Y) && Game.map.isBlocked(X2, Y)) {
            if ((Game.map.getAt(X1, Y) != 'B' && Game.map.getAt(X2, Y) != 'B') || !inBoom) {
                checkCollision = true;
            }
        } else {
            if (Game.map.isBlocked(X1, Y)) {
                if (Game.map.getAt(X1, Y) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X3, Y)) {
                        x = X3 * SCALED_SIZE;
                    } else {
                        checkCollision = true;
                    }
                }
            } else if (Game.map.isBlocked(X2, Y)) {
                if (Game.map.getAt(X2, Y) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X3, Y)) {
                        x = X3 * SCALED_SIZE + 7;
                    } else {
                        checkCollision = true;
                    }
                }
            }
        }
        if (checkCollision) {
            img = Sprite.player_up.getFxImage();
            y += speed;
        } else {
            img = Sprite.movingSprite(Sprite.player_up_1,
                    Sprite.player_up_2, frame, 15).getFxImage();
        }
    }

    public void moveDown() {
        frame++;
        boolean inBoom = checkInBoom();
        boolean checkCollision = false;
        y += speed;
        int X1 = x / SCALED_SIZE;
        int X2 = (x + 23) / SCALED_SIZE;
        int X3 = (x + 12) / SCALED_SIZE;
        int Y = (y + 32) / SCALED_SIZE;
        if (Game.map.isBlocked(X1, Y) && Game.map.isBlocked(X2, Y)) {
            if ((Game.map.getAt(X1, Y) != 'B' && Game.map.getAt(X2, Y) != 'B')
                    || !inBoom) {
                //y = Y * SCALED_SIZE - BOMBER_HEIGHT;
                checkCollision = true;
            }
        } else {
            if (Game.map.isBlocked(X1, Y)) {
                if (Game.map.getAt(X1, Y) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X3, Y)) {
                        x = X3 * SCALED_SIZE;
                    } else {
                        checkCollision = true;
                    }
                }
            } else if (Game.map.isBlocked(X2, Y)) {
                if (Game.map.getAt(X2, Y) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X3, Y)) {
                        x = X3 * SCALED_SIZE + 7;
                    } else {
                        checkCollision = true;
                    }
                }
            }
        }
        if (checkCollision) {
            img = Sprite.player_down.getFxImage();
            y -= speed;
        } else {
            img = Sprite.movingSprite(Sprite.player_down_1,
                    Sprite.player_down_2, frame, 15).getFxImage();
        }
    }

    public void moveRight() {
        frame++;
        boolean inBoom = checkInBoom();
        boolean checkCollision = false;
        x += speed;
        if (y % SCALED_SIZE == 0) {
            int X = (x + BOMBER_WIDTH) / SCALED_SIZE;
            int Y = y / SCALED_SIZE;
            if (Game.map.isBlocked(X, Y)) {
                if (Game.map.getAt(X, Y) != 'B' || !inBoom) {
                    checkCollision = true;
                    x = X * SCALED_SIZE - BOMBER_WIDTH;
                }
            }
        } else {
            int X = (x + BOMBER_WIDTH) / SCALED_SIZE;
            int Y1 = (y + 10) / SCALED_SIZE;
            int Y2 = y / SCALED_SIZE;
            int Y3 = (y + 22) / SCALED_SIZE;
            int Y4 = (y + BOMBER_HEIGHT) / SCALED_SIZE;
            if (Game.map.isBlocked(X, Y2)) {
                if (Game.map.getAt(X, Y2) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X, Y1)) {
                        y = Y1 * SCALED_SIZE;
                    } else {
                        checkCollision = true;
                        x = X * SCALED_SIZE - 24;
                    }
                }
            } else if (Game.map.isBlocked(X, Y4)) {
                if (Game.map.getAt(X, Y4) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X, Y3)) {
                        y = Y3 * SCALED_SIZE;
                    } else {
                        x = X * SCALED_SIZE - 24;
                    }
                }
            }
        }
        if (checkCollision) {
            img = Sprite.player_right.getFxImage();
        } else {
            img = Sprite.movingSprite(Sprite.player_right_1,
                    Sprite.player_right_2, frame, 15).getFxImage();
        }
    }

    public void moveLeft() {
        frame++;
        boolean inBoom = checkInBoom();
        boolean checkCollision = false;
        x -= speed;
        if (y % SCALED_SIZE == 0) {
            int X = x / SCALED_SIZE;
            int Y = y / SCALED_SIZE;
            if (Game.map.isBlocked(X, Y)) {
                if (Game.map.getAt(X, Y) != 'B' || !inBoom) {
                    checkCollision = true;
                    x = (X + 1) * SCALED_SIZE;
                }
            }
        } else {
            int X = x / SCALED_SIZE;
            int Y1 = (y + 10) / SCALED_SIZE;
            int Y2 = y / SCALED_SIZE;
            int Y3 = (y + 22) / SCALED_SIZE;
            int Y4 = (y + 32) / SCALED_SIZE;
            if (Game.map.isBlocked(X, Y2)) {
                if (Game.map.getAt(X, Y2) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X, Y1)) {
                        y = Y1 * SCALED_SIZE;
                    } else {
                        checkCollision = true;
                        x = (X + 1) * SCALED_SIZE;
                    }
                }
            } else if (Game.map.isBlocked(X, Y4)) {
                if (Game.map.getAt(X, Y4) != 'B' || !inBoom) {
                    if (!Game.map.isBlocked(X, Y3)) {
                        y = Y3 * SCALED_SIZE;
                    } else {
                        x = (X + 1) * SCALED_SIZE;
                    }
                }
            }
        }
        if (checkCollision) {
            img = Sprite.player_left.getFxImage();
        } else {
            img = Sprite.movingSprite(Sprite.player_left_1,
                    Sprite.player_left_2, frame, 15).getFxImage();
        }
    }

    @Override
    public void update() {
        whenDead();
        if ((dead && timing <= 40) || heart == 0) {
            return;
        }
        if (moving) {
            switch (direction) {
                case 'R':
                    moveRight();
                    break;
                case 'L':
                    moveLeft();
                    break;
                case 'U':
                    moveUp();
                    break;
                case 'D':
                    moveDown();
                    break;
            }
        } else {
            frame = 0;
            switch (direction) {
                case 'R':
                    img = Sprite.player_right.getFxImage();
                    break;
                case 'L':
                    img = Sprite.player_left.getFxImage();
                    break;
                case 'U':
                    img = Sprite.player_up.getFxImage();
                    break;
                case 'D':
                    img = Sprite.player_down.getFxImage();
                    break;
            }
        }
    }

    @Override
    public void collide(Entity e) {
        if (e instanceof Item item) {
            int bomberX = getLocationX();
            int bomberY = getLocationY();
            int X = item.getX() / SCALED_SIZE;
            int Y = item.getY() / SCALED_SIZE;
            if (bomberX == X && bomberY == Y) {
                Sound.play("Item");
                if (item instanceof SpeedItem) {
                    if (speed <= 2) {
                        speed++;
                    }
                } else if (item instanceof FlameItem) {
                    if (lengthOfBoom <= 4) {
                        lengthOfBoom++;
                    }
                } else if (item instanceof BombItem) {
                    if (sizeOfBoom <= 5) {
                        sizeOfBoom++;
                    }
                }
                item.setDead(true);
            }
        }
        if (e instanceof Portal portal) {
            if (x / Sprite.SCALED_SIZE == portal.getX() / Sprite.SCALED_SIZE
                    && y / Sprite.SCALED_SIZE == portal.getY() / Sprite.SCALED_SIZE) {
                inPortal = true;
            }
        }
    }
}
