package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Maze {
  private ArrayList<String> mazeArr;
  private int rows;
  private int cols;

  public Maze(String fileDir) {
    rows = 0;
    mazeArr = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileDir));
      String line = null;
      while ((line = reader.readLine()) != null) {
        for (String s : line.split("")) {
          mazeArr.add(s);
          cols = line.length();
        }
        rows += 1;
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<String> getMaze() {
    return mazeArr;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }
}
