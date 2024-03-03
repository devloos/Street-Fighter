package edu.st.common.models;

import java.io.Serializable;
import java.util.UUID;

public class GamePair implements Serializable {
  private UUID gameId = null;
  private String hostname = null;

  public GamePair() {
  }

  public GamePair(UUID gameId, String hostname) {
    this.gameId = gameId;
    this.hostname = hostname;
  }

  public UUID getGameId() {
    return gameId;
  }

  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }
}
