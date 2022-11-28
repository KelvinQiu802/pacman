package model;

import java.util.ArrayList;

import screen.PlayerListener;
import ucd.comp2011j.engine.Game;

public class Pacman implements Game {
  // Height: 24 * 20 + 50(Info)
  // Width: 20 * 20
  public static final int SCREEN_HEIGHT = 530 + 50;
  public static final int SCREEN_WIDTH = 400;
  private static final int NO_LEVELS = 14;

  private PlayerListener listener;
  private int playerLives;
  private int playerScore;
  private boolean pause = false;
  private int currentLevel = 1;
  private String[] level;
  private Maze curMaze;

  private Player player;
  private ArrayList<Ghost> ghostList;
  private ArrayList<Dot> dotList;
  private ArrayList<Fruit> fruitList;
  private ArrayList<Power> powerList;
  private ArrayList<Wall> wallList;
  private ArrayList<AirWall> airWallList;

  public Pacman(PlayerListener listener) {
    this.listener = listener;
    this.level = getLevelArr();
    startNewGame();
  }

  @Override
  public int getPlayerScore() {
    return playerScore;
  }

  @Override
  public int getLives() {
    return playerLives;
  }

  @Override
  public void updateGame() {
    if (!isPaused()) {
      movePlayer();
      playerEat();
    }
  }

  public void playerEat() {
    // Eat Dot
    for (int i = 0; i < dotList.size(); i++) {
      if (player.eatDot(dotList.get(i))) {
        dotList.remove(i);
        player.addPoints(10);
      }
    }
  }

  public void movePlayer() {
    checkPlayerHitWall();
    if (!player.isHitWall()) {
      player.move();
    }
    // 只有到正中央才可以转向
    if ((player.getX() / 20.0) % 1 == 0 && ((player.getY() - 50) / 20.0) % 1 == 0) {
      if (listener.isPressingLeft()) {
        if (canChangeDirection(Directions.LEFT)) {
          player.changeDirection(Directions.LEFT);
        }
      } else if (listener.isPressingRight()) {
        if (canChangeDirection(Directions.RIGHT)) {
          player.changeDirection(Directions.RIGHT);
        }
      } else if (listener.isPressingUp()) {
        if (canChangeDirection(Directions.UP)) {
          player.changeDirection(Directions.UP);
        }
      } else if (listener.isPressingDown()) {
        if (canChangeDirection(Directions.DOWN)) {
          player.changeDirection(Directions.DOWN);
        }
      }
    }
  }

  public boolean canChangeDirection(Directions d) {
    int row, col, index;
    switch (d) {
      case UP:
        row = (player.getY() - 1 - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return false;
        }
        break;
      case DOWN:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row + 1, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return false;
        }
        break;
      case LEFT:
        row = (player.getY() - 50) / 20;
        col = (player.getX() - 1) / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return false;
        }
        break;
      case RIGHT:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row, col + 1, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          return false;
        }
        break;
    }
    return true;
  }

  public void checkPlayerHitWall() {
    int row, col, index;
    switch (player.getDirection()) {
      case UP:
        row = (player.getY() - 1 - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          player.setHitWall(true);
        }
        break;
      case DOWN:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row + 1, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          player.setHitWall(true);
        }
        break;
      case LEFT:
        row = (player.getY() - 50) / 20;
        col = (player.getX() - 1) / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          player.setHitWall(true);
        }
        break;
      case RIGHT:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row, col + 1, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W")) {
          player.setHitWall(true);
        }
        break;
    }
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public boolean isPaused() {
    return pause;
  }

  @Override
  public void checkForPause() {
    if (listener.hasPressedPause()) {
      pause = !pause;
      listener.resetPause();
    }
  }

  @Override
  public void startNewGame() {
    playerLives = 3;
    playerScore = 0;
    curMaze = new Maze(level[currentLevel]);

    player = generatePlayer();
    ghostList = generateGhostList();
    dotList = generateDotList();
    fruitList = generateFruitList();
    powerList = generatePowerList();
    wallList = generateWallList();
    airWallList = generateAirWallList();
  }

  public ArrayList<Dot> generateDotList() {
    ArrayList<String> mazeArr = curMaze.getMaze();
    ArrayList<Dot> dotArr = new ArrayList<>();
    for (int i = 0; i < mazeArr.size(); i++) {
      if (mazeArr.get(i).equals(".")) {
        dotArr.add(new Dot(i, curMaze));
      }
    }
    return dotArr;
  }

  public ArrayList<Dot> getDotList() {
    return dotList;
  }

  public ArrayList<Fruit> generateFruitList() {
    ArrayList<String> mazeArr = curMaze.getMaze();
    ArrayList<Fruit> fruitArr = new ArrayList<>();
    for (int i = 0; i < mazeArr.size(); i++) {
      if (mazeArr.get(i).equals("F")) {
        fruitArr.add(new Fruit(i, curMaze));
      }
    }
    return fruitArr;
  }

  public ArrayList<Fruit> getFruitList() {
    return fruitList;
  }

  public ArrayList<Wall> generateWallList() {
    ArrayList<String> mazeArr = curMaze.getMaze();
    ArrayList<Wall> wallArr = new ArrayList<>();
    for (int i = 0; i < mazeArr.size(); i++) {
      if (mazeArr.get(i).equals("W")) {
        wallArr.add(new Wall(i, curMaze));
      }
    }
    return wallArr;
  }

  public ArrayList<Wall> getWallList() {
    return wallList;
  }

  public ArrayList<AirWall> generateAirWallList() {
    ArrayList<String> mazeArr = curMaze.getMaze();
    ArrayList<AirWall> wallArr = new ArrayList<>();
    for (int i = 0; i < mazeArr.size(); i++) {
      if (mazeArr.get(i).equals("-")) {
        wallArr.add(new AirWall(i, curMaze));
      }
    }
    return wallArr;
  }

  public ArrayList<AirWall> getAirWallList() {
    return airWallList;
  }

  public ArrayList<Power> generatePowerList() {
    ArrayList<String> mazeArr = curMaze.getMaze();
    ArrayList<Power> powerArr = new ArrayList<>();
    for (int i = 0; i < mazeArr.size(); i++) {
      if (mazeArr.get(i).equals("*")) {
        powerArr.add(new Power(i, curMaze));
      }
    }
    return powerArr;
  }

  public ArrayList<Power> getPowerList() {
    return powerList;
  }

  public Player generatePlayer() {
    ArrayList<String> mazeArr = curMaze.getMaze();
    for (int i = 0; i < mazeArr.size(); i++) {
      if (mazeArr.get(i).equals("P")) {
        int posX = Coordinate.getCoordinate(i, curMaze.getCols()).getCol() * 20;
        int posY = Coordinate.getCoordinate(i, curMaze.getCols()).getRow() * 20 + 50;
        return new Player(posX, posY);
      }
    }
    throw new Error("Player dosen't exist");
  }

  public ArrayList<Ghost> generateGhostList() {
    ArrayList<String> mazeArr = curMaze.getMaze();
    ArrayList<Ghost> ghostArr = new ArrayList<>();
    for (int i = 0; i < mazeArr.size(); i++) {
      if (mazeArr.get(i).equals("G")) {
        int posX = Coordinate.getCoordinate(i, curMaze.getCols()).getCol() * 20;
        int posY = Coordinate.getCoordinate(i, curMaze.getCols()).getRow() * 20 + 50;
        ghostArr.add(new Ghost(posX, posY));
      }
    }
    return ghostArr;
  }

  public ArrayList<Ghost> getGhostList() {
    return ghostList;
  }

  public String[] getLevelArr() {
    String[] arr = new String[NO_LEVELS];
    for (int i = 0; i < NO_LEVELS; i++) {
      arr[i] = "./maze/" + i + ".txt";
    }
    return arr;
  }

  public Maze getMaze() {
    return curMaze;
  }

  @Override
  public boolean isLevelFinished() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isPlayerAlive() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public void resetDestroyedPlayer() {
    // TODO Auto-generated method stub

  }

  @Override
  public void moveToNextLevel() {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean isGameOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getScreenWidth() {
    return SCREEN_WIDTH;
  }

  @Override
  public int getScreenHeight() {
    return SCREEN_HEIGHT;
  }
}
