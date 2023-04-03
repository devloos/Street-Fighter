package edu.saddleback.tictactoe.models.messages.server;

import java.util.ArrayList;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameList extends Message {
  GameList(ArrayList<Integer> lobby_ids) {
    super(MessageType.GameList);
    this.lobby_ids = lobby_ids;
  }

  public ArrayList<Integer> getLobbyIds() {
    return lobby_ids;
  }

  public void setLobbyIds(ArrayList<Integer> lobby_ids) {
    this.lobby_ids = lobby_ids;
  }
  
  private ArrayList<Integer> lobby_ids = null;
}
