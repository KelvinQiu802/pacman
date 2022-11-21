import javax.swing.*;
import model.Pacman;
import screen.About;
import screen.Game;
import screen.Menu;
import screen.MenuListener;
import screen.PlayerListener;
import screen.Score;
import ucd.comp2011j.engine.GameManager;
import ucd.comp2011j.engine.ScoreKeeper;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame mainWindow = new JFrame();
        mainWindow.setSize(800, 800);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setTitle("Space Invaders Game");
        mainWindow.setLocationRelativeTo(null);

        PlayerListener playerListener = new PlayerListener();
        mainWindow.addKeyListener(playerListener);
        MenuListener menuListener = new MenuListener();
        mainWindow.addKeyListener(menuListener);

        Pacman pacman = new Pacman(playerListener);
        ScoreKeeper scoreKeeper = new ScoreKeeper("scores.txt");

        Menu menu = new Menu();
        About about = new About();
        Score score = new Score();
        Game game = new Game();

        GameManager mm = new GameManager(pacman, mainWindow, menuListener, menu, about, score, game, scoreKeeper);

        mainWindow.setVisible(true);
        mm.run();
    }
}
