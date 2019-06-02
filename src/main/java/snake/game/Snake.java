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
    private int tailLength;

    Snake() {
        head = new Point(1, 1);
        tailLength = STARTING_TAIL_LENGTH;
        snakeParts = new ArrayList<>();
    }

    public void update(Graphics g) {
        move();
        checkCollision();
        draw(g);
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
                            eat();
                            food.setRemoved(true);
                        }
                    }
                });
    }

    private void draw(Graphics g) {
        g.setColor(Color.BLUE);
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

    private void eat() {
        tailLength++;
    }
}
