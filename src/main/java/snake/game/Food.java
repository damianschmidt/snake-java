package snake.game;

import lombok.Getter;

import java.awt.*;
import java.util.Random;

class Food extends BaseObject {
    @Getter
    private Random random;
    @Getter
    private int power;

    Food(Color color, int power) {
        random = new Random();
        this.point = generateNewPosition();
        this.color = color;
        this.power = power;
    }

    Food() {
        this(Color.RED, 1);
    }

    public void update(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x * Game.RECT_SCALE, point.y * Game.RECT_SCALE,
                Game.RECT_SCALE, Game.RECT_SCALE);
    }

    private Point generateNewPosition() {
        return new Point(
                random.nextInt((
                        Game.getInstance().getJFrame().getWidth() / Game.RECT_SCALE) - 2 * Game.RECT_SCALE)
                        + Game.RECT_SCALE,
                random.nextInt((
                        Game.getInstance().getJFrame().getHeight() / Game.RECT_SCALE) - 2 * Game.RECT_SCALE)
                        + Game.RECT_SCALE);
    }
}
