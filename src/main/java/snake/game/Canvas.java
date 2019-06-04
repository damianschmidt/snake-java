package snake.game;

import javax.swing.*;
import java.awt.*;

class Canvas extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            drawBackground(g);
            Game.getInstance().getObjects()
                    .forEach((object) -> object.update(g));
            Game.getInstance().getObjectsToAdd()
                    .forEach(objectToAdd -> Game.getInstance().getObjects().add(objectToAdd));
            Game.getInstance().getHud().update(g);
            Game.getInstance().getRanking().update(g);
            Game.getInstance().getObjectsToAdd().clear();
            Game.getInstance().getObjects()
                    .removeIf(object -> object instanceof BaseObject && ((BaseObject) object).isRemoved());
        }
        catch (NullPointerException e){
            System.out.println("List of objects is empty!");
        }
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(38, 38, 38));
        g.fillRect(0, 0, Game.getWIDTH(), Game.getHEIGHT());
    }
}