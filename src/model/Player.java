package model;

import java.awt.*;

public class Player {
  private int x;
  private int y;
  private Rectangle hitBox;

  public Player(int x, int y) {
    this.x = x;
    this.y = y;
    hitBox = new Rectangle(x, y, 20, 20);
  }

  public void move(int distX, int distY) {
    x += distX;
    y += distY;
  }

  public boolean isEatten(Ghost ghost) {
    Rectangle s = ghost.getHitbox();
    return s.intersects(hitBox.getBounds());
  }

  public Rectangle getHitbox() {
    return hitBox;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
