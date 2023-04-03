package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.GameResult;
import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameEnded extends Message {
  public GameEnded(GameResult result) {
    super(MessageType.GameEnded);
    this.result = result;
  }

  public GameResult getGameResult() {
    return result;
  }

  public void setGameResult(GameResult result) {
    this.result = result;
  }

  private GameResult result = null;
}
