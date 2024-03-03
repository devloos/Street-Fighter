package edu.st.common.messages.server;

import java.util.UUID;

import edu.st.common.messages.Message;

public class GameStarted extends Message {
  private String username = null;
  private UUID gameId = null;
  private boolean isHost = false;

  public GameStarted() {
  }

  public GameStarted(String username, UUID gameId, boolean isHost) {
    this.username = username;
    this.gameId = gameId;
    this.isHost = isHost;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UUID getGameId() {
    return gameId;
  }

  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }

  public boolean isHost() {
    return isHost;
  }

  public void setHost(boolean isHost) {
    this.isHost = isHost;
  }
}
