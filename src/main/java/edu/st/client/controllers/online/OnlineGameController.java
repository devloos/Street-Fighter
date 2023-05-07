package edu.st.client.controllers.online;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import edu.st.client.Main;
import edu.st.client.models.Player;
import edu.st.client.services.GameService;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.Received;
import edu.st.common.messages.client.MakeMove;
import edu.st.common.messages.server.MoveMade;
import edu.st.common.models.Token;
import edu.st.common.serialize.SerializerFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class OnlineGameController {
  public AnchorPane overlay = null;
  public GridPane grid = null;
  public AnchorPane profile1 = null;
  public AnchorPane profile2 = null;
  public Button restart_btn = null;
  public Button return_btn = null;

  public OnlineGameController(String host, String player, Socket socket, UUID gameId) {
    this.host = new Player(Main.class.getResource("images/avatars/Balrog.jpg"), host);
    this.player = new Player(Main.class.getResource("images/avatars/Bison.jpg"), player);
    this.socket = socket;
    this.gameId = gameId;
  }

  @FXML
  public void initialize() {
    GameService.setPlayerProfile(profile1, host);
    GameService.setPlayerProfile(profile2, player);

    // restart_btn.setOnAction(event -> {
    // overlay.setVisible(false);
    // resetGame();
    // });

    // return_btn.setOnAction(event -> {
    // try {
    // FXMLLoader loader = new
    // FXMLLoader(Main.class.getResource("views/Login.fxml"));
    // AnchorPane pane = loader.<AnchorPane>load();
    // Stage stage = (Stage) Window.getWindows().get(0);
    // media.stop();
    // stage.getScene().setRoot(pane);
    // } catch (IOException e) {
    // System.out.println(e);
    // }
    // });

    Thread thread = new Thread(
        () -> {
          try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (flag) {
              String m = input.readLine();

              if (m == null) {
                continue;
              }

              Packet<Message> packet = SerializerFactory.getSerializer().deserialize(m);

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
                System.out.println("MoveMade");
                MoveMade msg = (MoveMade) message;
                Platform.runLater(() -> {
                  this.updateUI(msg.getRow(), msg.getCol());
                });
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (ClassNotFoundException e) {
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

    println(socket, new MakeMove(row, col), gameId.toString());
  }

  public void updateUI(Integer row, Integer col) {
    HBox tile = (HBox) grid.getChildren().stream()
        .filter(node -> GridPane.getRowIndex(node).equals(row) && GridPane.getColumnIndex(node).equals(col)).findFirst()
        .orElse(null);

    System.out.println(tile);
    switch (currentPlayer) {
      case X: {
        GameService.setPlayerTile(tile, host);
        setCurrentPlayer(Token.Y);
        break;
      }
      case Y: {
        GameService.setPlayerTile(tile, player);
        setCurrentPlayer(Token.X);
        break;
      }
      default: {
        break;
      }
    }
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

  private void println(Socket socket, Message message, String channel) {
    Packet<Message> packet = new Packet<Message>(message, channel);
    try {
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
      output.println(SerializerFactory.getSerializer().serialize(packet));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Player host = null;
  private Player player = null;
  private Socket socket = null;
  private UUID gameId = null;
  private Token currentPlayer = Token.X;
  private boolean flag = true;
}
