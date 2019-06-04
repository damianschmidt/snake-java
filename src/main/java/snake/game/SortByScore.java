package snake.game;

import java.util.Comparator;

public class SortByScore implements Comparator<Record> {
    @Override
    public int compare(Record o1, Record o2) {
        return o2.getScore() - o1.getScore();
    }
}
