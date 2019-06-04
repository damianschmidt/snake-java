package snake.game.food;

import snake.game.Game;
import snake.game.Snake;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FoodMakesSnakeMuchLonger extends Food {
    public FoodMakesSnakeMuchLonger(int power) throws IOException {
        super(Game.RECT_SCALE * 2, Game.RECT_SCALE * 2);
        setPower(power);
        image = ImageIO.read(new File("C:\\Users\\Damian Schmidt\\IdeaProjects\\snake\\snake_pwr\\src\\main\\resources\\grapes.png"));
    }

    @Override
    public void eat(Snake snake) {
        super.eat(snake);
    }
}
