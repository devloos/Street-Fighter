package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameJoined extends Message {
  public GameJoined(String host_username) {
    super(MessageType.GameJoined);
    this.host_username = host_username;
  }

  public String getHostUsername() {
    return host_username;
  }

  public void setHostUsername(String host_username) {
    this.host_username = host_username;
  }

  private String host_username = null;
}
