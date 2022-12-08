package loadmap;

import gameplay.Game;

import java.io.File;
import java.util.Scanner;

public class GameMap {
    private String[] map;

    public void loadMap(String address) {
        try {
            Scanner sc = new Scanner(new File(address));
            int level = sc.nextInt();
            int height = sc.nextInt();
            int width = sc.nextInt();
            Game.WIDTH = width;
            Game.HEIGHT = height;
            map = new String[height];
            map[0] = sc.nextLine();
            for (int i = 0; i < height; ++i) {
                map[i] = sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            map = new String[0];
        }
    }

    public char getAt(int x, int y) {
        return map[y].charAt(x);
    }

    public void setAt(int x, int y, char c) {
        map[y] = map[y].substring(0, x) + c + map[y].substring(x + 1);
    }

    public boolean isBlocked(int x, int y) {
        return map[y].charAt(x) == 'B' || map[y].charAt(x) == '*'
                || map[y].charAt(x) == '#';
    }

}
