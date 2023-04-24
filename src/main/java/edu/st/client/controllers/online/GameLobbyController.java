package edu.st.client.controllers.online;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import edu.st.client.ClientThread;
import edu.st.common.messages.Packet;
import edu.st.common.messages.Subscribe;
import edu.st.common.messages.client.CreateGame;
import edu.st.common.models.Game;
import edu.st.common.serialize.SerializerFactory;
import javafx.fxml.FXML;
import javafx.geometry.Pos;

public class GameLobbyController {
  public ListView<HBox> gameList = null;
  public TextField gameId = null;
  public TextField hostname = null;
  public Button createGamebtn = null;

  public GameLobbyController() {
    try {
      this.socket = new Socket(host, port);
      this.output = new PrintWriter(socket.getOutputStream(), true);

      ArrayList<String> channels = new ArrayList<>();
      channels.add("/subscribe/gamelist");
      Subscribe subscribe = new Subscribe(channels);
      Packet<Subscribe> packet = new Packet<Subscribe>(subscribe, "/subscribe");

      String serializedMsg = SerializerFactory.getSerializer().serialize(packet);
      output.println(serializedMsg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  };

  @FXML
  public void initialize() {
    ClientThread thread = new ClientThread(socket, this);
    thread.start();

    createGamebtn.setOnAction(event -> {
      if (hostname.getText().isEmpty()) {
        return;
      }

      if (gameId.getText().isEmpty()) {
        return;
      }

      CreateGame message = new CreateGame(hostname.getText(), gameId.getText());
      Packet<CreateGame> packet = new Packet<CreateGame>(message, "/creategame");
      this.output.println(SerializerFactory.getSerializer().serialize(packet));

      hostname.setText("");
      gameId.setText("");
    });
  }

  public void setGameList(ArrayList<Game> games) {
    gameList.getItems().clear();
    for (Game game : games) {
      HBox hbox = new HBox();
      hbox.setAlignment(Pos.CENTER);
      hbox.setSpacing(100);
      hbox.getChildren().add(new Label("Game ID: " + game.getGameId()));
      hbox.getChildren().add(new Label("Host: " + game.getHostname()));
      hbox.getChildren().add(new Button("Join Game"));

      gameList.getItems().add(hbox);
    }
  }

  private Socket socket = null;
  private PrintWriter output = null;
  public static final int port = 8000;
  public static final String host = "localhost";
}
