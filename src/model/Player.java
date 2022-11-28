package model;

import java.awt.*;

public class Player implements Moveable {
  private int x;
  private int y;
  private Directions directions;
  private Boolean isPower;
  private Boolean hitWall;
  private Rectangle hitBox;

  public Player(int x, int y) {
    this.x = x;
    this.y = y;
    hitBox = new Rectangle(x, y, 20, 20);
    directions = Directions.LEFT; // 默认都先向左移动
    isPower = false;
    hitWall = false;
  }

  public void move() {
    int speed;
    if (isPower) {
      speed = 5;
    } else {
      speed = 1;
    }
    switch (directions) {
      case UP:
        y -= speed;
        break;
      case DOWN:
        y += speed;
        break;
      case LEFT:
        x -= speed;
        break;
      case RIGHT:
        x += speed;
        break;
    }
    hitBox = new Rectangle(x, y, 20, 20);
  }

  public void move(int distX, int distY) {
    x += distX;
    y += distY;
    hitBox = new Rectangle(x, y, 20, 20);
  }

  public boolean isEatten(Ghost ghost) {
    Rectangle s = ghost.getHitbox();
    return s.intersects(hitBox.getBounds());
  }

  public void changeDirection(Directions d) {
    hitWall = false;
    directions = d;
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

  public Directions getDirection() {
    return this.directions;
  }

  public Boolean isHitWall() {
    return hitWall;
  }

  public void setHitWall(Boolean t) {
    hitWall = t;
  }
}
