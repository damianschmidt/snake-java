package snake.game.food;

import snake.game.Game;
import snake.game.Snake;
import snake.game.Wall;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FoodCreatesObstacle extends Food {
    public FoodCreatesObstacle() throws IOException {
        super();
        image = ImageIO.read(new File("C:\\Users\\Damian Schmidt\\IdeaProjects\\snake\\snake_pwr\\src\\main\\resources\\orange.png"));
    }

    @Override
    public void eat(Snake snake) {
        super.eat(snake);
        width = round(getRandom().nextInt(Game.getWIDTH() / 4 + 5));
        height = round(getRandom().nextInt(Game.getHEIGHT() / 4 + 5));
        generateNewPosition();
        Game.getInstance().getObjectsToAdd().add(new Wall(point.x, point.y, width, height));
    }
}
