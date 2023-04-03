package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameNotJoinable extends Message {
  public GameNotJoinable() {
    super(MessageType.GameNotjoinable);
  }
}
