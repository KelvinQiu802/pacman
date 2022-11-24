package screen;

import javax.swing.*;

import model.Coordinate;
import model.Dot;
import model.Fruit;
import model.Maze;
import model.Pacman;
import model.Player;
import model.Power;

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

      drawPlayer(g, game.getPlayer());
      drawMaze(g, game.getMaze());
      drawDot(g, game.getDotList());
      drawFruit(g, game.getFruitList());
      drawPower(g, game.getPowerList());
    }
  }

  public void drawPlayer(Graphics g, Player p) {
    g.setColor(new Color(255, 0, 0));
    // g.fillRect(p.getX(), p.getY(), 10, 10);
    g.fillOval(p.getX(), p.getY(), 20, 20);
  }

  public void drawDot(Graphics g, ArrayList<Dot> dotList) {
    g.setColor(new Color(255, 255, 0));
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

  public void drawGhost() {
  }

  public void drawMaze(Graphics g, Maze maze) {
    // size: 20 * 20
    // init: (0, 50)
    int posX = 0;
    int posY = 50;
    int rows = maze.getRows();
    int cols = maze.getCols();
    ArrayList<String> mazeArr = maze.getMaze();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Coordinate cor = Coordinate.getIndex(i, j, cols);
        int index = cor.getIndex();

        switch (mazeArr.get(index)) {
          case "W": // Wall
            g.setColor(new Color(0, 0, 255));
            g.fillRect(posX, posY, 20, 20);
            break;
          case "G": // Ghost Spawn 暂时放在这里
            g.setColor(new Color(255, 255, 255));
            g.fillRect(posX, posY, 20, 20);
            break;
          case "-": // Empty Square Gohst can Pass
            g.setColor(new Color(128, 128, 128));
            g.fillRect(posX, posY, 20, 20);
            break;
        }
        posX += 20;
      }
      posX = 0;
      posY += 20;
    }
  }
}
