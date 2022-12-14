import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6; // Initial size of the snake
    int applesEaten;
    int appleX; // xCoord of the apple
    int appleY; // yCoord of the apple
    int badApplesEaten;
    int badAppleX; // xCoord of the apple
    int badAappleY; // yCoord of the apple
    char direction = 'R'; // Snake initially goes right
    boolean running = false;
    Timer timer;
    Random random;

    /**
     * GamePanel Constructor
     * Creates the game panel and starts the game
     */
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    /**
     * Creates a new apple and starts the timer to refresh the game every 75 ms (DELAY)
     */
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Paints the components on the game panel
     * @param g - the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draw the apples, snake, score and Game Over when the player loses
     * @param g - the <code>Graphics</code> object to protect
     */
    public void draw(Graphics g) {
        if(running) {
            /* Drawing a grid to make it easier to see (TEMPORARY)
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            */

            // Draw the apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Draw the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    /* Random color snake???
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    */
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // Game score text
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    /**
     * Creates a new apple at random coordinates on the game panel
     */
    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    /**
     * Moves the snake's head by 1 UNIT_SIZE and shift its body
     */
    public void move() {
        // Shifting each bodyPart left by 1
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    /**
     * Check if the snake eats (go over/hit) an apple, increments his body parts and number of apple eaten (Score)
     */
    public void checkApple() {
        if(x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    /**
     * CHeck for collision with the edges of the panel. Ends the game if it does.
     */
    public void checkCollision() {
        // Check if the snake collides with its body
        for (int i = bodyParts; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        // Check if the snake collides with left border
        if(x[0] < 0) {
            running = false;
        }
        // Check if the snake collides with right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // Check if the snake collides with top border
        if(y[0] < 0) {
            running = false;
        }
        // Check if the snake collides with bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }

    /**
     * Prints the score and Game Over message when the player hits the edge of the panel.
     * @param g
     */
    public void gameOver(Graphics g) {
        // Game score text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2,
                g.getFont().getSize());
        // Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    /**
     * Check for the key pressed by the player and change the direction of the snake accordingly.
     * The snake can't go back on himself (ex. If going left, can't go right next. It has to go up or down first
     * before going right.)
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch ((e.getKeyCode())) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
