package snake.game;

import lombok.Getter;

import java.awt.*;
import java.util.Random;

class Food {
    @Getter
    private Point object;
    private Random random;

    Food() {
        random = new Random();
        object = new Point(random.nextInt(Game.getInstance().getJFrame().getWidth() / Game.RECT_SCALE),
                random.nextInt(Game.getInstance().getJFrame().getHeight() / Game.RECT_SCALE));
    }

    void generateNewPosition() {
        object = new Point(random.nextInt((Game.getInstance().getJFrame().getWidth() / Game.RECT_SCALE) - Game.RECT_SCALE),
                random.nextInt((Game.getInstance().getJFrame().getHeight() / Game.RECT_SCALE) - Game.RECT_SCALE));
    }
}
