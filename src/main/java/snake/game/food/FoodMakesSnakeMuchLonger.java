package snake.game.food;

import snake.game.Snake;

import java.awt.*;

class FoodMakesSnakeMuchLonger extends Food {
    FoodMakesSnakeMuchLonger(int width, int height, int power){
        super(width, height, Color.BLUE);
        setPower(power);
    }

    @Override
    public void eat(Snake snake) {
        super.eat(snake);
    }
}
