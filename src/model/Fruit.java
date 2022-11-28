package model;

public class Fruit {
  private int row;
  private int col;
  private int index;

  public Fruit(int index, Maze maze) {
    this.index = index;
    this.row = Coordinate.getCoordinate(index, maze.getCols()).getRow();
    this.col = Coordinate.getCoordinate(index, maze.getCols()).getCol();
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