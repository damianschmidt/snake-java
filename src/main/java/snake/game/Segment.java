package snake.game;

import java.awt.*;

public class Segment extends BaseObject {
    Segment(int x, int y, Color color) {
        super(x, y, color);
    }

    public void update(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x, point.y, width, height);
    }
}
