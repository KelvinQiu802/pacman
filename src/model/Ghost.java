package model;

public class Ghost {
  private int x;
  private int y;

  public Ghost(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void move(int distX, int distY) {
    x += distX;
    y += distY;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
