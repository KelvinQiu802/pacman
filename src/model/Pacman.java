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
    }
  }

  public void movePlayer() {
    playerHitWall();
    if (!player.isHitWall()) {
      player.move();
    } else {
      System.out.println("NO");
      switch (player.getDirection()) {
        case UP:
          player.move(0, 1);
          break;
        case DOWN:
          player.move(0, -1);
          break;
        case RIGHT:
          player.move(-1, 0);
          break;
        case LEFT:
          player.move(1, 0);
          break;
      }
      player.setHitWall(false);
    }
    if (listener.isPressingLeft()) {
      player.changeDirection(Directions.LEFT);
    } else if (listener.isPressingRight()) {
      player.changeDirection(Directions.RIGHT);
    } else if (listener.isPressingUp()) {
      player.changeDirection(Directions.UP);
    } else if (listener.isPressingDown()) {
      player.changeDirection(Directions.DOWN);
    }
  }

  public void playerHitWall() {
    for (Wall wall : wallList) {
      if (wall.isHitten(player)) {
        player.setHitWall(true);
      }
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
