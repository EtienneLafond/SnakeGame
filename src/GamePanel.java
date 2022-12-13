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
    char direction = 'R'; // Snake initially goes right
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {

    }

    public void startGame() {

    }

    public void paintComponant(Graphics g) {

    }

    public void draw(Graphics g) {

    }

    public void move() {

    }

    public void checkApple() {

    }

    public void checkCollision() {

    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }
}
