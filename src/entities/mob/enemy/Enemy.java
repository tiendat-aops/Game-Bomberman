package entities.mob.enemy;

import entities.mob.Bomber;
import entities.Entity;
import entities.mob.Mob;
import gameplay.Game;
import javafx.scene.image.Image;

public abstract class Enemy extends Mob {
    protected int timing;

    public Enemy(int x, int y, Image img) {
        super(x, y, img);
        direction = ' ';
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }

    public abstract void whenDead();

    @Override
    public void collide(Entity e) {
        if (e instanceof Bomber) {
            Bomber player = Game.bombers.get(0);
            if (!player.isDead()) {
                int X1 = (player.getX() + 8);
                int Y1 = (player.getY() + 8);
                int X2 = (player.getX() + 16);
                int Y2 = (player.getY() + 8);
                int X3 = (player.getX() + 8);
                int Y3 = (player.getY() + 24);
                int X4 = (player.getX() + 16);
                int Y4 = (player.getY() + 24);
                int X = this.getX();
                int Y = this.getY();
                if ((X1 >= X && X1 <= X + 32 && Y1 >= Y && Y1 <= Y + 32)
                        || (X2 >= X && X2 <= X + 32 && Y2 >= Y && Y3 <= Y + 32)
                        || (X3 >= X && X3 <= X + 32 && Y3 >= Y && Y3 <= Y + 32)
                        || (X4 >= X && X4 <= X + 32 && Y4 >= Y && Y4 <= Y + 32)) {
                    player.setDead(true);
                    player.setTiming(0);
                }
            }
        }
    }
}
