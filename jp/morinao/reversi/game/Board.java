package jp.morinao.reversi.game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Board {

  private static final String BLANK = " ";
  private static final String DELOMITER = "|";

  private Piece[][] board = new Piece[8][8];

  public Board() {
    board[3][3] = Piece.WHITE;
    board[3][4] = Piece.BLACK;
    board[4][3] = Piece.BLACK;
    board[4][4] = Piece.WHITE;
  }

  public Piece getPiece(Coordinate coordinate) {
    return board[coordinate.getRow()][coordinate.getCol()];
  }

  public void setPiece(Piece piece, Coordinate coordinate) {
    board[coordinate.getRow()][coordinate.getCol()] = piece;
  }

  public void showBoard() {
    System.out.println(BLANK + BLANK + BLANK + "A B C D E F G H");
    for (int i = 0; i < board.length; i++) {
      System.out.print((i + 1) + BLANK);
      for (int j = 0; j < board[i].length; j++) {
        var pieceAppearance = board[i][j] == null
          ? BLANK
          : board[i][j].getAppearance();
        System.out.print(DELOMITER + pieceAppearance);
      }
      System.out.println(DELOMITER);
    }
  }

  public List<Coordinate> getAllCoordinates() {
    List<Coordinate> result = new ArrayList<>();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        result.add(new Coordinate(i, j));
      }
    }
    return result;
  }

  public List<Coordinate> getReversiblePlaces(
    Piece piece,
    Coordinate coordinate
  ) {
    List<Coordinate> result = new ArrayList<>();
    if (getPiece(coordinate) != null) {
      return result;
    }
    var upwardConfirmations = coordinate.getRow();
    var rightwardConfirmations = board[0].length - 1 - coordinate.getCol();
    var downwardConfirmations = board.length - 1 - coordinate.getRow();
    var leftwardConfirmations = coordinate.getCol();
    // 上方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        upwardConfirmations,
        c -> new Coordinate(c.getRow() - 1, c.getCol())
      )
    );
    // 右上方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        upwardConfirmations < rightwardConfirmations
          ? upwardConfirmations
          : rightwardConfirmations,
        c -> new Coordinate(c.getRow() - 1, c.getCol() + 1)
      )
    );
    // 右方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        rightwardConfirmations,
        c -> new Coordinate(c.getRow(), c.getCol() + 1)
      )
    );
    // 右下方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        downwardConfirmations < rightwardConfirmations
          ? downwardConfirmations
          : rightwardConfirmations,
        c -> new Coordinate(c.getRow() + 1, c.getCol() + 1)
      )
    );
    // 下方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        downwardConfirmations,
        c -> new Coordinate(c.getRow() + 1, c.getCol())
      )
    );
    // 左下方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        downwardConfirmations < leftwardConfirmations
          ? downwardConfirmations
          : leftwardConfirmations,
        c -> new Coordinate(c.getRow() + 1, c.getCol() - 1)
      )
    );
    // 左方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        leftwardConfirmations,
        c -> new Coordinate(c.getRow(), c.getCol() - 1)
      )
    );
    // 左上方向
    result.addAll(
      getunidirectionalReversiblePlaces(
        piece,
        coordinate,
        upwardConfirmations < leftwardConfirmations
          ? upwardConfirmations
          : leftwardConfirmations,
        c -> new Coordinate(c.getRow() - 1, c.getCol() - 1)
      )
    );
    return result;
  }

  private List<Coordinate> getunidirectionalReversiblePlaces(
    Piece piece,
    Coordinate coordinate,
    int roopNum,
    UnaryOperator<Coordinate> function
  ) {
    List<Coordinate> result = new ArrayList<>();
    for (int i = 1; i <= roopNum; i++) {
      coordinate = function.apply(coordinate);
      var coordinatePiece = getPiece(coordinate);
      if (coordinatePiece == null) {
        break;
      }
      if (coordinatePiece == piece) {
        return result;
      }
      result.add(coordinate);
    }
    return new ArrayList<>();
  }
}
