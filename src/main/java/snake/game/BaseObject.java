package snake.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@EqualsAndHashCode
abstract class BaseObject implements UpdatePossible {
    @Getter
    protected Point point;
    @Setter
    @Getter
    protected boolean removed;
    @Setter
    @Getter
    protected int width;
    @Setter
    @Getter
    protected int height;
    protected Color color;

    BaseObject(int x, int y, int width, int height, Color color) {
        this.point = new Point();
        this.point.x = x;
        this.point.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    BaseObject(int x, int y, int width, int height) {
        this(x, y, width, height, Color.MAGENTA);
    }

    BaseObject(int x, int y) {
        this(x, y, 1, 1, Color.MAGENTA);
    }

    BaseObject(Color color) {
        this(0, 0, 1, 1, color);
    }

    BaseObject() {
        this(0, 0);
    }
}
