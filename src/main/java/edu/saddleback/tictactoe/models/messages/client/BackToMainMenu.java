package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class BackToMainMenu extends Message {
  public BackToMainMenu() {
    super(MessageType.BackToMainMenu);
  }
}
