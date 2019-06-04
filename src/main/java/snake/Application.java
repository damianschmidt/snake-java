package snake;

import snake.game.Game;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Game.getInstance().start();
    }
}
