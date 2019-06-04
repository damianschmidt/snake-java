package snake.game.food;

import lombok.val;
import snake.game.Game;
import snake.game.Snake;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FoodMakesOtherOpponentsShorten extends Food {
    public FoodMakesOtherOpponentsShorten() throws IOException {
        super();
        image = ImageIO.read(new File("C:\\Users\\Damian Schmidt\\IdeaProjects\\snake\\snake_pwr\\src\\main\\resources\\kiwi.png"));
    }

    @Override
    public void eat(Snake snake) {
        super.eat(snake);
        Game.getInstance().getObjects()
                .stream()
                .filter(object -> this.hashCode() != object.hashCode())
                .forEach((object) -> {
                    if (object instanceof Snake) {
                        val otherSnake = ((Snake) object);
                        if(!otherSnake.equals(snake)) {
                            otherSnake.setTailLength(10);
                        }
                    }
                });
    }
}
