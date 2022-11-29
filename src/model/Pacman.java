package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
  private int tempScore = 0;
  private boolean pause = true;
  private int currentLevel = 2;
  private String[] level;
  private Maze curMaze;

  private Player player;
  private ArrayList<Ghost> ghostList;
  private ArrayList<Ghost> homeGhostList;
  private ArrayList<Ghost> pendingGhostList = new ArrayList<>(); // start timer
  private ArrayList<Dot> dotList;
  private ArrayList<Fruit> fruitList;
  private ArrayList<Power> powerList;
  private ArrayList<Wall> wallList;
  private ArrayList<AirWall> airWallList;
  private Fruit curFruit;
  private int curFruitIndex;
  private int eatGhostCounter;

  private Timer timer = new Timer();
  private boolean refreshFruit = true;

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
      manageGhost();
      moveGhost();
      playerEat();
      randomFruit();
    }
  }

  public void manageGhost() {
    Random rand = new Random();
    int waitTime = 0;
    if (homeGhostList.size() == ghostList.size()) {
      homeGhostList.get(0).setMove(true);
      homeGhostList.remove(0);
    }
    for (int i = 0; i < homeGhostList.size(); i++) {
      waitTime += rand.nextInt(15 * 100, 30 * 100);
      Ghost g = homeGhostList.get(i);
      pendingGhostList.add(g);
      TimerTask pend = new TimerTask() {
        public void run() {
          g.setMove(true);
          pendingGhostList.remove(g);
        }
      };
      timer.schedule(pend, waitTime);
    }
    homeGhostList.clear();
  }

  public void moveGhost() {
    for (int i = 0; i < ghostList.size(); i++) {
      Ghost g = ghostList.get(i);
      g.move();
    }
  }

  public void randomFruit() {
    if (refreshFruit && fruitList.size() != 0) {
      Random rand = new Random();
      curFruitIndex = rand.nextInt(fruitList.size());
      curFruit = fruitList.get(curFruitIndex);
      refreshFruit = !refreshFruit;
      TimerTask changeRefreshState = new TimerTask() {
        public void run() {
          refreshFruit = !refreshFruit;
        }
      };
      int delay = rand.nextInt(5000, 10001);
      timer.schedule(changeRefreshState, delay);
    }
  }

  public void playerEat() {
    // Eat Dot
    for (int i = 0; i < dotList.size(); i++) {
      if (player.eat(dotList.get(i))) {
        dotList.remove(i);
        addScore(10);
      }
    }
    // Eat Fruit
    if (curFruit != null) {
      if (player.eat((curFruit))) {
        fruitList.remove(curFruitIndex);
        curFruit = null;
        addScore(500);
      }
    }
    // Eat Power
    for (int i = 0; i < powerList.size(); i++) {
      if (player.eat(powerList.get(i))) {
        powerList.remove(i);
        player.setPower();
        addScore(50);
        eatGhostCounter = 0;
      }
    }
    // Eat Ghost
    if (player.isPowering()) {
      for (int i = 0; i < ghostList.size(); i++) {
        if (player.eat(ghostList.get(i))) {
          Ghost g = ghostList.get(i);
          g.reset();
          homeGhostList.add(g);
          addScore(200 * (int) Math.pow(2, eatGhostCounter));
          eatGhostCounter++;
        }
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
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
          return false;
        }
        break;
      case DOWN:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row + 1, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
          return false;
        }
        break;
      case LEFT:
        row = (player.getY() - 50) / 20;
        col = (player.getX() - 1) / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
          return false;
        }
        break;
      case RIGHT:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row, col + 1, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
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
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
          player.setHitWall(true);
        }
        break;
      case DOWN:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row + 1, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
          player.setHitWall(true);
        }
        break;
      case LEFT:
        row = (player.getY() - 50) / 20;
        col = (player.getX() - 1) / 20;
        index = Coordinate.getIndex(row, col, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
          player.setHitWall(true);
        }
        break;
      case RIGHT:
        row = (player.getY() - 50) / 20;
        col = player.getX() / 20;
        index = Coordinate.getIndex(row, col + 1, curMaze.getCols()).getIndex();
        if (curMaze.getMaze().get(index).equals("W") ||
            curMaze.getMaze().get(index).equals("-")) {
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
    eatGhostCounter = 0;
    curMaze = new Maze(level[currentLevel]);

    player = generatePlayer();
    ghostList = generateGhostList();
    dotList = generateDotList();
    fruitList = generateFruitList();
    powerList = generatePowerList();
    wallList = generateWallList();
    airWallList = generateAirWallList();
    curFruit = fruitList.get(0);

    homeGhostList = new ArrayList<>();
    for (Ghost g : ghostList) {
      homeGhostList.add(g);
    }
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
        ghostArr.add(new Ghost(posX, posY, curMaze, player));
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

  public void addScore(int num) {
    playerScore += num;
    tempScore += num;
    if (tempScore >= 10000) {
      playerLives += 1;
      tempScore = 0;
    }
  }

  public Fruit getCurrentFruit() {
    return curFruit;
  }

  public int getLevel() {
    return currentLevel;
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
