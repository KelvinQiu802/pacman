package screen;

import javax.swing.*;

import model.AirWall;
import model.Dot;
import model.Fruit;
import model.Ghost;
import model.Pacman;
import model.Player;
import model.Power;
import model.Wall;

import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel {
  private Pacman game;

  public Game(Pacman game) {
    this.game = game;
  }

  public void paintComponent(Graphics g) {
    if (game != null) {
      g.setColor(new Color(0, 0, 0));
      g.fillRect(0, 0, Pacman.SCREEN_WIDTH, Pacman.SCREEN_HEIGHT);

      drawWall(g, game.getWallList());
      drawAirWall(g, game.getAirWallList());
      drawDot(g, game.getDotList());
      drawFruit(g, game.getFruitList());
      drawPower(g, game.getPowerList());
      drawGhost(g, game.getGhostList());
      drawPlayer(g, game.getPlayer());

      drawInfo(g);

      if (game.isPaused()) {
        drawPause(g);
      }
    }
  }

  public void drawPlayer(Graphics g, Player p) {
    g.setColor(new Color(255, 255, 0));
    g.fillOval(p.getX() + 1, p.getY() + 1, 20, 20);
  }

  public void drawDot(Graphics g, ArrayList<Dot> dotList) {
    g.setColor(new Color(255, 184, 151));
    for (Dot dot : dotList) {
      int posX = dot.getCol() * 20;
      int posY = dot.getRow() * 20 + 50;
      g.fillOval(posX + 7, posY + 7, 6, 6);
    }
  }

  public void drawFruit(Graphics g, ArrayList<Fruit> fruitList) {
    g.setColor(new Color(237, 175, 31));
    for (Fruit fruit : fruitList) {
      int posX = fruit.getCol() * 20;
      int posY = fruit.getRow() * 20 + 50;
      g.fillOval(posX + 3, posY + 3, 14, 14);
    }
  }

  public void drawPower(Graphics g, ArrayList<Power> powerList) {
    g.setColor(new Color(135, 206, 255));
    for (Power power : powerList) {
      int posX = power.getCol() * 20;
      int posY = power.getRow() * 20 + 50;
      g.fillOval(posX + 3, posY + 3, 14, 14);
    }
  }

  public void drawGhost(Graphics g, ArrayList<Ghost> ghostList) {
    Color[] colors = new Color[4];
    colors[0] = new Color(255, 0, 0);
    colors[1] = new Color(255, 184, 222);
    colors[2] = new Color(255, 184, 71);
    colors[3] = new Color(0, 255, 222);
    for (int i = 0; i < ghostList.size(); i++) {
      g.setColor(colors[i]);
      Ghost p = ghostList.get(i);
      g.fillOval(p.getX(), p.getY(), 20, 20);
    }
  }

  public void drawWall(Graphics g, ArrayList<Wall> wallList) {
    g.setColor(new Color(30, 30, 202));
    for (Wall wall : wallList) {
      int posX = wall.getCol() * 20;
      int posY = wall.getRow() * 20 + 50;
      g.fillRect(posX, posY, 20, 20);
    }
  }

  public void drawAirWall(Graphics g, ArrayList<AirWall> wallList) {
    g.setColor(new Color(115, 115, 115));
    for (AirWall wall : wallList) {
      int posX = wall.getCol() * 20;
      int posY = wall.getRow() * 20 + 50;
      g.fillRect(posX, posY, 20, 20);
    }
  }

  public void drawPause(Graphics g) {
    g.setColor(new Color(255, 255, 0));
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("Press 'p' to continue ", 80, 280);
  }

  public void drawInfo(Graphics g) {
    g.setColor(new Color(255, 255, 255));
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("Level: " + game.getLevel(), 20, 40);
    g.drawString("Lives: " + game.getLives(), 120, 40);
    g.drawString("Scores: " + game.getPlayerScore(), 220, 40);
  }
}
