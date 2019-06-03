package snake.game;

import lombok.val;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hud implements UpdatePossible {
    private List<Integer> scores;

    Hud() {
        scores = new ArrayList<Integer>();
    }


    public void update(Graphics g) {
        getScores();
        String scoreString = "";
        for (int i = 0; i < scores.size(); i++){
            scoreString += "Player" + (i + 1) + ": " + scores.get(i) + "   ";
        }
        g.setColor(Color.WHITE);
        g.drawString(scoreString, 10, 10);
    }

    private void getScores() {
        scores.clear();
        Game.getInstance().getObjects()
                .stream()
                .filter(object -> this.hashCode() != object.hashCode())
                .forEach((object) -> {
                    if (object instanceof Snake) {
                        val snake = ((Snake) object);
                        scores.add(snake.getScore());
                    }
                });
    }
}
