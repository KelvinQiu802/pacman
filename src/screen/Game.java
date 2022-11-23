package screen;

import javax.swing.*;
import model.Pacman;
import model.Player;
import java.awt.*;

public class Game extends JPanel {
  private Pacman game;

  public Game(Pacman game) {
    this.game = game;
  }

  public void paintComponent(Graphics g) {
    if (game != null) {
      g.setColor(new Color(0, 0, 0));
      g.fillRect(100, 100, 20, 20);

      drawPlayer(g, game.getPlayer());
    }
  }

  public void drawPlayer(Graphics g, Player p) {
    g.setColor(new Color(255, 255, 0));
    g.fillRect(p.getX(), p.getY(), 10, 10);
  }
}
