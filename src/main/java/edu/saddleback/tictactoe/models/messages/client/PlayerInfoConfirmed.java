package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class PlayerInfoConfirmed extends Message {
  public PlayerInfoConfirmed(String username, String avatarSelected) {
    super(MessageType.PlayerInfoConfirmed);
    this.username = username;
    this.avatarSelected = avatarSelected;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatarSelected() {
    return this.avatarSelected;
  }

  public void setAvatarSelected(String avatarSelected) {
    this.avatarSelected = avatarSelected;
  }

  private String username = null;
  private String avatarSelected = null;
}
