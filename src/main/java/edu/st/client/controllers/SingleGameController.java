package edu.st.client.controllers;

import java.util.ArrayList;
import java.util.Random;

import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import edu.st.client.services.GameService;
import edu.st.common.Util;
import edu.st.common.models.Token;
import javafx.application.Platform;
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

  public SingleGameController(Player player, Player cpu) {
    this.player = player;
    this.cpu = cpu;
    initBoard();
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
      GameService.addTask(700, () -> {
        overlay.setVisible(true);
      });
    } else {
      GameService.addTask(700, () -> {
        cpuMove();
        if (Util.isWinner(board) || Util.isBoardFull(board)) {
          overlay.setVisible(true);
        }
      });
    }
  }

  private void cpuMove() {
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

  private void initBoard() {
    board = new ArrayList<ArrayList<Token>>();
    for (int i = 0; i < 3; ++i) {
      board.add(new ArrayList<Token>());
      for (int j = 0; j < 3; ++j) {
        board.get(i).add(null);
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

  private Player player = null;
  private Player cpu = null;
  private ArrayList<ArrayList<Token>> board = null;
  private Token currentPlayer = Token.X;
}
