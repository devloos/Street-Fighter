package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;
import javafx.util.Pair;

public class MakeMove extends Message {
  public MakeMove(int row, int column) {
    super(MessageType.MakeMove);
    this.tile = new Pair<Integer, Integer>(row, column);
  }

  public Pair<Integer, Integer> getTile() {
    return this.tile;
  }

  public void setTitle(int row, int column) {
    this.tile = new Pair<Integer, Integer>(row, column);
  }

  private Pair<Integer, Integer> tile = null;
}
