package snake.game;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Snake {
    @Getter @Setter
    private Point head;
    @Getter
    private List<Point> snakeParts;
    private int tailLength;

    Snake() {
        head = new Point(0, 0);
        tailLength = 5;
//        direction = DOWN;
        snakeParts = new ArrayList<Point>();
    }

    void update() {
        snakeParts.add(new Point(head.x, head.y));
        if (snakeParts.size() > tailLength) {
            snakeParts.remove(0);
        }
    }

}
