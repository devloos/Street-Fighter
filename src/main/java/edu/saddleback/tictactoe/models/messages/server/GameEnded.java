package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.GameResult;
import edu.saddleback.tictactoe.models.messages.Message;

public class GameEnded extends Message {
  public GameResult result;
}
