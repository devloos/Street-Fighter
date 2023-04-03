package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class AvatarSelectionStarted extends Message {
  AvatarSelectionStarted(String username) {
    super(MessageType.AvatarSelectionStarted);
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  private String username = null;
}
