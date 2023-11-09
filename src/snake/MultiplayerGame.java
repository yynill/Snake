package snake;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// collsiion between snakes and food 

public class MultiplayerGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeihght;
    int tileSize = 25;

    Tile snakeHead;
    Tile snakeHead_2;

    ArrayList<Tile> snakeBody;
    ArrayList<Tile> snakeBody_2;

    Tile food;
    Random random;

    int velocityX;
    int velocityY;

    int velocityX_2;
    int velocityY_2;

    Timer gameLoop;
    boolean gameOver = false;
    boolean gameOver_2 = false;

    MultiplayerGame(int boardWidth, int boardHeihght) {
        this.boardWidth = boardWidth;
        this.boardHeihght = boardHeihght;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeihght));
        setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(19, 19);
        snakeHead_2 = new Tile(4, 4);

        snakeBody = new ArrayList<Tile>();
        snakeBody_2 = new ArrayList<Tile>();

        food = new Tile(0, 0);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = -1;
        velocityX_2 = 0;
        velocityY_2 = 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void resetGame() {
        snakeHead.x = 19;
        snakeHead.y = 19;
        snakeHead_2.x = 4;
        snakeHead_2.y = 4;

        snakeBody.clear();
        snakeBody_2.clear();

        placeFood();
        velocityX = -1;
        velocityY = 0;
        velocityX_2 = 1;
        velocityY_2 = 0;
        gameOver = false;
        gameOver_2 = false;
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
        g.setColor(new Color(124, 11, 43));
        g.fillRect(snakeHead_2.x * tileSize, snakeHead_2.y * tileSize, tileSize, tileSize);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            g.setColor(new Color(108, 190, 237));
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }
        for (int i = 0; i < snakeBody_2.size(); i++) {
            g.setColor(new Color(244, 144, 151));
            Tile snakePart = snakeBody_2.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        // Score
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver_2) {
            g.setColor(new Color(77, 144, 154));
            g.drawString("Game Over: - blue won with: " + String.valueOf(snakeBody.size()) + "-"
                    + String.valueOf(snakeBody_2.size()), tileSize - 16, tileSize);

        } else if (gameOver) {
            g.setColor(new Color(124, 11, 43));
            g.drawString("Game Over: - red won with: " + String.valueOf(snakeBody_2.size()) + "-"
                    + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);

        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeihght / tileSize);
    }

    public void move() {

        // eat food & collsision
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(snakeHead.x, snakeHead.y));
            placeFood();
        } else if (collision(snakeHead_2, food)) {
            snakeBody_2.add(new Tile(snakeHead_2.x, snakeHead_2.y));
            placeFood();
        }

        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { // after the head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        for (int i = snakeBody_2.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody_2.get(i);
            if (i == 0) { // after the head
                snakePart.x = snakeHead_2.x;
                snakePart.y = snakeHead_2.y;
            } else {
                Tile prevSnakePart = snakeBody_2.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        snakeHead_2.x += velocityX_2;
        snakeHead_2.y += velocityY_2;

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            } else if (collision(snakeHead_2, snakePart)) {
                gameOver_2 = true;
            }
        }
        for (int i = 0; i < snakeBody_2.size(); i++) {
            Tile snakePart = snakeBody_2.get(i);
            if (collision(snakeHead_2, snakePart)) {
                gameOver_2 = true;
            } else if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeihght) {
            gameOver = true;
        }
        if (snakeHead_2.x * tileSize < 0 || snakeHead_2.x * tileSize > boardWidth ||
                snakeHead_2.y * tileSize < 0 || snakeHead_2.y * tileSize > boardHeihght) {
            gameOver_2 = true;
        }

        if (gameOver || gameOver_2) {
            velocityX = 0;
            velocityY = 0;
            gameLoop.stop();
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

        if (e.getKeyCode() == KeyEvent.VK_W && velocityY_2 != 1) {
            velocityX_2 = 0;
            velocityY_2 = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_S && velocityY_2 != -1) {
            velocityX_2 = 0;
            velocityY_2 = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_A && velocityX_2 != 1) {
            velocityX_2 = -1;
            velocityY_2 = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_D && velocityX_2 != -1) {
            velocityX_2 = 1;
            velocityY_2 = 0;
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
