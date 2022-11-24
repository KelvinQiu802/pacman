package model;

import java.awt.*;

public class Ghost {
  private int x;
  private int y;
  private Rectangle hitBox;

  public Ghost(int x, int y) {
    this.x = x;
    this.y = y;
    hitBox = new Rectangle(x, y, 20, 20);
  }

  public void move(int distX, int distY) {
    x += distX;
    y += distY;
  }

  public boolean isEatten(Player p) {
    Rectangle s = p.getHitbox();
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
