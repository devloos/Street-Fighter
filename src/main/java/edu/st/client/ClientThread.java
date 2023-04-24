package edu.st.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import edu.st.client.controllers.online.GameLobbyController;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.server.GameList;
import edu.st.common.models.Game;
import edu.st.common.serialize.SerializerFactory;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClientThread extends Thread {
  public ClientThread(Socket socket, GameLobbyController lobby, ListView<HBox> gameList) {
    this.socket = socket;
    this.gameList = gameList;
    this.lobby = lobby;
  }

  @Override
  public void run() {
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      while (!this.socket.isClosed()) {
        String m = input.readLine();

        if (m == null) {
          continue;
        }

        Packet<Message> packet = SerializerFactory.getSerializer().deserialize(m);

        if (packet == null) {
          continue;
        }

        Message message = packet.getMessage();

        if (message.getType().contains("GameList")) {
          GameList gameListMsg = (GameList) message;
          this.lobby.setGameList(gameListMsg.getGames());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private Socket socket = null;
  private GameLobbyController lobby = null;
  private ListView<HBox> gameList = null;
}
