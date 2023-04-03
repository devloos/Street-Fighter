package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;
import javafx.util.Pair;

public class MakeMove extends Message {
  public Pair<Integer, Integer> tile = null;
}
