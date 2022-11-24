package model;

public class Coordinate {
  private int row;
  private int col;
  private int index;

  public Coordinate(int row, int col, int numCols) {
    this.row = row;
    this.col = col;
    this.index = col + row * numCols;
  }

  public Coordinate(int index, int numCols) {
    this.index = index;
    this.row = index / numCols;
    this.col = index % numCols;
  }

  public static Coordinate getCoordinate(int i, int numCols) {
    return new Coordinate(i, numCols);
  }

  public static Coordinate getIndex(int row, int col, int numCols) {
    return new Coordinate(row, col, numCols);
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
