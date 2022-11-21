package screen;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(100, 100, 20, 20);
    g.drawLine(0, 50, 400, 50);
  }
}
