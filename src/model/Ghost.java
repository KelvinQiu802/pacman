package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Ghost implements Eattable {
  private int x;
  private int y;
  private int initX;
  private int initY;
  private Rectangle hitBox;
  private Directions direction;
  private Maze curMaze;
  private Player player;
  private boolean canMove;

  public Ghost(int x, int y, Maze curMaze, Player p) {
    this.x = x;
    this.y = y;
    this.initX = x;
    this.initY = y;
    this.curMaze = curMaze;
    this.player = p;
    hitBox = new Rectangle(x, y, 20, 20);
    direction = null;
    canMove = false;
  }

  public void reset() {
    canMove = false;
    x = initX;
    y = initY;
    hitBox = new Rectangle(x, y, 20, 20);
  }

  private class Choice implements Comparable<Choice> {
    protected Directions direction;
    protected double distance;

    public Choice(Directions d, double dist) {
      this.direction = d;
      this.distance = dist;
    }

    public int compareTo(Choice c) {
      if (distance > c.distance) {
        return 1;
      } else if (distance < c.distance) {
        return -1;
      } else {
        return 0;
      }
    }

    public String toString() {
      return direction + " " + distance;
    }
  }

  public void move() {
    if (!canMove) {
      return;
    }
    int speed = 1;
    if ((x / 20.0) % 1 == 0 && ((y - 50) / 20.0) % 1 == 0) {
      if (player.isPowering()) {
        getNextDirection(false);
      } else {
        getNextDirection(true);
      }
    }
    switch (direction) {
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

  public void getNextDirection(boolean chase) {
    int speed = 1;
    ArrayList<Directions> allDirections = new ArrayList<>();
    ArrayList<Directions> okDirections = new ArrayList<>();
    ArrayList<Choice> allChioces = new ArrayList<>();
    allDirections.add(Directions.UP);
    allDirections.add(Directions.DOWN);
    allDirections.add(Directions.LEFT);
    allDirections.add(Directions.RIGHT);

    // 排除掉不能走的路
    for (int i = 0; i < allDirections.size(); i++) {
      if (!checkHitWall(allDirections.get(i))) {
        okDirections.add(allDirections.get(i));
      }
    }
    allDirections = okDirections;

    // 如果没有方向了或只有一个方向
    if (allDirections.size() == 1) {
      direction = allDirections.get(0);
    }

    // 计算不同选择和玩家的距离
    for (int i = 0; i < allDirections.size(); i++) {
      int tempX, tempY;
      double dist;
      switch (allDirections.get(i)) {
        case UP:
          tempX = x;
          tempY = y - speed;
          dist = Math.abs(Math.pow(tempX - player.getX(), 2) + Math.pow(tempY - player.getY(), 2));
          allChioces.add(new Choice(Directions.UP, dist));
          break;
        case DOWN:
          tempX = x;
          tempY = y + speed;
          dist = Math.abs(Math.pow(tempX - player.getX(), 2) + Math.pow(tempY - player.getY(), 2));
          allChioces.add(new Choice(Directions.DOWN, dist));
          break;
        case LEFT:
          tempX = x - speed;
          tempY = y;
          dist = Math.abs(Math.pow(tempX - player.getX(), 2) + Math.pow(tempY - player.getY(), 2));
          allChioces.add(new Choice(Directions.LEFT, dist));
          break;
        case RIGHT:
          tempX = x + speed;
          tempY = y;
          dist = Math.abs(Math.pow(tempX - player.getX(), 2) + Math.pow(tempY - player.getY(), 2));
          allChioces.add(new Choice(Directions.RIGHT, dist));
          break;
      }
    }

    // 按照距离从小到大排序
    Collections.sort(allChioces);
    if (chase) {
      direction = allChioces.get(0).direction;
    } else {
      direction = allChioces.get(allChioces.size() - 1).direction;
    }
  }

  public boolean checkHitWall(Directions direction) {
    int row, col, index;
    switch (direction) {
      case UP:
        row = (y - 1 - 50) / 20;
        col = x / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return true;
        }
        break;
      case DOWN:
        row = (y - 50) / 20;
        col = x / 20;
        index = Coordinate.getIndex(row + 1, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return true;
        }
        break;
      case LEFT:
        row = (y - 50) / 20;
        col = (x - 1) / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return true;
        }
        break;
      case RIGHT:
        row = (y - 50) / 20;
        col = x / 20;
        index = Coordinate.getIndex(row, col + 1, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return true;
        }
        break;
    }
    return false;
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

  public Directions getNewDirection() {
    return direction;
  }

  public void setMove(boolean t) {
    canMove = t;
  }
}
