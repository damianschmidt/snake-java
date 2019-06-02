package snake.game;

import lombok.Getter;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener, ActionListener {
    static final int RECT_SCALE = 10;
    private static Game instance;
    @Getter
    private List<UpdatePossible> objects;
    @Getter
    private JFrame jFrame;
    @Getter
    private Timer timer;
    @Getter
    private Direction direction = Direction.DOWN;
    private Canvas canvas;
    private int ticks;
    private boolean keyPressed;

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
        objects = new ArrayList<>();
        objects.add(new Food());
        objects.add(new Snake());
        objects.add(new Wall(0, 0, jFrame.getWidth(), RECT_SCALE));
        objects.add(new Wall(0, jFrame.getHeight() - 45, jFrame.getWidth(), RECT_SCALE));
        objects.add(new Wall(0, 0, RECT_SCALE, jFrame.getHeight()));
        objects.add(new Wall(jFrame.getWidth() - 24, 0, RECT_SCALE, jFrame.getHeight()));
        timer = new Timer(10, this);
        ticks = 0;
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    private void initializeWindow() {
        jFrame = new JFrame("Snake");
        jFrame.setVisible(true);
        jFrame.setSize(800, 600);
        jFrame.setResizable(false);
        val screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(screenSize.width / 2 - jFrame.getWidth() / 2, screenSize.height / 2 - jFrame.getHeight() / 2);
        jFrame.add(canvas = new Canvas());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
        val i = e.getKeyCode();
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

    public void actionPerformed(ActionEvent e) {
        ticks++;
        if (ticks % 5 == 0) {
            if (ticks % 1000 == 0) {
                objects.add(new Food());
            }

            if (objects.stream().noneMatch(object -> object instanceof Food)) {
                objects.add(new Food());
            }

            canvas.repaint();
            keyPressed = false;
        }


    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
