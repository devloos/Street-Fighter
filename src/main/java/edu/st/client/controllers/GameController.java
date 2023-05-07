package edu.st.client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import edu.st.client.Main;
import edu.st.client.models.Player;
import edu.st.client.services.GameService;
import edu.st.common.Util;
import edu.st.common.models.Token;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

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
    media = new MediaPlayer(new Media(Main.class.getResource("audio/vs.mp3").toString()));
    media.setOnEndOfMedia(() -> {
      media.seek(Duration.ZERO); // reset playback position to the beginning
      media.play(); // start playing from the beginning
    });
    media.setVolume(0.1);
    media.play();
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
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/Login.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();
        Stage stage = (Stage) Window.getWindows().get(0);
        media.stop();
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

    if (Util.isWinner(board)) {
      overlay.setVisible(true);
    } else if (Util.isBoardFull(board)) {
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
  private MediaPlayer media = null;
}
