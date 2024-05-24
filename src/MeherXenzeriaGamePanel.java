import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MeherXenzeriaGamePanel extends JPanel implements ActionListener {

    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 400;
    private static final int UNIT_SIZE = 20;
    private static final int ALL_UNITS = (BOARD_WIDTH * BOARD_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 140; // Double the original delay


    private final LinkedList<Position> snake = new LinkedList<>();
    private Position food;
    private Position strawberry; // Added
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private final Random random;

    MeherXenzeriaGamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    private void startGame() {
        snake.clear();
        snake.add(new Position(0, 0));
        snake.add(new Position(UNIT_SIZE, 0));
        snake.add(new Position(2 * UNIT_SIZE, 0));
        newFood();
        newStrawberry(); // Added
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillRect(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            // Draw strawberry
            g.setColor(Color.MAGENTA);
            g.fillRect(strawberry.x, strawberry.y, UNIT_SIZE, UNIT_SIZE);

            for (Position position : snake) {
                if (position.equals(snake.getFirst())) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(position.x, position.y, UNIT_SIZE, UNIT_SIZE);
            }
        } else {
            gameOver(g);
        }
    }

    private void newFood() {
        int x = random.nextInt(BOARD_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int y = random.nextInt(BOARD_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        food = new Position(x, y);
    }

    private void newStrawberry() {
        int x = random.nextInt(BOARD_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int y = random.nextInt(BOARD_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        strawberry = new Position(x, y);
    }

    private void move() {
        Position head = snake.getFirst();
        Position newHead = new Position(head.x, head.y);

        switch (direction) {
            case 'U':
                newHead.y -= UNIT_SIZE;
                break;
            case 'D':
                newHead.y += UNIT_SIZE;
                break;
            case 'L':
                newHead.x -= UNIT_SIZE;
                break;
            case 'R':
                newHead.x += UNIT_SIZE;
                break;
        }

        snake.addFirst(newHead);
        if (newHead.equals(food)) {
            // Generate new food
            newFood();
        } else if (newHead.equals(strawberry)) { // If snake eats strawberry
            snake.addLast(new Position(-1, -1)); // Add a dummy tail segment
            newStrawberry(); // Generate new strawberry
        } else {
            snake.removeLast();
        }
    }

    private void checkCollisions() {
        // No collision check needed for this implementation
    }

    private void gameOver(Graphics g) {
        String message = "Game Over";
        Font font = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(message, (BOARD_WIDTH - metrics.stringWidth(message)) / 2, BOARD_HEIGHT / 2);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (running) {
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollisions();
        }
        repaint();
    }

    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
