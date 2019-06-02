package snake.game;

import javax.swing.*;
import java.awt.*;

class Canvas extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        Game.getInstance().getObjects()
                .forEach((object) -> object.update(g));
        Game.getInstance().getObjects()
                .removeIf(object -> object instanceof BaseObject && ((BaseObject)object).isRemoved());
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(4414545));
        g.fillRect(0, 0, 800, 700);
    }

}