package model;

import screen.PlayerListener;
import ucd.comp2011j.engine.Game;

public class Pacman implements Game {
  // Height: 24 * 20 + 50(Info)
  // Width: 20 * 20
  public static final int SCREEN_HEIGHT = 530;
  public static final int SCREEN_WIDTH = 400;
  private static final int NO_LEVELS = 14;

  private PlayerListener listener;
  private int playerLives;
  private int playerScore;
  private boolean pause = false;
  private int currentLevel = 1;

  private Player player;

  public Pacman(PlayerListener listener) {
    this.listener = listener;
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
    System.out.println("Hello");
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
    System.out.println("NONO");
    if (listener.hasPressedPause()) {
      pause = !pause;
      listener.resetPause();
    }
  }

  @Override
  public void startNewGame() {
    playerLives = 3;
    playerScore = 0;
    player = new Player(200, 200);
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
