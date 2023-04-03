package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class PlayerReady extends Message {
  public PlayerReady() {
    super(MessageType.PlayerReady);
  }
}
