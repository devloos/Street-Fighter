package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class PlayAgain extends Message {
  public PlayAgain() {
    super(MessageType.PlayAgain);
  }
}
