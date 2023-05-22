package edu.st.client.controllers.online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.UUID;

import edu.st.client.controllers.BaseController;
import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import edu.st.client.services.GameService;
import edu.st.common.Util;
import edu.st.common.messages.GameResult;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.Received;
import edu.st.common.messages.client.BackToMainMenu;
import edu.st.common.messages.client.MakeMove;
import edu.st.common.messages.client.PlayAgain;
import edu.st.common.messages.server.GameEnded;
import edu.st.common.messages.server.MoveMade;
import edu.st.common.models.Token;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class OnlineGameController extends BaseController {
  public AnchorPane overlay = null;
  public GridPane grid = null;
  public AnchorPane win = null;
  public AnchorPane tie = null;
  public AnchorPane profile1 = null;
  public AnchorPane profile2 = null;
  public Button restart_btn = null;
  public Button return_btn = null;

  public OnlineGameController(Player host, Player player, Socket socket, UUID gameId) {
    this.host = host;
    this.player = player;
    this.socket = socket;
    this.gameId = gameId;
  }

  @FXML
  public void initialize() {
    GameService.setPlayerProfile(profile1, host);
    GameService.setPlayerProfile(profile2, player);

    restart_btn.setOnAction(event -> {
      Util.println(socket, new PlayAgain(), gameId.toString());
      return_btn.setDisable(true);
      restart_btn.setDisable(true);
    });

    return_btn.setOnAction(event -> {
      flag = false;
      Util.println(socket, new BackToMainMenu(), gameId.toString());
      FxService.switchViews("views/Login.fxml", null);
    });

    Thread thread = new Thread(
        () -> {
          try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (flag) {
              String m = input.readLine();

              if (m == null) {
                continue;
              }

              Packet<Message> packet = Util.deserialize(m);

              if (packet == null) {
                continue;
              }

              Message message = packet.getMessage();

              if (message.getType().contains("Received")) {
                Received msg = (Received) message;

                if (!msg.isSuccess()) {
                  continue;
                }
              }

              if (message.getType().contains("MoveMade")) {
                MoveMade msg = (MoveMade) message;
                Platform.runLater(() -> {
                  this.updateUI(msg.getRow(), msg.getCol(), msg.getMoveMaker());
                });
              }

              if (message.getType().contains("GameEnded")) {
                GameEnded msg = (GameEnded) message;
                Platform.runLater(() -> {
                  this.updateUI(msg.getRow(), msg.getCol(), Token.X);
                  if (msg.getResult() == GameResult.Host) {
                    GameService.setPlayerProfile(win, host);
                    this.win.setVisible(true);
                  } else if (msg.getResult() == GameResult.Player) {
                    GameService.setPlayerProfile(win, player);
                    this.win.setVisible(true);
                  } else {
                    this.tie.setVisible(true);
                  }
                  this.overlay.setVisible(true);
                });
              }

              if (message.getType().contains("PlayAgain")) {
                Platform.runLater(() -> {
                  this.overlay.setVisible(false);
                  return_btn.setDisable(false);
                  restart_btn.setDisable(false);
                  win.setVisible(false);
                  tie.setVisible(false);
                  this.resetGame();
                });
              }

              if (message.getType().contains("BackToMainMenu")) {
                Platform.runLater(() -> {
                  flag = false;
                  FxService.switchViews("views/Login.fxml", null);
                });
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    thread.start();
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
    if (tile.getStyleClass().contains("tile-taken")) {
      return;
    }

    Util.println(socket, new MakeMove(row, col), gameId.toString());
  }

  public void updateUI(Integer row, Integer col, Token moveMaker) {
    HBox tile = (HBox) grid.getChildren().stream()
        .filter(node -> GridPane.getRowIndex(node).equals(row) && GridPane.getColumnIndex(node).equals(col)).findFirst()
        .orElse(null);

    switch (moveMaker) {
      case X: {
        GameService.setPlayerTile(tile, host);
        GameService.setPlayerThinking(profile2, true);
        GameService.setPlayerThinking(profile1, false);
        break;
      }
      case Y: {
        GameService.setPlayerTile(tile, player);
        GameService.setPlayerThinking(profile1, true);
        GameService.setPlayerThinking(profile2, false);
        break;
      }
      default: {
        break;
      }
    }
  }

  private void resetGame() {
    // clear visuals
    for (Node node : grid.getChildren()) {
      GameService.resetTile((HBox) node);
    }

    GameService.setPlayerThinking(profile1, true);
    GameService.setPlayerThinking(profile2, false);
  }

  private Player host = null;
  private Player player = null;
  private Socket socket = null;
  private UUID gameId = null;
  private volatile boolean flag = true;
}
