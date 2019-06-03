package snake.game;

import lombok.Getter;

class Record {
    @Getter
    private String name;
    @Getter
    private int score;

    Record(String name, int score) {
        this.name = name;
        this.score = score;
    }

    void printRecord(){
        System.out.println("Record: " + name + " " + score);
    }
}
