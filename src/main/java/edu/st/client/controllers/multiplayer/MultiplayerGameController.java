package edu.st.client.controllers.multiplayer;

import java.util.ArrayList;

import edu.st.client.controllers.BaseController;
import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import edu.st.client.services.GameService;
import edu.st.common.Util;
import edu.st.common.models.Token;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MultiplayerGameController extends BaseController {
  // all fxml properties
  public AnchorPane overlay = null;
  public GridPane grid = null;
  public AnchorPane win = null;
  public AnchorPane tie = null;
  public AnchorPane profile1 = null;
  public AnchorPane profile2 = null;
  public Button restart_btn = null;
  public Button return_btn = null;

  public MultiplayerGameController(Player player1, Player player2) {
    p1 = player1;
    p2 = player2;
    GameService.initBoard(board);
    FxService.setMedia("audio/vs.mp3");
    FxService.playMedia();
  }

  @FXML
  public void initialize() {
    GameService.setPlayerProfile(profile1, p1);
    GameService.setPlayerProfile(profile2, p2);

    restart_btn.setOnAction(event -> {
      overlay.setVisible(false);
      win.setVisible(false);
      tie.setVisible(false);
      resetGame();
    });

    return_btn.setOnAction(event -> {
      FxService.switchViews("views/Login.fxml", null);
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

    Player p = null;

    // takes previous player since updateBoard is not a
    // pure function and mutates current player
    if (currentPlayer == Token.X) {
      p = p2;
    } else {
      p = p1;
    }

    isGameOver(p);
  }

  private boolean isGameOver(Player player) {
    if (Util.isWinner(board)) {
      GameService.addTask(GameService.DELAY, () -> {
        GameService.setPlayerProfile(win, player);
        overlay.setVisible(true);
        win.setVisible(true);
      });
      return true;
    } else if (Util.isBoardFull(board)) {
      GameService.addTask(GameService.DELAY, () -> {
        overlay.setVisible(true);
        tie.setVisible(true);
      });
      return true;
    }

    return false;
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

  private ArrayList<ArrayList<Token>> board = new ArrayList<ArrayList<Token>>();
  private Player p1 = null;
  private Player p2 = null;
  private Token currentPlayer = Token.X;
}
