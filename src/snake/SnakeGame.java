package snake;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    JFrame frame;

    int boardWidth;
    int boardHeihght;
    int tileSize = 25;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    Tile food;
    Random random;

    Timer gameLoop;
    int velocityX;
    int velocityY;

    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeihght) {
        this.boardWidth = boardWidth;
        this.boardHeihght = boardHeihght;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeihght));
        setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(11, 11);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(0, 0);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void resetGame() {
        snakeHead = new Tile(11, 11);
        snakeBody.clear();
        placeFood();
        velocityX = 1;
        velocityY = 0;
        gameOver = false;
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // board grid
        g.setColor(new Color(162, 208, 76));
        g.fillRect(0, 0, boardWidth, boardHeihght);

        int rows = boardWidth / tileSize;
        int cols = boardHeihght / tileSize;

        int tileSize = Math.min(getWidth() / cols, getHeight() / rows);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(new Color(169, 215, 81));
                    g.fillRect(row * tileSize, col * tileSize, tileSize, tileSize);
                } else {

                }
            }
        }

        draw(g);
    }

    public void draw(Graphics g) {
        // food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // Snake Head
        g.setColor(new Color(77, 144, 154));
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            g.setColor(new Color(108, 190, 237));
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        // Score
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeihght / tileSize);
    }

    public void move() {
        // snake head

        // eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(snakeHead.x, snakeHead.y));
            placeFood();
        }

        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { // right before the head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeihght) {
            gameOver = true;
        }

        if (gameOver) {
            velocityX = 0;
            velocityY = 0;
            gameLoop.stop();

            // comment
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y; // returns true
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    };

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    // not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
