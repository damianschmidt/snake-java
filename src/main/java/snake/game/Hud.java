package snake.game;

import lombok.val;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hud implements UpdatePossible {
    private List<Integer> scores;
    private List<String> names;

    Hud() {
        scores = new ArrayList<>();
        names = new ArrayList<>();
    }


    public void update(Graphics g) {
        getScores();
        String scoreString = createScoreString();
        g.setColor(Color.WHITE);
        g.drawString(scoreString, 10, 10);
        gameOver(g);
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
                        names.add(snake.getName());
                    }
                });
    }

    private String createScoreString() {
        String scoreString = "";
        for (int i = 0; i < scores.size(); i++){
            scoreString += names.get(i) + ": " + scores.get(i) + "    ";
        }
        return scoreString;
    }

    private void gameOver(Graphics g) {
        for (UpdatePossible object : Game.getInstance().getObjects()) {
            if (this.hashCode() != object.hashCode()) {
                if (object instanceof Snake) {
                    Snake snake = ((Snake) object);
                    if (snake.isDead()) {
                        String lost = snake.getName() + " lost!";
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                        g.setColor(snake.getColor());
                        g.drawString(lost, 30, Game.getHEIGHT() - 30);
                        break;
                    }
                }
            }
        }
    }
}
