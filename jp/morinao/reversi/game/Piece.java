package jp.morinao.reversi.game;

public enum Piece {
  BLACK("黒", "○"),
  WHITE("白", "●");

  private String name;
  private String appearance;

  private Piece(String name, String appearance) {
    this.name = name;
    this.appearance = appearance;
  }

  public String getName() {
    return name;
  }

  public String getAppearance() {
    return appearance;
  }

  public Piece getOpponent() {
    if (this == BLACK) {
      return WHITE;
    } else {
      return BLACK;
    }
  }
}
