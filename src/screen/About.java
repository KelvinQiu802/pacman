package screen;

import javax.swing.*;

import model.Pacman;

import java.awt.*;

public class About extends JPanel {
  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, Pacman.SCREEN_WIDTH, Pacman.SCREEN_HEIGHT);

    g.setColor(new Color(255, 255, 0));
    Font font = new Font("Arial", Font.BOLD, 30);
    g.setFont(font);
    g.drawString("Pacman Controls", 75, 50);

    font = new Font("Arial", Font.BOLD, 18);
    g.setFont(font);
    g.drawString("Move Up: up arrow", 95, 150);
    g.drawString("Move Down: down arrow", 95, 200);
    g.drawString("Move Left: left arrow", 95, 250);
    g.drawString("Move Right: right arrow", 95, 300);
    g.drawString("Press 'M' to return to the Main Menu", 50, 400);
  }
}
