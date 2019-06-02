package snake.game;

import java.awt.*;

public class Wall extends BaseObject {
    private int width, height;

    Wall(int x, int y, int width, int height) {
        this.point.x = x;
        this.point.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(point.x, point.y, width, height);
    }
}
