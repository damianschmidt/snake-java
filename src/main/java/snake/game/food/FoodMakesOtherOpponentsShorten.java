package snake.game.food;

import lombok.val;
import snake.game.Game;
import snake.game.Snake;

import java.awt.*;

public class FoodMakesOtherOpponentsShorten extends Food {
    public FoodMakesOtherOpponentsShorten(){
        super(Color.CYAN);

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
                            otherSnake.setTailLength(5);
                        }
                    }
                });
    }
}
