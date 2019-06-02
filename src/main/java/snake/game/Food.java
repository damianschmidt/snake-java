package snake.game;

import lombok.Getter;

import java.awt.*;
import java.util.Random;

class Food extends BaseObject {
    @Getter
    private Random random;

    Food() {
        random = new Random();
        generateNewPosition();
    }

    void generateNewPosition() {
        point = new Point(
                random.nextInt((
                        Game.getInstance().getJFrame().getWidth() / Game.RECT_SCALE) - 2 * Game.RECT_SCALE)
                        + Game.RECT_SCALE,
                random.nextInt((
                        Game.getInstance().getJFrame().getHeight() / Game.RECT_SCALE) - 2 * Game.RECT_SCALE)
                        + Game.RECT_SCALE);
    }

    public void update(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(point.x * Game.RECT_SCALE, point.y * Game.RECT_SCALE,
                Game.RECT_SCALE, Game.RECT_SCALE);
    }
}
