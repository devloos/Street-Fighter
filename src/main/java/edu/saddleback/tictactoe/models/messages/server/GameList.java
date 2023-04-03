package edu.saddleback.tictactoe.models.messages.server;

import java.util.ArrayList;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameList extends Message {
  public GameList(ArrayList<Integer> lobbyIds) {
    super(MessageType.GameList);
    this.lobbyIds = lobbyIds;
  }

  public ArrayList<Integer> getLobbyIds() {
    return lobbyIds;
  }

  public void setLobbyIds(ArrayList<Integer> lobbyIds) {
    this.lobbyIds = lobbyIds;
  }

  private ArrayList<Integer> lobbyIds = null;
}
