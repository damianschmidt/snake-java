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
    private static Game instance;
    private Canvas canvas;
    @Getter
    private Snake snake;
    @Getter
    private JFrame jFrame;
    @Getter
    private Food food;
    @Getter
    private Timer timer;
    static final int RECT_SCALE = 10;
    @Getter
    private Direction direction = Direction.DOWN;
    private int ticks;
    private boolean keyPressed = false;

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
        food = new Food();
        snake = new Snake();
        timer = new Timer(1, this);
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
        jFrame.add(canvas = new Canvas());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addKeyListener(this);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (!keyPressed) {
            keyPressed = true;
            if (i == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (i == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            } else if (i == KeyEvent.VK_UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            } else if (i == KeyEvent.VK_DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        canvas.repaint();
        ticks++;
        if (ticks % 20 == 0) {
            snake.update();
            snake.move();
            keyPressed = false;
        }
    }
}
