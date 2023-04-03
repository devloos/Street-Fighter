package edu.saddleback.tictactoe.models.messages.server;

import java.util.ArrayList;

import edu.saddleback.tictactoe.models.messages.Message;

public class GameList extends Message {
  public ArrayList<Integer> lobby_ids = null;
}
