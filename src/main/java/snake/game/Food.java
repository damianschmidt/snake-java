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
        generateNewPosition();
    }

    void generateNewPosition() {
        object = new Point(random.nextInt((Game.getInstance().getJFrame().getWidth() / Game.RECT_SCALE) - Game.RECT_SCALE),
                random.nextInt((Game.getInstance().getJFrame().getHeight() / Game.RECT_SCALE) - Game.RECT_SCALE));
    }

    void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(object.x * Game.RECT_SCALE,object.y * Game.RECT_SCALE,
                Game.RECT_SCALE, Game.RECT_SCALE);
    }
}
