package snake.game;

import lombok.Getter;
import lombok.val;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Snake {
    private static final int STARTING_TAIL_LENGTH = 10;
    @Getter
    private Point head;
    @Getter
    private List<Point> snakeParts;
    private int tailLength;

    Snake() {
        head = new Point(0, 0);
        tailLength = STARTING_TAIL_LENGTH;
        snakeParts = new ArrayList<Point>();
    }

    void update() {
        move();
        removeLastPart();
        eat();
    }

    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        for (val point : snakeParts) {
            g.fillRect(point.x * Game.RECT_SCALE, point.y * Game.RECT_SCALE, Game.RECT_SCALE, Game.RECT_SCALE);
        }
        g.fillRect(head.x * Game.RECT_SCALE, head.y * Game.RECT_SCALE,
                Game.RECT_SCALE, Game.RECT_SCALE);
    }

    private void move() {
        if (Game.getInstance().getDirection() == Direction.UP && noTailAt(head.x, head.y - 1)) {
            head = new Point(head.x, head.y - 1);
        } else if (Game.getInstance().getDirection() == Direction.DOWN && noTailAt(head.x, head.y + 1)) {
            head = new Point(head.x, head.y + 1);
        } else if (Game.getInstance().getDirection() == Direction.LEFT && noTailAt(head.x - 1, head.y)) {
            head = new Point(head.x - 1, head.y);
        } else if (Game.getInstance().getDirection() == Direction.RIGHT && noTailAt(head.x + 1, head.y)) {
            head = new Point(head.x + 1, head.y);
        } else {
            Game.getInstance().stop();
        }
    }

    private void removeLastPart() {
        snakeParts.add(new Point(head.x, head.y));
        if (snakeParts.size() > tailLength) {
            snakeParts.remove(0);
        }
    }

    private void eat() {
        if (head.equals(Game.getInstance().getFood().getObject())) {
            Game.getInstance().getFood().generateNewPosition();
            tailLength++;
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
}
