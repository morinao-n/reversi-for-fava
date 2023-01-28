package jp.morinao.reversi.game;

public class Coordinate {

  private int row;
  private int col;

  public Coordinate(int row, int col) {
    if (col < 0 || 7 < col || row < 0 || 7 < row) {
      throw new IllegalArgumentException();
    }
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }
}
