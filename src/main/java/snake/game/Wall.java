package snake.game;

import java.awt.*;

public class Wall extends BaseObject {
    Wall(int x, int y, int width, int height) {
        super(x / Game.RECT_SCALE, y / Game.RECT_SCALE, width / Game.RECT_SCALE, height / Game.RECT_SCALE);
    }

    public void update(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x * Game.RECT_SCALE, point.y * Game.RECT_SCALE, width * Game.RECT_SCALE, height * Game.RECT_SCALE);
    }
}
