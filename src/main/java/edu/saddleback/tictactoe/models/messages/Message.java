package edu.saddleback.tictactoe.models.messages;

import java.io.Serializable;

public class Message implements Serializable {
  public Message(MessageType type) {
    this.type = type;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  private MessageType type = null;
}