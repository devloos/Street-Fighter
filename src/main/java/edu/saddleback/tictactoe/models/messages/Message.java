package edu.saddleback.tictactoe.models.messages;

import java.io.Serializable;

public class Message implements Serializable {
  public MessageType type = null;
  public String message = null;
}