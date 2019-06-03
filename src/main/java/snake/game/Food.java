package snake.game;

import lombok.Getter;
import lombok.val;

import java.awt.*;
import java.util.Random;

class Food extends BaseObject {
    @Getter
    private Random random;
    @Getter
    private int power;
    private boolean collide;

    Food(int width, int height, Color color, int power) {
        super(0, 0, width, height, color);
        random = new Random();
        generateNewPosition();
        this.power = power;
    }

    Food(Color color, int power) {
        this(Game.RECT_SCALE, Game.RECT_SCALE, color, power);

    }

    Food() {
        this(Color.RED, 1);
    }

    public void update(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x, point.y, width, height);
    }

    private void generateNewPosition() {
        point = generateRandomPoint();

        do {
            collide = false;
            Game.getInstance().getObjects()
                    .stream()
                    .filter(object -> this.hashCode() != object.hashCode())
                    .forEach((object) -> {
                        if (object instanceof BaseObject) {
                            val baseObject = ((BaseObject) object);
                            if (isCollidingWithObjects(point, baseObject)) {
                                collide = true;
                            }
                        }
                        if (object instanceof Snake) {
                            val snake = ((Snake) object);
                            if (isCollidingWithSnake(point, snake)) {
                                collide = true;
                            }
                        }
                    });
            if (collide) {
                point = generateRandomPoint();
            }
        }
        while (collide);
    }

    private Point generateRandomPoint() {
        int newX = round(random.nextInt(Game.getWIDTH() - 2) + 1);
        int newY = round(random.nextInt(Game.getHEIGHT() - 2) + 1);
        return new Point(newX, newY);
    }

    private static int round(int n) {
        int a = (n / 10) * 10;
        int b = a + 10;
        return (n - a > b - n) ? b : a;
    }

    private boolean isCollidingWithObjects(Point point, BaseObject object) {
        return point.x < object.getPoint().x + object.getWidth() &&
                point.x + width > object.getPoint().x &&
                point.y < object.getPoint().y + object.getHeight() &&
                height + point.y > object.getPoint().y;
    }

    private boolean isCollidingWithSnake(Point point, Snake snake) {
        for (Segment segment : snake.getSnakeParts()) {
            return isCollidingWithObjects(point, segment);
        }
        return isCollidingWithObjects(point, snake.getHead());
    }
}
