package edu.saddleback.tictactoe.controllers;

import java.util.ArrayList;

import edu.saddleback.tictactoe.Game;
import edu.saddleback.tictactoe.models.Player;
import edu.saddleback.tictactoe.services.GameService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GameController {
  // all fxml properties
  public AnchorPane overlay = null;
  public GridPane grid = null;
  public AnchorPane profile1 = null;
  public AnchorPane profile2 = null;
  public Button restart_btn = null;
  public Button quit_btn = null;

  // we should take in two players here
  public GameController() {
    p1 = new Player(Game.class.getResource("images/avatars/Blanka.jpg"),
        "Carlos");
    p2 = new Player(Game.class.getResource("images/avatars/Sagat.jpg"),
        "Professor");

    initBoard();
  }

  @FXML
  public void initialize() {
    GameService.setPlayerProfile(profile1, p1);
    GameService.setPlayerProfile(profile2, p2);

    restart_btn.setOnAction(event -> {
      overlay.setVisible(false);
      resetGame();
    });

    quit_btn.setOnAction(event -> {
      Stage stage = (Stage) overlay.getScene().getWindow();
      stage.close();
    });
  }

  @FXML
  public void playerClickedTile(MouseEvent event) {
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

    if (isWinner()) {
      overlay.setVisible(true);
    }
  }

  private void updateBoard(HBox tile, Integer row, Integer col) {
    switch (currentPlayer) {
      case X: {
        GameService.setPlayerTile(tile, p1);
        board.get(row).set(col, currentPlayer);
        setCurrentPlayer(Token.Y);
        break;
      }
      case Y: {
        GameService.setPlayerTile(tile, p2);
        board.get(row).set(col, currentPlayer);
        setCurrentPlayer(Token.X);
        break;
      }
      default: {
        break;
      }
    }
  }

  private boolean isWinner() {
    for (ArrayList<Token> row : board) {
      if (row.contains(null)) {
        return false;
      }
    }

    return true;
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

  private void initBoard() {
    board = new ArrayList<ArrayList<Token>>();
    for (int i = 0; i < 3; ++i) {
      board.add(new ArrayList<Token>());
      for (int j = 0; j < 3; ++j) {
        board.get(i).add(null);
      }
    }
  }

  // this function should be used to set player since it is being watched
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

  private ArrayList<ArrayList<Token>> board = null;
  private Player p1 = null;
  private Player p2 = null;
  private Token currentPlayer = Token.X;

  private enum Token {
    X, Y
  };
}
