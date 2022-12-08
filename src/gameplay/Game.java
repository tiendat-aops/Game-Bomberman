package gameplay;

import entities.*;
import entities.mob.enemy.*;
import entities.item.BombItem;
import entities.item.FlameItem;
import entities.item.Item;
import entities.item.SpeedItem;
import entities.mob.Bomber;
import entities.tile.Brick;
import entities.tile.Grass;
import entities.tile.Portal;
import entities.tile.Wall;
import graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import loadmap.GameMap;
import sound.AudioPlayer;
import sound.Sound;
import update.BoomUpdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    //map
    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    public static GameMap map = new GameMap();
    public static int time = 18000;
    public static boolean nextLevel = false;
    //objects
    public static List<Bomber> bombers = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
    public static List<Entity> tiles = new ArrayList<>();
    public static List<Boom> booms = new ArrayList<>();
    public static List<Flame> flames = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Portal> portals = new ArrayList<>();

    private static GraphicsContext gc;
    private Canvas canvas;
    private int level = 1;
    private int startX = 1, startY = 1;
    private boolean win = false;
    private boolean lose = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        AudioPlayer mainSound = new AudioPlayer("game");
        time = 18000;
        mainSound.run();
        createMap("res/levels/Level1.txt");
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        stage.setResizable(false);
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                --time;
                Bomber player = bombers.get(0);
                //lose
                if ((player.isDead() && player.getHeart() == 0 && player.getTiming() == 120)
                        || time == 0) {
                    clearAll();
                    this.stop();
                    mainSound.stop();
                    Image image = new Image("/image/gameover.jpg");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(500);
                    imageView.setFitWidth(992);
                    root.getChildren().add(imageView);
                    Sound.play("lose");
                }
                //win
                if (win) {
                    this.stop();
                    clearAll();
                    mainSound.stop();
                    Image image = new Image("/image/win.jpg");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(500);
                    imageView.setFitWidth(992);
                    root.getChildren().add(imageView);
                    Sound.play("win");
                }
            }
        };
        timer.start();

        scene.setOnKeyPressed(event -> {
            Bomber player1 = bombers.get(0);
            switch (event.getCode()) {
                case UP:
                    player1.setMoving(true, 'U');
                    break;
                case DOWN:
                    player1.setMoving(true, 'D');
                    break;
                case LEFT:
                    player1.setMoving(true, 'L');
                    break;
                case RIGHT:
                    player1.setMoving(true, 'R');
                    break;
                case SPACE:
                    if (booms.size() >= player1.getSizeOfBoom()) {
                        break;
                    }
                    if (map.getAt(player1.getLocationX(), player1.getLocationY()) == 'B') {
                        break;
                    }
                    if (player1.isDead() && player1.getTiming() <= 40) {
                        break;
                    }
                    Boom newBoom = new Boom(player1.getLocationX(), player1.getLocationY(), Sprite.bomb.getFxImage());
                    booms.add(newBoom);
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            Bomber player1 = bombers.get(0);
            switch (event.getCode()) {
                case UP: case DOWN: case LEFT: case RIGHT:
                    player1.setMoving(false);
                    break;
            }
        });
    }

    public void createMap(String input) {
        map.loadMap(input);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                switch (map.getAt(j, i)) {
                    case '#':
                        tiles.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        break;
                    case '*':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        bricks.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    case 'x':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        portals.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        bricks.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map.setAt(j, i, '*');
                        break;
                    case 'p':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        if (bombers.isEmpty()) {
                            bombers.add(new Bomber(j, i, Sprite.player_right.getFxImage()));
                            //System.out.println(bombers.size());
                        } else {
                            bombers.get(0).setX(startX * Sprite.SCALED_SIZE);
                            bombers.get(0).setY(startY * Sprite.SCALED_SIZE);
                        }
                        break;
                    case 'b':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        items.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        bricks.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map.setAt(j, i, '*');
                        break;
                    case 's':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        items.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        bricks.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map.setAt(j, i, '*');
                        break;
                    case 'f':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        items.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        bricks.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map.setAt(j, i, '*');
                        break;
                    case '1':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemies.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
                        break;
                    case '2':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    case '3':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemies.add(new Doria(j, i, Sprite.kondoria_left1.getFxImage()));
                        break;
                    case '4':
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemies.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                        break;
                    default:
                        tiles.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                }
            }
        }
    }

    public void flamesUpdate() {
        for (int i = 0; i < booms.size(); ++i) {
            if (booms.get(i).getTiming() == Boom.TIME_BOMB) {
                flames = BoomUpdate.createFlames(booms.get(i), bombers.get(0),
                        flames, bricks, booms);
            }
        }
        for (int i = 0; i < flames.size(); ++i) {
            flames.get(i).update();
            flames.get(i).collide(bombers.get(0));
            for (Item item : items) {
                flames.get(i).collide(item);
            }
            for (Enemy enemy : enemies) {
                flames.get(i).collide(enemy);
            }
            if (flames.get(i).getTiming() == 15) {
                flames.remove(i);
                --i;
            }
        }
    }

    public void boomsUpdate() {
        for (int i = 0; i < booms.size(); ++i) {
            booms.get(i).collide(bombers.get(0));
            booms.get(i).update();
            if (booms.get(i).getTiming() == Boom.TIME_BOMB + 15) {
                booms.remove(i);
                --i;
            }
        }
    }

    public void bricksUpdate() {
        for (int i = 0; i < bricks.size(); ++i) {
            bricks.get(i).update();
            int time = bricks.get(i).getTiming();
            if (time == 15) {
                bricks.remove(i);
                --i;
            }
        }
    }

    public void enemiesUpdate() {
        for (int i = 0; i < enemies.size(); ++i) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if (!enemy.isDead()) {
                enemy.collide(bombers.get(0));
            } else {
                if (enemy.getTiming() == 30) {
                    enemies.remove(i);
                    --i;
                }
            }
        }
    }

    public void itemsUpdate() {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.update();
            if (item.isDead()) {
                items.remove(i);
                --i;
            }
        }
    }

    public void playerUpdate() {
        Bomber player = bombers.get(0);
        player.update();
        for (Item item : items) {
            player.collide(item);
        }
        for (Portal portal: portals) {
            player.collide(portal);
        }
        if (player.isDead() && player.getTiming() == 10) {
            player.setHeart(player.getHeart() - 1);
        } else if (player.isDead() && player.getHeart() > 0 && player.getTiming() == 40) {
            player.setX(startX * Sprite.SCALED_SIZE);
            player.setY(startY * Sprite.SCALED_SIZE);
            player.setImg(Sprite.player_left.getFxImage());
        } else if (player.getTiming() == 220) {
            player.setDead(false);
            player.setTiming(0);
        }
    }

    public void clearAll() {
        tiles.clear();
        booms.clear();
        flames.clear();
        bricks.clear();
        items.clear();
        enemies.clear();
        portals.clear();
    }

    public void updateMap() {
       if (enemies.size() == 0 && bombers.get(0).inPortal()) {
           nextLevel = true;
       }
       if (nextLevel) {
           if (level < 2) {
               nextLevel = false;
               clearAll();
               ++level;
               createMap("res/levels/Level" + level + ".txt");
               time = 18000;
               return;
           } else {
              win = true;
           }
       }
    }

    public void update() {
        boomsUpdate();
        flamesUpdate();
        bricksUpdate();
        enemiesUpdate();
        itemsUpdate();
        playerUpdate();
        updateMap();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        tiles.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        booms.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bombers.forEach(g -> g.render(gc));
    }
}
