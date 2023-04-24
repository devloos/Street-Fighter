package edu.st.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import edu.st.client.controllers.online.GameLobbyController;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.server.GameList;
import edu.st.common.serialize.SerializerFactory;

public class ClientThread extends Thread {
  public ClientThread(Socket socket, GameLobbyController lobby) {
    this.socket = socket;
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
}
