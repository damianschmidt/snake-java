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
        super(color);
        random = new Random();
        this.point = generateNewPosition();
        this.power = power;
    }

    Food() {
        this(Color.RED, 1);
    }

    public void update(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x, point.y, width, height);
    }

    private Point generateNewPosition() {
        int newX = round(random.nextInt((Game.getWIDTH() - 2 * Game.RECT_SCALE) + Game.RECT_SCALE - 1));
        int newY = round(random.nextInt((Game.getHEIGHT()) - 2 * Game.RECT_SCALE) + Game.RECT_SCALE - 1);
        return new Point(newX, newY);
    }

    private static int round(int n) {
        // Smaller multiple
        int a = (n / 10) * 10;

        // Larger multiple
        int b = a + 10;

        // Return of closest of two
        return (n - a > b - n) ? b : a;
    }
}
