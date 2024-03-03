package edu.st.common.messages.server;

import java.util.ArrayList;

import edu.st.common.messages.Message;
import edu.st.common.models.GamePair;

public class GameList extends Message {
  private ArrayList<GamePair> games = null;

  public GameList() {
  }

  public GameList(ArrayList<GamePair> games) {
    this.games = games;
  }

  public ArrayList<GamePair> getGames() {
    return games;
  }

  public void setGames(ArrayList<GamePair> games) {
    this.games = games;
  }
}
