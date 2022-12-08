package AI;

import gameplay.Game;
import graphics.Sprite;

import java.util.Random;

public class AILow {
    public static char directionForWallPass(int x, int y) {
        char[] a = new char[4];
        int dem = -1;
        int X = x / Sprite.SCALED_SIZE;
        int Y = y / Sprite.SCALED_SIZE;
        int newX = X + 1, newY = Y;
        if (newX >= 0 && newX < Game.WIDTH && newY >= 0 && newY < Game.HEIGHT && Game.map.getAt(newX, newY) != '#') {
            ++dem;
            a[dem] = 'R';
        }
        newX = X - 1;
        if (newX >= 0 && newX < Game.WIDTH && newY >= 0 && newY < Game.HEIGHT && Game.map.getAt(newX, newY) != '#') {
            ++dem;
            a[dem] = 'L';
        }
        newX = X;
        newY = Y - 1;
        if (newX >= 0 && newX < Game.WIDTH && newY >= 0 && newY < Game.HEIGHT && Game.map.getAt(newX, newY) != '#') {
            ++dem;
            a[dem] = 'U';
        }
        newY = Y + 1;
        if (newX >= 0 && newX < Game.WIDTH && newY >= 0 && newY < Game.HEIGHT && Game.map.getAt(newX, newY) != '#') {
            ++dem;
            a[dem] = 'D';
        }
        if (dem != -1) {
            Random random = new Random();
            int value = random.nextInt(dem + 1);
            return a[value];
        }
        return ' ';
    }

    public static char directionForNonWallPass(int x, int y) {
        char[] a = new char[4];
        int dem = -1;
        int X = x / Sprite.SCALED_SIZE;
        int Y = y / Sprite.SCALED_SIZE;

        int newX = X + 1, newY = Y;// check right
        if (!Game.map.isBlocked(newX, newY)) {
            ++dem;
            a[dem] = 'R';
        }

        newX = X - 1; // check left
        if (!Game.map.isBlocked(newX, newY)) {
            ++dem;
            a[dem] = 'L';
        }

        newX = X;
        newY = Y - 1; // check up
        if (!Game.map.isBlocked(newX, newY)) {
            ++dem;
            a[dem] = 'U';
        }

        newY = Y + 1; //check down
        if (!Game.map.isBlocked(newX, newY)) {
            ++dem;
            a[dem] = 'D';
        }
        if (dem != -1) {
            Random random = new Random();
            int value = random.nextInt(dem + 1);
            return a[value];
        }
        return ' ';
    }
}
