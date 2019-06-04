package snake.game;

import java.awt.*;

public class Wall extends BaseObject {
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void update(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x, point.y, width, height);
    }
}
