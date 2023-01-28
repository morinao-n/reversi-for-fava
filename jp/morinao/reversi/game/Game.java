package jp.morinao.reversi.game;

import java.util.Scanner;

public class Game {

  private Board board;
  private Piece turn;

  public Game() {
    board = new Board();
    turn = Piece.BLACK;
  }

  public void startGame() {
    System.out.println("------GAME START------");
    var scanner = new Scanner(System.in);
    while (true) {
      board.showBoard();
      if (!hasPlaces(turn) && !hasPlaces(turn.getOpponent())) {
        outputWinner();
        break;
      }
      System.out.print(turn.getName() + "の番です。");
      if (!hasPlaces(turn)) {
        System.out.println("パスします。エンターキーを押してください。");
        scanner.nextLine();
        setNextTurn();
        continue;
      }
      System.out.println(
        "打つ場所を入力してエンターキーを押してください。(例:A1)"
      );
      while (true) {
        var stringCoordinate = scanner.nextLine();
        try {
          var coordinate = toCoordinate(stringCoordinate);
          var reversiblePlaces = board.getReversiblePlaces(turn, coordinate);
          if (reversiblePlaces.isEmpty()) {
            throw new Exception();
          }
          reversiblePlaces.add(coordinate);
          reversiblePlaces.forEach(c -> board.setPiece(turn, c));
          break;
        } catch (Exception e) {
          System.out.println(
            "入力が正しくありません。もう一度入力してください。"
          );
        }
      }
      setNextTurn();
    }
    scanner.close();
    System.out.println("------GAME OVER------");
  }

  private void setNextTurn() {
    turn = turn.getOpponent();
  }

  private boolean hasPlaces(Piece piece) {
    return board
      .getAllCoordinates()
      .stream()
      .anyMatch(c -> board.getReversiblePlaces(piece, c).size() > 0);
  }

  private Coordinate toCoordinate(String coodinate) throws Exception {
    if (coodinate.length() != 2) {
      throw new Exception();
    }
    var stringCol = coodinate.substring(0, 1);
    int col;
    switch (stringCol) {
      case "A":
      case "a":
        col = 0;
        break;
      case "B":
      case "b":
        col = 1;
        break;
      case "C":
      case "c":
        col = 2;
        break;
      case "D":
      case "d":
        col = 3;
        break;
      case "E":
      case "e":
        col = 4;
        break;
      case "F":
      case "f":
        col = 5;
        break;
      case "G":
      case "g":
        col = 6;
        break;
      case "H":
      case "h":
        col = 7;
        break;
      default:
        throw new Exception();
    }
    var stringRow = coodinate.substring(1, 2);
    int row = Integer.parseInt(stringRow) - 1;
    if (row < 0 || 7 < row) {
      throw new Exception();
    }
    return new Coordinate(row, col);
  }

  private void outputWinner() {
    int black = 0;
    int white = 0;
    var allCoordinates = board.getAllCoordinates();
    for (int i = 0; i < allCoordinates.size(); i++) {
      var piece = board.getPiece(allCoordinates.get(i));
      if (piece == Piece.BLACK) {
        black++;
      }
      if (piece == Piece.WHITE) {
        white++;
      }
    }
    if (black == white) {
      System.out.println("Draw");
    } else {
      var winner = black > white ? Piece.BLACK : Piece.WHITE;
      System.out.println(winner.getName() + "の勝利です");
    }
  }
}
