package pkg;

import javax.swing.*;

import static pkg.Constants.*;

public class Main {
    public static void main(String args[]) {
        //separate class for game panel (maybe same as Constants)
        AsteroidsGame game = new AsteroidsGame();
        JFrame frame = new JFrame("AsteroidsGame");
        frame.setSize(WIDTH, HEIGHT);
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.startGame();
    }
}