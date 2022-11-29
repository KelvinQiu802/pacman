package model;

import java.awt.*;

public class Power implements Eattable {
  private int row;
  private int col;
  private int index;
  private Rectangle hitBox;

  public Power(int index, Maze maze) {
    this.index = index;
    this.row = Coordinate.getCoordinate(index, maze.getCols()).getRow();
    this.col = Coordinate.getCoordinate(index, maze.getCols()).getCol();
    hitBox = new Rectangle(col * 20 + 3, row * 20 + 50 + 3, 14, 14);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getIndex() {
    return index;
  }

  public Rectangle getHitbox() {
    return hitBox;
  }
}
