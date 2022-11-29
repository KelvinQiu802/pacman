package screen;

import javax.swing.*;
import model.Pacman;
import ucd.comp2011j.engine.ScoreKeeper;
import java.awt.*;

public class Score extends JPanel {
  private ScoreKeeper sk;

  public Score(ScoreKeeper sk) {
    this.sk = sk;
  }

  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, Pacman.SCREEN_WIDTH, Pacman.SCREEN_HEIGHT);

    g.setColor(new Color(255, 255, 0));
    Font font = new Font("Arial", Font.BOLD, 30);
    g.setFont(font);
    g.drawString("Hall of Frame !!!", 90, 50);

    font = new Font("Arial", Font.BOLD, 18);
    g.setFont(font);
    int y = 100;
    for (ucd.comp2011j.engine.Score s : sk.getScores()) {
      g.drawString(s.getName() + ": " + s.getScore(), 150, y);
      y += 30;
    }
    g.drawString("Press 'M' to return to the Main Menu", 50, 460);
  }
}
