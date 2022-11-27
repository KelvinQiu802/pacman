package model;

import java.awt.*;

public class Wall {
  private int row;
  private int col;
  private int index;
  private Rectangle hitBox;

  public Wall(int index, Maze maze) {
    this.index = index;
    this.row = Coordinate.getCoordinate(index, maze.getCols()).getRow();
    this.col = Coordinate.getCoordinate(index, maze.getCols()).getCol();
    hitBox = new Rectangle(col * 20, row * 20 + 50, 20, 20);
  }

  public boolean isHitten(Moveable m) {
    // 暂时换一种碰撞检测的方法
    return hitBox.intersects(m.getHitbox());
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
}
