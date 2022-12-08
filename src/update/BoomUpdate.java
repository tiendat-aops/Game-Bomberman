package update;

import entities.mob.Bomber;
import entities.Boom;
import entities.tile.Brick;
import entities.Flame;
import gameplay.Game;
import graphics.Sprite;

import java.util.List;

import static entities.Boom.TIME_BOMB;

public class BoomUpdate {
    public static List<Flame> createFlames(Boom boom, Bomber player,
                                           List<Flame> flames,
                                           List<Brick> brickObjects, List<Boom> booms) {
        int x = boom.getX() / Sprite.SCALED_SIZE;
        int y = boom.getY() / Sprite.SCALED_SIZE;
        int lengthOfBoom = player.getLengthOfBoom();

        //check phai
        for (int i = x + 1; i <= x + lengthOfBoom; ++i) {
            if (Game.map.getAt(i, y) == '#') {
                break;
            }
            for (Boom boom1 : booms) {
                if (boom1.getTiming() >= TIME_BOMB) {
                    continue;
                }
                int X = boom1.getX() / Sprite.SCALED_SIZE;
                int Y = boom1.getY() / Sprite.SCALED_SIZE;
                if (X == i && Y == y) {
                    boom1.setTiming(TIME_BOMB);
                }
            }
            if (Game.map.getAt(i, y) == '*') {
                Game.map.setAt(i, y, ' ');
                for (int u = 0; u < brickObjects.size(); ++u) {
                    Brick brick = brickObjects.get(u);
                    if (brick.getX() == i && brick.getY() == y) {
                        brick.setDead(true);
                        break;
                    }
                }
                break;
            }
            if ((i < x + lengthOfBoom && Game.map.getAt(i + 1, y) == '#')
                    || (i == x + lengthOfBoom)) {
                Flame flame = new Flame(i, y, Sprite.explosion_horizontal_right_last.getFxImage(), 'r');
                flames.add(flame);
                break;
            }
            if (i < x + lengthOfBoom && Game.map.getAt(i + 1, y) == 'B') {
                Flame flame = new Flame(i, y, Sprite.explosion_horizontal.getFxImage(), 'h');
                flames.add(flame);
                break;
            }
            Flame flame = new Flame(i, y, Sprite.explosion_horizontal.getFxImage(), 'h');
            flames.add(flame);
        }

        //check trái
        for (int i = x - 1; i >= x - lengthOfBoom; --i) {
            if (Game.map.getAt(i, y) == '#') {
                break;
            }
            for (Boom boom1 : booms) {
                if (boom1.getTiming() >= TIME_BOMB) {
                    continue;
                }
                int X = boom1.getX() / Sprite.SCALED_SIZE;
                int Y = boom1.getY() / Sprite.SCALED_SIZE;
                if (X == i && Y == y) {
                    boom1.setTiming(TIME_BOMB);
                }
            }
            if (Game.map.getAt(i, y) == '*') {
                Game.map.setAt(i, y, ' ');
                for (int u = 0; u < brickObjects.size(); ++u) {
                    Brick brick = brickObjects.get(u);
                    if (brick.getX() == i && brick.getY() == y) {
                        brick.setDead(true);
                        break;
                    }
                }
                break;
            }
            if ((i > x - lengthOfBoom && Game.map.getAt(i - 1, y) == '#')
                    || (i == x - lengthOfBoom)) {
                Flame flame = new Flame(i, y, Sprite.explosion_horizontal_left_last.getFxImage(), 'l');
                flames.add(flame);
                break;
            }
            Flame flame = new Flame(i, y, Sprite.explosion_horizontal.getFxImage(), 'h');
            flames.add(flame);
        }

        //check duoi
        for (int j = y + 1; j <= y + lengthOfBoom; ++j) {
            if (Game.map.getAt(x, j) == '#') {
                break;
            }
            for (Boom boom1 : booms) {
                if (boom1.getTiming() >= TIME_BOMB) {
                    continue;
                }
                int X = boom1.getX() / Sprite.SCALED_SIZE;
                int Y = boom1.getY() / Sprite.SCALED_SIZE;
                if (X == x && Y == j) {
                    boom1.setTiming(TIME_BOMB);
                }
            }
            if (Game.map.getAt(x, j) == '*') {
                Game.map.setAt(x, j, ' ');
                for (int u = 0; u < brickObjects.size(); ++u) {
                    Brick brick = brickObjects.get(u);
                    if (brick.getX() == x && brick.getY() == j) {
                        brick.setDead(true);
                        break;
                    }
                }
                break;
            }
            if ((j < y + lengthOfBoom && Game.map.getAt(x, j + 1) == '#')
                    || j == y + lengthOfBoom) {
                Flame flame = new Flame(x, j, Sprite.explosion_vertical_down_last.getFxImage(), 'd');
                flames.add(flame);
                break;
            }
            Flame flame = new Flame(x, j, Sprite.explosion_vertical.getFxImage(), 'v');
            flames.add(flame);
        }
        // check trên
        for (int j = y - 1; j >= y - lengthOfBoom; --j) {
            if (Game.map.getAt(x, j) == '#') {
                break;
            }
            for (Boom boom1 : booms) {
                if (boom1.getTiming() >= TIME_BOMB) {
                    continue;
                }
                int X = boom1.getX() / Sprite.SCALED_SIZE;
                int Y = boom1.getY() / Sprite.SCALED_SIZE;
                if (X == x && Y == j) {
                    boom1.setTiming(TIME_BOMB);
                }
            }
            if (Game.map.getAt(x, j) == '*') {
                Game.map.setAt(x, j, ' ');
                for (int u = 0; u < brickObjects.size(); ++u) {
                    Brick brick = brickObjects.get(u);
                    if (brick.getX() == x && brick.getY() == j) {
                        brick.setDead(true);
                        break;
                    }
                }
                break;
            }
            if ((j > y - lengthOfBoom && Game.map.getAt(x, j - 1) == '#')
                    || j == y - lengthOfBoom) {
                Flame flame = new Flame(x, j, Sprite.explosion_vertical_top_last.getFxImage(), 't');
                flames.add(flame);
                break;
            }
            Flame flame = new Flame(x, j, Sprite.explosion_vertical.getFxImage(), 'v');
            flames.add(flame);
        }
        return flames;
    }
}
