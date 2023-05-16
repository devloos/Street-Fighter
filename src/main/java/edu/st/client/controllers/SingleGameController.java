package edu.st.client.controllers;

import java.util.ArrayList;
import java.util.Random;

import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import edu.st.client.services.GameService;
import edu.st.common.Util;
import edu.st.common.models.Mode;
import edu.st.common.models.Token;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SingleGameController extends BaseController {
  public AnchorPane profile1 = null;
  public AnchorPane profile2 = null;
  public Button restart_btn = null;
  public Button return_btn = null;
  public AnchorPane overlay = null;
  public GridPane grid = null;

  public SingleGameController(Player player, Player cpu, Mode mode) {
    this.player = player;
    this.cpu = cpu;
    this.mode = mode;
    GameService.initBoard(board);
    // FxService.setMedia("audio/vs.mp3");
    // FxService.playMedia();
  }

  @FXML
  public void initialize() {
    GameService.setPlayerProfile(profile1, player);
    GameService.setPlayerProfile(profile2, cpu);

    restart_btn.setOnAction(event -> {
      overlay.setVisible(false);
      resetGame();
    });

    return_btn.setOnAction(event -> {
      FxService.switchViews("views/Login.fxml", null);
    });
  }

  @FXML
  public void playerClickedTile(MouseEvent event) {
    if (currentPlayer == Token.Y) {
      return;
    }

    Node node = event.getPickResult().getIntersectedNode();
    // clicked on anything else but inner tile
    if (!(node instanceof HBox)) {
      return;
    }

    HBox tile = (HBox) node;
    Integer col = GridPane.getColumnIndex(tile);
    Integer row = GridPane.getRowIndex(tile);

    // not a valid index
    if (col == null && row == null) {
      return;
    }

    // tile is already taken
    if (board.get(row).get(col) != null) {
      return;
    }

    updateBoard(tile, row, col);

    if (Util.isWinner(board) || Util.isBoardFull(board)) {
      GameService.addTask(GameService.DELAY, () -> {
        overlay.setVisible(true);
      });
    } else if (mode == Mode.EASY) {
      GameService.addTask(GameService.DELAY, () -> {
        cpuEasyMove();
        if (Util.isWinner(board) || Util.isBoardFull(board)) {
          overlay.setVisible(true);
        }
      });
    } else if (mode == Mode.HARD) {
      GameService.addTask(GameService.DELAY, () -> {
        cpuHardMove();
        if (Util.isWinner(board) || Util.isBoardFull(board)) {
          overlay.setVisible(true);
        }
      });
    }
  }

  private void cpuEasyMove() {
    if (Util.isWinner(board) || Util.isBoardFull(board)) {
      overlay.setVisible(true);
      return;
    }

    Random random = new Random();

    Integer row = random.nextInt(3);
    Integer col = random.nextInt(3);

    boolean indexFound = false;

    while (!indexFound) {
      if (board.get(row).get(col) == null) {
        indexFound = true;
      } else {
        row = random.nextInt(3);
        col = random.nextInt(3);
      }
    }

    HBox tile = null;
    for (Node node : grid.getChildren()) {
      if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
        tile = (HBox) node;
        break;
      }
    }

    updateBoard(tile, row, col);
  }

  private void updateBoard(HBox tile, Integer row, Integer col) {
    switch (currentPlayer) {
      case X: {
        GameService.setPlayerTile(tile, player);
        board.get(row).set(col, currentPlayer);
        setCurrentPlayer(Token.Y);
        break;
      }
      case Y: {
        GameService.setPlayerTile(tile, cpu);
        board.get(row).set(col, currentPlayer);
        setCurrentPlayer(Token.X);
        break;
      }
      default: {
        break;
      }
    }
  }

  private void resetGame() {
    // clear state
    for (ArrayList<Token> row : board) {
      for (int i = 0; i < row.size(); ++i) {
        row.set(i, null);
      }
    }

    // clear visuals
    for (Node node : grid.getChildren()) {
      GameService.resetTile((HBox) node);
    }

    // Player one starts again
    setCurrentPlayer(Token.X);
  }

  private void setCurrentPlayer(Token token) {
    currentPlayer = token;
    switch (currentPlayer) {
      case X: {
        GameService.setPlayerThinking(profile1, true);
        GameService.setPlayerThinking(profile2, false);
        break;
      }
      case Y: {
        GameService.setPlayerThinking(profile2, true);
        GameService.setPlayerThinking(profile1, false);
        break;
      }
      default: {
        break;
      }
    }
  }

  // Evlaute function, checks all possible winning lines (row, columns, and
  // diagonals) and assigns a score based on whoever is winning:
  // +10 if CPU (Token.Y) is winning
  // -10 if Player (Token.X) is winning
  // 0 otherwise
  // Cite:
  // https://www.geeksforgeeks.org/finding-optimal-move-in-tic-tac-toe-using-minimax-algorithm-in-game-theory/
  private int evaluate() {
    // Check rows for winner
    for (int row = 0; row < SIZE; row++) {
      if (board.get(row).get(0) == board.get(row).get(1) && board.get(row).get(1) == board.get(row).get(2)) {
        if (board.get(row).get(0) == Token.Y) {
          return +10;
        } else if (board.get(row).get(0) == Token.X) {
          return -10;
        }
      }
    }

    // Check columns for winner
    for (int col = 0; col < SIZE; col++) {
      if (board.get(0).get(col) == board.get(1).get(col) && board.get(1).get(col) == board.get(2).get(col)) {
        if (board.get(0).get(col) == Token.Y) {
          return +10;
        } else if (board.get(0).get(col) == Token.X) {
          return -10;
        }
      }
    }

    // Check diagonals for winner
    if (board.get(0).get(0) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(2)) {
      if (board.get(0).get(0) == Token.Y) {
        return +10;
      } else if (board.get(0).get(0) == Token.X) {
        return -10;
      }
    }

    if (board.get(0).get(2) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(0)) {
      if (board.get(0).get(2) == Token.Y) {
        return +10;
      } else if (board.get(0).get(2) == Token.X) {
        return -10;
      }
    }

    // If no one has won, return 0
    return 0;
  }

  // Minimax is a recursive function that simulates all possible game states
  // (moves) and evaluates them using
  // the evaluate() function. It simulates two players (a minimizer and maximizer)
  // taking turns each trying to
  // minimize and maximize the evaluation score respectively.
  // For the maximizer (CPU), the function returns the maximum score of all
  // possible moves
  // For the minimizer (Player), the function returns the minimum score
  // Cite:
  // https://www.geeksforgeeks.org/finding-optimal-move-in-tic-tac-toe-using-minimax-algorithm-in-game-theory/
  private int minimax(int depth, Boolean isMax) {
    int score = evaluate();

    // If Maximizer won return their score
    if (score == 10) {
      return score;
    }

    // If Minimizer won return their score
    if (score == -10) {
      return score;
    }

    // no more moves, no winner -> tie
    if (Util.isBoardFull(board)) {
      return 0;
    }

    // If it is the maximizers (CPU) move
    if (isMax) {
      int best = -1000;

      for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
          if (board.get(i).get(j) == null) {
            // Make the move
            board.get(i).set(j, Token.Y);

            // Recursive
            best = Math.max(best, minimax(depth + 1, !isMax));

            // "Backtrack" - Undo the move
            board.get(i).set(j, null);
          }
        }
      }
      return best;
    } // If its the minimizer's move (player)
    else {
      int best = 1000;

      for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
          if (board.get(i).get(j) == null) {
            // Make the move
            board.get(i).set(j, Token.X);

            // Recursive
            best = Math.min(best, minimax(depth + 1, !isMax));

            // "Backtrack" - Undo the move
            board.get(i).set(j, null);
          }
        }
      }
      return best;
    }
  }

  // BestMove function uses minimax to find the best move for the computer. It
  // iterates over all empty cells
  // on the game board, and simulates the computer making a move on each cell, and
  // uses minimax to evaluate
  // the game stae. The function returns the move as an array of the highest
  // evaluation score
  // Cite:
  // https://www.geeksforgeeks.org/finding-optimal-move-in-tic-tac-toe-using-minimax-algorithm-in-game-theory/
  private int[] bestMove() {
    int bestVal = -1000;
    int[] bestMove = { -1, -1 };

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board.get(i).get(j) == null) {
          // Make the move
          board.get(i).set(j, Token.Y);

          // Compute evaluation function for this move
          int moveVal = minimax(0, false);

          // "Backtrack" - Undo the move
          board.get(i).set(j, null);

          // Check for Max Value
          if (moveVal > bestVal) {
            bestMove[0] = i;
            bestMove[1] = j;
            bestVal = moveVal;
          }
        }
      }
    }

    return bestMove;
  }

  private void cpuHardMove() {
    int[] bestMove = bestMove();

    HBox tile = null;
    for (Node node : grid.getChildren()) {
      if (GridPane.getRowIndex(node) == bestMove[0] && GridPane.getColumnIndex(node) == bestMove[1]) {
        tile = (HBox) node;
        break;
      }
    }

    updateBoard(tile, bestMove[0], bestMove[1]);
  }

  private static final int SIZE = 3;
  private Player player = null;
  private Player cpu = null;
  private ArrayList<ArrayList<Token>> board = new ArrayList<ArrayList<Token>>();
  private Token currentPlayer = Token.X;
  private Mode mode = null;
}
