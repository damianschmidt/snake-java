package snake.game.food;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import snake.game.BaseObject;
import snake.game.Game;
import snake.game.Segment;
import snake.game.Snake;

import java.awt.*;
import java.util.Random;

public class Food extends BaseObject {
    @Getter
    private Random random;
    @Setter
    @Getter
    private int power;
    private boolean collide;

    Food(int width, int height, Color color) {
        super(0, 0, width, height, color);
        random = new Random();
        generateNewPosition();
        power = 1;

    }

    Food(Color color) {
        this(Game.RECT_SCALE, Game.RECT_SCALE, color);

    }

    public Food() {
        this(Color.RED);
    }

    @Override
    public void update(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x, point.y, width, height);
    }

    public void eat(Snake snake) {
        snake.setTailLength(snake.getTailLength() + power);
        snake.setScore(snake.getScore() + 1);
    }

    void generateNewPosition() {
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

    Point generateRandomPoint() {
        int newX = round(random.nextInt(Game.getWIDTH() - 3) + 1);
        int newY = round(random.nextInt(Game.getHEIGHT() - 3) + 1);
        return new Point(newX, newY);
    }

    static int round(int n) {
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
