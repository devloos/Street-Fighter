package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import javafx.util.Pair;

public class MoveMade extends Message {
  public Pair<Integer, Integer> tile = null;
}
