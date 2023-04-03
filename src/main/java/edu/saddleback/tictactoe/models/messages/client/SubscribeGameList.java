package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class SubscribeGameList extends Message {
  // has socket data
  public SubscribeGameList() {
    super(MessageType.SubscribeGameList);
  }
}
