package snake.game;

import lombok.Getter;
import lombok.val;
import snake.game.food.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game implements KeyListener, ActionListener {
    public static final int RECT_SCALE = 20;
    @Getter
    private static final int WIDTH = 800;
    @Getter
    private static final int HEIGHT = 600;
    private static Game instance;
    @Getter
    private List<UpdatePossible> objects;
    @Getter
    private List<UpdatePossible> objectsToAdd;
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
    @Getter
    private Hud hud;
    private int ticks;
    @Getter
    private boolean paused, over, keyPressedOne, keyPressedTwo;

    private Game() {
        initializeWindow();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void start() throws IOException {
        over = false;
        paused = false;
        playerOneDirection = new Direction[]{Direction.UP};
        playerTwoDirection = new Direction[]{Direction.DOWN};
        BufferedImage headImageOne = ImageIO.read(new File("C:\\Users\\Damian Schmidt\\IdeaProjects\\snake\\snake_pwr\\src\\main\\resources\\snakeOneHead.png"));
        BufferedImage headImageTwo = ImageIO.read(new File("C:\\Users\\Damian Schmidt\\IdeaProjects\\snake\\snake_pwr\\src\\main\\resources\\snakeTwoHead.png"));
        objects = new ArrayList<>();
        objectsToAdd = new ArrayList<>();
        objects.add(new Wall(0, 0, WIDTH, RECT_SCALE)); //TOP
        objects.add(new Wall(0, HEIGHT - RECT_SCALE, WIDTH, RECT_SCALE)); //BOTTOM
        objects.add(new Wall(0, 0, RECT_SCALE, HEIGHT)); //LEFT
        objects.add(new Wall(WIDTH - RECT_SCALE, 0, RECT_SCALE, HEIGHT)); //RIGHT
//        objects.add(new Snake(RECT_SCALE, RECT_SCALE, playerTwoDirection, new Color(122, 155, 239), "Damian", headImageOne));
        objects.add(new Snake(WIDTH - 2 * RECT_SCALE, HEIGHT - 2 * RECT_SCALE, playerOneDirection, new Color(255, 246, 143), "WegeWonsz", headImageTwo));
        ranking = new Ranking();
        hud = new Hud();
        timer = new Timer(1000 / 60, this);
        ticks = 0;
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        ticks++;
        if (ticks % 5 == 0) {
            gameOver();
            if (ticks % 500 == 0) {
                try {
                    objectsToAdd.add(new Food());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (ticks % 1000 == 0) {
                try {
                    objectsToAdd.add(new FoodMakesSnakeMuchLonger(15));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (ticks % 1300 == 0) {
                try {
                    objectsToAdd.add(new FoodCreatesObstacle());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (ticks % 1800 == 0) {
                try {
                    objectsToAdd.add(new FoodMakesOtherOpponentsShorten());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (objects.stream().noneMatch(object -> object instanceof Food)) {
                try {
                    objects.add(new Food());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            canvas.repaint();
            keyPressedOne = false;
            keyPressedTwo = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    private void stop() {
        timer.stop();
    }

    private void restart() {
        if (!over) {
            timer.restart();
        }
    }

    private void initializeWindow() {
        jFrame = new JFrame("Snake");
        jFrame.setVisible(true);
        jFrame.setSize(814, 638);
        jFrame.setResizable(false);
        val screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(screenSize.width / 2 - jFrame.getWidth() / 2, screenSize.height / 2 - jFrame.getHeight() / 2);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addKeyListener(this);
        jFrame.add(canvas = new Canvas());
    }

    public void keyPressed(KeyEvent e) {
        val i = e.getKeyCode();
        if (!keyPressedOne) {
            if (i == KeyEvent.VK_LEFT && playerOneDirection[0] != Direction.RIGHT) {
                playerOneDirection[0] = Direction.LEFT;
                keyPressedOne = true;
            } else if (i == KeyEvent.VK_RIGHT && playerOneDirection[0] != Direction.LEFT) {
                playerOneDirection[0] = Direction.RIGHT;
                keyPressedOne = true;
            } else if (i == KeyEvent.VK_UP && playerOneDirection[0] != Direction.DOWN) {
                playerOneDirection[0] = Direction.UP;
                keyPressedOne = true;
            } else if (i == KeyEvent.VK_DOWN && playerOneDirection[0] != Direction.UP) {
                playerOneDirection[0] = Direction.DOWN;
                keyPressedOne = true;
            }
        }

        if (!keyPressedTwo) {
            if (i == KeyEvent.VK_A && playerTwoDirection[0] != Direction.RIGHT) {
                playerTwoDirection[0] = Direction.LEFT;
                keyPressedTwo = true;
            } else if (i == KeyEvent.VK_D && playerTwoDirection[0] != Direction.LEFT) {
                playerTwoDirection[0] = Direction.RIGHT;
                keyPressedTwo = true;
            } else if (i == KeyEvent.VK_W && playerTwoDirection[0] != Direction.DOWN) {
                playerTwoDirection[0] = Direction.UP;
                keyPressedTwo = true;
            } else if (i == KeyEvent.VK_S && playerTwoDirection[0] != Direction.UP) {
                playerTwoDirection[0] = Direction.DOWN;
                keyPressedTwo = true;
            }
        }

        if (i == KeyEvent.VK_SPACE) {
            stop();
            try {
                start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        if (!over) {
            objects.stream()
                    .filter(object -> object instanceof Snake)
                    .filter(object -> ((Snake) object).isDead())
                    .forEach(object -> {
                        stop();
                        addSnakeToRanking();
                        ranking.saveRankingToFile();
                        over = true;
                    });
        }
    }

    private void addSnakeToRanking() {
        objects.stream()
                .filter(object -> object instanceof Snake)
                .forEach(object -> {
                    val snake = ((Snake) object);
                    Record record = new Record(snake.getName(), snake.getScore());
                    ranking.addToRanking(record);
                });
    }
}
