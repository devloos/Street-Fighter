package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameJoined extends Message {
  public GameJoined(String hostUsername) {
    super(MessageType.GameJoined);
    this.hostUsername = hostUsername;
  }

  public String getHostUsername() {
    return hostUsername;
  }

  public void setHostUsername(String hostUsername) {
    this.hostUsername = hostUsername;
  }

  private String hostUsername = null;
}
