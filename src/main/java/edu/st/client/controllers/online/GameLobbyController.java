package edu.st.client.controllers.online;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import edu.st.client.Main;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.Received;
import edu.st.common.messages.Subscribe;
import edu.st.common.messages.client.CreateGame;
import edu.st.common.messages.client.JoinGame;
import edu.st.common.messages.server.GameList;
import edu.st.common.messages.server.GameStarted;
import edu.st.common.messages.server.MoveMade;
import edu.st.common.models.Game;
import edu.st.common.models.GamePair;
import edu.st.common.serialize.SerializerFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

public class GameLobbyController {
  public ListView<HBox> gameList = null;
  public TextField username = null;
  public Button createGamebtn = null;

  public GameLobbyController() {
    try {
      this.socket = new Socket(host, port);
      this.output = new PrintWriter(socket.getOutputStream(), true);

      ArrayList<String> channels = new ArrayList<>();
      channels.add("/gamelist");
      Subscribe subscribe = new Subscribe(channels);
      Packet<Subscribe> packet = new Packet<Subscribe>(subscribe, "/" + this.socket);

      String serializedMsg = SerializerFactory.getSerializer().serialize(packet);
      output.println(serializedMsg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  };

  @FXML
  public void initialize() {
    createGamebtn.setOnAction(event -> {
      if (username.getText().isEmpty()) {
        return;
      }

      CreateGame message = new CreateGame(username.getText());
      Packet<CreateGame> packet = new Packet<CreateGame>(message, "/creategame");
      this.output.println(SerializerFactory.getSerializer().serialize(packet));

      try {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/WaitingRoom.fxml"));
        loader.setController(this);
        Stage stage = (Stage) Window.getWindows().get(0);
        AnchorPane pane = loader.<AnchorPane>load();
        stage.getScene().setRoot(pane);
      } catch (IOException e) {
        e.printStackTrace();
      }
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

              if (message.getType().contains("GameList")) {
                GameList gameListMsg = (GameList) message;
                Platform.runLater(() -> {
                  this.setGameList(gameListMsg.getGames());
                });
              }

              if (message.getType().contains("GameStarted")) {
                GameStarted msg = (GameStarted) message;
                flag = false;
                Platform.runLater(() -> {
                  this.gameStarted(msg.getUsername(), msg.getGameId(), msg.isHost());
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

  public void setGameList(ArrayList<GamePair> games) {
    gameList.getItems().clear();
    for (GamePair game : games) {
      HBox hbox = new HBox();
      hbox.setStyle("-fx-padding: 5px;");
      hbox.setAlignment(Pos.CENTER);
      hbox.setSpacing(90);

      Label gameNameLabel = new Label("Game ID: " + game.getGameId());
      gameNameLabel.getStyleClass().add("label");
      hbox.getChildren().add(gameNameLabel);

      Label hostLabel = new Label("Host: " + game.getHostname());
      hostLabel.getStyleClass().add("label");
      hbox.getChildren().add(hostLabel);

      Button btnJoin = new Button("Join Game");
      btnJoin.getStyleClass().add("btn");
      btnJoin.setOnAction(event -> {
        if (username.getText().isEmpty()) {
          return;
        }

        JoinGame message = new JoinGame(username.getText(), game.getGameId());
        Packet<JoinGame> packet = new Packet<JoinGame>(message, "/joingame");
        this.output.println(SerializerFactory.getSerializer().serialize(packet));
      });
      hbox.getChildren().add(btnJoin);

      gameList.getItems().add(hbox);
    }
  }

  public void gameStarted(String playerUsername, UUID gameId, boolean isHost) {
    String hostName = isHost ? username.getText() : playerUsername;
    String userName = !isHost ? username.getText() : playerUsername;
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/OnlineGame.fxml"));
      loader.setController(new OnlineGameController(hostName, userName, socket, gameId));
      Stage stage = (Stage) Window.getWindows().get(0);
      AnchorPane pane = loader.<AnchorPane>load();
      stage.getScene().setRoot(pane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Socket socket = null;
  private PrintWriter output = null;
  private boolean flag = true;
  public static final int port = 8000;
  public static final String host = "localhost";
}
