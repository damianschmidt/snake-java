package snake.game;

import lombok.Getter;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener, ActionListener {
    @Getter
    private static Game instance;
    private Canvas canvas;
    @Getter
    private Snake snake;
    static final int RECT_SCALE = 10;
    private Direction direction = Direction.DOWN;
    private int ticks;
    private JFrame jFrame;

    private Game() {
        initializeWindow();
        start();
    }

    public static void initialize() {
        if (instance == null) {
            instance = new Game();
        }
    }

    private void start() {
        snake = new Snake();
        Timer timer = new Timer(1, this);
        ticks = 0;
        timer.start();
    }

    private void initializeWindow() {
        jFrame = new JFrame("Snake");
        jFrame.setVisible(true);
        jFrame.setSize(800, 600);
        jFrame.setResizable(false);
        val dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getWidth() / 2, dim.height / 2 - jFrame.getHeight() / 2);
        jFrame.add(new Canvas());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addKeyListener(this);
        canvas = new Canvas();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        }
        if (i == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
        if (i == KeyEvent.VK_UP && direction != Direction.DOWN) {
            direction = Direction.UP;
        }
        if (i == KeyEvent.VK_DOWN && direction != Direction.UP) {
            direction = Direction.DOWN;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        canvas.repaint();
        ticks++;
        snake.update();
        if (ticks % 20 == 0) {
            if (direction == Direction.UP) {
                if (snake.getHead().y - 1 >= 0) {
                    snake.setHead(new Point(snake.getHead().x, snake.getHead().y - 1));
                }
            }
            if (direction == Direction.DOWN) {
                if (snake.getHead().y + 1 <= jFrame.getHeight() / RECT_SCALE) {
//                    System.out.println(snake.getHead());
                    snake.setHead(new Point(snake.getHead().x, snake.getHead().y + 1));
                }
            }
            if (direction == Direction.LEFT) {
                if (snake.getHead().x - 1 >= 0) {
                    snake.setHead(new Point(snake.getHead().x - 1, snake.getHead().y));
                }
            }
            if (direction == Direction.RIGHT) {
                if (snake.getHead().x + 1 <= jFrame.getWidth() / RECT_SCALE) {
                    snake.setHead(new Point(snake.getHead().x + 1, snake.getHead().y));
                }
            }
        }
    }
}
