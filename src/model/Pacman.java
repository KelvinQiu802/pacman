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
  private ArrayList<Dot> dotList;

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
    if (listener.isPressingLeft()) {
      player.move(-6, 0);
    } else if (listener.isPressingRight()) {
      player.move(+6, 0);
    } else if (listener.isPressingUp()) {
      player.move(0, -6);
    } else if (listener.isPressingDown()) {
      player.move(0, +6);
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
    player = new Player(200, 200); // 在确定level后，才能根据地图算出player的位置
    curMaze = new Maze(level[currentLevel]);
    // 从maze中找到player，dot等的位置，并且创建他们自己的数据结构
    dotList = generateDotList();
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
