package model;

import screen.PlayerListener;
import ucd.comp2011j.engine.Game;

public class Pacman implements Game {
  private PlayerListener listener;

  public Pacman(PlayerListener linster) {
    this.listener = listener;
    startNewGame();
  }

  @Override
  public int getPlayerScore() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getLives() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void updateGame() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isPaused() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void checkForPause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void startNewGame() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isLevelFinished() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isPlayerAlive() {
    // TODO Auto-generated method stub
    return false;
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
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getScreenHeight() {
    // TODO Auto-generated method stub
    return 0;
  }

}
