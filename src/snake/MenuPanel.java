package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	int boardWidth;
	int boardHeihght;
	int tileSize = 25;
	JFrame frame;

	SnakeGame snakeGame;
	MultiplayerGame multiplayerGame;

	public MenuPanel(int boardWidth, int boardHeihght, JFrame frame) {
		this.boardWidth = boardWidth;
		this.boardHeihght = boardHeihght;

		setPreferredSize(new Dimension(boardWidth, boardHeihght));
		setBackground(Color.BLACK);
		setLayout(null);

		// initialize games
		snakeGame = new SnakeGame(boardWidth, boardHeihght);
		multiplayerGame = new MultiplayerGame(boardWidth, boardHeihght);

		JButton singelPlayerBtn = new JButton("SinglePlayer");
		singelPlayerBtn.setBounds(175, 300, 100, 40);
		singelPlayerBtn.setBackground(new Color(52, 152, 219));
		singelPlayerBtn.setForeground(Color.WHITE);
		singelPlayerBtn.setBackground(Color.BLUE);
		singelPlayerBtn.setBorder(BorderFactory.createRaisedBevelBorder());
		singelPlayerBtn.setFocusPainted(false);

		JButton multyPlayerBtn = new JButton("1 v 1");
		multyPlayerBtn.setLayout(null);
		multyPlayerBtn.setBounds(325, 300, 100, 40);
		multyPlayerBtn.setForeground(Color.WHITE);
		multyPlayerBtn.setBackground(Color.BLUE);
		multyPlayerBtn.setBorder(BorderFactory.createBevelBorder(0));

		JButton aiBtn = new JButton("Ai");
		aiBtn.setLayout(null);
		aiBtn.setBounds(175, 360, 100, 40);
		aiBtn.setForeground(Color.WHITE);
		aiBtn.setBackground(Color.BLUE);
		aiBtn.setBorder(BorderFactory.createBevelBorder(0));

		JButton multiplayerAiBtn = new JButton("You v AI");
		multiplayerAiBtn.setLayout(null);
		multiplayerAiBtn.setBounds(325, 360, 100, 40);
		multiplayerAiBtn.setForeground(Color.WHITE);
		multiplayerAiBtn.setBackground(Color.BLUE);
		multiplayerAiBtn.setBorder(BorderFactory.createBevelBorder(0));

		singelPlayerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("singelPlayerBtn - button clicked");
				frame.remove(MenuPanel.this);
				frame.add(snakeGame);
				frame.revalidate(); // Update the frame
				frame.repaint(); // Repaint the frame
				snakeGame.requestFocus(); // Set focus on SnakeGame
				snakeGame.resetGame();
			}
		});

		multyPlayerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("multyPlayerBtn - button clicked");
				frame.remove(MenuPanel.this);
				frame.add(multiplayerGame);
				frame.revalidate();
				frame.repaint();
				multiplayerGame.requestFocus();
				multiplayerGame.resetGame();

			}
		});

		aiBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("aiBtn - button clicked");
			}
		});

		multiplayerAiBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("multiplayerAiBtn - button clicked");
			}
		});

		add(singelPlayerBtn);
		add(multyPlayerBtn);
		add(aiBtn);
		add(multiplayerAiBtn);
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
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.PLAIN, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("SNAKE", (boardWidth - metrics.stringWidth("SNAKE")) / 2, 200);
	}
}