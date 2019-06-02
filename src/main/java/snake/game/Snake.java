package snake.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.val;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
class Snake implements UpdatePossible {
    private static final int STARTING_TAIL_LENGTH = 10;
    @Getter
    private Point head;
    @Getter
    private List<Point> snakeParts;
    private Color color;
    private int tailLength;
    private Direction[] direction;
    @Getter
    private boolean Dead;

    Snake(int x, int y, Direction[] direction, Color color) {
        head = new Point(x, y);
        tailLength = STARTING_TAIL_LENGTH;
        snakeParts = new ArrayList<>();
        this.direction = direction;
        this.color = color;
    }

    Snake(Direction[] direction) {
        this(1, 1, direction, Color.BLUE);
    }



    public void update(Graphics g) {
        move();
        checkCollision();
        draw(g);
    }

    private void move() {
        if (direction[0] == Direction.UP && noTailAt(head.x, head.y - 1)) {
            head = new Point(head.x, head.y - 1);
        } else if (direction[0] == Direction.DOWN && noTailAt(head.x, head.y + 1)) {
            head = new Point(head.x, head.y + 1);
        } else if (direction[0] == Direction.LEFT && noTailAt(head.x - 1, head.y)) {
            head = new Point(head.x - 1, head.y);
        } else if (direction[0] == Direction.RIGHT && noTailAt(head.x + 1, head.y)) {
            head = new Point(head.x + 1, head.y);
        } else {
            Dead = true;
        }

        removeLastPart();
    }

    private void checkCollision() {
        Game.getInstance().getObjects()
                .stream()
                .filter(object -> this.hashCode() != object.hashCode())
                .forEach((object) -> {
                    if(object instanceof Food) {
                        val food = ((Food) object);
                        if (head.equals(food.getPoint())){
                            eat(food.getPower());
                            food.setRemoved(true);
                        }
                    }
                    if(object instanceof Wall) {
                        val wall = ((Wall) object);
                        if (isColliding(wall)){
                            Dead = true;
                        }
                    }
                });
    }

    private void draw(Graphics g) {
        g.setColor(color);
        for (val point : snakeParts) {
            g.fillRect(point.x * Game.RECT_SCALE, point.y * Game.RECT_SCALE, Game.RECT_SCALE, Game.RECT_SCALE);
        }
        g.fillRect(head.x * Game.RECT_SCALE, head.y * Game.RECT_SCALE,
                Game.RECT_SCALE, Game.RECT_SCALE);
    }

    private void removeLastPart() {
        snakeParts.add(new Point(head.x, head.y));
        if (snakeParts.size() > tailLength) {
            snakeParts.remove(0);
        }
    }

    private boolean noTailAt(int x, int y) {
        for (Point point : snakeParts) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }

    private void eat(int power) {
        tailLength += power;
    }

    private boolean isColliding(BaseObject object) {
        return head.x < object.getPoint().x + object.getWidth() &&
                head.x + 1 > object.getPoint().x &&
                head.y < object.getPoint().y + object.getHeight() &&
                1 + head.y > object.getPoint().y;
    }
}
