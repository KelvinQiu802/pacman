package model;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Player implements Eattable {
  private int x;
  private int y;
  private int initX;
  private int initY;
  private Directions directions;
  private Boolean isPower;
  private Boolean hitWall;
  private Rectangle hitBox;

  public Player(int x, int y) {
    this.x = x;
    this.y = y;
    this.initX = x;
    this.initY = y;
    hitBox = new Rectangle(x, y, 20, 20);
    directions = Directions.LEFT; // 默认都先向左移动
    isPower = false;
    hitWall = false;
  }

  public void reset() {
    x = initX;
    y = initY;
    hitBox = new Rectangle(x, y, 20, 20);
  }

  public void move() {
    int speed = 2;
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

  public boolean eat(Eattable d) {
    return hitBox.intersects(d.getHitbox());
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

  public boolean isPowering() {
    return isPower;
  }

  public void setPower() {
    isPower = true;
    Timer timer = new Timer();
    TimerTask power = new TimerTask() {
      public void run() {
        isPower = false;
      }
    };
    timer.schedule(power, 5000);
  }
}
