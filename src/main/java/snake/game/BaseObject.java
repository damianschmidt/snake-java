package snake.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@EqualsAndHashCode
public abstract class BaseObject implements UpdatePossible {
    @Setter
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

    public BaseObject(int x, int y, int width, int height, Color color) {
        this.point = new Point();
        this.point.x = x;
        this.point.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public BaseObject(int x, int y, int width, int height) {
        this(x, y, width, height, new Color(77, 25, 25));
    }

    public BaseObject(int x, int y, Color color) {
        this(x, y, Game.RECT_SCALE, Game.RECT_SCALE, color);
    }
}
