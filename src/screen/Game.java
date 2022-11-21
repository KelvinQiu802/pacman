package screen;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {
  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(100, 100, 20, 20);
  }
}
