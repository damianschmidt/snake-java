package snake.game;

import java.awt.*;

class FoodMakesSnakeMuchLonger extends Food {
    FoodMakesSnakeMuchLonger(int width, int height, int power){
        super(width, height, Color.BLUE);
        setPower(power);
    }

    @Override
    void eat(Snake snake) {
        super.eat(snake);
    }
}
