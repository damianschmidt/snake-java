package snake.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import snake.game.food.Food;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class Snake implements UpdatePossible {
    private static final int STARTING_TAIL_LENGTH = 10;
    @Getter
    private Segment head;
    @Getter
    private List<Segment> snakeParts;
    @Getter
    private Color color;
    @Getter
    @Setter
    private int tailLength;
    private Direction[] direction;
    @Getter
    private boolean Dead;
    @Setter
    @Getter
    private int score;
    @Getter
    private String name;

    Snake(int x, int y, Direction[] direction, Color color, String name) {
        head = new Segment(x, y, color);
        tailLength = STARTING_TAIL_LENGTH;
        snakeParts = new ArrayList<>();
        this.direction = direction;
        this.color = color;
        this.name = name;
        score = 0;
    }

    @Override
    public void update(Graphics g) {
        move();
        checkCollision();
        draw(g);
    }

    private void move() {
        if (!Game.getInstance().isOver()) {
            if (direction[0] == Direction.UP && noTailAt(head.getPoint().x, head.getPoint().y - Game.RECT_SCALE)) {
                head.setPoint(new Point(head.getPoint().x, head.getPoint().y - Game.RECT_SCALE));
            } else if (direction[0] == Direction.DOWN && noTailAt(head.getPoint().x, head.getPoint().y + Game.RECT_SCALE)) {
                head.setPoint(new Point(head.getPoint().x, head.getPoint().y + Game.RECT_SCALE));
            } else if (direction[0] == Direction.LEFT && noTailAt(head.getPoint().x - Game.RECT_SCALE, head.getPoint().y)) {
                head.setPoint(new Point(head.getPoint().x - Game.RECT_SCALE, head.getPoint().y));
            } else if (direction[0] == Direction.RIGHT && noTailAt(head.getPoint().x + Game.RECT_SCALE, head.getPoint().y)) {
                head.setPoint(new Point(head.getPoint().x + Game.RECT_SCALE, head.getPoint().y));
            } else {
                Dead = true;
            }
            removeLastPart();
        }
    }

    private void checkCollision() {
        Game.getInstance().getObjects()
                .stream()
                .filter(object -> this.hashCode() != object.hashCode())
                .forEach((object) -> {
                    if (object instanceof Wall) {
                        val wall = ((Wall) object);
                        if (isColliding(wall)) {
                            Dead = true;
                        }
                    }
                    if (object instanceof Snake) {
                        val snake = ((Snake) object);
                        if (isCollidingWithSnakeTail(snake)) {
                            Dead = true;
                        } else if (isCollidingWithSnakeHead(snake)) {
                            Dead = true;
                        }
                    }
                    if (object instanceof Food) {
                        val food = ((Food) object);
                        if (isColliding(food)) {
                            food.eat(this);
                            food.setRemoved(true);
                        }
                    }
                });
    }

    private void draw(Graphics g) {
        g.setColor(color);
        for (val segment : snakeParts) {
            g.fillRect(segment.getPoint().x, segment.getPoint().y, segment.getWidth(), segment.getHeight());
        }
        g.fillRect(head.getPoint().x, head.getPoint().y, head.getWidth(), head.getHeight());
    }

    private void removeLastPart() {
        snakeParts.add(new Segment(head.getPoint().x, head.getPoint().y, color));
        if (snakeParts.size() > tailLength) {
            while (snakeParts.size() != tailLength) {
                snakeParts.remove(0);
            }
        }
    }

    private boolean noTailAt(int x, int y) {
        for (Segment segment : snakeParts) {
            if (segment.equals(new Segment(x, y, color))) {
                return false;
            }
        }
        return true;
    }

    private boolean isColliding(BaseObject object) {
        return head.getPoint().x < object.getPoint().x + object.getWidth() &&
                head.getPoint().x + head.getWidth() > object.getPoint().x &&
                head.getPoint().y < object.getPoint().y + object.getHeight() &&
                head.getHeight() + head.getPoint().y > object.getPoint().y;
    }

    private boolean isCollidingWithSnakeTail(Snake snake) {
        for (Segment segment : snake.getSnakeParts()) {
            if (head.getPoint().equals(segment.getPoint())) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollidingWithSnakeHead(Snake snake) {
        return head.getPoint().equals(snake.getHead().getPoint());
    }
}
