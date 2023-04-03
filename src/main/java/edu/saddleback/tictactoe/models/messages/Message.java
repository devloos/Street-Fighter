package edu.saddleback.tictactoe.models.messages;

import java.io.Serializable;

public class Message implements Serializable {
  public Message(MessageType type) {
    this.type = type;
  }

  public Message(MessageType type, String message) {
    this.type = type;
    this.message = message;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  private MessageType type = null;
  private String message = null;
}