package snake.game;

import java.awt.*;

class FoodCreatesObstacle extends Food {
    FoodCreatesObstacle(){
        super(Color.PINK);
    }

    @Override
    void eat(Snake snake) {
        super.eat(snake);
        width = round(getRandom().nextInt(Game.getWIDTH() / 4 + 5));
        height = round(getRandom().nextInt(Game.getHEIGHT() / 4 + 5));
        generateNewPosition();
        Game.getInstance().getObjectsToAdd().add(new Wall(point.x, point.y, width, height));
    }
}
