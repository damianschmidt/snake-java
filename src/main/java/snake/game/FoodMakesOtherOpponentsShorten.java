package snake.game;

import lombok.val;

import java.awt.*;

class FoodMakesOtherOpponentsShorten extends Food {
    FoodMakesOtherOpponentsShorten(Color color){
        super(color);
    }

    @Override
    void eat(Snake snake) {
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
