package snake.game;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Snake {
    @Getter
    @Setter
    private Point head;
    @Getter
    private List<Point> snakeParts;
    private int tailLength;

    Snake() {
        head = new Point(0, 0);
        tailLength = 10;
        snakeParts = new ArrayList<Point>();
    }

    void update() {
        snakeParts.add(new Point(head.x, head.y));
        if (snakeParts.size() > tailLength) {
            snakeParts.remove(0);
        }
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

    void move() {
        if (Game.getInstance().getDirection() == Direction.UP && noTailAt(head.x, head.y - 1)) {
            head = new Point(head.x, head.y - 1);
        } else if (Game.getInstance().getDirection() == Direction.DOWN && noTailAt(head.x, head.y + 1)) {
            head = new Point(head.x, head.y + 1);
        } else if (Game.getInstance().getDirection() == Direction.LEFT && noTailAt(head.x - 1, head.y)) {
            head = new Point(head.x - 1, head.y);
        } else if (Game.getInstance().getDirection() == Direction.RIGHT && noTailAt(head.x + 1, head.y)) {
            head = new Point(head.x + 1, head.y);
        } else {
            Game.getInstance().getTimer().stop();
        }
    }
}
