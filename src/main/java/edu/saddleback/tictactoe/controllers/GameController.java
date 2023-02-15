package edu.saddleback.tictactoe.controllers;

import java.io.IOException;
import java.util.ArrayList;

import edu.saddleback.tictactoe.Game;
import edu.saddleback.tictactoe.models.Player;
import edu.saddleback.tictactoe.services.GameService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
  public Button return_btn = null;

  // we should take in two players here
  public GameController(Player player1, Player player2) {
    p1 = player1;
    p2 = player2;
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

    return_btn.setOnAction(event -> {
      try {
        FXMLLoader loader = new FXMLLoader(Game.class.getResource("views/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane pane = loader.<AnchorPane>load();
        stage.getScene().setRoot(pane);
      } catch (IOException e) {
        System.out.println(e);
      }
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
    Token t1 = null;
    Token t2 = null;
    Token t3 = null;
    for (ArrayList<Token> row : board) {
      t1 = row.get(0);
      t2 = row.get(1);
      t3 = row.get(2);

      if (checkTiles(t1, t2, t3)) {
        return true;
      }
    }

    for (int col = 0; col < board.size(); ++col) {
      t1 = board.get(0).get(col);
      t2 = board.get(1).get(col);
      t3 = board.get(2).get(col);

      if (checkTiles(t1, t2, t3)) {
        return true;
      }
    }

    t1 = board.get(0).get(0);
    t2 = board.get(1).get(1);
    t3 = board.get(2).get(2);
    if (checkTiles(t1, t2, t3)) {
      return true;
    }

    t1 = board.get(0).get(2);
    t2 = board.get(1).get(1);
    t3 = board.get(2).get(0);
    if (checkTiles(t1, t2, t3)) {
      return true;
    }

    return false;
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

  private boolean checkTiles(Token t1, Token t2, Token t3) {
    if (t1 == null || t2 == null || t3 == null) {
      return false;
    }

    if (t1 == t2 && t2 == t3) {
      return true;
    }

    return false;
  }

  private ArrayList<ArrayList<Token>> board = null;
  private Player p1 = null;
  private Player p2 = null;
  private Token currentPlayer = Token.X;

  private enum Token {
    X, Y
  };
}
