package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;
import javafx.util.Pair;

public class MoveMade extends Message {
  public MoveMade(Pair<Integer, Integer> tile) {
    super(MessageType.MoveMade);
    this.tile = tile;
  }

  public Pair<Integer, Integer> getTile() {
    return tile;
  }

  public void setTile(Pair<Integer, Integer> tile) {
    this.tile = tile;
  }
  private Pair<Integer, Integer> tile = null;
}