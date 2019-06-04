package snake.game.food;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import snake.game.BaseObject;
import snake.game.Game;
import snake.game.Segment;
import snake.game.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Food extends BaseObject {
    @Getter
    private Random random;
    @Setter
    @Getter
    private int power;
    private boolean collide;
    BufferedImage image;


    Food(int width, int height) throws IOException {
        super(0, 0, width, height);
        image = ImageIO.read(new File("C:\\Users\\Damian Schmidt\\IdeaProjects\\snake\\snake_pwr\\src\\main\\resources\\food.png"));
        random = new Random();
        generateNewPosition();
        power = 1;
    }

    public Food() throws IOException {
        this(Game.RECT_SCALE, Game.RECT_SCALE);
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(image, point.x, point.y, width, height, null);
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
        int newX = round(random.nextInt(Game.getWIDTH() - 2 * Game.RECT_SCALE) + Game.RECT_SCALE);
        int newY = round(random.nextInt(Game.getHEIGHT() - 2 * Game.RECT_SCALE) + Game.RECT_SCALE);
        return new Point(newX, newY);
    }

    static int round(int n) {
        int a = (n / Game.RECT_SCALE) * Game.RECT_SCALE;
        int b = a + Game.RECT_SCALE;
        return (n - a > b - n) ? b : a;
    }

    private boolean isCollidingWithObjects(Point point, BaseObject object) {
        return point.x < object.getPoint().x + object.getWidth() &&
                point.x + width > object.getPoint().x &&
                point.y < object.getPoint().y + object.getHeight() &&
                height + point.y > object.getPoint().y;
    }

    private boolean isCollidingWithSnake(Point point, Snake snake) {
        boolean isCollide = false;
        for (Segment segment : snake.getSnakeParts()) {
            if (isCollidingWithObjects(point, segment)) {
                isCollide = true;
            }
        }
        if (isCollidingWithObjects(point, snake.getHead())) {
            isCollide = true;
        }
        return isCollide;
    }
}
