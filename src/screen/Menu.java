package screen;

import javax.swing.*;
import model.Pacman;
import java.awt.*;

public class Menu extends JPanel {
  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, Pacman.SCREEN_WIDTH, Pacman.SCREEN_HEIGHT);

    g.setColor(new Color(255, 255, 0));
    Font font = new Font("Arial", Font.BOLD, 20);
    g.setFont(font);
    g.drawString("Welcome to Pacman !!!", 87, 50);

    font = new Font("Arial", Font.BOLD, 18);
    g.setFont(font);
    g.drawString("To start a game press N", 96, 150);
    g.drawString("To see the controls press A", 86, 200);
    g.drawString("To see the High scores press H", 75, 250);
    g.drawString("To exit press X", 130, 300);
  }
}
