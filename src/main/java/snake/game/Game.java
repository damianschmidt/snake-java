package snake.game;

import lombok.Getter;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game implements KeyListener, ActionListener {
    static final int RECT_SCALE = 10;
    @Getter
    private static final int WIDTH = 800;
    @Getter
    private static final int HEIGHT = 600;
    private static Game instance;
    @Getter
    private List<UpdatePossible> objects;
    @Getter
    private JFrame jFrame;
    @Getter
    private Timer timer;
    @Getter
    private Direction[] playerOneDirection;
    @Getter
    private Direction[] playerTwoDirection;
    private Canvas canvas;
    @Getter
    private Ranking ranking;
    private int ticks;
    @Getter
    private boolean paused;

    private Game() {
        initializeWindow();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void start() {
        playerOneDirection = new Direction[]{Direction.UP};
        playerTwoDirection = new Direction[]{Direction.DOWN};
        ranking = new Ranking();
        objects = new ArrayList<>();
        objects.add(new Snake(RECT_SCALE, RECT_SCALE, playerTwoDirection, new Color(122, 155, 239), "Damian"));
        objects.add(new Snake(WIDTH - 2 *RECT_SCALE, HEIGHT - 2 * RECT_SCALE, playerOneDirection, new Color(255, 246, 143), "Seba"));
        objects.add(new Wall(0, 0, WIDTH, RECT_SCALE)); //TOP
        objects.add(new Wall(0, HEIGHT - RECT_SCALE, WIDTH, RECT_SCALE)); //BOTTOM
        objects.add(new Wall(0, 0, RECT_SCALE, HEIGHT)); //LEFT
        objects.add(new Wall(WIDTH - RECT_SCALE, 0, RECT_SCALE, HEIGHT)); //RIGHT
        objects.add(new FoodMakesSnakeMuchLonger(20, 20, 10));
        objects.add(new FoodMakesOtherOpponentsShorten());
        objects.add(new Hud());
        objects.add(ranking);
        timer = new Timer(10, this);
        ticks = 0;
        timer.start();
    }

    private void stop() {
        timer.stop();
    }

    private void restart() {
        timer.restart();
    }

    private void initializeWindow() {
        jFrame = new JFrame("Snake");
        jFrame.setVisible(true);
        jFrame.setSize(814, 635);
        jFrame.setResizable(false);
        val screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(screenSize.width / 2 - jFrame.getWidth() / 2, screenSize.height / 2 - jFrame.getHeight() / 2);
        jFrame.add(canvas = new Canvas());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
        val i = e.getKeyCode();
        if (i == KeyEvent.VK_LEFT && playerOneDirection[0] != Direction.RIGHT) {
            playerOneDirection[0] = Direction.LEFT;
        } else if (i == KeyEvent.VK_RIGHT && playerOneDirection[0] != Direction.LEFT) {
            playerOneDirection[0] = Direction.RIGHT;
        } else if (i == KeyEvent.VK_UP && playerOneDirection[0] != Direction.DOWN) {
            playerOneDirection[0] = Direction.UP;
        } else if (i == KeyEvent.VK_DOWN && playerOneDirection[0] != Direction.UP) {
            playerOneDirection[0] = Direction.DOWN;
        }

        if (i == KeyEvent.VK_A && playerTwoDirection[0] != Direction.RIGHT) {
            playerTwoDirection[0] = Direction.LEFT;
        } else if (i == KeyEvent.VK_D && playerTwoDirection[0] != Direction.LEFT) {
            playerTwoDirection[0] = Direction.RIGHT;
        } else if (i == KeyEvent.VK_W && playerTwoDirection[0] != Direction.DOWN) {
            playerTwoDirection[0] = Direction.UP;
        } else if (i == KeyEvent.VK_S && playerTwoDirection[0] != Direction.UP) {
            playerTwoDirection[0] = Direction.DOWN;
        }

        if (i == KeyEvent.VK_SPACE ) {
            stop();
            start();
        }

        if (i == KeyEvent.VK_R && !paused) {
            paused = true;
            canvas.repaint();
            stop();
        } else if (i == KeyEvent.VK_R) {
            paused = false;
            restart();
        }
    }

    private void gameOver() {
        objects.stream()
                .filter(object -> object instanceof Snake)
                .filter(object -> ((Snake) object).isDead())
                .forEach(object -> {
                    stop();
                    ranking.saveRankingToFile();
                    addAliveToRanking();
                });
    }

    private void addAliveToRanking() {
        objects.stream()
                .filter(object -> object instanceof Snake)
                .filter(object -> !((Snake) object).isDead())
                .forEach(object -> {
                    val snake = ((Snake) object);
                    Record record = new Record(snake.getName(), snake.getScore());
                    ranking.addToRanking(record);
                });
    }

    public void actionPerformed(ActionEvent e) {
        ticks++;
        gameOver();
        if (ticks % 5 == 0) {
            if (ticks % 1000 == 0) {
                objects.add(new FoodMakesOtherOpponentsShorten());
            }

            if (objects.stream().noneMatch(object -> object instanceof Food)) {
                objects.add(new Food());
            }

            canvas.repaint();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
