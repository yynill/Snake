package snake;

import javax.swing.JFrame;

public class App extends JFrame {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeihght = boardWidth;

        JFrame frame = new JFrame("Snake");

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeihght);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuPanel menuPanel = new MenuPanel(boardWidth, boardHeihght, frame);
        frame.add(menuPanel);
        frame.pack();
    }
}
