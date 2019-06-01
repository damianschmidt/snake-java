package snake.game;

import lombok.val;

import javax.swing.*;
import java.awt.*;

class Canvas extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(4414545));
        g.fillRect(0, 0, 800, 700);
        g.setColor(Color.BLUE);
        for (val point : Game.getInstance().getSnake().getSnakeParts()) {
            g.fillRect(point.x * Game.RECT_SCALE, point.y * Game.RECT_SCALE, Game.RECT_SCALE, Game.RECT_SCALE);
        }
        g.fillRect(Game.getInstance().getSnake().getHead().x * Game.RECT_SCALE,
                Game.getInstance().getSnake().getHead().y * Game.RECT_SCALE,
                Game.RECT_SCALE, Game.RECT_SCALE);
        g.setColor(Color.RED);
        g.fillRect(Game.getInstance().getFood().getObject().x * Game.RECT_SCALE,
                Game.getInstance().getFood().getObject().y * Game.RECT_SCALE,
                Game.RECT_SCALE, Game.RECT_SCALE);

    }

}