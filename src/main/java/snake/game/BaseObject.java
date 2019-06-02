package snake.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@EqualsAndHashCode
abstract class BaseObject implements UpdatePossible {
    @Getter
    protected Point point;
    @Setter
    @Getter
    protected boolean removed;

    BaseObject() {
        point = new Point();
    }
}
